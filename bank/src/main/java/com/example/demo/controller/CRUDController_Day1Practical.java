package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Item;
import com.example.demo.model.ItemRequest;
import com.example.demo.service.ItemService;
import com.example.demo.validation.ItemValidation; // Import the validation class

@RestController
@RequestMapping("/demo/v2") // Base path for all endpoints in this controller
public class CRUDController_Day1Practical {

    private final ItemService itemService; // Inject the service

    // Constructor injection for ItemService
    public CRUDController_Day1Practical(ItemService itemService) {
        this.itemService = itemService;
    }

    // --- CREATE (HTTP POST - Auto-generated ID) ---
    @PostMapping
    public ResponseEntity<String> createItem(@RequestBody String newItemName) {
        try {
            ItemValidation.validateItemName(newItemName); // Validate input
            Item createdItem = itemService.createItem(newItemName);
            return new ResponseEntity<>("Item created successfully with ID: " + createdItem.id() + " and data: " + createdItem.value(), HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }

    // --- CREATE (HTTP POST - Client-provided ID) ---
    @PostMapping("/object")
    public ResponseEntity<String> createItemWithID(@RequestBody ItemRequest itemRequest) {
        try {
            ItemValidation.validateItemRequest(itemRequest); // Validate ItemRequest fields

            Long itemId = ItemValidation.parseAndValidateLongId(itemRequest.id()); // Validate and parse ID

            Optional<Item> createdItem = itemService.createItemWithProvidedId(itemId, itemRequest.value());

            if (createdItem.isPresent()) {
                return new ResponseEntity<>("Item created successfully with ID: " + createdItem.get().id() + " and data: " + createdItem.get().value(), HttpStatus.CREATED); // 201 Created
            } else {
                // If service returns empty, it means ID already exists in this context
                return new ResponseEntity<>("Item with ID: " + itemId + " already exists. Cannot create.", HttpStatus.CONFLICT); // 409 Conflict
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }

    // --- READ (HTTP GET - All Items) ---
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> allItems = itemService.getAllItems();
        return new ResponseEntity<>(allItems, HttpStatus.OK); // 200 OK
    }

    // --- READ (HTTP GET - By ID) ---
    @GetMapping("/{id}")
    public ResponseEntity<String> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemService.getItemById(id);
        if (item.isPresent()) {
            return new ResponseEntity<>("Found item with ID: " + item.get().id() + " and data: " + item.get().value(), HttpStatus.OK); // 200 OK if found
        }
        return new ResponseEntity<>("Item with ID: " + id + " not found.", HttpStatus.NOT_FOUND); // 404 Not Found if not found
    }

    // --- UPDATE (HTTP PUT) ---
    @PutMapping("/{id}")
    public ResponseEntity<String> updateItem(@PathVariable Long id, @RequestBody String updatedName) {
        try {
            ItemValidation.validateItemName(updatedName); // Re-use validation for name
            Optional<Item> updatedItem = itemService.updateItem(id, updatedName);
            if (updatedItem.isPresent()) {
                return new ResponseEntity<>("Item with ID: " + updatedItem.get().id() + " updated successfully to: " + updatedItem.get().value(), HttpStatus.OK); // 200 OK if updated
            }
            return new ResponseEntity<>("Item with ID: " + id + " not found for update.", HttpStatus.NOT_FOUND); // 404 Not Found if not found
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }

    // --- DELETE (HTTP DELETE) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        boolean deleted = itemService.deleteItem(id);
        if (deleted) {
            return new ResponseEntity<>("Item with ID: " + id + " deleted successfully.", HttpStatus.NO_CONTENT); // 204 No Content for successful deletion
        }
        return new ResponseEntity<>("Item with ID: " + id + " not found for deletion.", HttpStatus.NOT_FOUND); // 404 Not Found if item didn't exist
    }
}
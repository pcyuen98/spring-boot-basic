package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Item;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service // Mark this as a Spring service component

//TODO: Day2:Practical 2 (Group)
// Search restful services

//Discuss in a group 
// How to modify the existing Spring Boot to search input words
// 
// Hint:Use the API below with passing parameter of "demo" and return objects with "demo" only
// Discuss use DO POST or DO GET

//Create a new service call ItemServiceAnalysis
//Complete the method getAllItemsWithDemo()
//return only the value with "demo" wording

//Create a Unit Testing to test the method above


//Create a new RestController with class name of CRUDController_Day1Practical.java
//Add DOGET, DOPOST, DO DELETE and DOPUT function as below 
//Ensure it's working with Postman
//Upload your code your Git Hub


public class ItemService {

	private final Map<Long, String> dataStore = new ConcurrentHashMap<>();
	private final AtomicLong idCounter = new AtomicLong();

	public Item createItem(String value) {
		// Validation is now handled by ItemValidation in the controller,
		// but we might add more complex business rules here if needed.
		long newId = idCounter.incrementAndGet();
		dataStore.put(newId, value);
		return new Item(newId, value);
	}

	public Optional<Item> createItemWithProvidedId(Long id, String value) {
		// Business rule: if ID already exists, we consider it a conflict for creation.
		if (dataStore.containsKey(id)) {
			return Optional.empty(); // Indicate that creation failed due to conflict
		}
		dataStore.put(id, value);
		return Optional.of(new Item(id, value));
	}

	public Optional<Item> getItemById(Long id) {
		return Optional.ofNullable(dataStore.get(id)).map(value -> new Item(id, value));
	}

	public List<Item> getAllItems() {
		return dataStore.entrySet().stream().map(entry -> new Item(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}

	public Optional<Item> updateItem(Long id, String newValue) {
		// Validation for newValue would be in controller/validation class.
		if (dataStore.containsKey(id)) {
			dataStore.put(id, newValue);
			return Optional.of(new Item(id, newValue));
		}
		return Optional.empty(); // Item not found for update
	}

	public boolean deleteItem(Long id) {
		return dataStore.remove(id) != null;
	}
}
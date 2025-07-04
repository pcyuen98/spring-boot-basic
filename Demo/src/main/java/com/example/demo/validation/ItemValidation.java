package com.example.demo.validation;

import com.example.demo.model.ItemRequest;

public class ItemValidation {

	/**
	 * Validates a general item name string.
	 * 
	 * @param itemName The string to validate.
	 * @throws IllegalArgumentException if the item name is null or blank.
	 */
	public static void validateItemName(String itemName) {
		if (itemName == null || itemName.isBlank()) {
			throw new IllegalArgumentException("Item name cannot be empty or blank.");
		}
	}

	/**
	 * Validates an ItemRequest object for object creation.
	 * 
	 * @param itemRequest The ItemRequest to validate.
	 * @throws IllegalArgumentException if the ItemRequest or its id/value
	 *                                  components are invalid.
	 */
	public static void validateItemRequest(ItemRequest itemRequest) {
		if (itemRequest == null) {
			throw new IllegalArgumentException("Request body cannot be null.");
		}
		if (itemRequest.id() == null || itemRequest.id().isBlank()) {
			throw new IllegalArgumentException("Item ID in request cannot be empty or blank.");
		}
		if (itemRequest.value() == null || itemRequest.value().isBlank()) {
			throw new IllegalArgumentException("Item value in request cannot be empty or blank.");
		}
	}

	/**
	 * Validates if a string ID can be converted to a Long.
	 * 
	 * @param idString The string ID to validate.
	 * @return The Long representation of the ID.
	 * @throws IllegalArgumentException if the ID string is not a valid number.
	 */
	public static Long parseAndValidateLongId(String idString) {
		try {
			return Long.valueOf(idString);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid ID format. ID must be a valid number.");
		}
	}
}
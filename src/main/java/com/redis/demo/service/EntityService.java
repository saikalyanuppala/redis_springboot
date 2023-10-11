package com.redis.demo.service;

import org.springframework.stereotype.Service;

@Service
public class EntityService {

	public boolean updateEntity(Long entityId) {
		// Simulate updating the entity by printing a message
		System.out.println("Updating entity with ID: " + entityId);
		return true; // Assume update is successful
	}
}

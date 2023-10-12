package com.redis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.demo.service.DistributedLockService;

@RestController
public class DistributedLockController {

	@Autowired
	private DistributedLockService lockService;

	@PutMapping("/update/{entityId}")
	public String updateEntity(@PathVariable Long entityId) {
		try {
			if (lockService.acquireLock(entityId)) {
				try {
					// Perform the update operation on the entity
					// Simulating update with a sleep for 5 seconds
					System.out.println("Entity with ID " + entityId + " is being updated...");
					Thread.sleep(5000);
					System.out.println("Entity with ID " + entityId + " has been updated successfully.");
					return "Entity updated successfully.";
				} finally {
					lockService.releaseLock(entityId);
				}
			} else {
				return "Unable to acquire lock for entity with ID " + entityId
						+ ". Another user is updating the entity. Please try again later.";
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return "Error during entity update.";
		}
	}
}

package com.redis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.demo.service.EntityService;
import com.redis.demo.service.TransactionService;

@RestController
public class EntityController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private EntityService entityService;

	@PutMapping("/updateEntity/{entityId}")
	public String updateEntity(@PathVariable Long entityId) throws InterruptedException {
		boolean lockAcquired = transactionService.acquireTransactionLock(entityId);
		if (lockAcquired) {
			try {
				Thread.sleep(5000);
				// Perform the update operation on the entity
				boolean updateSuccessful = entityService.updateEntity(entityId);

				// Adjust the lock timeout dynamically based on the update duration
				transactionService.releaseTransactionLock(entityId);

				if (updateSuccessful) {
					return "Entity updated successfully.";
				} else {
					return "Unable to update the entity.";
				}
			} finally {
				transactionService.releaseTransactionLock(entityId);
			}
		} else {
			return "Unable to acquire lock. Another user is updating the entity with id " + entityId
					+ ", Please try again later.";
		}
	}
}

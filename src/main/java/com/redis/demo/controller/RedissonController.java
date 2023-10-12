package com.redis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.demo.service.EntityService;
import com.redis.demo.service.RedissonClientService;

@RestController
public class RedissonController {

	@Autowired
	private RedissonClientService redissonClientService;

	@Autowired
	private EntityService entityService;

	@PutMapping("/updateEntity/{entityId}")
	public String updateEntity(@PathVariable Long entityId) throws InterruptedException {
		boolean lockAcquired = redissonClientService.acquireLock(entityId);
		if (lockAcquired) {
			try {
				Thread.sleep(5000);
				// Perform the update operation on the entity
				boolean updateSuccessful = entityService.updateEntity(entityId);

				// Adjust the lock timeout dynamically based on the update duration
				redissonClientService.releaseLock(entityId);

				if (updateSuccessful) {
					return "Entity updated successfully.";
				} else {
					return "Unable to update the entity.";
				}
			} finally {
				redissonClientService.releaseLock(entityId);
			}
		} else {
			return "Unable to acquire lock. Another user is updating the entity with id " + entityId
					+ ", Please try again later.";
		}
	}
}

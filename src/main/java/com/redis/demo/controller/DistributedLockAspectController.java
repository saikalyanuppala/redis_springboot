package com.redis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.demo.exception.LockException;
import com.redis.demo.service.EntityService;

@RestController
public class DistributedLockAspectController {

	@Autowired
	private EntityService entityService;

	@PutMapping("/update1/{entityId}")
	public String updateEntity(@PathVariable Long entityId) throws InterruptedException {
		try {
			entityService.updateEntity(entityId);
			return "Entity updated successfully. Aspect";
		} catch (LockException e) {
			return e.getMessage();
		}
	}
}

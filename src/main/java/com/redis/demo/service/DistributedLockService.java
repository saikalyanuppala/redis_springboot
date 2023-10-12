package com.redis.demo.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class DistributedLockService {

	private static final String LOCK_KEY_PREFIX = "entityLock:";

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public boolean acquireLock(Long entityId) {
		String lockKey = LOCK_KEY_PREFIX + entityId;
		Boolean lockAcquired = redisTemplate.opsForValue().setIfAbsent(lockKey, 1L, 10, TimeUnit.SECONDS);
		return lockAcquired != null && lockAcquired;
	}

	public void releaseLock(Long entityId) {
		String lockKey = LOCK_KEY_PREFIX + entityId;
		redisTemplate.delete(lockKey);
	}
}

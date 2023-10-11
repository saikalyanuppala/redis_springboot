package com.redis.demo.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

	@Autowired
	private RedissonClient redissonClient;

	private static final String TRANSACTION_LOCK_KEY_PREFIX = "transactionLock:";

	public boolean acquireTransactionLock(Long entityId) {
		String lockKey = TRANSACTION_LOCK_KEY_PREFIX + entityId;
		RLock lock = redissonClient.getLock(lockKey);
		try {
			// Acquire the lock with a wait time of 0 and a dynamic lease time
			return lock.tryLock(0, getDynamicLeaseTime(entityId), TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return false;
		}
	}

	public void releaseTransactionLock(Long entityId) {
		String lockKey = TRANSACTION_LOCK_KEY_PREFIX + entityId;
		RLock lock = redissonClient.getLock(lockKey);
		if (lock.isHeldByCurrentThread()) {
			lock.unlock();
		}
	}

	private long getDynamicLeaseTime(Long entityId) {
		// Implement your logic to calculate dynamic lease time based on entityId
		// For now, let's use a fixed value of 10 seconds as an example
		return 10000L; // 10 seconds in milliseconds
	}
}

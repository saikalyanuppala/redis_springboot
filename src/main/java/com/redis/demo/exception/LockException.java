package com.redis.demo.exception;

public class LockException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 167349143226200409L;

	public LockException(String message) {
		super(message);
	}
}
package com.redis.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.redis.demo.model.User;

@Repository
public class UserDAO {

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	private static final String KEY = "USER";

	@SuppressWarnings("unchecked")
	public boolean saveUser(User user) {
		try {
			redisTemplate.opsForHash().put(KEY, user.getId().toString(), user);
			System.out.println("***** "+user.getId().toString());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> fetchAllUser() {
		List<User> users;
		users = redisTemplate.opsForHash().values(KEY);
		return users;
	}

	@SuppressWarnings("unchecked")
	public User fetchUserById(Long id) {
		User user;
		user = (User) redisTemplate.opsForHash().get(KEY, id.toString());
		return user;
	}

	@SuppressWarnings("unchecked")
	public boolean deleteUser(Long id) {
		try {
			redisTemplate.opsForHash().delete(KEY, id.toString());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean updateUser(Long id, User user) {
		try {
			redisTemplate.opsForHash().put(KEY, id, user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

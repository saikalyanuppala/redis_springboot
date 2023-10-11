package com.redis.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redis.demo.model.User;
import com.redis.demo.repository.UserDAO;

@Service
public class UserService {

	@Autowired
	private UserDAO userDao;

	public boolean saveUser(User user) {
		return userDao.saveUser(user);
	}

	public List<User> fetchAllUser() {
		return userDao.fetchAllUser();
	}

	public User fetchUserById(Long id) {
		return userDao.fetchUserById(id);
	}

	public boolean deleteUser(Long id) {
		return userDao.deleteUser(id);
	}

	public boolean updateUser(Long id, User user) {
		return userDao.updateUser(id, user);
	}

}

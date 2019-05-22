package com.zhangt.service;

import com.zhangt.dao.UserDao;
import com.zhangt.entity.User;

public class UserService {

	private UserDao userDao = new UserDao();
	
	public User login(User user) {
		return userDao.login(user);
	}
	
	// Ä£Äâ×¢²á
	public void register(User user) {
		userDao.register(user);
	}
}

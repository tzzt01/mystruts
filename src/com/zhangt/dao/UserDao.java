package com.zhangt.dao;

import com.zhangt.entity.User;

public class UserDao {

	public User login(User user) {
		if("123123".equals(user.getPassword()) && "admin".equals(user.getName())) {
			return user;
		}
		return null;
	}
	
	public void register(User user) {
		System.out.println("注册成功：用户，" + user.getName());
	}
}

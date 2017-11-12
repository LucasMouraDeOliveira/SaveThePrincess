package com.lordkadoc.dao.users;

import com.lordkadoc.entities.User;

public interface UserDAO {
	
	public User findByName(String name);

	public void insert(User user);

}

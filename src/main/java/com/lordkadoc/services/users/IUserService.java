package com.lordkadoc.services.users;

import com.lordkadoc.entities.User;

public interface IUserService {
	
	public User findUserByLogin(String login);
	
	public void insertUser(User user);

}

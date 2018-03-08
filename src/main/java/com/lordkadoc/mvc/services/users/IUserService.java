package com.lordkadoc.mvc.services.users;

import com.lordkadoc.mvc.entities.User;

public interface IUserService {
	
	public User findUserByLogin(String login);
	
	public void saveUser(User user);

}

package com.lordkadoc.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lordkadoc.dao.users.UserDAO;
import com.lordkadoc.entities.User;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public User findUserByLogin(String login) {
		return this.userDAO.findByName(login);
	}

	@Override
	public void insertUser(User user) {
		this.userDAO.save(user);
	}

	public User findUser(Integer playerId) {
		return this.userDAO.find(playerId);
	}

}

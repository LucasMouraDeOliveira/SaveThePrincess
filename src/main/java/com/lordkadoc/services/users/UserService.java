package com.lordkadoc.services.users;

import java.util.Arrays;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lordkadoc.dao.role.RoleDAO;
import com.lordkadoc.dao.users.UserDAO;
import com.lordkadoc.entities.Role;
import com.lordkadoc.entities.User;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByLogin(String login) {
		return this.userDAO.findByLogin(login);
	}

	@Transactional
	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Role userRole = roleDAO.findRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userDAO.save(user);
	}

}

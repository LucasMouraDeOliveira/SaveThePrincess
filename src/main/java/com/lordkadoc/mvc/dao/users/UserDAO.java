package com.lordkadoc.mvc.dao.users;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lordkadoc.mvc.dao.DAOUtil;
import com.lordkadoc.mvc.entities.User;

@Repository
public class UserDAO extends DAOUtil<User> {
	
	public UserDAO() {
		super(User.class);
	}
	
	public User findByLogin(String login) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("login", login));
		return this.findEntityByCriterions(criterions);
	}

}

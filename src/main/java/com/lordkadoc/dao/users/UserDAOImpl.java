package com.lordkadoc.dao.users;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lordkadoc.dao.DAOUtil;
import com.lordkadoc.entities.User;

@Repository
public class UserDAOImpl extends DAOUtil<User> implements UserDAO {
	

	@Override
	public User findByName(String login) {
		Session session = this.sessionFactory.openSession();
		Criteria criteria = session.createCriteria(User.class);
		User user = (User) criteria.add(Restrictions.eq("login", login)).uniqueResult();
		session.close();
		return user;
	}

}

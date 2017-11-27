package com.lordkadoc.dao.users;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lordkadoc.dao.DAOUtil;
import com.lordkadoc.entities.User;

@Repository
@Transactional
public class UserDAO extends DAOUtil<User> {
	
	public UserDAO() {
		super(User.class);
	}
	
	public User findByName(String login) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(User.class);
		User user = (User) criteria.add(Restrictions.eq("login", login)).uniqueResult();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
			tx.commit();
		}
		session.close();
		return user;
	}

}

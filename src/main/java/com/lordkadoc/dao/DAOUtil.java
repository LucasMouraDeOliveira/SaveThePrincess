package com.lordkadoc.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DAOUtil<T> {
	
	@Autowired
	protected SessionFactory sessionFactory;

	public void insert(T object) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(object);
		session.getTransaction().commit();
	}
	
}

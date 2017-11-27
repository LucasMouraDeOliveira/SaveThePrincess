package com.lordkadoc.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class DAOUtil<T> implements IDAOUtil<T>{
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	public Class<T> entityType;
	
	public DAOUtil(Class<T> entityType) {
		this.entityType = entityType;
	}
	
	public T find(Serializable id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		T entity =  session.get(entityType, id);
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
			tx.commit();
		}
		session.close();
		return entity;
	}
	
	public void save(T object) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(object);
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
			tx.commit();
		}
		session.close();
	}
	
	public void delete(T object) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(object);
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
			tx.commit();
		}
		session.close();
	}
	
}

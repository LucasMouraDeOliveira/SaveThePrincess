package com.lordkadoc.mvc.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordkadoc.mvc.entities.User;

public abstract class DAOUtil<T> implements IDAOUtil<T>{
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	public Class<T> entityType;
	
	public DAOUtil(Class<T> entityType) {
		this.entityType = entityType;
	}
	
	public T find(Serializable id) {
		return getCurrentSession().get(entityType, id);
	}
	
	public void save(T object) {
		getCurrentSession().saveOrUpdate(object);
	}
	
	public void delete(T object) {
		getCurrentSession().delete(object);
	}
	
	@SuppressWarnings("unchecked")
	public T findEntityByCriterions(List<Criterion> criterions) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(entityType);
		for(Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		return (T)criteria.uniqueResult();
	}
	
	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
}

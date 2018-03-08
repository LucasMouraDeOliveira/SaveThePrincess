package com.lordkadoc.mvc.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

public interface IDAOUtil<T> {
	
	public T find(Serializable id);
	
	public void save(T entity);
	
	public void delete(T entity);
	
	public T findEntityByCriterions(List<Criterion> criterions);

}

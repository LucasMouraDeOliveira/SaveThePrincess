package com.lordkadoc.dao;

import java.io.Serializable;

public interface IDAOUtil<T> {
	
	public T find(Serializable id);
	
	public void save(T entity);
	
	public void delete(T entity);

}

package com.lordkadoc.dao.role;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lordkadoc.dao.DAOUtil;
import com.lordkadoc.entities.Role;
import com.lordkadoc.entities.User;

@Repository
@Transactional
public class RoleDAO extends DAOUtil<Role> {
	
	public RoleDAO() {
		super(Role.class);
	}

	public Role findRole(String roleName) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Role.class);
		Role role = (Role) criteria.add(Restrictions.eq("role", roleName)).uniqueResult();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
			tx.commit();
		}
		session.close();
		return role;
	}

}

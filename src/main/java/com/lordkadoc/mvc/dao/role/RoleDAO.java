package com.lordkadoc.mvc.dao.role;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lordkadoc.mvc.dao.DAOUtil;
import com.lordkadoc.mvc.entities.Role;

@Repository
@Transactional
public class RoleDAO extends DAOUtil<Role> {
	
	public RoleDAO() {
		super(Role.class);
	}

	public Role findRole(String roleName) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("role", roleName));
		return this.findEntityByCriterions(criterions);
	}

}

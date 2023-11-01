package com.brijframwork.authorization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframwork.authorization.model.EOUserRole;

@Repository
@Transactional
public interface UserRoleRepository  extends JpaRepository<EOUserRole, Long>{

	EOUserRole findByRoleName(String role);
	
	EOUserRole findByPosition(int position);

	List<EOUserRole> findAllByRoleType(String type);

}

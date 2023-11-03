package com.brijframwork.authorization.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframwork.authorization.model.EOUserRoleEndpoint;

@Repository
@Transactional
public interface UserRoleEndpointRepository  extends JpaRepository<EOUserRoleEndpoint, Long>{

	@Query(nativeQuery = true,  value="select * from USER_ROLE_ENDPOINT URE where URE.ROLE_ID = :roleId and URE.ENDPOINT_ID=:userEndpointId ")
	Optional<EOUserRoleEndpoint> findByRoleIdAndEndpointId(@Param("roleId")Long roleId, @Param("userEndpointId") Long userEndpointId);
}

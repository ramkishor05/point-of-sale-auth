package com.brijframwork.authorization.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframwork.authorization.model.EOUserAccount;

@Repository
@Transactional
public interface UserAccountRepository  extends JpaRepository<EOUserAccount, Long>{

	@Query(nativeQuery = true,  value="select * from USER_ACCOUNT UA where UA.USERNAME = :username")
	Optional<EOUserAccount> findUserName(@Param("username")String username);

}

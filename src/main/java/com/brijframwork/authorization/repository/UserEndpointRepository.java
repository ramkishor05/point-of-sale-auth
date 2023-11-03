package com.brijframwork.authorization.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframwork.authorization.model.EOUserEndpoint;
import com.brijframwork.authorization.model.EOUserRole;

@Repository
@Transactional
public interface UserEndpointRepository  extends JpaRepository<EOUserEndpoint, Long>{

	@Query(nativeQuery = true,  value="select * from USER_ENDPOINT UE where UE.TITLE = :title")
	Optional<EOUserEndpoint> findByTitle(@Param("title")String title);
	
	@Query(nativeQuery = true,  value="select * from USER_ENDPOINT UE where UE.URL = :url")
	Optional<EOUserEndpoint> findByUrl(@Param("url")String url);

	@Query(nativeQuery = true,  value="select * from USER_ENDPOINT UE where UE.TYPE = :type")
	List<EOUserEndpoint> findAllByType(@Param("type")String type);

	@Query(nativeQuery = true,  value="select * from USER_ENDPOINT UE where UE.URL in (:urls)")
	List<EOUserEndpoint> findByUrls(@Param("urls")List<String> urls);


}

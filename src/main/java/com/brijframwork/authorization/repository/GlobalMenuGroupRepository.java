package com.brijframwork.authorization.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframwork.authorization.model.EOGlobalMenuGroup;

@Repository
@Transactional
public interface GlobalMenuGroupRepository  extends JpaRepository<EOGlobalMenuGroup, Long>{

	@Query(nativeQuery = true,  value="select * from GLOBAL_MENU_GROUPGLOBAL_MENU_GROUP UE where UE.TITLE = :title")
	Optional<EOGlobalMenuGroup> findByTitle(@Param("title")String title);
	
	@Query(nativeQuery = true,  value="select * from GLOBAL_MENU_GROUP UE where UE.URL = :url")
	Optional<EOGlobalMenuGroup> findByUrl(@Param("url")String url);

	@Query(nativeQuery = true,  value="select * from GLOBAL_MENU_GROUP UE where UE.TYPE = :type")
	List<EOGlobalMenuGroup> findAllByType(@Param("type")String type);

	@Query(nativeQuery = true,  value="select * from GLOBAL_MENU_GROUP UE where UE.URL in (:urls)")
	List<EOGlobalMenuGroup> findByUrls(@Param("urls")List<String> urls);


}

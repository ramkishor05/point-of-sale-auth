package com.brijframwork.authorization.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframwork.authorization.model.EOGlobalMenuItem;

@Repository
@Transactional
public interface GlobalMenuItemRepository  extends JpaRepository<EOGlobalMenuItem, Long>{

	@Query(nativeQuery = true,  value="select * from GLOBAL_MENU_ITEM UE where UE.TITLE = :title")
	Optional<EOGlobalMenuItem> findByTitle(@Param("title")String title);
	
	@Query(nativeQuery = true,  value="select * from GLOBAL_MENU_ITEM UE where UE.URL = :url")
	Optional<EOGlobalMenuItem> findByUrl(@Param("url")String url);

	@Query(nativeQuery = true,  value="select * from GLOBAL_MENU_ITEM UE where UE.TYPE = :type")
	List<EOGlobalMenuItem> findAllByType(@Param("type")String type);

	@Query(nativeQuery = true,  value="select * from GLOBAL_MENU_ITEM UE where UE.URL in (:urls)")
	List<EOGlobalMenuItem> findByUrls(@Param("urls")List<String> urls);


}

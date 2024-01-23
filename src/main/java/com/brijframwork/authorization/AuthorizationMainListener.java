package com.brijframwork.authorization;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.brijframework.production.schema.factories.JsonSchemaDataFactory;
import com.brijframwork.authorization.constant.UserRole;
import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.model.EOUserProfile;
import com.brijframwork.authorization.model.EOUserRole;
import com.brijframwork.authorization.model.headers.EOHeaderItem;
import com.brijframwork.authorization.model.headers.EORoleHeaderItem;
import com.brijframwork.authorization.model.menus.EOMenuGroup;
import com.brijframwork.authorization.model.menus.EOMenuItem;
import com.brijframwork.authorization.model.menus.EORoleMenuGroup;
import com.brijframwork.authorization.model.menus.EORoleMenuItem;
import com.brijframwork.authorization.repository.HeaderItemRepository;
import com.brijframwork.authorization.repository.MenuGroupRepository;
import com.brijframwork.authorization.repository.MenuItemRepository;
import com.brijframwork.authorization.repository.RoleHeaderItemRepository;
import com.brijframwork.authorization.repository.RoleMenuGroupRepository;
import com.brijframwork.authorization.repository.RoleMenuItemRepository;
import com.brijframwork.authorization.repository.UserAccountRepository;
import com.brijframwork.authorization.repository.UserProfileRepository;
import com.brijframwork.authorization.repository.UserRoleRepository;

@Component
public class AuthorizationMainListener implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private MenuItemRepository globalMenuItemRepository;
	
	@Autowired
	private MenuGroupRepository globalMenuGroupRepository;
	
	@Autowired
	private RoleMenuGroupRepository roleMenuGroupRepository;
	
	@Autowired
	private RoleMenuItemRepository roleMenuItemRepository;
	
	@Autowired
	private HeaderItemRepository headerItemRepository;
	
	@Autowired
	private RoleHeaderItemRepository roleHeaderItemRepository;
	
	@Value("${spring.db.datajson.upload}")
	boolean upload;
	
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
    	Map<Integer, EOUserRole> userRoleMap = userRoleRepository.findAll().parallelStream().collect(Collectors.toMap(EOUserRole::getPosition, Function.identity()));
    	Map<String, EOUserAccount> userAccountMap = userAccountRepository.findAll().parallelStream().collect(Collectors.toMap(EOUserAccount::getUsername, Function.identity()));
    	if(upload) {
	    	JsonSchemaDataFactory instance = JsonSchemaDataFactory.getInstance();
	    	List<EOUserRole> userRoleList = instance.getAll(EOUserRole.class);
	    	for (EOUserRole userRole : userRoleList) {
	    		EOUserRole eoUserRole = userRoleMap.getOrDefault(userRole.getPosition(),userRole);
	    		BeanUtils.copyProperties(userRole, eoUserRole, "id");
	    		EOUserRole saveUserRole=userRoleRepository.saveAndFlush(eoUserRole);
	    		userRole.setId(saveUserRole.getId());
	    		userRoleMap.put(userRole.getPosition(), userRole);
	    		if(UserRole.ADMIN.getRoleType().equalsIgnoreCase(userRole.getRoleType())) {
	    			EOUserAccount eoUserAccount = userAccountMap.getOrDefault(eoUserRole.getRoleName(), new EOUserAccount());
    	    		eoUserAccount.setAccountName(eoUserRole.getRoleName());
    	    		eoUserAccount.setUsername(eoUserRole.getRoleName());
    	    		eoUserAccount.setType(eoUserRole.getRoleName());
    	    		eoUserAccount.setPassword((eoUserAccount.getPassword()==null? eoUserRole.getRoleName() : eoUserAccount.getPassword()));
    	    		eoUserAccount.setUserRole(eoUserRole);
    	    		eoUserAccount=userAccountRepository.saveAndFlush(eoUserAccount);
    	    		if(eoUserAccount.getUserProfile()==null) {
	    	    		EOUserProfile eoUserProfile=   new EOUserProfile();
	    	    		eoUserProfile.setFullName(eoUserRole.getRoleName());
	    	    		eoUserProfile.setUserAccount(eoUserAccount);
	    	    		userProfileRepository.saveAndFlush(eoUserProfile);
    	    		}
	    		}
	    	}
	    	List<EOMenuGroup> globalMenuGroupList = instance.getAll(EOMenuGroup.class);
	    	Map<String, EOMenuGroup> globalMenuGroupMap = globalMenuGroupRepository.findAll()
	    			.stream().collect(Collectors.toMap(EOMenuGroup::getUrl, Function.identity()));
	    	for (EOMenuGroup globalMenuGroup : globalMenuGroupList) {
	    		EOMenuGroup eoUserEndpoint = globalMenuGroupMap.getOrDefault(globalMenuGroup.getUrl(),globalMenuGroup);
	    		BeanUtils.copyProperties(globalMenuGroup, eoUserEndpoint, "id");
	    		EOMenuGroup saveGlobalMenuGroup = globalMenuGroupRepository.save(eoUserEndpoint);
	    		globalMenuGroup.setId(saveGlobalMenuGroup.getId());
	    		//globalMenuGroupMap.remove(globalMenuGroup.getUrl());
			}
	    	List<EOMenuItem> globalMenuItemList = instance.getAll(EOMenuItem.class);
	    	Map<String, EOMenuItem> globalMenuItemMap = globalMenuItemRepository.findAll()
	    			.stream().collect(Collectors.toMap(EOMenuItem::getUrl, Function.identity()));
	    	for (EOMenuItem globalMenuItem : globalMenuItemList) {
	    		EOMenuItem eoGlobalMenuItem = globalMenuItemMap.getOrDefault(globalMenuItem.getUrl(),globalMenuItem);
	    		BeanUtils.copyProperties(globalMenuItem, eoGlobalMenuItem, "id");
	    		EOMenuItem saveGlobalMenuItem = globalMenuItemRepository.save(eoGlobalMenuItem);
	    		globalMenuItem.setId(saveGlobalMenuItem.getId());
	    		//globalMenuItemMap.remove(globalMenuItem.getUrl());
			}
	    	Map<String, EORoleMenuGroup> roleMenuGroupMap = roleMenuGroupRepository.findAll().parallelStream().collect(Collectors.toMap((userRoleMenuGroup)->getRoleMenuGroupKey(userRoleMenuGroup), Function.identity()));
	    	List<EORoleMenuGroup> roleMenuGroups = instance.getAll(EORoleMenuGroup.class);
	    	for(EORoleMenuGroup roleMenuGroup: roleMenuGroups) {
	    		try {
	    			String roleMenuGroupKey= getRoleMenuGroupKey(roleMenuGroup);
					EORoleMenuGroup eoRoleMenuGroup = roleMenuGroupMap.getOrDefault(roleMenuGroupKey,roleMenuGroup);
					BeanUtils.copyProperties(roleMenuGroup, eoRoleMenuGroup, "id");
		    		EORoleMenuGroup saveRoleMenuGroup = roleMenuGroupRepository.save(eoRoleMenuGroup);
		    		roleMenuGroup.setId(saveRoleMenuGroup.getId());
		    		eoRoleMenuGroup.setId(saveRoleMenuGroup.getId());
		    		//roleMenuGroupMap.remove(roleMenuGroupKey);
	    		}catch (Exception e) {
					System.out.println("roleMenuGroup="+roleMenuGroup);
					e.printStackTrace();
				}
			}
	    	Map<String, EORoleMenuItem> roleMenuItemMap = roleMenuItemRepository.findAll().parallelStream().collect(Collectors.toMap((userRoleMenuItem)->getRoleMenuItemKey(userRoleMenuItem), Function.identity()));
	    	List<EORoleMenuItem> roleEndpointList = instance.getAll(EORoleMenuItem.class);
	    	for(EORoleMenuItem roleMenuItem: roleEndpointList) {
	    		try {
	    			String roleMenuItemKey= getRoleMenuItemKey(roleMenuItem);
					EORoleMenuItem eoRoleEndpoint = roleMenuItemMap.getOrDefault(roleMenuItemKey,roleMenuItem);
					BeanUtils.copyProperties(roleMenuItem, eoRoleEndpoint, "id");
		    		EORoleMenuItem saveRoleEndpoint = roleMenuItemRepository.save(eoRoleEndpoint);
		    		roleMenuItem.setId(saveRoleEndpoint.getId());
		    		//roleMenuGroupMap.remove(roleMenuItemKey);
	    		}catch (Exception e) {
					System.out.println("roleEndpoint="+roleMenuItem);
					e.printStackTrace();
				}
			}
	    	
	    	Map<String, EOHeaderItem> headerItemMap = headerItemRepository.findAll().parallelStream().collect(Collectors.toMap((headerItem)->headerItem.getIdenNo(), Function.identity()));
	    	List<EOHeaderItem> headerItemList = instance.getAll(EOHeaderItem.class);
	    	for(EOHeaderItem headerItem: headerItemList) {
	    		try {
	    			EOHeaderItem eoHeaderItem = headerItemMap.getOrDefault(headerItem.getIdenNo(),headerItem);
					BeanUtils.copyProperties(headerItem, eoHeaderItem, "id");
					EOHeaderItem saveHeaderItem = headerItemRepository.save(eoHeaderItem);
		    		headerItem.setId(saveHeaderItem.getId());
		    		//headerItemMap.remove(headerItem.getIdenNo());
	    		}catch (Exception e) {
					System.out.println("headerItem="+headerItem);
					e.printStackTrace();
				}
			}
	    	
	    	Map<String, EORoleHeaderItem> roleHeaderItemMap = roleHeaderItemRepository.findAll().parallelStream().collect(Collectors.toMap((userRoleHeaderItem)->getRoleHeaderItemKey(userRoleHeaderItem), Function.identity()));
	    	List<EORoleHeaderItem> userRoleHeaderItemList = instance.getAll(EORoleHeaderItem.class);
	    	for(EORoleHeaderItem roleHeaderItem: userRoleHeaderItemList) {
	    		try {
	    			String roleHeaderItemKey=getRoleHeaderItemKey(roleHeaderItem);
	    			EORoleHeaderItem eoRoleHeaderItem = roleHeaderItemMap.getOrDefault(roleHeaderItemKey,roleHeaderItem);
					BeanUtils.copyProperties(roleHeaderItem, eoRoleHeaderItem, "id");
					EORoleHeaderItem saveRoleHeaderItem = roleHeaderItemRepository.save(eoRoleHeaderItem);
		    		roleHeaderItem.setId(saveRoleHeaderItem.getId());
		    		//roleHeaderItemMap.remove(roleHeaderItemKey);
	    		}catch (Exception e) {
					System.out.println("roleHeaderItem="+roleHeaderItem);
					e.printStackTrace();
				}
			}/*
	    	if(!roleHeaderItemMap.isEmpty())
	    	roleHeaderItemRepository.deleteAll(roleHeaderItemMap.values());
	    	if(!headerItemMap.isEmpty())
	    	headerItemRepository.deleteAll(headerItemMap.values());
	    	if(!roleMenuItemMap.isEmpty())
	    	roleMenuItemRepository.deleteAll(roleMenuItemMap.values());
	    	if(!roleMenuGroupMap.isEmpty())
	    	roleMenuGroupRepository.deleteAll(roleMenuGroupMap.values());
	    	if(!globalMenuItemMap.isEmpty())
	    	globalMenuItemRepository.deleteAll(globalMenuItemMap.values());
	    	if(!globalMenuGroupMap.isEmpty())
	    	globalMenuGroupRepository.deleteAll(globalMenuGroupMap.values());*/
    	}
    }

	private String getRoleMenuItemKey(EORoleMenuItem userRoleMenuItem) {
		if(userRoleMenuItem.getMenuItem()==null || userRoleMenuItem.getUserRole()==null) {
			return "";
		}
		return userRoleMenuItem.getUserRole().getId()+"_"+ userRoleMenuItem.getMenuItem().getId();
	}

	private String getRoleMenuGroupKey(EORoleMenuGroup userRoleMenuGroup) {
		if(userRoleMenuGroup.getMenuGroup()==null || userRoleMenuGroup.getUserRole()==null) {
			
			return "";
		}
		return userRoleMenuGroup.getUserRole().getId()+"_"+ userRoleMenuGroup.getMenuGroup().getId();
	}
    
    public static String getRoleHeaderItemKey(EORoleHeaderItem eoRoleHeaderItem) {
    	if(eoRoleHeaderItem.getHeaderItem()==null || eoRoleHeaderItem.getUserRole()==null) {
			return "";
		}
    	return eoRoleHeaderItem.getUserRole().getId()+"_"+eoRoleHeaderItem.getHeaderItem().getId();
    }
}
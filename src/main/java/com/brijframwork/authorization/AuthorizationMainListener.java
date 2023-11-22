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
import com.brijframwork.authorization.model.EOGlobalMenuGroup;
import com.brijframwork.authorization.model.EOGlobalMenuItem;
import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.model.EOUserProfile;
import com.brijframwork.authorization.model.EOUserRole;
import com.brijframwork.authorization.model.EOUserRoleMenuGroup;
import com.brijframwork.authorization.model.EOUserRoleMenuItem;
import com.brijframwork.authorization.repository.GlobalMenuGroupRepository;
import com.brijframwork.authorization.repository.GlobalMenuItemRepository;
import com.brijframwork.authorization.repository.UserAccountRepository;
import com.brijframwork.authorization.repository.UserProfileRepository;
import com.brijframwork.authorization.repository.UserRoleMenuGroupRepository;
import com.brijframwork.authorization.repository.UserRoleMenuItemRepository;
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
	private GlobalMenuItemRepository globalMenuItemRepository;
	
	@Autowired
	private GlobalMenuGroupRepository globalMenuGroupRepository;
	
	@Autowired
	private UserRoleMenuGroupRepository userRoleMenuGroupRepository;
	
	@Autowired
	private UserRoleMenuItemRepository userRoleMenuItemRepository;
	
	@Value("${spring.db.datajson.upload}")
	boolean upload;
	
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
    	Map<Integer, EOUserRole> userRoleMap = userRoleRepository.findAll().parallelStream().collect(Collectors.toMap(EOUserRole::getPosition, Function.identity()));
    	Map<String, EOUserAccount> userAccountMap = userAccountRepository.findAll().parallelStream().collect(Collectors.toMap(EOUserAccount::getUsername, Function.identity()));
    	for(UserRole userRole : UserRole.values()) {
    		EOUserRole findByPosition = userRoleMap.get(userRole.getPosition());
    		if(findByPosition==null) {
    			EOUserRole eoUserRole = new EOUserRole(userRole.getPosition(),userRole.getRole(),userRole.getRole());
    			eoUserRole.setRoleType(userRole.getType());
    			eoUserRole=userRoleRepository.saveAndFlush(eoUserRole);
    			EOUserAccount findUserAccount = userAccountMap.get(eoUserRole.getRoleName());
    	    	if(findUserAccount==null) {
    	    		EOUserAccount eoUserAccount=new EOUserAccount();
    	    		eoUserAccount.setAccountName(eoUserRole.getRoleName());
    	    		eoUserAccount.setUsername(eoUserRole.getRoleName());
    	    		eoUserAccount.setType(eoUserRole.getRoleName());
    	    		eoUserAccount.setPassword((eoUserRole.getRoleName()));
    	    		eoUserAccount.setUserRole(eoUserRole);
    	    		eoUserAccount=userAccountRepository.saveAndFlush(eoUserAccount);
    	    		EOUserProfile eoUserProfile=new EOUserProfile();
    	    		eoUserProfile.setFullName(eoUserRole.getRoleName());
    	    		eoUserProfile.setUserAccount(eoUserAccount);
    	    		userProfileRepository.saveAndFlush(eoUserProfile);
    	    	}
    		}
    	}
    	if(upload) {
	    	JsonSchemaDataFactory instance = JsonSchemaDataFactory.getInstance();
	    	List<EOUserRole> userRoleList = instance.getAll(EOUserRole.class);
	    	for (EOUserRole userRole : userRoleList) {
	    		EOUserRole eoUserRole = userRoleMap.getOrDefault(userRole.getPosition(),userRole);
	    		BeanUtils.copyProperties(userRole, eoUserRole, "id");
	    		EOUserRole saveUserRole=userRoleRepository.saveAndFlush(eoUserRole);
	    		userRole.setId(saveUserRole.getId());
	    		userRoleMap.put(userRole.getPosition(), userRole);
	    	}
	    	List<EOGlobalMenuGroup> globalMenuGroupList = instance.getAll(EOGlobalMenuGroup.class);
	    	List<String> globalMenuGroupUrls=globalMenuGroupList.stream().map(userEndpoint->userEndpoint.getUrl()).collect(Collectors.toList());
	    	Map<String, EOGlobalMenuGroup> globalMenuGroupMap = globalMenuGroupRepository.findByUrls(globalMenuGroupUrls)
	    			.stream().collect(Collectors.toMap(EOGlobalMenuGroup::getUrl, Function.identity()));
	    	for (EOGlobalMenuGroup globalMenuGroup : globalMenuGroupList) {
	    		EOGlobalMenuGroup eoUserEndpoint = globalMenuGroupMap.getOrDefault(globalMenuGroup.getUrl(),globalMenuGroup);
	    		BeanUtils.copyProperties(globalMenuGroup, eoUserEndpoint, "id");
	    		EOGlobalMenuGroup saveGlobalMenuGroup = globalMenuGroupRepository.save(eoUserEndpoint);
	    		globalMenuGroup.setId(saveGlobalMenuGroup.getId());
	    		globalMenuGroupMap.put(globalMenuGroup.getUrl(), globalMenuGroup);
			}
	    	List<EOGlobalMenuItem> globalMenuItemList = instance.getAll(EOGlobalMenuItem.class);
	    	List<String> globalMenuItemUrls=globalMenuItemList.stream().map(userEndpoint->userEndpoint.getUrl()).collect(Collectors.toList());
	    	Map<String, EOGlobalMenuItem> globalMenuItemMap = globalMenuItemRepository.findByUrls(globalMenuItemUrls)
	    			.stream().collect(Collectors.toMap(EOGlobalMenuItem::getUrl, Function.identity()));
	    	for (EOGlobalMenuItem globalMenuItem : globalMenuItemList) {
	    		EOGlobalMenuItem eoGlobalMenuItem = globalMenuItemMap.getOrDefault(globalMenuItem.getUrl(),globalMenuItem);
	    		BeanUtils.copyProperties(globalMenuItem, eoGlobalMenuItem, "id");
	    		EOGlobalMenuItem saveGlobalMenuItem = globalMenuItemRepository.save(eoGlobalMenuItem);
	    		globalMenuItem.setId(saveGlobalMenuItem.getId());
	    		globalMenuItemMap.put(globalMenuItem.getUrl(), globalMenuItem);
			}
	    	Map<String, EOUserRoleMenuGroup> userRoleMenuGroupMap = userRoleMenuGroupRepository.findAll().parallelStream().collect(Collectors.toMap((userRoleMenuGroup)->groupKey(userRoleMenuGroup), Function.identity()));
	    	List<EOUserRoleMenuGroup> userRoleMenuGroups = instance.getAll(EOUserRoleMenuGroup.class);
	    	for(EOUserRoleMenuGroup userRoleMenuGroup: userRoleMenuGroups) {
	    		try {
					EOUserRoleMenuGroup eoUserRoleMenuGroup = userRoleMenuGroupMap.getOrDefault(groupKey(userRoleMenuGroup),userRoleMenuGroup);
					BeanUtils.copyProperties(userRoleMenuGroup, eoUserRoleMenuGroup, "id");
		    		EOUserRoleMenuGroup saveUserRoleMenuGroup = userRoleMenuGroupRepository.save(eoUserRoleMenuGroup);
		    		userRoleMenuGroup.setId(saveUserRoleMenuGroup.getId());
	    		}catch (Exception e) {
					System.out.println("userRoleMenuGroup="+userRoleMenuGroup);
				}
			}
	    	Map<String, EOUserRoleMenuItem> userRoleMenuItemMap = userRoleMenuItemRepository.findAll().parallelStream().collect(Collectors.toMap((userRoleMenuItem)->itemKey(userRoleMenuItem), Function.identity()));
	    	List<EOUserRoleMenuItem> userRoleEndpointList = instance.getAll(EOUserRoleMenuItem.class);
	    	for(EOUserRoleMenuItem userRoleMenuItem: userRoleEndpointList) {
	    		try {
					EOUserRoleMenuItem eoUserRoleEndpoint = userRoleMenuItemMap.getOrDefault(itemKey(userRoleMenuItem),userRoleMenuItem);
		    		if(eoUserRoleEndpoint!=null)
					BeanUtils.copyProperties(userRoleMenuItem, eoUserRoleEndpoint, "id");
		    		EOUserRoleMenuItem saveUserRoleEndpoint = userRoleMenuItemRepository.save(eoUserRoleEndpoint);
		    		userRoleMenuItem.setId(saveUserRoleEndpoint.getId());
	    		}catch (Exception e) {
					System.out.println("userRoleEndpoint="+userRoleMenuItem);
				}
			}
    	}
    }

	private String itemKey(EOUserRoleMenuItem userRoleMenuItem) {
		return userRoleMenuItem.getUserRole().getId()+"_"+ userRoleMenuItem.getMenuItem().getId();
	}

	private String groupKey(EOUserRoleMenuGroup userRoleMenuGroup) {
		return userRoleMenuGroup.getUserRole().getId()+"_"+ userRoleMenuGroup.getMenuGroup().getId();
	}
    
    public static String getKey(EOUserRoleMenuItem userRoleEndpoint) {
    	return userRoleEndpoint.getUserRole().getId()+"_"+userRoleEndpoint.getMenuItem().getId();
    }
}
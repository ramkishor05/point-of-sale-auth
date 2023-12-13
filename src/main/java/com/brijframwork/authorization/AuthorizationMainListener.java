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
import com.brijframwork.authorization.model.menus.EOMenuGroup;
import com.brijframwork.authorization.model.menus.EOMenuItem;
import com.brijframwork.authorization.model.menus.EORoleMenuGroup;
import com.brijframwork.authorization.model.menus.EORoleMenuItem;
import com.brijframwork.authorization.repository.MenuGroupRepository;
import com.brijframwork.authorization.repository.MenuItemRepository;
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
	private RoleMenuGroupRepository userRoleMenuGroupRepository;
	
	@Autowired
	private RoleMenuItemRepository userRoleMenuItemRepository;
	
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
	    	List<EOMenuGroup> globalMenuGroupList = instance.getAll(EOMenuGroup.class);
	    	List<String> globalMenuGroupUrls=globalMenuGroupList.stream().map(userEndpoint->userEndpoint.getUrl()).collect(Collectors.toList());
	    	Map<String, EOMenuGroup> globalMenuGroupMap = globalMenuGroupRepository.findByUrls(globalMenuGroupUrls)
	    			.stream().collect(Collectors.toMap(EOMenuGroup::getUrl, Function.identity()));
	    	for (EOMenuGroup globalMenuGroup : globalMenuGroupList) {
	    		EOMenuGroup eoUserEndpoint = globalMenuGroupMap.getOrDefault(globalMenuGroup.getUrl(),globalMenuGroup);
	    		BeanUtils.copyProperties(globalMenuGroup, eoUserEndpoint, "id");
	    		EOMenuGroup saveGlobalMenuGroup = globalMenuGroupRepository.save(eoUserEndpoint);
	    		globalMenuGroup.setId(saveGlobalMenuGroup.getId());
	    		globalMenuGroupMap.put(globalMenuGroup.getUrl(), globalMenuGroup);
			}
	    	List<EOMenuItem> globalMenuItemList = instance.getAll(EOMenuItem.class);
	    	List<String> globalMenuItemUrls=globalMenuItemList.stream().map(userEndpoint->userEndpoint.getUrl()).collect(Collectors.toList());
	    	Map<String, EOMenuItem> globalMenuItemMap = globalMenuItemRepository.findByUrls(globalMenuItemUrls)
	    			.stream().collect(Collectors.toMap(EOMenuItem::getUrl, Function.identity()));
	    	for (EOMenuItem globalMenuItem : globalMenuItemList) {
	    		EOMenuItem eoGlobalMenuItem = globalMenuItemMap.getOrDefault(globalMenuItem.getUrl(),globalMenuItem);
	    		BeanUtils.copyProperties(globalMenuItem, eoGlobalMenuItem, "id");
	    		EOMenuItem saveGlobalMenuItem = globalMenuItemRepository.save(eoGlobalMenuItem);
	    		globalMenuItem.setId(saveGlobalMenuItem.getId());
	    		globalMenuItemMap.put(globalMenuItem.getUrl(), globalMenuItem);
			}
	    	Map<String, EORoleMenuGroup> userRoleMenuGroupMap = userRoleMenuGroupRepository.findAll().parallelStream().collect(Collectors.toMap((userRoleMenuGroup)->groupKey(userRoleMenuGroup), Function.identity()));
	    	List<EORoleMenuGroup> userRoleMenuGroups = instance.getAll(EORoleMenuGroup.class);
	    	for(EORoleMenuGroup userRoleMenuGroup: userRoleMenuGroups) {
	    		try {
					EORoleMenuGroup eoUserRoleMenuGroup = userRoleMenuGroupMap.getOrDefault(groupKey(userRoleMenuGroup),userRoleMenuGroup);
					BeanUtils.copyProperties(userRoleMenuGroup, eoUserRoleMenuGroup, "id");
		    		EORoleMenuGroup saveUserRoleMenuGroup = userRoleMenuGroupRepository.save(eoUserRoleMenuGroup);
		    		userRoleMenuGroup.setId(saveUserRoleMenuGroup.getId());
		    		eoUserRoleMenuGroup.setId(saveUserRoleMenuGroup.getId());
	    		}catch (Exception e) {
					System.out.println("userRoleMenuGroup="+userRoleMenuGroup);
					e.printStackTrace();
				}
			}
	    	Map<String, EORoleMenuItem> userRoleMenuItemMap = userRoleMenuItemRepository.findAll().parallelStream().collect(Collectors.toMap((userRoleMenuItem)->itemKey(userRoleMenuItem), Function.identity()));
	    	List<EORoleMenuItem> userRoleEndpointList = instance.getAll(EORoleMenuItem.class);
	    	for(EORoleMenuItem userRoleMenuItem: userRoleEndpointList) {
	    		try {
					EORoleMenuItem eoUserRoleEndpoint = userRoleMenuItemMap.getOrDefault(itemKey(userRoleMenuItem),userRoleMenuItem);
					BeanUtils.copyProperties(userRoleMenuItem, eoUserRoleEndpoint, "id");
		    		EORoleMenuItem saveUserRoleEndpoint = userRoleMenuItemRepository.save(eoUserRoleEndpoint);
		    		userRoleMenuItem.setId(saveUserRoleEndpoint.getId());
	    		}catch (Exception e) {
					System.out.println("userRoleEndpoint="+userRoleMenuItem);
					e.printStackTrace();
				}
			}
    	}
    }

	private String itemKey(EORoleMenuItem userRoleMenuItem) {
		if(userRoleMenuItem.getMenuItem()==null || userRoleMenuItem.getUserRole()==null) {
			return "";
		}
		return userRoleMenuItem.getUserRole().getId()+"_"+ userRoleMenuItem.getMenuItem().getId();
	}

	private String groupKey(EORoleMenuGroup userRoleMenuGroup) {
		if(userRoleMenuGroup.getMenuGroup()==null || userRoleMenuGroup.getUserRole()==null) {
			return "";
		}
		return userRoleMenuGroup.getUserRole().getId()+"_"+ userRoleMenuGroup.getMenuGroup().getId();
	}
    
    public static String getKey(EORoleMenuItem userRoleEndpoint) {
    	return userRoleEndpoint.getUserRole().getId()+"_"+userRoleEndpoint.getMenuItem().getId();
    }
}
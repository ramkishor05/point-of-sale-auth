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
	    	Map<String, EORoleMenuGroup> roleMenuGroupMap = roleMenuGroupRepository.findAll().parallelStream().collect(Collectors.toMap((userRoleMenuGroup)->getRoleMenuGroupKey(userRoleMenuGroup), Function.identity()));
	    	List<EORoleMenuGroup> roleMenuGroups = instance.getAll(EORoleMenuGroup.class);
	    	for(EORoleMenuGroup roleMenuGroup: roleMenuGroups) {
	    		try {
					EORoleMenuGroup eoRoleMenuGroup = roleMenuGroupMap.getOrDefault(getRoleMenuGroupKey(roleMenuGroup),roleMenuGroup);
					BeanUtils.copyProperties(roleMenuGroup, eoRoleMenuGroup, "id");
		    		EORoleMenuGroup saveRoleMenuGroup = roleMenuGroupRepository.save(eoRoleMenuGroup);
		    		roleMenuGroup.setId(saveRoleMenuGroup.getId());
		    		eoRoleMenuGroup.setId(saveRoleMenuGroup.getId());
	    		}catch (Exception e) {
					System.out.println("roleMenuGroup="+roleMenuGroup);
					e.printStackTrace();
				}
			}
	    	Map<String, EORoleMenuItem> roleMenuItemMap = roleMenuItemRepository.findAll().parallelStream().collect(Collectors.toMap((userRoleMenuItem)->getRoleMenuItemKey(userRoleMenuItem), Function.identity()));
	    	List<EORoleMenuItem> roleEndpointList = instance.getAll(EORoleMenuItem.class);
	    	for(EORoleMenuItem roleMenuItem: roleEndpointList) {
	    		try {
					EORoleMenuItem eoRoleEndpoint = roleMenuItemMap.getOrDefault(getRoleMenuItemKey(roleMenuItem),roleMenuItem);
					BeanUtils.copyProperties(roleMenuItem, eoRoleEndpoint, "id");
		    		EORoleMenuItem saveRoleEndpoint = roleMenuItemRepository.save(eoRoleEndpoint);
		    		roleMenuItem.setId(saveRoleEndpoint.getId());
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
	    		}catch (Exception e) {
					System.out.println("headerItem="+headerItem);
					e.printStackTrace();
				}
			}
	    	
	    	Map<String, EORoleHeaderItem> roleHeaderItemMap = roleHeaderItemRepository.findAll().parallelStream().collect(Collectors.toMap((userRoleHeaderItem)->getRoleHeaderItemKey(userRoleHeaderItem), Function.identity()));
	    	List<EORoleHeaderItem> userRoleHeaderItemList = instance.getAll(EORoleHeaderItem.class);
	    	for(EORoleHeaderItem roleHeaderItem: userRoleHeaderItemList) {
	    		try {
	    			EORoleHeaderItem eoRoleHeaderItem = roleHeaderItemMap.getOrDefault(getRoleHeaderItemKey(roleHeaderItem),roleHeaderItem);
					BeanUtils.copyProperties(roleHeaderItem, eoRoleHeaderItem, "id");
					EORoleHeaderItem saveRoleHeaderItem = roleHeaderItemRepository.save(eoRoleHeaderItem);
		    		roleHeaderItem.setId(saveRoleHeaderItem.getId());
	    		}catch (Exception e) {
					System.out.println("roleHeaderItem="+roleHeaderItem);
					e.printStackTrace();
				}
			}
	    	
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
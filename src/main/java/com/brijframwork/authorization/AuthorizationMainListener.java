package com.brijframwork.authorization;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.brijframework.production.schema.factories.JsonSchemaDataFactory;
import com.brijframwork.authorization.constant.UserRole;
import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.model.EOUserEndpoint;
import com.brijframwork.authorization.model.EOUserProfile;
import com.brijframwork.authorization.model.EOUserRole;
import com.brijframwork.authorization.model.EOUserRoleEndpoint;
import com.brijframwork.authorization.repository.UserAccountRepository;
import com.brijframwork.authorization.repository.UserEndpointRepository;
import com.brijframwork.authorization.repository.UserProfileRepository;
import com.brijframwork.authorization.repository.UserRoleEndpointRepository;
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
	private UserEndpointRepository userEndpointRepository;
	
	@Autowired
	private UserRoleEndpointRepository userRoleEndpointRepository;
	
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
    	
    	for(UserRole userRole : UserRole.values()) {
    		Optional<EOUserRole> findByPosition = userRoleRepository.findByPosition(userRole.getPosition());
    		if( !findByPosition.isPresent()) {
    			EOUserRole eoUserRole = new EOUserRole(userRole.getPosition(),userRole.getRole(),userRole.getRole());
    			eoUserRole.setRoleType(userRole.getType());
    			eoUserRole=userRoleRepository.saveAndFlush(eoUserRole);
    			Optional<EOUserAccount> findUserAccount = userAccountRepository.findUserName(eoUserRole.getRoleName());
    	    	if(!findUserAccount.isPresent()) {
    	    		EOUserAccount eoUserAccount=new EOUserAccount();
    	    		eoUserAccount.setAccountName(eoUserRole.getRoleName());
    	    		eoUserAccount.setUsername(eoUserRole.getRoleName());
    	    		eoUserAccount.setType(eoUserRole.getRoleName());
    	    		eoUserAccount.setPassword((eoUserRole.getRoleName()));
    	    		eoUserAccount.setUserRole(eoUserRole);
    	    		eoUserAccount=userAccountRepository.saveAndFlush(eoUserAccount);
    	    		EOUserProfile eoUserProfile=new EOUserProfile();
    	    		eoUserProfile.setFirstName(eoUserRole.getRoleName());
    	    		eoUserProfile.setUserAccount(eoUserAccount);
    	    		userProfileRepository.saveAndFlush(eoUserProfile);
    	    	}
    		}
    	}
    	JsonSchemaDataFactory instance = JsonSchemaDataFactory.getInstance();
    	List<EOUserRole> userRoleList = instance.getAll(EOUserRole.class);
    	for (EOUserRole userRole : userRoleList) {
    		EOUserRole eoUserRole = userRoleRepository.findByPosition(userRole.getPosition()).orElse(userRole);
    		BeanUtils.copyProperties(userRole, eoUserRole, "id");
    		EOUserRole saveUserRole=userRoleRepository.saveAndFlush(eoUserRole);
    		userRole.setId(saveUserRole.getId());
    	}
    	List<EOUserEndpoint> userEndpointList = instance.getAll(EOUserEndpoint.class);
    	for (EOUserEndpoint userEndpoint : userEndpointList) {
    		EOUserEndpoint eoUserEndpoint = userEndpointRepository.findByUrl(userEndpoint.getUrl()).orElse(userEndpoint);
    		BeanUtils.copyProperties(userEndpoint, eoUserEndpoint, "id");
    		EOUserEndpoint saveUserEndpoint = userEndpointRepository.save(eoUserEndpoint);
    		userEndpoint.setId(saveUserEndpoint.getId());
		}
    	List<EOUserRoleEndpoint> userRoleEndpointList = instance.getAll(EOUserRoleEndpoint.class);
    	for(EOUserRoleEndpoint userRoleEndpoint: userRoleEndpointList) {
    		if(userRoleEndpoint.getUserRole()==null || userRoleEndpoint.getUserEndpoint()==null) {
    			continue;
    		}
			EOUserRoleEndpoint eoUserRoleEndpoint = userRoleEndpointRepository.findByRoleIdAndEndpointId(userRoleEndpoint.getUserRole().getId(), userRoleEndpoint.getUserEndpoint().getId()).orElse(userRoleEndpoint);
    		BeanUtils.copyProperties(userRoleEndpoint, eoUserRoleEndpoint, "id");
    		EOUserRoleEndpoint saveUserRoleEndpoint = userRoleEndpointRepository.save(eoUserRoleEndpoint);
    		userRoleEndpoint.setId(saveUserRoleEndpoint.getId());
		}
    }
}
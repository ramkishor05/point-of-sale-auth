package com.brijframwork.authorization;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.brijframwork.authorization.constant.UserRole;
import com.brijframwork.authorization.constant.UserType;
import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.model.EOUserProfile;
import com.brijframwork.authorization.model.EOUserRole;
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
	
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
    	for(UserRole userRole : UserRole.values()) {
    		if(userRoleRepository.findByPosition(userRole.getPosition()) ==null) {
    			userRoleRepository.saveAndFlush(new EOUserRole(userRole.getPosition(),userRole.getRole(),userRole.getRole()));
    		}
    	}
    
    	EOUserRole userRole=userRoleRepository.findByPosition(UserRole.ADMIN.getPosition());
    	Optional<EOUserAccount> findUserAccount = userAccountRepository.findUserName(UserRole.ADMIN.getRole());
    	if(!findUserAccount.isPresent()) {
    		
    		EOUserAccount eoUserAccount=new EOUserAccount();
    		eoUserAccount.setAccountName(UserRole.ADMIN.getRole());
    		eoUserAccount.setUsername(UserRole.ADMIN.getRole());
    		eoUserAccount.setType(UserType.ADMIN.name());
    		eoUserAccount.setPassword((UserRole.ADMIN.getRole()));
    		eoUserAccount.setUserRole(userRole);
    		eoUserAccount=userAccountRepository.saveAndFlush(eoUserAccount);
    		
    		EOUserProfile eoUserProfile=new EOUserProfile();
    		eoUserProfile.setFirstName(UserType.ADMIN.name());
    		eoUserProfile.setUserAccount(eoUserAccount);
    		userProfileRepository.saveAndFlush(eoUserProfile);
    	}
    }
}
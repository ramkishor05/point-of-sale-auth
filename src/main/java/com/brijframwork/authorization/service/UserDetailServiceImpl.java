package com.brijframwork.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframwork.authorization.beans.UserDetailRequest;
import com.brijframwork.authorization.constant.UserRole;
import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.model.EOUserProfile;
import com.brijframwork.authorization.model.EOUserRole;
import com.brijframwork.authorization.repository.UserAccountRepository;
import com.brijframwork.authorization.repository.UserProfileRepository;
import com.brijframwork.authorization.repository.UserRoleRepository;

@Service
public class UserDetailServiceImpl implements UserDetailService {
	

	@Autowired
	private UserAccountRepository userLoginRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Override
	public boolean register(UserDetailRequest userDetailRequest) {
		if(isAlreadyExists(userDetailRequest.getUsername())) {
			return false;
		}
		UserRole owner = UserRole.OWNER;
		EOUserRole eoUserRole = userRoleRepository.findByPosition(owner.getPosition());
		
		EOUserProfile eoUserProfile=new EOUserProfile();
		eoUserProfile.setFirstName(eoUserRole.getRoleName());
		eoUserProfile = userProfileRepository.saveAndFlush(eoUserProfile);
		
		EOUserAccount eoUserAccount=new EOUserAccount();
		eoUserAccount.setUsername(userDetailRequest.getUsername());
		eoUserAccount.setPassword(userDetailRequest.getPassword());
		eoUserAccount.setOwnerId(userDetailRequest.getOwnerId());
		eoUserAccount.setUserRole(eoUserRole);
		eoUserAccount.setUserProfile(eoUserProfile);
		eoUserAccount=userAccountRepository.saveAndFlush(eoUserAccount);
		return true;
	}

	@Override
	public boolean isAlreadyExists(String username) {
		return userLoginRepository.findUserName(username).isPresent();
	}


}

package com.brijframwork.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframwork.authorization.beans.UIUserProfile;
import com.brijframwork.authorization.beans.UIUserAccount;
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
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Override
	public boolean register(UIUserAccount userDetailRequest) {
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
		eoUserAccount.setType(eoUserRole.getRoleId());
		eoUserAccount.setMobile(userDetailRequest.getMobile());
		eoUserAccount.setEmail(userDetailRequest.getEmail());
		eoUserAccount.setAccountName(userDetailRequest.getAccountName());
		eoUserAccount.setOwnerId(userDetailRequest.getOwnerId());
		eoUserAccount.setUserRole(eoUserRole);
		eoUserAccount.setUserProfile(eoUserProfile);
		eoUserAccount=userAccountRepository.saveAndFlush(eoUserAccount);
		return true;
	}

	@Override
	public boolean isAlreadyExists(String username) {
		return userAccountRepository.findUserName(username).isPresent();
	}

	@Override
	public UIUserProfile updateUserProfile(UIUserProfile uiUserProfile) {
		EOUserProfile eoUserProfile=userProfileRepository.getOne(uiUserProfile.getId());
		eoUserProfile.setTitle(uiUserProfile.getTitle());
		eoUserProfile.setFirstName(uiUserProfile.getFirstName());
		eoUserProfile.setLastName(uiUserProfile.getLastName());
		eoUserProfile.setPreferredName(uiUserProfile.getPreferredName());
		eoUserProfile.setPictureURL(uiUserProfile.getPictureURL());
		eoUserProfile = userProfileRepository.saveAndFlush(eoUserProfile);
		return uiUserProfile;
	}

	@Override
	public UIUserAccount updateUserAccount(UIUserAccount uiUserAccount) {
		EOUserAccount eoUserAccount=userAccountRepository.getOne(uiUserAccount.getId());
		eoUserAccount.setUsername(uiUserAccount.getUsername());
		eoUserAccount.setPassword(uiUserAccount.getPassword());
		eoUserAccount.setType(uiUserAccount.getType());
		eoUserAccount.setAccountName(uiUserAccount.getAccountName());
		eoUserAccount.setMobile(uiUserAccount.getMobile());
		eoUserAccount.setEmail(uiUserAccount.getEmail());
		eoUserAccount.setOwnerId(uiUserAccount.getOwnerId());
		eoUserAccount=userAccountRepository.saveAndFlush(eoUserAccount);
		return uiUserAccount;
	}

	@Override
	public UIUserProfile getUserProfile(Long id) {
		EOUserProfile eoUserProfile=userProfileRepository.getOne(id);
		UIUserProfile uiUserProfile=new UIUserProfile();
		uiUserProfile.setTitle(eoUserProfile.getTitle());
		uiUserProfile.setFirstName(eoUserProfile.getFirstName());
		uiUserProfile.setLastName(eoUserProfile.getLastName());
		uiUserProfile.setPreferredName(eoUserProfile.getPreferredName());
		uiUserProfile.setPictureURL(eoUserProfile.getPictureURL());
		return uiUserProfile;
	}


}

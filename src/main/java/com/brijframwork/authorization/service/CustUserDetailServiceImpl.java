package com.brijframwork.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframwork.authorization.beans.UIUserAccount;
import com.brijframwork.authorization.beans.UserDetailResponse;
import com.brijframwork.authorization.mapper.UserDetailMapper;
import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.model.EOUserProfile;
import com.brijframwork.authorization.model.EOUserRole;
import com.brijframwork.authorization.repository.UserAccountRepository;
import com.brijframwork.authorization.repository.UserProfileRepository;
import com.brijframwork.authorization.repository.UserRoleRepository;

@Service
public class CustUserDetailServiceImpl implements CustUserDetailService {
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	UserDetailMapper userDetailMapper;
	
	@Override
	public UserDetailResponse registerAccount(Long ownerId,UIUserAccount userDetailRequest) {
		if(isAlreadyExists(userDetailRequest.getUsername())) {
			throw new RuntimeException("AlreadyExists");
		}
		EOUserRole eoUserRole = userRoleRepository.findByPosition(userDetailRequest.getUserRoleId());
		
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
		return userDetailMapper.mapToDTO(eoUserAccount);
	}

	public boolean isAlreadyExists(String username) {
		return userAccountRepository.findUserName(username).isPresent();
	}

	@Override
	public UIUserAccount updateAccount(Long ownerId,UIUserAccount uiUserAccount) {
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
	public List<UserDetailResponse> getCustUsers(Long ownerId) {
		return userDetailMapper.mapToDTO(userAccountRepository.findAllByOwnerId(ownerId));
	}

}

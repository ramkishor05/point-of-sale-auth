package com.brijframework.authorization.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.authorization.beans.UIUserProfile;
import com.brijframework.authorization.beans.UserDetailRequest;
import com.brijframework.authorization.beans.UserDetailResponse;
import com.brijframework.authorization.constant.UserType;
import com.brijframework.authorization.mapper.UserDetailMapper;
import com.brijframework.authorization.model.EOUserAccount;
import com.brijframework.authorization.model.EOUserProfile;
import com.brijframework.authorization.model.EOUserRole;
import com.brijframework.authorization.repository.UserAccountRepository;
import com.brijframework.authorization.repository.UserProfileRepository;
import com.brijframework.authorization.repository.UserRoleRepository;

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
	public UserDetailResponse registerAccount(Long ownerId,UserDetailRequest userDetailRequest) {
		/*
		 * if(isAlreadyExists(userDetailRequest.getUsername())) { throw new
		 * RuntimeException("AlreadyExists"); }
		 */
		EOUserRole eoUserRole = userRoleRepository.findById(userDetailRequest.getUserRoleId()).orElseThrow(()-> new RuntimeException("Invalid role id"));
		if(userDetailRequest.getUserProfile()==null) {
			UIUserProfile uiUserProfile=new UIUserProfile();
			userDetailRequest.setUserProfile(uiUserProfile);
		}
		EOUserAccount eoUserAccount=userAccountRepository.findUserName(userDetailRequest.getUsername()).orElse(new EOUserAccount());
		eoUserAccount.setUsername(userDetailRequest.getUsername());
		eoUserAccount.setPassword(userDetailRequest.getPassword());
		eoUserAccount.setType(eoUserRole.getRoleId());
		eoUserAccount.setRegisteredMobile(userDetailRequest.getRegisteredMobile());
		eoUserAccount.setRegisteredEmail(userDetailRequest.getRegisteredEmail());
		eoUserAccount.setAccountName(userDetailRequest.getAccountName());
		eoUserAccount.setOwnerId(ownerId);
		eoUserAccount.setUserRole(eoUserRole);
		eoUserAccount.setUserProfile(getUserProfile(userDetailRequest.getUserProfile()));
		eoUserAccount=userAccountRepository.save(eoUserAccount);		
		return userDetailMapper.mapToDTO(eoUserAccount);
	}
	
	@Override
	public UserDetailResponse deleteAccount(Long ownerId, String username) {
		Optional<EOUserAccount> findUserAccount = userAccountRepository.findUserName(username);
		if(findUserAccount.isPresent()) {
			userAccountRepository.delete(findUserAccount.get());
			return userDetailMapper.mapToDTO(new EOUserAccount());
		}
		return userDetailMapper.mapToDTO(new EOUserAccount());
	}

	public boolean isAlreadyExists(String username) {
		return userAccountRepository.findUserName(username).isPresent();
	}

	@Override
	public UserDetailResponse updateAccount(Long ownerId,UserDetailRequest userDetailRequest) {
		if(!isAlreadyExists(userDetailRequest.getUsername())) {
			throw new RuntimeException("User not exists in system.");
		}
		EOUserRole eoUserRole = userRoleRepository.findById(userDetailRequest.getUserRoleId()).orElseThrow(()-> new RuntimeException("Invalid role id"));
		if(userDetailRequest.getUserProfile()==null) {
			UIUserProfile uiUserProfile=new UIUserProfile();
			userDetailRequest.setUserProfile(uiUserProfile);
		}
		EOUserAccount eoUserAccount=new EOUserAccount();
		eoUserAccount.setId(userDetailRequest.getId());
		eoUserAccount.setUsername(userDetailRequest.getUsername());
		eoUserAccount.setPassword(userDetailRequest.getPassword());
		eoUserAccount.setType(eoUserRole.getRoleId());
		eoUserAccount.setRegisteredMobile(userDetailRequest.getRegisteredMobile());
		eoUserAccount.setRegisteredEmail(userDetailRequest.getRegisteredEmail());
		eoUserAccount.setAccountName(userDetailRequest.getAccountName());
		eoUserAccount.setOwnerId(ownerId);
		eoUserAccount.setUserRole(eoUserRole);
		eoUserAccount.setUserProfile(getUserProfile(userDetailRequest.getUserProfile()));
		eoUserAccount=userAccountRepository.save(eoUserAccount);		
		return userDetailMapper.mapToDTO(eoUserAccount);
	}

	private EOUserProfile getUserProfile(UIUserProfile userProfile) {
		EOUserProfile eoUserProfile=new EOUserProfile();
		eoUserProfile.setId(userProfile.getId());
		eoUserProfile.setTitle(userProfile.getTitle());
		eoUserProfile.setFullName(userProfile.getFullName());
		eoUserProfile.setPreferredName(userProfile.getPreferredName());
		eoUserProfile.setPictureURL(userProfile.getPictureURL());
		return userProfileRepository.save(eoUserProfile);
	}

	@Override
	public List<UserDetailResponse> getCustUsers(Long ownerId) {
		return userDetailMapper.mapToDTO(userAccountRepository.findAllByOwnerIdAndRoleType(ownerId, UserType.VENDOR.getType()));
	}

}

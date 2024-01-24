package com.brijframwork.authorization.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframwork.authorization.beans.UIUserAccount;
import com.brijframwork.authorization.beans.UIUserProfile;
import com.brijframwork.authorization.beans.UserDetailResponse;
import com.brijframwork.authorization.constant.UserRole;
import com.brijframwork.authorization.mapper.UserDetailMapper;
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
	
	@Autowired
	private UserDetailMapper userDetailMapper;

	@Autowired
	private UserOnBoardingService userOnBoardingService;

	@Override
	public boolean register(UIUserAccount userDetailRequest) {
		if(isAlreadyExists(userDetailRequest.getUsername())) {
			return false;
		}
		UserRole owner = UserRole.VENDOR;
		EOUserRole eoUserRole = userRoleRepository.findByPosition(owner.getPosition()).orElse(null);
		
		EOUserProfile eoUserProfile=new EOUserProfile();
		eoUserProfile.setFullName(eoUserRole.getRoleName());
		eoUserProfile = userProfileRepository.save(eoUserProfile);
		
		EOUserAccount eoUserAccount=new EOUserAccount();
		eoUserAccount.setUsername(userDetailRequest.getUsername());
		eoUserAccount.setPassword(userDetailRequest.getPassword());
		eoUserAccount.setType(eoUserRole.getRoleId());
		eoUserAccount.setRegisteredMobile(userDetailRequest.getMobile());
		eoUserAccount.setRegisteredEmail(userDetailRequest.getEmail());
		eoUserAccount.setAccountName(userDetailRequest.getAccountName());
		eoUserAccount.setOwnerId(userDetailRequest.getOwnerId());
		eoUserAccount.setUserRole(eoUserRole);
		eoUserAccount.setUserProfile(eoUserProfile);
		eoUserAccount.setOnBoarding(true);		
		eoUserAccount=userAccountRepository.save(eoUserAccount);
		
		userOnBoardingService.initOnBoarding(eoUserAccount);
		
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
		eoUserProfile.setFullName(uiUserProfile.getFullName());
		eoUserProfile.setPreferredName(uiUserProfile.getPreferredName());
		eoUserProfile.setPictureURL(uiUserProfile.getPictureURL());
		eoUserProfile = userProfileRepository.saveAndFlush(eoUserProfile);
		return uiUserProfile;
	}

	@Override
	public UserDetailResponse updateUserAccount(UIUserAccount uiUserAccount) {
		EOUserAccount eoUserAccount=userAccountRepository.getOne(uiUserAccount.getId());
		eoUserAccount.setUsername(uiUserAccount.getUsername());
		eoUserAccount.setPassword(uiUserAccount.getPassword());
		eoUserAccount.setType(uiUserAccount.getType());
		eoUserAccount.setAccountName(uiUserAccount.getAccountName());
		eoUserAccount.setRegisteredMobile(uiUserAccount.getMobile());
		eoUserAccount.setRegisteredEmail(uiUserAccount.getEmail());
		eoUserAccount.setOwnerId(uiUserAccount.getOwnerId());
		eoUserAccount=userAccountRepository.saveAndFlush(eoUserAccount);
		userOnBoardingService.initOnBoarding(eoUserAccount);
		return userDetailMapper.mapToDTO(eoUserAccount);
	}

	@Override
	public UIUserProfile getUserProfile(Long id) {
		EOUserProfile eoUserProfile=userProfileRepository.getOne(id);
		UIUserProfile uiUserProfile=new UIUserProfile();
		uiUserProfile.setTitle(eoUserProfile.getTitle());
		uiUserProfile.setFullName(eoUserProfile.getFullName());
		uiUserProfile.setPreferredName(eoUserProfile.getPreferredName());
		uiUserProfile.setPictureURL(eoUserProfile.getPictureURL());
		return uiUserProfile;
	}

	@Override
	public List<UserDetailResponse> getUsers() {
		return userDetailMapper.mapToDTO(userAccountRepository.findAll());
	}

	@Override
	public boolean updateOnboarding(Long id, boolean onboarding , String idenNo) {
		Optional<EOUserAccount> findUserAccount = userAccountRepository.findById(id);
		if(findUserAccount.isPresent()) {
			EOUserAccount eoUserAccount = findUserAccount.get();
			return userOnBoardingService.updateOnBoardingStatus(onboarding, idenNo, eoUserAccount);
		}
		return false;
	}

}

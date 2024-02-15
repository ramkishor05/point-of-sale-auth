package com.brijframework.authorization.service;

import java.util.List;

import com.brijframework.authorization.beans.UIUserAccount;
import com.brijframework.authorization.beans.UIUserProfile;
import com.brijframework.authorization.beans.UserDetailResponse;

public interface UserDetailService {

	boolean register(UIUserAccount userDetailRequest);

	boolean isAlreadyExists(String username);

	UIUserProfile updateUserProfile(UIUserProfile uiUserProfile);

	UserDetailResponse updateUserAccount(UIUserAccount uiUserAccount);

	UIUserProfile getUserProfile(Long id);

	List<UserDetailResponse> getUsers();

	boolean updateOnboarding(Long id, boolean onboarding, String idenNo);
}

package com.brijframwork.authorization.service;

import com.brijframwork.authorization.beans.UIUserProfile;
import com.brijframwork.authorization.beans.UIUserAccount;

public interface UserDetailService {

	boolean register(UIUserAccount userDetailRequest);

	boolean isAlreadyExists(String username);

	UIUserProfile updateUserProfile(UIUserProfile uiUserProfile);

	UIUserAccount updateUserAccount(UIUserAccount uiUserAccount);

}

package com.brijframwork.authorization.service;

import java.util.List;

import com.brijframwork.authorization.beans.UIUserAccount;
import com.brijframwork.authorization.beans.UserDetailResponse;

public interface CustUserDetailService {

	UserDetailResponse registerAccount(Long ownerId, UIUserAccount uiUserAccount);

	UIUserAccount updateAccount(Long ownerId, UIUserAccount uiUserAccount);

	List<UserDetailResponse> getCustUsers(Long ownerId);
}

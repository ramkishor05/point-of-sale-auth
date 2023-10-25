package com.brijframwork.authorization.service;

import com.brijframwork.authorization.beans.UserDetailRequest;

public interface UserDetailService {

	boolean register(UserDetailRequest userDetailRequest);

	boolean isAlreadyExists(String username);

}

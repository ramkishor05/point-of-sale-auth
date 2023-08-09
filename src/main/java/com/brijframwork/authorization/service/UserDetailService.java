package com.brijframwork.authorization.service;

import com.brijframwork.authorization.beans.UserDetailResponse;

public interface UserDetailService {

	UserDetailResponse getUserDetailFromToken(String token);

}

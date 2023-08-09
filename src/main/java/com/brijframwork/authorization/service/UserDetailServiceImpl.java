package com.brijframwork.authorization.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframwork.authorization.adptor.TokenProvider;
import com.brijframwork.authorization.beans.UserDetailResponse;
import com.brijframwork.authorization.mapper.UserDetailMapper;
import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.repository.UserAccountRepository;

@Service
public class UserDetailServiceImpl implements UserDetailService {
	

	@Autowired
	private UserDetailMapper userDetailMapper;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private UserAccountRepository userLoginRepository;

	@Override
	public UserDetailResponse getUserDetailFromToken(String token) {
		String username = tokenProvider.getUsernameFromToken(token);
		Optional<EOUserAccount> findUserLogin = userLoginRepository.findUserName(username);
		EOUserAccount eoUserAccount = findUserLogin.orElse(null);
		return userDetailMapper.mapToDTO(eoUserAccount);
	}

}

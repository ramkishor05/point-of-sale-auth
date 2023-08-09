package com.brijframwork.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframwork.authorization.constant.Constants;
import com.brijframwork.authorization.service.UserDetailService;

@RestController
@RequestMapping("/api/user")
public class UserDetailController {
	
	private static final String API_TOKEN = "api_token";
	
	@Autowired
    private UserDetailService userDetailService;

	@GetMapping
    public ResponseEntity<?> getUserDetailFromToken(@RequestHeader(API_TOKEN) String apiToken) throws AuthenticationException {
    	String token = apiToken.replaceFirst(Constants.TOKEN_PREFIX, "");
    	return ResponseEntity.ok(userDetailService.getUserDetailFromToken(token));
    }
}

package com.brijframwork.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframwork.authorization.beans.UserDetailRequest;
import com.brijframwork.authorization.service.UserDetailService;

@RestController
@RequestMapping("/api/user")
public class UserDetailController {
	
	@Autowired
    private UserDetailService userDetailService;
	
	@PostMapping
	public ResponseEntity<?> register(@RequestBody UserDetailRequest userDetailRequest){
    	return ResponseEntity.ok(userDetailService.register(userDetailRequest));
	}
	
	@GetMapping("/exists/{username}")
	public ResponseEntity<?> isAlreadyExists(@PathVariable String username){
    	return ResponseEntity.ok(userDetailService.isAlreadyExists(username));
	}
	
}

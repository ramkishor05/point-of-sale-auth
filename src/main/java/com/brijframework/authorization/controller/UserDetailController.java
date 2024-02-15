package com.brijframework.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.authorization.beans.UIUserAccount;
import com.brijframework.authorization.beans.UIUserProfile;
import com.brijframework.authorization.service.UserDetailService;

@RestController
@RequestMapping("/api/user")
public class UserDetailController {
	
	@Autowired
    private UserDetailService userDetailService;
	
	@PostMapping
	public ResponseEntity<?> register(@RequestBody UIUserAccount uiUserAccount){
    	return ResponseEntity.ok(userDetailService.register(uiUserAccount));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUserAccount(@PathVariable Long id, @RequestBody UIUserAccount uiUserAccount){
    	return ResponseEntity.ok(userDetailService.updateUserAccount(uiUserAccount));
	}
	
	@GetMapping
	public ResponseEntity<?> getUsers(){
    	return ResponseEntity.ok(userDetailService.getUsers());
	}
	
	@GetMapping("/exists/{username}")
	public ResponseEntity<?> isAlreadyExists(@PathVariable String username){
    	return ResponseEntity.ok(userDetailService.isAlreadyExists(username));
	}
	
	@PutMapping("/profile")
	public ResponseEntity<?> updateUserProfile(@RequestBody UIUserProfile uiUserProfile){
    	return ResponseEntity.ok(userDetailService.updateUserProfile(uiUserProfile));
	}
	
	@PutMapping("/onboarding/{id}/{onboarding}/{idenNo}")
	public ResponseEntity<?> updateOnboarding(@PathVariable Long id, @PathVariable Boolean onboarding,@PathVariable String idenNo){
    	return ResponseEntity.ok(userDetailService.updateOnboarding(id, onboarding, idenNo));
	}
	
	@GetMapping("/profile/{id}")
	public ResponseEntity<?> getUserProfile(@PathVariable Long id){
    	return ResponseEntity.ok(userDetailService.getUserProfile(id));
	}
	
}

package com.brijframwork.authorization.controller;

import static com.brijframwork.authorization.constant.Constants.OWNER_ID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframwork.authorization.beans.UserDetailRequest;
import com.brijframwork.authorization.service.CustUserDetailService;

@RestController
@RequestMapping("/api/cust/user")
public class CustUserController {
	
	@Autowired
    private CustUserDetailService userDetailService;
	
	@PostMapping
	public ResponseEntity<?> registerCust(@RequestHeader(OWNER_ID) long ownerId, @RequestBody UserDetailRequest uiUserAccount){
		return ResponseEntity.ok(userDetailService.registerAccount(ownerId,uiUserAccount));
	}
	
	@DeleteMapping("/username/{username}")
	public ResponseEntity<?> deleteCust(@RequestHeader(OWNER_ID) long ownerId, @PathVariable String username){
		
    	return ResponseEntity.ok(userDetailService.deleteAccount(ownerId,username));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUserAccount(@RequestHeader(OWNER_ID) long ownerId, @PathVariable Long id, @RequestBody UserDetailRequest uiUserAccount){
    	return ResponseEntity.ok(userDetailService.updateAccount(ownerId, uiUserAccount));
	}
	
	@GetMapping
	public ResponseEntity<?> getCustUsers(@RequestHeader(OWNER_ID) long ownerId){
    	return ResponseEntity.ok(userDetailService.getCustUsers(ownerId));
	}
	
}

package com.brijframwork.authorization.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframwork.authorization.beans.UIUserRole;
import com.brijframwork.authorization.service.CustRoleService;

@RestController
@RequestMapping("/api/cust/role")
public class CustRoleController {
	
	@Autowired
    private CustRoleService custRoleService;
	
	@PostMapping
	public ResponseEntity<UIUserRole> addUserRole(@RequestBody UIUserRole uiUserRole){
    	return ResponseEntity.ok(custRoleService.addUserRole(uiUserRole));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UIUserRole> updateUserRole(@PathVariable Long id, @RequestBody UIUserRole uiUserRole){
    	return ResponseEntity.ok(custRoleService.updateUserRole(uiUserRole));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteUserRole(@PathVariable Long id){
    	return ResponseEntity.ok(custRoleService.deleteUserRole(id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UIUserRole> getUserRole(@PathVariable Long id){
    	return ResponseEntity.ok(custRoleService.getUserRole(id));
	}
	
	@GetMapping
	public ResponseEntity<List<UIUserRole>> getUserRoleList(){
    	return ResponseEntity.ok(custRoleService.getUserRoleList());
	}
	
	@GetMapping("/type/{type}")
	public ResponseEntity<List<UIUserRole>> getUserRoleList(@PathVariable String type){
    	return ResponseEntity.ok(custRoleService.getUserRoleList(type));
	}
	
}

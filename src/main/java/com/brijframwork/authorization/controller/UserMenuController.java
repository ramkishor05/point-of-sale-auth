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

import com.brijframwork.authorization.beans.UIMenuGroup;
import com.brijframwork.authorization.service.GlobalMenuService;

@RestController
@RequestMapping("/api/menu")
public class UserMenuController {
	
	@Autowired
    private GlobalMenuService userMenuService;
	
	@PostMapping
	public ResponseEntity<UIMenuGroup> addMenuGroup(@RequestBody UIMenuGroup uiMenuGroup){
    	return ResponseEntity.ok(userMenuService.addMenuGroup(uiMenuGroup));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UIMenuGroup> updateMenuGroup(@PathVariable Long id, @RequestBody UIMenuGroup uiMenuGroup){
    	return ResponseEntity.ok(userMenuService.updateMenuGroup(uiMenuGroup));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteMenuGroup(@PathVariable Long id){
    	return ResponseEntity.ok(userMenuService.deleteMenuGroup(id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UIMenuGroup> getMenuGroup(@PathVariable Long id){
    	return ResponseEntity.ok(userMenuService.getMenuGroup(id));
	}
	
	@GetMapping
	public ResponseEntity<List<UIMenuGroup>> getMenuGroupList(){
    	return ResponseEntity.ok(userMenuService.getMenuGroupList());
	}
	
	@GetMapping("/type/{type}")
	public ResponseEntity<List<UIMenuGroup>> getMenuGroupList(@PathVariable String type){
    	return ResponseEntity.ok(userMenuService.getMenuGroupList(type));
	}
	
	@GetMapping("/role/{roleId}")
	public ResponseEntity<List<UIMenuGroup>> getMenuGroupList(@PathVariable Long roleId){
    	return ResponseEntity.ok(userMenuService.getMenuGroupListByRoleId(roleId));
	}
}

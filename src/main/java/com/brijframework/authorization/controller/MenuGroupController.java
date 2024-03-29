package com.brijframework.authorization.controller;

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

import com.brijframework.authorization.beans.UIMenuGroup;
import com.brijframework.authorization.service.MenuGroupService;

@RestController
@RequestMapping("/api/menu/group")
public class MenuGroupController {
	
	@Autowired
    private MenuGroupService menuGroupService;
	
	@PostMapping
	public ResponseEntity<UIMenuGroup> addMenuGroup(@RequestBody UIMenuGroup uiMenuGroup){
    	return ResponseEntity.ok(menuGroupService.addMenuGroup(uiMenuGroup));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UIMenuGroup> updateMenuGroup(@PathVariable Long id, @RequestBody UIMenuGroup uiMenuGroup){
    	return ResponseEntity.ok(menuGroupService.updateMenuGroup(uiMenuGroup));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteMenuGroup(@PathVariable Long id){
    	return ResponseEntity.ok(menuGroupService.deleteMenuGroup(id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UIMenuGroup> getMenuGroup(@PathVariable Long id){
    	return ResponseEntity.ok(menuGroupService.getMenuGroup(id));
	}
	
	@GetMapping
	public ResponseEntity<List<UIMenuGroup>> getMenuGroupList(){
    	return ResponseEntity.ok(menuGroupService.getMenuGroupList());
	}
	
	@GetMapping("/role/{roleId}")
	public ResponseEntity<List<UIMenuGroup>> getMenuGroupListByRole(@PathVariable Long roleId){
    	return ResponseEntity.ok(menuGroupService.getMenuGroupListByRole(roleId));
	}
}

package com.brijframwork.authorization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframwork.authorization.beans.UIMenuGroup;
import com.brijframwork.authorization.beans.UIMenuItem;
import com.brijframwork.authorization.mapper.GlobalMenuGroupMapper;
import com.brijframwork.authorization.model.EOGlobalMenuGroup;
import com.brijframwork.authorization.model.EOUserRoleMenuGroup;
import com.brijframwork.authorization.model.EOUserRoleMenuItem;
import com.brijframwork.authorization.repository.GlobalMenuGroupRepository;
import com.brijframwork.authorization.repository.UserRoleMenuGroupRepository;

@Service
public class GlobalMenuServiceImpl implements GlobalMenuService {
	
	@Autowired
	private GlobalMenuGroupRepository globalMenuGroupRepository;
	
	@Autowired
	private UserRoleMenuGroupRepository userRoleMenuGroupRepository;
	
	@Autowired
	private GlobalMenuGroupMapper globalMenuGroupMapper;

	@Override
	public UIMenuGroup addMenuGroup(UIMenuGroup uiMenuGroup) {
		EOGlobalMenuGroup eoGlobalMenuGroup = globalMenuGroupMapper.mapToDAO(uiMenuGroup);
		eoGlobalMenuGroup=globalMenuGroupRepository.save(eoGlobalMenuGroup);
		
		return globalMenuGroupMapper.mapToDTO(eoGlobalMenuGroup);
	}

	@Override
	public UIMenuGroup updateMenuGroup(UIMenuGroup uiMenuGroup) {
		EOGlobalMenuGroup eoGlobalMenuGroup = globalMenuGroupMapper.mapToDAO(uiMenuGroup);
		eoGlobalMenuGroup=globalMenuGroupRepository.save(eoGlobalMenuGroup);
		return globalMenuGroupMapper.mapToDTO(eoGlobalMenuGroup);
	}

	@Override
	public boolean deleteMenuGroup(Long id) {
		globalMenuGroupRepository.deleteById(id);
		return true;
	}

	@Override
	public UIMenuGroup getMenuGroup(Long id) {
		return globalMenuGroupMapper.mapToDTO(globalMenuGroupRepository.getOne(id));
	}

	@Override
	public List<UIMenuGroup> getMenuGroupList() {
		return globalMenuGroupMapper.mapToDTO(globalMenuGroupRepository.findAll());
	}

	@Override
	public List<UIMenuGroup> getMenuGroupList(String type) {
		return globalMenuGroupMapper.mapToDTO(globalMenuGroupRepository.findAllByType(type));
	}

	@Override
	public List<UIMenuGroup> getMenuGroupListByRoleId(Long roleId) {
		List<UIMenuGroup> uiMenuGroups=new ArrayList<UIMenuGroup>();
		List<EOUserRoleMenuGroup> eoUserRoleMenuGroups = userRoleMenuGroupRepository.findAllByRoleId(roleId);
		for(EOUserRoleMenuGroup eoUserRoleMenuGroup: eoUserRoleMenuGroups) {
			UIMenuGroup uiMenuGroup=new UIMenuGroup();
			uiMenuGroup.setId(eoUserRoleMenuGroup.getId());
			uiMenuGroup.setTitle(eoUserRoleMenuGroup.getMenuGroup().getTitle());
			uiMenuGroup.setType(eoUserRoleMenuGroup.getMenuGroup().getType());
			uiMenuGroup.setUrl(eoUserRoleMenuGroup.getMenuGroup().getUrl());
			List<EOUserRoleMenuItem> userRoleMenuItems = eoUserRoleMenuGroup.getUserRoleMenuItems();
			for(EOUserRoleMenuItem userRoleMenuItem: userRoleMenuItems) {
				UIMenuItem uiMenuItem=new UIMenuItem();
				uiMenuItem.setId(userRoleMenuItem.getId());
				uiMenuItem.setTitle(userRoleMenuItem.getMenuItem().getTitle());
				uiMenuItem.setType(userRoleMenuItem.getMenuItem().getType());
				uiMenuItem.setUrl(userRoleMenuItem.getMenuItem().getUrl());
				uiMenuGroup.getMenuItems().add(uiMenuItem);
			}
			uiMenuGroups.add(uiMenuGroup);
		}
		return uiMenuGroups;
	}

}

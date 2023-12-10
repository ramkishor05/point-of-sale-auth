package com.brijframwork.authorization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframwork.authorization.beans.UIMenuGroup;
import com.brijframwork.authorization.beans.UIMenuItem;
import com.brijframwork.authorization.mapper.MenuGroupMapper;
import com.brijframwork.authorization.model.menus.EOMenuGroup;
import com.brijframwork.authorization.model.menus.EOMenuItem;
import com.brijframwork.authorization.model.menus.EORoleMenuGroup;
import com.brijframwork.authorization.model.menus.EORoleMenuItem;
import com.brijframwork.authorization.repository.MenuGroupRepository;
import com.brijframwork.authorization.repository.RoleMenuGroupRepository;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuGroupRepository menuGroupRepository;
	
	@Autowired
	private RoleMenuGroupRepository userRoleMenuGroupRepository;
	
	@Autowired
	private MenuGroupMapper menuGroupMapper;

	@Override
	public UIMenuGroup addMenuGroup(UIMenuGroup uiMenuGroup) {
		EOMenuGroup eoMenuGroup = menuGroupMapper.mapToDAO(uiMenuGroup);
		eoMenuGroup=menuGroupRepository.save(eoMenuGroup);
		
		return menuGroupMapper.mapToDTO(eoMenuGroup);
	}

	@Override
	public UIMenuGroup updateMenuGroup(UIMenuGroup uiMenuGroup) {
		EOMenuGroup eoMenuGroup = menuGroupMapper.mapToDAO(uiMenuGroup);
		eoMenuGroup=menuGroupRepository.save(eoMenuGroup);
		return menuGroupMapper.mapToDTO(eoMenuGroup);
	}

	@Override
	public boolean deleteMenuGroup(Long id) {
		menuGroupRepository.deleteById(id);
		return true;
	}

	@Override
	public UIMenuGroup getMenuGroup(Long id) {
		return menuGroupMapper.mapToDTO(menuGroupRepository.getOne(id));
	}

	@Override
	public List<UIMenuGroup> getMenuGroupList() {
		return menuGroupMapper.mapToDTO(menuGroupRepository.findAll());
	}

	@Override
	public List<UIMenuGroup> getMenuGroupList(String type) {
		return menuGroupMapper.mapToDTO(menuGroupRepository.findAllByType(type));
	}

	@Override
	public List<UIMenuGroup> getMenuGroupListByRoleId(Long roleId) {
		List<UIMenuGroup> uiMenuGroups=new ArrayList<UIMenuGroup>();
		List<EORoleMenuGroup> eoUserRoleMenuGroups = userRoleMenuGroupRepository.findAllByRoleId(roleId);
		for(EORoleMenuGroup eoUserRoleMenuGroup: eoUserRoleMenuGroups) {
			EOMenuGroup menuGroup = eoUserRoleMenuGroup.getMenuGroup();
			UIMenuGroup uiMenuGroup=new UIMenuGroup();
			uiMenuGroup.setId(eoUserRoleMenuGroup.getId());
			uiMenuGroup.setTitle(menuGroup.getTitle());
			uiMenuGroup.setType(menuGroup.getType());
			uiMenuGroup.setUrl(menuGroup.getUrl());
			uiMenuGroup.setIcon(menuGroup.getIcon());
			uiMenuGroup.setOrder(menuGroup.getOrder());
			List<EORoleMenuItem> userRoleMenuItems = eoUserRoleMenuGroup.getRoleMenuItems();
			for(EORoleMenuItem userRoleMenuItem: userRoleMenuItems) {
				EOMenuItem menuItem = userRoleMenuItem.getMenuItem();
				addMenuItem(uiMenuGroup, userRoleMenuItem, menuItem);
			}
			uiMenuGroup.getMenuItems().sort((o1,o2)->o1.getOrder().compareTo(o2.getOrder()));
			uiMenuGroups.add(uiMenuGroup);
		}
		uiMenuGroups.sort((o1,o2)->o1.getOrder().compareTo(o2.getOrder()));
		return uiMenuGroups;
	}

	private void addMenuItem(UIMenuGroup uiMenuGroup, EORoleMenuItem userRoleMenuItem, EOMenuItem menuItem) {
		UIMenuItem uiMenuItem=new UIMenuItem();
		uiMenuItem.setId(userRoleMenuItem.getId());
		uiMenuItem.setTitle(menuItem.getTitle());
		uiMenuItem.setType(menuItem.getType());
		uiMenuItem.setUrl(menuItem.getUrl());
		uiMenuItem.setIcon(menuItem.getIcon());
		uiMenuItem.setOrder(menuItem.getOrder());
		uiMenuItem.setHomePage(userRoleMenuItem.isHomePage());	
		uiMenuGroup.getMenuItems().add(uiMenuItem);
	}

}

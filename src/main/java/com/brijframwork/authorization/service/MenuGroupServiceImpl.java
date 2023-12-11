package com.brijframwork.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframwork.authorization.beans.UIMenuGroup;
import com.brijframwork.authorization.mapper.MenuGroupMapper;
import com.brijframwork.authorization.model.menus.EOMenuGroup;
import com.brijframwork.authorization.repository.MenuGroupRepository;

@Service
public class MenuGroupServiceImpl implements MenuGroupService {
	
	@Autowired
	private MenuGroupRepository menuGroupRepository;
	
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
}

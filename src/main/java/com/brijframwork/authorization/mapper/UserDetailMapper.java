package com.brijframwork.authorization.mapper;

import static com.brijframwork.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframwork.authorization.contants.Constants.SPRING;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import com.brijframwork.authorization.beans.UIHeaderItem;
import com.brijframwork.authorization.beans.UIMenuGroup;
import com.brijframwork.authorization.beans.UIMenuItem;
import com.brijframwork.authorization.beans.UserDetailResponse;
import com.brijframwork.authorization.beans.UserRoleResponse;
import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.model.EOUserRole;
import com.brijframwork.authorization.model.headers.EOHeaderItem;
import com.brijframwork.authorization.model.headers.EORoleHeaderItem;
import com.brijframwork.authorization.model.menus.EOMenuGroup;
import com.brijframwork.authorization.model.menus.EOMenuItem;
import com.brijframwork.authorization.model.menus.EORoleMenuGroup;
import com.brijframwork.authorization.model.menus.EORoleMenuItem;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface UserDetailMapper extends GenericMapper<EOUserAccount, UserDetailResponse> {

	UserRoleResponse userRole(EOUserRole eoUserRole);
	
	default List<UIMenuGroup> mapRoleMenuGroupsToMenuGroupDTOs(List<EORoleMenuGroup> eoRoleMenuGroupList) {
		List<UIMenuGroup> uiMenuGroups=new ArrayList<UIMenuGroup>();
		for(EORoleMenuGroup eoRoleMenuGroup: eoRoleMenuGroupList) {
			uiMenuGroups.add(mapRoleMenuGroupToMenuGroupDTO(eoRoleMenuGroup));
		}
		uiMenuGroups.sort((m1,m2)->m1.getOrder().compareTo(m2.getOrder()));
		return uiMenuGroups;
	}
	
	default UIMenuGroup mapRoleMenuGroupToMenuGroupDTO(EORoleMenuGroup eoRoleMenuGroup) {
		UIMenuGroup uiMenuGroup = mapToMenuGroupDTO(eoRoleMenuGroup.getMenuGroup());
		uiMenuGroup.setMenuItems(mapRoleMenuItemToMenuItemDTOs(eoRoleMenuGroup.getRoleMenuItems()));
		return uiMenuGroup;
	}
	
	UIMenuGroup mapToMenuGroupDTO(EOMenuGroup eoMenuGroup);
	
	default List<UIMenuItem> mapRoleMenuItemToMenuItemDTOs(List<EORoleMenuItem> eoRoleMenuItemList) {
		List<UIMenuItem> uiMenuItems=new ArrayList<UIMenuItem>();
		for(EORoleMenuItem eoRoleMenuItem: eoRoleMenuItemList) {
			uiMenuItems.add(mapRoleMenuItemToMenuItemDTO(eoRoleMenuItem));
		}
		uiMenuItems.sort((m1,m2)->m1.getOrder().compareTo(m2.getOrder()));
		return uiMenuItems;
	}
	
	default UIMenuItem mapRoleMenuItemToMenuItemDTO(EORoleMenuItem eoRoleMenuItem) {
		UIMenuItem roleEndpoint = mapToMenuItemDTO(eoRoleMenuItem.getMenuItem());
		roleEndpoint.setHomePage(eoRoleMenuItem.isHomePage());
		return roleEndpoint;
	}
	
	UIMenuItem mapToMenuItemDTO(EOMenuItem eoMenuItem);
	
	default List<UIHeaderItem> mapRoleHeaderItemToHeaderItemDTOs(List<EORoleHeaderItem> eoRoleHeaderItemList) {
		List<UIHeaderItem> uiHeaderItems=new ArrayList<UIHeaderItem>();
		for(EORoleHeaderItem eoRoleHeaderItem: eoRoleHeaderItemList) {
			uiHeaderItems.add(mapRoleHeaderItemToHeaderItemDTO(eoRoleHeaderItem));
		}
		uiHeaderItems.sort((m1,m2)->m1.getOrder().compareTo(m2.getOrder()));
		return uiHeaderItems;
	}
	
	default UIHeaderItem mapRoleHeaderItemToHeaderItemDTO(EORoleHeaderItem eoRoleHeaderItem) {
		UIHeaderItem headerItem = mapRoleHeaderItemToHeaderItemDTO(eoRoleHeaderItem.getHeaderItem());
		return headerItem;
	}

	UIHeaderItem mapRoleHeaderItemToHeaderItemDTO(EOHeaderItem eoHeaderItem);
}

package com.brijframwork.authorization.mapper;

import static com.brijframwork.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframwork.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframwork.authorization.beans.UIMenuItem;
import com.brijframwork.authorization.beans.UserDetailResponse;
import com.brijframwork.authorization.beans.UserRoleResponse;
import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.model.EOUserRole;
import com.brijframwork.authorization.model.menus.EOMenuItem;
import com.brijframwork.authorization.model.menus.EORoleMenuItem;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface UserDetailMapper extends GenericMapper<EOUserAccount, UserDetailResponse> {

	UserRoleResponse userRole(EOUserRole eoUserRole);
	
	default UIMenuItem roleEndpoints(EORoleMenuItem eoRoleEndpoint) {
		return roleEndpoint(eoRoleEndpoint.getMenuItem());
	}

	UIMenuItem roleEndpoint(EOMenuItem userEndpoint);
}

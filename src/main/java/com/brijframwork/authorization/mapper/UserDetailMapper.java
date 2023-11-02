package com.brijframwork.authorization.mapper;

import static com.brijframwork.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframwork.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframwork.authorization.beans.UIEndpoint;
import com.brijframwork.authorization.beans.UserDetailResponse;
import com.brijframwork.authorization.beans.UserRoleResponse;
import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.model.EOUserEndpoint;
import com.brijframwork.authorization.model.EOUserRole;
import com.brijframwork.authorization.model.EOUserRoleEndpoint;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface UserDetailMapper extends GenericMapper<EOUserAccount, UserDetailResponse> {

	UserRoleResponse userRole(EOUserRole eoUserRole);
	
	default UIEndpoint roleEndpoints(EOUserRoleEndpoint eoUserRoleEndpoint) {
		return roleEndpoint(eoUserRoleEndpoint.getUserEndpoint());
	}

	UIEndpoint roleEndpoint(EOUserEndpoint userEndpoint);
}

package com.brijframwork.authorization.beans;

import java.io.Serializable;

public class UserRoleResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	
	private int position;
	
	private String roleName;
	
	private String roleId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
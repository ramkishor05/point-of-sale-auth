package com.brijframwork.authorization.constant;

public enum UserRole {

	ADMIN("ADMIN",1, "GLOBAL"), 
	
	CUSTOMER("CUSTOMER",1, "APP"),
	
	SUPPLIER("SUPPLIER",1, "APP"),
	
	VENDOR("VENDOR",1, "APP")
	; 
	
	String role;
	int position;
	String type;
	
	private UserRole(String role, int position,String type) {
		this.role = role;
		this.position = position;
		this.type=type;
	}

	public String getRole() {
		return role;
	}

	public int getPosition() {
		return position;
	}
	
	public String getType() {
		return type;
	}

	UserRole forRoleId(int userRoleId) {
		for(UserRole userRole: values()) {
			if(userRole.getPosition()==userRoleId) {
				return userRole;
			}
		}
		return null;
	}
	
}

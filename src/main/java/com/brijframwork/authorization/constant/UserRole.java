package com.brijframwork.authorization.constant;

public enum UserRole {

	App_Admin("App_Admin",0, "APP"), OWNER("OWNER",1, "APP"), MANAGER("MANAGER",2,"CUST"), SUPERVISOR("SUPERVISOR",3,"CUST"), CREW("CREW",4,"CUST");
	 
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

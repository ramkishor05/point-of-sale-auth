package com.brijframwork.authorization.constant;

public enum UserRole {

	ADMIN("ADMIN",0), OWNER("OWNER",1), MANAGER("MANAGER",2), SUPERVISOR("SUPERVISOR",3), CREW("CREW",4);
	 
	String role;
	int position;
	
	private UserRole(String role, int position) {
		this.role = role;
		this.position = position;
	}

	public String getRole() {
		return role;
	}

	public int getPosition() {
		return position;
	}
	
}

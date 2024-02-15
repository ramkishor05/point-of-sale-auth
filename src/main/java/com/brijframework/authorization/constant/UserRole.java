package com.brijframework.authorization.constant;

public enum UserRole {

	ADMIN("Admin","ADMIN", 1, "GLOBAL"),

	CUSTOMER("Customer","CUSTOMER", 2, "APP"),

	SUPPLIER("Supplier","SUPPLIER", 3, "APP"),

	VENDOR("Vendor","VENDOR", 4, "APP");

	String roleName;
	String roleId;
	int position;
	String roleType;

	private UserRole(String roleName, String roleId, int position, String roleType) {
		this.roleName = roleName;
		this.roleId=roleId;
		this.position = position;
		this.roleType = roleType;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getRoleId() {
		return roleId;
	}

	public int getPosition() {
		return position;
	}

	public String getRoleType() {
		return roleType;
	}

	UserRole forRoleId(int userRoleId) {
		for (UserRole userRole : values()) {
			if (userRole.getPosition() == userRoleId) {
				return userRole;
			}
		}
		return null;
	}

}

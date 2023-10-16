package com.brijframwork.authorization.beans;

public class UserDetailResponse {

	private long id;

	private String username;

	private String accountName;

	private String type;

	private Long ownerId;

	private UserRoleResponse userRole;

	private UserProfileResponse userProfile;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserRoleResponse getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRoleResponse userRole) {
		this.userRole = userRole;
	}

	public UserProfileResponse getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfileResponse userProfile) {
		this.userProfile = userProfile;
	}

}

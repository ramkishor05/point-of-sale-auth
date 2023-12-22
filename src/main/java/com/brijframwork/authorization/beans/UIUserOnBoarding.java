package com.brijframwork.authorization.beans;

public class UIUserOnBoarding {

	private long id;
	
	private UIMenuItem roleMenuItem;
	
	private Boolean onBoardingStatus;

	private Integer onBoardingLevel;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UIMenuItem getRoleMenuItem() {
		return roleMenuItem;
	}

	public void setRoleMenuItem(UIMenuItem roleMenuItem) {
		this.roleMenuItem = roleMenuItem;
	}

	public Boolean getOnBoardingStatus() {
		return onBoardingStatus;
	}

	public void setOnBoardingStatus(Boolean onBoardingStatus) {
		this.onBoardingStatus = onBoardingStatus;
	}
	
	public Integer getOnBoardingLevel() {
		return onBoardingLevel;
	}

	public void setOnBoardingLevel(Integer onBoardingLevel) {
		this.onBoardingLevel = onBoardingLevel;
	}
	
}

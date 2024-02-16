package com.brijframework.authorization.model.onboarding;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.brijframework.authorization.model.EOEntityObject;
import com.brijframework.authorization.model.EOUserAccount;
import com.brijframework.authorization.model.menus.EORoleMenuItem;

@Entity
@Table(name = "USER_ONBOARDING", uniqueConstraints= {@UniqueConstraint(columnNames = { "USER_ACCOUNT_ID", "ROLE_MENU_ITEM_ID" })})
public class EOUserOnBoarding  extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JoinColumn(name = "USER_ACCOUNT_ID")
	@ManyToOne
	private EOUserAccount userAccount;

	@JoinColumn(name = "ROLE_MENU_ITEM_ID")
	@ManyToOne
	private EORoleMenuItem roleMenuItem;
		
	@Column(name = "ON_BOARDING_STATUS")
	private Boolean onBoardingStatus;
	
	@Column(name = "ON_BOARDING_LEVEL", nullable = true)
	private Integer onBoardingLevel;
	
	@PrePersist
	public void init() {
		onBoardingStatus=false;
	}

	public EOUserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(EOUserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public EORoleMenuItem getRoleMenuItem() {
		return roleMenuItem;
	}

	public void setRoleMenuItem(EORoleMenuItem roleMenuItem) {
		this.roleMenuItem = roleMenuItem;
	}

	public Boolean getOnBoardingStatus() {
		if(onBoardingStatus==null) {
			return false;
		}
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

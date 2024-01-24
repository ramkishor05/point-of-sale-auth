
package com.brijframwork.authorization.model.menus;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.brijframwork.authorization.model.EOEntityObject;
import com.brijframwork.authorization.model.EOUserRole;
import com.brijframwork.authorization.model.onboarding.EOUserOnBoarding;

@Entity
@Table(name = "ROLE_MENU_ITEM", uniqueConstraints= {
		@UniqueConstraint(columnNames = { "USER_ROLE_ID","MENU_ITEM_ID" }),
		@UniqueConstraint(columnNames = { "IDEN_NO" }) })
public class EORoleMenuItem extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Column(name = "IDEN_NO")
	private String idenNo;
	
	@Column(name = "OWNER_ID", nullable = true)
	private Long ownerId;

	@OneToOne
	@JoinColumn(name = "USER_ROLE_ID")
	private EOUserRole userRole;

	@OneToOne
	@JoinColumn(name = "MENU_ITEM_ID")
	private EOMenuItem menuItem;
	
	@OneToOne
	@JoinColumn(name = "MENU_GROUP_ID")
	private EORoleMenuGroup  roleMenuGroup;
	
	@Column(name = "IS_HOME_PAGE")
	private boolean homePage=false;

	@Column(name = "ON_BOARDING", nullable = true)
	private Boolean onBoarding;
	
	@Column(name = "ON_BOARDING_LEVEL", nullable = true)
	private Integer onBoardingLevel;
	
	@OneToMany(mappedBy = "roleMenuItem", cascade = CascadeType.ALL)
	private List<EOUserOnBoarding> userOnBoardingList;
	
	public String getIdenNo() {
		return idenNo;
	}

	public void setIdenNo(String idenNo) {
		this.idenNo = idenNo;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public EOUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(EOUserRole userRole) {
		this.userRole = userRole;
	}

	public EOMenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(EOMenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public EORoleMenuGroup getRoleMenuGroup() {
		return roleMenuGroup;
	}

	public void setRoleMenuGroup(EORoleMenuGroup roleMenuGroup) {
		this.roleMenuGroup = roleMenuGroup;
	}

	public boolean isHomePage() {
		return homePage;
	}

	public void setHomePage(boolean homePage) {
		this.homePage = homePage;
	}

	public Boolean getOnBoarding() {
		if(onBoarding==null) {
			return false;
		}
		return onBoarding;
	}

	public void setOnBoarding(Boolean onBoarding) {
		this.onBoarding = onBoarding;
	}

	public Integer getOnBoardingLevel() {
		return onBoardingLevel;
	}

	public void setOnBoardingLevel(Integer onBoardingLevel) {
		this.onBoardingLevel = onBoardingLevel;
	}

	public List<EOUserOnBoarding> getUserOnBoardingList() {
		return userOnBoardingList;
	}

	public void setUserOnBoardingList(List<EOUserOnBoarding> userOnBoardingList) {
		this.userOnBoardingList = userOnBoardingList;
	}

	@Override
	public String toString() {
		return "EOUserRoleMenuItem [id=" + getId() + ", ownerId=" + ownerId + ", userRole=" + userRole + ", menuItem="
				+ menuItem + ", roleMenuGroup=" + roleMenuGroup + "]";
	}
	
	
}

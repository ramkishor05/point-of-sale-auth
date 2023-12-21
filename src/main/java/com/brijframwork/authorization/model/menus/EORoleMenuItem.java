
package com.brijframwork.authorization.model.menus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.brijframwork.authorization.model.EOEntityObject;
import com.brijframwork.authorization.model.EOUserRole;

@Entity
@Table(name = "ROLE_MENU_ITEM", uniqueConstraints= {@UniqueConstraint(columnNames = { "USER_ROLE_ID","MENU_ITEM_ID" })})
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

	@Override
	public String toString() {
		return "EOUserRoleMenuItem [id=" + getId() + ", ownerId=" + ownerId + ", userRole=" + userRole + ", menuItem="
				+ menuItem + ", roleMenuGroup=" + roleMenuGroup + "]";
	}
	
	
}

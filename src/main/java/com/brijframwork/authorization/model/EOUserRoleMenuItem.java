
package com.brijframwork.authorization.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "USER_ROLE_MENU_ITEM", uniqueConstraints= {@UniqueConstraint(columnNames = { "USER_ROLE_ID","MENU_ITEM_ID" })})
public class EOUserRoleMenuItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;
	
	@Column(name = "OWNER_ID", nullable = true)
	private Long ownerId;

	@OneToOne
	@JoinColumn(name = "USER_ROLE_ID")
	private EOUserRole userRole;

	@OneToOne
	@JoinColumn(name = "MENU_ITEM_ID")
	private EOGlobalMenuItem menuItem;
	
	@OneToOne
	@JoinColumn(name = "USER_ROLE_MENU_GROUP_ID")
	private EOUserRoleMenuGroup  userRoleMenuGroup;

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

	public EOUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(EOUserRole userRole) {
		this.userRole = userRole;
	}

	public EOGlobalMenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(EOGlobalMenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public EOUserRoleMenuGroup getUserRoleMenuGroup() {
		return userRoleMenuGroup;
	}

	public void setUserRoleMenuGroup(EOUserRoleMenuGroup userRoleMenuGroup) {
		this.userRoleMenuGroup = userRoleMenuGroup;
	}

	@Override
	public String toString() {
		return "EOUserRoleMenuItem [id=" + id + ", ownerId=" + ownerId + ", userRole=" + userRole + ", menuItem="
				+ menuItem + ", userRoleMenuGroup=" + userRoleMenuGroup + "]";
	}
	
	
}

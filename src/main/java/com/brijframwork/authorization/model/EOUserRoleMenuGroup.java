
package com.brijframwork.authorization.model;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "USER_ROLE_MENU_GROUP", uniqueConstraints= {@UniqueConstraint(columnNames = { "USER_ROLE_ID","MENU_GROUP_ID"})})
public class EOUserRoleMenuGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;
	
	@OneToOne
	@JoinColumn(name = "USER_ROLE_ID")
	private EOUserRole userRole;
	
	@OneToOne
	@JoinColumn(name = "MENU_GROUP_ID")
	private EOGlobalMenuGroup menuGroup;

	@OneToMany(mappedBy = "userRoleMenuGroup")
	private List<EOUserRoleMenuItem> userRoleMenuItems;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public EOUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(EOUserRole userRole) {
		this.userRole = userRole;
	}

	public EOGlobalMenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(EOGlobalMenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}

	public List<EOUserRoleMenuItem> getUserRoleMenuItems() {
		return userRoleMenuItems;
	}

	public void setUserRoleMenuItems(List<EOUserRoleMenuItem> userRoleMenuItems) {
		this.userRoleMenuItems = userRoleMenuItems;
	}
}

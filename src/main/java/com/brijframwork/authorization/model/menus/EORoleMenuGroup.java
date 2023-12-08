
package com.brijframwork.authorization.model.menus;

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

import com.brijframwork.authorization.model.EOUserRole;

@Entity
@Table(name = "ROLE_MENU_GROUP", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "USER_ROLE_ID", "MENU_GROUP_ID" }) })
public class EORoleMenuGroup implements Serializable {

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
	private EOMenuGroup menuGroup;

	@OneToMany(mappedBy = "roleMenuGroup")
	private List<EORoleMenuItem> roleMenuItems;
	

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

	public EOMenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(EOMenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}

	public List<EORoleMenuItem> getRoleMenuItems() {
		return roleMenuItems;
	}

	public void setRoleMenuItems(List<EORoleMenuItem> roleMenuItems) {
		this.roleMenuItems = roleMenuItems;
	}

	@Override
	public String toString() {
		return "EORoleMenuGroup [id=" + id + ", userRole=" + userRole + ", menuGroup=" + menuGroup + ", roleMenuItems="
				+ roleMenuItems + "]";
	}

	
}

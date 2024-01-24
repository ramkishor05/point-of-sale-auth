
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

@Entity
@Table(name = "ROLE_MENU_GROUP", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "USER_ROLE_ID", "MENU_GROUP_ID" }) ,
		@UniqueConstraint(columnNames = { "IDEN_NO" }) })
public class EORoleMenuGroup extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "IDEN_NO")
	private String idenNo;

	@OneToOne
	@JoinColumn(name = "USER_ROLE_ID")
	private EOUserRole userRole;

	@OneToOne
	@JoinColumn(name = "MENU_GROUP_ID")
	private EOMenuGroup menuGroup;

	@OneToMany(mappedBy = "roleMenuGroup", cascade = CascadeType.ALL)
	private List<EORoleMenuItem> roleMenuItems;
		
	public String getIdenNo() {
		return idenNo;
	}

	public void setIdenNo(String idenNo) {
		this.idenNo = idenNo;
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
		return "EORoleMenuGroup [id=" + getId() + ", userRole=" + userRole + ", menuGroup=" + menuGroup + "";
	}

	
}

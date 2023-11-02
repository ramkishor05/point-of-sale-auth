
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
@Table(name = "USER_ROLE_ENDPOINT", uniqueConstraints= {@UniqueConstraint(columnNames = { "ROLE_ID","ENDPOINT_ID" })})
public class EOUserRoleEndpoint implements Serializable {

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
	@JoinColumn(name = "ROLE_ID")
	private EOUserRole userRole;

	@OneToOne
	@JoinColumn(name = "ENDPOINT_ID")
	private EOUserEndpoint userEndpoint;

	public long getId() {
		return id;
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

	public EOUserEndpoint getUserEndpoint() {
		return userEndpoint;
	}

	public void setUserEndpoint(EOUserEndpoint userEndpoint) {
		this.userEndpoint = userEndpoint;
	}

	public void setId(long id) {
		this.id = id;
	}
}

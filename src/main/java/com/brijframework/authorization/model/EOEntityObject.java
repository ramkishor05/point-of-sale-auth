package com.brijframework.authorization.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class EOEntityObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@org.springframework.data.annotation.Id
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CREATED_UID")
	private String createdUid;
	
	@Column(name = "CREATED_AT")
	@CreationTimestamp
	private Date createdAt;
	
	@Column(name = "UPDATED_UID")
	private String updatedUid;

	@Column(name = "UPDATED_AT")
	@UpdateTimestamp
	private Date updatedAt;
	
	@Column(name = "RECORD_STATUS")
	private Boolean recordState;
	
	@Column(name = "ORDER_SEQUENCE")
	private Float orderSequence;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedUid() {
		return createdUid;
	}

	public void setCreatedUid(String createdUid) {
		this.createdUid = createdUid;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedUid() {
		return updatedUid;
	}

	public void setUpdatedUid(String updatedUid) {
		this.updatedUid = updatedUid;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean getRecordState() {
		if(recordState==null) {
			recordState=false;
		}
		return recordState;
	}

	public void setRecordState(Boolean recordState) {
		this.recordState = recordState;
	}

	public Float getOrderSequence() {
		return orderSequence;
	}

	public void setOrderSequence(Float orderSequence) {
		this.orderSequence = orderSequence;
	}
	
}

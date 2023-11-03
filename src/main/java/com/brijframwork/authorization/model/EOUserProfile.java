package com.brijframwork.authorization.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_PROFILE")
public class EOUserProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "FULL_NAME")
	private String fullName;
	
	@Column(name = "PREFERRED_NAME")
	private String preferredName;
	
	@Column(name = "PIC_URL")
	@Lob
	private String pictureURL;
	

	@OneToOne(mappedBy = "userProfile")
	private EOUserAccount userAccount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPreferredName() {
		return preferredName;
	}

	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public EOUserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(EOUserAccount userAccount) {
		this.userAccount = userAccount;
	}

}

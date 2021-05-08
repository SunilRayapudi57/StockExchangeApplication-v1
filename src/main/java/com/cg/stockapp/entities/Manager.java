package com.cg.stockapp.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

@Entity
@Table(name = "manager")
public class Manager {

	@Id
	@Column(name = "MANAGER_ID")
	private String managerId;

//	@NotNull
//	@Length(min = 5, max = 20, message = "Username should be from 5 to 20 characters")
	@Column(name = "NAME")
	private String name;

	@Column(name = "GENDER")
	private String gender;

	// @NotNull
//	@Pattern(regexp = "^[A-Za-z0-9._]+@[A-za-z0-9]+[.][A-za-z]{2,5}$", message = "Email is not valid")
	@Column(name = "EMAIL")
	private String email;

//	@Pattern(regexp = "[0-9]{10}", message = "Invalid mobile number")
	@Column(name = "MOBILE_NUM")
	private String mobileNum;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "companyId")
	private Company company;

	public Manager(String managerId, String name, String email, String gender, String mobileNum) {
		super();
		this.managerId = managerId;
		this.name = name;
		this.email = email;
		this.mobileNum = mobileNum;
		this.gender = gender;
//		this.company = company;
	}

	public Manager() {
		super();
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}


package com.cg.stockapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class DAOUser {

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private long userId;
	
	@NotNull
	@Length(min = 5, max = 20, message = "Username should be from 5 to 20 characters")
	@Pattern(regexp = "^[A-Za-z]+[A-Za-z_0-9]*$", message = "Username should not contain any special characters")
	@Column(name = "USERNAME")
	private String username;
	
	@NotNull
	@Length(min = 7, max = 20, message = "Password should be from 7 to 25 characters")
	@Column(name = "PASSWORD")
	private String password;
	
	@NotNull
	@Column(name = "ROLE")
	private String role;// Admin or investor or manager
	
	@Column
	@JsonIgnore
	private boolean isLoggedIn;
	
	public DAOUser(long userId, String username, String password, String role, boolean isLoggedIn) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.role = role;
		this.isLoggedIn = isLoggedIn;
	}
	public DAOUser() {
		super();
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", role=" + role + "]";
	}
}
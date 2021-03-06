package com.cg.stockapp.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

@Entity
@Table(name="investor")
public class Investor {
	
	@Id
	@Column(name = "INVESTOR_ID")
	private String investorId;
	
//	@NotNull
//	@Range(min = 5, max = 20, message = "Username should be from 5 to 20 characters")
	@Column(name = "NAME")
	private String name;
	
//	@Pattern(regexp = "^[A-Za-z0-9._]+@[A-za-z0-9]+[.][A-za-z]{2,5}$", message = "Email is not valid")
	@Column(name = "EMAIL")
	private String email;
	
//	@NotNull
//	@Pattern(regexp = "[A-Za-z]+[0-9]+[!@#$%^&*]+", message = "Password should atleast contain a digit and a special character")
//	@Length(min = 7, max = 20, message = "Username should be from 7 to 20 characters")
//	@Column(name = "PASSWORD")
//	private String password;
	
//	@NotNull
//	@Pattern(regexp = "[0-9]{10}", message = "Invalid Mobile number")
	@Column(name = "MOBILE_NO")
	private String mobileNo;
	
//	@Pattern(regexp = "(male|female)", message = "Invalid Gender")
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "STATUS")
	private String status; // approved or pending
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="accountNo")
	private BankAccount account;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "investors", fetch = FetchType.LAZY)
	private List<Stock> stocks = new ArrayList<>();
	
	public Investor(String investorId, String name, String email, String mobileNo,
			String gender) {
		super();
		this.investorId = investorId;
		this.name = name;
		this.email = email;
		this.mobileNo = mobileNo;
		this.gender = gender;
	}

	public Investor() {
		super();
	}
	
	public void addStock(Stock stock) {
		this.stocks.add(stock);
	}
	public void removeStock(Stock stock) {
		this.stocks.remove(stock);
	}

	
	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public String getInvestorId() {
		return investorId;
	}
	public void setInvestorId(String investorId) {
		this.investorId = investorId;
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
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public BankAccount getAccount() {
		return account;
	}
	public void setAccount(BankAccount account) {
		this.account = account;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
}

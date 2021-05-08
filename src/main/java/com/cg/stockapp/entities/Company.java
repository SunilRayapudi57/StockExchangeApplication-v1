
package com.cg.stockapp.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "company")
public class Company {

	@Id
	@Column(name = "COMPANY_ID")
	private String companyId;

	@Column(name = "COMPANY_NAME")
//	@Range(min = 4, max = 25)
	private String companyName;
	
	@Column(name = "MANAGER_ID")
	private String managerId;

	@Column(name = "CATEGORY")
	private String category;
	
	@JsonIgnore
	@Column(name = "NO_OF_STOCKS")
	private int noOfStocks;

	@JsonIgnore
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<Stock> stocks = new ArrayList<>();

	public Company(String companyId, String companyName, String managerId, String category) {
		super();
		this.companyId = companyId;
		this.companyName = companyName;
		this.managerId = managerId;
		this.category = category;
	}

	public Company() {
		super();
	}

	public void addStock(Stock stock) {
		this.stocks.add(stock);
	}
	public void removeStock(Stock stock) {
		this.stocks.remove(stock);
	}
	
	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public int getNoOfStocks() {
		return noOfStocks;
	}

	public void setNoOfStocks(int noOfStocks) {
		this.noOfStocks = noOfStocks;
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", companyName=" + companyName + ", category=" + category + "]";
	}

}
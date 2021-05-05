package com.cg.stockapp.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sun.istack.NotNull;

@Entity
@Table(name = "stock")
public class Stock {

	@Id
	@Column(name = "STOCK_ID")
	private String stockId;

//	@NotNull
	@Column(name = "STOCK_NAME")
	private String stockName;

//	@NotNull
//	@JsonValue
	@Column(name = "STOCK_PRICE")
	private double stockPrice;

//	@NotNull
	@Column(name = "QUANTITY")
	private int quantity;

//	@NotNull
	@Column(name = "TYPE")
	private String type;

	@Transient
	@Column(name = "STOCK_TOTAL")
	private double stockTotal;

	@Column(name = "PROFIT_LOSS")
	private double profitLoss;

	@JsonIgnore
	@Column(name = "STATUS")
	private String status = "active"; // active or non-Active

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "Stock_Investor",
				joinColumns = @JoinColumn(name = "stock_id"),
				inverseJoinColumns = @JoinColumn(name="investor_id"))
	private List<Investor> investors = new ArrayList<>();

	public Stock(String stockId, String stockName, int quantity, String type, double stockPrice, Company company) {
		super();
		this.stockId = stockId;
		this.stockName = stockName;
		this.quantity = quantity;
		this.type = type;
		this.stockPrice = stockPrice;
		this.company = company;
		this.stockTotal = this.quantity*this.stockPrice;
	}

	public Stock() {
		super();
	}
	
	public void addInvestor(Investor investor) {
		this.investors.add(investor);
	}
	public void removeInvestor(Investor investor) {
		this.investors.remove(investor);
	}
	
	public double getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(double stockPrice) {
		this.stockPrice = stockPrice;
	}

	public double getStockTotal() {
		return stockTotal;
	}

	public void setStockTotal(double stockTotal) {
		this.stockTotal = stockTotal;
	}

	public List<Investor> getInvestors() {
		return investors;
	}

	public void setInvestors(List<Investor> investors) {
		this.investors = investors;
	}


	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getProfitLoss() {
		return profitLoss;
	}

	public void setProfitLoss(double profitLoss) {
		this.profitLoss = profitLoss;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
package com.cg.stockapp.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.stockapp.entities.Company;
import com.cg.stockapp.entities.Investor;
import com.cg.stockapp.entities.Stock;
import com.cg.stockapp.exceptions.CompanyNotFoundException;
import com.cg.stockapp.exceptions.DuplicateStockException;
import com.cg.stockapp.exceptions.EmptyTableException;
import com.cg.stockapp.exceptions.InvestorNotFoundException;
import com.cg.stockapp.exceptions.StockNotFoundException;
import com.cg.stockapp.repository.CompanyRepository;
import com.cg.stockapp.repository.InvestorRepository;
import com.cg.stockapp.repository.StockRepository;

@Service
public class StockService implements IStockService {

	@Autowired
	StockRepository repo;
	
	@Autowired
	InvestorRepository investorRepo;
	
	@Autowired
	CompanyRepository companyRepo;
	
	Logger log = LoggerFactory.getLogger(StockService.class);

	//Add the Stock
	@Override
	public boolean addStock(Stock stock) {
		log.info("addStock() has been invoked");
		Company company = stock.getCompany();
		String companyId = company.getCompanyId();
		if(!companyRepo.existsById(companyId)) {
			log.warn("CompanyNotFoundException : Creation failed, compnay not found with id "+companyId);
			throw new CompanyNotFoundException("Creation", "Company not found with id "+companyId);
		}
		String stockId = stock.getStockId();
		if (repo.existsById(stockId)) {
			log.warn("DuplicateStockException : Stock already exists with id "+ stockId);
			throw new DuplicateStockException("Stock already exists with id " + stockId);
		}
		else {
			int noOfStocks = companyRepo.findById(companyId).get().getNoOfStocks();
			company.setNoOfStocks(noOfStocks+1);
			company.addStock(stock);
			companyRepo.save(company);
			repo.save(stock);
			log.info("A new Stock has been added for the company "+stock.getCompany().getCompanyName());
			return true;
		}
	}
	
	//Remove the Stock
		@Override
		public boolean removeStockDetails(String stockId) {
			log.info("removeStock() has been invoked");
			
			if (repo.existsById(stockId)) {
				Stock stock = repo.findById(stockId).get();
				Company company = stock.getCompany();
				int noOfStocks = companyRepo.findById(company.getCompanyId()).get().getNoOfStocks();				
				company.setNoOfStocks(noOfStocks-1);
				company.removeStock(stock);
				companyRepo.save(company);
				repo.deleteById(stockId);
				log.info("A stock with id "+stockId+" has been removed");
				return true;
			}
			else {
				log.warn("StockNotFoundException : Delete failed, Stock not found with id "+stockId);
				throw new StockNotFoundException("Delete", "Stock not found with id "+stockId);
			}
		}

	//Display all the Stocks
	@Override
	public List<Stock> viewAllStock() {
		log.info("viewAllStock() has been invoked");
		if (repo.findAll().isEmpty()) {
			log.warn("EmptyTableException : No data found in the database");
			throw new EmptyTableException("No Data Found in the database");
		}
		else {
			log.info("All stocks returned");
			return repo.findAll();
		}
	}

	//Update Stock details
	@Override
	public boolean updateStockDetails(Stock stock) {
		log.info("updateStock() has been invoked");
		String companyId = stock.getCompany().getCompanyId();
		if(!companyRepo.existsById(companyId)) {
			log.warn("CompanyNotFoundException : Update failed, compnay not found with id "+companyId);
			throw new CompanyNotFoundException("Update", "Company not found with id "+companyId);
		}
		if (repo.existsById(stock.getStockId())) {
			repo.save(stock);
			log.info("A stock with id "+stock.getStockId()+" has been updated");
			return true;
		}
		else {
			log.warn("StockNotFoundException : Update failed, Stock not found with id "+stock.getStockId());
			throw new StockNotFoundException("Update", "Stock not found with id "+stock.getStockId());
		}
	}

	//Display the Stock details
	@Override
	public Stock viewStockDetails(String stockId) {
		log.info("viewStockDetails() has been invoked");
		if(repo.existsById(stockId)) {
			log.info("A stock has been returned with id "+stockId);
			return repo.findById(stockId).get();
		}
		else {
			log.warn("StockNotFoundException : Request failed, stock not found with id "+stockId);
			throw new StockNotFoundException("Request", "Stock not found with id "+stockId);
		}
	}

	//Display the Stock by Company
	@Override
	public List<Stock> viewStockByCompany(String companyName) {
		
		log.info("viewStockByCompany() has been invoked");
		if(!companyRepo.existsByCompanyName(companyName)) {
			log.warn("CompanyNotFoundException : Request failed, campany not found with name "+companyName);
			throw new CompanyNotFoundException("Request", "Company not found with name "+companyName);
		}
		
		Company company = companyRepo.findByCompanyName(companyName);
		List<Stock> stockList = company.getStocks();
		
		if(stockList.isEmpty()) {
			log.warn("StockNotFoundException : Rquest failed, no stock found for the company "+companyName);
			throw new StockNotFoundException("Request", "No stock found for the company "+companyName);
		}
		else {
			log.info("All stock returned for the company "+companyName);
			return stockList;
		}
		
	}
	
	//Display all the Stocks by Investor
	public List<Stock> viewStockAllByInvestor(String investorId){
		log.info("viewAllInvestor() has been invoked");
		if(!investorRepo.existsById(investorId)) {
			throw new InvestorNotFoundException("Request", "Investor not found with id "+investorId);
		}
		
		List<Stock> stockList = investorRepo.findById(investorId).get().getStocks();
		
		if(stockList.isEmpty()) {
			throw new StockNotFoundException("Request", "No stock found for the the investor with id "+investorId);
		}
		
		return stockList.stream().distinct().collect(Collectors.toList());
	}

}

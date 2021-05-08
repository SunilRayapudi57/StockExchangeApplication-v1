package com.cg.stockapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.stockapp.entities.Company;
import com.cg.stockapp.service.ICompanyService;
import com.cg.stockapp.service.JwtUserDetailsService;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {

	@Autowired
	ICompanyService serv;

	@Autowired
	JwtUserDetailsService js;

	@PostMapping
	public String addCompany(@RequestBody Company company) {
		serv.addCompany(company);
		return "Company added successfully";
	}

	@GetMapping("{companyId}")
	public Company getCompanyInfo(@PathVariable("companyId") String companyId) {
		return serv.getCompanyInfo(companyId);
	}

	@GetMapping()
	public List<Company> getAllCompanyInfo() {
		return serv.getAllCompanyInfo();
	}

	@PutMapping
	public String updateCompany(@RequestBody Company company) {
		serv.updateCompany(company);
		return "Company with id " + company.getCompanyId();
	}

	@DeleteMapping("{companyId}")
	public String deleteCompany(@PathVariable("companyId") String companyId,
			@RequestHeader("Authorization") String token) {
		String role = js.getRoleFromToken(token);
		if (role.equals("Admin")) {
			serv.deleteCompany(companyId);
			return "Company with id " + companyId + " has been deleted successfully";
		}
		else {
			return "You are not authorized to perform this operation !";
		}

	}

}

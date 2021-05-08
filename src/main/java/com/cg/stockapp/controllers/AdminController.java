package com.cg.stockapp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.stockapp.entities.Admin;
import com.cg.stockapp.service.IAdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
	
	@Autowired
	IAdminService serv;
	
	@GetMapping("{adminId}")
	public Admin viewAdmin(@PathVariable("adminId") String adminId) {
		return serv.viewAdmin(adminId);
	}
	
	@PutMapping
	public ResponseEntity<?> updateAdmin(@Valid @RequestBody Admin admin, BindingResult result) {
		if(result.hasErrors())
			return new ResponseEntity<List<String>>(result.getFieldErrors().stream()
														   .map(FieldError::getDefaultMessage)
														   	.collect(Collectors.toList()),HttpStatus.OK);
		serv.updateAdmin(admin);
		return new ResponseEntity<String>("Admin with id "+admin.getAdminId()+" updated successfully",HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> addAdmin(@Valid @RequestBody Admin admin,BindingResult result) {
		if(result.hasErrors())
			return new ResponseEntity<List<String>>(result.getFieldErrors().stream()
					   .map(FieldError::getDefaultMessage)
					   	.collect(Collectors.toList()),HttpStatus.BAD_REQUEST);
		serv.addAdmin(admin);
		return new ResponseEntity<>("Admin added successfully",HttpStatus.OK);
	}
	
	@DeleteMapping("{adminId}")
	public String removeAdmin(@PathVariable("adminId") String adminId) {
		serv.removeAdmin(adminId);
		return "Admin with id "+adminId+" has been removed successfully";
	}
	
}

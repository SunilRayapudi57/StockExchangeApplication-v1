package com.cg.stockapp.controllers;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.stockapp.entities.DAOUser;
import com.cg.stockapp.service.IUserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	IUserService serv;

	@PostMapping
	public ResponseEntity<?> addUser(@Valid @RequestBody DAOUser user, BindingResult result) {

		if (result.hasErrors()) {
			List<String> errorList = result.getFieldErrors().stream()
											.map(FieldError::getDefaultMessage)
										    .collect(Collectors.toList());
			Map<String, Object> errorBody = new LinkedHashMap<>();
			errorBody.put("errors", errorList);
			errorBody.put("Timestamp", LocalDateTime.now());
			errorBody.put("details", "Data recieved is not valid");
			return new ResponseEntity<Object>(errorBody, HttpStatus.BAD_REQUEST);
		}

		serv.addUser(user);
		return new ResponseEntity<>("A new user has been added successfully", HttpStatus.OK);
	}

	@DeleteMapping("/remove/{userId}")
	public String removeUser(@PathVariable("userId") long userId) {
		serv.removeUser(userId);
		return "User with id " + userId + " has been deleted successfully";
	}

	@PutMapping
	public ResponseEntity<?> updateUser(@Valid @RequestBody DAOUser user, BindingResult result) {

		if (result.hasErrors()) {
			List<String> errorList = result.getFieldErrors().stream()
											.map(FieldError::getDefaultMessage)
											.collect(Collectors.toList());
			Map<String, Object> errorBody = new LinkedHashMap<>();
			errorBody.put("errors", errorList);
			errorBody.put("Timestamp", LocalDateTime.now());
			errorBody.put("details", "Data recieved is not valid");
			return new ResponseEntity<Object>(errorBody, HttpStatus.BAD_REQUEST);
		}
		serv.updateUser(user);
		return new ResponseEntity<>("A new user has been added successfully", HttpStatus.OK);
	}

}

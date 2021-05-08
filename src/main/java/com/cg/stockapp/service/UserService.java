package com.cg.stockapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.stockapp.entities.DAOUser;
import com.cg.stockapp.exceptions.DuplicateUserException;
import com.cg.stockapp.exceptions.UserNotFoundException;
import com.cg.stockapp.repository.UserDao;

@Service
public class UserService implements IUserService{

	@Autowired
	UserDao repo;
	
	Logger log = LoggerFactory.getLogger(UserService.class);
	
	//Add the User
	@Override
	public boolean addUser(DAOUser user) {
		log.info("addUser() invoked");
		if(repo.existsById(user.getUserId())) {
			log.warn("DuplicateUserException : Creation Failed, User already exists with id "+user.getUserId());
			throw new DuplicateUserException("User already exists with id "+user.getUserId());
		} 
		else {
			user.setLoggedIn(false);
			repo.save(user);
			log.info("A new user has been added successfully");
			return true;
		}
	}

	//Remove the User
	@Override
	public boolean removeUser(long userId) {
		log.info("removeUser() invoked");
		if(repo.existsById(userId)) {
			repo.deleteById(userId);
			log.info("User with id "+userId+" removed");
			return true;
		}
		else {
			log.warn("UserNotFoundException thrown...Delete Failed, User not found with id "+userId);
			throw new UserNotFoundException("Delete","User not found with id "+userId);
		}
	}

	//Update the User
	@Override
	public boolean updateUser(DAOUser user) {
		log.info("updateUser() invoked");
		if(repo.existsById(user.getUserId())) {
			repo.save(user);
			log.info("User with id "+user.getUserId()+" updated");
			return true;
		}
		else {
			log.warn("UserNotFoundException thrown...Delete Failed, User not found with id "+user.getUsername());
			throw new UserNotFoundException("Update","User not found with id "+user.getUserId());
		}
	}

}

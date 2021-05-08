package com.cg.stockapp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.stockapp.config.JwtTokenUtil;
import com.cg.stockapp.entities.DAOUser;
import com.cg.stockapp.exceptions.UserNotFoundException;
import com.cg.stockapp.model.PasswordDTO;
import com.cg.stockapp.model.UserDTO;
import com.cg.stockapp.model.UserdetailsResponse;
import com.cg.stockapp.repository.UserDao;


@Service()
public class JwtUserDetailsService implements UserDetailsService,IJwtUserDetailsService {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DAOUser user = userDao.findByUsername(username);
		
		/*DAOUser user = new DAOUser();
		user.setUsername("vector");
		user.setPassword(bcryptEncoder.encode("pass"));
		if(!username.equals(user.getUsername())) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}*/
		
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
		return new User(user.getUsername(), bcryptEncoder.encode(user.getPassword()),
				new ArrayList<>());
	}
	
	@Override
	public DAOUser save(UserDTO user) {
		DAOUser newUser = new DAOUser();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userDao.save(newUser);
	}
	
	@Override
	public void setLoggedIn(String username) {
		DAOUser user = userDao.findByUsername(username);
		if(user == null) {
			throw new UserNotFoundException("Login","Username: '"+username+"' not found!");
		}
		user.setLoggedIn(true);
		userDao.save(user);
	}
	
	@Override
	public boolean logOut(String userName) {
		DAOUser existUser = userDao.findByUsername(userName);
		
		if(existUser == null) {
			throw new UserNotFoundException("Logout", "Username: '"+userName+"' not found!");
		}
		
		if(existUser.isLoggedIn()) {
			existUser.setLoggedIn(false);
			userDao.save(existUser);
			
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean changePassword(String userName, PasswordDTO passwordDTO) throws UsernameNotFoundException {
		DAOUser user = userDao.findByUsername(userName);
		if(user == null) {
			throw new UsernameNotFoundException("User not found with username: " + userName);
		}
		
		/*
		if(bcryptEncoder.matches(passwordDTO.getOldPassword(), user.getPassword()) && user.isLoggedIn()) {
			user.setPassword(bcryptEncoder.encode(passwordDTO.getNewPassword()));
			userDao.save(user);
			
			return true;
		}
		*/
		
		if(user.getPassword().equals(passwordDTO.getOldPassword()) && user.isLoggedIn()) {
			user.setPassword(passwordDTO.getNewPassword());
			userDao.save(user);
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public String getUserNameToken(String token) {
		String jwtToken = token.substring(7);
		String userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
		return userName;
	}
	
	
	@Override
	public String getRoleFromToken(String token) throws UsernameNotFoundException {
		String jwtToken = token.substring(7);
		String userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
		DAOUser user = userDao.findByUsername(userName);
		
		if(user == null) {
			throw new UsernameNotFoundException("User not found with username: " + userName);
		}
		
		return user.getRole();
	}
	
	@Override
	public UserdetailsResponse getUserDetailsFromToken(String token) throws UsernameNotFoundException {
		String jwtToken = token.substring(7);
		String userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
		DAOUser user = userDao.findByUsername(userName);
		
		if(user == null) {
			throw new UsernameNotFoundException("User not found with username: " + userName);
		}
		
		return toUserDetailsResponse(user);
	}
	
	@Override
	public UserdetailsResponse toUserDetailsResponse(DAOUser user) {
		
		UserdetailsResponse response = new UserdetailsResponse();
		
		response.setUsername(user.getUsername());
		response.setPassword(user.getPassword());
		response.setRole(user.getRole());
		
		return response;
	}
}
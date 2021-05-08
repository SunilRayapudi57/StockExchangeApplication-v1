package com.cg.stockapp.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cg.stockapp.entities.DAOUser;
import com.cg.stockapp.model.PasswordDTO;
import com.cg.stockapp.model.UserDTO;
import com.cg.stockapp.model.UserdetailsResponse;



public interface IJwtUserDetailsService {
	
	public DAOUser save(UserDTO user);
	
	public void setLoggedIn(String username);
	
	public boolean changePassword(String userName, PasswordDTO passwordDTO) throws UsernameNotFoundException;
	
	public boolean logOut(String userName);
	
	public String getUserNameToken(String token);
	
	public String getRoleFromToken(String token) throws UsernameNotFoundException;
	
	public UserdetailsResponse getUserDetailsFromToken(String token) throws UsernameNotFoundException;

	public UserdetailsResponse toUserDetailsResponse(DAOUser user);

}

package com.cg.stockapp.service;

import com.cg.stockapp.entities.DAOUser;

public interface IUserService {
	
	public boolean addUser(DAOUser user);
	
	public boolean removeUser(long userId);
	
	public boolean updateUser(DAOUser user);
	
}

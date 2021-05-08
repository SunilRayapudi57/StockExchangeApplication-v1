package com.cg.stockapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.stockapp.entities.DAOUser;

@Repository
public interface UserDao extends CrudRepository<DAOUser, Long> {

	DAOUser findByUsername(String username);

	@Query("DELETE FROM DAOUser u WHERE u.username=:name")
	public void deleteByUserName(@Param("name") String userName);
	
}

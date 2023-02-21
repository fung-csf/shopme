package com.shopme.admin.user;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.common.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	/*
	 * JPQL statement. It should be noted that as compared to native SQL, JPQL does
	 * not interact with database tables, records, and fields - but with Java
	 * classes and instances.
	 * 
	 * JPA kicks in as the mediator and transpiles JPQL queries to SQL queries for execution.
	 * 
	 * If your database can change or varies from development to production, as long as 
	 * they're both relational - JPQL works wonders and you can 
	 * write JPQL queries to create generic logic that can be used over and over again.
	 */
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
	// countById() declaration follows the JPA specification
	//to check the existence of a user
	public Long countById(Integer id);
	
	//change user enabled status
	//need @Modifying annotation for update/delete operation
	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
}

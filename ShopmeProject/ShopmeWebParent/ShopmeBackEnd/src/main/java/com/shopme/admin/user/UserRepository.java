package com.shopme.admin.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopme.admin.paging.SearchRepository;
import com.shopme.common.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, SearchRepository<User, Integer> {

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
	
	
	//for filtering users by keyword
	/*
	 * @Query("SELECT u FROM User u WHERE u.firstName LIKE %?1% OR u.lastName LIKE %?1%"
	 * + " OR u.email LIKE %?1%")
	 * 
	 * the above query does not work when we search for user with keyword "nam ha minh".
	 * 
	 * Instead we should concatenate the fields separated by space as the query below
	 */
	 @Query("SELECT u FROM User u WHERE  CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName) LIKE %?1%")
	public Page<User> findAll(String keyword, Pageable pageable);
	
	
	//change user enabled status
	//need @Modifying annotation for update/delete operation
	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
}

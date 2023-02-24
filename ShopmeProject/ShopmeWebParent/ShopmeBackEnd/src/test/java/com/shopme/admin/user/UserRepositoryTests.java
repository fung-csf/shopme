package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest(showSql = false)
//configure to run on real database(MySQL) instead of a test database
@AutoConfigureTestDatabase(replace = Replace.NONE)
/*
 * Datajpatest will rollback the transaction after executing the test method
 * everytime, so that the transaction will not be committed. if we want to
 * commit the transaction(aka "save the record"), we can disable rollback by
 * setting it to false. Now, all transactions will be committed
 */
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	// On the very first execution of testCreateUser() method; hibernate will create
	// the users table & users_roles table
	@Test
	public void testCreateUserWithOneRole() {

		Role roleAdmin = entityManager.find(Role.class, 1);
		User userChow = new User("chow@gmail.com", "password", "chow", "seng fung");
		userChow.addRoles(roleAdmin);
		User savedUser = repo.save(userChow);
		assertThat(savedUser.getId()).isGreaterThan(0);

	}

	@Test
	public void testCreateUserWithTwoRole() {

		User userRavi = new User("ravi@gmail.com", "ravipass", "ravi", "kumar");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);

		userRavi.addRoles(roleEditor);
		userRavi.addRoles(roleAssistant);

		User savedUser = repo.save(userRavi);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();

		// use method reference instead of lambda expression here
		listUsers.forEach(System.out::println);

	}

	@Test
	public void testGetUserById() {

		User userChow = repo.findById(1).get();
		System.out.println(userChow);
		// use method reference instead of lambda expression here
		assertThat(userChow).isNotNull();
	}

	@Test
	public void testUpdateUserDetails() {
		
		User userChow = repo.findById(1).get();
		userChow.setEnabled(true);
		userChow.setEmail("chow22@gmail.com");
		
		repo.save(userChow);
	}
	
	@Test
	public void testUpdateUserRoles() {
		
		User userRavi = repo.findById(4).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson= new Role(2);
		
		//change role from editor to salesperson
		
		/* take note of overriding/implementing the hashcode and equals method in roles
		 * class. need to override those two methods for remove() to work.
		 *  The remove() method removes an element "e" such that Objects.equals(o, e),
		 *  this will invoke the overwritten equals() method in Role Class. 
		 * */
		userRavi.getRoles().remove(roleEditor);
		userRavi.addRoles(roleSalesperson);
		
		repo.save(userRavi);
	}
	
	@Test
	public void testDeleteUser() {
		
		Integer userId = 4;
		repo.deleteById(userId);
	}
	
	
	@Test
	public void testGetUserByEmail() {
		String email = "ravi@gmail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	/*
	 * count the number of records in database with a particular id.
	 * return value should be 1,
	 * because id is unique.
	 * 
	 * use countById() method to test whether a user exists. 
	 */
	@Test
	public void testCountById() {
		
		Integer userId = 1;
		Long countById = repo.countById(userId);
		
		assertThat(countById).isNotNull().isGreaterThan(0).isEqualTo(1);
	}
	
	@Test	
	public void testDisableUser() {
		Integer userId = 1;
		
		repo.updateEnabledStatus(userId, false);
	}

	@Test	
	public void testEnableUser() {
		Integer userId = 1;
		
		repo.updateEnabledStatus(userId, true);
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 0; // the first page number always start with 0, change pageNumber to 1 to get the second page, 2 for thid page and so forth
		int pageSize = 4; // no of elements
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(System.out::println);
		
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
	
	
	@Test 
	public void testSearchUsers() {
		
		String keyword = "bruce";
		
		
		int pageNumber = 0; // the first page number always start with 0, change pageNumber to 1 to get the second page, 2 for thid page and so forth
		int pageSize = 4; // no of elements
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword, pageable);
		
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(System.out::println);
		
		assertThat(listUsers.size()).isGreaterThan(0);
		
	}
	
	
}


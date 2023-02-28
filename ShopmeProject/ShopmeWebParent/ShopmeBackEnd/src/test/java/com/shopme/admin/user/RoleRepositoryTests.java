package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.swing.event.TreeWillExpandListener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

/*
 * Unit Test for RepoRepository
 * 
 * objective:
 * 1. test the methods in repository 
 * 2. populate the database with records
 * 
 * 
*/


@DataJpaTest
//configure to run on real database(MySQL) instead of a test database
@AutoConfigureTestDatabase(replace = Replace.NONE)
/*
 * Datajpatest will rollback the transaction after executing the test method
 * everytime, so that the transaction will not be committed. if we want to
 * commit the transaction(aka "save the record"), we can disable rollback by
 * setting it to false. Now, all transactions will be committed
 */
@Rollback(false)
public class RoleRepositoryTests {

	@Autowired
	private RoleRepository repo;

	/*
	 * will create role table automatically on the very first test method execution
	 * no need to manually create the tables in db; just create the entity class and
	 * add spring.jpa.hibernate.ddl-auto=update in the application.properties file
	 * 
	 */
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("admin", "manage everything");

		Role savedRole = repo.save(roleAdmin);

		// check whether object persisted into database
		assertThat(savedRole.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateRestRole() {
		Role roleSalesperson = new Role("Salesperson",
				"manage product price, customers, " + "shipping, orders and sales report");

		Role roleEditor = new Role("Editor", "manage categories, brands" + ", products, articles, and menu");

		Role roleShipper = new Role("Shipper", "view products, view orders," + "and update order status");

		Role roleAssistant = new Role("Assistant", "manage questions and reviews");

		repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));

	}

}

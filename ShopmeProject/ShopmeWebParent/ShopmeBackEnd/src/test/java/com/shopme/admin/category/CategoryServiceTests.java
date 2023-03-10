package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.nullable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shopme.common.entity.Category;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

	/*
	 * CategoryService class is the "class under test" for this unit test. We need
	 * to create mock objects for each of the dependencies in CategoryService class.
	 * CategoryService class depends on CategoryRepository, so we need to create a
	 * mock object for CategoryRepository.
	 * 
	 * In a unit test, we are only testing the "class under test"; which is
	 * CategoryService.
	 * 
	 */

	@MockBean
	CategoryRepository repo;

	@InjectMocks
	CategoryService service;

	@Test
	public void testCheckUniqueInNewModeReturnDuplicateName() {

		Integer id = null;
		String name = "Computers";
		String alias = "abc";

		Category category = new Category(id, name, alias);

		// when the method "repo.findByName(name)" in checkUnique() is executed, return
		// the category above
		Mockito.when(repo.findByName(name)).thenReturn(category);
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);

		String result = service.checkUnique(id, name, alias);

		assertThat(result).isEqualTo("DuplicateName");

	}

	@Test
	public void testCheckUniqueInNewModeReturnDuplicateAlias() {

		Integer id = null;
		String name = "NameABC";
		String alias = "computers";

		Category category = new Category(id, name, alias);

		// when the method "repo.findByName(name)" in checkUnique() is executed, return
		// the category above
		Mockito.when(repo.findByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(category);

		String result = service.checkUnique(id, name, alias);

		assertThat(result).isEqualTo("DuplicateAlias");

	}

	@Test
	public void testCheckUniqueInNewModeReturnOK() {

		Integer id = null;
		String name = "NameABC";
		String alias = "computers";

		// when the method "repo.findByName(name)" in checkUnique() is executed, return
		// the category above
		Mockito.when(repo.findByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);

		String result = service.checkUnique(id, name, alias);

		assertThat(result).isEqualTo("OK");

	}

	@Test
	public void testCheckUniqueInEditModeReturnDuplicateName() {

		Integer id = 1;
		String name = "Computers";
		String alias = "abc";

		Category category = new Category(2, name, alias);

		// when the method "repo.findByName(name)" in checkUnique() is executed, return
		// the category above
		Mockito.when(repo.findByName(name)).thenReturn(category);
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);

		String result = service.checkUnique(id, name, alias);

		assertThat(result).isEqualTo("DuplicateName");

	}
	
	@Test
	public void testCheckUniqueInEditModeReturnDuplicateAlias() {

		Integer id = 1;
		String name = "NameABC";
		String alias = "computers";

		Category category = new Category(2, name, alias);

		// when the method "repo.findByName(name)" in checkUnique() is executed, return
		// the category above
		Mockito.when(repo.findByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(category);

		String result = service.checkUnique(id, name, alias);

		assertThat(result).isEqualTo("DuplicateAlias");

	}
	
	@Test
	public void testCheckUniqueInEditModeReturnOK() {

		Integer id = 1;
		String name = "NameABC";
		String alias = "computers";

		Category category = new Category(id, name, alias);
		
		// when the method "repo.findByName(name)" in checkUnique() is executed, return
		// the category above
		Mockito.when(repo.findByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(category);

		String result = service.checkUnique(id, name, alias);

		assertThat(result).isEqualTo("OK");

	}
}
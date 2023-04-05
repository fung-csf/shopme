package com.shopme.admin.paging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

//so spring data jpa will not create a bean for this interface
@NoRepositoryBean
public interface SearchRepository<T, ID> extends PagingAndSortingRepository<T, ID> {
	
	public Page<T> findAll(String keyword, Pageable pageable);
}

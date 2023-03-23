package com.shopme.category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Category;
import com.shopme.common.exception.CategoryNotFoundException;

@Service
public class CategoryService {
	
	@Autowired private CategoryRepository repo;
	
	public List<Category> listNoChildrenCategories() {
		
		List<Category> listEnabledCategories = repo.findAllEnabled();
		
		List<Category> listNoChildrenCategories = listEnabledCategories
									.stream()
									.filter(category -> category.getChildren() == null || category.getChildren().size() == 0)
									.collect(Collectors.toList());
		
//		listEnabledCategories.forEach(category -> {
//			Set<Category> children = category.getChildren();
//			if (children == null || children.size() == 0) {
//				listNoChildrenCategories.add(category);
//			}
//		});
		
		return listNoChildrenCategories;
	}
	
	public Category getCategory(String alias) throws CategoryNotFoundException {
		Category category = repo.findByAliasEnabled(alias);
		if (category == null) {
			throw new CategoryNotFoundException("Could not find any categories with alias " + alias);
		}
		
		return category;
	}
	
	/*for breadcrumb in  products_by_category page*/
	public List<Category> getCategoryParents(Category child) {
		List<Category> listParents = new ArrayList<>();
		
		Category parent = child.getParent();
		
		while (parent != null) {
			listParents.add(0, parent);
			parent = parent.getParent();
		}
		
		listParents.add(child);
		
		return listParents;
	}
	
}

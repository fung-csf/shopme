package com.shopme.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.shopme.product.ProductService;

import com.shopme.common.exception.CategoryNotFoundException;
import com.shopme.category.CategoryService;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.product.Product;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/c/{category_alias}")
	public String viewCategoryFirstPage(@PathVariable("category_alias") String alias, Model model)
			throws CategoryNotFoundException {

		return viewCategoryByPage(alias, 1, model);
	}

	@GetMapping("/c/{category_alias}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_alias") String alias, @PathVariable("pageNum") int pageNum,
			Model model) throws CategoryNotFoundException {

		try {

			Category category = categoryService.getCategory(alias);

			/* for breadcrumb in products_by_category.html */
			List<Category> listCategoryParents = categoryService.getCategoryParents(category);

			Page<Product> pageProducts = productService.listByCategory(pageNum, category.getId());
			List<Product> listProducts = pageProducts.getContent();

			long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
			long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
			if (endCount > pageProducts.getTotalElements()) {
				endCount = pageProducts.getTotalElements();
			}

			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalItems", pageProducts.getTotalElements());
			model.addAttribute("listProducts", listProducts);

			// category object as model attribute -> to obtain the alias of the category for
			// pagination
			model.addAttribute("category", category);

			model.addAttribute("pageTitle", category.getName());
			model.addAttribute("listCategoryParents", listCategoryParents);

			return "products_by_category";
		} catch (CategoryNotFoundException ex) {
			return "error/404";
		}
	}

}

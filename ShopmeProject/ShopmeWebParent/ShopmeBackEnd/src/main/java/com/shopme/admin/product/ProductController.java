package com.shopme.admin.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopme.admin.brand.BrandService;
import com.shopme.admin.category.CategoryService;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.product.Product;

@Controller
public class ProductController {

	@Autowired private ProductService productService;
	@Autowired private BrandService brandService;
	@Autowired private CategoryService categoryService;
	
	
	@GetMapping("/products")
	public String listAll(Model model) {
		
		List<Product> listProducts = productService.listAll();
		
		model.addAttribute("listProducts", listProducts);
		
		return "products/products";
	}
	
	@GetMapping("/products/new")
	public String newProduct(Model model) {
		List<Brand> listBrands = brandService.listAll();
		
		Product product = new Product();
		product.setEnabled(true);
		product.setInStock(true);
		
		model.addAttribute("product", product);
		model.addAttribute("listBrands", listBrands);
		model.addAttribute("pageTitle", "Create New Product");
		/* model.addAttribute("numberOfExistingExtraImages", 0); */
		
		return "products/product_form";
	}
	
	/*
	 * @PostMapping("/products/save") public String
	 * saveProduct(@ModelAttribute("product") Product product, RedirectAttributes
	 * ra,
	 * 
	 * @RequestParam(value = "fileImage", required = false) MultipartFile
	 * mainImageMultipart,
	 * 
	 * @RequestParam(value = "extraImage", required = false) MultipartFile[]
	 * extraImageMultiparts,
	 * 
	 * @RequestParam(name = "detailIDs", required = false) String[] detailIDs,
	 * 
	 * @RequestParam(name = "detailNames", required = false) String[] detailNames,
	 * 
	 * @RequestParam(name = "detailValues", required = false) String[] detailValues,
	 * 
	 * @RequestParam(name = "imageIDs", required = false) String[] imageIDs,
	 * 
	 * @RequestParam(name = "imageNames", required = false) String[] imageNames,
	 * 
	 * @AuthenticationPrincipal ShopmeUserDetails loggedUser ) throws IOException {
	 * 
	 * if (!loggedUser.hasRole("Admin") && !loggedUser.hasRole("Editor")) { if
	 * (loggedUser.hasRole("Salesperson")) {
	 * productService.saveProductPrice(product); ra.addFlashAttribute("message",
	 * "The product has been saved successfully."); return defaultRedirectURL; } }
	 * 
	 * ProductSaveHelper.setMainImageName(mainImageMultipart, product);
	 * ProductSaveHelper.setExistingExtraImageNames(imageIDs, imageNames, product);
	 * ProductSaveHelper.setNewExtraImageNames(extraImageMultiparts, product);
	 * ProductSaveHelper.setProductDetails(detailIDs, detailNames, detailValues,
	 * product);
	 * 
	 * Product savedProduct = productService.save(product);
	 * 
	 * ProductSaveHelper.saveUploadedImages(mainImageMultipart,
	 * extraImageMultiparts, savedProduct);
	 * 
	 * ProductSaveHelper.deleteExtraImagesWeredRemovedOnForm(product);
	 * 
	 * ra.addFlashAttribute("message", "The product has been saved successfully.");
	 * 
	 * return "redirect:/products"; }
	 */
	
	
	
}

package com.sl.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.app.entity.Product;
import com.sl.app.service.ProductService;


@Controller
@RequestMapping("/api/v1")
public class ProductController {

	private ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@RequestMapping("/home")
	 public String viewHomePage(Model model) {
		String keyword = null;
		return listByPage(model, 1, "productName", "asc", keyword);
	}
	@RequestMapping("/home2")
	 public String viewHomePage2(Model model) {
		String keyword = null;
		return listByPage2(model, 1, "productName", "asc", keyword);
	}
	
	@GetMapping("/page/{pageNumber}")
		public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
				@Param("sortField") String sortField, 
				@Param("sortDir") String sortDir,
				@Param("keyword") String keyword) {
		Page<Product> page = productService.listAllProducts(currentPage, sortField, sortDir, keyword);
		
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();
		
		List<Product> listProducts = page.getContent();
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems",totalItems);
		model.addAttribute("totalPages",totalPages);
		model.addAttribute("listProducts",listProducts);
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		
		String reverseSortDir = sortDir.equals("asc")?"desc":"asc";
		
		model.addAttribute("reverseSortDir",reverseSortDir);
		
		return "admin/products";
	}
	
	@GetMapping("/paget/{pageNumber}")
	public String listByPage2(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("sortField") String sortField, 
			@Param("sortDir") String sortDir,
			@Param("keyword") String keyword) {
	Page<Product> page = productService.listAllProducts(currentPage, sortField, sortDir, keyword);
	
	long totalItems = page.getTotalElements();
	int totalPages = page.getTotalPages();
	
	List<Product> listProducts = page.getContent();
	
	model.addAttribute("currentPage", currentPage);
	model.addAttribute("totalItems",totalItems);
	model.addAttribute("totalPages",totalPages);
	model.addAttribute("listProducts",listProducts);
	model.addAttribute("sortField",sortField);
	model.addAttribute("sortDir", sortDir);
	model.addAttribute("keyword", keyword);
	
	String reverseSortDir = sortDir.equals("asc")?"desc":"asc";
	
	model.addAttribute("reverseSortDir",reverseSortDir);
	
	return "admin/manageProducts";
}
	


	// add new product
	@GetMapping("/product/new")
	public String createProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("productAttribute", product);
		return "admin/createProduct";
	}

	// Save Product
	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute("productAttribute") Product product) {
		productService.saveProduct(product);
		return "redirect:/api/v1/home";
	}

	// mapping for delete functionality
	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return "redirect:/api/v1/home2";
	}

	// mapping for update returning the form
	@GetMapping("/products/edit/{id}")
	public String editProductForm(@PathVariable Long id, Model model) {
		model.addAttribute("productAttribute", productService.getProductById(id));
		return "admin/updateProduct";
	}

	// updating the product
	@PostMapping("/updateProduct/{id}")
	public String updateProduct(@PathVariable Long id, @ModelAttribute("productAttribute") Product product) {

		Product existingProduct = null;
		// find the existing product
		if (id != null) {
			existingProduct = productService.getProductById(id);
		}

		if (existingProduct != null) {
			existingProduct.setProductName(product.getProductName());
			existingProduct.setProductDescription(product.getProductDescription());
			existingProduct.setCurrentQuantity(product.getCurrentQuantity());
			existingProduct.setProductAcquiringCost(product.getProductAcquiringCost());
			existingProduct.setProductSellingPrice(product.getProductSellingPrice());
			existingProduct.setProductUnitOfMeasurement(product.getProductUnitOfMeasurement());
			existingProduct.setProductCategory(product.getProductCategory());
			existingProduct.setReorderQuantity(product.getReorderQuantity());
			productService.saveProduct(existingProduct);
		}
		return "redirect:/api/v1/home2";

	}

	@GetMapping("/products/view/{id}")
	public String getProductInfo(@PathVariable Long id, Model model) {
		Product product = productService.getProductById(id);
		model.addAttribute("productAttribute", product);
		return "admin/viewProductDetails";

	}

}

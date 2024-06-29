package com.sl.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sl.app.entity.Product;

public interface ProductService {

	List<Product> findAllProducts(String keyword);

	void saveProduct(Product product);

	void deleteProduct(Long id);

	Product getProductById(Long id);

	public Page<Product> listAllProducts(int pageNumber, String sortField, String sortDir, String keyword);

}

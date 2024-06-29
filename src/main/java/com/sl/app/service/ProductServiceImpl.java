package com.sl.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sl.app.entity.Product;
import com.sl.app.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public Page<Product> listAllProducts(int pageNumber, String sortField, String sortDir, String keyword){
		
		 Sort sort = Sort.by(sortField);
		 sort = sortDir.equals("asc")?sort.ascending():sort.descending();
		 
		 Pageable pageable = PageRequest.of(pageNumber-1, 5 ,sort);
		 
		 if(keyword != null) {
				return productRepository.findAll(keyword, pageable);
			}
		 return productRepository.findAll(pageable);
	}

	
	@Override
	public List<Product> findAllProducts(String keyword) {
		
		if(keyword != null) {
			 return productRepository.findByKeyword(keyword);
		}
		List<Product> products = productRepository.findAll();
		return products;
	}

	@Override
	public void saveProduct(Product product) {
		productRepository.save(product);

	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).get();
	}
	

}

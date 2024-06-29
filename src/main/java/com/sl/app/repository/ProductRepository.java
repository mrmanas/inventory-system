package com.sl.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sl.app.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "SELECT * FROM products p WHERE p.product_name LIKE %:keyword% "
			+ "OR p.product_description LIKE %:keyword% "
			+ "OR p.product_category LIKE %:keyword%"
			, nativeQuery = true)
	List<Product> findByKeyword(@Param("keyword") String keyword);
	
//	@Query("Select p FROM products p WHERE"
//			+ "CONCAT(p.product_name,product_description,p.product_category)" + " LIKE %?1%")
	@Query(value = "SELECT p FROM Product p WHERE"
			+ " LOWER(CONCAT(p.productName,' ',p.productDescription,' ',p.currentQuantity,' ', p.reorderQuantity,' ', p.productAcquiringCost,' ', p.productSellingPrice))"
			+ " LIKE LOWER(CONCAT('%', :keyword, '%'))")
			
	public Page<Product> findAll(String keyword, Pageable pageable);

}

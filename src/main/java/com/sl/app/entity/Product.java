package com.sl.app.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sl.app.util.Category;
import com.sl.app.util.MeasurementUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@Column(nullable = false, name = "Product_Name")
	private String productName;

	@Column(nullable = false, name = "Product_Description")
	private String productDescription;

	@Column(nullable = false, name = "Product_Category")
	@Enumerated(EnumType.STRING)
	private Category productCategory;

	@Column(nullable = false, name = "Product_Unit_Of_Measurement")
	@Enumerated(EnumType.STRING)
	private MeasurementUnit productUnitOfMeasurement;

	// stores the cost at which product is purchased by the shopkeeper
	@Column(nullable = false, name = "Product_Purchase_Price")
	private double productAcquiringCost;

	@Column(nullable = false, name = "Product_Sell_Price")
	private double productSellingPrice;

	@Column(nullable = false, name = "Product_Quantity")
	private double currentQuantity;

	// keeps track of min quantity at which product need to be re-purchased
	@Column(nullable = false, name = "Product_Reorder_Value")
	private double reorderQuantity;

	// time will taken from the system clock of the local machine
	@Column(nullable = false, name = "Added_On_Date")
	@CreationTimestamp
	private LocalDateTime productCreationDate;

	@Column(nullable = false, name = "Updated_On_Date")
	@UpdateTimestamp
	private LocalDateTime productUpdationDate;

}

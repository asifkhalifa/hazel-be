package com.hazelngrazel.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hazelngrazel.app.dto.ProductRequest;
import com.hazelngrazel.app.dto.ProductResponse;
import com.hazelngrazel.app.serviceI.ProductServiceI;


@RestController
@RequestMapping("/api/product")
@CrossOrigin("*")
public class ProductController {
	
	@Autowired
	ProductServiceI productServiceI;
	
	@PostMapping("/save")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveProduct(@ModelAttribute ProductRequest request) {
	    try {
	    	productServiceI.saveProduct(request);
	        return ResponseEntity.ok("Product added successfully");
	    } catch (Exception e) {
	        return ResponseEntity.badRequest()
	                .body(e.getMessage());
	    }
	}

	@GetMapping("/all/get")
	public ResponseEntity<?> getAllProducts(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String name) {
		try {
			Page<ProductResponse> products = productServiceI.getAllProducts(page, size, status, name);
			return ResponseEntity.ok(products);
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}

	@DeleteMapping("/delete/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteProduct(
			@PathVariable Long productId,
			@RequestParam Boolean isDisabled) {
		try {
			productServiceI.deleteProduct(productId, isDisabled);
			return ResponseEntity.ok("Product status updated successfully");
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}

	@PostMapping("/update/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateProduct(
			@PathVariable Long productId,
			@ModelAttribute ProductRequest request) {
		try {
			productServiceI.updateProduct(productId, request);
			return ResponseEntity.ok("Product updated successfully");
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}

	@GetMapping("/by-id/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable Long productId) {
		try {
			ProductResponse product = productServiceI.getProductById(productId);
			return ResponseEntity.ok(product);
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}

	@DeleteMapping("/hard-delete/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> hardDeleteProduct(@PathVariable Long productId) {
		try {
			productServiceI.hardDeleteProduct(productId);
			return ResponseEntity.ok("Product deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}

	@GetMapping("/featured")
	public ResponseEntity<?> getFeaturedProducts() {
		try {
			List<ProductResponse> products = productServiceI.getFeaturedProducts();
			return ResponseEntity.ok(products);
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}
}

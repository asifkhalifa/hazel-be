package com.hazelngrazel.app.serviceI;

import java.util.List;

import org.springframework.data.domain.Page;
import com.hazelngrazel.app.dto.ProductRequest;
import com.hazelngrazel.app.dto.ProductResponse;

public interface ProductServiceI {

	void saveProduct(ProductRequest request);

	Page<ProductResponse> getAllProducts(int page, int size, String status, String name);

	void deleteProduct(Long productId, Boolean isDisabled);

	void updateProduct(Long productId, ProductRequest request);

	ProductResponse getProductById(Long productId);

	void hardDeleteProduct(Long productId);

	List<ProductResponse> getFeaturedProducts();
}

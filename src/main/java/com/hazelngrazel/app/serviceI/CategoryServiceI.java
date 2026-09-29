package com.hazelngrazel.app.serviceI;

import java.util.List;

import org.springframework.data.domain.Page;

import com.hazelngrazel.app.dto.CategoryRequest;
import com.hazelngrazel.app.entity.CategoryEntity;

public interface CategoryServiceI {
	void createCategory(CategoryRequest request);
	Page<CategoryEntity> getAllCategories(int page, int size, String status, String name);
	CategoryEntity getCategoryById(Long categoryId);
	void updateCategory(Long categoryId, CategoryRequest request);
	void deleteCategory(Long categoryId, Boolean isDisabled);
	void hardDeleteCategory(Long categoryId);
}
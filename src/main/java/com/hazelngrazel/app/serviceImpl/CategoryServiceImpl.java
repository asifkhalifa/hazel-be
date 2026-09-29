package com.hazelngrazel.app.serviceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hazelngrazel.app.dto.CategoryRequest;
import com.hazelngrazel.app.entity.CategoryEntity;
import com.hazelngrazel.app.repository.CategoryRepository;
import com.hazelngrazel.app.repository.ProductRepository;
import com.hazelngrazel.app.serviceI.CategoryServiceI;

@Service
public class CategoryServiceImpl implements CategoryServiceI {
	
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductRepository productRepository;

	@Override
	public void createCategory(CategoryRequest request) {
		if (request.getCategoryName() == null || request.getCategoryName().trim().isEmpty()) {
		    throw new RuntimeException("Category name is required");
		}
		
		Optional<CategoryEntity> entity = categoryRepository.findByCategoryNameIgnoreCase(request.getCategoryName().trim());
		
		if(entity.isPresent()) {
			throw new RuntimeException("Category name is already present");
		}
		
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setCategoryName(request.getCategoryName());
		categoryRepository.save(categoryEntity);
	}

	@Override
	public Page<CategoryEntity> getAllCategories(
	        int page,
	        int size,
	        String status,
	        String name) {

	    Pageable pageable = PageRequest.of(page, size);

	    boolean hasName =
	            name != null && !name.trim().isEmpty();

	    boolean hasStatus =
	            status != null && !status.trim().isEmpty();

	    // Both search + status
	    if (hasName && hasStatus) {

	        Boolean isDisabled =
	                status.equalsIgnoreCase("DISABLED");

	        return categoryRepository
	                .findByCategoryNameContainingIgnoreCaseAndIsDisabled(
	                        name,
	                        isDisabled,
	                        pageable);
	    }

	    // Only search
	    if (hasName) {
	        return categoryRepository
	                .findByCategoryNameContainingIgnoreCase(
	                        name,
	                        pageable);
	    }

	    // Only status
	    if (hasStatus) {

	        Boolean isDisabled =
	                status.equalsIgnoreCase("DISABLED");

	        return categoryRepository
	                .findByIsDisabled(
	                        isDisabled,
	                        pageable);
	    }

	    // No filter => return all
	    return categoryRepository.findAll(pageable);
	}

	@Override
	public CategoryEntity getCategoryById(Long categoryId) {
		Optional<CategoryEntity> entity = categoryRepository.findById(categoryId);
		
		if(!entity.isPresent()) {
			throw new RuntimeException("Category data not found");
		}
		return entity.get();
	}

	@Override
	public void updateCategory(Long categoryId, CategoryRequest request) {

		Optional<CategoryEntity> entity = categoryRepository.findById(categoryId);
		
		if(!entity.isPresent()) {
			throw new RuntimeException("Category data not found");
		}
		
	    if (request.getCategoryName() == null
	            || request.getCategoryName().trim().isEmpty()) {

	        throw new RuntimeException("Category name is required");
	    }

	    Optional<CategoryEntity> existingCategory =
	            categoryRepository.findByCategoryNameIgnoreCase(
	                    request.getCategoryName().trim());

	    if (existingCategory.isPresent()
	            && !existingCategory.get().getCategoryId().equals(categoryId)) {

	        throw new RuntimeException("Category name is already present");
	    }

	    entity.get().setCategoryName(request.getCategoryName().trim());

	    categoryRepository.save(entity.get());
	}

	@Override
	public void deleteCategory(Long categoryId, Boolean isDisabled) {
		Optional<CategoryEntity> entity = categoryRepository.findById(categoryId);
		
		if(!entity.isPresent()) {
			throw new RuntimeException("Category data not found");
		}
		
		entity.get().setIsDisabled(isDisabled);
		categoryRepository.save(entity.get());
	}

	@Override
	public void hardDeleteCategory(Long categoryId) {
		Optional<CategoryEntity> entity = categoryRepository.findById(categoryId);
		
		if(!entity.isPresent()) {
			throw new RuntimeException("Category data not found");
		}
		
		if (productRepository.existsByCategoryCategoryId(categoryId)) {
			throw new RuntimeException("Cannot delete category because it is linked to active products. Please delete or reassign products first.");
		}
		
		categoryRepository.delete(entity.get());
	}
}
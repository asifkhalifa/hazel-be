package com.hazelngrazel.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hazelngrazel.app.dto.CategoryRequest;
import com.hazelngrazel.app.entity.CategoryEntity;
import com.hazelngrazel.app.serviceI.CategoryServiceI;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin("*")
public class CategoryController {

	@Autowired
	CategoryServiceI categoryServiceI;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest request) {
    	try {
    		categoryServiceI.createCategory(request);
    		return ResponseEntity.ok("Category Added Successfully..");
    	} catch (RuntimeException e) {
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}
    }

    @GetMapping("/all/get")
    public ResponseEntity<?> getAllCategories(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,
    										  @RequestParam(required = false) String status,@RequestParam(required = false) String name) {
        try {
            Page<CategoryEntity> categories = categoryServiceI.getAllCategories(page, size, status, name);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/by-id/get{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long categoryId) {
    	try {
    		CategoryEntity categoryEntity = categoryServiceI.getCategoryById(categoryId);
    		return ResponseEntity.ok(categoryEntity);
    	} catch (RuntimeException e) {
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}
    }

    @PutMapping("/upadate/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequest request) {
    	try {
    		categoryServiceI.updateCategory(categoryId, request);
    		return ResponseEntity.ok("Category Updated Successfully..");
    	} catch (RuntimeException e) {
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}
    }

    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId,@RequestParam Boolean isDisabled) {
    	try {
    		categoryServiceI.deleteCategory(categoryId, isDisabled);
    		return ResponseEntity.ok("Category Status Updated Successfully..");
    	} catch (RuntimeException e) {
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}
    }

    @DeleteMapping("/hard-delete/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> hardDeleteCategory(@PathVariable Long categoryId) {
    	try {
    		categoryServiceI.hardDeleteCategory(categoryId);
    		return ResponseEntity.ok("Category Deleted Successfully..");
    	} catch (RuntimeException e) {
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}
    }
}
package com.hazelngrazel.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hazelngrazel.app.dto.GalleryDataResponse;
import com.hazelngrazel.app.dto.GalleryDataRequest;
import com.hazelngrazel.app.dto.PageResponse;
import com.hazelngrazel.app.serviceI.GalleryServiceI;

@RestController
@RequestMapping("/api/gallery")
@CrossOrigin("*")
public class GalleryController {

	@Autowired
	GalleryServiceI galleryServiceI;
	
	@PostMapping("/image/save")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> uploadData(@ModelAttribute GalleryDataRequest request){
		try {
			galleryServiceI.uploadData(request);
			return ResponseEntity.ok("File uploaded successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/images/all/get")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllImages(
	        @RequestParam(required = false) Boolean isDisabled,
	        @RequestParam(defaultValue = "0") Integer page,
	        @RequestParam(defaultValue = "10") Integer size) {
	    try {
	        PageResponse<GalleryDataResponse> response = galleryServiceI.getAllImages(isDisabled, page, size);
	        return ResponseEntity.ok(response);
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest()
	                .body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Something went wrong while fetching images");
	    }
	}

	@GetMapping("/images/active")
	public ResponseEntity<?> getActiveImages(
	        @RequestParam(defaultValue = "0") Integer page,
	        @RequestParam(defaultValue = "100") Integer size) {
	    try {
	        PageResponse<GalleryDataResponse> response = galleryServiceI.getAllImages(false, page, size);
	        return ResponseEntity.ok(response);
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest()
	                .body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError()
	                .body("Something went wrong while fetching images");
	    }
	}

	@PutMapping("/image/status/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateStatus(
	        @PathVariable Long id,
	        @RequestParam Boolean isDisabled) {
	    try {
	        galleryServiceI.updateStatus(id, isDisabled);
	        return ResponseEntity.ok("Image status updated successfully");
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
	
}

package com.hazelngrazel.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hazelngrazel.app.dto.ChangePasswordRequest;
import com.hazelngrazel.app.dto.LoginRequest;
import com.hazelngrazel.app.dto.LoginResponse;
import com.hazelngrazel.app.dto.RegisterRequest;
import com.hazelngrazel.app.serviceI.AuthServiceI;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

	@Autowired
    AuthServiceI authServiceI;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    	try {
    		authServiceI.register(request);
    		return ResponseEntity.ok("Register Successfully");
		} catch (RuntimeException e) {
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}	
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    	try {
    		LoginResponse response = authServiceI.login(request);
    		return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}	
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
    	try {
    		authServiceI.changePassword(request.getCurrentPassword(), request.getNewPassword());
    		return ResponseEntity.ok("Password changed successfully");
		} catch (RuntimeException e) {
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}	
    }
}

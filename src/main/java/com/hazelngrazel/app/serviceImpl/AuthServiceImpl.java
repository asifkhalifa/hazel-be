package com.hazelngrazel.app.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hazelngrazel.app.dto.LoginRequest;
import com.hazelngrazel.app.dto.LoginResponse;
import com.hazelngrazel.app.dto.RegisterRequest;
import com.hazelngrazel.app.entity.Role;
import com.hazelngrazel.app.entity.ProfileEntity;
import com.hazelngrazel.app.repository.UserRepository;
import com.hazelngrazel.app.security.JwtService;
import com.hazelngrazel.app.serviceI.AuthServiceI;

@Service
public class AuthServiceImpl implements AuthServiceI {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
    JwtService jwtService;
	
    @Override
	public void register(RegisterRequest request) {

      Optional<ProfileEntity> profileEntity = userRepository.findByEmailIgnoreCase(request.getEmail());
      
      if(profileEntity.isPresent()) {
    	  throw new RuntimeException("Email already exists");
      }
      
      ProfileEntity user = new ProfileEntity();
      user.setName(request.getName());
      user.setEmail(request.getEmail());
      user.setPassword(passwordEncoder.encode(request.getPassword()));
      user.setRole(Role.ROLE_ADMIN);
      
      userRepository.save(user);
    }

	@Override
	public LoginResponse login(LoginRequest request) {
		authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        ProfileEntity user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .authorities(user.getRole().name())
                        .build()
        );
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return loginResponse;
	}

	@Override
	public void changePassword(String currentPassword, String newPassword) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		ProfileEntity user = userRepository.findByEmailIgnoreCase(email)
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
			throw new RuntimeException("Invalid current password");
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

}

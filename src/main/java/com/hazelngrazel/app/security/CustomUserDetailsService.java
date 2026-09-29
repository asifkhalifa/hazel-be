package com.hazelngrazel.app.security;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.hazelngrazel.app.entity.ProfileEntity;
import com.hazelngrazel.app.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
    	Optional<ProfileEntity> user = userRepository.findByEmailIgnoreCase(email);
       
    	if(!user.isPresent()) {
    		throw new RuntimeException("User Not Found..");
    	}
    	
    	return org.springframework.security.core.userdetails.User
                .withUsername(user.get().getEmail())
                .password(user.get().getPassword())
                .authorities(user.get().getRole().name())
                .build();
    }
}
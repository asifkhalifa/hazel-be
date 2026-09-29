package com.hazelngrazel.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hazelngrazel.app.entity.ProfileEntity;

@Repository
public interface UserRepository extends JpaRepository<ProfileEntity, Long> {

	Optional<ProfileEntity> findByEmailIgnoreCase(String email);

}

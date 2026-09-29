package com.hazelngrazel.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hazelngrazel.app.entity.SettingsEntity;

@Repository
public interface SettingsRepository extends JpaRepository<SettingsEntity, Long> {
}

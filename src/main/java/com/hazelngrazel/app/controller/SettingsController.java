package com.hazelngrazel.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hazelngrazel.app.dto.SettingsRequest;
import com.hazelngrazel.app.entity.SettingsEntity;
import com.hazelngrazel.app.serviceI.SettingsServiceI;

@RestController
@RequestMapping("/api/settings")
@CrossOrigin("*")
public class SettingsController {

    @Autowired
    SettingsServiceI settingsServiceI;

    @GetMapping("/get")
    public ResponseEntity<?> getSettings() {
        try {
            SettingsEntity settings = settingsServiceI.getSettings();
            return ResponseEntity.ok(settings);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveSettings(@RequestBody SettingsRequest request) {
        try {
            settingsServiceI.updateSettings(request);
            return ResponseEntity.ok("Settings saved successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

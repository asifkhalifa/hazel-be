package com.hazelngrazel.app.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hazelngrazel.app.dto.SettingsRequest;
import com.hazelngrazel.app.entity.SettingsEntity;
import com.hazelngrazel.app.repository.SettingsRepository;
import com.hazelngrazel.app.serviceI.SettingsServiceI;

@Service
public class SettingsServiceImpl implements SettingsServiceI {

    @Autowired
    SettingsRepository settingsRepository;

    @Override
    public SettingsEntity getSettings() {
        return settingsRepository.findById(1L).orElseGet(() -> {
            SettingsEntity defaults = new SettingsEntity();
            defaults.setStoreName("Hazel N Grazel");
            defaults.setStoreEmail("support@hazelngrazel.com");
            defaults.setWhatsappNumber("917304823073");
            defaults.setCurrencySymbol("₹");
            defaults.setStoreAddress("123 Sweet Lane, Pastry City");
            defaults.setCardCost(new java.math.BigDecimal("50.00"));
            defaults.setTaxRate(new java.math.BigDecimal("18.0"));
            defaults.setIsMaintenanceMode(false);
            return settingsRepository.save(defaults);
        });
    }

    @Override
    @Transactional
    public void updateSettings(SettingsRequest request) {
        SettingsEntity settings = settingsRepository.findById(1L).orElseGet(() -> {
            SettingsEntity s = new SettingsEntity();
            s.setId(1L);
            return s;
        });

        if (request.getStoreName() != null) settings.setStoreName(request.getStoreName());
        if (request.getStoreEmail() != null) settings.setStoreEmail(request.getStoreEmail());
        if (request.getWhatsappNumber() != null) settings.setWhatsappNumber(request.getWhatsappNumber());
        if (request.getCurrencySymbol() != null) settings.setCurrencySymbol(request.getCurrencySymbol());
        if (request.getStoreAddress() != null) settings.setStoreAddress(request.getStoreAddress());
        if (request.getCardCost() != null) settings.setCardCost(request.getCardCost());
        if (request.getTaxRate() != null) settings.setTaxRate(request.getTaxRate());
        if (request.getIsMaintenanceMode() != null) settings.setIsMaintenanceMode(request.getIsMaintenanceMode());

        settingsRepository.save(settings);
    }
}

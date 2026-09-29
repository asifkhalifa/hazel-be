package com.hazelngrazel.app.entity;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SettingsEntity {

    @Id
    private Long id = 1L;

    private String storeName;
    private String storeEmail;
    private String whatsappNumber;
    private String currencySymbol;

    @Column(columnDefinition = "TEXT")
    private String storeAddress;

    private BigDecimal cardCost;
    private BigDecimal taxRate;
    private Boolean isMaintenanceMode = false;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public BigDecimal getCardCost() {
        return cardCost;
    }

    public void setCardCost(BigDecimal cardCost) {
        this.cardCost = cardCost;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Boolean getIsMaintenanceMode() {
        return isMaintenanceMode;
    }

    public void setIsMaintenanceMode(Boolean isMaintenanceMode) {
        this.isMaintenanceMode = isMaintenanceMode;
    }
}

package com.hazelngrazel.app.dto;

import java.math.BigDecimal;

public class ProductVariantRequest {
	private String type; // STANDARD, CAVITY, PIECES

    private String value; // null, 2,4,6,12

    private BigDecimal price;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
    
    
}

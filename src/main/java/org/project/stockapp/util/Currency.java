package org.project.stockapp.util;

public enum Currency {
	DOLLAR("Dollar"), INR("Rupee"), EURO("Euro");
	private String currencyName;

	Currency(String currencyName) {
		this.currencyName = currencyName;
	}
}

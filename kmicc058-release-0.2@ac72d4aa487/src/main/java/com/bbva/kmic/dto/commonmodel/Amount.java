package com.bbva.kmic.dto.commonmodel;

import java.io.Serializable;

/**
 * Amount structure
 *
 * @author mb94394
 *
 */
public class Amount implements Serializable {

	private static final long serialVersionUID = -947644516901797739L;

	/**
	 * amount, eg 23070.01
	 */
	private double amount;
	/**
	 * currency, eg MXP
	 */
	private String currency;

	public double getAmount() {
		return amount;
	}

	public void setAmount(final double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "Amount [amount=" + amount + ", currency=" + currency + "]";
	}
}

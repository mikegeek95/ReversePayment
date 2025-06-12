package com.bbva.kmic.dto.paymentmodel;

import java.io.Serializable;

/**
 * Payment concepts
 * @author mb94394
 *
 */
public class Payment implements Serializable {

	private static final long serialVersionUID = -7837273195356186007L;

	/**
	 * Amortized capital amount, eg 1360.02
	 */
	private double amortizedCapital;
	/**
	 * Capital repayment amount, eg 5300.00
	 */
	private double capitalRepayment;
	/**
	 * Payment to be made in the period amount (total between the period), eg
	 * 4006.12
	 */
	private double amortizedCredit;
	/**
	 * Amount of the period recovered by payment, eg 25.81
	 */
	private double creditRepayment;
	/**
	 * Interest amount, eg 145.36
	 */
	private double interest;
	/**
	 * Commission amount, eg 250.33
	 */
	private double commission;
	/**
	 * Tax amount, eg 40.71
	 */
	private double tax;
	/**
	 * Balance amount, eg 200100.29
	 */
	private double balance;
	/**
	 * Payment currency, eg MXP
	 */
	private String currency;

	public double getCapitalRepayment() {
		return capitalRepayment;
	}

	public void setCapitalRepayment(final double capitalRepayment) {
		this.capitalRepayment = capitalRepayment;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(final double interest) {
		this.interest = interest;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(final double commission) {
		this.commission = commission;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(final double tax) {
		this.tax = tax;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(final double balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	public double getAmortizedCapital() {
		return amortizedCapital;
	}

	public void setAmortizedCapital(final double amortizedCapital) {
		this.amortizedCapital = amortizedCapital;
	}

	public double getAmortizedCredit() {
		return amortizedCredit;
	}

	public void setAmortizedCredit(final double amortizedCredit) {
		this.amortizedCredit = amortizedCredit;
	}

	public double getCreditRepayment() {
		return creditRepayment;
	}

	public void setCreditRepayment(final double creditRepayment) {
		this.creditRepayment = creditRepayment;
	}

	@Override
	public String toString() {
		return "Payment [amortizedCapital=" + amortizedCapital + ", capitalRepayment=" + capitalRepayment
				+ ", amortizedCredit=" + amortizedCredit + ", creditRepayment=" + creditRepayment + ", interest="
				+ interest + ", commission=" + commission + ", tax=" + tax + ", balance=" + balance + ", currency="
				+ currency + "]";
	}

}

package com.bbva.kmic.dto.payments;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AmountTypeDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private double amount;
	private String status;
	private Date date;
	
	public AmountTypeDto() {
	}

	public AmountTypeDto(double amount, String status, Date date) {
		this.amount = amount;
		this.status = status;
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, date, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AmountTypeDto other = (AmountTypeDto) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(date, other.date) && Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "\nAmountTypeDto [amount=" + amount + ", status=" + status + ", date=" + date + "]";
	}

}

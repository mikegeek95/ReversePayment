package com.bbva.kmic.dto.paymentmodel;

import java.io.Serializable;

import com.bbva.kmic.dto.commonmodel.Pagination;
import com.bbva.kmic.dto.commonmodel.Period;

/**
 * Scheduled payment information to search
 * @author mb94394
 *
 */
public class ScheduledPaymentFilter implements Serializable {

	private static final long serialVersionUID = -8996310693775149516L;

	/**
	 * Scheduled payment status filter value, eg UNPAID
	 */
	private String status;
	/**
	 * Scheduled payment settlement period to search
	 */
	private Period period;
	/**
	 * Parameters to segment the list
	 */
	private Pagination pagination;

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(final Period period) {
		this.period = period;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(final Pagination pagination) {
		this.pagination = pagination;
	}

	@Override
	public String toString() {
		return "ScheduledPaymentFilter [status=" + status + ", period=" + period + ", pagination=" + pagination + "]";
	}

}

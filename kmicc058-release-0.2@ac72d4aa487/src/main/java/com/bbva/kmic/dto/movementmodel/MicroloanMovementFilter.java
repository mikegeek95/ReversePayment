package com.bbva.kmic.dto.movementmodel;

import java.io.Serializable;

import com.bbva.kmic.dto.commonmodel.Pagination;
import com.bbva.kmic.dto.commonmodel.Period;

/**
 * Microloan movement filter information
 *
 * @author mb94394
 *
 */
public class MicroloanMovementFilter implements Serializable {

	private static final long serialVersionUID = -207880719258812710L;

	/**
	 * Microloan movement period filter information
	 */
	private Period period;
	/**
	 * Microloan movement type, eg PGVENCON
	 */
	private String movementType;
	/**
	 * Microloan movement pagination information
	 */
	private Pagination pagination;

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(final Period period) {
		this.period = period;
	}

	public String getMovementType() {
		return movementType;
	}

	public void setMovementType(final String movementType) {
		this.movementType = movementType;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(final Pagination pagination) {
		this.pagination = pagination;
	}

	@Override
	public String toString() {
		return "MicroloanMovementFilter [period=" + period + ", movementType=" + movementType + ", pagination="
				+ pagination + "]";
	}
}

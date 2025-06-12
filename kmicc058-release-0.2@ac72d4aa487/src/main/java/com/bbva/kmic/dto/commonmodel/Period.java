package com.bbva.kmic.dto.commonmodel;

import java.io.Serializable;
import java.util.Date;

/**
 * Microloan movement period filter information
 *
 * @author mb94394
 *
 */
public class Period implements Serializable {

	private static final long serialVersionUID = 2302528498032851257L;

	/**
	 * Initial microloan movement date, eg 2000-01-01
	 */
	private Date initialDate;
	/**
	 * Final microloan movement date, eg 2010-12-31
	 */
	private Date finalDate;

	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(final Date initialDate) {
		this.initialDate = initialDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(final Date finalDate) {
		this.finalDate = finalDate;
	}

	@Override
	public String toString() {
		return "Period [initialDate=" + initialDate + ", finalDate=" + finalDate + "]";
	}
}

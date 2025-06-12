package com.bbva.kmic.dto.commonmodel;

import java.io.Serializable;

/**
 * account event information
 *
 * @author mb94394
 *
 */
public class AccountEvent implements Serializable {

	private static final long serialVersionUID = 7737362121605614379L;

	/**
	 * Microloan movement account event code, eg PGRECCON
	 */
	private String code;
	/**
	 * Microloan movement account event status, eg Y
	 */
	private String status;
	/**
	 * Regional center information
	 */
	private RegionalCenter regionalCenter;

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public RegionalCenter getRegionalCenter() {
		return regionalCenter;
	}

	public void setRegionalCenter(final RegionalCenter regionalCenter) {
		this.regionalCenter = regionalCenter;
	}


	@Override
	public String toString() {
		return "AccountEvent [code=" + code + ", status=" + status + ", regionalCenter=" + regionalCenter + "]";
	}
}

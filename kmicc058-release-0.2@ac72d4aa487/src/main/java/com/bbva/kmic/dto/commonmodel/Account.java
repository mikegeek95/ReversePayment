package com.bbva.kmic.dto.commonmodel;

import java.io.Serializable;
import java.util.Date;

/**
 * Account structure
 *
 * @author mb94394
 *
 */
public class Account implements Serializable {

	private static final long serialVersionUID = -8644891121684249949L;

	/**
	 * Accounting concept to which the movement is associated, eg 01050607
	 */
	private String positionNumber;
	/**
	 * Account movement number, eg RECCON00002121960095848920240628125558
	 */
	private String number;
	/**
	 * Account date, eg 2020-12-31
	 */
	private Date date;
	/**
	 * Account codification, eg 92CFX10000000F32000000000000000000000MXP
	 */
	private String coded;
	/**
	 * Account event information
	 */
	private AccountEvent event;

	public String getPositionNumber() {
		return positionNumber;
	}

	public void setPositionNumber(final String positionNumber) {
		this.positionNumber = positionNumber;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public String getCoded() {
		return coded;
	}

	public void setCoded(final String coded) {
		this.coded = coded;
	}

	public AccountEvent getEvent() {
		return event;
	}

	public void setEvent(final AccountEvent event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "Account [positionNumber=" + positionNumber + ", number=" + number + ", date=" + date + ", coded="
				+ coded + ", event=" + event + "]";
	}

}

package com.bbva.kmic.dto.commonmodel;

import java.io.Serializable;

/**
 * Account event parameter information
 *
 * @author mb94394
 *
 */
public class AccountEventParameter extends TuplaAbstract implements Serializable {

	private static final long serialVersionUID = -7453634157615262007L;

	/**
	 * Account event code, eg GCIVACOB
	 */
	private String eventCode;

	/**
	 * Account event description, eg GENERACION IVA GASTOS DE COBRANZA
	 */
	private String eventDescription;
	/**
	 * Indicator of account, possible values Y or N
	 */
	private String eventIndicator;
	/**
	 * Account event operation identifier, eg FAC
	 */
	private String operationId;
	/**
	 * Account position number identifier, eg 0305
	 */
	private String positionId;

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(final String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(final String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getEventIndicator() {
		return eventIndicator;
	}

	public void setEventIndicator(final String eventIndicator) {
		this.eventIndicator = eventIndicator;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(final String operationId) {
		this.operationId = operationId;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(final String positionId) {
		this.positionId = positionId;
	}

	@Override
	public String toString() {
		return "AccountEventParameter [eventCode=" + eventCode + ", eventDescription=" + eventDescription
				+ ", eventIndicator=" + eventIndicator + ", operationId=" + operationId + ", positionId=" + positionId
				+ "]";
	}

}

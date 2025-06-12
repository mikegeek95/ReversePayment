package com.bbva.kmic.dto.commonmodel;

import java.io.Serializable;

/**
 * Regional center information
 *
 * @author mb94394
 *
 */
public class RegionalCenter implements Serializable {

	private static final long serialVersionUID = 3736541026506042422L;

	/**
	 * Destiny Regional Center, eg 2022
	 */
	private String destiny;
	/**
	 * Operative regional center, eg 1001
	 */
	private String operative;
	/**
	 * Regional center of origin, eg 0003
	 */
	private String origin;

	public String getDestiny() {
		return destiny;
	}

	public void setDestiny(final String destiny) {
		this.destiny = destiny;
	}

	public String getOperative() {
		return operative;
	}

	public void setOperative(final String operative) {
		this.operative = operative;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(final String origin) {
		this.origin = origin;
	}

	@Override
	public String toString() {
		return "RegionalCenter [destiny=" + destiny + ", operative=" + operative + ", origin=" + origin + "]";
	}

}

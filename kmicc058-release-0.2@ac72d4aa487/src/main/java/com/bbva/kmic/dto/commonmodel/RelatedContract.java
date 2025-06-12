package com.bbva.kmic.dto.commonmodel;

import java.io.Serializable;

/**
 * Related contract structure
 *
 * @author mb94394
 *
 */
public class RelatedContract implements Serializable {

	private static final long serialVersionUID = 8680954260144091369L;

	/**
	 * Related contract type, eg CCAR
	 */
	private String contractType;
	/**
	 * Related contract number, eg 00740010001502118284
	 */
	private String number;

	public String getContractType() {
		return contractType;
	}

	public void setContractType(final String contractType) {
		this.contractType = contractType;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "RelatedContract [contractType=" + contractType + ", number=" + number + "]";
	}

}

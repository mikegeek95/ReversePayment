package com.bbva.kmic.dto.commonmodel;

import java.io.Serializable;
import java.util.Calendar;

public class Audit implements Serializable {

	private static final long serialVersionUID = 4285991071611128669L;

	private String userCode;
	private Calendar timestamp = Calendar.getInstance();

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(final String userCode) {
		this.userCode = userCode;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final Calendar timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Audit [userCode=" + userCode + ", timestamp=" + timestamp + "]";
	}


}

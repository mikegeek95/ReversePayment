package com.bbva.kmic.dto.commonmodel;

public abstract class TuplaAbstract {
	protected Audit audit;

	public Audit getAudit() {
		return audit;
	}

	public void setAudit(final Audit audit) {
		this.audit = audit;
	}

	@Override
	public String toString() {
		return "[audit=" + audit + "]";
	}

}

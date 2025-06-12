package com.bbva.kmic.dto.commonmodel;

import java.io.Serializable;

/**
 * Pagination information
 *
 * @author mb94394
 *
 */
public class Pagination implements Serializable {

	private static final long serialVersionUID = 420220829608768750L;

	/**
	 * First row number in the list
	 */
	private int firstRow;
	/**
	 * Number of records in the list. The number of records must be greater than 0
	 * and lesser than 5000
	 */
	private int pageSize;

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(final int firstRow) {
		this.firstRow = firstRow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "Pagination [firstRow=" + firstRow + ", pageSize=" + pageSize + "]";
	}
}

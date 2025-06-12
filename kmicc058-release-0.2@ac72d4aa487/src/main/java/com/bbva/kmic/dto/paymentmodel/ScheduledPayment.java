package com.bbva.kmic.dto.paymentmodel;

import java.io.Serializable;
import java.util.Date;

import com.bbva.kmic.dto.commonmodel.RegionalCenter;
import com.bbva.kmic.dto.commonmodel.TuplaAbstract;

/**
 * Scheduled payment information
 *
 * @author mb94394
 *
 */
public class ScheduledPayment extends TuplaAbstract implements Serializable {

	private static final long serialVersionUID = -141051152414111549L;

	/**
	 * Scheduled payment contract identifier, eg MX007400219200013431
	 */
	private String contractId;
	/**
	 * Scheduled payment microloan identifier, eg 9287072197D0432336
	 */
	private String microloanId;
	/**
	 * Scheduled payment sequence, eg 1. This sequence allow to order the scheduled
	 * payments of the same contract
	 */
	private int sequence;
	/**
	 * Settlement date of the sheduled payment, eg 2024-01-31
	 */
	private Date settlementDate;
	/**
	 * Payment concepts
	 */
	private Payment payment;
	/**
	 * Scheduled payment status, eg SETTLED
	 */
	private String status;
	/**
	 * Channel code, eg MG
	 */
	private String channelCode;
	/**
	 * Regional center information
	 */
	private RegionalCenter regionalCenter;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(final String contractId) {
		this.contractId = contractId;
	}

	public String getMicroloanId() {
		return microloanId;
	}

	public void setMicroloanId(final String microloanId) {
		this.microloanId = microloanId;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(final int sequence) {
		this.sequence = sequence;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(final Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(final Payment payment) {
		this.payment = payment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(final String channelCode) {
		this.channelCode = channelCode;
	}

	public RegionalCenter getRegionalCenter() {
		return regionalCenter;
	}

	public void setRegionalCenter(final RegionalCenter regionalCenter) {
		this.regionalCenter = regionalCenter;
	}

	@Override
	public String toString() {
		return "ScheduledPayment [contractId=" + contractId + ", microloanId=" + microloanId + ", sequence=" + sequence
				+ ", settlementDate=" + settlementDate + ", payment=" + payment + ", status=" + status
				+ ", channelCode=" + channelCode + ", regionalCenter=" + regionalCenter + " " + super.toString() + "]";
	}

}

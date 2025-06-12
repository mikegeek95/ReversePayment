package com.bbva.kmic.dto.movementmodel;

import java.io.Serializable;
import java.util.Date;

import com.bbva.kmic.dto.commonmodel.Account;
import com.bbva.kmic.dto.commonmodel.Amount;
import com.bbva.kmic.dto.commonmodel.RelatedContract;
import com.bbva.kmic.dto.commonmodel.TuplaAbstract;

/**
 * Microloan movement information
 *
 * @author mb94394
 *
 */
public class MicroloanMovement extends TuplaAbstract implements Serializable {

	private static final long serialVersionUID = -8848668857318577704L;

	/**
	 * Microloan movement contract identifier, eg MX007400219200042121
	 */
	private String contractId;
	/**
	 * Microloan identificator, eg 110199998853863569
	 */
	private String microloanId;
	/**
	 * Microloan movement number, difference between movements of the same
	 * microloan, eg 2234403
	 */
	private int number;
	/**
	 * Microloan movement date, eg 2020-10-31
	 */
	private Date date;
	/**
	 * Microloan movement amount information
	 */
	private Amount amount;
	/**
	 * Microloan movement accounting information
	 */
	private Account account;
	/**
	 * Microloan movement installment date. eg 2021-01-01
	 */
	private Date installmentDate;
	/**
	 * Microloan movement related contract information
	 */
	private RelatedContract relatedContract;
	/**
	 * If the movement was carried out interbank this is the description, eg Pago de
	 * mensualidad
	 */
	private String interbankTrackingDescription;
	/**
	 * Channel code through which the movement was executed, eg MG
	 */
	private String channelCode;

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

	public int getNumber() {
		return number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(final Amount amount) {
		this.amount = amount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}

	public Date getInstallmentDate() {
		return installmentDate;
	}

	public void setInstallmentDate(final Date installmentDate) {
		this.installmentDate = installmentDate;
	}

	public RelatedContract getRelatedContract() {
		return relatedContract;
	}

	public void setRelatedContract(final RelatedContract relatedContract) {
		this.relatedContract = relatedContract;
	}

	public String getInterbankTrackingDescription() {
		return interbankTrackingDescription;
	}

	public void setInterbankTrackingDescription(final String interbankTrackingDescription) {
		this.interbankTrackingDescription = interbankTrackingDescription;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(final String channelCode) {
		this.channelCode = channelCode;
	}

	@Override
	public String toString() {
		return "MicroloanMovement [contractId=" + contractId + ", microloanId=" + microloanId + ", number=" + number
				+ ", date=" + date + ", amount=" + amount + ", account=" + account + ", installmentDate="
				+ installmentDate + ", relatedContract=" + relatedContract + ", interbankTrackingDescription="
				+ interbankTrackingDescription + ", channelCode=" + channelCode + " " + super.toString() + "]";
	}

}

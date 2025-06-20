package com.bbva.kmic.dto.payments;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;


public class ProductInputDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6211429429746516042L;
	
	private String contractId;            // G_CONTRACT_ID
	public String getContractId() {
		return contractId;
	}


	public void setContractId(String contractId) {
		this.contractId = contractId;
	}


	public String getMicroloanId() {
		return microloanId;
	}


	public void setMicroloanId(String microloanId) {
		this.microloanId = microloanId;
	}


	public Date getInstallmentDate() {
		return installmentDate;
	}


	public void setInstallmentDate(Date installmentDate) {
		this.installmentDate = installmentDate;
	}


	public double getAmount() {
		///pago completo
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	private String microloanId;           // GF_OPERATION_PAGE_ID
	private Date installmentDate;         // GF_INSTALLMENT_PERIOD_DATE
	private double amount;            // GF_MOVEMENT_AMOUNT
	private double amountCapital;            // GF_MOVEMENT_AMOUNT
	
	public double getAmountCapital() {
		return amountCapital;
	}


	public void setAmountCapital(double amountCapital) {
		this.amountCapital = amountCapital;
	}


	private double amountComision;            // GF_MOVEMENT_AMOUNT
	public double getAmountComision() {
		return amountComision;
	}


	public void setAmountComision(double amountComision) {
		this.amountComision = amountComision;
	}

	
	private double amountIva;            // GF_MOVEMENT_AMOUNT
	public double getAmountIva() {
		return amountIva;
	}


	public void setAmountIva(double amountIva) {
		this.amountIva = amountIva;
	}
	 
	



	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	    String formattedDate = installmentDate != null ? sdf.format(installmentDate) : "null";
	    
	    return "ProductInputDTO{" +
	            "contractId='" + contractId + '\'' +
	            ", microloanId='" + microloanId + '\'' +
	            ", installmentDate=" + formattedDate  +
	            ", amount=" + amount +
	            ", amountCapital=" + amountCapital +
	            ", amountComision=" + amountComision +
	            ", amountIva=" + amountIva +
	            '}';
	}

	

}

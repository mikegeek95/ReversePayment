package com.bbva.kmic.dto.payments;

import java.io.Serializable;
import java.util.Date;


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
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	private String microloanId;           // GF_OPERATION_PAGE_ID
	private Date installmentDate;         // GF_INSTALLMENT_PERIOD_DATE
	private double amount;            // GF_MOVEMENT_AMOUNT
	private String tipoMovimiento;  

    public String getTipoMovimiento() {
		return tipoMovimiento;
	}


	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}


	@Override
    public String toString() {
        return "DTO[contractId=" + contractId + ", microloanId=" + microloanId + ", installmentDate=" + installmentDate + ", amount=" + amount + ", tipoMovimiento=" + tipoMovimiento + "]";
    }
	

}

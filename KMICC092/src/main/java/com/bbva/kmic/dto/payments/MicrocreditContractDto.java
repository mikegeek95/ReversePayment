package com.bbva.kmic.dto.payments;

import java.io.Serializable;
import java.util.Objects;

public class MicrocreditContractDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7439865192079018676L;
	
	private String contractId;
	private String contractStatus;
	private Double grantedAmount;
	private Double drwdnAmount;
	private Double availableAmount;
	
	public MicrocreditContractDto() {
	}
	
	public MicrocreditContractDto(String contractId, String contractStatus, Double grantedAmount, Double drwdnAmount,
			Double availableAmount) {
		this.contractId = contractId;
		this.contractStatus = contractStatus;
		this.grantedAmount = grantedAmount;
		this.drwdnAmount = drwdnAmount;
		this.availableAmount = availableAmount;
	}

	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	public Double getGrantedAmount() {
		return grantedAmount;
	}
	public void setGrantedAmount(Double grantedAmount) {
		this.grantedAmount = grantedAmount;
	}
	public Double getDrwdnAmount() {
		return drwdnAmount;
	}
	public void setDrwdnAmount(Double drwdnAmount) {
		this.drwdnAmount = drwdnAmount;
	}
	public Double getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(Double availableAmount) {
		this.availableAmount = availableAmount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(availableAmount, contractId, contractStatus, drwdnAmount, grantedAmount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MicrocreditContractDto other = (MicrocreditContractDto) obj;
		return Objects.equals(availableAmount, other.availableAmount) && Objects.equals(contractId, other.contractId)
				&& Objects.equals(contractStatus, other.contractStatus)
				&& Objects.equals(drwdnAmount, other.drwdnAmount) && Objects.equals(grantedAmount, other.grantedAmount);
	}

	@Override
	public String toString() {
		return "MicrocreditContractDto [contractId=" + contractId + ", contractStatus=" + contractStatus
				+ ", grantedAmount=" + grantedAmount + ", drwdnAmount=" + drwdnAmount + ", availableAmount="
				+ availableAmount + "]";
	}

}

package com.bbva.kmic.dto.payments;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class DspnAmortDto implements Serializable{

	private static final long serialVersionUID = 7139233022758482115L;
	
	private String contractId;
	private String operationId;
	private String sequenceId;
	private Date itemDate;
	private Double capAmountTotal;
	private Double capAmountPay;
	private Double crAmountTotal;
	private Double crAmountPay;
	private String statusType;
	


	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Date getItemDate() {
		return itemDate;
	}

	public void setItemDate(Date itemDate) {
		this.itemDate = itemDate;
	}

	public Double getCapAmountTotal() {
		return capAmountTotal;
	}

	public void setCapAmountTotal(Double capAmountTotal) {
		this.capAmountTotal = capAmountTotal;
	}

	public Double getCapAmountPay() {
		return capAmountPay;
	}

	public void setCapAmountPay(Double capAmountPay) {
		this.capAmountPay = capAmountPay;
	}

	public Double getCrAmountTotal() {
		return crAmountTotal;
	}

	public void setCrAmountTotal(Double crAmountTotal) {
		this.crAmountTotal = crAmountTotal;
	}

	public Double getCrAmountPay() {
		return crAmountPay;
	}

	public void setCrAmountPay(Double crAmountPay) {
		this.crAmountPay = crAmountPay;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(capAmountPay, capAmountTotal, contractId, crAmountPay, crAmountTotal, itemDate, operationId,
				sequenceId, statusType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DspnAmortDto other = (DspnAmortDto) obj;
		return Objects.equals(capAmountPay, other.capAmountPay) && Objects.equals(capAmountTotal, other.capAmountTotal)
				&& Objects.equals(contractId, other.contractId) && Objects.equals(crAmountPay, other.crAmountPay)
				&& Objects.equals(crAmountTotal, other.crAmountTotal) && Objects.equals(itemDate, other.itemDate)
				&& Objects.equals(operationId, other.operationId) && Objects.equals(sequenceId, other.sequenceId)
				&& Objects.equals(statusType, other.statusType);
	}

	@Override
	public String toString() {
		return "DspnAmortDto [contractId=" + contractId + ", operationId=" + operationId + ", sequenceId=" + sequenceId
				+ ", itemDate=" + itemDate + ", capAmountTotal=" + capAmountTotal + ", capAmountPay=" + capAmountPay
				+ ", crAmountTotal=" + crAmountTotal + ", crAmountPay=" + crAmountPay + ", statusType=" + statusType
				+ "]\n";
	}

}

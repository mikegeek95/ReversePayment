package com.bbva.kmic.dto.payments;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ProductInputDTO implements Serializable {

    private static final long serialVersionUID = -6211429429746516042L;

    // === Atributos principales ===
    private String contractId;         // G_CONTRACT_ID
    private String microloanId;        // GF_OPERATION_PAGE_ID
    private Date installmentDate;      // GF_INSTALLMENT_PERIOD_DATE

    private double amount;             // GF_MOVEMENT_AMOUNT (pago completo)
    private double amountCapital;      // GF_MOVEMENT_AMOUNT (capital)
    private double amountComision;     // GF_MOVEMENT_AMOUNT (comisión)
    private double amountIva; 
    private double amountAutomatico; 
    private double amountIvaCobranza;
    private double amountCapCobranza;
    private String movId;
    private int sequenceId;
    
    public int getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(int sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getMovId() {
		return movId;
	}

	public void setMovId(String movId) {
		this.movId = movId;
	}


	
    
    public double getAmountIvaCobranza() {
		return amountIvaCobranza;
	}

	public void setAmountIvaCobranza(double amountIvaCobranza) {
		this.amountIvaCobranza = amountIvaCobranza;
	}

	public double getAmountCapCobranza() {
		return amountCapCobranza;
	}

	public void setAmountCapCobranza(double amountCapCobranza) {
		this.amountCapCobranza = amountCapCobranza;
	}

    // GF_MOVEMENT_AMOUNT (IVA)

    // === Getters y Setters ===
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

    public double getAmountCapital() {
        return amountCapital;
    }

    public void setAmountCapital(double amountCapital) {
        this.amountCapital = amountCapital;
    }

    public double getAmountComision() {
        return amountComision;
    }

    public void setAmountComision(double amountComision) {
        this.amountComision = amountComision;
    }

    public double getAmountIva() {
        return amountIva;
    }

    public void setAmountIva(double amountIva) {
        this.amountIva = amountIva;
    }

    public double getAmountAutomatico() {
        return amountAutomatico;
    }

    public void setAmountAutomatico(double amountAutomatico) {
        this.amountAutomatico = amountAutomatico;
    }
    
    // === Representación textual ===
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String formattedDate = (installmentDate != null) ? sdf.format(installmentDate) : "null";

        return "ProductInputDTO{" +
                "contractId='" + contractId + '\'' +
                ", microloanId='" + microloanId + '\'' +
                ", installmentDate=" + formattedDate +
                ", amount=" + amount +
                ", amountCapital=" + amountCapital +
                ", amountComision=" + amountComision +
                ", amountIva=" + amountIva +
                ", amountAutomatico=" + amountAutomatico +
                ", amountIvaCobranza="+ amountIvaCobranza+
                ", amountCapCobranza=" +amountCapCobranza+
                ", movId=" + movId+
                ", sequenceId=" +sequenceId+
                '}';
    }
}

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
    private double amountIva;          // GF_MOVEMENT_AMOUNT (IVA)

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
                '}';
    }
}

package com.bbva.kmic.dto.conditionmodel;

import java.io.Serializable;
import java.util.Date;

import com.bbva.kmic.dto.commonmodel.Account;
import com.bbva.kmic.dto.commonmodel.Amount;
import com.bbva.kmic.dto.commonmodel.Balances;
import com.bbva.kmic.dto.commonmodel.Product;
import com.bbva.kmic.dto.commonmodel.RegionalCenter;
import com.bbva.kmic.dto.commonmodel.Tax;
import com.bbva.kmic.dto.commonmodel.TuplaAbstract;

/**
 * Contract condition information.
 *
 * @author mb94394
 *
 */
public class ContractCondition extends TuplaAbstract implements Serializable {
    private static final long serialVersionUID = 2168786521530691350L;

    /**
     * Contract identifier, eg MX007400219200013431
     */
    private String contractId;
    /**
     * Concept identifier applied to the contract condition, eg COLLECTION_EXPENSES
     */
    private String conceptId;
    /**
     * Period in which the contract condition is generated, eg 2000-11-20
     */
    private Date installmentDate;
    /**
     * Number that differentiates the conditions of a contract, eg 100
     */
    private int number;
    /**
     * Operation identifier, eg 010199999950857603
     */
    private String operationId;
    /**
     * Status of the contract condition, eg ACTIVE
     */
    private String status;
    /**
     * Channel identifier through which the contract condition is generated, eg MG
     */
    private String channelId;
    /**
     * Information on the conditions offered at the time of contracting the product
     */
    private OfferCondition offer;
    /**
     * Amount information to contract condition
     */
    private Amount amount;
    /**
     * Percentage to be applied to the outstanding balance of the debt to obtain the
     * amount of the contract condition; eg For a debt of 1000 with a percentage of
     * 10%, the amount is 100
     */
    private Double percentage;
    /**
     * Accounting information on the condition of the contract
     */
    private Account account;
    /**
     * Balances of the contract condition
     */
    private Balances balances;
    /**
     * Information on the tax applied
     */
    private Tax tax;
    /**
     * Information on regional accounting centres
     */
    private RegionalCenter regionalCenter;
    /**
     * Product information
     */
    private Product product;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(final String contractId) {
        this.contractId = contractId;
    }

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(final String conceptId) {
        this.conceptId = conceptId;
    }

    public Date getInstallmentDate() {
        return installmentDate;
    }

    public void setInstallmentDate(final Date installmentDate) {
        this.installmentDate = installmentDate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(final String operationId) {
        this.operationId = operationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(final String channelId) {
        this.channelId = channelId;
    }

    public OfferCondition getOffer() {
        return offer;
    }

    public void setOffer(final OfferCondition offer) {
        this.offer = offer;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(final Amount amount) {
        this.amount = amount;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(final Double percentage) {
        this.percentage = percentage;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public Balances getBalances() {
        return balances;
    }

    public void setBalances(final Balances balances) {
        this.balances = balances;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(final Tax tax) {
        this.tax = tax;
    }

    public RegionalCenter getRegionalCenter() {
        return regionalCenter;
    }

    public void setRegionalCenter(final RegionalCenter regionalCenter) {
        this.regionalCenter = regionalCenter;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ContractCondition [contractId=" + contractId + ", conceptId=" + conceptId + ", installmentDate="
                + installmentDate + ", number=" + number + ", operationId=" + operationId + ", status=" + status
                + ", channelId=" + channelId + ", offer=" + offer + ", amount=" + amount + ", percentage=" + percentage
                + ", account=" + account + ", balances=" + balances + ", tax=" + tax + ", regionalCenter="
                + regionalCenter + ", product=" + product + "]";
    }

}

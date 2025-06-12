package com.bbva.kmic.dto.commonmodel;

import java.io.Serializable;

public class Tax implements Serializable {

    private static final long serialVersionUID = -6330331638241657739L;

    /**
     * Tax rate applied to the contract condition; the rate must be expressed in
     * decimal format, e.g. 0.01 = 1%
     */
    private double percentage;
    /**
     * Tax amount information
     */
    private Amount amount;
    /**
     * Balances of the tax of contract condition
     */
    private Balances balances;
    
    public double getPercentage() {
        return percentage;
    }
    
    public void setPercentage(final double percentage) {
        this.percentage = percentage;
    }
    
    public Amount getAmount() {
        return amount;
    }
    
    public void setAmount(final Amount amount) {
        this.amount = amount;
    }
    
    public Balances getBalances() {
        return balances;
    }
    
    public void setBalances(final Balances balances) {
        this.balances = balances;
    }
    
    @Override
    public String toString() {
        return "Tax [percentage=" + percentage + ", amount=" + amount + ", balances=" + balances + "]";
    }
}

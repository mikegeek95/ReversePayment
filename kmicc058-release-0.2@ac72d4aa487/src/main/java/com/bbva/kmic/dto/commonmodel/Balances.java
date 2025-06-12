package com.bbva.kmic.dto.commonmodel;

import java.io.Serializable;

public class Balances implements Serializable {

    private static final long serialVersionUID = -1288407653398938626L;

    /**
     * Outstanding amount applied to contract condition, eg 100
     */
    private double outstanding;
    /**
     * Recovered amount of the contract condition, eg 20
     */
    private double recovered;
    
    public double getOutstanding() {
        return outstanding;
    }
    
    public void setOutstanding(final double outstanding) {
        this.outstanding = outstanding;
    }
    
    public double getRecovered() {
        return recovered;
    }
    
    public void setRecovered(final double recovered) {
        this.recovered = recovered;
    }
    
    @Override
    public String toString() {
        return "Balances [outstanding=" + outstanding + ", recovered=" + recovered + "]";
    }
    
}

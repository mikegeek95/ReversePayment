package com.bbva.kmic.dto.commonmodel;

import java.io.Serializable;

/**
 * Product information
 *
 * @author mb94394
 *
 */
public class Product implements Serializable {
    
    private static final long serialVersionUID = -6634540241304223915L;

    /**
     * Product identificator
     */
    private String id;
    /**
     * Subproduct identificator
     */
    private String subproductId;
    
    public String getId() {
        return id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public String getSubproductId() {
        return subproductId;
    }
    
    public void setSubproductId(final String subproductId) {
        this.subproductId = subproductId;
    }
}

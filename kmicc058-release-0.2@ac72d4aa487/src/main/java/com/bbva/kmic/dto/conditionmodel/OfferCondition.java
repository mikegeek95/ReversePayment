package com.bbva.kmic.dto.conditionmodel;

import java.io.Serializable;

public class OfferCondition implements Serializable {

    private static final long serialVersionUID = -4449477347808563260L;

    /**
     * Identifier of the conditions agreed upon at the time of contracting the
     * product, eg OFFER1
     */
    private String id;
    /**
     * Identifier of the applied condition that was agreed upon when contracting the
     * product, eg 24_COLLECTION_EXPENSES
     */
    private String conditionId;
    /**
     * Identifier of the version applied to the contracting conditions, eg 1
     */
    private String versionId;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(final String conditionId) {
        this.conditionId = conditionId;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(final String versionId) {
        this.versionId = versionId;
    }

    @Override
    public String toString() {
        return "OfferCondition [id=" + id + ", conditionId=" + conditionId + ", versionId=" + versionId + "]";
    }

}

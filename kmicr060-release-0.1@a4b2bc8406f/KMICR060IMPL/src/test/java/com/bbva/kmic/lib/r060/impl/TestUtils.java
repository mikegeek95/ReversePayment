package com.bbva.kmic.lib.r060.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;

import com.bbva.kmic.dto.commonmodel.Account;
import com.bbva.kmic.dto.commonmodel.AccountEvent;
import com.bbva.kmic.dto.commonmodel.Amount;
import com.bbva.kmic.dto.commonmodel.RegionalCenter;
import com.bbva.kmic.dto.commonmodel.RelatedContract;
import com.bbva.kmic.dto.movementmodel.MicroloanMovement;

public class TestUtils {
	public static List<Map<String, Object>> mockListFull() {
		final List<Map<String, Object>> list = new ArrayList<>();
		for (int p = 0; p < 1001; p++) {
			list.add(mockBeanFull());
		}
		return list;
	}

	public static Map<String, Object> mockBeanFull() {
		final Map<String, Object> fieldSet = new HashMap<>();
		fieldSet.put("G_CONTRACT_ID", RandomStringUtils.randomAlphanumeric(50));
		fieldSet.put("GF_MOV_DATE", new Date());
		fieldSet.put("GF_SEQUENCE_ID", RandomUtils.nextInt(9900080));
		fieldSet.put("GF_OPERATION_PAGE_ID", RandomStringUtils.randomAlphanumeric(20));
		fieldSet.put("GF_ACCT_MOV_ID", RandomStringUtils.randomAlphanumeric(40));
		fieldSet.put("G_MICROCREDIT_MOVEMENT_TYPE", RandomStringUtils.randomAlphabetic(20));
		fieldSet.put("GF_MOVEMENT_AMOUNT", RandomUtils.nextFloat());
		fieldSet.put("G_CURRENCY_ID", RandomStringUtils.randomAlphabetic(3));
		fieldSet.put("G_ACTIVE_MOVEMENT_IND_TYPE", RandomUtils.nextFloat());
		fieldSet.put("GF_GL_ACCOUNT_DATE", new Date());
		fieldSet.put("GF_INSTALLMENT_PERIOD_DATE", "2000-03-25");
		fieldSet.put("GF_GL_ACCOUNTING_STRING_ID", RandomStringUtils.randomAlphabetic(200));
		fieldSet.put("G_LOCAL_CONTRACT_TYPE", RandomStringUtils.randomAlphanumeric(8));
		fieldSet.put("GF_MOV_ASSO_ACCOUNT_ID", RandomStringUtils.randomAlphanumeric(50));
		fieldSet.put("GF_TRACKING_TRANSACTION_DESC", RandomStringUtils.randomAlphanumeric(1000));
		fieldSet.put("GF_ACCOUNTING_POSITION_ID", RandomStringUtils.randomAlphanumeric(50));
		fieldSet.put("GF_NEW_CUST_REGIONAL_CENTRE_ID", RandomStringUtils.randomNumeric(4));
		fieldSet.put("GF_APP_CHANNEL_ID", RandomStringUtils.randomAlphanumeric(4));
		fieldSet.put("GF_CUSTOMER_PROPOSAL_BRANCH_ID", RandomStringUtils.randomNumeric(4));
		fieldSet.put("GF_CREDIT_REQUEST_ORIGIN_RC_ID", RandomStringUtils.randomNumeric(4));
		fieldSet.put("GF_USER_AUDIT_ID", RandomStringUtils.randomAlphanumeric(8));
		fieldSet.put("GF_AUDIT_DATE", Timestamp.from(Instant.now()));
		return fieldSet;
	}
    
    public static List<MicroloanMovement> mockListMovements(final int elements) {
        final List<MicroloanMovement> list = new ArrayList<>();
        
        for (int i = 0; i < elements; i++) {
            list.add(mockMovement());
        }
        
        return list;
    }
    
    public static MicroloanMovement mockMovement() {
        final MicroloanMovement mm = new MicroloanMovement();
        mm.setAccount(new Account());
        mm.getAccount().setEvent(new AccountEvent());
        mm.getAccount().getEvent().setRegionalCenter(new RegionalCenter());
        mm.setAmount(new Amount());
        mm.setRelatedContract(new RelatedContract());
        mm.setChannelCode(RandomStringUtils.randomAlphanumeric(4));
        mm.setContractId(RandomStringUtils.randomNumeric(20));
        return mm;
    }
}

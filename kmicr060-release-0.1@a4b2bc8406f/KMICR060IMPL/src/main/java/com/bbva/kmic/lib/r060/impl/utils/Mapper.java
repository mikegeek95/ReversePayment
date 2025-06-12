package com.bbva.kmic.lib.r060.impl.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bbva.kmic.dto.commonmodel.Account;
import com.bbva.kmic.dto.commonmodel.AccountEvent;
import com.bbva.kmic.dto.commonmodel.Amount;
import com.bbva.kmic.dto.commonmodel.Audit;
import com.bbva.kmic.dto.commonmodel.RegionalCenter;
import com.bbva.kmic.dto.commonmodel.RelatedContract;
import com.bbva.kmic.dto.movementmodel.MicroloanMovement;

public class Mapper {
	private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");

	private Mapper() {
		// sonar constructor
	}

	public static List<MicroloanMovement> mapListMicroloanMovement(final List<Map<String, Object>> resultSet) {
		final List<MicroloanMovement> list = new ArrayList<>();

		if (resultSet != null) {
			resultSet.forEach(row -> list.add(mapMicroloanMovement(row)));
		}
		return list;
	}

	public static MicroloanMovement mapMicroloanMovement(final Map<String, Object> row) {
		final MicroloanMovement mm = new MicroloanMovement();
		mm.setAmount(new Amount());
		mm.setAccount(new Account());
		mm.getAccount().setEvent(new AccountEvent());
		mm.getAccount().getEvent().setRegionalCenter(new RegionalCenter());
		mm.setRelatedContract(new RelatedContract());
		mm.setAudit(new Audit());
		mm.setContractId(readString(row.get("G_CONTRACT_ID")));
		mm.setDate(readDate(row.get("GF_MOV_DATE")));
		mm.setNumber(readInt(row.get("GF_SEQUENCE_ID")));
		mm.setMicroloanId(readString(row.get("GF_OPERATION_PAGE_ID")));
		mm.getAccount().setNumber(readString(row.get("GF_ACCT_MOV_ID")));
		mm.getAccount().getEvent().setCode(readString(row.get("G_MICROCREDIT_MOVEMENT_TYPE")));
		mm.getAmount().setAmount(readDouble(row.get("GF_MOVEMENT_AMOUNT")));
		mm.getAmount().setCurrency(readString(row.get("G_CURRENCY_ID")));
		mm.getAccount().getEvent().setStatus(readString(row.get("G_ACTIVE_MOVEMENT_IND_TYPE")));
		mm.getAccount().setDate(readDate(row.get("GF_GL_ACCOUNT_DATE")));
		mm.setInstallmentDate(readDate(row.get("GF_INSTALLMENT_PERIOD_DATE")));
		mm.getAccount().setCoded(readString(row.get("GF_GL_ACCOUNTING_STRING_ID")));
		mm.getRelatedContract().setContractType(readString(row.get("G_LOCAL_CONTRACT_TYPE")));
		mm.getRelatedContract().setNumber(readString(row.get("GF_MOV_ASSO_ACCOUNT_ID")));
		mm.setInterbankTrackingDescription(readString(row.get("GF_TRACKING_TRANSACTION_DESC")));
		mm.getAccount().setPositionNumber(readString(row.get("GF_ACCOUNTING_POSITION_ID")));
		mm.getAccount().getEvent().getRegionalCenter()
				.setDestiny(readString(row.get("GF_NEW_CUST_REGIONAL_CENTRE_ID")));
		mm.setChannelCode(readString(row.get("GF_APP_CHANNEL_ID")));
		mm.getAccount().getEvent().getRegionalCenter()
				.setOperative(readString(row.get("GF_CUSTOMER_PROPOSAL_BRANCH_ID")));
		mm.getAccount().getEvent().getRegionalCenter().setOrigin(readString(row.get("GF_CREDIT_REQUEST_ORIGIN_RC_ID")));
		mm.getAudit().setUserCode(readString(row.get("GF_USER_AUDIT_ID")));
		mm.getAudit().setTimestamp(readTimestamp(row.get("GF_AUDIT_DATE")));
		return mm;
	}

	public static Map<String, Object> hashMicroloanMovement(final MicroloanMovement bean) {
		final Map<String, Object> map = new HashMap<>();
		map.put("contractId", bean.getContractId());
		map.put("date", bean.getDate());
		map.put("number", bean.getNumber());
		map.put("microloanId", bean.getMicroloanId());
		map.put("interbankDesc", bean.getInterbankTrackingDescription());
		map.put("installmentDate", bean.getInstallmentDate());
		map.put("channel", bean.getChannelCode());
		if (bean.getAccount() != null) {
			map.put("accountNumber", bean.getAccount().getNumber());
			map.put("accountDate", bean.getAccount().getDate());
			map.put("accountCode", bean.getAccount().getCoded());
			map.put("accountPosition", bean.getAccount().getPositionNumber());
			if (bean.getAccount().getEvent() != null) {
				map.put("eventCode", bean.getAccount().getEvent().getCode());
				map.put("eventStatus", bean.getAccount().getEvent().getStatus());
				if (bean.getAccount().getEvent().getRegionalCenter() != null) {
					map.put("rcOperative", bean.getAccount().getEvent().getRegionalCenter().getOperative());
					map.put("rcOrigin", bean.getAccount().getEvent().getRegionalCenter().getOrigin());
					map.put("rcDestiny", bean.getAccount().getEvent().getRegionalCenter().getDestiny());
				}
			}
		}
		if (bean.getAmount() != null) {
			map.put("amount", bean.getAmount().getAmount());
			map.put("currency", bean.getAmount().getCurrency());
		}
		if (bean.getRelatedContract() != null) {
			map.put("contractType", bean.getRelatedContract().getContractType());
			map.put("contractNumber", bean.getRelatedContract().getNumber());
		}
		if (bean.getAudit() != null) {
			map.put("auditUserCode", bean.getAudit().getUserCode());
			map.put("auditTimestamp", bean.getAudit().getTimestamp().getTime());
		}
		return map;
	}

	private static String readString(final Object o) {
		String out = null;
		if (o instanceof String) {
			out = (String) o;
		} else {
			out = String.valueOf(o);
		}
		return out;
	}

	private static Date readDate(final Object o) {
		Date out = null;
		if (o instanceof Date) {
			out = (Date) o;
		} else if (o instanceof String) {
			final Matcher matcher = DATE_PATTERN.matcher((String) o);
			if (matcher.matches()) {
				final Calendar calendar = Calendar.getInstance();
				calendar.clear();
				calendar.set(Calendar.YEAR, Integer.parseInt(matcher.group(1)));
				calendar.set(Calendar.MONTH, Integer.parseInt(matcher.group(2)) - 1);
				calendar.set(Calendar.DATE, Integer.parseInt(matcher.group(3)));
				out = calendar.getTime();
			}
		}
		return out;
	}

	private static double readDouble(final Object o) {
		double out = 0;
		if (o instanceof Number) {
			out = ((Number) o).doubleValue();
		}
		return out;
	}

	private static Calendar readTimestamp(final Object o) {
		Calendar out = null;
		if (o instanceof Timestamp) {
			final Timestamp ts = (Timestamp) o;
			out = Calendar.getInstance();
			out.setTimeInMillis(ts.getTime());
		}
		return out;
	}

	private static int readInt(final Object o) {
		int out = 0;
		if (o instanceof Number) {
			out = ((Number) o).intValue();
		} else if (o instanceof String) {
			out = Integer.parseInt((String) o);
		}
		return out;
	}
}

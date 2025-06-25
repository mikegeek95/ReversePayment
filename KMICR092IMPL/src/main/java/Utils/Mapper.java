package Utils;

import java.text.SimpleDateFormat;
import java.util.*;
import com.bbva.kmic.dto.movementmodel.MicroloanMovement;
import com.bbva.kmic.dto.payments.ProductInputDTO;
import com.bbva.kmic.dto.commonmodel.*;
import Constants.Constants;

public class Mapper {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yy");

    private Mapper() {}

    // Conversión de ResultSet a lista de movimientos
    public static List<MicroloanMovement> mapListMicroloanMovement(List<Map<String, Object>> resultSet) {
        List<MicroloanMovement> list = new ArrayList<>();
        if (resultSet != null) {
            for (Map<String, Object> row : resultSet) {
                list.add(mapMicroloanMovement(row));
            }
        }
        return list;
    }

    // Conversión de un solo row a MicroloanMovement
    public static MicroloanMovement mapMicroloanMovement(Map<String, Object> row) {
        MicroloanMovement movement = new MicroloanMovement();
        movement.setContractId(getString(row.get("G_CONTRACT_ID")));
        movement.setDate(getDate(row.get("GF_MOV_DATE")));
        movement.setNumber(getInt(row.get("GF_SEQUENCE_ID")));
        movement.setMicroloanId(getString(row.get("GF_OPERATION_PAGE_ID")));
        movement.setInstallmentDate(getDate(row.get("GF_INSTALLMENT_PERIOD_DATE")));
        movement.setChannelCode(getString(row.get("GF_APP_CHANNEL_ID")));

        movement.setAccount(readAccount(row));
        movement.setAmount(readAmount(row));
        return movement;
    }

    // Mapeo de parámetros para logs
    public static Map<String, Object> buildParamsLogMovement(ProductInputDTO dto) {
        Map<String, Object> params = new HashMap<>();
        params.put("contractId", dto.getContractId());
        params.put("operationPageId", dto.getMicroloanId());
        params.put("date", SDF.format(dto.getInstallmentDate()));
        return params;
    }

    // Parámetros para updates
    public static Map<String, Object> buildParamsUpdateMicrocreditContract(ProductInputDTO dto) {
        Map<String, Object> params = new HashMap<>();
        params.put("contractId", dto.getContractId());
        params.put("availableAmount", dto.getAmount());
        params.put("drawnAmount", dto.getAmount());
        return params;
    }

    public static Map<String, Object> buildParamsUpdateDisposition(ProductInputDTO dto) {
        Map<String, Object> params = new HashMap<>();
        params.put("contractId", dto.getContractId());
        params.put("operationPageId", dto.getMicroloanId());
        params.put("recoveredTotalAmount", dto.getAmount());
        params.put("repaymentOutstdAmount", dto.getAmount());
        return params;
    }

    public static Map<String, Object> buildParamsUpdateAmortization(ProductInputDTO dto) {
        Map<String, Object> params = new HashMap<>();
        params.put("contractId", dto.getContractId());
        params.put("operationPageId", dto.getMicroloanId());
        params.put("itemSettlementDate", SDF.format(dto.getInstallmentDate()));
        params.put("drwdnAmortTradeAmount", dto.getAmount());
        params.put("amortCapitalAmount", dto.getAmountCapital());
        params.put("mcrcrAmortStatusType", Constants.STATUS_PENDING);
        return params;
    }

    public static Map<String, Object> buildParamsUpdateAmortizationCondition(ProductInputDTO dto) {
        Map<String, Object> params = new HashMap<>();
        params.put("contractId", dto.getContractId());
        params.put("operationPageId", dto.getMicroloanId());
        params.put("itemSettlementDate", SDF.format(dto.getInstallmentDate()));
        params.put("instlmntFeeRcovrAmount", dto.getAmountComision());
        params.put("recoveredTaxAmount", dto.getAmountIva());
        params.put("mcrcrAmortStatusType", Constants.STATUS_PENDING);
        return params;
    }

    // Métodos auxiliares

    private static Account readAccount(Map<String, Object> row) {
        Account account = new Account();
        AccountEvent event = new AccountEvent();

        account.setNumber(getString(row.get("GF_ACCT_MOV_ID")));
        account.setDate(getDate(row.get("GF_GL_ACCOUNT_DATE")));
        account.setCoded(getString(row.get("GF_GL_ACCOUNTING_STRING_ID")));
        account.setPositionNumber(getString(row.get("GF_ACCOUNTING_POSITION_ID")));

        event.setCode(getString(row.get("G_MICROCREDIT_MOVEMENT_TYPE")));
        event.setStatus(getString(row.get("G_ACTIVE_MOVEMENT_IND_TYPE")));

        account.setEvent(event);
        return account;
    }

    private static Amount readAmount(Map<String, Object> row) {
        Amount amount = new Amount();
        amount.setAmount(getDouble(row.get("GF_MOVEMENT_AMOUNT")));
        amount.setCurrency(getString(row.get("G_CURRENCY_ID")));
        return amount;
    }

    private static String getString(Object o) {
        return o == null ? null : o.toString();
    }

    private static int getInt(Object o) {
        if (o instanceof Number) return ((Number) o).intValue();
        if (o instanceof String) return Integer.parseInt((String) o);
        return 0;
    }

    private static double getDouble(Object o) {
        return o instanceof Number ? ((Number) o).doubleValue() : 0.0;
    }

    private static Date getDate(Object o) {
        if (o instanceof Date) return (Date) o;
        if (o instanceof String) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse((String) o);
            } catch (Exception ignored) {}
        }
        return null;
    }
}

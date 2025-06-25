package Utils;

import com.bbva.kmic.dto.commonmodel.Account;
import com.bbva.kmic.dto.commonmodel.AccountEvent;
import com.bbva.kmic.dto.commonmodel.Amount;
import com.bbva.kmic.dto.movementmodel.MicroloanMovement;
import com.bbva.kmic.dto.payments.ProductInputDTO;
import Constants.Constants;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

public class MapperTest {

    @Test
    public void testMapListMicroloanMovement_null() {
        List<MicroloanMovement> result = Mapper.mapListMicroloanMovement(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testMapListMicroloanMovement_empty() {
        List<MicroloanMovement> result = Mapper.mapListMicroloanMovement(Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testMapListMicroloanMovement_validRow() {
        Map<String, Object> row = new HashMap<>();
        row.put("G_CONTRACT_ID", "123");
        row.put("GF_MOV_DATE", "2024-07-20");
        row.put("GF_SEQUENCE_ID", 1);
        row.put("GF_OPERATION_PAGE_ID", "OP123");
        row.put("GF_INSTALLMENT_PERIOD_DATE", "2024-08-01");
        row.put("GF_APP_CHANNEL_ID", "CH01");
        row.put("GF_ACCT_MOV_ID", "AC01");
        row.put("GF_GL_ACCOUNT_DATE", "2024-07-21");
        row.put("GF_GL_ACCOUNTING_STRING_ID", "GL01");
        row.put("GF_ACCOUNTING_POSITION_ID", "POS01");
        row.put("G_MICROCREDIT_MOVEMENT_TYPE", "TYPE1");
        row.put("G_ACTIVE_MOVEMENT_IND_TYPE", "Y");
        row.put("GF_MOVEMENT_AMOUNT", 123.45);
        row.put("G_CURRENCY_ID", "MXN");

        List<MicroloanMovement> result = Mapper.mapListMicroloanMovement(Collections.singletonList(row));
        assertEquals(1, result.size());
        assertEquals("123", result.get(0).getContractId());
    }

    @Test
    public void testBuildParamsLogMovement() throws Exception {
        ProductInputDTO dto = new ProductInputDTO();
        dto.setContractId("C1");
        dto.setMicroloanId("MID1");
        dto.setInstallmentDate(new SimpleDateFormat("dd/MM/yy").parse("20/07/24"));

        Map<String, Object> map = Mapper.buildParamsLogMovement(dto);
        assertEquals("C1", map.get("contractId"));
        assertEquals("MID1", map.get("operationPageId"));
        assertEquals("20/07/24", map.get("date"));
    }

    @Test
    public void testBuildParamsUpdateMicrocreditContract() {
        ProductInputDTO dto = createDTO();
        Map<String, Object> map = Mapper.buildParamsUpdateMicrocreditContract(dto);
        assertEquals(dto.getAmount(), map.get("availableAmount"));
    }

    @Test
    public void testBuildParamsUpdateDisposition() {
        ProductInputDTO dto = createDTO();
        Map<String, Object> map = Mapper.buildParamsUpdateDisposition(dto);
        assertEquals(dto.getAmount(), map.get("repaymentOutstdAmount"));
    }

    @Test
    public void testBuildParamsUpdateAmortization() throws Exception {
        ProductInputDTO dto = createDTO();
        dto.setInstallmentDate(new SimpleDateFormat("dd/MM/yy").parse("20/07/24"));
        Map<String, Object> map = Mapper.buildParamsUpdateAmortization(dto);
        assertEquals(Constants.STATUS_PENDING, map.get("mcrcrAmortStatusType"));
    }

    @Test
    public void testBuildParamsUpdateAmortizationCondition() throws Exception {
        ProductInputDTO dto = createDTO();
        dto.setInstallmentDate(new SimpleDateFormat("dd/MM/yy").parse("20/07/24"));
        Map<String, Object> map = Mapper.buildParamsUpdateAmortizationCondition(dto);
        assertEquals(Constants.STATUS_PENDING, map.get("mcrcrAmortStatusType"));
    }

    @Test
    public void testGetHelpers() throws Exception {
        assertNull(MapperTestHelper.callGetString(null));
        assertEquals("abc", MapperTestHelper.callGetString("abc"));
        assertEquals(42, MapperTestHelper.callGetInt("42"));
        assertEquals(5, MapperTestHelper.callGetInt(5));
        assertEquals(0, MapperTestHelper.callGetInt(new Object()));
        assertEquals(1.5, MapperTestHelper.callGetDouble(1.5), 0.01);
        assertEquals(0.0, MapperTestHelper.callGetDouble("x"), 0.01);
        assertNotNull(MapperTestHelper.callGetDate("2024-07-20"));
        assertNull(MapperTestHelper.callGetDate("xx"));
    }

    private ProductInputDTO createDTO() {
        ProductInputDTO dto = new ProductInputDTO();
        dto.setContractId("C1");
        dto.setMicroloanId("MID1");
        dto.setAmount(1000.0);
        dto.setAmountCapital(800.0);
        dto.setAmountComision(100.0);
        dto.setAmountIva(100.0);
        return dto;
    }
}
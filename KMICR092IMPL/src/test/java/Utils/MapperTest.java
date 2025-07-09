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
    public void testMapMicroloanMovement_fullMapping() {
        Map<String, Object> row = new HashMap<>();
        row.put("G_CONTRACT_ID", "123");
        row.put("GF_MOV_DATE", "2024-07-20");
        row.put("GF_SEQUENCE_ID", "99");
        row.put("GF_OPERATION_PAGE_ID", "OP001");
        row.put("GF_INSTALLMENT_PERIOD_DATE", "2024-08-01");
        row.put("GF_APP_CHANNEL_ID", "WEB");

        row.put("GF_ACCT_MOV_ID", "AC001");
        row.put("GF_GL_ACCOUNT_DATE", "2024-07-19");
        row.put("GF_GL_ACCOUNTING_STRING_ID", "GL-STR");
        row.put("GF_ACCOUNTING_POSITION_ID", "POS99");
        row.put("G_MICROCREDIT_MOVEMENT_TYPE", "TYPE1");
        row.put("G_ACTIVE_MOVEMENT_IND_TYPE", "Y");

        row.put("GF_MOVEMENT_AMOUNT", 1500.50);
        row.put("G_CURRENCY_ID", "USD");

        row.put("G_LOCAL_CONTRACT_TYPE", "TYPE-A");
        row.put("GF_MOV_ASSO_ACCOUNT_ID", "ASSO123");
        row.put("GF_TRACKING_TRANSACTION_DESC", "Tracking info");

        row.put("GF_NEW_CUST_REGIONAL_CENTRE_ID", "RC1");
        row.put("GF_CUSTOMER_PROPOSAL_BRANCH_ID", "RC2");
        row.put("GF_CREDIT_REQUEST_ORIGIN_RC_ID", "RC3");

        row.put("GF_USER_AUDIT_ID", "AUD001");
        row.put("GF_AUDIT_DATE", new java.sql.Timestamp(System.currentTimeMillis()));

        MicroloanMovement m = Mapper.mapMicroloanMovement(row);
        assertEquals("123", m.getContractId());
        assertEquals("USD", m.getAmount().getCurrency());
        assertEquals("TYPE-A", m.getRelatedContract().getContractType());
        assertEquals("RC2", m.getAccount().getEvent().getRegionalCenter().getOperative());
        assertNotNull(m.getAudit().getTimestamp());
    }

    @Test
    public void testGetTimestampWithInvalidType() {
        assertNull(MapperTestHelper.callGetTimestamp("notATimestamp"));
    }
    
    

    @Test
    public void testGetIntWithInvalidString() {
        assertEquals(0, MapperTestHelper.callGetInt("notANumber"));
    }

    @Test
    public void testGetDoubleWithNull() {
        assertEquals(0.0, MapperTestHelper.callGetDouble(null), 0.01);
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
    public void testBuildParamsUpdateContractCondition() throws Exception {
        ProductInputDTO dto = createDTO();
        
        Map<String, Object> map = Mapper.buildParamsUpdateContractCondition(dto);
        assertEquals(Constants.STATUS_PENDING, map.get("contCondStatusType"));
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
    
    @Test
    public void testGetDateWithDateInstance() {
        Date now = new Date();
        Date result = MapperTestHelper.callGetDate(now);
        assertEquals(now, result); // o assertSame
    }
    
    


    private ProductInputDTO createDTO() {
        ProductInputDTO dto = new ProductInputDTO();
        dto.setContractId("C1");
        dto.setMicroloanId("MID1");
        dto.setAmount(1000.0);
        dto.setAmountCapital(800.0);
        dto.setAmountComision(100.0);
        dto.setAmountIva(100.0);
        dto.setAmountCapCobranza(100.0);
        dto.setAmountIvaCobranza(100.1);
        dto.setMovId("MID2");
        dto.setSequenceId(123);
        return dto;
    }
}
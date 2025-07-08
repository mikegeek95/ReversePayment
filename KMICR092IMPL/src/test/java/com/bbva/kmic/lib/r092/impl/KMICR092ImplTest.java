package com.bbva.kmic.lib.r092.impl;

import com.bbva.apx.exception.db.DBException;
import com.bbva.elara.utility.jdbc.JdbcUtils;
import com.bbva.kmic.dto.commonmodel.*;
import com.bbva.kmic.dto.movementmodel.*;
import com.bbva.kmic.dto.payments.ProductInputDTO;
import com.bbva.kmic.lib.r060.KMICR060;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KMICR092ImplTest {

    private KMICR092Impl service;

    @Mock
    private KMICR060 kmicR060;

    @Mock
    private JdbcUtils jdbcUtils;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new KMICR092Impl();
        service.kmicR060 = kmicR060;
        service.jdbcUtils = jdbcUtils;
    }

    @Test
    public void testExecutePlusAmount() throws Exception {
        ProductInputDTO dto = new ProductInputDTO();
        dto.setAmountCapital(100.0);
        dto.setAmountComision(50.0);
        dto.setAmountIva(20.0);

        Method method = KMICR092Impl.class.getDeclaredMethod("executePlusAmount", ProductInputDTO.class);
        method.setAccessible(true);
        BigDecimal result = (BigDecimal) method.invoke(service, dto);

        assertEquals(new BigDecimal("170.00"), result);
    }

    @Test
    public void testToMoney() throws Exception {
        Method method = KMICR092Impl.class.getDeclaredMethod("toMoney", double.class);
        method.setAccessible(true);
        BigDecimal result = (BigDecimal) method.invoke(service, 123.456);

        assertEquals(new BigDecimal("123.46"), result);
    }

    @Test
    public void testExecuteEqualsAmountTrue() throws Exception {
        ProductInputDTO dto = new ProductInputDTO();
        dto.setAmount(100.0);
        dto.setAmountCapital(30.0);
        dto.setAmountComision(30.0);
        dto.setAmountIva(40.0);

        Method method = KMICR092Impl.class.getDeclaredMethod("executeEqualsAmount", ProductInputDTO.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(service, dto);

        assertTrue(result);
    }

    @Test
    public void testExecuteEqualsAmountFalse() throws Exception {
        ProductInputDTO dto = new ProductInputDTO();
        dto.setAmount(99.0);
        dto.setAmountCapital(30.0);
        dto.setAmountComision(30.0);
        dto.setAmountIva(40.0);

        Method method = KMICR092Impl.class.getDeclaredMethod("executeEqualsAmount", ProductInputDTO.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(service, dto);

        assertFalse(result);
    }

    @Test
    public void testExecuteProcessSingleReversalValid() throws Exception {
        ProductInputDTO dto = new ProductInputDTO();
        MicroloanMovement movement = mock(MicroloanMovement.class);
        Account account = mock(Account.class);
        AccountEvent event = mock(AccountEvent.class);
        Amount amount = mock(Amount.class);
        
        account.setEvent(event);
        movement.setAccount(account);
        movement.setAmount(amount);

        when(movement.getAccount()).thenReturn(account);
        when(account.getEvent()).thenReturn(event);
        when(account.getNumber()).thenReturn("ACC001");
        when(event.getCode()).thenReturn("PGMNCMDI"); // código con acción válida
        when(movement.getAmount()).thenReturn(amount);
        when(amount.getAmount()).thenReturn(80.0);
        when(movement.getMicroloanId()).thenReturn("M123");
        when(movement.getNumber()).thenReturn(123);

        Method method = KMICR092Impl.class.getDeclaredMethod("executeProcessSingleReversal", MicroloanMovement.class, ProductInputDTO.class);
        method.setAccessible(true);
        method.invoke(service, movement, dto);

        assertEquals("M123", dto.getMicroloanId());
        assertEquals("ACC001", dto.getMovId());
        assertEquals(123, dto.getSequenceId());
        assertEquals(80.0, dto.getAmountComision(), 0.001);
    }

    @Test
    public void testExecuteProcessSingleReversalUnknownCode() throws Exception {
        ProductInputDTO dto = new ProductInputDTO();
        MicroloanMovement movement = mock(MicroloanMovement.class);
        Account account = mock(Account.class);
        AccountEvent event = mock(AccountEvent.class);
        Amount amount = mock(Amount.class);
        
        account.setEvent(event);
        movement.setAccount(account);
        movement.setAmount(amount);

        when(movement.getAccount()).thenReturn(account);
        when(account.getEvent()).thenReturn(event);
        when(account.getNumber()).thenReturn("ACC999");
        when(event.getCode()).thenReturn("UNKNOWN"); // código no reconocido
        when(movement.getAmount()).thenReturn(amount);
        when(amount.getAmount()).thenReturn(45.0);
        when(movement.getMicroloanId()).thenReturn("M999");
        when(movement.getNumber()).thenReturn(1234);

        Method method = KMICR092Impl.class.getDeclaredMethod("executeProcessSingleReversal", MicroloanMovement.class, ProductInputDTO.class);
        method.setAccessible(true);
        method.invoke(service, movement, dto);

        //assertEquals("M999", dto.getMicroloanId());
        assertEquals("ACC999", dto.getMovId());
        assertEquals(1234, dto.getSequenceId());
    }

    @Test
    public void testExecuteUpdatesHandlesDBException() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("contractId", "TEST123");
        doThrow(new DBException("Error"))
            .when(jdbcUtils).update(anyString(), anyMap());

        Method method = KMICR092Impl.class.getDeclaredMethod("executeUpdates", String.class, Map.class);
        method.setAccessible(true);
        int result = (int) method.invoke(service, "query", params);

        assertEquals(0, result);
    }
}
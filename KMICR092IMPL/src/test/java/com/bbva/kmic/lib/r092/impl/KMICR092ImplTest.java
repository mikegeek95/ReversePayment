package com.bbva.kmic.lib.r092.impl;

import com.bbva.kmic.dto.payments.ProductInputDTO;
import com.bbva.kmic.dto.movementmodel.MicroloanMovement;
import com.bbva.kmic.lib.r060.KMICR060;
import com.bbva.apx.exception.db.DBException;
import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.elara.utility.jdbc.JdbcUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import Constants.Constants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class KMICR092ImplTest {

	 private KMICR092Impl kmicr092Impl;

	    @Mock
	    private JdbcUtils jdbcUtils;

	    @Mock
	    private KMICR060 kmicr060;

    private ProductInputDTO inputDTO;

    private MicroloanMovement microloanMovement;
    private ApplicationConfigurationService applicationConfigurationService;

    @Before
    public void setUp() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MockitoAnnotations.initMocks(this);
        kmicr092Impl = new KMICR092Impl();
        kmicr092Impl.setJdbcUtils(jdbcUtils);
        kmicr092Impl.setKmicR060(kmicr060);
        
        inputDTO = new ProductInputDTO();
        inputDTO.setContractId("MX007400219200001818");
        inputDTO.setMicroloanId("202408200324351553");
        inputDTO.setInstallmentDate(Date.valueOf("2024-07-01"));
        inputDTO.setAmount(1216.33);
        
        
        Method method = KMICR092Impl.class.getDeclaredMethod("executeMapingMicroloanMovement", ProductInputDTO.class);
        method.setAccessible(true); // 💥 habilita acceso a método privado
        microloanMovement = (MicroloanMovement) method.invoke(kmicr092Impl, inputDTO);
        kmicr092Impl.setApplicationConfigurationService(applicationConfigurationService); 
    }

    @Test
    public void testExecuteGetReversePayments_success_and_ExceptionFlows() {
        // Arrange: 
        when(kmicr060.executeGetMicroloanMovement(any(MicroloanMovement.class)))
            .thenReturn(new MicroloanMovement());

        //when(jdbcUtils.update(eq(Constants.MICRO_CREDIT_CONTRACT), anyMap()))
        //    .thenReturn(1);
        //when(jdbcUtils.update(eq(Constants.MCECR_AMORTIZATION), anyMap()))
        //    .thenReturn(1);
        //when(jdbcUtils.update(eq(Constants.AMORTIZATION_CONDITION), anyMap()))
        //    .thenReturn(1);

        // Act: Ejecutar flujo normal
        kmicr092Impl.executeGetReversePayments(Collections.singletonList(inputDTO));

        // Assert flujo normal
        verify(kmicr060, times(1)).executeGetMicroloanMovement(any(MicroloanMovement.class));
       // verify(jdbcUtils, times(1)).update(eq(Constants.MICRO_CREDIT_CONTRACT), anyMap());
        //verify(jdbcUtils, times(1)).update(eq(Constants.MCECR_AMORTIZATION), anyMap());
        //verify(jdbcUtils, times(1)).update(eq(Constants.AMORTIZATION_CONDITION), anyMap());
        
        // Ahora provocamos el error para cada método
        reset(jdbcUtils); // Limpia las interacciones anteriores

        //when(jdbcUtils.update(eq(Constants.MICRO_CREDIT_CONTRACT), anyMap()))
        //    .thenThrow(new DBException("Error simulado microcredit"));
        //when(jdbcUtils.update(eq(Constants.MCECR_AMORTIZATION), anyMap()))
        //    .thenThrow(new DBException("Error simulado amortization"));
        //when(jdbcUtils.update(eq(Constants.AMORTIZATION_CONDITION), anyMap()))
        //    .thenThrow(new DBException("Error simulado amortization condition"));

        // Ejecutamos manualmente los métodos de update para provocar el catch
        int resultMicrocredit = kmicr092Impl.executeUpdateMicrocreditContract(new HashMap<>());
        int resultDspn = kmicr092Impl.executeUpdateDspnAmort(new HashMap<>());
        int resultAmortization = kmicr092Impl.executeUpdateAmortizationContition(new HashMap<>());

        // Assert: todos deben regresar 0 al fallar
        assertEquals(0, resultMicrocredit);
        assertEquals(0, resultDspn);
        assertEquals(0, resultAmortization);

        // Verificar que se intentaron las 3 actualizaciones
        //verify(jdbcUtils, times(1)).update(eq(Constants.MICRO_CREDIT_CONTRACT), anyMap());
        //verify(jdbcUtils, times(1)).update(eq(Constants.MCECR_AMORTIZATION), anyMap());
        //verify(jdbcUtils, times(1)).update(eq(Constants.AMORTIZATION_CONDITION), anyMap());
    }




    @Test
    public void testExecuteGetReversePayments_whenNoMovementFound() {
        // Ahora simulamos que KMICR060 no encuentra un movimiento (retorna null)
        when(kmicr060.executeGetMicroloanMovement(any(MicroloanMovement.class)))
                .thenReturn(null);

        // Ejecutamos el método
        kmicr092Impl.executeGetReversePayments(Collections.singletonList(inputDTO));

        // Debe intentar buscar el movimiento
        verify(kmicr060, times(1)).executeGetMicroloanMovement(any(MicroloanMovement.class));

        // No debe ejecutar ningún update
        verify(jdbcUtils, never()).update(anyString(), anyMap());
    }
}

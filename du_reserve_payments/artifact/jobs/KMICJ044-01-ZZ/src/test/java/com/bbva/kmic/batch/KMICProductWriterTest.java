package com.bbva.kmic.batch;

import com.bbva.kmic.dto.payments.ReservePaymentDto;
import com.bbva.kmic.lib.r092.KMICR092;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class KMICProductWriterTest {

    private KMICProductWriter writer;

    @Mock
    private KMICR092 kmicR092;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        writer = new KMICProductWriter();
        writer.setKmicR092(kmicR092);
    }

    @Test
    public void testWrite_success() throws Exception {
    	ReservePaymentDto dto = new ReservePaymentDto();
        dto.setContractId("MX007400219200001818");

        writer.write(Collections.singletonList(dto));

        verify(kmicR092, times(1)).executeCheckPayment(anyList());
    }
}


package com.bbva.kmic.batch;

import com.bbva.kmic.dto.payments.ProductInputDTO;
import com.bbva.kmic.lib.r092.KMICR092;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

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
        ProductInputDTO dto = new ProductInputDTO();
        dto.setContractId("MX007400219200001818");
        dto.setMicroloanId("202408200324351553");

        List<ProductInputDTO> inputList = Collections.singletonList(dto);

        writer.write(inputList);

        verify(kmicR092, times(1)).executeGetReversePayments(inputList);
    }
}

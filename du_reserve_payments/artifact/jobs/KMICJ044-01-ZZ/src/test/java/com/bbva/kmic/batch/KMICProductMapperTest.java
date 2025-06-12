package com.bbva.kmic.batch;

import com.bbva.kmic.dto.payments.ProductInputDTO;
import org.junit.Test;
import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.batch.item.file.transform.FieldSet;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KMICProductMapperTest {

    private final KMICProductMapper mapper = new KMICProductMapper();

    @Test
    public void testMapFieldSet_success() throws Exception {
        String[] names = {"contractId", "microloanId", "installmentDate", "amount", "tipoMovimiento"};
        String[] values = {"MX007400219200001818", "202408200324351553", "01/07/2024", "1216.33", "PGAUTCON"};

        FieldSet fieldSet = new DefaultFieldSet(values, names);

        ProductInputDTO dto = mapper.mapFieldSet(fieldSet);

        assertNotNull(dto);
        assertEquals("MX007400219200001818", dto.getContractId());
        assertEquals("202408200324351553", dto.getMicroloanId());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date expectedDate = sdf.parse("01/07/2024");
        assertEquals(expectedDate, dto.getInstallmentDate());
        
        assertEquals(1216.33, dto.getAmount(), 0.001);
        assertEquals("PGAUTCON", dto.getTipoMovimiento());
    }
}

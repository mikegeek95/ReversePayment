package com.bbva.kmic.batch;



import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.bbva.kmic.dto.payments.ReservePaymentDto;

public class KMICProductMapper implements FieldSetMapper<ReservePaymentDto> {


    @Override
    public ReservePaymentDto mapFieldSet(FieldSet fieldSet) throws BindException {
    	ReservePaymentDto dto = new ReservePaymentDto();

        dto.setContractId(fieldSet.readString("contractId"));
        dto.setContractDisId(fieldSet.readString("microloanId"));
        dto.setPeriod(fieldSet.readDate("installmentDate", "dd/MM/yyyy"));
        dto.setAmount(fieldSet.readDouble("amount"));
        dto.setMovementType(fieldSet.readString("tipoMovimiento"));

        return dto;
    }
}

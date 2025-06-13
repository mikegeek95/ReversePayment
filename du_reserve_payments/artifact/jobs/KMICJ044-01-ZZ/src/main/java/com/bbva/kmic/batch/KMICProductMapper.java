package com.bbva.kmic.batch;



import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.bbva.kmic.dto.payments.ProductInputDTO;

public class KMICProductMapper implements FieldSetMapper<ProductInputDTO> {


    @Override
    public ProductInputDTO mapFieldSet(FieldSet fieldSet) throws BindException {
        ProductInputDTO dto = new ProductInputDTO();

        dto.setContractId(fieldSet.readString("contractId"));
        dto.setMicroloanId(fieldSet.readString("microloanId"));
        dto.setInstallmentDate(fieldSet.readDate("installmentDate", "dd/MM/yyyy"));
        dto.setAmount(fieldSet.readDouble("amount"));
        

        return dto;
    }
}

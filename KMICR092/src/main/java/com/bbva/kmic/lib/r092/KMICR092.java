package com.bbva.kmic.lib.r092;

import java.util.List;
import java.util.Map;

import com.bbva.kmic.dto.movementmodel.MicroloanMovement;
import com.bbva.kmic.dto.payments.ProductInputDTO;

/**
 * The  interface KMICR092 class...
 */
public interface KMICR092 {

	/**
	 * The execute method...
	 */
	void executeGetReversePayments( List<ProductInputDTO> items);
	 int executeUpdateMicrocreditContract (ProductInputDTO args);
	 int executeUpdateAmortizationContition (ProductInputDTO args);
	 int executeUpdateDspnAmort (ProductInputDTO args);
	List<MicroloanMovement> getMovementList(ProductInputDTO params);
	int executeUpdateDisposition(ProductInputDTO args);

}

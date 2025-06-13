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
	 int executeUpdateMicrocreditContract (Map<String, Object> args);
	 int executeUpdateAmortizationContition (Map<String, Object> args);
	 int executeUpdateDspnAmort (Map<String, Object> args);
	List<MicroloanMovement> getMovementList(Map<String, Object> params);
	int executeUpdateDisposition(Map<String, Object> args);

}

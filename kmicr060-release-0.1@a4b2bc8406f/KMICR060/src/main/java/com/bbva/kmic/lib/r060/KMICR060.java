package com.bbva.kmic.lib.r060;

import java.util.List;
import java.util.Map;

import com.bbva.kmic.dto.movementmodel.MicroloanMovement;
import com.bbva.kmic.dto.movementmodel.MicroloanMovementFilter;

/**
 * Batch library to manage microloans movements. A microloan movement records a
 * change in the credit balance.
 */
public interface KMICR060 {

	/**
	 * List microloans movements filtered by movement date and type. A microloan
	 * movement register a change in the balance of the credit
	 *
	 * @param <MicroloanMovement> Microloan movement filter information
	 * @param microloanMovement   List of microloans movements
	 */
	List<MicroloanMovement> executeListMicroloansMovements(MicroloanMovementFilter microloanMovement);

	/**
     * Create a list of microloan movements. Add a list of records in
     * KMIC.T_KMIC_MCRCR_LOG_MOVEMENT.
     *
     * @param microloanMovements List of microloan movement information
     * @return Count of inserted records, eg 1
     */
    int executeCreateMicroloanMovements(List<MicroloanMovement> microloanMovements);

	/**
	 * Get a microloan movement. Return null if microloan movement not found.
	 *
	 * @param microloanMovement Microloan movement filter information
	 * @return Microloan movement information
	 */
	MicroloanMovement executeGetMicroloanMovement(MicroloanMovement microloanMovement);
	


}

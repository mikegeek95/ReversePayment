package com.bbva.kmic.lib.r060.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbva.apx.exception.db.IncorrectResultSizeException;
import com.bbva.apx.exception.db.NoResultException;
import com.bbva.kmic.dto.movementmodel.MicroloanMovement;
import com.bbva.kmic.dto.movementmodel.MicroloanMovementFilter;
import com.bbva.kmic.lib.r060.impl.utils.Constants;
import com.bbva.kmic.lib.r060.impl.utils.Mapper;
import com.bbva.kmic.lib.r060.impl.utils.Utils;

/**
 * Batch library to manage microloans movements. A microloan movement records a
 * change in the credit balance.
 */
public class KMICR060Impl extends KMICR060Abstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(KMICR060Impl.class);

    /**
     * List microloans movements filtered by movement date and type. A microloan
     * movement register a change in the balance of the credit
     *
     * @param <MicroloanMovement> Microloan movement filter information
     * @param microloanMovement   List of microloans movements
     */
    @Override
    public List<MicroloanMovement> executeListMicroloansMovements(final MicroloanMovementFilter microloanMovement) {
        LOGGER.debug("filter: {}", microloanMovement);
        final Map<String, Object> listParams = Utils.buildListParams(microloanMovement);
        final List<Map<String, Object>> resultSet = jdbcUtils.pagingQueryForList(Constants.QUERIES.LIST,
                microloanMovement.getPagination().getFirstRow(), microloanMovement.getPagination().getPageSize(),
                listParams);
        return Mapper.mapListMicroloanMovement(resultSet);
    }

    /**
     * Create a list of microloan movements. Add a list of records in
     * KMIC.T_KMIC_MCRCR_LOG_MOVEMENT.
     *
     * @param microloanMovements List of microloan movement information
     * @return Count of inserted records, eg 1
     */
    @SuppressWarnings("unchecked")
    @Override
    public int executeCreateMicroloanMovements(final List<MicroloanMovement> microloanMovements) {
        int total = 0;

        @SuppressWarnings("rawtypes")
        final Map[] mapsArray = microloanMovements.stream().map(Mapper::hashMicroloanMovement).toArray(Map[]::new);

        final int[] partials = jdbcUtils.batchUpdate(Constants.QUERIES.INSERT, mapsArray);
        for (final int partial : partials) {
            total += partial;
        }
        return total;
    }

    /**
     * Get a microloan movement. Return null if microloan movement not found.
     *
     * @param microloanMovement Microloan movement filter information
     * @return Microloan movement information
     */
    @Override
    public MicroloanMovement executeGetMicroloanMovement(final MicroloanMovement microloanMovement) {
        LOGGER.debug("movemnt: {}", microloanMovement);
        MicroloanMovement out;
        final Map<String, Object> mapMM = Mapper.hashMicroloanMovement(microloanMovement);
        String query;

        if (microloanMovement.getInterbankTrackingDescription() == null
                || microloanMovement.getInterbankTrackingDescription().trim().isEmpty()) {
            query = Constants.QUERIES.SELECT;
        } else {
            query = Constants.QUERIES.SELECT_DETAIL;
        }

        try {
            final Map<String, Object> resultSet = jdbcUtils.queryForMap(query, mapMM);
            out = Mapper.mapMicroloanMovement(resultSet);
        } catch (final NoResultException e) {
            LOGGER.info("No result", e);
            out = null;
        } catch (final IncorrectResultSizeException irse) {
            LOGGER.info("Multiple results");
            out = new MicroloanMovement();
        }

        return out;
    }
}

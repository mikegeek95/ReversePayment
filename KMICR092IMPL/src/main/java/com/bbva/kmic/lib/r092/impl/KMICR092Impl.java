package com.bbva.kmic.lib.r092.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.bbva.kmic.dto.payments.ProductInputDTO;
import com.bbva.kmic.dto.movementmodel.MicroloanMovement;
import com.bbva.apx.exception.db.DBException;

import Constants.Constants;
import Constants.Diccionario;
import Utils.Mapper;

public class KMICR092Impl extends KMICR092Abstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(KMICR092Impl.class);

    @Override
    public void executeGetReversePayments(List<ProductInputDTO> items) {
        for (ProductInputDTO dto : items) {
            LOGGER.info("[KMICR092] DTO recibido: {}", dto);

            MicroloanMovement inputMovement = Mapper.mapToMicroloanMovement(dto);
            MicroloanMovement resultMovement = fetchMicroloanMovement(inputMovement);

            if (resultMovement == null) {
                LOGGER.warn("No se encontró movimiento para: {}", dto);
                continue;
            }

            LOGGER.info("Movimiento encontrado: {}", resultMovement);

            Map<String, Object> params = Mapper.buildQueryParams(inputMovement);
            List<MicroloanMovement> movementList = retrieveMovements(params);

            if (movementList.isEmpty()) {
                LOGGER.warn("No hay movimientos para procesar para contrato: {}", params.get("contractId"));
                continue;
            }

            applyUpdatesToContract(params);
            applyReversalsAndInsert(movementList);
        }
    }

    private MicroloanMovement fetchMicroloanMovement(MicroloanMovement inputMovement) {
        try {
            LOGGER.info("Buscando movimiento con parámetros: {}", inputMovement);
            MicroloanMovement result = kmicR060.executeGetMicroloanMovement(inputMovement);

            if (result == null) {
                LOGGER.warn("Movimiento no encontrado: {}", inputMovement);
            }

            return result;
        } catch (Exception e) {
            LOGGER.error("Error al obtener movimiento de microcrédito", e);
            return null;
        }
    }

    @Override
    public List<MicroloanMovement> getMovementList(Map<String, Object> params) {
        return retrieveMovements(params);
    }

    private List<MicroloanMovement> retrieveMovements(Map<String, Object> params) {
        List<MicroloanMovement> movements = new ArrayList<>();

        try {
            LOGGER.info("Consultando movimientos con parámetros: {}", params);
            List<Map<String, Object>> rows = jdbcUtils.queryForList(Constants.SELECT_TRAE_DATOS_LOG, params);
            movements = Mapper.mapListMicroloanMovement(rows);
            LOGGER.info("Movimientos recuperados: {}", movements.size());
        } catch (Exception e) {
            LOGGER.error("Error al obtener movimientos para contrato: {}", params.get("contractId"), e);
        }

        return movements;
    }

    private void applyUpdatesToContract(Map<String, Object> params) {
        updateWithQuery(Constants.UPDATE_MICROCREDIT_CONTRACT, params);
        updateWithQuery(Constants.UPDATE_MCRCR_DISPOSITION, params);
        updateWithQuery(Constants.UPDATE_AMORTIZATION_CONDITION, params);
        updateWithQuery(Constants.UPDATE_MCRCR_AMORTIZATION, params);
    }

    private void applyReversalsAndInsert(List<MicroloanMovement> movements) {
        for (MicroloanMovement movement : movements) {
            String originalCode = movement.getAccount().getEvent().getCode();
            String reverseCode = Diccionario.obtenerCodigoContrario(originalCode);

            if (reverseCode != null && !reverseCode.isEmpty()) {
                movement.getAccount().getEvent().setCode(reverseCode);
                LOGGER.info("Código de reverso aplicado: {} → {}", originalCode, reverseCode);
            } else {
                LOGGER.warn("No se encontró código de reverso para: {}", originalCode);
            }
        }

        insertMovementsBatch(movements);
    }

    private void updateWithQuery(String queryKey, Map<String, Object> args) {
        LOGGER.info("Actualizando con query [{}] para contrato: {}", queryKey, args.get("contractId"));
        try {
            jdbcUtils.update(queryKey, args);
            LOGGER.info("Actualización exitosa para contrato: {}", args.get("contractId"));
        } catch (DBException e) {
        	LOGGER.info("Error en actualización con query [{}]: {}", queryKey, args.get("contractId"));
        }
    }

    public int insertMovementsBatch(List<MicroloanMovement> movements) {
        if (movements == null || movements.isEmpty()) {
            LOGGER.warn("Lista vacía de movimientos. No se insertará nada.");
            return 0;
        }

        try {
            int inserted = kmicR060.executeCreateMicroloanMovements(movements);
            LOGGER.info("Inserción de movimientos completada. Total insertado: {}", inserted);
            return inserted;
        } catch (Exception e) {
            LOGGER.error("Error al insertar movimientos", e);
            return 0;
        }
    }

    // Métodos heredados expuestos como públicos (si el contrato lo requiere)

    @Override
    public int executeUpdateMicrocreditContract(Map<String, Object> args) {
        return updateWithResult(Constants.UPDATE_MICROCREDIT_CONTRACT, args);
    }

    @Override
    public int executeUpdateDisposition(Map<String, Object> args) {
        return updateWithResult(Constants.UPDATE_MCRCR_DISPOSITION, args);
    }

    @Override
    public int executeUpdateAmortizationContition(Map<String, Object> args) {
        return updateWithResult(Constants.UPDATE_AMORTIZATION_CONDITION, args);
    }

    @Override
    public int executeUpdateDspnAmort(Map<String, Object> args) {
        return updateWithResult(Constants.UPDATE_MCRCR_AMORTIZATION, args);
    }

    private int updateWithResult(String queryKey, Map<String, Object> args) {
        try {
            return jdbcUtils.update(queryKey, args);
        } catch (DBException e) {
            LOGGER.info("Error ejecutando update [{}] para contrato: {}", queryKey, args.get("contractId"));
            return 0;
        }
    }
}


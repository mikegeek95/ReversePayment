package com.bbva.kmic.lib.r092.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.sql.Date;

import com.bbva.kmic.dto.payments.ProductInputDTO;
import com.bbva.kmic.dto.commonmodel.Amount;
import com.bbva.kmic.dto.movementmodel.MicroloanMovement;
import com.bbva.apx.exception.db.DBException;

import Constants.Constants;
import Constants.Diccionario;
import Utils.Mapper;

/**
 * Implementación concreta del proceso KMICR092
 */
public class KMICR092Impl extends KMICR092Abstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(KMICR092Impl.class);

    @Override
    public void executeGetReversePayments(List<ProductInputDTO> items) {
        for (ProductInputDTO dto : items) {
            LOGGER.info("[KMICR092] DTO recibido: {}", dto);

            MicroloanMovement inputMovement = mapToMicroloanMovement(dto);
            MicroloanMovement resultMovement = kmicR060.executeGetMicroloanMovement(inputMovement);

            if (resultMovement != null) {
                LOGGER.info("Movimiento encontrado: {}", resultMovement);

                Map<String, Object> args = buildQueryParams(inputMovement);
                List<MicroloanMovement> movements = getMovementList(args);
                processReversals(movements);
            } else {
                LOGGER.warn("No se encontró movimiento para: {}", dto);
            }
        }
    }

    private MicroloanMovement mapToMicroloanMovement(ProductInputDTO dto) {
        MicroloanMovement movement = new MicroloanMovement();

        movement.setContractId(dto.getContractId());
        movement.setMicroloanId(dto.getMicroloanId());
        movement.setInstallmentDate(dto.getInstallmentDate());

        Amount amount = new Amount();
        amount.setAmount(dto.getAmount());
        movement.setAmount(amount);

        return movement;
    }

    private Map<String, Object> buildQueryParams(MicroloanMovement movement) {
        Map<String, Object> params = new HashMap<>();
        LOGGER.debug("Iniciando construcción de parámetros para: {}", movement);

        params.put("contractId", movement.getContractId());
        params.put("microloanId", movement.getMicroloanId());
        params.put("date", new Date(movement.getInstallmentDate().getTime()));

        if (movement.getAmount() != null) {
            params.put("amount", movement.getAmount().getAmount());
        }

        if (movement.getAccount() != null && movement.getAccount().getEvent() != null) {
            params.put("eventCode", movement.getAccount().getEvent().getCode());
        }

        LOGGER.debug("Parámetros construidos: {}", params);
        return params;
    }

    @Override
    public List<MicroloanMovement> getMovementList(Map<String, Object> params) {
        List<MicroloanMovement> movements = new ArrayList<>();

        try {
            LOGGER.info("Ejecutando consulta con parámetros: {}", params);
            List<Map<String, Object>> rows = jdbcUtils.queryForList(Constants.select_list_moviemintos, params);
            movements = Mapper.mapListMicroloanMovement(rows);
            LOGGER.info("Movimientos recuperados correctamente para contrato: {}", params.get("contractId"));
        } catch (Exception e) {
            LOGGER.error("Error al obtener movimientos para contrato: {}", params.get("contractId"), e);
        }

        return movements;
    }

    private void processReversals(List<MicroloanMovement> movements) {
        for (MicroloanMovement movement : movements) {
            String eventCode = movement.getAccount().getEvent().getCode();
            LOGGER.debug("Procesando reverso para código de evento: {}", eventCode);

            switch (eventCode) {
                case Diccionario.PGMNCMDI:
                    // TODO: Lógica para PGMNCMDI
                    break;

                case Diccionario.ANULDISP:
                    // TODO: Lógica para ANULDISP
                    break;

                case Diccionario.PAGMENCA:
                default:
                    // TODO: Lógica por defecto o para PAGMENCA
                    break;
            }
        }
    }

    @Override
    public int executeUpdateMicrocreditContract(Map<String, Object> args) {
        return executeUpdate(Constants.MICRO_CREDIT_CONTRACT, args);
    }

    @Override
    public int executeUpdateAmortizationContition(Map<String, Object> args) {
        return executeUpdate(Constants.AMORTIZATION_CONDITION, args);
    }

    @Override
    public int executeUpdateDspnAmort(Map<String, Object> args) {
        return executeUpdate(Constants.MCECR_AMORTIZATION, args);
    }

    private int executeUpdate(String query, Map<String, Object> args) {
        LOGGER.info("Ejecutando actualización para: {}", args.get("contractId"));
        int result = 0;

        try {
            LOGGER.debug("Parámetros: {}, Query: {}", args, query);
            result = jdbcUtils.update(query, args);
            LOGGER.info("Actualización completada para contrato: {}", args.get("contractId"));
        } catch (DBException e) {
            LOGGER.error("Error en la actualización del contrato: {}", args.get("contractId"), e);
        }

        return result;
    }
    
    public int executeInsertMicroloanMovements(List<MicroloanMovement> movements) {
        LOGGER.info("Iniciando inserción de movimientos. Total a insertar: {}", movements != null ? movements.size() : 0);

        if (movements == null || movements.isEmpty()) {
            LOGGER.warn("Lista de movimientos vacía o nula. No se insertará nada.");
            return 0;
        }

        try {
            int inserted = kmicR060.executeCreateMicroloanMovements(movements);
            LOGGER.info("Inserción completada. Total insertado: {}", inserted);
            return inserted;
        } catch (Exception e) {
            LOGGER.error("Error al insertar movimientos microcrédito", e);
            return 0;
        }
    }

}

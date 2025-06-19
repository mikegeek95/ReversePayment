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

            Map<String, Object> params = Mapper.buildParamsLogMovement(dto);
            List<MicroloanMovement> originalMovementList = getMovementList(dto);

            if (originalMovementList.isEmpty()) {
                LOGGER.warn("No hay movimientos para procesar para contrato: {}", params.get("contractId"));
                continue;
            }

            List<MicroloanMovement> validatedMovements = new ArrayList<>();

            for (MicroloanMovement movement : originalMovementList) {
                MicroloanMovement result = fetchMicroloanMovement(movement);

                if (result != null) {
                    validatedMovements.add(movement);
                    LOGGER.info("Movimiento válido encontrado: {}", result);
                } else {
                    LOGGER.warn("Movimiento no válido (no encontrado): {}", movement);
                }
            }

            if (validatedMovements.isEmpty()) {
                LOGGER.warn("No se encontró ningún movimiento válido para: {}", dto.getContractId());
                continue;
            }

            applyReversalsAndInsert(validatedMovements, dto);
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
    public List<MicroloanMovement> getMovementList(ProductInputDTO args) {
    	List<MicroloanMovement> movements = new ArrayList<>();
    	Map<String, Object> params=Mapper.buildParamsLogMovement(args);
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

 
    private void applyReversalsAndInsert(List<MicroloanMovement> movements, ProductInputDTO dto) {
        LOGGER.info("Iniciando proceso de reverso para contrato: {}", dto.getContractId());

        for (MicroloanMovement movement : movements) {
            processSingleReversal(movement, dto);
        }

        double totalAmountComponents = dto.getAmountCapital() + dto.getAmountComision() + dto.getAmountIva();

        if (Double.compare(dto.getAmount(), totalAmountComponents) == 0) {
            LOGGER.info("El monto total coincide. Ejecutando actualizaciones.");
            executeAllUpdates(dto);
        } else {
            LOGGER.warn("Monto inconsistente. Monto original: {}, suma componentes: {}", dto.getAmount(), totalAmountComponents);
        }

        insertMovementsBatch(movements);
    }

    
    private void processSingleReversal(MicroloanMovement movement, ProductInputDTO dto) {
        String originalCode = movement.getAccount().getEvent().getCode();
        String reversedCode = Diccionario.obtenerCodigoContrario(originalCode);

        if (reversedCode == null) {
            LOGGER.warn("No se encontró reverso para el código: {}", originalCode);
            return;
        }

        movement.getAccount().getEvent().setCode(reversedCode);
        movement.setDate(getAccountingDateCurrentDate());

        double amount = movement.getAmount().getAmount();

        switch (originalCode) {
            case Diccionario.PGMNCMDI: // Capital
                dto.setAmountCapital(amount);
                LOGGER.info("Reverso de CAPITAL aplicado: {}", amount);
                break;
            case Diccionario.PGMNIVAC: // IVA
                dto.setAmountIva(amount);
                LOGGER.info("Reverso de IVA aplicado: {}", amount);
                break;
            case Diccionario.PAGMENCA: // Comisión
                dto.setAmountComision(amount);
                LOGGER.info("Reverso de COMISIÓN aplicado: {}", amount);
                break;
            default:
                LOGGER.warn("Tipo de movimiento no reconocido para reverso: {}", originalCode);
        }
    }

    
    private void executeAllUpdates(ProductInputDTO dto) {
        try {
            executeUpdateMicrocreditContract(dto);
            executeUpdateDisposition(dto);
            executeUpdateAmortizationContition(dto);
            executeUpdateDspnAmort(dto);
            LOGGER.info("Actualizaciones completadas correctamente para el contrato: {}", dto.getContractId());
        } catch (Exception e) {
            LOGGER.error("Error en la ejecución de actualizaciones para el contrato: {}", dto.getContractId(), e);
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
    public int executeUpdateMicrocreditContract(ProductInputDTO args) {
    	Map<String, Object> argsu=Mapper.buildParamsUpdateMicrocreditContract(args);
        return updateWithResult(Constants.UPDATE_MICROCREDIT_CONTRACT, argsu);
    }

    @Override
    public int executeUpdateDisposition(ProductInputDTO args) {
    	Map<String, Object> argsu=Mapper.buildParamsUpdateDisposition(args);
        return updateWithResult(Constants.UPDATE_MCRCR_DISPOSITION, argsu);
    }

    @Override
    public int executeUpdateAmortizationContition(ProductInputDTO args) {
    	Map<String, Object> argsu=Mapper.buildParamsUpdateAmortization(args);
        return updateWithResult(Constants.UPDATE_AMORTIZATION_CONDITION, argsu);
    }

    @Override
    public int executeUpdateDspnAmort(ProductInputDTO args) {
    	Map<String, Object> argsu=Mapper.buildParamsUpdateAmortizationCondition(args);
        return updateWithResult(Constants.UPDATE_MCRCR_AMORTIZATION, argsu);
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


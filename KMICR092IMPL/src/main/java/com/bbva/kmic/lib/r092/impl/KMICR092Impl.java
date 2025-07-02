package com.bbva.kmic.lib.r092.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.bbva.kmic.dto.payments.ProductInputDTO;
import com.bbva.kmic.dto.movementmodel.MicroloanMovement;
import com.bbva.apx.exception.db.DBException;

import Constants.Constants;
import Constants.Diccionario;
import Utils.Mapper;

public class KMICR092Impl extends KMICR092Abstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(KMICR092Impl.class);
    private static final Set<String> MOVIMIENTOS_CAPITAL = new HashSet<>(Arrays.asList(
        Diccionario.PAGMENCA, Diccionario.PGANTCAP, Diccionario.PGVENCAP
    ));
    private static final Set<String> MOVIMIENTOS_IVA = new HashSet<>(Arrays.asList(
        Diccionario.PGMNIVAC, Diccionario.PIVACOMD, Diccionario.PGVNIVAC
    ));
    private static final Set<String> MOVIMIENTOS_COMISION = new HashSet<>(Arrays.asList(
        Diccionario.PGMNCMDI, Diccionario.PGCOMDIS, Diccionario.PGVNCDIS
    ));


    @Override
    public void executeGetReversePayments(List<ProductInputDTO> items) {
        for (ProductInputDTO dto : items) {
            LOGGER.info("[KMICR092] DTO recibido: {}", dto);

            List<MicroloanMovement> originalMovements = executeGetMovementList(dto);
            if (originalMovements.isEmpty()) {
                LOGGER.warn("No hay movimientos para procesar para contrato: {}", dto.getContractId());
                continue;
            }

            List<MicroloanMovement> validatedMovements = new ArrayList<>();
            for (MicroloanMovement movement : originalMovements) {
                String code = movement.getAccount().getEvent().getCode();

                if (Diccionario.esMovimientoYaReversado(code)) {
                    LOGGER.warn("Movimiento ya reversado omitido: {}", code);
                    continue;
                }

                MicroloanMovement result = executeFetchMicroloanMovement(movement);
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

            executeReversalsAndInsert(validatedMovements, dto);
        }
    }

    private MicroloanMovement executeFetchMicroloanMovement(MicroloanMovement input) {
        try {
            LOGGER.info("Buscando movimiento con parámetros: {}", input);
            MicroloanMovement result = kmicR060.executeGetMicroloanMovement(input);
            if (result == null) LOGGER.warn("Movimiento no encontrado: {}", input);
            return result;
        } catch (Exception e) {
            LOGGER.error("Error al obtener movimiento de microcredito", e);
            return null;
        }
    }

    @Override
    public List<MicroloanMovement> executeGetMovementList(ProductInputDTO dto) {
        try {
            Map<String, Object> params = Mapper.buildParamsLogMovement(dto);
            LOGGER.info("Consultando movimientos con parámetros: {}", params);
            List<Map<String, Object>> rows = jdbcUtils.queryForList(Constants.SELECT_TRAE_DATOS_LOG, params);
            List<MicroloanMovement> movements = Mapper.mapListMicroloanMovement(rows);
            LOGGER.info("Movimientos recuperados: {}", movements.size());
            return movements;
        } catch (Exception e) {
            LOGGER.error("Error al obtener movimientos para contrato: {}", dto.getContractId(), e);
            return Collections.emptyList();
        }
    }

    private void executeReversalsAndInsert(List<MicroloanMovement> movements, ProductInputDTO dto) {
        LOGGER.info("Iniciando proceso de reverso para contrato: {}", dto.getContractId());

        movements.forEach(movement -> executeProcessSingleReversal(movement, dto));

        if (executeEqualsAmount(dto)) {
            LOGGER.info("El monto total coincide. Ejecutando actualizaciones.");
            executeAllUpdates(dto);
        } else {
            BigDecimal total = executePlusAmount(dto);
            LOGGER.warn("Monto inconsistente. Monto original: {}, suma componentes: {}",
                    BigDecimal.valueOf(dto.getAmount()).setScale(2, RoundingMode.HALF_UP), total);
        }

        executeInsertMovementsBatch(movements);
    }

    private boolean executeEqualsAmount(ProductInputDTO dto) {
        BigDecimal original = BigDecimal.valueOf(dto.getAmount()).setScale(2, RoundingMode.HALF_UP);
        return original.compareTo(executePlusAmount(dto)) == 0;
    }

    BigDecimal executePlusAmount(ProductInputDTO dto) {
        return BigDecimal.valueOf(dto.getAmountCapital()).setScale(2, RoundingMode.HALF_UP)
                .add(BigDecimal.valueOf(dto.getAmountComision()).setScale(2, RoundingMode.HALF_UP))
                .add(BigDecimal.valueOf(dto.getAmountIva()).setScale(2, RoundingMode.HALF_UP));
    }

    private void executeProcessSingleReversal(MicroloanMovement movement, ProductInputDTO dto) {
        String originalCode = movement.getAccount().getEvent().getCode();
        String reversedCode = Diccionario.obtenerCodigoContrario(originalCode);

        if (reversedCode == null) {
            LOGGER.warn("No se encontro reverso para el codigo: {}", originalCode);
            return;
        }

        movement.getAccount().getEvent().setCode(reversedCode);
        movement.setDate(new Date());

        double amount = movement.getAmount().getAmount();
        dto.setMicroloanId(movement.getMicroloanId());
        dto.setMovId(movement.getAccount().getNumber());
        dto.setSequenceId(movement.getNumber());

        if (MOVIMIENTOS_CAPITAL.contains(originalCode)) {
            dto.setAmountCapital(amount);
            LOGGER.info("Reverso de CAPITAL aplicado: {}", amount);
        } else if (MOVIMIENTOS_IVA.contains(originalCode)) {
            dto.setAmountIva(amount);
            LOGGER.info("Reverso de IVA aplicado: {}", amount);
        } else if (MOVIMIENTOS_COMISION.contains(originalCode)) {
            dto.setAmountComision(amount);
            LOGGER.info("Reverso de COMISION aplicado: {}", amount);
        } else if (Diccionario.PGIVAGCB.equals(originalCode)) {
            dto.setAmountIvaCobranza(amount);
            LOGGER.info("Reverso de COBRANZA a iva aplicado: {}", amount);
        }else if (Diccionario.PGGASCOB.equals(originalCode)) {
            dto.setAmountCapCobranza(amount);
            LOGGER.info("Reverso de COBRANZA a capital aplicado: {}", amount);
        }else {
            LOGGER.warn("Tipo de movimiento no reconocido para reverso: {}", originalCode);
        }
    }

    private void executeAllUpdates(ProductInputDTO dto) {
        try {
            executeUpdateMicrocreditContract(dto);
            executeUpdateDisposition(dto);
            executeUpdateAmortizationContition(dto);
            executeUpdateDspnAmort(dto);
            executeUpdateContractCondition(dto);
            LOGGER.info("Actualizaciones completadas correctamente para el contrato: {}", dto.getContractId());
        } catch (Exception e) {
            LOGGER.error("Error en la ejecución de actualizaciones para el contrato: {}", dto.getContractId(), e);
        }
    }

    public int executeInsertMovementsBatch(List<MicroloanMovement> movements) {
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

    @Override
    public int executeUpdateMicrocreditContract(ProductInputDTO dto) {
        return executeUpdates(Constants.UPDATE_MICROCREDIT_CONTRACT, Mapper.buildParamsUpdateMicrocreditContract(dto));
    }

    @Override
    public int executeUpdateDisposition(ProductInputDTO dto) {
        return executeUpdates(Constants.UPDATE_MCRCR_DISPOSITION, Mapper.buildParamsUpdateDisposition(dto));
    }

    @Override
    public int executeUpdateAmortizationContition(ProductInputDTO dto) {
        return executeUpdates(Constants.UPDATE_AMORTIZATION_CONDITION, Mapper.buildParamsUpdateAmortizationCondition(dto));
    }

    @Override
    public int executeUpdateDspnAmort(ProductInputDTO dto) {
        return executeUpdates(Constants.UPDATE_MCRCR_AMORTIZATION, Mapper.buildParamsUpdateAmortization(dto));
    }
    
    @Override
    public int executeUpdateContractCondition(ProductInputDTO dto) {
        return executeUpdates(Constants.UPDATE_CONTRACT_CONDITION, Mapper.buildParamsUpdateContractCondition(dto));
    }

    private int executeUpdates(String queryKey, Map<String, Object> params) {
        try {
            LOGGER.info("Ejecutando update [{}] con parametros: {}", queryKey, params);
            return jdbcUtils.update(queryKey, params);
        } catch (DBException e) {
            LOGGER.info("Error ejecutando update [{}] para contrato: {}", queryKey, params.get("contractId"));
            return 0;
        }
    }
}


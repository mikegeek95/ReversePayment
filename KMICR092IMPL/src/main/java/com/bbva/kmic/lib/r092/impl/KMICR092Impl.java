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

            List<MicroloanMovement> originalMovements = getMovementList(dto);
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

    private MicroloanMovement fetchMicroloanMovement(MicroloanMovement input) {
        try {
            LOGGER.info("Buscando movimiento con parámetros: {}", input);
            MicroloanMovement result = kmicR060.executeGetMicroloanMovement(input);
            if (result == null) LOGGER.warn("Movimiento no encontrado: {}", input);
            return result;
        } catch (Exception e) {
            LOGGER.error("Error al obtener movimiento de microcrédito", e);
            return null;
        }
    }

    @Override
    public List<MicroloanMovement> getMovementList(ProductInputDTO dto) {
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

    private void applyReversalsAndInsert(List<MicroloanMovement> movements, ProductInputDTO dto) {
        LOGGER.info("Iniciando proceso de reverso para contrato: {}", dto.getContractId());

        movements.forEach(movement -> processSingleReversal(movement, dto));

        if (montoTotalCoincide(dto)) {
            LOGGER.info("El monto total coincide. Ejecutando actualizaciones.");
            executeAllUpdates(dto);
        } else {
            BigDecimal total = sumaComponentes(dto);
            LOGGER.warn("Monto inconsistente. Monto original: {}, suma componentes: {}",
                    BigDecimal.valueOf(dto.getAmount()).setScale(2, RoundingMode.HALF_UP), total);
        }

        insertMovementsBatch(movements);
    }

    private boolean montoTotalCoincide(ProductInputDTO dto) {
        BigDecimal original = BigDecimal.valueOf(dto.getAmount()).setScale(2, RoundingMode.HALF_UP);
        return original.compareTo(sumaComponentes(dto)) == 0;
    }

    private BigDecimal sumaComponentes(ProductInputDTO dto) {
        return BigDecimal.valueOf(dto.getAmountCapital()).setScale(2, RoundingMode.HALF_UP)
                .add(BigDecimal.valueOf(dto.getAmountComision()).setScale(2, RoundingMode.HALF_UP))
                .add(BigDecimal.valueOf(dto.getAmountIva()).setScale(2, RoundingMode.HALF_UP));
    }

    private void processSingleReversal(MicroloanMovement movement, ProductInputDTO dto) {
        String originalCode = movement.getAccount().getEvent().getCode();
        String reversedCode = Diccionario.obtenerCodigoContrario(originalCode);

        if (reversedCode == null) {
            LOGGER.warn("No se encontró reverso para el código: {}", originalCode);
            return;
        }

        movement.getAccount().getEvent().setCode(reversedCode);
        movement.setDate(new Date());

        double amount = movement.getAmount().getAmount();
        dto.setMicroloanId(movement.getMicroloanId());

        if (MOVIMIENTOS_CAPITAL.contains(originalCode)) {
            dto.setAmountCapital(amount);
            LOGGER.info("Reverso de CAPITAL aplicado: {}", amount);
        } else if (MOVIMIENTOS_IVA.contains(originalCode)) {
            dto.setAmountIva(amount);
            LOGGER.info("Reverso de IVA aplicado: {}", amount);
        } else if (MOVIMIENTOS_COMISION.contains(originalCode)) {
            dto.setAmountComision(amount);
            LOGGER.info("Reverso de COMISIÓN aplicado: {}", amount);
        } else {
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

    @Override
    public int executeUpdateMicrocreditContract(ProductInputDTO dto) {
        return ejecutarUpdate(Constants.UPDATE_MICROCREDIT_CONTRACT, Mapper.buildParamsUpdateMicrocreditContract(dto));
    }

    @Override
    public int executeUpdateDisposition(ProductInputDTO dto) {
        return ejecutarUpdate(Constants.UPDATE_MCRCR_DISPOSITION, Mapper.buildParamsUpdateDisposition(dto));
    }

    @Override
    public int executeUpdateAmortizationContition(ProductInputDTO dto) {
        return ejecutarUpdate(Constants.UPDATE_AMORTIZATION_CONDITION, Mapper.buildParamsUpdateAmortizationCondition(dto));
    }

    @Override
    public int executeUpdateDspnAmort(ProductInputDTO dto) {
        return ejecutarUpdate(Constants.UPDATE_MCRCR_AMORTIZATION, Mapper.buildParamsUpdateAmortization(dto));
    }

    private int ejecutarUpdate(String queryKey, Map<String, Object> params) {
        try {
            LOGGER.info("Ejecutando update [{}] con parametros: {}", queryKey, params);
            return jdbcUtils.update(queryKey, params);
        } catch (DBException e) {
            LOGGER.info("Error ejecutando update [{}] para contrato: {}", queryKey, params.get("contractId"));
            return 0;
        }
    }
}


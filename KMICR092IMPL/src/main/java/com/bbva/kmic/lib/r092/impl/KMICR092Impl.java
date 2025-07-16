package com.bbva.kmic.lib.r092.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import com.bbva.apx.exception.db.DBException;
import com.bbva.kmic.dto.movementmodel.MicroloanMovement;
import com.bbva.kmic.dto.payments.ProductInputDTO;

import Constants.Constants;
import Constants.Diccionario;
import Utils.Mapper;

public class KMICR092Impl extends KMICR092Abstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(KMICR092Impl.class);

    private static final Set<String> MOVIMIENTOS_CAPITAL = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
        Diccionario.PAGMENCA, Diccionario.PGANTCAP, Diccionario.PGVENCAP
    )));
    private static final Set<String> MOVIMIENTOS_IVA = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
        Diccionario.PGMNIVAC, Diccionario.PIVACOMD, Diccionario.PGVNIVAC
    )));
    private static final Set<String> MOVIMIENTOS_COMISION = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
        Diccionario.PGMNCMDI, Diccionario.PGCOMDIS, Diccionario.PGVNCDIS
    )));
    private static final Set<String> MOVIMIENTOS_AUTOMATICOS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
        Diccionario.PGAUTCON, Diccionario.PGANTCON, Diccionario.PGVENCON
    )));

    private static final Map<String, BiConsumer<ProductInputDTO, Double>> REVERSAL_ACTIONS = new HashMap<>();
    static {
        MOVIMIENTOS_CAPITAL.forEach(code -> REVERSAL_ACTIONS.put(code, ProductInputDTO::setAmountCapital));
        MOVIMIENTOS_IVA.forEach(code -> REVERSAL_ACTIONS.put(code, ProductInputDTO::setAmountIva));
        MOVIMIENTOS_COMISION.forEach(code -> REVERSAL_ACTIONS.put(code, ProductInputDTO::setAmountComision));
        MOVIMIENTOS_AUTOMATICOS.forEach(code -> REVERSAL_ACTIONS.put(code, ProductInputDTO::setAmountAutomatico));
        REVERSAL_ACTIONS.put(Diccionario.PGIVAGCB, ProductInputDTO::setAmountIvaCobranza);
        REVERSAL_ACTIONS.put(Diccionario.PGGASCOB, ProductInputDTO::setAmountCapCobranza);
    }

    @Override
    public void executeGetReversePayments(List<ProductInputDTO> items) {
        for (ProductInputDTO dto : items) {
            LOGGER.info("[KMICR092] DTO recibido: {}", dto);

            List<MicroloanMovement> originalMovements = executeGetMovementList(dto);
            if (originalMovements.isEmpty()) {
                LOGGER.warn("No hay movimientos para procesar para contrato: {}", dto.getContractId());
                continue;
            }

            List<MicroloanMovement> reversados = getMovimientosReversados(originalMovements);
            List<MicroloanMovement> noReversados = getMovimientosNoReversados(originalMovements);

            Set<String> movimientosYaReversados = buildReversoIndex(reversados);

            List<MicroloanMovement> movimientosFinales = noReversados.stream()
                .filter(mov -> !estaYaReversado(mov, movimientosYaReversados))
                .filter(mov -> executeFetchMicroloanMovement(mov) != null)
                .collect(Collectors.toList());

            if (movimientosFinales.isEmpty()) {
                LOGGER.warn("No se encontró ningún movimiento válido para: {}", dto.getContractId());
                continue;
            }

            List<MicroloanMovement> selectedCombination = findMatchingCombination(movimientosFinales, dto.getAmount());

            if (!selectedCombination.isEmpty()) {
                selectedCombination.forEach(movement -> executeProcessSingleReversal(movement, dto));
                LOGGER.info("Combinación válida encontrada. Ejecutando actualizaciones.");
                executeAllUpdates(dto);
                executeInsertMovementsBatch(selectedCombination);
            } else {
                LOGGER.warn("No se encontró combinación que coincida con el monto: {}", dto.getAmount());
            }
        }
    }

    private List<MicroloanMovement> getMovimientosReversados(List<MicroloanMovement> movimientos) {
        return movimientos.stream()
            .filter(m -> Diccionario.esMovimientoYaReversado(m.getAccount().getEvent().getCode()))
            .collect(Collectors.toList());
    }

    private List<MicroloanMovement> getMovimientosNoReversados(List<MicroloanMovement> movimientos) {
        return movimientos.stream()
            .filter(m -> !Diccionario.esMovimientoYaReversado(m.getAccount().getEvent().getCode()))
            .collect(Collectors.toList());
    }

    private Set<String> buildReversoIndex(List<MicroloanMovement> reversados) {
        return reversados.stream()
            .map(m -> generarClaveMovimiento(m.getAccount().getEvent().getCode(), m.getAmount().getAmount(), m.getAccount().getNumber()))
            .collect(Collectors.toSet());
    }

    private boolean estaYaReversado(MicroloanMovement mov, Set<String> movimientosReversados) {
        String codigoReverso = Diccionario.obtenerCodigoContrario(mov.getAccount().getEvent().getCode());
        if (codigoReverso == null) return false;

        String clave = generarClaveMovimiento(codigoReverso, mov.getAmount().getAmount(), mov.getAccount().getNumber());
        boolean encontrado = movimientosReversados.contains(clave);
        if (encontrado) {
            LOGGER.warn("Movimiento {} ya fue reversado anteriormente, se omite", mov);
        }
        return encontrado;
    }

    private String generarClaveMovimiento(String code, double amount, String accountNumber) {
        return code + "|" + toMoney(amount) + "|" + accountNumber;
    }


    private MicroloanMovement executeFetchMicroloanMovement(MicroloanMovement input) {
        try {
            return kmicR060.executeGetMicroloanMovement(input);
        } catch (Exception e) {
            LOGGER.error("Error al obtener movimiento de microcrédito", e);
            return null;
        }
    }

    @Override
    public List<MicroloanMovement> executeGetMovementList(ProductInputDTO dto) {
        try {
            Map<String, Object> params = Mapper.buildParamsLogMovement(dto);
            List<Map<String, Object>> rows = jdbcUtils.queryForList(Constants.SELECT_TRAE_DATOS_LOG, params);
            return Mapper.mapListMicroloanMovement(rows);
        } catch (Exception e) {
            LOGGER.error("Error al obtener movimientos para contrato: {}", dto.getContractId(), e);
            return Collections.emptyList();
        }
    }

    private List<MicroloanMovement> findMatchingCombination(List<MicroloanMovement> movements, double targetAmount) {
        List<MicroloanMovement> result = new ArrayList<>();
        int n = movements.size();
        BigDecimal target = toMoney(targetAmount);

        for (int i = 1; i < (1 << n); i++) {
            List<MicroloanMovement> subset = new ArrayList<>();
            BigDecimal sum = BigDecimal.ZERO;

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    MicroloanMovement m = movements.get(j);
                    subset.add(m);
                    sum = sum.add(toMoney(m.getAmount().getAmount()));
                }
            }
            if (sum.compareTo(target) == 0) return subset;
        }
        return result;
    }

    private BigDecimal toMoney(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    private void executeProcessSingleReversal(MicroloanMovement movement, ProductInputDTO dto) {
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
        dto.setMovId(movement.getAccount().getNumber());

        BiConsumer<ProductInputDTO, Double> action = REVERSAL_ACTIONS.get(originalCode);
        if (action != null) {
            action.accept(dto, amount);
            LOGGER.info("Reverso aplicado para código {}: {}", originalCode, amount);
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
            executeUpdateContractCondition(dto);
        } catch (Exception e) {
            LOGGER.error("Error ejecutando actualizaciones para contrato: {}", dto.getContractId(), e);
        }
    }

    private int executeInsertMovementsBatch(List<MicroloanMovement> movements) {
        try {
            return kmicR060.executeCreateMicroloanMovements(movements);
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


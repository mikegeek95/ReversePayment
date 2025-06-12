package Utils;

import java.math.BigDecimal;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import com.bbva.kmic.dto.payments.*;
import com.bbva.kmic.lib.r092.impl.KMICR092Impl;
import com.bbva.kmic.dto.movementmodel.MicroloanMovement;

public class ReverseCalc {
	
	Consultas consult= new Consultas();
	MappingMeth mapper = new MappingMeth();
	private static final Logger LOGGER = LoggerFactory.getLogger(KMICR092Impl.class);


    
	public MicroloanMovement createLog(int id, double amountReverse, MicroloanMovement movement, String operationId) {
	    Date currentDate = getCurrentDateWithoutTime();
	    AmountTypeDto amount = createAmount(amountReverse, currentDate);

	    switch (id) {
	        case 1:
	            amount.setStatus("ANPGMNCN");
	            return mapper.mapToLog(movement, amount);

	        case 2:
	            return createMovementWithOperationId("ANPGMNCA", amount, movement, operationId);

	        case 3:
	            return createMovementWithOperationId("ANPGMNCD", amount, movement, operationId);

	        case 4:
	            return createMovementWithOperationId("ANPGMNIC", amount, movement, operationId);

	        default:
	            LOGGER.info("Error al crear log");
	            return null;
	    }
	}

	private Date getCurrentDateWithoutTime() {
	    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	    try {
	        return format.parse(format.format(new Date()));
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return new Date();
	    }
	}

	private AmountTypeDto createAmount(double amountValue, Date date) {
	    AmountTypeDto amount = new AmountTypeDto();
	    amount.setAmount(amountValue);
	    amount.setDate(date);
	    return amount;
	}

	private MicroloanMovement createMovementWithOperationId(String status, AmountTypeDto amount, MicroloanMovement baseMovement, String operationId) {
	    amount.setStatus(status);
	    MicroloanMovement movement = mapper.mapToLog(baseMovement, amount);
	    movement.setMicroloanId(operationId);
	    return movement;
	}

	
	public MicrocreditContractDto calculateMicrocredit(MicrocreditContractDto contractDto, BigDecimal reverseAmountTotal) {
	    BigDecimal drwdnAmount = new BigDecimal(contractDto.getDrwdnAmount()).add(reverseAmountTotal);
	    BigDecimal availableAmount = new BigDecimal(contractDto.getAvailableAmount()).subtract(reverseAmountTotal);

	    contractDto.setDrwdnAmount(drwdnAmount.doubleValue());
	    contractDto.setAvailableAmount(availableAmount.doubleValue());

	    return contractDto;
	}

	
	public AmortConditionDto calculateAmortizationCondition(AmortConditionDto amorCond, BigDecimal comision, BigDecimal iva) {
	    BigDecimal feeAmount = new BigDecimal(amorCond.getFeeAmount()).subtract(comision);
	    BigDecimal recTaxAmount = new BigDecimal(amorCond.getRecTaxAmount()).subtract(iva);

	    amorCond.setFeeAmount(feeAmount.doubleValue());
	    amorCond.setRecTaxAmount(recTaxAmount.doubleValue());
	    amorCond.setStatusType("PENDING");

	    return amorCond;
	}

	
	public ContDispositionDto calculateDisposition(List<ContDispositionDto> dispositionDtos, BigDecimal saveAmount, String operationId) {
	    ContDispositionDto dispositionDto = findDispositionByOperationId(dispositionDtos, operationId);
	    if (dispositionDto == null) {
	        return null;
	    }

	    updateDispositionAmounts(dispositionDto, saveAmount);
	    updateDispositionStatus(dispositionDto);

	    return dispositionDto;
	}

	private ContDispositionDto findDispositionByOperationId(List<ContDispositionDto> list, String operationId) {
	    return list.stream()
	            .filter(d -> d.getOperationId().equals(operationId))
	            .findFirst().orElse(null);
	}

	private void updateDispositionAmounts(ContDispositionDto dto, BigDecimal amount) {
	    BigDecimal recoveredTotal = new BigDecimal(dto.getRecoveredTotal()).subtract(amount);
	    BigDecimal repayment = new BigDecimal(dto.getRepaymentAmount()).add(amount);

	    dto.setRecoveredTotal(recoveredTotal.doubleValue());
	    dto.setRepaymentAmount(repayment.doubleValue());
	}

	private void updateDispositionStatus(ContDispositionDto dto) {
	    if (Double.compare(dto.getRepaymentAmount(), 0.0) != 0) {
	        dto.setStatusType("PENDING");
	    }
	}

	
	public Double executeGetAmount(int id, List<MicroloanMovement> listEvents) {
	    String eventCode = getEventCodeById(id);
	    if (eventCode == null) return 0.0;

	    return listEvents.stream()
	            .filter(m -> m.getAccount().getEvent().getCode().equals(eventCode))
	            .map(m -> m.getAmount().getAmount())
	            .findFirst()
	            .orElse(0.0);
	}

	private String getEventCodeById(int id) {
	    switch (id) {
	        case 1: return "PAGMENCA";
	        case 2: return "PGMNCMDI";
	        case 3: return "PGMNIVAC";
	        default: return null;
	    }
	}


}

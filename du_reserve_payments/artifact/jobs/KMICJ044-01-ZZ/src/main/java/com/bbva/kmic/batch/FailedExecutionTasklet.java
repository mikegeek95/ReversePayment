package com.bbva.kmic.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FailedExecutionTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(FailedExecutionTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        logger.error("Ejecución fallida en uno de los pasos anteriores. Se ha activado el stepFailedExecution.");
        // Puedes agregar lógica adicional aquí (notificación, escritura de log, etc.)
        return RepeatStatus.FINISHED;
    }
}

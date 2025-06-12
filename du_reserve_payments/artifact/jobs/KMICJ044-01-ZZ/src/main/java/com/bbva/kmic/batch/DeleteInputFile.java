package com.bbva.kmic.batch;

import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;


public class DeleteInputFile implements Tasklet {
	
	private static final Logger LOG = LoggerFactory.getLogger(DeleteInputFile.class);

    private String filename;
    private PathUtils pathUtils;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setPathUtils(PathUtils pathUtils) {
        this.pathUtils = pathUtils;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Path path = pathUtils.getFile(filename);
        Files.deleteIfExists(path);
        LOG.info("[KMICJ044] Archivo eliminado: {}", path.toString());
        return RepeatStatus.FINISHED;
    }

}

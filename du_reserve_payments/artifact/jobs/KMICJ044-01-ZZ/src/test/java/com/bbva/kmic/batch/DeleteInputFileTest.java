package com.bbva.kmic.batch;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class DeleteInputFileTest {

    private DeleteInputFile deleteInputFile;

    @Mock
    private PathUtils pathUtils;

    @Mock
    private StepContribution contribution;

    @Mock
    private ChunkContext chunkContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        deleteInputFile = new DeleteInputFile();
        deleteInputFile.setPathUtils(pathUtils);
        deleteInputFile.setFilename("inputfile.txt");
    }

    @Test
    public void testExecute_success() throws Exception {
        Path path = Paths.get("inputfile.txt");
        when(pathUtils.getFile("inputfile.txt")).thenReturn(path);

        RepeatStatus status = deleteInputFile.execute(contribution, chunkContext);

        assertEquals(RepeatStatus.FINISHED, status);
    }
}

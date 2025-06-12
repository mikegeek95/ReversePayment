package com.bbva.kmic.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

public class PathUtilsTest {

    private PathUtils pathUtils;

    @Before
    public void setUp() {
        pathUtils = new PathUtils();
    }

    @Test
    public void testGetFile_returnsCorrectPath() {
        // Arrange
        String filename = "testfile.txt";

        // Act
        Path path = pathUtils.getFile(filename);

        // Assert
        assertNotNull(path);
        assertEquals(filename, path.getFileName().toString());
    }
}

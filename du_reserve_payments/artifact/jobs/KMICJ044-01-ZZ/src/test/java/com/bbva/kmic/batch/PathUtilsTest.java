package com.bbva.kmic.batch;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PathUtilsTest {

    private PathUtils pathUtils;

    @Before
    public void setUp() {
        pathUtils = new PathUtils();
    }

    @Test
    public void testGetFile_returnsCorrectPath() {
        String filename = "testfile.txt";

        Path path = pathUtils.getFile(filename);

        assertNotNull(path);
        assertEquals("testfile.txt", path.getFileName().toString());
    }
}

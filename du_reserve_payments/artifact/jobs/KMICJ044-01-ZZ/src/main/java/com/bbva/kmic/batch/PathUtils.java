package com.bbva.kmic.batch;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtils {
    public Path getFile(String filename){
        return Paths.get(filename);
    }
}
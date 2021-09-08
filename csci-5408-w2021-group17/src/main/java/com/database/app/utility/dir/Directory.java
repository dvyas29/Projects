package com.database.app.utility.dir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Directory {
    final private String DATABASE_DIRECTORY = "dql\\";

    private String dbName;

    public Directory(String dbName) {
        this.dbName = dbName;
    }

    public boolean isDirectoryPresent() {
        return Files.exists(Paths.get(DATABASE_DIRECTORY + dbName));
    }

    public void createDirectory() {
        if (Files.notExists(Paths.get(DATABASE_DIRECTORY + dbName))) {
            try {
                Files.createDirectory(Paths.get(DATABASE_DIRECTORY + dbName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeDirectory() {
        try {
            Files.deleteIfExists(Paths.get(DATABASE_DIRECTORY + dbName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

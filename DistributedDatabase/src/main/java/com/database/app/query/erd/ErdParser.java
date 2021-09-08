package com.database.app.query.erd;

import com.database.app.model.Column;
import com.database.app.utility.dir.Directory;
import com.database.app.utility.files.FileOperation;
import com.database.app.utility.regex.RegexMatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ErdParser implements ErdInterface {

    private static final String ERD_QUERY_REGEX = "^ERD [A-Z_]*$";
    final private String DATABASE_DIRECTORY = "dql\\";

    @Override
    public boolean validateQuery(String query) {
        RegexMatcher regexMatcher = new RegexMatcher();
        return regexMatcher.matchQuery(query.toUpperCase(), ERD_QUERY_REGEX);
    }

    @Override
    public String extractDbName(String query) {
        int indexOfExportKeyword = query.toUpperCase().indexOf("EXPORT");
        int indexOfSecondSpace = query.indexOf(" ", indexOfExportKeyword + 1);
        return query.substring(indexOfSecondSpace + 1).toLowerCase().trim();
    }

    @Override
    public boolean isDbPresent(String dbName) {
        Directory directory = new Directory(dbName);
        return directory.isDirectoryPresent();
    }

    @Override
    public void generateErdFile(String dbName) {
        List<File> metadataFiles;
        try {
            metadataFiles = Files.list(Paths.get(DATABASE_DIRECTORY + dbName))
                    .filter(Files -> Files.toString().endsWith("meta"))
                    .sorted(Comparator.comparingLong(filePath -> filePath.toFile().lastModified()))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            String fileContent = "";
            for (File metadataFile : metadataFiles) {
                FileOperation fileOperation = new FileOperation(dbName, metadataFile.getName());
                Map<Integer, Column> tableMetaData = fileOperation.readTableMetaData();
                StringBuilder columnReferenceBuilder = new StringBuilder();
                for (Column column : tableMetaData.values()) {
                    if (column.getConstraint().equals("FOREIGN")) {
                        String tableName = metadataFile.getName().split("_")[0];
                        columnReferenceBuilder.append(tableName).append(" ---- references ----> ").append(column.getForeignKeyTable().toLowerCase());
                    }
                }
                if (columnReferenceBuilder.toString().length() > 0) {
                    fileContent += columnReferenceBuilder.toString() + "\n";
                }
            }
            this.saveToFile(fileContent, dbName);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void saveToFile(String fileContent, String dbName) {
        try {
            Files.writeString(Paths.get(DATABASE_DIRECTORY + dbName + "\\" + dbName + ".erd"), fileContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

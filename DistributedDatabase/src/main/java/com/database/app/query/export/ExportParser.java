package com.database.app.query.export;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class ExportParser implements ExportInterface {

    private static final String EXPORT_QUERY_REGEX = "^EXPORT [A-Z_]*$";
    final private String DATABASE_DIRECTORY = "dql\\";

    @Override
    public boolean validateQuery(String query) {
        RegexMatcher regexMatcher = new RegexMatcher();
        return regexMatcher.matchQuery(query.toUpperCase(), EXPORT_QUERY_REGEX);
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
    public void createDumpFile(String dbName) {
        List<File> metadataFiles;
        try {
            metadataFiles = Files.list(Paths.get(DATABASE_DIRECTORY + dbName))
                    .filter(Files -> Files.toString().endsWith("meta"))
                    .sorted(Comparator.comparingLong(filePath -> filePath.toFile().lastModified()))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            String fileContent = "CREATE DATABASE " + dbName + "\n\n";
            ArrayList<String> fileSequence = new ArrayList<>();
            for (File metadataFile : metadataFiles) {
                String fileName = metadataFile.getName().split("_")[0];
                fileSequence.add(fileName);
                StringBuilder createQueryBuilder = new StringBuilder("CREATE TABLE " + fileName + "(");
                FileOperation fileOperation = new FileOperation(dbName, metadataFile.getName());
                Map<Integer, Column> tableMetaData = fileOperation.readTableMetaData();
                StringJoiner createQueryJoiner = new StringJoiner(", ");
                for (Column column : tableMetaData.values()) {
                    StringBuilder columnBuilder = new StringBuilder();
                    columnBuilder.append(column.getType()).append(" ").append(column.getName());
                    if (column.getConstraint().startsWith("PRIMARY")) {
                        columnBuilder.append(" PRIMARY KEY");
                    } else if (column.getConstraint().equals("FOREIGN")) {
                        columnBuilder.append(" FOREIGN KEY REFERENCES ")
                                .append(column.getForeignKeyTable().toLowerCase()).append("(")
                                .append(column.getForeignKeyColumn()).append(")");
                    }
                    createQueryJoiner.add(columnBuilder);
                }
                createQueryBuilder.append(createQueryJoiner.toString());
                createQueryBuilder.append(")");
                fileContent += createQueryBuilder.toString() + "\n";
            }

            for (String fileName : fileSequence) {
                fileContent += "\n\n";
                FileOperation fileOperation = new FileOperation(dbName, fileName + "_table.txt");
                List<String> tableRows = fileOperation.readTableData();
                for (String row : tableRows) {
                    StringBuilder insertQueryBuilder = new StringBuilder();
                    insertQueryBuilder.append("INSERT INTO ").append(fileName).append(" VALUES (");
                    List<String> values = Arrays.asList(row.split("\\|"));
                    String insertValues = String.join(", ", values);
                    insertQueryBuilder.append(insertValues).append(")");
                    fileContent += insertQueryBuilder.toString() + "\n";
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
            Files.writeString(Paths.get(DATABASE_DIRECTORY + dbName + "\\" + dbName + "_dump.dmp"), fileContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

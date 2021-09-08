package com.database.app.query.export;

public interface ExportInterface {

    boolean validateQuery(String query);

    String extractDbName(String query);

    boolean isDbPresent(String dbName);

    void createDumpFile(String dbName);

    void saveToFile(String fileContent, String dbName);
}

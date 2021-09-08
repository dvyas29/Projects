package com.database.app.query.erd;

public interface ErdInterface {

    boolean validateQuery(String query);

    String extractDbName(String query);

    boolean isDbPresent(String dbName);

    void generateErdFile(String dbName);

    void saveToFile(String fileContent, String dbName);


}

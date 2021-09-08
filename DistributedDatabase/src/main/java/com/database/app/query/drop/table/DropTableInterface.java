package com.database.app.query.drop.table;

public interface DropTableInterface {

    boolean validateQuery(String query);

    String extractTableName(String query);

    boolean isTablePresent(String dbName, String tableName);

    void dropTable(String dbName, String tableName);

    void dropTableMetadata(String dbName, String tableName);
}

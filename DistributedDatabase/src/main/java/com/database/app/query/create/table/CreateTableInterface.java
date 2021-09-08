package com.database.app.query.create.table;

import com.database.app.model.Column;

import java.util.List;

public interface CreateTableInterface {

    boolean validateQuery(String query);

    String extractTableName(String query);

    boolean isTablePresent(String dbName, String tableName);

    List<Column> extractColumns(String query);

    void createTable(String dbName, String tableName);

    void createTableMetadata(String dbName, String tableName, List<Column> columns);

    boolean checkIfForeignKeyTableExists(String dbName, List<Column> columns);

    boolean checkIfForeignKeyColumnExists(String dbName, List<Column> columns);
}

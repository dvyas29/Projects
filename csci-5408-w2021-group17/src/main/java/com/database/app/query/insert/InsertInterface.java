package com.database.app.query.insert;

import com.database.app.model.Column;

import java.util.Map;

public interface InsertInterface {

    boolean validateQuery(String query);

    String extractTableName(String query);

    boolean isTablePresent(String dbName, String tableName);

    Map<Integer, Column> getTableColumns(String dbName, String tableName);

    boolean columnsPresent(String[] dataArray, Map<Integer, Column> tableColumnMap);

    String[] extractData(String query);

    boolean isDataValid(String[] dataArray, Map<Integer, Column> columnMap);

    void writeData(String dbName, String tableName, String[] dataArray);

    boolean checkDuplicatePrimaryKey(String dbName, String tableName, Map<Integer, Column> tableColumnMap, String[] dataArray);
}

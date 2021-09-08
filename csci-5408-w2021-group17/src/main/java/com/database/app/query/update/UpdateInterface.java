package com.database.app.query.update;

import com.database.app.model.Column;

import java.util.Map;

public interface UpdateInterface {

    boolean validateQuery(String query);

    String extractTableName(String query);

    boolean isTablePresent(String dbName, String tableName);

    String extractUpdateColumn(String query);

    Map<String, Column> getTableColumns(String dbName, String tableName);

    boolean isUpdateColumnPresent(String updateColumn, Map<String, Column> tableColumnMap);

    String extractUpdateWhereColumn(String query);

    boolean isUpdateWhereColumnPresent(String updateWhereColumn, Map<String, Column> tableColumnMap);

    String extractUpdateWhereValue(String query);

    String extractUpdateColumnData(String query);

    boolean isUpdateDataValid(String data, String updateColumn, Map<String, Column> tableColumnMap);

    void updateData(String dbName, String tableName, String updateColumn, String data, String updateWhereColumn,
					String updateWhereColumnValue, Map<String, Column> columnMap);
}

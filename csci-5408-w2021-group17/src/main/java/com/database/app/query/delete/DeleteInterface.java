package com.database.app.query.delete;

import com.database.app.model.Column;

import java.util.Map;

public interface DeleteInterface {

    boolean validateQuery(String query);

    String extractTableName(String query);

    boolean isTablePresent(String dbName, String tableName);

    String extractDeleteWhereColumn(String query);

    Map<String, Column> getTableColumns(String dbName, String tableName);

    boolean isDeleteColumnPresent(String deleteColumn, Map<String, Column> tableColumnMap);

    String extractWhereColumnValue(String query);

    void deleteData(String dbName, String tableName, String deleteWhereColumn,
					String deleteWhereColumnValue, Map<String, Column> columnMap);
}

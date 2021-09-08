package com.database.app.query.select;

import java.util.List;
import java.util.Map;

import com.database.app.model.Column;

public interface SelectInterface
{
	boolean validateQuery(String query);

	String extractTableName(String query);

	boolean isTablePresent(String dbName, String tableName);

	String[] extractColumns(String query);

	Map<String, Column> getTableColumns(String dbName, String tableName);

	boolean columnsPresent(String[] columns, Map<String, Column> tableColumnMap);
	
	String extractWhereColumnName(String query);
	
	String extractWhereColumnValue(String query);
	
	boolean isWhereColumnPresent(String whereColumnName, Map<String, Column> columnMap);
	
	List<String> readData(String dbName, String tableName, String[] columns, Map<String, Column> columnMap, String whereColumnName, String whereColumnValue);
}

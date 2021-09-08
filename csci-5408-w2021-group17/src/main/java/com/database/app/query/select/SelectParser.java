package com.database.app.query.select;

import com.database.app.model.Column;
import com.database.app.utility.files.FileOperation;
import com.database.app.utility.regex.RegexMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectParser implements SelectInterface {

    private FileOperation fileOperation;
    private static final String SELECT_QUERY_REGEX = "^SELECT (\\*|.*) FROM [A-Z_]* ?(WHERE [A-Z_]* = .*)?$";

    @Override
    public boolean validateQuery(String query) {
        RegexMatcher regexMatcher = new RegexMatcher();
        return regexMatcher.matchQuery(query.toUpperCase(), SELECT_QUERY_REGEX);
    }

    @Override
    public String extractTableName(String query) {
        int indexOfFromKeyword = query.toUpperCase().indexOf("FROM");
        int indexOfSpaceAfterFromKeyword = query.indexOf(" ", indexOfFromKeyword + 1);
        if (query.toUpperCase().contains("WHERE")) {
            int indexOfWhere = query.toUpperCase().indexOf("WHERE", indexOfSpaceAfterFromKeyword + 1);
            return query.substring(indexOfSpaceAfterFromKeyword + 1, indexOfWhere).toLowerCase().trim();
        }
        return query.substring(indexOfSpaceAfterFromKeyword + 1).toLowerCase().trim();
    }

    @Override
    public boolean isTablePresent(String dbName, String tableName) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table.txt");
        return fileOperation.isFilePresent();
    }

    @Override
    public String[] extractColumns(String query) {
        String[] columns = {};
        if (query.contains("*")) {
            return columns;
        } else {
            int indexOfFirstSpace = query.toUpperCase().indexOf(" ");
            int indexOfFrom = query.toUpperCase().indexOf("FROM", indexOfFirstSpace + 1);
            String selectColumnsString = query.substring(indexOfFirstSpace + 1, indexOfFrom).toUpperCase().trim();
            columns = selectColumnsString.split(",");
            return columns;
        }
    }

    @Override
    public Map<String, Column> getTableColumns(String dbName, String tableName) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table_meta.meta");
        return fileOperation.readTableMetaDataStringKey();
    }

    @Override
    public boolean columnsPresent(String[] columns, Map<String, Column> tableColumnMap) {
        boolean columnsPresent = true;
		for (String column : columns) {
			if (!tableColumnMap.containsKey(column.trim())) {
				columnsPresent = false;
			}
		}
        return columnsPresent;
    }

    @Override
    public String extractWhereColumnName(String query) {
        if (query.toUpperCase().contains("WHERE")) {
            int indexOfWhere = query.toUpperCase().indexOf("WHERE");
            int indexOfSpaceAfterWhere = query.indexOf(" ", indexOfWhere + 1);
            int indexOfEqual = query.indexOf("=", indexOfSpaceAfterWhere);
            return query.substring(indexOfSpaceAfterWhere + 1, indexOfEqual).toUpperCase().trim();
        }
        return null;
    }

    @Override
    public String extractWhereColumnValue(String query) {
        if (query.toUpperCase().contains("WHERE")) {
            int indexOfEqual = query.indexOf("=");
            return query.substring(indexOfEqual + 1).trim();
        }
        return null;
    }

    @Override
    public List<String> readData(String dbName, String tableName, String[] columns, Map<String, Column> columnMap,
                                 String whereColumnName, String whereColumnValue) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table.txt");
        List<String> dataLines = fileOperation.readTableData();
        List<String> selectDataLines = new ArrayList<String>();
        if (whereColumnName != null) {
            int whereColumnIndex = columnMap.get(whereColumnName).getIndex();
            if (columnMap.get(whereColumnName).getType().equals("VARCHAR")) {
                whereColumnValue = "'" + whereColumnValue + "'";
            }
            String[] dataArray;
            for (String dataLine : dataLines) {
                dataArray = dataLine.split("\\|");
                if (dataArray[whereColumnIndex].equals(whereColumnValue)) {
                    selectDataLines.add(dataLine);
                }
            }
        } else {
            selectDataLines.addAll(dataLines);
        }
        if (columns.length == 0) {
            return selectDataLines;
        } else {
            List<String> selectColumnsdataLines = new ArrayList<String>();
            Column column;
            String[] dataLineArray;
            StringBuilder newDataLine = new StringBuilder();
            for (String dataLine : selectDataLines) {
                dataLineArray = dataLine.split("\\|");
                for (int i = 0; i < columns.length; i++) {
                    column = columnMap.get(columns[i].trim());
                    if (i != columns.length - 1) {
                        newDataLine.append(dataLineArray[column.getIndex()]).append("|");
                    } else {
                        newDataLine.append(dataLineArray[column.getIndex()]);
                    }
                }
                selectColumnsdataLines.add(newDataLine.toString());
                newDataLine = new StringBuilder();
            }
            return selectColumnsdataLines;
        }
    }

    @Override
    public boolean isWhereColumnPresent(String whereColumnName, Map<String, Column> columnMap) {
        return columnMap.containsKey(whereColumnName);
    }

}

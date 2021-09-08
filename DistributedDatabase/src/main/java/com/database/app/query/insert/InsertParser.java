package com.database.app.query.insert;

import com.database.app.model.Column;
import com.database.app.utility.files.FileOperation;
import com.database.app.utility.regex.RegexMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InsertParser implements InsertInterface {

    private FileOperation fileOperation;
    private static final String INSERT_QUERY_REGEX = "^INSERT INTO [A-Z_]* VALUES \\(.*\\)$";


    @Override
    public boolean validateQuery(String query) {
        RegexMatcher regexMatcher = new RegexMatcher();
        return regexMatcher.matchQuery(query.toUpperCase(), INSERT_QUERY_REGEX);
    }

    @Override
    public String extractTableName(String query) {
        int indexOfIntoKeyword = query.toUpperCase().indexOf("INTO");
        int indexOfSpaceAfterIntoKeyword = query.toUpperCase().indexOf(" ", indexOfIntoKeyword + 1);
        int indexOfValuesKeyword = query.toUpperCase().indexOf("VALUES");
        return query.substring(indexOfSpaceAfterIntoKeyword + 1, indexOfValuesKeyword).trim();
    }

    @Override
    public boolean isTablePresent(String dbName, String tableName) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table.txt");
        return fileOperation.isFilePresent();
    }

    @Override
    public Map<Integer, Column> getTableColumns(String dbName, String tableName) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table_meta.meta");
        return fileOperation.readTableMetaData();
    }

    @Override
    public boolean columnsPresent(String[] dataArray, Map<Integer, Column> tableColumnMap) {
        return dataArray.length == tableColumnMap.size();
    }

    @Override
    public String[] extractData(String query) {
        int indexOfOpenBracket = query.indexOf("(");
        int indexOfCloseBracket = query.indexOf(")");
        return query.substring(indexOfOpenBracket + 1, indexOfCloseBracket).split(",");
    }

    @Override
    public boolean isDataValid(String[] dataArray, Map<Integer, Column> columnMap) {
        boolean isDataValid = true;
        String columnType;
        Column column;

        String checkStringData;
        int checkIntData;
        float checkFloatData;
        double checkDoubleData;
        boolean checkBooleanData;

        for (int i = 0; i < dataArray.length; i++) {
            column = columnMap.get(i);
            columnType = column.getType();
            switch (columnType) {
                case "VARCHAR": {
                    checkStringData = dataArray[i].trim();
                    isDataValid = checkStringData.startsWith("'");
                    if (!isDataValid) {
                        return false;
                    }
                    isDataValid = checkStringData.endsWith("'");
                    if (!isDataValid) {
                        return false;
                    }
                    break;
                }
                case "INT": {
                    try {
                        checkIntData = Integer.parseInt(dataArray[i].trim());
                    } catch (Exception e) {
                        return false;
                    }
                    break;
                }
                case "FLOAT": {
                    try {
                        checkFloatData = Float.parseFloat(dataArray[i].trim());
                    } catch (Exception e) {
                        return false;
                    }
                    break;
                }
                case "DOUBLE": {
                    try {
                        checkDoubleData = Double.parseDouble(dataArray[i].trim());
                    } catch (Exception e) {
                        return false;
                    }
                    break;
                }
                case "BOOLEAN": {
                    try {
                        checkBooleanData = Boolean.parseBoolean(dataArray[i].trim());
                    } catch (Exception e) {
                        return false;
                    }
                    break;
                }
                default:
                    break;
            }
        }
        return true;
    }

    @Override
    public void writeData(String dbName, String tableName, String[] dataArray) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table.txt");
        fileOperation.writeTableData(dataArray);
    }

    @Override
    public boolean checkDuplicatePrimaryKey(String dbName, String tableName, Map<Integer, Column> tableColumnMap,
                                            String[] dataArray) {
        List<Column> columns = new ArrayList<Column>(tableColumnMap.values());
        int primaryKeyIndex = 0;
        for (Column column : columns) {
            if (column.getConstraint().equals("PRIMARY")) {
                primaryKeyIndex = column.getIndex();
                break;
            }
        }

        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table.txt");
        List<String> dataLines = fileOperation.readTableData();
        String[] columnDataArray;

        String newDataPrimaryKey;
        String existingDataPrimaryKey;
        boolean isDuplicateKey = false;
        for (String dataLine : dataLines) {
            columnDataArray = dataLine.split("\\|");
            newDataPrimaryKey = dataArray[primaryKeyIndex].trim();
            existingDataPrimaryKey = columnDataArray[primaryKeyIndex].trim();
            if (newDataPrimaryKey.equals(existingDataPrimaryKey)) {
                isDuplicateKey = true;
                break;
            }
        }
        return isDuplicateKey;
    }

}

package com.database.app.query.update;

import com.database.app.model.Column;
import com.database.app.utility.files.FileOperation;
import com.database.app.utility.regex.RegexMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateParser implements UpdateInterface {

    private FileOperation fileOperation;

    private static final String UPDATE_TABLE_QUERY_REGEX = "^UPDATE [A-Z_]* SET [A-Z_]* = .* WHERE [A-Z_]* = .*$";


    @Override
    public boolean validateQuery(String query) {
        RegexMatcher regexMatcher = new RegexMatcher();
        return regexMatcher.matchQuery(query.toUpperCase(), UPDATE_TABLE_QUERY_REGEX);
    }

    @Override
    public String extractTableName(String query) {
        int indexOfSpace = query.toUpperCase().indexOf(" ");
        int indexOfSet = query.toUpperCase().indexOf("SET");
        return query.substring(indexOfSpace + 1, indexOfSet).toLowerCase().trim();
    }

    @Override
    public boolean isTablePresent(String dbName, String tableName) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table.txt");
        return fileOperation.isFilePresent();
    }

    @Override
    public String extractUpdateColumn(String query) {
        int indexOfSet = query.toUpperCase().indexOf("SET");
        int indexOfSpaceAfterSet = query.indexOf(" ", indexOfSet + 1);
        int indexOfEqual = query.indexOf("=", indexOfSpaceAfterSet);
        return query.substring(indexOfSpaceAfterSet + 1, indexOfEqual).toUpperCase().trim();
    }

    @Override
    public Map<String, Column> getTableColumns(String dbName, String tableName) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table_meta.meta");
        return fileOperation.readTableMetaDataStringKey();
    }

    @Override
    public boolean isUpdateColumnPresent(String updateColumn, Map<String, Column> tableColumnMap) {
        return tableColumnMap.containsKey(updateColumn);
    }

    @Override
    public String extractUpdateColumnData(String query) {
        int indexOfEqual = query.indexOf("=");
        int indexOfWhere = query.toUpperCase().indexOf("WHERE");
        return query.substring(indexOfEqual + 1, indexOfWhere).trim();
    }

    @Override
    public String extractUpdateWhereColumn(String query) {
        int indexOfWhere = query.toUpperCase().indexOf("WHERE");
        int indexOfSpaceAfterWhere = query.indexOf(" ", indexOfWhere + 1);
        int indexOfEqual = query.indexOf("=", indexOfSpaceAfterWhere);
        return query.substring(indexOfSpaceAfterWhere + 1, indexOfEqual).toUpperCase().trim();
    }

    @Override
    public boolean isUpdateWhereColumnPresent(String updateWhereColumn, Map<String, Column> tableColumnMap) {
        return tableColumnMap.containsKey(updateWhereColumn);
    }

    @Override
    public String extractUpdateWhereValue(String query) {
        int indexOfWhere = query.toUpperCase().indexOf("WHERE");
        int indexOfEqual = query.indexOf("=", indexOfWhere);
        return query.substring(indexOfEqual + 1, query.length()).trim();
    }

    @Override
    public boolean isUpdateDataValid(String data, String updateColumn, Map<String, Column> tableColumnMap) {
        boolean isDataValid = true;

        String checkStringData;
        int checkIntData;
        float checkFloatData;
        double checkDoubleData;
        boolean checkBooleanData;

        String columnType = tableColumnMap.get(updateColumn).getType();
        switch (columnType) {
            case "VARCHAR": {
                checkStringData = data.trim();
                isDataValid = checkStringData.startsWith("'");
                if (!isDataValid) {
                    return isDataValid;
                }
                isDataValid = checkStringData.endsWith("'");
                if (!isDataValid) {
                    return isDataValid;
                }
                break;
            }
            case "INT": {
                try {
                    checkIntData = Integer.parseInt(data.trim());
                } catch (Exception e) {
                    return false;
                }
                break;
            }
            case "FLOAT": {
                try {
                    checkFloatData = Float.parseFloat(data.trim());
                } catch (Exception e) {
                    return false;
                }
                break;
            }
            case "DOUBLE": {
                try {
                    checkDoubleData = Double.parseDouble(data.trim());
                } catch (Exception e) {
                    return false;
                }
                break;
            }
            case "BOOLEAN": {
                try {
                    checkBooleanData = Boolean.parseBoolean(data.trim());
                } catch (Exception e) {
                    return false;
                }
                break;
            }
            default:
                break;
        }
        return isDataValid;
    }

    @Override
    public void updateData(String dbName, String tableName, String updateColumn, String data, String updateWhereColumn,
                           String updateWhereColumnValue, Map<String, Column> columnMap) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table.txt");
        List<String> dataLines = fileOperation.readTableData();
        List<String[]> newDataLines = new ArrayList<String[]>();

        int updateWhereColumnIndex = columnMap.get(updateWhereColumn).getIndex();
        int updareColumnIndex = columnMap.get(updateColumn).getIndex();

        String[] dataLineArray;
        for (String dataLine : dataLines) {
            dataLineArray = dataLine.split("\\|");
            if (dataLineArray[updateWhereColumnIndex].equalsIgnoreCase(updateWhereColumnValue)) {
                dataLineArray[updareColumnIndex] = data;
            }
            newDataLines.add(dataLineArray);
        }

        fileOperation.removeFile();
        fileOperation.createFile();
        for (String[] dataArray : newDataLines) {
            fileOperation.writeTableData(dataArray);
        }
    }

}

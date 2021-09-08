package com.database.app.query.delete;

import com.database.app.model.Column;
import com.database.app.utility.files.FileOperation;
import com.database.app.utility.regex.RegexMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeleteParser implements DeleteInterface {

    private FileOperation fileOperation;
    private static final String DELETE_QUERY_REGEX = "^DELETE FROM [A-Z_]* ?(WHERE [A-Z_]* = .*)?$";

    @Override
    public boolean validateQuery(String query) {
        RegexMatcher regexMatcher = new RegexMatcher();
        return regexMatcher.matchQuery(query.toUpperCase(), DELETE_QUERY_REGEX);
    }

    @Override
    public String extractTableName(String query) {
        int indexOfFromKeyword = query.toUpperCase().indexOf("FROM");
        int indexOfSpaceAfterFromKeyword = query.indexOf(" ", indexOfFromKeyword + 1);
        int indexOfWhere = query.toUpperCase().indexOf("WHERE", indexOfSpaceAfterFromKeyword + 1);
        return query.substring(indexOfSpaceAfterFromKeyword + 1, indexOfWhere).toLowerCase().trim();
    }

    @Override
    public boolean isTablePresent(String dbName, String tableName) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table.txt");
        return fileOperation.isFilePresent();
    }

    @Override
    public String extractDeleteWhereColumn(String query) {
        int indexOfWhere = query.toUpperCase().indexOf("WHERE");
        int indexOfSpaceAfterWhere = query.indexOf(" ", indexOfWhere + 1);
        int indexOfEqual = query.indexOf("=", indexOfSpaceAfterWhere);
        return query.substring(indexOfSpaceAfterWhere + 1, indexOfEqual).toUpperCase().trim();
    }

    @Override
    public boolean isDeleteColumnPresent(String deleteColumn, Map<String, Column> tableColumnMap) {
        return tableColumnMap.containsKey(deleteColumn);
    }

    @Override
    public String extractWhereColumnValue(String query) {
        int indexOfEqual = query.indexOf("=");
        return query.substring(indexOfEqual + 1, query.length()).trim();
    }

    @Override
    public Map<String, Column> getTableColumns(String dbName, String tableName) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table_meta.meta");
        return fileOperation.readTableMetaDataStringKey();
    }

    @Override
    public void deleteData(String dbName, String tableName, String deleteWhereColumn, String deleteWhereColumnValue,
                           Map<String, Column> columnMap) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table.txt");
        List<String> dataLines = fileOperation.readTableData();
        List<String[]> newDataLines = new ArrayList<String[]>();

        int deleteWhereColumnIndex = columnMap.get(deleteWhereColumn).getIndex();

        String[] dataLineArray;
        for (String dataLine : dataLines) {
            dataLineArray = dataLine.split("\\|");
            if (!dataLineArray[deleteWhereColumnIndex].equals(deleteWhereColumnValue)) {
                newDataLines.add(dataLineArray);
            }
        }

        fileOperation.removeFile();
        fileOperation.createFile();
        for (String[] dataArray : newDataLines) {
            fileOperation.writeTableData(dataArray);
        }
    }

}

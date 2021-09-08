package com.database.app.query.create.table;

import com.database.app.model.Column;
import com.database.app.utility.files.FileOperation;
import com.database.app.utility.regex.RegexMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateTableParser implements CreateTableInterface {

    private static final String CREATE_TABLE_QUERY_REGEX = "^CREATE TABLE [A-Z_]* \\(.*\\)$";

    private FileOperation fileOperation;

    @Override
    public boolean validateQuery(String query) {
        RegexMatcher regexMatcher = new RegexMatcher();
        return regexMatcher.matchQuery(query.toUpperCase(), CREATE_TABLE_QUERY_REGEX);
    }

    @Override
    public String extractTableName(String query) {
        int indexOfTableKeyword = query.toUpperCase().indexOf("TABLE");
        int indexOfSpaceAfterTableKeyword = query.indexOf(" ", indexOfTableKeyword + 1);
        int indexOfOpenBracket = query.indexOf("(", indexOfTableKeyword + 1);
        return query.substring(indexOfSpaceAfterTableKeyword + 1, indexOfOpenBracket).toLowerCase().trim();
    }

    @Override
    public boolean isTablePresent(String dbName, String tableName) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table.txt");
        return fileOperation.isFilePresent();
    }

    @Override
    public List<Column> extractColumns(String query) {
        int indexOfOpenBracket = query.indexOf("(");
        String columnString = query.substring(indexOfOpenBracket + 1, query.length() - 1);
        String[] columnArray = columnString.split(",");

        String columnName = null;
        String columnType = null;
        String columnConstraint = null;
        String foreignKeyTable = null;
        String foreignKeyColumn = null;
        int columnIndex = 0;

        int indexOfFirstSpaceInColumn;
        int indexOfPrimary;
        int indexOfForeign;
        int indexOfReferences;
        int indexOfSpaceAfterReferences;
        int indexOfCloseBracket;

        Column column;
        List<Column> columns = new ArrayList<>();
        String columnStringTrim;
        for (int i = 0; i < columnArray.length; i++) {
            columnStringTrim = columnArray[i].trim().toUpperCase();
            indexOfFirstSpaceInColumn = columnStringTrim.indexOf(" ");
            columnName = columnStringTrim.substring(0, indexOfFirstSpaceInColumn).toUpperCase().trim();

            if (columnStringTrim.contains("PRIMARY")) {
                columnConstraint = "PRIMARY";
                indexOfPrimary = columnStringTrim.indexOf("PRIMARY");
                columnType = columnStringTrim.substring(indexOfFirstSpaceInColumn + 1, indexOfPrimary).trim();
            } else if (columnStringTrim.contains("FOREIGN")) {
                columnConstraint = "FOREIGN";
                indexOfForeign = columnStringTrim.indexOf("FOREIGN");
                columnType = columnStringTrim.substring(indexOfFirstSpaceInColumn + 1, indexOfForeign).trim();

                indexOfReferences = columnStringTrim.indexOf("REFERENCES");
                indexOfSpaceAfterReferences = columnStringTrim.indexOf(" ", indexOfReferences);
                indexOfOpenBracket = columnStringTrim.indexOf("(", indexOfReferences + 1);
                foreignKeyTable = columnStringTrim.substring(indexOfSpaceAfterReferences + 1, indexOfOpenBracket).trim();

                indexOfCloseBracket = columnStringTrim.indexOf(")", indexOfOpenBracket);
                foreignKeyColumn = columnStringTrim.substring(indexOfOpenBracket + 1, indexOfCloseBracket).trim();
            } else {
                columnConstraint = "EMPTY";
                columnType = columnStringTrim.substring(indexOfFirstSpaceInColumn + 1).trim();
            }

            columnIndex = i;
            column = new Column(columnName, columnType, columnConstraint, columnIndex, foreignKeyTable,
                    foreignKeyColumn);
            columns.add(column);

            foreignKeyTable = null;
            foreignKeyColumn = null;
        }
        return columns;
    }

    @Override
    public void createTable(String dbName, String tableName) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table.txt");
        fileOperation.createFile();
    }

    @Override
    public void createTableMetadata(String dbName, String tableName, List<Column> columns) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table_meta.meta");
        fileOperation.createFile();
        fileOperation.writeTableMetaData(columns);
    }

    @Override
    public boolean checkIfForeignKeyTableExists(String dbName, List<Column> columns) {
        FileOperation fileOperation = null;
        boolean foreignKeyTable = true;
        for (Column column : columns) {
            if (column.getConstraint().equals("FOREIGN")) {
                fileOperation = new FileOperation(dbName, column.getForeignKeyTable().toLowerCase() + "_table.txt");
                if (!fileOperation.isFilePresent()) {
                    foreignKeyTable = false;
                    break;
                }
            }
        }
        return foreignKeyTable;
    }

    @Override
    public boolean checkIfForeignKeyColumnExists(String dbName, List<Column> columns) {
        FileOperation fileOperation = null;
        boolean foreignKeyColumn = true;
        Map<String, Column> tableColumnMap = null;
        for (Column column : columns) {
            if (column.getConstraint().equals("FOREIGN")) {
                fileOperation = new FileOperation(dbName, column.getForeignKeyTable().toLowerCase() + "_table_meta.meta");
                tableColumnMap = fileOperation.readTableMetaDataStringKey();
                if (!tableColumnMap.containsKey(column.getForeignKeyColumn())) {
                    foreignKeyColumn = false;
                    break;
                } else if (!tableColumnMap.get(column.getForeignKeyColumn()).getConstraint().equalsIgnoreCase("PRIMARY")) {
                    foreignKeyColumn = false;
                    break;
                }
            }
        }
        return foreignKeyColumn;
    }

}

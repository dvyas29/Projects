package com.database.app.query.truncate;

import com.database.app.utility.files.FileOperation;
import com.database.app.utility.regex.RegexMatcher;

public class TruncateParser implements TruncateInterface {

    private FileOperation fileOperation;
    private static final String TRUNCATE_QUERY_REGEX = "^TRUNCATE TABLE [A-Z_]*$";


	@Override
    public boolean validateQuery(String query) {
		RegexMatcher regexMatcher = new RegexMatcher();
		return regexMatcher.matchQuery(query.toUpperCase(), TRUNCATE_QUERY_REGEX);
    }

    @Override
    public String extractTableName(String query) {
        int indexOfTableKeyword = query.toUpperCase().indexOf("TABLE");
        int indexOfSpaceAfterTableKeyword = query.indexOf(" ", indexOfTableKeyword + 1);
        return query.substring(indexOfSpaceAfterTableKeyword + 1, query.length()).toLowerCase().trim();
    }

    @Override
    public boolean isTablePresent(String dbName, String tableName) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table.txt");
        return fileOperation.isFilePresent();
    }

    @Override
    public void emptyTable(String dbName, String tableName) {
        fileOperation = new FileOperation(dbName, tableName.toLowerCase() + "_table.txt");
        fileOperation.removeFile();
        fileOperation.createFile();
    }

}

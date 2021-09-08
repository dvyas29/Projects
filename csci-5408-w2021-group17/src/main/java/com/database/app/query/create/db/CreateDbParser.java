package com.database.app.query.create.db;

import com.database.app.utility.dir.Directory;
import com.database.app.utility.files.FileOperation;
import com.database.app.utility.regex.RegexMatcher;

public class CreateDbParser implements CreateDbInterface {

    private Directory directory;

	private static final String CREATE_DATABASE_QUERY_REGEX = "^CREATE DATABASE [A-Z_]*$";


	@Override
    public boolean validateQuery(String query) {
		RegexMatcher regexMatcher = new RegexMatcher();
		return regexMatcher.matchQuery(query.toUpperCase(), CREATE_DATABASE_QUERY_REGEX);
    }

    @Override
    public String extractDbName(String query) {
        int indexOfDatabaseKeyword = query.toUpperCase().indexOf("DATABASE");
        int indexOfSecondSpace = query.indexOf(" ", indexOfDatabaseKeyword + 1);
        return query.substring(indexOfSecondSpace + 1).toLowerCase().trim();
    }

    @Override
    public boolean isDbPresent(String dbName) {
        directory = new Directory(dbName);
        return directory.isDirectoryPresent();
    }

    @Override
    public void createDb(String dbName) {
        directory = new Directory(dbName);
        directory.createDirectory();
    }

    @Override
    public void createDbMetadata(String dbName) {
        FileOperation fileOperation = new FileOperation(dbName, dbName + ".meta");
        fileOperation.createFile();
    }

}

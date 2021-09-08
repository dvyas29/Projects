package com.database.app.query.drop.db;

import com.database.app.utility.dir.Directory;
import com.database.app.utility.files.FileOperation;
import com.database.app.utility.regex.RegexMatcher;

public class DropDbParser implements DropDbInterface {

    private Directory directory;

	private static final String DROP_DATABASE_QUERY_REGEX = "^DROP DATABASE [A-Z_]*$";

	@Override
    public boolean validateQuery(String query) {
		RegexMatcher regexMatcher = new RegexMatcher();
		return regexMatcher.matchQuery(query.toUpperCase(), DROP_DATABASE_QUERY_REGEX);
    }

    @Override
    public String extractDbName(String query) {
        int indexOfDatabaseKeyword = query.toUpperCase().indexOf("DATABASE");
        int indexOfSpaceAfterDatabase = query.indexOf(" ", indexOfDatabaseKeyword + 1);
        return query.substring(indexOfSpaceAfterDatabase + 1, query.length()).toLowerCase().trim();
    }

    @Override
    public boolean isDbPresent(String dbName) {
        directory = new Directory(dbName);
        return directory.isDirectoryPresent();
    }

    @Override
    public void dropDb(String dbName) {
        directory = new Directory(dbName);
        directory.removeDirectory();
    }

    @Override
    public void dropDbMetadata(String dbName) {
        FileOperation fileOperation = new FileOperation(dbName, dbName + ".meta");
        fileOperation.removeFile();
    }

}

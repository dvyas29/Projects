package com.database.app.query.use;

import com.database.app.utility.dir.Directory;
import com.database.app.utility.regex.RegexMatcher;

public class UserDbParser implements UseDbInterface {

	private static final String USE_DATABASE_QUERY_REGEX = "^USE [A-Z_]*$";


	@Override
    public boolean validateQuery(String query) {
		RegexMatcher regexMatcher = new RegexMatcher();
		return regexMatcher.matchQuery(query.toUpperCase(), USE_DATABASE_QUERY_REGEX);
    }

    @Override
    public String extractDbName(String query) {
        int indexOfFirstSpace = query.indexOf(" ");
        return query.substring(indexOfFirstSpace + 1).trim().toLowerCase();
    }

    @Override
    public boolean isDbPresent(String dbName) {
		Directory directory = new Directory(dbName);
        return directory.isDirectoryPresent();
    }

}

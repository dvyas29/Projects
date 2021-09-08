package com.database.app.query.use;

public interface UseDbInterface {

    boolean validateQuery(String query);

    String extractDbName(String query);

    boolean isDbPresent(String dbName);
}

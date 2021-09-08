package com.database.app.query.erd;

import com.database.app.main.DatabaseAudit;
import com.database.app.main.DatabaseMain;

public class Erd {

    private final String query;
    private final ErdInterface erdInterface;

    public Erd(String query, ErdInterface erdInterface) {
        this.query = query;
        this.erdInterface = erdInterface;
        DatabaseAudit.writeAuditData(query);
        this.executeQuery();
    }

    private void executeQuery() {
        boolean isValidQuery = erdInterface.validateQuery(query);

        if (isValidQuery) {
            String dbName = erdInterface.extractDbName(query);
            boolean isDatabasePresent = erdInterface.isDbPresent(dbName);
            if (isDatabasePresent) {
                erdInterface.generateErdFile(dbName);
            } else {
                System.out.println(DatabaseMain.PRODUCT_NAME + "Database does not exist");
            }
        } else {
            System.out.println(DatabaseMain.PRODUCT_NAME + "Syntax error in ERD Query");
        }
    }
}

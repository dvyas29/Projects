package com.database.app.query.drop.db;

import com.database.app.main.DatabaseAudit;
import com.database.app.main.DatabaseMain;
import com.database.app.query.node.NodeDb;

public class DropDb {

    private final DropDbInterface dropDbInterface;

    private final String query;

    public DropDb(String query, DropDbInterface dropDbInterface, boolean distributedQuery) {
        this.query = query;
        this.dropDbInterface = dropDbInterface;
        DatabaseAudit.writeAuditData(query);
        if (NodeDb.currentNode.equalsIgnoreCase("LOCAL") && !distributedQuery)
		{
			this.executeQuery();
		}
    }

    public void executeQuery() {
        boolean isValidQuery = dropDbInterface.validateQuery(query);

        if (isValidQuery) {
            String dbName = dropDbInterface.extractDbName(query);
            boolean isDatabasePresent = dropDbInterface.isDbPresent(dbName);
            if (isDatabasePresent) {
                dropDbInterface.dropDbMetadata(dbName);
                dropDbInterface.dropDb(dbName);
                System.out.println(DatabaseMain.PRODUCT_NAME + dbName + " database dropped.");
            } else {
                System.out.println(DatabaseMain.PRODUCT_NAME + dbName + " does not exist.");
            }
        } else
		{
        	System.out.println(DatabaseMain.PRODUCT_NAME + "Syntax error");
		}
    }
    
    public String executeRemoteQuery() {
        boolean isValidQuery = dropDbInterface.validateQuery(query);

        if (isValidQuery) {
            String dbName = dropDbInterface.extractDbName(query);
            boolean isDatabasePresent = dropDbInterface.isDbPresent(dbName);
            if (isDatabasePresent) {
                dropDbInterface.dropDbMetadata(dbName);
                dropDbInterface.dropDb(dbName);
                return DatabaseMain.PRODUCT_NAME + dbName + " database dropped.";
            } else {
                return DatabaseMain.PRODUCT_NAME + dbName + " does not exist.";
            }
        } else {
        	return DatabaseMain.PRODUCT_NAME + "Syntax error";
        }
    }
}

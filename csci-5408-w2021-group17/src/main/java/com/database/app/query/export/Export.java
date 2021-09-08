package com.database.app.query.export;

import com.database.app.main.DatabaseAudit;
import com.database.app.main.DatabaseMain;

public class Export {

    private final String query;
    private final ExportInterface exportInterface;

    public Export(String query, ExportInterface exportInterface) {
        this.query = query;
        this.exportInterface = exportInterface;
        DatabaseAudit.writeAuditData(query);
        this.executeQuery();
    }

    public void executeQuery() {
        boolean isValidQuery = exportInterface.validateQuery(query);

        if (isValidQuery) {
            String dbName = exportInterface.extractDbName(query);
            boolean isDatabasePresent = exportInterface.isDbPresent(dbName);
            if (isDatabasePresent) {
                exportInterface.createDumpFile(dbName);
            } else {
                System.out.println(DatabaseMain.PRODUCT_NAME + "Database does not exist");
            }
        } else {
            System.out.println(DatabaseMain.PRODUCT_NAME + "Syntax error in Export Query");
        }
    }
}

package com.database.app.query.create.db;

import com.database.app.main.DatabaseAudit;
import com.database.app.main.DatabaseMain;
import com.database.app.query.node.NodeDb;
import com.database.app.query.use.UseDb;

public class CreateDb
{

	private final CreateDbInterface createDbInterface;

	private final String query;

	public CreateDb(String query, CreateDbInterface createDbInterface, boolean distributedQuery)
	{
		this.query = query;
		this.createDbInterface = createDbInterface;
		DatabaseAudit.writeAuditData(query);
		if (NodeDb.currentNode.equalsIgnoreCase("LOCAL") && !distributedQuery)
		{
			this.executeQuery();
		}
	}

	public void executeQuery()
	{
		boolean isValidQuery = createDbInterface.validateQuery(query);

		if (isValidQuery)
		{
			String dbName = createDbInterface.extractDbName(query);
			boolean isDatabasePresent = createDbInterface.isDbPresent(dbName);
			if (!isDatabasePresent)
			{
				createDbInterface.createDb(dbName);
//                createDbInterface.createDbMetadata(dbName);
				System.out.println(DatabaseMain.PRODUCT_NAME + dbName + " database created.");
			} else
			{
				System.out.println(DatabaseMain.PRODUCT_NAME + dbName + " already exist.");
			}
			UseDb.setCurrentDb(dbName);
		} else
		{
			System.out.println(DatabaseMain.PRODUCT_NAME + "Syntax error");
		}
	}

	public String executeRemoteQuery()
	{
		boolean isValidQuery = createDbInterface.validateQuery(query);

		if (isValidQuery)
		{
			String dbName = createDbInterface.extractDbName(query);
			boolean isDatabasePresent = createDbInterface.isDbPresent(dbName);
			if (!isDatabasePresent)
			{
				createDbInterface.createDb(dbName);
//                createDbInterface.createDbMetadata(dbName);
				UseDb.setCurrentDb(dbName);
				return DatabaseMain.PRODUCT_NAME + dbName + " database created.";
			} else
			{
				UseDb.setCurrentDb(dbName);
				return DatabaseMain.PRODUCT_NAME + dbName + " already exist.";
			}
		} else
		{
			return DatabaseMain.PRODUCT_NAME + "Syntax error";
		}
	}
}

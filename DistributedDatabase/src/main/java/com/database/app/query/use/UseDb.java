package com.database.app.query.use;

import com.database.app.main.DatabaseAudit;
import com.database.app.main.DatabaseMain;
import com.database.app.query.node.NodeDb;

public class UseDb
{

	private static String currentDb;
	private final UseDbInterface useDbInterface;
	private final String query;

	public UseDb(String query, UseDbInterface useDbInterface, boolean distributedQuery)
	{
		this.query = query;
		this.useDbInterface = useDbInterface;
		DatabaseAudit.writeAuditData(query);
		if (NodeDb.currentNode.equalsIgnoreCase("LOCAL") && !distributedQuery)
		{
			this.executeQuery();
		}

	}

	public static String getCurrentDb()
	{
		return currentDb;
	}

	public static void setCurrentDb(String currentDb)
	{
		UseDb.currentDb = currentDb;
	}

	private void executeQuery()
	{
		boolean isValidQuery = useDbInterface.validateQuery(query);

		if (isValidQuery)
		{
			String dbName = useDbInterface.extractDbName(query);
			boolean isDatabasePresent = useDbInterface.isDbPresent(dbName);
			if (isDatabasePresent)
			{
				UseDb.setCurrentDb(dbName);
				System.out.println(DatabaseMain.PRODUCT_NAME + "Database changed to " + dbName);
			} else
			{
				System.out.println(DatabaseMain.PRODUCT_NAME + dbName + " not present");
			}
		} else
		{
			System.out.println(DatabaseMain.PRODUCT_NAME + "Syntax error");
		}
	}

	public String executeRemoteQuery()
	{
		boolean isValidQuery = useDbInterface.validateQuery(query);

		if (isValidQuery)
		{
			String dbName = useDbInterface.extractDbName(query);
			boolean isDatabasePresent = useDbInterface.isDbPresent(dbName);
			if (isDatabasePresent)
			{
				UseDb.setCurrentDb(dbName);
				return DatabaseMain.PRODUCT_NAME + "Database changed to " + dbName;
			} else
			{
				return DatabaseMain.PRODUCT_NAME + dbName + " not present";
			}
		} else
		{
			return DatabaseMain.PRODUCT_NAME + "Syntax error";
		}
	}

}

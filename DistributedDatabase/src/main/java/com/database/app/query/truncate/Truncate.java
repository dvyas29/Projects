package com.database.app.query.truncate;

import com.database.app.main.DatabaseAudit;
import com.database.app.main.DatabaseMain;
import com.database.app.query.node.NodeDb;
import com.database.app.query.use.UseDb;

public class Truncate
{

	private final TruncateInterface truncateInterface;

	private final String query;

	public Truncate(String query, TruncateInterface truncateInterface, boolean distributedQuery)
	{
		this.query = query;
		this.truncateInterface = truncateInterface;
		DatabaseAudit.writeAuditData(query);
		if (NodeDb.currentNode.equalsIgnoreCase("LOCAL") && !distributedQuery)
		{
			this.executeQuery();
		}
	}

	public void executeQuery()
	{
		boolean isValidQuery = truncateInterface.validateQuery(query);

		if (isValidQuery)
		{
			String tableName = truncateInterface.extractTableName(query);

			boolean isTablePresent = truncateInterface.isTablePresent(UseDb.getCurrentDb(), tableName);
			if (isTablePresent)
			{
				truncateInterface.emptyTable(UseDb.getCurrentDb(), tableName);
				System.out.println(DatabaseMain.PRODUCT_NAME + tableName + " table data deleted.");
			} else
			{
				System.out.println(DatabaseMain.PRODUCT_NAME + tableName + " table does not exists in "
						+ UseDb.getCurrentDb() + " database.");
			}
		} else
		{
			System.out.println(DatabaseMain.PRODUCT_NAME + "Syntax error");
		}
	}

	public String executeRemoteQuery()
	{
		boolean isValidQuery = truncateInterface.validateQuery(query);

		if (isValidQuery)
		{
			String tableName = truncateInterface.extractTableName(query);

			boolean isTablePresent = truncateInterface.isTablePresent(UseDb.getCurrentDb(), tableName);
			if (isTablePresent)
			{
				truncateInterface.emptyTable(UseDb.getCurrentDb(), tableName);
				return DatabaseMain.PRODUCT_NAME + tableName + " table data deleted.";
			} else
			{
				return DatabaseMain.PRODUCT_NAME + tableName + " table does not exists in " + UseDb.getCurrentDb()
						+ " database.";
			}
		} else
		{
			return DatabaseMain.PRODUCT_NAME + "Syntax error";
		}
	}
}

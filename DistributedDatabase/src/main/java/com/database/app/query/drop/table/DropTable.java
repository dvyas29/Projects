package com.database.app.query.drop.table;

import com.database.app.main.DatabaseAudit;
import com.database.app.main.DatabaseMain;
import com.database.app.query.node.NodeDb;
import com.database.app.query.use.UseDb;

public class DropTable
{

	private final DropTableInterface dropTableInterface;

	private final String query;

	public DropTable(String query, DropTableInterface dropTableInterface, boolean distributedQuery)
	{
		this.query = query;
		this.dropTableInterface = dropTableInterface;
		DatabaseAudit.writeAuditData(query);
		if (NodeDb.currentNode.equalsIgnoreCase("LOCAL") && !distributedQuery)
		{
			this.executeQuery();
		}
	}

	public void executeQuery()
	{
		boolean isValidQuery = dropTableInterface.validateQuery(query);

		if (isValidQuery)
		{
			String tableName = dropTableInterface.extractTableName(query);

			boolean isTablePresent = dropTableInterface.isTablePresent(UseDb.getCurrentDb(), tableName);
			if (isTablePresent)
			{
				dropTableInterface.dropTable(UseDb.getCurrentDb(), tableName);
				dropTableInterface.dropTableMetadata(UseDb.getCurrentDb(), tableName);
				System.out.println(DatabaseMain.PRODUCT_NAME + tableName + " table dropped from " + UseDb.getCurrentDb()
						+ " database.");
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
		boolean isValidQuery = dropTableInterface.validateQuery(query);

		if (isValidQuery)
		{
			String tableName = dropTableInterface.extractTableName(query);

			boolean isTablePresent = dropTableInterface.isTablePresent(UseDb.getCurrentDb(), tableName);
			if (isTablePresent)
			{
				dropTableInterface.dropTable(UseDb.getCurrentDb(), tableName);
				dropTableInterface.dropTableMetadata(UseDb.getCurrentDb(), tableName);
				return DatabaseMain.PRODUCT_NAME + tableName + " table dropped from " + UseDb.getCurrentDb()
						+ " database.";
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

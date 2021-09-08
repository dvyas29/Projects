package com.database.app.query.insert;

import com.database.app.main.DatabaseAudit;
import com.database.app.main.DatabaseMain;
import com.database.app.main.DatabaseTransaction;
import com.database.app.model.Column;
import com.database.app.query.node.NodeDb;
import com.database.app.query.use.UseDb;

import java.util.Map;

public class Insert
{
	private final InsertInterface insertInterface;

	private final String query;

	private final boolean execute;

	public Insert(String query, InsertInterface insertInterface, boolean execute, boolean  distributedQuery)
	{
		this.query = query;
		this.insertInterface = insertInterface;
		this.execute = execute;
		DatabaseAudit.writeAuditData(query);
		if (NodeDb.currentNode.equalsIgnoreCase("LOCAL") && !distributedQuery)
		{
			this.executeQuery();
		}
	}

	public void executeQuery()
	{
		boolean isValidQuery = insertInterface.validateQuery(query);

		if (isValidQuery)
		{
			String tableName = insertInterface.extractTableName(query);

			boolean isTablePresent = insertInterface.isTablePresent(UseDb.getCurrentDb(), tableName);
			if (isTablePresent)
			{
				Map<Integer, Column> columnMap = insertInterface.getTableColumns(UseDb.getCurrentDb(), tableName);
				String[] dataArray = insertInterface.extractData(query);

				boolean columnsPresent = insertInterface.columnsPresent(dataArray, columnMap);
				if (columnsPresent)
				{
					boolean isDataValid = insertInterface.isDataValid(dataArray, columnMap);
					if (isDataValid)
					{
						boolean isDuplicateKey = insertInterface.checkDuplicatePrimaryKey(UseDb.getCurrentDb(),
								tableName, columnMap, dataArray);
						if (isDuplicateKey)
						{
							System.out.println(DatabaseMain.PRODUCT_NAME + "Duplicate primary key.");
						} else
						{
							if (execute)
							{
								insertInterface.writeData(UseDb.getCurrentDb(), tableName, dataArray);
							} else
							{
								DatabaseTransaction.writeTransaction(query);
								System.out.println(DatabaseMain.PRODUCT_NAME + "1 row inserted.");
							}
						}
					} else
					{
						System.out.println(DatabaseMain.PRODUCT_NAME + "Invalid insert data.");
					}
				} else
				{
					System.out.println(DatabaseMain.PRODUCT_NAME
							+ "Number of columns data to insert does not match with columns of " + tableName
							+ " table.");
				}
			} else
			{
				System.out.println(DatabaseMain.PRODUCT_NAME + tableName + " table does not exists in "
						+ UseDb.getCurrentDb() + " database.");
			}
		} else
		{
			System.out.println(DatabaseMain.PRODUCT_NAME + "Syntax error in Insert Query");
		}
	}

	public String executeRemoteQuery()
	{
		boolean isValidQuery = insertInterface.validateQuery(query);

		if (isValidQuery)
		{
			String tableName = insertInterface.extractTableName(query);

			boolean isTablePresent = insertInterface.isTablePresent(UseDb.getCurrentDb(), tableName);
			if (isTablePresent)
			{
				Map<Integer, Column> columnMap = insertInterface.getTableColumns(UseDb.getCurrentDb(), tableName);
				String[] dataArray = insertInterface.extractData(query);

				boolean columnsPresent = insertInterface.columnsPresent(dataArray, columnMap);
				if (columnsPresent)
				{
					boolean isDataValid = insertInterface.isDataValid(dataArray, columnMap);
					if (isDataValid)
					{
						boolean isDuplicateKey = insertInterface.checkDuplicatePrimaryKey(UseDb.getCurrentDb(),
								tableName, columnMap, dataArray);
						if (isDuplicateKey)
						{
							return DatabaseMain.PRODUCT_NAME + "Duplicate primary key.";
						} else
						{
							if (execute)
							{
								insertInterface.writeData(UseDb.getCurrentDb(), tableName, dataArray);
								return "";
							} else
							{
								DatabaseTransaction.writeTransaction(query);
								return DatabaseMain.PRODUCT_NAME + "1 row inserted.";
							}
						}
					} else
					{
						return DatabaseMain.PRODUCT_NAME + "Invalid insert data.";
					}
				} else
				{
					return DatabaseMain.PRODUCT_NAME
							+ "Number of columns data to insert does not match with columns of " + tableName
							+ " table.";
				}
			} else
			{
				return DatabaseMain.PRODUCT_NAME + tableName + " table does not exists in " + UseDb.getCurrentDb()
						+ " database.";
			}
		} else
		{
			return DatabaseMain.PRODUCT_NAME + "Syntax error in Insert Query";
		}
	}
}

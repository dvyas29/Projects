package com.database.app.query.create.table;

import com.database.app.main.DatabaseAudit;
import com.database.app.main.DatabaseGDC;
import com.database.app.main.DatabaseMain;
import com.database.app.model.Column;
import com.database.app.query.node.NodeDb;
import com.database.app.query.use.UseDb;

import java.util.List;

public class CreateTable
{

	private final CreateTableInterface createTableInterface;

	private final String query;

	public CreateTable(String query, CreateTableInterface createTableInterface, boolean distributedQuery)
	{
		this.query = query;
		this.createTableInterface = createTableInterface;
		DatabaseAudit.writeAuditData(query);
		if (NodeDb.currentNode.equalsIgnoreCase("LOCAL") && !distributedQuery)
		{
			this.executeQuery();
		}
	}

	public void executeQuery()
	{
		boolean isValidQuery = createTableInterface.validateQuery(query);

		if (isValidQuery)
		{
			String tableName = createTableInterface.extractTableName(query);

			boolean isTablePresent = createTableInterface.isTablePresent(UseDb.getCurrentDb(), tableName);
			if (!isTablePresent)
			{
				List<Column> columns = createTableInterface.extractColumns(query);
				boolean isForeignKeyTable = createTableInterface.checkIfForeignKeyTableExists(UseDb.getCurrentDb(),
						columns);
				if (isForeignKeyTable)
				{
					boolean isForeignKeyColumn = createTableInterface
							.checkIfForeignKeyColumnExists(UseDb.getCurrentDb(), columns);
					if (isForeignKeyColumn)
					{
						DatabaseGDC.enterInGDC(query);
						createTableInterface.createTable(UseDb.getCurrentDb(), tableName);
						createTableInterface.createTableMetadata(UseDb.getCurrentDb(), tableName, columns);
						System.out.println(DatabaseMain.PRODUCT_NAME + tableName + " table created in "
								+ UseDb.getCurrentDb() + " database.");
					} else
					{
						System.out.println(DatabaseMain.PRODUCT_NAME + "Invalid references column");
					}
				} else
				{
					System.out.println(DatabaseMain.PRODUCT_NAME + "References tables doesn't exist");
				}
			} else
			{
				System.out.println(DatabaseMain.PRODUCT_NAME + tableName + " table already exists in "
						+ UseDb.getCurrentDb() + " database.");
			}
		} else
		{
			System.out.println(DatabaseMain.PRODUCT_NAME + "Syntax error");
		}
	}

	public String executeRemoteQuery()
	{
		boolean isValidQuery = createTableInterface.validateQuery(query);

		if (isValidQuery)
		{
			String tableName = createTableInterface.extractTableName(query);

			boolean isTablePresent = createTableInterface.isTablePresent(UseDb.getCurrentDb(), tableName);
			if (!isTablePresent)
			{
				List<Column> columns = createTableInterface.extractColumns(query);
				boolean isForeignKeyTable = createTableInterface.checkIfForeignKeyTableExists(UseDb.getCurrentDb(),
						columns);
				if (isForeignKeyTable)
				{
					boolean isForeignKeyColumn = createTableInterface
							.checkIfForeignKeyColumnExists(UseDb.getCurrentDb(), columns);
					if (isForeignKeyColumn)
					{
						createTableInterface.createTable(UseDb.getCurrentDb(), tableName);
						createTableInterface.createTableMetadata(UseDb.getCurrentDb(), tableName, columns);
						return DatabaseMain.PRODUCT_NAME + tableName + " table created in " + UseDb.getCurrentDb()
								+ " database.";
					} else
					{
						return DatabaseMain.PRODUCT_NAME + "Invalid references column";
					}
				} else
				{
					return DatabaseMain.PRODUCT_NAME + "References tables doesn't exist";
				}
			} else
			{
				return DatabaseMain.PRODUCT_NAME + tableName + " table already exists in " + UseDb.getCurrentDb()
						+ " database.";
			}
		} else
		{
			return DatabaseMain.PRODUCT_NAME + "Syntax error";
		}
	}
}

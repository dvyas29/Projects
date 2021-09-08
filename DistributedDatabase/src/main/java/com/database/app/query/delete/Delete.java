package com.database.app.query.delete;

import com.database.app.main.DatabaseAudit;
import com.database.app.main.DatabaseMain;
import com.database.app.main.DatabaseTransaction;
import com.database.app.model.Column;
import com.database.app.query.node.NodeDb;
import com.database.app.query.use.UseDb;

import java.util.Map;

public class Delete
{

	private final DeleteInterface deleteInterface;

	private final String query;

	private final boolean execute;

	public Delete(String query, DeleteInterface deleteInterface, boolean execute, boolean distributedQuery)
	{
		this.query = query;
		this.deleteInterface = deleteInterface;
		this.execute = execute;
		DatabaseAudit.writeAuditData(query);
		if (NodeDb.currentNode.equalsIgnoreCase("LOCAL") && !distributedQuery)
		{
			this.executeQuery();
		}
	}

	public void executeQuery()
	{
		boolean isValidQuery = deleteInterface.validateQuery(query);

		if (isValidQuery)
		{
			String tableName = deleteInterface.extractTableName(query);
			boolean isTablePresent = deleteInterface.isTablePresent(UseDb.getCurrentDb(), tableName);
			if (isTablePresent)
			{
				Map<String, Column> columnMap = deleteInterface.getTableColumns(UseDb.getCurrentDb(), tableName);
				String whereColumn = deleteInterface.extractDeleteWhereColumn(query);
				String whereValue = deleteInterface.extractWhereColumnValue(query);
				boolean isWhereColumnPresent = deleteInterface.isDeleteColumnPresent(whereColumn, columnMap);
				if (isWhereColumnPresent)
				{
					if (execute)
					{
						deleteInterface.deleteData(UseDb.getCurrentDb(), tableName, whereColumn, whereValue, columnMap);
					} else
					{
						DatabaseTransaction.writeTransaction(query);
						System.out.println(DatabaseMain.PRODUCT_NAME + tableName + " record(s) deleted.");
					}
				} else
				{
					System.out.println(DatabaseMain.PRODUCT_NAME + "Invalid where column.");
				}
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

	public String executePrivateQuery()
	{
		boolean isValidQuery = deleteInterface.validateQuery(query);

		if (isValidQuery)
		{
			String tableName = deleteInterface.extractTableName(query);
			boolean isTablePresent = deleteInterface.isTablePresent(UseDb.getCurrentDb(), tableName);
			if (isTablePresent)
			{
				Map<String, Column> columnMap = deleteInterface.getTableColumns(UseDb.getCurrentDb(), tableName);
				String whereColumn = deleteInterface.extractDeleteWhereColumn(query);
				String whereValue = deleteInterface.extractWhereColumnValue(query);
				boolean isWhereColumnPresent = deleteInterface.isDeleteColumnPresent(whereColumn, columnMap);
				if (isWhereColumnPresent)
				{
					if (execute)
					{
						deleteInterface.deleteData(UseDb.getCurrentDb(), tableName, whereColumn, whereValue, columnMap);
						return "";
					} else
					{
						DatabaseTransaction.writeTransaction(query);
						return DatabaseMain.PRODUCT_NAME + tableName + " record(s) deleted.";
					}
				} else
				{
					return DatabaseMain.PRODUCT_NAME + "Invalid where column.";
				}
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

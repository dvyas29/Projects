package com.database.app.query.update;

import com.database.app.main.DatabaseAudit;
import com.database.app.main.DatabaseMain;
import com.database.app.main.DatabaseTransaction;
import com.database.app.model.Column;
import com.database.app.query.node.NodeDb;
import com.database.app.query.use.UseDb;

import java.util.Map;

public class Update
{
	private UpdateInterface updateInterface;

	private String query;

	private boolean execute;

	public Update(String query, UpdateInterface updateInterface, boolean execute, boolean distributedQuery)
	{
		this.query = query;
		this.updateInterface = updateInterface;
		this.execute = execute;
		DatabaseAudit.writeAuditData(query);
		if (NodeDb.currentNode.equalsIgnoreCase("LOCAL") && !distributedQuery)
		{
			this.executeQuery();
		}
	}

	public void executeQuery()
	{
		boolean isValidQuery = updateInterface.validateQuery(query);

		if (isValidQuery)
		{
			String tableName = updateInterface.extractTableName(query);
			boolean isTablePresent = updateInterface.isTablePresent(UseDb.getCurrentDb(), tableName);
			if (isTablePresent)
			{
				String updateColumn = updateInterface.extractUpdateColumn(query);
				String updateValue = updateInterface.extractUpdateColumnData(query);
				Map<String, Column> columnMap = updateInterface.getTableColumns(UseDb.getCurrentDb(), tableName);
				boolean isUpdateColumnPresent = updateInterface.isUpdateColumnPresent(updateColumn, columnMap);
				if (isUpdateColumnPresent)
				{
					String whereColumn = updateInterface.extractUpdateWhereColumn(query);
					String whereValue = updateInterface.extractUpdateWhereValue(query);
					boolean isWhereColumnPresent = updateInterface.isUpdateWhereColumnPresent(whereColumn, columnMap);
					if (isWhereColumnPresent)
					{
						boolean isUpdateDataValid = updateInterface.isUpdateDataValid(updateValue, updateColumn,
								columnMap);
						if (isUpdateDataValid)
						{
							if (execute)
							{
								updateInterface.updateData(UseDb.getCurrentDb(), tableName, updateColumn, updateValue,
										whereColumn, whereValue, columnMap);
							} else
							{
								DatabaseTransaction.writeTransaction(query);
								System.out.println(DatabaseMain.PRODUCT_NAME + tableName + " updated.");
							}
						} else
						{
							System.out.println(DatabaseMain.PRODUCT_NAME + "Invalid update data.");
						}
					} else
					{
						System.out.println(DatabaseMain.PRODUCT_NAME + "Invalid where column.");
					}
				} else
				{
					System.out.println(DatabaseMain.PRODUCT_NAME + "Invalid update column.");
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

	public String executeRemoteQuery()
	{
		boolean isValidQuery = updateInterface.validateQuery(query);

		if (isValidQuery)
		{
			String tableName = updateInterface.extractTableName(query);
			boolean isTablePresent = updateInterface.isTablePresent(UseDb.getCurrentDb(), tableName);
			if (isTablePresent)
			{
				String updateColumn = updateInterface.extractUpdateColumn(query);
				String updateValue = updateInterface.extractUpdateColumnData(query);
				Map<String, Column> columnMap = updateInterface.getTableColumns(UseDb.getCurrentDb(), tableName);
				boolean isUpdateColumnPresent = updateInterface.isUpdateColumnPresent(updateColumn, columnMap);
				if (isUpdateColumnPresent)
				{
					String whereColumn = updateInterface.extractUpdateWhereColumn(query);
					String whereValue = updateInterface.extractUpdateWhereValue(query);
					boolean isWhereColumnPresent = updateInterface.isUpdateWhereColumnPresent(whereColumn, columnMap);
					if (isWhereColumnPresent)
					{
						boolean isUpdateDataValid = updateInterface.isUpdateDataValid(updateValue, updateColumn,
								columnMap);
						if (isUpdateDataValid)
						{
							if (execute)
							{
								updateInterface.updateData(UseDb.getCurrentDb(), tableName, updateColumn, updateValue,
										whereColumn, whereValue, columnMap);
								return "";
							} else
							{
								DatabaseTransaction.writeTransaction(query);
								return DatabaseMain.PRODUCT_NAME + tableName + " updated.";
							}
						} else
						{
							return DatabaseMain.PRODUCT_NAME + "Invalid update data.";
						}
					} else
					{
						return DatabaseMain.PRODUCT_NAME + "Invalid where column.";
					}
				} else
				{
					return DatabaseMain.PRODUCT_NAME + "Invalid update column.";
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

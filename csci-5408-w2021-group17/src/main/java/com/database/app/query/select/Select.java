package com.database.app.query.select;

import com.database.app.main.DatabaseAudit;
import com.database.app.main.DatabaseMain;
import com.database.app.model.Column;
import com.database.app.query.node.NodeDb;
import com.database.app.query.use.UseDb;

import java.util.List;
import java.util.Map;

public class Select
{
	private final SelectInterface selectInterface;

	private final String query;

	public Select(String query, SelectInterface selectInterface)
	{
		this.query = query;
		this.selectInterface = selectInterface;
		DatabaseAudit.writeAuditData(query);
		if (NodeDb.currentNode.equalsIgnoreCase("LOCAL"))
		{
			this.executeQuery();
		}
	}

	public void executeQuery()
	{
		boolean isValidQuery = selectInterface.validateQuery(query);

		if (isValidQuery)
		{
			String tableName = selectInterface.extractTableName(query);
			boolean isTablePresent = selectInterface.isTablePresent(UseDb.getCurrentDb(), tableName);
			if (isTablePresent)
			{
				Map<String, Column> columnMap = selectInterface.getTableColumns(UseDb.getCurrentDb(), tableName);
				String[] columns = selectInterface.extractColumns(query);
				boolean columnsPresent = selectInterface.columnsPresent(columns, columnMap);
				if (columnsPresent)
				{
					String whereColumnName = selectInterface.extractWhereColumnName(query);
					boolean isWhereColumnPresent = (null == whereColumnName
							|| selectInterface.isWhereColumnPresent(whereColumnName, columnMap));
					if (isWhereColumnPresent)
					{
						String whereColumnValue = null;
						if (whereColumnName != null)
						{
							whereColumnValue = selectInterface.extractWhereColumnValue(query);
						}
						List<String> data = selectInterface.readData(UseDb.getCurrentDb(), tableName, columns,
								columnMap, whereColumnName, whereColumnValue);
						if (data.size() == 0)
						{
							System.out.println(DatabaseMain.PRODUCT_NAME + "No records found.");
						} else
						{
							for (String dataItem : data)
							{
								System.out.println(dataItem.replaceAll("\\|", ","));
							}
						}
					} else
					{
						System.out.println(DatabaseMain.PRODUCT_NAME + "Invalid where column " + whereColumnName);
					}
				} else
				{
					System.out.println(DatabaseMain.PRODUCT_NAME + "Invalid select columns.");
				}
			} else
			{
				System.out.println(DatabaseMain.PRODUCT_NAME + tableName + " table does not exists in "
						+ UseDb.getCurrentDb() + " database.");
			}
		} else
		{
			System.out.println(DatabaseMain.PRODUCT_NAME + "Syntax error in Select Query");
		}
	}

	public String executeRemoteQuery()
	{
		boolean isValidQuery = selectInterface.validateQuery(query);

		if (isValidQuery)
		{
			String tableName = selectInterface.extractTableName(query);
			boolean isTablePresent = selectInterface.isTablePresent(UseDb.getCurrentDb(), tableName);
			if (isTablePresent)
			{
				Map<String, Column> columnMap = selectInterface.getTableColumns(UseDb.getCurrentDb(), tableName);
				String[] columns = selectInterface.extractColumns(query);
				boolean columnsPresent = selectInterface.columnsPresent(columns, columnMap);
				if (columnsPresent)
				{
					String whereColumnName = selectInterface.extractWhereColumnName(query);
					boolean isWhereColumnPresent = (null == whereColumnName
							|| selectInterface.isWhereColumnPresent(whereColumnName, columnMap));
					if (isWhereColumnPresent)
					{
						String whereColumnValue = null;
						if (whereColumnName != null)
						{
							whereColumnValue = selectInterface.extractWhereColumnValue(query);
						}
						List<String> data = selectInterface.readData(UseDb.getCurrentDb(), tableName, columns,
								columnMap, whereColumnName, whereColumnValue);
						if (data.size() == 0)
						{
							return DatabaseMain.PRODUCT_NAME + "No records found.";
						} else
						{
							String dataString = "";
							for (int i = 0; i < data.size(); i++)
							{
								dataString += data.get(i).replaceAll("\\|", ",") + "\n";
							}
							return dataString;
						}
					} else
					{
						return DatabaseMain.PRODUCT_NAME + "Invalid where column " + whereColumnName;
					}
				} else
				{
					return DatabaseMain.PRODUCT_NAME + "Invalid select columns.";
				}
			} else
			{
				return DatabaseMain.PRODUCT_NAME + tableName + " table does not exists in " + UseDb.getCurrentDb()
						+ " database.";
			}
		} else
		{
			return DatabaseMain.PRODUCT_NAME + "Syntax error in Select Query";
		}
	}
}

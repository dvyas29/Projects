package com.database.app.query.node;

import com.database.app.main.DatabaseAudit;
import com.database.app.main.DatabaseMain;

public class NodeDb
{
	public static String currentNode = "LOCAL";
	private final NodeDbInterface nodeDbInterface;
	private final String query;

	public NodeDb(String query, NodeDbInterface nodeDbInterface)
	{
		this.nodeDbInterface = nodeDbInterface;
		this.query = query;
		DatabaseAudit.writeAuditData(query);
		this.executeQuery();
	}

	private void executeQuery()
	{
		boolean isValidQuery = nodeDbInterface.validateNodeQuery(query);
		if (isValidQuery)
		{
			if (query.toUpperCase().contains("CREATE"))
			{
				nodeDbInterface.createNode(query);
			} else if (query.toUpperCase().contains("DELETE"))
			{
				nodeDbInterface.deleteNode(query);
			} else if (query.toUpperCase().contains("SET"))
			{
				nodeDbInterface.setNode(query);
			}
		} else
		{
			System.out.println(DatabaseMain.PRODUCT_NAME + "Syntax error");
		}
	}
}

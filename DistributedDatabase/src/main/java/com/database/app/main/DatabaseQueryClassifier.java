package com.database.app.main;

import org.springframework.web.client.RestTemplate;

import com.database.app.query.commit.Commit;
import com.database.app.query.create.db.CreateDb;
import com.database.app.query.create.db.CreateDbParser;
import com.database.app.query.create.table.CreateTable;
import com.database.app.query.create.table.CreateTableParser;
import com.database.app.query.delete.Delete;
import com.database.app.query.delete.DeleteParser;
import com.database.app.query.drop.db.DropDb;
import com.database.app.query.drop.db.DropDbParser;
import com.database.app.query.drop.table.DropTable;
import com.database.app.query.drop.table.DropTableParser;
import com.database.app.query.erd.Erd;
import com.database.app.query.erd.ErdParser;
import com.database.app.query.export.Export;
import com.database.app.query.export.ExportParser;
import com.database.app.query.insert.Insert;
import com.database.app.query.insert.InsertParser;
import com.database.app.query.node.Node;
import com.database.app.query.node.NodeDb;
import com.database.app.query.node.NodeDbParser;
import com.database.app.query.select.Select;
import com.database.app.query.select.SelectParser;
import com.database.app.query.truncate.Truncate;
import com.database.app.query.truncate.TruncateParser;
import com.database.app.query.update.Update;
import com.database.app.query.update.UpdateParser;
import com.database.app.query.use.UseDb;
import com.database.app.query.use.UserDbParser;

enum Query
{
	USE, CREATE, DROP, INSERT, UPDATE, DELETE, SELECT, TRUNCATE, COMMIT, EXPORT, ERD, NODE
}

enum Structure
{
	TABLE, DATABASE
}

public class DatabaseQueryClassifier
{
	public void classifyQuery(String query)
	{
		String transformQuery = query.toUpperCase();
		if (NodeDb.currentNode.equalsIgnoreCase("LOCAL") || transformQuery.startsWith(String.valueOf(Query.NODE)))
		{
			if (transformQuery.startsWith(String.valueOf(Query.USE)))
			{
				new UseDb(query, new UserDbParser(), false);
				return;
			} else if (transformQuery.startsWith(String.valueOf(Query.CREATE)))
			{
				if (transformQuery.contains(String.valueOf(Structure.DATABASE)))
				{
					new CreateDb(query, new CreateDbParser(), false);
				} else if (transformQuery.contains(String.valueOf(Structure.TABLE)))
				{
					new CreateTable(query, new CreateTableParser(), false);
				}
				return;
			} else if (transformQuery.startsWith(String.valueOf(Query.DROP)))
			{
				if (transformQuery.contains(String.valueOf(Structure.DATABASE)))
				{
					new DropDb(query, new DropDbParser(), false);
					return;
				} else if (transformQuery.contains(String.valueOf(Structure.TABLE)))
				{
					new DropTable(query, new DropTableParser(), false);
					return;
				}
				return;
			} else if (transformQuery.startsWith(String.valueOf(Query.INSERT)))
			{
				new Insert(query, new InsertParser(), false, false);
				return;
			} else if (transformQuery.startsWith(String.valueOf(Query.UPDATE)))
			{
				new Update(query, new UpdateParser(), false, false);
				return;
			} else if (transformQuery.startsWith(String.valueOf(Query.DELETE)))
			{
				new Delete(query, new DeleteParser(), false, false);
				return;
			} else if (transformQuery.startsWith(String.valueOf(Query.SELECT)))
			{
				new Select(query, new SelectParser());
				return;
			} else if (transformQuery.startsWith(String.valueOf(Query.TRUNCATE)))
			{
				new Truncate(query, new TruncateParser(), false);
				return;
			} else if (transformQuery.equals(String.valueOf(Query.COMMIT)))
			{
				new Commit().commitTransactions();
				return;
			} else if (transformQuery.startsWith(String.valueOf(Query.EXPORT)))
			{
				new Export(query, new ExportParser());
				return;
			} else if (transformQuery.startsWith(String.valueOf(Query.ERD)))
			{
				new Erd(query, new ErdParser());
				return;
			} else if (transformQuery.startsWith(String.valueOf(Query.NODE)))
			{
				new NodeDb(query, new NodeDbParser());
				return;
			}
			System.out.println(DatabaseMain.PRODUCT_NAME + "Syntax Error");
		} else
		{
			if (transformQuery.startsWith(String.valueOf(Query.CREATE)))
			{
				if (transformQuery.contains(String.valueOf(Structure.TABLE)))
				{
					DatabaseGDC.enterInGDC(query);
				}
			}
			try
			{
				RestTemplate restTemplate = new RestTemplate();
				NodeDbParser nodeDbParser = new NodeDbParser();
				Node node = nodeDbParser.getNodeData();
				String remoteUrl = "http://" + node.getDistributedServer() + ":" + node.getDistributedPort()
						+ "/remotequery?password=dql@123&query=";
				String remoteQueryOutput = restTemplate.getForEntity(remoteUrl + query, String.class).getBody();
				System.out.println(remoteQueryOutput);
			} catch (Exception e)
			{
				e.printStackTrace();
				System.out.println(DatabaseMain.PRODUCT_NAME + "Error in connecting to remote database.");
			}
		}
	}
}

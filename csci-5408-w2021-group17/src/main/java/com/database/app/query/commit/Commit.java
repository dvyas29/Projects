package com.database.app.query.commit;

import java.util.List;

import com.database.app.main.DatabaseAudit;
import com.database.app.main.DatabaseTransaction;
import com.database.app.query.delete.Delete;
import com.database.app.query.delete.DeleteParser;
import com.database.app.query.insert.Insert;
import com.database.app.query.insert.InsertParser;
import com.database.app.query.node.NodeDb;
import com.database.app.query.update.Update;
import com.database.app.query.update.UpdateParser;

enum Query
{
	INSERT, UPDATE, DELETE
}

public class Commit
{
	public void commitTransactions()
	{
		List<String> transactions = DatabaseTransaction.readTransactions();
		if (NodeDb.currentNode.equalsIgnoreCase("LOCAL"))
		{
			for (String transaction : transactions)
			{
				if (transaction.toUpperCase().startsWith(String.valueOf(Query.INSERT)))
				{
					new Insert(transaction, new InsertParser(), true, false);
				} else if (transaction.toUpperCase().startsWith(String.valueOf(Query.UPDATE)))
				{
					new Update(transaction, new UpdateParser(), true, false);
				} else if (transaction.toUpperCase().startsWith(String.valueOf(Query.DELETE)))
				{
					new Delete(transaction, new DeleteParser(), true, false);
				}
			}
		} else
		{
			for (String transaction : transactions)
			{
				if (transaction.toUpperCase().startsWith(String.valueOf(Query.INSERT)))
				{
					Insert insert = new Insert(transaction, new InsertParser(), true, true);
					insert.executeRemoteQuery();
				} else if (transaction.toUpperCase().startsWith(String.valueOf(Query.UPDATE)))
				{
					Update update = new Update(transaction, new UpdateParser(), true, true);
					update.executeRemoteQuery();
				} else if (transaction.toUpperCase().startsWith(String.valueOf(Query.DELETE)))
				{
					Delete delete = new Delete(transaction, new DeleteParser(), true, true);
					delete.executePrivateQuery();
				}
			}
		}
		DatabaseAudit.writeAuditData("COMMIT");
		DatabaseTransaction.setTransaction();
	}
}

package com.database.app.main.distributed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.database.app.main.DatabaseMain;
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
import com.database.app.query.insert.Insert;
import com.database.app.query.insert.InsertParser;
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

@RestController
public class DistributedController
{
	@GetMapping("/remotequery")
	public String remoteQuery(@RequestParam(name = "password", required = true) String password,
			@RequestParam(name = "query", required = true) String query)
	{
		if (password == null || password.isEmpty() || password.isBlank() || !password.equals("dql@123"))
		{
			return "Unauthorized Access";
		}
		if (query == null || query.isEmpty() || query.isBlank())
		{
			return "Syntax Error";
		}
		String transformQuery = query.toUpperCase();
		if (transformQuery.startsWith(String.valueOf(Query.USE)))
		{
			UseDb useDb = new UseDb(query, new UserDbParser(), true);
			return useDb.executeRemoteQuery();
		} else if (transformQuery.startsWith(String.valueOf(Query.CREATE)))
		{
			if (transformQuery.contains(String.valueOf(Structure.DATABASE)))
			{
				CreateDb createDb = new CreateDb(query, new CreateDbParser(), true);
				return createDb.executeRemoteQuery();
			} else if (transformQuery.contains(String.valueOf(Structure.TABLE)))
			{
				CreateTable createTable = new CreateTable(query, new CreateTableParser(), true);
				return createTable.executeRemoteQuery();
			}
		} else if (transformQuery.startsWith(String.valueOf(Query.DROP)))
		{
			if (transformQuery.contains(String.valueOf(Structure.DATABASE)))
			{
				DropDb dropDb = new DropDb(query, new DropDbParser(), true);
				return dropDb.executeRemoteQuery();
			} else if (transformQuery.contains(String.valueOf(Structure.TABLE)))
			{
				DropTable dropTable = new DropTable(query, new DropTableParser(), true);
				return dropTable.executeRemoteQuery();
			}
		} else if (transformQuery.startsWith(String.valueOf(Query.INSERT)))
		{
			Insert insert = new Insert(query, new InsertParser(), false, true);
			return insert.executeRemoteQuery();
		} else if (transformQuery.startsWith(String.valueOf(Query.UPDATE)))
		{
			Update update = new Update(query, new UpdateParser(), false, true);
			return update.executeRemoteQuery();
		} else if (transformQuery.startsWith(String.valueOf(Query.DELETE)))
		{
			Delete delete = new Delete(query, new DeleteParser(), false, true);
			return delete.executePrivateQuery();
		} else if (transformQuery.startsWith(String.valueOf(Query.SELECT)))
		{
			Select select = new Select(query, new SelectParser());
			return select.executeRemoteQuery();
		} else if (transformQuery.startsWith(String.valueOf(Query.TRUNCATE)))
		{
			Truncate truncate = new Truncate(query, new TruncateParser(), true);
			return truncate.executeRemoteQuery();
		} else if (transformQuery.equals(String.valueOf(Query.COMMIT)))
		{
			new Commit().commitTransactions();
			return DatabaseMain.PRODUCT_NAME + "Commited";
		} else if (transformQuery.startsWith(String.valueOf(Query.EXPORT)))
		{
			return DatabaseMain.PRODUCT_NAME + "Dump cannot be exported for remote database.";
		} else if (transformQuery.startsWith(String.valueOf(Query.ERD)))
		{
			return DatabaseMain.PRODUCT_NAME + "Erd cannot be generated for remote database.";
		} else if (transformQuery.startsWith(String.valueOf(Query.NODE)))
		{
			return DatabaseMain.PRODUCT_NAME + "Node operations cannot be performed on remote database.";
		}
		return DatabaseMain.PRODUCT_NAME + "Syntax Error";
	}
}

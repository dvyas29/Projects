package com.database.app.main;

import com.database.app.query.node.NodeDb;
import com.database.app.query.use.UseDb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseInitialize
{

	final private String DATABASE_DIRECTORY = "dql";

	final private String ADMIN_DB = "admin";

	final private String NODE = "node";

	public DatabaseInitialize()
	{
		this.makeDatabaseDirectory();
	}

	private void makeDatabaseDirectory()
	{
		if (Files.notExists(Paths.get(DATABASE_DIRECTORY)))
		{
			try
			{
				Files.createDirectory(Paths.get(DATABASE_DIRECTORY));
				Files.createDirectory(Paths.get(DATABASE_DIRECTORY + "\\" + ADMIN_DB));
				UseDb.setCurrentDb(ADMIN_DB);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		} else if (Files.notExists(Paths.get(DATABASE_DIRECTORY + "\\" + ADMIN_DB)))
		{
			try
			{
				Files.createDirectory(Paths.get(DATABASE_DIRECTORY + "\\" + ADMIN_DB));
				UseDb.setCurrentDb(ADMIN_DB);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		} else
		{
			UseDb.setCurrentDb(ADMIN_DB);
		}

		if (Files.notExists(Paths.get(DATABASE_DIRECTORY + "\\" + NODE)))
		{
			try
			{
				Files.createDirectory(Paths.get(DATABASE_DIRECTORY + "\\" + NODE));
				Files.createFile(Paths.get(DATABASE_DIRECTORY + "\\node\\" + NODE + ".txt"));
				NodeDb.currentNode = "LOCAL";
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		} else if (Files.notExists(Paths.get(DATABASE_DIRECTORY + "\\node\\" + NODE + ".txt")))
		{
			try
			{
				Files.createFile(Paths.get(DATABASE_DIRECTORY + "\\node\\" + NODE + ".txt"));
				NodeDb.currentNode = "LOCAL";
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}

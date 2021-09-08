package com.database.app.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.database.app.query.node.NodeDb;
import com.database.app.query.use.UseDb;

public class DatabaseGDC
{
	final private static String DATABASE_DIRECTORY = "dql";
	final private static String GDC_FILE = DATABASE_DIRECTORY + "\\admin\\" + "gdc.txt";

	public static void enterInGDC(String query)
	{
		if (Files.notExists(Paths.get(GDC_FILE)))
		{
			try
			{
				Files.createFile(Paths.get(GDC_FILE));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		String gdcDataLine = NodeDb.currentNode + "|" + UseDb.getCurrentDb() + "|" + extractTableName(query);
		try
		{
			Files.writeString(Paths.get(GDC_FILE), gdcDataLine + "\n", StandardOpenOption.APPEND);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static String extractTableName(String query)
	{
		int indexOfTableKeyword = query.toUpperCase().indexOf("TABLE");
		int indexOfSpaceAfterTableKeyword = query.indexOf(" ", indexOfTableKeyword + 1);
		int indexOfOpenBracket = query.indexOf("(", indexOfTableKeyword + 1);
		return query.substring(indexOfSpaceAfterTableKeyword + 1, indexOfOpenBracket).toLowerCase().trim();
	}
}

package com.database.app.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseAudit
{
	final private static String DATABASE_DIRECTORY = "dql";

	final private static String AUDIT_DIR = "audit";

	final private static String AUDIT_FILE = "audit_log_";

	public static void writeAuditData(String query)
	{
		String pattern = "ddMMyyyy";
		String date = new SimpleDateFormat(pattern).format(new Date());

		String logPattern = "ddMMyyyy hh:mm:ss aa";
		String logTime = new SimpleDateFormat(logPattern).format(new Date());

		if (Files.notExists(Paths.get(DATABASE_DIRECTORY + "\\" + AUDIT_DIR)))
		{
			try
			{
				Files.createDirectory(Paths.get(DATABASE_DIRECTORY + "\\" + AUDIT_DIR));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		if (Files.notExists(Paths.get(DATABASE_DIRECTORY + "\\" + AUDIT_DIR + "\\" + AUDIT_FILE + date + ".txt")))
		{
			try
			{
				Files.createFile(Paths.get(DATABASE_DIRECTORY + "\\" + AUDIT_DIR + "\\" + AUDIT_FILE + date + ".txt"));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			Files.writeString(Paths.get(DATABASE_DIRECTORY + "\\" + AUDIT_DIR + "\\" + AUDIT_FILE + date + ".txt"),
					logTime + " " + query + "\n", StandardOpenOption.APPEND);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}

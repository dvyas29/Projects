package com.database.app.utility.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.database.app.model.Column;
import com.database.app.query.node.Node;

public class FileOperation
{
	final private String DATABASE_DIRECTORY = "dql\\";

	private final String dbName;

	private final String fileName;

	public FileOperation()
	{
		this.dbName = null;
		this.fileName = null;
	}

	public FileOperation(String dbName, String fileName)
	{
		this.dbName = dbName;
		this.fileName = fileName;
	}

	public boolean isFilePresent()
	{
		return Files.exists(Paths.get(DATABASE_DIRECTORY + dbName + "\\" + fileName));
	}

	public void createFile()
	{
		if (Files.notExists(Paths.get(DATABASE_DIRECTORY + dbName + "\\" + fileName)))
		{
			try
			{
				Files.createFile(Paths.get(DATABASE_DIRECTORY + dbName + "\\" + fileName));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void removeFile()
	{
		try
		{
			Files.deleteIfExists(Paths.get(DATABASE_DIRECTORY + dbName + "\\" + fileName));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void writeTableMetaData(List<Column> columns)
	{
		try
		{
			String tableMetaData = "";
			String separator = "|";
			for (Column column : columns)
			{
				tableMetaData += column.getIndex() + separator;
				tableMetaData += column.getName() + separator;
				tableMetaData += column.getType() + separator;
				tableMetaData += column.getConstraint() + separator;
				tableMetaData += column.getForeignKeyTable() + separator;
				tableMetaData += column.getForeignKeyColumn();
				tableMetaData += "\n";
			}
			Files.writeString(Paths.get(DATABASE_DIRECTORY + dbName + "\\" + fileName), tableMetaData);
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public Map<Integer, Column> readTableMetaData()
	{
		Map<Integer, Column> columnMap = new HashMap<Integer, Column>();
		try
		{
			List<String> columnLines = Files.readAllLines(Paths.get(DATABASE_DIRECTORY + dbName + "\\" + fileName));
			String[] columnDataArray;
			Column column;

			String columnName;
			String columnType;
			String columnConstraint;
			int columnIndex;
			String foreignKeyTable;
			String foreignKeyColumn;

			for (String columnLine : columnLines)
			{
				columnDataArray = columnLine.split("\\|");
				columnName = columnDataArray[1];
				columnType = columnDataArray[2];
				columnConstraint = columnDataArray[3];
				columnIndex = Integer.parseInt(columnDataArray[0]);
				foreignKeyTable = columnDataArray[4];
				foreignKeyColumn = columnDataArray[5];
				column = new Column(columnName, columnType, columnConstraint, columnIndex, foreignKeyTable,
						foreignKeyColumn);
				columnMap.put(columnIndex, column);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return columnMap;

	}

	public Map<String, Column> readTableMetaDataStringKey()
	{
		Map<String, Column> columnMap = new HashMap<String, Column>();
		try
		{
			List<String> columnLines = Files.readAllLines(Paths.get(DATABASE_DIRECTORY + dbName + "\\" + fileName));
			String[] columnDataArray;
			Column column;

			String columnName;
			String columnType;
			String columnConstraint;
			int columnIndex;
			String foreignKeyTable;
			String foreignKeyColumn;

			for (String columnLine : columnLines)
			{
				columnDataArray = columnLine.split("\\|");
				columnName = columnDataArray[1];
				columnType = columnDataArray[2];
				columnConstraint = columnDataArray[3];
				columnIndex = Integer.parseInt(columnDataArray[0]);
				foreignKeyTable = columnDataArray[4];
				foreignKeyColumn = columnDataArray[5];
				column = new Column(columnName, columnType, columnConstraint, columnIndex, foreignKeyTable,
						foreignKeyColumn);
				columnMap.put(columnName, column);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return columnMap;

	}

	public List<String> readTableData()
	{
		List<String> dataLines = null;
		try
		{
			dataLines = Files.readAllLines(Paths.get(DATABASE_DIRECTORY + dbName + "\\" + fileName));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return dataLines;
	}

	public void writeTableData(String[] dataArray)
	{
		try
		{
			String tableData = "";
			String separator = "|";
			for (int i = 0; i < dataArray.length; i++)
			{
				if (i == dataArray.length - 1)
				{
					tableData += dataArray[i].trim();
					tableData += "\n";
				} else
				{
					tableData += dataArray[i].trim() + separator;
				}
			}
			Files.writeString(Paths.get(DATABASE_DIRECTORY + dbName + "\\" + fileName), tableData,
					StandardOpenOption.APPEND);
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public void writeNodeData(Node node)
	{
		try
		{
			String nodeData = "";
			String separator = "|";
			nodeData = node.getNodeName() + separator + node.getDistributedServer() + separator
					+ node.getDistributedPort() + separator + "\n";
			Files.writeString(Paths.get(DATABASE_DIRECTORY + "\\node\\node.txt"), nodeData, StandardOpenOption.APPEND);
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public Map<String, Node> readNodeData()
	{
		Map<String, Node> map = new HashMap<String, Node>();
		List<String> dataLines = null;
		try
		{
			dataLines = Files.readAllLines(Paths.get(DATABASE_DIRECTORY + "\\node\\node.txt"));
			String[] nodeDataArray;
			Node node;
			for (String data : dataLines)
			{
				nodeDataArray = data.split("\\|");
				node = new Node();
				node.setNodeName(nodeDataArray[0]);
				node.setDistributedServer(nodeDataArray[1]);
				node.setDistributedPort(nodeDataArray[2]);
				map.put(nodeDataArray[0], node);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return map;
	}

	public void deleteNodeData(List<Node> nodes)
	{
		try
		{
			Files.deleteIfExists(Paths.get(DATABASE_DIRECTORY + "\\node\\node.txt"));
			Files.createFile(Paths.get(DATABASE_DIRECTORY + "\\node\\node.txt"));
			String nodeData = "";
			String separator = "|";
			for (Node node : nodes)
			{
				nodeData += node.getNodeName() + separator + node.getDistributedServer() + separator
						+ node.getDistributedPort() + separator + "\n";
			}
			Files.writeString(Paths.get(DATABASE_DIRECTORY + "\\node\\node.txt"), nodeData, StandardOpenOption.APPEND);
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}
}

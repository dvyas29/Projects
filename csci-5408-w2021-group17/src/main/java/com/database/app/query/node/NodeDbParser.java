package com.database.app.query.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.database.app.main.DatabaseMain;
import com.database.app.utility.files.FileOperation;
import com.database.app.utility.regex.RegexMatcher;

public class NodeDbParser implements NodeDbInterface
{
	private static final String CREATE_NODE_QUERY_REGEX = "^NODE CREATE [A-Z_]*$";
	private static final String DELETE_NODE_QUERY_REGEX = "^NODE DELETE [A-Z_]*$";
	private static final String SET_NODE_QUERY_REGEX = "^NODE SET [A-Z_]*$";

	private FileOperation fileOperation;

	@Override
	public boolean validateNodeQuery(String query)
	{
		if (query.toUpperCase().contains("CREATE"))
		{
			RegexMatcher regexMatcher = new RegexMatcher();
//			return regexMatcher.matchQuery(query.toUpperCase(), CREATE_NODE_QUERY_REGEX);
		} else if (query.toUpperCase().contains("DELETE"))
		{
			RegexMatcher regexMatcher = new RegexMatcher();
//			return regexMatcher.matchQuery(query.toUpperCase(), DELETE_NODE_QUERY_REGEX);
		} else if (query.toUpperCase().contains("SET"))
		{
			RegexMatcher regexMatcher = new RegexMatcher();
//			return regexMatcher.matchQuery(query.toUpperCase(), SET_NODE_QUERY_REGEX);
		}
		return true;
	}

	@Override
	public void createNode(String query)
	{
		int indexOfCreateKeyword = query.toUpperCase().indexOf("CREATE");
		int indexOfSecondSpace = query.indexOf(" ", indexOfCreateKeyword + 1);
		String[] nodeDataArray = query.substring(indexOfSecondSpace + 1).toLowerCase().trim().toLowerCase().split(",");
		String nodeName = nodeDataArray[0].trim();
		String nodeServer = nodeDataArray[1].trim();
		String nodePort = nodeDataArray[2].trim();

		fileOperation = new FileOperation();
		Map<String, Node> map = fileOperation.readNodeData();
		if (map.containsKey(nodeName))
		{
			System.out.println(DatabaseMain.PRODUCT_NAME + "Node already exists");
			return;
		}
		Node node = new Node();
		node.setNodeName(nodeName);
		node.setDistributedServer(nodeServer);
		node.setDistributedPort(nodePort);
		fileOperation.writeNodeData(node);
		System.out.println(DatabaseMain.PRODUCT_NAME + "Node " + nodeName + " created successfully.");
	}

	@Override
	public void deleteNode(String query)
	{
		int indexOfDeleteKeyword = query.toUpperCase().indexOf("DELETE");
		int indexOfSecondSpace = query.indexOf(" ", indexOfDeleteKeyword + 1);
		String nodeName = query.substring(indexOfSecondSpace + 1).toLowerCase().trim().toLowerCase();

		fileOperation = new FileOperation();
		Map<String, Node> map = fileOperation.readNodeData();
		if (map.containsKey(nodeName))
		{
			map.remove(nodeName);
			List<Node> nodes = new ArrayList<Node>(map.values());
			fileOperation.deleteNodeData(nodes);
			System.out.println(DatabaseMain.PRODUCT_NAME + "Node " + nodeName + " deleted successfully");
			return;
		} else
		{
			System.out.println(DatabaseMain.PRODUCT_NAME + "Node does not exist");
			return;
		}
	}

	@Override
	public void setNode(String query)
	{
		int indexOfSetKeyword = query.toUpperCase().indexOf("SET");
		int indexOfSecondSpace = query.indexOf(" ", indexOfSetKeyword + 1);
		String nodeName = query.substring(indexOfSecondSpace + 1).toLowerCase().trim().toLowerCase();

		if (!nodeName.equalsIgnoreCase("LOCAL"))
		{
			fileOperation = new FileOperation();
			Map<String, Node> map = fileOperation.readNodeData();
			if (map.containsKey(nodeName))
			{
				NodeDb.currentNode = nodeName.toUpperCase();
				return;
			} else
			{
				System.out.println(DatabaseMain.PRODUCT_NAME + "Node does not exist");
				return;
			}
		} else
		{
			NodeDb.currentNode = nodeName.toUpperCase();
			return;
		}
	}

	@Override
	public String getNode()
	{
		return NodeDb.currentNode;
	}

	@Override
	public Node getNodeData()
	{
		fileOperation = new FileOperation();
		Map<String, Node> map = fileOperation.readNodeData();
		return map.get(NodeDb.currentNode.toLowerCase());
	}

}

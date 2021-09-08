package com.database.app.query.node;

public class Node
{
	private String nodeName;

	private String distributedServer;

	private String distributedPort;

	public String getNodeName()
	{
		return nodeName;
	}

	public void setNodeName(String nodeName)
	{
		this.nodeName = nodeName;
	}

	public String getDistributedServer()
	{
		return distributedServer;
	}

	public void setDistributedServer(String distributedServer)
	{
		this.distributedServer = distributedServer;
	}

	public String getDistributedPort()
	{
		return distributedPort;
	}

	public void setDistributedPort(String distributedPort)
	{
		this.distributedPort = distributedPort;
	}

}

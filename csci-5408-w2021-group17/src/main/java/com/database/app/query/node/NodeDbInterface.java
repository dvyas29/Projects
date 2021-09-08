package com.database.app.query.node;

public interface NodeDbInterface
{
	public boolean validateNodeQuery(String query);
	
	public void createNode(String query);

	public void deleteNode(String query);

	public void setNode(String query);

	public String getNode();
	
	public Node getNodeData();
}

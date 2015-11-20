package com.cisco.topology.model;

public class RootVertex extends Vertex{
	
	public RootVertex(String id)
	{
		super(id);
		properties.put("groot", true);
	}
	
}

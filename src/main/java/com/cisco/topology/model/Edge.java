package com.cisco.topology.model;

import java.util.concurrent.ConcurrentHashMap;

public class Edge extends Mappable{
	public Edge(String id)
	{
		properties.put("gid", id);
	}
	
	public String getId() {
		return (String)properties.get("gid");
	}
	
	public EdgeType getType()
	{
		return (EdgeType)properties.get("gtype");
	}
	
	public void setInOut(String inId, String outId)
	{
		setType(EdgeType.INOUT);
		properties.put("gin", inId);
		properties.put("gout", outId);
	}
	
	public void setPair(String id1, String id2)
	{
		setType(EdgeType.EQUITY);
		properties.put("gpair1", id1);
		properties.put("gpair2", id2);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Edge)
		{
			return super.equals(obj);
		}
		return false;
	}

	private void setType(EdgeType et)
	{
		properties.put("gtype", et);
	}
}

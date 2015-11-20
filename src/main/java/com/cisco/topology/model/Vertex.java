package com.cisco.topology.model;

public class Vertex extends Mappable{
	public Vertex(String id)
	{
		properties.put("gid", id);
	}
	
	public String getId() {
		return (String)properties.get("gid");
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Vertex)
		{
			return super.equals(obj);
		}
		return false;
	}
}

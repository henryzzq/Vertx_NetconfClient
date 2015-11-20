package com.cisco.topology.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Graph extends Mappable{
	private static Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create(); 
	
	public Graph()
	{
		super();
		properties.put("gvertex", new ConcurrentHashMap());
		properties.put("gedge", new ConcurrentHashMap());
	}
	
	public Vertex addV(Vertex v)
	{
		return (Vertex)((Map)properties.get("gvertex")).put(v.getId(), v);
	}
	
	public Edge addE(Edge e)
	{
		return (Edge)((Map)properties.get("gedge")).put(e.getId(), e);
	}
	
	public Edge removeE(String eId)
	{
		return (Edge)((Map)properties.get("gedge")).remove(eId);
	}
	
	public Vertex removeV(String vId)
	{
		return (Vertex)((Map)properties.get("gvertex")).remove(vId);
	}
	
	public Map<String, Vertex> getVMap()
	{
		return new HashMap((Map)properties.get("gvertex"));
	}
	
	public Map<String, Edge> getEMap()
	{
		return new HashMap((Map)properties.get("gedge"));
	}
	
	public boolean isEmpty()
	{
		if(((Map)properties.get("gvertex")).size() > 0)
		{
			return false;
		}
		return true;
	}
	
	public Map getVEMap()
	{
		return new HashMap(properties);
	}
	
	public String toString()
	{
		Map map = new HashMap();
		List vList = new ArrayList();
		List eList = new ArrayList();
		map.put("gvertex", vList);
		map.put("gedge", eList);
		
		for(Object vObj: ((Map)properties.get("gvertex")).values())
		{
			vList.add(((Vertex)vObj).toMap());
		}
		
		for(Object eObj: ((Map)properties.get("gedge")).values())
		{
			eList.add(((Edge)eObj).toMap());
		}
		return gson.toJson(map);
	}
}

package com.cisco.topology.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cisco.topology.message.GraphMessage;
import com.cisco.topology.model.Edge;
import com.cisco.topology.model.EdgeType;
import com.cisco.topology.model.Graph;
import com.cisco.topology.model.Mappable;
import com.cisco.topology.model.Vertex;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GraphJsonUtil {
	private static Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create(); 
	
	public static String ToCytoscapeJsonString(Graph g)
	{
		Map<String, Object> jsonMap = new HashMap();
		List<Map> jsonVList = new ArrayList();
		List<Map> jsonEList = new ArrayList();
		jsonMap.put("nodes", jsonVList);
		jsonMap.put("edges", jsonEList);
		
		Map<String, List> veMap = g.getVEMap();
		List<Vertex> vList = new ArrayList(((Map)veMap.get("gvertex")).values());
		List<Edge> eList =  new ArrayList(((Map)veMap.get("gedge")).values());
		for(Vertex v : vList)
		{
			Map vMap = new HashMap();
			Map data = new HashMap();
			vMap.put("data", data);
			data.put("id", v.getId());
			data.put("name", v.getId());
			data.put("properties", v.toMap().toString());
			jsonVList.add(vMap);
		}
		
		for(Edge e : eList)
		{
			Map eMap = new HashMap();
			Map data = new HashMap();
			eMap.put("data", data);
			if(e.getType().equals(EdgeType.INOUT))
			{
				data.put("target", e.getProperty("gin"));
				data.put("source", e.getProperty("gout"));
			}
			else
			{
				data.put("target", e.getProperty("gpair1"));
				data.put("source", e.getProperty("gpair2"));
			}
			data.put("properties", e.toMap().toString());
			jsonEList.add(eMap);
		}
		return gson.toJson(jsonMap);
	}
	
	public static String ToD3JsonString(Graph g)
	{
		Map<String, Object> jsonMap = new HashMap();
		List<Map> jsonVList = new ArrayList();
		List<Map> jsonEList = new ArrayList();
		jsonMap.put("nodes", jsonVList);
		jsonMap.put("links", jsonEList);
		
		Map<String, List> veMap = g.getVEMap();
		List<Vertex> vList = new ArrayList(((Map)veMap.get("gvertex")).values());
		List<Edge> eList =  new ArrayList(((Map)veMap.get("gedge")).values());
		for(Vertex v : vList)
		{
			Map vMap = new HashMap();
			vMap.put("id", v.getId());
			vMap.put("properties", v.toMap());
			jsonVList.add(vMap);
		}
		
		for(Edge e : eList)
		{
			Map eMap = new HashMap();
			if(e.getType().equals(EdgeType.INOUT))
			{
				eMap.put("target", e.getProperty("gin"));
				eMap.put("source", e.getProperty("gout"));
			}
			else
			{
				eMap.put("target", e.getProperty("gpair1"));
				eMap.put("source", e.getProperty("gpair2"));
			}
			eMap.put("properties", e.toMap());
			jsonEList.add(eMap);
		}
		Map graphMap = new HashMap();
		graphMap.put("graph", jsonMap);
		return gson.toJson(graphMap);
	}
	
	public static String ToJsonString(Mappable map)
	{
		return gson.toJson(map.toMap());
	}
	
	public static String toJsonString(Map map)
	{
		return gson.toJson(map);
	}
	
	public static String toJsonString(List list)
	{
		return gson.toJson(list);
	}
}

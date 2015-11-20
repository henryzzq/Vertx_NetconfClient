package com.cisco.topology.util;

import java.util.Map;

import com.cisco.topology.message.GMessageType;
import com.cisco.topology.message.GraphMessage;
import com.cisco.topology.model.Edge;
import com.cisco.topology.model.Graph;
import com.cisco.topology.model.Vertex;

public class GComparison {
	public static GraphMessage compareGraph(GraphMessage gm, Graph orgG, Graph curG)
	{
		if((orgG == null) || (orgG.isEmpty()))
		{
			gm.joinMessage(GMessageType.NEW_GRAPH, curG);
			return gm;
		}
		
		if((curG == null) || (curG.isEmpty()))
		{
			gm.joinMessage(GMessageType.DELETE_GRAPH, orgG);
			return gm;
		}
		
		Map orgVEMap = orgG.getVEMap();
		Map curVEMap = curG.getVEMap();
		
		Map<String, Vertex> orgVMap = (Map)orgVEMap.get("gvertex");
		Map<String, Edge> orgEMap = (Map)orgVEMap.get("gedge");
		Map<String, Vertex> curVMap = (Map)curVEMap.get("gvertex");
		Map<String, Edge> curEMap = (Map)curVEMap.get("gedge");
		
		gm = compareVertexes(gm, orgVMap, curVMap);
		gm = compareEdges(gm, orgEMap, curEMap);
		
		return gm;
	}
	
	public static GraphMessage compareVertexes(GraphMessage gm, 
				           	            Map<String, Vertex> orgVMap, 
										Map<String, Vertex> curVMap)
	{
		for(String vKey : orgVMap.keySet())
		{
			Vertex orgV = orgVMap.get(vKey);
			Vertex curV = curVMap.get(vKey);
			if(curV == null)
			{
				gm.joinMessage(GMessageType.DELETE_V, orgV);
			}
			else if (!curV.equals(orgV))
			{
				gm.joinMessage(GMessageType.UPDATE_V, curV);
			}
		}
		
		for(String vKey : curVMap.keySet())
		{
			Vertex orgV = orgVMap.get(vKey);
			Vertex curV = curVMap.get(vKey);
			if(orgV == null)
			{
				gm.joinMessage(GMessageType.CREATE_V, curV);
			}
		}
		return gm;
	}
	
	public static GraphMessage compareEdges(GraphMessage gm, 
			                         Map<String, Edge> orgEMap, 
									 Map<String, Edge> curEMap)
	{
		for(String eKey : orgEMap.keySet())
		{
			Edge orgE = orgEMap.get(eKey);
			Edge curE = curEMap.get(eKey);
			if(curE == null)
			{
				gm.joinMessage(GMessageType.DELETE_E, orgE);
			}
			else if (!curE.equals(orgE))
			{
				gm.joinMessage(GMessageType.UPDATE_E, curE);
			}
		}

		for(String eKey : curEMap.keySet())
		{
			Edge orgE = orgEMap.get(eKey);
			Edge curE = curEMap.get(eKey);
			if(orgE == null)
			{
				gm.joinMessage(GMessageType.CREATE_E, curE);
			}
		}
		return gm;
	}
	
}

package com.cisco.topology.model;

import java.util.List;

public class PlanGraph extends Graph{
	public PlanGraph(String name)
	{
		super();
	}
	
	public boolean putPG(PieceGraph pg)
	{
		return false;
	}
	
	public PieceGraph getPG(String rootId)
	{
		return null;
	}
	
	public List listPG()
	{
		return null;
	}
	
	public boolean addE4PG(Edge e)
	{
		return false;
	}
}

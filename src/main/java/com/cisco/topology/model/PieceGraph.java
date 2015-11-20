package com.cisco.topology.model;

public class PieceGraph extends Graph implements SingleRootGraph{
	protected Vertex root;
	public PieceGraph(Vertex root)
	{
		super();
		this.root = root;
		addV(root);
	}
	public Vertex getRoot()
	{
		return root;
	}
}

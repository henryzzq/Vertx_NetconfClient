package com.cisco.topology.util;

import com.cisco.topology.message.GraphMessage;
import com.cisco.topology.model.Graph;
import com.cisco.topology.model.SingleRootGraph;
import com.cisco.topology.model.Vertex;

public class GVersion {
	public static final String BEGIN_VERSION = "0";
	public static String enableVersion(Graph graph) throws Exception
	{
		if(graph instanceof SingleRootGraph)
		{
			Vertex root = ((SingleRootGraph)graph).getRoot();
			if(root == null)
			{
				throw new Exception("Can't find root for a single root graph.");
			}
			
			String version = (String) root.getProperty("gversion");
			if(version != null)
			{
				throw new Exception("The version of the graph is already enabled.");
			}
			version = BEGIN_VERSION.toString();
			root.setProperty("gversion", version);
			return version;
		}
		//other cases will be considered later
		return null;
	}
	
	public static void disableVersion(Graph graph) throws Exception
	{
		if(graph instanceof SingleRootGraph)
		{
			Vertex root = ((SingleRootGraph)graph).getRoot();
			if(root == null)
			{
				throw new Exception("Can't find root for a single root graph.");
			}
			
			root.removeProperty("gversion");
		}
		//other cases will be considered later
	}
	
	public static String getVersion(Graph graph) throws Exception
	{
		if(graph instanceof SingleRootGraph)
		{
			Vertex root = ((SingleRootGraph)graph).getRoot();
			if(root == null)
			{
				throw new Exception("Can't find root for a single root graph.");
			}
			
			return (String) root.getProperty("gversion");
		}
		//other cases will be considered later
		return null;
	}
	
	public static String setVersion(String version, Graph graph) throws Exception
	{
		if(graph instanceof SingleRootGraph)
		{
			Vertex root = ((SingleRootGraph)graph).getRoot();
			if(root == null)
			{
				throw new Exception("Can't find root for a single root graph.");
			}
			
			return (String) root.setProperty("gversion", version);
		}
		//other cases will be considered later
		return null;
	}
	
	public static String publishVersion(Graph graph) throws Exception
	{
		if(graph instanceof SingleRootGraph)
		{
			Vertex root = ((SingleRootGraph)graph).getRoot();
			if(root == null)
			{
				throw new Exception("Can't find root for a single root graph.");
			}
			
			String version = (String) root.getProperty("gversion");
			if(version == null)
			{
				throw new Exception("The version of the graph is not enabled yet.");
			}
			version = (Integer.parseInt(version) + 1) + "";
			root.setProperty("gversion", version);
			return version;
		}
		//other cases will be considered later
		return null;
	}
	
	public static void tagVersion(GraphMessage gm, String oldVersion, String newVersion) throws Exception
	{
		if(oldVersion != null)
		{
			gm.setSupportInfo("old_gversion", oldVersion);
		}
		if(newVersion != null)
		{
			gm.setSupportInfo("new_gversion", newVersion);
		}
	}
}

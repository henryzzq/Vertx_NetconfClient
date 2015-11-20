package com.cisco.jnc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cisco.topology.model.Edge;
import com.cisco.topology.model.PieceGraph;
import com.cisco.topology.model.RootVertex;
import com.cisco.topology.model.Vertex;
import com.cisco.topology.util.GVersion;
import com.cisco.topology.util.GraphJsonUtil;
import com.tailf.jnc.Element;
import com.tailf.jnc.NodeSet;

public class JNCGraphMaker {
	private Map<String, Vertex> providerVMap = new HashMap();
	private Map<String, Vertex> tenantVMap = new HashMap();
	private Map<String, Vertex> serviceVMap = new HashMap();
	private Map<String, Vertex> nfvVMap = new HashMap();
	private Map<String, Vertex> cpeVMap = new HashMap();
	private PieceGraph pg;
	private Vertex root;
	
	public JNCGraphMaker(String name, String version)
	{
		root = new RootVertex(name);
		pg = new PieceGraph(root);
		try {
			GVersion.setVersion(version, pg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean importVPNServices(NodeSet data) throws Exception
	{
		for(Element cloudvpn : data)
		{
			String provider = getString(cloudvpn, "provider");
			String tenant =  getString(cloudvpn, "tenant");
			String service = getString(cloudvpn, "name");
			
			//building V list
			if(!providerVMap.containsKey(provider))
			{
				Vertex providerV = new Vertex(provider);
				providerV.setProperty("gvtype", "provider");
				providerVMap.put(provider, providerV);
				pg.addV(providerV);
			}
			
			if(!providerVMap.containsKey(tenant))
			{
				Vertex tenantV = new Vertex(tenant);
				tenantV.setProperty("gvtype", "tenant");
				tenantVMap.put(tenant, tenantV);
				pg.addV(tenantV);
			}	
			
			Vertex serviceV = new Vertex(service);
			serviceV.setProperty("gvtype", "service");
			serviceVMap.put(service, serviceV);
			pg.addV(serviceV);
			
			//building E list
			String r2pKey = "STRUCTURE_" + root.getId() + "_TO_" + provider ;
			Edge s2p = new Edge(r2pKey);
			s2p.setInOut(provider, root.getId());
			pg.addE(s2p);
			
			String p2tKey = "STRUCTURE_" + provider + "_TO_" + tenant ;
			Edge p2t = new Edge(p2tKey);
			p2t.setInOut(tenant, provider);
			pg.addE(p2t);
			
			String t2sKey = "STRUCTURE_" + tenant + "_TO_" + service ;
			Edge t2s = new Edge(t2sKey);
			t2s.setInOut(service, tenant);
			pg.addE(t2s);
		}
		return true;
	}
	
	public boolean importVPNNFV(NodeSet data) throws Exception
	{
		for(Element cloudvpndata : data)
		{
			String service = getString(cloudvpndata, "name");
			if(serviceVMap.containsKey(service))
			{
				NodeSet nfvs = cloudvpndata.getChildren("nfv");
				for(Element nfv : nfvs)
				{
					String name = getString(nfv, "name");
					String type = getString(nfv, "type");
					
					Vertex nfvV = new Vertex(name);
					nfvV.setProperty("gvtype", type);
					nfvVMap.put(name, nfvV);
					pg.addV(nfvV);
					
					String s2dKey = "STRUCTURE_" + service + "_TO_" + name ;
					Edge s2d = new Edge(s2dKey);
					s2d.setInOut(name, service);
					pg.addE(s2d);
				}
			}
		}
		return true;
	}
	
	public boolean importVPNCPE(NodeSet data) throws Exception
	{
		for(Element cloudvpn : data)
		{
			String service = getString(cloudvpn, "name");
			if(serviceVMap.containsKey(service))
			{
				NodeSet cpes = cloudvpn.getChildren("cpe");
				for(Element cpe : cpes)
				{
					String id = getString(cpe, "id");
					String serial = getString(cpe, "serial");
					List<Map> allocateList = new ArrayList();
					NodeSet allocates = cpe.getChildren("allocate");
					for(Element allocate : allocates)
					{
						Map allocateMap = new HashMap();
						allocateMap.put("ip_type", getString(allocate, "ip-type"));
						allocateMap.put("prefix_size", getString(allocate,"prefix-size"));
						allocateList.add(allocateMap);
					}
					
					Vertex cpeV = new Vertex(id);
					cpeV.setProperty("gvtype", "cpe");
					cpeV.setProperty("serial", serial);
					cpeV.setProperty("allocates", allocateList);
					cpeVMap.put("cpe-"+serial, cpeV);
					pg.addV(cpeV);
					
					String s2dKey = "STRUCTURE_" + service + "_TO_" + id ;
					Edge s2d = new Edge(s2dKey);
					s2d.setInOut(id, service);
					pg.addE(s2d);
				}
			}
		}
		return true;
	}
	
	public boolean importVPNADRR(NodeSet data) throws Exception
	{
		for(Element devices : data)
		{
			data = devices.getChildren("device");
			for(Element device : data)
			{
				String name = getString(device, "name");
				Vertex devV = nfvVMap.get(name);
				if(devV == null)
				{
					devV = cpeVMap.get(name);
				}
				if(devV != null)
				{
					String address = getString(device, "address");
					devV.setProperty("address", address);
					pg.addV(devV);
				}
			}
		}
		return true;
	}
	
	public PieceGraph getPieceGraph()
	{
		return pg;
	}
	
	protected String getString(Element e, String key) throws Exception
	{
		Object value = e.getValue(key);
		if(value != null)
		{
			return value.toString();
		}
		return null;
	}
}

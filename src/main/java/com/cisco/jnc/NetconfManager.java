package com.cisco.jnc;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cisco.topology.message.GraphMessage;
import com.cisco.topology.model.Edge;
import com.cisco.topology.model.Graph;
import com.cisco.topology.model.PieceGraph;
import com.cisco.topology.model.Vertex;
import com.cisco.topology.scheduler.RegularPollerManager;
import com.cisco.topology.scheduler.SchedulerConfig;
import com.cisco.topology.scheduler.SchedulerConfigManager;
import com.cisco.topology.server.GWebRouter;
import com.cisco.topology.util.GComparison;
import com.cisco.topology.util.GVersion;
import com.cisco.topology.util.GraphJsonUtil;
import com.tailf.jnc.Device;
import com.tailf.jnc.DeviceUser;
import com.tailf.jnc.Element;
import com.tailf.jnc.NetconfSession;
import com.tailf.jnc.NodeSet;
import com.tailf.jnc.SSHSession;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class NetconfManager {
	private static Vertx vertx = null;
    /** Singleton class */
    private static NetconfManager _instance = null;
    /** Singleton lock */
    private static Object _singletonLock = new Object();
    
	private Map<String, Device> deviceMap = new ConcurrentHashMap<String, Device>();
	
	private Map<String, PieceGraph> pgMap = new ConcurrentHashMap<String, PieceGraph>();

	public static NetconfManager instance()
    {
        if(_instance == null)
        {
            synchronized(_singletonLock)
            {
                if(_instance == null)
                {
                    _instance = new NetconfManager();
                }
            }
        }
        return _instance;
    }
	
	public void setVertx(Vertx vertx)
	{
		this.vertx = vertx;
	}
	
	public Device connect(Map<String, Object> hostConfig) throws Exception
	{
		Device dev = null;
		try{
			String name = (String)hostConfig.get("name");
			String host = (String)hostConfig.get("host");
			int port = Integer.parseInt((String)hostConfig.get("port"));
			
	        String localUser = (String)hostConfig.get("localuser");
			String remoteUser = (String)hostConfig.get("remoteuser");
			
			DeviceUser duser = new DeviceUser(localUser, remoteUser, (String)hostConfig.get("password"));
	        dev = new Device(name, host, port);   
	        
	        dev.addUser(duser);
	        
	        dev.connect(localUser);
	        deviceMap.put(name, dev);
	        
	        //we just need to create one session
	        dev.newSession(name);
	        NetconfSession session = dev.getSession(name);
	        SSHSession sshSession = dev.getSSHSession(name);
	        
	        session.createSubscription("cloudvpn-notif");
	        
	        NetconfManager.instance().getDeviceMap().put(name, dev);
	        vertx.setPeriodic(1000, new Handler<Long>() {
				@Override
				public void handle(final Long timerId) {
					try {
						Element notification = null;
						if(sshSession.ready())
						{
							notification = session.receiveNotification();
						}
						if (notification != null) {
							System.out.println(notification.toXMLString());
						}
					} catch (Exception e) {
						e.printStackTrace();
						vertx.cancelTimer(timerId);
						close(name);
					}
				}
			});
		}
		catch(Exception e)
		{
			e.printStackTrace();
			close(dev);
		}
		
		return dev;
	}
	
	public GraphMessage fetchModel(String name, SchedulerConfig sc)
	{
		Device device = deviceMap.get(name);
		if(device == null)
		{
			try {
				device = connect((sc.toMap()));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		if(device != null)
		{
			try {
				PieceGraph prePG = pgMap.get(name);
				String version = GVersion.BEGIN_VERSION;
				if(prePG != null)
				{
					version = GVersion.getVersion(prePG);
				}
				
				JNCGraphMaker gm = new JNCGraphMaker(name, version);
				
				String sessionName = name + "_api";
				device.newSession(sessionName);
				NetconfSession session = device.getSession(sessionName);
				
				NodeSet data = session.get("//cloudvpn/tenant|//cloudvpn/provider");
				gm.importVPNServices(data);
				
				data = session.get("//cloudvpn-data/nfv");
				gm.importVPNNFV(data);
				
				data = session.get("//cloudvpn/cpe");
				System.out.println(data.toXMLString());
				gm.importVPNCPE(data);
				
				data = session.get("//devices/device/address");
				gm.importVPNADRR(data);
				device.closeSession(sessionName);
				
				PieceGraph curPG = gm.getPieceGraph();
				//System.out.println(System.currentTimeMillis() + " : " + GraphJsonUtil.ToJsonString(curPG));
				
				GraphMessage gMsg = new GraphMessage();
				gMsg = GComparison.compareGraph(gMsg, prePG, curPG);
				if(!gMsg.isEmpty())
				{
					String newVersion = GVersion.publishVersion(curPG);
					GVersion.tagVersion(gMsg, version, newVersion);
					System.out.println("version updated :" + newVersion);
					pgMap.put(name, curPG);
					return gMsg;
				}
				
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				close(name);
			}
		}
		
		return null;
	}
	
	public void close(String name)
	{
		close(deviceMap.get(name));
	}
	
	public void close(Device dev)
	{
		if(dev != null)
		{
			synchronized(dev)
			{
				try{
					dev.close();
					deviceMap.remove(dev.name);
					pgMap.remove(dev.name);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public Map<String, Device> getDeviceMap() {
		return deviceMap;
	}
	
	public void handleGetNode(RoutingContext routingContext) {
	     HttpServerResponse response = routingContext.response();
		 String nodeId = routingContext.request().getParam("nodeId");
		 if(nodeId != null)
		 {
			 for(PieceGraph pg : pgMap.values())
			 {
				 for(Vertex v : pg.getVMap().values())
				 {
					 String value = (String)v.getProperty("address");
					 if(value != null && nodeId.equals(value))
					 {
						 Map map = v.toMap();
						 map.put("serviceId", getParentId(pg ,v.getId()));
						 response.putHeader("content-type", "application/json").end(GraphJsonUtil.toJsonString(map));
						 return;
					 }
				 }
			 }
		 }
		 response.putHeader("content-type", "application/json").end("{\"error\" : \" Node Not Found.\"}");
	}
	
	public void handleGetTopology(RoutingContext routingContext) {
		 HttpServerResponse response = routingContext.response();
		 String nodeId = routingContext.request().getParam("nodeId");
		 if(nodeId != null)
		 {
			 for(PieceGraph pg : pgMap.values())
			 {
				 Vertex root = pg.getRoot();
				 Map pMap = new HashMap();
				 List<Map> sons = new ArrayList();
				 pMap.put(root.getId(), sons);
				 constructSons(pg,  "ROOT_" + root.getId(), sons);
				 response.putHeader("content-type", "application/json").end(GraphJsonUtil.toJsonString(sons.get(0)));
				 return;
			 }
		 }
		 response.putHeader("content-type", "application/json").end("{\"error\" : \" Node Not Found.\"}");
	}
	
	public void handleGetDevices(RoutingContext routingContext)
	{
		HttpServerResponse response = routingContext.response();
		 String serviceId = routingContext.request().getParam("serviceId");
		 String deviceType = routingContext.request().getParam("deviceType");
		 if(serviceId != null && deviceType != null)
		 {
			 for(PieceGraph pg : pgMap.values())
			 {
				 List<Map> sons = getSons(pg, serviceId, deviceType);
				 response.putHeader("content-type", "application/json").end(GraphJsonUtil.toJsonString(sons));
				 return;
			 }
		 }
		 response.putHeader("content-type", "application/json").end("{\"error\" : \" Node Not Found.\"}");

	}
	
	public void handleD3(RoutingContext routingContext)
	{
		HttpServerResponse response = routingContext.response();
	    for(PieceGraph pg : pgMap.values())
		{
			response.putHeader("content-type", "application/json").end(GraphJsonUtil.ToD3JsonString(pg));
			return;
		}
		response.putHeader("content-type", "application/json").end("{\"error\" : \" Node Not Found.\"}");
	}
	private void constructSons(Graph graph, String pId, List<Map> sons)
	{
		pId = getTrueId(pId);
		List<String> sonIds = getSonIds(graph, pId);
		if(sonIds != null)
		{
			for(String sonId : sonIds)
			{
				Map pMap = new HashMap();
				List<Map> sonList = new ArrayList();
				pMap.put(sonId, sonList);
				sons.add(pMap);
				constructSons(graph, sonId, sonList);
			}
		}
	}
	
	private List getSonIds(Graph graph, String id)
	{
		List sonList = new ArrayList();
		Map<String, Vertex> vMap = graph.getVMap();
		for(Edge edge : graph.getEMap().values())
		{
			String parentId = (String)edge.getProperty("gout");
			if(parentId != null && id.equals(parentId))
			{
				String sonId = (String)edge.getProperty("gin");
				Vertex v = vMap.get(sonId);
				sonList.add(v.getProperty("gvtype")+"_"+sonId);
			}
		}
		return sonList;
	}
	
	private List<Map> getSons(Graph graph, String id, String type)
	{
		List sonList = new ArrayList();
		Map<String, Vertex> vMap = graph.getVMap();
		for(Edge edge : graph.getEMap().values())
		{
			String parentId = (String)edge.getProperty("gout");
			if(parentId != null && id.equals(parentId))
			{
				String sonId = (String)edge.getProperty("gin");
				Vertex v = vMap.get(sonId);
				if(v.getProperty("gvtype").equals(type))
				{
					Map vm = v.toMap();
					vm.put("serviceId", id);
					sonList.add(vm);
				}
			}
		}
		return sonList;
	}
	
	private String getParentId(Graph graph, String id)
	{
		for(Edge edge : graph.getEMap().values())
		{
			String sonId = (String)edge.getProperty("gin");
			if(sonId != null && id.equals(sonId))
			{
				return (String)edge.getProperty("gout");
			}
		}
		return null;
	}
	
	private String getTrueId(String id)
	{
		int index = id.indexOf('_');
		return id.substring(index+1);
	}
	
	public static void main(String args[])
	{
		try {
			//GraphMessage gMsg = NetconfManager.instance().fetchModel("10.124.21.187"); 
			//System.out.println(gMsg.toMap());
			//NetconfManager.instance().pgMap.get("10.124.21.187").addE(new Edge("haha"));
			//gMsg = NetconfManager.instance().fetchModel("10.124.21.187"); 
			//System.out.println(GraphJsonUtil.ToJsonString(gMsg));
			
			VertxOptions options = new VertxOptions(); 
			options.setMaxEventLoopExecuteTime(Long.MAX_VALUE);
			vertx = Vertx.vertx(options);
			
			RegularPollerManager rpm = new RegularPollerManager(vertx, new SchedulerConfigManager(new File("C:\\Users\\zhiqzhao\\workspace\\tailf-netconf-client\\sample.json")));
			
			Router router = GWebRouter.getGBasicRouter(vertx);
			router.get("/vms/address/:nodeId").handler(NetconfManager.instance()::handleGetNode);
			router.get("/vms/service/:serviceId/:deviceType").handler(NetconfManager.instance()::handleGetDevices);
			router.get("/vms/topology/:nodeId").handler(NetconfManager.instance()::handleGetTopology);
			router.get("/d3").handler(NetconfManager.instance()::handleD3);
			vertx.createHttpServer().requestHandler(router::accept).listen(8090);
			//rpm.importDevice("10.124.21.187", new SchedulerConfigManager(null).getConfig("10.124.21.187"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			//NetconfManager.instance().close("10.124.21.187");
			//vertx.close();
		}
	}
}

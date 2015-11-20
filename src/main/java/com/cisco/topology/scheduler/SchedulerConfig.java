package com.cisco.topology.scheduler;


import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.cisco.topology.model.Mappable;

public class SchedulerConfig extends Mappable{
	//1min
	public static final Long DEFAULT_INTERVAL = 60000L;
	
	public SchedulerConfig(String id)
	{
		super();
		properties.put("id", id);
	}
	
	public SchedulerConfig(Map map)
	{
		super();
		properties.putAll(map);
	}
	
	public String getId()
	{
		return (String)properties.get("id");
	}
	
	
	public Long getInterval()
	{
		return Long.parseLong(properties.get("sc_interval").toString());
	}
	
	public void setInterval(Long interval)
	{
		properties.put("sc_interval", interval);
	}
	
	public String getHandler()
	{
		return (String)properties.get("sc_handler");
	}
	
	public void setHandler(String className)
	{
		properties.put("sc_handler", className);
	}
}

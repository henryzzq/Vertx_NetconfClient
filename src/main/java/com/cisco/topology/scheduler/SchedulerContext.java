package com.cisco.topology.scheduler;

import com.cisco.topology.model.Mappable;

public class SchedulerContext extends Mappable{
	public SchedulerContext(String id, SchedulerConfig sc)
	{
		super();
		properties.put("id", id);
		properties.put("config", sc);
	}
	
	public String getId()
	{
		return (String)properties.get("id");
	}
	
	public SchedulerConfig getConfig()
	{
		return (SchedulerConfig)properties.get("config");
	}
}

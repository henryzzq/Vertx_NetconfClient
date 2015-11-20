package com.cisco.topology.scheduler;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.vertx.core.Vertx;

public class RegularPollerManager {
	
	protected static Map<String, Long> timerMap = new ConcurrentHashMap();
	protected Vertx vertx;
	protected SchedulerConfigManager scm;
	public RegularPollerManager(Vertx vertx, SchedulerConfigManager scm)
	{
		this.vertx = vertx;
		this.scm = scm;
		for(String id : scm.configMap.keySet())
		{
			importDevice(id, scm.configMap.get(id));
		}
	}
	
	public void importDevice(String id, SchedulerConfig sc) 
	{
		SchedulerContext context = new SchedulerContext(id, sc);
		String className = sc.getHandler();
		GHandler ghandler = null;
		try {
			ghandler = (GHandler)Class.forName(className).getConstructor(SchedulerContext.class).newInstance(context);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		Long timerId = //vertx.setPeriodic(sc.getInterval(), sc.getHandler());
		vertx.setTimer(1L, ghandler);
		vertx.setPeriodic(sc.getInterval(), ghandler);
		timerMap.put(id, timerId);
		//deviceMap.put(id, sc);
	}
	
	public void deleteDevice(String id)
	{
		Long timerId = timerMap.get(id);
		vertx.cancelTimer(timerId);
		timerMap.remove(id);
		//deviceMap.remove(id);
	}
}

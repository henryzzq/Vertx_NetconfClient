package com.cisco.topology.scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cisco.topology.message.GraphMessage;
import com.cisco.topology.util.GraphJsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class SchedulerConfigManager {
	protected static Map<String, SchedulerConfig> configMap = new ConcurrentHashMap<String, SchedulerConfig>();
	protected static File file;
	public SchedulerConfigManager(File file)
	{
		this.file = file;
		parse(file);
	}
	
	public SchedulerConfig getConfig(String id)
	{
		return configMap.get(id);
	}
	
	protected void parse(File file)
	{
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create(); 
		try {
			Map map = gson.fromJson(new FileReader(file), Map.class);
			SchedulerConfig sc = new SchedulerConfig(map);
			configMap.put(sc.getId(), sc);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	
}

package com.cisco.topology.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Mappable implements Serializable{
	protected Map properties = new ConcurrentHashMap();
	
	public Object setProperty(String key, Object value) {
		if((key != null) && (value != null))
		{
			return properties.put(key, value);
		}
		return null;
	}
	
	public Object getProperty(String key)
	{
		if(key != null)
		{
			return properties.get(key);
		}
		return null;
	}
	
	public Object removeProperty(String key)
	{
		return properties.remove(key);
	}
	
	public Map toMap()
	{
		return new HashMap(properties);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Mappable)
		{
			Mappable map = (Mappable)obj;
			return properties.equals(map.properties);
		}
		return false;
	}

	
}

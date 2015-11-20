package com.cisco.topology.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cisco.topology.model.Mappable;

public class GraphMessage extends Mappable{
	protected Map<String, Object> supportMap;
	public GraphMessage()
	{
		super();
	}
	
	public Object joinMessage(GMessageType gmt, Object obj)
	{
		List msgList = (List)properties.get(gmt);
		if(msgList == null)
		{
		    msgList = new ArrayList();
			properties.put(gmt, msgList);
		}
		return msgList.add(obj);
	}
	
	public Object setSupportInfo(String key, Object value)
	{
		if(supportMap == null)
		{
			supportMap = new HashMap();
		}
		return supportMap.put(key, value);
	}
	
	public boolean isEmpty()
	{
		return (properties.size() == 0);
	}

	@Override
	public Map toMap() {
		Map map = super.toMap();
		if(supportMap != null)
		{
			map.putAll(supportMap);
		}
		return map;
	}
	
	
}

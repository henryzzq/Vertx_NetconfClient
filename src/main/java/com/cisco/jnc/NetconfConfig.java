package com.cisco.jnc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.Map;

public class NetconfConfig {
	private static Map<String, Map> configMap = new ConcurrentHashMap<String, Map>();
	/** Singleton class */
    private static NetconfConfig _instance = null;
    /** Singleton lock */
    private static Object _singletonLock = new Object();
    
	static
	{
		Map config = new HashMap();
		config.put("name", "10.124.21.187");
		config.put("host", "10.124.21.187");
		config.put("port", 830);
		config.put("localuser", "admin");
		config.put("remoteuser", "admin");
		config.put("password", "admin");
		configMap.put("10.124.21.187", config);
	}
	
	public static NetconfConfig instance()
    {
        if(_instance == null)
        {
            synchronized(_singletonLock)
            {
                if(_instance == null)
                {
                    _instance = new NetconfConfig();
                }
            }
        }
        return _instance;
    }
	
	
	public Map getConfig(String name)
	{
		return configMap.get(name);
	}
}

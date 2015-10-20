package com.cisco.jnc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

/**
 * Netconf Client Class
 * 
 * @author zhiqzhao
 *
 */
public class NetconfClientVerticle extends AbstractVerticle{
	
	/* (non-Javadoc)
	 * @see io.vertx.core.AbstractVerticle#start(io.vertx.core.Future)
	 */
	@Override
	public void start(Future<Void> startFuture) throws Exception {
		super.start(startFuture);
		vertx.eventBus().consumer(StaticResources.NETCONF_VERTICLE, 
								  result -> {
									  
								  });
		
	}
	
	public static void main(String[] args)
	{
		
	}

	
}

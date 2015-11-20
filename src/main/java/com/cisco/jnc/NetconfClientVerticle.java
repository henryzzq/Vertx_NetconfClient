package com.cisco.jnc;

import java.io.File;

import com.cisco.topology.scheduler.RegularPollerManager;
import com.cisco.topology.scheduler.SchedulerConfigManager;
import com.cisco.topology.server.GWebRouter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.web.Router;

/**
 * Netconf Client Class
 * 
 * @author zhiqzhao
 *
 */
public class NetconfClientVerticle extends AbstractVerticle{
	
	/* 
	 * @see io.vertx.core.AbstractVerticle#start(io.vertx.core.Future)
	 */
	@Override
	public void start(Future<Void> startFuture) throws Exception {
		super.start(startFuture);
		try {
			
			NetconfManager.instance().setVertx(vertx);
			VertxOptions options = new VertxOptions(); 
			options.setMaxEventLoopExecuteTime(Long.MAX_VALUE);
			String filename = System.getenv("NSO_CONFIG_PATH");
			System.out.println("Loading " + filename);
			File file = new File(filename);
			
			RegularPollerManager rpm = new RegularPollerManager(vertx, new SchedulerConfigManager(file));
			
			Router router = GWebRouter.getGBasicRouter(vertx);
			router.get("/vms/address/:nodeId").handler(NetconfManager.instance()::handleGetNode);
			router.get("/vms/service/:serviceId/:deviceType").handler(NetconfManager.instance()::handleGetDevices);
			router.get("/vms/topology/:nodeId").handler(NetconfManager.instance()::handleGetTopology);
			router.get("/d3").handler(NetconfManager.instance()::handleD3);
			vertx.createHttpServer().requestHandler(router::accept).listen(8090);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			//vertx.close();
		}
		
	}
	
	
}

package com.cisco.topology.server;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class GWebRouter {
	
	public static Router getGBasicRouter(Vertx vertx)
	{
		//more examples in web-examples/src/main/java/io/vertx/example/web/rest
		Router router = Router.router(vertx);
		return router;
	}
}

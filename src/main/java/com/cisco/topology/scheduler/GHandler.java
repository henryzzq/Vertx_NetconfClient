package com.cisco.topology.scheduler;

import com.cisco.topology.message.GraphMessage;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public abstract class GHandler implements Handler<Long>{
	protected SchedulerContext context;
	protected abstract GraphMessage getGMessage(final Long timerId);
	protected abstract void processGMessage(final Long timerId, GraphMessage gm);
	
	public GHandler(SchedulerContext context)
	{
		super();
		this.context = context;
	}
	
	@Override
	public void handle(final Long timerId) {
		GraphMessage gm = getGMessage(timerId);
		if(gm != null && !gm.isEmpty())
		{
			processGMessage(timerId, gm);
		}
	}
	
	protected void finished()
	{
	}
}

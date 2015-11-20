package com.cisco.jnc;

import com.cisco.topology.message.GraphMessage;
import com.cisco.topology.scheduler.GHandler;
import com.cisco.topology.scheduler.SchedulerConfig;
import com.cisco.topology.scheduler.SchedulerContext;
import com.cisco.topology.util.GraphJsonUtil;

public class JNCGraphHandler extends GHandler{

	public JNCGraphHandler(SchedulerContext context) {
		super(context);
	}

	@Override
	protected GraphMessage getGMessage(Long timerId) {
		GraphMessage gMsg = NetconfManager.instance().fetchModel(context.getId(), 
																 context.getConfig()); 
		return gMsg;
	}

	@Override
	protected void processGMessage(Long timerId, GraphMessage gMsg) {
		//System.out.println(GraphJsonUtil.ToJsonString(gMsg));
	}

}

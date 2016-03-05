package com.justwin.agent;

import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;

import com.justwin.agent.conf.LoadSetting;
import com.justwin.agent.conf.Setting;
import com.justwin.agent.util.AgentConstants;
import com.justwin.agent.util.AgentEnum;

public class AgentBuilder {
	
	private static final Logger _logger = Logger.getLogger(AgentBuilder.class);

	private Setting setting;
	private Agent agent;

	public AgentBuilder(String path) {
		this.setting = new LoadSetting(path).load();
	}
	
	public void execute() {
		String agentType = (String)setting.get(AgentConstants.AGENT_TYPE);
		Class<?> agentClass = AgentEnum.getAgent(agentType);
		try {
			Constructor<?> constructor = agentClass.getConstructor(new Class[]{Setting.class});
			agent = (Agent)constructor.newInstance(new Object[]{setting});
			agent.report();
		} catch (Exception e) {
			_logger.error("Create agent failed...", e);
			throw new RuntimeException("Create agent failed...", e);
		} 
	}
	
}

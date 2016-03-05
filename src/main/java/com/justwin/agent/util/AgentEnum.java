package com.justwin.agent.util;

import java.util.HashMap;
import java.util.Map;

import com.justwin.agent.kafka.KafkaAgent;

public enum AgentEnum {

	KAFKA_AGENT(AgentConstants.KAFKA_AGENT, KafkaAgent.class);
	
	private static Map<String, Class<?>> _agentMap;
	private String agentName;
	private Class<?> agentClass;
	
	AgentEnum(String agentName, Class<?> agentClass) {
		this.agentName = agentName;
		this.agentClass = agentClass;
	}
	
	private String getAgentName() {
		return agentName;
	}
	
	private Class<?> getAgentClass() {
		return agentClass;
	}
	
	// pre loading
	static {
		_agentMap = new HashMap<String, Class<?>>();
		AgentEnum[] agents = AgentEnum.values();
		for (AgentEnum agent : agents) {
			_agentMap.put(agent.getAgentName(), agent.getAgentClass());
		}
	}
	
	public static Class<?> getAgent(String agentName) {
		return _agentMap.get(agentName);
	}
	
}

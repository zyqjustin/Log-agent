package com.justwin.agent.util;

public class AgentConstants {
	
	public static final String AGENT_TYPE = "agent.type";
	public static final String LISTEN_FILE = "agent.listening.file";
	
	/*------------------------- Kafka Agent Config -------------------------*/
	public static final String KAFKA_AGENT = "kafka";
	public static final String KAFKA_TOPIC = "kafka.topic";
	public static final String KAFKA_BROKER_LIST = "kafka.broker.list";
	public static final String KAFKA_AGENT_START_DELAY = "kafka.agent.start.delay";
	public static final String KAFKA_SERIALIZER_CLASS = "kafka.serializer.encode.class";
	public static final String KAFKA_AGENT_COLLECT_PERIOD = "kafka.agent.collect.period";
	/**
	 *  0, which means that the producer never waits for an acknowledgement
	 *  from the broker (the same behavior as 0.7). This option provides the
	 *  lowest latency but the weakest durability guarantees (some data will
	 *  be lost when a server fails).<br><br>
	 *  
	 *  1, which means that the producer gets an acknowledgement after the
	 *  leader replica has received the data. This option provides better
	 *  durability as the client waits until the server acknowledges the
	 *  request as successful (only messages that were written to the
	 *  now-dead leader but not yet replicated will be lost).<br><br>
	 *  
	 *  -1, which means that the producer gets an acknowledgement after all
	 *  in-sync replicas have received the data. This option provides the
	 *  best durability, we guarantee that no messages will be lost as long
	 *  as at least one in sync replica remains.
	 */
	public static final String KAFKA_REQUEST_REQUIRED_ACKS = "kafka.request.required.acks";
	public static final String KAFKA_KEY_SERIALIZER_CLASS = "kafka.key.serializer.encode.class";
	
	public static final int DEFAULT_KAFKA_AGENT_START_DELAY = 0;
	public static final int DEFAULT_KAFKA_AGENT_COLLECT_PERIOD = 2;
	public static final String DEFAULT_KAFKA_SERIALIZER_CLASS = "kafka.serializer.StringEncoder";
	public static final String DEFAULT_REQUEST_REQUIRED_ACKS = "-1";
	
}

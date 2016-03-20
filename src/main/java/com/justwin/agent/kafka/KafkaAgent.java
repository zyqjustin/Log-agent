package com.justwin.agent.kafka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.justwin.agent.Agent;
import com.justwin.agent.conf.Setting;
import com.justwin.agent.util.AgentConstants;

public class KafkaAgent implements Agent {
	
	private static final Logger _logger = Logger.getLogger(KafkaAgent.class);
	
	private final Setting setting;
	private long lastTimeFileSize = 0; // TODO sent by last close
	private RandomAccessFile readFile;
	private KafkaProduceAgent producer;
	
	public KafkaAgent(Setting setting) {
		init(setting);
		this.setting = setting;
		this.producer = new KafkaProduceAgent(setting, lastTimeFileSize, readFile);
	}

	public boolean report() {
		_logger.info("Now begin to collect file content and report to kafka...");
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(producer, 
				Integer.parseInt((String) setting.get(AgentConstants.KAFKA_AGENT_START_DELAY)),
				Integer.parseInt((String) setting.get(AgentConstants.KAFKA_AGENT_COLLECT_PERIOD)),
				TimeUnit.SECONDS);
	
		return true;
	}

	private void init(final Setting agentSetting) {
		File listenFile = new File((String) agentSetting.get(AgentConstants.LISTEN_FILE));
		try {
			readFile = new RandomAccessFile(listenFile, "r");
		} catch (FileNotFoundException e) {
			_logger.error("Not find listening file...", e);
			throw new IllegalArgumentException("Not find listening file...", e);
		}
	}
}

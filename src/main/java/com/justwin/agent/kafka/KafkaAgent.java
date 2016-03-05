package com.justwin.agent.kafka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	private long lastTimeFileSize = 0;
	private RandomAccessFile readFile;
	
	public KafkaAgent(Setting setting) {
		this.setting = setting;
		init(setting);
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

	public boolean report() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(new Runnable() {
			
			public void run() {
				try {
					readFile.seek(lastTimeFileSize);
					String content = "";     
                    while( (content = readFile.readLine()) != null) {     
                        System.out.println(new String(content.getBytes("ISO-8859-1")));     
                    }
                    lastTimeFileSize = readFile.length();
				} catch (IOException e) {
					_logger.error("Report file failed, last offset: " + lastTimeFileSize, e);
				}
			}
			
		}, 0, 2, TimeUnit.SECONDS);
		
		return true;
	}

}

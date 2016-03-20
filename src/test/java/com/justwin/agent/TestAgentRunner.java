package com.justwin.agent;

import org.apache.log4j.Logger;

public class TestAgentRunner {
	private static final Logger _logger = Logger.getLogger(TestAgentRunner.class);
	
	public static void main(String[] args) {
		_logger.info("Start agent...");
		// for test easy
		String path = "/Users/justwin/workspace/temp/conf.properties";
		try {
			AgentBuilder builder = new AgentBuilder(path);
			builder.execute();
		} catch (Exception e) {
			_logger.error("Agent crash...", e);
		}
	}
}

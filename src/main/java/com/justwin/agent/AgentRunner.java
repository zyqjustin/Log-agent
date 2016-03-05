package com.justwin.agent;

import org.apache.log4j.Logger;

public class AgentRunner {

	private static final Logger _logger = Logger.getLogger(AgentRunner.class);
	
	public static void main(String[] args) {
		args[0] = "";
		_logger.info("Start agent...");
		if (args[0] != null && args[0] != "") {
			try {
				AgentBuilder builder = new AgentBuilder(args[0]);
				builder.execute();
			} catch (Exception e) {
				_logger.error("Agent crash...", e);
			}
		} else {
			_logger.error("Absent config properties file...");
		}
	}
	
}

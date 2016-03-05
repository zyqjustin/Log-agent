package com.justwin.agent.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadSetting extends MapSetting {

	private String path = null;

	public LoadSetting(String path) {
		super();
		this.path = path;
	}
	
	public Setting load() {
		// TODO load properties
		// put(name, value);
		InputStream in = getClass().getClassLoader().getResourceAsStream(path);
		
		if (in == null) {
			throw new IllegalArgumentException("Not found properties file, file: " + path);
		}
		
		Properties properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			throw new RuntimeException("Properties loading failed, file: " + path, e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("Properties close failed, file: " + path, e);
			}
		}
		return this;
	}
	
}

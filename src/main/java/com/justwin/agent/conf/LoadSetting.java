package com.justwin.agent.conf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

public class LoadSetting implements Setting {

	private String path = null;
	private MapSetting setting;

	public LoadSetting(String path) {
		this.setting = new MapSetting();
		this.path = path;
	}
	
	public Setting load() {
		// put(name, value);
		FileInputStream in;
		try {
			in = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Not found properties file, file: " + path, e);
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
		
		for (Entry<Object, Object> entry : properties.entrySet()) {
			setting.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
		}
		
		return setting;
	}

	@Override
	public Object get(String name) {
		return setting.get(name);
	}

	@Override
	public Object getWithDefault(String name, Object defaultValue) {
		Object val = setting.get(name);
		return val != null ? val : defaultValue;
	}
	
}

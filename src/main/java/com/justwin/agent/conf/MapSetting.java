package com.justwin.agent.conf;

import java.util.HashMap;
import java.util.Map;

public class MapSetting implements Setting {

	private Map<String, Object> map;
	
	public MapSetting() {
		this(new HashMap<String, Object>());
	}

	public MapSetting(Map<String, Object> map) {
		this.map = map;
	}

	public Object get(String name) {
		return map.get(name);
	}

	public Object put(String name, Object value) {
		return map.put(name, value);
	}

	@Override
	public Object getWithDefault(String name, Object defaultValue) {
		Object val = map.get(name);
		return val != null ? val : defaultValue;
	}
}

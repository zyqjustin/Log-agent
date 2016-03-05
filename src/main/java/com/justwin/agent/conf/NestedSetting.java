package com.justwin.agent.conf;

import java.util.Map;

public class NestedSetting extends MapSetting implements Setting {

	private Setting inner;

	public NestedSetting(Setting inner) {
		this.inner = inner;
	}
	
	public NestedSetting(Map<String, Object> map, Setting inner) {
		super(map);
		this.inner = inner;
	}
	
	@Override
	public Object get(String name) {
		Object value = super.get(name);
		return value != null ? value : inner.get(name);
	}
}

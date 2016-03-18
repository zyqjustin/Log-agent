package com.justwin.agent.conf;

public interface Setting {

	public Object get(String name);
	
	public Object getWithDefault(String name, Object defaultValue);
}

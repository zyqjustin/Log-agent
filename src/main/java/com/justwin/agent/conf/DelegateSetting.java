package com.justwin.agent.conf;

public class DelegateSetting implements Setting {

	private Setting inner;
	private Setting outer;
	
	public DelegateSetting(Setting inner, Setting outer) {
		this.inner = inner;
		this.outer = outer;
	}

	public Object get(String name) {
		Object value = outer.get(name);
		return (value != null) ? value : inner.get(name);
	}

	@Override
	public Object getWithDefault(String name, Object defaultValue) {
		Object val = outer.get(name);
		if (val != null) {
			return val;
		} else {
			val = inner.get(name);
		}
		
		if (val != null) {
			return val;
		} else {
			return defaultValue;
		}
	}
	
}

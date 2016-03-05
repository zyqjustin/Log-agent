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
	
}

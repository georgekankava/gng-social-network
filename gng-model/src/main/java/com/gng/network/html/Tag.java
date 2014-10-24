package com.gng.network.html;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Tag {
	private String tagName;
	private Map<String, String> attributes;
	
	public Tag() {
		this.attributes = new LinkedHashMap<String, String>();
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	public void addAttribute(String name, String value) {
		attributes.put(name, value);
	}
	
	public String getAttribute(String name) {
		return attributes.get(name);
	}

	@Override
	public String toString() {
		return "HTMLTag [tagName=" + tagName + ", attributes=" + attributes + "]";
	}
}

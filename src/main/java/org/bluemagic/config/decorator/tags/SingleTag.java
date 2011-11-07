package org.bluemagic.config.decorator.tags;

import org.bluemagic.config.api.Tag;

public class SingleTag implements Tag {

	protected String prefix = "";
	
	protected String suffix = "";
	
	protected String value = "";
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public String getValue() {
		return prefix + value + suffix;
	}

	public void setValue(String value) {
		
		if ((prefix.length() > 0) && (value.startsWith(prefix))) {
			value = value.substring(prefix.length() - 1, value.length() - prefix.length() - 1);
		}
		if ((suffix.length() > 0) && (value.endsWith(suffix))) {
			value = value.substring(0, value.length() - suffix.length() - 1);
		}
		this.value = value;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}

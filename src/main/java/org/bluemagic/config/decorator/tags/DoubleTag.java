package org.bluemagic.config.decorator.tags;

public class DoubleTag extends SingleTag {

	private String key = "";
	
	public DoubleTag() { }
	
	public DoubleTag(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}

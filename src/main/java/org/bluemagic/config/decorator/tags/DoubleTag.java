package org.bluemagic.config.decorator.tags;

/**
 * This is a Java representation that encapsulates a doubletag.
 * A doubletag consists of a key/value pair
 * A simple example would be something like release=12.0_SNAPSHOT
 * where the key is "release" and the value is "12.0_SNAPSHOT"
 * 
 * This tag will be useful when needing to add some context to a singletag
 *
 */
public class DoubleTag extends SingleTag {

	private String key = "";
	
	public DoubleTag() { }
	
	public DoubleTag(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public DoubleTag(String key, String valuePrefix, String value, String valueSuffix) {
		
		// SET THE KEY
		this.key = key;
		
		// SET THE VALUE
		setPrefix(valuePrefix);
		this.value = value;
		setSuffix(valueSuffix);
	}
	
	@Override
	public boolean equals(Object obj) {
	
		boolean equals = false;
		
		if (obj instanceof DoubleTag) {
			DoubleTag other = (DoubleTag) obj;
			
			String otherKey = other.getKey();
			String otherValue = other.getValue();
			
			if (otherKey.equals(this.getKey()) && otherValue.equals(this.getValue())) {
				equals = true;
			}
		}
		return equals;
	}
	
	@Override
	public String toString() {
		return this.key + "=" + getValue();
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}

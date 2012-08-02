package org.bluemagic.config.tags;

import org.bluemagic.config.api.tag.SingleTag;

import org.bluemagic.config.api.tag.SingleTag;

/**
 * Implements the common hashtags found on Twitter
 * For example #RockyRoadIceCream or #test 
 * 
 * For more info:
 * https://support.twitter.com/groups/31-twitter-basics/topics/109-tweets-messages/articles/49309-what-are-hashtags-symbols
 */
public class Hashtag extends SingleTag {

	public static final String HASHTAG_PREFIX = "#";

	public Hashtag() { 
		
	}
	
	public Hashtag(String value) {
		this.setValue(value);
	}
	
	@Override
	public void setValue(String value) {
		
		if ((value != null) && (value.startsWith(HASHTAG_PREFIX))) {			
			this.value = value;
		} else {
			this.value = HASHTAG_PREFIX + value;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
	
		boolean equals = false;
		
		if (obj instanceof Hashtag) {
			Hashtag other = (Hashtag) obj;
			
			if (other.getValue().toLowerCase().equals(this.getValue().toLowerCase())) {
				equals = true;
			}
		}
		return equals;
	}
}

package org.bluemagic.config.tags;

import org.bluemagic.config.api.tag.SingleTag;

/**
 * Implements the common username tags found on Twitter
 * For example @SteveJobs or @billybob 
 * 
 * For more info:
 * https://support.twitter.com/groups/31-twitter-basics/topics/109-tweets-messages/articles/14023-what-are-replies-and-mentions
 */
public class UsernameTag extends SingleTag {

    public static final String USERNAME_PREFIX = "@";

	public UsernameTag() { 
		
	}
	
	public UsernameTag(String value) {
		this.setValue(value);
	}
	
	@Override
	public void setValue(String value) {
		
		if ((value != null) && (value.startsWith(USERNAME_PREFIX))) {			
			this.value = value;
		} else {
			this.value = USERNAME_PREFIX + value;
		}
	}
}

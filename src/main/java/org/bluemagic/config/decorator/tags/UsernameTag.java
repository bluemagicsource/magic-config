package org.bluemagic.config.decorator.tags;

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
		this.prefix = USERNAME_PREFIX;
	}
	
	public UsernameTag(String value) {
		this.prefix = USERNAME_PREFIX;
		this.value = value.toLowerCase();
	}
	
	public UsernameTag(String prefix, String value, String suffix) {

		setPrefix(prefix);
		this.value = value.toLowerCase();
		setSuffix(suffix);
	}
	
	@Override
	public void setPrefix(String prefix) {
		
		if ((prefix != null) && (prefix.startsWith(USERNAME_PREFIX))) {			
			this.prefix = prefix;
		} else {
			this.prefix = USERNAME_PREFIX + prefix;
		}
	}
	
	@Override
	public void setValue(String value) {
		super.setValue(value.toLowerCase());
	}
}

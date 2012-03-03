package org.bluemagic.config.decorator.tags;

/**
 * Implements the common username tags found on Twitter
 * For example @SteveJobs or @billybob 
 * 
 * For more info:
 * https://support.twitter.com/groups/31-twitter-basics/topics/109-tweets-messages/articles/14023-what-are-replies-and-mentions
 */
public class UsernameTag extends SingleTag {

    private static final String USERNAME_PREFIX = "@";

	public UsernameTag() { 
		this.prefix = USERNAME_PREFIX;
	}
	
	public UsernameTag(String value) {
		this.prefix = USERNAME_PREFIX;
		this.value = value;
	}
	
	public UsernameTag(String prefix, String value, String suffix) {

		setPrefix(prefix);
		this.value = value;
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
}

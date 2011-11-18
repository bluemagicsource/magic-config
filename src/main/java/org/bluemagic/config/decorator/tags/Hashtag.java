package org.bluemagic.config.decorator.tags;

/**
 * Implements the common hashtags found on Twitter
 * For example #SteveJobs or #test 
 * 
 */
public class Hashtag extends SingleTag {

	private static final String HASHTAG = "#";

	public Hashtag() { 
		this.prefix = HASHTAG;
	}
	
	public Hashtag(String value) {
		this.prefix = HASHTAG;
		this.value = value;
	}
	
	public Hashtag(String prefix, String value, String suffix) {

		setPrefix(prefix);
		this.value = value;
		setSuffix(suffix);
	}
	
	@Override
	public void setPrefix(String prefix) {
		
		if ((prefix != null) && (prefix.startsWith(HASHTAG))) {			
			this.prefix = prefix;
		} else {
			this.prefix = HASHTAG + prefix;
		}
	}
}

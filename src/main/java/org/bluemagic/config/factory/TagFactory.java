package org.bluemagic.config.factory;

import org.bluemagic.config.api.Tag;

public class TagFactory {

	public static String DEFAULT_TAG_PACKAGE_PREFIX = "org.bluemagic.config.decorator.tags.";
	
	public Tag build(String className) {
		
		Tag tag = null;
		
		try {
			getClass();
			tag = (Tag) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tag;
	}	
}

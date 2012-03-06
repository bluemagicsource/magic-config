package org.bluemagic.config.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.Tag;
import org.bluemagic.config.util.ReflectionUtils;
import org.bluemagic.config.util.StringUtils;

public class TagFactory {
	
	private static final Log LOG = LogFactory.getLog(TagFactory.class);

	public static String DEFAULT_TAG_PREFIX = "org.bluemagic.config.decorator.tags.";
	
	public Tag build(String className) {
		
		Tag tag = null;
		Class<?> clazz = null;
		Object instance = null;
		
		// 	TRY GETTING CLASS WITHOUT ADDING DEFAULT PACKAGE
		clazz = ReflectionUtils.classFromName(className);
		
		if (clazz == null) {
			className = TagFactory.DEFAULT_TAG_PREFIX + StringUtils.capitalize(className);
			
			// 	TRY GETTING CLASS AFTER ADDING DEFAULT PACKAGE
			clazz = ReflectionUtils.classFromName(className);
		}
		
		// TRY CREATING AND INSTANCE
		if (clazz != null) {
			instance = ReflectionUtils.instantiateClass(clazz);
		}
		if ((instance != null) && (instance instanceof Tag)) {
			
			// CAST AS A TAG AND RETURN
			tag = (Tag) instance;
			LOG.debug("Created: " + tag.toString() + " as instanceof: " + clazz.getName());
		}
		return tag;
	}
}

package org.bluemagic.config.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.tag.Tag;
import org.bluemagic.config.util.ReflectionUtils;
import org.bluemagic.config.util.StringUtils;

public class TagFactory {
	
	private static final Log LOG = LogFactory.getLog(TagFactory.class);

	public static String API_TAG_PREFIX = "org.bluemagic.config.api.tag.";
	public static String CONFIG_TAG_PREFIX = "org.bluemagic.config.tags.";
	
	public Tag build(String className) {
		String originalClassName = className;
		
		Tag tag = null;
		Class<?> clazz = null;
		Object instance = null;
		
		// 	TRY GETTING CLASS WITHOUT ADDING API PACKAGE
		clazz = ReflectionUtils.classFromName(originalClassName);
		
		if (clazz == null) {
			className = TagFactory.API_TAG_PREFIX + StringUtils.capitalize(originalClassName);
			
			// 	TRY GETTING CLASS AFTER ADDING API PACKAGE
			clazz = ReflectionUtils.classFromName(className);
		}
		
		if (clazz == null) {
            className = TagFactory.CONFIG_TAG_PREFIX + StringUtils.capitalize(originalClassName);
			
			// 	TRY GETTING CLASS AFTER ADDING CONFIG PACKAGE
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

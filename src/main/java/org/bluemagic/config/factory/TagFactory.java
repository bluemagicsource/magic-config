package org.bluemagic.config.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.Tag;
import org.bluemagic.config.util.StringUtils;

public class TagFactory {
	
	private static final Log LOG = LogFactory.getLog(TagFactory.class);

	private String defaultTagPrefix = "org.bluemagic.config.decorator.tags.";
	
	public Tag build(String className) {
		
		Tag tag = null;
		Class<?> clazz = null;
		Object instance = null;
		
		// 	TRY GETTING CLASS WITHOUT ADDING DEFAULT PACKAGE
		clazz = classFromName(className);
		
		if (clazz == null) {
			className = this.defaultTagPrefix + StringUtils.capitalize(className);
			
			// 	TRY GETTING CLASS AFTER ADDING DEFAULT PACKAGE
			clazz = classFromName(className);
		}
		
		// TRY CREATING AND INSTANCE
		if (clazz != null) {
			instance = instantiateClass(clazz);
		}
		if ((instance != null) && (instance instanceof Tag)) {
			// CAST AS A TAG AND RETURN
			tag = (Tag) instance;
			
		} else {
			// UNABLE TO FIND THE TAG!
			throw new RuntimeException("Tag class Not Found! -> " + className);
		}
		return tag;
	}
	
	public Object instantiateClass(Class<?> clazz) {
		
		Object instance = null;
		
		try {
			instance = clazz.newInstance();
		} catch (Throwable t) {
			LOG.warn("Unable to create instance of: " + clazz.getName(), t);
		}
		return instance;
	}

	public Class<?> classFromName(String className) {

		Class<?> clazz = null;
		
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException cnfe) { 
			LOG.debug(cnfe);
		}
		return clazz;
	}

	public String getDefaultTagPrefix() {
		return defaultTagPrefix;
	}

	public void setDefaultTagPrefix(String defaultTagPrefix) {
		this.defaultTagPrefix = defaultTagPrefix;
	}	
}

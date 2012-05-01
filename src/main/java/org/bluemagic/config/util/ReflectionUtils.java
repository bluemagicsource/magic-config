package org.bluemagic.config.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReflectionUtils {
	
	private static final Log LOG = LogFactory.getLog(ReflectionUtils.class);

	public static Class<?> classFromName(String className) {

		Class<?> clazz = null;
		
		try {
			LOG.trace("Attempting to getClass of type: " + className);
			clazz = Class.forName(className);
			
		} catch (ClassNotFoundException cnfe) {
			LOG.debug(cnfe);
		}
		return clazz;
	}
	
	public static Object instantiateClass(Class<?> clazz) {
		
		Object instance = null;
		
		try {
			LOG.trace("Attempting to instantiate class: " + clazz.getName());
			instance = clazz.newInstance();
			
		} catch (Throwable t) {
			LOG.debug("Unable to create instance of: " + clazz.getName(), t);
		}
		return instance;
	}
}

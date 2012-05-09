package org.bluemagic.config.factory;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.util.ReflectionUtils;
import org.bluemagic.config.util.StringUtils;

public class LocationFactory {
	
	private static final Log LOG = LogFactory.getLog(LocationFactory.class);
	
	public static String DEFAULT_LOCATION_PACKAGE_PREFIX = "org.bluemagic.config.location.";
	
	protected URI uri;
	
	public Location build(String className) {
		
		Location location = null;
		Class<?> clazz = null;
		Object instance = null;
		
		// 	TRY GETTING CLASS WITHOUT ADDING DEFAULT PACKAGE
		clazz = ReflectionUtils.classFromName(className);
		
		if (clazz == null) {
			className = LocationFactory.DEFAULT_LOCATION_PACKAGE_PREFIX + StringUtils.capitalize(className);
			
			// 	TRY GETTING CLASS AFTER ADDING DEFAULT PACKAGE
			clazz = ReflectionUtils.classFromName(className);
		}
		
		// TRY CREATING AND INSTANCE
		if (clazz != null) {
			instance = ReflectionUtils.instantiateClass(clazz);
		}
		if ((instance != null) && (instance instanceof Location)) {
			
			// CAST AS A LOCATION AND RETURN
			location = (Location) instance;
			LOG.debug("Created: " + location.toString() + " as instanceof: " + clazz.getName());
		}
		return location;
	}

//	public Location buildDecoratedLocation(Location location, Collection<Decorator> decorators) {
//
//		DecoratingLocationWrapper dlw = new DecoratingLocationWrapper();
//		
//		// CHECK TO SEE IF ITS A URI LOCATION
//		if (location != null) {
//			
//			// SET ESSENTIAL ITEMS
//			dlw.setInternal(location);
//			dlw.setDecorators(decorators);
//			LOG.trace("Added " + decorators.size() + " decorators to Location");
//		}
//		return dlw;
//	}
}

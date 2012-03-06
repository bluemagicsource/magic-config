package org.bluemagic.config.factory;

import java.net.URI;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.location.UriLocation;
import org.bluemagic.config.util.ReflectionUtils;
import org.bluemagic.config.util.StringUtils;

public class LocationFactory {
	
	private static final Log LOG = LogFactory.getLog(LocationFactory.class);
	
	public static String DEFAULT_LOCATION_PACKAGE_PREFIX = "org.bluemagic.config.location.";
	
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

	public UriLocation buildUriLocation(Location location, URI uri, Collection<Decorator> decorators) {

		UriLocation uriLocation = null;
		
		// CHECK TO SEE IF ITS A URI LOCATION
		if ((location != null) && (location instanceof UriLocation)) {
			
			// SAFELY CAST
			uriLocation = (UriLocation) location;
			
			// SET ESSENTIAL ITEMS
			uriLocation.setUri(uri);
			uriLocation.setDecorators(decorators);
			LOG.trace("Added " + decorators.size() + " decorators to UriLocation with uri: " + uri.toASCIIString());
		}
		return uriLocation;
	}
}

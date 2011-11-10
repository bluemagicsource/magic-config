package org.bluemagic.config.factory;

import java.net.URI;
import java.util.Collection;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.location.UriLocation;

public class LocationFactory {
	
	public static String DEFAULT_LOCATION_PACKAGE_PREFIX = "org.bluemagic.config.location.";
	
	public Location build(String className) {
		
		Location location = null;
		
		try {
			location = (Location) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
	}

	public Location build(String className, URI uri, Collection<Decorator> decorators) {

		UriLocation uriLocation = null;
		
		try {
			uriLocation = (UriLocation) Class.forName(className).newInstance();
			uriLocation.setUri(uri);
			uriLocation.setDecorators(decorators);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uriLocation;
	}
}

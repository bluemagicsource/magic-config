package org.bluemagic.config.factory;

import java.util.List;
import java.util.Map;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Location;

/**
 * A basic interface for factory that creates Location objects
 */
public interface LocationFactory {

	public Location createLocation(String locationType, 
			                       Map<String, String> attributes);
	
	public Location createLocation(String locationType, 
			                       Map<String, String> attributes, 
			                       List<Decorator> locationDecorators,
			                       List<Decorator> keyDecorators);
}

package org.bluemagic.config.factory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.location.FileLocation;
import org.bluemagic.config.location.WebLocation;
import org.bluemagic.config.util.StringUtils;

public class LocationFactoryImpl implements LocationFactory {

	private static final Log LOG = LogFactory.getLog(LocationFactoryImpl.class);
	private static final LocationFactoryImpl INSTANCE = new LocationFactoryImpl();
	
	protected LocationFactoryImpl() {}
	
	public static LocationFactory getInstance() {
		
		return INSTANCE;
	}
	
	public Location createLocation(String locationType, Map<String, String> attributes) {

		Location location = null;
		
		try {
			
			String methodName = "create" + StringUtils.capitalize(locationType);
			LOG.trace("Looking for method: " + methodName);
			
			Method method = this.getClass().getMethod(methodName, Map.class);
			
			if (LOG.isTraceEnabled()) {
				LOG.debug("Creating location with method: " + method.toString());
			}
			
			location = (Location) method.invoke(this, attributes);
			
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException("Unable to find method", e);
		}
		
		return location;		
	}
	
	public Location createLocation(String locationType, Map<String, String> attributes, List<Decorator> locationDecorators, List<Decorator> keyDecorators) {
		
		LOG.error("Not implemented");
		
		// Create the location that will be decorated
		Location location = createLocation(locationType, attributes);
		
		// Wrap the location with decorators and return it
//		return new DecoratedLocation(location, locationDecorators, keyDecorators);
		return null;
	}	
	
	public Location createFileLocation(Map<String, String> attributes) {
		
		FileLocation location = null;
		
		if (attributes.containsKey("file")) {
			
			location = new FileLocation();
			location.setFile(attributes.get("file"));
			
		} else if (attributes.containsKey("folder")) {
			
			location = new FileLocation();
			location.setFolder(attributes.get("folder"));
		} else {
			
			throw new RuntimeException("Unable to create FileLocation without a folder or file attribute");
		}
		
		return location;
	}
	
	public Location createWebLocation(Map<String, String> attributes) {
		
		WebLocation location = null;
		
		String uri = attributes.get("uri");
		if (uri == null) {
			throw new RuntimeException("Unable to create WebLocation without a uri attribute");
		}

		location = new WebLocation();
		location.setFile(uri);
		
		if (attributes.containsKey("prefix")) {
			
			location.setPrefix(attributes.get("prefix"));
		}
		
		return location;
	}
}

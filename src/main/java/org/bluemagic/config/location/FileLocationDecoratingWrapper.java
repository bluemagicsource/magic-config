package org.bluemagic.config.location;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.property.LocatedProperty;
import org.bluemagic.config.util.UriUtils;

public class FileLocationDecoratingWrapper extends DecoratingLocationWrapper {

	private static final Log LOG = LogFactory.getLog(FileLocationDecoratingWrapper.class);
	
	protected List<Location> locations = new ArrayList<Location>();
	private boolean initialized = false;
	
	public void init() {
		
		if (internal instanceof FileLocation) {
			
			FileLocation location = (FileLocation) internal;
			
			if (location.getFile() != null) {
				
				decorateFile(location);
			} else {
				
				if (location.getFolder() == null) {
					location.setFolder("");
				}
				
				decorateFolder(location);
			}
			
			initialized = true;
		} else {
			
			throw new RuntimeException(this.getClass().getName() + " requires a " + FileLocation.class.getName());
		}
	}
	
	private void decorateFile(FileLocation location) {
		
		URI key = UriUtils.toUri(location.getFile());
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		parameters.put(MagicKey.ORIGINAL_URI, key);
		
		Collection<URI> files = decorateUri(key, parameters);
		
		for (URI file : files) {
			
			FileLocation fileLocation = new FileLocation();
			fileLocation.setFile(file.toASCIIString());
			locations.add(fileLocation);
		}
	}

	private void decorateFolder(FileLocation location) {
		
		URI key = UriUtils.toUri(location.getFolder());
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		parameters.put(MagicKey.ORIGINAL_URI, key);
		
		Collection<URI> folders = decorateUri(key, parameters);
		
		for (URI folder : folders) {
			
			FileLocation fileLocation = new FileLocation();
			fileLocation.setFolder(folder.toASCIIString());
			locations.add(fileLocation);
		}
	}

	@Override
	public Entry<URI, Object> locate(URI key, Map<MagicKey, Object> parameters) {
		
		if (!initialized) {
			init();
		}
		
		Entry<URI,Object> property = null;
		
		// LOOP THROUGH THE LOCATIONS
		for (Location location : locations) {
			
			try {
				
				property = location.locate(key, parameters);
				
			} catch (Throwable t) {
				
				if (LOG.isDebugEnabled()) {
					LOG.debug("File not found for " + location);
				}
			}
			
			if (property instanceof LocatedProperty) {
				// THIS MEANS WE HAD A HIT SO BREAK RETURN IT
				break;
			}
		}
		
		return property;
	}

	public List<Location> getLocations() {
		return locations;
	}

}
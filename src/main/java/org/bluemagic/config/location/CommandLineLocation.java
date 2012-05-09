package org.bluemagic.config.location;

import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.property.LocatedProperty;
import org.bluemagic.config.api.property.MissingProperty;

public class CommandLineLocation implements Location {
	
	private static final Log LOG = LogFactory.getLog(CommandLineLocation.class);

	/**
     * @param  key - URI a unique identifier for a specific instance of data
     *               or set of data.
     * @param  parameters - Map<ConfigKey name, Object value> name and value pairs
     *                      of data. Note that the Map is required but it may be
     *                      empty. However, the map itself cannot be null.
     *                   The command line data provider requires that the orignal URI
     *                   is available in the parameters map using the name "originalUri".
     *
     * @return Object - The data associated with the URI or NoDataFound if this data
     *                  provider did not find any data. This allows the data provider
     *                  to return a null value (as a valid value).
     *
     *                   The command line data provider cannot return a null value
     *                   the most it can return is an empty string because it is
     *                   not possible to pass null into the command line.
     **/ 
	public Entry<URI,Object> locate(URI key, Map<MagicKey, Object> parameters) {

		String value = null;
		Entry<URI,Object> property = null;
		String keyAsString = key.toASCIIString();

		if ((keyAsString != null) && (!keyAsString.trim().isEmpty())) {

			// We want to check to see if the system property map contains
			// the key (if it does not contain the key then an override is
			// not going to occur. There is the case where the override can
			// be to remove a property (aka the value is empty).
			if (System.getProperties().containsKey(keyAsString)) {
				value = System.getProperty(keyAsString);
			}
			
			URI originalUri = (URI) parameters.get(MagicKey.ORIGINAL_URI);
			URI locatedUri = key;
			
			if (value != null) {
				
				parameters.put(MagicKey.RESOLVED_URI, key);
				property = new LocatedProperty(originalUri, locatedUri, value, this.getClass());
				
				if (LOG.isDebugEnabled()) {
					LOG.debug(property.toString());
				}
			} else {
				property = new MissingProperty(originalUri, locatedUri, this.getClass());
				if (LOG.isTraceEnabled()) {
					LOG.trace(property.toString());
				}
			}
		}
		return property;
	}
	
	public boolean supports(URI key) {
		return true;
	}
	
	@Override
	public String toString() {
		
		StringBuilder b = new StringBuilder();

		b.append(this.getClass().getName());
		
		return b.toString();
	}
}

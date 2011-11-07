package org.bluemagic.config.location;

import java.net.URI;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;

public class CommandLineLocation extends UriLocation {

	/**
     * @param  uri - URI a unique identifier for a specific instance of data
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
	public String get(URI key, Map<MagicKey, Object> parameters) {

		String rval = null;
		String k = (String) parameters.get(MagicKey.ORIGINAL_URI);

		if ((k != null) && (!k.trim().isEmpty())) {

			// We want to check to see if the system property map contains
			// the key (if it does not contain the key then an override is
			// not going to occur. There is the case where the override can
			// be to remove a property (aka the value is empty).
			if (System.getProperties().containsKey(k)) {
				rval = System.getProperty(k);
			}
		}
		return rval;
	}
	
	public boolean supports(URI key) {
		return "cmd".equals(key.getScheme());
	}
}

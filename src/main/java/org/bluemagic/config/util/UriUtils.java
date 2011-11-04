package org.bluemagic.config.util;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UriUtils {

private static final Log LOG = LogFactory.getLog(UriUtils.class);

    /**
     * Utility method that takes any URI placed the query parameters
     * in a name/value map
	 *
	 * For Example:
	 * https://www.bluemagicsource.org/configuration/project/database/username?systemType=development&appType=json
	 * Output should be:
     *     <systemType, development>	 
	 *     <appType, json>
     **/
    public static Map<String, String> parseUriParameters(URI uri) {

	Map<String, String> rval = new HashMap<String, String>();
		
	if ((uri.getQuery() != null) && (!uri.getQuery().isEmpty())) {
	
	    String[] parameters = uri.getQuery().split("&");
	    for (String parameter : parameters) {
		String[] values = parameter.split("=");
			
		String key = values[0];
			
		if (key != null) { 
		    rval.put(key, values[1]);		
		}
	    }
	}
	return rval;
    }

    /**
     * @param uri - The incoming uri is required (cannot be null) but it does not have
     *              to have query parameters and can have just a question mark.
     * @param queryParameters - Optional and can be null or empty. If values then we
     *              append them according to the order of the map keyset (use comparator
     *              to control the order on the incoming map).
     **/
    public static URI rebuildUriWithNewQueryParameters(URI uri,
				                       Map<String, String> queryParameters) {

        URI rval = null;

	    try {
		
		StringBuilder uriStr = new StringBuilder(uri.toString().substring(0, uri.toString().indexOf('?')));

                if ((queryParameters != null) && (queryParameters.size() > 0)) {
		
   			uriStr.append("?");

			for (String key : queryParameters.keySet()) {

			    if (!uriStr.toString().endsWith("?")) {
				uriStr.append("&");
			    }
			    uriStr.append(key);
			    uriStr.append("=");
			    uriStr.append(queryParameters.get(key));		    
			}
		}
		rval = new URI(uriStr.toString());

	    } catch (Throwable t) {
		LOG.error("Failed trying to recreate uri<"
			  + uri.toString()
			  + "> while removing duplicates and ordering parameters",
		    t);

		throw new RuntimeException(t);
	    }
        return rval;
    }
    
    public static URI createUriFromString(String uriString) {
    	
    	URI uri = null;
    	
    	try {
    		uri = new URI(uriString);
    		
    	} catch (Throwable t) { 
    		LOG.warn("Unable to create URI from string: " + uriString);
    	}
    	return uri;
    }

	public static URI urlToUri(URL url) {

		URI uri = null;
		
		try {
    		uri = url.toURI();
    		
    	} catch (Throwable t) { 
    		LOG.warn("Unable to create URI from URL: " + url.toString());
    	}
    	return uri;
	}
}

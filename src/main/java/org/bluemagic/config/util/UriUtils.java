package org.bluemagic.config.util;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
     * @param key - The incoming key is required (cannot be null) but it does not have
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
		LOG.error("Failed trying to recreate key<"
			  + uri.toString()
			  + "> while removing duplicates and ordering parameters",
		    t);

		throw new RuntimeException(t);
	    }
        return rval;
    }
    
    public static URI toUri(String uriString) {
    	
    	URI uri = null;
    	
    	try {
    		uri = new URI(uriString);
    		
    	} catch (Throwable t) { 
    		LOG.warn("Unable to create URI from string: " + uriString);
    	}
    	return uri;
    }
    
    public static String[] splitUriAfterScheme(URI uri) {
    	
    	String[] split = new String[2]; 
    	StringBuilder u = new StringBuilder();
		
		String scheme = uri.getScheme();
		if (scheme != null) {
			u.append(scheme);
			u.append(":");
		
			if (uri.toASCIIString().contains("//")) {					
				u.append("//");
			}
		}
		split[0] = u.toString();

		if (uri.toASCIIString().contains("//")) {					
			split[1] = uri.toASCIIString().replaceAll(scheme + "://", "");
		} else {
			split[1] = uri.toASCIIString().replaceAll(scheme + ":", "");
		}
    	
    	return split;
    }
    
    public static URI addParameterToUri(URI uri, String key, String value) {
		
    	StringBuilder u = new StringBuilder();
    	TreeMap<String, String> orderedParameters = new TreeMap<String, String>();

		// The tree set naturally orders them and removes the
		// duplicates by the nature of the map and a tree map
		// automatically provides order.
		orderedParameters.putAll(UriUtils.parseUriParameters(uri));
		orderedParameters.put(key, value); 

		// Finally add the parameters back to a new URI.
		int indexOf = uri.toASCIIString().indexOf('?');
		
		String substring = null;
		if (!(indexOf < 0)) {
			substring = uri.toASCIIString().substring(0, indexOf);
		} else {
			substring = uri.toASCIIString();
		}
		if (orderedParameters.size() > 0) {

			u = new StringBuilder(substring);
			u.append("?");

			
			for (String k : orderedParameters.keySet()) {

			    if (!u.toString().endsWith("?")) {
			    	u.append("&");
			    }
		    	u.append(k);
		    	u.append("=");
		    	u.append(orderedParameters.get(k));
		    }
		} else if (key.toString().endsWith("?")) {
			// Remove the question mark off of the end... (jerk)
			u = new StringBuilder(substring);
	    }
		try {
			uri = new URI(u.toString());
		} catch (Exception e) { }
		
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

	public static String[] splitUriIntoPrefixParts(URI key, URI originalUri, String separator) {

		String[] keySplit = splitUriAfterScheme(key);
		
		if (originalUri == null) {
			originalUri = key;
		}
		String[] originalSplit = splitUriAfterScheme(originalUri);
		
		String scheme = keySplit[0];
		String prefixTags = "";
		
		if (!originalSplit[1].isEmpty()) {
			
			if (!keySplit[1].equals(originalSplit[1])) {
				
				String findMe = "";
				
				if (keySplit[1].length() > 0) {
					findMe = separator + originalSplit[1];
				} else {
					findMe = originalSplit[1];
				}
				prefixTags = keySplit[1].replaceAll(findMe, "");
			}
		}
		
		String[] split = new String[3];
		split[0] = scheme;
		split[1] = prefixTags;
		split[2] = originalSplit[1];
		
		return split;
	}

	public static String[] splitUriIntoPlaceholderParts(URI key, URI originalUri, String replace) {

		String[] split = new String[3];
		String keyAsString = key.toASCIIString();
		
		if (originalUri == null) {
			originalUri = key;
		}
		String originalUriAsString = originalUri.toASCIIString();
		
		int replaceStart = originalUriAsString.indexOf(replace);
		
		String beforePlaceholder = originalUriAsString.substring(0, replaceStart);
		String afterPlaceholder = originalUriAsString.substring(replaceStart + replace.length());
		String placeholderTags = keyAsString.replace(beforePlaceholder, "").replace(afterPlaceholder, ""); 
		
		if (replace.equals(placeholderTags)) {
			placeholderTags = "";
		}
		
		split[0] = beforePlaceholder;
		split[1] = placeholderTags;
		split[2] = afterPlaceholder;
		
		return split;
	}
}

package org.bluemagic.config.location.remote;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.bluemagic.config.location.remote.RestClientManager;

/**
 * A simple RestClientManager that uses a HashMap to store the values.  This way we can test
 * our remote code without always having to connect to a real REST service. 
 * 
 * The "URL" for this Manager is "http://www.xyz.com/magic/property/"
 * 
 * @author Sean Dobberstein
 *
 */
public class RestClientManagerHashMapImpl implements RestClientManager {

	public static final String URL = "http://www.xyz.com/magic/property/";
	public static final String DATA = "Property-Data";
	
	private HashMap<String, String> propertyMap;
	
	public RestClientManagerHashMapImpl() {
		propertyMap = new HashMap<String, String>();
	}
	
	@Override
	public String post(URI uri, Map<String, String> parameters) {
		if (parameters.containsKey(DATA)) {
			propertyMap.put(uri.toASCIIString(), parameters.get(DATA));
			return propertyMap.get(DATA);
		} else {
			throw new RuntimeException("Property-Data is required!");
		}
	}

	@Override
	public String get(URI uri) {
		String orig = uri.toASCIIString();
		
		System.out.println("[REST CLIENT] Checking uri: " + orig);
		
		if (orig.startsWith(URL)) {
			String property = orig.replace(URL, "");
			System.out.println("[REST CLIENT] Looking for property: " + property);
			return propertyMap.get(property);
		}
		
		return null;
	}

	@Override
	public String update(URI uri, Map<String, String> parameters) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String delete(URI uri) {
		return propertyMap.remove(uri.toASCIIString());
	}

}

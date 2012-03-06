package org.bluemagic.config.location;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.property.LocatedProperty;
import org.bluemagic.config.api.property.MagicProperty;
import org.bluemagic.config.api.property.MissingProperty;
import org.bluemagic.config.api.property.UnavailableProperty;
import org.bluemagic.config.location.remote.RestClientManager;
import org.bluemagic.config.location.remote.SimpleRestClientManager;
import org.bluemagic.config.util.UriUtils;

/**
 * the URL data supplier attempts to read any url and return all of the data
 * either as a String (transforming the character set) or if a character set is
 * not defined it is returned as a byte array. Notice that this data supplier
 * does not support any search capabilities.
 * 
 * This class also support username and password authentication.
 **/
public class RemoteLocation extends UriLocation {
	
	private static final Log LOG = LogFactory.getLog(RemoteLocation.class);
	
	private static Map<URI, Set<String>> prefetched;
	
	private RestClientManager restClientManager;
	
	private boolean prefetch = false;

	public void init() {
		
		if (restClientManager == null) {
			restClientManager = new SimpleRestClientManager();
		}
	}
	
	/**
	 * Uses the location and optional search criteria to locate the data and
	 * return it.
	 * 
	 * @ param location - Any string value that tells the configuration
	 * Management implementation where to find the data. Some examples include
	 * but are not limited to URIs, URLs, xQuery, xPath, ...
	 * 
	 * @ param searchCriteria - Not used by this implementation.
	 **/
	public MagicProperty locateHelper(URI key, Map<MagicKey, Object> parameters) {
		
		String value = null;
		MagicProperty property = null;
		
		// Until we figure out how to initialize this automatically via xml,
		// we should initialize it here.
		init();
		
		if (prefetch) {
			
			// CHECK TO SEE IF URI WAS ALREADY PREFETCHED
			URI originalUri = (URI) parameters.get(MagicKey.ORIGINAL_URI);
			Set<String> discovered = prefetched.get(originalUri);
			if (discovered == null) {
				// NO IT WASN'T SO GO AND PREFETCH THE KEY
				prefetched.put(key, searchForKeys(originalUri));
				
			} else {
				if (!(discovered.contains(key.toASCIIString()))) {
					// NO HIT IN PREFETCHED KEYS
					LOG.debug("Key: " + key.toASCIIString() + " not found in prefetched keys!");
					return null;
					
				} else {
					// DO NOTHING SINCE KEY WAS DISCOVERED
					LOG.debug("Prefetch hit for: " + key.toASCIIString());
				}
			}
		}
		URI propertyUri = key;
		
		// IF URI NOT DEFINED THEN USE KEY AS URI
		if (this.uri != null) {
			
			// FALL BACK USING URI + KEY
			if (this.uri.toASCIIString().endsWith("/")) {
				propertyUri = UriUtils.toUri(this.uri.toASCIIString() + key);
			} else {
				propertyUri = UriUtils.toUri(this.uri.toASCIIString());
			}
		}
		URI originalUri = (URI) parameters.get(MagicKey.ORIGINAL_URI);
		URI locatedUri = this.uri;
		
		try {
			// ACTUALLY RETRIEVE THE VALUE FROM THE REST CLIENT
			value = restClientManager.get(propertyUri);
			
			if (value != null) {
				parameters.put(MagicKey.RESOLVED_URI, locatedUri);
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
		} catch (Throwable t) {
			property = new UnavailableProperty(originalUri, locatedUri, this.getClass(), t);
			if (LOG.isTraceEnabled()) {
				LOG.trace(property.toString());
			}
		}
		return property;
	}
	
	public Set<String> searchForKeys(URI baseUri) {

		Set<String> keySet = new HashSet<String>();
		String searchResults = restClientManager.get(baseUri);
		String[] split = searchResults.split("\n");
		
		for (String uriAsString : split) {
			keySet.add(uriAsString);
		}
		return keySet;
	}
	
	@Override
	protected void addOriginalURI(Collection<URI> decorated, URI key) {
		if (this.uri.toASCIIString().endsWith("/")) {
			decorated.add(UriUtils.toUri(this.uri.toASCIIString() + key.toASCIIString()));
		} else {
			decorated.add(UriUtils.toUri(this.uri.toASCIIString() + "/" + key.toASCIIString()));			
		}
	}

	public boolean supports(URI key) {
		
		boolean supports = true;
		
		if (this.uri == null) {
			supports = "http".equals(key.getScheme()); 
		}
		return supports;
	}

	public void setPrefetch(boolean prefetch) {
		
		if ((prefetch) && (prefetched == null)){
			prefetched = new HashMap<URI, Set<String>>();
		}
		this.prefetch = prefetch;
	}

	public boolean isPrefetch() {
		return prefetch;
	}

	public void setRestClientManager(RestClientManager restClientManager) {
		this.restClientManager = restClientManager;
	}

	public RestClientManager getRestClientManager() {
		return restClientManager;
	}
}

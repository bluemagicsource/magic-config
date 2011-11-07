package org.bluemagic.config;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.util.DataNotFoundException;
import org.bluemagic.config.util.UriUtils;

/**
 * The Default Configuration Agent class is the entry point where all external
 * processes and programs arrive to retrieve their configuration settings via
 * the hierarchical algorithms configured for their client. Note that the client
 * can be custom configured for almost any algorithm.
 * 
 * Developer's Note (Would like to put this at the package module level) Logging
 * (from the first time we wrote this code we learned that the logging setup was
 * very important and consistency was crucial for quickly finding problems
 * 
 * Our use of logging levels Error - A critical failure occurred that will
 * prevent our code from executing and recovering from the problem. Always
 * include stack traces. Warn - Usually indicates that there is a problem;
 * however, we can recover from this problem and continue. Stack traces should
 * be added if logging is set to trace. Info - Debug information to help at a
 * summary level Debug - More detailed debug information Trace - More detailed
 * debug and includes stack traces.
 * 
 **/
public class BlueMagicProperties extends Properties {
	
	private static final long serialVersionUID = -2208105007603310712L;
	
	private static final Log LOG = LogFactory.getLog(BlueMagicProperties.class);

	private Collection<Location> configLocations;
	
	private Collection<Location> agentLocations;
	
	private String keyPrefix = "";

	// SET TO FALSE FOR NOW, BUT IS CONFIGURABLE
	private boolean autoInitialize = false;

	// STOPS NAGGING ABOUT INITIALIZATION
	private boolean hasBeenWarned = false;
	
	public void init() {
		if (configLocations == null) {
			//configLocations = LocationBuilder.buildConfigLocations(agentLocations);
		}
	}

	public synchronized Object get(Object key) {
		
		Object value = null;
		
		// IF URI WAS PASSED IN, CONVERT IT TO STRING
		// BEING NICE AND FRIENDLY
		if (key instanceof URI) {
			key = ((URI) key).toASCIIString();
			
		}
		// ONLY STRING KEYS ARE SUPPORTED AT THIS TIME
		if (!(key instanceof String)) {
			
			String message = "Only java.util.String key values are supported at this time.";
			LOG.error(message);
			throw new RuntimeException(message);
		} else {
			// GET THE VALUE SINCE IT IS A STRING
			value = getProperty(key.toString()); 
		}
		return value;
	}
	
	public String getProperty(String keyAsString) {
		
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		URI key = UriUtils.toUri(getKeyPrefix() + keyAsString);

		return getMagic(key, parameters);
	}
	
	public String getProperty(String keyAsString, String defaultValue) {
		
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		URI key = UriUtils.toUri(getKeyPrefix() + keyAsString);
		parameters.put(MagicKey.DEFAULT_VALUE, defaultValue);
		
		return getMagic(key, parameters);
	}
	
	public String getMagic(URI key, Map<MagicKey, Object> parameters) {
		
		String value = null;
		
		// CHECK TO SEE IF INITIALIZED
		initializeCheck();
		
		// IF NO PARAMETERS PASSED IN PREP IT FOR MAGIC FRAMEWORK
		// DONT FORGET TO ADD IN THE ORIGINAL URI TO THE PARAMS
		if (parameters == null) {
			parameters = new HashMap<MagicKey, Object>();
		}
		parameters.put(MagicKey.ORIGINAL_URI, key);
		
		// THE CORE PART THAT CHECKS EACH LOCATION FOR PROPERTIES
		if (configLocations != null) {
			for (Location location : configLocations) {
				
				try {
					if (location.supports(key)) {
						value = location.get(key, parameters);
					}
				} catch (DataNotFoundException dnfe) {
					dnfe.printStackTrace();
				}
			}
		}
		// CHECK THE DEFAULT VALUE, IF ONE PROVIDED
		if (value == null) {
			value = (String) parameters.get(MagicKey.DEFAULT_VALUE);
		} else {
			LOG.debug("Found property: " + key + " at location: " + parameters.get(MagicKey.RESOLVED_URI));
		}
		// CHECK WITH PARENT PROPERTIES TO SEE IF SOMEONE ADDED IT
		if (value == null) {
			Object ret = super.get(key.toASCIIString());
			if (ret != null) {
				value = ret.toString();
			}
		}
		return value;
	}

	private void initializeCheck() {
		
		if ((configLocations == null) && (!hasBeenWarned)) {
			LOG.warn("BluemagicProperties was not initialized, please use init() method.");
			hasBeenWarned = true;
			
			if (isAutoInitialize()) {
				init();
			}
		}
	}

	public void setAgentLocations(Collection<Location> agentLocations) {
		this.agentLocations = agentLocations;
	}

	public Collection<Location> getAgentLocations() {
		return agentLocations;
	}

	public void setConfigLocations(Collection<Location> configLocations) {
		this.configLocations = configLocations;
	}

	public Collection<Location> getConfigLocations() {
		return configLocations;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setAutoInitialize(boolean autoInitialize) {
		this.autoInitialize = autoInitialize;
	}

	public boolean isAutoInitialize() {
		return autoInitialize;
	}
}

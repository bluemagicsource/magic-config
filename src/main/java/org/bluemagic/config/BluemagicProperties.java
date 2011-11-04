package org.bluemagic.config;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.agent.ConfigKey;
import org.bluemagic.config.api.agent.Location;
import org.bluemagic.config.util.DataNotFoundException;
import org.bluemagic.config.util.LocationBuilder;
import org.bluemagic.config.util.UriUtils;

/**
 * The Default Configuration Agent class is the entry point where all external
 * processes and programs arrive to retreive their configuration settings via
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
public class BluemagicProperties extends Properties {
	
	private static final long serialVersionUID = -2208105007603310712L;
	
	private static final Log LOG = LogFactory.getLog(BluemagicProperties.class);
	
	private String uriPrefix = "";

	private Collection<Location> agentLocations;
	
	private Collection<Location> configLocations;
	
	public void init() {
		if (configLocations == null) {
			configLocations = LocationBuilder.buildConfigLocations(agentLocations);
		}
	}

	public synchronized Object get(Object key) {
		return getProperty((String) key);
	}
	
	public String getProperty(String key) {
		
		Map<ConfigKey, Object> parameters = new HashMap<ConfigKey, Object>();
		URI uri = UriUtils.createUriFromString(uriPrefix + key);

		return getMagic(uri, parameters);
	}
	
	public String getProperty(String key, String defaultValue) {
		
		Map<ConfigKey, Object> parameters = new HashMap<ConfigKey, Object>();
		URI uri = UriUtils.createUriFromString(uriPrefix + key);
		parameters.put(ConfigKey.DEFAULT_VALUE, defaultValue);
		
		return getMagic(uri, parameters);
	}
	
	public String getMagic(URI uri, Map<ConfigKey, Object> parameters) {
		
		String value = null;
		
		if (configLocations == null) {
			LOG.warn("BluemagicProperties was not initialized, please use init() method.");
			init();
		}

		if (parameters == null) {
			parameters = new HashMap<ConfigKey, Object>();
		}
		parameters.put(ConfigKey.ORIGINAL_URI, uri);
		
		for (Location location : configLocations) {
			
			try {
				value = location.get(uri, parameters);
			} catch (DataNotFoundException dnfe) {
				dnfe.printStackTrace();
			}
		}
		if (value == null) {
			value = (String) parameters.get(ConfigKey.DEFAULT_VALUE);
		}
		return value;
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

	public void setUriPrefix(String uriPrefix) {
		this.uriPrefix = uriPrefix;
	}

	public String getUriPrefix() {
		return uriPrefix;
	}
}

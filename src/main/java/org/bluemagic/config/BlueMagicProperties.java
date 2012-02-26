package org.bluemagic.config;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.factory.ConfigXmlParser;
import org.bluemagic.config.factory.DecoratorFactory;
import org.bluemagic.config.factory.LocationFactory;
import org.bluemagic.config.factory.TagFactory;
import org.bluemagic.config.factory.TransformerFactory;
import org.bluemagic.config.location.LocalLocation;
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
	
	private String magicConfigFile = "magic-config.xml";
	
	private Collection<Location> magicConfigLocations;
	
	private Collection<Location> configLocations;
	
	private ConfigXmlParser xmlParser;
	
	private String keyPrefix = "";

	// SET TO TRUE FOR NOW, BUT IS CONFIGURABLE
	private boolean autoInitialize = true;

	// STOPS NAGGING ABOUT INITIALIZATION
	private boolean hasBeenWarned = false;
	
	public BlueMagicProperties() { }
	
	public BlueMagicProperties(String magicConfigFile) {
		this.magicConfigFile = magicConfigFile;
	}
	
	public void init() {
		if (configLocations == null) {
			
			// DEFAULT TO MAGIC-CONFIG FILE
			if (magicConfigLocations == null) {
				magicConfigLocations = new ArrayList<Location>();
				magicConfigLocations.add(new LocalLocation(getMagicConfigFile()));
			}
			// BAREBONES INITIALIZATION
			if (xmlParser == null) {
				xmlParser = new ConfigXmlParser();
				xmlParser.setDecoratorFactory(new DecoratorFactory());
				xmlParser.setLocationFactory(new LocationFactory());
				xmlParser.setTagFactory(new TagFactory());
				xmlParser.setTransformerFactory(new TransformerFactory());
			}
			// BUILD THE CONFIG LOCATIONS FROM THE MAGIC-CONFIG
			configLocations = xmlParser.buildLocations(magicConfigLocations);
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
		
		// CHECK IF THIS PROPERTY WAS ALREADY FOUND
        final Object ret = super.get(key.toASCIIString());
        if (ret != null) {
            value = ret.toString();
        }
		// THE CORE PART THAT CHECKS EACH LOCATION FOR PROPERTIES
		if (value == null && configLocations != null) {
			for (Location location : configLocations) {
				
				try {
					if (location.supports(key)) {
						value = location.locate(key, parameters);
						
						// CHECK TO SEE IF WE FOUND A VALUE
						if (value != null) {
						    super.put(key.toASCIIString(), value);
							break;
						}
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
		this.magicConfigLocations = agentLocations;
	}

	public Collection<Location> getAgentLocations() {
		return magicConfigLocations;
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

	public Collection<Location> getMagicConfigLocations() {
		return magicConfigLocations;
	}

	public void setMagicConfigLocations(Collection<Location> magicConfigLocations) {
		this.magicConfigLocations = magicConfigLocations;
	}

	public ConfigXmlParser getXmlParser() {
		return xmlParser;
	}

	public void setXmlParser(ConfigXmlParser xmlParser) {
		this.xmlParser = xmlParser;
	}

	public void setMagicConfigFile(String magicConfigFile) {
		this.magicConfigFile = magicConfigFile;
	}

	public String getMagicConfigFile() {
		return magicConfigFile;
	}
}

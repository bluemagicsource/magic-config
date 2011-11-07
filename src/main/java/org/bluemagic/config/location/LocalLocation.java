package org.bluemagic.config.location;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.util.DataNotFoundException;
import org.bluemagic.config.util.UriUtils;

/**
 *
 *
 **/
public class LocalLocation extends UriLocation {

	private static final Log LOG = LogFactory.getLog(LocalLocation.class);

	/**
	 * 
	 * 
	 */
	public String get(URI key, Map<MagicKey, Object> parameters) {

		File file = null;
		String value = null;
		boolean useKeyAsUri = false;
		Properties properties = new Properties();
		String keyAsString = key.toASCIIString();

		// IF URI IS NULL USE INCOMING KEY AS URI
		if (this.uri == null) {
			useKeyAsUri = true;
			this.uri = UriUtils.toUri(keyAsString.substring(0, keyAsString.lastIndexOf("/")));
			keyAsString = keyAsString.substring(keyAsString.lastIndexOf("/") + 1, keyAsString.length());
		}
		
		// CREATE THE FILE PATH
		try {
			// FILE ON THE SYSTEM
			if ("file".equals(this.uri.getScheme())) {
				file = new File(this.uri);

			} else {
				// FILE ON THE CLASSPATH
				String schemeSpecificPart = this.uri.getSchemeSpecificPart().replace("//", "");
				URL url = this.getClass().getClassLoader().getResource(schemeSpecificPart);
				file = new File(UriUtils.urlToUri(url));
			}
			//LOAD PROPERTIES FROM FILE
			properties = loadPropertiesFromFile(file);

		} catch (Throwable t) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("Failed to open file:" + this.uri, t);
			} else if (LOG.isDebugEnabled()) {
				LOG.trace("Failed to open file:" + this.uri);
			}
			throw new RuntimeException(t);
		}
		if (LOG.isTraceEnabled()) {
			LOG.trace("Searching for property:" + keyAsString);
		}
		// GET THE PROPERTY
		value = properties.getProperty(keyAsString);

		// IF WE GOT A VALUE, TRIM THE WHITESPACE
		if (value != null) {
			value = value.trim();
		}
		if (value == null) {
			throw new DataNotFoundException();
			
		} else if (LOG.isTraceEnabled()) {
			LOG.trace("Found Property for URI:" + key + " at " + this.uri.toString() + " value of " + value);
		}
		// QUICK CHECK TO RESET URI IF IT WAS DYNAMIC
		if (useKeyAsUri) {
			this.uri = null;
		}
		return value;
	}

	private Properties loadPropertiesFromFile(File file) throws Exception {

		Properties properties = new Properties();
		
		// XML FILE
		if (this.uri.toASCIIString().endsWith(".xml")) {
			properties.loadFromXML(new FileInputStream(file));
		} else {
			// STANDARD PROPERTIES FILE
			properties.load(new FileInputStream(file));
		}
		return properties;
	}

	public boolean supports(URI uri) {
		
		boolean supports = true;
		
		if (this.uri == null) {
			supports = "file".equals(uri.getScheme()) || "classpath".equals(uri.getScheme());
		} 
		return supports;
	}
}

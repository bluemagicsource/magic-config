package org.bluemagic.config.location;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.util.UriUtils;

/**
 *
 *
 **/
public class LocalLocation extends UriLocation {

	private static final Log LOG = LogFactory.getLog(LocalLocation.class);

	public LocalLocation() { }
	
	public LocalLocation(URI uri) {
		this.uri = uri;
	}
	
	public LocalLocation(String uriString) {
		this.uri = UriUtils.toUri(uriString);
	}
	
	/**
	 * 
	 * 
	 */
	public String get(URI key, Map<MagicKey, Object> parameters) {

		File file = null;
		String value = null;
		boolean useKeyAsUri = false;
		boolean returnFile = false;
		Properties properties = new Properties();
		String keyAsString = key.toASCIIString();

		// IF URI IS NULL USE INCOMING KEY AS URI
		if (this.uri == null) {
			useKeyAsUri = true;
			if ((keyAsString.endsWith(".xml")) || (keyAsString.endsWith(".properties"))) {
				returnFile = true;
				this.uri = key;
			} else {
				this.uri = UriUtils.toUri(keyAsString.substring(0, keyAsString.lastIndexOf("/")));
				keyAsString = keyAsString.substring(keyAsString.lastIndexOf("/") + 1, keyAsString.length());
			}
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
			if (returnFile == false) {
				//LOAD PROPERTIES FROM FILE
				properties = loadPropertiesFromFile(file);
			} else {
				return convertFileToString(file);
			}

		} catch (Throwable t) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("Failed to open file:" + this.uri, t);
			} else if (LOG.isDebugEnabled()) {
				LOG.trace("Failed to open file:" + this.uri);
			}
			//throw new RuntimeException(t);
		}
		if (LOG.isTraceEnabled()) {
			LOG.trace("Searching for property:" + keyAsString);
		}
		// GET THE PROPERTY
		value = properties.getProperty(keyAsString);

		// IF WE GOT A VALUE, TRIM THE WHITESPACE
		if (value != null) {
			
			value = value.trim();
			if (LOG.isTraceEnabled()) {
				LOG.trace("Found Property for URI:" + key + " at " + this.uri.toString() + " value of " + value);
			}
		}
		// QUICK CHECK TO RESET URI IF IT WAS DYNAMIC
		if (useKeyAsUri) {
			this.uri = null;
		}
		return value;
	}

	private String convertFileToString(File file) {

		StringBuilder b = new StringBuilder();
		
		try {
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream(file);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
				  // Print the content on the console
				  b.append(strLine);
				  b.append("\n");
			  }
			  //Close the input stream
			  in.close();
		} catch (Exception e) { //Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		}
		return b.toString();
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
			supports = (uri.getScheme() == null) || ("file".equals(uri.getScheme())) || ("classpath".equals(uri.getScheme()));
		} 
		return supports;
	}
}

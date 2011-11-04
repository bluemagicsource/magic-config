package org.bluemagic.config.location;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.agent.ConfigKey;
import org.bluemagic.config.util.DataNotFoundException;
import org.bluemagic.config.util.UriUtils;

/**
 * The classpath data supplier is expecting a location in the format of
 * classpath://[filename]?name=propname&type=json or it may also start with
 * "resource://", "classpath:", or "resource:" (the params are an optional way
 * of asking for a value (supported by another nested url data supplier).
 **/
public class LocalLocation extends UriLocation {

	private static final Log LOG = LogFactory.getLog(LocalLocation.class);

	/**
	 * Uses the location and optional search criteria to locate the data and
	 * return it.
	 * 
	 * @param uri
	 *            - Any URI value that tells the Configuration Management
	 *            implementation where to find the data. Some examples include
	 *            but are not limited to: classpath://[resource
	 *            name]?name=[property name]&type=[JSON|XML|TXT] file://[file
	 *            name]?name=[property name]&type=[JSON|XML|TXT] file://[file
	 *            name]?name=[property name]&type=[JSON|XML|TXT]
	 *            resource://[file name]?name=[property
	 *            name]&type=[JSON|XML|TXT] (note a resource can be found in
	 *            either a file or classpath)
	 *            https://www.svn.org/svn/repos/trunk
	 *            /conf/a.properties?name=prop&type=JSON Note that all
	 *            parameters (name and value pairs at the end of the URI) are
	 *            automatically added to the parameter map (second input
	 *            argument) If missing then we automatically create this map and
	 *            pass it along based on this contract.
	 * 
	 *            @ param parameterMap - Map<Name, Value> Generic method for
	 *            passing any data along such as search criteria that will be
	 *            used by subsequent implementations. Note that any name value
	 *            pairs from the URI are automatically added to this map.
	 * 
	 *            @ return InputStream - open to the file.
	 **/
	public String get(URI uri, Map<ConfigKey, Object> parameters) {

		File file = null;
		String value = null;

		String key;
		Properties properties = new Properties();

		// CREATE THE FILE PATH
		try {
			// FILE ON THE SYSTEM
			if ("file".equals(this.uri.getScheme())) {

				URI fileUri = UriUtils.createUriFromString(this.uri.toASCIIString().replaceAll(this.uri.getScheme(), "file"));
				file = new File(fileUri);

			} else {
				// FILE ON THE CLASSPATH
				URL url = this.getClass()
						.getClassLoader().getResource(this.uri.getAuthority());
				file = new File(UriUtils.urlToUri(url));
			}
			// XML FILE
			if (this.uri.toASCIIString().endsWith(".xml")) {
				properties.loadFromXML(new FileInputStream(file));
			} else {
				// STANDARD PROPERTIES FILE
				properties.load(new FileInputStream(file));
			}
			// GRAB THE KEY
			key = uri.toASCIIString();

		} catch (Throwable t) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("Failed to open file:" + this.uri, t);
			} else if (LOG.isDebugEnabled()) {
				LOG.trace("Failed to open file:" + this.uri);
			}
			throw new RuntimeException(t);
		}

		if (LOG.isTraceEnabled()) {
			LOG.trace("Searching for property:" + key);
		}
		value = properties.getProperty(key);

		// IF WE GOT A VALUE, TRIM THE WHITESPACE
		if (value != null) {
			value = value.trim();
		}

		if (LOG.isTraceEnabled()) {
			LOG.trace(properties);
		} else if (LOG.isTraceEnabled()) {
			LOG.debug("Value:" + value);
		}

		if (value == null) {
			throw new DataNotFoundException();
		} else if (LOG.isTraceEnabled()) {
			LOG.trace("Found Property for URI:" + uri + " at "
					+ this.uri.toString() + "value of" + value);
		}
		return value;
	}
}

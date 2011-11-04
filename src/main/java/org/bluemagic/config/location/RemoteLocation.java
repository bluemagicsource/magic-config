package org.bluemagic.config.location;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.agent.ConfigKey;
import org.bluemagic.config.api.agent.Location;
import org.bluemagic.config.util.UsernamePasswordAuthenticator;

/**
 * the URL data supplier attempts to read any url and return all of the data
 * either as a String (transforming the character set) or if a character set is
 * not defined it is returned as a byte array. Notice that this data supplier
 * does not support any search capabilities.
 * 
 * This class also support username and password authentication.
 **/
public class RemoteLocation implements Location {
	
	private static final Log LOG = LogFactory.getLog(RemoteLocation.class);
	
	private String username = null;
	
	private String password = null;
	
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
	public String get(URI uri, Map<ConfigKey, Object> parameters) {
		
		String rval = null;
		HttpURLConnection urlConnection = null;
		BufferedReader urlReader = null;

		try {

			if ((username != null) && (password != null)) {
				 Authenticator.setDefault(new
				 UsernamePasswordAuthenticator(username,
				 password.toCharArray()));
			}

			URL url = null;
			url = uri.toURL();

			urlConnection = (HttpURLConnection) url.openConnection();
			HttpURLConnection.setFollowRedirects(true);
			urlConnection.setInstanceFollowRedirects(true);
			urlConnection.setAllowUserInteraction(false);
			urlConnection.connect();

			// Read the data out of the stream; we are assuming
			// that we can do it in one read (otherwise we are
			// probably timing out and have other problems).
			urlReader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));

			String buffer = null;
			StringBuffer content = new StringBuffer();

			while ((buffer = urlReader.readLine()) != null) {

				// Append a newline if we are past the second line and the new
				// buffer
				// is not empty.
				if ((content.length() > 0) && (buffer.length() > 0)) {
					content.append("\n"); // it trims the newline character
				}
				content.append(buffer);
			}

			// check for a stack or error from the server...
			int responseCode = urlConnection.getResponseCode();
			if (responseCode != 200) {
				throw new RuntimeException("Server displayed a stack trace;\n"
						+ content.toString());
			} else if (content.toString().indexOf("java.lang.RuntimeException") > -1) {
				throw new RuntimeException(
						"Server displayed a stack trace without setting the responseCode "
								+ content.toString());
			}
			rval = content.toString();

			LOG.trace("Found content:" + rval);

		} catch (Throwable t) {

			LOG.trace(
					"Failed to retrieve data from the server:" + uri.toString(),
					t);

			throw new RuntimeException(
					"Failed to retrieve data from the server " + uri, t);

		} finally {

			if (urlReader != null) {
				// ResourceManager.close(urlReader);
			}

			if (urlConnection != null) {
				try {
					urlConnection.disconnect();
				} catch (Throwable t1) {
					// do nothing we are simply trying to close
				}
			}
		}

		return rval;
	}
	
	/**
	 * Optional, username for authentication with the subversion server.
	 * 
	 * @param username
	 *            - String, when null we assume no authentication (or an
	 *            alternative implementation will be supplied)
	 **/
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Optional, password for authentication withe subversion server
	 * 
	 * @param password
	 *            - String, when null we assume no authentication (or an
	 *            alternative implementation will be supplied).
	 **/
	public void setPassword(String password) {
		this.password = password;
	}
}

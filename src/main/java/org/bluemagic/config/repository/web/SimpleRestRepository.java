package org.bluemagic.config.location.remote;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SimpleRestClientManager implements RestClientManager {
	
	private static final Log LOG = LogFactory.getLog(SimpleRestClientManager.class);

	public String post(URI uri, Map<String, String> parameters) {
		throw new UnsupportedOperationException();
	}

	public String get(URI uri) {
		
		URL url = null;
		String rval = null;
		BufferedReader urlReader = null;
		HttpURLConnection urlConnection = null;
		
		try {
			// QUICK NULL CHECK
			if (uri != null) {
				url = uri.toURL();
			
				urlConnection = (HttpURLConnection) url.openConnection();
				HttpURLConnection.setFollowRedirects(true);
				urlConnection.setInstanceFollowRedirects(true);
				urlConnection.setAllowUserInteraction(false);
				urlConnection.connect();
		
				// Read the data out of the stream; we are assuming
				// that we can do it in one read (otherwise we are
				// probably timing out and have other problems).
				urlReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
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
					throw new RuntimeException("Server displayed a stack trace;\n" + content.toString());
				} else if (content.toString().indexOf("java.lang.RuntimeException") > -1) {
					throw new RuntimeException("Server displayed a stack trace without setting the responseCode " + content.toString());
				}
				rval = content.toString();
		
				LOG.trace("Found content:" + rval);
			}
	
		} catch (Throwable t) {
			LOG.trace("Failed to retrieve data from the server:" + url.toString(), t);
	
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

	public String update(URI uri, Map<String, String> parameters) {
		throw new UnsupportedOperationException();
	}

	public String delete(URI uri) {
		throw new UnsupportedOperationException();
	}
}

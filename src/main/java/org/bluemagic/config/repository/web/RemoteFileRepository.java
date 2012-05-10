package org.bluemagic.config.repository.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class RemoteFileRepository extends WebRepository {

	@Override
	public Object put(URI key, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(URI key) {
		
		Object result = null;
		StringBuilder b = new StringBuilder();
		
		try {
			
			URL url = key.toURL();
			InputStream inputStream = url.openStream();
			
			// Get the object of DataInputStream
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String strLine;
	
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				b.append(strLine);
			}
			result = b.toString().trim();
			
			// Close the input stream
			inputStream.close();
			
			return result;
			
		} catch (Throwable t) {
			throw new RuntimeException(t.getMessage(), t);
		}
	}

	@Override
	public Object remove(URI key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return 1;
	}

}

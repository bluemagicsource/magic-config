package org.bluemagic.config.repository.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class TextFileRepository extends FileRepository {
	
	@Override
	public Object put(URI key, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(URI key) {
		
		Object value = null;
		StringBuilder b = new StringBuilder();

		try {
			File file = new File(key.toASCIIString());
			FileInputStream fstream = new FileInputStream(file);
			
			// Get the object of DataInputStream
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;

			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				b.append(strLine);
			}
			value = b.toString().trim();
			
			// Close the input stream
			fstream.close();
			
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return value;
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

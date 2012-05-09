package org.bluemagic.config.repository.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.Reader;
import java.net.URI;
import java.util.Properties;

public class PropertiesFileRepository extends FileRepository {

	private Properties properties = new Properties();
	
	public PropertiesFileRepository(File file) {

		try {
			// XML FILE
			if (file.getName().endsWith(".xml")) {
				properties.loadFromXML(new FileInputStream(file));
			} else {
				// STANDARD PROPERTIES FILE
				properties.load(new FileInputStream(file));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PropertiesFileRepository(Reader reader) {
		try {
			properties.load(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object put(URI key, Object value) {
		return properties.put(key, value);
	}

	public Object get(URI key) {
		return properties.get(key.toASCIIString());
	}

	public Object remove(URI key) {
		return properties.remove(key);
	}

	public void clear() {
		properties.clear();
	}

	public int size() {
		return properties.size();
	}
	
	@Override
	public String toString() {
		
		StringBuilder b = new StringBuilder();

		b.append(this.getClass().getName());

		return b.toString();
	}
}

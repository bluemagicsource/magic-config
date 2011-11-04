package org.bluemagic.config.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyFileUtils {
	
	private static final Log LOG = LogFactory.getLog(PropertyFileUtils.class);

	public static Properties loadFromFile(File file) {

		Properties properties = new Properties();
		
		try {
			properties.load(new FileInputStream(file));
		} catch (Throwable t) {
			LOG.error("Unable to load properties from file: " + file.getName());
		}
		return properties;
	}
}

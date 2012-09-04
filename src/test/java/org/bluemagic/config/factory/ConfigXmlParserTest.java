package org.bluemagic.config.factory;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

public class ConfigXmlParserTest {

	private static final Log LOG = LogFactory.getLog(ConfigXmlParserTest.class);
	private static final String BASE_PATH = "src" + File.separator + "test" 
			+ File.separator + "resources" + File.separator;
	
	@Before
	public void setUp() throws Exception {
		// Setup logging
		PropertyConfigurator.configure(BASE_PATH + "log4j.junit.properties");
	}

	@Test
	public void test() {
		
		LOG.info("Starting xml parser");
		
		ConfigXmlParser.parseMagicConfig(BASE_PATH + "newMagicConfig.xml");
	}

}

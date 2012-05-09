package org.bluemagic.config.util;

import java.util.ArrayList;
import java.util.Collection;

import org.bluemagic.config.api.Location;
import org.bluemagic.config.factory.ConfigXmlParser;
import org.bluemagic.config.factory.DecoratorFactory;
import org.bluemagic.config.factory.LocationFactory;
import org.bluemagic.config.factory.TagFactory;
import org.bluemagic.config.factory.TransformerFactory;
import org.bluemagic.config.location.FileLocation;
import org.junit.Test;

public class PropertiesFactoryTest {

	@Test
	public void buildLocation() {
		Collection<Location> agentLocations = new ArrayList<Location>();
		
		FileLocation fileLocationOld = new FileLocation();
		fileLocationOld.setFile("sample-agent.xml");
		agentLocations.add(fileLocationOld);
		
		ConfigXmlParser configXmlParser = new ConfigXmlParser();
		configXmlParser.setDecoratorFactory(new DecoratorFactory());
		configXmlParser.setLocationFactory(new LocationFactory());
		configXmlParser.setTagFactory(new TagFactory());
		configXmlParser.setTransformerFactory(new TransformerFactory());
		
//		Collection<Location> locations = configXmlParser.buildLocations(agentLocations);
//		assertEquals(3, locations.size());
	}
}

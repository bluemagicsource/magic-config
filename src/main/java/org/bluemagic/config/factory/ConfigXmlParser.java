package org.bluemagic.config.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.tag.Tag;
import org.bluemagic.config.tag.MethodDecoratedTag;
import org.bluemagic.config.util.DocumentUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ConfigXmlParser {
	
	private static final Log LOG = LogFactory.getLog(ConfigXmlParser.class);
	
	private static LocationFactory locationFactory = LocationFactoryImpl.getInstance();
	
	private static DecoratorFactory decoratorFactory = DecoratorFactoryImpl.getInstance();
	
	private static TagFactory tagFactory = TagFactoryImpl.getInstance();
	
	private ConfigXmlParser() {}
	
	public static List<Location> parseMagicConfig(String pathToConfig) {
		
		LOG.info("Parsing magic config file: " + pathToConfig);
		
		// Perform XSD validation on the config file
		
		// Parse the config file to a document
		Document document = DocumentUtils.createDocument(pathToConfig);
		
		// Get the location elements from the xml
		List<Location> locations = new ArrayList<Location>();
		Element rootElement = document.getDocumentElement();
		List<Element> locationElements = DocumentUtils.getElementList(rootElement.getChildNodes());
		
		// Loop through the locationElements and create Location objects
		for (Element locationElement : locationElements) {
			List<Location> newLocations = createLocation(locationElement);
			if (newLocations != null) {
				locations.addAll(newLocations);
			}
		}
		
		LOG.info("There were " + locations.size() + " locations created from the config file");
		for (Location location : locations) {
			LOG.debug(location);
		}
		
		return locations;
	}

	private static List<Location> createLocation(Element locationElement) {
		
		LOG.debug("Going to create location from element: " + locationElement.getNodeName());
		
		List<Location> locations = new ArrayList<Location>();
		
		if (locationElement.getChildNodes().getLength() == 0) {
			
			// THIS IS A SIMPLE LOCATION, NO DECORATORS OR TAGS
			
			LOG.debug("Creating location without decorators");
			
			// Create the simple un-decorated location
			locations.add(locationFactory.createLocation(locationElement.getNodeName(), 
					                                     DocumentUtils.getAttributesMap(locationElement)));
			
			LOG.debug("Location created: " + locations);
			
		} else {
			
			LOG.debug("Going to create decorators for location");
			
			// THIS IS A COMPLEX LOCATION, IT COULD EITHER CONTAIN DECORATORS DIRECTLY OR ATTEMPT BLOCKS
			List<Element> locationChildren = DocumentUtils.getElementList(locationElement.getChildNodes());
			for (Element element : locationChildren) {
				
				if (element.getNodeName().equals("attempt")) {
					
					LOG.info("Attempt found, create location from it's children");
					locations.add(createDecoratedLocation(locationElement.getNodeName(), 
													      DocumentUtils.getAttributesMap(locationElement),
													      element));
				} else {
					
					LOG.info("No attempt found, create location from this element");
					locations.add(createDecoratedLocation(locationElement.getNodeName(), 
													      DocumentUtils.getAttributesMap(locationElement),
													      locationElement));
				}
			}
		}
		
		return locations;
	}
	
	private static Location createDecoratedLocation(String locationType, Map<String, String> attributes, Element locationElement) {
		
		List<Decorator> locationDecorators = new ArrayList<Decorator>();
		List<Decorator> keyDecorators = new ArrayList<Decorator>();
		createDecorators(locationElement, locationDecorators, keyDecorators);
		
		LOG.debug("Location decorators created: " + locationDecorators);
		LOG.debug("Key decorators created: " + keyDecorators);
		LOG.debug("Creating decorated location");
		
		// Create the decorated location
		Location location = locationFactory.createLocation(locationType, 
				                                           attributes, 
												           locationDecorators,
												           keyDecorators);
		
		LOG.debug("Decorated location created: " + location);
		
		return location;
	}

	private static void createDecorators(Node locationNode, List<Decorator> locationDecorators, List<Decorator> keyDecorators) {
		
		// Get the decorator elements from the location element
		List<Element> decoratorElements = DocumentUtils.getElementList(locationNode.getChildNodes());
		
		// a decorator MUST have at least one tag
		for (Element decoratorElement : decoratorElements) {
			
			LOG.debug("Going to create tags for decorator: " + decoratorElement);
			
			List<MethodDecoratedTag> tags = createTags(decoratorElement);
			
			LOG.debug("Tags created: " + tags);
			
			// Tags are required for a Decorator
			if (tags.size() == 0) {
				throw new RuntimeException("No tags created for decorator: " + decoratorElement);
			}
			
			for (MethodDecoratedTag tag : tags) {
				
				LOG.debug("Creating decorator for tag: " + tag.getTag());
				
	//			Decorator decorator = decoratorFactory.createDecorator(decoratorElement.getNodeName(), 
	//					                                               DocumentUtils.getAttributesMap(decoratorElement), 
	//					                                               tags);
	//			
	//			if (decoratorElement.getNodeName().equals("locationDecorator")) {
	//				locationDecorators.add(decorator);
	//			} else if (decoratorElement.getNodeName().equals("keyDecorator")) {
	//				keyDecorators.add(decorator);
	//			} else {
	//				throw new RuntimeException("Unknown decorator type");
	//			}
			}
		}
	}
	
	private static List<MethodDecoratedTag> createTags(Element decoratorElement) {
		
		List<MethodDecoratedTag> tags = new ArrayList<MethodDecoratedTag>();
		
		// Get the tag elements from the decorator element
		List<Element> tagElements = DocumentUtils.getElementList(decoratorElement.getChildNodes());
		LOG.debug("Going to create " + tagElements.size() + " tags for this decorator");
		
		for (Element tagElement : tagElements) {
			
			LOG.debug("Creating tag from element: " + tagElement);

			tags.add(tagFactory.createTag(tagElement.getNodeName(), 
					 DocumentUtils.getAttributesMap(tagElement), 
					 tagElement.getTextContent()));
		}
		
		return tags;
	}
}

package org.bluemagic.config.factory;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.property.LocatedProperty;
import org.bluemagic.config.api.tag.DoubleTag;
import org.bluemagic.config.api.tag.SingleTag;
import org.bluemagic.config.api.tag.Tag;
import org.bluemagic.config.api.tag.TripleTag;
import org.bluemagic.config.exception.MagicConfigParserException;
import org.bluemagic.config.location.DecoratingLocationWrapper;
import org.bluemagic.config.location.FileLocation;
import org.bluemagic.config.repository.file.TextFileRepository;
import org.bluemagic.config.util.StringUtils;
import org.bluemagic.config.util.UriUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ConfigXmlParser {
	
	private static final Log LOG = LogFactory.getLog(ConfigXmlParser.class);
	
	private LocationFactory locationFactory;
	
	private DecoratorFactory decoratorFactory;
	
	private TagFactory tagFactory;
	
	private TransformerFactory transformerFactory;

	
	public Collection<Location> buildLocations(Collection<Location> agentLocations) {
		
		for (Location location : agentLocations) {
			
			if (location instanceof FileLocation) {
				
				FileLocation local = (FileLocation) location;
				URI key = UriUtils.toUri(local.getFile());
				local.setRepository(new TextFileRepository());
				Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
				
				parameters.put(MagicKey.ORIGINAL_URI, key);
				Entry<URI,Object> property = local.locate(key, parameters);
				
				if ((property instanceof LocatedProperty) && (!property.getValue().toString().isEmpty())) {
					return parse(property.getValue().toString());
				}
			}
		}
		return null;
	}
	
	public Collection<Location> parse(String xmlAsString) {
		
		Document dom;
		List<Location> locations = new ArrayList<Location>();
		
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
		try {
	
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
	
			//parse using builder to get DOM representation of the XML file
			dom = db.parse(new ByteArrayInputStream(xmlAsString.getBytes("UTF-8")));
	
			Element docEle = dom.getDocumentElement();

			NodeList nl = docEle.getChildNodes();
			if(nl != null && nl.getLength() > 0) {
				
				for(int i = 0 ; i < nl.getLength();i++) {
					
					Node node = nl.item(i);
					String nodeName = node.getNodeName();
					
					if (!nodeName.startsWith("#")) {
						
						if ("data-transformers".equals(nodeName)) {
							//target.setparseDataTranformers();
						}
						if ("list".equals(nodeName)) {
							
							locations.addAll(parseList(node));
							//target.setConfigs(configList);
						}
					}
				}
			}
			LOG.trace("Parsing COMPLETE!");
	
		} catch(Throwable t) {
			String message = "Parse FAILURE!";
			LOG.fatal(message, t);
			throw new MagicConfigParserException(message, t);
		}
		return locations;
	}

	private Collection<Location> parseList(Node n) {

		Collection<Location> locations = new ArrayList<Location>();
		LOG.trace("Encountered XML: " + n.getNodeName());
		
		NodeList nl = n.getChildNodes();
		if(nl != null && nl.getLength() > 0) {
			
			for(int i = 0 ; i < nl.getLength();i++) {
				
				Node node = nl.item(i);
				String nodeName = node.getNodeName();
				
				if (!nodeName.startsWith("#")) {
					LOG.trace("Encountered XML: " + nodeName);
					locations.addAll(parseLocation(node));
				}
			}
		}
		return locations;
	}

	private Collection<Location> parseLocation(Node n) {
		
		Location rootLocation = null;
		Collection<Decorator> decorators = new ArrayList<Decorator>();
		Collection<Location> locations = new ArrayList<Location>();
		
		// REGULAR LOCATION
		String nodeName = n.getNodeName();
		
		if ("location".equals(nodeName)) {
			nodeName = "decoratingLocationWrapper";
		}
		String locationClassName = nodeName;
		rootLocation = locationFactory.build(locationClassName);
		
		NamedNodeMap attributes = n.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			String key = attributes.item(i).getNodeName();
			String value = attributes.item(i).getNodeValue();
			callSetterMethod(rootLocation, key, value);
		}
		
		if (rootLocation == null) {
			// BIG PROBLEM IF WE CANT GET A TAG
			throw new MagicConfigParserException("Could not find LOCATION class: " + locationClassName + "on classpath!");
		}
		
		NodeList nl = n.getChildNodes();
		if(nl != null && nl.getLength() > 0) {
			
			for(int i = 0 ; i < nl.getLength();i++) {
				
				Node node = nl.item(i);
				nodeName = node.getNodeName();
				
				if (!nodeName.startsWith("#")) {
					
					if ("list".equals(nodeName)) {
						Collection<Location> subLocations = parseList(node);
						
						for (Location l : subLocations) {
							
							if (l instanceof DecoratingLocationWrapper) {
								
								DecoratingLocationWrapper dlw = (DecoratingLocationWrapper) l;
								dlw.setDecorators(decorators);
								dlw.setInternal(rootLocation);
								
								locations.add(dlw);
							}
						}
					}
					if ("decorator".equals(nodeName)) {
						decorators.addAll(parseDecorator(node));
					}
					if (rootLocation instanceof DecoratingLocationWrapper) {
						
						DecoratingLocationWrapper dlw = (DecoratingLocationWrapper) rootLocation;
						dlw.setDecorators(decorators);
					}
				}
			}
		}
		locations.add(rootLocation);
		
		return locations;	
	}

	private Collection<Decorator> parseDecorator(Node n) {
		
		LOG.trace("Encountered XML: " + n.getNodeName());
		Collection<Decorator> decorators = new ArrayList<Decorator>();
		String method = n.getAttributes().getNamedItem("method").getNodeValue();
		
		NodeList nl = n.getChildNodes();
		if(nl != null && nl.getLength() > 0) {
			
			for(int i = 0 ; i < nl.getLength();i++) {
				
				Node node = nl.item(i);
				String nodeName = node.getNodeName();
				
				if (!nodeName.startsWith("#")) {
					decorators.add(parseTag(node, method));
				}
			}
		}
		return decorators;
	}

	private Decorator parseTag(final Node n, String method) {

		LOG.trace("Encountered XML: " + n.getNodeName());
		String className = n.getNodeName();
		
		Tag tag = tagFactory.build(className);
		
		if (tag == null) {
			// BIG PROBLEM IF WE CANT GET A TAG
			throw new MagicConfigParserException("Could not find TAG class: " + className + "on classpath!");
		}
		
		if ((tag instanceof SingleTag) || (tag instanceof DoubleTag) || (tag instanceof TripleTag)) {
			String textContent = n.getTextContent();
			if ((textContent != null) && (!textContent.isEmpty())) {
				callSetterMethod(tag, "value", textContent);
			}
		}
		NamedNodeMap attributes = n.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			String key = attributes.item(i).getNodeName();
			String value = attributes.item(i).getNodeValue();
			callSetterMethod(tag, key, value);
		}
		Decorator decorator = decoratorFactory.build(tag, method);
		
		if (decorator == null) {
			throw new MagicConfigParserException("Could not create a DECORATOR for TAG: " + tag.toString() + "!");
		}
		return decorator;
	}

	private void callSetterMethod(Object obj, String field, String value) {
		
		Method m = null;
		try {
			m = obj.getClass().getMethod("set" + StringUtils.capitalize(field), String.class);
			m.invoke(obj, value);
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

	public LocationFactory getLocationFactory() {
		return locationFactory;
	}

	public void setLocationFactory(LocationFactory locationFactory) {
		this.locationFactory = locationFactory;
	}

	public DecoratorFactory getDecoratorFactory() {
		return decoratorFactory;
	}

	public void setDecoratorFactory(DecoratorFactory decoratorFactory) {
		this.decoratorFactory = decoratorFactory;
	}

	public TagFactory getTagFactory() {
		return tagFactory;
	}

	public void setTagFactory(TagFactory tagFactory) {
		this.tagFactory = tagFactory;
	}

	public TransformerFactory getTransformerFactory() {
		return transformerFactory;
	}

	public void setTransformerFactory(TransformerFactory transformerFactory) {
		this.transformerFactory = transformerFactory;
	}
}

package org.bluemagic.config.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.Tag;
import org.bluemagic.config.decorator.tags.DoubleTag;
import org.bluemagic.config.decorator.tags.SingleTag;
import org.bluemagic.config.decorator.tags.TripleTag;
import org.bluemagic.config.location.ChildUriLocation;
import org.bluemagic.config.location.LocalLocation;
import org.bluemagic.config.location.UriLocation;
import org.bluemagic.config.util.StringUtils;
import org.bluemagic.config.util.UriUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ConfigXmlParser {
	
	private LocationFactory locationFactory;
	
	private DecoratorFactory decoratorFactory;
	
	private TagFactory tagFactory;
	
	private TransformerFactory transformerFactory;

	
	public Collection<Location> buildLocations(Collection<Location> agentLocations) {
		
		for (Location location : agentLocations) {
			
			if (location instanceof LocalLocation) {
				
				LocalLocation local = (LocalLocation) location;
				URI key = local.getUri();
				local.setUri(null);
				String xml = local.get(key, new HashMap<MagicKey, Object>());
				
				if ((xml != null) && (!xml.isEmpty())) {
					return parse(xml);
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
			System.out.println("COMPLETE!");
	
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return locations;
	}

	private Collection<Location> parseList(Node n) {

		Collection<Location> locations = new ArrayList<Location>();
		System.out.println(n.getNodeName());
		
		NodeList nl = n.getChildNodes();
		if(nl != null && nl.getLength() > 0) {
			
			for(int i = 0 ; i < nl.getLength();i++) {
				
				Node node = nl.item(i);
				String nodeName = node.getNodeName();
				
				if (!nodeName.startsWith("#")) {
					System.out.println(nodeName);
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
			nodeName = "childUriLocation";
		}
		String className = nodeName;
		if (!className.contains(".")) {
			className = LocationFactory.DEFAULT_LOCATION_PACKAGE_PREFIX + StringUtils.capitalize(className); 
		}
		rootLocation = locationFactory.build(className);
		
		if (rootLocation instanceof UriLocation) {
			
			UriLocation uriLocation = (UriLocation) rootLocation;
			String uriAsString = null;
			Node uriNode = n.getAttributes().getNamedItem("uri");
			
			if (uriNode != null) {
				uriAsString = uriNode.getNodeValue();
				URI uri = UriUtils.toUri(uriAsString);
				uriLocation.setUri(uri);
			}
			uriLocation.setDecorators(decorators);
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
							
							if (l instanceof ChildUriLocation) {
								ChildUriLocation cul = (ChildUriLocation) l;
								Location subLocation = null;
								
								if (cul.getUri() == null) {
									String uriAsString = null;
									Node uriNode = n.getAttributes().getNamedItem("uri");
									
									if (uriNode != null) {
										uriAsString = uriNode.getNodeValue();
									}
									subLocation = locationFactory.build(rootLocation.getClass().getName(), UriUtils.toUri(uriAsString), cul.getDecorators());
								}
								locations.add(subLocation);
							}
						}
					}
					if ("decorator".equals(nodeName)) {
						decorators.addAll(parseDecorator(node));
					}
				}
			}
		}
		locations.add(rootLocation);
		
		return locations;	
	}

	private Collection<Decorator> parseDecorator(Node n) {
		
		System.out.println(n.getNodeName());
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

		System.out.println(n.getNodeName());
		String className = n.getNodeName();
		if (!className.contains(".")) {
			className = TagFactory.DEFAULT_TAG_PACKAGE_PREFIX + StringUtils.capitalize(className); 
		}
		Tag tag = tagFactory.build(className);
		
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
		return decoratorFactory.build(tag, method);
	}

	private void callSetterMethod(Object obj, String field, String value) {
		
		Method m = null;
		try {
			m = obj.getClass().getDeclaredMethod("set" + StringUtils.capitalize(field), String.class);
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

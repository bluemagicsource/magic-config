package org.bluemagic.config.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
import org.bluemagic.config.location.ChildUriLocation;
import org.bluemagic.config.location.LocalLocation;
import org.bluemagic.config.location.UriLocation;
import org.bluemagic.config.util.StringUtils;
import org.bluemagic.config.util.UriUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
				return parse(xml);
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
		
		// CUSTOM LOCATION
		Node customLocationNode = n.getAttributes().getNamedItem("class");
		if (customLocationNode != null) {
			String className = customLocationNode.getNodeValue();
			rootLocation = locationFactory.build(className);
			
		} else {
			// REGULAR LOCATION
			String nodeName = n.getNodeName();
			
			if ("location".equals(nodeName)) {
				nodeName = "childUriLocation";
			}
			rootLocation = locationFactory.build(LocationFactory.DEFAULT_LOCATION_PACKAGE_PREFIX + StringUtils.capitalize(nodeName));
			
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
		}
		NodeList nl = n.getChildNodes();
		if(nl != null && nl.getLength() > 0) {
			
			for(int i = 0 ; i < nl.getLength();i++) {
				
				Node node = nl.item(i);
				String nodeName = node.getNodeName();
				
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
						Decorator decorator = parseDecorator(node);
						decorators.add(decorator);
					}
				}
			}
		}
		locations.add(rootLocation);
		
		return locations;	
	}

	private Decorator parseDecorator(Node n) {
		
		System.out.println(n.getNodeName());
		String method = n.getAttributes().getNamedItem("method").getNodeValue();
		
		NodeList nl = n.getChildNodes();
		if(nl != null && nl.getLength() > 0) {
			
			for(int i = 0 ; i < nl.getLength();i++) {
				
				Node node = nl.item(i);
				String nodeName = node.getNodeName();
				
				if (!nodeName.startsWith("#")) {
					return parseTag(node, method);
				}
			}
		}
		return null;
	}

	private Decorator parseTag(final Node n, String method) {

		System.out.println(n.getNodeName());
		
		Tag tag = tagFactory.build(TagFactory.DEFAULT_TAG_PACKAGE_PREFIX + StringUtils.capitalize(n.getNodeName()));
		return decoratorFactory.build(tag, method);
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

package org.bluemagic.config.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Location;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class LocationBuilder {
	
	public static void parse(String agentPropertiesUri) {
		
		Document dom;
		
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
		try {
	
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
	
			//parse using builder to get DOM representation of the XML file
			dom = db.parse(agentPropertiesUri);
	
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
						if ("location-chain".equals(nodeName)) {
							List<Location> locations = new ArrayList<Location>();
							
							locations.addAll(parseLocationChain(node));
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
	}

	private static List<Location> parseLocationChain(Node n) {

		List<Location> configList = new ArrayList<Location>();
		System.out.println(n.getNodeName());
		
		NodeList nl = n.getChildNodes();
		if(nl != null && nl.getLength() > 0) {
			
			for(int i = 0 ; i < nl.getLength();i++) {
				
				Node node = nl.item(i);
				String nodeName = node.getNodeName();
				
				if ((!nodeName.startsWith("#")) && ("location".equals(nodeName))) {
					System.out.println(nodeName);
					configList.addAll(parseLocation(node));
				}
			}
		}
		return configList;
	}

	private static List<Location> parseLocation(Node n) {
		
		List<Location> configList = new ArrayList<Location>();
		
		NodeList nl = n.getChildNodes();
		if(nl != null && nl.getLength() > 0) {
			
			for(int i = 0 ; i < nl.getLength();i++) {
				
				Node node = nl.item(i);
				String nodeName = node.getNodeName();
				
				if (!nodeName.startsWith("#")) {
					
					if ("location-chain".equals(nodeName)) {
						configList.addAll(parseLocationChain(node));
					}
					if ("decorate".equals(nodeName)) {
//						Decorator decorator = parseDecorator(node);
//						ConfigImpl config = new ConfigImpl();
//						config.
//						configList.add(config);
					}
				}
			}
		} else {

//			ConfigImpl config = new ConfigImpl();
//			String key = n.getAttributes().getNamedItem("key").getTextContent();
//			
//			FileClasspathDataProvider classpathDataProvider = null;
//			FileDataProvider fileDataProvider = null;
//			ResourceDataProvider resourceDataProvider = null;
//			
//			if (key.startsWith("classpath:") || key.startsWith("resource:")) {
//				classpathDataProvider = new FileClasspathDataProvider();
//				
//				if (key.startsWith("resource:")) {
//					classpathDataProvider.setClasspathUriAsString(key.replaceAll("resource:", "classpath:"));
//				} else {
//					classpathDataProvider.setClasspathUriAsString(key);
//				}
//				config.setDataProvider(classpathDataProvider);
//			}
//			if (key.startsWith("file:") || key.startsWith("resource:")) {
//				fileDataProvider = new FileDataProvider();
//				
//				if (key.startsWith("resource:")) {
//					fileDataProvider.setFileUriAsString(key.replaceAll("resource:", "file:"));
//				} else {
//					fileDataProvider.setFileUriAsString(key);
//				}
//				config.setDataProvider(fileDataProvider);
//			}
//			if (key.startsWith("resource:")) {
//				resourceDataProvider = new ResourceDataProvider();
//				resourceDataProvider.setClasspathDataProvider(classpathDataProvider);
//				resourceDataProvider.setFileDataProvider(fileDataProvider);
//				resourceDataProvider.setResourceUriAsString(key);
//				config.setDataProvider(resourceDataProvider);
//			}
//			configList.add(config);
		}
		return configList;	
	}

	private static Decorator parseDecorator(Node n) {
		
		System.out.println(n.getNodeName());
		
		NodeList nl = n.getChildNodes();
		List<Decorator> decorators = new ArrayList<Decorator>();
		if(nl != null && nl.getLength() > 0) {
			
			for(int i = 0 ; i < nl.getLength();i++) {
				
				Node node = nl.item(i);
				String nodeName = node.getNodeName();
				
				if (!nodeName.startsWith("#")) {
					
					if ("tag".equals(nodeName) || "hashtag".equals(nodeName)) {
						decorators.add(parseTag(node));
					}
				}
			}
		}
		if (decorators.size() == 1) {
			return decorators.get(0);
			
		} else if (decorators.size() > 1) {
			
//			CompoundDecorator cd = new CompoundDecorator();
//			cd.setCompoundDecorators(decorators);
//			return cd;
		}
		return null;
	}

	private static Decorator parseTag(final Node n) {

		System.out.println(n.getNodeName());
		
		if ("tag".equals(n.getNodeName())) {
			
			final String clazz = n.getAttributes().getNamedItem("class").getTextContent();

			return null;
			
		} else if ("hashtag".equals(n.getNodeName())) {
		
		}			
		return null;
	}

	public static Collection<Location> buildConfigLocations(Collection<Location> agentLocations) {
		// TODO Auto-generated method stub
		return null;
	}
}

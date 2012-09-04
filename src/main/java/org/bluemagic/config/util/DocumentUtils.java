package org.bluemagic.config.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A helper class to handle common Document processing
 */
public class DocumentUtils {

	/**
	 * No creating an instance of DocumentUtils
	 */
	private DocumentUtils() {}
	
	/**
	 * Create a Document object from a File
	 * 
	 * @param pathToDocument
	 * 			The path to a File that should be loaded
	 * 			into a Document
	 * 
	 * @return
	 * 			The Document that was created from the path.
	 * 			A RuntimeException gets thrown if a Document
	 * 			fails to be created.
	 */
	public static Document createDocument(String pathToDocument) {
		
		try {
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			
			return docBuilder.parse(pathToDocument);
		
		} catch (Exception e) {
			throw new RuntimeException("Unabled to create document from file: " + pathToDocument, e);
		}
	}
	
	/**
	 * Retrieve the attributes of an Element object as a Map.
	 * 
	 * @param element
	 * 			The element to get the attributes of
	 * 
	 * @return
	 * 			A Map<String, String> of the attributes that were
	 * 			contained in the Element.  If there were no
	 * 			attributes and empty Map is returned.
	 */
	public static Map<String, String> getAttributesMap(Node element) {
		
		Map<String, String> attributes = new HashMap<String, String>();
		
		NamedNodeMap attributeNNM = element.getAttributes();
		for (int i = 0; i < attributeNNM.getLength(); i++) {
			Node attribute = attributeNNM.item(i);
			attributes.put(attribute.getNodeName(), attribute.getNodeValue());
		}
		
		return attributes;
	}
	
	/**
	 * Get all of the element nodes from a NodeList
	 * 
	 * @param nodeList
	 * 			The NodeList to pull the Elements from
	 * 
	 * @return
	 * 			A List containing all of the Element objects
	 * 			that were contained in the NodeList.  If there
	 * 			were no Elements and empty List will be returned.
	 */
	public static List<Element> getElementList(NodeList nodeList) {
		
		List<Element> elements = new ArrayList<Element>();
		
		if (!(nodeList == null) && nodeList.getLength() > 0) {
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				Node node = nodeList.item(i);
				
				// Check if the node is an element and add it to the list
				if (Node.ELEMENT_NODE == node.getNodeType()) {
					elements.add((Element) node);
				}
			}
		}		
		
		return elements;
	}
}

package org.bluemagic.config.factory;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.tag.DoubleTag;
import org.bluemagic.config.api.tag.SingleTag;
import org.bluemagic.config.api.tag.TripleTag;
import org.bluemagic.config.tag.MethodDecoratedTag;
import org.bluemagic.config.util.StringUtils;

/**
 * The default BlueMagic implementation of a TagFactory.  This TagFactory
 * is setup to create Single, Double, Triple and Hostname tags.
 * 
 * To be able to create additional types of Tag objects, a new class
 * should extend BlueMagicTagFactory and follow the prototype pattern
 * used in this class and the method will be automatically called by
 * using reflection.  The subclass should maintain a private or
 * protected constructor and will need to override the getInstance method.
 * 
 * The method prototype should match the pattern:
 * public Tag createTagName(Map<String, String> attributes, String tagValue);
 */
public class TagFactoryImpl implements TagFactory {

	private static final Log LOG = LogFactory.getLog(TagFactoryImpl.class);
	private static final TagFactoryImpl INSTANCE = new TagFactoryImpl();
	
	protected TagFactoryImpl() {}
	
	public static TagFactory getInstance() {
		
		return INSTANCE;
	}
	
	public MethodDecoratedTag createTag(String tagType, Map<String, String> attributes, String tagValue) {
		
		MethodDecoratedTag tag = null;
		
		try {
			
			String methodName = "create" + StringUtils.capitalize(tagType);
			LOG.trace("Looking for method: " + methodName);
			
			Method method = this.getClass().getMethod(methodName, Map.class, String.class);
			
			if (LOG.isTraceEnabled()) {
				LOG.debug("Creating tag with method: " + method.toString());
			}
			
			tag = (MethodDecoratedTag) method.invoke(this, attributes, tagValue);
		
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException("Unable to find method", e);
		}		
		
		return tag;
	}
	
	public MethodDecoratedTag createHostnameTag(Map<String, String> attributes, String tagValue) {
		
//		return new HostnameTag();
		throw new RuntimeException("HostnameTag not implemented");
	}
	
	public MethodDecoratedTag createSingleTag(Map<String, String> attributes, String tagValue) {
		
		// Check if the tag value exists
		validateValueExists(tagValue);
		
		return new MethodDecoratedTag(new SingleTag(tagValue.trim()), 
				                      getMethod(attributes.get("method")));
	}
	
	public MethodDecoratedTag createDoubleTag(Map<String, String> attributes, String tagValue) {
		
		// Check if the tag value exists
		validateValueExists(tagValue);
		
		// Get the key from the attributes
		String key = attributes.get("key");
		
		// Validate the key exists
		validateValueExists(key);
		
		// Create and return the DoubleTag
		return new MethodDecoratedTag(new DoubleTag(key.trim(), tagValue.trim()), 
				                      getMethod(attributes.get("method")));
	}
	
	public MethodDecoratedTag createTripleTag(Map<String, String> attributes, String tagValue) {
		
		// Check if the tag value exists
		validateValueExists(tagValue);
		
		// Get the predicate from the attributes
		String predicate = attributes.get("predicate");
		
		// Validate the predicate exists
		validateValueExists(predicate);
		
		// Get the namespace from the attributes
		String namespace = attributes.get("namespace");
		
		// Validate the namespace exists
		validateValueExists(namespace);
		
		return new MethodDecoratedTag(new TripleTag(namespace.trim(), predicate.trim(), tagValue.trim()), 
				                      getMethod(attributes.get("method")));
	}
	
	/**
	 * Verify a String contains a value
	 * 
	 * @param value
	 * 			The String that is being validated
	 * 
	 * @throws RuntimeException 
	 * 			When the value is null or empty
	 */
	private static void validateValueExists(String value) {
		
		if (value == null || value.trim().isEmpty()) {
			throw new RuntimeException("Tag value cannot be null or empty");
		}
	}
	
	private static Decorator.Method getMethod(String method) {
		
		if (method == null) {
			
			throw new NullPointerException("Tag doesn't contain a method");
		}
		
		if ("PREFIX".equalsIgnoreCase(method)) {
			
			return Decorator.Method.PREFIX;
			
		} else if ("SUFFIX".equalsIgnoreCase(method)) {
			
			return Decorator.Method.SUFFIX;
			
		} else if ("PLACEHOLDER".equalsIgnoreCase(method)) {
			
			return Decorator.Method.PLACEHOLDER;
			
		} else {
			
			throw new RuntimeException("Unknown method type: " + method);
		}
	}
}

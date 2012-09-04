package org.bluemagic.config.factory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.tag.Tag;
import org.bluemagic.config.decorator.TagDecorator;
import org.bluemagic.config.util.StringUtils;

public class DecoratorFactoryImpl implements DecoratorFactory {

	private static final Log LOG = LogFactory.getLog(DecoratorFactoryImpl.class);
	private static final DecoratorFactoryImpl INSTANCE = new DecoratorFactoryImpl();
	
	protected DecoratorFactoryImpl() {}
	
	public static DecoratorFactory getInstance() {
		
		return INSTANCE;
	}
	
	@Override
	public Decorator createDecorator(String decoratorType, Map<String, String> attributes, Tag tag, Decorator.Method method) {
		
		Decorator decorator = null;
		
		try {
			
			String methodName = "create" + StringUtils.capitalize(decoratorType);
			LOG.trace("Looking for method: " + methodName);
			
			Method m = this.getClass().getMethod(methodName, Map.class, Tag.class, Decorator.Method.class);
			
			if (LOG.isTraceEnabled()) {
				LOG.debug("Creating decorator with method: " + m.toString());
			}
			
			decorator = (Decorator) m.invoke(this, attributes, tag, method);
		
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException("Unable to find method", e);
		}		
		
		return decorator;
	}
	
	public Decorator createKeyDecorator(Map<String, String> attributes, Tag tag, Decorator.Method method) {
		
		return generateTagDecorator(attributes, tag, method);
	}
	
	public Decorator createLocationDecorator(Map<String, String> attributes, Tag tag, Decorator.Method method) {
		
		return generateTagDecorator(attributes, tag, method);
	}
	
	public Decorator generateTagDecorator(Map<String, String> attributes, Tag tag, Decorator.Method method) {
		
//		LOG.error("Not implemented");
//		
//		// Create a TagDecorator
//		TagDecorator decorator = new TagDecorator();
//		
//		// Set the prefix separator, if there is one
//		String prefixSeparator = attributes.get("prefixSeparator");
//		if (prefixSeparator != null && !prefixSeparator.trim().isEmpty()) {
//			decorator.setPrefixSeparator(prefixSeparator);
//		}
//		
//		// Add the tags to the decorator
//		decorator.setTag(tag);
//		
//		return decorator;
		
		throw new RuntimeException("Not implemented");
	}
}

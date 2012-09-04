package org.bluemagic.config.factory;

import java.util.Map;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Decorator.Method;
import org.bluemagic.config.api.tag.Tag;

/**
 * A basic interface for factory that creates Decorator objects
 */
public interface DecoratorFactory {

	public Decorator createDecorator(String decoratorType, Map<String, String> attributes, Tag tag, Method method);
}

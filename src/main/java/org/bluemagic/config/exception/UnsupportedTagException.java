package org.bluemagic.config.exception;

import org.bluemagic.config.api.Tag;
import org.bluemagic.config.decorator.TagDecorator;

public class UnsupportedTagException extends RuntimeException {

	private static final long serialVersionUID = 7852234375791195353L;

	public UnsupportedTagException(Class<? extends TagDecorator> callingClass, Class<? extends Tag> expected, Class<?> found) {
		super(callingClass.getSimpleName() + " expects a tag of type: " + expected.getName() + ", NOT: " + found.getName() + "!");
	}

}
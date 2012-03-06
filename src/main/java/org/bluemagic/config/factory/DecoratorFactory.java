package org.bluemagic.config.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Decorator.Method;
import org.bluemagic.config.api.Tag;
import org.bluemagic.config.decorator.DoubleTagDecorator;
import org.bluemagic.config.decorator.SingleTagDecorator;
import org.bluemagic.config.decorator.TripleTagDecorator;
import org.bluemagic.config.decorator.tags.DoubleTag;
import org.bluemagic.config.decorator.tags.SingleTag;
import org.bluemagic.config.decorator.tags.TripleTag;

public class DecoratorFactory {
	
	private static final Log LOG = LogFactory.getLog(DecoratorFactory.class);

	public Decorator build(Tag tag, String method) {
		
		Decorator decorator = null;
		
		if (tag instanceof TripleTag) {
			TripleTagDecorator ttd = new TripleTagDecorator();
			ttd.setTag(tag);
			ttd.setMethod(getMethod(method));
			decorator = ttd;
			
		} else if (tag instanceof DoubleTag) {
			DoubleTagDecorator dtd = new DoubleTagDecorator();
			dtd.setTag(tag);
			dtd.setMethod(getMethod(method));
			decorator = dtd;
			
		} else if (tag instanceof SingleTag) {
			SingleTagDecorator std = new SingleTagDecorator();
			std.setTag(tag);
			std.setMethod(getMethod(method));
			decorator = std;
		}
		if (decorator != null) {
			LOG.debug("Created decorator: " + decorator.toString());
		}
		return decorator;
	}

	private Method getMethod(String method) {
		
		if ("prefix".equals(method.toLowerCase())) {
			return Method.PREFIX;
		}
		if ("suffix".equals(method.toLowerCase())) {
			return Method.SUFFIX;
		}
		if ("placeholder".equals(method.toLowerCase())) {
			return Method.PLACEHOLDER;
		}
		return Method.PLACEHOLDER;
	}
}

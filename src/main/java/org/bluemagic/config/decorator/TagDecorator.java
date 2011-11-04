package org.bluemagic.config.decorator;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.bluemagic.config.api.agent.ConfigKey;
import org.bluemagic.config.api.agent.Decorator;

public abstract class TagDecorator implements Decorator {
	
	protected String replace;
	
	private Method method;
	
	public Collection<URI> decorate(Collection<URI> uriList, Map<ConfigKey, Object> parameters) {
		
		Collection<URI> decoratedUris = new ArrayList<URI>();
		
		for (URI uri : uriList) {

			URI decoratedUri = null;
			
			if (method == Method.PREFIX) {
				
				decoratedUri = decoratePrefix(uri, parameters);
				
			} else if (method == Method.PLACEHOLDER) {
				
				decoratedUri = decoratePlaceholder(uri, replace, parameters);
				
			} else if (method == Method.SUFFIX) {
				
				decoratedUri = decorateSuffix(uri, parameters);
			}
			if (decoratedUri != null) {
				decoratedUris.add(decoratedUri);
			}
		}
		return decoratedUris;
	}

	public abstract URI decorateSuffix(URI uri, Map<ConfigKey, Object> parameters);

	public abstract URI decoratePlaceholder(URI uri, String replace, Map<ConfigKey, Object> parameters);

	public abstract URI decoratePrefix(URI uri, Map<ConfigKey, Object> parameters);

	public void setMethod(Method method) {
		this.method = method;
	}

	public Method getMethod() {
		return method;
	}

	public void setReplace(String replace) {
		this.replace = replace;
	}

	public String getReplace() {
		return replace;
	}
}

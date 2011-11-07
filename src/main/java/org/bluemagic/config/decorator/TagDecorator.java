package org.bluemagic.config.decorator;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.MagicKey;

public abstract class TagDecorator implements Decorator {
	
	protected String prefixSeperator = ".";
	
	protected String replace;
	
	private Method method;
	
	public Collection<URI> decorate(Collection<URI> keyList, Map<MagicKey, Object> parameters) {
		
		Collection<URI> decoratedUris = new ArrayList<URI>();
		
		for (URI uri : keyList) {

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

	public abstract URI decoratePrefix(URI key, Map<MagicKey, Object> parameters);

	public abstract URI decoratePlaceholder(URI key, String replace, Map<MagicKey, Object> parameters);

	public abstract URI decorateSuffix(URI key, Map<MagicKey, Object> parameters);
	
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

	public void setPrefixSeperator(String prefixSeperator) {
		this.prefixSeperator = prefixSeperator;
	}

	public String getPrefixSeperator() {
		return prefixSeperator;
	}
}

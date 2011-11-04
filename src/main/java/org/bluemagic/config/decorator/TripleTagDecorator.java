package org.bluemagic.config.decorator;

import java.net.URI;
import java.util.Map;
import java.util.TreeMap;

import org.bluemagic.config.api.agent.ConfigKey;
import org.bluemagic.config.decorator.tags.TripleTag;
import org.bluemagic.config.util.UriUtils;

public class TripleTagDecorator extends TagDecorator {
	
	private TripleTag tripleTag;

	public URI decoratePrefix(URI uri, Map<ConfigKey, Object> parameters) {
		
		URI decoratedUri = null;
		StringBuilder u = new StringBuilder();
		
		u.append(uri.getScheme());
		u.append(":");
		
		if (uri.toASCIIString().contains("//")) {					
			u.append("//");
		}
		u.append(tripleTag.getNamespace());
		u.append(":");
		u.append(tripleTag.getPredicate());
		u.append("=");
		u.append(tripleTag.getValue());
		u.append(".");
		
		if (uri.toASCIIString().contains("//")) {					
			u.append(uri.toASCIIString().replaceAll(uri.getScheme() + "://", ""));
		} else {
			u.append(uri.toASCIIString().replaceAll(uri.getScheme() + ":", ""));
		}
		try {
			decoratedUri = new URI(u.toString());
		} catch (Exception e) { }
		
		return decoratedUri;
	}

	public URI decoratePlaceholder(URI uri, String replace, Map<ConfigKey, Object> parameters) {
		
		URI decoratedUri = null;
		StringBuilder u = new StringBuilder();
		
		u.append(uri.toASCIIString().replaceAll("${" + replace + "}", tripleTag.getNamespace() + ":" + tripleTag.getPredicate() + "=" + tripleTag.getValue()));
		
		try {
			decoratedUri = new URI(u.toString());
		} catch (Exception e) { }
		
		return decoratedUri;
	}

	public URI decorateSuffix(URI uri, Map<ConfigKey, Object> parameters) {

		URI decoratedUri = null;
		StringBuilder u = new StringBuilder();
		TreeMap<String, String> orderedParameters = new TreeMap<String, String>();

		// The tree set naturally orders them and removes the
		// duplicates by the nature of the map and a tree map
		// automatically provides order.
		orderedParameters.putAll(UriUtils.parseUriParameters(uri));

		// Finally add the parameters back to a new URI.
		if (orderedParameters.size() > 0) {

			u = new StringBuilder(uri.toString().substring(0, uri.toString().indexOf('?')));
			u.append("?");

			orderedParameters.put(tripleTag.getNamespace() + "-" + tripleTag.getPredicate(), tripleTag.getValue()); 
			
			for (String key : orderedParameters.keySet()) {

			    if (!u.toString().endsWith("?")) {
			    	u.append("&");
			    }
		    	u.append(key);
		    	u.append("=");
		    	u.append(orderedParameters.get(key));
		    }
		} else if (uri.toString().endsWith("?")) {
			// Remove the question mark off of the end... (jerk)
			u = new StringBuilder(uri.toString().substring(0, uri.toString().indexOf('?')));
	    }
		try {
			decoratedUri = new URI(u.toString());
		} catch (Exception e) { }
		
		return decoratedUri;
	}

	public void setTripleTag(TripleTag tripleTag) {
		this.tripleTag = tripleTag;
	}

	public TripleTag getTripleTag() {
		return tripleTag;
	}
}

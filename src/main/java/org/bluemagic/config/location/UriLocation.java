package org.bluemagic.config.location;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.util.UriUtils;

public abstract class UriLocation implements Location {
	
	private Collection<Decorator> decorators;

	protected URI uri;
	
	public String locate(URI key, Map<MagicKey, Object> parameters) {
		
		String result = null;
		Collection<URI> keyList = new ArrayList<URI>();
		Collection<URI> decorated = new ArrayList<URI>();
		keyList.add(this.uri);
		
		for (Decorator decorator : decorators) {
			decorated.addAll(decorator.decorate(keyList, parameters));
		}
		if (decorated.size() == 0) {
			decorated.add(this.uri);
		}
		for (URI uri : decorated) {
			this.uri = uri;
			result = get(key, parameters);
			
			if (result != null) {
				break;
			}
		}
		return result;
	}
	
	public abstract String get(URI key, Map<MagicKey, Object> parameters);

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public URI getUri() {
		return uri;
	}

	public void setDecorators(Collection<Decorator> decorators) {
		this.decorators = decorators;
	}

	public Collection<Decorator> getDecorators() {
		return decorators;
	}
}

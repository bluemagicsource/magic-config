package org.bluemagic.config.location;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.MagicKey;

public abstract class UriLocation implements Location {
	
	private Collection<Decorator> decorators;

	protected URI uri;
	
	public String locate(URI key, Map<MagicKey, Object> parameters) {
		
		URI originalUri = this.uri;
		String result = null;
		Collection<URI> decorated = new ArrayList<URI>();
		decorated.add(this.uri);
		
		List<Decorator> reversedDecorators = new ArrayList<Decorator>(decorators);
		Collections.reverse(reversedDecorators);
		
		for (Decorator decorator : reversedDecorators) {
			decorated = decorator.decorate(decorated, parameters);
		}
		
		for (URI uri : decorated) {
			this.uri = uri;
			result = get(key, parameters);
			
			if (result != null) {
				break;
			}
		}
		this.uri = originalUri;
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

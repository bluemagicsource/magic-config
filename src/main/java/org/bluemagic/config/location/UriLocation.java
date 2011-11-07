package org.bluemagic.config.location;

import java.net.URI;
import java.util.Map;

import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.MagicKey;

public abstract class UriLocation implements Location {

	protected URI uri;
	
	public abstract String get(URI key, Map<MagicKey, Object> parameters);

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public URI getUri() {
		return uri;
	}
}

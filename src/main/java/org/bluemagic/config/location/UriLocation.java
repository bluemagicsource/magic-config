package org.bluemagic.config.location;

import java.net.URI;
import java.util.Map;

import org.bluemagic.config.api.agent.ConfigKey;
import org.bluemagic.config.api.agent.Location;

public abstract class UriLocation implements Location {

	protected URI uri;
	
	public abstract String get(URI uri, Map<ConfigKey, Object> parameters);

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public URI getUri() {
		return uri;
	}
}

package org.bluemagic.config.location.remote;

import java.net.URI;
import java.util.Map;

public class SessionizingRestClientManager implements RestClientManager {
	
	private RestClientManager internalRestClientManager;

	public String post(URI uri, Map<String, String> parameters) {
		throw new UnsupportedOperationException();
	}

	public String get(URI uri) {
		throw new UnsupportedOperationException();
	}

	public String update(URI uri, Map<String, String> parameters) {
		throw new UnsupportedOperationException();
	}

	public String delete(URI uri) {
		throw new UnsupportedOperationException();
	}

	public void setInternalRestClientManager(RestClientManager internalRestClientManager) {
		this.internalRestClientManager = internalRestClientManager;
	}

	public RestClientManager getInternalRestClientManager() {
		return internalRestClientManager;
	}
}

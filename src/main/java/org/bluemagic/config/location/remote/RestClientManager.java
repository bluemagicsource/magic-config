package org.bluemagic.config.location.remote;

import java.net.URI;
import java.util.Map;

public interface RestClientManager {

	public String post(URI uri, Map<String, String> parameters);
	
	public String get(URI uri);
	
	public String update(URI uri, Map<String, String> parameters);
	
	public String delete(URI uri);
}

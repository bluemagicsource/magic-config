package org.bluemagic.config.location;

import java.net.URI;
import java.util.Map;

import org.bluemagic.config.api.agent.ConfigKey;
import org.bluemagic.config.api.agent.Location;

public class DynamicLocation implements Location {

	private CommandlineLocation commandlineLocation;
	
	private LocalLocation localLocation;
	
	private RemoteLocation remoteLocation;

	public String get(URI uri, Map<ConfigKey, Object> parameters) {

		if (uri.toASCIIString().startsWith("cmd:")) {
			return commandlineLocation.get(uri, parameters);
			
		} else if (uri.toASCIIString().startsWith("http")) {
			return remoteLocation.get(uri, parameters);
			
		} else {
			return getLocalLocation().get(uri, parameters);
		}
	}

	public void setRemoteLocation(RemoteLocation remoteLocation) {
		this.remoteLocation = remoteLocation;
	}


	public RemoteLocation getRemoteLocation() {
		return remoteLocation;
	}

	public void setLocalLocation(LocalLocation localLocation) {
		this.localLocation = localLocation;
	}

	public LocalLocation getLocalLocation() {
		return localLocation;
	}
}

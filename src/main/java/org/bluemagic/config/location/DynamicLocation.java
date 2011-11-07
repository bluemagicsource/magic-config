package org.bluemagic.config.location;

import java.net.URI;
import java.util.Map;

import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.MagicKey;

public class DynamicLocation implements Location {

	private CommandLineLocation commandlineLocation;
	
	private LocalLocation localLocation;
	
	private RemoteLocation remoteLocation;

	public String get(URI uri, Map<MagicKey, Object> parameters) {

		if (uri.toASCIIString().startsWith("cmd:")) {
			return getCommandlineLocation().get(uri, parameters);
			
		} else if (uri.toASCIIString().startsWith("http")) {
			return remoteLocation.get(uri, parameters);
			
		} else {
			return getLocalLocation().get(uri, parameters);
		}
	}
	
	public boolean supports(URI uri) {
		
		return getCommandlineLocation().supports(uri) || localLocation.supports(uri) || remoteLocation.supports(uri);
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

	public void setCommandlineLocation(CommandLineLocation commandlineLocation) {
		this.commandlineLocation = commandlineLocation;
	}

	public CommandLineLocation getCommandlineLocation() {
		return commandlineLocation;
	}
}

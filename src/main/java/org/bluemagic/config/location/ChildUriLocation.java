package org.bluemagic.config.location;

import java.net.URI;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;

public class ChildUriLocation extends UriLocation {

	public boolean supports(URI key) {
		return true;
	}

	public String get(URI key, Map<MagicKey, Object> parameters) {
		return null;
	}
}

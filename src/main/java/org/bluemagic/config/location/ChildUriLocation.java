package org.bluemagic.config.location;

import java.net.URI;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.property.MagicProperty;

public class ChildUriLocation extends UriLocation {

	public boolean supports(URI key) {
		return true;
	}

	public MagicProperty locateHelper(URI key, Map<MagicKey, Object> parameters) {
		return null;
	}
}

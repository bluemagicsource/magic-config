package org.bluemagic.config.location;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.property.LocatedProperty;

public class KeyDecoratingLocationWrapper extends DecoratingLocationWrapper {

	public Entry<URI,Object> locate(URI key, Map<MagicKey, Object> parameters) {
		
		Entry<URI,Object> property = null;
		Collection<URI> decoratedList = decorateUri(key, parameters);
		
		// LOOP THRU DECORATED URIs AND LOCATE THEM
		for (URI decorated : decoratedList) {
			property = internal.locate(decorated, parameters);
			
			if (property instanceof LocatedProperty) {
				// THIS MEANS WE HAD A HIT SO BREAK RETURN IT
				break;
			}
		}
		return property;
	}
}

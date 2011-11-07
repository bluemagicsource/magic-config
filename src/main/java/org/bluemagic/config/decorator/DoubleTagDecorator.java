package org.bluemagic.config.decorator;

import java.net.URI;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.decorator.tags.DoubleTag;
import org.bluemagic.config.util.UriUtils;

public class DoubleTagDecorator extends TagDecorator {
	
	private DoubleTag doubleTag;

	public URI decoratePrefix(URI key, Map<MagicKey, Object> parameters) {
		
		StringBuilder u = new StringBuilder();
		String[] split = UriUtils.splitUriAfterScheme(key);
		
		u.append(split[0]);
		u.append(doubleTag.getKey());
		u.append("=");
		u.append(doubleTag.getValue());
		u.append(prefixSeperator);
		u.append(split[1]);
		
		return UriUtils.toUri(u.toString());
	}

	public URI decoratePlaceholder(URI key, String replace, Map<MagicKey, Object> parameters) {
		
		String uriAsString = "";
		
		uriAsString = key.toASCIIString().replace(replace, doubleTag.getKey() + "=" + doubleTag.getValue());
		
		return UriUtils.toUri(uriAsString);
	}

	public URI decorateSuffix(URI key, Map<MagicKey, Object> parameters) {
		
		// ADD PARAMETER TO THE URI
		return UriUtils.addParameterToUri(key, doubleTag.getKey(), doubleTag.getValue());
	}

	public void setDoubleTag(DoubleTag doubleTag) {
		this.doubleTag = doubleTag;
	}

	public DoubleTag getDoubleTag() {
		return doubleTag;
	}
}

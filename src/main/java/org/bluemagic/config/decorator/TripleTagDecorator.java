package org.bluemagic.config.decorator;

import java.net.URI;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.decorator.tags.TripleTag;
import org.bluemagic.config.util.UriUtils;

public class TripleTagDecorator extends TagDecorator {
	
	private TripleTag tripleTag;

	public URI decoratePrefix(URI key, Map<MagicKey, Object> parameters) {
		
		StringBuilder u = new StringBuilder();
		String[] split = UriUtils.splitUriAfterScheme(key);
		
		u.append(split[0]);
		u.append(tripleTag.getNamespace());
		u.append(":");
		u.append(tripleTag.getPredicate());
		u.append("=");
		u.append(tripleTag.getValue());
		u.append(prefixSeperator);
		u.append(split[1]);
		
		return UriUtils.toUri(u.toString());
	}

	public URI decoratePlaceholder(URI key, String replace, Map<MagicKey, Object> parameters) {
		
		String uriAsString = "";
		
		uriAsString = key.toASCIIString().replace(replace, tripleTag.getNamespace() + ":" + tripleTag.getPredicate() + "=" + tripleTag.getValue());
		
		return UriUtils.toUri(uriAsString);
	}
	
	public URI decorateSuffix(URI key, Map<MagicKey, Object> parameters) {
		
		// ADD PARAMETER TO THE URI
		return UriUtils.addParameterToUri(key, tripleTag.getNamespace() + ":" + tripleTag.getPredicate(), tripleTag.getValue());
	}

	public void setTripleTag(TripleTag tripleTag) {
		this.tripleTag = tripleTag;
	}

	public TripleTag getTripleTag() {
		return tripleTag;
	}
}

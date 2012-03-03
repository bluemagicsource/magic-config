package org.bluemagic.config.decorator;

import java.net.URI;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.Tag;
import org.bluemagic.config.decorator.tags.SingleTag;
import org.bluemagic.config.decorator.tags.TripleTag;
import org.bluemagic.config.util.UnsupportedTagException;
import org.bluemagic.config.util.UriUtils;

public class TripleTagDecorator extends TagDecorator {
	
	public URI decorateSuffix(URI key, Map<MagicKey, Object> parameters) {
		
		TripleTag tripleTag = (TripleTag) getTag();
		
		// ADD PARAMETER TO THE URI
		return UriUtils.addParameterToUri(key, tripleTag.getNamespace() + tripleTag.getNameSpacePredicateSeparator() + tripleTag.getPredicate(), tripleTag.getValue());
	}
	
	public boolean supports(Tag tag) {
		
		boolean supports = false;
		
		// CHECK TYPE OF TAG
		if (tag instanceof SingleTag) {
			supports = true;
		}
		return supports;
	}
	
	@Override
	public void setTag(Tag tag) {
		
		if (!supports(tag)) {
			throw new UnsupportedTagException(this.getClass(), TripleTag.class, tag.getClass());
		}
		super.setTag(tag);
	}
}

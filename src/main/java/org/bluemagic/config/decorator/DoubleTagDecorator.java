package org.bluemagic.config.decorator;

import java.net.URI;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.tag.DoubleTag;
import org.bluemagic.config.api.tag.Tag;
import org.bluemagic.config.exception.UnsupportedTagException;
import org.bluemagic.config.tag.TagEncoder;
import org.bluemagic.config.util.UriUtils;

public class DoubleTagDecorator extends TagDecorator {

	public URI decorateSuffix(URI key, Map<MagicKey, Object> parameters) {
		
		DoubleTag doubleTag = (DoubleTag) getTag();
		
		// ADD PARAMETER TO THE URI
		return UriUtils.addParameterToUri(key, TagEncoder.encodeString(doubleTag.getKey(), getEncoding()), TagEncoder.encodeString(doubleTag.getValue(), getEncoding()));
	}
	
	public boolean supports(Tag tag) {
		
		boolean supports = false;
		
		// CHECK TYPE OF TAG
		if (tag instanceof DoubleTag) {
			supports = true;
		}
		return supports;
	}
	
	@Override
	public void setTag(Tag tag) {
		
		if (!supports(tag)) {
			throw new UnsupportedTagException(this.getClass(), DoubleTag.class, tag.getClass());
		}
		super.setTag(tag);
	}
}

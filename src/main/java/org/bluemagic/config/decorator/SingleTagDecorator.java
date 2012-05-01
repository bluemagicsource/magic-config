package org.bluemagic.config.decorator;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.tag.SingleTag;
import org.bluemagic.config.api.tag.Tag;
import org.bluemagic.config.exception.UnsupportedTagException;
import org.bluemagic.config.util.UriUtils;

public class SingleTagDecorator extends TagDecorator {

	private String tagParameterKey = "tags";

	private String suffixSeperator = ",";
	
	public URI decorateSuffix(URI key, Map<MagicKey, Object> parameters) {

		Map<String, String> keyParameters = new HashMap<String, String>();

		// PARSE ALL THE URI PARAMETERS
		keyParameters.putAll(UriUtils.parseUriParameters(key));
		
		// GRAB ANY EXISTING SINGLE TAGS
		String tagsValue = keyParameters.get(tagParameterKey);
		
		if ((tagsValue != null) && (tagsValue.length() > 0)) {
			
			// COMBINE THE CURRENT TAG WITH THE OTHERS
			tagsValue = combineAndSortTags(this.getTag().toString(), tagsValue, suffixSeperator);
			
		} else {
			// THE ONLY SINGLETAG IS THE NEW ONE
			tagsValue = getTag().toString();
		}
		// COLLAPSE ADDING THE SINGLE TAGS WITH TAG PARAMETER KEY
		return UriUtils.addParameterToUri(key, tagParameterKey, tagsValue);
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
			throw new UnsupportedTagException(this.getClass(), SingleTag.class, tag.getClass());
		}
		super.setTag(tag);
	}

	public String getTagParameterKey() {
		return tagParameterKey;
	}

	public void setTagParameterKey(String tagParameterKey) {
		this.tagParameterKey = tagParameterKey;
	}

	public String getSuffixSeperator() {
		return suffixSeperator;
	}

	public void setSuffixSeperator(String suffixSeperator) {
		this.suffixSeperator = suffixSeperator;
	}
}

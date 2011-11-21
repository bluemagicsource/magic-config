package org.bluemagic.config.decorator;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.Tag;
import org.bluemagic.config.util.UriUtils;

public class SingleTagDecorator extends TagDecorator {

	public static String tagParameterKey = "tags";
	
	private Tag tag;

	public URI decoratePrefix(URI key, Map<MagicKey, Object> parameters) {
		
		StringBuilder u = new StringBuilder();
		String[] split = UriUtils.splitUriAfterScheme(key);
		
		u.append(split[0]);
		u.append(tag.getValue());
		u.append(prefixSeperator);
		u.append(split[1]);
		
		return UriUtils.toUri(u.toString());
	}
	
	public URI decoratePlaceholder(URI key, String replace, Map<MagicKey, Object> parameters) {
		
		String uriAsString = "";
		
		uriAsString = key.toASCIIString().replace(replace, tag.getValue());
		
		return UriUtils.toUri(uriAsString);
	}

	public URI decorateSuffix(URI key, Map<MagicKey, Object> parameters) {

		Map<String, String> keyParameters = new HashMap<String, String>();

		keyParameters.putAll(UriUtils.parseUriParameters(key));
		
		String tagsValue = keyParameters.get(tagParameterKey);
		if ((tagsValue != null) && (tagsValue.length() > 0)) {
			tagsValue = tagsValue + "," + tag.getValue();
		} else {
			tagsValue = tag.getValue();
		}
		return UriUtils.addParameterToUri(key, tagParameterKey, tagsValue);
	}

	public void setTagParameterKey(String tagParameterKey) {
		SingleTagDecorator.tagParameterKey = tagParameterKey;
	}

	public String getTagParameterKey() {
		return tagParameterKey;
	}
	
	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
}

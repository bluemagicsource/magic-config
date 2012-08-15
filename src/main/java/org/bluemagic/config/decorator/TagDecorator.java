package org.bluemagic.config.decorator;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.tag.Tag;
import org.bluemagic.config.tag.TagEncoder;
import org.bluemagic.config.util.UriUtils;

public abstract class TagDecorator implements Decorator {
	
	private String prefixSeparator = ".";
	
	private String placeholderSeparator = "-";
	
	private String placeholder = "?";
	
	private Method method;
	
	private String encoding;
	
	private Tag tag;
	
	
	public Collection<URI> decorate(Collection<URI> keyList, Map<MagicKey, Object> parameters) {
		
		Collection<URI> decoratedUris = new ArrayList<URI>();
		
		for (URI uri : keyList) {

			URI decoratedUri = null;
			
			if (method == Method.PREFIX) {
				
				decoratedUri = decoratePrefix(uri, parameters);
				
			} else if (method == Method.PLACEHOLDER) {
				
				decoratedUri = decoratePlaceholder(uri, placeholder, parameters);
				
			} else if (method == Method.SUFFIX) {
				
				decoratedUri = decorateSuffix(uri, parameters);
			}
			if (decoratedUri != null) {
				decoratedUris.add(decoratedUri);
			}
		}
		return decoratedUris;
	}

	public URI decoratePrefix(URI key, Map<MagicKey, Object> parameters) {
		
		StringBuilder u = new StringBuilder();
		
		// SPLITS URI INTO PARTS
		String[] split = UriUtils.splitUriIntoPrefixParts(key, (URI) parameters.get(MagicKey.ORIGINAL_URI), prefixSeparator);
		
		// THIS IS THE SCHEME
		u.append(split[0]);
		
		// THESE ARE THE TAGS
		u.append(combineTagsAsPrefix(TagEncoder.encode(tag, encoding), split[1], prefixSeparator));
		
		// ADD SEPARATOR 
		u.append(prefixSeparator);
		
		// THIS IS THE REST OF THE URI
		u.append(split[2]);
		
		// CREATE URI FROM THE ABOVE
		return UriUtils.toUri(u.toString());
	}
	
	public URI decoratePlaceholder(URI key, String replace, Map<MagicKey, Object> parameters) {
		
		StringBuilder b = new StringBuilder();
		String[] split = UriUtils.splitUriIntoPlaceholderParts(key, (URI) parameters.get(MagicKey.ORIGINAL_URI), replace);
		
		// THIS IS THE SCHEME
		b.append(split[0]);
		
		// THESE ARE THE TAGS
		b.append(combineAndSortTags(tag.toString(), split[1], placeholderSeparator));
		
		// THIS IS THE REST OF THE URI
		b.append(split[2]);
		
		// CREATE URI FROM THE ABOVE
		return UriUtils.toUri(b.toString());
	}
	
	public String combineAndSortTags(String addMe, String tagsValue, String separator) {
		
		StringBuilder b = new StringBuilder();
		List<String> asList = new ArrayList<String>();
		
		if (tagsValue.contains(separator)) {
			asList = Arrays.asList(tagsValue.split(separator));
		} else {
			asList.add(tagsValue);
		}
		
		Set<String> uniqueTags = new TreeSet<String>(asList);
		uniqueTags.remove("");
		
		// ADD THE NEW TAG
		uniqueTags.add(addMe);
		
		// ITERATE OVER THE TAGS CREATING THE VALUE
		Iterator<String> tagIterator = uniqueTags.iterator();
		
		while (tagIterator.hasNext()) {
			
			// APPEND THE TAG VALUE
			b.append(tagIterator.next());
			
			// CHECK TO SEE IF WE NEED TO ADD A SEPARATOR
			if (tagIterator.hasNext()) {
				b.append(separator);
			}
		}
		// RETURN THE WHOLE VALUE
		return b.toString();
	}
	
	public String combineTagsAsPrefix(String addMe, String tagsValue, String separator) {
		
		StringBuilder b = new StringBuilder();
		List<String> allTags = new ArrayList<String>();
		
		if (tagsValue.contains(separator)) {
			
			String splitPrefix = "";
			
			if (".".equals(separator)) {
				// SPECIAL CASE SO WE CAN ESCAPE THE PERIOD BECAUSE IT IS A REGEX
				splitPrefix = "\\";
			}
			allTags.addAll(Arrays.asList(tagsValue.split(splitPrefix + separator)));
			
		} else {
			allTags.add(tagsValue);
		}
		
		// ADD THE NEW TAG
		allTags.add(addMe);
		
		allTags.remove("");
		
		// ITERATE OVER THE TAGS CREATING THE VALUE
		for (int count = 0; count < allTags.size(); count++) {
		
			// APPEND THE TAG VALUE
			b.append(allTags.get(count));
			
			// CHECK TO SEE IF WE NEED TO ADD A SEPARATOR
			if (count < (allTags.size() - 1)) {
				b.append(separator);
			}
		}
		// RETURN THE WHOLE VALUE
		return b.toString();
	}
	
	@Override
	public String toString() {
		
		StringBuilder b = new StringBuilder();
		
		b.append(this.getClass().getName());
		b.append(" with TAG: ");
		b.append(tag.toString());
		
		return b.toString();
	}

	public abstract URI decorateSuffix(URI key, Map<MagicKey, Object> parameters);
	
	public abstract boolean supports(Tag tag);
	
	public String getPrefixSeparator() {
		return prefixSeparator;
	}

	public void setPrefixSeparator(String prefixSeparator) {
		this.prefixSeparator = prefixSeparator;
	}

	public String getPlaceholderSeparator() {
		return placeholderSeparator;
	}

	public void setPlaceholderSeparator(String placeholderSeparator) {
		this.placeholderSeparator = placeholderSeparator;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}

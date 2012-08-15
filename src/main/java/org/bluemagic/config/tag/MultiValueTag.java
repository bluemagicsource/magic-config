package org.bluemagic.config.tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.bluemagic.config.api.tag.Tag;

public class MultiValueTag implements Tag {

	private Collection<String> values = new ArrayList<String>();
	
	private String separator = ":";
	
	public MultiValueTag() { }
	
	public MultiValueTag(String ... strings) { 
		values.addAll(Arrays.asList(strings));
	}
	
	@Override
	public String toString() {
		
		String value = null;
		StringBuilder b = new StringBuilder();
		
		Iterator<String> iter = values.iterator();
		
		while (iter.hasNext()) {

			value = iter.next();
			b.append(value);
			
			if (iter.hasNext()) {
				b.append(separator);
			}
		}
		return b.toString();
	}
}

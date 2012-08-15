package org.bluemagic.config.tag;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.bluemagic.config.api.tag.DoubleTag;
import org.bluemagic.config.api.tag.SingleTag;
import org.bluemagic.config.api.tag.Tag;
import org.bluemagic.config.api.tag.TripleTag;

public class TagEncoder {
	
	public static String FILE_ENCODING = "file";
	
	public static String URL_ENCODING = "url";

	public static String encode(Tag tag, String enc) {
		
		String encoded = "";
		
		if (tag instanceof TripleTag) {
			encoded = encodeTriple((TripleTag) tag, enc);
			
		} else if (tag instanceof DoubleTag) {
			encoded = encodeDouble((DoubleTag) tag, enc);
			
		} else if (tag instanceof SingleTag) {
			encoded = encodeSingle((SingleTag) tag, enc);
		}
		return encoded;
	}
	
	public static String encodeString(String unencoded, String enc) {

		String encoded = "";
		
		if (enc == FILE_ENCODING) {
			
			String property = System.getProperty("os.name");
			if (property.toLowerCase().startsWith("linux") || property.toLowerCase().startsWith("mac")) {
				encoded = unencoded.replaceAll("/", "");
				
			} else {
				encoded = encoded.replaceAll(":", "");
				encoded = encoded.replaceAll("\\*", "");
				encoded = encoded.replaceAll("\\\"", "");
				encoded = encoded.replaceAll("<", "");
				encoded = encoded.replaceAll(">", "");
				encoded = encoded.replaceAll("|", "");
				encoded = encoded.replaceAll("\\\\", "");
				encoded = encoded.replaceAll("\\?", "");
			}
		} else if (enc == URL_ENCODING) {
			try {
				encoded = URLEncoder.encode(unencoded, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		} else {
			encoded = unencoded;
		}
		return encoded;
	}

	private static String encodeSingle(SingleTag tag, String enc) {
		return encodeString(tag.getValue(), enc);
	}

	private static String encodeDouble(DoubleTag tag, String enc) {
		
		StringBuilder b = new StringBuilder();
		
		b.append(encodeString(tag.getKey(), enc));
		b.append(tag.getKeyValueSeparator());
		b.append(encodeString(tag.getValue(), enc));
		
		return b.toString();
	}

	private static String encodeTriple(TripleTag tag, String enc) {
			
		StringBuilder b = new StringBuilder();
		b.append(encodeString(tag.getNamespace(), enc));
		b.append(tag.getNameSpacePredicateSeparator());
		b.append(encodeString(tag.getPredicate(), enc));
		b.append(tag.getPredicateValueSeparator());
		b.append(encodeString(tag.getValue(), enc));

		return b.toString();
	}
}

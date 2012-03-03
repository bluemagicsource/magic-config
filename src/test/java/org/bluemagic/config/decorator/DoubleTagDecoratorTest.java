package org.bluemagic.config.decorator;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.decorator.tags.DoubleTag;
import org.bluemagic.config.util.UriUtils;
import org.junit.Test;

public class DoubleTagDecoratorTest {

	@Test
	public void simpleDecoratePrefix() {
		
		DoubleTag doubleTag = new DoubleTag();
		doubleTag.setKey("k1");
		doubleTag.setValue("v1");
		
		DoubleTagDecorator dtd = new DoubleTagDecorator();
		dtd.setTag(doubleTag);
		
		URI key = UriUtils.toUri("abc");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("k1" + doubleTag.getKeyValueSeparator() +  "v1.abc", dtd.decoratePrefix(key, parameters).toASCIIString());
	}
	
	@Test
	public void simpleDecoratePlaceholder() {
		
		DoubleTag doubleTag = new DoubleTag();
		doubleTag.setKey("k1");
		doubleTag.setValue("v1");
		
		DoubleTagDecorator dtd = new DoubleTagDecorator();
		dtd.setTag(doubleTag);
		
		URI key = UriUtils.toUri("abc/?/jackster");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("abc/k1" + doubleTag.getKeyValueSeparator() + "v1/jackster", dtd.decoratePlaceholder(key, "?", parameters).toASCIIString());
	}
	
	@Test
	public void complexDecoratePlaceholder() {
		
		DoubleTag doubleTag = new DoubleTag();
		doubleTag.setKey("k1");
		doubleTag.setValue("v1");
		
		DoubleTagDecorator dtd = new DoubleTagDecorator();
		dtd.setTag(doubleTag);
		
		URI key = UriUtils.toUri("abc/zeta=123" + dtd.getPlaceholderSeparator() + "charmer=567/jackster");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		parameters.put(MagicKey.ORIGINAL_URI, UriUtils.toUri("abc/?/jackster"));
		
		assertEquals("abc/charmer=567-k1=v1-zeta=123/jackster", dtd.decoratePlaceholder(key, "?", parameters).toASCIIString());
	}
	
	@Test
	public void complexDecoratePlaceholder2() {
		
		DoubleTag doubleTag = new DoubleTag();
		doubleTag.setKey("k1");
		doubleTag.setValue("v1");
		doubleTag.setKeyValueSeparator("-");
		
		DoubleTagDecorator dtd = new DoubleTagDecorator();
		dtd.setPlaceholderSeparator("/");
		dtd.setTag(doubleTag);
		
		URI key = UriUtils.toUri("abc/zeta-123" + dtd.getPlaceholderSeparator() + "charmer-567/jackster");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		parameters.put(MagicKey.ORIGINAL_URI, UriUtils.toUri("abc/?/jackster"));
		
		assertEquals("abc/charmer-567/k1-v1/zeta-123/jackster", dtd.decoratePlaceholder(key, "?", parameters).toASCIIString());
	}
	
	@Test
	public void simpleDecorateSuffix() {
		
		DoubleTag doubleTag = new DoubleTag();
		doubleTag.setKey("k1");
		doubleTag.setValue("v1");
		
		DoubleTagDecorator dtd = new DoubleTagDecorator();
		dtd.setTag(doubleTag);
		
		URI key = UriUtils.toUri("abc");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("abc?k1" + doubleTag.getKeyValueSeparator() + "v1", dtd.decorateSuffix(key, parameters).toASCIIString());
	}
}

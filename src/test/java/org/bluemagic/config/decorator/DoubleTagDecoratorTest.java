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
		dtd.setDoubleTag(doubleTag);
		
		URI key = UriUtils.toUri("abc");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("k1=v1.abc", dtd.decoratePrefix(key, parameters).toASCIIString());
	}
	
	@Test
	public void simpleDecoratePlaceholder() {
		
		DoubleTag doubleTag = new DoubleTag();
		doubleTag.setKey("k1");
		doubleTag.setValue("v1");
		
		DoubleTagDecorator dtd = new DoubleTagDecorator();
		dtd.setDoubleTag(doubleTag);
		
		URI key = UriUtils.toUri("abc/?/jackster");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("abc/k1=v1/jackster", dtd.decoratePlaceholder(key, "?", parameters).toASCIIString());
	}
	
	@Test
	public void simpleDecorateSuffix() {
		
		DoubleTag doubleTag = new DoubleTag();
		doubleTag.setKey("k1");
		doubleTag.setValue("v1");
		
		DoubleTagDecorator dtd = new DoubleTagDecorator();
		dtd.setDoubleTag(doubleTag);
		
		URI key = UriUtils.toUri("abc");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("abc?k1=v1", dtd.decorateSuffix(key, parameters).toASCIIString());
	}
}

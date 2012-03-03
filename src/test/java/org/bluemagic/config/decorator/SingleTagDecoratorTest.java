package org.bluemagic.config.decorator;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.decorator.tags.SingleTag;
import org.bluemagic.config.util.UriUtils;
import org.junit.Test;

public class SingleTagDecoratorTest {
	
	@Test
	public void simpleDecoratePrefix() {
		
		SingleTag singleTag = new SingleTag();
		singleTag.setValue("myTag");
		
		SingleTagDecorator std = new SingleTagDecorator();
		std.setTag(singleTag);
		
		URI key = UriUtils.toUri("abc");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("myTag.abc", std.decoratePrefix(key, parameters).toASCIIString());
	}
	
	@Test
	public void simpleDecoratePrefix2() {
		
		SingleTag singleTag = new SingleTag();
		singleTag.setValue("myTag");
		
		SingleTagDecorator std = new SingleTagDecorator();
		std.setTag(singleTag);
		
		URI key = UriUtils.toUri("abc");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("myTag.abc", std.decoratePrefix(key, parameters).toASCIIString());
	}
	
	@Test
	public void simpleDecoratePlaceholder() {
		
		SingleTag singleTag = new SingleTag();
		singleTag.setValue("myTag");
		
		SingleTagDecorator std = new SingleTagDecorator();
		std.setTag(singleTag);
		
		URI key = UriUtils.toUri("abc/?/jackster");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("abc/myTag/jackster", std.decoratePlaceholder(key, "?", parameters).toASCIIString());
	}
	
	@Test
	public void complexDecoratePlaceholder() {
		
		SingleTag singleTag = new SingleTag();
		singleTag.setValue("myTag");
		
		SingleTagDecorator std = new SingleTagDecorator();
		std.setTag(singleTag);
		
		URI key = UriUtils.toUri("abc/zeta" + std.getPlaceholderSeparator() + "charmer/jackster");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		parameters.put(MagicKey.ORIGINAL_URI, UriUtils.toUri("abc/" + std.getPlaceholder() + "/jackster"));
		
		assertEquals("abc/charmer-myTag-zeta/jackster", std.decoratePlaceholder(key, "?", parameters).toASCIIString());
	}
	
	@Test
	public void simpleDecorateSuffix() {
		
		SingleTag singleTag = new SingleTag();
		singleTag.setValue("myTag");
		
		SingleTagDecorator std = new SingleTagDecorator();
		std.setTag(singleTag);
		
		URI key = UriUtils.toUri("abc");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("abc?tags=myTag", std.decorateSuffix(key, parameters).toASCIIString());
	}
	
	@Test
	public void simpleDecorateSuffixMultipleTags() {
		
		SingleTag singleTag = new SingleTag();
		singleTag.setValue("myTag");
		
		SingleTagDecorator std = new SingleTagDecorator();
		std.setTag(singleTag);
		
		URI key = UriUtils.toUri("abc?" + std.getTagParameterKey() + "=alert" + std.getSuffixSeperator() + "boston");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("abc?" + std.getTagParameterKey() + "=alert" + std.getSuffixSeperator() + "boston"+ std.getSuffixSeperator() + "myTag", std.decorateSuffix(key, parameters).toASCIIString());
	}
}

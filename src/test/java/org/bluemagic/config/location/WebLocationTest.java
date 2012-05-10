package org.bluemagic.config.location;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.util.UriUtils;
import org.junit.Assert;
import org.junit.Test;

public class WebLocationTest {

	@Test
	public void testGetRemoteLocationPropertyWithFileSet() {
		
		System.out.println("--testGetRemoteLocationPropertyWithFileSet--");
		WebLocation webLocation = new WebLocation();
		webLocation.setFile("https://raw.github.com/bluemagicsource/magic-config/master/src/test/resources/test.properties");
		
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		URI key = UriUtils.toUri("abc");
		Entry<URI, Object> entry = webLocation.locate(key, parameters);
		
		System.out.println(entry.getValue());
		
		Assert.assertEquals("123", entry.getValue());
	}
	
	@Test
	public void testGetRemoteLocationProperyNoFileOrPrefix() {
		
		System.out.println("--testGetRemoteLocationProperyNoFileOrPrefix--");
		WebLocation webLocation = new WebLocation();
		
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		URI key = UriUtils.toUri("https://raw.github.com/bluemagicsource/magic-config/master/src/test/resources/test.properties");
		Entry<URI, Object> entry = webLocation.locate(key, parameters);
		
		System.out.println(entry.getValue());
		
		Assert.assertEquals("abc=123", entry.getValue());
	}
	
	@Test
	public void testGetRemoteLocationProperyWithPrefix() {
		
		System.out.println("--testGetRemoteLocationProperyWithPrefix--");
		WebLocation webLocation = new WebLocation();
		webLocation.setPrefix("https://raw.github.com/bluemagicsource/magic-config/master/");
		
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		URI key = UriUtils.toUri("src/test/resources/test.properties");
		Entry<URI, Object> entry = webLocation.locate(key, parameters);
		
		System.out.println(entry.getValue());
		
		Assert.assertEquals("abc=123", entry.getValue());
	}
	
	@Test
	public void testGetRemoteLocationProperyWithPrefixInUri() {
		
		System.out.println("--testGetRemoteLocationProperyWithPrefixInUri--");
		WebLocation webLocation = new WebLocation();
		webLocation.setPrefix("https://raw.github.com/bluemagicsource/magic-config/master/");
		
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		URI key = UriUtils.toUri("https://raw.github.com/bluemagicsource/magic-config/master/src/test/resources/test.properties");
		Entry<URI, Object> entry = webLocation.locate(key, parameters);
		
		System.out.println(entry.getValue());
		
		Assert.assertEquals("abc=123", entry.getValue());
	}
}

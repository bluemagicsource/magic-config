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
	public void testGetRemoteLocationProperty() {
		
		System.out.println("--testGetRemoteLocationProperty--");
		WebLocation webLocation = new WebLocation();
		webLocation.setFile("https://raw.github.com/bluemagicsource/magic-config/master/src/test/resources/test.properties");
		
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		URI key = UriUtils.toUri("abc");
		Entry<URI, Object> entry = webLocation.locate(key, parameters);
		
		System.out.println(entry.getValue());
		
		Assert.assertEquals("123", entry.getValue());
	}
}

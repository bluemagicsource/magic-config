package org.bluemagic.config.location;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.util.UriUtils;
import org.junit.Assert;
import org.junit.Test;

public class CommandLineLocationTest {

	@Test
	public void testWithoutCommandLineParameters() {

		URI key = UriUtils.toUri("http://bluemagicsource.org/test");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();

		CommandLineLocation cldp = new CommandLineLocation();
		Object rval = cldp.get(key, parameters);

		Assert.assertNull(rval);
	}

	@Test
	public void testWithEmptyCommandLineParameter() {

		URI key = UriUtils.toUri("http://bluemagicsource.org/test");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();

		System.setProperty("http://bluemagicsource.org/test", "");

		CommandLineLocation cldp = new CommandLineLocation();
		Object rval = cldp.get(key, parameters);

		Assert.assertTrue(rval instanceof String);
		Assert.assertTrue(((String) rval).isEmpty());
	}

	@Test
	public void testWithValidCommandLineParameter() {

		URI key = UriUtils.toUri("http://bluemagicsource.org/test");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();

		System.setProperty("http://bluemagicsource.org/test", "success");

		CommandLineLocation cldp = new CommandLineLocation();
		Object rval = cldp.get(key, parameters);

		Assert.assertTrue(rval instanceof String);
		Assert.assertTrue(rval.equals(new String("success")));
	}

	@Test
	public void testWithMultipleValidCommandLineParameter() {

		URI key = UriUtils.toUri("http://bluemagicsource.org/test");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();

		System.setProperty("http://bluemagicsource.org/test", "success");
		System.setProperty("http://bluemagicsource.org/fail", "fail");

		CommandLineLocation cldp = new CommandLineLocation();
		Object rval = cldp.get(key, parameters);

		Assert.assertTrue(rval instanceof String);
		Assert.assertTrue(rval.equals(new String("success")));
	}
}

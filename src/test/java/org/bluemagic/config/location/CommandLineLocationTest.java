package org.bluemagic.config.location;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.util.UriUtils;
import org.junit.Assert;
import org.junit.Test;

public class CommandLineLocationTest {

	protected URI uri = UriUtils.toUri("http://bluemagicsource.org/test");

	@Test
	public void testWithoutCommandLineParameters() {

		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		parameters.put(MagicKey.ORIGINAL_URI, "http://bluemagicsource.org/test");

		CommandLineLocation cldp = new CommandLineLocation();
		Object rval = cldp.get(uri, parameters);

		Assert.assertNull(rval);
	}

	@Test
	public void testWithEmptyCommandLineParameter() {

		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		parameters.put(MagicKey.ORIGINAL_URI, "http://bluemagicsource.org/test");

		System.setProperty("http://bluemagicsource.org/test", "");

		CommandLineLocation cldp = new CommandLineLocation();
		Object rval = cldp.get(uri, parameters);

		Assert.assertTrue(rval instanceof String);
		Assert.assertTrue(((String) rval).isEmpty());
	}

	@Test
	public void testWithValidCommandLineParameter() {

		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		parameters.put(MagicKey.ORIGINAL_URI, "http://bluemagicsource.org/test");

		System.setProperty("http://bluemagicsource.org/test", "success");

		CommandLineLocation cldp = new CommandLineLocation();
		Object rval = cldp.get(uri, parameters);

		Assert.assertTrue(rval instanceof String);
		Assert.assertTrue(rval.equals(new String("success")));
	}

	@Test
	public void testWithMultipleValidCommandLineParameter() {

		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		parameters.put(MagicKey.ORIGINAL_URI, "http://bluemagicsource.org/test");

		System.setProperty("http://bluemagicsource.org/test", "success");
		System.setProperty("http://bluemagicsource.org/fail", "fail");

		CommandLineLocation cldp = new CommandLineLocation();
		Object rval = cldp.get(uri, parameters);

		Assert.assertTrue(rval instanceof String);
		Assert.assertTrue(rval.equals(new String("success")));
	}
}

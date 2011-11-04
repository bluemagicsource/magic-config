package org.bluemagic.config.agent.provider;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.bluemagic.config.agent.provider.CommandLineDataProvider;
import org.bluemagic.config.api.agent.ConfigKey;
import org.bluemagic.config.api.agent.NoDataFound;
import org.junit.Assert;
import org.junit.Test;

public class CommandLineDataProviderTest {

    protected URI uri = null;

    @Test
    public void testWithoutCommandLineParameters() {

	Map<ConfigKey, Object> parameters = new HashMap<ConfigKey, Object>();
	parameters.put(ConfigKey.ORIGINAL_URI, "http://bluemagicsource.org/test");

	CommandLineDataProvider cldp = new CommandLineDataProvider();
	Object rval = cldp.findData(uri, parameters);

	Assert.assertTrue(rval instanceof NoDataFound);       
    }

    @Test
    public void testWithEmptyCommandLineParameter() {

	Map<ConfigKey, Object> parameters = new HashMap<ConfigKey, Object>();
	parameters.put(ConfigKey.ORIGINAL_URI, "http://bluemagicsource.org/test");

	System.setProperty("http://bluemagicsource.org/test","");

	CommandLineDataProvider cldp = new CommandLineDataProvider();
	Object rval = cldp.findData(uri, parameters);

	Assert.assertTrue(rval instanceof String);       
	Assert.assertTrue(((String)rval).isEmpty());
    }

    @Test
    public void testWithValidCommandLineParameter() {

	Map<ConfigKey, Object> parameters = new HashMap<ConfigKey, Object>();
	parameters.put(ConfigKey.ORIGINAL_URI, "http://bluemagicsource.org/test");

	System.setProperty("http://bluemagicsource.org/test","success");

	CommandLineDataProvider cldp = new CommandLineDataProvider();
	Object rval = cldp.findData(uri, parameters);

	Assert.assertTrue(rval instanceof String);       
	Assert.assertTrue(rval.equals(new String("success")));
    }

    @Test
    public void testWithMultipleValidCommandLineParameter() {

	Map<ConfigKey, Object> parameters = new HashMap<ConfigKey, Object>();
	parameters.put(ConfigKey.ORIGINAL_URI, "http://bluemagicsource.org/test");

	System.setProperty("http://bluemagicsource.org/test","success");
	System.setProperty("http://bluemagicsource.org/fail","fail");

	CommandLineDataProvider cldp = new CommandLineDataProvider();
	Object rval = cldp.findData(uri, parameters);

	Assert.assertTrue(rval instanceof String);       
	Assert.assertTrue(rval.equals(new String("success")));
    }
}

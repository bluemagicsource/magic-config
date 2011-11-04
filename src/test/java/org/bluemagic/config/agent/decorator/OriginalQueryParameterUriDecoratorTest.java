package org.bluemagic.config.agent.decorator;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.bluemagic.config.api.agent.ConfigKey;
import org.bluemagic.config.api.agent.NoDataFound;
import org.junit.Assert;
import org.junit.Test;

public class OriginalQueryParameterUriDecoratorTest {

    protected URI uri = null;

    @Test
    public void testZeroParameters() {

	Map<ConfigKey, Object> parameters = new HashMap<ConfigKey, Object>();
	URI uri = null;
	try {
	    uri = new URI("https://www.bluemagicsource.org/config/projectx/database/username");
	} catch(Throwable t) {
	    Assert.fail("Failed building the uri");
	}

	OriginalQueryParameterUriDecorator oqpud = new OriginalQueryParameterUriDecorator();
	URI resultingUri = oqpud.decorate(uri, parameters);

	Assert.assertNull(resultingUri);
	Assert.assertNotNull(parameters.get(ConfigKey.QUERY_PARAMETERS));
	Map<String, String> queryParameters = (Map<String, String>) parameters.get(ConfigKey.QUERY_PARAMETERS);
	Assert.assertTrue(queryParameters.size() == 0);
    }

    @Test
    public void testOneParameter() {

	Map<ConfigKey, Object> parameters = new HashMap<ConfigKey, Object>();
	URI uri = null;
	try {
	    uri = new URI("https://www.bluemagicsource.org/config/projectx/database/username"
			  + "?tag=latest");
	} catch(Throwable t) {
	    Assert.fail("Failed building the uri");
	}

	OriginalQueryParameterUriDecorator oqpud = new OriginalQueryParameterUriDecorator();
	URI resultingUri = oqpud.decorate(uri, parameters);

	Assert.assertNull(resultingUri);
	Assert.assertNotNull(parameters.get(ConfigKey.QUERY_PARAMETERS));
	Map<String, String> queryParameters = (Map<String, String>) parameters.get(ConfigKey.QUERY_PARAMETERS);
	Assert.assertTrue(queryParameters.size() == 1);
    }

    @Test
    public void testMulipleParameters() {

	Map<ConfigKey, Object> parameters = new HashMap<ConfigKey, Object>();
	URI uri = null;
	try {
	    uri = new URI("https://www.bluemagicsource.org/config/projectx/database/username"
			  + "?tag=latest&systemType=development&appType=json");
	} catch(Throwable t) {
	    Assert.fail("Failed building the uri");
	}

	OriginalQueryParameterUriDecorator oqpud = new OriginalQueryParameterUriDecorator();
	URI resultingUri = oqpud.decorate(uri, parameters);

	Assert.assertNull(resultingUri);
	Assert.assertNull(resultingUri);
	Assert.assertNotNull(parameters.get(ConfigKey.QUERY_PARAMETERS));
	Map<String, String> queryParameters = (Map<String, String>) parameters.get(ConfigKey.QUERY_PARAMETERS);
	Assert.assertTrue(queryParameters.size() == 3);
    }
	
	@Test 
	public void nullTest() {

		OriginalQueryParameterUriDecorator oqpud = new OriginalQueryParameterUriDecorator();
		URI output = oqpud.decorate((URI)null, null);
        Assert.assertNull(output);
   } 

}

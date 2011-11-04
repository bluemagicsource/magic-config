package org.bluemagic.config.agent.decorator;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.bluemagic.config.api.agent.ConfigKey;
import org.bluemagic.config.api.agent.NoDataFound;
import org.junit.Assert;
import org.junit.Test;

public class ParameterOrderDecoratorTest {

    protected URI uri = null;

    @Test
    public void testParametersOrdering() {

	Map<ConfigKey, Object> parameters = new HashMap<ConfigKey, Object>();
	URI uri = null;
	try {
	    uri = new URI("https://www.bluemagicsource.org/config/projectx/database/username"
			  + "?tag=latest&appType=json&systemType=development");
	} catch(Throwable t) {
	    Assert.fail("Failed building the uri");
	}

	ParameterOrderDecorator pod = new ParameterOrderDecorator();
	URI resultingUri = pod.decorate(uri, parameters);

	Assert.assertEquals(resultingUri.toString(), 
			    "https://www.bluemagicsource.org/config/projectx/database/username"
			    + "?appType=json&systemType=development&tag=latest");       
    }

    @Test
    public void testDuplicateParameters() {

	Map<ConfigKey, Object> parameters = new HashMap<ConfigKey, Object>();
	URI uri = null;
	try {
	    uri = new URI("https://www.bluemagicsource.org/config/projectx/database/username"
			  + "?tag=latest&tag=1&tag=2&appType=json&systemType=development");
	} catch(Throwable t) {
	    Assert.fail("Failed building the uri");
	}

	ParameterOrderDecorator pod = new ParameterOrderDecorator();
	URI resultingUri = pod.decorate(uri, parameters);

	Assert.assertEquals(resultingUri.toString(), 
			    "https://www.bluemagicsource.org/config/projectx/database/username"
			    + "?appType=json&systemType=development&tag=2");       
    }

    @Test
    public void testWithoutParameters() {

	Map<ConfigKey, Object> parameters = new HashMap<ConfigKey, Object>();
	URI uri = null;
	try {
	    uri = new URI("https://www.bluemagicsource.org/config/projectx/database/username");
	} catch(Throwable t) {
	    Assert.fail("Failed building the uri");
	}

	ParameterOrderDecorator pod = new ParameterOrderDecorator();
	URI resultingUri = pod.decorate(uri, parameters);

	Assert.assertEquals(resultingUri.toString(), 
			    "https://www.bluemagicsource.org/config/projectx/database/username");
    }

    @Test
    public void testWithOnlyQuestionMark() {

	Map<ConfigKey, Object> parameters = new HashMap<ConfigKey, Object>();
	URI uri = null;
	try {
	    uri = new URI("https://www.bluemagicsource.org/config/projectx/database/username?");
	} catch(Throwable t) {
	    Assert.fail("Failed building the uri");
	}

	ParameterOrderDecorator pod = new ParameterOrderDecorator();
	URI resultingUri = pod.decorate(uri, parameters);

	Assert.assertEquals(resultingUri.toString(), 
			    "https://www.bluemagicsource.org/config/projectx/database/username");
    }

    @Test
    public void testWithEndingAmpersand() {

	Map<ConfigKey, Object> parameters = new HashMap<ConfigKey, Object>();
	URI uri = null;
	try {
	    uri = new URI("https://www.bluemagicsource.org/config/projectx/database/username?systemType=development&");
	} catch(Throwable t) {
	    Assert.fail("Failed building the uri");
	}

	ParameterOrderDecorator pod = new ParameterOrderDecorator();
	URI resultingUri = pod.decorate(uri, parameters);

	Assert.assertEquals(resultingUri.toString(), 
			    "https://www.bluemagicsource.org/config/projectx/database/username?systemType=development");
    }
	@Test 
   public void nullTest() {

    ParameterOrderDecorator pod = new ParameterOrderDecorator();
	URI output = pod.decorate((URI)null, null);;
    Assert.assertNull(output);
   } 

}

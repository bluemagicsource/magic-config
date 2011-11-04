package org.bluemagic.config.agent.decorator;

import java.net.URI;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ParameterDecoratorTest {

   @Test 
   public void URItest() {

	URI input = null;
	    try {
		     input = new URI("https://www.bluemagicsource.org/configuration/database/username?systemType=development");
		} catch(Throwable t) {
			 Assert.fail("failed to create uri");
		}
		ParameterDecorator pd = new ParameterDecorator();
		pd.setParameterName("systemType");
		URI output = pd.decorate(input, null);
		Assert.assertEquals("unexpected decorator", 
				    "https://www.bluemagicsource.org/" 
				    + "configuration/development/development.database"
				    + "/username?systemType=development",
				    output.toString());
   }
@Test 
   public void URItest2() {

	URI input = null;
	    try {
		     input = new URI("https://www.bluemagicsource.org/configuration/database/username?systemType=development");
		} catch(Throwable t) {
			 Assert.fail("failed to create uri");
		}
		ParameterDecorator pd = new ParameterDecorator();
		pd.setParameterName("systemType");
		URI output = pd.decorate(input, null);
		Assert.assertEquals("unexpected decorator", "https://www.bluemagicsource.org/configuration/development/development.database/username?systemType=development",output.toString());
   }

@Test 
   public void nullTest() {

    ParameterDecorator pd = new ParameterDecorator();
	pd.setParameterName("systemType");
	URI output = pd.decorate((URI)null, null);
    Assert.assertNull(output);
   } 
}

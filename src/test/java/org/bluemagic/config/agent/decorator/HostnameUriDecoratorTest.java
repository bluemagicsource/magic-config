package org.bluemagic.config.agent.decorator;

import java.net.InetAddress;
import java.net.URI;

import org.junit.Assert;
import org.junit.Test;

public class HostnameUriDecoratorTest {

   @Test 
   public void test1() {
         
		URI input = null;
	    try {
		     input = new URI("www.bluemagicsource.org/configuration/applicationx/settingy");
		} catch(Throwable t) {
			 Assert.fail("failed to create uri");
		}
		HostnameUriDecorator hud = new HostnameUriDecorator();
		hud.setHostname("zeus");
		URI output = hud.decorate(input, null);
		Assert.assertEquals("unexpected decorator", "www.bluemagicsource.org/configuration/zeus/zeus.applicationx/settingy",output.toString());
   }


   @Test
   public void testNullUri() {

       HostnameUriDecorator hud = new HostnameUriDecorator();
       hud.setHostname("zeus");
       URI output = hud.decorate((URI)null, null);
       Assert.assertNull(output);
   }
}
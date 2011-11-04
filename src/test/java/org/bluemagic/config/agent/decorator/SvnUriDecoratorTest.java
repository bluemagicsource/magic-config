package org.bluemagic.config.agent.decorator;

import java.net.URI;

import org.junit.Assert;
import org.junit.Test;

public class SvnUriDecoratorTest {
   @Test 
   public void SvnUriTagReplaceTest() {
         
	    URI input = null;
	    try {
		 input = new URI("https://www.bluemagicsource.org/svn/agent/trunk/project/database/username");
	    } catch(Throwable t) {
		 Assert.fail("failed to create uri");
	    }
	    SvnUriDecorator sud = new SvnUriDecorator();
	    sud.setReplaceValue("tags/1.0.0.0");
	    URI output = sud.decorate(input, null);
	    Assert.assertTrue(new String("https://www.bluemagicsource.org/svn/agent/tags/1.0.0.0/project/database/username").equals(output.toString()));
   }
  @Test 
   public void nullTest() {

        SvnUriDecorator sud = new SvnUriDecorator();
	    sud.setReplaceValue("tags/1.0.0.0");
	    URI output = sud.decorate((URI)null, null);
        Assert.assertNull(output);
   } 
}

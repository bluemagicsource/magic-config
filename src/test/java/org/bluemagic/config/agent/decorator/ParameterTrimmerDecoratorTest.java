package org.bluemagic.config.agent.decorator;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;


public class ParameterTrimmerDecoratorTest {

   @Test 
   public void TrimmerTest() {
         
	    URI input = null;
	    try {
		 input = new URI("https://www.bluemagicsource/config/projectx/database/username?tag=latest&systemType=development");
	    } catch(Throwable t) {
		 Assert.fail("failed to create uri");
	    }
	    ParameterTrimmerDecorator ptd = new ParameterTrimmerDecorator();
		List<String> filterList = new ArrayList<String>();
		filterList.add("tag");
		filterList.add("systemType");
		ptd.setFilterList(filterList);
		
	    URI output = ptd.decorate(input, null);
	    Assert.assertEquals("https://www.bluemagicsource/config/projectx/database/username",output.toString());
   }
@Test 
   public void nullTest() {

    ParameterTrimmerDecorator ptd = new ParameterTrimmerDecorator();
	List<String> filterList = new ArrayList<String>();
		filterList.add("tag");
		filterList.add("systemType");
	ptd.setFilterList(filterList);
	URI output = ptd.decorate((URI)null, null);
    Assert.assertNull(output);
   } 
}

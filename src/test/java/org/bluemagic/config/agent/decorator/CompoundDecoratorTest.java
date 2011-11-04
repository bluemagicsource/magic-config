package org.bluemagic.config.agent.decorator;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;

import org.bluemagic.config.api.agent.Decorator;
import org.bluemagic.config.api.agent.ConfigKey;
import org.junit.Assert;
import org.junit.Test;

public class CompoundDecoratorTest {

   @Test 
   public void DecorateTest() {

	List<URI> input = new ArrayList<URI>();
	try {
	    input.add(new URI("https://www.bluemagicsource/config/projectx/database/username"
			  + "?tag=latest&systemType=development&tag=latest&appType=json"));
	} catch(Throwable t) {
	    Assert.fail("Failed building the uri");
	}

	List <Decorator> decorators = new ArrayList<Decorator>();
        ParameterTrimmerDecorator ptd = new ParameterTrimmerDecorator();
	List<String> filterList = new ArrayList<String>();
	filterList.add("appType");
	ptd.setFilterList(filterList);
	ParameterOrderDecorator pod = new ParameterOrderDecorator();	
	decorators.add(ptd);
	decorators.add(pod);
	CompoundDecorator cd = new CompoundDecorator();
	cd.setCompoundDecorators(decorators);
        List<URI> output = cd.decorate(input, null);
	Assert.assertEquals("[https://www.bluemagicsource/config/projectx/database/username?systemType=development&tag=latest]",output.toString());  
    }
@Test 
   public void nullTest() {

	List <Decorator> decorators = new ArrayList<Decorator>();
        ParameterTrimmerDecorator ptd = new ParameterTrimmerDecorator();
	List<String> filterList = new ArrayList<String>();
	filterList.add("appType");
	ptd.setFilterList(filterList);
	ParameterOrderDecorator pod = new ParameterOrderDecorator();	
	decorators.add(ptd);
	decorators.add(pod);   	
	CompoundDecorator cd = new CompoundDecorator();
	cd.setCompoundDecorators(decorators);
        List<URI> output = cd.decorate(null, null);
    	Assert.assertNull(output);
}
}

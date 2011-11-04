package org.bluemagic.config.agent.decorator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * The replacement decorator, searches for a term and replaces it with the
 * specified replacement term either in the first or all occurrences based on 
 * the settings associated with this class.
 */
public class LastPathUriStripperDecoratorTest {

    @Test 
    public void test1() {
          
         URI testURI = null;
         URI expectedURI = null;
         try {
              testURI = new URI("classpath://database.properties/username?tag=test");
              expectedURI = new URI("classpath://database.properties?tag=test");
         } catch(Throwable t) {
             Assert.fail("Failed to create URI");
         }
	 LastPathUriStripperDecorator lpusd = new LastPathUriStripperDecorator();
         URI returnURI = lpusd.decorate(testURI, null);
	 Assert.assertTrue(returnURI.equals(expectedURI));
    }

    @Test 
    public void test2() {
          
         URI testURI = null;
         URI expectedURI = null;
         try {
              testURI = new URI("classpath://database.properties?tag=test");
              expectedURI = new URI("classpath://database.properties?tag=test");
         } catch(Throwable t) {
             Assert.fail("Failed to create URI");
         }
	 LastPathUriStripperDecorator lpusd = new LastPathUriStripperDecorator();
         URI returnURI = lpusd.decorate(testURI, null);
	 Assert.assertTrue(returnURI.equals(expectedURI));
    }
    
}

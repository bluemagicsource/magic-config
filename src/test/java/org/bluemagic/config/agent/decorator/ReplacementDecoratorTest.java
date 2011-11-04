package org.bluemagic.config.agent.decorator;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

/**
 * The replacement decorator, searches for a term and replaces it with the
 * specified replacement term either in the first or all occurrences based on 
 * the settings associated with this class.
 */
public class ReplacementDecoratorTest {

    @Test 
    public void testReplaceAll() {
          
         URI testURI = null;
         URI expectedURI = null;
         try {
              testURI = new URI("www.bluemagicsource.org/example/uri/test/test");
              expectedURI = new URI("www.bluemagicsource.org/example/uri/modified/modified");
         } catch(Throwable t) {
             Assert.fail("Failed to create URI");
         }
         
         ReplacementDecorator replacementDecorator = new ReplacementDecorator();
         replacementDecorator.setFindValue("test");
         replacementDecorator.setReplaceValue("modified");
         replacementDecorator.setReplaceAll(true);
         
         URI output = replacementDecorator.decorate(testURI, null);
         Assert.assertEquals(output, expectedURI);
    }
    
    @Test 
    public void testReplaceFirst() {
          
         URI testURI = null;
         URI expectedURI = null;
         try {
              testURI = new URI("www.bluemagicsource.org/example/uri/test/test");
              expectedURI = new URI("www.bluemagicsource.org/example/uri/modified/test");
         } catch(Throwable t) {
             Assert.fail("Failed to create URI");
         }
         
         ReplacementDecorator replacementDecorator = new ReplacementDecorator();
         replacementDecorator.setFindValue("test");
         replacementDecorator.setReplaceValue("modified");
         
         URI output = replacementDecorator.decorate(testURI, null);
         Assert.assertEquals(output, expectedURI);
    }
    
    @Test 
    public void testReplaceFail() {
        URI invalidURI = null;
        
        try {
             invalidURI = new URI("www.bluemagicsource.org");
        } catch(Throwable t) {
            Assert.fail("Failed to create URI");
        }
        
        ReplacementDecorator replacementDecorator = new ReplacementDecorator();
        replacementDecorator.setFindValue("www.bluemagicsource.org");
        replacementDecorator.setReplaceValue("invalid replace");
        
         try {     
             URI output = replacementDecorator.decorate(invalidURI, null);
             Assert.fail("Did not catch invalid URI");
         }
         catch (RuntimeException re) {
             Assert.assertTrue(re.getMessage().startsWith("Unable to create decorated URI"));
         }
    }
	
	@Test
   public void testNullUri() {

       URI invalidURI = null;
       
	   ReplacementDecorator replacementDecorator = new ReplacementDecorator();
       replacementDecorator.setFindValue("www.bluemagicsource.org");
       replacementDecorator.setReplaceValue("invalid replace");
	   URI output = replacementDecorator.decorate(invalidURI, null);
       Assert.assertNull(output);
   }

}
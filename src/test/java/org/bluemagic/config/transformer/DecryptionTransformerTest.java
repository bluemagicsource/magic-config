package org.bluemagic.config.transformer;
//import junit.framework.TestCase;
import junit.framework.Assert;

import org.junit.Test;

public class DecryptionTransformerTest{
	
	@Test
	public void testStringDecryption()
	{
	    String testData = "ENC:7256cc222a32a504277a2483c988a6a438329643b";
	    String decryptedString = "testencryptedstring";
	    String indicator = "ENC:";
	    
		DecryptionTransformer dt = new DecryptionTransformer ();
		dt.setIndicator(indicator);
		Assert.assertEquals(decryptedString, dt.transform(testData, null));
	}
	
	@Test
    public void testStringDecryptionNull()
    {
        DecryptionTransformer dt = new DecryptionTransformer ();
        Assert.assertEquals(null, dt.transform(null, null));
    }
	
	@Test
    public void testStringDecryptionNonStringObject()
    {
	    Integer data = new Integer(5);
	    
        DecryptionTransformer dt = new DecryptionTransformer ();
        Assert.assertEquals(data.toString(), dt.transform(data.toString(), null));
    }

}
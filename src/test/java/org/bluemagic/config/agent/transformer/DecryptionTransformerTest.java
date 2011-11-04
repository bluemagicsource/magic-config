package org.bluemagic.config.agent.transformer;
//import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.Map;

import junit.framework.Assert;

import org.bluemagic.config.api.agent.ConfigKey;
import org.bluemagic.config.transformer.DecryptionTransformer;
import org.junit.After;
import org.junit.Before;
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
		Assert.assertEquals(decryptedString, dt.translate(null, null, testData));
	}
	
	@Test
    public void testStringDecryptionNull()
    {
        DecryptionTransformer dt = new DecryptionTransformer ();
        Assert.assertEquals(null, dt.translate(null, null, null));
    }
	
	@Test
    public void testStringDecryptionNonStringObject()
    {
	    Integer data = new Integer(5);
	    
        DecryptionTransformer dt = new DecryptionTransformer ();
        Assert.assertEquals(data, dt.translate(null, null, data));
    }

}
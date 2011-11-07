package org.bluemagic.config.util;

import org.junit.Assert;
import org.junit.Test;

public class SecurityUtilsTest  {
 
    @Test
    public void decryptTest() {
        Assert.assertEquals("testencryptedstring", SecurityUtils.show("7256cc222a32a504277a2483c988a6a438329643b"));
    }
    
    @Test
    public void encryptTest() {
        Assert.assertEquals("7256cc222a32a504277a2483c988a6a438329643b", SecurityUtils.hide("testencryptedstring"));
    }
    
}
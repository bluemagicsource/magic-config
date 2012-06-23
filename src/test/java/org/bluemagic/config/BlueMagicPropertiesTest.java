package org.bluemagic.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Test;

public class BlueMagicPropertiesTest {

	@Test
	public void simpleGet() {
		BlueMagicProperties bmp = new BlueMagicProperties();
		assertNull(bmp.get("abc"));
	}
	
	@Test
	public void simpleGetValueStoredInParent() {
		BlueMagicProperties bmp = new BlueMagicProperties();
		bmp.put("def", "123");
		assertEquals("123", bmp.getProperty("def"));
	}
	
	@Test
	public void getOtherTypeOfObjec() {
		BlueMagicProperties bmp = new BlueMagicProperties();
		
		try {
			bmp.get(new HashMap<String, String>());
			fail();
		} catch (RuntimeException re) { }
	}
	
//	@Test
//	public void loadCannedProperties() {
//		
//		Properties bmp = new BlueMagicProperties("sample-magic-config.xml");
//		Object foo = bmp.get("foo");
//		System.out.println("\n\nfoo=" + foo.toString());
//		
////		foo = bmp.get("jammin");
////		System.out.println("\n\njammin=" + foo.toString());
//		
//		foo = bmp.get("fu");
//		System.out.println("fu=" + foo.toString());
//	}
//	
//	@Test
//    public void loadPropertyTwice() {
//        
//        Properties bmp = new BlueMagicProperties("sample-magic-config.xml");
//        Object test = bmp.get("test");
//        System.out.println("\n\ntest=" + test.toString());
//        
//        test = bmp.get("test");
//        System.out.println("test=" + test.toString());
//        
//        //foo = bmp.get("fu");
//        //System.out.println("fu=" + foo.toString());
//    }
}

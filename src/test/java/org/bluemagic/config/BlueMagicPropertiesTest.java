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
	
	@Test
	public void prefixKeyCheck() {
		BlueMagicProperties bmp = new BlueMagicProperties();
		bmp.setKeyPrefix("jackster:rodr/");
		
		bmp.put("jackster:rodr/def", "123");
		assertEquals("123", bmp.getProperty("def"));
		
		bmp.put("jackster:rodr/hokey", "yamble");
		assertEquals("yamble", bmp.getProperty("hokey"));
	}
}

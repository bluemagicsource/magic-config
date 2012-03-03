package org.bluemagic.config.decorator.tags;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UsernameTagTest {
	
	@Test
	public void checkPrefix() {
		
		UsernameTag uTag = new UsernameTag();
		uTag.setValue(UsernameTag.USERNAME_PREFIX + "abc");
		assertEquals(UsernameTag.USERNAME_PREFIX + "abc", uTag.getValue());
		
		uTag = new UsernameTag();
		uTag.setValue("abc");
		assertEquals(UsernameTag.USERNAME_PREFIX + "abc", uTag.getValue());
		
		uTag = new UsernameTag();
		uTag.setValue("abc");
		assertEquals(UsernameTag.USERNAME_PREFIX + "abc", uTag.toString());
	}
}

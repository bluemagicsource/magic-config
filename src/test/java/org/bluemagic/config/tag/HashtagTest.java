package org.bluemagic.config.tag;

import static org.junit.Assert.assertEquals;

import org.bluemagic.config.tag.Hashtag;
import org.junit.Test;

public class HashtagTest {
	
	@Test
	public void checkPrefix() {
		
		Hashtag htag = new Hashtag();
		htag.setValue(Hashtag.HASHTAG_PREFIX + "abc");
		assertEquals(Hashtag.HASHTAG_PREFIX + "abc", htag.getValue());
		
		htag = new Hashtag();
		htag.setValue("abc");
		assertEquals(Hashtag.HASHTAG_PREFIX + "abc", htag.getValue());
		
		htag = new Hashtag();
		htag.setValue("abc");
		assertEquals(Hashtag.HASHTAG_PREFIX + "abc", htag.toString());
	}
	
	@Test
	public void checkEquals() {
		
		Hashtag htag1 = new Hashtag();
		htag1.setValue("#UpperCase");
		
		Hashtag htag2 = new Hashtag();
		htag2.setValue("#uppercase");
		
		assertEquals(htag1, htag2);
	}
}

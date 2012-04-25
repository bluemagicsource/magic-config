package org.bluemagic.config.tags;

import static org.junit.Assert.assertEquals;

import org.bluemagic.config.tags.Hashtag;
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
}

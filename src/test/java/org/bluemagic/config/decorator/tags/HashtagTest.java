package org.bluemagic.config.decorator.tags;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HashtagTest {
	
	@Test
	public void checkPrefix() {
		
		Hashtag htag = new Hashtag();
		htag.setValue("#abc");
		assertEquals("#abc", htag.getValue());
		
		htag = new Hashtag();
		htag.setValue("abc");
		assertEquals("#abc", htag.getValue());
		
		htag = new Hashtag();
		htag.setValue("abc");
		assertEquals("#abc", htag.toString());
	}
}

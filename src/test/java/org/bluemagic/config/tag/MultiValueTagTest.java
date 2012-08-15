package org.bluemagic.config.tag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MultiValueTagTest {

	@Test
	public void checkTags() {
		MultiValueTag mTag = new MultiValueTag("abc", "def", "ghi");
		assertEquals("abc:def:ghi", mTag.toString());
	}
}

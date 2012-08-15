package org.bluemagic.config.tag;

import static org.junit.Assert.assertEquals;

import org.bluemagic.config.api.tag.SingleTag;
import org.junit.Test;

public class TagEncoderTest {

	@Test
	public void testUrlSafeString() throws Exception {
		
		SingleTag st = new SingleTag();
		st.setValue("butters=@sucker");
		assertEquals("butters%3D%40sucker", TagEncoder.encode(st, TagEncoder.URL_ENCODING));
	}
	
	@Test
	public void testFileSystemSafeString() throws Exception {
		
		SingleTag st = new SingleTag();
		st.setValue("butters/sucker");
		assertEquals("butterssucker", TagEncoder.encode(st, TagEncoder.FILE_ENCODING));
	}
}

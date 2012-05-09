package org.bluemagic.config.decorator.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.bluemagic.config.api.tag.SingleTag;
import org.junit.Test;

public class SingleTagTest {

	@Test
	public void checkValue() {
		
		SingleTag st = new SingleTag();
		st.setPrefix("prefix-");
		st.setValue("value");
		st.setSuffix("-suffix");
		
		assertEquals("prefix-value-suffix", st.getValue());
	}
	
	@Test
	public void checkValueWithConstructor() {
		
		SingleTag st = new SingleTag("prefix-", "value", "-suffix");
		
		assertEquals("prefix-value-suffix", st.getValue());
	}
	
	@Test
	public void checkEquals() {
		
		SingleTag st1 = new SingleTag("prefix-", "value", "-suffix");
		SingleTag st2 = new SingleTag("prefix-", "value", "-suffix");
		assertEquals(st1, st2);
		
		st1 = new SingleTag("prefix-", "value", "-suffix");
		st2 = new SingleTag("prefix-", "value", "-suffix");
		assertEquals(st1, st1);
		
		st1 = new SingleTag("prefix-", "value", "-suffix");
		st2 = new SingleTag("prefix-", "value", "-suffix");
		assertTrue(st1.equals(st2));
		
		st1 = new SingleTag("different", "value", "-suffix");
		st2 = new SingleTag("prefix-", "value", "-suffix");
		assertFalse(st1.equals(st2));
		
		st1 = new SingleTag("different", "value", "-suffix");
		st2 = new SingleTag("prefix-", "value", "-suffix");
		assertFalse(st2.equals(st1));
	}
	
	@Test
	public void checkNullFixes() {
		
		SingleTag st = new SingleTag("value");
		st.setPrefix(null);
		st.setSuffix(null);
		assertEquals("value", st.getValue());
	}
	
	@Test
	public void checkToString() {
		SingleTag st = new SingleTag("value");
		assertEquals("value", st.toString());
	}
	
	@Test
	public void checkSetValue() {
		
		SingleTag st = new SingleTag();
		st.setPrefix("p-");
		st.setSuffix("-s");
		
		st.setValue("p-abc");
		assertEquals("p-abc-s", st.getValue());
		
		st.setValue("abc-s");
		assertEquals("p-abc-s", st.getValue());
		
		st.setValue("p-abc-s");
		assertEquals("p-abc-s", st.getValue());
		
		st.setValue("abc");
		assertEquals("p-abc-s", st.getValue());
	}
}

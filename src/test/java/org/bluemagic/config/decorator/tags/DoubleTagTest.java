package org.bluemagic.config.decorator.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DoubleTagTest {

	@Test
	public void checkValue() {

		DoubleTag dt = new DoubleTag();
		dt.setKey("key");
		dt.setValue("value");
		assertEquals("value", dt.getValue());
	}
	
	@Test
	public void checkValueWithConstructor() {
		
		DoubleTag dt = new DoubleTag("key", "value");
		assertEquals("value", dt.getValue());
		
		dt = new DoubleTag("key", "prefix-", "value", "-suffix");
		assertEquals("prefix-value-suffix", dt.getValue());
	}
	
	@Test
	public void checkEquals() {
		
		DoubleTag dt1 = new DoubleTag("key", "value");
		DoubleTag dt2 = new DoubleTag("key", "value");
		assertEquals(dt1, dt2);
		assertTrue(dt1.equals(dt1));
		
		dt1 = new DoubleTag("different", "value");
		dt2 = new DoubleTag("key", "value");
		assertFalse(dt1.equals(dt2));
		assertFalse(dt2.equals(dt1));

		dt1 = new DoubleTag("key", "different");
		dt2 = new DoubleTag("key", "value");
		assertFalse(dt1.equals(dt2));
	}
	
	@Test
	public void checkToString() {
		DoubleTag dt = new DoubleTag("key", "value");
		assertEquals("key" + dt.getKeyValueSeparator() + "value", dt.toString());
		
		dt = new DoubleTag("key", "prefix-", "value", "-suffix");
		assertEquals("key" + dt.getKeyValueSeparator() + "prefix-value-suffix", dt.toString());
	}
}

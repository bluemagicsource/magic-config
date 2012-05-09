package org.bluemagic.config.decorator.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.bluemagic.config.api.tag.TripleTag;
import org.junit.Test;

public class TripleTagTest {

	@Test
	public void checkValue() {
		
		TripleTag tt = new TripleTag();
		tt.setNamespace("namespace");
		tt.setPredicate("predicate");
		tt.setValue("value");
		
		assertEquals("value", tt.getValue());
	}
	
	@Test
	public void checkValueWithConstructor() {
	
		TripleTag tt = new TripleTag("namespace", "predicate", "value");
		assertEquals("value", tt.getValue());
		
		tt = new TripleTag("namespace", "predicate", "prefix-", "value", "-suffix");
		assertEquals("prefix-value-suffix", tt.getValue());
	}
	
	@Test
	public void checkEquals() {
		
		TripleTag tt1 = new TripleTag("namespace", "predicate", "value");
		TripleTag tt2 = new TripleTag("namespace", "predicate", "value");
		assertEquals(tt1, tt1);
		assertEquals(tt1, tt2);
		
		tt1 = new TripleTag("namespace", "predicate", "value");
		tt2 = new TripleTag("namespace", "predicate", "value");
		assertTrue(tt1.equals(tt1));
		assertTrue(tt1.equals(tt2));
		
		tt1 = new TripleTag("different", "predicate", "value");
		tt2 = new TripleTag("namespace", "predicate", "value");
		assertTrue(tt2.equals(tt2));
		assertFalse(tt1.equals(tt2));
		
		tt1 = new TripleTag("namespace", "different", "value");
		tt2 = new TripleTag("namespace", "predicate", "value");
		assertFalse(tt1.equals(tt2));
		
		tt1 = new TripleTag("namespace", "predicate", "different");
		tt2 = new TripleTag("namespace", "predicate", "value");
		assertFalse(tt1.equals(tt2));
	}
	
	@Test
	public void checkToString() {
		
		TripleTag tt1 = new TripleTag("namespace", "predicate", "value");
		assertEquals("namespace" + tt1.getNameSpacePredicateSeparator() + "predicate" + tt1.getPredicateValueSeparator() + "value", tt1.toString());
	}
}

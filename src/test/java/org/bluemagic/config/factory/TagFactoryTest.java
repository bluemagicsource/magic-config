package org.bluemagic.config.factory;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.bluemagic.config.api.tag.SingleTag;
import org.bluemagic.config.api.tag.Tag;
import org.junit.Test;

public class TagFactoryTest {

	@Test
	public void testBuildSingleTag() {
		
		TagFactory tf = new TagFactory();
		assertTrue(tf.build("singleTag") instanceof SingleTag);
		assertTrue(tf.build("singleTag") instanceof Tag);
	}
	
	@Test
	public void testBuildSingleTagWithFullyQualifiedPackage() {
		
		TagFactory tf = new TagFactory();
		assertTrue(tf.build("org.bluemagic.config.api.tag.SingleTag") instanceof SingleTag);
		assertTrue(tf.build("org.bluemagic.config.api.tag.SingleTag") instanceof Tag);
	}
	
	@Test
	public void testBuildDoesNotExistClass() {
		
		TagFactory tf = new TagFactory();
		assertNull(tf.build("GoatCheese"));
	}
	
	@Test
	public void testBuildDoesNotExtendsTag() {
		
		TagFactory tf = new TagFactory();
		assertNull(tf.build("java.util.Properties"));
	}
}

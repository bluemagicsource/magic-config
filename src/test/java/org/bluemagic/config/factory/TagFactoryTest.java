package org.bluemagic.config.factory;

import static org.junit.Assert.assertTrue;

import org.bluemagic.config.api.tag.SingleTag;
import org.bluemagic.config.api.tag.Tag;
import org.junit.Test;

public class TagFactoryTest {

	@Test
	public void testBuildSingleTag() {
		
		TagFactoryImpl tf = (TagFactoryImpl) TagFactoryImpl.getInstance();
		Tag tag = tf.createTag("singleTag", null, "value");
		assertTrue(tag instanceof SingleTag);
		assertTrue(tag instanceof Tag);
	}
	
	@Test
	public void testBuildSingleTagWithFullyQualifiedPackage() {
		
		TagFactoryImpl tf = (TagFactoryImpl) TagFactoryImpl.getInstance();
		Tag tag = tf.createTag("org.bluemagic.config.api.tag.SingleTag", null, "value");
		assertTrue(tag instanceof SingleTag);
		assertTrue(tag instanceof Tag);
	}
	
//	@Test
//	public void testBuildDoesNotExistClass() {
//		
//		JackTagFactory tf = new JackTagFactory();
//		assertNull(tf.build("GoatCheese"));
//	}
//	
//	@Test
//	public void testBuildDoesNotExtendsTag() {
//		
//		JackTagFactory tf = new JackTagFactory();
//		assertNull(tf.build("java.util.Properties"));
//	}
}

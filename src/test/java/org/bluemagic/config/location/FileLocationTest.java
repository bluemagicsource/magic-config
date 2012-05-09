package org.bluemagic.config.location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.util.UriUtils;
import org.junit.Test;

public class FileLocationTest {

	@Test
	public void readFromUriXmlFile() {
		
		FileLocation ll = new FileLocation();
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		ll.setFile("testProperties.xml");
		assertEquals("bar", ll.locate(UriUtils.toUri("foo"), parameters).getValue().toString());
		
		ll.setFile("testProperties.xml");
		assertEquals("bar", ll.locate(UriUtils.toUri("foo"), parameters).getValue().toString());
		
		ll.setFile("nested/testProperties.xml");
		assertEquals("bar", ll.locate(UriUtils.toUri("foo"), parameters).getValue().toString());
		
		ll.setFile("nested/testProperties.xml");
		assertEquals("bar", ll.locate(UriUtils.toUri("foo"), parameters).getValue().toString());
	}	
	
	@Test
	public void readFromClasspathUriXmlFile() {
		
		FileLocation ll = new FileLocation();
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		ll.setFile("testProperties.xml");
		assertEquals("bar", ll.locate(UriUtils.toUri("foo"), parameters).getValue().toString());
		
		ll.setFile("classpath://testProperties.xml");
		assertEquals("bar", ll.locate(UriUtils.toUri("foo"), parameters).getValue().toString());
		
		ll.setFile("classpath://nested/testProperties.xml");
		assertEquals("bar", ll.locate(UriUtils.toUri("foo"), parameters).getValue().toString());
		
		ll.setFile("classpath://nested/testProperties.xml");
		assertEquals("bar", ll.locate(UriUtils.toUri("foo"), parameters).getValue().toString());
	}		
	
	@Test
	public void readFromClasspathUriPropertiesFile() {
		
		FileLocation ll = new FileLocation();
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		ll.setFile("test.properties");
		assertEquals("123", ll.locate(UriUtils.toUri("abc"), parameters).getValue().toString());
		
		ll.setFile("classpath://test.properties");
		assertEquals("123", ll.locate(UriUtils.toUri("abc"), parameters).getValue().toString());
		
		ll.setFile("classpath://nested/test.properties");
		assertEquals("123", ll.locate(UriUtils.toUri("abc"), parameters).getValue().toString());
		
		ll.setFile("classpath://nested/test.properties");
		assertEquals("123", ll.locate(UriUtils.toUri("abc"), parameters).getValue().toString());
	}
	
	@Test
	public void isSupportedClasspath() {
		
		FileLocation ll = new FileLocation();
		
		assertTrue(ll.supports(UriUtils.toUri("classpath:abc.xml")));
		assertTrue(ll.supports(UriUtils.toUri("classpath://abc.xml")));
		assertTrue(ll.supports(UriUtils.toUri("file:abc.xml")));
		assertTrue(ll.supports(UriUtils.toUri("file://abc.xml")));
		
		assertFalse(ll.supports(UriUtils.toUri("http://abc.xml")));
		assertFalse(ll.supports(UriUtils.toUri("https://abc.xml")));
	}
}

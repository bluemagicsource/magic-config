package org.bluemagic.config.location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.property.MagicProperty;
import org.bluemagic.config.util.UriUtils;
import org.junit.Test;

public class LocalLocationTest {

	@Test
	public void readFromUriXmlFile() {
		
		LocalLocation ll = new LocalLocation();
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		ll.setUri(UriUtils.toUri("testProperties.xml"));
		assertEquals("bar", ll.locateHelper(UriUtils.toUri("foo"), parameters).getValue().toString());
		
		ll.setUri(UriUtils.toUri("testProperties.xml"));
		assertEquals("bar", ll.locateHelper(UriUtils.toUri("foo"), parameters).getValue().toString());
		
		ll.setUri(UriUtils.toUri("nested/testProperties.xml"));
		assertEquals("bar", ll.locateHelper(UriUtils.toUri("foo"), parameters).getValue().toString());
		
		ll.setUri(UriUtils.toUri("nested/testProperties.xml"));
		assertEquals("bar", ll.locateHelper(UriUtils.toUri("foo"), parameters).getValue().toString());
	}	
	
	@Test
	public void readFromClasspathUriXmlFile() {
		
		LocalLocation ll = new LocalLocation();
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		ll.setUri(UriUtils.toUri("classpath:testProperties.xml"));
		assertEquals("bar", ll.locateHelper(UriUtils.toUri("foo"), parameters).getValue().toString());
		
		ll.setUri(UriUtils.toUri("classpath://testProperties.xml"));
		assertEquals("bar", ll.locateHelper(UriUtils.toUri("foo"), parameters).getValue().toString());
		
		ll.setUri(UriUtils.toUri("classpath://nested/testProperties.xml"));
		assertEquals("bar", ll.locateHelper(UriUtils.toUri("foo"), parameters).getValue().toString());
		
		ll.setUri(UriUtils.toUri("classpath://nested/testProperties.xml"));
		assertEquals("bar", ll.locateHelper(UriUtils.toUri("foo"), parameters).getValue().toString());
	}		
	
	@Test
	public void readFromClasspathUriPropertiesFile() {
		
		LocalLocation ll = new LocalLocation();
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		ll.setUri(UriUtils.toUri("classpath:test.properties"));
		assertEquals("123", ll.locateHelper(UriUtils.toUri("abc"), parameters).getValue().toString());
		
		ll.setUri(UriUtils.toUri("classpath://test.properties"));
		assertEquals("123", ll.locateHelper(UriUtils.toUri("abc"), parameters).getValue().toString());
		
		ll.setUri(UriUtils.toUri("classpath://nested/test.properties"));
		assertEquals("123", ll.locateHelper(UriUtils.toUri("abc"), parameters).getValue().toString());
		
		ll.setUri(UriUtils.toUri("classpath://nested/test.properties"));
		assertEquals("123", ll.locateHelper(UriUtils.toUri("abc"), parameters).getValue().toString());
	}
	
	@Test
	public void readFromClasspathUriAsKeyXmlFile() {
		
		LocalLocation ll = new LocalLocation();
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("bar", ll.locateHelper(UriUtils.toUri("classpath:testProperties.xml/foo"), parameters).getValue().toString());
		assertEquals("bar", ll.locateHelper(UriUtils.toUri("classpath://testProperties.xml/foo"), parameters).getValue().toString());
		
		assertEquals("bar", ll.locateHelper(UriUtils.toUri("classpath:nested/testProperties.xml/foo"), parameters).getValue().toString());
		assertEquals("bar", ll.locateHelper(UriUtils.toUri("classpath://nested/testProperties.xml/foo"), parameters).getValue().toString());
	}	
	
	@Test
	public void readFromClasspathUriAsKeyPropertiesFile() {
		
		LocalLocation ll = new LocalLocation();
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("123", ll.locateHelper(UriUtils.toUri("classpath:test.properties/abc"), parameters).getValue().toString());
		assertEquals("123", ll.locateHelper(UriUtils.toUri("classpath://test.properties/abc"), parameters).getValue().toString());
		
		assertEquals("123", ll.locateHelper(UriUtils.toUri("classpath:nested/test.properties/abc"), parameters).getValue().toString());
		assertEquals("123", ll.locateHelper(UriUtils.toUri("classpath://nested/test.properties/abc"), parameters).getValue().toString());
	}	
	
	@Test
	public void isSupportedClasspath() {
		
		LocalLocation ll = new LocalLocation();
		
		assertTrue(ll.supports(UriUtils.toUri("classpath:abc.xml")));
		assertTrue(ll.supports(UriUtils.toUri("classpath://abc.xml")));
		assertTrue(ll.supports(UriUtils.toUri("file:abc.xml")));
		assertTrue(ll.supports(UriUtils.toUri("file://abc.xml")));
		
		assertFalse(ll.supports(UriUtils.toUri("http://abc.xml")));
		assertFalse(ll.supports(UriUtils.toUri("https://abc.xml")));
		
		ll.setUri(UriUtils.toUri("classpath://abc.xml"));
		assertTrue(ll.supports(UriUtils.toUri("http://abc.xml")));
		assertTrue(ll.supports(UriUtils.toUri("https://abc.xml")));
	}
	
	@Test
	public void readFromFile() {
		
		LocalLocation ll = new LocalLocation();
		
		MagicProperty property = ll.locateHelper(UriUtils.toUri("test.properties"), new HashMap<MagicKey,Object>());
		assertEquals("abc=123", property.getValue().toString().trim());
	}
}

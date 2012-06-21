package org.bluemagic.config.location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Decorator.Method;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.tag.SingleTag;
import org.bluemagic.config.decorator.SingleTagDecorator;
import org.bluemagic.config.util.UriUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileLocationDecoratingWrapperTest {

	private FileLocationDecoratingWrapper wrapper;
	
	@Before
	public void setUp() {
		
		wrapper = new FileLocationDecoratingWrapper();
		
		FileLocation internal= new FileLocation();
		internal.setFile("testProperties.xml");
		wrapper.setInternal(internal);
	}
	
	/*
	 * Will only check testProperties.xml for "foo"
	 */
	@Test
	public void testNoDecorators() {
		
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		String value = wrapper.locate(UriUtils.toUri("foo"), parameters).getValue().toString();
		
		Assert.assertEquals("bar", value);
		
		System.out.println(wrapper.getLocations());
		
		Assert.assertEquals(1, wrapper.getLocations().size());
		
		FileLocation location01 = (FileLocation) wrapper.getLocations().get(0);
		Assert.assertEquals("testProperties.xml", location01.getFile());
	}
	
	/*
	 * Will find "foo" in abc.testProperties.xml
	 */
	@Test
	public void testOneDecorators() {
		
		Collection<Decorator> decorators = new ArrayList<Decorator>();
		SingleTag tag01 = new SingleTag("abc");
		SingleTagDecorator decorator01 = new SingleTagDecorator();
		decorator01.setMethod(Method.PREFIX);
		decorator01.setTag(tag01);
		decorators.add(decorator01);
		wrapper.setDecorators(decorators);
		
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		String value = wrapper.locate(UriUtils.toUri("foo"), parameters).getValue().toString();
		
		System.out.println(wrapper.getLocations());
		Assert.assertEquals("tonn", value);
		Assert.assertEquals(2, wrapper.getLocations().size());
		
		FileLocation location01 = (FileLocation) wrapper.getLocations().get(0);
		Assert.assertEquals("abc.testProperties.xml", location01.getFile());
		
		FileLocation location02 = (FileLocation) wrapper.getLocations().get(1);
		Assert.assertEquals("testProperties.xml", location02.getFile());
	}
	
	/*
	 * Will check:
	 * - abc.testProperties.xml
	 * - testProperties.xml
	 * 
	 * Will find "test" in testProperties.xml
	 */
	@Test
	public void testOneDecoratorsFoundInBaseFile() {
		
		Collection<Decorator> decorators = new ArrayList<Decorator>();
		SingleTag tag01 = new SingleTag("abc");
		SingleTagDecorator decorator01 = new SingleTagDecorator();
		decorator01.setMethod(Method.PREFIX);
		decorator01.setTag(tag01);
		decorators.add(decorator01);
		wrapper.setDecorators(decorators);
		
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		String value = wrapper.locate(UriUtils.toUri("test"), parameters).getValue().toString();
		
		System.out.println(wrapper.getLocations());
		Assert.assertEquals("success", value);
		Assert.assertEquals(2, wrapper.getLocations().size());
		
		FileLocation location01 = (FileLocation) wrapper.getLocations().get(0);
		Assert.assertEquals("abc.testProperties.xml", location01.getFile());
		
		FileLocation location02 = (FileLocation) wrapper.getLocations().get(1);
		Assert.assertEquals("testProperties.xml", location02.getFile());
	}
	
	/*
	 * Will check:
	 * - def.abc.testProperties.xml
	 * - abc.testProperties.xml
	 * - testProperties.xml
	 * 
	 * Will find "test" in testProperties.xml
	 */
	@Test
	public void testTwoDecoratorsFoundInFirstLocation() {
		
		Collection<Decorator> decorators = new ArrayList<Decorator>();
		
		SingleTag tag01 = new SingleTag("abc");
		SingleTagDecorator decorator01 = new SingleTagDecorator();
		decorator01.setMethod(Method.PREFIX);
		decorator01.setTag(tag01);
		decorators.add(decorator01);
		
		SingleTag tag02 = new SingleTag("def");
		SingleTagDecorator decorator02 = new SingleTagDecorator();
		decorator02.setMethod(Method.PREFIX);
		decorator02.setTag(tag02);
		decorators.add(decorator02);
		
		wrapper.setDecorators(decorators);
		
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		String value = wrapper.locate(UriUtils.toUri("twice"), parameters).getValue().toString();
		
		System.out.println(wrapper.getLocations());
		Assert.assertEquals("kazam", value);
		Assert.assertEquals(2, wrapper.getLocations().size());
		
		FileLocation location01 = (FileLocation) wrapper.getLocations().get(0);
		Assert.assertEquals("def.abc.testProperties.xml", location01.getFile());
		
		FileLocation location02 = (FileLocation) wrapper.getLocations().get(1);
		Assert.assertEquals("abc.testProperties.xml", location02.getFile());
		
		FileLocation location03 = (FileLocation) wrapper.getLocations().get(2);
		Assert.assertEquals("testProperties.xml", location03.getFile());
	}
	
	/*
	 * Will check:
	 * - def.abc.testProperties.xml
	 * - abc.testProperties.xml
	 * - testProperties.xml
	 * 
	 * Will find "test" in testProperties.xml
	 */
	@Test
	public void testThreeDecoratorsFoundInBaseLocation() {
		
		Collection<Decorator> decorators = new ArrayList<Decorator>();
		
		SingleTag tag01 = new SingleTag("abc");
		SingleTagDecorator decorator01 = new SingleTagDecorator();
		decorator01.setMethod(Method.PREFIX);
		decorator01.setTag(tag01);
		decorators.add(decorator01);
		
		SingleTag tag02 = new SingleTag("def");
		SingleTagDecorator decorator02 = new SingleTagDecorator();
		decorator02.setMethod(Method.PREFIX);
		decorator02.setTag(tag02);
		decorators.add(decorator02);
		
		SingleTag tag03 = new SingleTag("ghi");
		SingleTagDecorator decorator03 = new SingleTagDecorator();
		decorator03.setMethod(Method.PREFIX);
		decorator03.setTag(tag03);
		decorators.add(decorator03);
		
		wrapper.setDecorators(decorators);
		
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		String value = wrapper.locate(UriUtils.toUri("test"), parameters).getValue().toString();
		
		System.out.println(wrapper.getLocations());
		Assert.assertEquals("success", value);
		Assert.assertEquals(2, wrapper.getLocations().size());
		
		FileLocation location01 = (FileLocation) wrapper.getLocations().get(0);
		Assert.assertEquals("ghi.def.abc.testProperties.xml", location01.getFile());
		
		FileLocation location02 = (FileLocation) wrapper.getLocations().get(1);
		Assert.assertEquals("def.abc.testProperties.xml", location02.getFile());
		
		FileLocation location03 = (FileLocation) wrapper.getLocations().get(2);
		Assert.assertEquals("abc.testProperties.xml", location03.getFile());
		
		FileLocation location04 = (FileLocation) wrapper.getLocations().get(3);
		Assert.assertEquals("testProperties.xml", location04.getFile());
	}
}

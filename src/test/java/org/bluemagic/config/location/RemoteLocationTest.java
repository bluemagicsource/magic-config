package org.bluemagic.config.location;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Decorator.Method;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.decorator.SingleTagDecorator;
import org.bluemagic.config.decorator.tags.SingleTag;
import org.bluemagic.config.location.remote.RestClientManagerHashMapImpl;
import org.bluemagic.config.util.UriUtils;
import org.junit.Assert;
import org.junit.Test;


public class RemoteLocationTest {

	@Test
	public void testRemoteLocationProperty() {
		System.out.println("---testRemoteLocationProperty---");
		RemoteLocation rl = new RemoteLocation();
		rl.setUri(UriUtils.toUri(RestClientManagerHashMapImpl.URL));
		rl.setRestClientManager(new RestClientManagerHashMapImpl());

		Map<String, String> params = new HashMap<String, String>();
		params.put(RestClientManagerHashMapImpl.DATA, "shazam!");
		rl.getRestClientManager().post(UriUtils.toUri("abc/def"), params);
		
		URI key = UriUtils.toUri("abc/def");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		String rval = rl.locate(key, parameters);
		
		System.out.println(rval);
		Assert.assertEquals("shazam!", rval);
	}
	
	@Test
	public void testRemoteLocationPropertyDoesntExist() {
		System.out.println("\n---testRemoteLocationPropertyDoesntExist---");
		RemoteLocation rl = new RemoteLocation();
		rl.setUri(UriUtils.toUri(RestClientManagerHashMapImpl.URL));
		rl.setRestClientManager(new RestClientManagerHashMapImpl());
		
		URI key = UriUtils.toUri("abc/def");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		String rval = rl.locate(key, parameters);

		System.out.println(rval);
		Assert.assertNull(rval);
	}
	
	@Test
	public void testRemoteLocationSingleTagSuffixDecoratorPropertyNotFound() {
		System.out.println("\n---testRemoteLocationSingleTagSuffixDecoratorPropertyNotFound---");
		RemoteLocation rl = new RemoteLocation();
		rl.setUri(UriUtils.toUri(RestClientManagerHashMapImpl.URL));
		rl.setRestClientManager(new RestClientManagerHashMapImpl());
		
		List<Decorator> decorators = new ArrayList<Decorator>();
		SingleTagDecorator decorator = new SingleTagDecorator();
		decorator.setMethod(Method.SUFFIX);
		SingleTag tag = new SingleTag("test");
		decorator.setTag(tag);
		decorators.add(decorator);
		rl.setDecorators(decorators);
		
		URI key = UriUtils.toUri("abc/def");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		String rval = rl.locate(key, parameters);

		System.out.println(rval);
		Assert.assertNull(rval);
	}
	
	@Test
	public void testRemoteLocationSingleTagSuffixDecorator() {
		System.out.println("\n---testRemoteLocationSingleTagSuffixDecorator---");
		RemoteLocation rl = new RemoteLocation();
		rl.setUri(UriUtils.toUri(RestClientManagerHashMapImpl.URL));
		rl.setRestClientManager(new RestClientManagerHashMapImpl());
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(RestClientManagerHashMapImpl.DATA, "huzzah!");
		rl.getRestClientManager().post(UriUtils.toUri("abc/def?tags=test"), params);
		
		List<Decorator> decorators = new ArrayList<Decorator>();
		SingleTagDecorator decorator = new SingleTagDecorator();
		decorator.setMethod(Method.SUFFIX);
		SingleTag tag = new SingleTag("test");
		decorator.setTag(tag);
		decorators.add(decorator);
		rl.setDecorators(decorators);
		
		URI key = UriUtils.toUri("abc/def");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		String rval = rl.locate(key, parameters);

		System.out.println(rval);
		Assert.assertEquals("huzzah!", rval);
	}
	
//	@Test
//	public void tryLocation() {
//		
//		RemoteLocation rl = new RemoteLocation();
//		//rl.setUri("")
//		
//		URI key = UriUtils.toUri("http://configprops.com/property/abc");
//		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
//		
//		try {
//			rl.get(key, parameters);
//			fail();
//		} catch (Exception e) {
//			assertEquals("Failed to retrieve data from the server http://configprops.com/property/abc", e.getMessage());
//		}
//	}
//	
//	@Test
//	public void tryLocation1() {
//		
//		RemoteLocation rl = new RemoteLocation();
//		rl.setUri(UriUtils.toUri("http://configprops.com/property"));
//		
//		URI key = UriUtils.toUri("abc");
//		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
//		
//		try {
//			rl.get(key, parameters);
//			fail();
//		} catch (Exception e) {
//			assertEquals("Failed to retrieve data from the server http://configprops.com/property/abc", e.getMessage());
//		}
//	}
//	
//	@Test
//	public void tryLocation2() {
//		
//		RemoteLocation rl = new RemoteLocation();
//		rl.setUri(UriUtils.toUri("http://configprops.com/property/"));
//		
//		URI key = UriUtils.toUri("abc");
//		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
//		
//		try {
//			rl.get(key, parameters);
//			fail();
//		} catch (Exception e) {
//			assertEquals("Failed to retrieve data from the server http://configprops.com/property/abc", e.getMessage());
//		}
//	}
}

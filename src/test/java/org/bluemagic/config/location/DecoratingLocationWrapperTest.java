package org.bluemagic.config.location;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Decorator.Method;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.property.LocatedProperty;
import org.bluemagic.config.api.property.MissingProperty;
import org.bluemagic.config.api.tag.SingleTag;
import org.bluemagic.config.decorator.SingleTagDecorator;
import org.bluemagic.config.location.remote.RestClientManagerHashMapImpl;
import org.bluemagic.config.util.UriUtils;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

public class DecoratingLocationWrapperTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testLocationSingleTagSuffixDecoratorPropertyNotFound() {
		
		Location mockLocation = EasyMock.createStrictMock(Location.class);
		EasyMock.expect(mockLocation.locate((URI) EasyMock.anyObject(), (Map<MagicKey, Object>) EasyMock.anyObject())).andReturn(new MissingProperty(null, null, null));
		EasyMock.replay(mockLocation);
		
		List<Decorator> decorators = new ArrayList<Decorator>();
		SingleTagDecorator decorator = new SingleTagDecorator();
		decorator.setMethod(Method.SUFFIX);
		SingleTag tag = new SingleTag("test");
		decorator.setTag(tag);
		decorators.add(decorator);
		
		DecoratingLocationWrapper dlw = new DecoratingLocationWrapper();
		dlw.setDecorators(decorators);
		dlw.setInternal(mockLocation);
		
		URI key = UriUtils.toUri("abc/def");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		Entry<URI,Object> property = mockLocation.locate(key, parameters);
		Assert.assertTrue(property instanceof MissingProperty);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testLocationSingleTagSuffixDecorator() throws Exception {
		
		Location mockLocation = EasyMock.createStrictMock(Location.class);
		EasyMock.expect(mockLocation.locate((URI) EasyMock.anyObject(), (Map<MagicKey, Object>) EasyMock.anyObject())).andReturn(new LocatedProperty(new URI(""), new URI(""), "huzzah!", Location.class));
		EasyMock.replay(mockLocation);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(RestClientManagerHashMapImpl.DATA, "huzzah!");

		List<Decorator> decorators = new ArrayList<Decorator>();
		SingleTagDecorator decorator = new SingleTagDecorator();
		decorator.setMethod(Method.SUFFIX);
		SingleTag tag = new SingleTag("test");
		decorator.setTag(tag);
		decorators.add(decorator);
		
		DecoratingLocationWrapper dlw = new DecoratingLocationWrapper();
		dlw.setDecorators(decorators);
		dlw.setInternal(mockLocation);
		
		URI key = UriUtils.toUri("abc/def");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		String rval = dlw.locate(key, parameters).getValue().toString();

		Assert.assertEquals("huzzah!", rval);
	}
}

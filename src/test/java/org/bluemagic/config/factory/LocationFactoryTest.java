package org.bluemagic.config.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.location.LocalLocation;
import org.bluemagic.config.location.RemoteLocation;
import org.bluemagic.config.location.UriLocation;
import org.bluemagic.config.util.UriUtils;
import org.junit.Test;

public class LocationFactoryTest {

	@Test
	public void testBuildLocalLocation() {
		
		LocationFactory lf = new LocationFactory();
		assertTrue(lf.build("localLocation") instanceof LocalLocation);
	}
	
	@Test
	public void testBuildRemoteLocationWithFullyQualifiedPackage() {
		
		LocationFactory lf = new LocationFactory();
		assertTrue(lf.build("org.bluemagic.config.location.RemoteLocation") instanceof RemoteLocation);
	}
	
	@Test
	public void testBuildDoesNotExistClass() {
		
		LocationFactory lf = new LocationFactory();
		assertNull(lf.build("Cheddar"));
	}
	
	@Test
	public void testBuildDoesNotExtendsTag() {
		
		LocationFactory lf = new LocationFactory();
		assertNull(lf.build("java.util.Properties"));
	}
	
	@Test
	public void testBuildUriLocation() {
		
		LocationFactory lf = new LocationFactory();
		
		Location location = new LocalLocation();
		URI uri = UriUtils.toUri("abc");
		List<Decorator> decoratorList = new ArrayList<Decorator>();
		
		UriLocation uriLocation = lf.buildUriLocation(location, uri, decoratorList);
		
		assertNotNull(uriLocation);
		assertEquals(uri, uriLocation.getUri());
		assertEquals(decoratorList, uriLocation.getDecorators());
	}
}

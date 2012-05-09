package org.bluemagic.config.factory;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.bluemagic.config.location.FileLocation;
import org.bluemagic.config.location.WebLocation;
import org.junit.Test;

public class LocationFactoryTest {

	@Test
	public void testBuildLocalLocation() {
		
		LocationFactory lf = new LocationFactory();
		assertTrue(lf.build("fileLocation") instanceof FileLocation);
	}
	
	@Test
	public void testBuildRemoteLocationWithFullyQualifiedPackage() {
		
		LocationFactory lf = new LocationFactory();
		assertTrue(lf.build("org.bluemagic.config.location.WebLocation") instanceof WebLocation);
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
//	
//	@Test
//	public void testBuildUriLocation() {
//		
//		LocationFactory lf = new LocationFactory();
//		
//		Location location = new FileLocationOld();
//		List<Decorator> decoratorList = new ArrayList<Decorator>();
//		
//		Location uriLocation = lf.buildDecoratedLocation(location, decoratorList);
//		
//		assertTrue(uriLocation instanceof DecoratingLocationWrapper);
//		assertEquals(location, ((DecoratingLocationWrapper) uriLocation).getInternal());
//		assertEquals(decoratorList, ((DecoratingLocationWrapper) uriLocation).getDecorators());
//	}
}

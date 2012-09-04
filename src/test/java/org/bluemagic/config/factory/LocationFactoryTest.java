package org.bluemagic.config.factory;

import static org.junit.Assert.assertTrue;

import org.bluemagic.config.api.Location;
import org.bluemagic.config.location.FileLocation;
import org.bluemagic.config.location.WebLocation;
import org.junit.Test;

public class LocationFactoryTest {

	@Test
	public void testBuildLocalLocation() {
		
		LocationFactoryImpl lf = (LocationFactoryImpl) LocationFactoryImpl.getInstance();
		Location location = lf.createLocation("fileLocation", null);
		assertTrue(location instanceof FileLocation);
	}
	
	@Test
	public void testBuildRemoteLocationWithFullyQualifiedPackage() {
		
		LocationFactoryImpl lf = (LocationFactoryImpl) LocationFactoryImpl.getInstance();
		Location location = lf.createLocation("org.bluemagic.config.location.WebLocation", null);
		assertTrue(location instanceof WebLocation);
	}
	
//	@Test
//	public void testBuildDoesNotExistClass() {
//		
//		LocationFactoryImpl lf = (LocationFactoryImpl) LocationFactoryImpl.getInstance();
//		assertNull(lf.build("Cheddar"));
//	}
//	
//	@Test
//	public void testBuildDoesNotExtendsTag() {
//		
//		LocationFactoryImpl lf = (LocationFactoryImpl) LocationFactoryImpl.getInstance();
//		assertNull(lf.build("java.util.Properties"));
//	}
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
//		assertTrue(uriLocation instanceof KeyDecoratingLocationWrapper);
//		assertEquals(location, ((KeyDecoratingLocationWrapper) uriLocation).getInternal());
//		assertEquals(decoratorList, ((KeyDecoratingLocationWrapper) uriLocation).getDecorators());
//	}
}

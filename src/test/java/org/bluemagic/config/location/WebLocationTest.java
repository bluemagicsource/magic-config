package org.bluemagic.config.location;



public class WebLocationTest {

//	@Test
//	public void testRemoteLocationProperty() throws Exception {
//		System.out.println("---testRemoteLocationProperty---");
//		WebLocationOld rl = new WebLocationOld();
//		rl.setUrl(UriUtils.toUri(RestClientManagerHashMapImpl.URL).toURL());
//		rl.setRestClientManager(new RestClientManagerHashMapImpl());
//
//		Map<String, String> params = new HashMap<String, String>();
//		params.put(RestClientManagerHashMapImpl.DATA, "shazam!");
//		rl.getRestClientManager().post(UriUtils.toUri("abc/def"), params);
//		
//		URI key = UriUtils.toUri("abc/def");
//		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
//		
//		String rval = rl.locate(key, parameters).getValue().toString();
//		
//		System.out.println(rval);
//		Assert.assertEquals("shazam!", rval);
//	}
//	
//	@Test
//	public void testRemoteLocationPropertyDoesntExist() throws Exception {
//		System.out.println("\n---testRemoteLocationPropertyDoesntExist---");
//		WebLocationOld rl = new WebLocationOld();
//		rl.setUrl(UriUtils.toUri(RestClientManagerHashMapImpl.URL).toURL());
//		rl.setRestClientManager(new RestClientManagerHashMapImpl());
//		
//		URI key = UriUtils.toUri("abc/def");
//		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
//		
//		Entry<URI,Object> property = rl.locate(key, parameters);
//		Assert.assertTrue(property instanceof MissingProperty);
//	}
	
//	@Test
//	public void tryLocation() {
//		
//		WebLocationOld rl = new WebLocationOld();
//		//rl.setUrl("")
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
//		WebLocationOld rl = new WebLocationOld();
//		rl.setUrl(UriUtils.toUri("http://configprops.com/property"));
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
//		WebLocationOld rl = new WebLocationOld();
//		rl.setUrl(UriUtils.toUri("http://configprops.com/property/"));
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

package org.bluemagic.config.location.remote;

import org.junit.Test;

public class TimedRestClientManagerTest {

	@Test
	public void tryGet() throws Exception {
		
		TimedRestClientManager trcm = new TimedRestClientManager();
		trcm.setInternalRestClientManager(new SimpleRestClientManager());
		
		trcm.setTimeoutInMillis(10);
		//assertNull(trcm.get(new URI("http://configprops.com/property/abc")));
		
		trcm.setTimeoutInMillis(1000);
		//assertNotNull(trcm.get(new URI("http://configprops.com/property/abc")));
	}
}

package org.bluemagic.config.location.remote;

import java.net.URI;

public class SecureRestClientManager extends SimpleRestClientManager {

	@Override
	public String get(URI uri) {
		
		String retval = null;
		
		if ("https".equals(uri.getScheme())) {
			// TODO: BRIAN ADD HTTPS CALL HERE
			
		} else {
			retval = super.get(uri);
		}
		return retval;
	}
}

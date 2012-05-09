package org.bluemagic.config.repository.web;

import java.net.URI;

public class SecureRestClientManager extends SimpleRestRepository {

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

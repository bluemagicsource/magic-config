package org.bluemagic.config.tags;

import java.net.InetAddress;

import org.bluemagic.config.api.tag.SingleTag;

public class HostnameTag extends SingleTag {

	@Override
	public String getValue() {
		
		if ((value == null) || (value.isEmpty())) {
			try {
				value = InetAddress.getLocalHost().getHostName();
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
		return value;
	}
}

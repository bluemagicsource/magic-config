package org.bluemagic.config.location;

import java.net.URI;
import java.util.Collection;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.tag.Tag.Encoding;

public abstract class DecoratingLocationWrapper implements Location {
	
	protected Collection<Decorator> decorators;
	
	protected Location internal;
	
	public boolean supports(URI key) {
		// CHECK TO SEE IF INTERNAL SUPPORTS THE KEY TYPE
		return internal.supports(key);
	}
	
	@Override
	public String toString() {

		StringBuilder b = new StringBuilder();
		
		b.append(this.getClass().getName());
		if (this.internal != null) {
			b.append(" wrapping ");
			b.append(this.internal.toString());
		}
		if (this.decorators != null) {
			b.append(" with ");
			b.append(this.decorators.size());
			b.append(" decorators");
		}
		return b.toString();
	}
	
	@Override
	public Encoding getEncoding() {
		
		if (internal == null) {
			return Encoding.NONE;
		}
		return internal.getEncoding();
	}
	
	public void setDecorators(Collection<Decorator> decorators) {
		this.decorators = decorators;
	}

	public Collection<Decorator> getDecorators() {
		return decorators;
	}

	public Location getInternal() {
		return internal;
	}

	public void setInternal(Location internal) {
		this.internal = internal;
	}
}

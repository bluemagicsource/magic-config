package org.bluemagic.config.location;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.property.LocatedProperty;
import org.bluemagic.config.api.tag.Tag;
import org.bluemagic.config.api.tag.Tag.Encoding;

public class DecoratingLocationWrapper implements Location {

	private Collection<Decorator> decorators;
	
	private Location internal;

	public Entry<URI,Object> locate(URI key, Map<MagicKey, Object> parameters) {
		
		Entry<URI,Object> property = null;
		Collection<URI> decoratedList = new ArrayList<URI>();
		
		// COPY THE DECORATOR LIST AND REVERSE IT
		List<Decorator> reversedDecorators = new ArrayList<Decorator>(this.decorators);
		Collections.reverse(reversedDecorators);
		
		// ADD THE UNDECORATED VALUE
		decoratedList.add(key);
		
		// ITERATE THRU EACH DECORATOR AND DECORATE THE URI
		for (Decorator decorator : reversedDecorators) {
			decoratedList = decorator.decorate(decoratedList, parameters);
		}
		
		// LOOP THRU DECORATED URIs AND LOCATE THEM
		for (URI decorated : decoratedList) {
			property = internal.locate(decorated, parameters);
			
			if (property instanceof LocatedProperty) {
				// THIS MEANS WE HAD A HIT SO BREAK RETURN IT
				break;
			}
		}
		return property;
	}
	
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
		return Tag.Encoding.NONE;
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

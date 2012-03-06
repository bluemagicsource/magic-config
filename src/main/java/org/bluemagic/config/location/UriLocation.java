package org.bluemagic.config.location;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.property.LocatedProperty;
import org.bluemagic.config.api.property.MagicProperty;

public abstract class UriLocation implements Location {
	
	private Collection<Decorator> decorators;

	protected URI uri;
	
	public MagicProperty locate(URI key, Map<MagicKey, Object> parameters) {
		
		URI originalUri = this.uri;
		MagicProperty property = null;
		Collection<URI> decorated = new ArrayList<URI>();
		
		addOriginalURI(decorated, key);
		
		List<Decorator> reversedDecorators = new ArrayList<Decorator>(getDecorators());
		Collections.reverse(reversedDecorators);
		
		for (Decorator decorator : reversedDecorators) {
			decorated = decorator.decorate(decorated, parameters);
		}
		
		for (URI uri : decorated) {
			this.uri = uri;
			property = locateHelper(key, parameters);
			
			if (property instanceof LocatedProperty) {
				break;
			}
		}
		this.uri = originalUri;
		
		return property;
	}
	
	/**
	 * <p>
	 * This method should be overridden if additional logic needs to be done
	 * to determine the original uri (such as in the RemoteLocation class).  By default this method just adds
	 * this class's URI parameter to the list of URI's to be decorated.
	 * </p>
	 * 
	 * <p>
	 * The need for this method arose because of the RemoteLocation.  For RemoteLocation's
	 * the original URI consists of the URI parameter plus the key appended to the end.
	 * </p>
	 * 
	 * <p>
	 * An example of a remote location:<br>
	 * URI: http://www.xyz.com/property/<br>
	 * Key: some/really/cool/property
	 * </p>
	 * 
	 * <p>
	 * RemoteLocation original uri should be:  http://www.xyz.com/property/some/really/cool/property
	 * </p>
	 * 
	 * <p>
	 * Then this URI should be decorated with the tags.  For example, a single suffix tag, "test":
	 * </p>
	 * 
	 * <p>
	 * Result:  http://www.xyz.com/property/some/really/cool/property?tags=test
	 * </p>
	 * 
	 * <p>
	 * With the way it was before, the result would have been:
	 * </p>
	 * 
	 * http://www.xyz.com/property/?tags=test/some/really/cool/property , which is not what we are looking for.
	 */
	protected void addOriginalURI(Collection<URI> decorated, URI key) {
		decorated.add(this.uri);
	}
	
	public abstract MagicProperty locateHelper(URI key, Map<MagicKey, Object> parameters);

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public URI getUri() {
		return uri;
	}

	public void setDecorators(Collection<Decorator> decorators) {
		this.decorators = decorators;
	}

	public Collection<Decorator> getDecorators() {
		if (decorators == null || decorators.isEmpty()) {
			return Collections.<Decorator>emptyList();
		}
		
		return decorators;
	}
}

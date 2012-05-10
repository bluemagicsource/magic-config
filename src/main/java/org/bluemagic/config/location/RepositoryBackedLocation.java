package org.bluemagic.config.location;

import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;

import org.bluemagic.config.api.Location;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.Repository;
import org.bluemagic.config.api.property.LocatedProperty;
import org.bluemagic.config.api.property.MissingProperty;

public abstract class RepositoryBackedLocation implements Location {

	protected Repository repository;

	abstract protected void init();
	
	public boolean supports(URI key) {
		return repository.supports(key);
	}
	
	public Entry<URI, Object> locate(URI key, Map<MagicKey, Object> parameters) {

		Entry<URI, Object> property = null;
		
		if (repository == null) {
			init();
		}
		
		// DELEGATE TO A REPOSITORY FOR PROPERTIES
		Object object = getPropertyFromRepository(key);
		
		if (object != null) {
			property = new LocatedProperty((URI) parameters.get(MagicKey.ORIGINAL_URI), key, object, this.getClass());
		} else {
			property = new MissingProperty((URI) parameters.get(MagicKey.ORIGINAL_URI), key, this.getClass());
		}
		return property;
	}
	
	public Object getPropertyFromRepository(URI key) {
		return this.repository.get(key);
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}

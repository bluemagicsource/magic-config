package org.bluemagic.config.repository;

import java.net.URI;

import org.bluemagic.config.api.Repository;

public class SingleKeyRepository implements Repository {

	@Override
	public boolean supports(URI uri) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object put(URI key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(URI key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object remove(URI key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}

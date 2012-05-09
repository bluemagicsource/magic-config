package org.bluemagic.config.repository;

import java.io.File;
import java.net.URI;

import org.bluemagic.config.api.Repository;
import org.bluemagic.config.util.UriUtils;

public class DirectoryRepository implements Repository {

	private Repository repository;
	
	private String path;
	
	public DirectoryRepository(String path, Repository repository) {
		this.setPath(path);
		this.setRepository(repository);
	}

	@Override
	public Object put(URI key, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(URI key) {
		return getRepository().get(UriUtils.toUri(getPath() + File.separator + key.toASCIIString()));
	}

	@Override
	public Object remove(URI key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}
	
	public boolean supports(URI uri) {
		return (uri != null);
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + " reading: " + getPath();
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}

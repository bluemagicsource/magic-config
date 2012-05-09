package org.bluemagic.config.repository.file;

import java.net.URI;

import org.bluemagic.config.api.Repository;


public abstract class FileRepository implements Repository {
	
	private URI file;
	
	public boolean supports(URI uri) {
		return (uri != null);
	}
	
	public URI getFile() {
		return file;
	}

	public void setFile(URI file) {
		this.file = file;
	}
}

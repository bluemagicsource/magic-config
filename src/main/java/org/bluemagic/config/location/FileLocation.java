package org.bluemagic.config.location;

import java.io.File;
import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.repository.DirectoryRepository;
import org.bluemagic.config.repository.file.PropertiesFileRepository;
import org.bluemagic.config.repository.file.TextFileRepository;

public class FileLocation extends RepositoryBackedLocation {
	
	private static final Log LOG = LogFactory.getLog(FileLocation.class);
	
	private String folder;
	
	private String file;
	
	@Override
	public void init() {
		
		if (this.file != null) {
			LOG.trace("Initializing with file: " + this.file);
			// GIVEN A PROPERTIES FILE ON DISK
			File propertiesFile = getFileFromDisk(this.file);
			this.repository = new PropertiesFileRepository(propertiesFile);
			
		} else { 
			LOG.trace("Initializing with folder: " + this.folder);
			
			// IF NO PATH SPECIFIED THEN PREFIX WITH EMPTY PATH
			if (this.folder == null) {
				this.folder = "";
			}
			// GIVEN A DIRECTORY ON DISK
			this.repository = new DirectoryRepository(this.folder, new TextFileRepository());
		}
		LOG.debug("Repository initialized with: " + this.repository.toString());
	}
	
	public boolean supports(URI key) {
		
		boolean supports = false;

		if ((key != null) && ("file".equals(key.getScheme()) || ("classpath".equals(key.getScheme())) || ("".equals(key.getScheme()) || (key.getScheme() == null)))) {
			supports = true;
		}
		return supports;
	}

	private File getFileFromDisk(String file) {
		
		File propertiesFile = null;
		
		try {
			URI uri = this.getClass().getClassLoader().getResource(file).toURI();							
			propertiesFile = new File(uri);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return propertiesFile;
	}
	
	@Override
	public String toString() {
		
		StringBuilder b = new StringBuilder();

		b.append(this.getClass().getName());

		if (this.file != null) {
			b.append(" for file: ");
			b.append(this.file);
		} else if (this.folder != null) {
			b.append(" for folder: ");
			b.append(this.folder);
		} else {
			b.append(" for unknown location");
		}
		if (this.repository != null) {
			b.append(" with repository: ");
			b.append(this.repository.toString());
		}
		return b.toString();
	}

	public String getFolder() {
		return this.folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}
}

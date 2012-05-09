package org.bluemagic.config.location;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

import org.bluemagic.config.repository.DirectoryRepository;
import org.bluemagic.config.repository.file.PropertiesFileRepository;
import org.bluemagic.config.repository.web.SimpleRestRepository;

public class WebLocation extends RepositoryBackedLocation {
	
	private String prefix;
	
	private String file;

	public void init() {
		
		if (file != null) {
			// GIVEN A REMOTE PROPERTIES FILE
			Reader readerFromServer = getFileFromServer(file);
			this.repository = new PropertiesFileRepository(readerFromServer);
			
		} else { 
			
			// IF NO PATH SPECIFIED THEN PREFIX WITH EMPTY PATH
			if (prefix == null) {
				prefix = "";
			}
			// GIVEN A PREFIX OF THE URL
			this.repository = new DirectoryRepository(prefix, new SimpleRestRepository());
		}
	}
	
	public boolean supports(URI key) {
		
		String scheme = key.getScheme();
		return (scheme.isEmpty()) || (scheme.startsWith("http")); 
	}
	
	private Reader getFileFromServer(String file) {
		
		Reader reader = null;
		
		try {
			
			URL url = new URL(file);
			InputStream inputStream = url.openStream();
			reader = new InputStreamReader(inputStream);
			
			return reader;
		} catch (Throwable t) {
			throw new RuntimeException(t.getMessage(), t);
		}
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
}

package org.bluemagic.config.location;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

import org.bluemagic.config.api.tag.Tag;
import org.bluemagic.config.api.tag.Tag.Encoding;
import org.bluemagic.config.repository.file.PropertiesFileRepository;
import org.bluemagic.config.repository.web.RemoteFileRepository;
import org.bluemagic.config.util.UriUtils;

public class WebLocation extends RepositoryBackedLocation {
	
	private String prefix;
	
	private String file;

	public void init() {
		
		if (file != null) {
			// GIVEN A REMOTE PROPERTIES FILE
			Reader readerFromServer = getFileFromServer(file);
			this.repository = new PropertiesFileRepository(readerFromServer);
			
		} else { 
			
			this.repository = new RemoteFileRepository();
		}
	}
	
	@Override
	public Object getPropertyFromRepository(URI key) {
		
		String originalKey = key.toString();
		String normalizedKey = null;
		
		if (prefix != null) {
			
			// CHECK TO SEE IF THE PREFIX IS IN THE KEY ALREADY
			// IF IT IS, THEN NO NEED TO APPEND THE PREFIX
			
			if (!originalKey.startsWith(prefix)) {
				normalizedKey = prefix + originalKey;
			} else {
				normalizedKey = originalKey;
			}
		} else {
			normalizedKey = originalKey;
		}
		
		return this.repository.get(UriUtils.toUri(normalizedKey));
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
	
	@Override
	public Encoding getEncoding() {
		return Tag.Encoding.NONE;
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

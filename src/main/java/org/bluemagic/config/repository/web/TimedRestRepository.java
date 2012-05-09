package org.bluemagic.config.location.remote;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TimedRestClientManager implements RestClientManager {
	
	private long timeoutInMillis = 1000;
	
	private RestClientManager internalRestClientManager;

	public String post(URI uri, Map<String, String> parameters) {
		throw new UnsupportedOperationException();
	}

	public String get(URI uri) {
		
		RestfulGetCallable getCallable = new RestfulGetCallable(uri);
		
		try {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.invokeAll(Arrays.asList(getCallable), timeoutInMillis, TimeUnit.MILLISECONDS);
			executor.shutdown();
			
		} catch (Exception e) {
			return null;
		}
		return getCallable.result;
	}

	public String update(URI uri, Map<String, String> parameters) {
		throw new UnsupportedOperationException();
	}

	public String delete(URI uri) {
		throw new UnsupportedOperationException();
	}

	public void setInternalRestClientManager(RestClientManager internalRestClientManager) {
		this.internalRestClientManager = internalRestClientManager;
	}

	public RestClientManager getInternalRestClientManager() {
		return internalRestClientManager;
	}
	
	public void setTimeoutInMillis(long timeoutInMillis) {
		this.timeoutInMillis = timeoutInMillis;
	}

	public long getTimeoutInMillis() {
		return timeoutInMillis;
	}

	private class RestfulGetCallable implements Callable<String> {

		public String result = null;
		
		private URI uri;
		
		public RestfulGetCallable(URI uri) {
			this.setUri(uri);
		}
		
		public void setUri(URI uri) {
			this.uri = uri;
		}

		public URI getUri() {
			return uri;
		}

		public String call() throws Exception {
			result = internalRestClientManager.get(getUri());
			return result;
		}
	}
}

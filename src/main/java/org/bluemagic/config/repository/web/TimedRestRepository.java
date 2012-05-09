package org.bluemagic.config.repository.web;

import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TimedRestRepository extends WebRepository {
	
	private long timeoutInMillis = 1000;
	
	private WebRepository internalWebRepository;

	@Override
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
			result = internalWebRepository.get(getUri()).toString();
			return result;
		}
	}

	@Override
	public Object put(URI key, Object value) {
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

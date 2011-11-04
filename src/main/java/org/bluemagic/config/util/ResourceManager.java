package org.bluemagic.config.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Provide a means to safely close all resources as not to needlessly
 * use up resources that affect performance and scalability. Since,
 * resources all use close methods, but do not share the same interface
 * this class uses reflection to check if the resource has a close method
 * and call it via reflection. This implementation is a tiny bit slower
 * because of reflection; however, it also does not pull in a ton of
 * libraries checking data types which would be even slower....
 **/
public class ResourceManager {

    /**
     * @see org.bluemagic.config.util.ResourceManager class notes
     **/
    public static void close(Object resource) {
	
	if (resource != null) {
	    
	    // First check all of the return methods for a close without
	    // parameters.
	    Method method = null;
	    try {
		method = resource.getClass().getMethod("close", (Class[])null);
		
		if ((method != null)
		    && (Modifier.isPublic(method.getModifiers()))) {
		    method.invoke(resource, (Object[])null);
		}
	    } catch(Throwable t) {
		// Exception hiding, we failed closing a resource...
		// add logging here if you wish but a closed failure only
		//means that this was already closed.
	    }
	}
    }
}

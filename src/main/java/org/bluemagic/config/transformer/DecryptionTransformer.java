package org.bluemagic.config.transformer;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.agent.ConfigKey;
import org.bluemagic.config.api.agent.Transformer;
import org.bluemagic.config.util.SecurityUtils;

public class DecryptionTransformer implements Transformer {
    
    private static final Log LOG = LogFactory.getLog(DecryptionTransformer.class);
    
    private String indicator = "ENC:";
    
    
    /***
     * Checks incoming data for string starting with ENC:
     * and returns decrypted string
     * 
     * @param data value in the key/value relationship
     * @param parameters Key Value pairs
     * @return decrypted string
     */
    public String transform(String data, Map<ConfigKey, Object> parameters) {  
        
    	String transformed = data;
    	
        if (data != null) {
            if (data.startsWith(indicator)) {
                transformed = SecurityUtils.show(data.substring(indicator.length()));
                LOG.debug("Transformed " + data + " to " + transformed);
            }
        }
        return transformed;
    }
    
    /**
     * Sets the encryption string prefix. Default value is ENC:
     * @param indicator can not be null
     */
    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }
}
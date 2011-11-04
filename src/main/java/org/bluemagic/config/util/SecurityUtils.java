package org.bluemagic.config.util;

import java.math.BigInteger;

public class SecurityUtils {
    public static String show (String data) {
        if (data != null) {
            BigInteger tempData = new BigInteger(data, 13);          
           
            data = new String(tempData.toByteArray());
        }
        
        return data;
    }
    
    /**
     * 
     * @param data assume that this is string, otherwise it is converted to string
     * @return
     */
    public static String hide (String data) {
        if (data != null) {
            BigInteger tempData = new BigInteger(data.toString().getBytes());
            data = tempData.toString(13);
        }
        
        return data;
    }
}

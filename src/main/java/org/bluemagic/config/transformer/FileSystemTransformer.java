package org.bluemagic.config.transformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.Transformer;

/***
 * Transforms key value properties into file format
 * @author Peter Platt
 */
public class FileSystemTransformer implements Transformer {
    
    private static final Log LOG = LogFactory.getLog(FileSystemTransformer.class);

    private URI URIpropertiesFile = null;
    private String propertiesFile = "";

    
    /***
     * Transforms key/value property into a file
     * @param key Property URI Value(key)
     * @param parameters Key Value pairs
     * @param data value in the key/value relationship
     */
    public String transform(String data, Map<MagicKey, Object> parameters) {        
        storeProperties((String) parameters.get(MagicKey.ORIGINAL_URI), data);       
        return data;
    }    
    
    /*** 
     * Stores key/value data to a file
     * @param key property key 
     * @param value property data 
     */
    private void storeProperties (String key, String value) {
        Properties fileProperties = new Properties();
        String propertiesFileLoc = "";        
        
        
        //sets the propertiesFileLoc variable to the either the URI or string file path
        //RTE happens if the URI isn't formated properly
        propertiesFileLoc = checkPropertiesFile();
        
        //Tests if an existing properties file exists. If it does it opens it and associates it with 
        //the fileProperties variables. note: assumes the properties file is in XML format 
        fileProperties = loadExistingPropeties(fileProperties,propertiesFileLoc);
         
        //Sets additional properties
        fileProperties.setProperty(key, value);
        
        //Saves to an XML formated properties file
        OutputStream xmlProperties = null;        
        try {
            xmlProperties = new FileOutputStream(propertiesFileLoc);
            fileProperties.storeToXML(xmlProperties, "");            
        } catch (Exception e){
        	LOG.error("Unable to save xmlProperties",e);
            throw new RuntimeException(e);
        } finally {
            try {
            	xmlProperties.close();
                } catch (IOException e) { LOG.error("Unable to close the xml Properties file",e);}            
        }
    }
    
    /***
     * Loads any existing properties into the properties file
     * @param fileProperties
     * @param propertiesFileLoc
     * @return
     */
    private Properties loadExistingPropeties(Properties fileProperties,String propertiesFileLoc) {
    	File propertiesFileCheck = new File(propertiesFileLoc);
        if (propertiesFileCheck.exists()) {
            try {
                FileInputStream xmlPropFileIn = new FileInputStream(propertiesFileLoc);
                 fileProperties.loadFromXML(xmlPropFileIn);                 
                 }catch (InvalidPropertiesFormatException invalidPropFormat) {
                     //This only happen if a file is empty or not formated properly program will continue   
                     LOG.debug("File '"+propertiesFileLoc+"' was not formated properly no longer loading xml data");} 
            	catch (Exception e) {                
                // This only happens when we can't load properties file does user have permission or not properly formated
                LOG.error("Unable to load properties file", e);
                throw new RuntimeException(e);
            }
        } 
		return fileProperties;
	}

	/***
     * Sets the properties file location
     * @return full properties file location 
     */
    private String checkPropertiesFile() {	
    	if (!propertiesFile.equals("")) { return propertiesFile;}	
        if (URIpropertiesFile != null) {            
            if (URIpropertiesFile.getScheme().equals("file")) {                
                return URIpropertiesFile.getSchemeSpecificPart().substring(2).replace('/',File.separatorChar);
            } else {
                LOG.error("URI is not properly formated as a file");
                throw new RuntimeException("URI is not properly formated as a file");
            }
        }
        return "";
    }

    		
    
	/**
     * @return the URI formated properties file location
     */
    public URI getURIpropertiesFile() {
        return URIpropertiesFile;
    }

    /**
     * @param uRIpropertiesFile URI formated path of the properties file. Needs to be XML
     */
    public void setURIpropertiesFile(URI uRIpropertiesFile) {
        URIpropertiesFile = uRIpropertiesFile;
    }

    /**
     * @return full path propertiesFile
     */
    public String getPropertiesFile() {
        return propertiesFile;
    }

    /**
     * @param propertiesFile Full path and filename of the properties file. Needs to be XML
     */
    public void setPropertiesFile(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }
    
    /**
     * @param propertiesFile Full path and filename of the properties file. Needs to be XML
     */
    public void setFile(String propertiesFile) {
        setPropertiesFile(propertiesFile);
    }    
}
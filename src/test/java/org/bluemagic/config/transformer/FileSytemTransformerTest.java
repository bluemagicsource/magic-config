package org.bluemagic.config.transformer;
//import junit.framework.TestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

import org.bluemagic.config.api.MagicKey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileSytemTransformerTest{
	
	String testFileName;
	String testFilePath;

	
	@Before
	public void generateTestData()
	{
		testFileName = "FileTransformer_Properties_test.xml";
		String tempTestFile = "src"+File.separator+"test"+File.separator+"resources"+File.separator+testFileName;
		
		//Look into this an IO error here should shut down all tests
		try
		{
			File F = new File(tempTestFile);
			if (F.exists())
			{
				F.delete();
			}
			F.createNewFile();
			testFilePath = tempTestFile;			
			testFilePath = testFilePath.substring(0,testFilePath.lastIndexOf(File.separator));			
			
			
		}
		catch (Exception e)
		{
			AssertionError ae = new AssertionError("IO error with filesystem");
			ae.initCause(e);
			throw ae;
		}
	}

	
	//Tests is SetFile works properly
	@Test
	public void testSetFile()
	{
		FileSystemTransformer fsdt = new FileSystemTransformer();
		
		fsdt.setPropertiesFile(testFilePath+File.separator+testFileName);
		
		assertEquals("Tests if setFile method works",testFilePath+File.separator+testFileName,fsdt.getPropertiesFile());
	}
	
	//Tests if Object Data actually changes 
	@Test
	public void testOjectChange(){
		String valueData = "";
		Map<MagicKey, Object> testParameters =new HashMap<MagicKey, Object>();
		FileSystemTransformer fsdt = new FileSystemTransformer();
			
		//Set testParameters
		MagicKey key = MagicKey.ORIGINAL_URI;
		testParameters.put(key, "Noriega Chronicles");
		//Set Data object
		valueData = "Noriega Chronicles";
		//Set Test Filename
		fsdt.setPropertiesFile(testFilePath+File.separator+"testPropertiesFile.xml");		
		
		assertEquals("Object Data will stay the same", valueData, fsdt.transform(valueData, testParameters));
		
	}
	
	//Tests if a properties file can be appended to 
	//Note on refactor clean up the gen code
	@Test
	public void testPropertiesAppend(){		
		String valueData = "";
		Map<MagicKey, Object> testParameters =new HashMap<MagicKey, Object>();
		FileSystemTransformer fsdt = new FileSystemTransformer();
		
		File f = new File(testFilePath+File.separator+"testPropertiesFile.xml");
		if (!f.exists())
		{
			//Generates a properties file so it can be appended later
			//Set testParameters
			MagicKey key = MagicKey.ORIGINAL_URI;
			testParameters.put(key, "http://www.tmpStudios.com/movies");
			//Set Data object
			valueData = "Noriega Chronicles";
			//Set Test Filename
			fsdt.setPropertiesFile(testFilePath+File.separator+"testPropertiesFile.xml");
			
			fsdt.transform(valueData, testParameters);
			
			if (!f.exists()){fail("No File Generated to append to");}
		}
		
		//Set testParameters
		MagicKey key = MagicKey.ORIGINAL_URI;
		testParameters.put(key, "http://www.tmpStudios.com/producer");
		//Set Data object
		valueData = "Peter Platt";		
		
		fsdt.setPropertiesFile(testFilePath+File.separator+"testPropertiesFile.xml");
		fsdt.transform(valueData, testParameters);
		
		Properties testProp = new Properties();
		
		FileInputStream xmlPropFileIn;
		try {
			xmlPropFileIn = new FileInputStream(testFilePath+File.separator+"testPropertiesFile.xml");
			testProp.loadFromXML(xmlPropFileIn);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Properties File not generated");
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Invalid Properties file generated");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("IO errors occured");
		}
		
		String PropertyListingProducer = testProp.getProperty("http://www.tmpStudios.com/producer");
		String PropertyListingMovies = testProp.getProperty("http://www.tmpStudios.com/movies");
		
		assertEquals("Test if properties were added","Peter Platt_Noriega Chronicles",PropertyListingProducer+"_"+PropertyListingMovies);
	}
	
	@Test
	public void testURIFileSetting()
	{
		try {			
			
			URI propertiesPath = new URI(("file://"+testFilePath.replace('\\','/')+'/'+testFileName.replace('\\','/')).replace(" ", "%20"));
			String valueData = "";
			Map<MagicKey, Object> testParameters =new HashMap<MagicKey, Object>();
			FileSystemTransformer fsdt = new FileSystemTransformer();
				
			//Set testParameters
			MagicKey key = MagicKey.ORIGINAL_URI;
			testParameters.put(key, "Noriega Chronicles");
			//Set Data object
			valueData = "Noriega Chronicles";
			//Set Test Filename
			fsdt.setURIpropertiesFile(propertiesPath);		
			
			assertEquals("Object Data will stay the same", valueData, fsdt.transform(valueData, testParameters));			
			
		} catch (URISyntaxException e) {
			fail("URL improperly formated :"+e.toString());
		}
	}
	
	
	//probably a little sloppy here look into setting the f var to the other file
	@After
	public void tossTestData()
	{
		File f = new File(testFileName);
		if (f.exists())			
		{f.delete();}
		
		File f2 = new File(testFilePath+File.separator+"testPropertiesFile.xml");
		if (f2.exists())			
		{f2.delete();}
	}
}
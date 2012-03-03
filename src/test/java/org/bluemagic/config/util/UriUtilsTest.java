package org.bluemagic.config.util;

import java.net.URI;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class UriUtilsTest {

	@Test
	public void parseUriParameters() {

		URI input = null;
		try {
			input = new URI("https://www.bluemagicsource.org/configuration/project/database/username?systemType=development&appType=json");
		} catch (Throwable t) {
			Assert.fail("failed to create key");
		}

		Map<String, String> results = UriUtils.parseUriParameters(input);

		Assert.assertEquals("systemType,development expected", "development",results.get("systemType")); // Verify systemType/development
		Assert.assertEquals("appType, json expected", "json",results.get("appType")); // Verify appType/json
	}

	@Test
	public void testWithOnlyQuestionMark() {

		URI input = null;
		try {
			input = new URI("https://www.bluemagicsource.org/configuration/project/database/username?");
		} catch (Throwable t) {
			Assert.fail("failed to create key");
		}

		Map<String, String> results = UriUtils.parseUriParameters(input);

		Assert.assertTrue(results.size() == 0);
	}
	
	@Test
	public void testParsePrefixPart() {

		URI key = null;
		URI originalUri = null;
		try {
			key = new URI("file://jack.maribel.janet.my.properties.xml");
			originalUri = new URI("file://my.properties.xml");
		} catch (Throwable t) {
			Assert.fail("failed to create key");
		}

		String[] array = UriUtils.splitUriIntoPrefixParts(key, originalUri, ".");

		Assert.assertEquals("file://", array[0]);
		Assert.assertEquals("jack.maribel.janet", array[1]);
		Assert.assertEquals("my.properties.xml", array[2]);
	}
	
	@Test
	public void testParsePrefixPartNoTags() {

		URI key = null;
		URI originalUri = null;
		try {
			key = new URI("file://my.properties.xml");
			originalUri = new URI("file://my.properties.xml");
		} catch (Throwable t) {
			Assert.fail("failed to create key");
		}

		String[] array = UriUtils.splitUriIntoPrefixParts(key, originalUri, ".");

		Assert.assertEquals("file://", array[0]);
		Assert.assertEquals("", array[1]);
		Assert.assertEquals("my.properties.xml", array[2]);
	}
	
	@Test
	public void testParsePrefixPartNoScheme() {

		URI key = null;
		URI originalUri = null;
		try {
			key = new URI("my.properties.xml");
			originalUri = new URI("my.properties.xml");
		} catch (Throwable t) {
			Assert.fail("failed to create key");
		}

		String[] array = UriUtils.splitUriIntoPrefixParts(key, originalUri, ".");

		Assert.assertEquals("", array[0]);
		Assert.assertEquals("", array[1]);
		Assert.assertEquals("my.properties.xml", array[2]);
	}
	
	@Test
	public void testParsePrefixPartNoSchemeOnlyTags() {

		URI key = null;
		URI originalUri = null;
		try {
			key = new URI("jack.maribel.janet.my.properties.xml");
			originalUri = new URI("my.properties.xml");
		} catch (Throwable t) {
			Assert.fail("failed to create key");
		}

		String[] array = UriUtils.splitUriIntoPrefixParts(key, originalUri, ".");

		Assert.assertEquals("", array[0]);
		Assert.assertEquals("jack.maribel.janet", array[1]);
		Assert.assertEquals("my.properties.xml", array[2]);
	}
	
	@Test
	public void testParsePrefixPartNoTagsNull() {

		URI key = null;
		URI originalUri = null;
		try {
			key = new URI("file://my.properties.xml");
			originalUri = null;
		} catch (Throwable t) {
			Assert.fail("failed to create key");
		}

		String[] array = UriUtils.splitUriIntoPrefixParts(key, originalUri, ".");

		Assert.assertEquals("file://", array[0]);
		Assert.assertEquals("", array[1]);
		Assert.assertEquals("my.properties.xml", array[2]);
	}
	
	@Test
	public void testParsePrefixPartNoSchemeNull() {

		URI key = null;
		URI originalUri = null;
		try {
			key = new URI("my.properties.xml");
			originalUri = null;
		} catch (Throwable t) {
			Assert.fail("failed to create key");
		}

		String[] array = UriUtils.splitUriIntoPrefixParts(key, originalUri, ".");

		Assert.assertEquals("", array[0]);
		Assert.assertEquals("", array[1]);
		Assert.assertEquals("my.properties.xml", array[2]);
	}
	
	@Test
	public void testParsePrefixPartNoSchemeOnlyTagsNull() {

		URI key = null;
		URI originalUri = null;
		try {
			key = new URI("jack.maribel.janet.my.properties.xml");
			originalUri = null;
		} catch (Throwable t) {
			Assert.fail("failed to create key");
		}

		String[] array = UriUtils.splitUriIntoPrefixParts(key, originalUri, ".");

		Assert.assertEquals("", array[0]);
		Assert.assertEquals("", array[1]);
		Assert.assertEquals("jack.maribel.janet.my.properties.xml", array[2]);
	}
	
	@Test
	public void testSplitUriIntoPlaceholderParts() {

		URI key = null;
		URI originalUri = null;
		try {
			key = new URI("abc-jabooty-my.properties.xml");
			originalUri = new URI("abc-?-my.properties.xml");
		} catch (Throwable t) {
			Assert.fail("failed to create key");
		}

		String[] array = UriUtils.splitUriIntoPlaceholderParts(key, originalUri, "?");

		Assert.assertEquals("abc-", array[0]);
		Assert.assertEquals("jabooty", array[1]);
		Assert.assertEquals("-my.properties.xml", array[2]);
	}
}
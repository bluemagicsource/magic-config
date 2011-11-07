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
}
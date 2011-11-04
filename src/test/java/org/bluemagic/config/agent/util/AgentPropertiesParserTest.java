package org.bluemagic.config.agent.util;

import org.bluemagic.config.agent.ConfigAgentImpl;
import org.bluemagic.config.util.LocationBuilder;

import junit.framework.TestCase;

public class AgentPropertiesParserTest extends TestCase {

	public void testSimple() throws Exception {
		
		ConfigAgentImpl impl = new ConfigAgentImpl();
		
		LocationBuilder.parse("src/main/resources/sample-resource-agent.xml", impl);
	}
}

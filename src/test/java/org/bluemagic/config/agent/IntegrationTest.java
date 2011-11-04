package org.bluemagic.config.agent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

public class IntegrationTest extends TestCase {

	public void testname() throws Exception {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config-resource.xml");
		
	    //String agentDrivenString = (String) ctx.getBean("agentDrivenString");
	    //System.out.println(agentDrivenString);
	}
}

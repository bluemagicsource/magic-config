package org.bluemagic.config.factory;

import junit.framework.Assert;

import org.bluemagic.config.api.Decorator;
import org.bluemagic.config.api.tag.DoubleTag;
import org.bluemagic.config.api.tag.SingleTag;
import org.bluemagic.config.api.tag.TripleTag;
import org.bluemagic.config.decorator.DoubleTagDecorator;
import org.bluemagic.config.decorator.SingleTagDecorator;
import org.bluemagic.config.decorator.TripleTagDecorator;
import org.junit.Test;

public class DecoratorFactoryTest {

	@Test
	public void testBuildSingleTag() {
		
		SingleTag tag = new SingleTag("abc");
		DecoratorFactoryImpl df = (DecoratorFactoryImpl) DecoratorFactoryImpl.getInstance();
		
		Decorator decorator = df.createDecorator("tagDecorator", null, tag, null);
		
		Assert.assertTrue(decorator instanceof SingleTagDecorator);
		Assert.assertEquals(tag, ((SingleTagDecorator) decorator).getTag());
		Assert.assertEquals(Decorator.Method.PREFIX, decorator.getMethod());
	}
	
	@Test
	public void testBuildDoubleTag() {
		
		DoubleTag tag = new DoubleTag("key", "abc");
		DecoratorFactoryImpl df = (DecoratorFactoryImpl) DecoratorFactoryImpl.getInstance();
		
		Decorator decorator = df.createDecorator("tagDecorator", null, tag, null);
		
		Assert.assertTrue(decorator instanceof DoubleTagDecorator);
		Assert.assertEquals(tag, ((DoubleTagDecorator) decorator).getTag());
		Assert.assertEquals(Decorator.Method.PLACEHOLDER, decorator.getMethod());
	}
	
	@Test
	public void testBuildTripleTag() {
		
		TripleTag tag = new TripleTag("name", "pred", "abc");
		DecoratorFactoryImpl df = (DecoratorFactoryImpl) DecoratorFactoryImpl.getInstance();
		
		Decorator decorator = df.createDecorator("tagDecorator", null, tag, null);
		
		Assert.assertTrue(decorator instanceof TripleTagDecorator);
		Assert.assertEquals(tag, ((TripleTagDecorator) decorator).getTag());
		Assert.assertEquals(Decorator.Method.SUFFIX, decorator.getMethod());
	}
}

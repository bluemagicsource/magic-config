package org.bluemagic.config.decorator;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.bluemagic.config.api.MagicKey;
import org.bluemagic.config.api.tag.TripleTag;
import org.bluemagic.config.util.UriUtils;
import org.junit.Test;

public class TripleTagDecoratorTest {

	@Test
	public void simpleDecoratePrefix() {
		
		TripleTag tripleTag = new TripleTag();
		tripleTag.setNamespace("geo");
		tripleTag.setPredicate("lon");
		tripleTag.setValue("53.60913");
		
		TripleTagDecorator ttd = new TripleTagDecorator();
		ttd.setTag(tripleTag);
		
		URI key = UriUtils.toUri("abc");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("geo" + tripleTag.getNameSpacePredicateSeparator() + "lon" + tripleTag.getPredicateValueSeparator() + "53.60913.abc", ttd.decoratePrefix(key, parameters).toASCIIString());
	}
	
	@Test
	public void simpleDecoratePlaceholder() {
		
		TripleTag tripleTag = new TripleTag();
		tripleTag.setNamespace("geo");
		tripleTag.setPredicate("lon");
		tripleTag.setValue("53.60913");
		
		TripleTagDecorator ttd = new TripleTagDecorator();
		ttd.setTag(tripleTag);
		
		URI key = UriUtils.toUri("abc/?/jackster");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("abc/geo" + tripleTag.getNameSpacePredicateSeparator() + "lon" + tripleTag.getPredicateValueSeparator() + "53.60913/jackster", ttd.decoratePlaceholder(key, "?", parameters).toASCIIString());
	}
	
	@Test
	public void simpleDecorateSuffix() {
		
		TripleTag tripleTag = new TripleTag();
		tripleTag.setNamespace("geo");
		tripleTag.setPredicate("lon");
		tripleTag.setValue("53.60913");
		
		TripleTagDecorator ttd = new TripleTagDecorator();
		ttd.setTag(tripleTag);
		
		URI key = UriUtils.toUri("abc");
		Map<MagicKey, Object> parameters = new HashMap<MagicKey, Object>();
		
		assertEquals("abc?geo" + tripleTag.getNameSpacePredicateSeparator() + "lon" + tripleTag.getPredicateValueSeparator() + "53.60913", ttd.decorateSuffix(key, parameters).toASCIIString());
	}
}

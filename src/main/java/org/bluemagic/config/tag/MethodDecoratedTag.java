package org.bluemagic.config.tag;

import org.bluemagic.config.api.Decorator.Method;
import org.bluemagic.config.api.tag.Tag;

public class MethodDecoratedTag implements Tag {

	private Tag tag;
	private Method method;
	
	public MethodDecoratedTag(Tag tag, Method method) {
		this.tag = tag;
		this.method = method;
	}
	
	public Tag getTag() {
		return tag;
	}
	
	public Method getMethod() {
		return method;
	}
}

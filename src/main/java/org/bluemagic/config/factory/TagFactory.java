package org.bluemagic.config.factory;

import java.util.Map;

import org.bluemagic.config.tag.MethodDecoratedTag;

/**
 * A basic interface for factory that creates Tag objects
 */
public interface TagFactory {

	public MethodDecoratedTag createTag(String tagType, Map<String, String> attributes, String tagValue);
}

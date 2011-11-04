package org.bluemagic.config.decorator.tags;

public class TripleTag extends SingleTag {

	private String namespace;
	
	private String predicate;

	public TripleTag() { }
	
	public TripleTag(String namespace, String predicate, String value) {
		this.namespace = namespace;
		this.predicate = predicate;
		this.value = value;
	}
	
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getNamespace() {
		return namespace;
	}
}

package org.bluemagic.config.decorator.tags;

/**
 * This is the Java representation of a tripletag or a machinetag
 * It can be used to describe enhanced tagging information
 * 
 * For example geo:latitude=83.3 where "geo" is the namespace, "latitude"
 * id the predicate and "83.3" is the value
 *
 */
public class TripleTag extends SingleTag {
	
	private String nameSpacePredicateSeparator = ":";
	
	private String predicateValueSeparator = "=";

	private String namespace = "";
	
	private String predicate = "";

	public TripleTag() { }
	
	public TripleTag(String namespace, String predicate, String value) {
		this.namespace = namespace;
		this.predicate = predicate;
		this.value = value;
	}
	
	public TripleTag(String namespace, String predicate, String valuePrefix, String value, String valueSuffix) {
		
		this.namespace = namespace;
		this.predicate = predicate;
		
		setPrefix(valuePrefix);
		this.value = value;
		setSuffix(valueSuffix);
	}
	
	@Override
	public boolean equals(Object obj) {
	
		boolean equals = false;
		
		if (obj instanceof TripleTag) {
			TripleTag other = (TripleTag) obj;
			
			String otherNamespace = other.getNamespace();
			String otherPredicate = other.getPredicate();
			String otherValue = other.getValue();
			
			if (otherNamespace.equals(this.getNamespace()) && otherPredicate.equals(this.getPredicate()) && otherValue.equals(this.getValue())) {
				equals = true;
			}
		}
		return equals;
	}
	
	@Override
	public String toString() {
		
		StringBuilder b = new StringBuilder();
		b.append(this.namespace);
		b.append(this.nameSpacePredicateSeparator);
		b.append(this.predicate);
		b.append(this.predicateValueSeparator);
		b.append(getValue());

		return b.toString();
	}
	
	@Override
	public int hashCode() {
		return namespace.hashCode() + predicate.hashCode() + prefix.hashCode() + value.hashCode() + suffix.hashCode();
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

	public String getNameSpacePredicateSeparator() {
		return nameSpacePredicateSeparator;
	}

	public void setNameSpacePredicateSeparator(
			String nameSpacePredicateSeparator) {
		this.nameSpacePredicateSeparator = nameSpacePredicateSeparator;
	}

	public String getPredicateValueSeparator() {
		return predicateValueSeparator;
	}

	public void setPredicateValueSeparator(String predicateValueSeparator) {
		this.predicateValueSeparator = predicateValueSeparator;
	}
}

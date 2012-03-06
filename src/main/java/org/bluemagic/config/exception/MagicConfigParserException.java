package org.bluemagic.config.exception;

public class MagicConfigParserException extends RuntimeException {

	private static final long serialVersionUID = 6342919895946844764L;

	public MagicConfigParserException(String message) {
		super(message);
	}

	public MagicConfigParserException(String message, Throwable t) {
		super(message, t);
	}
}

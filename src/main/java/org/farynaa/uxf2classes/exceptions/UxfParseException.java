package org.farynaa.uxf2classes.exceptions;

/**
 * @author devil
 *
 */
public class UxfParseException extends AbstractCriticalException {

	private static final String MESSAGE = "error: Invalid '.uxf' file.";
	
	public UxfParseException() {
		super(MESSAGE);
	}
}

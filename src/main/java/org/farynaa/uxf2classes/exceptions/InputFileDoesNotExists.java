package org.farynaa.uxf2classes.exceptions;

/**
 * @author devil
 *
 */
public class InputFileDoesNotExists extends AbstractCriticalException {

	private static final String MESSAGE = "error: Input file does not exists.";
	
	public InputFileDoesNotExists() {
		super(MESSAGE);
	}
	
}

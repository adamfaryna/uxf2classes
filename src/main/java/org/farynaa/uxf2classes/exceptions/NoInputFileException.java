package org.farynaa.uxf2classes.exceptions;

/**
 * @author devil
 *
 */
public class NoInputFileException extends AbstractCriticalException {

	private static final String MESSAGE = "error: Input file cxf must be provided.";
	
	public NoInputFileException() {
		super(MESSAGE);
	}
	
}

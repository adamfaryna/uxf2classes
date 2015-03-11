package org.farynaa.uxf2classes.exceptions;

/**
 * @author devil
 *
 */
public class InvalidInputFileExtention extends AbstractCriticalException {

	private static final String MESSAGE = "error: Invalid input file extention. Input file should have '.ucf' extention.";
	
	public InvalidInputFileExtention() {
		super(MESSAGE);
	}
	
}

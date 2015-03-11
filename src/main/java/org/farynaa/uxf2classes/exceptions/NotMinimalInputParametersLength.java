package org.farynaa.uxf2classes.exceptions;

/**
 * @author devil
 *
 */
public class NotMinimalInputParametersLength extends AbstractCriticalException {

	private static final String MESSAGE = "error: Program requires at least one parameter - uxf file.";
	
	public NotMinimalInputParametersLength() {
		super(MESSAGE);
	}
	
}

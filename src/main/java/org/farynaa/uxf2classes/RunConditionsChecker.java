package org.farynaa.uxf2classes;

import java.io.File;

import org.farynaa.uxf2classes.exceptions.InvalidInputFileExtention;
import org.farynaa.uxf2classes.exceptions.NoInputFileException;
import org.farynaa.uxf2classes.exceptions.NotMinimalInputParametersLength;

/**
 * @author devil
 *
 */
public class RunConditionsChecker {

	private String [] inputParameters;
	
	private static final int INPUT_FILENAME_PARAMETER_INDEX = 1;
	private static final int MINIMAL_PARAMETERS_NUMBER = 2;
	private static final String VALID_INPUT_FILE_EXTENTION = ".ucf";

	
	public RunConditionsChecker(String [] inputParameters) {
		this.inputParameters = inputParameters;
	}
	
	public void validateInputParameters() {
		validateInputParametersLength();
		validateInputFileExists();
		validateInputFileExtention();
	}
	
	private void validateInputParametersLength() {
		if (isMinimalInputParametersLength()) {
			throw new NotMinimalInputParametersLength();
		}
	}
	
	private void validateInputFileExtention() {
		if (isInputFileExtentionCorrect()) {
			throw new InvalidInputFileExtention();
		}
	}
	
	private boolean isInputFileExtentionCorrect() {
		return getInputFilename().endsWith(VALID_INPUT_FILE_EXTENTION);
	}
	
	private void validateInputFileExists() {
		if (isInputFileExists()) {
			throw new NoInputFileException();
		}
	}
	
	private boolean isInputFileExists() {
		File inputfile = new File(getInputFilename());
		return inputfile.exists();
	}
	
	private String getInputFilename() {
		return inputParameters[INPUT_FILENAME_PARAMETER_INDEX];
	}
	
	private boolean isMinimalInputParametersLength() {
		return inputParameters.length >= MINIMAL_PARAMETERS_NUMBER;
	}
}

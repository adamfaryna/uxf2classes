package org.farynaa.uxf2classes;

import java.util.List;

import org.farynaa.uxf2classes.exceptions.AbstractCriticalException;
import org.farynaa.uxf2classes.xml.ClassMetadata;
import org.farynaa.uxf2classes.xml.UxfReader;

public class Main {

	public static void main(String[] args) {
		validateRunConditions(args);

		try {
			List<ClassMetadata> extractClassDataForInput = UxfReader.extractClassDataForInput(args[1]);
			
			
			
		} catch (AbstractCriticalException e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	private static void validateRunConditions(String [] runConditions) {
		RunConditionsChecker runConditionsChecker = new RunConditionsChecker(runConditions);
		runConditionsChecker.validateInputParameters();
	}
}

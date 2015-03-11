package org.farynaa.uxf2classes;

import java.io.File;
import java.io.IOException;

/**
 * @author devil
 *
 */
public class PackageBuilder {
	
	private static final String OUTPUT_FOLDER_NAME = "uxf2classes_output";

	private File createOutputFolder() {
		try {
			File outputfolder = File.createTempFile("uxf", "classes");
			return outputfolder;
			
		} catch (IOException e) {
			System.out.println("error: Couldn't create output folder.");
			System.exit(1);
			return null;
		}
	}
	
	
}

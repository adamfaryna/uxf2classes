package org.farynaa.uxf2classes.xml;

import java.util.ArrayList;
import java.util.List;

import org.farynaa.uxf2classes.exceptions.InputFileDoesNotExists;
import org.farynaa.uxf2classes.exceptions.NoInputFileException;
import org.farynaa.uxf2classes.exceptions.UxfParseException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author devil
 *
 */
public class UxfReaderTest {

	private static final String SAMPLE_CORRECT_UXF_FILENAME = "sampleCorrectData.uxf";
	private static final String SAMPLE_CORUPTED_UXF_FILENAME = "sampleCorruptedData.uxf";
	private static final String SAMPLE_NOT_EXISTING_FILENAME = "notExisting.uxf"; 
	
	@Test(expected = NoInputFileException.class)
	public void passNullInputFilename() {
		UxfReader.extractClassDataForInput(null);
	}

	@Test(expected = NoInputFileException.class)
	public void passEmptyInputFilename() {
		UxfReader.extractClassDataForInput("");
	}
	
	@Test(expected = InputFileDoesNotExists.class)
	public void passNotExistingInputFilename() {
		UxfReader.extractClassDataForInput(SAMPLE_NOT_EXISTING_FILENAME);
	}
	
	@Test(expected = UxfParseException.class)
	public void openInvalidUxfFile() {
		UxfReader.extractClassDataForInput(SAMPLE_CORUPTED_UXF_FILENAME);
	}

	@Test
	public void openCorrectUxfFileWithDiagramClasses() {
		List<ClassMetadata> actualResult = UxfReader.extractClassDataForInput(SAMPLE_CORRECT_UXF_FILENAME);
		assertGeneratedClasses(actualResult);
	}
	
	private void assertGeneratedClasses(List<ClassMetadata> actualResults) {
		List<ClassMetadata> expectedResults = createExpectedClassMetadataDataset();
		Assert.assertEquals(expectedResults, actualResults);
	}
	
	private List<ClassMetadata> createExpectedClassMetadataDataset() {
		List<ClassMetadata> expectedResult = new ArrayList<ClassMetadata>();
		
		ClassMetadata classMetadata1 = new ClassMetadata();
		classMetadata1.setClassName("Subscriber");
		classMetadata1.getProperties().add(new ClassMetadataParameter("Long", "id"));
		classMetadata1.getProperties().add(new ClassMetadataParameter("String", "name"));
		expectedResult.add(classMetadata1);
		
		ClassMetadata classMetadata2 = new ClassMetadata();
		classMetadata2.setClassName("SubscriberGroup");
		classMetadata2.getProperties().add(new ClassMetadataParameter("Long", "id"));
		classMetadata2.getProperties().add(new ClassMetadataParameter("String", "surname"));
		expectedResult.add(classMetadata2);
		
		return expectedResult;
	}
}

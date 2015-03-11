package org.farynaa.uxf2classes.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.farynaa.uxf2classes.Utils;
import org.farynaa.uxf2classes.exceptions.InputFileDoesNotExists;
import org.farynaa.uxf2classes.exceptions.InvalidInputFileExtention;
import org.farynaa.uxf2classes.exceptions.NoInputFileException;
import org.farynaa.uxf2classes.exceptions.UxfParseException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * @author devil
 *
 */
public class UxfReader {

	private SAXBuilder saxBuilder = new SAXBuilder();

	private Stack<ClassMetadata> classes = new Stack<ClassMetadata>();

	private static final String CLASS_TYPE_NODE_VALUE = "com.umlet.element.Class";
	private static final String CLASS_METADATA_NODE_NAME = "panel_attributes";
	private static final String CLASS_ELEMENT_NODE_NAME = "element";
	private static final String CLASS_TYPE_NODE_NAME = "type";

	private static final int PARAM_NAME_SPLIT_INDEX = 0;
	private static final int PARAM_TYPE_SPLIT_INDEX = 1;
	
	private static final String PARAMETER_PREFIX = "-";
	private static final String DATA_SECTION_SEPARATOR = "--";
	private static final String PARAMETER_NAME_AND_TYPE_SEPARATOR = ": ";
	
	private static final String VALID_INPUT_FILE_EXTENTION = ".ucf";

	public static List<ClassMetadata> extractClassDataForInput(String filename) {
		validateInputFile(filename);
		InputStream fileInputStream = getFileInputStream(filename);
		return new UxfReader().extract(fileInputStream);
	}
	
	public List<ClassMetadata> extract(InputStream fileInputStream) {
		List<Element> classElementNodes = extractClassElementsFromCxf(fileInputStream);

		for (Element classElementNode : classElementNodes) {
			if (isElementTypeClass(classElementNode)) {
				createClassMetadataObject();
				fillClassMetadataObject(classElementNode);
			}
		}
		
		return new ArrayList<ClassMetadata>(classes);
	}

	private List<Element> extractClassElementsFromCxf(InputStream fileInputStream) {
		List<Element> classCandidatesElements = extractClassCandidatesElements(fileInputStream);
		List<Element> classElements = filterNoClassElementsNodes(classCandidatesElements);
		return classElements;
	}

	private List<Element> extractClassCandidatesElements(InputStream fileInputStream) {
		Element rootNode = extractRootNode(fileInputStream);
		List<Element> classCandidatesNodes = extractClassCandidatesNodes(rootNode);
		return classCandidatesNodes;
	}

	private Element extractRootNode(InputStream fileInputStream) {
		Document xmlDocument = createXmlDocumentFromFile(fileInputStream);
		Element rootElement = xmlDocument.getRootElement();
		return rootElement;
	}
	
	private Document createXmlDocumentFromFile(InputStream inputStream) {
		try {
			Document xmlDocument = saxBuilder.build(inputStream);
			return xmlDocument;

		} catch (JDOMException e) {
			throw new UxfParseException();
			
		} catch (IOException e) {
			throw new NoInputFileException();
		} 		
	}
	
	private static void validateInputFile(String filename) {
		validateInput(filename);
		validateExtentionIn(filename);
	}
	
	private static void validateInput(String filename) {
		if (filename == null || filename.isEmpty()) {
			throw new NoInputFileException();
		}
	}
	
	private static InputStream getFileInputStream(String filename) {
		InputStream fileInputStream = UxfReader.class.getClassLoader().getResourceAsStream(filename);
		if (fileInputStream == null) {
			throw new InputFileDoesNotExists();
		}
		return fileInputStream;
	}
	
	private static void validateExtentionIn(String filename) {
		if (filename.endsWith(VALID_INPUT_FILE_EXTENTION)) {
			throw new InvalidInputFileExtention();
		}
	}

	private List<Element> extractClassCandidatesNodes(Element rootNode) {
		List<Element> classCandidatesNodes = rootNode.getChildren(CLASS_ELEMENT_NODE_NAME);
		return classCandidatesNodes;
	}

	private List<Element> filterNoClassElementsNodes(List<Element> classCandidatesNodes) {
		List<Element> classCandidatesNodesCopy = new ArrayList<Element>(classCandidatesNodes);
		Iterator<Element> classCandidatesNodesIterator = classCandidatesNodesCopy.iterator();

		while (classCandidatesNodesIterator.hasNext()) {
			filterNoClassElementNodes(classCandidatesNodesIterator);
		}

		return classCandidatesNodesCopy;
	}

	private void filterNoClassElementNodes(Iterator<Element> classCandidatesNodesIterator) {
		Element next = classCandidatesNodesIterator.next();
		if (!isElementTypeClass(next)) {
			classCandidatesNodesIterator.remove();
		}
	}

	private void createClassMetadataObject() {
		ClassMetadata classMetadata = new ClassMetadata();
		classes.push(classMetadata);
	}

	private ClassMetadata getCurrentClassMetadata() {
		return classes.peek();
	}

	private boolean isElementTypeClass(Element elementTypeNode) {
		String elementTypeValue = elementTypeNode.getChildTextTrim(CLASS_TYPE_NODE_NAME);
		return CLASS_TYPE_NODE_VALUE.equals(elementTypeValue);
	}

	private void fillClassMetadataObject(Element classElementNode) {
		String classMetadataSection = extractClassMetadataSectionFromElementNode(classElementNode);
		fillClassMetadataClassName(classMetadataSection);
		fillClassMetadataParameters(classMetadataSection);
	}

	private String extractClassMetadataSectionFromElementNode(Element classElementNode) {
		String classMetadataSection = classElementNode
				.getChildTextTrim(CLASS_METADATA_NODE_NAME);
		return classMetadataSection;
	}

	private void fillClassMetadataClassName(String classMetadataSection) {
		String className = extractClassName(classMetadataSection);
		ClassMetadata currentClassMetadata = getCurrentClassMetadata();
		currentClassMetadata.setClassName(className);
	}

	private String extractClassName(String classMetadataSection) {
		int indexOfDash = findIndexOfNearestDash(classMetadataSection);
		String classname = classMetadataSection.substring(0, indexOfDash);
		classname = Utils.removeEndOfTheLine(classname);
		return classname;
	}

	private int findIndexOfNearestDash(String classMetadataSection) {
		int indexOfDash = classMetadataSection.indexOf(DATA_SECTION_SEPARATOR);
		return indexOfDash;
	}

	private void fillClassMetadataParameters(String classMetadataSection) {
		String[] parametersNameAndTypePairs = extractParametersNameAndTypePairs(classMetadataSection);

		for (String parameterNameAndTypePair : parametersNameAndTypePairs) {
			extractParameter(parameterNameAndTypePair);
		}
	}

	private String[] extractParametersNameAndTypePairs(String classMetadataSection) {
		String parametersSection = extractParametersSection(classMetadataSection);
		String[] parametersNameAndTypePairs = separateParametersIntoNameAndTypePairs(parametersSection);
		return parametersNameAndTypePairs;
	}

	private String[] separateParametersIntoNameAndTypePairs(String parametersSection) {
		String[] parametersNameAndTypePairs = parametersSection.split(PARAMETER_PREFIX);
		parametersNameAndTypePairs = removeFirstEmptyElement(parametersNameAndTypePairs);
		return parametersNameAndTypePairs;
	}

	private String[] removeFirstEmptyElement(String[] parametersNameAndTypePairs) {
		return Utils.removeFirstElementFromArray(parametersNameAndTypePairs);
	}

	private String extractParametersSection(String classMetadataSection) {
		int indexOfParametersSections = calculateIndexOfParametersSection(classMetadataSection);
		return classMetadataSection.substring(indexOfParametersSections);
	}

	private int calculateIndexOfParametersSection(String classMetadataSection) {
		return classMetadataSection.indexOf(DATA_SECTION_SEPARATOR) + DATA_SECTION_SEPARATOR.length();
	}

	private void extractParameter(String singleParamString) {
		String[] paramNameAndTypePair = paramNameAndTypePairFromSingleParamString(singleParamString);
		String paramName = extractParamNameFromNameAndTypePair(paramNameAndTypePair);
		String paramType = extractParamTypeFromNameAndTypePair(paramNameAndTypePair);

		ClassMetadataParameter classMetadataParameter = new ClassMetadataParameter(paramType, paramName);

		ClassMetadata currentClassMetadata = getCurrentClassMetadata();
		currentClassMetadata.getProperties().add(classMetadataParameter);
	}

	private String[] paramNameAndTypePairFromSingleParamString(String singleParamString) {
		return singleParamString.split(PARAMETER_NAME_AND_TYPE_SEPARATOR);
	}

	private String extractParamNameFromNameAndTypePair(String[] paramNameAndTypePair) {
		return paramNameAndTypePair[PARAM_NAME_SPLIT_INDEX];
	}

	private String extractParamTypeFromNameAndTypePair(String[] paramNameAndTypePair) {
		String paramType = paramNameAndTypePair[PARAM_TYPE_SPLIT_INDEX];
		String paramTypeWithoutNewLine = Utils.removeEndOfTheLine(paramType);
		return paramTypeWithoutNewLine;
	}
}

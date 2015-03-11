package org.farynaa.uxf2classes.xml;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

import org.farynaa.uxf2classes.Utils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author devil
 *
 */
public class XmlParser extends DefaultHandler {

	private Stack<ClassMetadata> classes = new Stack<ClassMetadata>();
	private Map<String, String> extractedParameters = new LinkedHashMap<String, String>();
	
//	private static final String CLASS
	private static final String XML_CLASS_TYPE = "com.umlet.element.Class";
	private static final String PARAMETER_PREFIX = "-";
	private static final String DATA_SECTION_SEPARATOR = "--";
	private static final String PARAMETER_NAME_AND_TYPE_SEPARATOR = ": ";
	
	public XmlParser() {
		// TODO Auto-generated constructor stub		
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ("type".equals(qName)) {
			ClassMetadata classMetadata = new ClassMetadata();
			
			
			classes.push(classMetadata);
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		String string = new String(ch, start, length).trim();

		String className = extractClassName(string);
		
		ClassMetadata currentClass = classes.peek();
		currentClass.setClassName(className);

		extractParameters(string);
	}
	
	private String extractClassName(String string) {
		int indexOfDash = string.indexOf(DATA_SECTION_SEPARATOR);
		return string.substring(0, indexOfDash);
	}
	
	private void extractParameters(String dataSection) {
		String[] parametersNameAndTypePairs = extractParametersNameAndTypePairs(dataSection);
		
		for (String parameterNameAndTypePair : parametersNameAndTypePairs) {
			extractParameter(parameterNameAndTypePair);
		}
	}
	
	private String [] extractParametersNameAndTypePairs(String dataSection) {
		String parametersSection = extractParametersSection(dataSection);
		String [] parametersNameAndTypePairs = parametersSection.split(PARAMETER_PREFIX);
		String [] parametersNameAndTypePairsNew = Utils.removeFirstElementFromArray(parametersNameAndTypePairs);
		return parametersNameAndTypePairsNew;
	}
	
	private int calculateIndexOfParametersSection(String dataSection) {
		return dataSection.indexOf(DATA_SECTION_SEPARATOR) + DATA_SECTION_SEPARATOR.length(); 
	}
	
	private String extractParametersSection(String dataSection) {
		int indexOfParametersSections = calculateIndexOfParametersSection(dataSection);
		return dataSection.substring(indexOfParametersSections);
	}
	
	private void extractParameter(String content) {
		String[] paramNameType = content.split(PARAMETER_NAME_AND_TYPE_SEPARATOR);
		String paramName = paramNameType[0];
		String paramType = paramNameType[1];
		extractedParameters.put(paramName, paramType);
	}
}

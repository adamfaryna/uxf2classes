package org.farynaa.uxf2classes;

import java.util.Arrays;

/**
 * @author devil
 *
 */
public final class Utils {

	
	public static <T> T [] removeFirstElementFromArray(T [] array) {
		return Arrays.copyOfRange(array, 1, array.length);
	}
	
	public static String removeEndOfTheLine(String string) {
		return string.replaceAll("(\\r|\\n)", "");
	}
	
}

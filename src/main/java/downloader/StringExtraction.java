package downloader;

import org.apache.commons.lang3.StringUtils;

import static downloader.SpecialCharacterUtil.replaceQuotation;

public class StringExtraction {
	
	private static final String JSON_ARRAY_START = "[{";

	private StringExtraction() {
		// Util Type Class
	}
	
	public static String extraxtJsonArray(String inputString) {
		String startCorrected = JSON_ARRAY_START + StringUtils.substringAfter(inputString, JSON_ARRAY_START);
		String jsonArray = startCorrected.substring(0, endIndex(startCorrected));
		return replaceQuotation(jsonArray);
	}

	private static int endIndex(String input) {
		return findMatchingClosingBracket(input) + 1;
	}
	public static int findMatchingClosingBracket(String str) {
		int count = 0;
		char[] charArray = str.toCharArray();

		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == '[') {
				count++;
			} else if (charArray[i] == ']') {
				count--;
				if (count == 0) {
					return i;
				}
			}
		}
		throw new IllegalArgumentException("Could not parse 'Trackinfo' JSON Array from String");
	}

}

package downloader;

import static downloader.SpecialCharacterUtil.replaceQuotation;

public class StringExtraction {
	
	private static final String JSON_ARRAY_START = "[{";
	private static final String JSON_ARRAY_FINISH = "}]";

	private StringExtraction() {
		// Util Type Class
	}
	
	public static String extraxtJsonArray(String inputString) {
		String startCorrected = inputString.substring(inputString.indexOf(JSON_ARRAY_START));
		int b = startCorrected.indexOf(JSON_ARRAY_FINISH) + JSON_ARRAY_FINISH.length();
		String jsonArray = startCorrected.substring(0, b);
		return replaceQuotation(jsonArray);
	}

}

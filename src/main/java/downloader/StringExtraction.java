package downloader;

import static downloader.SpecialCharacterUtil.replaceQuotation;

public class StringExtraction {
	
	private static final String JSON_ARRAY_START = "[{";
	private static final String JSON_ARRAY_FINISH = "}]";

	private StringExtraction() {
		// Util Type Class
	}
	
	public static String extraxtJsonArray(String inputString) {
		int a = inputString.indexOf(JSON_ARRAY_START);
		int b = inputString.indexOf(JSON_ARRAY_FINISH, a) + JSON_ARRAY_FINISH.length();
		String jsonArray = inputString.substring(a, b);
		return replaceQuotation(jsonArray);
	}

}

package downloader;

public class SpecialCharacterUtil {
	
	private SpecialCharacterUtil() {
		// Util Class
	}
	
	public static String replaceSpecialCharacters(String stringToCleanUp) {
		stringToCleanUp = replaceAmpersand(stringToCleanUp);
		stringToCleanUp = replaceApostrophe(stringToCleanUp);
		return stringToCleanUp;
	}

	private static String replaceApostrophe(String stringToCleanUp) {
		return stringToCleanUp.replaceAll("&#39;", "'");
	}

	private static String replaceAmpersand(String stringToCleanUp) {
		return stringToCleanUp.replaceAll("&amp;", "&");
	}

}

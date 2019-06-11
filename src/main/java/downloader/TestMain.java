package downloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class TestMain {

	public static void main(String[] args) throws IOException {
		String testFile = "src/test/resources/testURL1.html";
		File testUrl = new File(testFile);
		URL url2 = testUrl.toURI().toURL();
		
		String home = System.getProperty("user.home");
		String saveFileDirectory = home + "/Downloads/Privat/Musik";
		
	}

}

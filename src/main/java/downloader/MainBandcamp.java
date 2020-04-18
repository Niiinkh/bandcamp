package downloader;

import java.io.IOException;
import java.net.URL;

public class MainBandcamp {

	public static void main(String[] args) throws IOException {
		String saveFileDirectory = System.getProperty("user.home") + "/Downloads/Privat/Musik";
		
		URL url = new URL("https://tops.bandcamp.com/album/i-feel-alive");
		DownloadFiles.runDownload(url, saveFileDirectory);
	}

}

package downloader;

import java.io.IOException;
import java.net.URL;

public class MainBandcamp {

	public static void main(String[] args) throws IOException {
		String bandcampUrl = args[0];
		String saveFileDirectory = System.getProperty("user.home") + "/Downloads/Privat/Musik";
		URL url = new URL(bandcampUrl);
		DownloadFiles.runDownload(url, saveFileDirectory);
	}

}

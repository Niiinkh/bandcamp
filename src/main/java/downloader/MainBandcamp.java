package downloader;

import java.net.MalformedURLException;
import java.net.URL;

public class MainBandcamp {

	public static void main(String[] args) throws MalformedURLException {
		String saveFileDirectory = System.getProperty("user.home") + "/Downloads/Privat/Musik";
		
		URL url = new URL("https://m-ward.bandcamp.com/album/more-rain");
		DownloadFiles.runDownload(url, saveFileDirectory);
	}

}

package downloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainBandcamp {

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			throw new IllegalArgumentException("Mama needs her url");
		}
		String saveFileDirectory = System.getProperty("user.home") + "/Downloads/bandcamp";
		new File(saveFileDirectory).mkdirs();

		String bandcampUrl = args[0];
		new BandcampDownload().runDownload(new URL(bandcampUrl), saveFileDirectory);
	}

}

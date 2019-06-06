package downloader;

public class MainBandcamp {

	public static void main(String[] args) {
		String URLAdress = "https://siskiyou.bandcamp.com/album/not-somewhere";
		String saveFileDirectory = "C:/Users/AAboueldahab/Downloads/Privat/Musik";
		DownloadFiles.runDownload(URLAdress, saveFileDirectory);
	}

}

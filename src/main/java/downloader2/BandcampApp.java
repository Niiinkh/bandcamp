package downloader2;


import downloader2.adapter.in.BandcampDownloader;
import downloader2.application.DownloadService;
import downloader2.domain.Album;

import java.io.IOException;

public class BandcampApp {

    private static DownloadService downloader = new BandcampDownloader();

    public static void main(String[] args) throws IOException {
        String url = "https://bigthief.bandcamp.com/album/dragon-new-warm-mountain-i-believe-in-you?from=footer-cc-a353779083";
        //String url = "https://jackterriclothfoundation.bandcamp.com/releases";

        Album album = downloader.requestDownload(url);

        System.out.println(album);

    }


}

package downloader;


import downloader.adapter.out.albuminfo.BandcampInformationFetcher;
import downloader.adapter.out.albuminfo.JsoupWrapper;
import downloader.adapter.out.downloader.BandcampAlbumDownloader;
import downloader.adapter.out.downloader.BandcampTrackDownloader;
import downloader.application.InformationFetcher;
import downloader.domain.Album;

import java.io.IOException;

import static downloader.adapter.out.downloader.FileSystemUtil.homeDirectory;

public class BandcampApp {

    public static void main(String[] args) throws IOException {
        String url = "https://adriannelenker.bandcamp.com/album/hours-were-the-birds";

        InformationFetcher albumInfo = new BandcampInformationFetcher(new JsoupWrapper());
        Album album = albumInfo.fetch(url);

        String baseDirectory = homeDirectory() + "/Downloads/bandcamp";
        BandcampAlbumDownloader downloader = new BandcampAlbumDownloader(new BandcampTrackDownloader(), baseDirectory);
        downloader.download(album);
    }

}

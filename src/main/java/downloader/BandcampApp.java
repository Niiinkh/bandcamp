package downloader;


import downloader.adapter.out.albuminfo.BandcampInformationFetcher;
import downloader.adapter.out.albuminfo.JsoupWrapper;
import downloader.adapter.out.downloader.BandcampAlbumDownloader;
import downloader.adapter.out.downloader.BandcampTrackDownloader;
import downloader.application.InformationFetcher;
import downloader.domain.Album;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static downloader.adapter.out.downloader.FileSystemUtil.homeDirectory;

public class BandcampApp {

    private static final Logger logger = LoggerFactory.getLogger(BandcampApp.class);

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            logger.error("Es muss eine URL angegeben werden");
            return;
        }

        String url = args[0];

        InformationFetcher albumInfo = new BandcampInformationFetcher(new JsoupWrapper());
        Album album = albumInfo.fetch(url);

        String baseDirectory = homeDirectory() + "/Downloads/bandcamp";
        BandcampAlbumDownloader downloader = new BandcampAlbumDownloader(new BandcampTrackDownloader(), baseDirectory);
        downloader.download(album);
    }

}

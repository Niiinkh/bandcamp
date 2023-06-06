package downloader2;


import downloader2.adapter.out.albuminfo.BandcampInformationFetcher;
import downloader2.adapter.out.albuminfo.JsoupWrapper;
import downloader2.adapter.out.downloader.BandcampAlbumDownloader;
import downloader2.adapter.out.downloader.BandcampTrackDownloader;
import downloader2.application.InformationFetcher;
import downloader2.domain.Album;

import java.io.IOException;

import static downloader2.adapter.out.downloader.FileSystemUtil.homeDirectory;

public class BandcampApp {

    public static void main(String[] args) throws IOException {
        /*
        https://jackterriclothfoundation.bandcamp.com/releases;
        https://theduesseldorfduesterboys.bandcamp.com/album/duo-duo
        https://bigthief.bandcamp.com/album/dragon-new-warm-mountain-i-believe-in-you
        https://spanishlovesongs.bandcamp.com/album/schmaltz
        https://trikont.bandcamp.com/album/malva-das-grell-in-meinem-kopf
        https://theduesseldorfduesterboys.bandcamp.com/album/im-winter-ep
        https://theduesseldorfduesterboys.bandcamp.com/album/nenn-mich-musik
        https://internationalmusicband.bandcamp.com/album/ententraum
        https://internationalmusicband.bandcamp.com/album/mein-schweiss
        https://trikont.bandcamp.com/album/philip-bradatsch
        */

        String url = "https://theduesseldorfduesterboys.bandcamp.com/album/duo-duo";

        InformationFetcher albumInfo = new BandcampInformationFetcher(new JsoupWrapper());
        Album album = albumInfo.fetch(url);

        String baseDirectory = homeDirectory() + "/Downloads/bandcamp";
        BandcampAlbumDownloader downloader = new BandcampAlbumDownloader(new BandcampTrackDownloader(), baseDirectory);
        downloader.download(album);
    }

}

package downloader2;


import downloader2.adapter.in.BandcampDownloader;
import downloader2.application.DownloadService;
import downloader2.domain.Album;

import java.io.IOException;

public class BandcampApp {

    private static DownloadService downloader = new BandcampDownloader();

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

        String url = "https://bigthief.bandcamp.com/album/dragon-new-warm-mountain-i-believe-in-you";
        Album album = downloader.requestDownload(url);

        System.out.println(album);

    }


}

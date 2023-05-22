package downloader2;


import downloader2.domain.Album;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class BandcampApp {

    public static void main(String[] args) throws IOException {
        //String url = "https://bigthief.bandcamp.com/album/dragon-new-warm-mountain-i-believe-in-you?from=footer-cc-a353779083";
        String url = "https://jackterriclothfoundation.bandcamp.com/releases";
        Document document = Jsoup.connect(url).get();

        Elements data = document.select("script[data-tralbum]");
        String jsonString = data.attr("data-tralbum");

        JSONObject json = new JSONObject(jsonString);
        Album album = new AlbumMapper().mapFromJson(json);

        System.out.println(jsonString);


    }


}

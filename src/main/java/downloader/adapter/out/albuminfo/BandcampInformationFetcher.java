package downloader.adapter.out.albuminfo;

import downloader.application.InformationFetcher;
import downloader.domain.Album;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class BandcampInformationFetcher implements InformationFetcher {

    private final JsoupWrapper jsoup;

    public BandcampInformationFetcher(JsoupWrapper jsoup) {
        this.jsoup = jsoup;
    }

    @Override
    public Album fetch(String url) throws IOException {
        Document document = jsoup.connect(url);

        Elements data = document.select("script[data-tralbum]");
        String jsonString = data.attr("data-tralbum");

        JSONObject json = new JSONObject(jsonString);
        return new AlbumMapper().mapFromJson(json);
    }
}

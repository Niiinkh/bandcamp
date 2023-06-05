package downloader2.adapter.out.albuminfo;

import downloader2.domain.Album;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class BandcampInformationFetcherTest {

    private static final String PATH = "src/test/resources/";

    private BandcampInformationFetcher informationFetcher;
    private Document document;

    @BeforeEach
    void setup() {
        informationFetcher = new BandcampInformationFetcher(new TestJsoupWrapper());
    }

    @Test
    void integrationTest() throws Exception {
        document = Jsoup.parse(new File(PATH + "the-duesseldorf-duesterboys_im-winter.html"));
        Album album = informationFetcher.fetch("notRelevantForTest");

        assertThat(album.getTitle()).isEqualTo("Im Winter EP");
        assertThat(album.getArtist()).isEqualTo("The Düsseldorf Düsterboys");
        assertThat(album.getReleaseDate()).isEqualTo("2022-12-02");

        assertThat(album.getTracks())
                .hasSize(4)
                .extracting("trackNumber", "artist", "title")
                .containsExactly(
                        tuple(1, "The Düsseldorf Düsterboys", "Im Winter fallen Blätter auf den Weg"),
                        tuple(2, "The Düsseldorf Düsterboys", "Palmen"),
                        tuple(3, "The Düsseldorf Düsterboys", "Willst du nicht mehr"),
                        tuple(4, "The Düsseldorf Düsterboys", "Komm her zu mir"));
    }

    private class TestJsoupWrapper extends JsoupWrapper {
        @Override
        public Document connect(String url) {
            return document;
        }
    }
}
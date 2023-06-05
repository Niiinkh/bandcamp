package downloader2.adapter.out.albuminfo;

import downloader2.domain.Album;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class AlbumMapperTest {

    private AlbumMapper albumMapper;

    @BeforeEach
    void setup() {
        albumMapper = new AlbumMapper();
    }

    @Test
    void artistUndTitleWerdenGemapped() {
        String json = """
                {
                	"current": {
                		"title": "Endless Possibility: A Tribute to Jack Terricloth",
                		"new_date": "01 Jun 2022 04:31:41 GMT",
                		"mod_date": "07 Oct 2022 19:47:37 GMT",
                		"publish_date": "03 Jun 2022 14:18:02 GMT",
                		"release_date": "31 Oct 2022 00:00:00 GMT",
                		"type": "album",
                		"artist": null
                	},
                	"artist": "Various Artists",
                	"item_type": "album",
                	"album_release_date": "31 Oct 2022 00:00:00 GMT",
                	"trackinfo": [	]
                }
                """;
        Album album = albumMapper.mapFromJson(new JSONObject(json));
        assertThat(album.getArtist()).isEqualTo("Various Artists");
        assertThat(album.getTitle()).isEqualTo("Endless Possibility: A Tribute to Jack Terricloth");
    }

    @Test
    void releaseDateWirdGeparsed() {
        String json = """
                {
                	"current": {
                		"new_date": "01 Jun 2022 04:31:41 GMT",
                		"mod_date": "07 Oct 2022 19:47:37 GMT",
                		"publish_date": "03 Jun 2022 14:18:02 GMT",
                		"release_date": "12 Oct 2022 00:00:00 GMT",
                	},
                	"album_release_date": "31 Oct 2022 00:00:00 GMT",
                	"trackinfo": [	]
                }
                """;
        Album album = albumMapper.mapFromJson(new JSONObject(json));
        assertThat(album.getReleaseDate()).isEqualTo(LocalDate.of(2022, 10, 31));
    }

    @Test
    void releaseDateNull() {
        String json = """
                {
                	"album_release_date": null,
                	"trackinfo": [	]
                }
                """;
        Album album = albumMapper.mapFromJson(new JSONObject(json));
        assertThat(album.getReleaseDate()).isNull();
    }

    @Test
    void releaseDateUnknownFormat() {
        String json = """
                {
                	"album_release_date": "2023-12-05",
                	"trackinfo": [	]
                }
                """;
        Album album = albumMapper.mapFromJson(new JSONObject(json));
        assertThat(album.getReleaseDate()).isNull();
    }

}
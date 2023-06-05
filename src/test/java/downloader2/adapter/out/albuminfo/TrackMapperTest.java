package downloader2.adapter.out.albuminfo;

import downloader2.domain.Album;
import downloader2.domain.Track;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TrackMapperTest {

    private TrackMapper trackMapper;
    private Album album;


    @BeforeEach
    void setup() {
        trackMapper = new TrackMapper();
        album = new Album();
    }

    @Test
    void trackWithDownloadLink() {
        String trackWithDownloadLink = """
                {
                			"id": 1815818753,
                			"file": {
                				"mp3-128": "https://t4.bcbits.com/stream/13204b8e149ebe7a8c7d35eeb1fb2e08/mp3-128/1815818753?p=0&ts=1684716413&t=36c27c64d62ced703918ae8c954b0dd9bc97d234&token=1684716413_619a6d13340200ec96c08ae569c54a52743f4d12"
                			},
                			"artist": "Worriers",
                			"title": "Grasping At Straws",
                			"has_lyrics": false,
                			"track_num": 2,
                			"is_downloadable": true,
                			"has_free_download": null,
                			"free_album_download": false,
                			"duration": 186.543
                }
                """;
        Track track = trackMapper.mapFromJson(new JSONObject(trackWithDownloadLink), album);
        assertThat(track.trackNumber()).isEqualTo(2);
        assertThat(track.title()).isEqualTo("Grasping At Straws");
        assertThat(track.artist()).isEqualTo("Worriers");
        assertThat(track.downloadLink()).isEqualTo("https://t4.bcbits.com/stream/13204b8e149ebe7a8c7d35eeb1fb2e08/mp3-128/1815818753?p=0&ts=1684716413&t=36c27c64d62ced703918ae8c954b0dd9bc97d234&token=1684716413_619a6d13340200ec96c08ae569c54a52743f4d12");
    }

    @Test
    void trackWithoutDownloadLink() {
        String trackWithoutDownloadLink = """
                {
                			"id": 1815818753,
                			"file": null,
                			"artist": "Worriers",
                			"title": "Grasping At Straws",
                			"has_lyrics": false,
                			"track_num": 2,
                			"is_downloadable": false,
                			"has_free_download": null,
                			"free_album_download": false,
                			"duration": 186.543
                }
                """;
        Track track = trackMapper.mapFromJson(new JSONObject(trackWithoutDownloadLink), album);
        assertThat(track.trackNumber()).isEqualTo(2);
        assertThat(track.title()).isEqualTo("Grasping At Straws");
        assertThat(track.artist()).isEqualTo("Worriers");
        assertThat(track.downloadLink()).isNull();
    }

    @Test
    void trackWithArtistInformation() {
        String trackJson = """
                {
                	"track_num": 2,
                	"artist": "Worriers",
                	"file": null
                }
                """;
        Track track = trackMapper.mapFromJson(new JSONObject(trackJson), album);
        assertThat(track.artist()).isEqualTo("Worriers");
    }

    @Test
    void trackWithoutArtistInformation() {
        String trackJson = """
                {
                	"track_num": 2,
                	"artist": null,
                	"file": null
                }
                """;
        album.setArtist("Heavy Heavy");
        Track track = trackMapper.mapFromJson(new JSONObject(trackJson), album);
        assertThat(track.artist()).isEqualTo("Heavy Heavy");
    }


    @Test
    void trackWithoutArtistAndAlbumArtistInformation() {
        String trackJson = """
                {
                	"track_num": 2,
                	"artist": null,
                	"file": null
                }
                """;
        album.setArtist(null);
        Track track = trackMapper.mapFromJson(new JSONObject(trackJson), album);
        assertThat(track.artist()).isEqualTo(null);
    }

}
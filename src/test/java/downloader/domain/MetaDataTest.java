package downloader.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MetaDataTest {

    private Album album;

    @BeforeEach
    void setup() {
        album = new Album();
    }

    @Test
    void trackNumber() {
        Track track = new Track(5, "Penny Lane", "Beatles, the", "");
        MetaData metaData = new MetaData(track, album);
        assertThat(metaData.trackNumber()).isEqualTo("5");
    }

    @Test
    void title() {
        Track track = new Track(5, "Penny Lane", "Beatles, the", "");
        MetaData metaData = new MetaData(track, album);
        assertThat(metaData.title()).isEqualTo("Penny Lane");
    }

    @Test
    void artistFromTrackIfPresent() {
        Track track = new Track(5, "Penny Lane", "Beatles, the", "");
        album.setArtist("the Beatles");
        MetaData metaData = new MetaData(track, album);
        assertThat(metaData.artist()).isEqualTo("Beatles, the");
    }

    @Test
    void artistFromAlbumIfTrackArtistIsNull() {
        Track track = new Track(5, "Penny Lane", null, "");
        album.setArtist("the Beatles");
        MetaData metaData = new MetaData(track, album);
        assertThat(metaData.artist()).isEqualTo("the Beatles");
    }

    @Test
    void artistFromAlbumIfTrackArtistIsEmpty() {
        Track track = new Track(5, "Penny Lane", "", "");
        album.setArtist("the Beatles");
        MetaData metaData = new MetaData(track, album);
        assertThat(metaData.artist()).isEqualTo("the Beatles");
    }

    @Test
    void artistUnknown() {
        Track track = new Track(5, "Penny Lane", "", "");
        album.setArtist("");
        MetaData metaData = new MetaData(track, album);
        assertThat(metaData.artist()).isEqualTo("unknown artist");
    }

    @Test
    void album() {
        Track track = new Track(5, "Penny Lane", "Beatles, the", "");
        album.setTitle("Magical Mystery Tour");
        MetaData metaData = new MetaData(track, album);
        assertThat(metaData.album()).isEqualTo("Magical Mystery Tour");
    }


    @Test
    void year() {
        Track track = new Track(5, "Penny Lane", "Beatles, the", "");
        album.setReleaseDate(LocalDate.of(1967, 12, 8));
        MetaData metaData = new MetaData(track, album);
        assertThat(metaData.year()).isEqualTo("1967");
    }

}
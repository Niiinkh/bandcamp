package downloader.domain;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class MetaData {

    private final Track track;
    private final Album album;

    public MetaData(Track track, Album album) {
        this.track = track;
        this.album = album;
    }

    public String trackNumber() {
        return String.valueOf(track.trackNumber());
    }

    public String title() {
        return track.title();
    }

    public String artist() {
        if (StringUtils.isNotEmpty(track.artist()))
            return track.artist();
        if (StringUtils.isNotEmpty(album.getArtist()))
            return album.getArtist();
        return "unknown artist";
    }

    public String album() {
        return album.getTitle();
    }

    public String year() {
        LocalDate releaseDate = album.getReleaseDate();
        return releaseDate == null ? null : String.valueOf(releaseDate.getYear());
    }

    @Override
    public String toString() {
        return "MetaData{" +
                "trackNumber=" + trackNumber() +
                ", title=" + title() +
                ", artist=" + artist() +
                ", album=" + album() +
                ", year=" + year() +
                '}';
    }
}

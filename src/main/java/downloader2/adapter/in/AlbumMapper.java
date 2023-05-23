package downloader2.adapter.in;

import downloader2.domain.Album;
import downloader2.domain.Track;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlbumMapper {

    private final Logger logger = LoggerFactory.getLogger(AlbumMapper.class);

    public Album mapFromJson(JSONObject json) {
        JSONObject current = json.optJSONObject("current", new JSONObject());

        Album album = new Album();
        album.setTitle(current.optString("title"));
        album.setArtist(json.optString("artist"));
        album.setReleaseDate(parseDate(json.optString("album_release_date")));
        album.setTracks(mapTracks(json.getJSONArray("trackinfo")));

        return album;
    }

    private LocalDate parseDate(String dateString) {
        String expectedPattern = "dd MMM yyyy";
        if (StringUtils.length(dateString) < expectedPattern.length())
            return null;

        String shortenedDate = dateString.substring(0, expectedPattern.length());
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(expectedPattern, Locale.ENGLISH);
            return LocalDate.parse(shortenedDate, formatter);
        } catch (DateTimeParseException e) {
            logger.warn("Das ReleaseDate wird ignoeriert, da es nicht im erwarteten Format ist: " + dateString);
            return null;
        }
    }

    private List<Track> mapTracks(JSONArray trackinfo) {
        ArrayList<Track> tracks = new ArrayList<>();
        TrackMapper trackMapper = new TrackMapper();
        for (Object track : trackinfo) {
            tracks.add(trackMapper.mapFromJson((JSONObject) track));
        }
        return tracks;
    }
}

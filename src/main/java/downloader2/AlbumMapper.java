package downloader2;

import downloader2.domain.Album;
import downloader2.domain.Track;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlbumMapper {

    public Album mapFromJson(JSONObject json) {
        JSONObject current = json.optJSONObject("current", new JSONObject());

        Album album = new Album();
        album.setTitle(current.optString("title"));
        album.setArtist(json.optString("artist"));
        album.setReleaseDate(parseDate(json.optString("album_release_date")));
        album.setTracks(mapTracks(json.getJSONArray("trackinfo")));

        return album;
    }

    private static LocalDate parseDate(String dateString) {
        String expectedPattern = "dd MMM yyyy";
        if (StringUtils.length(dateString) < expectedPattern.length())
            return null;

        dateString = dateString.substring(0, expectedPattern.length());
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(expectedPattern, Locale.ENGLISH);
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            // todo: log
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

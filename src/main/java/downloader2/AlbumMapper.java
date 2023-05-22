package downloader2;

import downloader2.domain.Album;
import downloader2.domain.Track;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlbumMapper {

    public Album mapFromJson(JSONObject json) {
        JSONObject current = json.getJSONObject("current");

        Album album = new Album();
        album.setName(current.optString("title"));
        album.setArtist(json.optString("artist"));
        album.setTracks(mapTracks(json.getJSONArray("trackinfo")));

        return album;
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

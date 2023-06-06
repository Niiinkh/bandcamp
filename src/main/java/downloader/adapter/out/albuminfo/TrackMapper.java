package downloader.adapter.out.albuminfo;

import downloader.domain.Album;
import downloader.domain.Track;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

public class TrackMapper {

    public Track mapFromJson(JSONObject json, Album album) {
        int trackNumber = json.getInt("track_num");
        String title = json.optString("title");
        String artist = json.optString("artist");
        if (StringUtils.isEmpty(artist) && album != null) {
            artist = album.getArtist();
        }
        String downloadLink = getDownloadLink(json);
        return new Track(trackNumber, title, artist, downloadLink);
    }

    private String getDownloadLink(JSONObject json) {
        if (json.get("file") == JSONObject.NULL) {
            return null;
        }
        JSONObject file = json.getJSONObject("file");
        return file.optString("mp3-128");
    }

}

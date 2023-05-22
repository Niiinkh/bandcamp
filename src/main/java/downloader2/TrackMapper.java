package downloader2;

import downloader2.domain.Track;
import org.json.JSONObject;

public class TrackMapper {

    public Track mapFromJson(JSONObject json) {
        int trackNumber = json.getInt("track_num");
        String title = json.optString("title");
        String artist = json.optString("artist");
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

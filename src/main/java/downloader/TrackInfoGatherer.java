package downloader;

import static downloader.SpecialCharacterUtil.replaceSpecialCharacters;
import static downloader.StringExtraction.extraxtJsonArray;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class TrackInfoGatherer {

	private static final String TRACK_INFO_INDICATOR = "&quot;trackinfo&quot;:[{";
	private static final String RELEASE_YEAR_INDICATOR = "<meta name=\"description\"";
	private static final String ALBUM_DESCRIPTION_INDICATOR = "<meta name=\"title\" content=";

	private static final String SEPERATOR_BETWEEN_ALBUM_AND_ARTIST = ", by ";

	public TrackInfo getTrackInfo(BufferedReader br) {
		String line;
		TrackInfo trackinfo = new TrackInfo();
		try {
			while ((line = br.readLine()) != null) {

				if (line.contains(ALBUM_DESCRIPTION_INDICATOR)) {
					extractAlbumDescription(br.readLine(), trackinfo);
				}

				if (line.contains(RELEASE_YEAR_INDICATOR)) {
					extractYear(br.readLine(), trackinfo);
				}

				if (line.contains(TRACK_INFO_INDICATOR)) {
					trackinfo.setTrackInfoJson(extraxtJsonArray(line));
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return trackinfo;
	}

	private void extractAlbumDescription(String brReadLine, TrackInfo trackinfo) {
		String albumDescription = brReadLine.substring(brReadLine.indexOf("content") + 9, brReadLine.length() - 2);

		trackinfo.setAlbum("unknown Album");
		trackinfo.setArtist("unknown Artist");
		String[] split = albumDescription.split(SEPERATOR_BETWEEN_ALBUM_AND_ARTIST);
		if (split.length == 2) {
			trackinfo.setAlbum(replaceSpecialCharacters(split[0]));
			trackinfo.setArtist(replaceSpecialCharacters(split[1]));
		}
	}

	private void extractYear(String line, TrackInfo trackinfo) {
		line = line.trim();
		if (line.length() >= 4) {
			String yearString = line.substring(line.length() - 4);
			if (StringUtils.isNumeric(yearString)) {
				trackinfo.setYear(yearString);
			}
		}
	}

}

package downloader;

import static downloader.SpecialCharacterUtil.replaceSpecialCharacters;
import static downloader.StringExtraction.extraxtJsonArray;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class TrackInfoGatherer {
	
	public TrackInfo getTrackInfo(BufferedReader br) {
		String line;
		TrackInfo trackinfo = new TrackInfo();
		try {
			while ((line = br.readLine()) != null) {

				if (line.contains("<meta name=\"title\" content=")) {
					extractAlbumDescription(br.readLine(), trackinfo);
				}

				if (line.contains("<meta name=\"description\"")) {
					extractYear(br.readLine(), trackinfo);
				}

				if (line.contains("&quot;trackinfo&quot;:[{")) {
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
		int z1 = albumDescription.indexOf(", by ");
		int z2 = albumDescription.length();
		
		String album;
		String artist;
		if (z1 != -1) {
			album = albumDescription.substring(0, z1);
			album = replaceSpecialCharacters(album);
			if (z2 != -1) {
				artist = albumDescription.substring(z1 + 5, z2);
				artist = replaceSpecialCharacters(artist);
			} else {
				artist = "unknown Artist";
			}
		} else {
			artist = "unknown Artist";
			album = "unknown Album";
		}
		trackinfo.setAlbum(album);
		trackinfo.setArtist(artist);
	}

	private void extractYear(String line, TrackInfo trackinfo) {
		line = line.trim();
		if (line.length() >= 4) {
			String yearString = line.substring(line.length() - 4, line.length());
			if (StringUtils.isNumeric(yearString)) {
				trackinfo.setYear(yearString);
			}
		}
	}

}

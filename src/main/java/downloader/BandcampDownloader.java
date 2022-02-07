package downloader;

import static downloader.SpecialCharacterUtil.replaceSpecialCharacters;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class BandcampDownloader {

	public void runDownload(URL url, String saveFileDirectory) {
		BufferedReader br = getReaderFromURL(url);
		TrackInfo trackinfo = new TrackInfoGatherer().getTrackInfo(br);
		System.out.println("Downloading " + trackinfo.getAlbum() + " by " + trackinfo.getArtist() + " ("
				+ trackinfo.getYear() + ")");
		downloadTracks(saveFileDirectory, trackinfo);
		System.out.println("Done.");
	}

	private BufferedReader getReaderFromURL(URL url) {
		BufferedReader br = null;
		try {
			URLConnection connection = url.openConnection();
			connection.connect();
			br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return br;
	}

	private void downloadTracks(String parentDirectory, TrackInfo trackinfo) {

		String folderName = trackinfo.getArtist() + " - " + trackinfo.getAlbum();
		folderName = folderName.replaceAll("[\"?*`/<>|\":]", "");

		JSONArray jsonArray = new JSONArray(trackinfo.getTrackInfoJson());
		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject) object;
			String trackNum = Integer.toString(jsonObject.getInt("track_num"));
			String songTitle = getSongTitle(jsonObject);
			String downloadLink = getDownloadLink(jsonObject);
			if (!StringUtils.isEmpty(downloadLink)) {
				String fileName = trackNum + " " + songTitle + ".mp3";
				String directory = parentDirectory + "/" + folderName + "/";
				File dir = new File(directory);
				dir.mkdir();
				System.out.println("Downloading: \"" + fileName + "\"");
				File mp3TempFile = downloadSong(downloadLink);
				writeMetaData(directory, fileName, mp3TempFile, gatherMetaData(trackinfo, trackNum, songTitle));
			}

		}
	}

	private void writeMetaData(String directory, String fileName, File mp3TempFile, MetaData metaData) {
		try {
			Mp3File mp3File = new Mp3File(mp3TempFile.getCanonicalPath());
			metaData.setTags(mp3File, directory + fileName);
		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
			e.printStackTrace();
		} finally {
			mp3TempFile.delete();
		}
	}

	private MetaData gatherMetaData(TrackInfo trackInfo, String trackNum, String songTitle) {
		MetaData metaData = new MetaData();
		metaData.setTrackNumber(trackNum);
		metaData.setTitle(songTitle);
		metaData.setArtist(trackInfo.getArtist());
		metaData.setAlbum(trackInfo.getAlbum());
		metaData.setYear(trackInfo.getYear());
		return metaData;
	}

	private String getSongTitle(JSONObject jo) {
		String songTitle = jo.getString("title");
		songTitle = replaceSpecialCharacters(songTitle);
		songTitle = songTitle.replaceAll("[\"?*`/<>|\":]", "");
		songTitle = songTitle.replaceAll("[\\\\]", "");
		return songTitle;
	}

	public File downloadSong(String urlAdress) {
		try {
			URL url = new URL(urlAdress);
			File tempFile = File.createTempFile("temp", ".mp3");
			FileUtils.copyURLToFile(url, tempFile);
			return tempFile;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getDownloadLink(JSONObject jo) {
		if (jo.get("file") == JSONObject.NULL) {
			return null;
		}
		JSONObject file = jo.getJSONObject("file");
		return file.getString("mp3-128");
	}

}

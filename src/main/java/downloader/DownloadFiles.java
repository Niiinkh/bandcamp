package downloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class DownloadFiles {

	private static String album;
	private static String artist;

	public static void runDownload(String URLAdress, String saveFileDirectory) {

		BufferedReader br = getReaderFromURL(URLAdress);

		String trackinfo = getTrackInfo(br);

		downloadTracks(saveFileDirectory, trackinfo);

	}

	private static String getTrackInfo(BufferedReader br) {
		String line;
		String trackinfo = null;
		try {
			while ((line = br.readLine()) != null) {

				if (line.contains("<meta name=\"title\" content=")) {
					getAlbumDescription(br.readLine());
				}

				if (line.contains("trackinfo: [{")) {
					int a = line.indexOf("[");
					int b = line.indexOf("]") + 1;
					trackinfo = line.substring(a, b);
				}

			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return trackinfo;
	}

	private static BufferedReader getReaderFromURL(String URLAdress) {
		BufferedReader br = null;
		try {

			URL bandcampURL = new URL(URLAdress);
			URLConnection connection = bandcampURL.openConnection();
			connection.connect();
			br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return br;
	}

	private static void downloadTracks(String parentDirectory, String trackinfo) {



		String folderName = artist + " - " + album;
		folderName = folderName.replaceAll("[\"?*`/<>|\":]", "");

		JSONArray jsonArray = new JSONArray(trackinfo);
		for (Object object : jsonArray) {
			JSONObject jo = (JSONObject) object;
			String songTitle = getSongTitle(jo);
			Integer trackNum = jo.getInt("track_num");
			String downloadLink = getDownloadLink(jo);
			if (!downloadLink.equals("")) {
				String fileName = Integer.toString(trackNum) + " " + songTitle + ".mp3";
				String directory = parentDirectory + "/" + folderName + "/" + fileName;
				System.out.println("Downloading: \"" + fileName + "\"");
				downloadSong(downloadLink, directory);
			}

		}
	}

	private static String getSongTitle(JSONObject jo) {
		String songTitle = (String) jo.get("title");
		songTitle = songTitle.replaceAll("[\"?*`/<>|\":]", "");
		songTitle = songTitle.replaceAll("[\\\\]", "");
		return songTitle;
	}

	private static boolean downloadSong(String urlAdress, String directory) {
		try {
			URL url = new URL(urlAdress);
			File file = new File(directory);
			FileUtils.copyURLToFile(url, file);
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static String getDownloadLink(JSONObject jo) {
		JSONObject file = (JSONObject) jo.get("file");
		Map<String, Object> map = file.toMap();
		Set<String> keySet = map.keySet();
		String downloadLink = "";
		for (String s : keySet) {
			downloadLink = (String) file.get(s);
			break;
		}
		return downloadLink;
	}

	private static void getAlbumDescription(String brReadLine) {
		String albumDescription = brReadLine.substring(brReadLine.indexOf("content") + 9, brReadLine.length() - 2);
		int z1 = albumDescription.indexOf(", by ");
		int z2 = albumDescription.length();
		if (z1 != -1) {
			album = albumDescription.substring(0, z1);
			album = album.replaceAll("&#39;", "'");
			album = album.replaceAll("&amp;", "&");
			if (z2 != -1) {
				artist = albumDescription.substring(z1 + 5, z2);
				artist = artist.replaceAll("&#39;", "'");
				artist = artist.replaceAll("&amp;", "&");
			} else {
				artist = "unknown Artist";
			}
		} else {
			artist = "unknown Artist";
			album = "unknown Album";
		}
	}
}

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
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class DownloadFiles {

	private static String album;
	private static String artist;
	private static String year;

	public static void runDownload(URL url, String saveFileDirectory) {
		BufferedReader br = getReaderFromURL(url);

		String trackinfo = getTrackInfo(br);
//		System.out.println(trackinfo);
		downloadTracks(saveFileDirectory, trackinfo);
		System.out.println("Done.");

	}

	private static String getTrackInfo(BufferedReader br) {
		String line;
		String trackinfo = null;
		try {
			while ((line = br.readLine()) != null) {

				if (line.contains("<meta name=\"title\" content=")) {
					getAlbumDescription(br.readLine());
				}

				if (line.contains("<meta name=\"Description\"")) {
					extractYear(br.readLine());
				}

				if (line.contains("trackinfo: [{")) {
					int a = line.indexOf("[{");
					int b = line.indexOf("}]") + 2;
					trackinfo = line.substring(a, b);
				}

			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return trackinfo;
	}

	private static BufferedReader getReaderFromURL(URL url) {
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

	private static void downloadTracks(String parentDirectory, String trackinfo) {

		String folderName = artist + " - " + album;
		folderName = folderName.replaceAll("[\"?*`/<>|\":]", "");

		JSONArray jsonArray = new JSONArray(trackinfo);
		for (Object object : jsonArray) {
			JSONObject jo = (JSONObject) object;
			String trackNum = Integer.toString(jo.getInt("track_num"));
			String songTitle = getSongTitle(jo);
			String downloadLink = getDownloadLink(jo);
			if (!downloadLink.equals("")) {

				String fileName = trackNum + " " + songTitle + ".mp3";
				String directory = parentDirectory + "/" + folderName + "/";
				File dir = new File(directory);
				dir.mkdir();
				System.out.println("Downloading: \"" + fileName + "\"");
				File mp3TempFile = downloadSong(downloadLink);
				writeMetaData(directory, fileName, mp3TempFile, gatherMetaData(trackNum, songTitle));

			}

		}
	}

	private static void createFolder(String directory) {
		// TODO Auto-generated method stub
		
	}

	private static void writeMetaData(String directory, String fileName, File mp3TempFile, MetaData metaData) {
		try {
			Mp3File mp3File = new Mp3File(mp3TempFile.getCanonicalPath());
			metaData.setTags(mp3File, directory + fileName);
		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
			e.printStackTrace();
		} finally {
			mp3TempFile.delete();
		}
	}

	private static MetaData gatherMetaData(String trackNum, String songTitle) {
		MetaData metaData = new MetaData();
		metaData.setTrackNumber(trackNum);
		metaData.setTitle(songTitle);
		metaData.setArtist(artist);
		metaData.setAlbum(album);
		metaData.setYear(year);
		return metaData;
	}

	private static String getSongTitle(JSONObject jo) {
		String songTitle = (String) jo.get("title");
		songTitle = songTitle.replaceAll("[\"?*`/<>|\":]", "");
		songTitle = songTitle.replaceAll("[\\\\]", "");
		return songTitle;
	}

	public static File downloadSong(String urlAdress) {
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

	private static void extractYear(String line) {
		line = line.trim();
		if (line.length() >= 4) {
			String yearString = line.substring(line.length() - 4, line.length());
			if (StringUtils.isNumeric(yearString)) {
				year = yearString;
			}
		}
	}
}

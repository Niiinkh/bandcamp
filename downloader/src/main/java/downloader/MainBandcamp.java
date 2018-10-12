package downloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;



public class MainBandcamp {
		
	public static void main(String[] args) {
		new Gui();
	}
	
	public static void runDownload(String URLAdress) {
		URL bandcampURL = null;
		BufferedReader br = null;
		
		try {
			
			bandcampURL = new URL(URLAdress);
			URLConnection connection = bandcampURL.openConnection();
			connection.connect();
			br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			
			String line;
			String albumDescription = "";
			List<String> tracklist = new ArrayList<String>();
			String trackinfo = "";
			
			try {
				while ((line = br.readLine()) != null) {

					if (line.contains("<meta name=\"title\" content=")) {
						albumDescription = br.readLine();
						albumDescription = albumDescription.substring(albumDescription.indexOf("content")+9,albumDescription.length()-2);
					}
					if (line.contains("<meta name=\"Description")) {
						while ((line = br.readLine()) != null) {
							if (line.isEmpty()) {
								break;
							}
							int a = line.indexOf(" ")+1;
							int b = line.length();
							String track = line.substring(a, b);
							tracklist.add(track);
						}
					}
					
					if (line.contains("trackinfo: [{")) {
						int a = line.indexOf("[")+1;
						int b = line.indexOf("]");
						trackinfo = line.substring(a,b);
					}
					
				}
				br.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
			String currentTrack;
			String songTitle;
			String link = "";
			String filename = "";
			String artist = "";
			String album = "";
			
			
			int z1 = albumDescription.indexOf(", by ");
			int z2 = albumDescription.length();
			if (z1 != -1) {
				album = albumDescription.substring(0, z1);
				album = album.replaceAll("&#39;", "'");
				album = album.replaceAll("&amp;", "&");
				if (z2 != -1) {
					artist = albumDescription.substring(z1+5, z2);
					artist = artist.replaceAll("&#39;", "'");
					artist = artist.replaceAll("&amp;", "&");
				}
				else {
					artist = "unknown Artist";
				}
			}
			else {
				artist = "unknown Artist";
				album  = "unknown Album";
			}
			
			String folderName = artist + " - " + album;
			folderName = folderName.replaceAll("[\"?*`/<>|\":]", "");
			
//			System.out.println(folderName);
			
			int a = trackinfo.indexOf("{");
			int b = trackinfo.indexOf("}",a);
			
			
			int i = 0;
			
			while (a != -1 && b != -1) {
				
				boolean foundLink = false;
				boolean foundTitle = false;
				i++;
				
				currentTrack = trackinfo.substring(a+1,b);

				int t1 = currentTrack.indexOf(",\"title\"");
				if (t1 != -1) {
					t1 += 10;
					int t2 = currentTrack.indexOf("\",\"", t1);
					songTitle = currentTrack.substring(t1,t2);
					songTitle = songTitle.replaceAll("[\"?*`/<>|\":]", "");
					songTitle = songTitle.replaceAll("[\\\\]", "");
					
					filename = Integer.toString(i) + " " + songTitle + ".mp3";
					foundTitle = true;
				}
				
				
				int d1 = currentTrack.indexOf("\"file\":");
				d1 = currentTrack.indexOf("http",d1);
				if (d1 != -1) {
					int d2 = currentTrack.length()-1;
					link = currentTrack.substring(d1,d2);
					foundLink = true;
				}
				
				// --> DOWNLOAD SONG
//				System.out.println(filename);
//				System.out.println(link);
				
				if (foundLink) {
					String t;
					if (foundTitle) {
						t = filename;
					}
					else {
						t = "Track no. " + Integer.toString(i) + ".mp3";
					}
					String directory = "C://Music/" + folderName + "/" + t;
					downloadSong(link, directory);
					
				}
//				System.out.println("completed.");
				
				
				
				a = trackinfo.indexOf("{",b);
				b = trackinfo.indexOf("}",a);
			}
			

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	private static boolean downloadSong(String urlAdress, String directory ) {
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

}

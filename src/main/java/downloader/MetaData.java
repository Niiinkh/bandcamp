package downloader;

import java.io.IOException;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;

public class MetaData {

	private String track;
	private String title;
	private String artist;
	private String album;
	private String year;

	public String getTrackNumber() {
		return track;
	}

	public void setTrackNumber(String track) {
		this.track = track;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setTags(Mp3File mp3File, String newFileName) {
		setId3v1Tag(mp3File);
		setId3v2Tag(mp3File);
		try {
			mp3File.save(newFileName);
		} catch (NotSupportedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setId3v1Tag(Mp3File mp3File) {
		if (mp3File.getId3v1Tag() == null) {
			ID3v1Tag tag = new ID3v1Tag();
			setMetaData(tag);
			mp3File.setId3v1Tag(tag);
		}
	}

	private void setId3v2Tag(Mp3File mp3File) {
		if (mp3File.getId3v2Tag() == null) {
			ID3v24Tag tag = new ID3v24Tag();
			setMetaData(tag);
			mp3File.setId3v2Tag(tag);
		}
	}

	private void setMetaData(ID3v1 tag) {
		tag.setTrack(track);
		tag.setTitle(title);
		tag.setArtist(artist);
		tag.setAlbum(album);
		tag.setYear(year);
	}

	@Override
	public String toString() {
		return "MetaData [trackNumber=" + track + ", title=" + title + ", artist=" + artist + ", album=" + album
				+ ", year=" + year + "]";
	}
}

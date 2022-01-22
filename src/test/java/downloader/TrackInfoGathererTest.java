package downloader;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrackInfoGathererTest {


	private static final String ALBUM = "album";
	private static final String ARTIST = "artist";
	private static final String YEAR = "year";

	private TrackInfoGatherer trackInfoGatherer;

	@BeforeEach
	public void setup() {
		trackInfoGatherer = new TrackInfoGatherer();
	}

	@Test
	public void testGetTrackInfo_siskiyou() {
		BufferedReader bufferedReader = loadFile("siskiyou_not-somewhere.html");
		TrackInfo trackInfo = trackInfoGatherer.getTrackInfo(bufferedReader);
		assertThat(trackInfo).isNotNull() //
				.hasFieldOrPropertyWithValue(YEAR, "2019") //
				.hasFieldOrPropertyWithValue(ARTIST, "Siskiyou") //
				.hasFieldOrPropertyWithValue(ALBUM, "Not Somewhere");
		assertThat(trackInfo.getTrackInfoJson()).isNotNull();
	}
	
	@Test
	public void testGetTrackInfo_angelaAux() {
		BufferedReader bufferedReader = loadFile("angela-aux_unten-am-fluss.html");
		TrackInfo trackInfo = trackInfoGatherer.getTrackInfo(bufferedReader);
		assertThat(trackInfo).isNotNull() //
		.hasFieldOrPropertyWithValue(YEAR, "2020") //
		.hasFieldOrPropertyWithValue(ARTIST, "Angela Aux") //
		.hasFieldOrPropertyWithValue(ALBUM, "Unten am Fluss, wo ich den Morgen aufgelesen hab");
		assertThat(trackInfo.getTrackInfoJson()).isNotNull();
	}

	@Test
	public void testGetTrackInfo_graceCummings() {
		BufferedReader bufferedReader = loadFile("grace-cummings_storm-queen.html");
		TrackInfo trackInfo = trackInfoGatherer.getTrackInfo(bufferedReader);
		assertThat(trackInfo).isNotNull() //
		.hasFieldOrPropertyWithValue(YEAR, "2022") //
		.hasFieldOrPropertyWithValue(ARTIST, "Grace Cummings") //
		.hasFieldOrPropertyWithValue(ALBUM, "Storm Queen");
		assertThat(trackInfo.getTrackInfoJson()).isNotNull();
	}

	private BufferedReader loadFile(String fileName) {
		try {
			return new BufferedReader(new FileReader("src/test/resources/" + fileName));
		} catch (FileNotFoundException e) {
			Assertions.fail("FILE NOT FOUND: '" + fileName + "'");
		}
		return null;
	}

}

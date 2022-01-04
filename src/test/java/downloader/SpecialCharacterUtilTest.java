package downloader;

import static downloader.SpecialCharacterUtil.replaceSpecialCharacters;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class SpecialCharacterUtilTest {

	@Test
	public void testReplaceSpecialCharacters_ampersand() {
		assertThat(replaceSpecialCharacters("&amp;")).isEqualTo("&");
	}

	@Test
	public void testReplaceSpecialCharacters_ampersandWithSurroundingText() {
		assertThat(replaceSpecialCharacters("Hello&amp;Goodbye")).isEqualTo("Hello&Goodbye");
	}
	
	@Test
	public void testReplaceSpecialCharacters_apostrophe() {
		assertThat(replaceSpecialCharacters("&#39;")).isEqualTo("'");
	}
	
	@Test
	public void testReplaceSpecialCharacters_apostropheWithSurroundingText() {
		assertThat(replaceSpecialCharacters("Just don&#39;t")).isEqualTo("Just don't");
	}
	
	

}

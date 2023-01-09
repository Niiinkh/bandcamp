package downloader;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class StringExtractionTest {

	@Test
	public void jsonArrayStringGetsExtractedFromNoise() {
		String stringWithJson = "noisenoise[{json}]noisenoise";
		assertThat(StringExtraction.extraxtJsonArray(stringWithJson)).isEqualTo("[{json}]");
	}

	@Test
	public void onlyFirstArrayGetsExtracted() {
		String stringWithJson = "noisenoise[{firstJson}]noise[{secondJson}]noise";
		assertThat(StringExtraction.extraxtJsonArray(stringWithJson)).isEqualTo("[{firstJson}]");
	}

	@Test
	public void randomConfusingArrayEndIsIgnored() {
		String stringWithJson = "confusingEnd}]noisenoise[{json}]noisenoise";
		assertThat(StringExtraction.extraxtJsonArray(stringWithJson)).isEqualTo("[{json}]");
	}

	@Test
	public void htmlQuotationsGetReplaced() {
		String stringWithJson = "noisenoise[{&quot;json&quot;}]noisenoise";
		assertThat(StringExtraction.extraxtJsonArray(stringWithJson)).isEqualTo("[{\"json\"}]");
	}

}

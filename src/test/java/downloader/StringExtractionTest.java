package downloader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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

	@Test
	public void nestedArrayIsIdentifiedCorrectly() {
		String stringWithJson = "noisenoise[{\"array1\":[{}]}, {\"nestedArray\":[{\"innerArray\":[{}]}]}]noisenoise";
		assertThat(StringExtraction.extraxtJsonArray(stringWithJson)).isEqualTo("[{\"array1\":[{}]}, {\"nestedArray\":[{\"innerArray\":[{}]}]}]");
	}

	@Test
	public void exceptionIsThrownIfClosingBracketIsMissing() {
		String stringWithJson = "noisenoise[{json}noisenoise";
		assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> StringExtraction.extraxtJsonArray(stringWithJson));
	}

}

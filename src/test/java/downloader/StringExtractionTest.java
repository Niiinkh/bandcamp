package downloader;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class StringExtractionTest {

	@Test
	public void testExtraxtJsonArray_stringWithOneArray() {
		String stringWithJson = "noisenoise[{json}]noisenoise";
		assertThat(StringExtraction.extraxtJsonArray(stringWithJson)).isEqualTo("[{json}]");
	}

	@Test
	public void testExtraxtJsonArray_stringWithPreviousArrayEnd() {
		String stringWithJson = "confusingEnd}]noisenoise[{json}]noisenoise";
		assertThat(StringExtraction.extraxtJsonArray(stringWithJson)).isEqualTo("[{json}]");
	}

	@Test
	public void testExtraxtJsonArray_withQuotation() {
		String stringWithJson = "noisenoise[{&quot;json&quot;}]noisenoise";
		assertThat(StringExtraction.extraxtJsonArray(stringWithJson)).isEqualTo("[{\"json\"}]");
	}

}

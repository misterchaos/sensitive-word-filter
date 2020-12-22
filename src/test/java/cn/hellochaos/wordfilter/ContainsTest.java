package cn.hellochaos.wordfilter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;

/**
 * @author Yuchao Huang on 2020/12/19
 */
public class ContainsTest {

    public static Iterable<Object[]> testCases() {
        return Arrays.asList(new Object[][]{
                {true, "apple"},
                {true, "apple tree"},
                {true, "apples"},
                {true, "pear"},
                {true, "pear tree"},
                {true, "banana"},
                {true, "banana tree"},

                {false, "a"},
                {false, "app"},
                {false, "c"},
                {false, "dog"},
                {false, "ple"},
                {false, null}
        });
    }


    @ParameterizedTest
    @MethodSource(value = "testCases")
    public void containsTest(boolean expected, String src) {
        for (WordFilter wordFilter : TestHelper.getWordFilters()) {
            this.doTest(expected, src, wordFilter);
        }
    }

    public void doTest(boolean expected, String src, WordFilter wordFilter) {
        Assertions.assertEquals(expected, wordFilter.contains(src));
    }

}

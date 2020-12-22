package cn.hellochaos.wordfilter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;

/**
 * @author Yuchao Huang on 2020/12/19
 */
public class ReplaceTest {

    public static Iterable<Object[]> testCases() {
        return Arrays.asList(new Object[][]{
                {"here is an apple tree", "here is an *"},
                {"here is an apple", "here is an *"},
                {"apple", "*"},
                {"An apple is on the apple tree", "An * is on the *"},
        });
    }


    @ParameterizedTest
    @MethodSource(value = "testCases")
    public void replaceTest(String before, String after) {
        for (WordFilter wordFilter : TestHelper.getWordFilters()) {
            this.doTest(before, after, wordFilter);
        }
    }

    private void doTest(String before, String after, WordFilter wordFilter) {
        String actual = wordFilter.replace(before);
        Assertions.assertEquals(after, actual);
    }

}

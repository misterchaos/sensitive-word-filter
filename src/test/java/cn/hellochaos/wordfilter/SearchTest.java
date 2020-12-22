package cn.hellochaos.wordfilter;

import cn.hutool.core.util.ArrayUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;

/**
 * @author Yuchao Huang on 2020/12/19
 */
public class SearchTest {

    public static Iterable<Object[]> testCases() {
        return Arrays.asList(new Object[][]{
                {"apple tree is a tree", new String[]{"apple tree"}},
        });
    }


    @ParameterizedTest
    @MethodSource(value = "testCases")
    public void searchTest(String src, String[] expected) {
        for (WordFilter wordFilter : TestHelper.getWordFilters()) {
            this.doTest(src, expected, wordFilter);
        }
    }

    private void doTest(String src, String[] expected, WordFilter wordFilter) {
        String[] sensitiveWords = wordFilter.search(src);
        if (!Arrays.equals(expected, sensitiveWords)) {
            System.out.println("Source: " + src);
            System.out.println("Expected: " + ArrayUtil.toString(expected));
            System.out.println("Actual: " + ArrayUtil.toString(sensitiveWords));
            Assertions.fail("Search test failed");
        }
    }

}

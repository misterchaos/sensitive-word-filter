package cn.hellochaos.wordfilter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;

/**
 * @author Yuchao Huang on 2020/12/19
 */
public class DeleteTest {

    public static Iterable<Object[]> testCases() {
        return Arrays.asList(new Object[][]{
                {true, "apple tree", new Object[][]{
                        {true, "apple"},
                        {true, "apple tree"},
                        {true, "An apple grow on apple tree"}
                }},
                {true, "pear", new Object[][]{
                        {false, "pear"},
                        {false, "p"},
                        {true, "pear tree"}
                }},
        });
    }


    @ParameterizedTest
    @MethodSource(value = "testCases")
    public void deleteTest(boolean expected, String src, Object[][] args) {
        for (WordFilter wordFilter : TestHelper.getWordFilters()) {
            this.doTest(expected, src, args, wordFilter);
        }
    }

    private void doTest(boolean expected, String src, Object[][] args, WordFilter wordFilter) {
        boolean actual = wordFilter.delete(src);
        Assertions.assertEquals(expected, actual);
        for (Object[] arg : args) {
            Assertions.assertEquals(arg[0], wordFilter.contains((String) arg[1]));
        }
    }


}

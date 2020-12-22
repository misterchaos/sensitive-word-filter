package cn.hellochaos.wordfilter;

import org.junit.jupiter.api.Test;

/**
 * @author Yuchao Huang on 2020/12/19
 */
public class TokenizerTest {
    private final WordFilter wordFilter = TestHelper.getDefaultWordFilter();

    @Test
    public void tokenizerTest() {
        wordFilter.setEnableTokenizer(true);
        wordFilter.contains("apple");
        wordFilter.contains("a");
        wordFilter.contains("i have an apple");
        wordFilter.search("here is an apple");
        wordFilter.replace("apple");
        wordFilter.replace("i have an apple");
        wordFilter.replace("apple", "$");
        wordFilter.delete("apple");
    }
}

package cn.hellochaos.wordfilter;

import cn.hellochaos.wordfilter.dictionary.Dictionary;
import cn.hellochaos.wordfilter.dictionary.TrieTreeDictionary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * @author Yuchao Huang on 2020/12/19
 */
public class WordFilterTest {
    @Test
    public void constructorTest() {
        // empty filter
        new WordFilter();
        // default filter
        DefaultWordFilter.getInstance();
        // create by dictionary
        Dictionary dictionary = new TrieTreeDictionary();
        new WordFilter(dictionary);
        // create by not exist file
        Assertions.assertThrows(RuntimeException.class,
                () -> new WordFilter("not_exist_filepath", new TrieTreeDictionary()));
    }

    @Test
    public void insertTest() {
        // empty filter
        WordFilter wordFilter = new WordFilter();
        wordFilter.insert("apple", "pear");
        Assertions.assertTrue(wordFilter.contains("apple"));
        Assertions.assertTrue(wordFilter.contains("pear"));
    }


    @Test
    public void fieldTest() {
        for (WordFilter wordFilter : TestHelper.getWordFilters()) {
            Dictionary dictionary = new TrieTreeDictionary();
            wordFilter.setDictionary(dictionary);
            Assertions.assertEquals(dictionary, wordFilter.getDictionary());
            wordFilter.setReplacement("$");
            Assertions.assertEquals("$", wordFilter.getReplacement());
            wordFilter.setEnableTokenizer(true);
            Assertions.assertTrue(wordFilter.isEnableTokenizer());

            Class<?> clazz = WordFilter.class;
            try {
                Field dictField = clazz.getDeclaredField("dictionary");
                dictField.setAccessible(true);
                dictField.set(wordFilter, dictionary);

                Field replacementField = clazz.getDeclaredField("replacement");
                replacementField.setAccessible(true);
                replacementField.set(wordFilter, "$");

                Field tokenField = clazz.getDeclaredField("enableTokenizer");
                tokenField.setAccessible(true);
                tokenField.set(wordFilter, true);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void clearTest() {
        for (WordFilter wordFilter : TestHelper.getWordFilters()) {
            wordFilter.clear();
        }
    }

}

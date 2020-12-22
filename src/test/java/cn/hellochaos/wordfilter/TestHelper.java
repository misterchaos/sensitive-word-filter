package cn.hellochaos.wordfilter;

import cn.hellochaos.wordfilter.dictionary.HashSetDictionary;
import cn.hellochaos.wordfilter.dictionary.TrieTreeDictionary;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;

import java.io.File;
import java.lang.reflect.Field;

/**
 * @author Yuchao Huang on 2020/12/19
 */
public class TestHelper {

    public static final File DEFAULT_FILE = new File(
            ClassLoader.getSystemResource("sensitive-word/sensitive_words.dic")
                    .getPath()).getParentFile();

    public static final TimeInterval timer = DateUtil.timer();

    private TestHelper() {

    }

    public static WordFilter[] getWordFilters() {
        return new WordFilter[]{getDefaultWordFilter(), getHashSetWordFilter(), getTrieTreeWordFilter()};
    }

    public static WordFilter getDefaultWordFilter() {
        Class<?> clazz = DefaultWordFilter.class;
        try {
            Field instant = clazz.getDeclaredField("INSTANCE");
            instant.setAccessible(true);
            instant.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return DefaultWordFilter.getInstance();
    }

    public static WordFilter getHashSetWordFilter() {
        return new WordFilter(DEFAULT_FILE, new HashSetDictionary());
    }

    public static WordFilter getTrieTreeWordFilter() {
        return new WordFilter(DEFAULT_FILE, new TrieTreeDictionary());
    }

    public static void printTestTitle(WordFilter wordFilter, Class<?> testClass) {
        String className = wordFilter.getDictionary().getClass().getName();
        String dictionaryName = className.substring(className.lastIndexOf('.') + 1);
        String method = testClass.getName().substring(testClass.getName().lastIndexOf('.') + 1);
        System.out.println("===========TEST dictionary: " + dictionaryName + " method: " + method + "===================");
        TestHelper.timer.restart();
    }

    public static void printTestResult() {
        System.out.println("Time Usage:" + TestHelper.timer.intervalRestart());
    }


}

package cn.hellochaos.wordfilter;

import cn.hellochaos.wordfilter.dictionary.HashSetDictionary;
import cn.hellochaos.wordfilter.dictionary.TrieTreeDictionary;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yuchao Huang on 2020/12/19
 */
public class PerformanceTest {

    private static final TimeInterval timer = DateUtil.timer();
    private static final int count = 100;
    private static final File tempTile = FileUtil.createTempFile(FileUtil.getTmpDir());

    public static Iterable<Object[]> testCases() {
        WordFilter trieTreeFilter = new WordFilter(tempTile.getAbsolutePath(), new TrieTreeDictionary());
        WordFilter hashSetFilter = new WordFilter(tempTile.getAbsolutePath(), new HashSetDictionary());
        return Arrays.asList(new Object[][]{
                {10, trieTreeFilter},
                {10, hashSetFilter},
                {100, trieTreeFilter},
                {100, hashSetFilter},
                {200, trieTreeFilter},
                {200, hashSetFilter}
        });
    }

    @BeforeAll
    public static void generateTestFile() throws IOException {
        BufferedWriter writer = FileUtil.getWriter(tempTile.getAbsolutePath(), StandardCharsets.UTF_8, false);
        String[] sensitiveWords = genRandomStr(10);
        for (String sensitiveWord : sensitiveWords) {
            writer.write(sensitiveWord);
            writer.newLine();
        }
    }

    @AfterAll
    public static void deleteTestFile() {
        FileUtil.del(tempTile.getAbsolutePath());
    }

    private static String[] genRandomStr(int length) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String src = RandomUtil.randomString(length);
            list.add(src);
        }
        return list.toArray(new String[count]);
    }

    @ParameterizedTest
    @MethodSource(value = "testCases")
    public void matchTest(Integer length, WordFilter wordFilter) {
        String[] inputStrings = genRandomStr(length);
        timer.restart();
        for (String inputString : inputStrings) {
            wordFilter.contains(inputString);
        }
        String className = wordFilter.getDictionary().getClass().getName();
        String dictionaryName = className.substring(className.lastIndexOf('.') + 1);
        System.out.println("Input String Length :" + length);
        System.out.println(dictionaryName + " : " + timer.intervalRestart());
    }


}

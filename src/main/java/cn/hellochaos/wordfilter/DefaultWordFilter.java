package cn.hellochaos.wordfilter;

import cn.hellochaos.wordfilter.dictionary.Dictionary;
import cn.hellochaos.wordfilter.dictionary.TrieTreeDictionary;

import java.io.File;

/**
 * @author Yuchao Huang on 2020/12/19
 * A default word filter that get sensitive word from default sensitive words file
 */
public class DefaultWordFilter extends WordFilter {

    /**
     * Default sensitive words file
     * 默认敏感词库文件
     */
    private static final File DEFAULT_FILE = new File(
            ClassLoader.getSystemResource("sensitive-word/sensitive_words.dic")
                    .getPath()).getParentFile();
    private static final Dictionary DEFAULT_DICTIONARY = new TrieTreeDictionary();
    /**
     * Default instance
     * 默认单例
     */
    private static WordFilter INSTANCE;

    private DefaultWordFilter() {

    }

    /**
     * Get the default singleton, which uses the sensitive words files <br/>
     * in `./sensitive-word` as the data source by default<br/>
     * 获取默认的单例，默认使用`./sensitive-word`路径下的所有文件里面的敏感词作为数据源
     *
     * @return Default instance 默认单例
     */
    public static WordFilter getInstance() {
        if (INSTANCE == null) {
            synchronized (DefaultWordFilter.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WordFilter(DEFAULT_FILE, DEFAULT_DICTIONARY);
                }
            }
        }
        return INSTANCE;
    }

}

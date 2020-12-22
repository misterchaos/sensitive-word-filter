package cn.hellochaos.wordfilter;

import cn.hellochaos.wordfilter.dictionary.Dictionary;
import cn.hellochaos.wordfilter.dictionary.TrieTreeDictionary;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.tokenizer.Result;
import cn.hutool.extra.tokenizer.TokenizerEngine;
import cn.hutool.extra.tokenizer.TokenizerUtil;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Use to filter sensitive words in string content<br/>
 *
 * <p>
 * {@link #contains(String src)} Return true if src contains sensitive word<br/>
 * {@link #replace(String src)} Replace sensitive words detected in src into replacement<br/>
 * {@link #search(String src)} Returns sensitive words detected in src<br/>
 * {@link #insert(String... sensitiveWords)} Add sensitive words into dictionary if not exist<br/>
 *
 * @author Yuchao Huang on 2020/12/10
 */
@Data
public class WordFilter implements Serializable {

    /**
     * Replacement of sensitive word
     * 文本中敏感词的替代字符
     */
    private String replacement = "*";

    /**
     * Sensitive word container
     * 敏感词库
     */
    private Dictionary dictionary;

    /**
     * Whether to enable tokenizer
     * 设置是否启用分词器
     */
    private boolean enableTokenizer;

    /**
     * Build an empty dictionary, you should add sensitive words manually
     * 构造一个空的敏感词过滤器，需要手动加入敏感词
     */
    public WordFilter() {
        this.dictionary = new TrieTreeDictionary();
    }

    /**
     * Build dictionary with specified file
     * 通过指定文件的内容构造敏感词过滤器
     * Input null to build an empty dictionary, you should add sensitive words manually
     * 传入null表示构造一个空的敏感词过滤器，需要手动加入敏感词
     *
     * @param filepath   包含敏感词的文件路径，如果是文件夹，则扫描其下所有文件
     * @param dictionary 用户自己维护的敏感词库
     */
    public WordFilter(String filepath, Dictionary dictionary) {
        this(new File(filepath), dictionary);
    }

    /**
     * Create Word Filter by input dictionary and read sensitive words from file
     * 从指定的文件读取敏感词并添加到dictionary中
     *
     * @param file       敏感词库文件
     * @param dictionary 用户自己维护的敏感词库
     */
    public WordFilter(File file, Dictionary dictionary) {
        this.dictionary = dictionary;
        if (file == null) {
            return;
        }
        if (FileUtil.isDirectory(file)) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    readFile(f);
                }
            }
        } else {
            readFile(file);
        }

    }

    /**
     * Create Word Filter by input dictionary
     * 通过传入已创建好的敏感词库构造过滤器
     *
     * @param dictionary 用户自己维护的敏感词库
     */
    public WordFilter(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Read sensitive words from file
     * 从敏感词文件读取敏感词
     *
     * @param file file that contains sensitive words
     */
    private void readFile(File file) {
        BufferedReader reader = FileUtil.getUtf8Reader(file);
        String word;
        try {
            while (((word = reader.readLine()) != null)) {
                this.dictionary.insert(word);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Return true if src contains sensitive word
     * 判断源文本中是否包含敏感词
     *
     * @param src 源文本
     * @return 是否包含
     */
    public boolean contains(String src) {
        if (src == null) {
            return false;
        }
        // Enable chinese tokenizer
        if (enableTokenizer) {
            TokenizerEngine engine = TokenizerUtil.createEngine();
            // Chinese word segmentation
            Result result = engine.parse(src);
            while (result.hasNext()) {
                String word = result.next().toString();
                if (this.dictionary.contains(word)) {
                    return true;
                }
            }
            return false;
        } else {
            return this.dictionary.matchFirst(src) != null;
        }
    }

    /**
     * Replace sensitive words detected in src into this.replacement
     * 将源文本中的敏感词替换成this.replacement的值
     *
     * @param src 源文本
     * @return 替换后的文本
     */
    public String replace(String src) {
        return replace(src, this.replacement);
    }

    /**
     * Replace sensitive words detected in src into replacement
     * 将源文本中的敏感词替换成传入的replacement的值<p>
     * 传入的replacement不会影响this.replacement的值
     * <p>
     * 在非分词模式下，匹配的是同一个字符开头的最长敏感词，比如对于敏感词："程序"，"程序员"，
     * 输入"我是程序员"的替换结果是："我是*" 而不是 "我是*员"
     *
     * @param src         源文本
     * @param replacement 替代字符
     * @return 替换后的文本
     */
    public String replace(String src, String replacement) {
        // Enable chinese tokenizer
        if (enableTokenizer) {
            StringBuilder sb = new StringBuilder();
            TokenizerEngine engine = TokenizerUtil.createEngine();
            // Chinese word segmentation
            Result result = engine.parse(src);
            while (result.hasNext()) {
                String word = result.next().toString();
                if (this.dictionary.contains(word)) {
                    sb.append(replacement);
                } else {
                    sb.append(word);
                }
            }
            return sb.toString();
        } else {
            String[] sensitiveWords = this.dictionary.matchAll(src);
            String res = src;
            for (String sensitiveWord : sensitiveWords) {
                res = res.replaceFirst(sensitiveWord, replacement);
            }
            return res;
        }
    }

    /**
     * Returns sensitive words detected in src
     * 返回源文本中检测到的敏感词列表，如果一个敏感词嵌套另一个敏感词，则返回长的那个
     *
     * @param src 源文本
     * @return 包含的敏感词列表
     */
    public String[] search(String src) {
        List<String> sensitiveWords = new LinkedList<>();
        // Enable chinese tokenizer
        if (enableTokenizer) {
            TokenizerEngine engine = TokenizerUtil.createEngine();
            // Chinese word segmentation
            Result result = engine.parse(src);
            while (result.hasNext()) {
                String word = result.next().toString();
                if (this.dictionary.contains(word)) {
                    sensitiveWords.add(word);
                }
            }
            return sensitiveWords.toArray(new String[0]);
        } else {
            return dictionary.matchAll(src);
        }
    }

    /**
     * Add sensitive words into dictionary if not exist
     * 将敏感词添加到词库中,如果已存在不会重复添加
     *
     * @param sensitiveWords 敏感词列表
     */
    public void insert(String... sensitiveWords) {
        for (String sensitiveWord : sensitiveWords) {
            this.dictionary.insert(sensitiveWord);
        }
    }

    /**
     * Returns true if this word is deleted successfully.<br/>
     * If current dictionary does not contains this word, it returns false.
     * 从敏感词库中删除敏感词，如果删除成功则返回true。如果词库中不含有次敏感词，则返回false。
     *
     * @param word sensitive word to delete
     * @return Returns true if this word is deleted successfully
     */
    public boolean delete(String word) {
        return this.dictionary.delete(word);
    }

    /**
     * Remove all words from current filter
     * 清空当前敏感词过滤器的词库
     */
    public void clear() {
        this.dictionary.clear();
    }
}

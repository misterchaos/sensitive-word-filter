package cn.hellochaos.wordfilter.dictionary;


import cn.hellochaos.wordfilter.trietree.TrieTree;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Yuchao Huang on 2020/12/18
 * <p>
 * Trie tree based dictionary to provide word matching
 */
public class TrieTreeDictionary implements Dictionary {

    private final TrieTree trieTree = new TrieTree();

    /**
     * Insert sensitive word into dictionary
     *
     * @param word sensitive word to insert
     */
    @Override
    public void insert(String word) {
        trieTree.insert(word);
    }

    /**
     * Returns true if current dictionary contains this word, or else false
     *
     * @param word sensitive word to match
     * @return Returns true if current dictionary contains this word, or else false
     */
    @Override
    public boolean contains(String word) {
        return trieTree.contains(word);
    }

    /**
     * Returns all the sensitive words found in the text input
     *
     * @param src text to find sensitive word
     * @return Returns all the sensitive words found in the text input
     */
    @Override
    public String[] matchAll(String src) {
        if (src == null) {
            return null;
        }
        Set<String> sensitiveWords = new HashSet<>();
        for (int i = 0; i < src.length(); i++) {
            if (!this.trieTree.hasPrefix(src.substring(i, i + 1))) {
                continue;
            }
            for (int j = src.length(); j >= i + 2; j--) {
                String word = src.substring(i, j);
                if (this.contains(word)) {
                    sensitiveWords.add(word);
                    break;
                }
            }
        }
        return sensitiveWords.toArray(new String[0]);
    }

    /**
     * Returns the first sensitive words found in the text input.<br/>
     * If no sensitive word found in the text input,it returns null.
     *
     * @param src text to find sensitive word
     * @return Returns the first sensitive words found in the text input
     */
    @Override
    public String matchFirst(String src) {
        if (src == null) {
            return null;
        }
        for (int i = 0; i < src.length(); i++) {
            if (!this.trieTree.hasPrefix(src.substring(i, i + 1))) {
                continue;
            }
            for (int j = i + 2; j <= src.length(); j++) {
                String word = src.substring(i, j);
                if (this.contains(word)) {
                    return word;
                }
            }
        }
        return null;
    }

    /**
     * Returns true if this word is deleted successfully.<br/>
     * If current dictionary does not contains this word, it returns false.
     *
     * @param word sensitive word to delete
     * @return Returns true if this word is deleted successfully
     */
    @Override
    public boolean delete(String word) {
        return trieTree.delete(word);
    }

    /**
     * Remove all words from current dictionary
     */
    @Override
    public void clear() {
        trieTree.clear();
    }
}

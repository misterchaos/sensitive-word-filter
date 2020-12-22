package cn.hellochaos.wordfilter.dictionary;

import java.io.Serializable;

/**
 * @author Yuchao Huang on 2020/12/18
 * <p>
 * A dictionary is a sensitive word container that provides insert,match,delete and clear function
 */
public interface Dictionary extends Serializable {

    long serialVersionUID = 1L;

    /**
     * Insert sensitive word into dictionary
     *
     * @param word sensitive word to insert
     */
    void insert(String word);

    /**
     * Returns true if current dictionary contains this word, or else false
     *
     * @param word sensitive word to match
     * @return Returns true if current dictionary contains this word, or else false
     */
    boolean contains(String word);

    /**
     * Returns all the sensitive words found in the text input
     *
     * @param src text to find sensitive word
     * @return Returns all the sensitive words found in the text input
     */
    String[] matchAll(String src);

    /**
     * Returns the first sensitive words found in the text input.<br/>
     * If no sensitive word found in the text input,it returns null.
     *
     * @param src text to find sensitive word
     * @return Returns the first sensitive words found in the text input
     */
    String matchFirst(String src);

    /**
     * Returns true if this word is deleted successfully.<br/>
     * If current dictionary does not contains this word, it returns false.
     *
     * @param word sensitive word to delete
     * @return Returns true if this word is deleted successfully
     */
    boolean delete(String word);

    /**
     * Remove all words from current dictionary
     */
    void clear();
}

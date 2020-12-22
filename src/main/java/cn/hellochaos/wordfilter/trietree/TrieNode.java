package cn.hellochaos.wordfilter.trietree;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Node of {@link TrieTree} <p>
 *
 * @author Yuchao Huang on 2020/12/11
 */
@RequiredArgsConstructor
public class TrieNode {
    /**
     * To hold all son {@link TrieNode}
     */
    public final Map<Character, TrieNode> sons = new HashMap<>();
    /**
     * Char value of current {@link TrieNode}
     */
    @NonNull
    public char val;
    /**
     * Marking if val is the last char of a {@link String}
     */
    public boolean isTail;
    /**
     * Marking how many {@link String} across this {@link TrieNode}
     */
    public int acrossCount = 1;
}

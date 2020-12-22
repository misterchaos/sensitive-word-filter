package cn.hellochaos.wordfilter.trietree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * An implement of {@link TrieTree}<p>
 * {@link TrieTree#insert(String)} Insert str into TrieTree <br/>
 * {@link TrieTree#delete(String)} Delete str from TrieTree <br/>
 * {@link TrieTree#search(String)} Returns tail of str if current tree contains str, or else false <br/>
 * {@link TrieTree#contains(String)} Returns true if current tree contains str, or else false
 *
 * @author Yuchao Huang on 2020/12/11
 */
public class TrieTree {

    private final TrieNode root = new TrieNode('/');

    /**
     * Insert str into TrieTree
     *
     * @param str String to insert
     */
    public void insert(String str) {
        if (str == null || search(str) != null) {
            return;
        }
        TrieNode p = root;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (p.sons.containsKey(ch)) {
                p = p.sons.get(ch);
                p.acrossCount++;
            } else {
                TrieNode son = new TrieNode(ch);
                p.sons.put(ch, son);
                p = son;
            }
        }
        p.isTail = true;
    }

    /**
     * Returns true if str is deleted successfully.<br/>
     * If current tree does not contains str, it returns false.
     *
     * @param str String to delete
     */
    public boolean delete(String str) {
        if (search(str) == null) {
            return false;
        }

        TrieNode p = root;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (p.sons.containsKey(ch)) {
                /*
                 * If str is the only String that contains(across) ch,
                 * we can remove ch’s node and return true directly,
                 * or else we should let ch.acrossCount--
                 */
                if (p.sons.get(ch).acrossCount == 1) {
                    p.sons.remove(ch);
                    return true;
                } else {
                    p = p.sons.get(ch);
                    p.acrossCount--;
                }
            }
        }
        p.isTail = false;
        return true;
    }

    /**
     * Returns tail of str if current tree contains str, or else false
     *
     * @param str String to search
     */
    public TrieNode search(String str) {
        if (str == null) {
            return null;
        }
        TrieNode p = root;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!p.sons.containsKey(ch)) {
                return null;
            } else {
                p = p.sons.get(ch);
            }
        }
        return p.isTail ? p : null;
    }

    /**
     * Returns true if this tree contains value with this prefix
     *
     * @param str String to match prefix
     * @return Returns true if this tree contains value with this prefix
     */
    public boolean hasPrefix(String str) {
        if (str == null) {
            return false;
        }
        TrieNode p = root;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!p.sons.containsKey(ch)) {
                return false;
            } else {
                p = p.sons.get(ch);
            }
        }
        return true;
    }

    /**
     * Returns true if current tree contains str, or else false
     * {@link TrieTree#search(String str)}
     *
     * @param str String to match
     */
    public boolean contains(String str) {
        return search(str) != null;
    }


    /**
     * Remove all nodes from current trie tree
     */
    public void clear() {
        Queue<TrieNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TrieNode p = queue.poll();
            if (!p.sons.isEmpty()) {
                queue.addAll(p.sons.values());
            }
            p.sons.clear();
        }
    }
}

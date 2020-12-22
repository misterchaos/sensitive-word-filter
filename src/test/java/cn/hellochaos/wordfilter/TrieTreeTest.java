package cn.hellochaos.wordfilter;

import cn.hellochaos.wordfilter.trietree.TrieNode;
import cn.hellochaos.wordfilter.trietree.TrieTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Yuchao Huang on 2020/12/19
 */
public class TrieTreeTest {

    private final static TrieTree trieTree = new TrieTree();

    @BeforeEach
    public void init() {
        trieTree.insert("apple");
        trieTree.insert("pear");
        trieTree.insert("banana");
        trieTree.insert("apple tree");
        trieTree.insert("pear tree");
        trieTree.insert("banana tree");
    }

    @Test
    void insert() {
        trieTree.insert(null);
        trieTree.insert("tree");
        Assertions.assertFalse(trieTree.contains("tre"));
        Assertions.assertFalse(trieTree.contains("trees"));
        Assertions.assertTrue(trieTree.contains("tree"));

        trieTree.insert("hello world");
        Assertions.assertTrue(trieTree.contains("hello world"));
        Assertions.assertFalse(trieTree.contains("hello"));

        trieTree.insert("hello");
        Assertions.assertTrue(trieTree.contains("hello"));
    }

    @Test
    void delete() {
        boolean success = trieTree.delete("a");
        Assertions.assertFalse(success);
        Assertions.assertTrue(trieTree.contains("apple"));
        Assertions.assertTrue(trieTree.contains("apple tree"));

        success = trieTree.delete(null);
        Assertions.assertFalse(success);

        success = trieTree.delete("pear");
        Assertions.assertTrue(success);
        Assertions.assertFalse(trieTree.contains("pear"));
        Assertions.assertFalse(trieTree.contains("p"));
        Assertions.assertTrue(trieTree.contains("pear tree"));

        success = trieTree.delete("banana tree");
        Assertions.assertTrue(success);
        Assertions.assertTrue(trieTree.contains("banana"));
        Assertions.assertFalse(trieTree.contains("banana tree"));
    }

    @Test
    void search() {
        TrieNode node = trieTree.search("a");
        Assertions.assertNull(node);
        node = trieTree.search(null);
        Assertions.assertNull(node);

        node = trieTree.search("apple");
        Assertions.assertNotNull(node);
        Assertions.assertTrue(node.isTail);
        Assertions.assertEquals('e', node.val);
        Assertions.assertEquals(2, node.acrossCount);
        Assertions.assertEquals(1, node.sons.size());


        node = trieTree.search("apple tree");
        Assertions.assertNotNull(node);
        Assertions.assertTrue(node.isTail);
        Assertions.assertEquals('e', node.val);
        Assertions.assertEquals(1, node.acrossCount);
        Assertions.assertEquals(0, node.sons.size());
    }

    @Test
    void hasPrefix() {
        Assertions.assertTrue(trieTree.hasPrefix("a"));
        Assertions.assertTrue(trieTree.hasPrefix("app"));
        Assertions.assertTrue(trieTree.hasPrefix("b"));
        Assertions.assertTrue(trieTree.hasPrefix("p"));

        Assertions.assertFalse(trieTree.hasPrefix("z"));
        Assertions.assertFalse(trieTree.hasPrefix(null));
    }

    @Test
    void contains() {
        Assertions.assertTrue(trieTree.contains("apple"));
        Assertions.assertTrue(trieTree.contains("apple tree"));
        Assertions.assertTrue(trieTree.contains("pear"));
        Assertions.assertTrue(trieTree.contains("pear tree"));
        Assertions.assertTrue(trieTree.contains("banana"));
        Assertions.assertTrue(trieTree.contains("banana tree"));

        Assertions.assertFalse(trieTree.contains("a"));
        Assertions.assertFalse(trieTree.contains("app"));
        Assertions.assertFalse(trieTree.contains("apples"));
    }

    @Test
    void clear() {
        trieTree.clear();
    }
}
package cn.hellochaos.wordfilter.example;

import cn.hellochaos.wordfilter.DefaultWordFilter;
import cn.hellochaos.wordfilter.WordFilter;
import cn.hellochaos.wordfilter.dictionary.HashSetDictionary;

/**
 * @author Yuchao Huang on 2021/3/2
 */
public class UseHashMap {
    public static void main(String[] args) {
        // Create an empty word filter instance with HashSetDictionary
        WordFilter wordFilter = new WordFilter(new HashSetDictionary());
        // Insert sensitive word into dictionary
        wordFilter.insert("test");
        // Check if the word is in the dictionary
        wordFilter.contains("this is a test case");
        // Replace sensitive words
        wordFilter.replace("this is a test case");
        // Print the results
        System.out.println("contains result : "+wordFilter.contains("this is a test case"));
        System.out.println("replace result : "+wordFilter.replace("this is a test case"));
    }
}

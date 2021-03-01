package cn.hellochaos.wordfilter.example;

import cn.hellochaos.wordfilter.DefaultWordFilter;
import cn.hellochaos.wordfilter.WordFilter;

/**
 * @author Yuchao Huang on 2021/3/2
 */
public class Default {
    public static void main(String[] args) {
        // Get default word filter instance
        WordFilter wordFilter = DefaultWordFilter.getInstance();
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

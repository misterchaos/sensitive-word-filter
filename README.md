# sensitive-word-filter

A fast, easy to use sensitive word filter
## Overview

Use to filter sensitive words in string content
- contains(String src) Return true if src contains sensitive word
- replace(String src) Replace sensitive words detected in src into replacement 
- search(String src) Returns sensitive words detected in src 
- insert(String... sensitiveWords) Add sensitive words into dictionary if not exist

Two kind of dictionary supported
- Trie-Tree
- HashSet

## Usage

### Basic Usage
Get the default singleton, which uses all the sensitive words files in `./sensitive-word` as the data source by default
```java
// Get default word filter instance
WordFilter wordFilter=DefaultWordFilter.getInstance();
// Insert sensitive word into dictionary
wordFilter.insert("test");
// Check if the word is in the dictionary
wordFilter.contains("this is a test case");
// Replace sensitive words
wordFilter.replace("this is a test case");
// Print the results
System.out.println("contains result : "+wordFilter.contains("this is a test case"));
System.out.println("replace result : "+wordFilter.replace("this is a test case"));
```
The output of the above code is as follows

```bash
contains result : true
replace result : this is a * case
```
### Use HashSet Dictionary
In most cases, HashSet is more memory efficient than the default Trietree, but the matching is less efficient</br>
You can change the dictionary to `HashSetDictionary` with the following code
```java
// Create an empty word filter instance with HashSetDictionary
WordFilter wordFilter = new WordFilter(new HashSetDictionary());
```
Notice：Replacing the dictionary type will cause the sensitive words files to not be loaded automatically and you will need to specify it manually

### Specify the sensitive words file
```java
// Static constant defined in DefaultWordFilter.java
public static final File DEFAULT_FILE = new File(
            ClassLoader.getSystemResource("sensitive-word/sensitive_words.dic")
                    .getPath()).getParentFile();

// Create a word filter instance with HashSetDictionary from a specified file
WordFilter wordFilter = new WordFilter(DEFAULT_FILE,new HashSetDictionary());
```
You can specify any file as a sensitive dictionary with this argument


## License
The code is open source using GPL3 protocol. If you need to use the code, please follow the relevant provisions of CPL3 protocol.

## Authors
- Yuchao Huang [@misterchaos](https://github.com/misterchaos/) - Original Author


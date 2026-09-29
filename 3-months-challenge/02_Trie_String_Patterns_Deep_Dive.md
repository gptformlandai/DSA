# Trie & String Algorithms Patterns Deep Dive
## Junior Dev's Complete Guide to L5 MAANG String Mastery

---

# THE TRIE MINDSET: Before You Code Anything

## The One Sentence That Unlocks Trie

> **Trie = A tree where each path from root to node represents a prefix of stored words.**

## When to Use Trie?

```
1. PREFIX MATCHING: "Find all words starting with..."
2. AUTOCOMPLETE: Suggest completions
3. WORD SEARCH: Multiple words in grid
4. DICTIONARY OPERATIONS: Insert, search, startsWith
5. XOR PROBLEMS: Binary trie for max XOR
```

---

# PART 1: TRIE PATTERNS

## The Trie Node Template (MEMORIZE THIS!)

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];  // For lowercase letters
    boolean isEndOfWord = false;
    // Optional: String word, int count, etc.
}

class Trie {
    TrieNode root = new TrieNode();
    
    void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isEndOfWord = true;
    }
    
    boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEndOfWord;
    }
    
    boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }
    
    private TrieNode searchPrefix(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) return null;
            node = node.children[idx];
        }
        return node;
    }
}
```

---

## PATTERN 1: Implement Trie (LC 208)

### Pattern Recognition Signal

> **When you see:** "Implement prefix tree" or "Insert, search, startsWith"
> **Instant thought:** "Standard Trie with TrieNode array!"

### Visual Dry Run

```
Insert: "apple", "app"

        root
         |
         a
         |
         p
         |
         p (isEnd=true for "app")
         |
         l
         |
         e (isEnd=true for "apple")

search("apple") -> true (path exists, isEnd=true)
search("app") -> true (path exists, isEnd=true)
search("ap") -> false (path exists, but isEnd=false)
startsWith("app") -> true (path exists)
```

### The Code

```java
class Trie {
    private TrieNode root;
    
    public Trie() {
        root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isEndOfWord = true;
    }
    
    public boolean search(String word) {
        TrieNode node = traverse(word);
        return node != null && node.isEndOfWord;
    }
    
    public boolean startsWith(String prefix) {
        return traverse(prefix) != null;
    }
    
    private TrieNode traverse(String s) {
        TrieNode node = root;
        for (char c : s.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) return null;
            node = node.children[idx];
        }
        return node;
    }
}

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEndOfWord = false;
}
```

### Mind-Map Anchor

```
IMPLEMENT TRIE
      |
      v
+----------------------+
| TrieNode: children[] |
| + isEndOfWord        |
| Insert: create path  |
| Search: traverse +   |
| check isEnd          |
| StartsWith: traverse |
+----------------------+
```

**Memory phrase:** "Children array + isEnd flag, traverse for all operations"

---

## PATTERN 2: Design Add and Search Words (LC 211)

### Pattern Recognition Signal

> **When you see:** "Search with wildcard ." or "Pattern matching in dictionary"
> **Instant thought:** "Trie + DFS for wildcard!"

### The Key Insight

When we see '.', we must try ALL children (DFS branching).

### Visual Dry Run

```
addWord("bad"), addWord("dad"), addWord("mad")

Trie:
      root
     / | \
    b  d  m
    |  |  |
    a  a  a
    |  |  |
    d  d  d

search("pad") -> false (no 'p' child)
search("bad") -> true
search(".ad") -> true (try b,d,m -> all have "ad" path)
search("b..") -> true (b->a->d exists)
```

### The Code

```java
class WordDictionary {
    private TrieNode root;
    
    public WordDictionary() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isEndOfWord = true;
    }
    
    public boolean search(String word) {
        return dfs(word, 0, root);
    }
    
    private boolean dfs(String word, int index, TrieNode node) {
        if (index == word.length()) {
            return node.isEndOfWord;
        }
        
        char c = word.charAt(index);
        
        if (c == '.') {
            // Wildcard: try all children
            for (TrieNode child : node.children) {
                if (child != null && dfs(word, index + 1, child)) {
                    return true;
                }
            }
            return false;
        } else {
            // Regular character
            int idx = c - 'a';
            if (node.children[idx] == null) return false;
            return dfs(word, index + 1, node.children[idx]);
        }
    }
}
```

### Mind-Map Anchor

```
ADD AND SEARCH WORDS
         |
         v
+------------------------+
| Trie + DFS             |
| '.' = try ALL children |
| Regular = normal trie  |
| Recursive search       |
+------------------------+
```

**Memory phrase:** "Dot means branch to all children with DFS"

---

## PATTERN 3: Word Search II (LC 212)

### Pattern Recognition Signal

> **When you see:** "Find multiple words in grid"
> **Instant thought:** "Build Trie of words, DFS on grid following Trie!"

### Visual Dry Run

```
board = [["o","a","a","n"],
         ["e","t","a","e"],
         ["i","h","k","r"],
         ["i","f","l","v"]]
words = ["oath","pea","eat","rain"]

Build Trie of words:
      root
     / | \ \
    o  p  e  r
    |  |  |  |
    a  e  a  a
    |  |  |  |
    t  a  t  i
    |        |
    h        n

DFS from each cell, following Trie:
- (0,0)='o': Trie has 'o' -> continue
- (1,0)='e': no 'oe' path, backtrack
- (0,1)='a': Trie o->a exists -> continue
- (1,1)='t': Trie o->a->t exists -> continue
- (2,1)='h': Trie o->a->t->h exists, isEnd=true -> "oath" FOUND!

Found: ["oath", "eat"]
```

### The Code

```java
class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        // Build Trie
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNode();
                }
                node = node.children[idx];
            }
            node.word = word;  // Store word at end
        }
        
        List<String> result = new ArrayList<>();
        
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                dfs(board, r, c, root, result);
            }
        }
        
        return result;
    }
    
    private void dfs(char[][] board, int r, int c, TrieNode node, List<String> result) {
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) return;
        
        char ch = board[r][c];
        if (ch == '#' || node.children[ch - 'a'] == null) return;
        
        node = node.children[ch - 'a'];
        
        if (node.word != null) {
            result.add(node.word);
            node.word = null;  // Avoid duplicates
        }
        
        board[r][c] = '#';  // Mark visited
        dfs(board, r + 1, c, node, result);
        dfs(board, r - 1, c, node, result);
        dfs(board, r, c + 1, node, result);
        dfs(board, r, c - 1, node, result);
        board[r][c] = ch;   // Restore
    }
}

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    String word = null;  // Store complete word at end
}
```

### Mind-Map Anchor

```
WORD SEARCH II
      |
      v
+------------------------+
| Build Trie of words    |
| DFS from each cell     |
| Follow Trie path       |
| Store word at TrieNode |
| Mark visited with '#'  |
+------------------------+
```

**Memory phrase:** "Trie of words, DFS on grid, follow Trie path"

---

## PATTERN 4: Replace Words (LC 648)

### Pattern Recognition Signal

> **When you see:** "Replace words with their shortest root/prefix"
> **Instant thought:** "Build Trie of roots, find shortest prefix for each word!"

### Visual Dry Run

```
dictionary = ["cat", "bat", "rat"]
sentence = "the cattle was rattled by the battery"

Build Trie of roots:
      root
     / | \
    c  b  r
    |  |  |
    a  a  a
    |  |  |
    t* t* t*  (* = isEnd)

Process each word:
"the" -> no prefix in Trie -> "the"
"cattle" -> "cat" is prefix -> "cat"
"was" -> no prefix -> "was"
"rattled" -> "rat" is prefix -> "rat"
"by" -> no prefix -> "by"
"the" -> no prefix -> "the"
"battery" -> "bat" is prefix -> "bat"

Result: "the cat was rat by the bat"
```

### The Code

```java
public String replaceWords(List<String> dictionary, String sentence) {
    // Build Trie of roots
    TrieNode root = new TrieNode();
    for (String word : dictionary) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isEndOfWord = true;
    }
    
    StringBuilder result = new StringBuilder();
    String[] words = sentence.split(" ");
    
    for (int i = 0; i < words.length; i++) {
        if (i > 0) result.append(" ");
        result.append(findRoot(root, words[i]));
    }
    
    return result.toString();
}

private String findRoot(TrieNode root, String word) {
    TrieNode node = root;
    StringBuilder prefix = new StringBuilder();
    
    for (char c : word.toCharArray()) {
        int idx = c - 'a';
        if (node.children[idx] == null) break;  // No more prefix
        
        prefix.append(c);
        node = node.children[idx];
        
        if (node.isEndOfWord) {
            return prefix.toString();  // Found shortest root!
        }
    }
    
    return word;  // No root found, return original
}
```

### Mind-Map Anchor

```
REPLACE WORDS
      |
      v
+----------------------+
| Trie of dictionary   |
| For each word, find  |
| shortest prefix that |
| is a complete root   |
| Return root or word  |
+----------------------+
```

**Memory phrase:** "Trie of roots, find shortest matching prefix"

---

## PATTERN 5: Design Search Autocomplete System (LC 642)

### Pattern Recognition Signal

> **When you see:** "Autocomplete with frequency ranking"
> **Instant thought:** "Trie + store sentences at nodes + sort by frequency!"

### The Code

```java
class AutocompleteSystem {
    private TrieNode root;
    private TrieNode current;
    private StringBuilder currentInput;
    
    public AutocompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();
        currentInput = new StringBuilder();
        current = root;
        
        for (int i = 0; i < sentences.length; i++) {
            insert(sentences[i], times[i]);
        }
    }
    
    private void insert(String sentence, int count) {
        TrieNode node = root;
        for (char c : sentence.toCharArray()) {
            int idx = getIndex(c);
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
            node.sentences.put(sentence, node.sentences.getOrDefault(sentence, 0) + count);
        }
    }
    
    public List<String> input(char c) {
        if (c == '#') {
            insert(currentInput.toString(), 1);
            currentInput = new StringBuilder();
            current = root;
            return new ArrayList<>();
        }
        
        currentInput.append(c);
        
        if (current != null) {
            int idx = getIndex(c);
            current = current.children[idx];
        }
        
        if (current == null) return new ArrayList<>();
        
        // Get top 3 by frequency, then lexicographically
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
            (a, b) -> a.getValue().equals(b.getValue()) ? 
                      a.getKey().compareTo(b.getKey()) : 
                      b.getValue() - a.getValue()
        );
        
        pq.addAll(current.sentences.entrySet());
        
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 3 && !pq.isEmpty(); i++) {
            result.add(pq.poll().getKey());
        }
        
        return result;
    }
    
    private int getIndex(char c) {
        return c == ' ' ? 26 : c - 'a';
    }
}

class TrieNode {
    TrieNode[] children = new TrieNode[27];  // 26 letters + space
    Map<String, Integer> sentences = new HashMap<>();  // sentence -> frequency
}
```

### Mind-Map Anchor

```
AUTOCOMPLETE SYSTEM
        |
        v
+------------------------+
| Trie with frequency map|
| at each node           |
| Track current position |
| '#' = end input, save  |
| Return top 3 by freq   |
+------------------------+
```

**Memory phrase:** "Trie + frequency map at nodes, track current position"

---

## PATTERN 6: Longest Word in Dictionary (LC 720)

### Pattern Recognition Signal

> **When you see:** "Longest word that can be built one char at a time"
> **Instant thought:** "Trie + BFS/DFS only through complete words!"

### Visual Dry Run

```
words = ["w","wo","wor","worl","world"]

Build Trie, mark isEnd:
      root
       |
       w*
       |
       o*
       |
       r*
       |
       l*
       |
       d*

BFS from root, only visit nodes where isEnd=true:
Level 1: w (isEnd=true) -> valid
Level 2: wo (isEnd=true) -> valid
Level 3: wor (isEnd=true) -> valid
Level 4: worl (isEnd=true) -> valid
Level 5: world (isEnd=true) -> valid

Longest: "world"
```

### The Code

```java
public String longestWord(String[] words) {
    TrieNode root = new TrieNode();
    root.word = "";
    
    // Build Trie
    for (String word : words) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.word = word;
    }
    
    // BFS - only visit nodes with complete words
    String result = "";
    Queue<TrieNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        TrieNode node = queue.poll();
        
        for (TrieNode child : node.children) {
            if (child != null && child.word != null) {
                if (child.word.length() > result.length() ||
                    (child.word.length() == result.length() && 
                     child.word.compareTo(result) < 0)) {
                    result = child.word;
                }
                queue.offer(child);
            }
        }
    }
    
    return result;
}
```

### Mind-Map Anchor

```
LONGEST WORD
     |
     v
+----------------------+
| Build Trie           |
| BFS only through     |
| nodes with isEnd=true|
| Track longest word   |
+----------------------+
```

**Memory phrase:** "BFS through complete words only"


---

## PATTERN 7: Maximum XOR of Two Numbers (LC 421)

### Pattern Recognition Signal

> **When you see:** "Maximum XOR of two numbers"
> **Instant thought:** "Binary Trie! Greedily pick opposite bits"

### The Key Insight

Build a binary Trie (0/1 children). For each number, traverse Trie picking opposite bits when possible to maximize XOR.

### Visual Dry Run

```
nums = [3, 10, 5, 25, 2, 8]

Binary (5 bits):
3  = 00011
10 = 01010
5  = 00101
25 = 11001
2  = 00010
8  = 01000

Build Binary Trie with all numbers.

For num=25 (11001), find max XOR:
  Bit 4: have 1, want 0 -> go 0 if exists
  Bit 3: have 1, want 0 -> go 0 if exists
  Bit 2: have 0, want 1 -> go 1 if exists
  Bit 1: have 0, want 1 -> go 1 if exists
  Bit 0: have 1, want 0 -> go 0 if exists

Best match: 5 (00101)
XOR: 25 ^ 5 = 11001 ^ 00101 = 11100 = 28

Answer: 28
```

### The Code

```java
public int findMaximumXOR(int[] nums) {
    TrieNode root = new TrieNode();
    
    // Build binary Trie
    for (int num : nums) {
        TrieNode node = root;
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if (node.children[bit] == null) {
                node.children[bit] = new TrieNode();
            }
            node = node.children[bit];
        }
    }
    
    int maxXor = 0;
    
    // Find max XOR for each number
    for (int num : nums) {
        TrieNode node = root;
        int xor = 0;
        
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            int oppositeBit = 1 - bit;
            
            if (node.children[oppositeBit] != null) {
                xor |= (1 << i);  // This bit contributes to XOR
                node = node.children[oppositeBit];
            } else {
                node = node.children[bit];
            }
        }
        
        maxXor = Math.max(maxXor, xor);
    }
    
    return maxXor;
}

class TrieNode {
    TrieNode[] children = new TrieNode[2];  // Binary: 0 and 1
}
```

### Mind-Map Anchor

```
MAXIMUM XOR
     |
     v
+----------------------+
| Binary Trie (0/1)    |
| Insert all numbers   |
| For each, greedily   |
| pick OPPOSITE bits   |
| Maximize XOR result  |
+----------------------+
```

**Memory phrase:** "Binary Trie, greedily pick opposite bits"

---

# PART 2: STRING ALGORITHM PATTERNS

## PATTERN 8: KMP Algorithm - strStr (LC 28)

### Pattern Recognition Signal

> **When you see:** "Find pattern in text" or "First occurrence of substring"
> **Instant thought:** "KMP! Build failure/LPS array, avoid re-scanning"

### The Key Insight

LPS (Longest Proper Prefix which is also Suffix) array tells us where to continue matching after a mismatch.

### Visual Dry Run

```
text = "ABABDABACDABABCABAB"
pattern = "ABABCABAB"

Build LPS array for pattern:
pattern: A B A B C A B A B
index:   0 1 2 3 4 5 6 7 8
LPS:     0 0 1 2 0 1 2 3 4

Meaning: LPS[i] = length of longest proper prefix of pattern[0..i]
         that is also a suffix

Matching:
text:    A B A B D A B A C D A B A B C A B A B
pattern: A B A B C
              ^ mismatch at index 4

LPS[3] = 2, so pattern has "AB" prefix = "AB" suffix
Continue matching from pattern[2]:

text:    A B A B D A B A C D A B A B C A B A B
pattern:     A B A B C
                  ^ mismatch

... continue until match found at index 10
```

### The Code

```java
public int strStr(String haystack, String needle) {
    if (needle.isEmpty()) return 0;
    
    int[] lps = buildLPS(needle);
    int i = 0, j = 0;
    
    while (i < haystack.length()) {
        if (haystack.charAt(i) == needle.charAt(j)) {
            i++;
            j++;
            if (j == needle.length()) {
                return i - j;  // Match found!
            }
        } else if (j > 0) {
            j = lps[j - 1];  // Use LPS to skip
        } else {
            i++;
        }
    }
    
    return -1;
}

private int[] buildLPS(String pattern) {
    int[] lps = new int[pattern.length()];
    int len = 0;
    int i = 1;
    
    while (i < pattern.length()) {
        if (pattern.charAt(i) == pattern.charAt(len)) {
            len++;
            lps[i] = len;
            i++;
        } else if (len > 0) {
            len = lps[len - 1];
        } else {
            lps[i] = 0;
            i++;
        }
    }
    
    return lps;
}
```

### Mind-Map Anchor

```
KMP ALGORITHM
      |
      v
+----------------------+
| Build LPS array      |
| LPS[i] = longest     |
| prefix = suffix      |
| On mismatch, jump to |
| LPS[j-1] position    |
| O(n+m) time!         |
+----------------------+
```

**Memory phrase:** "LPS array, on mismatch jump to lps[j-1]"

---

## PATTERN 9: Rabin-Karp - Rolling Hash

### Pattern Recognition Signal

> **When you see:** "Find pattern with hash" or "Multiple pattern matching"
> **Instant thought:** "Rolling hash! Hash window, slide and update"

### The Key Insight

Instead of comparing strings, compare hashes. Rolling hash updates in O(1) by removing first char and adding new char.

### Visual Dry Run

```
text = "abcdef", pattern = "cde"

Hash function: sum of char values (simplified)
Pattern hash: 'c'+'d'+'e' = 99+100+101 = 300

Window 1: "abc" -> 97+98+99 = 294 != 300
Window 2: "bcd" -> 294 - 97 + 100 = 297 != 300
Window 3: "cde" -> 297 - 98 + 101 = 300 == 300 -> MATCH!

Verify actual string match to avoid hash collision.
```

### The Code

```java
public int rabinKarp(String text, String pattern) {
    int n = text.length(), m = pattern.length();
    if (m > n) return -1;
    
    long base = 26;
    long mod = 1_000_000_007;
    long patternHash = 0, textHash = 0;
    long power = 1;
    
    // Calculate initial hashes
    for (int i = 0; i < m; i++) {
        patternHash = (patternHash * base + pattern.charAt(i)) % mod;
        textHash = (textHash * base + text.charAt(i)) % mod;
        if (i < m - 1) power = (power * base) % mod;
    }
    
    // Slide window
    for (int i = 0; i <= n - m; i++) {
        if (patternHash == textHash) {
            // Verify actual match
            if (text.substring(i, i + m).equals(pattern)) {
                return i;
            }
        }
        
        if (i < n - m) {
            // Rolling hash: remove first, add next
            textHash = (textHash - text.charAt(i) * power % mod + mod) % mod;
            textHash = (textHash * base + text.charAt(i + m)) % mod;
        }
    }
    
    return -1;
}
```

### Mind-Map Anchor

```
RABIN-KARP
    |
    v
+----------------------+
| Rolling hash         |
| Remove first char    |
| Add new char         |
| O(1) hash update     |
| Verify on hash match |
+----------------------+
```

**Memory phrase:** "Rolling hash, remove old add new, verify matches"

---

## PATTERN 10: Longest Palindromic Substring (LC 5)

### Pattern Recognition Signal

> **When you see:** "Longest palindromic substring"
> **Instant thought:** "Expand around center! Try each position as center"

### Visual Dry Run

```
s = "babad"

Try each center:
Center 0 ('b'): expand -> "b" (length 1)
Center 0.5 (between b,a): expand -> "" (no match)
Center 1 ('a'): expand -> "bab" (length 3)
Center 1.5: expand -> "" 
Center 2 ('b'): expand -> "aba" (length 3)
Center 2.5: expand -> ""
Center 3 ('a'): expand -> "a" (length 1)
Center 3.5: expand -> ""
Center 4 ('d'): expand -> "d" (length 1)

Longest: "bab" or "aba" (length 3)
```

### The Code

```java
public String longestPalindrome(String s) {
    if (s.length() < 2) return s;
    
    int start = 0, maxLen = 1;
    
    for (int i = 0; i < s.length(); i++) {
        // Odd length palindrome (single center)
        int len1 = expandAroundCenter(s, i, i);
        // Even length palindrome (two centers)
        int len2 = expandAroundCenter(s, i, i + 1);
        
        int len = Math.max(len1, len2);
        
        if (len > maxLen) {
            maxLen = len;
            start = i - (len - 1) / 2;
        }
    }
    
    return s.substring(start, start + maxLen);
}

private int expandAroundCenter(String s, int left, int right) {
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
        left--;
        right++;
    }
    return right - left - 1;
}
```

### Mind-Map Anchor

```
LONGEST PALINDROME SUBSTRING
            |
            v
+------------------------+
| Expand around center   |
| Try odd (i,i) and      |
| even (i,i+1) centers   |
| Track max length       |
| O(n²) time             |
+------------------------+
```

**Memory phrase:** "Expand from each center, try odd and even"

---

## PATTERN 11: Shortest Palindrome (LC 214)

### Pattern Recognition Signal

> **When you see:** "Add characters to front to make palindrome"
> **Instant thought:** "Find longest palindrome prefix using KMP!"

### The Key Insight

Find longest palindrome starting at index 0. Add reverse of remaining suffix to front.

Use KMP: concatenate s + "#" + reverse(s), find LPS of last position.

### Visual Dry Run

```
s = "aacecaaa"

reverse(s) = "aaacecaa"
combined = "aacecaaa#aaacecaa"

Build LPS:
a a c e c a a a # a a a c e c a a
0 1 0 0 0 1 2 2 0 1 2 2 3 4 5 6 7

LPS[last] = 7 -> longest palindrome prefix has length 7
Palindrome prefix: "aacecaa"
Remaining: "a"
Add reverse("a") = "a" to front

Result: "a" + "aacecaaa" = "aaacecaaa"
```

### The Code

```java
public String shortestPalindrome(String s) {
    if (s.isEmpty()) return s;
    
    String rev = new StringBuilder(s).reverse().toString();
    String combined = s + "#" + rev;
    
    int[] lps = buildLPS(combined);
    int palindromeLen = lps[combined.length() - 1];
    
    String suffix = s.substring(palindromeLen);
    String prefix = new StringBuilder(suffix).reverse().toString();
    
    return prefix + s;
}
```

### Mind-Map Anchor

```
SHORTEST PALINDROME
        |
        v
+------------------------+
| s + "#" + reverse(s)   |
| Build LPS array        |
| LPS[last] = palindrome |
| prefix length          |
| Add reverse of rest    |
+------------------------+
```

**Memory phrase:** "KMP on s#reverse(s), LPS gives palindrome prefix length"

---

## PATTERN 12: Repeated Substring Pattern (LC 459)

### Pattern Recognition Signal

> **When you see:** "Can string be constructed by repeating substring?"
> **Instant thought:** "s+s contains s in middle if true! Or use KMP"

### The Trick

If s can be formed by repeating a pattern, then (s+s)[1:-1] contains s.

### Visual Dry Run

```
s = "abab"

s + s = "abababab"
Remove first and last: "bababa"

Does "bababa" contain "abab"? 
"bababa" -> found at index 1: "babab" NO
Wait, let me check: b-a-b-a-b-a
                      a-b-a-b -> YES at index 2!

Answer: true
```

### The Code

```java
public boolean repeatedSubstringPattern(String s) {
    String doubled = s + s;
    // Check if s exists in doubled[1:-1]
    return doubled.substring(1, doubled.length() - 1).contains(s);
}

// Alternative: KMP approach
public boolean repeatedSubstringPatternKMP(String s) {
    int[] lps = buildLPS(s);
    int len = lps[s.length() - 1];
    int patternLen = s.length() - len;
    
    return len > 0 && s.length() % patternLen == 0;
}
```

### Mind-Map Anchor

```
REPEATED SUBSTRING
        |
        v
+------------------------+
| Method 1: (s+s)[1:-1]  |
| contains s?            |
| Method 2: KMP LPS      |
| len % (n - LPS[n-1])==0|
+------------------------+
```

**Memory phrase:** "Double string, check middle contains original"

---

## PATTERN 13: Palindrome Pairs (LC 336)

### Pattern Recognition Signal

> **When you see:** "Find pairs that form palindrome when concatenated"
> **Instant thought:** "Trie of reversed words + check all prefix/suffix splits!"

### The Code

```java
public List<List<Integer>> palindromePairs(String[] words) {
    Map<String, Integer> map = new HashMap<>();
    for (int i = 0; i < words.length; i++) {
        map.put(words[i], i);
    }
    
    List<List<Integer>> result = new ArrayList<>();
    
    for (int i = 0; i < words.length; i++) {
        String word = words[i];
        
        for (int j = 0; j <= word.length(); j++) {
            String prefix = word.substring(0, j);
            String suffix = word.substring(j);
            
            // Case 1: prefix is palindrome, check if reverse(suffix) exists
            if (isPalindrome(prefix)) {
                String revSuffix = new StringBuilder(suffix).reverse().toString();
                if (map.containsKey(revSuffix) && map.get(revSuffix) != i) {
                    result.add(Arrays.asList(map.get(revSuffix), i));
                }
            }
            
            // Case 2: suffix is palindrome, check if reverse(prefix) exists
            if (suffix.length() > 0 && isPalindrome(suffix)) {
                String revPrefix = new StringBuilder(prefix).reverse().toString();
                if (map.containsKey(revPrefix) && map.get(revPrefix) != i) {
                    result.add(Arrays.asList(i, map.get(revPrefix)));
                }
            }
        }
    }
    
    return result;
}

private boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    while (left < right) {
        if (s.charAt(left++) != s.charAt(right--)) return false;
    }
    return true;
}
```

### Mind-Map Anchor

```
PALINDROME PAIRS
       |
       v
+------------------------+
| Map word -> index      |
| For each word, try all |
| prefix/suffix splits   |
| If prefix palindrome,  |
| find reverse(suffix)   |
| Vice versa for suffix  |
+------------------------+
```

**Memory phrase:** "Split at each position, check palindrome + reverse exists"

---

# QUICK REFERENCE: All 13 Trie & String Patterns

| # | Pattern | Key Technique |
|---|---------|---------------|
| 1 | Implement Trie | TrieNode array + isEnd |
| 2 | Add/Search Words | Trie + DFS for wildcard |
| 3 | Word Search II | Trie of words + grid DFS |
| 4 | Replace Words | Trie of roots, find prefix |
| 5 | Autocomplete | Trie + frequency map |
| 6 | Longest Word | BFS through complete words |
| 7 | Maximum XOR | Binary Trie, opposite bits |
| 8 | KMP strStr | LPS array, skip on mismatch |
| 9 | Rabin-Karp | Rolling hash |
| 10 | Longest Palindrome | Expand around center |
| 11 | Shortest Palindrome | KMP on s#reverse(s) |
| 12 | Repeated Substring | (s+s)[1:-1] contains s |
| 13 | Palindrome Pairs | Split + reverse lookup |

---

## When to Use What?

```
PREFIX OPERATIONS (insert, search, startsWith):
  -> Standard Trie

WILDCARD SEARCH (.):
  -> Trie + DFS

MULTIPLE WORDS IN GRID:
  -> Trie of words + Grid DFS

PATTERN MATCHING (single pattern):
  -> KMP (O(n+m))

PATTERN MATCHING (multiple patterns):
  -> Rabin-Karp or Aho-Corasick

PALINDROME SUBSTRING:
  -> Expand around center

XOR PROBLEMS:
  -> Binary Trie
```

---

*End of Trie & String Patterns Deep Dive - 13 Patterns for L5 MAANG*


---

# PART 3: ADDITIONAL TRIE PATTERNS

## PATTERN 14: Map Sum Pairs (LC 677)

### Pattern Recognition Signal

> **When you see:** "Sum of values for keys with given prefix"
> **Instant thought:** "Trie with value at each node, sum during traversal!"

### Visual Dry Run

```
insert("apple", 3)
insert("app", 2)

Trie:
      root
       |
       a (sum=5)
       |
       p (sum=5)
       |
       p (sum=5, val=2 for "app")
       |
       l (sum=3)
       |
       e (sum=3, val=3 for "apple")

sum("ap") -> traverse to 'p', return sum=5
sum("app") -> traverse to second 'p', return sum=5
```

### The Code

```java
class MapSum {
    private TrieNode root;
    private Map<String, Integer> map;
    
    public MapSum() {
        root = new TrieNode();
        map = new HashMap<>();
    }
    
    public void insert(String key, int val) {
        int delta = val - map.getOrDefault(key, 0);
        map.put(key, val);
        
        TrieNode node = root;
        for (char c : key.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
            node.sum += delta;
        }
    }
    
    public int sum(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) return 0;
            node = node.children[idx];
        }
        return node.sum;
    }
}

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    int sum = 0;
}
```

### Mind-Map Anchor

```
MAP SUM PAIRS
      |
      v
+----------------------+
| Trie with sum at node|
| Track delta for      |
| updates              |
| Traverse to prefix   |
| Return node.sum      |
+----------------------+
```

**Memory phrase:** "Store running sum at each node, use delta for updates"

---

## PATTERN 15: Prefix and Suffix Search (LC 745)

### Pattern Recognition Signal

> **When you see:** "Search by both prefix AND suffix"
> **Instant thought:** "Build Trie of suffix#word combinations!"

### The Key Insight

For word "apple", insert: "e#apple", "le#apple", "ple#apple", "pple#apple", "apple#apple"

Query "a", "e" -> search for "e#a"

### The Code

```java
class WordFilter {
    private TrieNode root;
    
    public WordFilter(String[] words) {
        root = new TrieNode();
        
        for (int weight = 0; weight < words.length; weight++) {
            String word = words[weight];
            
            // Insert all suffix#word combinations
            for (int i = 0; i <= word.length(); i++) {
                String key = word.substring(i) + "#" + word;
                insert(key, weight);
            }
        }
    }
    
    private void insert(String key, int weight) {
        TrieNode node = root;
        for (char c : key.toCharArray()) {
            int idx = c == '#' ? 26 : c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
            node.weight = weight;  // Latest weight wins
        }
    }
    
    public int f(String prefix, String suffix) {
        String key = suffix + "#" + prefix;
        TrieNode node = root;
        
        for (char c : key.toCharArray()) {
            int idx = c == '#' ? 26 : c - 'a';
            if (node.children[idx] == null) return -1;
            node = node.children[idx];
        }
        
        return node.weight;
    }
}

class TrieNode {
    TrieNode[] children = new TrieNode[27];  // 26 letters + #
    int weight = -1;
}
```

### Mind-Map Anchor

```
PREFIX + SUFFIX SEARCH
         |
         v
+------------------------+
| Insert suffix#word     |
| for all suffixes       |
| Query: suffix#prefix   |
| Store weight at nodes  |
+------------------------+
```

**Memory phrase:** "All suffix#word combos, query suffix#prefix"

---

## PATTERN 16: Stream of Characters (LC 1032)

### Pattern Recognition Signal

> **When you see:** "Stream of characters, check if suffix matches any word"
> **Instant thought:** "Trie of REVERSED words, check from end of stream!"

### The Code

```java
class StreamChecker {
    private TrieNode root;
    private StringBuilder stream;
    
    public StreamChecker(String[] words) {
        root = new TrieNode();
        stream = new StringBuilder();
        
        // Insert reversed words
        for (String word : words) {
            TrieNode node = root;
            for (int i = word.length() - 1; i >= 0; i--) {
                int idx = word.charAt(i) - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNode();
                }
                node = node.children[idx];
            }
            node.isEndOfWord = true;
        }
    }
    
    public boolean query(char letter) {
        stream.append(letter);
        
        TrieNode node = root;
        // Check from end of stream backwards
        for (int i = stream.length() - 1; i >= 0 && node != null; i--) {
            int idx = stream.charAt(i) - 'a';
            node = node.children[idx];
            if (node != null && node.isEndOfWord) {
                return true;
            }
        }
        
        return false;
    }
}
```

### Mind-Map Anchor

```
STREAM OF CHARACTERS
         |
         v
+------------------------+
| Trie of REVERSED words |
| On query, check stream |
| from END backwards     |
| Match = suffix exists  |
+------------------------+
```

**Memory phrase:** "Reverse words in Trie, check stream backwards"

---

## PATTERN 17: Concatenated Words (LC 472)

### Pattern Recognition Signal

> **When you see:** "Words formed by concatenating other words"
> **Instant thought:** "Trie + DP/DFS to check if word can be split!"

### The Code

```java
public List<String> findAllConcatenatedWordsInADict(String[] words) {
    Set<String> wordSet = new HashSet<>(Arrays.asList(words));
    List<String> result = new ArrayList<>();
    
    for (String word : words) {
        if (word.isEmpty()) continue;
        if (canForm(word, wordSet, new HashMap<>())) {
            result.add(word);
        }
    }
    
    return result;
}

private boolean canForm(String word, Set<String> wordSet, Map<String, Boolean> memo) {
    if (memo.containsKey(word)) return memo.get(word);
    
    for (int i = 1; i < word.length(); i++) {
        String prefix = word.substring(0, i);
        String suffix = word.substring(i);
        
        if (wordSet.contains(prefix)) {
            if (wordSet.contains(suffix) || canForm(suffix, wordSet, memo)) {
                memo.put(word, true);
                return true;
            }
        }
    }
    
    memo.put(word, false);
    return false;
}
```

### Mind-Map Anchor

```
CONCATENATED WORDS
        |
        v
+------------------------+
| For each word, try all |
| prefix splits          |
| If prefix in dict AND  |
| suffix in dict or can  |
| be formed -> valid     |
| Use memoization        |
+------------------------+
```

**Memory phrase:** "Split at each position, check prefix + recurse on suffix"

---

# PART 4: ADVANCED STRING PATTERNS

## PATTERN 18: Z-Algorithm

### Pattern Recognition Signal

> **When you see:** "Pattern matching" or "Longest prefix at each position"
> **Instant thought:** "Z-array! Z[i] = length of longest substring starting at i that matches prefix"

### Visual Dry Run

```
s = "aabxaab"

Z-array:
index: 0 1 2 3 4 5 6
char:  a a b x a a b
Z:     - 1 0 0 3 1 0

Z[1]=1: s[1..1]="a" matches s[0..0]="a"
Z[4]=3: s[4..6]="aab" matches s[0..2]="aab"
```

### The Code

```java
public int[] zFunction(String s) {
    int n = s.length();
    int[] z = new int[n];
    int l = 0, r = 0;
    
    for (int i = 1; i < n; i++) {
        if (i < r) {
            z[i] = Math.min(r - i, z[i - l]);
        }
        
        while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
            z[i]++;
        }
        
        if (i + z[i] > r) {
            l = i;
            r = i + z[i];
        }
    }
    
    return z;
}

// Pattern matching using Z-algorithm
public int findPattern(String text, String pattern) {
    String combined = pattern + "$" + text;
    int[] z = zFunction(combined);
    
    for (int i = pattern.length() + 1; i < combined.length(); i++) {
        if (z[i] == pattern.length()) {
            return i - pattern.length() - 1;
        }
    }
    
    return -1;
}
```

### Mind-Map Anchor

```
Z-ALGORITHM
     |
     v
+----------------------+
| Z[i] = longest match |
| with prefix starting |
| at position i        |
| Use [l,r] window     |
| O(n) time            |
+----------------------+
```

**Memory phrase:** "Z[i] = how much of prefix matches at position i"

---

## PATTERN 19: Longest Happy Prefix (LC 1392)

### Pattern Recognition Signal

> **When you see:** "Longest prefix that is also suffix"
> **Instant thought:** "KMP LPS array! LPS[n-1] is the answer"

### Visual Dry Run

```
s = "level"

Build LPS:
l e v e l
0 0 0 0 1

LPS[4] = 1 -> "l" is both prefix and suffix

s = "ababab"
a b a b a b
0 0 1 2 3 4

LPS[5] = 4 -> "abab" is both prefix and suffix
```

### The Code

```java
public String longestPrefix(String s) {
    int[] lps = new int[s.length()];
    int len = 0;
    int i = 1;
    
    while (i < s.length()) {
        if (s.charAt(i) == s.charAt(len)) {
            len++;
            lps[i] = len;
            i++;
        } else if (len > 0) {
            len = lps[len - 1];
        } else {
            lps[i] = 0;
            i++;
        }
    }
    
    return s.substring(0, lps[s.length() - 1]);
}
```

### Mind-Map Anchor

```
LONGEST HAPPY PREFIX
         |
         v
+----------------------+
| Build KMP LPS array  |
| LPS[n-1] = length of |
| longest prefix that  |
| is also suffix       |
+----------------------+
```

**Memory phrase:** "KMP LPS, answer is LPS[last]"

---

## PATTERN 20: Longest Duplicate Substring (LC 1044)

### Pattern Recognition Signal

> **When you see:** "Longest duplicate/repeated substring"
> **Instant thought:** "Binary search on length + Rabin-Karp rolling hash!"

### The Key Insight

Binary search on answer length. For each length, use rolling hash to find duplicates.

### The Code

```java
public String longestDupSubstring(String s) {
    int left = 1, right = s.length() - 1;
    String result = "";
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        String dup = findDuplicate(s, mid);
        
        if (dup != null) {
            result = dup;
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return result;
}

private String findDuplicate(String s, int len) {
    long base = 26;
    long mod = (1L << 32);
    long hash = 0;
    long power = 1;
    
    // Calculate initial hash and power
    for (int i = 0; i < len; i++) {
        hash = (hash * base + s.charAt(i)) % mod;
        if (i < len - 1) power = (power * base) % mod;
    }
    
    Map<Long, List<Integer>> seen = new HashMap<>();
    seen.put(hash, new ArrayList<>(Arrays.asList(0)));
    
    for (int i = 1; i <= s.length() - len; i++) {
        // Rolling hash
        hash = (hash - s.charAt(i - 1) * power % mod + mod) % mod;
        hash = (hash * base + s.charAt(i + len - 1)) % mod;
        
        if (seen.containsKey(hash)) {
            String current = s.substring(i, i + len);
            for (int prev : seen.get(hash)) {
                if (s.substring(prev, prev + len).equals(current)) {
                    return current;
                }
            }
        }
        
        seen.computeIfAbsent(hash, k -> new ArrayList<>()).add(i);
    }
    
    return null;
}
```

### Mind-Map Anchor

```
LONGEST DUPLICATE SUBSTRING
            |
            v
+------------------------+
| Binary search on length|
| For each length, use   |
| rolling hash to find   |
| duplicate              |
| O(n log n) average     |
+------------------------+
```

**Memory phrase:** "Binary search length + rolling hash for duplicates"

---

## PATTERN 21: Count Palindromic Substrings (LC 647)

### Pattern Recognition Signal

> **When you see:** "Count all palindromic substrings"
> **Instant thought:** "Expand around center, count instead of track!"

### Visual Dry Run

```
s = "aaa"

Center 0 ('a'): expand -> "a" (count 1)
Center 0.5: expand -> "aa" (count 1)
Center 1 ('a'): expand -> "a", "aaa" (count 2)
Center 1.5: expand -> "aa" (count 1)
Center 2 ('a'): expand -> "a" (count 1)

Total: 1 + 1 + 2 + 1 + 1 = 6
Palindromes: "a", "a", "a", "aa", "aa", "aaa"
```

### The Code

```java
public int countSubstrings(String s) {
    int count = 0;
    
    for (int i = 0; i < s.length(); i++) {
        // Odd length
        count += expandAndCount(s, i, i);
        // Even length
        count += expandAndCount(s, i, i + 1);
    }
    
    return count;
}

private int expandAndCount(String s, int left, int right) {
    int count = 0;
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
        count++;
        left--;
        right++;
    }
    return count;
}
```

### Mind-Map Anchor

```
COUNT PALINDROMES
       |
       v
+----------------------+
| Expand around center |
| Count each expansion |
| Try odd and even     |
| O(n²) time           |
+----------------------+
```

**Memory phrase:** "Expand and count, same as longest but count all"

---

## PATTERN 22: Repeated DNA Sequences (LC 187)

### Pattern Recognition Signal

> **When you see:** "Find repeated substrings of fixed length"
> **Instant thought:** "Rolling hash or HashSet of substrings!"

### The Code

```java
public List<String> findRepeatedDnaSequences(String s) {
    Set<String> seen = new HashSet<>();
    Set<String> repeated = new HashSet<>();
    
    for (int i = 0; i <= s.length() - 10; i++) {
        String sub = s.substring(i, i + 10);
        if (!seen.add(sub)) {
            repeated.add(sub);
        }
    }
    
    return new ArrayList<>(repeated);
}

// Optimized with bit manipulation (2 bits per nucleotide)
public List<String> findRepeatedDnaSequencesBit(String s) {
    if (s.length() < 10) return new ArrayList<>();
    
    Map<Character, Integer> map = Map.of('A', 0, 'C', 1, 'G', 2, 'T', 3);
    Set<Integer> seen = new HashSet<>();
    Set<Integer> repeated = new HashSet<>();
    List<String> result = new ArrayList<>();
    
    int hash = 0;
    int mask = (1 << 20) - 1;  // 10 chars * 2 bits = 20 bits
    
    for (int i = 0; i < s.length(); i++) {
        hash = ((hash << 2) | map.get(s.charAt(i))) & mask;
        
        if (i >= 9) {
            if (!seen.add(hash) && repeated.add(hash)) {
                result.add(s.substring(i - 9, i + 1));
            }
        }
    }
    
    return result;
}
```

### Mind-Map Anchor

```
REPEATED DNA SEQUENCES
          |
          v
+------------------------+
| Fixed length = 10      |
| HashSet of seen        |
| Add to result if seen  |
| twice                  |
| Optimize: 2-bit encode |
+------------------------+
```

**Memory phrase:** "HashSet of 10-char substrings, track seen twice"

---

# COMPLETE INDEX: All 22 Trie & String Patterns

## Trie Patterns (1-7, 14-17)
| # | Pattern | LeetCode | Key Technique |
|---|---------|----------|---------------|
| 1 | Implement Trie | 208 | TrieNode array + isEnd |
| 2 | Add/Search Words | 211 | Trie + DFS for wildcard |
| 3 | Word Search II | 212 | Trie of words + grid DFS |
| 4 | Replace Words | 648 | Trie of roots, find prefix |
| 5 | Autocomplete | 642 | Trie + frequency map |
| 6 | Longest Word | 720 | BFS through complete words |
| 7 | Maximum XOR | 421 | Binary Trie, opposite bits |
| 14 | Map Sum Pairs | 677 | Trie with sum at nodes |
| 15 | Prefix+Suffix Search | 745 | suffix#word combinations |
| 16 | Stream of Characters | 1032 | Reversed words Trie |
| 17 | Concatenated Words | 472 | Trie + DP word split |

## String Algorithm Patterns (8-13, 18-22)
| # | Pattern | LeetCode | Key Technique |
|---|---------|----------|---------------|
| 8 | KMP strStr | 28 | LPS array, skip on mismatch |
| 9 | Rabin-Karp | - | Rolling hash |
| 10 | Longest Palindrome | 5 | Expand around center |
| 11 | Shortest Palindrome | 214 | KMP on s#reverse(s) |
| 12 | Repeated Substring | 459 | (s+s)[1:-1] contains s |
| 13 | Palindrome Pairs | 336 | Split + reverse lookup |
| 18 | Z-Algorithm | - | Z[i] = prefix match length |
| 19 | Longest Happy Prefix | 1392 | KMP LPS[n-1] |
| 20 | Longest Duplicate | 1044 | Binary search + rolling hash |
| 21 | Count Palindromes | 647 | Expand and count |
| 22 | Repeated DNA | 187 | HashSet of fixed substrings |

---

## MAANG Interview Frequency

```
HIGH FREQUENCY (Must Know):
├── Implement Trie (LC 208)
├── Word Search II (LC 212)
├── Design Autocomplete (LC 642)
├── KMP Algorithm
├── Longest Palindromic Substring (LC 5)
└── Maximum XOR (LC 421)

MEDIUM FREQUENCY:
├── Add/Search Words (LC 211)
├── Replace Words (LC 648)
├── Palindrome Pairs (LC 336)
├── Shortest Palindrome (LC 214)
└── Concatenated Words (LC 472)

GOOD TO KNOW:
├── Z-Algorithm
├── Rabin-Karp
├── Manacher's Algorithm
└── Longest Duplicate Substring (LC 1044)
```

---

*End of Trie & String Patterns Deep Dive - 22 Patterns for L5 MAANG*


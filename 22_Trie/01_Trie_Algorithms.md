# Section 22 — Trie Algorithms

---

## 1. What Problem Does This Solve?

A Trie (prefix tree) is a tree data structure optimized for string prefix operations. It solves:
- "Does any word in the dictionary start with prefix 'ap'?" → O(L) where L = prefix length
- Autocomplete (find all words with given prefix)
- Word search in a dictionary of thousands of words
- Maximum XOR of two numbers (binary Trie)

With a HashMap, prefix search requires scanning all keys. With a Trie, prefix search is O(L) — independent of dictionary size.

---

## 2. Beginner-Friendly Intuition

A Trie is a **family tree for strings**. The root is empty. Each edge is labeled with a character. A path from root to a marked node spells a complete word. All words sharing a prefix share the same path from root up to where they diverge.

Think of a **phone book index**: common prefixes (all names starting with "Smi") share the same branch before diverging into "Smith," "Smile," "Smirk."

---

## 3. Real-World Analogy

**Google Autocomplete:** You type "app" and see "apple," "application," "approve." The search engine traverses the Trie to the node for "app" and then lists all paths from that node to complete words.

**IP routing tables:** Network routers use tries (PATRICIA tries) to match the longest prefix of an IP address to find the best route.

---

## 4. Core Concept

### Trie Node Structure

```
TrieNode:
    children: TrieNode[26]  (or HashMap<Character, TrieNode>)
    isEnd: boolean          (true if a word ends here)
```

### Key Operations

| Operation | Time | Description |
|-----------|------|-------------|
| Insert | O(L) | Follow/create nodes for each char |
| Search | O(L) | Follow nodes; check isEnd at last |
| StartsWith | O(L) | Follow nodes; return true if path exists |
| Delete | O(L) | Unmark isEnd; prune if no other children |

---

## 5. Pattern Recognition Signals

Use Trie when:
```
"Autocomplete / word suggestions"
"Does any word start with prefix?"
"Word search in grid + dictionary"
"Longest word in dictionary built from other words"
"Replace words with shortest root"
"Maximum XOR pair in array"
"Implement a dictionary with prefix search"
"Stream of characters — word at any point?"
```

---

## 6. Step-by-Step Algorithm

### Insert Word
```
current = root
For each char c in word:
    If current.children[c] doesn't exist:
        current.children[c] = new TrieNode()
    current = current.children[c]
current.isEnd = true
```

### Search Word
```
current = root
For each char c in word:
    If current.children[c] doesn't exist:
        Return false
    current = current.children[c]
Return current.isEnd
```

### StartsWith Prefix
```
current = root
For each char c in prefix:
    If current.children[c] doesn't exist:
        Return false
    current = current.children[c]
Return true  (reached end of prefix without missing a node)
```

---

## 7. Dry Run with Example

### Insert "apple", "app", "apply"

```
root
└── 'a' → TrieNode
    └── 'p' → TrieNode
        └── 'p' → TrieNode [isEnd=true] ("app")
            ├── 'l' → TrieNode
            │   ├── 'e' → TrieNode [isEnd=true] ("apple")
            │   └── 'y' → TrieNode [isEnd=true] ("apply")
```

**search("apple"):** root→a→p→p→l→e. isEnd=true → true ✓
**search("appl"):**  root→a→p→p→l. isEnd=false → false ✓
**startsWith("app"):** root→a→p→p. path exists → true ✓
**startsWith("apt"):** root→a→p, then 't' not in children → false ✓

---

## 8. Code Implementation

### Trie Data Structure

```java
class Trie {
    private TrieNode root;

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }

    Trie() { root = new TrieNode(); }

    void insert(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (curr.children[idx] == null)
                curr.children[idx] = new TrieNode();
            curr = curr.children[idx];
        }
        curr.isEnd = true;
    }

    boolean search(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (curr.children[idx] == null) return false;
            curr = curr.children[idx];
        }
        return curr.isEnd;
    }

    boolean startsWith(String prefix) {
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (curr.children[idx] == null) return false;
            curr = curr.children[idx];
        }
        return true;
    }
}
```

### Word Search II (Find All Words from Board Using Trie)

```java
List<String> findWords(char[][] board, String[] words) {
    Trie trie = new Trie();
    for (String w : words) trie.insert(w);
    Set<String> result = new HashSet<>();
    int m = board.length, n = board[0].length;
    for (int r = 0; r < m; r++)
        for (int c = 0; c < n; c++)
            dfs(board, r, c, trie.root, new StringBuilder(), result);
    return new ArrayList<>(result);
}

void dfs(char[][] board, int r, int c, Trie.TrieNode node, StringBuilder path, Set<String> result) {
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c] == '#') return;
    char ch = board[r][c];
    Trie.TrieNode next = node.children[ch - 'a'];
    if (next == null) return; // no word starts with this prefix
    path.append(ch);
    if (next.isEnd) result.add(path.toString());
    board[r][c] = '#'; // mark visited
    dfs(board, r+1, c, next, path, result);
    dfs(board, r-1, c, next, path, result);
    dfs(board, r, c+1, next, path, result);
    dfs(board, r, c-1, next, path, result);
    board[r][c] = ch; // restore
    path.deleteCharAt(path.length() - 1);
}
```

### Replace Words with Root (Shortest Prefix)

```java
String replaceWords(List<String> dictionary, String sentence) {
    Trie trie = new Trie();
    for (String root : dictionary) trie.insert(root);
    StringBuilder result = new StringBuilder();
    for (String word : sentence.split(" ")) {
        if (result.length() > 0) result.append(" ");
        // Find shortest prefix in trie
        Trie.TrieNode curr = trie.root;
        StringBuilder prefix = new StringBuilder();
        boolean found = false;
        for (char c : word.toCharArray()) {
            if (curr.children[c - 'a'] == null) break;
            prefix.append(c);
            curr = curr.children[c - 'a'];
            if (curr.isEnd) { found = true; break; }
        }
        result.append(found ? prefix : word);
    }
    return result.toString();
}
```

### Maximum XOR of Two Numbers (Binary Trie)

```java
int findMaximumXOR(int[] nums) {
    // Build binary trie (32-bit integers)
    int[][] trie = new int[32 * nums.length + 1][2];
    int trieSize = 1;
    for (int num : nums) {
        int node = 0;
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if (trie[node][bit] == 0) trie[node][bit] = trieSize++;
            node = trie[node][bit];
        }
    }
    int max = 0;
    for (int num : nums) {
        int node = 0, xor = 0;
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            int want = 1 - bit; // want opposite bit for max XOR
            if (trie[node][want] != 0) { xor |= (1 << i); node = trie[node][want]; }
            else node = trie[node][bit];
        }
        max = Math.max(max, xor);
    }
    return max;
}
```

---

## 9. Time Complexity

| Operation | Complexity | Notes |
|-----------|-----------|-------|
| Insert | O(L) | L = word length |
| Search | O(L) | L = word length |
| StartsWith | O(L) | L = prefix length |
| Build trie for n words avg len L | O(n×L) | Total insert time |
| Word Search II | O(m×n×4^L) | Grid DFS pruned by trie |

---

## 10. Space Complexity

| Structure | Space | Notes |
|-----------|-------|-------|
| Array-based trie | O(n×L×26) | n words, avg len L, alphabet 26 |
| HashMap-based trie | O(n×L) | Sparser for large alphabets |
| Binary trie | O(n×32) | For 32-bit integers |

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| Empty string insert | Set root.isEnd = true |
| Searching word longer than any in trie | Returns false when path breaks |
| Same word inserted twice | isEnd stays true, no duplicate |
| Prefix equals a full word | startsWith=true AND search=true |
| Non-lowercase chars | Use HashMap<Character, TrieNode> instead of int[26] |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Confusing search() and startsWith()
// search("app") returns false if "app" wasn't inserted, even if "apple" was
// startsWith("app") returns true if any word starting with "app" was inserted

// MISTAKE 2: Index calculation
int idx = c; // WRONG: using raw char as array index
int idx = c - 'a'; // CORRECT: offset by 'a' for lowercase letters

// MISTAKE 3: Not restoring board cell in Word Search II
board[r][c] = '#'; // mark
dfs(board, r+1, c, ...);
// MISSING: board[r][c] = ch; // must restore after DFS

// MISTAKE 4: Not checking all 4 directions in Word Search
// All 4 directions: up, down, left, right — don't miss any

// MISTAKE 5: Using array-based trie for non-lowercase strings
// If characters can be anything, use HashMap<Character, TrieNode>
// Array trie[26] only works for lowercase a-z
```

---

## 13. Interview-Level Explanation

**Q: "Why use a Trie instead of a HashSet for prefix searches?"**

> "A HashSet can check exact word membership in O(1), but prefix search requires iterating all keys — O(n×L) for n words of length L. A Trie organizes words by their shared prefixes, so a prefix search traverses exactly L nodes regardless of dictionary size. When your operations are prefix-based (autocomplete, spell check, IP routing), a Trie is strictly better."

**Q: "How does Word Search II use the Trie to prune the DFS?"**

> "In naive Word Search II, you'd run a separate DFS for each of n words — O(n × m × n × 4^L). With a Trie, all words share the same DFS. At each cell, we check if any word continues through this cell by looking up the character in the current Trie node's children. If no word has this prefix, we immediately stop — no need to explore deeper. This is the key pruning optimization."

---

## 14. Real-World Use Cases

| Application | Trie Usage |
|------------|-----------|
| **Search engines** | Autocomplete, spell correction |
| **IDE code completion** | Method/variable name suggestions |
| **IP routing** | Longest prefix matching in routers |
| **DNS lookup** | Domain name hierarchical lookup |
| **T9 phone input** | Multi-letter key → word prediction |
| **Bioinformatics** | DNA/protein sequence databases |
| **Spell checkers** | Prefix-based word suggestions |

---

## 15. Variations of This Pattern

| Variation | Key Difference | Example |
|-----------|---------------|---------|
| Basic Trie | search + startsWith | Implement Trie |
| Wildcard Trie | Handle '.' with recursion | Design Add/Search |
| Compressed Trie | Merge single-child chains | Patricia Trie |
| Binary Trie | For XOR optimization | Maximum XOR |
| Aho-Corasick | Multi-pattern matching | Find Words in Text |
| Suffix Trie | All suffixes indexed | Substring search |

---

## 16. Practice Problems

### Easy — Foundation
1. **Implement Trie (Prefix Tree)** (LeetCode #208)
   - *Task:* Implement insert, search, startsWith.
   - *Hint:* TrieNode with children[26] and isEnd boolean.

2. **Longest Common Prefix** (LeetCode #14)
   - *Task:* Find longest common prefix of all strings.
   - *Hint:* Insert all in Trie, traverse until branch or end.

3. **Autocomplete System** — conceptual
   - *Task:* Given prefix, return top-3 matching words.
   - *Hint:* Trie + DFS from prefix node, collect all words, sort by frequency.

### Medium — Core Trie Problems
1. **Add and Search Word** (LeetCode #211)
   - *Task:* Support '.' wildcard in search.
   - *Hint:* DFS with branching at '.' to try all 26 children.

2. **Replace Words** (LeetCode #648)
   - *Task:* Replace words with their shortest root prefix.
   - *Hint:* Build Trie from roots. For each word, find shortest matching prefix.

3. **Maximum XOR of Two Numbers** (LeetCode #421)
   - *Task:* Max XOR of any two numbers in array.
   - *Hint:* Binary Trie. For each num, greedily pick opposite bit.

4. **Concatenated Words** (LeetCode #472)
   - *Task:* Find words that can be formed from other words in dictionary.
   - *Hint:* Trie + DP or Trie + DFS for each word.

5. **Design Search Autocomplete System** (LeetCode #642)
   - *Task:* Return top-3 historical sentences with given prefix.
   - *Hint:* Trie where each node stores ranked sentences.

### Hard — Advanced Trie
1. **Word Search II** (LeetCode #212)
   - *Task:* Find all words from dictionary in a 2D board.
   - *Hint:* Build Trie from words, DFS on board pruned by Trie.

2. **Palindrome Pairs** (LeetCode #336)
   - *Task:* Find all pairs (i,j) where words[i]+words[j] is a palindrome.
   - *Hint:* Trie of reversed words. For each word, search for palindrome completions.

3. **Stream of Characters** (LeetCode #1032)
   - *Task:* At each new character, check if any search word ends here.
   - *Hint:* Reverse Trie (insert reversed words). Match from end of stream backward.

---

## 17. How to Know You Have Mastered Trie Algorithms

You have mastered this topic when you can:
- [ ] Write the Trie class (insert, search, startsWith) from memory
- [ ] Explain why Trie is better than HashSet for prefix queries
- [ ] Implement Add and Search Word with '.' wildcard
- [ ] Use Trie to prune DFS in Word Search II
- [ ] Describe how Binary Trie enables O(n log max) maximum XOR
- [ ] Replace words with shortest prefix using Trie traversal
- [ ] Know when to use HashMap-based vs array-based Trie nodes
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. After inserting "cat", "can", "cow", "car", draw the Trie structure.

2. Why does search("ca") return false even after inserting "cat"?

3. In Word Search II, why do we mark `board[r][c] = '#'` and restore after DFS?

4. The wildcard '.' in "Add and Search Word" tries all 26 children. What's the worst-case time for a search with all wildcards?

5. For Maximum XOR using Binary Trie: given nums=[3,10,5,25,2,8], how does the trie for 25 (11001) differ from 3 (00011)?

6. What is the space tradeoff between TrieNode with `children[26]` vs `HashMap<Character, TrieNode>`?

7. Can a Trie store non-string data? Give an example.

8. If you need to check if a word exists AND find all words with a given prefix, which data structure is best — HashMap, Trie, or sorted array?

> **Answers:**
> 1. root→c→a→t[end], root→c→a→n[end], root→c→a→r[end], root→c→o→w[end]. Nodes 'c' and 'a' are shared by cat/can/car.
> 2. "ca" was never marked as a complete word (isEnd=false at the 'a' node). search() returns curr.isEnd which is false. startsWith("ca") would return true.
> 3. To prevent reusing the same cell in a single word path. Without marking, you'd follow cycles or revisit cells. Restoring allows other word searches to use that cell.
> 4. O(26^L × nodes) where L is pattern length. Worst case when pattern is all wildcards and trie is fully branching.
> 5. 25 = 11001₂: path goes right-right-left-left-right from root. 3 = 00011₂: path goes left-left-left-right-right. They differ from the first bit.
> 6. Array[26]: O(26) space per node regardless of children, O(1) child access. HashMap: O(k) space (k=actual children), but O(1) average access with higher constants. For dense (many children) use array; for sparse alphabets use HashMap.
> 7. Yes. Binary Trie stores integers (as 32-bit binary paths). IP routing tables store IP addresses (as 32-bit binary paths). You can store any data expressible as a sequence of choices.
> 8. Trie. HashMap: prefix search is O(n×L) (scan all keys). Sorted array: binary search for prefix range is O(log n + k). Trie: exact O(L) prefix navigation, then DFS for all matching words.

---

**Next →** `../23_Range_Query/01_Range_Query_Structures.md`
- "Find all words with prefix 'hel'"
- Autocomplete suggestions

---

## 2. Beginner-Friendly Intuition

A Trie is like a branching road:
- Each junction is a character.
- Following a path spells out a word.
- All words sharing a prefix share the same path.

```
Insert: "apple", "app", "apt", "bat"

          root
         /    \
        a      b
        |      |
        p      a
       /  \    |
      p    t   t
      |
      l
      |
      e
```

---

## 3. Java Implementation

```java
class Trie {
    TrieNode root = new TrieNode();

    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd = false;
    }

    void insert(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (curr.children[idx] == null)
                curr.children[idx] = new TrieNode();
            curr = curr.children[idx];
        }
        curr.isEnd = true;
    }

    boolean search(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (curr.children[idx] == null) return false;
            curr = curr.children[idx];
        }
        return curr.isEnd;
    }

    boolean startsWith(String prefix) {
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (curr.children[idx] == null) return false;
            curr = curr.children[idx];
        }
        return true;
    }
}
```

---

## 4. Complexity

| Operation | Time | Space |
|-----------|------|-------|
| Insert | O(m) | O(m × ALPHABET) per word |
| Search | O(m) | — |
| StartsWith | O(m) | — |
| Total space | — | O(total characters) |

m = length of word/prefix

---

## 5. Word Search II (Trie + DFS)

**Problem:** Find all words from a list that exist in the board.

```java
List<String> findWords(char[][] board, String[] words) {
    Trie trie = new Trie();
    for (String word : words) trie.insert(word);

    Set<String> result = new HashSet<>();
    int m = board.length, n = board[0].length;
    for (int r = 0; r < m; r++)
        for (int c = 0; c < n; c++)
            dfs(board, r, c, trie.root, new StringBuilder(), result);
    return new ArrayList<>(result);
}

void dfs(char[][] board, int r, int c, Trie.TrieNode node,
         StringBuilder path, Set<String> result) {
    if (r<0||r>=board.length||c<0||c>=board[0].length||board[r][c]=='#') return;
    char ch = board[r][c];
    Trie.TrieNode next = node.children[ch-'a'];
    if (next == null) return;
    path.append(ch);
    if (next.isEnd) result.add(path.toString());
    board[r][c] = '#';
    dfs(board, r+1,c, next, path, result);
    dfs(board, r-1,c, next, path, result);
    dfs(board, r,c+1, next, path, result);
    dfs(board, r,c-1, next, path, result);
    board[r][c] = ch;
    path.deleteCharAt(path.length()-1);
}
```

---

## 6. Maximum XOR Using Trie (Binary Trie)

```java
class BinaryTrie {
    int[][] children = new int[32 * 100001][2];
    int idx = 1;

    void insert(int num) {
        int node = 0;
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if (children[node][bit] == 0)
                children[node][bit] = idx++;
            node = children[node][bit];
        }
    }

    int maxXor(int num) {
        int node = 0, xor = 0;
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            int want = 1 - bit;  // we want the opposite bit for max XOR
            if (children[node][want] != 0) { xor |= (1 << i); node = children[node][want]; }
            else node = children[node][bit];
        }
        return xor;
    }
}
```

---

## 7. Real-World Use Cases

- **Autocomplete:** Google search suggestions
- **Spell checker:** Word dictionary lookups
- **IP Routing:** Longest prefix matching in routers
- **T9 keyboard:** Multi-tap phone keyboard predictions

---

## 8. Practice Problems

**Easy:**
1. Implement Trie (Prefix Tree).
2. Longest Common Prefix.
3. Search Suggestions System.

**Medium:**
1. Design Add and Search Words.
2. Replace Words.
3. Implement Magic Dictionary.
4. Map Sum Pairs.
5. Longest Word with All Prefixes.

**Hard:**
1. Word Search II.
2. Maximum XOR of Two Numbers.
3. Palindrome Pairs using Trie.

# Section 21 — String Algorithms

---

## 1. What Problem Does This Solve?

String algorithms solve pattern matching, substring search, and string manipulation problems efficiently. The naive approach for finding a pattern in a text is O(n×m) — for each position in the text, check if the pattern matches. Specialized algorithms reduce this to O(n+m) by exploiting structure in the pattern itself.

Key problems:
- Find all occurrences of a pattern in text (KMP, Rabin-Karp)
- Longest palindromic substring (Expand Around Center, Manacher)
- String hashing and comparison
- Anagram detection

---

## 2. Beginner-Friendly Intuition

**KMP:** Instead of restarting from scratch when a mismatch occurs, KMP knows how far back it needs to reset based on the pattern's internal structure (repeated prefixes). It preprocesses the pattern to build a "failure function" — a map that says "if matching fails at position i, restart from position failure[i]."

**Rabin-Karp:** Instead of comparing characters one by one, compute a hash of the current window and compare to the pattern's hash. If hashes match, do a character-by-character verification.

---

## 3. Real-World Analogy

**KMP — Smart text search:** Instead of rereading a book from the previous paragraph every time you find a mismatch for a keyword, you jump to the next potential match position based on what you already know about the keyword's structure.

**Rolling Hash — Plagiarism detection:** Hash every paragraph in a document. To find if a paragraph appears in a database, just compute its hash and look it up — without reading the entire database character by character.

---

## 4. Core Concept

### KMP Algorithm

Two phases:
1. **Build failure function (`lps` array):** `lps[i]` = length of longest proper prefix of `pattern[0..i]` that is also a suffix.
2. **Search:** When mismatch at `j`, don't restart at `j=0`. Jump to `j = lps[j-1]`.

### Rabin-Karp (Rolling Hash)

Hash of window = `(hash_prev - arr[left] × base^(k-1)) × base + arr[right]`

Key: update hash in O(1) as window slides.

### Palindrome Expansion

From each center (n centers for odd, n-1 centers for even length strings), expand outward while characters match.

---

## 5. Pattern Recognition Signals

Use String algorithms when:
```
"Find pattern in text" → KMP or Rabin-Karp
"Longest palindromic substring" → Expand Around Center or Manacher
"Check if string is rotation of another" → s2 + s2 contains s1?
"Anagram detection" → sliding window + character count
"Repeated substring pattern" → KMP failure function
"Count palindromic substrings" → expand around center
"String matching with wildcards" → DP
```

---

## 6. Step-by-Step Algorithm

### KMP — Build LPS (Failure Function)
```
lps[0] = 0, len = 0, i = 1
While i < m (pattern length):
    If pattern[i] == pattern[len]:
        len++; lps[i] = len; i++
    Else:
        If len != 0: len = lps[len-1]  ← DON'T increment i
        Else: lps[i] = 0; i++
```

### KMP — Search
```
i = 0 (text index), j = 0 (pattern index)
While i < n:
    If text[i] == pattern[j]: i++; j++
    If j == m: MATCH found at position i-j. j = lps[j-1]
    Else if i < n AND text[i] != pattern[j]:
        If j != 0: j = lps[j-1]
        Else: i++
```

### Expand Around Center (Palindromes)
```
For each center c (0 to 2n-2):
    left = c / 2
    right = left + c % 2  (handles both odd and even length centers)
    While left >= 0 AND right < n AND s[left] == s[right]:
        left--; right++
    length = right - left - 1
    Update if longest
```

---

## 7. Dry Run with Example

### Example 1: KMP LPS Array

**Pattern:** `"AABAAB"`

```
i=0: lps[0]=0 (by definition)
i=1, len=0: p[1]='A'==p[0]='A' → len=1, lps[1]=1, i=2
i=2, len=1: p[2]='B'!=p[1]='A' → len=lps[0]=0
            p[2]='B'!=p[0]='A' → lps[2]=0, i=3
i=3, len=0: p[3]='A'==p[0]='A' → len=1, lps[3]=1, i=4
i=4, len=1: p[4]='A'==p[1]='A' → len=2, lps[4]=2, i=5
i=5, len=2: p[5]='B'==p[2]='B' → len=3, lps[5]=3, i=6

LPS = [0, 1, 0, 1, 2, 3]
Meaning: "AABAAB" has prefix "AAB" = suffix "AAB" (length 3) ✓
```

### Example 2: Longest Palindromic Substring

**Input:** `s = "babad"`

```
Centers (both odd and even):

c=0: 'b', expand: left=0,right=0 → len=1, palindrome="b"
c=1: between b,a (even) → b≠a → len=0
c=2: 'a', expand: left=1,right=1 → 'a'
     expand: left=0,right=2 → 'b'≠'b'... wait
     actually left=1,right=1='a'. left=0,right=2: s[0]='b',s[2]='b'... 
     
Let's redo:
c=0 (center at index 0): left=0, right=0 → "b", len=1
c=1 (even center at 0,1): left=0, right=1 → 'b'≠'a' → len=0
c=2 (center at index 1): left=1, right=1 → "a"
     expand: left=0, right=2 → s[0]='b', s[2]='b' → match! "bab", len=3
     expand: left=-1 → stop. Palindrome="bab", len=3
c=3 (even center at 1,2): left=1, right=2 → 'a'≠'b' → len=0
c=4 (center at index 2): left=2, right=2 → "b"
     expand: left=1, right=3 → s[1]='a', s[3]='a' → match! "aba", len=3
     expand: left=0, right=4 → s[0]='b', s[4]='d' → no match. Palindrome="aba", len=3
c=5 (even center 2,3): 'b'≠'a' → len=0
c=6 (center at 3): "a", expand → 'b'≠'d' → len=1
c=7 (even center 3,4): 'a'≠'d' → len=0
c=8 (center at 4): "d", len=1

Longest: "bab" or "aba", both length 3. Return either. ✓
```

---

## 8. Code Implementation

### KMP Pattern Matching

```java
List<Integer> kmpSearch(String text, String pattern) {
    List<Integer> result = new ArrayList<>();
    int n = text.length(), m = pattern.length();
    int[] lps = buildLPS(pattern);
    int i = 0, j = 0;
    while (i < n) {
        if (text.charAt(i) == pattern.charAt(j)) { i++; j++; }
        if (j == m) {
            result.add(i - j); // match at index i-j
            j = lps[j - 1];
        } else if (i < n && text.charAt(i) != pattern.charAt(j)) {
            if (j != 0) j = lps[j - 1];
            else i++;
        }
    }
    return result;
}

int[] buildLPS(String pattern) {
    int m = pattern.length();
    int[] lps = new int[m];
    int len = 0, i = 1;
    while (i < m) {
        if (pattern.charAt(i) == pattern.charAt(len)) {
            lps[i++] = ++len;
        } else {
            if (len != 0) len = lps[len - 1];
            else lps[i++] = 0;
        }
    }
    return lps;
}
```

### Longest Palindromic Substring (Expand Around Center)

```java
String longestPalindrome(String s) {
    if (s.length() < 2) return s;
    int start = 0, maxLen = 1;
    for (int center = 0; center < 2 * s.length() - 1; center++) {
        int left = center / 2;
        int right = left + center % 2; // right = left for odd, left+1 for even
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            if (right - left + 1 > maxLen) {
                maxLen = right - left + 1;
                start = left;
            }
            left--; right++;
        }
    }
    return s.substring(start, start + maxLen);
}
```

### Palindromic Substrings Count

```java
int countSubstrings(String s) {
    int count = 0;
    for (int center = 0; center < 2 * s.length() - 1; center++) {
        int left = center / 2, right = left + center % 2;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++;
            left--; right++;
        }
    }
    return count;
}
```

### Find All Anagrams in String (Sliding Window)

```java
List<Integer> findAnagrams(String s, String p) {
    List<Integer> result = new ArrayList<>();
    if (s.length() < p.length()) return result;
    int[] pCount = new int[26], sCount = new int[26];
    for (char c : p.toCharArray()) pCount[c - 'a']++;
    for (int i = 0; i < p.length(); i++) sCount[s.charAt(i) - 'a']++;
    if (Arrays.equals(pCount, sCount)) result.add(0);
    for (int i = p.length(); i < s.length(); i++) {
        sCount[s.charAt(i) - 'a']++;
        sCount[s.charAt(i - p.length()) - 'a']--;
        if (Arrays.equals(pCount, sCount)) result.add(i - p.length() + 1);
    }
    return result;
}
```

### Check if String is Rotation

```java
boolean isRotation(String s, String goal) {
    if (s.length() != goal.length()) return false;
    return (s + s).contains(goal); // every rotation of s is a substring of s+s
}
```

### Check Repeated Substring Pattern (KMP trick)

```java
boolean repeatedSubstringPattern(String s) {
    // Build LPS. If lps[last] > 0 AND s.length() % (s.length()-lps[last]) == 0
    int[] lps = buildLPS(s);
    int len = lps[s.length() - 1];
    return len > 0 && s.length() % (s.length() - len) == 0;
}
```

---

## 9. Time Complexity

| Algorithm | Time | Notes |
|-----------|------|-------|
| Naive pattern search | O(n×m) | Re-check at each mismatch |
| KMP | O(n+m) | LPS build O(m) + search O(n) |
| Rabin-Karp average | O(n+m) | O(n×m) worst (hash collisions) |
| Expand around center | O(n²) | n centers, up to n expansion each |
| Manacher | O(n) | Linear palindrome finding |
| Sliding window anagram | O(n) | Fixed-size window |

---

## 10. Space Complexity

| Algorithm | Space |
|-----------|-------|
| KMP | O(m) for LPS array |
| Expand around center | O(1) |
| Sliding window anagram | O(1) for int[26] |

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| Empty string | Return 0 or "" |
| Pattern longer than text | Impossible match; return empty list |
| Single character palindrome | Always returns itself |
| All same characters | KMP LPS all = 1,2,3,...,m-1 |
| Even/odd length palindromes | Expand around center handles both |

---

## 12. Common Mistakes

```java
// MISTAKE 1: In KMP, not decrementing len before re-checking (looping instead)
// Wrong: setting len=0 directly when mismatch
if (len != 0) len = lps[len-1]; // CORRECT: use LPS to fall back, not restart
else lps[i++] = 0;

// MISTAKE 2: Off-by-one in palindrome substring
right - left + 1 // CORRECT window size
right - left     // WRONG (misses 1 character)

// MISTAKE 3: Not handling even-length palindromes separately
// Expand around center handles both via the 2n-1 center trick
// If you only expand from each index, you miss even-length palindromes

// MISTAKE 4: Building KMP LPS with wrong index boundaries
for (int i = 1; i < m; i++) // CORRECT: start from i=1 (lps[0]=0 always)
for (int i = 0; i < m; i++) // WRONG: lps[0] must be 0, don't process it

// MISTAKE 5: Using contains() for rotation check without length check
// "a" + "a" = "aa" contains "aa" → but "a" is a rotation of "aa"? No! 
// Always check s.length() == goal.length() first
```

---

## 13. Interview-Level Explanation

**Q: "Why is KMP O(n+m) while naive search is O(n×m)?"**

> "Naive search re-processes text characters after each mismatch. KMP preprocesses the pattern to build the LPS (failure function) array, which tells us the next valid match position without re-checking already-matched characters. The text pointer `i` never moves backward. Combined with the LPS build time O(m), total is O(n+m)."

**Q: "How does expanding around center find all palindromes?"**

> "For a string of length n, there are 2n-1 possible centers: n centers for odd-length palindromes (each character) and n-1 centers for even-length palindromes (each gap between characters). Expanding from each center outward in O(n) per center gives O(n²) total."

---

## 14. Real-World Use Cases

| Application | String Algorithm |
|------------|----------------|
| **Text editors** | Find & replace (pattern matching) |
| **Plagiarism detection** | Rolling hash (Rabin-Karp) |
| **DNA sequencing** | KMP for gene motif finding |
| **Search engines** | Fast substring indexing |
| **Compilers** | Lexical analysis (pattern matching on tokens) |
| **Spam filters** | Keyword pattern matching |

---

## 15. Variations of This Pattern

| Variation | Algorithm | Example |
|-----------|----------|---------|
| Single pattern search | KMP | Strstr, Find All Occurrences |
| Multi-pattern search | Aho-Corasick | Find Words in Text |
| Rolling hash | Rabin-Karp | Duplicate substrings |
| Palindrome substring | Expand center | Longest Palindromic Substring |
| Palindrome count | Same (count each center) | Palindromic Substrings |
| Palindrome DP | 2D DP | Palindrome Partitioning |
| Anagram detection | Sliding window | Find All Anagrams |
| Rotation check | s+s contains | Rotate String |
| String hashing | Polynomial hash | Repeating Substring |

---

## 16. Practice Problems

### Easy — Foundation
1. **Valid Anagram** (LeetCode #242)
   - *Task:* Check if two strings are anagrams.
   - *Hint:* int[26] frequency count. Compare arrays.

2. **Reverse String** (LeetCode #344)
   - *Task:* Reverse a character array in-place.
   - *Hint:* Two pointers from both ends.

3. **First Occurrence in String** (LeetCode #28)
   - *Task:* Find needle's first occurrence in haystack.
   - *Hint:* KMP for O(n+m) or `haystack.indexOf(needle)`.

### Medium — Core Patterns
1. **Longest Palindromic Substring** (LeetCode #5)
   - *Task:* Find the longest palindromic substring.
   - *Hint:* Expand around center. Track max length.

2. **Palindromic Substrings** (LeetCode #647)
   - *Task:* Count all palindromic substrings.
   - *Hint:* Expand around each of the 2n-1 centers. Count each expansion.

3. **Find All Anagrams in a String** (LeetCode #438)
   - *Task:* Find starting indices of all anagrams of p in s.
   - *Hint:* Fixed sliding window of size p.length() + int[26] comparison.

4. **Repeated Substring Pattern** (LeetCode #459)
   - *Task:* Can string be constructed by repeating a substring?
   - *Hint:* KMP LPS trick OR check `(s+s).substring(1, 2n-1).contains(s)`.

5. **Longest Repeating Character Replacement** (LeetCode #424)
   - *Task:* Longest substring with at most k replacements to make uniform.
   - *Hint:* Sliding window + track maxFreq. Valid if size - maxFreq ≤ k.

### Hard — Advanced String
1. **Minimum Window Substring** (LeetCode #76)
   - *Task:* Smallest window in s containing all chars of t.
   - *Hint:* Variable sliding window + two frequency maps + "formed" counter.

2. **Shortest Palindrome** (LeetCode #214)
   - *Task:* Add minimum chars to front to make string palindrome.
   - *Hint:* KMP: build LPS of `s + '#' + reverse(s)`.

3. **Regular Expression Matching** (LeetCode #10)
   - *Task:* Match string against pattern with . and *.
   - *Hint:* 2D DP. Handle `.` and `*` separately.

---

## 17. How to Know You Have Mastered String Algorithms

You have mastered this topic when you can:
- [ ] Build the KMP LPS array from memory and explain each step
- [ ] Use the 2n-1 center trick to find all palindromic substrings
- [ ] Implement sliding window for anagram detection with int[26]
- [ ] Explain why KMP is O(n+m) using amortized analysis
- [ ] Check if one string is a rotation of another using `s+s`
- [ ] Implement Minimum Window Substring from memory
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. Build the LPS array for pattern `"ABCABD"`.

2. Using KMP to search for "ABA" in "ABABABA" — how many matches are found?

3. String `"racecar"` — expand around center at index 3 ('c'). What palindrome do you find?

4. For `s = "aa"`, is `(s+s).substring(1, 3)` equal to s? What does this tell you about rotation check?

5. The sliding window for anagram detection compares `int[26]` arrays. Is this O(1) per comparison?

6. KMP failure function for "AABAA": at i=4 (last 'A'), what is lps[4]?

7. "Find all palindromic substrings in 'aaa'". List them all and count.

8. Why use `int[26]` instead of `HashMap<Character,Integer>` for anagram detection?

> **Answers:**
> 1. "ABCABD": lps=[0,0,0,1,2,0]. 'A'B'C' no self-prefix. 'A'(i=3) matches start → 1. 'A'B' matches 'A'B' → 2. 'D' doesn't match 'C' → fall back to lps[0]=0; 'D'≠'A' → 0.
> 2. 3 matches: positions 0 ("ABA"), 2 ("ABA"), 4 ("ABA").
> 3. Expand from 'c' at index 3: s[2]='c'==s[4]='c'? No, "racecar": r-a-c-e-c-a-r. Index 3='e'. Expand: s[2]='c'==s[4]='c' ✓, s[1]='a'==s[5]='a' ✓, s[0]='r'==s[6]='r' ✓. Full palindrome "racecar" ✓.
> 4. `"aa"+"aa"="aaaa"`, substring(1,3)="aa"==s. Yes! But "aa" IS a rotation of itself (rotate by 0 or any amount). The length check ensures we don't falsely match "a" as rotation of "aa".
> 5. Yes — int[26] comparison is O(26) = O(1) since the alphabet size is constant.
> 6. i=4, pattern="AABAA": lps = [0,1,0,1,2]. lps[4]=2 (prefix "AA" matches suffix "AA").
> 7. "a"(0), "a"(1), "a"(2), "aa"(0-1), "aa"(1-2), "aaa"(0-2) = 6 palindromic substrings.
> 8. `int[26]` is a fixed-size array with O(1) access and O(26)=O(1) comparison. HashMap has O(1) amortized but higher constant due to hashing overhead. For lowercase letters, int[26] is more efficient.

---

**Next →** `../22_Trie/01_Trie_Algorithms.md`

**Key insight:** Use a **failure function** (LPS array) to avoid restarting from the beginning on mismatch.

### Build LPS (Longest Proper Prefix which is also Suffix)

```java
int[] buildLPS(String pattern) {
    int m = pattern.length();
    int[] lps = new int[m];
    int len = 0, i = 1;
    while (i < m) {
        if (pattern.charAt(i) == pattern.charAt(len)) {
            lps[i++] = ++len;
        } else if (len != 0) {
            len = lps[len - 1];  // fall back (don't increment i)
        } else {
            lps[i++] = 0;
        }
    }
    return lps;
}
```

### KMP Search

```java
List<Integer> kmpSearch(String text, String pattern) {
    int[] lps = buildLPS(pattern);
    List<Integer> result = new ArrayList<>();
    int i = 0, j = 0;
    while (i < text.length()) {
        if (text.charAt(i) == pattern.charAt(j)) { i++; j++; }
        if (j == pattern.length()) {
            result.add(i - j);  // found at index i-j
            j = lps[j - 1];
        } else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
            if (j != 0) j = lps[j - 1];
            else i++;
        }
    }
    return result;
}
```

**Real-world use:** Text editors (find/replace), anti-virus signature scanning, DNA sequence matching.

---

## Pattern 2: Z-Algorithm — O(n+m)

Build Z-array where Z[i] = length of longest substring starting at i that matches a prefix of the string.

```java
int[] zFunction(String s) {
    int n = s.length();
    int[] z = new int[n];
    int l = 0, r = 0;
    for (int i = 1; i < n; i++) {
        if (i < r) z[i] = Math.min(r - i, z[i - l]);
        while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) z[i]++;
        if (i + z[i] > r) { l = i; r = i + z[i]; }
    }
    return z;
}

// Find pattern in text using Z-algorithm
// Concatenate: pattern + "$" + text, then find Z[i] == pattern.length()
```

---

## Pattern 3: Rabin-Karp Rolling Hash — O(n+m) avg

```java
List<Integer> rabinKarp(String text, String pattern) {
    List<Integer> result = new ArrayList<>();
    int n = text.length(), m = pattern.length();
    int base = 31, mod = 1_000_000_007;

    long patHash = 0, textHash = 0, power = 1;
    for (int i = 0; i < m - 1; i++) power = power * base % mod;

    for (int i = 0; i < m; i++) {
        patHash = (patHash * base + pattern.charAt(i)) % mod;
        textHash = (textHash * base + text.charAt(i)) % mod;
    }

    for (int i = 0; i <= n - m; i++) {
        if (patHash == textHash)  // verify char by char to handle collision
            if (text.substring(i, i + m).equals(pattern)) result.add(i);
        if (i < n - m) {
            textHash = (textHash - text.charAt(i) * power % mod + mod) % mod;
            textHash = (textHash * base + text.charAt(i + m)) % mod;
        }
    }
    return result;
}
```

---

## Pattern 4: Longest Palindromic Substring

### Expand Around Center — O(n²)

```java
String longestPalindrome(String s) {
    int start = 0, maxLen = 0;
    for (int center = 0; center < s.length(); center++) {
        // Odd length palindromes
        int len1 = expand(s, center, center);
        // Even length palindromes
        int len2 = expand(s, center, center + 1);
        int len = Math.max(len1, len2);
        if (len > maxLen) {
            maxLen = len;
            start = center - (len - 1) / 2;
        }
    }
    return s.substring(start, start + maxLen);
}

int expand(String s, int left, int right) {
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
        left--; right++;
    }
    return right - left - 1;
}
```

---

## Pattern 5: String Hashing for Duplicate Detection

```java
// Find duplicate substring of length k
boolean hasDuplicate(String s, int k) {
    Set<String> seen = new HashSet<>();
    for (int i = 0; i + k <= s.length(); i++) {
        String sub = s.substring(i, i + k);
        if (!seen.add(sub)) return true;
    }
    return false;
}
// Optimize with rolling hash for O(n) instead of O(nk)
```

---

## Practice Problems

**Easy:**
1. Valid Palindrome.
2. First Unique Character.
3. Implement strStr() (naive pattern match).

**Medium:**
1. Longest Palindromic Substring.
2. Longest Repeating Character Replacement.
3. String Compression.
4. Group Anagrams.
5. Encode and Decode Strings.

**Hard:**
1. Shortest Palindrome (KMP-based).
2. Palindrome Pairs.
3. Find All Anagrams in a String (KMP + frequency).

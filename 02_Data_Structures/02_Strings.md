# Section 2.2 — Strings

---

## 1. What Problem Does This Solve?

Text manipulation is everywhere: parsing input, pattern matching, transforming sequences, and building output. Strings appear in almost every DSA problem. Understanding String internals in Java prevents the most common performance pitfall: O(n²) string building in a loop.

---

## 2. Beginner-Friendly Intuition

A String is an immutable array of characters. "Immutable" means once created, it cannot be changed. Every operation that looks like a modification (replace, substring, concat) actually creates a new String object in memory.

This is why string concatenation in a loop (`s += c`) is O(n²): each `+=` creates a new String, copying all previous characters into it.

The fix: use `StringBuilder`, which is a mutable character buffer — O(n) total for all appends.

---

## 3. Real-World Analogy

**String (immutable) — Printed book:** Once a book is printed, you can't add a page. To add a page, you reprint the entire book with the page included. Expensive.

**StringBuilder (mutable) — Notebook:** You write letter by letter. When done, you print (call `toString()`). One print at the end, not after every letter.

---

## 4. Core Concept

### Java String Internals
```
String s = "hello";
- Stored in String Pool (heap)
- s is a reference to char[] {'h','e','l','l','o'}
- s.length() = O(1) (stored as field)
- s.charAt(i) = O(1) (array access)
- s.substring(i, j) = O(j-i) (creates new String with copy)
- s1 + s2 = O(n+m) (creates new String, copies both)
```

### String Comparison
```java
s1 == s2       // WRONG for value comparison (reference equality)
s1.equals(s2)  // CORRECT for value comparison
s1.compareTo(s2) // Lexicographic comparison
```

### String vs StringBuilder vs char[]

| | String | StringBuilder | char[] |
|-|--------|--------------|--------|
| Mutable | No | Yes | Yes |
| Thread-safe | Yes | No | No |
| Use case | Store, compare | Build incrementally | Manipulation |
| Conversion | N/A | sb.toString() | new String(arr) |

---

## 5. Pattern Recognition Signals

```
"Check if two strings are anagrams" → frequency array or HashMap
"Longest substring without repeating chars" → Sliding window + Set
"Reverse words / reverse string" → Two pointers or Stack
"Check palindrome" → Two pointers from both ends
"Pattern matching in text" → KMP or built-in contains()
"Build string from parts" → StringBuilder
"Parse number from string" → Integer.parseInt() or manual
"All permutations of string" → Backtracking
"Longest palindromic substring" → DP or expand-from-center
```

---

## 6. Step-by-Step Algorithm

### Check Anagram
```
Sort both strings and compare → O(n log n)
OR
Count character frequencies → O(n):
  freq[26] for lowercase letters
  for each char in s1: freq[c-'a']++
  for each char in s2: freq[c-'a']--
  if any freq != 0: not anagram
```

### Check Palindrome (Two Pointers)
```
left = 0, right = s.length() - 1
while left < right:
    if s.charAt(left) != s.charAt(right): return false
    left++; right--
return true
```

---

## 7. Dry Run with Example

### Reverse Words in a String
```
input = "  the sky  is blue  "

Step 1: trim → "the sky  is blue"
Step 2: split by "\\s+" → ["the","sky","is","blue"]
Step 3: reverse array → ["blue","is","sky","the"]
Step 4: join with " " → "blue is sky the" ✓
```

### Longest Substring Without Repeating Characters
```
s = "abcabcbb"
Use Set + sliding window:
  i=0: {a}, max=1
  i=1: {a,b}, max=2
  i=2: {a,b,c}, max=3
  i=3: 'a' in set → remove chars from left until 'a' gone
       remove 'a'(j=0) → {b,c}, add 'a' → {b,c,a}, max=3
  i=4: {b,c,a,b} duplicate → remove until 'b' gone
       remove 'b'(j=1) → {c,a}, add 'b' → {c,a,b}, max=3
  i=5: {c,a,b,c} dup → remove j=2 → {a,b,c}, max=3
  i=6: {a,b,c,b} dup → remove j=3 → {c,b}, max=3
  i=7: {c,b,b} dup → remove j=5 → {b}, max=3
Result: 3 ✓
```

---

## 8. Code Implementation

```java
import java.util.*;

public class StringOperations {

    // ── Key Java String API ───────────────────────────────────────────────
    public void apiDemo() {
        String s = "Hello, World!";
        s.length();                  // 13
        s.charAt(0);                 // 'H'
        s.indexOf("World");          // 7
        s.substring(7, 12);         // "World"
        s.toLowerCase();             // "hello, world!"
        s.toUpperCase();             // "HELLO, WORLD!"
        s.trim();                    // remove leading/trailing spaces
        s.strip();                   // Unicode-aware trim (Java 11+)
        s.replace("World", "Java");  // "Hello, Java!"
        s.contains("World");         // true
        s.startsWith("Hello");       // true
        s.split(", ");               // ["Hello", "World!"]
        s.toCharArray();             // char[] — O(n)
        String.valueOf(42);          // "42"
        Integer.parseInt("42");      // 42
    }

    // ── StringBuilder — O(n) string building ─────────────────────────────
    public String buildString(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(i);           // O(1) amortized each
            if (i < n - 1) sb.append(",");
        }
        return sb.toString();       // O(n) at the end
    }

    // ── Reverse String ───────────────────────────────────────────────────
    public void reverseString(char[] s) {
        int l = 0, r = s.length - 1;
        while (l < r) {
            char tmp = s[l]; s[l] = s[r]; s[r] = tmp;
            l++; r--;
        }
    }

    // ── Check Anagram ────────────────────────────────────────────────────
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;
        for (char c : t.toCharArray()) freq[c - 'a']--;
        for (int f : freq) if (f != 0) return false;
        return true;
    }

    // ── Longest Substring Without Repeating Characters ────────────────────
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int left = 0, max = 0;
        for (int right = 0; right < s.length(); right++) {
            while (set.contains(s.charAt(right)))
                set.remove(s.charAt(left++)); // shrink window
            set.add(s.charAt(right));
            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    // ── Valid Palindrome ─────────────────────────────────────────────────
    public boolean isPalindrome(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) {
            while (l < r && !Character.isLetterOrDigit(s.charAt(l))) l++;
            while (l < r && !Character.isLetterOrDigit(s.charAt(r))) r--;
            if (Character.toLowerCase(s.charAt(l)) !=
                Character.toLowerCase(s.charAt(r))) return false;
            l++; r--;
        }
        return true;
    }

    // ── Character frequency tricks ────────────────────────────────────────
    public int charToIndex(char c) {
        return c - 'a';      // 'a'=0, 'b'=1, ..., 'z'=25
    }

    public int digitToInt(char c) {
        return c - '0';      // '0'=0, '1'=1, ..., '9'=9
    }
}
```

---

## 9. Time Complexity

| Operation | Complexity | Notes |
|-----------|-----------|-------|
| charAt(i) | O(1) | Array access |
| length() | O(1) | Stored field |
| substring(i,j) | O(j-i) | Creates new String |
| equals() | O(n) | Character by character |
| String concat (+) in loop | O(n^2) | New copy each time |
| StringBuilder.append | O(1) amortized | Amortized O(1) per char |
| StringBuilder.toString | O(n) | One-time copy |
| toCharArray() | O(n) | Copy |
| Arrays.sort(char[]) | O(n log n) | Standard sort |

---

## 10. Space Complexity

| Operation | Space |
|-----------|-------|
| String interning | O(n) in heap |
| StringBuilder | O(n) buffer |
| freq[26] array | O(1) constant |
| HashMap<Character, Integer> | O(k) for k distinct chars |

---

## 11. Edge Cases

| Scenario | Handling |
|----------|---------|
| Empty string `""` | Check `s.isEmpty()` or `s.length() == 0` |
| Single character | Most string algorithms handle naturally |
| All same characters | Sliding window: window expands fully |
| Unicode characters | Use `s.codePointAt(i)` and `codePointCount` |
| Null string | Check `s == null` before any operation |
| Case-insensitive compare | Use `s.equalsIgnoreCase(t)` |

---

## 12. Common Mistakes

```java
// MISTAKE 1: String comparison with ==
if (s1 == s2) // WRONG: compares references, not values
if (s1.equals(s2)) // CORRECT

// MISTAKE 2: String concatenation in loop (O(n^2))
String result = "";
for (char c : arr) result += c; // WRONG: O(n^2)
StringBuilder sb = new StringBuilder();
for (char c : arr) sb.append(c); // CORRECT: O(n)
String result = sb.toString();

// MISTAKE 3: s.charAt(i) - '0' for non-digit chars
// If c is not '0'-'9', this gives wrong value. Use Character.isDigit(c) first.

// MISTAKE 4: substring creates new String
String sub = s.substring(0, n); // O(n) copy — don't do this in a tight loop
// Use indices (start, end) to avoid unnecessary copies

// MISTAKE 5: Modifying String (impossible — immutable)
s.charAt(0) = 'A'; // COMPILE ERROR — strings are immutable
char[] arr = s.toCharArray(); arr[0] = 'A'; // CORRECT: convert first
```

---

## 13. Interview-Level Explanation

**Q: "Why is string concatenation in a loop O(n²)?"**

> "Each `s += character` in Java creates a new String object containing all previous characters plus the new one. After k iterations, the k-th concatenation copies k characters. Total copies = 1 + 2 + ... + n = n(n+1)/2 = O(n²). StringBuilder avoids this by maintaining a mutable char buffer that doubles when full — like ArrayList. Each append is O(1) amortized, and you call `toString()` once at the end for O(n)."

**Q: "What's the difference between String.equals() and String.compareTo()?"**

> "`equals()` returns a boolean — true if both strings have identical characters. `compareTo()` returns an int: negative if this < other, 0 if equal, positive if this > other. It's used for sorting (alphabetical order). For null-safe comparison, use `Objects.equals(s1, s2)`. For ordering, use `Comparator.naturalOrder()` or `s1.compareTo(s2)`."

---

## 14. Real-World Use Cases

| Application | String Operation |
|------------|-----------------|
| **Search engines** | KMP / Rabin-Karp pattern matching |
| **DNA sequencing** | Substring search, longest common subsequence |
| **Autocomplete** | Trie (prefix tree) on string keys |
| **Compression** | Run-length encoding, Huffman coding |
| **Compilers** | Tokenization and lexical analysis |
| **URL routing** | Prefix matching, regex |

---

## 15. Variations

| Variation | Technique |
|-----------|----------|
| Anagram detection | Frequency array or sorted string comparison |
| Palindrome check | Two pointers |
| Longest palindromic substring | Expand from center or DP |
| Pattern matching | KMP O(n+m), built-in contains O(n×m) |
| String rotation | `(s+s).contains(t)` |
| Count vowels/consonants | Iterate with Character checks |
| Decode ways | DP on string |

---

## 16. Practice Problems

### Easy — Foundation
1. **Valid Anagram** (LeetCode #242)
   - *Task:* Check if t is an anagram of s.
   - *Hint:* Frequency array of 26 characters.

2. **Reverse String** (LeetCode #344)
   - *Task:* Reverse char array in-place.
   - *Hint:* Two pointers from both ends.

3. **Valid Palindrome** (LeetCode #125)
   - *Task:* Check if string is palindrome ignoring non-alphanumeric.
   - *Hint:* Two pointers, skip non-alphanumeric.

### Medium — Core
1. **Longest Substring Without Repeating Characters** (LeetCode #3)
   - *Task:* Length of longest substring without duplicates.
   - *Hint:* Sliding window + HashSet.

2. **Longest Palindromic Substring** (LeetCode #5)
   - *Task:* Find longest palindromic substring.
   - *Hint:* Expand from center for each position, O(n^2).

3. **Group Anagrams** (LeetCode #49)
   - *Task:* Group strings that are anagrams.
   - *Hint:* Sort each string as key; use HashMap.

4. **String to Integer (atoi)** (LeetCode #8)
   - *Task:* Implement atoi with edge cases.
   - *Hint:* Trim, check sign, parse digits, handle overflow.

5. **Minimum Window Substring** (LeetCode #76)
   - *Task:* Shortest window in s containing all chars of t.
   - *Hint:* Sliding window with two frequency maps.

### Hard — Advanced
1. **Regular Expression Matching** (LeetCode #10)
   - *Task:* Implement regex with '.' and '*'.
   - *Hint:* 2D DP: dp[i][j] = s[0..i-1] matches p[0..j-1].

2. **Wildcard Matching** (LeetCode #44)
   - *Task:* '?' matches single char, '*' matches any sequence.
   - *Hint:* DP or greedy with backtracking.

3. **Edit Distance** (LeetCode #72)
   - *Task:* Minimum operations (insert, delete, replace) to convert.
   - *Hint:* 2D DP: dp[i][j] = edit distance between first i and j chars.

---

## 17. How to Know You Have Mastered Strings

You have mastered this topic when you can:
- [ ] Explain why `s += c` in a loop is O(n²) and why StringBuilder fixes it
- [ ] Use `char - 'a'` and `char - '0'` for frequency/digit conversions
- [ ] Apply two-pointer palindrome check
- [ ] Implement anagram detection with frequency array
- [ ] Use sliding window for substring problems
- [ ] Never use `==` for String value comparison
- [ ] Build strings with StringBuilder instead of concatenation
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. What is the time complexity of building a String with `s += c` for n characters in a loop?

2. "abcba" — is it a palindrome? Trace the two-pointer check.

3. `"hello".substring(1, 3)` returns what string? What is its time complexity?

4. How do you check if character `c` is a lowercase vowel in Java?

5. `s1.equals(s2)` vs `s1 == s2` — when does `==` return true for Strings?

6. What does `"abc" + "def"` create in memory?

7. To sort characters of a String and compare two strings for being anagrams, what is the time complexity?

8. `charAt(i) - '0'`: what does this compute, and when is it valid?

> **Answers:**
> 1. O(n²) — each `+=` creates a new String copying all previous characters. After n appends: 1+2+...+n = n(n+1)/2 copies total.
> 2. l=0('a'), r=4('a') match → l=1('b'), r=3('b') match → l=2('c'), r=2 → l > r → return true. Yes, palindrome.
> 3. "el" (indices 1 inclusive to 3 exclusive). Time complexity O(2) = O(length of result) — creates a new String copying 2 characters.
> 4. `"aeiou".indexOf(c) >= 0` or explicitly: `c=='a'||c=='e'||c=='i'||c=='o'||c=='u'`. Or `"aeiou".contains(String.valueOf(c))`.
> 5. `==` returns true only when both references point to the same object. Due to String interning, string literals ("abc" == "abc") may return true, but it's unreliable. Always use `equals()` for value comparison.
> 6. A new String object "abcdef" is created in heap memory containing 6 characters. Both "abc" and "def" may still exist in the String pool.
> 7. O(n log n) — sorting n characters takes O(n log n). Comparing two sorted strings takes O(n). Total: O(n log n).
> 8. Converts character digit ('0'-'9') to its integer value (0-9). It's valid only when `Character.isDigit(c)` is true. For c='5': '5'-'0' = 53-48 = 5.

---

**Next →** `03_Linked_List.md`

---

## 2. Core Concepts

A String is an **immutable sequence of characters** in Java.

```java
String s = "hello";
s.charAt(0);          // 'h'  — O(1)
s.length();           // 5   — O(1)
s.substring(1, 3);    // "el" — O(n) — creates new String!
s.equals("hello");    // true — O(n) comparison
```

**CRITICAL:** In Java, String concatenation in a loop is O(n²)!
```java
// BAD — O(n²)
String result = "";
for (int i = 0; i < n; i++) result += arr[i];

// GOOD — O(n)
StringBuilder sb = new StringBuilder();
for (int i = 0; i < n; i++) sb.append(arr[i]);
String result = sb.toString();
```

---

## 3. Operations & Complexity

| Operation | Time | Notes |
|-----------|------|-------|
| charAt(i) | O(1) | Direct access |
| length() | O(1) | Stored internally |
| substring(i,j) | O(j-i) | Creates new string |
| equals() | O(n) | Character by character |
| contains() | O(n×m) | Substring search |
| indexOf() | O(n×m) | Naive search |
| split() | O(n) | Regex-based |
| toCharArray() | O(n) | Copy to array |

---

## 4. Character Tricks in Java

```java
char c = 'a';
c - 'a';           // 0  — offset in alphabet
c - '0';           // digit to int if c is '0'-'9'
Character.isLetter(c);
Character.isDigit(c);
Character.toLowerCase(c);
Character.toUpperCase(c);
(int) c;           // ASCII value
```

**Frequency array (26 letters):**
```java
int[] freq = new int[26];
for (char c : s.toCharArray())
    freq[c - 'a']++;
```

---

## 5. Common String Patterns

### Pattern 1: Two Pointers for Palindrome
```java
boolean isPalindrome(String s) {
    int l = 0, r = s.length() - 1;
    while (l < r) {
        if (s.charAt(l) != s.charAt(r)) return false;
        l++; r--;
    }
    return true;
}
```

### Pattern 2: Sliding Window for Substring
```java
// Longest substring without repeating characters
int maxLen = 0, left = 0;
Set<Character> window = new HashSet<>();
for (int right = 0; right < s.length(); right++) {
    while (window.contains(s.charAt(right)))
        window.remove(s.charAt(left++));
    window.add(s.charAt(right));
    maxLen = Math.max(maxLen, right - left + 1);
}
```

### Pattern 3: Anagram Check
```java
boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;
    int[] count = new int[26];
    for (char c : s.toCharArray()) count[c - 'a']++;
    for (char c : t.toCharArray()) count[c - 'a']--;
    for (int x : count) if (x != 0) return false;
    return true;
}
```

---

## 6. String Search Algorithms

| Algorithm | Time | Use |
|-----------|------|-----|
| Naive | O(n×m) | Small strings |
| KMP | O(n+m) | Pattern matching |
| Rabin-Karp | O(n+m) avg | Multiple patterns |
| Z-Algorithm | O(n+m) | Pattern in text |

(Deep coverage in `21_Strings/`)

---

## 7. Edge Cases

- Empty string `""`
- Single character `"a"`
- All same characters `"aaaa"`
- Case sensitivity — clarify with interviewer
- Unicode characters (not just ASCII)
- Spaces and special characters

---

## 8. Common Mistakes

- Using `==` instead of `.equals()` for string comparison
- Forgetting `String` is immutable (operations create new strings)
- Using `+` concatenation in loops (use `StringBuilder`)
- Off-by-one in `substring(start, end)` — end is exclusive

---

## 9. Practice Problems

**Easy:**
1. Reverse a string.
2. Check if a string is a palindrome.
3. Count vowels in a string.

**Medium:**
1. Longest substring without repeating characters.
2. Check if two strings are anagrams.
3. Find all permutations of a string.
4. Longest common prefix.
5. Group anagrams from a list of strings.

**Hard:**
1. Minimum window substring.
2. Longest palindromic substring (Expand Around Center).
3. String to Integer (atoi) with full edge cases.

---

**Next →** `03_Linked_List.md`

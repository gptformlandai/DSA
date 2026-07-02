# Section 21b — Advanced String Algorithms

> Companion to `01_String_Algorithms.md`. That file covered KMP, expand-around-center palindromes, and anagram windows. This file closes the PRO-level gaps: the Z-algorithm, Manacher's linear palindromes, Rabin-Karp rolling hash (fully implemented), Aho-Corasick multi-pattern matching, and a suffix-array primer.

---

## 1. What Problem Does This Solve?

String problems escalate fast in interviews and competitive settings:

- **"Find all occurrences of a pattern"** in O(n+m) without KMP's failure-function subtlety → **Z-algorithm**.
- **"Longest palindromic substring"** in O(n) instead of O(n²) → **Manacher's**.
- **"Match/compare substrings in O(1)"** or **"detect duplicate substrings"** → **rolling hash (Rabin-Karp)**.
- **"Search for many patterns at once"** (spam filters, dictionaries) → **Aho-Corasick**.
- **"Rank/sort all suffixes, longest repeated substring, LCP"** → **suffix array**.

---

## 2. Z-Algorithm — Linear Pattern Matching

### Intuition
`z[i]` = length of the longest substring starting at `i` that is also a **prefix** of the string. Maintain a `[l, r]` window that is the rightmost prefix-match seen so far; inside it, reuse previously computed `z` values instead of recomparing.

### Code
```java
public int[] zFunction(String s) {
    int n = s.length();
    int[] z = new int[n];
    z[0] = n;                              // whole string matches itself
    int l = 0, r = 0;                      // current rightmost match window
    for (int i = 1; i < n; i++) {
        if (i < r) z[i] = Math.min(r - i, z[i - l]);   // reuse inside window
        while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) z[i]++; // extend
        if (i + z[i] > r) { l = i; r = i + z[i]; }      // push window right
    }
    return z;
}
```

### Pattern search with Z
Concatenate `pattern + '#' + text` (with a separator not in either). Any index `i` in the text region with `z[i] == pattern.length()` is a match.
```java
public List<Integer> search(String text, String pattern) {
    String combined = pattern + "\u0001" + text;
    int[] z = zFunction(combined);
    int m = pattern.length();
    List<Integer> res = new ArrayList<>();
    for (int i = m + 1; i < combined.length(); i++)
        if (z[i] == m) res.add(i - m - 1);   // position in text
    return res;
}
```
Time **O(n+m)**, space O(n+m). Z is often easier to reason about than KMP's LPS.

---

## 3. Manacher's Algorithm — All Palindromes in O(n)

### Intuition
Expand-around-center is O(n²) because centers redo work. Manacher reuses symmetry: if we're inside a known palindrome, the radius at the **mirror** position is a free lower bound. Transform the string with separators (`^#a#b#a#$`) so odd and even palindromes are handled uniformly.

### Code
```java
public String longestPalindrome(String s) {
    if (s.isEmpty()) return "";
    // Transform: insert '#' so every palindrome has odd length.
    StringBuilder t = new StringBuilder("^");
    for (char c : s.toCharArray()) t.append('#').append(c);
    t.append("#$");
    String str = t.toString();
    int n = str.length();
    int[] p = new int[n];                  // p[i] = palindrome radius at i
    int center = 0, right = 0;
    for (int i = 1; i < n - 1; i++) {
        int mirror = 2 * center - i;
        if (i < right) p[i] = Math.min(right - i, p[mirror]);   // reuse symmetry
        while (str.charAt(i + p[i] + 1) == str.charAt(i - p[i] - 1)) p[i]++;
        if (i + p[i] > right) { center = i; right = i + p[i]; }  // extend window
    }
    // Find max radius and map back to original indices.
    int maxLen = 0, centerIndex = 0;
    for (int i = 1; i < n - 1; i++)
        if (p[i] > maxLen) { maxLen = p[i]; centerIndex = i; }
    int start = (centerIndex - maxLen) / 2;   // back to original string
    return s.substring(start, start + maxLen);
}
```
Time **O(n)**, space O(n). The `^` and `$` sentinels remove bounds checks.

---

## 4. Rabin-Karp — Rolling Hash (Fully Implemented)

### Intuition
Treat a string as a base-`B` number modulo a large prime. A **rolling hash** updates in O(1) when the window slides: drop the leading char, shift, add the new char. Compare hashes first; verify on collision. Great for **substring equality in O(1)** and **duplicate detection**.

### Single-pattern search
```java
public List<Integer> rabinKarp(String text, String pat) {
    int n = text.length(), m = pat.length();
    List<Integer> res = new ArrayList<>();
    if (m > n) return res;
    long MOD = 1_000_000_007L, B = 131;
    long patHash = 0, winHash = 0, power = 1;
    for (int i = 0; i < m; i++) {
        patHash = (patHash * B + pat.charAt(i)) % MOD;
        winHash = (winHash * B + text.charAt(i)) % MOD;
        if (i < m - 1) power = power * B % MOD;    // B^(m-1)
    }
    for (int i = 0; i + m <= n; i++) {
        if (patHash == winHash && text.regionMatches(i, pat, 0, m))
            res.add(i);                            // verify to dodge collisions
        if (i + m < n) {                           // roll the window
            winHash = (winHash - text.charAt(i) * power % MOD + MOD) % MOD;
            winHash = (winHash * B + text.charAt(i + m)) % MOD;
        }
    }
    return res;
}
```

### Precomputed prefix hash — compare any two substrings in O(1)
```java
class StringHash {
    long[] h, pow;
    long MOD = 1_000_000_007L, B = 131;
    StringHash(String s) {
        int n = s.length();
        h = new long[n + 1];
        pow = new long[n + 1];
        pow[0] = 1;
        for (int i = 0; i < n; i++) {
            h[i + 1] = (h[i] * B + s.charAt(i)) % MOD;
            pow[i + 1] = pow[i] * B % MOD;
        }
    }
    long sub(int l, int r) {                        // hash of s[l..r] inclusive
        return (h[r + 1] - h[l] * pow[r - l + 1] % MOD + MOD * MOD) % MOD;
    }
}
```

- Use a **large prime modulus** and ideally **double hashing** (two moduli) to make collisions astronomically unlikely.
- Always **verify on hash match** in adversarial settings — anti-hash test cases exist.
- **Canonical problems:** LeetCode 1044 *Longest Duplicate Substring* (binary search + rolling hash), 187 *Repeated DNA Sequences*, 28 *Find the Index of the First Occurrence*.

---

## 5. Aho-Corasick — Match Many Patterns at Once

### Intuition
Build a trie of all patterns, then add KMP-style **failure links**: when a character mismatches, jump to the longest proper suffix that is still a prefix of some pattern. One pass over the text finds all occurrences of all patterns in **O(text + total_pattern_length + matches)**.

### Code (skeleton)
```java
class AhoCorasick {
    int[][] go = new int[MAX][26];   // trie transitions
    int[] fail = new int[MAX];       // failure links
    List<Integer>[] out = new List[MAX]; // pattern ids ending at this node
    int size = 1;                    // node 0 = root

    void add(String s, int id) {
        int cur = 0;
        for (char c : s.toCharArray()) {
            int k = c - 'a';
            if (go[cur][k] == 0) go[cur][k] = size++;
            cur = go[cur][k];
        }
        if (out[cur] == null) out[cur] = new ArrayList<>();
        out[cur].add(id);
    }

    void build() {                   // BFS to set fail links + finalize go[]
        Deque<Integer> q = new ArrayDeque<>();
        for (int c = 0; c < 26; c++)
            if (go[0][c] != 0) { fail[go[0][c]] = 0; q.add(go[0][c]); }
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int c = 0; c < 26; c++) {
                int v = go[u][c];
                if (v == 0) { go[u][c] = go[fail[u]][c]; }   // path-compress transition
                else {
                    fail[v] = go[fail[u]][c];
                    if (out[fail[v]] != null) {              // merge outputs along fail chain
                        if (out[v] == null) out[v] = new ArrayList<>();
                        out[v].addAll(out[fail[v]]);
                    }
                    q.add(v);
                }
            }
        }
    }

    void match(String text) {
        int cur = 0;
        for (int i = 0; i < text.length(); i++) {
            cur = go[cur][text.charAt(i) - 'a'];
            if (out[cur] != null)
                for (int id : out[cur]) { /* pattern `id` ends at index i */ }
        }
    }
}
```
- **Applications:** intrusion detection, spam/keyword filters, DNA motif search, LeetCode 1032 *Stream of Characters*.
- Think of it as **KMP generalized to a set of patterns via a trie**.

---

## 6. Suffix Array — Primer

A **suffix array** is the sorted order of all suffixes of a string (stored as starting indices). With the companion **LCP array** (longest common prefix of adjacent suffixes) it powers: longest repeated substring, number of distinct substrings, and fast pattern search via binary search.

```java
// O(n log^2 n) suffix array via prefix-doubling (concise, interview-friendly).
public int[] suffixArray(String s) {
    int n = s.length();
    Integer[] sa = new Integer[n];
    int[] rank = new int[n], tmp = new int[n];
    for (int i = 0; i < n; i++) { sa[i] = i; rank[i] = s.charAt(i); }
    for (int k = 1; k < n; k <<= 1) {
        final int kk = k;
        final int[] r = rank;
        Comparator<Integer> cmp = (a, b) -> {
            if (r[a] != r[b]) return Integer.compare(r[a], r[b]);
            int ra = a + kk < n ? r[a + kk] : -1;
            int rb = b + kk < n ? r[b + kk] : -1;
            return Integer.compare(ra, rb);
        };
        Arrays.sort(sa, cmp);
        tmp[sa[0]] = 0;
        for (int i = 1; i < n; i++)
            tmp[sa[i]] = tmp[sa[i - 1]] + (cmp.compare(sa[i - 1], sa[i]) < 0 ? 1 : 0);
        for (int i = 0; i < n; i++) rank[i] = tmp[i];
    }
    int[] res = new int[n];
    for (int i = 0; i < n; i++) res[i] = sa[i];
    return res;
}
```
- Faster O(n log n) (radix-sort ranks) and O(n) (SA-IS) constructions exist; the doubling version above is what most interviews expect you to know.
- **Kasai's algorithm** builds the LCP array in O(n) given the suffix array.
- **Applications:** LeetCode 1044 *Longest Duplicate Substring*, distinct-substring counting, bioinformatics.

---

## 7. Algorithm Selection Cheat Sheet

| Task | Best tool | Time |
|------|-----------|------|
| Single pattern search | KMP / Z / Rabin-Karp | O(n+m) |
| Longest palindromic substring | Manacher's | O(n) |
| Compare arbitrary substrings repeatedly | Prefix rolling hash | O(1) per query |
| Longest duplicate substring | Binary search + rolling hash / suffix array | O(n log n) |
| Many patterns simultaneously | Aho-Corasick | O(n + Σm + matches) |
| Rank suffixes / distinct substrings / LCP | Suffix array + Kasai | O(n log n) |

---

## 8. Failure Modes & Interview Traps

| Trap | Fix |
|------|-----|
| Rolling hash without verification | Collisions exist; verify or use double hashing. |
| Rolling hash overflow | Use `long` and reduce mod every step; add `+MOD` before `%` after subtraction. |
| Manacher without sentinels | Add `^ ... $` to avoid bounds checks; map indices back with `(center-radius)/2`. |
| Z used without a unique separator | Separator must not appear in pattern or text. |
| Aho-Corasick missing suffix-output merge | You'll miss patterns that are suffixes of others. |
| Choosing suffix array when a rolling hash suffices | Simpler hash solution often passes; reach for SA only when needed. |

---

## 9. 60-Second Explanation Template

> "This is a [single-pattern / multi-pattern / palindrome / substring-comparison] problem. The linear tool is [Z / Manacher / rolling hash / Aho-Corasick]. Its invariant is [rightmost match window / palindrome symmetry / polynomial hash / trie + failure links]. Complexity is [state it]. I'll guard against [hash collisions / bounds / separator collisions]."

---

## Practice Problems

**Medium:**
1. Longest Palindromic Substring (Manacher's).
2. Repeated DNA Sequences (rolling hash).
3. Find the Index of First Occurrence (Z / KMP / Rabin-Karp).
4. Shortest Palindrome (Z / KMP on `s + '#' + reverse(s)`).

**Hard:**
1. Longest Duplicate Substring (binary search + rolling hash / suffix array).
2. Stream of Characters (Aho-Corasick).
3. Distinct Echo Substrings (rolling hash).
4. Count distinct substrings (suffix array + LCP).

---

**Next →** `../22_Trie/01_Trie_Algorithms.md`

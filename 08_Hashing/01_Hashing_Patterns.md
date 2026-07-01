# Section 8 — Hashing Patterns

---

## 1. What Problem Does This Solve?

Many array and string problems require you to answer: "Have I seen this before?" or "How many times did X appear?" Without hashing, answering these questions requires scanning the entire data structure — O(n) per query.

**Hashing solves this by providing O(1) average-case lookup, insert, and delete** — no matter how large the input.

The pattern unlocks solutions to:
- Pair/triplet finding
- Frequency counting
- Grouping by property
- Subarray sum problems
- Duplicate detection
- Caching and memoization

---

## 2. Beginner-Friendly Intuition

Imagine a school with 1,000 students. Finding if "Alice" is enrolled:
- **Without hashing:** Check every student one by one → O(n)
- **With hashing:** Use a student ID system → Alice has ID 247 → go directly to slot 247 → O(1)

A hash function is like that ID system. It converts any key (name, number, string) into a slot number in an array — so you can go directly there.

---

## 3. Real-World Analogy

**Library catalog system:** Each book has a unique call number (like "QA76.73.J38"). When you want a book, you don't walk every aisle. You look up the call number → it tells you exactly which shelf → you go there directly.

The call number is computed from the book's properties (subject, title). That's exactly how a hash function works.

**Phone contacts:** You search "Mom" and the phone finds her in milliseconds — not by scanning 500 contacts alphabetically, but by using an internal hash index.

---

## 4. Core Concept

### The Hash Function

```
hash("apple") → 92731 → 92731 % 16 = 11 → stored at slot 11
hash("mango") → 18432 → 18432 % 16 =  0 → stored at slot 0
```

### Collision

Two keys can hash to the same slot. Java handles this with **chaining** (linked list at each slot, or tree after 8 elements in Java 8+).

### HashMap vs HashSet

| | HashMap | HashSet |
|--|---------|---------|
| Stores | Key → Value pairs | Keys only |
| Use when | You need to map to a value | You need presence check |
| Example | word → count | "have I seen this?" |

### Java's HashMap Key Guarantee

For a custom object to be a valid HashMap key, you must implement:
- `hashCode()` — determines bucket
- `equals()` — resolves collisions

Strings, integers, and arrays of characters work as keys by default.

---

## 5. Pattern Recognition Signals

Your brain should fire "use hashing" when you read:
```
"find if duplicate exists..."
"count frequency of..."
"find pair summing to target..."
"group elements by..."
"first unique/missing element..."
"check if anagram..."
"longest subarray where..."
"how many subarrays sum to K..."
"two strings are isomorphic..."
```

**Key clue:** If the brute force has a nested loop, and one loop is "searching for something" — that search can often be replaced by a HashMap lookup.

---

## 6. Step-by-Step Algorithm

### The Universal Hashing Template

```
Step 1: Identify WHAT you need to look up quickly
        (complement, frequency, canonical form, prefix sum, etc.)

Step 2: Choose the right structure:
        - HashMap<K,V> → key maps to a value
        - HashSet<K>   → only need presence check

Step 3: Scan input ONCE (left to right)
        At each element:
        a. CHECK if something useful exists in the map
        b. UPDATE the map with current element

Step 4: Return answer (accumulated during scan, or read from map)
```

### Two Sum — Applying the Template

```
What to look up: complement = target - nums[i]
Structure: HashMap (number → index)
Scan: For each num at index i:
  a. CHECK: Is (target - num) in map? → found the pair
  b. UPDATE: Add num → i to map
```

---

## 7. Dry Run with Example

### Example 1: Two Sum

**Input:** `nums = [2, 7, 11, 15]`, `target = 9`

```
map = {}

Step i=0, num=2:
  complement = 9 - 2 = 7
  Is 7 in map? → NO
  Add to map: {2: 0}

Step i=1, num=7:
  complement = 9 - 7 = 2
  Is 2 in map? → YES! Stored at index 0
  Return [0, 1] ✓

Total: 2 iterations (early exit)
```

### Example 2: Subarray Sum = K

**Input:** `nums = [1, 1, 1]`, `k = 2`

```
Prefix sum idea: if prefixSum[j] - prefixSum[i] = k,
then subarray [i+1..j] sums to k.
Rearranged: we need prefixSum[i] = prefixSum[j] - k

map = {0: 1}   ← base case: empty prefix exists once

i=0, num=1: sum=1
  Look for (1 - 2) = -1 → not in map, count stays 0
  map = {0:1, 1:1}

i=1, num=1: sum=2
  Look for (2 - 2) = 0 → in map with count 1! count = 1
  map = {0:1, 1:1, 2:1}

i=2, num=1: sum=3
  Look for (3 - 2) = 1 → in map with count 1! count = 2
  map = {0:1, 1:1, 2:1, 3:1}

Answer: 2 ✓  (subarrays [1,1] at indices 0-1 and 1-2)
```

### Example 3: Group Anagrams

**Input:** `["eat", "tea", "tan", "ate", "nat", "bat"]`

```
"eat" → sort → "aet" → groups: {"aet": ["eat"]}
"tea" → sort → "aet" → groups: {"aet": ["eat","tea"]}
"tan" → sort → "ant" → groups: {"aet":[...], "ant": ["tan"]}
"ate" → sort → "aet" → groups: {"aet": ["eat","tea","ate"]}
"nat" → sort → "ant" → groups: {..., "ant": ["tan","nat"]}
"bat" → sort → "abt" → groups: {..., "abt": ["bat"]}

Result: [["eat","tea","ate"], ["tan","nat"], ["bat"]]
```

---

## 8. Code Implementation

### Pattern 1: Frequency Counting

```java
// For lowercase letters — O(1) space
int[] freq = new int[26];
for (char c : s.toCharArray())
    freq[c - 'a']++;

// For any values — O(k) space where k = distinct values
Map<Integer, Integer> freq = new HashMap<>();
for (int num : arr)
    freq.put(num, freq.getOrDefault(num, 0) + 1);
// or cleaner:
freq.merge(num, 1, Integer::sum);
```

### Pattern 2: Two Sum — Complement Lookup

```java
int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> seen = new HashMap<>(); // number → index
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (seen.containsKey(complement))
            return new int[]{seen.get(complement), i};
        seen.put(nums[i], i);  // store AFTER checking (avoid self-pairing)
    }
    return new int[]{};
}
```

### Pattern 3: Group by Canonical Form

```java
List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> groups = new HashMap<>();
    for (String word : strs) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars); // canonical form
        groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
    }
    return new ArrayList<>(groups.values());
}
```

### Pattern 4: Prefix Sum + HashMap

```java
int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0, 1); // CRITICAL: empty prefix with sum 0 exists once
    int sum = 0, count = 0;
    for (int num : nums) {
        sum += num;
        // If (sum - k) appeared before, subarrays ending here sum to k
        count += prefixCount.getOrDefault(sum - k, 0);
        prefixCount.merge(sum, 1, Integer::sum);
    }
    return count;
}
```

### Pattern 5: Longest Consecutive Sequence

```java
int longestConsecutive(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int num : nums) set.add(num);

    int maxLen = 0;
    for (int num : set) {
        // Only start counting from the beginning of a sequence
        if (!set.contains(num - 1)) {
            int curr = num, len = 1;
            while (set.contains(curr + 1)) { curr++; len++; }
            maxLen = Math.max(maxLen, len);
        }
    }
    return maxLen;
}
// Why skip if (num-1) exists? To avoid O(n²) — each sequence processed once
```

---

## 9. Time Complexity

| Operation | Average Case | Worst Case | Notes |
|-----------|-------------|-----------|-------|
| HashMap.put(k,v) | O(1) | O(n) | Worst = all keys collide |
| HashMap.get(k) | O(1) | O(n) | Rare with good hash |
| HashMap.containsKey(k) | O(1) | O(n) | |
| Build HashMap from n elements | O(n) | O(n²) | |
| HashMap iteration | O(n) | O(n) | Visit all entries |
| int[26] frequency | O(1) | O(1) | Fixed size = constant |

> **Interview note:** Always say O(1) average for HashMap operations. If asked about worst case, explain collisions and that Java's implementation (open addressing + tree fallback) keeps it practical O(1).

---

## 10. Space Complexity

| Data Structure | Space | When |
|---------------|-------|------|
| HashMap with n entries | O(n) | n distinct keys |
| HashSet with n elements | O(n) | |
| `int[26]` frequency | O(1) | Only lowercase letters |
| `int[128]` frequency | O(1) | ASCII characters |
| Prefix sum map | O(n) | n distinct prefix sums |

> **Space optimization:** When the key space is small and bounded (e.g., lowercase letters, digits), prefer an array over a HashMap. `int[26]` uses constant space and has better cache performance.

---

## 11. Edge Cases

| Scenario | What to Check |
|----------|--------------|
| Empty array/string | Guard with `if (arr.length == 0) return ...` |
| Single element | Can it form a valid pair/group? |
| Negative numbers | HashMap handles them fine |
| Target = 2 × element | e.g., nums=[3,3], target=6 → need TWO 3s, not the same one |
| All same elements | Frequency map should count correctly |
| Integer overflow | `sum + num` can overflow int; use `long` |
| `null` keys | HashMap allows 1 null key; TreeMap does NOT |
| Duplicate values needing index | Return first occurrence vs last occurrence |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Checking AFTER putting (self-pairing bug)
map.put(nums[i], i);                       // BAD — stores self first
if (map.containsKey(target - nums[i])) ... // may find itself!

// FIX: Check first, put after
if (map.containsKey(target - nums[i])) return ...;
map.put(nums[i], i);

// MISTAKE 2: == comparison for Integer objects
Integer a = 200, b = 200;
if (a == b) ... // FALSE! Integer cache only goes to 127
if (a.equals(b)) ... // TRUE ✓

// MISTAKE 3: Forgetting base case in prefix sum
// prefixCount MUST start as {0: 1} to count subarrays from index 0

// MISTAKE 4: Using mutable object as key
int[] key = {1, 2, 3};
map.put(key, value); // WRONG — hashCode based on identity, not content
String key = Arrays.toString(arr); // Use string representation instead

// MISTAKE 5: Not handling the "exactly one solution" assumption
// Always verify whether problem guarantees unique solution
// If not, handle no-solution case (return empty, -1, etc.)
```

---

## 13. Interview-Level Explanation

**Q: "Explain your Two Sum solution."**

> "The brute force checks every pair in O(n²). The bottleneck is: for each element, I'm doing an O(n) search for its complement in the rest of the array.
>
> I can eliminate that inner search by using a HashMap to store each number's index as I scan. For each element, I compute `complement = target - num` and check if the complement is already in the map — O(1). If found, I have my answer. If not, I store the current number for future lookups.
>
> This is O(n) time, O(n) space. The space-time tradeoff: I use O(n) extra memory to go from O(n²) to O(n) — worthwhile for large inputs."

**Q: "Why put AFTER checking in Two Sum?"**

> "If I put first and then check, when target = 2×nums[i], I'd find the element itself as its own complement — returning the same index twice, which violates the constraint that indices must differ."

---

## 14. Real-World Use Cases

| Application | Hashing Usage |
|------------|--------------|
| **Database indexing** | Hash index: O(1) row lookup by primary key |
| **Caching (Redis)** | key → cached value; evict least-used |
| **DNS resolution** | domain → IP address in routers |
| **Compilers** | Symbol table: variable name → memory address |
| **Git** | SHA hash → content-addressable storage |
| **Password storage** | Hash + salt (never store raw passwords!) |
| **Load balancing** | Consistent hashing: request → server |
| **Deduplication** | File dedup: hash(content) → detect duplicates |
| **Bloom filters** | Probabilistic set membership (space-efficient hashing) |

---

## 15. Variations of This Pattern

| Variation | Description | Key Trick |
|-----------|-------------|-----------|
| **Two Sum (sorted)** | Input already sorted | Two pointers instead of HashMap |
| **Two Sum (multiple pairs)** | Return all pairs | HashMap with list of indices |
| **Prefix XOR = K** | Subarray XOR equals K | Same as prefix sum, but `^` operator |
| **Isomorphic strings** | Characters map consistently | Two maps (forward + backward) |
| **Anagram with frequency diff** | Check if anagram | int[26] + compare arrays |
| **Sliding window + Map** | Window character frequency | Map updated as window shifts |
| **HashMap + Heap** | Top K frequent | freq map → min-heap of size K |
| **HashMap + DLL** | LRU/LFU Cache | O(1) both get and put |
| **Rolling hash** | Rabin-Karp string matching | Hash recomputed in O(1) per step |

---

## 16. Practice Problems

### Easy — Build Core Intuition First
1. **Contains Duplicate** (LeetCode #217)
   - *Task:* Return true if any value appears at least twice.
   - *Hint:* HashSet — add and check in one pass.

2. **Two Sum** (LeetCode #1)
   - *Task:* Return indices of two numbers summing to target.
   - *Hint:* HashMap: num → index. Check complement before inserting.

3. **First Unique Character in a String** (LeetCode #387)
   - *Task:* Find index of first non-repeating character.
   - *Hint:* Build freq array, then scan string again for first freq[c]==1.

### Medium — Apply Patterns in Non-Obvious Ways
1. **Group Anagrams** (LeetCode #49)
   - *Task:* Group strings that are anagrams of each other.
   - *Hint:* Sorted string as canonical key → HashMap<String, List<String>>.

2. **Top K Frequent Elements** (LeetCode #347)
   - *Task:* Return k most frequent elements.
   - *Hint:* Frequency map → Min-heap of size k. Or bucket sort.

3. **Subarray Sum Equals K** (LeetCode #560)
   - *Task:* Count subarrays summing to k (can have negatives).
   - *Hint:* Prefix sum + HashMap. Watch the base case `{0:1}`.

4. **Longest Consecutive Sequence** (LeetCode #128)
   - *Task:* Find longest sequence of consecutive integers in O(n).
   - *Hint:* HashSet. Only count sequences from their starting element.

5. **4Sum II** (LeetCode #454)
   - *Task:* Count tuples (i,j,k,l) from 4 arrays where sum = 0.
   - *Hint:* HashMap of (A[i]+B[j]) counts → check -(C[k]+D[l]).

### Hard — Combine Hashing with Other Patterns
1. **Minimum Window Substring** (LeetCode #76)
   - *Task:* Smallest window in s containing all chars of t.
   - *Hint:* Need map + sliding window + "formed" count tracking.

2. **Substring with Concatenation of All Words** (LeetCode #30)
   - *Task:* Find starting indices where all words appear concatenated.
   - *Hint:* Sliding window of size (wordLen × count) with frequency map check.

3. **LFU Cache** (LeetCode #460)
   - *Task:* Implement Least Frequently Used cache with O(1) get/put.
   - *Hint:* Three HashMaps: key→val, key→freq, freq→LinkedHashSet of keys.

---

## 17. How to Know You Have Mastered Hashing

You have mastered this topic when you can:
- [ ] Implement Two Sum from memory in under 3 minutes
- [ ] Immediately recognize "I need O(1) lookup" → HashMap/HashSet
- [ ] Know when to use `int[26]` vs HashMap (bounded vs unbounded keys)
- [ ] Explain the prefix sum + HashMap pattern (subarraySum = K) verbally
- [ ] Write Group Anagrams without looking it up
- [ ] Explain HashMap vs TreeMap vs LinkedHashMap tradeoffs
- [ ] Solve a new unseen hashing problem by recognizing the pattern
- [ ] Know WHY to put AFTER checking in Two Sum
- [ ] Have solved all 11 practice problems in this section

---

## 18. Mini Quiz — Test Yourself

**Answer these before moving on:**

1. In Two Sum, why must `map.put(nums[i], i)` come AFTER the containsKey check?

2. You have 1 million strings containing only lowercase letters. You need to check if two strings are anagrams. Would you use `int[26]` or `HashMap<Character,Integer>`? Why?

3. In `subarraySum(nums, k)`, why do we initialize the map with `{0: 1}`? What case does it handle?

4. What is a collision in hashing? How does Java's HashMap handle it?

5. You need to find the single element that appears only once in an array where all other elements appear twice. Can you solve it in O(n) time and O(1) space without hashing? (Hint: XOR)

6. Given `nums = [3, 3]` and `target = 6`, does your Two Sum implementation correctly return `[0, 1]`? Trace through it.

7. When is `TreeMap` preferred over `HashMap`?

8. What is the time complexity of `groupAnagrams`? (Account for sorting each word.)

> **Answers:**
> 1. Prevents using the same element as its own pair (self-pairing).
> 2. `int[26]` — O(1) space, faster cache access, keys bounded to 26.
> 3. Handles subarrays that start at index 0 (when prefix sum itself equals k).
> 4. Two keys hash to same bucket. Java chains them in a linked list (tree after 8).
> 5. Yes — XOR all elements: pairs cancel out, single remains.
> 6. Yes: i=0: check 3 not in map, put {3:0}. i=1: check 3 IS in map → [0,1] ✓.
> 7. When you need keys in sorted order, or need floor/ceiling/range queries.
> 8. O(n × m log m) where n = number of words, m = max word length.

---

**Next →** `../09_Stack_Patterns/01_Stack_Patterns.md`

---

## Pattern 1: Frequency Counting

```java
// Count char frequency
int[] freq = new int[26];
for (char c : s.toCharArray()) freq[c - 'a']++;

// Count element frequency (any values)
Map<Integer, Integer> freq = new HashMap<>();
for (int num : arr) freq.merge(num, 1, Integer::sum);
```

**Use for:** Anagram detection, top K frequent, duplicate checks.

---

## Pattern 2: Two Sum Variants

```java
// Two Sum (return indices)
Map<Integer, Integer> map = new HashMap<>();
for (int i = 0; i < nums.length; i++) {
    int comp = target - nums[i];
    if (map.containsKey(comp)) return new int[]{map.get(comp), i};
    map.put(nums[i], i);
}

// Two Sum (return boolean, values can repeat)
Set<Integer> seen = new HashSet<>();
for (int num : nums) {
    if (seen.contains(target - num)) return true;
    seen.add(num);
}
```

---

## Pattern 3: Group by Canonical Form

**Group Anagrams:**
```java
Map<String, List<String>> groups = new HashMap<>();
for (String word : words) {
    char[] chars = word.toCharArray();
    Arrays.sort(chars);
    String key = new String(chars);
    groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
}
return new ArrayList<>(groups.values());
```

**Group Isomorphic Strings, Group by digit pattern, etc.** — same idea: find a canonical key.

---

## Pattern 4: Prefix Sum + HashMap

**Count subarrays with sum exactly k:**
```java
// Already covered in Prefix Sum section
// The power: prefix[j] - prefix[i] = k
// → prefix[i] = prefix[j] - k
// Store counts of seen prefix sums
```

**Count subarrays with 0 sum:**
```java
Map<Integer, Integer> seen = new HashMap<>();
seen.put(0, 1);
int sum = 0, count = 0;
for (int num : arr) {
    sum += num;
    count += seen.getOrDefault(sum, 0);
    seen.merge(sum, 1, Integer::sum);
}
```

---

## Pattern 5: Longest Consecutive Sequence (O(n))

```java
int longestConsecutive(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int num : nums) set.add(num);
    int maxLen = 0;
    for (int num : set) {
        if (!set.contains(num - 1)) {  // num is start of sequence
            int curr = num, len = 1;
            while (set.contains(curr + 1)) { curr++; len++; }
            maxLen = Math.max(maxLen, len);
        }
    }
    return maxLen;
}
```

**Why only start at sequence starts?** Avoids re-processing. Each sequence explored once → O(n).

---

## Pattern 6: HashMap for Seen State

**Find duplicate substrings, detect repeated states:**
```java
// Find duplicate in array (no sorting)
Set<Integer> seen = new HashSet<>();
for (int num : arr) {
    if (!seen.add(num)) return num;  // add returns false if already present
}

// First unique character
int[] freq = new int[26];
for (char c : s.toCharArray()) freq[c - 'a']++;
for (int i = 0; i < s.length(); i++)
    if (freq[s.charAt(i) - 'a'] == 1) return i;
```

---

## Pattern 7: HashMap vs TreeMap Decision

| Need | Use |
|------|-----|
| O(1) lookup | HashMap |
| Sorted order on iteration | TreeMap |
| Floor/ceiling queries | TreeMap |
| Count of elements in range | TreeMap |

```java
TreeMap<Integer, Integer> treeMap = new TreeMap<>();
treeMap.floorKey(k);    // largest key ≤ k
treeMap.ceilingKey(k);  // smallest key ≥ k
treeMap.subMap(lo, hi); // keys in [lo, hi)
```

---

## Pattern 8: Rolling Hash (Conceptual)

Used in Rabin-Karp string matching.  
Hash of substring of length k can be computed from previous window in O(1):
```
hash(s[i+1..i+k]) = (hash(s[i..i+k-1]) - s[i] * base^(k-1)) * base + s[i+k]
```

This enables O(n) average string pattern matching.

---

## Common Hashing Mistakes

1. Using `Integer` equality with `==` instead of `.equals()` for values > 127.
2. Modifying the key after inserting into HashMap.
3. Not handling null keys (HashMap allows one null key; TreeMap does not).
4. Forgetting `hashCode()` and `equals()` for custom key objects.

---

## Practice Problems

**Easy:**
1. Two Sum.
2. Contains Duplicate.
3. Happy Number.

**Medium:**
1. Group Anagrams.
2. Top K Frequent Elements.
3. Longest Consecutive Sequence.
4. Isomorphic Strings.
5. Word Pattern.

**Hard:**
1. Minimum Window Substring.
2. Substring with Concatenation of All Words.
3. LFU Cache.

---

**Next →** `../09_Stack_Patterns/01_Stack_Patterns.md`

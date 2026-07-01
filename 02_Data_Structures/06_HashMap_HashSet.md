# Section 2.6 — HashMap & HashSet

---

## 1. What Problem Does This Solve?

HashMap and HashSet solve the problem of fast lookup, insertion, and deletion by key. Arrays give O(1) access by index, but keys must be integers in a small range. HashMap generalizes this to any key type — String, Integer, Object — with O(1) average time.

---

## 2. Beginner-Friendly Intuition

Imagine a massive filing cabinet with 10,000 drawers. Instead of checking each drawer for your file, you use a hash function: it takes your file name and tells you exactly which drawer to check. If two files hash to the same drawer (collision), they're stored in a small list inside that drawer.

Average case: you compute the hash, go directly to the drawer — O(1).
Worst case: everything hashes to one drawer — O(n) (rare in practice with good hash functions).

---

## 3. Real-World Analogy

**Library card catalog (old-school):** To find a book, look up its index card by title. The index card (HashMap) maps title → shelf location. Without it, you'd scan every book on every shelf — O(n).

**Phone contacts:** Your contacts app maps name → phone number. O(1) lookup by name.

---

## 4. Core Concept

### Hash Function
```
hashCode(key) → index in backing array
index = hashCode(key) % capacity

Java HashMap default capacity: 16
Load factor: 0.75 (resize when 75% full)
Resize: double capacity when overloaded → rehash all entries
```

### Collision Resolution in Java HashMap
```
Java 8+: Separate chaining with treeification
- Each bucket is a LinkedList
- When bucket size > 8: convert to Red-Black Tree
- Lookup: O(1) average, O(log n) worst case (if many collisions)
```

### Java HashMap vs HashSet vs LinkedHashMap vs TreeMap

| Class | Key | Value | Order | Time |
|-------|-----|-------|-------|------|
| HashMap | Any | Any | No order | O(1) avg |
| HashSet | Any | - | No order | O(1) avg |
| LinkedHashMap | Any | Any | Insertion order | O(1) avg |
| TreeMap | Comparable | Any | Sorted by key | O(log n) |
| TreeSet | Comparable | - | Sorted | O(log n) |

---

## 5. Pattern Recognition Signals

```
"Count frequency of elements" → HashMap<element, count>
"Find element seen before" → HashSet
"Two Sum / pair with target" → HashMap (value → index)
"Group by some property" → HashMap<key, List<>>
"Sliding window distinct count" → HashMap (window elements)
"Check duplicate" → HashSet.add() returns false if duplicate
"Find first non-repeating" → LinkedHashMap (preserves insertion order)
"Intersection/Union of sets" → HashSet operations
"Anagram grouping" → HashMap<sorted_string, List<String>>
```

---

## 6. Step-by-Step Algorithm

### Two Sum
```
map = {} (value → index)
for each i, num in array:
    complement = target - num
    if complement in map: return [map[complement], i]
    map[num] = i
return [] // no solution
```

### Frequency Count
```
freq = {}
for each element x:
    freq[x] = freq.getOrDefault(x, 0) + 1
```

### Sliding Window with Distinct Count
```
map = {} (char → count in window)
left = 0, distinct = 0
for right in 0..n-1:
    add s[right] to map, increment count
    if count goes from 0 to 1: distinct++
    while distinct > k:         // shrink window
        decrement map[s[left]]
        if map[s[left]] == 0: distinct--; remove from map
        left++
    update max window size
```

---

## 7. Dry Run with Example

### Group Anagrams: ["eat","tea","tan","ate","nat","bat"]
```
Process each word:
"eat" → sorted="aet" → map{"aet":["eat"]}
"tea" → sorted="aet" → map{"aet":["eat","tea"]}
"tan" → sorted="ant" → map{"aet":["eat","tea"], "ant":["tan"]}
"ate" → sorted="aet" → map{"aet":["eat","tea","ate"], "ant":["tan"]}
"nat" → sorted="ant" → map{"aet":[...], "ant":["tan","nat"]}
"bat" → sorted="abt" → map{"aet":[...], "ant":[...], "abt":["bat"]}

Result: [["eat","tea","ate"], ["tan","nat"], ["bat"]] ✓
```

---

## 8. Code Implementation

```java
import java.util.*;

public class HashMapAlgorithms {

    // ── Basic HashMap Operations ──────────────────────────────────────────
    public void apiDemo() {
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 3);
        map.get("apple");               // 3
        map.getOrDefault("banana", 0);  // 0 (key absent)
        map.containsKey("apple");       // true
        map.containsValue(3);           // true
        map.remove("apple");            // removes entry
        map.size();                     // 0
        map.isEmpty();                  // true

        // Iterate
        for (Map.Entry<String, Integer> e : map.entrySet())
            System.out.println(e.getKey() + "=" + e.getValue());
        for (String key : map.keySet()) {}
        for (int val : map.values()) {}

        // Merge / update
        map.merge("apple", 1, Integer::sum); // put 1 if absent, else sum
        map.compute("apple", (k, v) -> v == null ? 1 : v + 1);
        map.computeIfAbsent("list", k -> new ArrayList<>()).add("item");
    }

    // ── Two Sum ────────────────────────────────────────────────────────────
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(); // value → index
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement))
                return new int[]{map.get(complement), i};
            map.put(nums[i], i);
        }
        return new int[]{};
    }

    // ── Group Anagrams ────────────────────────────────────────────────────
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] arr = s.toCharArray();
            Arrays.sort(arr);
            String key = new String(arr); // sorted string as key
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }

    // ── Frequency Count ──────────────────────────────────────────────────
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : nums) freq.merge(n, 1, Integer::sum);
        // Min-heap: keep k most frequent
        PriorityQueue<Integer> pq = new PriorityQueue<>(
            (a, b) -> freq.get(a) - freq.get(b)); // min-heap by frequency
        for (int num : freq.keySet()) {
            pq.offer(num);
            if (pq.size() > k) pq.poll(); // remove least frequent
        }
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) result[i] = pq.poll();
        return result;
    }

    // ── HashSet for Duplicate Detection ────────────────────────────────────
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            if (!seen.add(num)) return true; // add returns false if already present
        }
        return false;
    }

    // ── Longest Consecutive Sequence ─────────────────────────────────────
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);
        int longest = 0;
        for (int num : set) {
            if (!set.contains(num - 1)) { // start of a sequence
                int curr = num, length = 1;
                while (set.contains(curr + 1)) { curr++; length++; }
                longest = Math.max(longest, length);
            }
        }
        return longest;
    }
}
```

---

## 9. Time Complexity

| Operation | Average | Worst Case | Notes |
|-----------|---------|-----------|-------|
| put(key, val) | O(1) | O(n) | Rare — all keys collide |
| get(key) | O(1) | O(log n) | After Java 8 treeification |
| containsKey | O(1) | O(log n) | Same |
| remove(key) | O(1) | O(log n) | Same |
| Iteration | O(n) | O(n) | All entries |

---

## 10. Space Complexity

| Structure | Space |
|-----------|-------|
| HashMap with n entries | O(n) |
| HashSet with n elements | O(n) |
| freq[26] for lowercase | O(1) constant |
| TreeMap with n entries | O(n) |

---

## 11. Edge Cases

| Scenario | Handling |
|----------|---------|
| Null key in HashMap | Allowed — stored at index 0 |
| Null key in TreeMap | NOT allowed — throws NullPointerException |
| Null key in HashSet | Allowed — treated as a valid element |
| Integer overflow in frequency | Use Long if counts can exceed Integer.MAX_VALUE |
| HashMap with custom key object | Must override `hashCode()` AND `equals()` |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Using == for String key comparison in map
map.get("apple") // CORRECT — HashMap uses equals() internally, not ==

// MISTAKE 2: Custom key class not overriding hashCode/equals
class Point { int x, y; }
Map<Point, String> map = new HashMap<>();
map.put(new Point(1,2), "A");
map.get(new Point(1,2)); // WRONG: returns null! Different objects, different hashCode
// CORRECT: override hashCode() and equals() in Point

// MISTAKE 3: ConcurrentModificationException during iteration
for (int key : map.keySet()) if (condition) map.remove(key); // WRONG: CME
// CORRECT: use Iterator or collect keys to remove, then remove after loop

// MISTAKE 4: getOrDefault vs computeIfAbsent
map.getOrDefault(key, new ArrayList<>()).add(item); // WRONG: not stored in map!
map.computeIfAbsent(key, k -> new ArrayList<>()).add(item); // CORRECT

// MISTAKE 5: Forgetting that HashMap is unordered
// If you iterate and need insertion order: use LinkedHashMap
// If you need sorted order: use TreeMap
```

---

## 13. Interview-Level Explanation

**Q: "How does Java's HashMap handle collisions?"**

> "Java's HashMap uses separate chaining — each bucket (index in the backing array) holds a linked list of entries that hash to the same index. In Java 8+, when a bucket's linked list grows beyond 8 entries, it's converted to a Red-Black Tree, reducing worst-case lookup from O(n) to O(log n). The HashMap also resizes (doubles capacity and rehashes) when the load factor (filled buckets / total capacity) exceeds 0.75, maintaining O(1) amortized performance."

**Q: "When would you use TreeMap vs HashMap?"**

> "HashMap gives O(1) average for all operations but provides no ordering. TreeMap maintains keys in sorted order using a Red-Black Tree, with O(log n) operations. Use TreeMap when you need: sorted key iteration, floor/ceiling operations (e.g., `floorKey(k)` returns largest key ≤ k), or range queries on keys. Use HashMap when order doesn't matter and you need maximum speed."

---

## 14. Real-World Use Cases

| Application | HashMap/HashSet |
|------------|----------------|
| **Database indexing** | Hash index for exact lookups |
| **Web caching (URL → response)** | HashMap |
| **DNS lookup** | hostname → IP address |
| **Deduplication** | HashSet |
| **Word frequency** | HashMap<word, count> |
| **Graph adjacency list** | HashMap<node, List<neighbors>> |
| **LRU Cache** | LinkedHashMap with access order |

---

## 15. Variations

| Variation | Structure |
|-----------|----------|
| LRU Cache | LinkedHashMap with removeEldestEntry |
| Frequency map | HashMap<T, Integer> |
| Bidirectional map | Two HashMaps |
| MultiMap (key → multiple values) | HashMap<K, List<V>> |
| Counting sort | int[256] for ASCII or int[26] for letters |
| Set operations | HashSet: addAll (union), retainAll (intersection), removeAll |

---

## 16. Practice Problems

### Easy — Foundation
1. **Two Sum** (LeetCode #1)
   - *Task:* Return indices of two numbers summing to target.
   - *Hint:* HashMap stores value → index.

2. **Contains Duplicate** (LeetCode #217)
   - *Task:* Check if any value appears twice.
   - *Hint:* HashSet — add returns false if already present.

3. **Valid Anagram** (LeetCode #242)
   - *Task:* Check if two strings are anagrams.
   - *Hint:* freq[26] array or HashMap of character counts.

### Medium — Core
1. **Group Anagrams** (LeetCode #49)
   - *Task:* Group strings that are anagrams.
   - *Hint:* Sort each string as HashMap key.

2. **Longest Consecutive Sequence** (LeetCode #128)
   - *Task:* Length of longest consecutive sequence in O(n).
   - *Hint:* HashSet — start sequence only if num-1 not in set.

3. **Top K Frequent Elements** (LeetCode #347)
   - *Task:* K most frequent elements.
   - *Hint:* HashMap freq + min-heap of size k.

4. **Subarray Sum Equals K** (LeetCode #560)
   - *Task:* Count subarrays with sum = k.
   - *Hint:* HashMap of prefix sum frequencies.

5. **LRU Cache** (LeetCode #146)
   - *Task:* O(1) get and put with LRU eviction.
   - *Hint:* LinkedHashMap with access order, or Doubly LL + HashMap.

### Hard — Advanced
1. **Minimum Window Substring** (LeetCode #76)
   - *Task:* Shortest window in s containing all chars of t.
   - *Hint:* Two HashMaps + sliding window.

2. **Alien Dictionary** (LeetCode #269)
   - *Task:* Determine character ordering from sorted alien words.
   - *Hint:* Topological sort with HashMap adjacency list.

3. **All O'one Data Structure** (LeetCode #432)
   - *Task:* O(1) for inc, dec, getMaxKey, getMinKey.
   - *Hint:* Doubly linked list of frequency buckets + HashMap.

---

## 17. How to Know You Have Mastered HashMap & HashSet

You have mastered this topic when you can:
- [ ] Implement frequency counting with `getOrDefault` and `merge`
- [ ] Use `computeIfAbsent` for group-by patterns
- [ ] Know when to use HashMap vs TreeMap vs LinkedHashMap
- [ ] Override `hashCode()` and `equals()` for custom key objects
- [ ] Implement Two Sum and Group Anagrams from scratch
- [ ] Explain how Java HashMap handles collisions (chaining + treeification)
- [ ] Use HashSet for O(1) duplicate detection
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. `map.getOrDefault("x", new ArrayList<>()).add("item")` — does this store "item" in the map? Why or why not?

2. You create a class `Point(int x, int y)` and use it as a HashMap key without overriding anything. What happens?

3. What is the default load factor and initial capacity of Java HashMap?

4. TreeMap vs HashMap for `floorKey(k)` operation — which supports it, and what does it return?

5. `set.add(x)` returns false — what does this mean?

6. Why must you NOT modify a HashMap while iterating its `keySet()` directly?

7. For Longest Consecutive Sequence, why do we only start counting when `set.contains(num-1)` is false?

8. HashMap's worst-case lookup is O(n). In Java 8+, what optimization reduces this to O(log n)?

> **Answers:**
> 1. No, it does NOT store "item" in the map. `getOrDefault` returns the default value (a new ArrayList) without inserting it into the map. The list is created, "item" is added to it, but then it's discarded. Use `map.computeIfAbsent("x", k -> new ArrayList<>()).add("item")` to store it.
> 2. Java uses the default `hashCode()` (based on object identity/memory address) and `equals()` (reference equality). Two `Point(1,2)` objects are different objects → different hashCodes → stored in different buckets → `map.get(new Point(1,2))` returns null even though a logically equal key exists.
> 3. Default initial capacity: 16. Default load factor: 0.75. HashMap resizes when size > capacity × load factor = 16 × 0.75 = 12 entries.
> 4. TreeMap supports `floorKey(k)` — returns the largest key ≤ k (or null if none). HashMap does NOT support it since entries are unordered. TreeMap also supports `ceilingKey`, `headMap`, `tailMap` for range queries.
> 5. `set.add(x)` returns false when x was already in the set (i.e., duplicate detected). The element is NOT added again (sets don't store duplicates).
> 6. Modifying a HashMap (adding/removing entries) during iteration changes the internal structure. Java's HashMap is fail-fast: it throws `ConcurrentModificationException` if the modCount changes during iteration. Use `Iterator.remove()` or collect keys then remove after loop.
> 7. If `num-1` is in the set, then `num` is not the START of a consecutive sequence — `num-1` already began a longer sequence. Starting from each sequence's beginning avoids redundant counting and ensures O(n) total work.
> 8. When a bucket's chain grows beyond 8 entries, Java 8+ converts it from a LinkedList to a Red-Black Tree. Tree lookup is O(log n) even in the worst case, compared to O(n) for a degenerate linked list.

---

**Next →** `07_Trees_Binary_BST.md`

When you need **O(1) lookup by key** — not by index.  
No matter how large the dataset, finding "does key exist?" or "what value maps to key?" in constant time.

---

## 2. Beginner-Friendly Intuition

A HashMap is like a **dictionary**:
- Word (key) → Definition (value)
- You don't search page by page — you look up directly.

A HashSet is like a **uniqueness checker**:
- Just stores keys, no values.
- "Have I seen this before?" in O(1).

---

## 3. How Hashing Works Internally

1. Key is passed through a **hash function** → produces an integer (hash code).
2. Hash code is mapped to an **array index** (hash code % capacity).
3. Value is stored at that index.

```
key="apple"  →  hashCode=92731  →  index = 92731 % 16 = 11
key="mango"  →  hashCode=18432  →  index = 18432 % 16 =  0
```

**Collision:** Two keys map to same index. Handled by:
- **Chaining:** Each slot holds a linked list.
- **Open addressing:** Find next empty slot.

---

## 4. Operations & Complexity

| Operation | Average | Worst Case |
|-----------|---------|-----------|
| put(key, val) | O(1) | O(n) (all collisions) |
| get(key) | O(1) | O(n) |
| containsKey(key) | O(1) | O(n) |
| remove(key) | O(1) | O(n) |
| Iterate | O(n) | O(n) |

> Worst case happens with poor hash function or adversarial input. In practice, Java's HashMap is O(1).

---

## 5. Java Implementation

```java
// HashMap — key-value pairs
Map<String, Integer> map = new HashMap<>();
map.put("apple", 5);
map.get("apple");               // 5
map.getOrDefault("mango", 0);   // 0 (safe default)
map.containsKey("apple");       // true
map.remove("apple");
map.size();

// Iterate
for (Map.Entry<String, Integer> e : map.entrySet()) {
    System.out.println(e.getKey() + " → " + e.getValue());
}
for (String key : map.keySet()) { }
for (int val : map.values()) { }

// HashSet — unique elements
Set<Integer> set = new HashSet<>();
set.add(5);
set.contains(5);    // true — O(1)
set.remove(5);
set.size();

// Frequency counting (classic pattern)
Map<Character, Integer> freq = new HashMap<>();
for (char c : s.toCharArray())
    freq.merge(c, 1, Integer::sum);
// or:
freq.put(c, freq.getOrDefault(c, 0) + 1);
```

---

## 6. TreeMap vs HashMap

| Feature | HashMap | TreeMap |
|---------|---------|---------|
| Order | Unordered | Sorted by key |
| Get/Put | O(1) avg | O(log n) |
| Iteration order | Arbitrary | Sorted |
| Use case | Fast lookup | Ordered traversal |

```java
TreeMap<Integer, String> treeMap = new TreeMap<>();
treeMap.firstKey();         // smallest key
treeMap.lastKey();          // largest key
treeMap.floorKey(5);        // largest key ≤ 5
treeMap.ceilingKey(5);      // smallest key ≥ 5
```

---

## 7. Key Interview Patterns

### Two Sum
```java
Map<Integer, Integer> seen = new HashMap<>();
for (int i = 0; i < nums.length; i++) {
    int comp = target - nums[i];
    if (seen.containsKey(comp)) return new int[]{seen.get(comp), i};
    seen.put(nums[i], i);
}
```

### Group Anagrams
```java
Map<String, List<String>> groups = new HashMap<>();
for (String word : words) {
    char[] chars = word.toCharArray();
    Arrays.sort(chars);
    String key = new String(chars);  // "eat" → "aet"
    groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
}
```

### Longest Consecutive Sequence
```java
Set<Integer> set = new HashSet<>(Arrays.asList(nums));
int maxLen = 0;
for (int num : set) {
    if (!set.contains(num - 1)) {  // start of sequence
        int curr = num, len = 1;
        while (set.contains(curr + 1)) { curr++; len++; }
        maxLen = Math.max(maxLen, len);
    }
}
```

---

## 8. When HashMap Fails

- When keys aren't hashable (custom objects need `hashCode()` and `equals()`)
- When order matters → use TreeMap
- When you need to access by index → use array

---

## 9. Practice Problems

**Easy:**
1. Two Sum.
2. Contains Duplicate.
3. First unique character in string.

**Medium:**
1. Group Anagrams.
2. Top K Frequent Elements.
3. Longest Consecutive Sequence.
4. Subarray sum equals K.
5. 4Sum II.

**Hard:**
1. Minimum Window Substring.
2. Substring with Concatenation of All Words.
3. LRU Cache.

---

**Next →** `07_Trees_Binary_BST.md`

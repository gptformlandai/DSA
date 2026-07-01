# Section 1.3 — Problem Solving Framework

---

## The Universal DSA Problem Solving Method

Use this framework for every single problem — in practice AND in interviews.

---

## Step-by-Step Framework

```
1. READ       → Understand what is being asked
2. EXAMPLES   → Work examples by hand (small ones)
3. BRUTE      → Think brute force first
4. ANALYZE    → What's the complexity? Where's the bottleneck?
5. PATTERN    → What DSA pattern fits?
6. OPTIMIZE   → Reduce time/space complexity
7. CODE       → Write clean code
8. TEST       → Check edge cases
9. EXPLAIN    → Communicate complexity
```

---

## Step 1: Read the Problem

- Read it twice before touching code.
- Identify: What is given? What must be returned?
- Restate it in your own words.

**Example:** "Given an array of integers, return indices of two numbers that add to target."

Restate: "Find i and j (i ≠ j) such that arr[i] + arr[j] == target."

---

## Step 2: Work Examples by Hand

Always start with small examples:
```
arr = [2, 7, 11, 15], target = 9
2 + 7 = 9 ✓  → return [0, 1]
```

Then try edge cases:
```
arr = [3], target = 6       → no pair
arr = [3, 3], target = 6    → same element twice? check problem
arr = [], target = 0        → empty input
```

---

## Step 3: Think Brute Force First

Don't try to be clever immediately.  
The brute force always exists. State it.

```java
// Two Sum — Brute Force
for (int i = 0; i < n; i++)
    for (int j = i+1; j < n; j++)
        if (arr[i] + arr[j] == target)
            return new int[]{i, j};
```

Now you have a baseline: O(n²) time, O(1) space.

---

## Step 4: Identify the Bottleneck

Ask: "What is the slow part?"

In Two Sum:
- The inner loop redoes work — for each i, we search all j's.
- We're **re-checking** already seen elements.

Bottleneck: Repeated search.

---

## Step 5: Match a Pattern

Use the cheatsheet in `26_Cheatsheets/` to map the bottleneck to a pattern.

> "Need to look up whether a number exists? → **HashMap** → O(1) lookup"

---

## Step 6: Optimize

```java
// Two Sum — Optimized
Map<Integer, Integer> map = new HashMap<>();
for (int i = 0; i < n; i++) {
    int complement = target - arr[i];
    if (map.containsKey(complement))
        return new int[]{map.get(complement), i};
    map.put(arr[i], i);
}
```

Now: O(n) time, O(n) space.

---

## Step 7: Code Cleanly

Rules for clean code:
- Meaningful variable names (`lo`, `hi` not `a`, `b`)
- Handle null/empty inputs first
- Don't nest more than 2 levels deep if possible
- Add comments for non-obvious logic only

---

## Step 8: Test Edge Cases

Always test these:
| Category | Example |
|---------|---------|
| Empty input | `[]`, `""`, `null` |
| Single element | `[5]` |
| All same elements | `[1,1,1,1]` |
| Negative numbers | `[-1, -2, 3]` |
| Max/min integer | `Integer.MAX_VALUE` |
| Already sorted | `[1,2,3,4,5]` |
| Reverse sorted | `[5,4,3,2,1]` |

---

## Step 9: Communicate in Interview

Structure your verbal explanation:
1. "I'll approach this by..."
2. "My brute force is O(n²) because..."
3. "I can optimize by using a HashMap to get O(1) lookup..."
4. "My final solution is O(n) time and O(n) space..."
5. "Edge cases I'm handling: empty array, duplicates..."

---

## Pattern Recognition Quick Guide

| Problem Says... | Think... |
|----------------|---------|
| Sorted array + find something | Binary Search |
| Find subarray / contiguous window | Sliding Window / Prefix Sum |
| Find pair in array | Two Pointers / HashMap |
| All combinations/permutations | Backtracking |
| Min/Max repeated queries | Heap |
| Dependencies / ordering | Topological Sort |
| Shortest path | BFS / Dijkstra |
| Connected components | DFS / BFS / DSU |
| Choices at each step | DP or Greedy |
| Overlapping intervals | Sort + Sweep |
| String prefixes | Trie |
| Next greater/smaller element | Monotonic Stack |
| Range queries | Segment Tree / Fenwick |
| Detect cycle | Floyd / DFS with color |

---

## How to Avoid Interview Panic

1. **Think aloud** — silence is worse than wrong thinking.
2. **Start simple** — brute force first, then optimize.
3. **Ask clarifying questions** — "Can there be duplicates? Is input sorted?"
4. **Draw the example** — visualize on paper/whiteboard.
5. **Say "let me trace through this"** before coding.
6. **Don't freeze** — if stuck, explain the bottleneck: "I know this part is slow..."

---

**Next →** `../02_Data_Structures/01_Arrays.md`

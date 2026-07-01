# Section 25 — Designing a Problem Solving System

---

## 1. What Problem Does This Solve?

Every DSA interview and competitive programming contest demands a reliable process to go from "I have no idea" to "I have an efficient solution." Without a system, most people panic, start coding immediately, get stuck, and waste time on wrong approaches.

This section gives you a repeatable 10-step framework that works for every problem — from Easy to Hard.

---

## 2. Beginner-Friendly Intuition

A DSA problem is like a locked room. The key is not memorizing every possible lock — it's learning to recognize lock types and having the right tools ready.

The framework is:
1. Understand what the lock looks like
2. Classify the lock type (array? graph? DP?)
3. Think brute force first (always works, just slow)
4. Optimize by recognizing the pattern
5. Code cleanly, then test

---

## 3. Real-World Analogy

A doctor doesn't diagnose by guessing. They:
1. Collect symptoms (read the problem)
2. Rule out conditions (identify constraints)
3. Run tests (dry run examples)
4. Confirm hypothesis (verify edge cases)
5. Prescribe treatment (write correct code)

Following the same process ensures repeatable success, not luck.

---

## 4. Core Concept: The 10-Step Framework

```
Step 1:  Read the problem completely (don't start coding yet)
Step 2:  Clarify constraints and edge cases
Step 3:  Identify input/output and data types
Step 4:  Classify the problem by pattern
Step 5:  Think brute force (verbalize it)
Step 6:  Optimize (eliminate the bottleneck)
Step 7:  Write pseudocode (verify logic)
Step 8:  Code the solution (Java)
Step 9:  Trace through test cases (dry run)
Step 10: Analyze complexity and discuss trade-offs
```

---

## 5. Pattern Recognition Signals

### Pattern Classification Guide

| Signal in Problem | Pattern to Consider |
|-------------------|---------------------|
| Sorted array, find target | Binary Search |
| Contiguous subarray | Sliding Window / Prefix Sum |
| Two elements summing to target | Two Pointers / Hashing |
| Maximize/minimize something | Greedy / DP |
| All combinations/permutations | Backtracking |
| Shortest path | BFS (unweighted) / Dijkstra (weighted) |
| Connected components | DFS / DSU |
| Overlapping subproblems | DP |
| Linked list cycle / middle | Fast-Slow Pointers |
| Parentheses / nested structure | Stack |
| Kth largest/smallest | Heap |
| Prefix matching | Trie |
| Range queries with updates | Segment Tree / BIT |

---

## 6. Step-by-Step Algorithm (The Framework in Detail)

### Step 1: Read Completely
- Read the problem statement AND all examples before thinking about code
- Highlight key verbs: "find", "count", "return", "maximize"

### Step 2: Clarify Constraints
Ask (or write down):
```
- Input size: n = 10^5? 10^9?
- Value range: arr[i] negative? zero possible?
- Duplicates allowed?
- Empty input possible?
- Return format: index? value? boolean?
```

### Step 3: Identify Data Types
```
n ≤ 10^9 → int is fine (2^31-1 ≈ 2.1 × 10^9)
n × n ≤ 10^18 → use long
String problems → char[] or StringBuilder
```

### Step 4: Classify by Pattern
Use the signal table above. Identify 1-2 candidate patterns.

### Step 5: Brute Force First
Always articulate the O(n^2) or O(2^n) solution verbally:
> "The brute force is to check every pair, which is O(n^2). We need to reduce this."

### Step 6: Optimize — Remove the Bottleneck
Common optimizations:
```
O(n^2) → O(n log n): Sort + two pointer, or Binary Search
O(n^2) → O(n):       Hashing, Prefix Sum, Sliding Window
O(2^n) → O(n^2):     DP (memoize repeated subproblems)
O(n^3) → O(n^2 log n): BIT or Segment Tree
```

### Step 7: Write Pseudocode
Write 5-10 lines of English pseudocode before Java code:
```
Sort the array
Use left=0, right=n-1 pointers
While left < right:
    if sum < target: left++
    else if sum > target: right--
    else: record pair, move both
```

### Step 8: Code
Translate pseudocode to Java. Keep it clean.

### Step 9: Dry Run
Trace through at least 2 examples:
- One normal case
- One edge case (empty, single element, all same)

### Step 10: Complexity Analysis
State time and space complexity, and discuss any trade-offs.

---

## 7. Dry Run with Example

### Problem: Two Sum (LeetCode #1)
```
Input: nums = [2, 7, 11, 15], target = 9

Step 1: Find two numbers summing to 9.
Step 2: n ≤ 10^4, values can be negative.
Step 3: Return indices (int[]).
Step 4: Two elements summing to target → Hashing pattern.
Step 5: Brute force: check all pairs O(n^2).
Step 6: Use HashMap: store (value → index) as we scan.
         For each num, check if (target - num) is in map.
Step 7: 
  map = {}
  for i, num in nums:
    complement = target - num
    if complement in map: return [map[complement], i]
    map[num] = i

Step 8: Code it (see below).
Step 9: 
  i=0, num=2, complement=7, map={} → not found, map={2:0}
  i=1, num=7, complement=2, map={2:0} → FOUND! return [0,1] ✓

Step 10: O(n) time, O(n) space.
```

---

## 8. Code Implementation

```java
// Template for problem-solving mindset in Java
// Example: Two Sum problem as demonstration of the framework

class Solution {
    public int[] twoSum(int[] nums, int target) {
        // Step 6: HashMap stores (value → index) for O(1) lookup
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];  // What do we need?

            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};  // Found!
            }

            map.put(nums[i], i);  // Store for future use
        }

        return new int[]{};  // No solution found (per constraints, won't reach)
    }
}
```

```java
// Framework Template (copy-paste starter for every problem)
class Solution {
    public ??? solve(??? input) {
        // Step 2: Handle edge cases first
        if (input == null || input.length == 0) return ???;

        // Step 7: Implementation following pseudocode
        // ... your logic here

        // Step 10: Ensure no unnecessary operations
        return result;
    }
}
```

---

## 9. Time Complexity of Problem Solving

| Problem Constraint | Allowed Complexity | Typical Algorithm |
|-------------------|-------------------|-------------------|
| n ≤ 10 | O(n!) | Backtracking, permutations |
| n ≤ 20 | O(2^n) | Bitmask DP, subsets |
| n ≤ 500 | O(n^2) or O(n^3) | DP, Floyd-Warshall |
| n ≤ 10^4 | O(n^2) | Nested loops OK |
| n ≤ 10^5 | O(n log n) | Sort, BST, Heap |
| n ≤ 10^6 | O(n) or O(n log n) | Linear scan, BFS/DFS |
| n ≤ 10^9 | O(log n) or O(sqrt n) | Binary search, math |

---

## 10. Space Complexity

| Extra Data Structure | Space Cost |
|---------------------|-----------|
| HashMap/HashSet | O(n) |
| Recursion stack depth d | O(d) |
| DP table | O(n×m) or O(n) if optimized |
| Sorting (Java TimSort) | O(log n) to O(n) |

---

## 11. Edge Cases to Always Check

| Scenario | What to Test |
|----------|-------------|
| Empty input | `n = 0`, null array |
| Single element | `n = 1` |
| All same elements | `[5, 5, 5, 5]` |
| Already sorted | Ascending and descending |
| Negative numbers | Negative values, negative sums |
| Max constraint | n = 10^5, verify no TLE |
| Integer overflow | Large values — use `long` |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Jumping to code without understanding
// Start coding immediately after reading → miss edge cases, wrong approach
// CORRECT: Read fully, clarify, classify, THEN code

// MISTAKE 2: Not considering negative numbers
int sum = 0; // Sum might need long, or go negative
// CORRECT: Read constraints. Use long when values can be > 10^4

// MISTAKE 3: Off-by-one in loops
for (int i = 0; i < n; i++) // CORRECT for 0-indexed
for (int i = 1; i <= n; i++) // CORRECT for 1-indexed
for (int i = 0; i <= n; i++) // WRONG: ArrayIndexOutOfBoundsException

// MISTAKE 4: Modifying array while iterating
for (int x : list) list.remove(x); // WRONG: ConcurrentModificationException
// CORRECT: Collect to remove, then remove after loop

// MISTAKE 5: Not testing with edge cases before submitting
// Always trace empty input, single element, and max constraint test case
```

---

## 13. Interview-Level Explanation

**Q: "How do you approach a problem you've never seen before?"**

> "I start by reading the problem fully and understanding exactly what input/output is expected. Then I note the constraints — especially n — since that tells me what complexity I need. I verbalize the brute force approach first, even if it's slow, because it confirms I understand the problem. Then I look for inefficiencies: am I recomputing something? Am I scanning unnecessarily? I identify the pattern (two pointers, DP, BFS, etc.) and translate that to pseudocode before writing Java. Finally, I trace through 2-3 examples to verify correctness before discussing complexity."

**Q: "What do you do when you're stuck?"**

> "I go back to first principles. I re-read the constraints — sometimes n ≤ 100 means O(n^2) is fine. I try small examples and look for patterns. If I see subproblems overlapping, that signals DP. If I see a sorted array, I think binary search. If stuck on optimization, I think about what information I'm 'throwing away' on each iteration — can I cache it in a HashMap? That reframing usually unlocks the approach."

---

## 14. Real-World Use Cases

| Problem Solving Step | Engineering Equivalent |
|---------------------|----------------------|
| Clarify constraints | Define requirements and SLAs |
| Brute force first | Write working MVP before optimizing |
| Identify bottleneck | Profiling and performance analysis |
| Pseudocode | System design before implementation |
| Test edge cases | Unit and integration testing |
| Complexity analysis | Capacity planning |

---

## 15. Variations of This Pattern

| Approach | When to Use |
|----------|------------|
| Reduce to known problem | Transform problem to a well-known pattern |
| Divide and conquer | Problem splits into independent halves |
| Simulation | No clever trick; simulate exactly what's described |
| Constructive | Build the answer element-by-element |
| Proof by contradiction | Show any deviation from greedy choice leads to worse result |

---

## 16. Practice Problems (Apply the Full Framework)

### Easy — Warm Up
1. **Two Sum** (LeetCode #1) — Apply all 10 steps before coding.
2. **Valid Parentheses** (LeetCode #20) — Classify pattern, state brute force.
3. **Best Time to Buy and Sell Stock** (LeetCode #121) — State the O(n^2) brute force, then optimize.

### Medium — Full Framework Required
1. **3Sum** (LeetCode #15) — Sort + two pointers. Handle duplicates.
2. **Longest Substring Without Repeating Characters** (LeetCode #3) — Sliding window.
3. **Word Search** (LeetCode #79) — Backtracking on 2D grid.
4. **Jump Game II** (LeetCode #45) — Greedy. State why greedy is correct.
5. **Coin Change** (LeetCode #322) — DP. State brute force (2^n), optimize to O(n×amount).

### Hard — Where the Framework Matters Most
1. **Trapping Rain Water** (LeetCode #42) — Multiple approaches; argue complexity trade-offs.
2. **Median of Two Sorted Arrays** (LeetCode #4) — Binary search on answer. State O(n+m) merge first.
3. **Hard DP of your choice** — Apply all 10 steps, document your classification.

---

## 17. How to Know You Have Mastered Problem Solving

You have mastered this topic when you can:
- [ ] Apply all 10 steps automatically for any unfamiliar problem
- [ ] Identify the correct pattern within 2-3 minutes of reading
- [ ] State a brute force solution for any problem before optimizing
- [ ] Use the constraint table to immediately know required complexity
- [ ] Write pseudocode before any code for problems above Easy
- [ ] Catch your own off-by-one and overflow bugs before running
- [ ] Dry run edge cases: empty, single element, all same, max n
- [ ] Explain time and space complexity clearly with trade-offs

---

## 18. Mini Quiz — Test Yourself

1. n = 10^6 and you need an O(n log n) solution. You have a nested loop O(n^2). What's the single biggest optimization technique to try first?

2. The problem says "Find all combinations." What pattern does this signal?

3. What is the brute force for "Find two numbers in sorted array summing to target"? What is the optimized version?

4. "Return the minimum cost to reach the end." Subproblems overlap. What pattern?

5. You see a problem with n ≤ 20 and "all possible subsets." What complexity is expected?

6. What edge cases do you ALWAYS test for array problems?

7. The problem has a graph with weighted edges. Shortest path. Which algorithm?

8. Your O(n^2) DP solution for n=10^5 times out. What technique often reduces DP from O(n^2) to O(n log n)?

> **Answers:**
> 1. HashSet or HashMap for O(1) lookup instead of O(n) inner scan. Or sorting + binary search. Both eliminate the inner loop.
> 2. Backtracking (generate all combinations/subsets/permutations).
> 3. Brute force: check all pairs O(n^2). Optimized: since sorted, two pointers O(n): left=0, right=n-1, move based on sum vs target.
> 4. Dynamic Programming — the "overlapping subproblems" signal.
> 5. O(2^n) — expected, since all subsets of 20 elements is 2^20 ≈ 10^6.
> 6. Empty array (n=0), single element (n=1), all same values, negative values, duplicate values, already sorted (ascending and descending).
> 7. Dijkstra's algorithm O((V+E) log V) for non-negative weights. Bellman-Ford for negative weights.
> 8. Optimizing the DP transition using a monotonic deque (sliding window max/min), segment tree, or BIT — reducing O(n) inner loop to O(log n).

---

**Next →** `../03_Searching/01_Searching_Algorithms.md`

---

### Step 1: Understand the Problem

Read twice. Then answer:
- What are the inputs? (type, size, constraints)
- What is the output? (format, edge cases)
- What are the rules? (can I modify input? is it sorted?)

**Example:** "Given array of integers, return indices of two numbers that add to target."
- Input: `int[] nums`, `int target`
- Output: `int[]` — indices, not values
- Constraint: Exactly one solution, can't use same element twice

---

### Step 2: Work Small Examples by Hand

Never skip this. Hand-trace at least 2 examples.

```
nums = [2, 7, 11, 15], target = 9
Check: 2+7=9 → indices [0,1] ✓

nums = [3, 2, 4], target = 6
3+2=5, 3+4=7, 2+4=6 → indices [1,2] ✓
```

---

### Step 3: Try Brute Force First

"What's the most obvious solution?"
Never dismiss it — it establishes correctness.

```java
// Two Sum brute force
for (int i = 0; i < n; i++)
    for (int j = i+1; j < n; j++)
        if (nums[i] + nums[j] == target)
            return new int[]{i, j};
// O(n²) time, O(1) space
```

---

### Step 4: Analyze Brute Force

- Time complexity: O(n²)
- Space complexity: O(1)
- "For n=10⁵, that's 10¹⁰ operations — too slow."

---

### Step 5: Identify the Bottleneck

Ask: "What part is slow? What work is being repeated?"

In Two Sum: "For each i, I re-search all j's for the complement."
- Bottleneck: Repeated search.
- Question: Can I look up complement in O(1)?

---

### Step 6: Match to a Pattern

Use the cheatsheet:
- "Need O(1) lookup of a value?" → HashMap

---

### Step 7: Optimize

```java
Map<Integer, Integer> map = new HashMap<>();
for (int i = 0; i < nums.length; i++) {
    int comp = target - nums[i];
    if (map.containsKey(comp)) return new int[]{map.get(comp), i};
    map.put(nums[i], i);
}
// O(n) time, O(n) space
```

---

### Step 8: Dry Run the Optimized Solution

```
nums=[2,7,11,15], target=9
i=0: comp=7, map={}, not found → map={2:0}
i=1: comp=2, map has 2 → return [0,1] ✓
```

---

### Step 9: Code Cleanly

```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> seen = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (seen.containsKey(complement))
            return new int[]{seen.get(complement), i};
        seen.put(nums[i], i);
    }
    return new int[]{};  // no solution (shouldn't reach here per constraints)
}
```

---

### Step 10: Test Edge Cases

```
nums = [3, 3], target = 6    → [0, 1] (same value, different indices)
nums = [1], target = 2       → [] (single element)
nums = [-1, -2, -3], target = -5 → [1, 2]
```

---

## Interview Communication Script

**Opening:**
> "Let me make sure I understand the problem. Given [inputs], I need to return [output]. The constraints are [constraints]. Is that correct?"

**After examples:**
> "Let me trace through a few examples to confirm my understanding..."

**Brute force:**
> "The brute force approach would be to [describe]. This is O(n²) time because [reason]. Let me think about how to optimize this."

**Optimization:**
> "The bottleneck is [X]. I can improve this by [technique]. This brings the time complexity to [Y] because [reason]."

**While coding:**
> "I'm initializing [X] to handle [edge case]..."
> "This loop runs [n times] because [reason]..."

**After coding:**
> "Let me trace through my example again to verify..."
> "Edge cases I want to check: empty input, single element, negative numbers..."

---

## How to Recognize Patterns Quickly

Train your eye to see these signals:

```
See:                          Think:
─────────────────────────────────────────────────────
Sorted array                 Binary Search / Two Pointers
Find k-th largest            Quick Select / Heap
Maximum/minimum subarray     Sliding Window / Kadane's
Count subarrays with sum K   Prefix Sum + HashMap
All combinations/subsets     Backtracking
Shortest path                BFS (unweighted) / Dijkstra
Dependencies                 Topological Sort
Intervals                    Sort + Greedy / Sweep Line
Optimal substructure         Dynamic Programming
"Can we achieve X?"          Binary Search on Answer
Repeated min/max             Monotonic Stack
Strings with prefix          Trie
Range query with updates     Segment Tree / BIT
Connectivity / merging sets  DSU
```

---

## How to Debug DSA Code

1. **Print intermediate states** — what are your variables after each iteration?
2. **Trace with smallest input** — n=2 or n=3 first.
3. **Check base cases** — does it handle empty/single-element correctly?
4. **Off-by-one** — is it `< n` or `<= n`? Is it `lo + (hi-lo)/2` or `(lo+hi)/2`?
5. **Integer overflow** — multiply before comparing? Cast to `long`.
6. **Null pointer** — is `node.next != null` checked before accessing `node.next.val`?

---

## Writing Clean Java Code

```java
// Use meaningful names
int left = 0, right = n - 1;    // not: int a = 0, b = n-1;

// Guard clauses first
if (nums == null || nums.length == 0) return 0;

// Avoid magic numbers
final int MOD = 1_000_000_007;

// Use long when multiplication can overflow
long product = (long) a * b;

// Ternary for simple if-else
int min = a < b ? a : b;

// Collections.emptyList() for empty returns
return Collections.emptyList();
```

---

**Next →** `../26_Cheatsheets/01_Pattern_Recognition.md`

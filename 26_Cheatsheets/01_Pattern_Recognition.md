# Section 26 — Pattern Recognition Cheatsheet

> Bookmark this. Return to it after every problem.

---

## 🔍 Array / Number Problems

| If the problem says... | Use this |
|-----------------------|---------|
| Array is sorted + find element | Binary Search |
| Find two elements summing to target | Two Pointers (sorted) or HashMap (unsorted) |
| Find three elements summing to target | Sort + Two Pointers per element |
| Contiguous subarray with max sum | Kadane's Algorithm |
| Contiguous subarray summing to K | Prefix Sum + HashMap |
| Longest/shortest subarray with condition | Sliding Window |
| Maximum sum subarray of size K | Fixed Sliding Window |
| Move elements in-place | Two Pointers (slow/fast) |
| Rotate array | Reverse trick |
| Find missing/duplicate | XOR / Sum formula / Cycle detection |
| Next permutation | Two Pointers from end |
| Majority element | Boyer-Moore Voting |

---

## 🔍 String Problems

| If the problem says... | Use this |
|-----------------------|---------|
| Anagram / character frequency | Frequency array (int[26]) or HashMap |
| Longest substring without repeating | Sliding Window + HashSet |
| Pattern matching in text | KMP / Rabin-Karp / Z-Algorithm |
| All palindromic substrings | Expand Around Center |
| Longest palindromic substring | Expand Around Center / Manacher's |
| Edit/transform one string to another | DP (Edit Distance) |
| Common subsequence/substring | DP (LCS/LCS) |
| Words starting with prefix | Trie |
| String encoding/decoding | Stack or StringBuilder |

---

## 🔍 Linked List Problems

| If the problem says... | Use this |
|-----------------------|---------|
| Detect/find cycle | Floyd's Fast & Slow Pointers |
| Find middle | Fast & Slow Pointers |
| Kth from end | Two Pointers (gap k) |
| Reverse whole or partial | Iterative pointer reversal |
| Merge sorted lists | Two pointers + dummy node |
| Check palindrome | Find middle + reverse + compare |

---

## 🔍 Tree Problems

| If the problem says... | Use this |
|-----------------------|---------|
| Level-by-level processing | BFS (Queue) |
| Path from root to leaf | DFS + backtracking |
| Validate BST | DFS with min/max bounds |
| LCA | Post-order DFS |
| Height / depth | Recursive DFS |
| Serialize/deserialize | Preorder DFS |
| Diameter / longest path | Post-order + global max |
| Vertical order / column grouping | DFS + TreeMap by column |
| Convert sorted to BST | Binary search mid as root |

---

## 🔍 Graph Problems

| If the problem says... | Use this |
|-----------------------|---------|
| Connected components count | DFS/BFS/DSU |
| Shortest path (unweighted) | BFS |
| Shortest path (weighted, positive) | Dijkstra |
| Shortest path (negative weights) | Bellman-Ford |
| All-pairs shortest path | Floyd-Warshall |
| Detect cycle (undirected) | DFS with parent / DSU |
| Detect cycle (directed) | DFS with color (white/gray/black) |
| Topological order | Kahn's (BFS) / DFS |
| Minimum spanning tree | Kruskal's (DSU) / Prim's (Heap) |
| Bipartite check | BFS 2-coloring |
| Islands / flood fill | DFS/BFS on grid |
| Distance from multiple sources | Multi-source BFS |
| DAG + optimal choices | DP on DAG |
| Merging groups / sets | DSU |

---

## 🔍 DP Problems

| If the problem says... | Use this |
|-----------------------|---------|
| Count ways to reach target | DP — count variations |
| Maximum/minimum of some value | DP — optimization |
| Can we achieve X? | DP — boolean (or greedy) |
| Subsequence (not contiguous) | LCS-style DP |
| Substring (contiguous) | Sliding window or DP |
| 0/1 choice for each item | 0/1 Knapsack DP |
| Unlimited use of items | Unbounded Knapsack DP |
| Choosing intervals optimally | Interval DP |
| State is a subset | Bitmask DP |
| Tree structure + optimization | Tree DP |

---

## 🔍 Heap / Priority Queue

| If the problem says... | Use this |
|-----------------------|---------|
| Kth largest/smallest | Min-Heap of size K |
| Top K frequent elements | Min-Heap of size K |
| Merge K sorted arrays/lists | Min-Heap with all heads |
| Median maintenance | Two Heaps (max + min) |
| Scheduling with priorities | Max-Heap |
| Dijkstra's algorithm | Min-Heap |

---

## 🔍 Stack / Queue / Deque

| If the problem says... | Use this |
|-----------------------|---------|
| Next greater element | Monotonic decreasing stack |
| Next smaller element | Monotonic increasing stack |
| Largest rectangle | Monotonic stack |
| Valid parentheses / brackets | Stack |
| Expression evaluation | Two-stack (operators + operands) |
| BFS traversal | Queue |
| Level-by-level processing | Queue + level size snapshot |
| Sliding window max/min | Monotonic Deque |

---

## 🔍 Binary Search

| If the problem says... | Use this |
|-----------------------|---------|
| Sorted array + find value | Classic binary search |
| Find first/last occurrence | Binary search with boundary |
| Minimum/maximum that satisfies condition | Binary Search on Answer |
| "Feasibility" function that's monotonic | Binary Search on Answer |
| Search in 2D sorted matrix | Map to 1D, binary search |
| Search in rotated array | Modified binary search |

---

## 🔍 Backtracking

| If the problem says... | Use this |
|-----------------------|---------|
| Generate all subsets | Subsets backtracking |
| Generate all permutations | Permutations backtracking |
| Generate all combinations | Combination backtracking |
| Find if a path exists (brute) | DFS / backtracking |
| Solve constraint puzzle | Constraint satisfaction backtracking |
| Find word in grid | DFS + visited marking |

---

## 💡 Complexity Shortcuts

```
O(1)       → Direct math, HashMap lookup, stack/queue ops
O(log n)   → Binary search, heap insert/delete, balanced BST
O(n)       → Single loop, HashSet contains, prefix sum
O(n log n) → Sorting, merge sort, all heap operations n times
O(n²)      → Nested loops, naive substring search
O(n³)      → Triple nested loops, Floyd-Warshall
O(2ⁿ)      → Subsets, exponential backtracking
O(n!)      → Permutations
```

---

## ⚠️ Common Interview Traps

1. **Sorted → Binary Search NOT Two Pointers** (unless finding pairs)
2. **Count subsets vs Enumerate subsets** — DP vs Backtracking
3. **Greedy seems right but isn't** — always try to construct a counterexample
4. **Negative numbers break sliding window** — use prefix sum instead
5. **Integer overflow** — use `long` when multiplying large numbers
6. **DFS vs BFS** — BFS for shortest, DFS for existence/connectivity
7. **0-indexed vs 1-indexed** — be careful with boundary conditions
8. **Empty array / null** — always guard first

---

**Next →** `../27_Practice_Plan/01_Staged_Practice_Plan.md`

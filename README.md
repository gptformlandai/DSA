# DSA Mastery — Beginner to MAANG-Ready

> Complete, modular DSA notes — beginner-friendly, interview-focused, Java code throughout.

---

## 📁 Folder Structure

```
DSA/
├── 00_Roadmap/
│   └── Master_Roadmap.md          ← Start here
│
├── 01_Foundations/
│   ├── 01_What_Is_DSA.md
│   ├── 02_Big_O_Complexity.md
│   └── 03_Problem_Solving_Framework.md
│
├── 02_Data_Structures/
│   ├── 01_Arrays.md
│   ├── 02_Strings.md
│   ├── 03_Linked_List.md
│   ├── 04_Stack.md
│   ├── 05_Queue_Deque.md
│   ├── 06_HashMap_HashSet.md
│   ├── 07_Trees_Binary_BST.md
│   ├── 08_Heap_PriorityQueue.md
│   └── 09_Balanced_Trees_And_Hashing_Internals.md  ← AVL/Red-Black, BST delete, open addressing, heapify proof
│
├── 03_Searching/
│   └── 01_Searching_Algorithms.md   ← Linear, Binary, Binary on Answer, all variants
│
├── 04_Sorting/
│   └── 01_Sorting_Algorithms.md     ← All sorts + comparison table
│
├── 05_Two_Pointers/
│   └── 01_Two_Pointers.md
│
├── 06_Sliding_Window/
│   └── 01_Sliding_Window.md
│
├── 07_Prefix_Sum/
│   └── 01_Prefix_Sum.md             ← Prefix sum, diff array, Kadane's, XOR
│
├── 08_Hashing/
│   └── 01_Hashing_Patterns.md
│
├── 09_Stack_Patterns/
│   └── 01_Stack_Patterns.md         ← Monotonic stack, NGE, histogram
│
├── 10_Queue_Deque/
│   └── 01_Queue_Deque_Patterns.md   ← BFS patterns, 0-1 BFS, LRU
│
├── 11_Linked_List/
│   └── 01_Linked_List_Patterns.md
│
├── 12_Recursion_Backtracking/
│   └── 01_Recursion_Backtracking.md ← Subsets, permutations, N-Queens, Sudoku
│
├── 13_Trees/
│   └── 01_Tree_Algorithms.md        ← All traversals, LCA, diameter, serialize
│
├── 14_Heap/
│   └── 01_Heap_Patterns.md          ← Top-K, k-way merge, median stream, scheduling
│
├── 15_Graphs/
│   └── 01_Graph_Algorithms.md       ← BFS, DFS, Dijkstra, Kruskal, Prim, Topo
│   └── 02_Advanced_Graph_Algorithms.md ← Bellman-Ford, Floyd-Warshall, 0-1 BFS, A*, SCC, bridges
│
├── 16_DSU/
│   └── 01_DSU.md                    ← Path compression, union by rank, patterns
│
├── 17_Greedy/
│   └── 01_Greedy_Algorithms.md
│
├── 18_Dynamic_Programming/
│   ├── 01_DP_Patterns.md            ← 1D, Knapsack, String DP, Grid DP, Interval DP
│   ├── 02_DP_Pattern_Masterclass.md ← Recursion-to-DP guide for the DPPatterns.pdf families
│   └── 03_Advanced_DP.md            ← Bitmask DP, Digit DP, Tree DP + rerooting, reconstruction
│
├── 19_Intervals/
│   └── 01_Intervals.md
│
├── 20_Bit_Manipulation/
│   └── 01_Bit_Manipulation.md
│
├── 21_Strings/
│   └── 01_String_Algorithms.md      ← KMP, Z-algo, Rabin-Karp, palindromes
│   └── 02_Advanced_String_Algorithms.md ← Z-algorithm, Manacher, rolling hash, Aho-Corasick, suffix array
│
├── 22_Trie/
│   └── 01_Trie_Algorithms.md
│
├── 23_Range_Query/
│   └── 01_Range_Query_Structures.md ← Segment Tree, Fenwick Tree, Sparse Table
│   └── 02_Advanced_Range_Query.md   ← Lazy propagation, range-update BIT, sqrt decomposition, Mo's
│
├── 24_Math/
│   └── 01_Math_Algorithms.md        ← GCD, Sieve, Fast Exponentiation, Combinatorics
│
├── 25_Problem_Solving/
│   └── 01_Problem_Solving_System.md ← The universal 10-step framework
│
├── 26_Cheatsheets/
│   └── 01_Pattern_Recognition.md    ← Quick lookup: "problem says X → use Y"
│
├── 27_Practice_Plan/
│   ├── 01_Staged_Practice_Plan.md   ← 15-stage plan with LeetCode problems
│   ├── 02_PDF_Crosscheck_Missing_Problems.md ← DSA.pdf missing-problem upgrade queue
│   ├── 03_Hot_Topic_Interview_Drills.md ← Timed drills for Recursion, Trees, Graphs, DP
│   └── 04_Behavioral_Communication_And_Mock_Rubric.md ← STAR, talk-tracks, mock scoring, spaced repetition
│
├── 28_MAANG_Hot_Problem_Solutions/
│   ├── 01_Hot_150_Index.md          ← Hot 150 tracker + Hot 200 extension queue
│   ├── 02_Hot_Topic_Solved_Walkthroughs.md ← Detailed interview-style solutions
│   ├── 03_Arrays_TwoPointers_BinarySearch_Solutions.md
│   ├── 04_Stack_Linked_Heap_Greedy_Solutions.md
│   ├── 05_Hot_Topic_Expansion_Solutions.md
│   ├── 05_Hot_Topic_Expansion_Solutions.md
│   └── 06_Remaining_Hot_150_Solutions.md
│   └── 07_Hot_150_Completion_Solutions.md  ← Final 41 cards → all 150 core problems solved
│
└── matrix/                          ← Matrix-specific notes
```

---

## 🚀 How to Start

**Complete beginner?**
→ `01_Foundations/01_What_Is_DSA.md`

**Already know basics, want patterns?**
→ `26_Cheatsheets/01_Pattern_Recognition.md`

**Want a structured practice plan?**
→ `27_Practice_Plan/01_Staged_Practice_Plan.md`

**Preparing for interviews next month?**
→ `25_Problem_Solving/01_Problem_Solving_System.md` + `27_Practice_Plan/` + `28_MAANG_Hot_Problem_Solutions/`

**Practicing communication & behavioral rounds?**
→ `27_Practice_Plan/04_Behavioral_Communication_And_Mock_Rubric.md`

---

## 📐 Every Note Follows This Structure

1. What problem does this solve?
2. Beginner-friendly intuition
3. Real-world analogy
4. Core concept + visual
5. Step-by-step algorithm
6. Dry run with example
7. Java implementation
8. Time & Space complexity
9. Edge cases & common mistakes
10. Practice problems (Easy / Medium / Hard)

---

## 🏆 Milestones

| Milestone | Target |
|-----------|--------|
| Stage 1-4 complete | Core patterns solid |
| 50 problems solved | Beginner → Intermediate |
| 100 problems solved | Intermediate level |
| 150 problems solved | Interview-ready |
| Hot 150 explained from memory | Strong MAANG signal |
| Stages 1-15 complete | MAANG-ready |

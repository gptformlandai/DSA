# Section 27 — Staged Practice Plan

> Follow this plan in order. Complete all problems per stage before moving on.

> PDF cross-check: after finishing each stage, use `02_PDF_Crosscheck_Missing_Problems.md` as the upgrade queue for problems from `DSA.pdf` that were not already present in the notes.

> Hot-topic drills: use `03_Hot_Topic_Interview_Drills.md` for Recursion, Trees/BST, Graphs/DSU, and DP timed practice.

---

## Stage 1: Absolute Basics (Week 1)

**Goal:** Understand what DSA is, Big O, and problem reading.

**Topics:**
- What is DSA? Why does it matter?
- Time complexity, Space complexity, Big O notation
- Best/Average/Worst case
- How to analyze loops and recursion

**Problems:**
1. Write a function to find max of array
2. Count even numbers in array
3. Print first n Fibonacci numbers (iterative)
4. Determine if a number is prime
5. Reverse an integer

**Mastery Checklist:**
- [ ] Can state Big O of any simple loop
- [ ] Understands n=10⁵ → O(n log n) or better
- [ ] Can explain time vs space tradeoff

---

## Stage 2: Arrays & Strings (Week 2)

**Topics:** Arrays, Strings, Two Pointers basics

**LeetCode Problems:**
| # | Problem | Level |
|---|---------|-------|
| 1 | Two Sum | Easy |
| 26 | Remove Duplicates from Sorted Array | Easy |
| 121 | Best Time to Buy and Sell Stock | Easy |
| 283 | Move Zeroes | Easy |
| 344 | Reverse String | Easy |
| 11 | Container With Most Water | Medium |
| 15 | 3Sum | Medium |
| 238 | Product of Array Except Self | Medium |

**Common Mistakes:**
- Off-by-one in array bounds
- Forgetting StringBuilder for string building
- Not handling empty arrays

---

## Stage 3: Searching & Sorting (Week 3)

**Topics:** Binary Search, Sorting algorithms, Binary Search on Answer

**Problems:**
| # | Problem | Level |
|---|---------|-------|
| 704 | Binary Search | Easy |
| 35 | Search Insert Position | Easy |
| 34 | Find First and Last Position | Medium |
| 33 | Search in Rotated Sorted Array | Medium |
| 875 | Koko Eating Bananas | Medium |
| 74 | Search a 2D Matrix | Medium |
| 4 | Median of Two Sorted Arrays | Hard |

**Common Mistakes:**
- `(lo+hi)/2` overflow → use `lo + (hi-lo)/2`
- Wrong condition: `lo <= hi` vs `lo < hi`
- Forgetting to check binary search on answer pattern

---

## Stage 4: Two Pointers & Sliding Window (Week 3-4)

**Problems:**
| # | Problem | Level |
|---|---------|-------|
| 125 | Valid Palindrome | Easy |
| 167 | Two Sum II | Easy |
| 643 | Maximum Average Subarray | Easy |
| 567 | Permutation in String | Medium |
| 3 | Longest Substring Without Repeating | Medium |
| 424 | Longest Repeating Character Replacement | Medium |
| 76 | Minimum Window Substring | Hard |
| 42 | Trapping Rain Water | Hard |

---

## Stage 5: Hashing & Prefix Sum (Week 4)

**Problems:**
| # | Problem | Level |
|---|---------|-------|
| 217 | Contains Duplicate | Easy |
| 1 | Two Sum | Easy |
| 525 | Contiguous Array | Medium |
| 560 | Subarray Sum Equals K | Medium |
| 49 | Group Anagrams | Medium |
| 128 | Longest Consecutive Sequence | Medium |
| 53 | Maximum Subarray (Kadane's) | Medium |

---

## Stage 6: Stack & Queue (Week 5)

**Problems:**
| # | Problem | Level |
|---|---------|-------|
| 20 | Valid Parentheses | Easy |
| 155 | Min Stack | Medium |
| 150 | Evaluate Reverse Polish Notation | Medium |
| 739 | Daily Temperatures | Medium |
| 496 | Next Greater Element I | Easy |
| 239 | Sliding Window Maximum | Hard |
| 84 | Largest Rectangle in Histogram | Hard |

---

## Stage 7: Recursion & Backtracking (Week 6)

**Problems:**
| # | Problem | Level |
|---|---------|-------|
| 78 | Subsets | Medium |
| 46 | Permutations | Medium |
| 39 | Combination Sum | Medium |
| 22 | Generate Parentheses | Medium |
| 17 | Letter Combinations of Phone Number | Medium |
| 79 | Word Search | Medium |
| 51 | N-Queens | Hard |
| 37 | Sudoku Solver | Hard |

---

## Stage 8: Linked List (Week 6-7)

**Problems:**
| # | Problem | Level |
|---|---------|-------|
| 206 | Reverse Linked List | Easy |
| 21 | Merge Two Sorted Lists | Easy |
| 141 | Linked List Cycle | Easy |
| 19 | Remove Nth Node From End | Medium |
| 143 | Reorder List | Medium |
| 2 | Add Two Numbers | Medium |
| 25 | Reverse Nodes in k-Group | Hard |
| 146 | LRU Cache | Medium |

---

## Stage 9: Trees (Week 7-8)

**Problems:**
| # | Problem | Level |
|---|---------|-------|
| 104 | Maximum Depth of Binary Tree | Easy |
| 226 | Invert Binary Tree | Easy |
| 572 | Subtree of Another Tree | Easy |
| 102 | Binary Tree Level Order Traversal | Medium |
| 230 | Kth Smallest in BST | Medium |
| 235 | LCA of BST | Medium |
| 105 | Construct from Preorder+Inorder | Medium |
| 124 | Binary Tree Maximum Path Sum | Hard |
| 297 | Serialize and Deserialize Binary Tree | Hard |

---

## Stage 10: Heaps (Week 8)

**Problems:**
| # | Problem | Level |
|---|---------|-------|
| 703 | Kth Largest in Stream | Easy |
| 215 | Kth Largest in Array | Medium |
| 347 | Top K Frequent Elements | Medium |
| 23 | Merge K Sorted Lists | Hard |
| 295 | Find Median from Data Stream | Hard |
| 621 | Task Scheduler | Medium |

---

## Stage 11: Graphs (Week 9-10)

**Problems:**
| # | Problem | Level |
|---|---------|-------|
| 200 | Number of Islands | Medium |
| 695 | Max Area of Island | Medium |
| 207 | Course Schedule | Medium |
| 210 | Course Schedule II | Medium |
| 417 | Pacific Atlantic Water Flow | Medium |
| 743 | Network Delay Time (Dijkstra) | Medium |
| 127 | Word Ladder | Hard |
| 269 | Alien Dictionary | Hard |

---

## Stage 12: Greedy (Week 10)

**Problems:**
| # | Problem | Level |
|---|---------|-------|
| 455 | Assign Cookies | Easy |
| 55 | Jump Game | Medium |
| 45 | Jump Game II | Medium |
| 56 | Merge Intervals | Medium |
| 435 | Non-overlapping Intervals | Medium |
| 452 | Minimum Arrows | Medium |
| 134 | Gas Station | Medium |
| 135 | Candy | Hard |

---

## Stage 13: Dynamic Programming (Week 11-12)

**Start with 1D, then 2D, then string DP, then knapsack.**

**Before solving:** read `../18_Dynamic_Programming/02_DP_Pattern_Masterclass.md` to learn the recursion -> memoization -> tabulation ladder and the five DP pattern families from `DPPatterns.pdf`.

**Problems:**
| # | Problem | Level |
|---|---------|-------|
| 70 | Climbing Stairs | Easy |
| 198 | House Robber | Medium |
| 322 | Coin Change | Medium |
| 300 | Longest Increasing Subsequence | Medium |
| 1143 | Longest Common Subsequence | Medium |
| 72 | Edit Distance | Hard |
| 312 | Burst Balloons | Hard |
| 115 | Distinct Subsequences | Hard |

---

## Stage 14: Advanced Topics (Week 12-13)

**Topics:** Trie, Bit Manipulation, Segment Tree, Range Queries, DSU

**Problems:**
| # | Problem | Level |
|---|---------|-------|
| 208 | Implement Trie | Medium |
| 421 | Maximum XOR of Two Numbers | Medium |
| 212 | Word Search II | Hard |
| 338 | Counting Bits | Easy |
| 136 | Single Number | Easy |
| 307 | Range Sum Query — Mutable | Medium |
| 547 | Number of Provinces (DSU) | Medium |
| 684 | Redundant Connection | Medium |

---

## Stage 15: Mock Interview Readiness (Week 13-15)

**Daily Routine:**
- Solve 2 medium problems timed (25 minutes each)
- Solve 1 hard problem (45 minutes)
- Do 2× mock interviews per week
- Revisit 2 problems you struggled with

**Mock Interview Format:**
1. Read problem (2 min)
2. Ask clarifying questions (2 min)
3. Explain brute force (3 min)
4. Optimize + explain (5 min)
5. Code (15 min)
6. Test edge cases + analyze complexity (5 min)
Total: 30-35 minutes

---

## Revision Method

After completing each stage:
1. Re-solve 3 problems from that stage without looking at notes.
2. Can you do it faster this time?
3. Write the key insight in your own words.
4. Create a test case that would break a naive solution.

---

## 150-Problem Milestone Checklist

By the time you've done 150 problems across stages:
- [ ] 30 Easy
- [ ] 90 Medium
- [ ] 30 Hard
- [ ] Coverage: Arrays, Binary Search, Two Pointers, Sliding Window, HashMap, Stack, Queue, Recursion, Backtracking, Trees, Graphs, DP, Greedy
- [ ] Track completion in `../28_MAANG_Hot_Problem_Solutions/01_Hot_150_Index.md`
- [ ] Study detailed hot-topic writeups in `../28_MAANG_Hot_Problem_Solutions/02_Hot_Topic_Solved_Walkthroughs.md`
- [ ] 10+ Mock interviews done

---

## Resources for Practice

- LeetCode — primary platform (filter by topic and difficulty)
- `../28_MAANG_Hot_Problem_Solutions/` — repo-native Hot 150/200 tracker and detailed solutions
- NeetCode.io — curated 150-problem roadmap with video solutions
- AlgoExpert — structured video lessons
- Codeforces / AtCoder — competitive programming (after Stage 13)

# Section 6 — Prefix Sum Patterns Deep Dive (MAANG L5 Coverage)

---

# INDEX — Quick Navigation

## Core Concepts
| Section | Description |
|---------|-------------|
| [The "One Sentence"](#the-one-sentence-that-unlocks-all-prefix-sum-problems) | Unlocks all prefix sum problems |
| [Zero to Hero](#-zero-to-hero-understanding-prefix-sums-from-scratch) | **START HERE if confused!** |
| [The 3 Prefix Sum Techniques](#the-3-prefix-sum-techniques-your-weapons) | Your weapons |
| [Junior Dev Cheat Card](#-the-junior-dev-cheat-card-memorize-this) | Memorizable summary |
| [Quick Start](#-quick-start-the-60-second-prefix-sum-approach) | 60-second approach |
| [Master Decision Tree](#the-master-decision-tree) | Which technique to use |

---

## Foundational Patterns (Patterns 0-3)
| # | Pattern | LeetCode |
|---|---------|----------|
| 0 | [Range Sum Query - Immutable](#pattern-0-range-sum-query---immutable-leetcode-303) | 303 |
| 1 | [Subarray Sum Equals K](#pattern-1-subarray-sum-equals-k-leetcode-560) | 560 |
| 2 | [Continuous Subarray Sum](#pattern-2-continuous-subarray-sum-leetcode-523) | 523 |
| 3 | [Subarray Sums Divisible by K](#pattern-3-subarray-sums-divisible-by-k-leetcode-974) | 974 |

## Length-Based Patterns (Patterns 4-5)
| # | Pattern | LeetCode |
|---|---------|----------|
| 4 | [Longest Subarray With Sum Divisible by K](#pattern-4-longest-subarray-with-sum-divisible-by-k) | - |
| 5 | [Smallest Subarray With Sum Divisible by K](#pattern-5-smallest-subarray-with-sum-divisible-by-k) | - |

## Counting Patterns (Patterns 6-8)
| # | Pattern | LeetCode |
|---|---------|----------|
| 6 | [Count Subarrays With Equal 0s, 1s, and 2s](#pattern-6-count-subarrays-with-equal-0s-1s-and-2s) | - |
| 7 | [Binary Subarrays With Sum](#pattern-7-binary-subarrays-with-sum-leetcode-930) | 930 |
| 8 | [Count Number of Nice Subarrays](#pattern-8-count-number-of-nice-subarrays-leetcode-1248) | 1248 |

## Advanced Patterns (Patterns 9-11)
| # | Pattern | LeetCode |
|---|---------|----------|
| 9 | [Subarray Sums in Circular Array](#pattern-9-subarray-sums-in-circular-array) | - |
| 10 | [Range Sum Query 2D - Immutable](#pattern-10-range-sum-query-2d---immutable-leetcode-304) | 304 |
| 11 | [Number of Submatrices That Sum to Target](#pattern-11-number-of-submatrices-that-sum-to-target-leetcode-1074) | 1074 |

## MAANG Favorites (Patterns 12-15)
| # | Pattern | LeetCode |
|---|---------|----------|
| 12 | [Product of Array Except Self](#pattern-12-product-of-array-except-self-leetcode-238) | 238 |
| 13 | [Maximum Size Subarray Sum Equals K](#pattern-13-maximum-size-subarray-sum-equals-k-leetcode-325) | 325 |
| 14 | [Contiguous Array](#pattern-14-contiguous-array-leetcode-525) | 525 |
| 15 | [Find Pivot Index](#pattern-15-find-pivot-index-leetcode-724) | 724 |

## Reference Sections
| Section |
|---------|
| [MAANG Coverage Map](#maang-coverage-map) |
| [Prefix Sum Cheat Sheet](#prefix-sum-cheat-sheet) |
| [Mastery Checklist](#mastery-checklist) |

---

# The "One Sentence That Unlocks All Prefix Sum Problems"

> **"A prefix sum lets you compute ANY subarray sum in O(1) — store cumulative sums, then subtract to get any range."**

That's the entire subject. Every prefix sum problem is just:
1. **RANGE QUERY** — answer "what's the sum from index i to j?" instantly
2. **SUBARRAY = K** — find subarrays with a target sum using HashMap
3. **DIVISIBILITY** — find subarrays divisible by K using modulo trick
4. **TRANSFORMATION** — convert the problem (0→-1, count→sum) then apply prefix sum

---

# 🌟 ZERO TO HERO: Understanding Prefix Sums From Scratch

**If you're a junior dev and prefix sums feel confusing, START HERE.**

---

## What IS a Prefix Sum? (The Real-World Analogy)

Imagine you're tracking your **bank account balance** over time:

```
Day:        1     2     3     4     5
Deposit:   $100  $50   $30   $70   $20
Balance:   $100  $150  $180  $250  $270
            ↑     ↑     ↑     ↑     ↑
         prefix prefix prefix prefix prefix
         sum[0] sum[1] sum[2] sum[3] sum[4]
```

**The Balance IS the Prefix Sum!**
- Balance on Day 3 ($180) = sum of ALL deposits from Day 1 to Day 3
- Balance on Day 5 ($270) = sum of ALL deposits from Day 1 to Day 5

**The Magic Question:** "How much did I deposit between Day 2 and Day 4?"

```
Answer = Balance[Day 4] - Balance[Day 1]
       = $250 - $100
       = $150

Check: $50 + $30 + $70 = $150 ✓
```

**You didn't need to add up each day — just ONE subtraction!**

---

## Why Do We Need Prefix Sums?

**Because adding up subarrays repeatedly is SLOW.**

### The Problem: Range Sum Queries

```
Array: [3, 1, 4, 1, 5, 9, 2, 6]

Query 1: Sum from index 2 to 5?  → 4 + 1 + 5 + 9 = 19
Query 2: Sum from index 0 to 3?  → 3 + 1 + 4 + 1 = 9
Query 3: Sum from index 4 to 7?  → 5 + 9 + 2 + 6 = 22
... 10,000 more queries ...
```

**Brute Force:** Each query takes O(n) → 10,000 queries = O(10,000 × n) = TOO SLOW!

**With Prefix Sum:** Each query takes O(1) → 10,000 queries = O(10,000) = FAST!

---

## The 2 Key Formulas (Memorize These!)

### Formula 1: Building the Prefix Sum Array

```
prefix[i] = sum of elements from index 0 to i

prefix[0] = nums[0]
prefix[i] = prefix[i-1] + nums[i]  (for i > 0)
```

**Example:**
```
nums:   [3,  1,  4,  1,  5]
         ↓   ↓   ↓   ↓   ↓
prefix: [3,  4,  8,  9, 14]
         │   │   │   │   │
         │   │   │   │   └── 3+1+4+1+5 = 14
         │   │   │   └────── 3+1+4+1 = 9
         │   │   └────────── 3+1+4 = 8
         │   └────────────── 3+1 = 4
         └────────────────── 3
```

### Formula 2: Getting Any Range Sum

```
sum(i, j) = prefix[j] - prefix[i-1]

Special case: if i == 0, sum(0, j) = prefix[j]
```

**Example:**
```
nums:   [3, 1, 4, 1, 5]
prefix: [3, 4, 8, 9, 14]

sum(2, 4) = prefix[4] - prefix[1]
          = 14 - 4
          = 10

Check: nums[2] + nums[3] + nums[4] = 4 + 1 + 5 = 10 ✓
```

---

## The HashMap Trick (The REAL Power of Prefix Sum)

**The Problem:** Find subarrays that sum to K.

**The Insight:**
```
If prefix[j] - prefix[i] = K
Then prefix[i] = prefix[j] - K

As we scan, store prefix sums in HashMap.
For each new prefix, check if (prefix - K) exists!
```

**Visual:**
```
nums = [1, 2, 3], K = 3

Index:    0    1    2
nums:    [1]  [2]  [3]
prefix:   1    3    6

At index 1: prefix = 3
            Looking for prefix - K = 3 - 3 = 0
            0 exists (empty prefix)! Found subarray [1,2]!

At index 2: prefix = 6
            Looking for prefix - K = 6 - 3 = 3
            3 exists (at index 1)! Found subarray [3]!
```

---

## The Modulo Trick (For Divisibility Problems)

**The Problem:** Find subarrays with sum divisible by K.

**The Insight:**
```
If (prefix[j] - prefix[i]) % K == 0
Then prefix[j] % K == prefix[i] % K

Store REMAINDERS in HashMap, not actual sums!
```

**Visual:**
```
nums = [4, 5, 0, -2, -3, 1], K = 5

Index:    0    1    2    3    4    5
nums:    [4]  [5]  [0] [-2] [-3]  [1]
prefix:   4    9    9    7    4    5
mod 5:    4    4    4    2    4    0

Same remainder at indices 0, 1, 2, 4 (all have remainder 4)
Any pair forms a subarray divisible by 5!
- (0,1): sum = 5, divisible ✓
- (0,2): sum = 5, divisible ✓
- (1,2): sum = 0, divisible ✓
- etc.
```

---

## The 0/1 Transformation Trick

**The Problem:** Find subarrays with equal 0s and 1s.

**The Insight:**
```
Transform: 0 → -1, 1 → 1

Now "equal 0s and 1s" becomes "sum = 0"!

Original: [0, 1, 0, 1, 1, 0]
Transform:[-1, 1,-1, 1, 1,-1]

Subarray [0,1,0,1] has two 0s and two 1s
After transform: [-1,1,-1,1] sums to 0!
```

This transforms a counting problem into a sum problem!

---

# 📋 THE JUNIOR DEV CHEAT CARD (Memorize This!)

```
╔═══════════════════════════════════════════════════════════════════════╗
║                   PREFIX SUM PROBLEM? USE THIS!                        ║
╠═══════════════════════════════════════════════════════════════════════╣
║                                                                        ║
║  STEP 1: "What type of problem is this?"                              ║
║                                                                        ║
║  ┌────────────────────────────────────────────────────────────────┐   ║
║  │ "Range sum query"          → BASIC PREFIX SUM                  │   ║
║  │ "Subarray sum equals K"    → PREFIX SUM + HASHMAP              │   ║
║  │ "Divisible by K"           → PREFIX SUM + MODULO + HASHMAP     │   ║
║  │ "Equal 0s and 1s"          → TRANSFORM (0→-1) + PREFIX SUM     │   ║
║  │ "2D range sum"             → 2D PREFIX SUM                     │   ║
║  └────────────────────────────────────────────────────────────────┘   ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  THE 3 CORE FORMULAS:                                                  ║
║                                                                        ║
║  ┌──────────────────────────────────────────────────────────────────┐ ║
║  │ 1. BUILD:    prefix[i] = prefix[i-1] + nums[i]                   │ ║
║  │                                                                  │ ║
║  │ 2. QUERY:    sum(i,j) = prefix[j] - prefix[i-1]                  │ ║
║  │                                                                  │ ║
║  │ 3. HASHMAP:  if prefix[j] - prefix[i] = K                        │ ║
║  │              then look for (prefix - K) in map                   │ ║
║  └──────────────────────────────────────────────────────────────────┘ ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  THE MODULO TRICK (for divisibility):                                  ║
║                                                                        ║
║  ┌──────────────────────────────────────────────────────────────────┐ ║
║  │ If (prefix[j] - prefix[i]) % K == 0                              │ ║
║  │ Then prefix[j] % K == prefix[i] % K                              │ ║
║  │                                                                  │ ║
║  │ Store REMAINDERS in HashMap!                                     │ ║
║  │ Same remainder = subarray divisible by K                         │ ║
║  └──────────────────────────────────────────────────────────────────┘ ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  CRITICAL INITIALIZATION:                                              ║
║                                                                        ║
║  ┌──────────────────────────────────────────────────────────────────┐ ║
║  │ ALWAYS: map.put(0, 1)  // or map.put(0, -1) for index tracking   │ ║
║  │                                                                  │ ║
║  │ WHY? Handles subarrays starting from index 0!                    │ ║
║  │                                                                  │ ║
║  │ For COUNTING: map.put(0, 1)  → "empty prefix exists once"        │ ║
║  │ For LENGTH:   map.put(0, -1) → "empty prefix at index -1"        │ ║
║  └──────────────────────────────────────────────────────────────────┘ ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  JAVA CODE TEMPLATE:                                                   ║
║                                                                        ║
║  // For counting subarrays with sum = K                               ║
║  Map<Integer, Integer> map = new HashMap<>();                         ║
║  map.put(0, 1);  // Empty prefix                                      ║
║  int prefix = 0, count = 0;                                           ║
║                                                                        ║
║  for (int num : nums) {                                               ║
║      prefix += num;                                                   ║
║      count += map.getOrDefault(prefix - k, 0);                        ║
║      map.put(prefix, map.getOrDefault(prefix, 0) + 1);                ║
║  }                                                                     ║
║                                                                        ║
╚═══════════════════════════════════════════════════════════════════════╝
```

---

# 🚀 QUICK START: The 60-Second Prefix Sum Approach

### The ONE Question That Solves Most Problems:

> **"Can I express this as finding subarrays with a specific sum (or sum property)?"**

If YES → Use prefix sum!

### The Simple Decision Tree:

```
1. "Multiple range sum queries on static array?"
   → Build prefix array once, answer each query in O(1)
   → sum(i,j) = prefix[j] - prefix[i-1]

2. "Count/find subarrays with sum = K?"
   → Prefix Sum + HashMap
   → Store prefix sums, look for (prefix - K)
   → Initialize map.put(0, 1)

3. "Subarrays divisible by K?"
   → Prefix Sum + Modulo + HashMap
   → Store remainders, same remainder = divisible
   → Handle negative remainders: ((prefix % K) + K) % K

4. "Equal count of two things (0s and 1s)?"
   → Transform one to -1, problem becomes sum = 0
   → Then use Prefix Sum + HashMap

5. "2D range sum queries?"
   → 2D Prefix Sum with inclusion-exclusion
   → sum = prefix[r2][c2] - prefix[r1-1][c2] - prefix[r2][c1-1] + prefix[r1-1][c1-1]
```

---

# The Master Decision Tree

```
                        ┌─────────────────────────────┐
                        │   PREFIX SUM PROBLEM?       │
                        │   "subarray", "range sum"   │
                        └─────────────┬───────────────┘
                                      │
                    ┌─────────────────┼─────────────────┐
                    │                 │                 │
                    ▼                 ▼                 ▼
            ┌───────────┐     ┌───────────┐     ┌───────────┐
            │  STATIC   │     │  DYNAMIC  │     │    2D     │
            │  QUERIES  │     │  (single  │     │  MATRIX   │
            │           │     │   pass)   │     │           │
            └─────┬─────┘     └─────┬─────┘     └─────┬─────┘
                  │                 │                 │
                  ▼                 │                 ▼
         ┌────────────────┐        │        ┌────────────────┐
         │ Basic Prefix   │        │        │ 2D Prefix Sum  │
         │ sum(i,j) =     │        │        │ Inclusion-     │
         │ pre[j]-pre[i-1]│        │        │ Exclusion      │
         └────────────────┘        │        └────────────────┘
                                   │
                    ┌──────────────┼──────────────┐
                    │              │              │
                    ▼              ▼              ▼
            ┌───────────┐  ┌───────────┐  ┌───────────┐
            │  SUM = K  │  │ DIVISIBLE │  │  EQUAL    │
            │           │  │   BY K    │  │  COUNTS   │
            └─────┬─────┘  └─────┬─────┘  └─────┬─────┘
                  │              │              │
                  ▼              ▼              ▼
         ┌────────────────┐ ┌────────────────┐ ┌────────────────┐
         │ HashMap stores │ │ HashMap stores │ │ Transform then │
         │ prefix sums    │ │ remainders     │ │ use sum = 0    │
         │ Look for       │ │ (pre % K)      │ │                │
         │ (prefix - K)   │ │ Same remainder │ │ 0→-1, 1→1      │
         └────────────────┘ │ = divisible    │ └────────────────┘
                            └────────────────┘
```

---

# The 3 Prefix Sum Techniques (Your "Weapons")

## Technique 1: BASIC PREFIX SUM (Range Queries)

**Purpose:** Answer multiple range sum queries in O(1) each after O(n) preprocessing.

**When to Use:**
- "Given an array, answer Q queries for sum from index i to j"
- Static array (no updates)
- Multiple queries on same array

**The Formula:**
```
Build:  prefix[i] = prefix[i-1] + nums[i]
Query:  sum(i, j) = prefix[j] - prefix[i-1]
        sum(0, j) = prefix[j]  (special case)
```

**Code Template:**
```java
// Build prefix array
int[] prefix = new int[n];
prefix[0] = nums[0];
for (int i = 1; i < n; i++) {
    prefix[i] = prefix[i-1] + nums[i];
}

// Answer query: sum from index i to j
int rangeSum(int i, int j) {
    if (i == 0) return prefix[j];
    return prefix[j] - prefix[i-1];
}
```

**Why It Works:**
```
nums:   [3, 1, 4, 1, 5]
prefix: [3, 4, 8, 9, 14]

sum(2, 4) = "sum of elements 2,3,4"
          = "sum of elements 0,1,2,3,4" - "sum of elements 0,1"
          = prefix[4] - prefix[1]
          = 14 - 4 = 10 ✓
```

---

## Technique 2: PREFIX SUM + HASHMAP (Subarray Sum = K)

**Purpose:** Count or find subarrays with a specific sum in O(n).

**When to Use:**
- "Count subarrays with sum = K"
- "Find subarray with sum = K"
- "Longest/shortest subarray with sum = K"

**The Key Insight:**
```
If prefix[j] - prefix[i] = K
Then prefix[i] = prefix[j] - K

For each prefix sum, check if (prefix - K) was seen before!
```

**Code Template (Counting):**
```java
int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);  // Empty prefix exists once
    
    int prefix = 0, count = 0;
    
    for (int num : nums) {
        prefix += num;
        
        // If (prefix - k) exists, we found subarrays!
        count += map.getOrDefault(prefix - k, 0);
        
        // Record this prefix sum
        map.put(prefix, map.getOrDefault(prefix, 0) + 1);
    }
    
    return count;
}
```

**Code Template (Longest Length):**
```java
int maxSubArrayLen(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, -1);  // Empty prefix at index -1
    
    int prefix = 0, maxLen = 0;
    
    for (int i = 0; i < nums.length; i++) {
        prefix += nums[i];
        
        if (map.containsKey(prefix - k)) {
            maxLen = Math.max(maxLen, i - map.get(prefix - k));
        }
        
        // Only store FIRST occurrence (for longest)
        map.putIfAbsent(prefix, i);
    }
    
    return maxLen;
}
```

**Why It Works (Visual Proof):**
```
nums = [1, 2, 3], k = 3

        index:  -1    0    1    2
        nums:    -   [1]  [2]  [3]
        prefix:  0    1    3    6
                 ↑         ↑
                 │         │
                 └─────────┘
                 prefix[1] - prefix[-1] = 3 - 0 = 3 = k ✓
                 Subarray from index 0 to 1: [1, 2]

        prefix:  0    1    3    6
                           ↑    ↑
                           │    │
                           └────┘
                 prefix[2] - prefix[1] = 6 - 3 = 3 = k ✓
                 Subarray from index 2 to 2: [3]
```

---

## Technique 3: PREFIX SUM + MODULO (Divisibility)

**Purpose:** Find subarrays with sum divisible by K in O(n).

**When to Use:**
- "Subarrays with sum divisible by K"
- "Count subarrays divisible by K"
- "Longest subarray divisible by K"

**The Key Insight:**
```
If (prefix[j] - prefix[i]) % K == 0
Then prefix[j] % K == prefix[i] % K

Store REMAINDERS in HashMap!
Same remainder means the difference is divisible by K.
```

**Code Template:**
```java
int subarraysDivByK(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);  // Empty prefix has remainder 0
    
    int prefix = 0, count = 0;
    
    for (int num : nums) {
        prefix += num;
        
        // Handle negative remainders!
        int remainder = ((prefix % k) + k) % k;
        
        // Same remainder = divisible subarray
        count += map.getOrDefault(remainder, 0);
        
        map.put(remainder, map.getOrDefault(remainder, 0) + 1);
    }
    
    return count;
}
```

**Why Handle Negative Remainders?**
```
In Java/C++: -7 % 5 = -2  (NOT 3!)
We want:     -7 % 5 = 3   (positive remainder)

Fix: ((prefix % k) + k) % k

Example: ((-7 % 5) + 5) % 5 = (-2 + 5) % 5 = 3 ✓
```

**Why It Works (Visual Proof):**
```
nums = [4, 5, 0, -2, -3, 1], k = 5

index:     -1    0    1    2    3    4    5
nums:       -   [4]  [5]  [0] [-2] [-3]  [1]
prefix:     0    4    9    9    7    4    5
remainder:  0    4    4    4    2    4    0
            ↑              ↑
            │              │
            └──────────────┘
            Same remainder (4)!
            prefix[2] - prefix[-1] = 9 - 0 = 9
            9 % 5 = 4... wait, that's not 0!
            
Actually: indices with same remainder form pairs.
          remainder 4 appears at: -1(as 0), 0, 1, 2, 4
          
Wait, let me recalculate:
index -1: prefix = 0, remainder = 0
index 0:  prefix = 4, remainder = 4
index 1:  prefix = 9, remainder = 4
index 2:  prefix = 9, remainder = 4
index 3:  prefix = 7, remainder = 2
index 4:  prefix = 4, remainder = 4
index 5:  prefix = 5, remainder = 0

Pairs with same remainder:
- remainder 0: indices -1, 5 → subarray [0,5] sum = 5, divisible ✓
- remainder 4: indices 0,1,2,4 → C(4,2) = 6 pairs
  - (0,1): sum = 5, divisible ✓
  - (0,2): sum = 5, divisible ✓
  - (1,2): sum = 0, divisible ✓
  - etc.
```

---

---

# PATTERN 0: Range Sum Query - Immutable (LeetCode 303)

## Pattern Recognition Signal

**When you see:** "multiple range sum queries", "sum from index i to j", "immutable array"

**Instant thought:** "Basic Prefix Sum! Build once, query O(1)"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Given an array, answer multiple queries:
"What is the sum of elements from index i to index j?"

Example:
nums = [-2, 0, 3, -5, 2, -1]

Query: sumRange(0, 2) → -2 + 0 + 3 = 1
Query: sumRange(2, 5) → 3 + (-5) + 2 + (-1) = -1
Query: sumRange(0, 5) → -2 + 0 + 3 + (-5) + 2 + (-1) = -3
```

### Why Prefix Sum?

```
BRUTE FORCE: For each query, loop from i to j and sum → O(n) per query
             With Q queries → O(Q × n) total

SMART WAY: Build prefix sum array once → O(n)
           Answer each query in O(1)
           With Q queries → O(n + Q) total

For 10,000 queries on array of 10,000 elements:
- Brute force: 100,000,000 operations
- Prefix sum:  20,000 operations (5000x faster!)
```

### The Algorithm in Plain English

```
1. Build prefix array where prefix[i] = sum of nums[0..i]
2. For query (i, j):
   - If i == 0: return prefix[j]
   - Else: return prefix[j] - prefix[i-1]
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [-2, 0, 3, -5, 2, -1]`

```
═══════════════════════════════════════════════════════════

STEP 1: Build Prefix Sum Array

nums:     [-2,  0,  3, -5,  2, -1]
index:      0   1   2   3   4   5

prefix[0] = nums[0] = -2
prefix[1] = prefix[0] + nums[1] = -2 + 0 = -2
prefix[2] = prefix[1] + nums[2] = -2 + 3 = 1
prefix[3] = prefix[2] + nums[3] = 1 + (-5) = -4
prefix[4] = prefix[3] + nums[4] = -4 + 2 = -2
prefix[5] = prefix[4] + nums[5] = -2 + (-1) = -3

prefix:   [-2, -2,  1, -4, -2, -3]

═══════════════════════════════════════════════════════════

STEP 2: Answer Queries

Query: sumRange(0, 2)
  i = 0, so return prefix[2] = 1 ✓
  (Check: -2 + 0 + 3 = 1 ✓)

═══════════════════════════════════════════════════════════

Query: sumRange(2, 5)
  i ≠ 0, so return prefix[5] - prefix[1]
  = -3 - (-2) = -1 ✓
  (Check: 3 + (-5) + 2 + (-1) = -1 ✓)

═══════════════════════════════════════════════════════════

Query: sumRange(0, 5)
  i = 0, so return prefix[5] = -3 ✓
  (Check: -2 + 0 + 3 + (-5) + 2 + (-1) = -3 ✓)

═══════════════════════════════════════════════════════════
```

---

## The Code (With Line-by-Line Explanation)

```java
class NumArray {
    private int[] prefix;
    
    // Constructor: Build prefix sum array
    public NumArray(int[] nums) {
        int n = nums.length;
        prefix = new int[n];
        
        prefix[0] = nums[0];  // First element is itself
        
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i-1] + nums[i];  // Cumulative sum
        }
    }
    
    // Query: Return sum from index left to right
    public int sumRange(int left, int right) {
        if (left == 0) {
            return prefix[right];  // Sum from start
        }
        return prefix[right] - prefix[left-1];  // Subtract prefix before left
    }
}
```

**Alternative with padding (cleaner query logic):**

```java
class NumArray {
    private int[] prefix;
    
    public NumArray(int[] nums) {
        int n = nums.length;
        prefix = new int[n + 1];  // Extra element for padding
        
        prefix[0] = 0;  // Padding: sum of zero elements
        
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
    }
    
    public int sumRange(int left, int right) {
        // No special case needed!
        return prefix[right + 1] - prefix[left];
    }
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Off-by-one in query | `prefix[j] - prefix[i]` misses element at i | Use `prefix[j] - prefix[i-1]` or padding |
| Not handling i=0 | `prefix[-1]` is out of bounds | Check `if (i == 0)` or use padding |
| Integer overflow | Large sums exceed int range | Use `long[]` for prefix array |

---

## Mind-Map Anchor

```
RANGE SUM QUERY
       │
       ▼
┌─────────────────────────┐
│ Build: O(n) once        │
│ Query: O(1) each        │
│                         │
│ prefix[i] = sum[0..i]   │
│ sum(i,j) = pre[j]-pre[i-1]│
└─────────────────────────┘
```

**Memory phrase:** "Build once, query forever — subtract prefixes for any range"

---

## Complexity Analysis

| Operation | Time | Space |
|-----------|------|-------|
| Constructor | O(n) | O(n) |
| sumRange | O(1) | O(1) |

---

# PATTERN 1: Subarray Sum Equals K (LeetCode 560)

## Pattern Recognition Signal

**When you see:** "count subarrays", "subarray sum equals K", "number of subarrays"

**Instant thought:** "Prefix Sum + HashMap! Store prefix sums, look for (prefix - K)"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [1, 1, 1], k = 2

Find ALL contiguous subarrays that sum to exactly 2:
- [1, 1] at indices (0,1) sums to 2 ✓
- [1, 1] at indices (1,2) sums to 2 ✓

Answer: 2
```

### Why Prefix Sum + HashMap?

```
BRUTE FORCE: Check all O(n²) subarrays → Too slow!

SMART WAY: 
If prefix[j] - prefix[i] = K
Then prefix[i] = prefix[j] - K

As we compute prefix sums, store them in HashMap.
For each new prefix sum, check if (prefix - K) exists!

This finds all valid subarrays in ONE PASS!
```

### The Key Insight (Why This Works)

```
prefix[j] = sum of elements from index 0 to j
prefix[i] = sum of elements from index 0 to i

prefix[j] - prefix[i] = sum of elements from index (i+1) to j

If this equals K, we found a subarray!

So for each j, we ask: "Is there an i where prefix[i] = prefix[j] - K?"
HashMap answers this in O(1)!
```

### The Algorithm in Plain English

```
1. Initialize: prefix = 0, count = 0, map = {0: 1}
   (The {0: 1} handles subarrays starting at index 0)
   
2. For each number:
   a. Add to running prefix sum
   b. Check if (prefix - K) is in map → add its count to result
   c. Add current prefix to map (increment its count)
   
3. Return count
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [1, 2, 3]`, `k = 3`

```
═══════════════════════════════════════════════════════════

Initial State:
  prefix = 0
  count = 0
  map = {0: 1}  ← "Empty prefix (sum=0) exists once"

═══════════════════════════════════════════════════════════

Process nums[0] = 1:

  Step 1: Update prefix
    prefix = 0 + 1 = 1
  
  Step 2: Look for (prefix - k) = 1 - 3 = -2 in map
    -2 NOT in map → count stays 0
    "No subarray ending here sums to 3"
  
  Step 3: Add prefix to map
    map = {0: 1, 1: 1}

  State: prefix=1, count=0, map={0:1, 1:1}

═══════════════════════════════════════════════════════════

Process nums[1] = 2:

  Step 1: Update prefix
    prefix = 1 + 2 = 3
  
  Step 2: Look for (prefix - k) = 3 - 3 = 0 in map
    0 IS in map with count 1!
    count += 1 → count = 1
    "Found subarray! prefix[1] - prefix[-1] = 3 - 0 = 3"
    "This is subarray [1, 2] (indices 0 to 1)"
  
  Step 3: Add prefix to map
    map = {0: 1, 1: 1, 3: 1}

  State: prefix=3, count=1, map={0:1, 1:1, 3:1}

═══════════════════════════════════════════════════════════

Process nums[2] = 3:

  Step 1: Update prefix
    prefix = 3 + 3 = 6
  
  Step 2: Look for (prefix - k) = 6 - 3 = 3 in map
    3 IS in map with count 1!
    count += 1 → count = 2
    "Found subarray! prefix[2] - prefix[1] = 6 - 3 = 3"
    "This is subarray [3] (index 2 only)"
  
  Step 3: Add prefix to map
    map = {0: 1, 1: 1, 3: 1, 6: 1}

  State: prefix=6, count=2, map={0:1, 1:1, 3:1, 6:1}

═══════════════════════════════════════════════════════════

Final Answer: count = 2 ✓

Subarrays found:
1. [1, 2] → sum = 3 ✓
2. [3]    → sum = 3 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
public int subarraySum(int[] nums, int k) {
    // HashMap: prefix_sum → count of times we've seen it
    Map<Integer, Integer> prefixCount = new HashMap<>();
    
    // CRITICAL: Empty prefix (sum = 0) exists once
    // This handles subarrays starting from index 0
    prefixCount.put(0, 1);
    
    int prefix = 0;  // Running prefix sum
    int count = 0;   // Count of valid subarrays
    
    for (int num : nums) {
        // Step 1: Update running prefix sum
        prefix += num;
        
        // Step 2: How many times have we seen (prefix - k)?
        // Each occurrence represents a valid subarray ending here
        count += prefixCount.getOrDefault(prefix - k, 0);
        
        // Step 3: Record this prefix sum
        prefixCount.put(prefix, prefixCount.getOrDefault(prefix, 0) + 1);
    }
    
    return count;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Forgetting `map.put(0, 1)` | Miss subarrays starting at index 0 | Always initialize with `{0: 1}` |
| Adding to map BEFORE checking | Count same prefix twice, wrong answer | Check FIRST, then add to map |
| Using `map.get()` without default | NullPointerException if key missing | Use `getOrDefault(key, 0)` |
| Assuming positive numbers only | Algorithm works for negatives too! | No change needed, it just works |

---

## Mind-Map Anchor

```
SUBARRAY SUM = K
       │
       ▼
┌─────────────────────────┐
│ Prefix Sum + HashMap    │
│                         │
│ prefix[j] - prefix[i] = K│
│ ⟹ Look for (prefix - K) │
│                         │
│ Initialize: {0: 1}      │
│ Check THEN add to map   │
└─────────────────────────┘
```

**Memory phrase:** "Running prefix, look for (prefix - K) in map, init with {0:1}"

---

## Complexity Analysis

| Metric | Value | Why |
|--------|-------|-----|
| Time | O(n) | Single pass through array |
| Space | O(n) | HashMap stores up to n prefix sums |

---

# PATTERN 2: Continuous Subarray Sum (LeetCode 523)

## Pattern Recognition Signal

**When you see:** "subarray sum divisible by K", "multiple of K", "continuous subarray", "length at least 2"

**Instant thought:** "Prefix Sum + Modulo! Same remainder = divisible difference"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [23, 2, 4, 6, 7], k = 6

Find if there's a subarray of length >= 2 whose sum is divisible by 6:
- [2, 4] sums to 6, divisible by 6 ✓
- [23, 2, 4, 6, 7] sums to 42, divisible by 6 ✓

Answer: true (we found at least one)
```

### Why Prefix Sum + Modulo?

```
KEY MATH INSIGHT:

If (prefix[j] - prefix[i]) % K == 0
Then prefix[j] % K == prefix[i] % K

Proof:
  Let prefix[j] = a*K + r₁  (r₁ is remainder)
  Let prefix[i] = b*K + r₂  (r₂ is remainder)
  
  prefix[j] - prefix[i] = (a-b)*K + (r₁ - r₂)
  
  For this to be divisible by K:
  (r₁ - r₂) must be divisible by K
  
  Since 0 ≤ r₁, r₂ < K, the only way is r₁ = r₂!

CONCLUSION: Store remainders in HashMap.
            Same remainder = subarray divisible by K!
```

### The Algorithm in Plain English

```
1. Initialize: prefix = 0, map = {0: -1}
   (Remainder 0 at "index -1" for subarrays starting at 0)
   
2. For each index i:
   a. Add nums[i] to prefix
   b. Compute remainder = prefix % K
   c. If remainder seen before at index j:
      - If i - j >= 2: return true (found valid subarray!)
   d. If remainder NOT seen before:
      - Store it with current index (first occurrence only!)
   
3. Return false (no valid subarray found)
```

### Why Store First Occurrence Only?

```
We want LONGEST possible subarray (to maximize chance of length >= 2).
First occurrence gives the earliest starting point.

Example: remainders at indices [0, 3, 5] are all the same
- Using index 0: subarrays ending at 3 or 5 have length 3+ and 5+
- Using index 3: subarray ending at 5 has length only 2

First occurrence maximizes length!
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [23, 2, 4, 6, 7]`, `k = 6`

```
═══════════════════════════════════════════════════════════

Initial State:
  prefix = 0
  map = {0: -1}  ← "Remainder 0 seen at index -1"

═══════════════════════════════════════════════════════════

Process index 0, nums[0] = 23:

  prefix = 0 + 23 = 23
  remainder = 23 % 6 = 5
  
  Is 5 in map? NO
  Store it: map = {0: -1, 5: 0}

═══════════════════════════════════════════════════════════

Process index 1, nums[1] = 2:

  prefix = 23 + 2 = 25
  remainder = 25 % 6 = 1
  
  Is 1 in map? NO
  Store it: map = {0: -1, 5: 0, 1: 1}

═══════════════════════════════════════════════════════════

Process index 2, nums[2] = 4:

  prefix = 25 + 4 = 29
  remainder = 29 % 6 = 5
  
  Is 5 in map? YES, at index 0!
  Length check: 2 - 0 = 2 >= 2? YES!
  
  FOUND! Subarray from index 1 to 2: [2, 4]
  Sum = 6, divisible by 6 ✓
  
  Return TRUE

═══════════════════════════════════════════════════════════
```

---

## The Code (With Line-by-Line Explanation)

```java
public boolean checkSubarraySum(int[] nums, int k) {
    // HashMap: remainder → first index where this remainder occurred
    Map<Integer, Integer> remainderIndex = new HashMap<>();
    
    // CRITICAL: Remainder 0 at index -1
    // Handles subarrays starting from index 0
    remainderIndex.put(0, -1);
    
    int prefix = 0;
    
    for (int i = 0; i < nums.length; i++) {
        prefix += nums[i];
        
        // Compute remainder (handle k=0 edge case)
        int remainder = (k == 0) ? prefix : prefix % k;
        
        if (remainderIndex.containsKey(remainder)) {
            // Same remainder found! Check length >= 2
            if (i - remainderIndex.get(remainder) >= 2) {
                return true;
            }
            // Don't update! Keep first occurrence for max length
        } else {
            // First time seeing this remainder
            remainderIndex.put(remainder, i);
        }
    }
    
    return false;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Updating map when remainder exists | Lose first occurrence, might miss valid subarray | Only store if NOT already in map |
| Forgetting length >= 2 check | Problem requires at least 2 elements | Check `i - map.get(remainder) >= 2` |
| Not handling k = 0 | Division by zero | Special case: if k=0, check for sum=0 |
| Negative remainders | Java's % can return negative | Use `((prefix % k) + k) % k` |

---

## Mind-Map Anchor

```
DIVISIBLE BY K (exists?)
       │
       ▼
┌─────────────────────────┐
│ Prefix Sum + Modulo     │
│                         │
│ Same remainder =        │
│ divisible difference    │
│                         │
│ Store FIRST occurrence  │
│ Check length >= 2       │
│ Init: {0: -1}           │
└─────────────────────────┘
```

**Memory phrase:** "Same remainder means divisible — store first index, check length"

---

# PATTERN 3: Subarray Sums Divisible by K (LeetCode 974)

## Pattern Recognition Signal

**When you see:** "count subarrays", "sum divisible by K", "number of subarrays"

**Instant thought:** "Prefix Sum + Modulo + Counting! Same remainder pairs form divisible subarrays"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [4, 5, 0, -2, -3, 1], k = 5

Count ALL subarrays whose sum is divisible by 5:
- [5] → 5, divisible ✓
- [4, 5, 0, -2, -3, 1] → 5, divisible ✓
- [0] → 0, divisible ✓
- [5, 0] → 5, divisible ✓
- [-2, -3] → -5, divisible ✓
- [0, -2, -3] → -5, divisible ✓
- [4, 5, 0, -2, -3] → 4, NOT divisible ✗
... and more

Answer: 7
```

### Why Prefix Sum + Modulo + Counting?

```
Same insight as Pattern 2, but now we COUNT pairs!

If prefix[j] % K == prefix[i] % K
Then subarray (i+1, j) is divisible by K

For each remainder r, if it appears c times:
- Number of pairs = C(c, 2) = c * (c-1) / 2

OR: As we scan, for each new remainder r:
- Add count of previous occurrences of r to result
- Then increment count of r
```

### The Algorithm in Plain English

```
1. Initialize: prefix = 0, count = 0, map = {0: 1}
   
2. For each number:
   a. Add to prefix
   b. Compute remainder = ((prefix % k) + k) % k  // Handle negatives!
   c. Add map[remainder] to count (pairs with same remainder)
   d. Increment map[remainder]
   
3. Return count
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [4, 5, 0, -2, -3, 1]`, `k = 5`

```
═══════════════════════════════════════════════════════════

Initial State:
  prefix = 0, count = 0
  map = {0: 1}  ← "Remainder 0 seen once (empty prefix)"

═══════════════════════════════════════════════════════════

Process nums[0] = 4:
  prefix = 0 + 4 = 4
  remainder = ((4 % 5) + 5) % 5 = 4
  
  map[4] = 0 (not present) → count += 0
  map = {0: 1, 4: 1}
  
  count = 0

═══════════════════════════════════════════════════════════

Process nums[1] = 5:
  prefix = 4 + 5 = 9
  remainder = ((9 % 5) + 5) % 5 = 4
  
  map[4] = 1 → count += 1 = 1
  "Found 1 subarray: [5] (indices 1-1)"
  map = {0: 1, 4: 2}
  
  count = 1

═══════════════════════════════════════════════════════════

Process nums[2] = 0:
  prefix = 9 + 0 = 9
  remainder = ((9 % 5) + 5) % 5 = 4
  
  map[4] = 2 → count += 2 = 3
  "Found 2 more subarrays: [5,0] and [0]"
  map = {0: 1, 4: 3}
  
  count = 3

═══════════════════════════════════════════════════════════

Process nums[3] = -2:
  prefix = 9 + (-2) = 7
  remainder = ((7 % 5) + 5) % 5 = 2
  
  map[2] = 0 → count += 0
  map = {0: 1, 4: 3, 2: 1}
  
  count = 3

═══════════════════════════════════════════════════════════

Process nums[4] = -3:
  prefix = 7 + (-3) = 4
  remainder = ((4 % 5) + 5) % 5 = 4
  
  map[4] = 3 → count += 3 = 6
  "Found 3 more subarrays ending here"
  map = {0: 1, 4: 4, 2: 1}
  
  count = 6

═══════════════════════════════════════════════════════════

Process nums[5] = 1:
  prefix = 4 + 1 = 5
  remainder = ((5 % 5) + 5) % 5 = 0
  
  map[0] = 1 → count += 1 = 7
  "Found 1 more subarray: entire array [4,5,0,-2,-3,1]"
  map = {0: 2, 4: 4, 2: 1}
  
  count = 7

═══════════════════════════════════════════════════════════

Final Answer: count = 7 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
public int subarraysDivByK(int[] nums, int k) {
    // HashMap: remainder → count of times seen
    Map<Integer, Integer> remainderCount = new HashMap<>();
    
    // Empty prefix has remainder 0
    remainderCount.put(0, 1);
    
    int prefix = 0;
    int count = 0;
    
    for (int num : nums) {
        prefix += num;
        
        // CRITICAL: Handle negative remainders!
        // In Java, -7 % 5 = -2, but we want 3
        int remainder = ((prefix % k) + k) % k;
        
        // Add count of same remainders seen before
        count += remainderCount.getOrDefault(remainder, 0);
        
        // Record this remainder
        remainderCount.put(remainder, remainderCount.getOrDefault(remainder, 0) + 1);
    }
    
    return count;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Not handling negative remainders | `-7 % 5 = -2` in Java, not `3` | Use `((prefix % k) + k) % k` |
| Forgetting `{0: 1}` initialization | Miss subarrays starting at index 0 | Always init with `{0: 1}` |
| Confusing with Pattern 2 | Pattern 2 checks existence, this counts | Different logic: count vs boolean |

---

## Mind-Map Anchor

```
COUNT DIVISIBLE BY K
       │
       ▼
┌─────────────────────────┐
│ Prefix Sum + Modulo     │
│                         │
│ Same remainder = pair   │
│ Count pairs as you go   │
│                         │
│ Handle negative: +k % k │
│ Init: {0: 1}            │
└─────────────────────────┘
```

**Memory phrase:** "Same remainder = divisible pair — count as you go, handle negatives"

---

# PATTERN 4: Longest Subarray With Sum Divisible by K

## Pattern Recognition Signal

**When you see:** "longest subarray", "sum divisible by K", "maximum length"

**Instant thought:** "Prefix Sum + Modulo + First Index! Store first occurrence of each remainder"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [2, 7, 6, 1, 4, 5], k = 3

Find the LONGEST subarray whose sum is divisible by 3:
- [2, 7] → 9, divisible, length 2
- [7, 6, 1, 4] → 18, divisible, length 4
- [2, 7, 6, 1, 4, 5] → 25, NOT divisible
- [6] → 6, divisible, length 1
- [2, 7, 6, 1, 4] → 20, NOT divisible

Answer: 4 (subarray [7, 6, 1, 4])
```

### Why Store First Occurrence?

```
For LONGEST subarray, we want the EARLIEST starting point.

If remainder r appears at indices [2, 5, 8]:
- Subarray from 3 to 8 has length 6
- Subarray from 6 to 8 has length 3

First occurrence (index 2) gives longest subarray!
```

### The Algorithm in Plain English

```
1. Initialize: prefix = 0, maxLen = 0, map = {0: -1}
   
2. For each index i:
   a. Add nums[i] to prefix
   b. Compute remainder = ((prefix % k) + k) % k
   c. If remainder in map:
      - Length = i - map[remainder]
      - Update maxLen if longer
   d. If remainder NOT in map:
      - Store current index (first occurrence)
   
3. Return maxLen
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [2, 7, 6, 1, 4, 5]`, `k = 3`

```
═══════════════════════════════════════════════════════════

Initial: prefix = 0, maxLen = 0, map = {0: -1}

═══════════════════════════════════════════════════════════

i=0, nums[0]=2:
  prefix = 2, remainder = 2 % 3 = 2
  2 not in map → store: map = {0: -1, 2: 0}
  maxLen = 0

═══════════════════════════════════════════════════════════

i=1, nums[1]=7:
  prefix = 9, remainder = 9 % 3 = 0
  0 in map at index -1!
  length = 1 - (-1) = 2
  maxLen = max(0, 2) = 2
  Don't update map (keep first occurrence)
  
  "Subarray [2,7] has sum 9, divisible by 3, length 2"

═══════════════════════════════════════════════════════════

i=2, nums[2]=6:
  prefix = 15, remainder = 15 % 3 = 0
  0 in map at index -1!
  length = 2 - (-1) = 3
  maxLen = max(2, 3) = 3
  
  "Subarray [2,7,6] has sum 15, divisible by 3, length 3"

═══════════════════════════════════════════════════════════

i=3, nums[3]=1:
  prefix = 16, remainder = 16 % 3 = 1
  1 not in map → store: map = {0: -1, 2: 0, 1: 3}
  maxLen = 3

═══════════════════════════════════════════════════════════

i=4, nums[4]=4:
  prefix = 20, remainder = 20 % 3 = 2
  2 in map at index 0!
  length = 4 - 0 = 4
  maxLen = max(3, 4) = 4
  
  "Subarray [7,6,1,4] has sum 18, divisible by 3, length 4"

═══════════════════════════════════════════════════════════

i=5, nums[5]=5:
  prefix = 25, remainder = 25 % 3 = 1
  1 in map at index 3!
  length = 5 - 3 = 2
  maxLen = max(4, 2) = 4 (no change)

═══════════════════════════════════════════════════════════

Final Answer: maxLen = 4 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
public int longestSubarrayDivByK(int[] nums, int k) {
    // HashMap: remainder → FIRST index where seen
    Map<Integer, Integer> firstIndex = new HashMap<>();
    
    // Empty prefix (remainder 0) at index -1
    firstIndex.put(0, -1);
    
    int prefix = 0;
    int maxLen = 0;
    
    for (int i = 0; i < nums.length; i++) {
        prefix += nums[i];
        
        // Handle negative remainders
        int remainder = ((prefix % k) + k) % k;
        
        if (firstIndex.containsKey(remainder)) {
            // Same remainder found! Calculate length
            int length = i - firstIndex.get(remainder);
            maxLen = Math.max(maxLen, length);
            // DON'T update map - keep first occurrence!
        } else {
            // First time seeing this remainder
            firstIndex.put(remainder, i);
        }
    }
    
    return maxLen;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Updating map when remainder exists | Lose first occurrence, get shorter length | Only store if NOT in map |
| Using `{0: 0}` instead of `{0: -1}` | Off-by-one in length calculation | Use `{0: -1}` for correct length |
| Forgetting negative remainder handling | Wrong remainder for negative prefix sums | Use `((prefix % k) + k) % k` |

---

## Mind-Map Anchor

```
LONGEST DIVISIBLE BY K
       │
       ▼
┌─────────────────────────┐
│ Prefix Sum + Modulo     │
│                         │
│ Store FIRST index only  │
│ First occurrence =      │
│ longest possible length │
│                         │
│ Init: {0: -1}           │
└─────────────────────────┘
```

**Memory phrase:** "First occurrence for longest — don't update existing remainders"

---

# PATTERN 5: Smallest Subarray With Sum Divisible by K

## Pattern Recognition Signal

**When you see:** "smallest subarray", "minimum length", "sum divisible by K"

**Instant thought:** "Prefix Sum + Modulo + Last Index! Store most recent occurrence"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [2, 7, 6, 1, 4, 5], k = 3

Find the SHORTEST subarray whose sum is divisible by 3:
- [6] → 6, divisible, length 1 ← SHORTEST!
- [2, 7] → 9, divisible, length 2
- [7, 6, 1, 4] → 18, divisible, length 4

Answer: 1
```

### Why Store Last Occurrence?

```
For SHORTEST subarray, we want the LATEST starting point.

If remainder r appears at indices [2, 5, 8]:
- Subarray from 3 to 8 has length 6
- Subarray from 6 to 8 has length 3

Last occurrence (index 5) gives shortest subarray ending at 8!
```

### The Algorithm in Plain English

```
1. Initialize: prefix = 0, minLen = infinity, map = {0: -1}
   
2. For each index i:
   a. Add nums[i] to prefix
   b. Compute remainder = ((prefix % k) + k) % k
   c. If remainder in map:
      - Length = i - map[remainder]
      - Update minLen if shorter
   d. ALWAYS update map with current index (last occurrence)
   
3. Return minLen (or 0/-1 if not found)
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [2, 7, 6, 1, 4, 5]`, `k = 3`

```
═══════════════════════════════════════════════════════════

Initial: prefix = 0, minLen = ∞, map = {0: -1}

═══════════════════════════════════════════════════════════

i=0, nums[0]=2:
  prefix = 2, remainder = 2
  2 not in map
  Update map: {0: -1, 2: 0}
  minLen = ∞

═══════════════════════════════════════════════════════════

i=1, nums[1]=7:
  prefix = 9, remainder = 0
  0 in map at -1!
  length = 1 - (-1) = 2
  minLen = min(∞, 2) = 2
  Update map: {0: 1, 2: 0}  ← Update to latest index!

═══════════════════════════════════════════════════════════

i=2, nums[2]=6:
  prefix = 15, remainder = 0
  0 in map at 1!
  length = 2 - 1 = 1
  minLen = min(2, 1) = 1
  Update map: {0: 2, 2: 0}
  
  "Found [6] with length 1!"

═══════════════════════════════════════════════════════════

i=3, nums[3]=1:
  prefix = 16, remainder = 1
  1 not in map
  Update map: {0: 2, 2: 0, 1: 3}
  minLen = 1

═══════════════════════════════════════════════════════════

i=4, nums[4]=4:
  prefix = 20, remainder = 2
  2 in map at 0!
  length = 4 - 0 = 4
  minLen = min(1, 4) = 1 (no change)
  Update map: {0: 2, 2: 4, 1: 3}

═══════════════════════════════════════════════════════════

i=5, nums[5]=5:
  prefix = 25, remainder = 1
  1 in map at 3!
  length = 5 - 3 = 2
  minLen = min(1, 2) = 1 (no change)
  Update map: {0: 2, 2: 4, 1: 5}

═══════════════════════════════════════════════════════════

Final Answer: minLen = 1 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
public int smallestSubarrayDivByK(int[] nums, int k) {
    // HashMap: remainder → LAST index where seen
    Map<Integer, Integer> lastIndex = new HashMap<>();
    
    // Empty prefix at index -1
    lastIndex.put(0, -1);
    
    int prefix = 0;
    int minLen = Integer.MAX_VALUE;
    
    for (int i = 0; i < nums.length; i++) {
        prefix += nums[i];
        
        int remainder = ((prefix % k) + k) % k;
        
        if (lastIndex.containsKey(remainder)) {
            int length = i - lastIndex.get(remainder);
            minLen = Math.min(minLen, length);
        }
        
        // ALWAYS update - we want last occurrence for shortest
        lastIndex.put(remainder, i);
    }
    
    return minLen == Integer.MAX_VALUE ? -1 : minLen;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Not updating map (like longest pattern) | Miss shorter subarrays | ALWAYS update to latest index |
| Returning MAX_VALUE when not found | Invalid answer | Return -1 or handle appropriately |

---

## Mind-Map Anchor

```
SHORTEST DIVISIBLE BY K
       │
       ▼
┌─────────────────────────┐
│ Prefix Sum + Modulo     │
│                         │
│ Store LAST index        │
│ ALWAYS update map       │
│ Last occurrence =       │
│ shortest possible       │
│                         │
│ Init: {0: -1}           │
└─────────────────────────┘
```

**Memory phrase:** "Last occurrence for shortest — always update the map"

---

## Longest vs Shortest: The Key Difference

```
┌─────────────────────────────────────────────────────────────────────┐
│                                                                      │
│  LONGEST SUBARRAY:                                                  │
│  - Store FIRST occurrence of each remainder                         │
│  - DON'T update if remainder already exists                         │
│  - First occurrence → earliest start → longest length               │
│                                                                      │
│  SHORTEST SUBARRAY:                                                 │
│  - Store LAST occurrence of each remainder                          │
│  - ALWAYS update to current index                                   │
│  - Last occurrence → latest start → shortest length                 │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

---

# PATTERN 6: Count Subarrays With Equal 0s, 1s, and 2s

## Pattern Recognition Signal

**When you see:** "equal count of three values", "equal 0s, 1s, and 2s", "subarray with same frequency"

**Instant thought:** "Transform to differences! Track (count0-count1, count1-count2) pairs"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: arr = [0, 1, 0, 2, 0, 1, 2]

Find subarrays with equal number of 0s, 1s, and 2s:
- [0, 1, 2] at indices (0,1,3) → one 0, one 1, one 2 ✓
- [0, 1, 0, 2, 0, 1, 2] → three 0s, two 1s, two 2s ✗
- [1, 0, 2] at indices (1,2,3) → one 0, one 1, one 2 ✓
- etc.

Count all such subarrays.
```

### The Transformation Trick

```
Instead of tracking three counts, track DIFFERENCES!

Let: c0 = count of 0s, c1 = count of 1s, c2 = count of 2s

Equal counts means: c0 = c1 = c2

This is equivalent to: (c0 - c1) = 0 AND (c1 - c2) = 0

KEY INSIGHT:
If at index j: (c0-c1, c1-c2) = (a, b)
If at index i: (c0-c1, c1-c2) = (a, b)  [same pair!]

Then subarray (i+1, j) has equal 0s, 1s, and 2s!

Why? The DIFFERENCE in differences is zero!
```

### The Algorithm in Plain English

```
1. Initialize: c0=0, c1=0, c2=0, count=0
   map = {(0,0): 1}  // Empty prefix has equal counts (all zero)
   
2. For each element:
   a. Increment appropriate counter (c0, c1, or c2)
   b. Compute key = (c0-c1, c1-c2)
   c. Add map[key] to count (same key = equal counts in between)
   d. Increment map[key]
   
3. Return count
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `arr = [0, 1, 2, 0, 1, 2]`

```
═══════════════════════════════════════════════════════════

Initial: c0=0, c1=0, c2=0, count=0
         map = {(0,0): 1}

═══════════════════════════════════════════════════════════

i=0, arr[0]=0:
  c0=1, c1=0, c2=0
  key = (1-0, 0-0) = (1, 0)
  (1,0) not in map → count += 0
  map = {(0,0): 1, (1,0): 1}

═══════════════════════════════════════════════════════════

i=1, arr[1]=1:
  c0=1, c1=1, c2=0
  key = (1-1, 1-0) = (0, 1)
  (0,1) not in map → count += 0
  map = {(0,0): 1, (1,0): 1, (0,1): 1}

═══════════════════════════════════════════════════════════

i=2, arr[2]=2:
  c0=1, c1=1, c2=1
  key = (1-1, 1-1) = (0, 0)
  (0,0) in map with count 1!
  count += 1 = 1
  "Found [0,1,2] with equal counts!"
  map = {(0,0): 2, (1,0): 1, (0,1): 1}

═══════════════════════════════════════════════════════════

i=3, arr[3]=0:
  c0=2, c1=1, c2=1
  key = (2-1, 1-1) = (1, 0)
  (1,0) in map with count 1!
  count += 1 = 2
  "Found [1,2,0] with equal counts!"
  map = {(0,0): 2, (1,0): 2, (0,1): 1}

═══════════════════════════════════════════════════════════

i=4, arr[4]=1:
  c0=2, c1=2, c2=1
  key = (2-2, 2-1) = (0, 1)
  (0,1) in map with count 1!
  count += 1 = 3
  "Found [2,0,1] with equal counts!"
  map = {(0,0): 2, (1,0): 2, (0,1): 2}

═══════════════════════════════════════════════════════════

i=5, arr[5]=2:
  c0=2, c1=2, c2=2
  key = (2-2, 2-2) = (0, 0)
  (0,0) in map with count 2!
  count += 2 = 5
  "Found [0,1,2,0,1,2] and [0,1,2] (second half)!"
  map = {(0,0): 3, (1,0): 2, (0,1): 2}

═══════════════════════════════════════════════════════════

Final Answer: count = 5 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
public int countSubarraysWithEqual012(int[] arr) {
    // Map: (diff1, diff2) → count
    // Using string key "diff1,diff2" for simplicity
    Map<String, Integer> map = new HashMap<>();
    
    // Empty prefix: all counts are 0, so differences are (0, 0)
    map.put("0,0", 1);
    
    int c0 = 0, c1 = 0, c2 = 0;
    int count = 0;
    
    for (int num : arr) {
        // Update appropriate counter
        if (num == 0) c0++;
        else if (num == 1) c1++;
        else c2++;
        
        // Compute difference pair
        String key = (c0 - c1) + "," + (c1 - c2);
        
        // Same key = equal counts in subarray between
        count += map.getOrDefault(key, 0);
        
        // Record this state
        map.put(key, map.getOrDefault(key, 0) + 1);
    }
    
    return count;
}
```

**Alternative using array encoding:**

```java
public int countSubarraysWithEqual012(int[] arr) {
    // Use a map with pair as key (can use long encoding)
    Map<Long, Integer> map = new HashMap<>();
    map.put(0L, 1);  // (0, 0) encoded as 0
    
    int c0 = 0, c1 = 0, c2 = 0;
    int count = 0;
    int OFFSET = 100001;  // To handle negative differences
    
    for (int num : arr) {
        if (num == 0) c0++;
        else if (num == 1) c1++;
        else c2++;
        
        int d1 = c0 - c1;
        int d2 = c1 - c2;
        
        // Encode pair as single long (with offset for negatives)
        long key = (long)(d1 + OFFSET) * (2 * OFFSET) + (d2 + OFFSET);
        
        count += map.getOrDefault(key, 0);
        map.put(key, map.getOrDefault(key, 0) + 1);
    }
    
    return count;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Tracking three separate counts | Can't use HashMap efficiently | Track differences instead |
| Forgetting initial state (0,0) | Miss subarrays starting at index 0 | Init map with `{(0,0): 1}` |
| Integer overflow in key encoding | Large differences cause collision | Use long or string keys |

---

## Mind-Map Anchor

```
EQUAL 0s, 1s, 2s
       │
       ▼
┌─────────────────────────┐
│ Track DIFFERENCES       │
│ (c0-c1, c1-c2)          │
│                         │
│ Same pair = equal       │
│ counts in between       │
│                         │
│ Init: {(0,0): 1}        │
└─────────────────────────┘
```

**Memory phrase:** "Equal counts = same difference pair — track (c0-c1, c1-c2)"

---

# PATTERN 7: Binary Subarrays With Sum (LeetCode 930)

## Pattern Recognition Signal

**When you see:** "binary array", "subarray sum equals K", "count subarrays with sum"

**Instant thought:** "Prefix Sum + HashMap! Binary array is just a special case"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [1, 0, 1, 0, 1], goal = 2

Count subarrays with exactly 2 ones:
- [1, 0, 1] at (0,2) → 2 ones ✓
- [0, 1, 0, 1] at (1,4) → 2 ones ✓
- [1, 0, 1] at (2,4) → 2 ones ✓
- [1, 0, 1, 0] at (0,3) → 2 ones ✓
- [1, 0, 1, 0, 1] at (0,4) → 3 ones ✗

Answer: 4
```

### Why This is Just Pattern 1?

```
In a binary array (only 0s and 1s):
- Sum of subarray = count of 1s in that subarray!

So "subarray with sum = goal" = "subarray with exactly 'goal' ones"

This is EXACTLY Pattern 1 (Subarray Sum Equals K)!
```

### The Algorithm in Plain English

```
Same as Pattern 1:
1. Initialize: prefix = 0, count = 0, map = {0: 1}
2. For each number:
   a. Add to prefix (this counts 1s seen so far)
   b. Look for (prefix - goal) in map
   c. Add current prefix to map
3. Return count
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [1, 0, 1, 0, 1]`, `goal = 2`

```
═══════════════════════════════════════════════════════════

Initial: prefix = 0, count = 0, map = {0: 1}

═══════════════════════════════════════════════════════════

i=0, nums[0]=1:
  prefix = 1
  Look for 1 - 2 = -1 → not in map
  count = 0
  map = {0: 1, 1: 1}

═══════════════════════════════════════════════════════════

i=1, nums[1]=0:
  prefix = 1
  Look for 1 - 2 = -1 → not in map
  count = 0
  map = {0: 1, 1: 2}  // prefix 1 seen twice now

═══════════════════════════════════════════════════════════

i=2, nums[2]=1:
  prefix = 2
  Look for 2 - 2 = 0 → in map with count 1!
  count = 1
  "Found [1,0,1] with 2 ones"
  map = {0: 1, 1: 2, 2: 1}

═══════════════════════════════════════════════════════════

i=3, nums[3]=0:
  prefix = 2
  Look for 2 - 2 = 0 → in map with count 1!
  count = 2
  "Found [1,0,1,0] with 2 ones"
  map = {0: 1, 1: 2, 2: 2}

═══════════════════════════════════════════════════════════

i=4, nums[4]=1:
  prefix = 3
  Look for 3 - 2 = 1 → in map with count 2!
  count = 2 + 2 = 4
  "Found [0,1,0,1] and [1,0,1] with 2 ones each"
  map = {0: 1, 1: 2, 2: 2, 3: 1}

═══════════════════════════════════════════════════════════

Final Answer: count = 4 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
public int numSubarraysWithSum(int[] nums, int goal) {
    Map<Integer, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0, 1);
    
    int prefix = 0;
    int count = 0;
    
    for (int num : nums) {
        prefix += num;  // In binary array, this counts 1s
        
        // Look for prefix - goal
        count += prefixCount.getOrDefault(prefix - goal, 0);
        
        prefixCount.put(prefix, prefixCount.getOrDefault(prefix, 0) + 1);
    }
    
    return count;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Overcomplicating for binary | It's just sum = K in disguise | Use standard prefix sum + HashMap |
| Forgetting goal = 0 case | Need subarrays with all 0s | Algorithm handles it naturally |

---

## Mind-Map Anchor

```
BINARY SUBARRAYS WITH SUM
       │
       ▼
┌─────────────────────────┐
│ Just Pattern 1!         │
│                         │
│ Sum in binary array =   │
│ count of 1s             │
│                         │
│ Prefix Sum + HashMap    │
└─────────────────────────┘
```

**Memory phrase:** "Binary sum = count of ones — use standard prefix sum"

---

# PATTERN 8: Count Number of Nice Subarrays (LeetCode 1248)

## Pattern Recognition Signal

**When you see:** "count odd numbers", "exactly K odd numbers", "nice subarray"

**Instant thought:** "Transform! Replace odd→1, even→0, then it's binary sum = K"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [1, 1, 2, 1, 1], k = 3

Count subarrays with exactly 3 odd numbers:
- [1, 1, 2, 1] → 3 odds ✓
- [1, 2, 1, 1] → 3 odds ✓

Answer: 2
```

### The Transformation Trick

```
Transform the array:
- Odd number → 1
- Even number → 0

Original: [1, 1, 2, 1, 1]
Transform: [1, 1, 0, 1, 1]

Now "exactly K odd numbers" = "sum equals K"

This is Pattern 7 (Binary Subarrays With Sum)!
```

### The Algorithm in Plain English

```
1. Transform: odd → 1, even → 0 (or just use num % 2)
2. Apply Pattern 1/7: Prefix Sum + HashMap for sum = K
3. Return count
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [1, 1, 2, 1, 1]`, `k = 3`

```
═══════════════════════════════════════════════════════════

Transform (conceptually):
nums:      [1, 1, 2, 1, 1]
parity:    [1, 1, 0, 1, 1]  (odd=1, even=0)

═══════════════════════════════════════════════════════════

Initial: prefix = 0, count = 0, map = {0: 1}

═══════════════════════════════════════════════════════════

i=0, nums[0]=1 (odd):
  prefix = 0 + 1 = 1
  Look for 1 - 3 = -2 → not in map
  map = {0: 1, 1: 1}

═══════════════════════════════════════════════════════════

i=1, nums[1]=1 (odd):
  prefix = 1 + 1 = 2
  Look for 2 - 3 = -1 → not in map
  map = {0: 1, 1: 1, 2: 1}

═══════════════════════════════════════════════════════════

i=2, nums[2]=2 (even):
  prefix = 2 + 0 = 2
  Look for 2 - 3 = -1 → not in map
  map = {0: 1, 1: 1, 2: 2}

═══════════════════════════════════════════════════════════

i=3, nums[3]=1 (odd):
  prefix = 2 + 1 = 3
  Look for 3 - 3 = 0 → in map with count 1!
  count = 1
  "Found [1,1,2,1] with 3 odds"
  map = {0: 1, 1: 1, 2: 2, 3: 1}

═══════════════════════════════════════════════════════════

i=4, nums[4]=1 (odd):
  prefix = 3 + 1 = 4
  Look for 4 - 3 = 1 → in map with count 1!
  count = 2
  "Found [1,2,1,1] with 3 odds"
  map = {0: 1, 1: 1, 2: 2, 3: 1, 4: 1}

═══════════════════════════════════════════════════════════

Final Answer: count = 2 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
public int numberOfSubarrays(int[] nums, int k) {
    Map<Integer, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0, 1);
    
    int prefix = 0;  // Count of odd numbers seen
    int count = 0;
    
    for (int num : nums) {
        // Add 1 if odd, 0 if even
        prefix += num % 2;  // or: prefix += (num & 1);
        
        // Look for (prefix - k) odd numbers
        count += prefixCount.getOrDefault(prefix - k, 0);
        
        prefixCount.put(prefix, prefixCount.getOrDefault(prefix, 0) + 1);
    }
    
    return count;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Creating new transformed array | Wastes O(n) space | Just use `num % 2` inline |
| Checking `num % 2 == 1` for odd | Negative numbers: `-3 % 2 = -1` | Use `num % 2 != 0` or `num & 1` |

---

## Mind-Map Anchor

```
NICE SUBARRAYS (K ODDS)
       │
       ▼
┌─────────────────────────┐
│ Transform: odd→1, even→0│
│                         │
│ Then it's binary sum = K│
│ (Pattern 7)             │
│                         │
│ Use num % 2 inline      │
└─────────────────────────┘
```

**Memory phrase:** "Count odds = sum of parities — transform inline with num % 2"

---

# PATTERN 9: Subarray Sums in Circular Array

## Pattern Recognition Signal

**When you see:** "circular array", "wrap around", "subarray sum in circular"

**Instant thought:** "Two cases! Max normal subarray OR total - min subarray"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [5, -3, 5] (circular, so 5 connects back to 5)

Find maximum subarray sum, considering wrap-around:
- Normal: [5] = 5, [5,-3,5] = 7
- Circular: [5, 5] (wrapping) = 10 ← This is the answer!

The circular subarray [5, 5] = last element + first element
```

### The Key Insight

```
A circular subarray that wraps around is:
  Total Sum - (some middle subarray)

To MAXIMIZE circular subarray:
  Total Sum - MINIMUM middle subarray

So the answer is:
  max(maxSubarray, totalSum - minSubarray)

┌─────────────────────────────────────────────────────────────────────┐
│                                                                      │
│  CASE 1: Maximum subarray doesn't wrap                              │
│  [....XXXXX....]  → Use Kadane's algorithm                          │
│                                                                      │
│  CASE 2: Maximum subarray wraps around                              │
│  [XXX........XXX]  → Total - minimum middle subarray                │
│                                                                      │
│  Answer = max(Case 1, Case 2)                                       │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Edge Case: All Negative Numbers

```
If all numbers are negative:
- maxSubarray = largest negative number
- minSubarray = entire array
- totalSum - minSubarray = 0 (empty subarray)

But empty subarray isn't valid! So if maxSubarray < 0, return maxSubarray.
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [5, -3, 5]`

```
═══════════════════════════════════════════════════════════

Step 1: Calculate total sum
  total = 5 + (-3) + 5 = 7

═══════════════════════════════════════════════════════════

Step 2: Find max subarray (Kadane's)

  i=0: currentMax = max(5, 0+5) = 5
       maxSum = max(-∞, 5) = 5
       
  i=1: currentMax = max(-3, 5-3) = 2
       maxSum = max(5, 2) = 5
       
  i=2: currentMax = max(5, 2+5) = 7
       maxSum = max(5, 7) = 7

  maxSubarray = 7 (the entire array [5,-3,5])

═══════════════════════════════════════════════════════════

Step 3: Find min subarray (Kadane's inverted)

  i=0: currentMin = min(5, 0+5) = 5
       minSum = min(∞, 5) = 5
       
  i=1: currentMin = min(-3, 5-3) = -3
       minSum = min(5, -3) = -3
       
  i=2: currentMin = min(5, -3+5) = 2
       minSum = min(-3, 2) = -3

  minSubarray = -3 (just the element [-3])

═══════════════════════════════════════════════════════════

Step 4: Calculate circular max
  circularMax = total - minSubarray = 7 - (-3) = 10
  
  This represents: [5, 5] (wrapping around, excluding -3)

═══════════════════════════════════════════════════════════

Step 5: Return answer
  answer = max(maxSubarray, circularMax)
         = max(7, 10) = 10 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
public int maxSubarraySumCircular(int[] nums) {
    int total = 0;
    int maxSum = Integer.MIN_VALUE;
    int minSum = Integer.MAX_VALUE;
    int currentMax = 0;
    int currentMin = 0;
    
    for (int num : nums) {
        // Total sum
        total += num;
        
        // Kadane's for maximum subarray
        currentMax = Math.max(num, currentMax + num);
        maxSum = Math.max(maxSum, currentMax);
        
        // Kadane's for minimum subarray
        currentMin = Math.min(num, currentMin + num);
        minSum = Math.min(minSum, currentMin);
    }
    
    // Edge case: all negative numbers
    // If maxSum < 0, the circular case would give empty subarray (invalid)
    if (maxSum < 0) {
        return maxSum;
    }
    
    // Return max of normal case and circular case
    return Math.max(maxSum, total - minSum);
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Forgetting all-negative case | `total - minSum = 0` (empty subarray) | Check if `maxSum < 0`, return `maxSum` |
| Using prefix sum instead of Kadane | Works but more complex | Kadane is simpler for this |
| Not considering both cases | Miss optimal circular subarray | Always compute both and take max |

---

## Mind-Map Anchor

```
CIRCULAR SUBARRAY SUM
       │
       ▼
┌─────────────────────────┐
│ Two cases:              │
│                         │
│ 1. Normal max (Kadane)  │
│ 2. Total - min subarray │
│                         │
│ Answer = max(case1, 2)  │
│                         │
│ Edge: all negative →    │
│ return maxSum           │
└─────────────────────────┘
```

**Memory phrase:** "Circular max = max(normal, total - min) — watch all-negative edge case"

---

# PATTERN 10: Range Sum Query 2D - Immutable (LeetCode 304)

## Pattern Recognition Signal

**When you see:** "2D matrix", "sum of rectangle", "multiple queries", "immutable"

**Instant thought:** "2D Prefix Sum! Inclusion-exclusion principle"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Given a 2D matrix, answer queries:
"What is the sum of elements in rectangle from (r1,c1) to (r2,c2)?"

Matrix:
[3, 0, 1, 4, 2]
[5, 6, 3, 2, 1]
[1, 2, 0, 1, 5]
[4, 1, 0, 1, 7]
[1, 0, 3, 0, 5]

Query: sumRegion(2, 1, 4, 3) = ?
       Sum of rectangle from (2,1) to (4,3)
       = 2+0+1 + 1+0+1 + 0+3+0 = 8
```

### The 2D Prefix Sum Concept

```
prefix[i][j] = sum of all elements in rectangle from (0,0) to (i,j)

Building it:
prefix[i][j] = matrix[i][j] 
             + prefix[i-1][j]    (above)
             + prefix[i][j-1]    (left)
             - prefix[i-1][j-1]  (double-counted corner)
```

### The Inclusion-Exclusion Query

```
To get sum of rectangle (r1,c1) to (r2,c2):

┌─────────────────────────────────────────────────────────────────────┐
│                                                                      │
│  ┌───────────────┬───────────┐                                      │
│  │       A       │     B     │                                      │
│  │               │           │                                      │
│  ├───────────────┼───────────┤ ← r1-1                               │
│  │       C       │  TARGET   │                                      │
│  │               │    (D)    │                                      │
│  └───────────────┴───────────┘                                      │
│                  ↑           ↑                                       │
│                c1-1         c2                                       │
│                                                                      │
│  prefix[r2][c2] = A + B + C + D                                     │
│  prefix[r1-1][c2] = A + B                                           │
│  prefix[r2][c1-1] = A + C                                           │
│  prefix[r1-1][c1-1] = A                                             │
│                                                                      │
│  TARGET (D) = prefix[r2][c2]                                        │
│             - prefix[r1-1][c2]    (remove top)                      │
│             - prefix[r2][c1-1]    (remove left)                     │
│             + prefix[r1-1][c1-1]  (add back corner, removed twice)  │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Visual Dry Run (Step-by-Step)

**Input Matrix:**
```
[3, 0, 1, 4, 2]
[5, 6, 3, 2, 1]
[1, 2, 0, 1, 5]
[4, 1, 0, 1, 7]
[1, 0, 3, 0, 5]
```

```
═══════════════════════════════════════════════════════════

Step 1: Build 2D Prefix Sum (with padding)

Using 1-indexed prefix array (size [m+1][n+1]):

prefix[0][*] = 0 (padding row)
prefix[*][0] = 0 (padding column)

prefix[1][1] = 3 + 0 + 0 - 0 = 3
prefix[1][2] = 0 + 3 + 0 - 0 = 3
prefix[1][3] = 1 + 3 + 0 - 0 = 4
...

Full prefix array:
[0,  0,  0,  0,  0,  0]
[0,  3,  3,  4,  8, 10]
[0,  8, 14, 18, 24, 27]
[0,  9, 17, 21, 28, 36]
[0, 13, 22, 26, 34, 49]
[0, 14, 23, 30, 38, 58]

═══════════════════════════════════════════════════════════

Step 2: Query sumRegion(2, 1, 4, 3)

Convert to 1-indexed: (r1=3, c1=2, r2=5, c2=4)

sum = prefix[5][4] - prefix[2][4] - prefix[5][1] + prefix[2][1]
    = 38 - 24 - 14 + 8
    = 8 ✓

═══════════════════════════════════════════════════════════
```

---

## The Code (With Line-by-Line Explanation)

```java
class NumMatrix {
    private int[][] prefix;
    
    public NumMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        // Use (m+1) x (n+1) for padding (avoids boundary checks)
        prefix = new int[m + 1][n + 1];
        
        // Build prefix sum
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                prefix[i][j] = matrix[i-1][j-1]  // Current element
                             + prefix[i-1][j]     // Above
                             + prefix[i][j-1]     // Left
                             - prefix[i-1][j-1];  // Remove double-count
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        // Convert to 1-indexed
        int r1 = row1 + 1, c1 = col1 + 1;
        int r2 = row2 + 1, c2 = col2 + 1;
        
        // Inclusion-exclusion
        return prefix[r2][c2] 
             - prefix[r1-1][c2]    // Remove top
             - prefix[r2][c1-1]    // Remove left
             + prefix[r1-1][c1-1]; // Add back corner
    }
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Off-by-one errors | 0-indexed vs 1-indexed confusion | Use padding (m+1, n+1) array |
| Wrong inclusion-exclusion | Signs mixed up | Draw the diagram, verify formula |
| Not handling empty matrix | NullPointerException | Check matrix.length > 0 |

---

## Mind-Map Anchor

```
2D RANGE SUM QUERY
       │
       ▼
┌─────────────────────────┐
│ 2D Prefix Sum           │
│                         │
│ Build: O(m×n)           │
│ Query: O(1)             │
│                         │
│ Inclusion-Exclusion:    │
│ +whole -top -left +corner│
└─────────────────────────┘
```

**Memory phrase:** "2D prefix = current + above + left - corner; query = whole - top - left + corner"

---

# PATTERN 11: Number of Submatrices That Sum to Target (LeetCode 1074)

## Pattern Recognition Signal

**When you see:** "count submatrices", "sum equals target", "2D subarray count"

**Instant thought:** "Fix two rows, reduce to 1D prefix sum problem!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: matrix = [[0,1,0],[1,1,1],[0,1,0]], target = 0

Count all submatrices (rectangles) that sum to 0:
- Single cell [0] at (0,0) → sum 0 ✓
- Single cell [0] at (0,2) → sum 0 ✓
- Single cell [0] at (2,0) → sum 0 ✓
- Single cell [0] at (2,2) → sum 0 ✓
- Rectangle from (0,0) to (2,0): [0,1,0]ᵀ → sum 1 ✗
- etc.

Answer: 4
```

### The Dimension Reduction Trick

```
2D problem → 1D problem!

Fix top row (r1) and bottom row (r2).
Compress columns into a 1D array where:
  compressed[c] = sum of matrix[r1..r2][c]

Now find subarrays in compressed[] that sum to target!
This is Pattern 1 (Subarray Sum Equals K)!

┌─────────────────────────────────────────────────────────────────────┐
│                                                                      │
│  For each pair (r1, r2):                                            │
│                                                                      │
│  Matrix:           Compressed:                                       │
│  [a, b, c]                                                          │
│  [d, e, f]   →    [a+d+g, b+e+h, c+f+i]                             │
│  [g, h, i]                                                          │
│                                                                      │
│  Now count 1D subarrays with sum = target                           │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### The Algorithm in Plain English

```
1. For each starting row r1 (0 to m-1):
   a. Initialize compressed array of size n (all zeros)
   
   b. For each ending row r2 (r1 to m-1):
      i. Add row r2 to compressed array
      ii. Use Pattern 1 on compressed array
      iii. Add count to result

2. Return total count
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `matrix = [[0,1,0],[1,1,1],[0,1,0]]`, `target = 0`

```
═══════════════════════════════════════════════════════════

r1 = 0:

  r2 = 0: compressed = [0, 1, 0]
          Pattern 1 with target=0:
          - prefix=0: look for 0-0=0, found 1 (init)
          - prefix=1: look for 1-0=1, not found
          - prefix=1: look for 1-0=1, found 1
          Wait, let me redo...
          
          Actually for [0,1,0], target=0:
          map={0:1}, prefix=0, count=0
          
          num=0: prefix=0, look for 0, found! count=1
                 map={0:2}
          num=1: prefix=1, look for 1, not found
                 map={0:2, 1:1}
          num=0: prefix=1, look for 1, found! count=2
                 map={0:2, 1:2}
          
          Subarrays: [0] at index 0, [1,0] NO wait...
          
          Let me recalculate:
          [0,1,0], target=0
          Subarrays summing to 0: [0] at idx 0, [0] at idx 2
          count = 2

═══════════════════════════════════════════════════════════

  r2 = 1: compressed = [0+1, 1+1, 0+1] = [1, 2, 1]
          Pattern 1 with target=0:
          No subarray sums to 0
          count = 0

═══════════════════════════════════════════════════════════

  r2 = 2: compressed = [1+0, 2+1, 1+0] = [1, 3, 1]
          Pattern 1 with target=0:
          No subarray sums to 0
          count = 0

═══════════════════════════════════════════════════════════

r1 = 1:

  r2 = 1: compressed = [1, 1, 1]
          No subarray sums to 0
          count = 0

  r2 = 2: compressed = [1+0, 1+1, 1+0] = [1, 2, 1]
          No subarray sums to 0
          count = 0

═══════════════════════════════════════════════════════════

r1 = 2:

  r2 = 2: compressed = [0, 1, 0]
          Same as r1=0, r2=0
          count = 2

═══════════════════════════════════════════════════════════

Total count = 2 + 0 + 0 + 0 + 0 + 2 = 4 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
public int numSubmatrixSumTarget(int[][] matrix, int target) {
    int m = matrix.length;
    int n = matrix[0].length;
    int count = 0;
    
    // Fix top row
    for (int r1 = 0; r1 < m; r1++) {
        // Compressed column sums for rows r1 to r2
        int[] compressed = new int[n];
        
        // Extend bottom row
        for (int r2 = r1; r2 < m; r2++) {
            // Add current row to compressed
            for (int c = 0; c < n; c++) {
                compressed[c] += matrix[r2][c];
            }
            
            // Now apply Pattern 1 on compressed array
            count += countSubarraySum(compressed, target);
        }
    }
    
    return count;
}

// Pattern 1: Count subarrays with sum = target
private int countSubarraySum(int[] nums, int target) {
    Map<Integer, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0, 1);
    
    int prefix = 0;
    int count = 0;
    
    for (int num : nums) {
        prefix += num;
        count += prefixCount.getOrDefault(prefix - target, 0);
        prefixCount.put(prefix, prefixCount.getOrDefault(prefix, 0) + 1);
    }
    
    return count;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Not resetting compressed array | Accumulates across different r1 | Reset for each new r1 |
| O(m²n²) brute force | Too slow for large matrices | Use dimension reduction |
| Forgetting to add row incrementally | Recomputing from scratch each time | Add row r2 to existing compressed |

---

## Complexity Analysis

| Metric | Value | Why |
|--------|-------|-----|
| Time | O(m² × n) | m² row pairs, O(n) for each 1D problem |
| Space | O(n) | Compressed array + HashMap |

---

## Mind-Map Anchor

```
COUNT SUBMATRICES = TARGET
       │
       ▼
┌─────────────────────────┐
│ Dimension Reduction     │
│                         │
│ Fix rows (r1, r2)       │
│ Compress to 1D array    │
│ Apply Pattern 1         │
│                         │
│ Time: O(m² × n)         │
└─────────────────────────┘
```

**Memory phrase:** "Fix two rows, compress columns, solve 1D — dimension reduction!"

---

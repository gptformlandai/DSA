# Section 04 — Binary Search Patterns Deep Dive (MAANG L5 Coverage)

--

# INDEX — Quick Navigation (20 Patterns)

## Core Concepts
| Section | Description |
|-----|-------|
| [The "One Sentence"](#the-one-sentence-that-unlocks-all-binary-search-problems) | Unlocks all BS problems |
| [3 Templates](#the-3-binary-search-templates) | Your weapons |
| [Decision Tree](#the-master-decision-tree-pick-your-template-in-10-seconds) | Pick your template |

--

## Classical Binary Search (0-3)
| # | Pattern | LeetCode |
|--|-----|-----|
| 0 | [Binary Search](#pattern-0-binary-search-leetcode-704) | 704 |
| 1 | [Search Insert Position](#pattern-1-search-insert-position-leetcode-35) | 35 |
| 2 | [First Bad Version](#pattern-2-first-bad-version-leetcode-278) | 278 |
| 3 | [Sqrt(x)](#pattern-3-sqrtx-leetcode-69) | 69 |

## First/Last Position (4-5)
| # | Pattern | LeetCode |
|--|-----|-----|
| 4 | [Find First and Last Position](#pattern-4-find-first-and-last-position-of-element-in-sorted-array-leetcode-34) | 34 |
| 5 | [Find Peak Element](#pattern-5-find-peak-element-leetcode-162) | 162 |

## Rotated Array Family (6-9)
| # | Pattern | LeetCode |
|--|-----|-----|
| 6 | [Search in Rotated Sorted Array](#pattern-6-search-in-rotated-sorted-array-leetcode-33) | 33 |
| 7 | [Search in Rotated Sorted Array II](#pattern-7-search-in-rotated-sorted-array-ii-leetcode-81) | 81 |
| 8 | [Find Minimum in Rotated Sorted Array](#pattern-8-find-minimum-in-rotated-sorted-array-leetcode-153) | 153 |
| 9 | [Find Minimum in Rotated Sorted Array II](#pattern-9-find-minimum-in-rotated-sorted-array-ii-leetcode-154) | 154 |

## Binary Search on Answer (10-14)
| # | Pattern | LeetCode |
|--|-----|-----|
| 10 | [Koko Eating Bananas](#pattern-10-koko-eating-bananas-leetcode-875) | 875 |
| 11 | [Capacity To Ship Packages](#pattern-11-capacity-to-ship-packages-within-d-days-leetcode-1011) | 1011 |
| 12 | [Split Array Largest Sum](#pattern-12-split-array-largest-sum-leetcode-410) | 410 |
| 13 | [Minimum Number of Days to Make m Bouquets](#pattern-13-minimum-number-of-days-to-make-m-bouquets-leetcode-1482) | 1482 |
| 14 | [Magnetic Force Between Two Balls](#pattern-14-magnetic-force-between-two-balls-leetcode-1552) | 1552 |

## 2D Binary Search (15-16)
| # | Pattern | LeetCode |
|--|-----|-----|
| 15 | [Search a 2D Matrix](#pattern-15-search-a-2d-matrix-leetcode-74) | 74 |
| 16 | [Search a 2D Matrix II](#pattern-16-search-a-2d-matrix-ii-leetcode-240) | 240 |

## Advanced Binary Search (17-19)
| # | Pattern | LeetCode |
|--|-----|-----|
| 17 | [Median of Two Sorted Arrays](#pattern-17-median-of-two-sorted-arrays-leetcode-4) | 4 |
| 18 | [Find K-th Smallest Pair Distance](#pattern-18-find-k-th-smallest-pair-distance-leetcode-719) | 719 |
| 19 | [Aggressive Cows / Maximize Minimum Distance](#pattern-19-aggressive-cows--maximize-minimum-distance-classic) | Classic |

## Reference Sections
| Section |
|-----|
| [MAANG Coverage Map](#maang-coverage-map) |
| [Pattern Recognition Cheat Sheet](#pattern-recognition-cheat-sheet) |
| [Mastery Checklist](#mastery-checklist) |

--

# The "One Sentence That Unlocks All Binary Search Problems"

> **"Binary Search finds a BOUNDARY in a MONOTONIC space by repeatedly halving the search range."**

That's the entire subject. Every binary search problem — from easy to hard — is just:
1. **Identify** the search space (array indices OR answer range)
2. **Define** the monotonic property (what makes left different from right)
3. **Choose** the right template based on what you're finding
4. **Shrink** the range correctly until you find the boundary

--

## 📋 THE JUNIOR DEV CHEAT CARD (Memorize This!)

```
╔═══════════════════════════════════════════════════════════════════════════════╗
║                     BINARY SEARCH PROBLEM? USE THIS!                           ║
╠═══════════════════════════════════════════════════════════════════════════════╣
║                                                                                ║
║  STEP 1: "What am I searching for?"                                           ║
║                                                                                ║
║  ┌──────────────────────────────────────────────────────────────────────────┐ ║
║  │ "Exact value in sorted array"  → Template 1 (left <= right)              │ ║
║  │ "First/Last position"          → Template 2 (left < right)               │ ║
║  │ "Minimum/Maximum that works"   → Template 3 (Binary Search on Answer)    │ ║
║  └──────────────────────────────────────────────────────────────────────────┘ ║
║                                                                                ║
║  ═══════════════════════════════════════════════════════════════════════════  ║
║                                                                                ║
║  THE GOLDEN RULE: "MONOTONIC PROPERTY!"                                       ║
║                                                                                ║
║  Binary Search ONLY works when there's a monotonic property:                  ║
║  - Left side: all FALSE (or all TRUE)                                         ║
║  - Right side: all TRUE (or all FALSE)                                        ║
║  - You're finding the BOUNDARY between them                                   ║
║                                                                                ║
║  ═══════════════════════════════════════════════════════════════════════════  ║
║                                                                                ║
║  THE 3 TEMPLATES:                                                             ║
║                                                                                ║
║  ┌──────────────────┬───────────────────────────────────────────────────────┐ ║
║  │ Template 1       │ while (left <= right)                                 │ ║
║  │ Exact Match      │   mid = left + (right - left) / 2                     │ ║
║  │                  │   if (arr[mid] == target) return mid                  │ ║
║  │                  │   else if (arr[mid] < target) left = mid + 1          │ ║
║  │                  │   else right = mid - 1                                │ ║
║  │                  │ return -1                                             │ ║
║  ├──────────────────┼───────────────────────────────────────────────────────┤ ║
║  │ Template 2       │ while (left < right)                                  │ ║
║  │ Boundary         │   mid = left + (right - left) / 2                     │ ║
║  │ (First True)     │   if (condition(mid)) right = mid                     │ ║
║  │                  │   else left = mid + 1                                 │ ║
║  │                  │ return left                                           │ ║
║  ├──────────────────┼───────────────────────────────────────────────────────┤ ║
║  │ Template 3       │ while (left < right)                                  │ ║
║  │ BS on Answer     │   mid = left + (right - left) / 2                     │ ║
║  │ (Min Valid)      │   if (canAchieve(mid)) right = mid                    │ ║
║  │                  │   else left = mid + 1                                 │ ║
║  │                  │ return left                                           │ ║
║  └──────────────────┴───────────────────────────────────────────────────────┘ ║
║                                                                                ║
║  ═══════════════════════════════════════════════════════════════════════════  ║
║                                                                                ║
║  COMMON PITFALLS:                                                             ║
║                                                                                ║
║  1. Integer Overflow: Use mid = left + (right - left) / 2                     ║
║     NOT mid = (left + right) / 2                                              ║
║                                                                                ║
║  2. Infinite Loop: When using left < right with right = mid,                  ║
║     use mid = left + (right - left) / 2 (rounds down)                         ║
║     When using left = mid, use mid = left + (right - left + 1) / 2 (rounds up)║
║                                                                                ║
║  3. Off-by-one: Template 1 returns -1 if not found                            ║
║     Template 2 returns left (the boundary position)                           ║
║                                                                                ║
╚═══════════════════════════════════════════════════════════════════════════════╝
```

--

## 🚀 QUICK START: The 60-Second Binary Search Approach

### The ONE Question That Solves Most Problems:

> **"What is the MONOTONIC PROPERTY that divides my search space into two halves?"**

### The Simple Decision Process:

```
1. "Looking for exact value?"
   → Template 1: while (left <= right)
   → Return mid when found, -1 when not found
   → Classic binary search

2. "Looking for first/last occurrence or boundary?"
   → Template 2: while (left < right)
   → Shrink towards the boundary
   → Return left (the boundary position)

3. "Looking for minimum/maximum valid answer?"
   → Template 3: Binary Search on Answer
   → Define canAchieve(x) predicate
   → Search the answer space, not the array!
```

### The "Monotonic Property" Mantra

This is the KEY insight for binary search:

```
WRONG THINKING:
"I need to find something in a sorted array"

RIGHT THINKING:
"I need to find the BOUNDARY where a property changes from FALSE to TRUE"

Example: Find first element >= 5 in [1, 2, 4, 5, 5, 7, 9]
         Property: arr[i] >= 5
         Array:    [1, 2, 4, 5, 5, 7, 9]
         Property: [F, F, F, T, T, T, T]
                          ↑
                    First TRUE = Answer!
```

--

# Zero to Hero: Understanding Binary Search from First Principles

## Why Binary Search Exists

Imagine you have a phone book with 1 million names sorted alphabetically. To find "Smith":

**Linear Search (Naive):**
- Check each name one by one
- Worst case: 1,000,000 comparisons
- Time: O(n)

**Binary Search (Smart):**
- Open to the middle: "M" - Smith comes after, go right half
- Open to middle of right half: "T" - Smith comes before, go left half
- Keep halving until found
- Worst case: log₂(1,000,000) ≈ 20 comparisons!
- Time: O(log n)

## The Core Insight

Binary search works because of **MONOTONICITY**:
- Everything to the LEFT of the answer has one property
- Everything to the RIGHT of the answer has the opposite property
- The answer is at the BOUNDARY

```
Search Space:  [  FALSE  FALSE  FALSE  |  TRUE  TRUE  TRUE  ]
                                       ↑
                              BOUNDARY (What we're finding!)
```

## The Three Levels of Binary Search Mastery

### Level 1: Array Index Search
- Search space: indices 0 to n-1
- Looking for: specific value or boundary in array
- Examples: LC 704, LC 35, LC 34

### Level 2: Modified Array Search
- Search space: indices, but array has special structure
- Looking for: value in rotated/special array
- Examples: LC 33, LC 153, LC 162

### Level 3: Binary Search on Answer
- Search space: possible ANSWERS (not array indices!)
- Looking for: minimum/maximum valid answer
- Examples: LC 875, LC 1011, LC 410

--

# The 3 Binary Search Templates

## Template 1: Standard Binary Search (Exact Match)

**When to use:** Finding an exact value in a sorted array

**The Pattern:**
```
[1, 3, 5, 7, 9, 11, 13]  Target: 7
 L        M          R

Step 1: mid = 3 (value 7) == target → Found!
```

**Code Template:**
```java
public int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    
    while (left <= right) {  // Note: <= not <
        int mid = left + (right - left) / 2;  // Avoid overflow
        
        if (arr[mid] == target) {
            return mid;  // Found!
        } else if (arr[mid] < target) {
            left = mid + 1;  // Target in right half
        } else {
            right = mid - 1;  // Target in left half
        }
    }
    
    return -1;  // Not found
}
```

**Key Characteristics:**
- Loop condition: `left <= right`
- Terminates when: `left > right` (search space empty)
- Returns: exact index or -1
- Both pointers move: `left = mid + 1` and `right = mid - 1`

**Visual Trace:**
```
Array: [1, 3, 5, 7, 9, 11, 13], Target: 9

Iteration 1: left=0, right=6, mid=3
             arr[3]=7 < 9, so left = 4
             [1, 3, 5, 7, 9, 11, 13]
                          L   M    R

Iteration 2: left=4, right=6, mid=5
             arr[5]=11 > 9, so right = 4
             [1, 3, 5, 7, 9, 11, 13]
                          LR
                          M

Iteration 3: left=4, right=4, mid=4
             arr[4]=9 == 9, return 4!
```

--

## Template 2: Boundary Binary Search (First/Last Position)

**When to use:** Finding the first or last position where a condition is true

**The Pattern:**
```
Find FIRST element >= 5:
[1, 2, 4, 5, 5, 7, 9]
 F  F  F  T  T  T  T   (Property: >= 5)
          ↑
    First TRUE = Answer
```

**Code Template (Find First TRUE):**
```java
public int findFirstTrue(int[] arr, int target) {
    int left = 0, right = arr.length;  // Note: right can be arr.length
    
    while (left < right) {  // Note: < not <=
        int mid = left + (right - left) / 2;
        
        if (arr[mid] >= target) {  // Condition is TRUE
            right = mid;  // Answer is mid or to the left
        } else {
            left = mid + 1;  // Answer is to the right
        }
    }
    
    return left;  // First position where condition is TRUE
}
```

**Code Template (Find Last TRUE):**
```java
public int findLastTrue(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    
    while (left < right) {
        int mid = left + (right - left + 1) / 2;  // Round UP to avoid infinite loop
        
        if (arr[mid] <= target) {  // Condition is TRUE
            left = mid;  // Answer is mid or to the right
        } else {
            right = mid - 1;  // Answer is to the left
        }
    }
    
    return left;  // Last position where condition is TRUE
}
```

**Key Characteristics:**
- Loop condition: `left < right`
- Terminates when: `left == right` (converged to boundary)
- Returns: boundary position (may need validation)
- For first TRUE: `right = mid` (keep mid as candidate)
- For last TRUE: `left = mid` with ceiling division

**Critical: Avoiding Infinite Loops**
```
When using left = mid:
  - MUST use mid = left + (right - left + 1) / 2 (ceiling)
  - Otherwise: left=0, right=1 → mid=0 → left=0 (infinite loop!)

When using right = mid:
  - Use mid = left + (right - left) / 2 (floor) - this is safe
```

--

## Template 3: Binary Search on Answer (Monotonic Predicate)

**When to use:** Finding minimum/maximum value that satisfies a condition

**The Pattern:**
```
Koko eating bananas: What's the minimum speed to finish in H hours?

Speed:     1   2   3   4   5   6   7   8   9   10
Can finish: F   F   F   T   T   T   T   T   T   T
                        ↑
                  Minimum valid speed = 4
```

**Code Template:**
```java
public int binarySearchOnAnswer(int[] arr, int constraint) {
    int left = minPossibleAnswer;
    int right = maxPossibleAnswer;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        if (canAchieve(arr, mid, constraint)) {
            right = mid;  // mid works, try smaller
        } else {
            left = mid + 1;  // mid doesn't work, need larger
        }
    }
    
    return left;  // Minimum valid answer
}

// The key helper function - defines the monotonic property
private boolean canAchieve(int[] arr, int candidate, int constraint) {
    // Check if 'candidate' answer satisfies the constraint
    // This function MUST be monotonic!
}
```

**Key Characteristics:**
- Search space: possible ANSWERS, not array indices
- Requires: monotonic predicate function
- For minimum valid: `right = mid` when valid
- For maximum valid: `left = mid` when valid (use ceiling division)

**The Monotonic Predicate Pattern:**
```
For MINIMUM valid answer (most common):
- canAchieve(small) = FALSE
- canAchieve(large) = TRUE
- Find first TRUE

For MAXIMUM valid answer:
- canAchieve(small) = TRUE
- canAchieve(large) = FALSE
- Find last TRUE
```

--

# The Master Decision Tree — Pick Your Template in 10 Seconds

```
                    ┌─────────────────────────────────────┐
                    │   What are you searching for?       │
                    └─────────────────────────────────────┘
                                      │
              ┌───────────────────────┼───────────────────────┐
              │                       │                       │
              ▼                       ▼                       ▼
    ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
    │  Exact Value    │    │ First/Last      │    │ Min/Max Answer  │
    │  in Array       │    │ Boundary        │    │ That Works      │
    └─────────────────┘    └─────────────────┘    └─────────────────┘
              │                       │                       │
              ▼                       ▼                       ▼
    ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
    │  TEMPLATE 1     │    │  TEMPLATE 2     │    │  TEMPLATE 3     │
    │  left <= right  │    │  left < right   │    │  left < right   │
    │  return mid     │    │  return left    │    │  + canAchieve() │
    └─────────────────┘    └─────────────────┘    └─────────────────┘
              │                       │                       │
              ▼                       ▼                       ▼
    ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
    │ LC 704, 35, 69  │    │ LC 34, 162, 153 │    │ LC 875, 1011,   │
    │ LC 278, 74      │    │ LC 33, 81       │    │ LC 410, 1482    │
    └─────────────────┘    └─────────────────┘    └─────────────────┘
```

--

# Template Comparison Table

| Aspect | Template 1 | Template 2 | Template 3 |
|----|------|------|------|
| **Loop Condition** | `left <= right` | `left < right` | `left < right` |
| **Termination** | `left > right` | `left == right` | `left == right` |
| **Mid Calculation** | Floor | Floor (first) / Ceiling (last) | Floor (min) / Ceiling (max) |
| **Return Value** | `mid` or `-1` | `left` (boundary) | `left` (answer) |
| **Use Case** | Exact match | First/Last position | Min/Max valid answer |
| **Search Space** | Array indices | Array indices | Answer range |

--

--

# Pattern 0: Binary Search (LeetCode 704)

## Problem Statement
Given a sorted array of integers `nums` and a target value `target`, return the index if the target is found. If not, return -1.

## Pattern Recognition Signal
- ✅ Sorted array
- ✅ Find exact value
- ✅ Return index or -1
- 🎯 **Template 1: Standard Binary Search**

## Mental Model: The Dictionary Lookup

Imagine looking up a word in a physical dictionary:
1. Open to the middle
2. Is your word before or after? Go to that half
3. Repeat until found

You never check every page - you eliminate half the dictionary each time!

## Visual Dry Run

```
Array: [−1, 0, 3, 5, 9, 12], Target: 9

Initial: left=0, right=5
         [−1, 0, 3, 5, 9, 12]
          L        M       R

Step 1: mid = 2, arr[2] = 3 < 9
        Move left to mid + 1 = 3
        [−1, 0, 3, 5, 9, 12]
                   L  M   R

Step 2: mid = 4, arr[4] = 9 == 9
        Found! Return 4

═══════════════════════════════════════════════════════════════

Array: [−1, 0, 3, 5, 9, 12], Target: 2

Initial: left=0, right=5
         [−1, 0, 3, 5, 9, 12]
          L        M       R

Step 1: mid = 2, arr[2] = 3 > 2
        Move right to mid - 1 = 1
        [−1, 0, 3, 5, 9, 12]
          L  R
             M

Step 2: mid = 0, arr[0] = -1 < 2
        Move left to mid + 1 = 1
        [−1, 0, 3, 5, 9, 12]
             LR
             M

Step 3: mid = 1, arr[1] = 0 < 2
        Move left to mid + 1 = 2
        left > right, exit loop
        Return -1 (not found)
```

## Code Implementation

```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            // Avoid integer overflow: don't use (left + right) / 2
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;  // Found the target!
            } else if (nums[mid] < target) {
                left = mid + 1;  // Target is in right half
            } else {
                right = mid - 1;  // Target is in left half
            }
        }
        
        return -1;  // Target not found
    }
}
```

```python
class Solution:
    def search(self, nums: List[int], target: int) -> int:
        left, right = 0, len(nums) - 1
        
        while left <= right:
            mid = left + (right - left) // 2
            
            if nums[mid] == target:
                return mid
            elif nums[mid] < target:
                left = mid + 1
            else:
                right = mid - 1
        
        return -1
```

## Complexity Analysis
- **Time:** O(log n) - halving search space each iteration
- **Space:** O(1) - only using pointers

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| `mid = (left + right) / 2` | Integer overflow when left + right > INT_MAX | `mid = left + (right - left) / 2` |
| `while (left < right)` | Misses the case when target is at the last remaining position | `while (left <= right)` |
| `left = mid` or `right = mid` | Can cause infinite loop | `left = mid + 1` and `right = mid - 1` |
| Not checking array bounds | Empty array causes crash | Check `nums.length == 0` first |

## Mind-Map Anchor

```
🔍 BINARY SEARCH (LC 704)
│
├── Signal: "sorted array + find exact value"
├── Template: 1 (left <= right)
├── Key: mid = left + (right - left) / 2
├── Return: mid when found, -1 when not
└── Mantra: "Equal? Found! Less? Go right! More? Go left!"
```

--

# Pattern 1: Search Insert Position (LeetCode 35)

## Problem Statement
Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

## Pattern Recognition Signal
- ✅ Sorted array
- ✅ Find position (exact or insertion point)
- ✅ "Where would it go?"
- 🎯 **Template 2: Boundary Search (First >= target)**

## Mental Model: Finding Your Seat

Imagine a movie theater with numbered seats (sorted). You have ticket #7:
- If seat 7 exists, sit there
- If not, find the first seat number >= 7 (that's where you'd insert yourself)

## Visual Dry Run

```
Array: [1, 3, 5, 6], Target: 5 (exists)

Initial: left=0, right=4 (length)
         [1, 3, 5, 6]
          L     M    R

Step 1: mid = 2, arr[2] = 5 >= 5 ✓
        right = mid = 2
        [1, 3, 5, 6]
          L  R
             M

Step 2: mid = 1, arr[1] = 3 < 5
        left = mid + 1 = 2
        [1, 3, 5, 6]
             LR

left == right, return 2 ✓

═══════════════════════════════════════════════════════════════

Array: [1, 3, 5, 6], Target: 2 (doesn't exist)

Initial: left=0, right=4
         [1, 3, 5, 6]
          L     M    R

Step 1: mid = 2, arr[2] = 5 >= 2 ✓
        right = mid = 2
        [1, 3, 5, 6]
          L  R
             M

Step 2: mid = 1, arr[1] = 3 >= 2 ✓
        right = mid = 1
        [1, 3, 5, 6]
          LR
          M

Step 3: mid = 0, arr[0] = 1 < 2
        left = mid + 1 = 1
        [1, 3, 5, 6]
             LR

left == right, return 1 ✓
(Insert 2 at index 1: [1, 2, 3, 5, 6])

═══════════════════════════════════════════════════════════════

Array: [1, 3, 5, 6], Target: 7 (larger than all)

Initial: left=0, right=4
         [1, 3, 5, 6]
          L     M    R

Step 1: mid = 2, arr[2] = 5 < 7
        left = mid + 1 = 3
        [1, 3, 5, 6]
                L  R
                M

Step 2: mid = 3, arr[3] = 6 < 7
        left = mid + 1 = 4
        [1, 3, 5, 6]
                   LR (both at index 4)

left == right, return 4 ✓
(Insert 7 at index 4: [1, 3, 5, 6, 7])
```

## Code Implementation

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length;  // Note: can insert at the end
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] >= target) {
                right = mid;  // This could be the answer, or answer is to the left
            } else {
                left = mid + 1;  // Answer is to the right
            }
        }
        
        return left;  // First position where nums[i] >= target
    }
}
```

```python
class Solution:
    def searchInsert(self, nums: List[int], target: int) -> int:
        left, right = 0, len(nums)
        
        while left < right:
            mid = left + (right - left) // 2
            
            if nums[mid] >= target:
                right = mid
            else:
                left = mid + 1
        
        return left
```

## Alternative: Using Template 1

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        // When not found, left is the insertion point
        return left;
    }
}
```

## Complexity Analysis
- **Time:** O(log n)
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| `right = nums.length - 1` | Can't handle insertion at the end | `right = nums.length` |
| Using `>` instead of `>=` | Finds wrong boundary | Use `>=` for first position >= target |
| Returning `mid` | Template 2 converges to `left == right` | Return `left` |

## Mind-Map Anchor

```
📍 SEARCH INSERT (LC 35)
│
├── Signal: "sorted + find OR insert position"
├── Template: 2 (left < right)
├── Key: right = nums.length (can insert at end)
├── Condition: nums[mid] >= target
├── Return: left (first position >= target)
└── Mantra: "Find the first seat that fits!"
```

--

# Pattern 2: First Bad Version (LeetCode 278)

## Problem Statement
You have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad. Given an API `bool isBadVersion(version)`, find the first bad version.

## Pattern Recognition Signal
- ✅ Linear sequence (versions 1 to n)
- ✅ Monotonic property (good, good, ..., bad, bad, bad)
- ✅ Find FIRST occurrence
- 🎯 **Template 2: Boundary Search (First TRUE)**

## Mental Model: The Assembly Line

Imagine a factory assembly line producing widgets:
- At some point, a machine broke
- All widgets AFTER that point are defective
- Find the FIRST defective widget

```
Widget #:  1   2   3   4   5   6   7   8   9   10
Quality:   ✓   ✓   ✓   ✓   ✗   ✗   ✗   ✗   ✗   ✗
                           ↑
                    First Bad = 5
```

## Visual Dry Run

```
Versions: [1, 2, 3, 4, 5], First Bad = 4

Initial: left=1, right=5
         [1, 2, 3, 4, 5]
          L     M     R
         [G, G, G, B, B]

Step 1: mid = 3, isBadVersion(3) = false
        left = mid + 1 = 4
        [1, 2, 3, 4, 5]
                   L  R
                   M

Step 2: mid = 4, isBadVersion(4) = true
        right = mid = 4
        [1, 2, 3, 4, 5]
                   LR

left == right, return 4 ✓

═══════════════════════════════════════════════════════════════

Versions: [1, 2, 3, 4, 5], First Bad = 1 (all bad)

Initial: left=1, right=5
         [1, 2, 3, 4, 5]
          L     M     R
         [B, B, B, B, B]

Step 1: mid = 3, isBadVersion(3) = true
        right = mid = 3
        [1, 2, 3, 4, 5]
          L  R
             M

Step 2: mid = 2, isBadVersion(2) = true
        right = mid = 2
        [1, 2, 3, 4, 5]
          LR

Step 3: mid = 1, isBadVersion(1) = true
        right = mid = 1
        [1, 2, 3, 4, 5]
          LR

left == right, return 1 ✓
```

## Code Implementation

```java
/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int left = 1;
        int right = n;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (isBadVersion(mid)) {
                right = mid;  // This is bad, but there might be earlier bad ones
            } else {
                left = mid + 1;  // This is good, first bad is after this
            }
        }
        
        return left;  // First bad version
    }
}
```

```python
# The isBadVersion API is already defined for you.
# def isBadVersion(version: int) -> bool:

class Solution:
    def firstBadVersion(self, n: int) -> int:
        left, right = 1, n
        
        while left < right:
            mid = left + (right - left) // 2
            
            if isBadVersion(mid):
                right = mid
            else:
                left = mid + 1
        
        return left
```

## Complexity Analysis
- **Time:** O(log n) - halving each time
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| `left = 0` | Versions start from 1, not 0 | `left = 1` |
| `mid = (left + right) / 2` | Overflow when n is close to INT_MAX | `mid = left + (right - left) / 2` |
| `right = mid - 1` when bad | Might skip the first bad version | `right = mid` (keep it as candidate) |
| Using Template 1 | Works but less elegant for "first occurrence" | Template 2 is cleaner |

## Mind-Map Anchor

```
🐛 FIRST BAD VERSION (LC 278)
│
├── Signal: "first occurrence of TRUE in monotonic sequence"
├── Template: 2 (left < right)
├── Key: right = mid when TRUE (keep as candidate)
├── Return: left (first TRUE position)
└── Mantra: "Bad? Could be first, check left. Good? First bad is right."
```

--

# Pattern 3: Sqrt(x) (LeetCode 69)

## Problem Statement
Given a non-negative integer x, return the square root of x rounded down to the nearest integer.

## Pattern Recognition Signal
- ✅ Find a value in a range
- ✅ Monotonic property (k² <= x becomes FALSE at some point)
- ✅ Find LAST valid value
- 🎯 **Template 2: Boundary Search (Last TRUE)** or **Template 3: Binary Search on Answer**

## Mental Model: The Guessing Game

You're playing a guessing game:
- "Is 5² <= 8?" Yes! (5² = 25... wait, no!)
- Let's be systematic: find the LARGEST k where k² <= x

```
x = 8
k:     0   1   2   3   4   5   ...
k²:    0   1   4   9   16  25  ...
k²<=8: T   T   T   F   F   F   ...
                   ↑
           Last TRUE = 2 (Answer!)
```

## Visual Dry Run

```
x = 8, find sqrt(8) = 2

Search space: [0, 8]

Initial: left=0, right=8
         k²<=8: [T, T, T, F, F, F, F, F, F]
                 0  1  2  3  4  5  6  7  8
                 L           M           R

Step 1: mid = 4, 4² = 16 > 8
        right = mid - 1 = 3
         k²<=8: [T, T, T, F]
                 0  1  2  3
                 L     M  R

Step 2: mid = 1, 1² = 1 <= 8
        left = mid + 1 = 2
         k²<=8: [T, T, T, F]
                 0  1  2  3
                       L  R
                       M

Step 3: mid = 2, 2² = 4 <= 8
        left = mid + 1 = 3
         k²<=8: [T, T, T, F]
                 0  1  2  3
                          LR

Step 4: mid = 3, 3² = 9 > 8
        right = mid - 1 = 2
        left > right, exit

Return right = 2 ✓

═══════════════════════════════════════════════════════════════

x = 16, find sqrt(16) = 4 (perfect square)

Search space: [0, 16]

Step 1: mid = 8, 64 > 16, right = 7
Step 2: mid = 3, 9 < 16, left = 4
Step 3: mid = 5, 25 > 16, right = 4
Step 4: mid = 4, 16 == 16, left = 5
Step 5: left > right, exit

Return right = 4 ✓
```

## Code Implementation

### Approach 1: Template 1 Style (Find Last Valid)

```java
class Solution {
    public int mySqrt(int x) {
        if (x < 2) return x;
        
        int left = 1;
        int right = x / 2;  // sqrt(x) <= x/2 for x >= 2
        int result = 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long square = (long) mid * mid;  // Avoid overflow
            
            if (square == x) {
                return mid;  // Perfect square
            } else if (square < x) {
                result = mid;  // This works, try larger
                left = mid + 1;
            } else {
                right = mid - 1;  // Too big, try smaller
            }
        }
        
        return result;
    }
}
```

### Approach 2: Template 2 Style (Last TRUE)

```java
class Solution {
    public int mySqrt(int x) {
        if (x < 2) return x;
        
        int left = 1;
        int right = x / 2;
        
        while (left < right) {
            // Use ceiling to avoid infinite loop when left = mid
            int mid = left + (right - left + 1) / 2;
            
            if ((long) mid * mid <= x) {
                left = mid;  // mid works, try larger
            } else {
                right = mid - 1;  // mid too big
            }
        }
        
        return left;
    }
}
```

```python
class Solution:
    def mySqrt(self, x: int) -> int:
        if x < 2:
            return x
        
        left, right = 1, x // 2
        
        while left < right:
            mid = left + (right - left + 1) // 2  # Ceiling division
            
            if mid * mid <= x:
                left = mid
            else:
                right = mid - 1
        
        return left
```

## Complexity Analysis
- **Time:** O(log x)
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| `mid * mid` without casting | Integer overflow for large x | Use `(long) mid * mid` |
| `right = x` | Unnecessary large search space | `right = x / 2` (sqrt(x) <= x/2 for x >= 2) |
| Floor division with `left = mid` | Infinite loop | Use ceiling: `mid = left + (right - left + 1) / 2` |
| Forgetting x < 2 edge case | sqrt(0) = 0, sqrt(1) = 1 | Handle separately |

## Mind-Map Anchor

```
√ SQRT(X) (LC 69)
│
├── Signal: "find largest k where k² <= x"
├── Template: 2 (last TRUE) or 1 with result tracking
├── Key: Ceiling division when using left = mid
├── Overflow: Cast to long for mid * mid
├── Optimization: right = x / 2
└── Mantra: "Find the last number whose square fits!"
```

--

# Pattern 4: Find First and Last Position of Element in Sorted Array (LeetCode 34)

## Problem Statement
Given an array of integers `nums` sorted in non-decreasing order, find the starting and ending position of a given `target` value. If target is not found, return `[-1, -1]`.

## Pattern Recognition Signal
- ✅ Sorted array with duplicates
- ✅ Find FIRST and LAST occurrence
- ✅ Two boundary searches
- 🎯 **Template 2: Boundary Search (twice)**

## Mental Model: The Bookshelf

Imagine a bookshelf with books sorted by author name. You want to find ALL books by "Smith":
1. First, find where "Smith" books START (first occurrence)
2. Then, find where "Smith" books END (last occurrence)

```
Authors: [Adams, Brown, Smith, Smith, Smith, Taylor, Wilson]
          0      1      2      3      4      5       6
                        ↑             ↑
                     First=2       Last=4
```

## Visual Dry Run

```
Array: [5, 7, 7, 8, 8, 10], Target: 8

═══════════════════════════════════════════════════════════════
FINDING FIRST OCCURRENCE:

Property: nums[i] >= 8
Array:    [5, 7, 7, 8, 8, 10]
Property: [F, F, F, T, T, T ]
                   ↑
            First TRUE = 3

Initial: left=0, right=6
         [5, 7, 7, 8, 8, 10]
          L        M       R

Step 1: mid = 3, nums[3] = 8 >= 8 ✓
        right = 3
        [5, 7, 7, 8, 8, 10]
          L     R
             M

Step 2: mid = 1, nums[1] = 7 < 8
        left = 2
        [5, 7, 7, 8, 8, 10]
                L  R
                M

Step 3: mid = 2, nums[2] = 7 < 8
        left = 3
        [5, 7, 7, 8, 8, 10]
                   LR

First = 3 ✓

═══════════════════════════════════════════════════════════════
FINDING LAST OCCURRENCE:

Property: nums[i] <= 8
Array:    [5, 7, 7, 8, 8, 10]
Property: [T, T, T, T, T, F ]
                      ↑
            Last TRUE = 4

Initial: left=0, right=5
         [5, 7, 7, 8, 8, 10]
          L        M       R

Step 1: mid = 2, nums[2] = 7 <= 8 ✓
        left = 3 (using ceiling: mid = (0+5+1)/2 = 3)
        Actually let's use: mid = 3, nums[3] = 8 <= 8 ✓
        left = 4
        [5, 7, 7, 8, 8, 10]
                      L  R
                      M

Step 2: mid = 5, nums[5] = 10 > 8
        right = 4
        [5, 7, 7, 8, 8, 10]
                      LR

Last = 4 ✓

Result: [3, 4]
```

## Code Implementation

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] result = {-1, -1};
        if (nums.length == 0) return result;
        
        // Find first occurrence
        result[0] = findFirst(nums, target);
        
        // If first not found, last won't exist either
        if (result[0] == -1) return result;
        
        // Find last occurrence
        result[1] = findLast(nums, target);
        
        return result;
    }
    
    // Find first position where nums[i] == target
    private int findFirst(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] >= target) {
                right = mid;  // Could be first, or first is to the left
            } else {
                left = mid + 1;  // First is to the right
            }
        }
        
        // Verify we found the target
        return nums[left] == target ? left : -1;
    }
    
    // Find last position where nums[i] == target
    private int findLast(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            // Ceiling division to avoid infinite loop
            int mid = left + (right - left + 1) / 2;
            
            if (nums[mid] <= target) {
                left = mid;  // Could be last, or last is to the right
            } else {
                right = mid - 1;  // Last is to the left
            }
        }
        
        return nums[left] == target ? left : -1;
    }
}
```

```python
class Solution:
    def searchRange(self, nums: List[int], target: int) -> List[int]:
        if not nums:
            return [-1, -1]
        
        def find_first():
            left, right = 0, len(nums) - 1
            while left < right:
                mid = left + (right - left) // 2
                if nums[mid] >= target:
                    right = mid
                else:
                    left = mid + 1
            return left if nums[left] == target else -1
        
        def find_last():
            left, right = 0, len(nums) - 1
            while left < right:
                mid = left + (right - left + 1) // 2  # Ceiling
                if nums[mid] <= target:
                    left = mid
                else:
                    right = mid - 1
            return left if nums[left] == target else -1
        
        first = find_first()
        if first == -1:
            return [-1, -1]
        return [first, find_last()]
```

### Alternative: Using Lower/Upper Bound Concept

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int first = lowerBound(nums, target);
        
        // Check if target exists
        if (first == nums.length || nums[first] != target) {
            return new int[]{-1, -1};
        }
        
        // Last occurrence is one before the lower bound of target + 1
        int last = lowerBound(nums, target + 1) - 1;
        
        return new int[]{first, last};
    }
    
    // Returns first index where nums[i] >= target
    private int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
}
```

## Complexity Analysis
- **Time:** O(log n) - two binary searches
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| Single binary search | Only finds one occurrence | Need two searches |
| Not verifying target exists | Returns wrong index | Check `nums[left] == target` |
| Floor division for last | Infinite loop | Use ceiling: `(right - left + 1) / 2` |
| Not handling empty array | Index out of bounds | Check `nums.length == 0` |

## Mind-Map Anchor

```
🎯 FIRST AND LAST POSITION (LC 34)
│
├── Signal: "sorted array + duplicates + range"
├── Template: 2 (twice - first and last)
├── First: nums[mid] >= target → right = mid
├── Last: nums[mid] <= target → left = mid (ceiling!)
├── Verify: Check nums[left] == target
└── Mantra: "Two searches: first >= target, last <= target"
```

--

# Pattern 5: Find Peak Element (LeetCode 162)

## Problem Statement
A peak element is an element that is strictly greater than its neighbors. Given an array `nums`, find a peak element and return its index. The array may contain multiple peaks; return the index to any of the peaks.

Assume `nums[-1] = nums[n] = -∞`.

## Pattern Recognition Signal
- ✅ Array with local maxima
- ✅ "Greater than neighbors"
- ✅ nums[-1] = nums[n] = -∞ (boundaries are valleys)
- 🎯 **Template 2: Boundary Search (Climb the slope)**

## Mental Model: Mountain Climbing

Imagine you're blindfolded on a mountain range:
- You can only feel if you're going UP or DOWN
- If you're going UP, keep going (peak is ahead)
- If you're going DOWN, turn back (peak is behind)
- Eventually, you'll reach A peak (not necessarily the highest)

```
        Peak
         /\
        /  \
       /    \
      /      \
-∞ __/        \__ -∞

If slope is UP (nums[mid] < nums[mid+1]): go RIGHT
If slope is DOWN (nums[mid] > nums[mid+1]): go LEFT (or stay)
```

## Visual Dry Run

```
Array: [1, 2, 3, 1]

Visualization:
    3
   /\
  2  \
 /    1
1

Initial: left=0, right=3
         [1, 2, 3, 1]
          L     M  R

Step 1: mid = 1, nums[1]=2 < nums[2]=3 (going UP)
        Peak is to the right, left = mid + 1 = 2
        [1, 2, 3, 1]
                L  R
                M

Step 2: mid = 2, nums[2]=3 > nums[3]=1 (going DOWN)
        Peak is here or to the left, right = mid = 2
        [1, 2, 3, 1]
                LR

left == right, return 2 ✓ (nums[2] = 3 is a peak)

═══════════════════════════════════════════════════════════════

Array: [1, 2, 1, 3, 5, 6, 4]

Visualization:
          6
         /\
        5  4
       /
    3
   /\
  2  1
 /
1

Initial: left=0, right=6
         [1, 2, 1, 3, 5, 6, 4]
          L        M        R

Step 1: mid = 3, nums[3]=3 < nums[4]=5 (going UP)
        left = 4
        [1, 2, 1, 3, 5, 6, 4]
                      L  M  R

Step 2: mid = 5, nums[5]=6 > nums[6]=4 (going DOWN)
        right = 5
        [1, 2, 1, 3, 5, 6, 4]
                      L  R
                         M

Step 3: mid = 4, nums[4]=5 < nums[5]=6 (going UP)
        left = 5
        [1, 2, 1, 3, 5, 6, 4]
                         LR

left == right, return 5 ✓ (nums[5] = 6 is a peak)
```

## Code Implementation

```java
class Solution {
    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] < nums[mid + 1]) {
                // Going uphill, peak is to the right
                left = mid + 1;
            } else {
                // Going downhill or at peak, peak is here or to the left
                right = mid;
            }
        }
        
        // left == right, this is a peak
        return left;
    }
}
```

```python
class Solution:
    def findPeakElement(self, nums: List[int]) -> int:
        left, right = 0, len(nums) - 1
        
        while left < right:
            mid = left + (right - left) // 2
            
            if nums[mid] < nums[mid + 1]:
                # Ascending slope, peak is to the right
                left = mid + 1
            else:
                # Descending slope, peak is here or to the left
                right = mid
        
        return left
```

## Why This Works

The key insight is the **boundary conditions**: `nums[-1] = nums[n] = -∞`

This guarantees:
1. If we start going UP, we MUST eventually come DOWN (to -∞)
2. Therefore, a peak MUST exist between any uphill start and the boundary
3. By always moving towards the higher neighbor, we're guaranteed to find A peak

```
Case 1: Strictly increasing [1, 2, 3, 4, 5]
        Peak is at the end (before -∞)
        
Case 2: Strictly decreasing [5, 4, 3, 2, 1]
        Peak is at the start (after -∞)
        
Case 3: Mixed [1, 3, 2, 4, 1]
        Multiple peaks, we find any one
```

## Complexity Analysis
- **Time:** O(log n)
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| Checking both neighbors | Unnecessary and can cause out-of-bounds | Only compare `mid` with `mid + 1` |
| `right = mid - 1` when going down | Might skip the peak at mid | `right = mid` (keep mid as candidate) |
| Returning when `nums[mid] > nums[mid+1]` | Not at boundary yet | Continue until `left == right` |
| Forgetting boundary conditions | Might think no peak exists | `nums[-1] = nums[n] = -∞` guarantees a peak |

## Mind-Map Anchor

```
⛰️ FIND PEAK ELEMENT (LC 162)
│
├── Signal: "find local maximum + boundaries are -∞"
├── Template: 2 (left < right)
├── Key: Compare mid with mid+1 only
├── Going UP (mid < mid+1): left = mid + 1
├── Going DOWN (mid > mid+1): right = mid
├── Return: left (guaranteed to be a peak)
└── Mantra: "Always climb uphill, you'll reach a peak!"
```

--

# Pattern 6: Search in Rotated Sorted Array (LeetCode 33)

## Problem Statement
Given a rotated sorted array (rotated at some pivot), search for a target value. Return its index if found, otherwise return -1. All values are unique.

## Pattern Recognition Signal
- ✅ Sorted array that's been rotated
- ✅ Find exact value
- ✅ "Rotated at unknown pivot"
- 🎯 **Template 1 with extra logic to identify sorted half**

## Mental Model: The Broken Ruler

Imagine a ruler that was cut and the pieces swapped:

```
Original: [0, 1, 2, 3, 4, 5, 6, 7]
Cut at 4: [0, 1, 2, 3] | [4, 5, 6, 7]
Rotated:  [4, 5, 6, 7, 0, 1, 2, 3]
                      ↑
                   Pivot point
```

Key insight: **At least ONE half is always sorted!**

```
[4, 5, 6, 7, 0, 1, 2, 3]
 L        M           R

Left half [4,5,6,7]: SORTED (4 <= 7)
Right half [0,1,2,3]: SORTED (0 <= 3)

At mid=3 (value 7):
- Left half [4,5,6,7] is sorted (nums[L]=4 <= nums[mid]=7)
- Check if target is in sorted left half
- If yes, search left; if no, search right
```

## Visual Dry Run

```
Array: [4, 5, 6, 7, 0, 1, 2], Target: 0

Visualization:
    7
   /
  6
 /
5
|
4           2
            |
            1
            |
            0

Initial: left=0, right=6
         [4, 5, 6, 7, 0, 1, 2]
          L        M        R

Step 1: mid = 3, nums[mid] = 7 ≠ 0
        Is left half sorted? nums[0]=4 <= nums[3]=7 ✓ YES
        Is target in sorted left half? 4 <= 0 <= 7? NO
        Search right half: left = mid + 1 = 4
        
        [4, 5, 6, 7, 0, 1, 2]
                      L  M  R

Step 2: mid = 5, nums[mid] = 1 ≠ 0
        Is left half sorted? nums[4]=0 <= nums[5]=1 ✓ YES
        Is target in sorted left half? 0 <= 0 <= 1? YES
        Search left half: right = mid - 1 = 4
        
        [4, 5, 6, 7, 0, 1, 2]
                      LR
                      M

Step 3: mid = 4, nums[mid] = 0 == 0 ✓
        Found! Return 4

═══════════════════════════════════════════════════════════════

Array: [4, 5, 6, 7, 0, 1, 2], Target: 3 (not in array)

Initial: left=0, right=6
         [4, 5, 6, 7, 0, 1, 2]
          L        M        R

Step 1: mid = 3, nums[mid] = 7 ≠ 3
        Left half sorted: 4 <= 7 ✓
        Is 4 <= 3 <= 7? NO (3 < 4)
        Search right: left = 4

Step 2: mid = 5, nums[mid] = 1 ≠ 3
        Left half sorted: 0 <= 1 ✓
        Is 0 <= 3 <= 1? NO (3 > 1)
        Search right: left = 6

Step 3: mid = 6, nums[mid] = 2 ≠ 3
        Left half sorted: 2 <= 2 ✓
        Is 2 <= 3 <= 2? NO
        Search right: left = 7

left > right, return -1 ✓
```

## Code Implementation

```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            }
            
            // Determine which half is sorted
            if (nums[left] <= nums[mid]) {
                // Left half is sorted
                if (nums[left] <= target && target < nums[mid]) {
                    // Target is in sorted left half
                    right = mid - 1;
                } else {
                    // Target is in right half
                    left = mid + 1;
                }
            } else {
                // Right half is sorted
                if (nums[mid] < target && target <= nums[right]) {
                    // Target is in sorted right half
                    left = mid + 1;
                } else {
                    // Target is in left half
                    right = mid - 1;
                }
            }
        }
        
        return -1;
    }
}
```

```python
class Solution:
    def search(self, nums: List[int], target: int) -> int:
        left, right = 0, len(nums) - 1
        
        while left <= right:
            mid = left + (right - left) // 2
            
            if nums[mid] == target:
                return mid
            
            # Check which half is sorted
            if nums[left] <= nums[mid]:
                # Left half is sorted
                if nums[left] <= target < nums[mid]:
                    right = mid - 1
                else:
                    left = mid + 1
            else:
                # Right half is sorted
                if nums[mid] < target <= nums[right]:
                    left = mid + 1
                else:
                    right = mid - 1
        
        return -1
```

## The Decision Tree

```
                    ┌─────────────────────────────────────┐
                    │         nums[mid] == target?        │
                    └─────────────────────────────────────┘
                           │ NO                    │ YES
                           ▼                       ▼
              ┌────────────────────────┐      Return mid
              │ nums[left] <= nums[mid]│
              │   (Left half sorted?)  │
              └────────────────────────┘
                    │ YES           │ NO
                    ▼               ▼
         ┌──────────────────┐  ┌──────────────────┐
         │ Target in left?  │  │ Target in right? │
         │ left<=t<mid      │  │ mid<t<=right     │
         └──────────────────┘  └──────────────────┘
           │ YES    │ NO         │ YES    │ NO
           ▼        ▼            ▼        ▼
        right=     left=       left=    right=
        mid-1      mid+1       mid+1    mid-1
```

## Complexity Analysis
- **Time:** O(log n)
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| `nums[left] < nums[mid]` | Fails when left == mid (single element) | Use `<=` |
| `target <= nums[mid]` in left check | Should exclude mid (already checked) | Use `<` |
| Not checking both boundaries | Target might equal boundary | Check `nums[left] <= target` AND `target < nums[mid]` |
| Assuming array is always rotated | Array might not be rotated at all | Algorithm handles this case |

## Mind-Map Anchor

```
🔄 SEARCH IN ROTATED ARRAY (LC 33)
│
├── Signal: "rotated sorted array + find value"
├── Template: 1 (left <= right)
├── Key Insight: One half is ALWAYS sorted
├── Step 1: Find which half is sorted (nums[left] <= nums[mid])
├── Step 2: Check if target is in sorted half
├── Step 3: Search the appropriate half
└── Mantra: "Find sorted half, check if target fits, go there or opposite"
```

--

# Pattern 7: Search in Rotated Sorted Array II (LeetCode 81)

## Problem Statement
Same as LC 33, but the array may contain **duplicates**. Return true if target exists, false otherwise.

## Pattern Recognition Signal
- ✅ Rotated sorted array
- ✅ **Contains duplicates**
- ✅ Return boolean (exists or not)
- 🎯 **Template 1 with duplicate handling**

## Mental Model: The Foggy Ruler

Same as the broken ruler, but now some numbers are repeated:

```
[2, 5, 6, 0, 0, 1, 2]
 L        M        R

Problem: nums[left] = 2 = nums[right]
We can't tell which half is sorted!

Solution: Skip duplicates at boundaries
```

## The Duplicate Problem

```
Array: [1, 0, 1, 1, 1]
        L     M     R

nums[left] = 1
nums[mid] = 1
nums[right] = 1

Is left half sorted? nums[left] <= nums[mid]? 1 <= 1 ✓
But wait... left half is [1, 0, 1] which is NOT sorted!

The condition nums[left] <= nums[mid] doesn't work with duplicates!
```

## Visual Dry Run

```
Array: [2, 5, 6, 0, 0, 1, 2], Target: 0

Initial: left=0, right=6
         [2, 5, 6, 0, 0, 1, 2]
          L        M        R

Step 1: nums[left]=2 == nums[right]=2
        Can't determine sorted half!
        Skip duplicate: right-
        
         [2, 5, 6, 0, 0, 1, 2]
          L        M     R

Step 2: mid = 3, nums[mid] = 0
        nums[left]=2 > nums[mid]=0
        Right half is sorted: [0, 0, 1]
        Is 0 < 0 <= 1? NO (0 is not > 0)
        Search left: right = mid - 1 = 2
        
         [2, 5, 6, 0, 0, 1, 2]
          L  M  R

Step 3: mid = 1, nums[mid] = 5
        nums[left]=2 <= nums[mid]=5
        Left half is sorted: [2, 5]
        Is 2 <= 0 < 5? NO
        Search right: left = mid + 1 = 2
        
         [2, 5, 6, 0, 0, 1, 2]
                LR

Step 4: mid = 2, nums[mid] = 6 ≠ 0
        left > right after adjustment
        
Hmm, let me redo this more carefully...

═══════════════════════════════════════════════════════════════

Array: [2, 5, 6, 0, 0, 1, 2], Target: 0

Initial: left=0, right=6
         [2, 5, 6, 0, 0, 1, 2]
          L        M        R

Check: nums[left]=2 == nums[right]=2
       Skip: left++ (or right-)
       
         [2, 5, 6, 0, 0, 1, 2]
             L     M        R

Now: nums[left]=5 ≠ nums[right]=2, proceed normally

mid = 3, nums[mid] = 0 == target ✓
Return true!
```

## Code Implementation

```java
class Solution {
    public boolean search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return true;
            }
            
            // Handle duplicates: can't determine sorted half
            if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
                left++;
                right-;
            }
            // Left half is sorted
            else if (nums[left] <= nums[mid]) {
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            // Right half is sorted
            else {
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return false;
    }
}
```

```python
class Solution:
    def search(self, nums: List[int], target: int) -> bool:
        left, right = 0, len(nums) - 1
        
        while left <= right:
            mid = left + (right - left) // 2
            
            if nums[mid] == target:
                return True
            
            # Handle duplicates
            if nums[left] == nums[mid] == nums[right]:
                left += 1
                right -= 1
            # Left half is sorted
            elif nums[left] <= nums[mid]:
                if nums[left] <= target < nums[mid]:
                    right = mid - 1
                else:
                    left = mid + 1
            # Right half is sorted
            else:
                if nums[mid] < target <= nums[right]:
                    left = mid + 1
                else:
                    right = mid - 1
        
        return False
```

## Complexity Analysis
- **Time:** O(log n) average, **O(n) worst case** (all duplicates)
- **Space:** O(1)

## Why Worst Case is O(n)

```
Array: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1]
        L                    M                    R

Every step: nums[left] == nums[mid] == nums[right]
We can only skip one element at a time!
This degrades to linear search.
```

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| Using LC 33 solution directly | Fails with duplicates | Add duplicate handling |
| Only skipping left OR right | Need to shrink from both ends | `left++; right-;` |
| Checking `nums[left] == nums[mid]` only | Not sufficient | Check all three: left, mid, right |

## Mind-Map Anchor

```
🔄 SEARCH IN ROTATED II (LC 81)
│
├── Signal: "rotated + duplicates"
├── Template: 1 with duplicate handling
├── Key: When nums[left] == nums[mid] == nums[right]
│        → Can't determine sorted half
│        → Skip: left++, right-
├── Worst case: O(n) when all duplicates
└── Mantra: "Same at boundaries? Skip and retry!"
```

--

# Pattern 8: Find Minimum in Rotated Sorted Array (LeetCode 153)

## Problem Statement
Given a rotated sorted array with unique elements, find the minimum element.

## Pattern Recognition Signal
- ✅ Rotated sorted array
- ✅ Find minimum (not a specific target)
- ✅ Unique elements
- 🎯 **Template 2: Boundary Search (Find pivot)**

## Mental Model: Finding the Valley

In a rotated array, the minimum is at the "valley" - where the rotation happened:

```
Original: [1, 2, 3, 4, 5, 6, 7]
Rotated:  [4, 5, 6, 7, 1, 2, 3]
                      ↑
                   Valley (minimum)

The minimum is the only element smaller than its predecessor!
```

## Visual Dry Run

```
Array: [3, 4, 5, 1, 2]

Visualization:
      5
     /
    4
   /
  3           2
              |
              1 ← Minimum

Initial: left=0, right=4
         [3, 4, 5, 1, 2]
          L     M     R

Step 1: nums[mid]=5 > nums[right]=2
        Minimum is in right half (after the drop)
        left = mid + 1 = 3
        
         [3, 4, 5, 1, 2]
                   L  R
                   M

Step 2: nums[mid]=1 < nums[right]=2
        Minimum is in left half (including mid)
        right = mid = 3
        
         [3, 4, 5, 1, 2]
                   LR

left == right, return nums[3] = 1 ✓

═══════════════════════════════════════════════════════════════

Array: [2, 1] (simple rotation)

Initial: left=0, right=1
         [2, 1]
          L  R
          M

Step 1: nums[mid]=2 > nums[right]=1
        left = mid + 1 = 1
        
         [2, 1]
             LR

left == right, return nums[1] = 1 ✓

═══════════════════════════════════════════════════════════════

Array: [1, 2, 3, 4, 5] (not rotated)

Initial: left=0, right=4
         [1, 2, 3, 4, 5]
          L     M     R

Step 1: nums[mid]=3 < nums[right]=5
        Minimum is in left half (including mid)
        right = mid = 2
        
         [1, 2, 3, 4, 5]
          L  R
             M

Step 2: nums[mid]=2 < nums[right]=3
        right = mid = 1
        
         [1, 2, 3, 4, 5]
          LR

Step 3: nums[mid]=1 < nums[right]=2
        right = mid = 0
        
         [1, 2, 3, 4, 5]
          LR

left == right, return nums[0] = 1 ✓
```

## Code Implementation

```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[right]) {
                // Minimum is in right half (after mid)
                // The "drop" happens somewhere after mid
                left = mid + 1;
            } else {
                // Minimum is in left half (including mid)
                // mid could be the minimum
                right = mid;
            }
        }
        
        return nums[left];
    }
}
```

```python
class Solution:
    def findMin(self, nums: List[int]) -> int:
        left, right = 0, len(nums) - 1
        
        while left < right:
            mid = left + (right - left) // 2
            
            if nums[mid] > nums[right]:
                # Minimum is after mid
                left = mid + 1
            else:
                # Minimum is at mid or before
                right = mid
        
        return nums[left]
```

## Why Compare with Right, Not Left?

```
Compare with nums[right]:
- If nums[mid] > nums[right]: rotation point is in right half
- If nums[mid] < nums[right]: we're in the sorted part, min is left

Compare with nums[left] (DOESN'T WORK):
Array: [3, 4, 5, 1, 2]
mid = 2, nums[mid] = 5
nums[mid] > nums[left] (5 > 3) → Go right? WRONG! Min is at index 3.

The issue: nums[mid] > nums[left] is true for BOTH:
- When mid is before the rotation point
- When array is not rotated at all
```

## Complexity Analysis
- **Time:** O(log n)
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| Comparing with `nums[left]` | Ambiguous - can't distinguish cases | Compare with `nums[right]` |
| `right = mid - 1` | Might skip the minimum | `right = mid` (keep as candidate) |
| Using `left <= right` | Unnecessary, converges at `left == right` | Use `left < right` |
| Returning `nums[mid]` | Not at convergence point | Return `nums[left]` after loop |

## Mind-Map Anchor

```
📉 FIND MINIMUM IN ROTATED (LC 153)
│
├── Signal: "rotated array + find minimum"
├── Template: 2 (left < right)
├── Key: Compare nums[mid] with nums[right]
├── mid > right: min is in right half (left = mid + 1)
├── mid < right: min is in left half (right = mid)
├── Return: nums[left] after convergence
└── Mantra: "Compare with right, chase the drop!"
```

--

# Pattern 9: Find Minimum in Rotated Sorted Array II (LeetCode 154)

## Problem Statement
Same as LC 153, but the array may contain **duplicates**.

## Pattern Recognition Signal
- ✅ Rotated sorted array
- ✅ **Contains duplicates**
- ✅ Find minimum
- 🎯 **Template 2 with duplicate handling**

## Mental Model: Foggy Valley

Same as finding the valley, but fog (duplicates) obscures our view:

```
Array: [2, 2, 2, 0, 1, 2]
        L        M     R

nums[mid] = 2 = nums[right]
Can't tell if minimum is left or right!

Solution: Shrink the fog by removing one duplicate
```

## Visual Dry Run

```
Array: [2, 2, 2, 0, 1]

Initial: left=0, right=4
         [2, 2, 2, 0, 1]
          L     M     R

Step 1: nums[mid]=2 > nums[right]=1
        Minimum is in right half
        left = mid + 1 = 3
        
         [2, 2, 2, 0, 1]
                   L  R
                   M

Step 2: nums[mid]=0 < nums[right]=1
        Minimum is in left half (including mid)
        right = mid = 3
        
         [2, 2, 2, 0, 1]
                   LR

left == right, return nums[3] = 0 ✓

═══════════════════════════════════════════════════════════════

Array: [2, 2, 2, 0, 2] (duplicates at boundary)

Initial: left=0, right=4
         [2, 2, 2, 0, 2]
          L     M     R

Step 1: nums[mid]=2 == nums[right]=2
        Can't determine! Skip right duplicate.
        right-
        
         [2, 2, 2, 0, 2]
          L     M  R

Step 2: nums[mid]=2 > nums[right]=0
        left = mid + 1 = 3
        
         [2, 2, 2, 0, 2]
                   LR

left == right, return nums[3] = 0 ✓

═══════════════════════════════════════════════════════════════

Array: [10, 1, 10, 10, 10]

Initial: left=0, right=4
         [10, 1, 10, 10, 10]
          L      M        R

Step 1: nums[mid]=10 == nums[right]=10
        right-
        
         [10, 1, 10, 10, 10]
          L      M    R

Step 2: nums[mid]=10 == nums[right]=10
        right-
        
         [10, 1, 10, 10, 10]
          L   M  R

Step 3: nums[mid]=1 < nums[right]=10
        right = mid = 1
        
         [10, 1, 10, 10, 10]
          L   R

Step 4: nums[mid]=10 > nums[right]=1
        left = mid + 1 = 1
        
         [10, 1, 10, 10, 10]
              LR

left == right, return nums[1] = 1 ✓
```

## Code Implementation

```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[right]) {
                // Minimum is in right half
                left = mid + 1;
            } else if (nums[mid] < nums[right]) {
                // Minimum is in left half (including mid)
                right = mid;
            } else {
                // nums[mid] == nums[right], can't determine
                // Safe to remove right (if it's min, mid is also min)
                right-;
            }
        }
        
        return nums[left];
    }
}
```

```python
class Solution:
    def findMin(self, nums: List[int]) -> int:
        left, right = 0, len(nums) - 1
        
        while left < right:
            mid = left + (right - left) // 2
            
            if nums[mid] > nums[right]:
                left = mid + 1
            elif nums[mid] < nums[right]:
                right = mid
            else:
                # nums[mid] == nums[right]
                right -= 1
        
        return nums[left]
```

## Why `right-` is Safe

When `nums[mid] == nums[right]`:
- If `nums[right]` is the minimum, `nums[mid]` is also the minimum (same value)
- So we won't lose the minimum by removing `nums[right]`
- We're just removing a duplicate

## Complexity Analysis
- **Time:** O(log n) average, **O(n) worst case**
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| Using LC 153 solution | Fails with duplicates | Add `nums[mid] == nums[right]` case |
| `left++` when equal | Might skip minimum | Only `right-` is safe |
| Skipping both ends | Might skip minimum | Only skip one end |

## Mind-Map Anchor

```
📉 FIND MINIMUM IN ROTATED II (LC 154)
│
├── Signal: "rotated + duplicates + find minimum"
├── Template: 2 with duplicate handling
├── Key: When nums[mid] == nums[right]
│        → Can't determine direction
│        → Safe to do right- (won't lose min)
├── Worst case: O(n) when all duplicates
└── Mantra: "Equal at right? Shrink right, keep searching!"
```

--

# Pattern 10: Koko Eating Bananas (LeetCode 875)

## Problem Statement
Koko loves bananas. There are `n` piles of bananas, the `i-th` pile has `piles[i]` bananas. Guards will return in `h` hours. Koko can decide her eating speed `k` (bananas per hour). Each hour, she chooses a pile and eats `k` bananas. If the pile has fewer than `k` bananas, she eats all of them and won't eat any more during that hour. Return the minimum integer `k` such that she can eat all bananas within `h` hours.

## Pattern Recognition Signal
- ✅ "Minimum speed/rate to finish in time"
- ✅ Monotonic property: higher speed → always finishes
- ✅ Search for optimal VALUE, not index
- 🎯 **Template 3: Binary Search on Answer**

## Mental Model: The Speed Dial

Imagine a speed dial from 1 to max:

```
Speed:      1   2   3   4   5   6   7   8   9   10
Can finish: F   F   F   T   T   T   T   T   T   T
                        ↑
                 Minimum valid speed = 4

We're not searching an array - we're searching the ANSWER SPACE!
```

## Visual Dry Run

```
piles = [3, 6, 7, 11], h = 8

Step 1: Define search space
        - Minimum speed: 1 (eat at least 1 banana/hour)
        - Maximum speed: max(piles) = 11 (no need to go faster)
        
Step 2: Binary search on speed

Speed = 1: hours = ceil(3/1) + ceil(6/1) + ceil(7/1) + ceil(11/1)
                 = 3 + 6 + 7 + 11 = 27 hours > 8 ✗

Speed = 11: hours = ceil(3/11) + ceil(6/11) + ceil(7/11) + ceil(11/11)
                  = 1 + 1 + 1 + 1 = 4 hours <= 8 ✓

Binary search between 1 and 11:

Initial: left=1, right=11
         Speed: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
                 L           M                   R

Step 1: mid = 6
        hours = ceil(3/6) + ceil(6/6) + ceil(7/6) + ceil(11/6)
              = 1 + 1 + 2 + 2 = 6 <= 8 ✓
        Can finish! Try smaller: right = 6
        
         Speed: [1, 2, 3, 4, 5, 6]
                 L     M        R

Step 2: mid = 3
        hours = ceil(3/3) + ceil(6/3) + ceil(7/3) + ceil(11/3)
              = 1 + 2 + 3 + 4 = 10 > 8 ✗
        Can't finish! Need faster: left = 4
        
         Speed: [4, 5, 6]
                 L  M  R

Step 3: mid = 5
        hours = ceil(3/5) + ceil(6/5) + ceil(7/5) + ceil(11/5)
              = 1 + 2 + 2 + 3 = 8 <= 8 ✓
        Can finish! Try smaller: right = 5
        
         Speed: [4, 5]
                 L  R
                 M

Step 4: mid = 4
        hours = ceil(3/4) + ceil(6/4) + ceil(7/4) + ceil(11/4)
              = 1 + 2 + 2 + 3 = 8 <= 8 ✓
        Can finish! Try smaller: right = 4
        
         Speed: [4]
                 LR

left == right, return 4 ✓
```

## Code Implementation

```java
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        // Search space: [1, max(piles)]
        int left = 1;
        int right = 0;
        for (int pile : piles) {
            right = Math.max(right, pile);
        }
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (canFinish(piles, mid, h)) {
                right = mid;  // Can finish, try slower
            } else {
                left = mid + 1;  // Can't finish, need faster
            }
        }
        
        return left;
    }
    
    // Monotonic predicate: can Koko finish with speed k?
    private boolean canFinish(int[] piles, int k, int h) {
        int hours = 0;
        for (int pile : piles) {
            // Ceiling division: (pile + k - 1) / k
            hours += (pile + k - 1) / k;
            if (hours > h) return false;  // Early termination
        }
        return hours <= h;
    }
}
```

```python
class Solution:
    def minEatingSpeed(self, piles: List[int], h: int) -> int:
        def can_finish(k: int) -> bool:
            hours = 0
            for pile in piles:
                hours += (pile + k - 1) // k  # Ceiling division
                if hours > h:
                    return False
            return True
        
        left, right = 1, max(piles)
        
        while left < right:
            mid = left + (right - left) // 2
            
            if can_finish(mid):
                right = mid  # Can finish, try slower
            else:
                left = mid + 1  # Can't finish, need faster
        
        return left
```

## The Binary Search on Answer Pattern

```
┌─────────────────────────────────────────────────────────────────┐
│                  BINARY SEARCH ON ANSWER                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  1. IDENTIFY THE ANSWER SPACE                                   │
│     - What's the minimum possible answer?                       │
│     - What's the maximum possible answer?                       │
│                                                                 │
│  2. DEFINE THE MONOTONIC PREDICATE                              │
│     - canAchieve(x): returns true if x is a valid answer        │
│     - Must be monotonic: if canAchieve(x), then canAchieve(x+1) │
│       (for minimum problems)                                    │
│                                                                 │
│  3. BINARY SEARCH                                               │
│     - If canAchieve(mid): answer might be smaller → right = mid │
│     - If !canAchieve(mid): need larger → left = mid + 1         │
│                                                                 │
│  4. RETURN left (the minimum valid answer)                      │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## Complexity Analysis
- **Time:** O(n × log(max(piles))) - binary search × predicate check
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| `left = 0` | Speed of 0 causes division by zero | `left = 1` |
| `right = sum(piles)` | Unnecessarily large | `right = max(piles)` |
| Integer division for hours | Undercounts hours | Use ceiling: `(pile + k - 1) / k` |
| Not using early termination | Slower | Return false as soon as hours > h |

## Mind-Map Anchor

```
🍌 KOKO EATING BANANAS (LC 875)
│
├── Signal: "minimum speed/rate to finish in time"
├── Template: 3 (Binary Search on Answer)
├── Search space: [1, max(piles)]
├── Predicate: canFinish(speed) - total hours <= h
├── Key: Ceiling division for hours per pile
└── Mantra: "Search the answer space, not the array!"
```

--

# Pattern 11: Capacity To Ship Packages Within D Days (LeetCode 1011)

## Problem Statement
A conveyor belt has packages that must be shipped within `days` days. The `i-th` package has weight `weights[i]`. Each day, we load packages in order (can't skip or reorder). The ship has a weight capacity. Return the minimum capacity to ship all packages within `days` days.

## Pattern Recognition Signal
- ✅ "Minimum capacity to finish in time"
- ✅ Must process in ORDER (can't reorder)
- ✅ Monotonic: larger capacity → fewer days needed
- 🎯 **Template 3: Binary Search on Answer**

## Mental Model: The Shipping Container

```
Packages: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], days = 5

Capacity = 15:
Day 1: [1, 2, 3, 4, 5] = 15 ✓
Day 2: [6, 7] = 13 ✓
Day 3: [8] = 8 ✓
Day 4: [9] = 9 ✓
Day 5: [10] = 10 ✓
Total: 5 days ✓

Capacity = 14:
Day 1: [1, 2, 3, 4] = 10 ✓
Day 2: [5, 6] = 11 ✓
Day 3: [7] = 7 ✓
Day 4: [8] = 8 ✓
Day 5: [9] = 9 ✓
Day 6: [10] = 10 ✓
Total: 6 days ✗ (need 6 days, only have 5)

Minimum capacity = 15
```

## Visual Dry Run

```
weights = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], days = 5

Step 1: Define search space
        - Minimum capacity: max(weights) = 10 (must fit largest package)
        - Maximum capacity: sum(weights) = 55 (ship everything in 1 day)

Step 2: Binary search on capacity

Initial: left=10, right=55

mid = 32: Can ship in 5 days?
  Day 1: 1+2+3+4+5+6+7 = 28 ✓ (adding 8 would be 36 > 32)
  Day 2: 8+9+10 = 27 ✓
  Total: 2 days <= 5 ✓
  Try smaller: right = 32

mid = 21: Can ship in 5 days?
  Day 1: 1+2+3+4+5+6 = 21 ✓
  Day 2: 7+8 = 15 ✓
  Day 3: 9+10 = 19 ✓
  Total: 3 days <= 5 ✓
  Try smaller: right = 21

mid = 15: Can ship in 5 days?
  Day 1: 1+2+3+4+5 = 15 ✓
  Day 2: 6+7 = 13 ✓
  Day 3: 8 = 8 ✓
  Day 4: 9 = 9 ✓
  Day 5: 10 = 10 ✓
  Total: 5 days <= 5 ✓
  Try smaller: right = 15

mid = 12: Can ship in 5 days?
  Day 1: 1+2+3+4 = 10 ✓
  Day 2: 5+6 = 11 ✓
  Day 3: 7 = 7 ✓
  Day 4: 8 = 8 ✓
  Day 5: 9 = 9 ✓
  Day 6: 10 = 10 ✓
  Total: 6 days > 5 ✗
  Need larger: left = 13

mid = 14: Can ship in 5 days?
  Day 1: 1+2+3+4 = 10 ✓
  Day 2: 5+6 = 11 ✓
  Day 3: 7 = 7 ✓
  Day 4: 8 = 8 ✓
  Day 5: 9 = 9 ✓
  Day 6: 10 = 10 ✓
  Total: 6 days > 5 ✗
  Need larger: left = 15

left == right = 15, return 15 ✓
```

## Code Implementation

```java
class Solution {
    public int shipWithinDays(int[] weights, int days) {
        // Search space: [max(weights), sum(weights)]
        int left = 0, right = 0;
        for (int w : weights) {
            left = Math.max(left, w);  // Must fit largest package
            right += w;  // Ship everything in 1 day
        }
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (canShip(weights, mid, days)) {
                right = mid;  // Can ship, try smaller capacity
            } else {
                left = mid + 1;  // Can't ship, need larger capacity
            }
        }
        
        return left;
    }
    
    private boolean canShip(int[] weights, int capacity, int days) {
        int daysNeeded = 1;
        int currentLoad = 0;
        
        for (int w : weights) {
            if (currentLoad + w > capacity) {
                // Start a new day
                daysNeeded++;
                currentLoad = w;
                if (daysNeeded > days) return false;
            } else {
                currentLoad += w;
            }
        }
        
        return daysNeeded <= days;
    }
}
```

```python
class Solution:
    def shipWithinDays(self, weights: List[int], days: int) -> int:
        def can_ship(capacity: int) -> bool:
            days_needed = 1
            current_load = 0
            
            for w in weights:
                if current_load + w > capacity:
                    days_needed += 1
                    current_load = w
                    if days_needed > days:
                        return False
                else:
                    current_load += w
            
            return days_needed <= days
        
        left, right = max(weights), sum(weights)
        
        while left < right:
            mid = left + (right - left) // 2
            
            if can_ship(mid):
                right = mid
            else:
                left = mid + 1
        
        return left
```

## Complexity Analysis
- **Time:** O(n × log(sum - max)) - binary search × predicate check
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| `left = 1` | Can't fit packages larger than capacity | `left = max(weights)` |
| Starting `daysNeeded = 0` | First package needs day 1 | `daysNeeded = 1` |
| Forgetting order constraint | Packages must be shipped in order | Process sequentially |
| `currentLoad + w >= capacity` | Should be `>`, not `>=` | Use `>` for strict comparison |

## Mind-Map Anchor

```
🚢 SHIP PACKAGES (LC 1011)
│
├── Signal: "minimum capacity + days constraint + in order"
├── Template: 3 (Binary Search on Answer)
├── Search space: [max(weights), sum(weights)]
├── Predicate: canShip(capacity) - days needed <= days
├── Key: Process in order, start new day when overflow
└── Mantra: "Minimum capacity = max single item!"
```

--

# Pattern 12: Split Array Largest Sum (LeetCode 410)

## Problem Statement
Given an array `nums` and an integer `k`, split the array into `k` non-empty subarrays such that the largest sum among these subarrays is minimized. Return the minimized largest sum.

## Pattern Recognition Signal
- ✅ "Minimize the maximum" or "Maximize the minimum"
- ✅ Split into k parts
- ✅ Monotonic: larger max sum → fewer splits needed
- 🎯 **Template 3: Binary Search on Answer**

## Mental Model: Fair Division

Imagine dividing work among k workers:
- Each worker gets consecutive tasks
- You want to minimize the maximum workload (fairness)

```
Tasks: [7, 2, 5, 10, 8], k = 2 workers

Split 1: [7, 2, 5] | [10, 8] → max = max(14, 18) = 18
Split 2: [7, 2, 5, 10] | [8] → max = max(24, 8) = 24
Split 3: [7] | [2, 5, 10, 8] → max = max(7, 25) = 25
Split 4: [7, 2] | [5, 10, 8] → max = max(9, 23) = 23

Best split: [7, 2, 5] | [10, 8] with max sum = 18
```

## Visual Dry Run

```
nums = [7, 2, 5, 10, 8], k = 2

Step 1: Define search space
        - Minimum max sum: max(nums) = 10 (at least one subarray has max element)
        - Maximum max sum: sum(nums) = 32 (all in one subarray)

Step 2: Binary search on max sum

Initial: left=10, right=32

mid = 21: Can split into <= 2 parts with max sum <= 21?
  Part 1: 7+2+5 = 14 ✓ (adding 10 would be 24 > 21)
  Part 2: 10+8 = 18 ✓
  Total: 2 parts <= 2 ✓
  Try smaller: right = 21

mid = 15: Can split into <= 2 parts with max sum <= 15?
  Part 1: 7+2+5 = 14 ✓ (adding 10 would be 24 > 15)
  Part 2: 10 = 10 ✓ (adding 8 would be 18 > 15)
  Part 3: 8 = 8 ✓
  Total: 3 parts > 2 ✗
  Need larger: left = 16

mid = 18: Can split into <= 2 parts with max sum <= 18?
  Part 1: 7+2+5 = 14 ✓ (adding 10 would be 24 > 18)
  Part 2: 10+8 = 18 ✓
  Total: 2 parts <= 2 ✓
  Try smaller: right = 18

mid = 17: Can split into <= 2 parts with max sum <= 17?
  Part 1: 7+2+5 = 14 ✓ (adding 10 would be 24 > 17)
  Part 2: 10 = 10 ✓ (adding 8 would be 18 > 17)
  Part 3: 8 = 8 ✓
  Total: 3 parts > 2 ✗
  Need larger: left = 18

left == right = 18, return 18 ✓
```

## Code Implementation

```java
class Solution {
    public int splitArray(int[] nums, int k) {
        // Search space: [max(nums), sum(nums)]
        int left = 0, right = 0;
        for (int num : nums) {
            left = Math.max(left, num);
            right += num;
        }
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (canSplit(nums, mid, k)) {
                right = mid;  // Can split, try smaller max sum
            } else {
                left = mid + 1;  // Can't split, need larger max sum
            }
        }
        
        return left;
    }
    
    // Can we split into <= k parts with each part sum <= maxSum?
    private boolean canSplit(int[] nums, int maxSum, int k) {
        int parts = 1;
        int currentSum = 0;
        
        for (int num : nums) {
            if (currentSum + num > maxSum) {
                parts++;
                currentSum = num;
                if (parts > k) return false;
            } else {
                currentSum += num;
            }
        }
        
        return parts <= k;
    }
}
```

```python
class Solution:
    def splitArray(self, nums: List[int], k: int) -> int:
        def can_split(max_sum: int) -> bool:
            parts = 1
            current_sum = 0
            
            for num in nums:
                if current_sum + num > max_sum:
                    parts += 1
                    current_sum = num
                    if parts > k:
                        return False
                else:
                    current_sum += num
            
            return parts <= k
        
        left, right = max(nums), sum(nums)
        
        while left < right:
            mid = left + (right - left) // 2
            
            if can_split(mid):
                right = mid
            else:
                left = mid + 1
        
        return left
```

## The "Minimize Maximum" Pattern

This is a classic pattern that appears in many problems:

```
┌─────────────────────────────────────────────────────────────────┐
│              MINIMIZE MAXIMUM / MAXIMIZE MINIMUM                │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  MINIMIZE MAXIMUM (this problem):                               │
│  - Binary search on the maximum value                           │
│  - Predicate: can we achieve with max <= mid?                   │
│  - If yes: try smaller (right = mid)                            │
│  - If no: need larger (left = mid + 1)                          │
│                                                                 │
│  MAXIMIZE MINIMUM (e.g., aggressive cows):                      │
│  - Binary search on the minimum value                           │
│  - Predicate: can we achieve with min >= mid?                   │
│  - If yes: try larger (left = mid)                              │
│  - If no: need smaller (right = mid - 1)                        │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## Complexity Analysis
- **Time:** O(n × log(sum - max))
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| `left = 1` | Max sum must be at least max(nums) | `left = max(nums)` |
| Counting parts wrong | Start with 1, not 0 | `parts = 1` |
| `parts >= k` in predicate | Should be `>` | Use `parts > k` |
| Confusing with "exactly k parts" | We need "at most k parts" | `parts <= k` |

## Mind-Map Anchor

```
✂️ SPLIT ARRAY LARGEST SUM (LC 410)
│
├── Signal: "minimize the maximum" + "split into k parts"
├── Template: 3 (Binary Search on Answer)
├── Search space: [max(nums), sum(nums)]
├── Predicate: canSplit(maxSum) - parts needed <= k
├── Key: Greedy split - start new part when overflow
└── Mantra: "Binary search on the answer, greedy to validate!"
```

--

# Pattern 13: Minimum Number of Days to Make m Bouquets (LeetCode 1482)

## Problem Statement
You have `n` flowers, each blooming on day `bloomDay[i]`. To make a bouquet, you need `k` adjacent flowers. Return the minimum number of days to make `m` bouquets, or -1 if impossible.

## Pattern Recognition Signal
- ✅ "Minimum days to achieve goal"
- ✅ Monotonic: more days → more flowers bloomed
- ✅ Adjacency constraint
- 🎯 **Template 3: Binary Search on Answer**

## Mental Model: The Garden Calendar

```
bloomDay = [1, 10, 3, 10, 2], m = 3 bouquets, k = 1 flower each

Day 1: [✓, _, _, _, _] → 1 bouquet possible
Day 2: [✓, _, _, _, ✓] → 2 bouquets possible
Day 3: [✓, _, ✓, _, ✓] → 3 bouquets possible ✓

Minimum days = 3
```

## Visual Dry Run

```
bloomDay = [7, 7, 7, 7, 12, 7, 7], m = 2 bouquets, k = 3 adjacent flowers

Step 1: Define search space
        - Minimum days: min(bloomDay) = 7
        - Maximum days: max(bloomDay) = 12

Step 2: Binary search on days

Day 7: Which flowers bloomed? [✓, ✓, ✓, ✓, _, ✓, ✓]
       Adjacent groups of 3: [✓✓✓] [✓] [✓✓]
       Bouquets: 1 (only first group has 3 adjacent)
       Need 2, have 1 ✗

Day 12: Which flowers bloomed? [✓, ✓, ✓, ✓, ✓, ✓, ✓]
        Adjacent groups of 3: [✓✓✓] [✓✓✓] [✓]
        Bouquets: 2 ✓

Binary search:
left=7, right=12, mid=9
Day 9: [✓, ✓, ✓, ✓, _, ✓, ✓] → 1 bouquet ✗
left = 10

left=10, right=12, mid=11
Day 11: [✓, ✓, ✓, ✓, _, ✓, ✓] → 1 bouquet ✗
left = 12

left=12, right=12
Return 12 ✓
```

## Code Implementation

```java
class Solution {
    public int minDays(int[] bloomDay, int m, int k) {
        // Check if it's possible
        if ((long) m * k > bloomDay.length) {
            return -1;
        }
        
        // Search space: [min(bloomDay), max(bloomDay)]
        int left = Integer.MAX_VALUE, right = Integer.MIN_VALUE;
        for (int day : bloomDay) {
            left = Math.min(left, day);
            right = Math.max(right, day);
        }
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (canMakeBouquets(bloomDay, mid, m, k)) {
                right = mid;  // Can make, try fewer days
            } else {
                left = mid + 1;  // Can't make, need more days
            }
        }
        
        return left;
    }
    
    private boolean canMakeBouquets(int[] bloomDay, int day, int m, int k) {
        int bouquets = 0;
        int consecutive = 0;
        
        for (int bloom : bloomDay) {
            if (bloom <= day) {
                consecutive++;
                if (consecutive == k) {
                    bouquets++;
                    consecutive = 0;  // Reset for next bouquet
                    if (bouquets >= m) return true;
                }
            } else {
                consecutive = 0;  // Reset - flower not bloomed
            }
        }
        
        return bouquets >= m;
    }
}
```

```python
class Solution:
    def minDays(self, bloomDay: List[int], m: int, k: int) -> int:
        if m * k > len(bloomDay):
            return -1
        
        def can_make_bouquets(day: int) -> bool:
            bouquets = 0
            consecutive = 0
            
            for bloom in bloomDay:
                if bloom <= day:
                    consecutive += 1
                    if consecutive == k:
                        bouquets += 1
                        consecutive = 0
                        if bouquets >= m:
                            return True
                else:
                    consecutive = 0
            
            return bouquets >= m
        
        left, right = min(bloomDay), max(bloomDay)
        
        while left < right:
            mid = left + (right - left) // 2
            
            if can_make_bouquets(mid):
                right = mid
            else:
                left = mid + 1
        
        return left
```

## Complexity Analysis
- **Time:** O(n × log(max - min))
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| Not checking impossibility | m * k > n means impossible | Return -1 early |
| Integer overflow in `m * k` | Can overflow for large values | Cast to long |
| Not resetting consecutive | After making a bouquet, reset | `consecutive = 0` after bouquet |
| Using `bloom < day` | Should include flowers blooming on `day` | Use `bloom <= day` |

## Mind-Map Anchor

```
💐 MINIMUM DAYS FOR BOUQUETS (LC 1482)
│
├── Signal: "minimum days + adjacent constraint"
├── Template: 3 (Binary Search on Answer)
├── Search space: [min(bloomDay), max(bloomDay)]
├── Predicate: canMakeBouquets(day) - count adjacent groups
├── Key: Reset consecutive counter after each bouquet
├── Edge case: m * k > n → impossible (-1)
└── Mantra: "Count consecutive bloomed flowers!"
```

--

# Pattern 14: Magnetic Force Between Two Balls (LeetCode 1552)

## Problem Statement
Given `n` baskets at positions `position[i]` and `m` balls, place balls in baskets to maximize the minimum magnetic force (distance) between any two balls.

## Pattern Recognition Signal
- ✅ "Maximize the minimum distance"
- ✅ Placement problem
- ✅ Monotonic: larger min distance → harder to place all balls
- 🎯 **Template 3: Binary Search on Answer (Maximize Minimum)**

## Mental Model: Spreading Out

Imagine placing magnets on a ruler - they repel each other:
- You want them as far apart as possible
- But you have limited positions (baskets)
- Maximize the minimum gap between any two magnets

```
Positions: [1, 2, 3, 4, 7], m = 3 balls

Place at [1, 4, 7]: distances = [3, 3], min = 3 ✓
Place at [1, 2, 7]: distances = [1, 5], min = 1 ✗
Place at [1, 3, 7]: distances = [2, 4], min = 2

Best: min distance = 3
```

## Visual Dry Run

```
position = [1, 2, 3, 4, 7], m = 3 balls

Step 1: Sort positions (if not sorted)
        [1, 2, 3, 4, 7]

Step 2: Define search space
        - Minimum distance: 1 (adjacent positions)
        - Maximum distance: (7 - 1) / (3 - 1) = 3 (spread evenly)
        
        Actually, max = position[n-1] - position[0] = 6

Step 3: Binary search on minimum distance

Initial: left=1, right=6

mid = 3: Can place 3 balls with min distance >= 3?
  Ball 1 at position 1
  Ball 2: next position >= 1+3=4 → position 4 ✓
  Ball 3: next position >= 4+3=7 → position 7 ✓
  Placed 3 balls ✓
  Try larger: left = 3 (using ceiling for maximize)

Actually, for maximize minimum, we use:
  If can place: left = mid (try larger)
  If can't: right = mid - 1

mid = 4: Can place 3 balls with min distance >= 4?
  Ball 1 at position 1
  Ball 2: next position >= 1+4=5 → position 7 ✓
  Ball 3: next position >= 7+4=11 → no position ✗
  Placed 2 balls < 3 ✗
  Need smaller: right = 3

left=3, right=3
Return 3 ✓
```

## Code Implementation

```java
class Solution {
    public int maxDistance(int[] position, int m) {
        Arrays.sort(position);
        
        int n = position.length;
        int left = 1;
        int right = position[n - 1] - position[0];
        
        while (left < right) {
            // Ceiling division for maximize problem
            int mid = left + (right - left + 1) / 2;
            
            if (canPlace(position, mid, m)) {
                left = mid;  // Can place, try larger distance
            } else {
                right = mid - 1;  // Can't place, need smaller distance
            }
        }
        
        return left;
    }
    
    private boolean canPlace(int[] position, int minDist, int m) {
        int count = 1;  // Place first ball at first position
        int lastPos = position[0];
        
        for (int i = 1; i < position.length; i++) {
            if (position[i] - lastPos >= minDist) {
                count++;
                lastPos = position[i];
                if (count >= m) return true;
            }
        }
        
        return count >= m;
    }
}
```

```python
class Solution:
    def maxDistance(self, position: List[int], m: int) -> int:
        position.sort()
        
        def can_place(min_dist: int) -> bool:
            count = 1
            last_pos = position[0]
            
            for i in range(1, len(position)):
                if position[i] - last_pos >= min_dist:
                    count += 1
                    last_pos = position[i]
                    if count >= m:
                        return True
            
            return count >= m
        
        left, right = 1, position[-1] - position[0]
        
        while left < right:
            mid = left + (right - left + 1) // 2  # Ceiling for maximize
            
            if can_place(mid):
                left = mid
            else:
                right = mid - 1
        
        return left
```

## Maximize vs Minimize Pattern

```
┌─────────────────────────────────────────────────────────────────┐
│                    MAXIMIZE vs MINIMIZE                         │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  MINIMIZE (find first TRUE):                                    │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ Answer:  1   2   3   4   5   6   7   8   9   10         │   │
│  │ Valid:   F   F   F   T   T   T   T   T   T   T          │   │
│  │                      ↑                                   │   │
│  │              First TRUE = Answer                         │   │
│  └─────────────────────────────────────────────────────────┘   │
│  - mid = left + (right - left) / 2  (floor)                    │
│  - If valid: right = mid                                        │
│  - If not valid: left = mid + 1                                 │
│                                                                 │
│  MAXIMIZE (find last TRUE):                                     │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ Answer:  1   2   3   4   5   6   7   8   9   10         │   │
│  │ Valid:   T   T   T   T   T   T   F   F   F   F          │   │
│  │                          ↑                               │   │
│  │                  Last TRUE = Answer                      │   │
│  └─────────────────────────────────────────────────────────┘   │
│  - mid = left + (right - left + 1) / 2  (ceiling)              │
│  - If valid: left = mid                                         │
│  - If not valid: right = mid - 1                                │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## Complexity Analysis
- **Time:** O(n log n + n × log(max_dist)) - sort + binary search
- **Space:** O(1) or O(n) depending on sort implementation

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| Forgetting to sort | Positions may not be sorted | Sort first |
| Floor division for maximize | Causes infinite loop | Use ceiling: `(right - left + 1) / 2` |
| `left = mid + 1` for maximize | Skips potential answer | `left = mid` |
| Starting count at 0 | First ball is always placed | `count = 1` |

## Mind-Map Anchor

```
🧲 MAGNETIC FORCE (LC 1552)
│
├── Signal: "maximize minimum distance"
├── Template: 3 (Binary Search on Answer - MAXIMIZE)
├── Search space: [1, max_pos - min_pos]
├── Predicate: canPlace(minDist) - greedy placement
├── Key: Ceiling division + left = mid for maximize
├── Don't forget: Sort positions first!
└── Mantra: "Greedy place, maximize the gap!"
```

--

# Pattern 15: Search a 2D Matrix (LeetCode 74)

## Problem Statement
Write an efficient algorithm to search for a value in an m x n matrix. The matrix has:
- Integers in each row sorted left to right
- First integer of each row > last integer of previous row

## Pattern Recognition Signal
- ✅ 2D matrix with special sorted property
- ✅ "Flattened" it would be fully sorted
- ✅ Find exact value
- 🎯 **Template 1: Treat as 1D sorted array**

## Mental Model: The Unrolled Scroll

Imagine the matrix as a scroll that's been rolled up:

```
Matrix:                    Unrolled:
[1,  3,  5,  7]           [1, 3, 5, 7, 10, 11, 16, 20, 23, 30, 34, 60]
[10, 11, 16, 20]           0  1  2  3   4   5   6   7   8   9  10  11
[23, 30, 34, 60]

Index 7 in 1D → Row 7/4=1, Col 7%4=3 → matrix[1][3] = 20
```

## Visual Dry Run

```
Matrix:
[1,  3,  5,  7 ]
[10, 11, 16, 20]
[23, 30, 34, 60]

Target: 3
m = 3 rows, n = 4 cols, total = 12 elements

Treat as 1D array of size 12:
Index:  0   1   2   3   4   5   6   7   8   9  10  11
Value:  1   3   5   7  10  11  16  20  23  30  34  60

Binary search:
left=0, right=11

mid = 5
row = 5/4 = 1, col = 5%4 = 1
matrix[1][1] = 11 > 3
right = 4

mid = 2
row = 2/4 = 0, col = 2%4 = 2
matrix[0][2] = 5 > 3
right = 1

mid = 0
row = 0/4 = 0, col = 0%4 = 0
matrix[0][0] = 1 < 3
left = 1

mid = 1
row = 1/4 = 0, col = 1%4 = 1
matrix[0][1] = 3 == 3 ✓

Return true!
```

## Code Implementation

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        int left = 0;
        int right = m * n - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Convert 1D index to 2D coordinates
            int row = mid / n;
            int col = mid % n;
            int value = matrix[row][col];
            
            if (value == target) {
                return true;
            } else if (value < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }
}
```

```python
class Solution:
    def searchMatrix(self, matrix: List[List[int]], target: int) -> bool:
        m, n = len(matrix), len(matrix[0])
        left, right = 0, m * n - 1
        
        while left <= right:
            mid = left + (right - left) // 2
            
            # Convert 1D index to 2D
            row, col = mid // n, mid % n
            value = matrix[row][col]
            
            if value == target:
                return True
            elif value < target:
                left = mid + 1
            else:
                right = mid - 1
        
        return False
```

## The Index Conversion Formula

```
┌─────────────────────────────────────────────────────────────────┐
│                    1D ↔ 2D INDEX CONVERSION                     │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Given: m rows, n columns                                       │
│                                                                 │
│  1D index → 2D coordinates:                                     │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  row = index / n                                         │   │
│  │  col = index % n                                         │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                 │
│  2D coordinates → 1D index:                                     │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  index = row * n + col                                   │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                 │
│  Example: 3x4 matrix, index 7                                   │
│  row = 7 / 4 = 1                                                │
│  col = 7 % 4 = 3                                                │
│  → matrix[1][3]                                                 │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## Complexity Analysis
- **Time:** O(log(m × n))
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| `row = mid / m` | Should divide by columns, not rows | `row = mid / n` |
| `right = m * n` | Off by one | `right = m * n - 1` |
| Two binary searches | Unnecessary complexity | Single binary search on flattened array |
| Not checking empty matrix | Causes crash | Check `matrix.length == 0` |

## Mind-Map Anchor

```
📊 SEARCH 2D MATRIX (LC 74)
│
├── Signal: "2D matrix + each row sorted + rows connected"
├── Template: 1 (treat as 1D array)
├── Key: Index conversion: row = mid/n, col = mid%n
├── Search space: [0, m*n-1]
├── Same as: Binary search on flattened array
└── Mantra: "Unroll the matrix, search as 1D!"
```

--

# Pattern 16: Search a 2D Matrix II (LeetCode 240)

## Problem Statement
Search for a value in an m x n matrix with:
- Integers in each row sorted left to right
- Integers in each column sorted top to bottom

Note: First element of a row is NOT necessarily > last element of previous row!

## Pattern Recognition Signal
- ✅ 2D matrix with row AND column sorting
- ✅ NOT fully sorted when flattened
- ✅ Find exact value
- 🎯 **Staircase Search (not pure binary search)**

## Mental Model: The Staircase

Start from top-right (or bottom-left) corner:
- If current > target: move LEFT (smaller values)
- If current < target: move DOWN (larger values)
- If current == target: found!

```
Matrix:
[1,  4,  7, 11, 15]
[2,  5,  8, 12, 19]
[3,  6,  9, 16, 22]
[10,13, 14, 17, 24]
[18,21, 23, 26, 30]

Start at top-right (15):
- 15 > 5? Yes, go left
- 11 > 5? Yes, go left
- 7 > 5? Yes, go left
- 4 < 5? Yes, go down
- 5 == 5? Found!
```

## Visual Dry Run

```
Matrix:
[1,  4,  7, 11, 15]
[2,  5,  8, 12, 19]
[3,  6,  9, 16, 22]
[10,13, 14, 17, 24]
[18,21, 23, 26, 30]

Target: 5

Start: row=0, col=4 (top-right corner)
       Value = 15

Step 1: 15 > 5, move left
        row=0, col=3, value=11
        
Step 2: 11 > 5, move left
        row=0, col=2, value=7
        
Step 3: 7 > 5, move left
        row=0, col=1, value=4
        
Step 4: 4 < 5, move down
        row=1, col=1, value=5
        
Step 5: 5 == 5, found! ✓

═══════════════════════════════════════════════════════════════

Target: 20 (not in matrix)

Start: row=0, col=4, value=15

Step 1: 15 < 20, move down → row=1, col=4, value=19
Step 2: 19 < 20, move down → row=2, col=4, value=22
Step 3: 22 > 20, move left → row=2, col=3, value=16
Step 4: 16 < 20, move down → row=3, col=3, value=17
Step 5: 17 < 20, move down → row=4, col=3, value=26
Step 6: 26 > 20, move left → row=4, col=2, value=23
Step 7: 23 > 20, move left → row=4, col=1, value=21
Step 8: 21 > 20, move left → row=4, col=0, value=18
Step 9: 18 < 20, move down → row=5 (out of bounds)

Not found! ✗
```

## Code Implementation

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        // Start from top-right corner
        int row = 0;
        int col = n - 1;
        
        while (row < m && col >= 0) {
            int value = matrix[row][col];
            
            if (value == target) {
                return true;
            } else if (value > target) {
                col-;  // Move left (smaller values)
            } else {
                row++;  // Move down (larger values)
            }
        }
        
        return false;
    }
}
```

```python
class Solution:
    def searchMatrix(self, matrix: List[List[int]], target: int) -> bool:
        m, n = len(matrix), len(matrix[0])
        
        # Start from top-right corner
        row, col = 0, n - 1
        
        while row < m and col >= 0:
            value = matrix[row][col]
            
            if value == target:
                return True
            elif value > target:
                col -= 1  # Move left
            else:
                row += 1  # Move down
        
        return False
```

## Alternative: Binary Search on Each Row

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        for (int[] row : matrix) {
            if (binarySearch(row, target)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean binarySearch(int[] row, int target) {
        int left = 0, right = row.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (row[mid] == target) return true;
            else if (row[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }
}
// Time: O(m log n)
```

## Why Staircase Works

```
From top-right corner:
- Everything to the LEFT is smaller
- Everything BELOW is larger

This gives us a clear decision at each step!

From top-left corner (doesn't work):
- Everything to the RIGHT is larger
- Everything BELOW is larger
- Both directions have larger values - can't decide!

From bottom-left corner (also works):
- Everything to the RIGHT is larger
- Everything ABOVE is smaller
- Clear decision at each step!
```

## Complexity Analysis
- **Time:** O(m + n) - at most m + n steps
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| Starting from top-left | Can't decide direction | Start from top-right or bottom-left |
| Using LC 74 approach | Matrix isn't fully sorted | Use staircase search |
| `col > 0` in condition | Misses col=0 | Use `col >= 0` |
| Moving diagonally | Might skip the target | Move only horizontally or vertically |

## Mind-Map Anchor

```
📊 SEARCH 2D MATRIX II (LC 240)
│
├── Signal: "2D matrix + rows sorted + columns sorted (separately)"
├── Approach: Staircase search (NOT binary search)
├── Start: Top-right or bottom-left corner
├── Move: Left if too big, down if too small
├── Time: O(m + n)
└── Mantra: "Start at corner, staircase down!"
```

--

# Pattern 17: Median of Two Sorted Arrays (LeetCode 4)

## Problem Statement
Given two sorted arrays `nums1` and `nums2`, return the median of the two sorted arrays. The overall run time complexity should be O(log(m+n)).

## Pattern Recognition Signal
- ✅ Two sorted arrays
- ✅ Find median (middle element)
- ✅ O(log(m+n)) requirement
- 🎯 **Binary Search on partition position**

## Mental Model: The Perfect Cut

Imagine cutting both arrays such that:
- Left half has exactly (m+n+1)/2 elements
- All elements in left half <= all elements in right half

```
nums1: [1, 3, | 8, 9, 15]
nums2: [7, 11, | 18, 19, 21, 25]

Left half: [1, 3, 7, 11]  (4 elements)
Right half: [8, 9, 15, 18, 19, 21, 25]  (7 elements)

For median: we need equal halves (or left has 1 more for odd total)
Total = 11, so left should have 6 elements

Adjust cuts until:
- max(left1, left2) <= min(right1, right2)
```

## Visual Dry Run

```
nums1 = [1, 3], nums2 = [2]

Total = 3 (odd), median is element at position 2 (1-indexed)
Left half should have (3+1)/2 = 2 elements

Binary search on nums1 (shorter array):
- We choose how many elements from nums1 go to left half
- Rest come from nums2

Partition i in nums1: 0, 1, or 2 elements
Partition j in nums2: 2 - i elements

Try i = 1 (1 element from nums1):
  j = 2 - 1 = 1 (1 element from nums2)
  
  nums1: [1 | 3]
  nums2: [2 | ]
  
  Left half: [1, 2]
  Right half: [3]
  
  Check: max(1, 2) <= min(3, ∞)? 2 <= 3 ✓
  
  Median (odd) = max(left) = max(1, 2) = 2 ✓

═══════════════════════════════════════════════════════════════

nums1 = [1, 2], nums2 = [3, 4]

Total = 4 (even), median = average of elements 2 and 3
Left half should have (4+1)/2 = 2 elements

Try i = 1:
  j = 2 - 1 = 1
  
  nums1: [1 | 2]
  nums2: [3 | 4]
  
  Left: [1, 3], Right: [2, 4]
  
  Check: max(1, 3) <= min(2, 4)? 3 <= 2? ✗
  
  3 > 2 means we took too many from nums2
  Need more from nums1: move i right

Try i = 2:
  j = 2 - 2 = 0
  
  nums1: [1, 2 | ]
  nums2: [ | 3, 4]
  
  Left: [1, 2], Right: [3, 4]
  
  Check: max(2, -∞) <= min(∞, 3)? 2 <= 3 ✓
  
  Median (even) = (max(left) + min(right)) / 2
                = (2 + 3) / 2 = 2.5 ✓
```

## Code Implementation

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the shorter array
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int m = nums1.length;
        int n = nums2.length;
        int halfLen = (m + n + 1) / 2;
        
        int left = 0, right = m;
        
        while (left <= right) {
            int i = left + (right - left) / 2;  // Partition in nums1
            int j = halfLen - i;                 // Partition in nums2
            
            // Handle edge cases with infinity
            int nums1LeftMax = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int nums1RightMin = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int nums2LeftMax = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int nums2RightMin = (j == n) ? Integer.MAX_VALUE : nums2[j];
            
            if (nums1LeftMax <= nums2RightMin && nums2LeftMax <= nums1RightMin) {
                // Found the correct partition
                if ((m + n) % 2 == 1) {
                    // Odd total: median is max of left half
                    return Math.max(nums1LeftMax, nums2LeftMax);
                } else {
                    // Even total: median is average of max(left) and min(right)
                    return (Math.max(nums1LeftMax, nums2LeftMax) + 
                            Math.min(nums1RightMin, nums2RightMin)) / 2.0;
                }
            } else if (nums1LeftMax > nums2RightMin) {
                // nums1's left part is too big, move partition left
                right = i - 1;
            } else {
                // nums2's left part is too big, move partition right
                left = i + 1;
            }
        }
        
        return 0.0;  // Should never reach here
    }
}
```

```python
class Solution:
    def findMedianSortedArrays(self, nums1: List[int], nums2: List[int]) -> float:
        # Ensure nums1 is shorter
        if len(nums1) > len(nums2):
            nums1, nums2 = nums2, nums1
        
        m, n = len(nums1), len(nums2)
        half_len = (m + n + 1) // 2
        
        left, right = 0, m
        
        while left <= right:
            i = left + (right - left) // 2  # Partition in nums1
            j = half_len - i                 # Partition in nums2
            
            # Handle boundaries
            nums1_left_max = float('-inf') if i == 0 else nums1[i - 1]
            nums1_right_min = float('inf') if i == m else nums1[i]
            nums2_left_max = float('-inf') if j == 0 else nums2[j - 1]
            nums2_right_min = float('inf') if j == n else nums2[j]
            
            if nums1_left_max <= nums2_right_min and nums2_left_max <= nums1_right_min:
                # Correct partition found
                if (m + n) % 2 == 1:
                    return max(nums1_left_max, nums2_left_max)
                else:
                    return (max(nums1_left_max, nums2_left_max) + 
                            min(nums1_right_min, nums2_right_min)) / 2
            elif nums1_left_max > nums2_right_min:
                right = i - 1
            else:
                left = i + 1
        
        return 0.0
```

## The Partition Concept

```
┌─────────────────────────────────────────────────────────────────┐
│                    PARTITION CONCEPT                            │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  nums1: [a1, a2, ... | ai, ai+1, ...]                          │
│  nums2: [b1, b2, ... | bj, bj+1, ...]                          │
│                                                                 │
│  Left half: [a1...ai-1, b1...bj-1]                             │
│  Right half: [ai...am, bj...bn]                                │
│                                                                 │
│  Valid partition when:                                          │
│  1. Left half size = (m + n + 1) / 2                           │
│  2. max(left) <= min(right)                                     │
│     i.e., nums1[i-1] <= nums2[j] AND nums2[j-1] <= nums1[i]    │
│                                                                 │
│  Binary search on i (partition position in nums1)               │
│  j is determined: j = halfLen - i                               │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## Complexity Analysis
- **Time:** O(log(min(m, n))) - binary search on shorter array
- **Space:** O(1)

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| Binary search on longer array | Slower and more edge cases | Always search on shorter array |
| Forgetting boundary cases | i=0 or i=m causes index errors | Use -∞ and +∞ for boundaries |
| Wrong half length formula | Off by one for odd/even | Use `(m + n + 1) / 2` |
| Integer division for result | Loses precision | Use `/ 2.0` for double result |

## Mind-Map Anchor

```
📊 MEDIAN OF TWO SORTED ARRAYS (LC 4)
│
├── Signal: "median + two sorted arrays + O(log) required"
├── Approach: Binary search on partition position
├── Key insight: Find cut where max(left) <= min(right)
├── Search on: Shorter array (for efficiency)
├── Handle: Boundary cases with ±∞
├── Odd total: max(left half)
├── Even total: (max(left) + min(right)) / 2
└── Mantra: "Binary search the perfect cut!"
```

--

# Pattern 18: Find K-th Smallest Pair Distance (LeetCode 719)

## Problem Statement
Given an integer array `nums` and an integer `k`, return the k-th smallest distance among all pairs. The distance of a pair (nums[i], nums[j]) is |nums[i] - nums[j]|.

## Pattern Recognition Signal
- ✅ "K-th smallest" in a large space
- ✅ Can't enumerate all pairs (too many)
- ✅ Monotonic: more pairs have distance <= d as d increases
- 🎯 **Template 3: Binary Search on Answer + Counting**

## Mental Model: The Distance Threshold

Instead of finding all pairs and sorting, ask:
"How many pairs have distance <= d?"

```
nums = [1, 3, 1] → sorted: [1, 1, 3]
All pairs: (1,1)=0, (1,3)=2, (1,3)=2
Sorted distances: [0, 2, 2]

For k=1 (smallest):
- How many pairs with distance <= 0? → 1 pair
- 1 >= k=1, so answer is 0

Binary search on distance d:
- If count(d) >= k: answer might be smaller, right = d
- If count(d) < k: answer is larger, left = d + 1
```

## Visual Dry Run

```
nums = [1, 3, 1], k = 1

Step 1: Sort nums → [1, 1, 3]

Step 2: Define search space
        - Minimum distance: 0
        - Maximum distance: max - min = 3 - 1 = 2

Step 3: Binary search on distance

left=0, right=2

mid = 1: How many pairs with distance <= 1?
  Pairs: (1,1)=0 ✓, (1,3)=2 ✗, (1,3)=2 ✗
  Count = 1 >= k=1 ✓
  Try smaller: right = 1

mid = 0: How many pairs with distance <= 0?
  Pairs: (1,1)=0 ✓
  Count = 1 >= k=1 ✓
  Try smaller: right = 0

left == right = 0
Return 0 ✓

═══════════════════════════════════════════════════════════════

nums = [1, 6, 1], k = 3

Sorted: [1, 1, 6]
All distances: 0, 5, 5 → sorted: [0, 5, 5]
k=3 means we want the 3rd smallest = 5

left=0, right=5

mid = 2: pairs with distance <= 2?
  (1,1)=0 ✓, (1,6)=5 ✗, (1,6)=5 ✗
  Count = 1 < k=3 ✗
  Need larger: left = 3

mid = 4: pairs with distance <= 4?
  (1,1)=0 ✓, (1,6)=5 ✗, (1,6)=5 ✗
  Count = 1 < k=3 ✗
  Need larger: left = 5

left == right = 5
Return 5 ✓
```

## Code Implementation

```java
class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        
        int left = 0;
        int right = nums[n - 1] - nums[0];
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (countPairs(nums, mid) >= k) {
                right = mid;  // Enough pairs, try smaller distance
            } else {
                left = mid + 1;  // Not enough pairs, need larger distance
            }
        }
        
        return left;
    }
    
    // Count pairs with distance <= maxDist using two pointers
    private int countPairs(int[] nums, int maxDist) {
        int count = 0;
        int j = 0;
        
        for (int i = 0; i < nums.length; i++) {
            while (j < nums.length && nums[j] - nums[i] <= maxDist) {
                j++;
            }
            // Pairs: (i, i+1), (i, i+2), ..., (i, j-1)
            count += j - i - 1;
        }
        
        return count;
    }
}
```

```python
class Solution:
    def smallestDistancePair(self, nums: List[int], k: int) -> int:
        nums.sort()
        n = len(nums)
        
        def count_pairs(max_dist: int) -> int:
            count = 0
            j = 0
            for i in range(n):
                while j < n and nums[j] - nums[i] <= max_dist:
                    j += 1
                count += j - i - 1
            return count
        
        left, right = 0, nums[-1] - nums[0]
        
        while left < right:
            mid = left + (right - left) // 2
            
            if count_pairs(mid) >= k:
                right = mid
            else:
                left = mid + 1
        
        return left
```

## The Counting Technique

```
┌─────────────────────────────────────────────────────────────────┐
│              COUNTING PAIRS WITH TWO POINTERS                   │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Sorted array: [1, 1, 3, 6, 8]                                 │
│  Count pairs with distance <= 4                                 │
│                                                                 │
│  For each i, find largest j where nums[j] - nums[i] <= 4       │
│                                                                 │
│  i=0 (nums[i]=1): j goes to 3 (nums[3]=6, 6-1=5 > 4)           │
│                   pairs: (0,1), (0,2) → count += 2              │
│                                                                 │
│  i=1 (nums[i]=1): j stays at 3                                  │
│                   pairs: (1,2) → count += 1                     │
│                                                                 │
│  i=2 (nums[i]=3): j goes to 4 (nums[4]=8, 8-3=5 > 4)           │
│                   pairs: (2,3) → count += 1                     │
│                                                                 │
│  i=3 (nums[i]=6): j goes to 5 (out of bounds)                  │
│                   pairs: (3,4) → count += 1                     │
│                                                                 │
│  Total: 5 pairs                                                 │
│                                                                 │
│  Key: j never decreases! O(n) counting.                        │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## Complexity Analysis
- **Time:** O(n log n + n log W) where W = max - min
  - Sorting: O(n log n)
  - Binary search: O(log W) iterations
  - Each count: O(n) with two pointers
- **Space:** O(1) or O(n) for sorting

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| Not sorting | Two-pointer counting requires sorted array | Sort first |
| `count += j - i` | Overcounts (includes i itself) | `count += j - i - 1` |
| Resetting j for each i | Makes counting O(n²) | Keep j, it never decreases |
| `count > k` instead of `>= k` | Off by one | Use `>= k` |

## Mind-Map Anchor

```
🔢 K-TH SMALLEST PAIR DISTANCE (LC 719)
│
├── Signal: "k-th smallest in huge space"
├── Template: 3 (Binary Search on Answer)
├── Search space: [0, max - min]
├── Predicate: countPairs(dist) >= k
├── Counting: Two pointers on sorted array (O(n))
├── Key: j never decreases during counting
└── Mantra: "Binary search distance, count with two pointers!"
```

--

# Pattern 19: Aggressive Cows / Maximize Minimum Distance (Classic)

## Problem Statement
Given `n` stalls at positions and `c` cows, place cows in stalls to maximize the minimum distance between any two cows.

(This is the same pattern as LC 1552 - Magnetic Force Between Two Balls)

## Pattern Recognition Signal
- ✅ "Maximize the minimum distance"
- ✅ Placement/assignment problem
- ✅ Monotonic: larger min distance → harder to place all
- 🎯 **Template 3: Binary Search on Answer (Maximize)**

## Mental Model: Spreading Cows Apart

Cows don't like each other - they want to be as far apart as possible!

```
Stalls: [1, 2, 4, 8, 9], c = 3 cows

Place to maximize minimum distance:
- [1, 4, 8]: distances = [3, 4], min = 3
- [1, 4, 9]: distances = [3, 5], min = 3
- [1, 8, 9]: distances = [7, 1], min = 1
- [2, 4, 9]: distances = [2, 5], min = 2

Best: min distance = 3
```

## Visual Dry Run

```
stalls = [1, 2, 4, 8, 9], c = 3 cows

Step 1: Sort stalls (if not sorted)
        [1, 2, 4, 8, 9]

Step 2: Define search space
        - Minimum distance: 1 (adjacent stalls)
        - Maximum distance: (9 - 1) / (3 - 1) = 4 (evenly spread)
        
        Actually, max = stalls[n-1] - stalls[0] = 8

Step 3: Binary search on minimum distance

left=1, right=8

mid = 4: Can place 3 cows with min distance >= 4?
  Cow 1 at stall 1
  Cow 2: next stall >= 1+4=5 → stall 8 ✓
  Cow 3: next stall >= 8+4=12 → no stall ✗
  Placed 2 cows < 3 ✗
  Need smaller: right = 3

mid = 2: Can place 3 cows with min distance >= 2?
  Cow 1 at stall 1
  Cow 2: next stall >= 1+2=3 → stall 4 ✓
  Cow 3: next stall >= 4+2=6 → stall 8 ✓
  Placed 3 cows ✓
  Try larger: left = 2 (ceiling division)

Actually using ceiling: mid = (1+3+1)/2 = 2
left = 2

mid = 3: Can place 3 cows with min distance >= 3?
  Cow 1 at stall 1
  Cow 2: next stall >= 1+3=4 → stall 4 ✓
  Cow 3: next stall >= 4+3=7 → stall 8 ✓
  Placed 3 cows ✓
  Try larger: left = 3

left == right = 3
Return 3 ✓
```

## Code Implementation

```java
class Solution {
    public int aggressiveCows(int[] stalls, int c) {
        Arrays.sort(stalls);
        int n = stalls.length;
        
        int left = 1;
        int right = stalls[n - 1] - stalls[0];
        
        while (left < right) {
            // Ceiling division for maximize problem
            int mid = left + (right - left + 1) / 2;
            
            if (canPlace(stalls, mid, c)) {
                left = mid;  // Can place, try larger distance
            } else {
                right = mid - 1;  // Can't place, need smaller distance
            }
        }
        
        return left;
    }
    
    private boolean canPlace(int[] stalls, int minDist, int c) {
        int count = 1;  // Place first cow at first stall
        int lastPos = stalls[0];
        
        for (int i = 1; i < stalls.length; i++) {
            if (stalls[i] - lastPos >= minDist) {
                count++;
                lastPos = stalls[i];
                if (count >= c) return true;
            }
        }
        
        return count >= c;
    }
}
```

```python
def aggressive_cows(stalls: List[int], c: int) -> int:
    stalls.sort()
    
    def can_place(min_dist: int) -> bool:
        count = 1
        last_pos = stalls[0]
        
        for i in range(1, len(stalls)):
            if stalls[i] - last_pos >= min_dist:
                count += 1
                last_pos = stalls[i]
                if count >= c:
                    return True
        
        return count >= c
    
    left, right = 1, stalls[-1] - stalls[0]
    
    while left < right:
        mid = left + (right - left + 1) // 2  # Ceiling
        
        if can_place(mid):
            left = mid
        else:
            right = mid - 1
    
    return left
```

## The Maximize Minimum Template

```
┌─────────────────────────────────────────────────────────────────┐
│              MAXIMIZE MINIMUM TEMPLATE                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  while (left < right) {                                         │
│      int mid = left + (right - left + 1) / 2;  // CEILING!     │
│                                                                 │
│      if (canAchieve(mid)) {                                     │
│          left = mid;      // Can achieve, try LARGER           │
│      } else {                                                   │
│          right = mid - 1; // Can't achieve, need SMALLER       │
│      }                                                          │
│  }                                                              │
│  return left;  // Maximum valid answer                          │
│                                                                 │
│  ─────────────────────────────────────────────────────────────  │
│                                                                 │
│  WHY CEILING DIVISION?                                          │
│                                                                 │
│  With floor division and left = mid:                            │
│  left=2, right=3 → mid=2 → left=2 (infinite loop!)             │
│                                                                 │
│  With ceiling division:                                         │
│  left=2, right=3 → mid=3 → either left=3 or right=2            │
│  Progress guaranteed!                                           │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## Complexity Analysis
- **Time:** O(n log n + n log D) where D = max - min
- **Space:** O(1) or O(n) for sorting

## Common Traps

| Trap | Why It's Wrong | Correct Approach |
|---|--------|---------|
| Floor division with `left = mid` | Infinite loop | Use ceiling: `(right - left + 1) / 2` |
| `right = mid` for maximize | Wrong direction | `right = mid - 1` |
| Forgetting to sort | Greedy placement needs sorted positions | Sort first |
| `count > c` instead of `>= c` | Off by one | Use `>= c` |

## Mind-Map Anchor

```
🐄 AGGRESSIVE COWS (Classic)
│
├── Signal: "maximize minimum distance + placement"
├── Template: 3 (Binary Search on Answer - MAXIMIZE)
├── Search space: [1, max_pos - min_pos]
├── Predicate: canPlace(minDist) - greedy placement
├── Key: CEILING division + left = mid
├── Don't forget: Sort positions first!
└── Mantra: "Greedy place, maximize the gap!"
```

--

--

# MAANG Coverage Map

## Problem Frequency by Company

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        BINARY SEARCH @ MAANG                                │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  GOOGLE (Most Frequent)                                                     │
│  ├── LC 4   Median of Two Sorted Arrays          ████████████ (Very High)  │
│  ├── LC 33  Search in Rotated Sorted Array       ████████████ (Very High)  │
│  ├── LC 34  Find First and Last Position         ██████████   (High)       │
│  ├── LC 875 Koko Eating Bananas                  ████████     (Medium)     │
│  └── LC 410 Split Array Largest Sum              ████████     (Medium)     │
│                                                                             │
│  META (Facebook)                                                            │
│  ├── LC 33  Search in Rotated Sorted Array       ████████████ (Very High)  │
│  ├── LC 162 Find Peak Element                    ██████████   (High)       │
│  ├── LC 278 First Bad Version                    ██████████   (High)       │
│  ├── LC 74  Search a 2D Matrix                   ████████     (Medium)     │
│  └── LC 240 Search a 2D Matrix II                ████████     (Medium)     │
│                                                                             │
│  AMAZON                                                                     │
│  ├── LC 33  Search in Rotated Sorted Array       ████████████ (Very High)  │
│  ├── LC 153 Find Minimum in Rotated Array        ██████████   (High)       │
│  ├── LC 1011 Capacity To Ship Packages           ██████████   (High)       │
│  ├── LC 704 Binary Search                        ████████     (Medium)     │
│  └── LC 35  Search Insert Position               ████████     (Medium)     │
│                                                                             │
│  APPLE                                                                      │
│  ├── LC 704 Binary Search                        ██████████   (High)       │
│  ├── LC 33  Search in Rotated Sorted Array       ████████     (Medium)     │
│  ├── LC 34  Find First and Last Position         ████████     (Medium)     │
│  └── LC 69  Sqrt(x)                              ██████       (Medium)     │
│                                                                             │
│  NETFLIX                                                                    │
│  ├── LC 4   Median of Two Sorted Arrays          ████████     (Medium)     │
│  ├── LC 875 Koko Eating Bananas                  ██████       (Medium)     │
│  └── LC 410 Split Array Largest Sum              ██████       (Medium)     │
│                                                                             │
│  MICROSOFT                                                                  │
│  ├── LC 33  Search in Rotated Sorted Array       ████████████ (Very High)  │
│  ├── LC 4   Median of Two Sorted Arrays          ██████████   (High)       │
│  ├── LC 153 Find Minimum in Rotated Array        ████████     (Medium)     │
│  └── LC 162 Find Peak Element                    ████████     (Medium)     │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Difficulty Distribution

```
┌─────────────────────────────────────────────────────────────────┐
│                    DIFFICULTY LEVELS                            │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  EASY (Warm-up)                                                 │
│  ├── LC 704  Binary Search                                      │
│  ├── LC 35   Search Insert Position                             │
│  ├── LC 278  First Bad Version                                  │
│  └── LC 69   Sqrt(x)                                            │
│                                                                 │
│  MEDIUM (Core Skills)                                           │
│  ├── LC 34   Find First and Last Position                       │
│  ├── LC 162  Find Peak Element                                  │
│  ├── LC 33   Search in Rotated Sorted Array                     │
│  ├── LC 81   Search in Rotated Sorted Array II                  │
│  ├── LC 153  Find Minimum in Rotated Sorted Array               │
│  ├── LC 154  Find Minimum in Rotated Sorted Array II            │
│  ├── LC 74   Search a 2D Matrix                                 │
│  ├── LC 240  Search a 2D Matrix II                              │
│  ├── LC 875  Koko Eating Bananas                                │
│  ├── LC 1011 Capacity To Ship Packages                          │
│  ├── LC 1482 Minimum Days to Make m Bouquets                    │
│  └── LC 1552 Magnetic Force Between Two Balls                   │
│                                                                 │
│  HARD (Advanced)                                                │
│  ├── LC 4    Median of Two Sorted Arrays                        │
│  ├── LC 410  Split Array Largest Sum                            │
│  └── LC 719  Find K-th Smallest Pair Distance                   │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

--

# Pattern Recognition Cheat Sheet

## Quick Pattern Identification

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                    BINARY SEARCH PATTERN RECOGNITION                        │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  KEYWORDS → PATTERN                                                         │
│                                                                             │
│  "sorted array" + "find value"                                              │
│  → Template 1: Standard Binary Search                                       │
│                                                                             │
│  "sorted array" + "first/last occurrence"                                   │
│  → Template 2: Boundary Search                                              │
│                                                                             │
│  "sorted array" + "insert position"                                         │
│  → Template 2: Lower Bound                                                  │
│                                                                             │
│  "rotated sorted array"                                                     │
│  → Template 1 + identify sorted half                                        │
│                                                                             │
│  "minimum in rotated"                                                       │
│  → Template 2: compare with right boundary                                  │
│                                                                             │
│  "peak element" / "local maximum"                                           │
│  → Template 2: climb the slope                                              │
│                                                                             │
│  "minimum speed/capacity/days to achieve X"                                 │
│  → Template 3: Binary Search on Answer (minimize)                           │
│                                                                             │
│  "maximize minimum distance"                                                │
│  → Template 3: Binary Search on Answer (maximize)                           │
│                                                                             │
│  "minimize maximum sum/load"                                                │
│  → Template 3: Binary Search on Answer (minimize)                           │
│                                                                             │
│  "k-th smallest in large space"                                             │
│  → Template 3 + counting                                                    │
│                                                                             │
│  "2D matrix" + "rows sorted" + "rows connected"                             │
│  → Treat as 1D array                                                        │
│                                                                             │
│  "2D matrix" + "rows sorted" + "cols sorted" (separately)                   │
│  → Staircase search from corner                                             │
│                                                                             │
│  "median of two sorted arrays"                                              │
│  → Binary search on partition                                               │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Template Selection Flowchart

```
                         ┌─────────────────────────────┐
                         │  Binary Search Problem?     │
                         └─────────────────────────────┘
                                      │
                    ┌─────────────────┼─────────────────┐
                    │                 │                 │
                    ▼                 ▼                 ▼
          ┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐
          │ Search in Array │ │ Find Boundary   │ │ Search Answer   │
          │ (exact value)   │ │ (first/last)    │ │ Space           │
          └─────────────────┘ └─────────────────┘ └─────────────────┘
                    │                 │                 │
                    ▼                 ▼                 ▼
          ┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐
          │   TEMPLATE 1    │ │   TEMPLATE 2    │ │   TEMPLATE 3    │
          │ left <= right   │ │ left < right    │ │ left < right    │
          │ return mid/-1   │ │ return left     │ │ + predicate()   │
          └─────────────────┘ └─────────────────┘ └─────────────────┘
                    │                 │                 │
                    ▼                 ▼                 ▼
          ┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐
          │ • LC 704        │ │ • LC 34         │ │ • LC 875        │
          │ • LC 33         │ │ • LC 278        │ │ • LC 1011       │
          │ • LC 74         │ │ • LC 162        │ │ • LC 410        │
          │ • LC 81         │ │ • LC 153        │ │ • LC 1552       │
          └─────────────────┘ └─────────────────┘ └─────────────────┘
```

--

# Common Mistakes and How to Avoid Them

## The Big 5 Binary Search Bugs

### 1. Integer Overflow

```java
// WRONG - can overflow when left + right > Integer.MAX_VALUE
int mid = (left + right) / 2;

// CORRECT - safe from overflow
int mid = left + (right - left) / 2;
```

### 2. Infinite Loop with Wrong Division

```java
// WRONG - infinite loop when left = mid and left + 1 = right
while (left < right) {
    int mid = left + (right - left) / 2;  // Floor division
    if (condition) {
        left = mid;  // left never advances when mid = left!
    } else {
        right = mid - 1;
    }
}

// CORRECT - use ceiling division when left = mid
while (left < right) {
    int mid = left + (right - left + 1) / 2;  // Ceiling division
    if (condition) {
        left = mid;
    } else {
        right = mid - 1;
    }
}
```

### 3. Off-by-One in Search Space

```java
// WRONG - might miss insertion at end
int right = nums.length - 1;  // For search insert

// CORRECT - allow insertion at end
int right = nums.length;
```

### 4. Wrong Comparison in Rotated Array

```java
// WRONG - fails when left == mid (single element in range)
if (nums[left] < nums[mid]) {
    // Left half sorted
}

// CORRECT - use <= to handle single element
if (nums[left] <= nums[mid]) {
    // Left half sorted
}
```

### 5. Forgetting to Validate Result

```java
// WRONG - assumes target exists
return left;

// CORRECT - validate before returning
if (left < nums.length && nums[left] == target) {
    return left;
}
return -1;
```

--

# Mastery Checklist

## Level 1: Foundation (Complete These First)

- [ ] **LC 704 - Binary Search**: Can implement Template 1 from memory
- [ ] **LC 35 - Search Insert Position**: Understand lower bound concept
- [ ] **LC 278 - First Bad Version**: Can identify first TRUE in monotonic sequence
- [ ] **LC 69 - Sqrt(x)**: Handle last TRUE (ceiling division)

## Level 2: Core Patterns (Interview Essentials)

- [ ] **LC 34 - Find First and Last Position**: Two binary searches
- [ ] **LC 162 - Find Peak Element**: Understand slope-based search
- [ ] **LC 33 - Search in Rotated Sorted Array**: Identify sorted half
- [ ] **LC 153 - Find Minimum in Rotated Array**: Compare with right boundary
- [ ] **LC 74 - Search a 2D Matrix**: 1D-2D index conversion
- [ ] **LC 240 - Search a 2D Matrix II**: Staircase search

## Level 3: Binary Search on Answer (High Value)

- [ ] **LC 875 - Koko Eating Bananas**: Basic BS on answer
- [ ] **LC 1011 - Capacity To Ship Packages**: Greedy validation
- [ ] **LC 410 - Split Array Largest Sum**: Minimize maximum pattern
- [ ] **LC 1552 - Magnetic Force**: Maximize minimum pattern

## Level 4: Advanced (Differentiation)

- [ ] **LC 4 - Median of Two Sorted Arrays**: Partition-based search
- [ ] **LC 719 - K-th Smallest Pair Distance**: BS + counting
- [ ] **LC 81 - Search in Rotated II**: Handle duplicates
- [ ] **LC 154 - Find Minimum in Rotated II**: Handle duplicates

## Self-Assessment Questions

After completing each problem, ask yourself:

1. **Template Recognition**: Which template did I use and why?
2. **Boundary Conditions**: Did I handle edge cases correctly?
3. **Loop Invariant**: What property is maintained throughout the search?
4. **Termination**: Why does the loop terminate? What's the final state?
5. **Optimization**: Is there a way to reduce the search space further?

--

# Quick Reference Card

```
╔═══════════════════════════════════════════════════════════════════════════════╗
║                     BINARY SEARCH QUICK REFERENCE                              ║
╠═══════════════════════════════════════════════════════════════════════════════╣
║                                                                                ║
║  TEMPLATE 1: EXACT MATCH                                                       ║
║  ┌──────────────────────────────────────────────────────────────────────────┐ ║
║  │ while (left <= right) {                                                  │ ║
║  │     mid = left + (right - left) / 2;                                     │ ║
║  │     if (arr[mid] == target) return mid;                                  │ ║
║  │     else if (arr[mid] < target) left = mid + 1;                          │ ║
║  │     else right = mid - 1;                                                │ ║
║  │ }                                                                        │ ║
║  │ return -1;                                                               │ ║
║  └──────────────────────────────────────────────────────────────────────────┘ ║
║                                                                                ║
║  TEMPLATE 2: FIRST TRUE (Boundary)                                             ║
║  ┌──────────────────────────────────────────────────────────────────────────┐ ║
║  │ while (left < right) {                                                   │ ║
║  │     mid = left + (right - left) / 2;                                     │ ║
║  │     if (condition(mid)) right = mid;                                     │ ║
║  │     else left = mid + 1;                                                 │ ║
║  │ }                                                                        │ ║
║  │ return left;                                                             │ ║
║  └──────────────────────────────────────────────────────────────────────────┘ ║
║                                                                                ║
║  TEMPLATE 2: LAST TRUE (Boundary)                                              ║
║  ┌──────────────────────────────────────────────────────────────────────────┐ ║
║  │ while (left < right) {                                                   │ ║
║  │     mid = left + (right - left + 1) / 2;  // CEILING!                    │ ║
║  │     if (condition(mid)) left = mid;                                      │ ║
║  │     else right = mid - 1;                                                │ ║
║  │ }                                                                        │ ║
║  │ return left;                                                             │ ║
║  └──────────────────────────────────────────────────────────────────────────┘ ║
║                                                                                ║
║  TEMPLATE 3: BINARY SEARCH ON ANSWER                                           ║
║  ┌──────────────────────────────────────────────────────────────────────────┐ ║
║  │ left = minPossibleAnswer, right = maxPossibleAnswer;                     │ ║
║  │ while (left < right) {                                                   │ ║
║  │     mid = left + (right - left) / 2;  // or ceiling for maximize        │ ║
║  │     if (canAchieve(mid)) right = mid;  // or left = mid for maximize    │ ║
║  │     else left = mid + 1;               // or right = mid - 1            │ ║
║  │ }                                                                        │ ║
║  │ return left;                                                             │ ║
║  └──────────────────────────────────────────────────────────────────────────┘ ║
║                                                                                ║
║  KEY FORMULAS:                                                                 ║
║  • mid = left + (right - left) / 2          (floor - for first TRUE)          ║
║  • mid = left + (right - left + 1) / 2      (ceiling - for last TRUE)         ║
║  • row = index / cols, col = index % cols   (1D to 2D conversion)             ║
║  • hours = (pile + speed - 1) / speed       (ceiling division)                ║
║                                                                                ║
║  REMEMBER:                                                                     ║
║  • Monotonic property is REQUIRED for binary search                           ║
║  • Always use left + (right - left) / 2 to avoid overflow                     ║
║  • Use ceiling division when left = mid to avoid infinite loop                ║
║  • Validate result when searching for specific value                          ║
║                                                                                ║
╚═══════════════════════════════════════════════════════════════════════════════╝
```

--

# Interview Tips

## How to Communicate Binary Search in Interviews

### 1. State the Approach Clearly

> "I'll use binary search because the problem has a monotonic property - [explain the property]. The search space is [define bounds], and I'll find the boundary where [condition changes]."

### 2. Identify the Template

> "This is a [first occurrence / last occurrence / exact match / answer space] problem, so I'll use Template [1/2/3]."

### 3. Define the Predicate (for Template 3)

> "My predicate function checks whether [condition]. This is monotonic because [explain why]."

### 4. Handle Edge Cases

> "I need to handle: empty array, single element, target not found, target at boundaries."

### 5. Verify Termination

> "The loop terminates because [left increases / right decreases] each iteration, and they converge when [condition]."

## Common Follow-up Questions

1. **"What if there are duplicates?"**
   - For rotated arrays: add duplicate handling (skip when left == mid == right)
   - For first/last: Template 2 handles this naturally

2. **"Can you optimize further?"**
   - Early termination in predicate functions
   - Tighter bounds for search space

3. **"What's the time complexity?"**
   - Standard: O(log n)
   - BS on Answer: O(n × log(range)) where n is predicate cost

4. **"What if the array is very large?"**
   - Binary search is already optimal for sorted data
   - Consider memory access patterns for cache efficiency

--

# Summary: The Binary Search Mindset

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                     THE BINARY SEARCH MINDSET                               │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  1. IDENTIFY THE MONOTONIC PROPERTY                                         │
│     "What divides my search space into two distinct regions?"               │
│                                                                             │
│  2. DEFINE THE SEARCH SPACE                                                 │
│     "What are the minimum and maximum possible answers?"                    │
│                                                                             │
│  3. CHOOSE THE RIGHT TEMPLATE                                               │
│     "Am I finding exact value, first/last boundary, or optimal answer?"     │
│                                                                             │
│  4. IMPLEMENT CAREFULLY                                                     │
│     "Did I handle overflow, infinite loops, and edge cases?"                │
│                                                                             │
│  5. VERIFY THE RESULT                                                       │
│     "Does my answer actually satisfy the original problem?"                 │
│                                                                             │
│  ─────────────────────────────────────────────────────────────────────────  │
│                                                                             │
│  THE ONE SENTENCE:                                                          │
│                                                                             │
│  "Binary Search finds a BOUNDARY in a MONOTONIC space                       │
│   by repeatedly halving the search range."                                  │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

--

*End of Binary Search Patterns Deep Dive*

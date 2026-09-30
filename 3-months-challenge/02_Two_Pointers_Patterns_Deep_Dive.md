# Section 5 — Two Pointers Patterns Deep Dive (MAANG L5 Coverage)

--

# INDEX — Quick Navigation (18 Patterns)

## Core Concepts
| Section | Description |
|-----|-------|
| [The "One Sentence"](#the-one-sentence-that-unlocks-all-two-pointer-problems) | Unlocks all Two Pointer problems |
| [5 Pointer Techniques](#the-5-two-pointer-techniques-your-weapons) | Your configurations |
| [Decision Tree](#the-master-decision-tree-pick-your-weapon-in-10-seconds) | Pick your weapon |

--

## Opposite Direction Family (0-4)
| # | Pattern | LeetCode |
|--|-----|-----|
| 0 | [Two Sum II](#pattern-0-two-sum-ii-leetcode-167) | 167 |
| 1 | [Valid Palindrome](#pattern-1-valid-palindrome-leetcode-125) | 125 |
| 2 | [Valid Palindrome II](#pattern-2-valid-palindrome-ii-leetcode-680) | 680 |
| 3 | [Container With Most Water](#pattern-3-container-with-most-water-leetcode-11) | 11 |
| 4 | [Trapping Rain Water](#pattern-4-trapping-rain-water-leetcode-42) | 42 |

## Read-Write Pointer Family (5-8)
| # | Pattern | LeetCode |
|--|-----|-----|
| 5 | [Remove Duplicates from Sorted Array](#pattern-5-remove-duplicates-from-sorted-array-leetcode-26) | 26 |
| 6 | [Move Zeroes](#pattern-6-move-zeroes-leetcode-283) | 283 |
| 7 | [Remove Element](#pattern-7-remove-element-leetcode-27) | 27 |
| 8 | [Is Subsequence](#pattern-8-is-subsequence-leetcode-392) | 392 |

## Three Pointers / Partitioning (9-12)
| # | Pattern | LeetCode |
|--|-----|-----|
| 9 | [Sort Colors](#pattern-9-sort-colors-leetcode-75) | 75 |
| 10 | [3Sum](#pattern-10-3sum-leetcode-15) | 15 |
| 11 | [3Sum Closest](#pattern-11-3sum-closest-leetcode-16) | 16 |
| 12 | [4Sum](#pattern-12-4sum-leetcode-18) | 18 |

## Array Manipulation (13-17)
| # | Pattern | LeetCode |
|--|-----|-----|
| 13 | [Reverse String](#pattern-13-reverse-string-leetcode-344) | 344 |
| 14 | [Squares of Sorted Array](#pattern-14-squares-of-sorted-array-leetcode-977) | 977 |
| 15 | [Merge Sorted Array](#pattern-15-merge-sorted-array-leetcode-88) | 88 |
| 16 | [Partition Labels](#pattern-16-partition-labels-leetcode-763) | 763 |
| 17 | [Longest Mountain in Array](#pattern-17-longest-mountain-in-array-leetcode-845) | 845 |

## Reference Sections
| Section |
|-----|
| [MAANG Coverage Map](#maang-coverage-map) |
| [Pattern Recognition Cheat Sheet](#pattern-recognition-cheat-sheet) |
| [Mastery Checklist](#mastery-checklist) |

--

# The "One Sentence That Unlocks All Two Pointer Problems"

> **"Which TWO positions do I need to track, and what DECISION moves each pointer?"**

That's the entire subject. Every two pointer problem — from easy to hard — is just:
1. **Pick** the right starting positions (same end, opposite ends, different arrays)
2. **Know** what condition moves each pointer
3. **Decide** which pointer to move based on what you observe
4. **Terminate** when pointers meet or cross

--

## THE JUNIOR DEV CHEAT CARD (Memorize This!)

```
╔═══════════════════════════════════════════════════════════════════════╗
║                     TWO POINTER PROBLEM? USE THIS!                     ║
╠═══════════════════════════════════════════════════════════════════════╣
║                                                                        ║
║  STEP 1: "What type of problem is this?"                              ║
║                                                                        ║
║  ┌────────────────────────────────────────────────────────────────┐   ║
║  │ "Find pair in sorted array"  → Opposite ends, converge         │   ║
║  │ "Check palindrome"           → Opposite ends, compare          │   ║
║  │ "Remove/compact in-place"    → Read-Write pointers             │   ║
║  │ "Partition array"            → Three pointers (Dutch Flag)     │   ║
║  │ "Find triplet/quadruplet"    → Fix outer + two-pointer inner   │   ║
║  │ "Merge sorted arrays"        → Two pointers on two arrays      │   ║
║  └────────────────────────────────────────────────────────────────┘   ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  THE GOLDEN RULE: "SORTED = TWO POINTERS, UNSORTED = HASHMAP"         ║
║                                                                        ║
║  Before using two pointers, ask:                                      ║
║  "Is the array sorted or can I sort it?"                              ║
║  If YES → Two Pointers (O(n) time, O(1) space)                        ║
║  If NO  → HashMap might be better (O(n) time, O(n) space)             ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  THE 5 TECHNIQUES (Pick one based on problem type):                   ║
║                                                                        ║
║  ┌──────────────────┬─────────────────────────────────────────────┐   ║
║  │ Opposite/Converge│ left=0, right=n-1, move toward each other   │   ║
║  │ Same Direction   │ slow=0, fast=0, both move right             │   ║
║  │ Read-Write       │ write=0, read=0, write valid elements       │   ║
║  │ Three Pointers   │ low=0, mid=0, high=n-1 (Dutch Flag)         │   ║
║  │ Sliding Window   │ left=0, right expands, left contracts       │   ║
║  └──────────────────┴─────────────────────────────────────────────┘   ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  COMMON PATTERNS CODE:                                                 ║
║                                                                        ║
║  // Opposite Direction (pair sum)                                     ║
║  while (left < right) {                                               ║
║      int sum = arr[left] + arr[right];                                ║
║      if (sum == target) return true;                                  ║
║      else if (sum < target) left++;   // need bigger                  ║
║      else right-;                    // need smaller                 ║
║  }                                                                     ║
║                                                                        ║
║  // Read-Write (remove duplicates)                                    ║
║  int write = 0;                                                       ║
║  for (int read = 0; read < n; read++) {                               ║
║      if (shouldKeep(arr[read])) {                                     ║
║          arr[write++] = arr[read];                                    ║
║      }                                                                ║
║  }                                                                     ║
║  return write;  // new length                                         ║
║                                                                        ║
╚═══════════════════════════════════════════════════════════════════════╝
```

--

## QUICK START: The 60-Second Two Pointer Approach

### The ONE Question That Solves Most Problems:

> **"If I move this pointer, does it get me closer to the answer or eliminate possibilities I don't need?"**

### The Simple Decision Process:

```
1. "Need to find a pair with target sum in SORTED array?"
   → Setup: left = 0, right = n-1
   → Loop: if sum < target, left++; if sum > target, right-
   → Why: Moving left increases sum, moving right decreases sum

2. "Need to check if something is a palindrome?"
   → Setup: left = 0, right = n-1
   → Loop: compare arr[left] and arr[right], move both inward
   → Stop: when left >= right (they've met or crossed)

3. "Need to remove/compact elements in-place?"
   → Setup: write = 0 (where to put next valid element)
   → Loop: read scans everything, write only advances for valid elements
   → Return: write (the new length)

4. "Need to partition into groups (like 0s, 1s, 2s)?"
   → Setup: low = 0, mid = 0, high = n-1
   → Loop: swap elements to their correct region
   → Invariant: [0..low-1]=group1, [low..mid-1]=group2, [high+1..n-1]=group3
```

### The "Why Move This Pointer?" Mantra

This is the key insight for two pointers:

```
WRONG THINKING:
"I'll just try moving left... or maybe right... let me see what happens"

RIGHT THINKING:
"The sum is too small. In a sorted array, the only way to increase
the sum is to move left pointer right (to a larger value).
Moving right pointer left would make sum even smaller. So I MUST move left."
```

--

## Why Two Pointers Feel Easy But Bugs Happen

**The Problem:** You know the technique but mess up the termination condition or pointer movement.

**The Fix:** Before coding, explicitly answer:
1. What are my loop invariants? (What's true at every iteration?)
2. When exactly do I stop? (`left < right` vs `left <= right`)
3. Which pointer moves in which condition?
4. What happens when pointers meet?

--

# The 5 Two Pointer Techniques (Your "Weapons")

Think of these as your **5 weapons**. Every two pointer problem uses one (or a combination). Learn to recognize which weapon to draw!

--

## Technique 1: Opposite Direction (Converging Pointers)

**Mental Trigger:** "Two people walking toward each other from opposite ends"

**When to Use:**
- Find pair with target sum (sorted array)
- Check palindrome
- Container with most water
- Trapping rain water
- Any problem where you need to consider pairs from both ends

**The Visual Story:**
```
Imagine two people on a bridge, starting at opposite ends:

  [Person A]                              [Person B]
      ↓                                        ↓
  [1] [2] [3] [4] [5] [6] [7] [8] [9] [10]
   ↑                                        ↑
  left                                    right

They walk toward each other. At each step, they make a decision:
- If their combined value is too small → A moves right (toward bigger)
- If their combined value is too big → B moves left (toward smaller)
- If perfect → Found it!

They meet in the middle, having checked all useful pairs in O(n)!
```

**The Code Pattern:**
```java
int left = 0;
int right = arr.length - 1;

while (left < right) {
    // Compute something using arr[left] and arr[right]
    int value = compute(arr[left], arr[right]);
    
    if (value == target) {
        // Found it!
        return result;
    } else if (value < target) {
        left++;   // Need bigger value, move left pointer right
    } else {
        right-;  // Need smaller value, move right pointer left
    }
}
```

**Memory Trick:** "Start at ends, walk toward middle, move the pointer that helps"

--

## Technique 2: Same Direction (Fast-Slow / Read-Write)

**Mental Trigger:** "Two runners on a track, one faster than the other"

**When to Use:**
- Remove duplicates in-place
- Move zeros to end
- Remove specific elements
- Compact array based on condition
- Find middle of linked list
- Detect cycle in linked list

**The Visual Story:**
```
Imagine a conveyor belt with a quality inspector:

READ pointer: Scans every item on the belt
WRITE pointer: Only moves when a GOOD item is found

  [1] [1] [2] [2] [3]    ← Items on belt
   ↑
  read/write (both start at 0)

Step 1: arr[0]=1 is first, always keep it. write stays at 0.
Step 2: read=1, arr[1]=1 same as arr[write]=1, skip it.
Step 3: read=2, arr[2]=2 different! write++, arr[1]=2
Step 4: read=3, arr[3]=2 same as arr[write]=2, skip it.
Step 5: read=4, arr[4]=3 different! write++, arr[2]=3

Result: [1, 2, 3, _, _] with write=2, so length = write+1 = 3
```

**The Code Pattern:**
```java
int write = 0;  // Where to write next valid element

for (int read = 0; read < arr.length; read++) {
    if (isValid(arr[read])) {  // Condition to keep element
        arr[write] = arr[read];
        write++;
    }
}

return write;  // New length of compacted array
```

**Memory Trick:** "Read scans all, Write only moves for keepers"

--

## Technique 3: Read-Write Pointers (In-Place Transformation)

**Mental Trigger:** "Editor with a cursor — read ahead, write behind"

**When to Use:**
- Remove duplicates from sorted array
- Remove element by value
- Move zeros to end
- Compress string in-place
- Any "modify array in-place" problem

**The Visual Story:**
```
Think of it like editing a document:
- READ cursor scans through the entire document
- WRITE cursor only moves when you want to KEEP something

Original: [0, 1, 0, 3, 12]  (Move zeros to end)

  read=0: arr[0]=0, skip (it's zero)
  read=1: arr[1]=1, keep! arr[write]=1, write++
  read=2: arr[2]=0, skip
  read=3: arr[3]=3, keep! arr[write]=3, write++
  read=4: arr[4]=12, keep! arr[write]=12, write++
  
After read pass: [1, 3, 12, 3, 12], write=3
Fill rest with zeros: [1, 3, 12, 0, 0]
```

**The Code Pattern:**
```java
// Remove zeros example
int write = 0;

// Pass 1: Move non-zeros to front
for (int read = 0; read < arr.length; read++) {
    if (arr[read] != 0) {
        arr[write++] = arr[read];
    }
}

// Pass 2: Fill rest with zeros
while (write < arr.length) {
    arr[write++] = 0;
}
```

**Memory Trick:** "Write pointer marks the boundary of 'processed good elements'"

--

## Technique 4: Three Pointers (Dutch National Flag)

**Mental Trigger:** "Sorting into three buckets without extra space"

**When to Use:**
- Sort Colors (0s, 1s, 2s)
- Partition array around pivot
- Three-way partitioning
- Any problem with exactly 3 categories

**The Visual Story:**
```
Imagine sorting balls into three buckets: Red(0), White(1), Blue(2)

Three pointers maintain three regions:
- [0..low-1]     = All 0s (Red) — DONE
- [low..mid-1]   = All 1s (White) — DONE  
- [mid..high]    = Unknown — TO PROCESS
- [high+1..n-1]  = All 2s (Blue) — DONE

  [2, 0, 1, 2, 1, 0]
   ↑              ↑
  low/mid        high

Process arr[mid]:
- If 0: swap with low, low++, mid++ (0 goes to red region)
- If 1: mid++ (1 is already in place)
- If 2: swap with high, high- (2 goes to blue region, DON'T move mid!)

Why not move mid when swapping with high?
Because we don't know what we swapped IN — need to check it!
```

**The Code Pattern:**
```java
int low = 0, mid = 0, high = arr.length - 1;

while (mid <= high) {
    if (arr[mid] == 0) {
        swap(arr, low, mid);
        low++;
        mid++;
    } else if (arr[mid] == 1) {
        mid++;
    } else {  // arr[mid] == 2
        swap(arr, mid, high);
        high-;
        // Don't increment mid! We need to check swapped element
    }
}
```

**Memory Trick:** "0 goes left (swap with low), 2 goes right (swap with high), 1 stays"

--

## Technique 5: Sliding Window Hybrid

**Mental Trigger:** "Elastic window that expands and contracts"

**When to Use:**
- Minimum window substring
- Longest substring without repeating
- Maximum sum subarray of size k
- Problems combining two pointers with window state

**The Visual Story:**
```
Imagine a window on a train:
- RIGHT edge expands to include more scenery
- LEFT edge contracts to remove unwanted parts

  [a, b, c, a, b, c, b, b]
   ↑     ↑
  left  right (window = "abc")

Expand right to include more characters.
When window has duplicates or violates condition, contract left.
Track the best valid window seen.
```

**The Code Pattern:**
```java
int left = 0;
Map<Character, Integer> window = new HashMap<>();

for (int right = 0; right < s.length(); right++) {
    // Expand: add s[right] to window
    char c = s.charAt(right);
    window.put(c, window.getOrDefault(c, 0) + 1);
    
    // Contract: shrink window while invalid
    while (isInvalid(window)) {
        char leftChar = s.charAt(left);
        window.put(leftChar, window.get(leftChar) - 1);
        left++;
    }
    
    // Update answer with current valid window
    updateAnswer(right - left + 1);
}
```

**Memory Trick:** "Right expands greedily, Left contracts when needed"

--

# The Master Decision Tree — Pick Your Weapon in 10 Seconds

When you see a two pointer problem, ask these questions IN ORDER:

```
┌─────────────────────────────────────────────────────────────┐
│                    TWO POINTER PROBLEM                       │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
        ┌─────────────────────────────────────────┐
        │  1. Is array SORTED (or can be sorted)? │
        └─────────────────────────────────────────┘
                    │YES                │NO
                    ▼                   ▼
        ┌───────────────────┐   ┌─────────────────────────────┐
        │ Two Pointers!     │   │ Consider HashMap instead    │
        │ O(1) space        │   │ Or sort first if allowed    │
        └───────────────────┘   └─────────────────────────────┘
                    │
                    ▼
        ┌─────────────────────────────────────────┐
        │  2. Find PAIR with target property?     │
        └─────────────────────────────────────────┘
                    │YES                │NO
                    ▼                   ▼
        ┌───────────────────┐   ┌─────────────────────────────┐
        │ OPPOSITE DIRECTION│   │ 3. Remove/Compact in-place? │
        │ left=0, right=n-1 │   └─────────────────────────────┘
        │                   │           │YES            │NO
        │ Patterns: 0-4     │           ▼               ▼
        └───────────────────┘   ┌───────────────┐  ┌──────────────────────┐
                                │ READ-WRITE    │  │ 4. Partition into    │
                                │ write=0       │  │    3 groups?         │
                                │               │  └──────────────────────┘
                                │ Patterns: 5-8 │      │YES         │NO
                                └───────────────┘      ▼            ▼
                                               ┌────────────┐ ┌─────────────────┐
                                               │ THREE PTR  │ │ 5. Find triplet │
                                               │ Dutch Flag │ │    or more?     │
                                               │            │ └─────────────────┘
                                               │ Pattern: 9 │     │YES      │NO
                                               └────────────┘     ▼         ▼
                                                          ┌────────────┐ ┌──────────┐
                                                          │ FIX OUTER  │ │ SLIDING  │
                                                          │ + 2-ptr    │ │ WINDOW   │
                                                          │            │ │          │
                                                          │ Patterns:  │ │ Pattern: │
                                                          │ 10,11,12   │ │ 16       │
                                                          └────────────┘ └──────────┘
```

--

# The 4-Question Template (Ask For EVERY Pattern)

Before writing ANY code, answer these 4 questions:

```
┌────────────────────────────────────────────────────────────────────┐
│ 1. SETUP: Where do my pointers start?                              │
│    □ left=0, right=n-1 (opposite ends)                             │
│    □ write=0, read=0 (same end, read-write)                        │
│    □ low=0, mid=0, high=n-1 (three pointers)                       │
│    □ i fixed, left=i+1, right=n-1 (fix one + two-pointer)          │
├────────────────────────────────────────────────────────────────────┤
│ 2. TERMINATION: When do I stop?                                    │
│    □ left < right (converging, don't want same element)            │
│    □ left <= right (converging, might need same element)           │
│    □ read < n (read-write, scan entire array)                      │
│    □ mid <= high (Dutch flag, process all unknowns)                │
├────────────────────────────────────────────────────────────────────┤
│ 3. MOVEMENT: What moves each pointer?                              │
│    □ left++ when value too small / left element processed          │
│    □ right- when value too big / right element processed          │
│    □ write++ only when element should be kept                      │
│    □ Both move when match found (skip duplicates)                  │
├────────────────────────────────────────────────────────────────────┤
│ 4. EDGE CASES: What special cases exist?                           │
│    □ Empty array (n == 0)                                          │
│    □ Single element (n == 1)                                       │
│    □ All same elements                                             │
│    □ No valid answer exists                                        │
│    □ Duplicates in input                                           │
└────────────────────────────────────────────────────────────────────┘
```

--

# PATTERN 0: Two Sum II (LeetCode 167)

## Pattern Recognition Signal

**When you see:** "sorted array", "find two numbers", "sum equals target", "return indices"

**Instant thought:** "Opposite direction pointers — classic converging!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  numbers = [2, 7, 11, 15], target = 9
Output: [1, 2] (1-indexed)

Find two numbers that add up to target.
Array is SORTED — this is the key insight!
```

### The Bridge Analogy

```
Imagine two people on a number line bridge:

  [2]  [7]  [11]  [15]
   ↑                ↑
  left            right
  
They want their combined weight to equal 9.

Current sum = 2 + 15 = 17 > 9
  "We're too heavy! The person on the right should step to a smaller number."
  right-

  [2]  [7]  [11]  [15]
   ↑          ↑
  left      right

Current sum = 2 + 11 = 13 > 9
  "Still too heavy! Right person steps again."
  right-

  [2]  [7]  [11]  [15]
   ↑    ↑
  left right

Current sum = 2 + 7 = 9 == target
  "Perfect! Found it!"
```

### Why This Works (The Key Insight)

```
In a SORTED array:
- Moving left pointer RIGHT → increases the sum (going to larger values)
- Moving right pointer LEFT → decreases the sum (going to smaller values)

So we have CONTROL over the sum:
- Sum too small? Increase it by moving left++
- Sum too big? Decrease it by moving right-
- Sum perfect? Found the answer!

This is why sorting is essential — it gives us directional control!
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `numbers = [2, 7, 11, 15]`, `target = 9`

```
Initial State:
  left = 0, right = 3
  
  [2]  [7]  [11]  [15]
   ↑                ↑
  left            right

═══════════════════════════════════════════════════════════

Step 1: sum = numbers[0] + numbers[3] = 2 + 15 = 17

  17 > 9 (target)
  Sum too big → need smaller → right-
  
  [2]  [7]  [11]  [15]
   ↑          ↑
  left      right

═══════════════════════════════════════════════════════════

Step 2: sum = numbers[0] + numbers[2] = 2 + 11 = 13

  13 > 9 (target)
  Sum too big → need smaller → right-
  
  [2]  [7]  [11]  [15]
   ↑    ↑
  left right

═══════════════════════════════════════════════════════════

Step 3: sum = numbers[0] + numbers[1] = 2 + 7 = 9

  9 == 9 (target)
  FOUND IT!
  
  Return [left+1, right+1] = [1, 2] (1-indexed)

═══════════════════════════════════════════════════════════

Output: [1, 2] ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public int[] twoSum(int[] numbers, int target) {
    int left = 0;                          // Start at leftmost (smallest)
    int right = numbers.length - 1;        // Start at rightmost (largest)
    
    while (left < right) {                 // While pointers haven't crossed
        int sum = numbers[left] + numbers[right];
        
        if (sum == target) {
            // Found it! Return 1-indexed positions
            return new int[] {left + 1, right + 1};
        } else if (sum < target) {
            // Sum too small, need bigger numbers
            // Only way to increase: move left to larger value
            left++;
        } else {
            // Sum too big, need smaller numbers
            // Only way to decrease: move right to smaller value
            right-;
        }
    }
    
    // Problem guarantees a solution, but good practice
    return new int[] {-1, -1};
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Using HashMap | Works but O(n) space | Two pointers is O(1) space for sorted input |
| Forgetting 1-indexed | LeetCode 167 uses 1-indexed output | Return `left+1, right+1` |
| `left <= right` | Might use same element twice | Use `left < right` |
| Moving wrong pointer | Breaks the algorithm | Sum < target → left++, Sum > target → right- |
| Not checking sorted | Two pointers only works on sorted | Verify input is sorted |

--

## Mind-Map Anchor

```
TWO SUM II
    │
    ├── Trigger: "sorted array" + "pair sum"
    │
    ├── Setup: left=0, right=n-1
    │
    ├── Logic: sum < target → left++
    │          sum > target → right-
    │          sum == target → found!
    │
    ├── Why it works: Sorted gives directional control
    │
    └── Complexity: O(n) time, O(1) space
```

--

# PATTERN 1: Valid Palindrome (LeetCode 125)

## Pattern Recognition Signal

**When you see:** "palindrome", "reads same forward and backward", "ignore non-alphanumeric"

**Instant thought:** "Opposite direction — compare from both ends!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  "A man, a plan, a canal: Panama"
Output: true

Ignore spaces, punctuation, and case.
Check if it reads the same forward and backward.
```

### The Mirror Analogy

```
Imagine holding a mirror at the center of a word:

  "racecar"
   ↑     ↑
  left  right
  
  r == r ✓ → move both inward
  
  "racecar"
    ↑   ↑
   left right
   
  a == a ✓ → move both inward
  
  "racecar"
     ↑ ↑
    left right
    
  c == c ✓ → move both inward
  
  "racecar"
      ↑
   left=right (they've met!)
   
All characters matched → It's a palindrome!
```

### The Cleaning Step

```
Real input: "A man, a plan, a canal: Panama"

We need to:
1. Skip non-alphanumeric characters
2. Compare case-insensitively

  "A man, a plan, a canal: Panama"
   ↑                            ↑
  left                        right
  
  'A' vs 'a' → toLowerCase → 'a' == 'a' ✓
  
  Move both, skip spaces and punctuation...
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `"A man, a plan, a canal: Panama"`

```
Cleaned view: "amanaplanacanalpanama"

Initial:
  left = 0 ('A'), right = 29 ('a')
  
  "A man, a plan, a canal: Panama"
   ↑                            ↑
  left                        right

═══════════════════════════════════════════════════════════

Step 1: 
  left points to 'A' (alphanumeric)
  right points to 'a' (alphanumeric)
  toLowerCase: 'a' == 'a' ✓
  left++, right-

═══════════════════════════════════════════════════════════

Step 2:
  left points to ' ' (space) → skip, left++
  left points to 'm' (alphanumeric)
  right points to 'm' (alphanumeric)
  'm' == 'm' ✓
  left++, right-

═══════════════════════════════════════════════════════════

[... continues comparing a-a, n-n, a-a, p-p, l-l, a-a, n-n, a-a, c-c ...]

═══════════════════════════════════════════════════════════

Final: left >= right (pointers crossed)
All characters matched!

Output: true ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public boolean isPalindrome(String s) {
    int left = 0;
    int right = s.length() - 1;
    
    while (left < right) {
        // Skip non-alphanumeric from left
        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
            left++;
        }
        
        // Skip non-alphanumeric from right
        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
            right-;
        }
        
        // Compare characters (case-insensitive)
        if (Character.toLowerCase(s.charAt(left)) != 
            Character.toLowerCase(s.charAt(right))) {
            return false;  // Mismatch found
        }
        
        // Move both pointers inward
        left++;
        right-;
    }
    
    return true;  // All characters matched
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Forgetting to skip non-alphanumeric | Compares spaces/punctuation | Add skip loops |
| Case-sensitive comparison | 'A' != 'a' fails | Use `toLowerCase()` |
| `left < right` in skip loops | Might go out of bounds | Always check `left < right` |
| Creating new string | O(n) extra space | Use two pointers on original |
| Off-by-one in empty string | Edge case | `left < right` handles it |

--

## Mind-Map Anchor

```
VALID PALINDROME
    │
    ├── Trigger: "palindrome" + "ignore non-alphanumeric"
    │
    ├── Setup: left=0, right=n-1
    │
    ├── Logic: Skip non-alphanumeric
    │          Compare case-insensitively
    │          Any mismatch → false
    │
    ├── Edge cases: Empty string, single char, all non-alphanumeric
    │
    └── Complexity: O(n) time, O(1) space
```

--

# PATTERN 2: Valid Palindrome II (LeetCode 680)

## Pattern Recognition Signal

**When you see:** "palindrome", "delete at most one character", "can become palindrome"

**Instant thought:** "Two pointers with one skip allowance!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  "abca"
Output: true (remove 'b' or 'c' to get "aca" or "aba")

Can we make it a palindrome by removing AT MOST one character?
```

### The "One Mistake Allowed" Analogy

```
Imagine a strict teacher checking palindromes, but today they're lenient:
"I'll forgive ONE mistake."

  "abca"
   ↑  ↑
  left right
  
  'a' == 'a' ✓ → move inward
  
  "abca"
    ↑↑
   left right
   
  'b' != 'c' ✗ → MISMATCH!
  
  But we have one "forgiveness" left!
  Try two options:
  1. Skip left ('b'): check if "ca" is palindrome → "c" vs "a" ✗
  2. Skip right ('c'): check if "ba" is palindrome → "b" vs "a" ✗
  
  Wait, let me reconsider...
  
  Actually for "abca":
  - Skip 'b': remaining is "aca" → palindrome ✓
  - Skip 'c': remaining is "aba" → palindrome ✓
  
  Either works! Return true.
```

### The Key Insight

```
When we find a mismatch at positions left and right:
- Option 1: Skip s[left], check if s[left+1..right] is palindrome
- Option 2: Skip s[right], check if s[left..right-1] is palindrome

If EITHER option works, return true.
If BOTH fail, return false.
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `"abca"`

```
Initial:
  left = 0, right = 3
  
  "abca"
   ↑  ↑
  left right

═══════════════════════════════════════════════════════════

Step 1: s[0]='a', s[3]='a'
  'a' == 'a' ✓
  left++, right-
  
  "abca"
    ↑↑
   left right

═══════════════════════════════════════════════════════════

Step 2: s[1]='b', s[2]='c'
  'b' != 'c' ✗ MISMATCH!
  
  Use our one "skip" allowance:
  
  Option 1: Skip left, check s[2..2] = "c"
    "c" is a palindrome (single char) ✓
    
  Option 2: Skip right, check s[1..1] = "b"  
    "b" is a palindrome (single char) ✓
  
  At least one option works!

═══════════════════════════════════════════════════════════

Output: true ✓
```

**Another Example:** `"abc"`

```
  "abc"
   ↑ ↑
  left right
  
  'a' != 'c' ✗ MISMATCH at first comparison!
  
  Option 1: Skip left, check "bc" → 'b' != 'c' ✗
  Option 2: Skip right, check "ab" → 'a' != 'b' ✗
  
  Both options fail!

Output: false ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public boolean validPalindrome(String s) {
    int left = 0;
    int right = s.length() - 1;
    
    while (left < right) {
        if (s.charAt(left) != s.charAt(right)) {
            // Mismatch found! Try skipping one character
            // Option 1: skip left character
            // Option 2: skip right character
            return isPalindromeRange(s, left + 1, right) || 
                   isPalindromeRange(s, left, right - 1);
        }
        left++;
        right-;
    }
    
    return true;  // No mismatch found, already a palindrome
}

// Helper: check if s[i..j] is a palindrome
private boolean isPalindromeRange(String s, int i, int j) {
    while (i < j) {
        if (s.charAt(i) != s.charAt(j)) {
            return false;
        }
        i++;
        j-;
    }
    return true;
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Trying all possible deletions | O(n²) time | Only try deletion at mismatch point |
| Forgetting to check both options | Might miss valid solution | Use `||` to try both |
| Deleting more than one | Problem says "at most one" | Only one skip allowed |
| Not handling already-palindrome | Should return true | Main loop handles it |

--

## Mind-Map Anchor

```
VALID PALINDROME II
    │
    ├── Trigger: "palindrome" + "delete at most one"
    │
    ├── Setup: left=0, right=n-1
    │
    ├── Logic: On mismatch, try TWO options:
    │          1. Skip left char
    │          2. Skip right char
    │          Either works → true
    │
    ├── Key insight: Only need to try skip at mismatch point
    │
    └── Complexity: O(n) time, O(1) space
```

--

# PATTERN 3: Container With Most Water (LeetCode 11)

## Pattern Recognition Signal

**When you see:** "container", "most water", "two lines", "maximize area"

**Instant thought:** "Opposite direction — move the shorter line!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
Output: 49

Find two lines that form a container holding the most water.
Area = width × min(height[left], height[right])
```

### The Water Tank Analogy

```
Imagine vertical bars as walls of a water tank:

    |         |     
    |         |   | 
    | |       |   | 
    | |   |   |   | 
    | |   | | |   | 
    | |   | | | | | 
    | | | | | | | | 
  | | | | | | | | | 
  1 8 6 2 5 4 8 3 7
  
Water level is limited by the SHORTER wall.
Area = distance between walls × shorter wall height.

Start with widest possible container (left=0, right=8):
  Width = 8, Height = min(1, 7) = 1
  Area = 8 × 1 = 8

Can we do better? YES! But which wall do we move?
```

### The Key Insight (Why Move the Shorter Wall?)

```
Current: left wall = 1, right wall = 7, width = 8
Area = 8 × min(1, 7) = 8 × 1 = 8

If we move the TALLER wall (right):
  - Width decreases (bad)
  - Height is still limited by shorter wall (1)
  - Area can only decrease or stay same!

If we move the SHORTER wall (left):
  - Width decreases (bad)
  - BUT height might increase (if new wall is taller)
  - Area MIGHT increase!

Conclusion: Always move the shorter wall — it's the only way to potentially improve!
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `height = [1, 8, 6, 2, 5, 4, 8, 3, 7]`

```
Initial:
  left = 0 (height=1), right = 8 (height=7)
  
  1 8 6 2 5 4 8 3 7
  ↑               ↑
 left           right
 
  Area = (8-0) × min(1,7) = 8 × 1 = 8
  maxArea = 8
  
  height[left]=1 < height[right]=7 → move left

═══════════════════════════════════════════════════════════

Step 2:
  left = 1 (height=8), right = 8 (height=7)
  
  1 8 6 2 5 4 8 3 7
    ↑             ↑
   left         right
   
  Area = (8-1) × min(8,7) = 7 × 7 = 49
  maxArea = max(8, 49) = 49
  
  height[left]=8 > height[right]=7 → move right

═══════════════════════════════════════════════════════════

Step 3:
  left = 1 (height=8), right = 7 (height=3)
  
  Area = (7-1) × min(8,3) = 6 × 3 = 18
  maxArea = max(49, 18) = 49
  
  height[left]=8 > height[right]=3 → move right

═══════════════════════════════════════════════════════════

[... continues until left >= right ...]

═══════════════════════════════════════════════════════════

Final: maxArea = 49 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public int maxArea(int[] height) {
    int left = 0;
    int right = height.length - 1;
    int maxArea = 0;
    
    while (left < right) {
        // Calculate current container area
        int width = right - left;
        int h = Math.min(height[left], height[right]);
        int area = width * h;
        
        // Update maximum
        maxArea = Math.max(maxArea, area);
        
        // Move the shorter wall (only way to potentially improve)
        if (height[left] < height[right]) {
            left++;
        } else {
            right-;
        }
    }
    
    return maxArea;
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Moving the taller wall | Can only decrease area | Always move shorter wall |
| Using height[left] + height[right] | Area uses MIN, not sum | Use `Math.min()` |
| Forgetting width in area | Area = width × height | Include `right - left` |
| Brute force O(n²) | Too slow for large inputs | Two pointers is O(n) |
| Moving both pointers | Skips potential solutions | Move only one per iteration |

--

## Mind-Map Anchor

```
CONTAINER WITH MOST WATER
    │
    ├── Trigger: "container" + "most water" + "two lines"
    │
    ├── Setup: left=0, right=n-1
    │
    ├── Formula: area = (right-left) × min(height[left], height[right])
    │
    ├── Key insight: Move SHORTER wall (only way to improve)
    │
    ├── Why: Moving taller wall can only decrease area
    │
    └── Complexity: O(n) time, O(1) space
```

--

# PATTERN 4: Trapping Rain Water (LeetCode 42)

## Pattern Recognition Signal

**When you see:** "trapping rain water", "elevation map", "how much water"

**Instant thought:** "Two pointers tracking left/right max heights!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  height = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
Output: 6

How much rainwater can be trapped between the bars?
```

### The Bathtub Analogy

```
Imagine each bar as a wall. Water fills up to the level of the shorter surrounding wall.

For any position i:
  Water at i = min(maxLeft, maxRight) - height[i]
  
  maxLeft = tallest bar to the left of i
  maxRight = tallest bar to the right of i
  
The water level is limited by the SHORTER of the two sides!

Visual:
       |
   |   ||  |
 | || ||||||| 
 0 1 0 2 1 0 1 3 2 1 2 1
     ~   ~ ~       Water trapped at these positions
```

### The Key Insight (Why Two Pointers Work)

```
Brute force: For each position, scan left and right to find maxLeft and maxRight.
             O(n²) time.

Better: Precompute maxLeft[] and maxRight[] arrays.
        O(n) time, O(n) space.

Best: Two pointers with running max!
      O(n) time, O(1) space.

The insight:
- If height[left] <= height[right], we KNOW the water at left is bounded by leftMax
  (because there's definitely a wall at least as tall as height[right] on the right)
- Similarly for the right side

So we process the SHORTER side, knowing its water level is determined.
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `height = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]`

```
Initial:
  left = 0, right = 11
  leftMax = 0, rightMax = 0
  water = 0
  
  [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
   ↑                             ↑
  left                         right

═══════════════════════════════════════════════════════════

Step 1: height[left]=0 <= height[right]=1
  Process left side:
  leftMax = max(0, 0) = 0
  water += 0 - 0 = 0
  left++
  
  left=1, water=0

═══════════════════════════════════════════════════════════

Step 2: height[left]=1 <= height[right]=1
  Process left side:
  leftMax = max(0, 1) = 1
  water += 1 - 1 = 0
  left++
  
  left=2, water=0

═══════════════════════════════════════════════════════════

Step 3: height[left]=0 <= height[right]=1
  Process left side:
  leftMax = max(1, 0) = 1
  water += 1 - 0 = 1  ← 1 unit trapped!
  left++
  
  left=3, water=1

═══════════════════════════════════════════════════════════

Step 4: height[left]=2 > height[right]=1
  Process right side:
  rightMax = max(0, 1) = 1
  water += 1 - 1 = 0
  right-
  
  right=10, water=1

═══════════════════════════════════════════════════════════

[... continues processing shorter side each time ...]

═══════════════════════════════════════════════════════════

Final: water = 6 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public int trap(int[] height) {
    if (height == null || height.length == 0) return 0;
    
    int left = 0;
    int right = height.length - 1;
    int leftMax = 0;   // Tallest bar seen from left
    int rightMax = 0;  // Tallest bar seen from right
    int water = 0;
    
    while (left < right) {
        if (height[left] <= height[right]) {
            // Left side is the bottleneck
            // Water level at left is determined by leftMax
            leftMax = Math.max(leftMax, height[left]);
            water += leftMax - height[left];  // Water trapped at this position
            left++;
        } else {
            // Right side is the bottleneck
            // Water level at right is determined by rightMax
            rightMax = Math.max(rightMax, height[right]);
            water += rightMax - height[right];
            right-;
        }
    }
    
    return water;
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Using max(leftMax, rightMax) | Water bounded by MIN, not max | Use the side being processed |
| Forgetting to update leftMax/rightMax | Misses taller bars | Update before calculating water |
| Processing wrong side | Incorrect water calculation | Process the SHORTER side |
| Negative water values | height[i] > max shouldn't happen | Update max first, then calculate |
| Off-by-one errors | Missing first/last positions | `left < right` handles correctly |

--

## Mind-Map Anchor

```
TRAPPING RAIN WATER
    │
    ├── Trigger: "trap water" + "elevation map"
    │
    ├── Setup: left=0, right=n-1, leftMax=0, rightMax=0
    │
    ├── Formula: water at i = min(leftMax, rightMax) - height[i]
    │
    ├── Key insight: Process SHORTER side (its water level is determined)
    │
    ├── Why: Shorter side is bounded by its max (taller side guarantees it)
    │
    └── Complexity: O(n) time, O(1) space
```

--

# PATTERN 5: Remove Duplicates from Sorted Array (LeetCode 26)

## Pattern Recognition Signal

**When you see:** "sorted array", "remove duplicates", "in-place", "return new length"

**Instant thought:** "Read-Write pointers — write only keeps unique elements!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  nums = [1, 1, 2, 2, 3]
Output: 3 (first 3 elements are [1, 2, 3])

Remove duplicates IN-PLACE. Return the count of unique elements.
The first k elements should contain the unique values.
```

### The Bouncer Analogy

```
Imagine a bouncer at a VIP section:
- READ pointer: Checks everyone in line
- WRITE pointer: Marks the next VIP spot

Rule: "Only let someone in if they're DIFFERENT from the last VIP"

  [1, 1, 2, 2, 3]
   ↑
  write/read (both start at 0)
  
  First person (1) always gets in. write stays at 0.
  
  read=1: Person is 1, same as last VIP (1) → REJECTED
  read=2: Person is 2, different from last VIP (1) → write++, let them in!
  read=3: Person is 2, same as last VIP (2) → REJECTED  
  read=4: Person is 3, different from last VIP (2) → write++, let them in!
  
  VIP section: [1, 2, 3, _, _], write=2
  Return write+1 = 3 unique elements
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]`

```
Initial:
  write = 0 (position of last unique element)
  
  [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
   ↑
  write

═══════════════════════════════════════════════════════════

read=1: nums[1]=0 == nums[write]=0
  Same as last unique → skip
  
  [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
   ↑  ↑
  write read

═══════════════════════════════════════════════════════════

read=2: nums[2]=1 != nums[write]=0
  Different! write++, nums[write] = nums[read]
  
  [0, 1, 1, 1, 1, 2, 2, 3, 3, 4]
      ↑  ↑
    write read

═══════════════════════════════════════════════════════════

read=3,4: nums[3]=1, nums[4]=1 == nums[write]=1
  Same → skip both

read=5: nums[5]=2 != nums[write]=1
  Different! write++, nums[write] = nums[read]
  
  [0, 1, 2, 1, 1, 2, 2, 3, 3, 4]
         ↑           ↑
       write       read

═══════════════════════════════════════════════════════════

[... continues ...]

Final: [0, 1, 2, 3, 4, _, _, _, _, _], write=4
Return write+1 = 5 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public int removeDuplicates(int[] nums) {
    if (nums.length == 0) return 0;
    
    int write = 0;  // Position of last unique element
    
    for (int read = 1; read < nums.length; read++) {
        // If current element is different from last unique
        if (nums[read] != nums[write]) {
            write++;                    // Move write pointer
            nums[write] = nums[read];   // Copy new unique element
        }
        // If same, just skip (read advances automatically)
    }
    
    return write + 1;  // Length = last index + 1
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Starting read at 0 | Compares first element with itself | Start read at 1 |
| Returning write | Off by one | Return write + 1 |
| Comparing with read-1 | Wrong comparison | Compare with nums[write] |
| Empty array | Index out of bounds | Check length == 0 first |
| Forgetting to copy | Just incrementing write | Must copy: nums[write] = nums[read] |

--

## Mind-Map Anchor

```
REMOVE DUPLICATES
    │
    ├── Trigger: "sorted" + "remove duplicates" + "in-place"
    │
    ├── Setup: write=0, read starts at 1
    │
    ├── Logic: if nums[read] != nums[write]:
    │              write++, copy element
    │
    ├── Return: write + 1 (length, not index)
    │
    └── Complexity: O(n) time, O(1) space
```

--

# PATTERN 6: Move Zeroes (LeetCode 283)

## Pattern Recognition Signal

**When you see:** "move zeros to end", "maintain relative order", "in-place"

**Instant thought:** "Read-Write pointers — write non-zeros, fill rest with zeros!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  nums = [0, 1, 0, 3, 12]
Output: [1, 3, 12, 0, 0]

Move all zeros to the end while keeping non-zeros in original order.
```

### The Grocery Checkout Analogy

```
Imagine a checkout conveyor belt:
- Non-zero items go to the front (bagging area)
- Zeros are "empty spaces" pushed to the back

  [0, 1, 0, 3, 12]
   ↑
  write (where next non-zero goes)
  
  read=0: 0 is zero → skip
  read=1: 1 is non-zero → put at write position, write++
  read=2: 0 is zero → skip
  read=3: 3 is non-zero → put at write position, write++
  read=4: 12 is non-zero → put at write position, write++
  
  After pass 1: [1, 3, 12, 3, 12], write=3
  Fill rest with zeros: [1, 3, 12, 0, 0]
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [0, 1, 0, 3, 12]`

```
Initial:
  write = 0
  
  [0, 1, 0, 3, 12]
   ↑
  write/read

═══════════════════════════════════════════════════════════

read=0: nums[0]=0 (zero)
  Skip it, don't move write
  
  [0, 1, 0, 3, 12]
   ↑  ↑
  write read

═══════════════════════════════════════════════════════════

read=1: nums[1]=1 (non-zero)
  nums[write] = 1, write++
  
  [1, 1, 0, 3, 12]
      ↑     ↑
    write  read

═══════════════════════════════════════════════════════════

read=2: nums[2]=0 (zero)
  Skip it

read=3: nums[3]=3 (non-zero)
  nums[write] = 3, write++
  
  [1, 3, 0, 3, 12]
         ↑     ↑
       write  read

═══════════════════════════════════════════════════════════

read=4: nums[4]=12 (non-zero)
  nums[write] = 12, write++
  
  [1, 3, 12, 3, 12]
             ↑
           write

═══════════════════════════════════════════════════════════

Fill remaining with zeros:
  [1, 3, 12, 0, 0]

Output: [1, 3, 12, 0, 0] ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public void moveZeroes(int[] nums) {
    int write = 0;  // Where to place next non-zero
    
    // Pass 1: Move all non-zeros to front
    for (int read = 0; read < nums.length; read++) {
        if (nums[read] != 0) {
            nums[write] = nums[read];
            write++;
        }
    }
    
    // Pass 2: Fill remaining positions with zeros
    while (write < nums.length) {
        nums[write] = 0;
        write++;
    }
}

// Alternative: Single pass with swap (fewer writes)
public void moveZeroesSwap(int[] nums) {
    int write = 0;
    
    for (int read = 0; read < nums.length; read++) {
        if (nums[read] != 0) {
            // Swap only if positions are different
            if (read != write) {
                int temp = nums[write];
                nums[write] = nums[read];
                nums[read] = temp;
            }
            write++;
        }
    }
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Forgetting to fill zeros | Array has garbage at end | Add second pass to fill zeros |
| Swapping when read==write | Unnecessary operation | Check if read != write before swap |
| Not maintaining order | Problem requires relative order | Read-write maintains order |
| Using extra array | Problem says in-place | Use two pointers |

--

## Mind-Map Anchor

```
MOVE ZEROES
    │
    ├── Trigger: "move zeros to end" + "maintain order"
    │
    ├── Setup: write=0, read=0
    │
    ├── Logic: Non-zero? Copy to write position, write++
    │          Zero? Skip
    │          Fill rest with zeros
    │
    ├── Alternative: Swap approach (single pass)
    │
    └── Complexity: O(n) time, O(1) space
```

--

# PATTERN 7: Remove Element (LeetCode 27)

## Pattern Recognition Signal

**When you see:** "remove all instances of value", "in-place", "return new length"

**Instant thought:** "Read-Write pointers — write only keeps non-target elements!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  nums = [3, 2, 2, 3], val = 3
Output: 2 (first 2 elements are [2, 2])

Remove all occurrences of val. Return count of remaining elements.
```

### The Filter Analogy

```
Imagine a water filter that removes impurities (val=3):

  [3, 2, 2, 3]  ← Dirty water
   ↑
  write
  
  read=0: 3 is impurity → filter out (skip)
  read=1: 2 is clean → keep it, write++
  read=2: 2 is clean → keep it, write++
  read=3: 3 is impurity → filter out (skip)
  
  [2, 2, _, _]  ← Clean water (first 2 elements)
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [0, 1, 2, 2, 3, 0, 4, 2]`, `val = 2`

```
Initial:
  write = 0
  
  [0, 1, 2, 2, 3, 0, 4, 2]
   ↑
  write/read

═══════════════════════════════════════════════════════════

read=0: nums[0]=0 != 2
  Keep it! nums[write]=0, write++
  
read=1: nums[1]=1 != 2
  Keep it! nums[write]=1, write++
  
  [0, 1, 2, 2, 3, 0, 4, 2]
         ↑  ↑
       write read

═══════════════════════════════════════════════════════════

read=2: nums[2]=2 == 2
  Remove it! (skip, don't move write)
  
read=3: nums[3]=2 == 2
  Remove it! (skip)

═══════════════════════════════════════════════════════════

read=4: nums[4]=3 != 2
  Keep it! nums[write]=3, write++
  
  [0, 1, 3, 2, 3, 0, 4, 2]
            ↑        ↑
          write    read

═══════════════════════════════════════════════════════════

read=5: nums[5]=0 != 2 → Keep, write++
read=6: nums[6]=4 != 2 → Keep, write++
read=7: nums[7]=2 == 2 → Skip

Final: [0, 1, 3, 0, 4, _, _, _], write=5
Return 5 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public int removeElement(int[] nums, int val) {
    int write = 0;  // Where to place next kept element
    
    for (int read = 0; read < nums.length; read++) {
        if (nums[read] != val) {
            // Keep this element
            nums[write] = nums[read];
            write++;
        }
        // If nums[read] == val, skip it (don't copy, don't increment write)
    }
    
    return write;  // Number of elements kept
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Returning write+1 | Unlike remove duplicates, write IS the count | Return write (not write+1) |
| Shifting elements | O(n²) time | Use read-write pointers |
| Using extra space | Problem says in-place | Two pointers use O(1) |

--

## Mind-Map Anchor

```
REMOVE ELEMENT
    │
    ├── Trigger: "remove value" + "in-place" + "return length"
    │
    ├── Setup: write=0, read=0
    │
    ├── Logic: if nums[read] != val:
    │              copy to write, write++
    │
    ├── Return: write (not write+1!)
    │
    └── Complexity: O(n) time, O(1) space
```

--

# PATTERN 8: Is Subsequence (LeetCode 392)

## Pattern Recognition Signal

**When you see:** "subsequence", "characters in order", "without changing order"

**Instant thought:** "Two pointers on two strings — match characters in order!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  s = "abc", t = "ahbgdc"
Output: true

Is s a subsequence of t? (Characters of s appear in t in the same order)
```

### The Treasure Hunt Analogy

```
Imagine s is a list of treasures to find, t is the path:

s = "abc" (find 'a', then 'b', then 'c')
t = "ahbgdc" (the path)

  s: [a, b, c]
      ↑
     sPtr (looking for 'a')
     
  t: [a, h, b, g, d, c]
      ↑
     tPtr
     
  t[0]='a' matches s[0]='a' → Found first treasure! sPtr++
  t[1]='h' doesn't match s[1]='b' → Keep walking, tPtr++
  t[2]='b' matches s[1]='b' → Found second treasure! sPtr++
  t[3]='g' doesn't match s[2]='c' → Keep walking
  t[4]='d' doesn't match → Keep walking
  t[5]='c' matches s[2]='c' → Found last treasure! sPtr++
  
  sPtr reached end of s → All treasures found! Return true.
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `s = "abc"`, `t = "ahbgdc"`

```
Initial:
  sPtr = 0, tPtr = 0
  
  s: "abc"    t: "ahbgdc"
      ↑           ↑
     sPtr        tPtr

═══════════════════════════════════════════════════════════

Step 1: s[0]='a' == t[0]='a'
  Match! sPtr++, tPtr++
  
  s: "abc"    t: "ahbgdc"
       ↑           ↑
      sPtr        tPtr

═══════════════════════════════════════════════════════════

Step 2: s[1]='b' != t[1]='h'
  No match. tPtr++ only
  
  s: "abc"    t: "ahbgdc"
       ↑            ↑
      sPtr         tPtr

═══════════════════════════════════════════════════════════

Step 3: s[1]='b' == t[2]='b'
  Match! sPtr++, tPtr++
  
  s: "abc"    t: "ahbgdc"
        ↑             ↑
       sPtr          tPtr

═══════════════════════════════════════════════════════════

Step 4-5: s[2]='c' != t[3]='g', t[4]='d'
  No match. tPtr++ only

Step 6: s[2]='c' == t[5]='c'
  Match! sPtr++
  
  sPtr = 3 = s.length() → All matched!

═══════════════════════════════════════════════════════════

Output: true ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public boolean isSubsequence(String s, String t) {
    int sPtr = 0;  // Pointer for subsequence s
    int tPtr = 0;  // Pointer for main string t
    
    while (sPtr < s.length() && tPtr < t.length()) {
        if (s.charAt(sPtr) == t.charAt(tPtr)) {
            // Found a match, move both pointers
            sPtr++;
        }
        // Always move t pointer (scan through t)
        tPtr++;
    }
    
    // If sPtr reached end, all characters of s were found in order
    return sPtr == s.length();
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Moving sPtr on every iteration | Should only move on match | Only sPtr++ when characters match |
| Checking tPtr == t.length() | Wrong condition | Check sPtr == s.length() |
| Using indexOf repeatedly | O(n*m) time | Two pointers is O(n+m) |
| Forgetting empty s | Empty string is subsequence of anything | Loop handles it (returns true) |

--

## Mind-Map Anchor

```
IS SUBSEQUENCE
    │
    ├── Trigger: "subsequence" + "in order"
    │
    ├── Setup: sPtr=0 (subsequence), tPtr=0 (main string)
    │
    ├── Logic: Match? Both pointers advance
    │          No match? Only tPtr advances
    │
    ├── Return: sPtr == s.length() (all chars found)
    │
    └── Complexity: O(n+m) time, O(1) space
```

--

# PATTERN 9: Sort Colors (LeetCode 75)

## Pattern Recognition Signal

**When you see:** "sort array with only 0, 1, 2", "Dutch National Flag", "one-pass", "in-place"

**Instant thought:** "Three pointers — low, mid, high!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  nums = [2, 0, 2, 1, 1, 0]
Output: [0, 0, 1, 1, 2, 2]

Sort an array containing only 0s, 1s, and 2s without using sort().
```

### The Dutch Flag Analogy

```
Imagine sorting balls into three sections of a Dutch flag:
- Red (0) goes LEFT
- White (1) stays MIDDLE  
- Blue (2) goes RIGHT

Three pointers maintain three regions:
  [0..low-1]    = All 0s (RED) — DONE
  [low..mid-1]  = All 1s (WHITE) — DONE
  [mid..high]   = UNKNOWN — TO PROCESS
  [high+1..n-1] = All 2s (BLUE) — DONE

  [2, 0, 2, 1, 1, 0]
   ↑              ↑
  low/mid        high
  
Process nums[mid]:
- If 0: swap with low, both low++ and mid++
- If 1: just mid++ (it's in the right place)
- If 2: swap with high, high- (DON'T move mid!)
```

### Why Not Move Mid When Swapping With High?

```
When we swap nums[mid] with nums[high]:
- We know nums[mid] (which was 2) is now at high position ✓
- But we DON'T know what we got from high!
- It could be 0, 1, or 2 — we need to check it!

So we DON'T increment mid — we process the swapped element next iteration.

When we swap nums[mid] with nums[low]:
- We know nums[low] was in the "processed" region
- It must have been a 1 (since 0s are before low)
- So we KNOW we got a 1 — safe to move mid++
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [2, 0, 2, 1, 1, 0]`

```
Initial:
  low = 0, mid = 0, high = 5
  
  [2, 0, 2, 1, 1, 0]
   ↑              ↑
  low/mid        high
  
  Regions: [0..-1]=0s, [0..-1]=1s, [0..5]=unknown, [6..5]=2s

═══════════════════════════════════════════════════════════

Step 1: nums[mid]=2
  Swap with high, high-
  
  [0, 0, 2, 1, 1, 2]
   ↑           ↑
  low/mid     high
  
  DON'T move mid (need to check swapped element)

═══════════════════════════════════════════════════════════

Step 2: nums[mid]=0
  Swap with low, low++, mid++
  
  [0, 0, 2, 1, 1, 2]
      ↑        ↑
    low/mid   high

═══════════════════════════════════════════════════════════

Step 3: nums[mid]=0
  Swap with low, low++, mid++
  
  [0, 0, 2, 1, 1, 2]
         ↑     ↑
       low/mid high

═══════════════════════════════════════════════════════════

Step 4: nums[mid]=2
  Swap with high, high-
  
  [0, 0, 1, 1, 2, 2]
         ↑  ↑
       low/mid high

═══════════════════════════════════════════════════════════

Step 5: nums[mid]=1
  Just mid++
  
  [0, 0, 1, 1, 2, 2]
         ↑   ↑
        low mid/high

═══════════════════════════════════════════════════════════

Step 6: nums[mid]=1
  Just mid++
  
  [0, 0, 1, 1, 2, 2]
         ↑      ↑
        low    mid (mid > high, STOP!)

═══════════════════════════════════════════════════════════

mid > high → Loop ends
Output: [0, 0, 1, 1, 2, 2] ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public void sortColors(int[] nums) {
    int low = 0;                    // Boundary: everything before is 0
    int mid = 0;                    // Current element being processed
    int high = nums.length - 1;     // Boundary: everything after is 2
    
    while (mid <= high) {
        if (nums[mid] == 0) {
            // Swap with low region, expand both boundaries
            swap(nums, low, mid);
            low++;
            mid++;
        } else if (nums[mid] == 1) {
            // 1 is already in correct position
            mid++;
        } else {  // nums[mid] == 2
            // Swap with high region, shrink high boundary
            swap(nums, mid, high);
            high-;
            // DON'T increment mid! Need to check swapped element
        }
    }
}

private void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| mid++ after swap with high | Don't know what we got | Only high-, keep mid same |
| `mid < high` condition | Misses last element | Use `mid <= high` |
| Not swapping, just assigning | Loses data | Must swap to preserve elements |
| Using counting sort | Works but not one-pass | Dutch flag is elegant one-pass |

--

## Mind-Map Anchor

```
SORT COLORS (Dutch National Flag)
    │
    ├── Trigger: "sort 0,1,2" + "one pass" + "in-place"
    │
    ├── Setup: low=0, mid=0, high=n-1
    │
    ├── Invariants:
    │   [0..low-1] = 0s
    │   [low..mid-1] = 1s
    │   [mid..high] = unknown
    │   [high+1..n-1] = 2s
    │
    ├── Logic: 0 → swap low, low++, mid++
    │          1 → mid++
    │          2 → swap high, high- (NO mid++)
    │
    └── Complexity: O(n) time, O(1) space
```

--

# PATTERN 10: 3Sum (LeetCode 15)

## Pattern Recognition Signal

**When you see:** "three numbers", "sum to zero/target", "unique triplets", "no duplicates"

**Instant thought:** "Sort + Fix one + Two pointers on rest!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  nums = [-1, 0, 1, 2, -1, -4]
Output: [[-1, -1, 2], [-1, 0, 1]]

Find all unique triplets that sum to zero.
```

### The "Fix One, Find Two" Strategy

```
3Sum = Fix one number + 2Sum on the rest!

Step 1: Sort the array
  [-4, -1, -1, 0, 1, 2]

Step 2: Fix nums[i], find two numbers in [i+1..n-1] that sum to -nums[i]

  Fix i=0 (nums[i]=-4):
    Need two numbers summing to 4 in [-1, -1, 0, 1, 2]
    Two pointers: left=1, right=5
    
  Fix i=1 (nums[i]=-1):
    Need two numbers summing to 1 in [-1, 0, 1, 2]
    Two pointers find: -1+2=1 ✓ and 0+1=1 ✓
    
  ... and so on

The key insight: Sorting enables two pointers AND duplicate skipping!
```

### Handling Duplicates

```
Why skip duplicates?
  Input: [-1, -1, 0, 1]
  
  If we don't skip:
    i=0: [-1, 0, 1] ✓
    i=1: [-1, 0, 1] ✓ (DUPLICATE!)
    
  With skip:
    i=0: [-1, 0, 1] ✓
    i=1: nums[1]=nums[0], SKIP!
    
Three places to skip duplicates:
1. Skip duplicate i (outer loop)
2. Skip duplicate left (after finding triplet)
3. Skip duplicate right (after finding triplet)
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [-1, 0, 1, 2, -1, -4]`

```
Step 1: Sort
  [-4, -1, -1, 0, 1, 2]

═══════════════════════════════════════════════════════════

i=0: nums[i]=-4, target=4
  
  [-4, -1, -1, 0, 1, 2]
    ↑   ↑            ↑
    i  left        right
    
  sum = -1 + 2 = 1 < 4 → left++
  sum = -1 + 2 = 1 < 4 → left++
  sum = 0 + 2 = 2 < 4 → left++
  sum = 1 + 2 = 3 < 4 → left++
  left >= right → done with i=0

═══════════════════════════════════════════════════════════

i=1: nums[i]=-1, target=1
  
  [-4, -1, -1, 0, 1, 2]
        ↑   ↑        ↑
        i  left    right
        
  sum = -1 + 2 = 1 == target!
  Found: [-1, -1, 2] ✓
  Skip duplicate left: left++
  Skip duplicate right: right-
  
  sum = 0 + 1 = 1 == target!
  Found: [-1, 0, 1] ✓
  left++, right-
  
  left >= right → done with i=1

═══════════════════════════════════════════════════════════

i=2: nums[i]=-1 == nums[i-1]=-1
  SKIP! (duplicate i)

═══════════════════════════════════════════════════════════

i=3: nums[i]=0, target=0
  
  sum = 1 + 2 = 3 > 0 → right-
  left >= right → done

═══════════════════════════════════════════════════════════

Output: [[-1, -1, 2], [-1, 0, 1]] ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);  // MUST sort for two pointers to work
    
    for (int i = 0; i < nums.length - 2; i++) {
        // Skip duplicate i values
        if (i > 0 && nums[i] == nums[i - 1]) continue;
        
        // Early termination: if smallest is positive, no solution
        if (nums[i] > 0) break;
        
        int left = i + 1;
        int right = nums.length - 1;
        int target = -nums[i];  // We need left + right = -nums[i]
        
        while (left < right) {
            int sum = nums[left] + nums[right];
            
            if (sum == target) {
                result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                
                // Skip duplicate left values
                while (left < right && nums[left] == nums[left + 1]) left++;
                // Skip duplicate right values
                while (left < right && nums[right] == nums[right - 1]) right-;
                
                left++;
                right-;
            } else if (sum < target) {
                left++;
            } else {
                right-;
            }
        }
    }
    
    return result;
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Forgetting to sort | Two pointers need sorted array | Always sort first |
| Not skipping duplicates | Returns duplicate triplets | Skip at all three levels |
| `i > 0` check missing | Index out of bounds | Check `i > 0` before comparing |
| Moving only one pointer after match | Misses other solutions | Move BOTH left++ and right- |
| Skip duplicates BEFORE adding | Might skip valid triplet | Skip AFTER adding to result |

--

## Mind-Map Anchor

```
3SUM
    │
    ├── Trigger: "three numbers" + "sum to target" + "unique"
    │
    ├── Strategy: Sort + Fix one + Two pointers
    │
    ├── Setup: i loops, left=i+1, right=n-1
    │
    ├── Duplicate handling:
    │   1. Skip duplicate i
    │   2. Skip duplicate left (after match)
    │   3. Skip duplicate right (after match)
    │
    ├── Optimization: if nums[i] > 0, break early
    │
    └── Complexity: O(n²) time, O(1) space (excluding output)
```

--

# PATTERN 11: 3Sum Closest (LeetCode 16)

## Pattern Recognition Signal

**When you see:** "three numbers", "closest to target", "return sum"

**Instant thought:** "Like 3Sum but track closest difference!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  nums = [-1, 2, 1, -4], target = 1
Output: 2 (because -1 + 2 + 1 = 2 is closest to 1)

Find triplet sum closest to target. Return the sum (not indices).
```

### The "Closest" Tracking

```
Instead of finding exact match, track the closest sum seen so far.

  closestSum = initial large value
  
  For each triplet sum:
    if |sum - target| < |closestSum - target|:
      closestSum = sum
      
  Return closestSum
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [-1, 2, 1, -4]`, `target = 1`

```
Step 1: Sort
  [-4, -1, 1, 2]

closestSum = Integer.MAX_VALUE (or first possible sum)

═══════════════════════════════════════════════════════════

i=0: nums[i]=-4
  
  [-4, -1, 1, 2]
    ↑   ↑     ↑
    i  left  right
    
  sum = -4 + (-1) + 2 = -3
  |−3 − 1| = 4, closestSum = -3
  
  sum < target → left++
  
  sum = -4 + 1 + 2 = -1
  |−1 − 1| = 2 < 4, closestSum = -1
  
  sum < target → left++
  left >= right → done

═══════════════════════════════════════════════════════════

i=1: nums[i]=-1
  
  sum = -1 + 1 + 2 = 2
  |2 − 1| = 1 < 2, closestSum = 2
  
  sum > target → right-
  left >= right → done

═══════════════════════════════════════════════════════════

Output: 2 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public int threeSumClosest(int[] nums, int target) {
    Arrays.sort(nums);
    int closestSum = nums[0] + nums[1] + nums[2];  // Initialize with first triplet
    
    for (int i = 0; i < nums.length - 2; i++) {
        // Optional: skip duplicates for efficiency
        if (i > 0 && nums[i] == nums[i - 1]) continue;
        
        int left = i + 1;
        int right = nums.length - 1;
        
        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];
            
            // Update closest if this sum is closer
            if (Math.abs(sum - target) < Math.abs(closestSum - target)) {
                closestSum = sum;
            }
            
            // Early exit if exact match
            if (sum == target) {
                return sum;
            } else if (sum < target) {
                left++;
            } else {
                right-;
            }
        }
    }
    
    return closestSum;
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Returning difference | Problem asks for sum | Return closestSum, not difference |
| Not handling exact match | Wastes time continuing | Return immediately if sum == target |
| Integer overflow | sum - target might overflow | Use long or be careful with comparisons |

--

## Mind-Map Anchor

```
3SUM CLOSEST
    │
    ├── Trigger: "three numbers" + "closest to target"
    │
    ├── Strategy: Like 3Sum but track closest
    │
    ├── Key difference: Track |sum - target|, update closestSum
    │
    ├── Optimization: Return immediately if exact match
    │
    └── Complexity: O(n²) time, O(1) space
```

--

# PATTERN 12: 4Sum (LeetCode 18)

## Pattern Recognition Signal

**When you see:** "four numbers", "sum to target", "unique quadruplets"

**Instant thought:** "Fix two + Two pointers = O(n³)!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  nums = [1, 0, -1, 0, -2, 2], target = 0
Output: [[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]

Find all unique quadruplets that sum to target.
```

### The "Fix Two, Find Two" Strategy

```
4Sum = Fix two numbers + 2Sum on the rest!

for i in range(n-3):           // Fix first number
  for j in range(i+1, n-2):    // Fix second number
    // Two pointers for remaining two numbers
    left = j + 1
    right = n - 1
    target_remaining = target - nums[i] - nums[j]
    // Standard two-pointer search
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [1, 0, -1, 0, -2, 2]`, `target = 0`

```
Step 1: Sort
  [-2, -1, 0, 0, 1, 2]

═══════════════════════════════════════════════════════════

i=0, j=1: nums[i]=-2, nums[j]=-1, need sum=3
  
  [-2, -1, 0, 0, 1, 2]
    ↑   ↑  ↑        ↑
    i   j left    right
    
  sum = 0 + 2 = 2 < 3 → left++
  sum = 0 + 2 = 2 < 3 → left++
  sum = 1 + 2 = 3 == 3!
  Found: [-2, -1, 1, 2] ✓

═══════════════════════════════════════════════════════════

i=0, j=2: nums[i]=-2, nums[j]=0, need sum=2
  
  sum = 0 + 2 = 2 == 2!
  Found: [-2, 0, 0, 2] ✓

═══════════════════════════════════════════════════════════

i=1, j=2: nums[i]=-1, nums[j]=0, need sum=1
  
  sum = 0 + 1 = 1 == 1!
  Found: [-1, 0, 0, 1] ✓

═══════════════════════════════════════════════════════════

Output: [[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]] ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public List<List<Integer>> fourSum(int[] nums, int target) {
    List<List<Integer>> result = new ArrayList<>();
    if (nums.length < 4) return result;
    
    Arrays.sort(nums);
    
    for (int i = 0; i < nums.length - 3; i++) {
        // Skip duplicate i
        if (i > 0 && nums[i] == nums[i - 1]) continue;
        
        for (int j = i + 1; j < nums.length - 2; j++) {
            // Skip duplicate j
            if (j > i + 1 && nums[j] == nums[j - 1]) continue;
            
            int left = j + 1;
            int right = nums.length - 1;
            // Use long to avoid overflow
            long targetSum = (long) target - nums[i] - nums[j];
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == targetSum) {
                    result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                    
                    // Skip duplicates
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right-;
                    
                    left++;
                    right-;
                } else if (sum < targetSum) {
                    left++;
                } else {
                    right-;
                }
            }
        }
    }
    
    return result;
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Integer overflow | Large numbers can overflow | Use long for target calculation |
| Wrong duplicate skip for j | `j > 0` instead of `j > i + 1` | Must be `j > i + 1` |
| O(n⁴) brute force | Too slow | Use two pointers for O(n³) |

--

## Mind-Map Anchor

```
4SUM
    │
    ├── Trigger: "four numbers" + "sum to target"
    │
    ├── Strategy: Fix two + Two pointers
    │
    ├── Duplicate handling: Skip at all four levels
    │
    ├── Overflow: Use long for target calculation
    │
    └── Complexity: O(n³) time, O(1) space
```

--

# PATTERN 13: Reverse String (LeetCode 344)

## Pattern Recognition Signal

**When you see:** "reverse", "in-place", "O(1) extra space"

**Instant thought:** "Opposite direction — swap from both ends!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  s = ['h', 'e', 'l', 'l', 'o']
Output: ['o', 'l', 'l', 'e', 'h']

Reverse the array in-place.
```

### The Mirror Swap Analogy

```
Imagine folding a paper in half — characters on opposite sides swap:

  ['h', 'e', 'l', 'l', 'o']
    ↑                   ↑
   left               right
   
  Swap 'h' and 'o':
  ['o', 'e', 'l', 'l', 'h']
        ↑           ↑
       left       right
       
  Swap 'e' and 'l':
  ['o', 'l', 'l', 'e', 'h']
            ↑   ↑
          left right
          
  left >= right → DONE!
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `s = ['h', 'e', 'l', 'l', 'o']`

```
Initial:
  left = 0, right = 4
  
  ['h', 'e', 'l', 'l', 'o']
    ↑                   ↑
   left               right

═══════════════════════════════════════════════════════════

Step 1: Swap s[0] and s[4]
  ['o', 'e', 'l', 'l', 'h']
        ↑           ↑
       left       right

═══════════════════════════════════════════════════════════

Step 2: Swap s[1] and s[3]
  ['o', 'l', 'l', 'e', 'h']
            ↑   ↑
          left right

═══════════════════════════════════════════════════════════

Step 3: left=2, right=2
  left >= right → STOP

═══════════════════════════════════════════════════════════

Output: ['o', 'l', 'l', 'e', 'h'] ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public void reverseString(char[] s) {
    int left = 0;
    int right = s.length - 1;
    
    while (left < right) {
        // Swap characters at left and right
        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;
        
        // Move pointers toward center
        left++;
        right-;
    }
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| `left <= right` | Swaps middle element with itself | Use `left < right` |
| Creating new array | Problem says in-place | Swap in original array |
| Using StringBuilder | O(n) space | Two pointers is O(1) |

--

## Mind-Map Anchor

```
REVERSE STRING
    │
    ├── Trigger: "reverse" + "in-place"
    │
    ├── Setup: left=0, right=n-1
    │
    ├── Logic: Swap s[left] and s[right], move both inward
    │
    ├── Termination: left >= right
    │
    └── Complexity: O(n) time, O(1) space
```

--

# PATTERN 14: Squares of Sorted Array (LeetCode 977)

## Pattern Recognition Signal

**When you see:** "sorted array with negatives", "squares", "sorted output"

**Instant thought:** "Opposite direction — largest squares at ends!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  nums = [-4, -1, 0, 3, 10]
Output: [0, 1, 9, 16, 100]

Return squares in sorted order.
```

### The "Largest at Ends" Insight

```
In a sorted array with negatives:
- Largest absolute values are at the ENDS
- Smallest absolute values are near the MIDDLE

  [-4, -1, 0, 3, 10]
    ↑            ↑
   left        right
   
  |-4| = 4, |10| = 10
  10 > 4, so 10² = 100 is the LARGEST square
  
  Fill result array from the END (largest to smallest):
  result = [_, _, _, _, 100]
  
  Move right pointer, compare again...
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [-4, -1, 0, 3, 10]`

```
Initial:
  left = 0, right = 4, pos = 4 (fill from end)
  result = [_, _, _, _, _]
  
  [-4, -1, 0, 3, 10]
    ↑            ↑
   left        right

═══════════════════════════════════════════════════════════

Step 1: |-4|=4 vs |10|=10
  10 > 4, so result[4] = 100, right-
  
  result = [_, _, _, _, 100]
  pos = 3

═══════════════════════════════════════════════════════════

Step 2: |-4|=4 vs |3|=3
  4 > 3, so result[3] = 16, left++
  
  result = [_, _, _, 16, 100]
  pos = 2

═══════════════════════════════════════════════════════════

Step 3: |-1|=1 vs |3|=3
  3 > 1, so result[2] = 9, right-
  
  result = [_, _, 9, 16, 100]
  pos = 1

═══════════════════════════════════════════════════════════

Step 4: |-1|=1 vs |0|=0
  1 > 0, so result[1] = 1, left++
  
  result = [_, 1, 9, 16, 100]
  pos = 0

═══════════════════════════════════════════════════════════

Step 5: left=2, right=2 (same element)
  result[0] = 0
  
  result = [0, 1, 9, 16, 100]

═══════════════════════════════════════════════════════════

Output: [0, 1, 9, 16, 100] ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public int[] sortedSquares(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    
    int left = 0;
    int right = n - 1;
    int pos = n - 1;  // Fill from the end (largest first)
    
    while (left <= right) {
        int leftSquare = nums[left] * nums[left];
        int rightSquare = nums[right] * nums[right];
        
        if (leftSquare > rightSquare) {
            result[pos] = leftSquare;
            left++;
        } else {
            result[pos] = rightSquare;
            right-;
        }
        pos-;
    }
    
    return result;
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Filling from start | Puts largest at beginning | Fill from end (pos = n-1) |
| `left < right` | Misses middle element | Use `left <= right` |
| Sorting after squaring | O(n log n) | Two pointers is O(n) |
| Forgetting negatives | Negative² can be large | Compare absolute values |

--

## Mind-Map Anchor

```
SQUARES OF SORTED ARRAY
    │
    ├── Trigger: "sorted with negatives" + "squares" + "sorted output"
    │
    ├── Key insight: Largest absolute values at ENDS
    │
    ├── Setup: left=0, right=n-1, pos=n-1
    │
    ├── Logic: Compare |left| vs |right|, put larger² at pos
    │
    └── Complexity: O(n) time, O(n) space (for result)
```

--

# PATTERN 15: Merge Sorted Array (LeetCode 88)

## Pattern Recognition Signal

**When you see:** "merge two sorted arrays", "in-place", "first array has extra space"

**Instant thought:** "Three pointers from the END — fill backwards!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  nums1 = [1, 2, 3, 0, 0, 0], m = 3
        nums2 = [2, 5, 6], n = 3
Output: nums1 = [1, 2, 2, 3, 5, 6]

Merge nums2 into nums1 in-place. nums1 has enough space at the end.
```

### The "Fill From End" Strategy

```
Why fill from the end?
- If we fill from the start, we'd overwrite nums1's elements!
- But the END of nums1 is empty (zeros) — safe to write there!

  nums1 = [1, 2, 3, 0, 0, 0]
                ↑        ↑
               p1       pos
               
  nums2 = [2, 5, 6]
                ↑
               p2
               
  Compare nums1[p1]=3 vs nums2[p2]=6
  6 > 3, so nums1[pos] = 6, p2-, pos-
  
  nums1 = [1, 2, 3, 0, 0, 6]
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums1 = [1, 2, 3, 0, 0, 0]`, `m = 3`, `nums2 = [2, 5, 6]`, `n = 3`

```
Initial:
  p1 = 2 (last element of nums1's data)
  p2 = 2 (last element of nums2)
  pos = 5 (last position in nums1)
  
  nums1 = [1, 2, 3, 0, 0, 0]
                ↑        ↑
               p1       pos
               
  nums2 = [2, 5, 6]
                ↑
               p2

═══════════════════════════════════════════════════════════

Step 1: nums1[2]=3 vs nums2[2]=6
  6 > 3, nums1[5] = 6, p2-, pos-
  
  nums1 = [1, 2, 3, 0, 0, 6]
                ↑     ↑
               p1    pos

═══════════════════════════════════════════════════════════

Step 2: nums1[2]=3 vs nums2[1]=5
  5 > 3, nums1[4] = 5, p2-, pos-
  
  nums1 = [1, 2, 3, 0, 5, 6]
                ↑  ↑
               p1 pos

═══════════════════════════════════════════════════════════

Step 3: nums1[2]=3 vs nums2[0]=2
  3 > 2, nums1[3] = 3, p1-, pos-
  
  nums1 = [1, 2, 3, 3, 5, 6]
             ↑  ↑
            p1 pos

═══════════════════════════════════════════════════════════

Step 4: nums1[1]=2 vs nums2[0]=2
  2 == 2, take from nums2 (or nums1, doesn't matter)
  nums1[2] = 2, p2-, pos-
  
  nums1 = [1, 2, 2, 3, 5, 6]
             ↑
            p1/pos
            
  p2 < 0, done with nums2!

═══════════════════════════════════════════════════════════

Remaining nums1 elements [1, 2] are already in place!

Output: [1, 2, 2, 3, 5, 6] ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int p1 = m - 1;      // Last element of nums1's data
    int p2 = n - 1;      // Last element of nums2
    int pos = m + n - 1; // Last position in nums1
    
    // Merge from the end
    while (p1 >= 0 && p2 >= 0) {
        if (nums1[p1] > nums2[p2]) {
            nums1[pos] = nums1[p1];
            p1-;
        } else {
            nums1[pos] = nums2[p2];
            p2-;
        }
        pos-;
    }
    
    // If nums2 has remaining elements, copy them
    // (If nums1 has remaining, they're already in place!)
    while (p2 >= 0) {
        nums1[pos] = nums2[p2];
        p2-;
        pos-;
    }
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Merging from start | Overwrites nums1's data | Merge from end |
| Forgetting remaining nums2 | Leaves elements uncopied | Add second while loop |
| Copying remaining nums1 | They're already in place | Only copy remaining nums2 |
| Using extra array | Problem says in-place | Use three pointers |

--

## Mind-Map Anchor

```
MERGE SORTED ARRAY
    │
    ├── Trigger: "merge sorted" + "in-place" + "extra space at end"
    │
    ├── Key insight: Fill from END to avoid overwriting
    │
    ├── Setup: p1=m-1, p2=n-1, pos=m+n-1
    │
    ├── Logic: Compare, put larger at pos, decrement
    │
    ├── Cleanup: Copy remaining nums2 (nums1 already in place)
    │
    └── Complexity: O(m+n) time, O(1) space
```

--

# PATTERN 16: Partition Labels (LeetCode 763)

## Pattern Recognition Signal

**When you see:** "partition string", "each letter appears in at most one part", "maximum parts"

**Instant thought:** "Track last occurrence + greedy expansion!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  s = "ababcbacadefegdehijhklij"
Output: [9, 7, 8]

Partition so each letter appears in at most one part.
Return the sizes of the parts.
```

### The "Greedy Expansion" Strategy

```
For each partition:
1. Start at current position
2. The partition MUST extend to include the last occurrence of s[start]
3. But wait! Characters in between might have later occurrences too!
4. Keep expanding until we've included all necessary characters

  "ababcbacadefegdehijhklij"
   ↑
  start
  
  'a' last appears at index 8
  So partition must go at least to index 8
  
  But 'b' (at index 1) last appears at index 5
  And 'c' (at index 4) last appears at index 7
  
  All characters in [0..8] have their last occurrence within [0..8]
  So first partition is [0..8], size = 9
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `s = "ababcbacadefegdehijhklij"`

```
Step 1: Precompute last occurrence of each character
  a: 8, b: 5, c: 7, d: 14, e: 15, f: 11, g: 13, h: 19, i: 22, j: 23, k: 20, l: 21

═══════════════════════════════════════════════════════════

Partition 1: start=0
  
  i=0: 'a', last[a]=8, end=max(0,8)=8
  i=1: 'b', last[b]=5, end=max(8,5)=8
  i=2: 'a', last[a]=8, end=max(8,8)=8
  i=3: 'b', last[b]=5, end=max(8,5)=8
  i=4: 'c', last[c]=7, end=max(8,7)=8
  i=5: 'b', last[b]=5, end=max(8,5)=8
  i=6: 'a', last[a]=8, end=max(8,8)=8
  i=7: 'c', last[c]=7, end=max(8,7)=8
  i=8: 'a', last[a]=8, end=max(8,8)=8
  
  i == end! Partition complete: size = 8-0+1 = 9

═══════════════════════════════════════════════════════════

Partition 2: start=9
  
  i=9: 'd', last[d]=14, end=14
  i=10: 'e', last[e]=15, end=15
  i=11: 'f', last[f]=11, end=15
  i=12: 'e', last[e]=15, end=15
  i=13: 'g', last[g]=13, end=15
  i=14: 'd', last[d]=14, end=15
  i=15: 'e', last[e]=15, end=15
  
  i == end! Partition complete: size = 15-9+1 = 7

═══════════════════════════════════════════════════════════

Partition 3: start=16
  
  i=16: 'h', last[h]=19, end=19
  i=17: 'i', last[i]=22, end=22
  i=18: 'j', last[j]=23, end=23
  i=19: 'h', last[h]=19, end=23
  i=20: 'k', last[k]=20, end=23
  i=21: 'l', last[l]=21, end=23
  i=22: 'i', last[i]=22, end=23
  i=23: 'j', last[j]=23, end=23
  
  i == end! Partition complete: size = 23-16+1 = 8

═══════════════════════════════════════════════════════════

Output: [9, 7, 8] ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public List<Integer> partitionLabels(String s) {
    // Step 1: Record last occurrence of each character
    int[] lastIndex = new int[26];
    for (int i = 0; i < s.length(); i++) {
        lastIndex[s.charAt(i) - 'a'] = i;
    }
    
    List<Integer> result = new ArrayList<>();
    int start = 0;  // Start of current partition
    int end = 0;    // End of current partition (expands as needed)
    
    // Step 2: Greedily expand partitions
    for (int i = 0; i < s.length(); i++) {
        // Expand end to include last occurrence of current char
        end = Math.max(end, lastIndex[s.charAt(i) - 'a']);
        
        // If we've reached the end of current partition
        if (i == end) {
            result.add(end - start + 1);  // Add partition size
            start = i + 1;                 // Start new partition
        }
    }
    
    return result;
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Not precomputing last index | O(n²) to find last occurrence each time | Precompute in O(n) |
| Forgetting to expand end | Partition might be too small | Always update end with max |
| Off-by-one in size | Size = end - start + 1 | Include both endpoints |

--

## Mind-Map Anchor

```
PARTITION LABELS
    │
    ├── Trigger: "partition" + "each letter in one part"
    │
    ├── Strategy: Precompute last occurrence + greedy expansion
    │
    ├── Setup: lastIndex[], start=0, end=0
    │
    ├── Logic: Expand end to include last occurrence of each char
    │          When i == end, partition complete
    │
    └── Complexity: O(n) time, O(1) space (26 letters)
```

--

# PATTERN 17: Longest Mountain in Array (LeetCode 845)

## Pattern Recognition Signal

**When you see:** "mountain", "strictly increasing then decreasing", "longest"

**Instant thought:** "Two pointers to find peak and expand!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  arr = [2, 1, 4, 7, 3, 2, 5]
Output: 5 (mountain is [1, 4, 7, 3, 2])

Find the longest mountain subarray.
Mountain: strictly increasing, then strictly decreasing, length >= 3.
```

### The "Climb and Descend" Strategy

```
For each potential peak:
1. Check if it's actually a peak (higher than both neighbors)
2. Expand LEFT while strictly increasing
3. Expand RIGHT while strictly decreasing
4. Mountain length = right - left + 1

  [2, 1, 4, 7, 3, 2, 5]
         ↑
        peak candidate (index 3, value 7)
        
  Expand left: 7 > 4 > 1 ✓ (stop at index 1)
  Expand right: 7 > 3 > 2 ✓ (stop at index 5)
  
  Mountain: indices [1..5], length = 5
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `arr = [2, 1, 4, 7, 3, 2, 5]`

```
Check each position as potential peak:

═══════════════════════════════════════════════════════════

i=0: arr[0]=2
  No left neighbor, can't be peak

i=1: arr[1]=1
  1 < 2 (left), not a peak

i=2: arr[2]=4
  4 > 1 (left) but 4 < 7 (right), not a peak

═══════════════════════════════════════════════════════════

i=3: arr[3]=7
  7 > 4 (left) and 7 > 3 (right), IS A PEAK!
  
  Expand left:
    left = 3
    arr[2]=4 < arr[3]=7 ✓, left = 2
    arr[1]=1 < arr[2]=4 ✓, left = 1
    arr[0]=2 > arr[1]=1 ✗, stop
    left = 1
    
  Expand right:
    right = 3
    arr[4]=3 < arr[3]=7 ✓, right = 4
    arr[5]=2 < arr[4]=3 ✓, right = 5
    arr[6]=5 > arr[5]=2 ✗, stop
    right = 5
    
  Mountain length = 5 - 1 + 1 = 5
  maxLength = 5

═══════════════════════════════════════════════════════════

i=4,5: Not peaks (descending)

i=6: arr[6]=5
  5 > 2 (left), no right neighbor, not a peak

═══════════════════════════════════════════════════════════

Output: 5 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
public int longestMountain(int[] arr) {
    int n = arr.length;
    int maxLength = 0;
    
    for (int i = 1; i < n - 1; i++) {
        // Check if arr[i] is a peak
        if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
            // Expand left (while strictly increasing toward peak)
            int left = i;
            while (left > 0 && arr[left - 1] < arr[left]) {
                left-;
            }
            
            // Expand right (while strictly decreasing from peak)
            int right = i;
            while (right < n - 1 && arr[right] > arr[right + 1]) {
                right++;
            }
            
            // Calculate mountain length
            int length = right - left + 1;
            maxLength = Math.max(maxLength, length);
        }
    }
    
    return maxLength;
}

// Alternative: One-pass solution
public int longestMountainOnePass(int[] arr) {
    int n = arr.length;
    int maxLength = 0;
    int i = 1;
    
    while (i < n - 1) {
        // Find peak
        if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
            int left = i - 1;
            int right = i + 1;
            
            // Expand left
            while (left > 0 && arr[left - 1] < arr[left]) {
                left-;
            }
            
            // Expand right
            while (right < n - 1 && arr[right] > arr[right + 1]) {
                right++;
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
            i = right;  // Skip to end of this mountain
        } else {
            i++;
        }
    }
    
    return maxLength;
}
```

--

## Common Traps

| Trap | Why It's Wrong | Fix |
|---|--------|---|
| Not checking strictly increasing/decreasing | Plateau is not a mountain | Use `<` and `>`, not `<=` and `>=` |
| Forgetting minimum length 3 | Single peak or two elements isn't mountain | Peak check ensures length >= 3 |
| Not skipping processed elements | Rechecks same mountain | Jump i to right after finding mountain |
| Checking boundaries | Index out of bounds | Start i at 1, end at n-2 |

--

## Mind-Map Anchor

```
LONGEST MOUNTAIN
    │
    ├── Trigger: "mountain" + "increasing then decreasing"
    │
    ├── Strategy: Find peak, expand both directions
    │
    ├── Peak condition: arr[i] > arr[i-1] AND arr[i] > arr[i+1]
    │
    ├── Expand: Left while increasing, Right while decreasing
    │
    ├── Optimization: Skip to end of mountain after finding
    │
    └── Complexity: O(n) time, O(1) space
```

--

# MAANG Coverage Map

## Pattern Frequency by Company

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        TWO POINTERS — MAANG FREQUENCY                        │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  Pattern                    │ Meta │ Amazon │ Apple │ Netflix │ Google │    │
│  ───────────────────────────┼──────┼────────┼───────┼─────────┼────────┤    │
│  Two Sum II                 │  ★★  │  ★★★   │  ★★   │   ★★    │  ★★★   │    │
│  Valid Palindrome           │ ★★★  │  ★★★   │  ★★   │   ★★    │  ★★    │    │
│  Valid Palindrome II        │ ★★★  │  ★★    │  ★    │   ★     │  ★★    │    │
│  Container With Most Water  │ ★★★  │  ★★★   │  ★★   │   ★★    │  ★★★   │    │
│  Trapping Rain Water        │ ★★★  │  ★★★   │  ★★★  │   ★★    │  ★★★   │    │
│  Remove Duplicates          │  ★★  │  ★★★   │  ★★   │   ★     │  ★★    │    │
│  Move Zeroes                │ ★★★  │  ★★★   │  ★★   │   ★★    │  ★★    │    │
│  Remove Element             │  ★   │  ★★    │  ★    │   ★     │  ★     │    │
│  Is Subsequence             │  ★★  │  ★★    │  ★    │   ★     │  ★★    │    │
│  Sort Colors                │ ★★★  │  ★★★   │  ★★   │   ★★    │  ★★★   │    │
│  3Sum                       │ ★★★  │  ★★★   │  ★★★  │   ★★    │  ★★★   │    │
│  3Sum Closest               │  ★★  │  ★★    │  ★★   │   ★     │  ★★    │    │
│  4Sum                       │  ★★  │  ★★    │  ★    │   ★     │  ★★    │    │
│  Reverse String             │  ★   │  ★★    │  ★    │   ★     │  ★     │    │
│  Squares of Sorted Array    │  ★★  │  ★★★   │  ★★   │   ★     │  ★★    │    │
│  Merge Sorted Array         │ ★★★  │  ★★★   │  ★★   │   ★★    │  ★★    │    │
│  Partition Labels           │  ★★  │  ★★★   │  ★    │   ★     │  ★★    │    │
│  Longest Mountain           │  ★   │  ★★    │  ★    │   ★     │  ★★    │    │
│                                                                              │
│  Legend: ★ = Occasional, ★★ = Common, ★★★ = Very Frequent                   │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Top 10 Must-Know for Interviews

| Rank | Pattern | Why It's Critical |
|---|-----|----------|
| 1 | **3Sum** | Tests sorting + two pointers + duplicate handling |
| 2 | **Trapping Rain Water** | Classic hard problem, tests deep understanding |
| 3 | **Container With Most Water** | Tests greedy + two pointer reasoning |
| 4 | **Valid Palindrome** | Foundation for string two-pointer problems |
| 5 | **Sort Colors** | Dutch National Flag — unique three-pointer technique |
| 6 | **Merge Sorted Array** | Tests in-place merging from the end |
| 7 | **Move Zeroes** | Read-write pointer foundation |
| 8 | **Remove Duplicates** | In-place array modification |
| 9 | **Two Sum II** | Classic opposite-direction template |
| 10 | **Squares of Sorted Array** | Tests understanding of sorted array properties |

--

# Pattern Recognition Cheat Sheet

## Quick Decision Guide

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                     TWO POINTERS PATTERN RECOGNITION                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  IF YOU SEE...                          │  USE THIS PATTERN                 │
│  ───────────────────────────────────────┼───────────────────────────────────│
│  "Sorted array" + "pair sum"            │  Opposite Direction (converge)    │
│  "Palindrome"                           │  Opposite Direction (compare)     │
│  "Container" / "water" / "area"         │  Opposite Direction (greedy)      │
│  "Remove duplicates" + "in-place"       │  Read-Write Pointers              │
│  "Move zeros" / "compact array"         │  Read-Write Pointers              │
│  "Sort 0s, 1s, 2s"                      │  Three Pointers (Dutch Flag)      │
│  "Three numbers sum to X"               │  Fix One + Two Pointers           │
│  "Four numbers sum to X"                │  Fix Two + Two Pointers           │
│  "Merge sorted arrays"                  │  Two Pointers (from end)          │
│  "Subsequence"                          │  Two Pointers (two strings)       │
│  "Reverse in-place"                     │  Opposite Direction (swap)        │
│  "Squares of sorted array"              │  Opposite Direction (fill end)    │
│  "Partition string/array"               │  Greedy + Expansion               │
│  "Mountain" / "peak"                    │  Find Peak + Expand Both Ways     │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Complexity Quick Reference

| Pattern Type | Time | Space | Key Insight |
|-------|---|----|-------|
| Opposite Direction | O(n) | O(1) | Each pointer moves at most n times |
| Read-Write | O(n) | O(1) | Single pass, write ≤ read |
| Three Pointers | O(n) | O(1) | Each element processed once |
| Fix One + 2-Ptr | O(n²) | O(1) | Outer O(n) × Inner O(n) |
| Fix Two + 2-Ptr | O(n³) | O(1) | Two outer O(n²) × Inner O(n) |

--

# Mastery Checklist

## Level 1: Foundation (Complete These First)

- [ ] **Two Sum II** — Can implement opposite-direction template from memory
- [ ] **Valid Palindrome** — Handle non-alphanumeric characters correctly
- [ ] **Remove Duplicates** — Understand read-write pointer invariant
- [ ] **Move Zeroes** — Can do both two-pass and single-pass (swap) versions
- [ ] **Reverse String** — Swap from both ends without extra space

## Level 2: Core Patterns (Interview Essentials)

- [ ] **Container With Most Water** — Explain WHY we move the shorter pointer
- [ ] **3Sum** — Handle all three levels of duplicate skipping correctly
- [ ] **Sort Colors** — Explain Dutch National Flag invariants
- [ ] **Merge Sorted Array** — Fill from end to avoid overwriting
- [ ] **Squares of Sorted Array** — Understand largest values at ends

## Level 3: Advanced (MAANG Level)

- [ ] **Trapping Rain Water** — Implement O(n) time, O(1) space solution
- [ ] **Valid Palindrome II** — Handle "delete at most one" with helper function
- [ ] **3Sum Closest** — Track closest difference, not exact match
- [ ] **4Sum** — Handle integer overflow with long
- [ ] **Partition Labels** — Precompute last occurrence + greedy expansion
- [ ] **Longest Mountain** — Find peak and expand both directions

## Level 4: Mastery Indicators

- [ ] Can identify two-pointer applicability in < 30 seconds
- [ ] Can explain time/space complexity for all patterns
- [ ] Can handle edge cases (empty, single element, all same) without thinking
- [ ] Can optimize from O(n²) brute force to O(n) two pointers
- [ ] Can combine two pointers with other techniques (binary search, sliding window)

--

# Quick Reference Card

```
╔═══════════════════════════════════════════════════════════════════════════════╗
║                         TWO POINTERS QUICK REFERENCE                           ║
╠═══════════════════════════════════════════════════════════════════════════════╣
║                                                                                ║
║  OPPOSITE DIRECTION (Converging)                                               ║
║  ────────────────────────────────                                              ║
║  int left = 0, right = n - 1;                                                  ║
║  while (left < right) {                                                        ║
║      if (condition) left++;                                                    ║
║      else right-;                                                             ║
║  }                                                                             ║
║                                                                                ║
║  READ-WRITE POINTERS                                                           ║
║  ───────────────────                                                           ║
║  int write = 0;                                                                ║
║  for (int read = 0; read < n; read++) {                                        ║
║      if (shouldKeep(arr[read])) {                                              ║
║          arr[write++] = arr[read];                                             ║
║      }                                                                         ║
║  }                                                                             ║
║  return write;  // new length                                                  ║
║                                                                                ║
║  THREE POINTERS (Dutch Flag)                                                   ║
║  ───────────────────────────                                                   ║
║  int low = 0, mid = 0, high = n - 1;                                           ║
║  while (mid <= high) {                                                         ║
║      if (arr[mid] == 0) swap(low++, mid++);                                    ║
║      else if (arr[mid] == 1) mid++;                                            ║
║      else swap(mid, high-);  // DON'T increment mid!                          ║
║  }                                                                             ║
║                                                                                ║
║  FIX ONE + TWO POINTERS (3Sum)                                                 ║
║  ─────────────────────────────                                                 ║
║  sort(arr);                                                                    ║
║  for (int i = 0; i < n - 2; i++) {                                             ║
║      if (i > 0 && arr[i] == arr[i-1]) continue;  // skip duplicate             ║
║      int left = i + 1, right = n - 1;                                          ║
║      while (left < right) { /* two-pointer logic */ }                          ║
║  }                                                                             ║
║                                                                                ║
║  KEY INSIGHTS                                                                  ║
║  ────────────                                                                  ║
║  • Sorted array → Two pointers possible (O(1) space vs HashMap O(n))           ║
║  • Sum too small → move left pointer right (increase sum)                      ║
║  • Sum too big → move right pointer left (decrease sum)                        ║
║  • Container/Water → move SHORTER side (only way to improve)                   ║
║  • Dutch Flag → DON'T move mid when swapping with high                         ║
║  • Merge from END when first array has extra space                             ║
║                                                                                ║
╚═══════════════════════════════════════════════════════════════════════════════╝
```

--

# Interview Tips

## What Interviewers Look For

1. **Pattern Recognition** — Can you identify two pointers is applicable?
2. **Pointer Movement Logic** — Can you explain WHY each pointer moves?
3. **Edge Case Handling** — Empty array, single element, all duplicates
4. **Optimization Awareness** — Know when two pointers beats HashMap
5. **Clean Code** — Proper variable names, clear loop conditions

## Common Follow-Up Questions

| Question | Good Answer |
|-----|-------|
| "Why two pointers instead of HashMap?" | "Sorted input allows O(1) space with two pointers vs O(n) for HashMap" |
| "Why move the shorter pointer in Container?" | "Moving taller can only decrease area; moving shorter might find taller" |
| "Why not increment mid when swapping with high?" | "We don't know what we got from high — need to check it" |
| "How do you handle duplicates in 3Sum?" | "Skip at three levels: outer i, inner left, inner right — all AFTER processing" |
| "What's the time complexity of 4Sum?" | "O(n³) — two nested loops O(n²) × two pointers O(n)" |

## Red Flags to Avoid

- ❌ Using HashMap when array is sorted (wastes space)
- ❌ Forgetting to sort before two pointers
- ❌ Wrong loop condition (`<=` vs `<`)
- ❌ Moving wrong pointer (increases instead of decreases)
- ❌ Not handling duplicates in kSum problems
- ❌ Incrementing mid when swapping with high in Dutch Flag

--

**End of Two Pointers Patterns Deep Dive**

**Next →** `../06_Sliding_Window/01_Sliding_Window.md`

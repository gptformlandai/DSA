# Sliding Window Patterns Deep Dive (MAANG L5 Coverage)

--

# INDEX — Quick Navigation (9 Patterns)

| # | Pattern | Quick Link |
|---|---------|------------|
| 0 | Maximum Sum Subarray of Size K | [Jump](#pattern-0-maximum-sum-subarray-of-size-k) |
| 2 | Sliding Window Maximum (LC 239) | [Jump](#pattern-2-sliding-window-maximum-lc-239) |
| 3 | Find All Anagrams in a String (LC 438) | [Jump](#pattern-3-find-all-anagrams-in-a-string-lc-438) |
| 6 | Longest Substring Without Repeating Characters (LC 3) | [Jump](#pattern-6-longest-substring-without-repeating-characters-lc-3) |
| 8 | Longest Repeating Character Replacement (LC 424) | [Jump](#pattern-8-longest-repeating-character-replacement-lc-424) |
| 10 | Minimum Size Subarray Sum (LC 209) | [Jump](#pattern-10-minimum-size-subarray-sum-lc-209) |
| 12 | Subarray Product Less Than K (LC 713) | [Jump](#pattern-12-subarray-product-less-than-k-lc-713) |
| 13 | Minimum Window Substring (LC 76) | [Jump](#pattern-13-minimum-window-substring-lc-76) |
| 16 | Subarrays with K Different Integers (LC 992) | [Jump](#pattern-16-subarrays-with-k-different-integers-lc-992) |

---
# The One Sentence That Unlocks All Sliding Window Problems

> **"Maintain a WINDOW of elements, EXPAND to explore, SHRINK to restore validity."**

--

# ZERO TO HERO: Understanding Sliding Window

## What IS a Sliding Window?

Imagine looking through a **train window** as it moves:
- You see a fixed portion of the landscape
- As the train moves, old scenery leaves, new scenery enters
- You're always looking at a "window" of the world

```
Array: [1, 3, 2, 6, -1, 4, 1, 8, 2]
        └─────────┘
         Window of size 4
         
Slide right:
        [1, 3, 2, 6, -1, 4, 1, 8, 2]
           └─────────┘
            New window
```

--

## The 2 Types of Sliding Window

### Type 1: FIXED SIZE Window
```
Window size is given (e.g., "subarray of size K")
Just slide the window, no shrinking needed
```

### Type 2: VARIABLE SIZE Window
```
Window size changes based on a condition
EXPAND to explore, SHRINK when invalid
```

--

## The Master Decision Tree

```
         "Subarray/Substring problem?"
                    │
                    ▼
         ┌─────────────────────┐
         │ Is window size fixed?│
         └─────────────────────┘
                /        \
              YES         NO
              /            \
    ┌────────────┐    ┌─────────────────┐
    │ FIXED      │    │ What to find?   │
    │ WINDOW     │    └─────────────────┘
    └────────────┘         /    |    \
                      LONGEST  SHORTEST  COUNT
                         │        │        │
                    ┌────┴───┐ ┌──┴──┐ ┌──┴──┐
                    │while   │ │while│ │count│
                    │invalid │ │valid│ │+=   │
                    │shrink  │ │shrink││r-l+1│
                    └────────┘ └─────┘ └─────┘
```

--

# CORE TEMPLATES

## Template 1: Fixed Size Window

```java
// Window of exactly size K
int left = 0;
for (int right = 0; right < n; right++) {
    // 1. ADD: Include arr[right] in window
    windowSum += arr[right];
    
    // 2. WINDOW NOT READY YET
    if (right < k - 1) continue;
    
    // 3. PROCESS: Window is exactly size K
    result = Math.max(result, windowSum);
    
    // 4. REMOVE: Slide out arr[left]
    windowSum -= arr[left];
    left++;
}
```

**Use when:** "subarray of size K", "every K elements"

--

## Template 2: Variable Window - Find LONGEST

```java
int left = 0;
for (int right = 0; right < n; right++) {
    // 1. EXPAND: Add arr[right] to window
    // Update state (sum, freq map, count, etc.)
    
    // 2. SHRINK: While window is INVALID
    while (windowIsInvalid()) {
        // Remove arr[left] from window
        left++;
    }
    
    // 3. UPDATE: Window [left, right] is VALID
    maxLen = Math.max(maxLen, right - left + 1);
}
```

**Use when:** "longest substring with...", "maximum length..."

--

## Template 3: Variable Window - Find SHORTEST

```java
int left = 0, minLen = Integer.MAX_VALUE;
for (int right = 0; right < n; right++) {
    // 1. EXPAND: Add arr[right]
    
    // 2. SHRINK: While window is VALID
    while (windowIsValid()) {
        minLen = Math.min(minLen, right - left + 1);
        // Remove arr[left]
        left++;
    }
}
```

**Use when:** "minimum length subarray...", "shortest substring..."

--

## Template 4: Variable Window - COUNT All Valid

```java
int left = 0, count = 0;
for (int right = 0; right < n; right++) {
    // 1. EXPAND: Add arr[right]
    
    // 2. SHRINK: While window is INVALID
    while (windowIsInvalid()) {
        // Remove arr[left]
        left++;
    }
    
    // 3. COUNT: All subarrays ending at right
    count += (right - left + 1);
}
```

**Use when:** "count subarrays with at most K..."

--

## The "Exactly K" Trick

```
exactlyK(k) = atMostK(k) - atMostK(k-1)
```

This is CRITICAL for problems like "exactly K distinct characters"!

--

# PATTERN 0: Maximum Sum Subarray of Size K

## Pattern Recognition Signal

**When you see:** "subarray of size K", "maximum sum"

**Instant thought:** "Fixed window! Slide and track max."

--

## The Mental Model

```
Window of size 3 sliding through array:

[2, 1, 5, 1, 3, 2]
 └──────┘ sum = 8

[2, 1, 5, 1, 3, 2]
    └──────┘ sum = 7

[2, 1, 5, 1, 3, 2]
       └──────┘ sum = 9  ← Maximum!
```

--

## Visual Dry Run

**Input:** `arr = [2, 1, 5, 1, 3, 2]`, `k = 3`

```
═══════════════════════════════════════════════════════════════

Step 0: right=0, arr[0]=2
  Window: [2]
  left=0, right=0
  windowSum = 2
  Window size = 1 < k=3, not ready yet

═══════════════════════════════════════════════════════════════

Step 1: right=1, arr[1]=1
  Window: [2, 1]
  left=0, right=1
  windowSum = 2 + 1 = 3
  Window size = 2 < k=3, not ready yet

═══════════════════════════════════════════════════════════════

Step 2: right=2, arr[2]=5
  Window: [2, 1, 5]
  left=0, right=2
  windowSum = 3 + 5 = 8
  Window size = 3 = k ✓ READY!
  maxSum = max(0, 8) = 8
  Remove arr[left]=2, windowSum = 8 - 2 = 6
  left++ → left=1

═══════════════════════════════════════════════════════════════

Step 3: right=3, arr[3]=1
  Window: [1, 5, 1]
  left=1, right=3
  windowSum = 6 + 1 = 7
  maxSum = max(8, 7) = 8
  Remove arr[left]=1, windowSum = 7 - 1 = 6
  left++ → left=2

═══════════════════════════════════════════════════════════════

Step 4: right=4, arr[4]=3
  Window: [5, 1, 3]
  left=2, right=4
  windowSum = 6 + 3 = 9
  maxSum = max(8, 9) = 9 ← NEW MAX!
  Remove arr[left]=5, windowSum = 9 - 5 = 4
  left++ → left=3

═══════════════════════════════════════════════════════════════

Step 5: right=5, arr[5]=2
  Window: [1, 3, 2]
  left=3, right=5
  windowSum = 4 + 2 = 6
  maxSum = max(9, 6) = 9
  Remove arr[left]=1, windowSum = 6 - 1 = 5
  left++ → left=4

═══════════════════════════════════════════════════════════════

Final: maxSum = 9 (window [5, 1, 3])
```

--

## The Code

```java
int maxSumSubarray(int[] arr, int k) {
    int windowSum = 0, maxSum = 0;
    int left = 0;
    
    for (int right = 0; right < arr.length; right++) {
        windowSum += arr[right];  // Add right element
        
        if (right >= k - 1) {     // Window is ready
            maxSum = Math.max(maxSum, windowSum);
            windowSum -= arr[left];  // Remove left element
            left++;
        }
    }
    return maxSum;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Starting max at 0 | Negative arrays fail | Use Integer.MIN_VALUE or first window |
| Off-by-one in window size | Window too big/small | right >= k-1 means window has k elements |

--

# PATTERN 6: Longest Substring Without Repeating Characters (LC 3)

## Pattern Recognition Signal

**When you see:** "longest substring", "without repeating", "all unique"

**Instant thought:** "Variable window! Expand until duplicate, shrink until valid."

--

## The Mental Model: "The Unique Guest List"

```
You're a bouncer at an exclusive party.
Rule: No two guests with the same name allowed inside.

As guests arrive (expand window):
- If name is NEW → let them in
- If name ALREADY INSIDE → kick out guests from front
  until the duplicate leaves

Track the maximum party size you ever achieved.
```

--

## Visual Dry Run

**Input:** `s = "abcabcbb"`

```
═══════════════════════════════════════════════════════════════

Step 0: right=0, char='a'
  Window: [a]
  Set: {a}
  maxLen = 1

═══════════════════════════════════════════════════════════════

Step 1: right=1, char='b'
  Window: [a, b]
  Set: {a, b}
  maxLen = 2

═══════════════════════════════════════════════════════════════

Step 2: right=2, char='c'
  Window: [a, b, c]
  Set: {a, b, c}
  maxLen = 3

═══════════════════════════════════════════════════════════════

Step 3: right=3, char='a'
  'a' IS in set! → SHRINK
  Remove 'a' at left=0, left++
  Window: [b, c, a]
  maxLen = 3

═══════════════════════════════════════════════════════════════

Final: maxLen = 3
```

--

## The Code

```java
int lengthOfLongestSubstring(String s) {
    Set<Character> window = new HashSet<>();
    int left = 0, maxLen = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        
        // SHRINK: While duplicate exists
        while (window.contains(c)) {
            window.remove(s.charAt(left));
            left++;
        }
        
        // EXPAND: Add current char
        window.add(c);
        
        // UPDATE: Track max
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using if instead of while | Multiple duplicates | Use while loop |
| Forgetting to add char after shrinking | Window state wrong | Add after while |

--

## Mind-Map Anchor

```
"LONGEST SUBSTRING ALL UNIQUE"
              │
              ▼
┌─────────────────────────────────────────┐
│ Variable Window - LONGEST               │
│ Invariant: No duplicates                │
│ Expand: Add char                        │
│ Shrink: While duplicate exists          │
│ Track: maxLen = right - left + 1        │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Expand until duplicate, shrink until unique."

--

# PATTERN 13: Minimum Window Substring (LC 76)

## Pattern Recognition Signal

**When you see:** "minimum window", "contains all characters"

**Instant thought:** "Variable window - SHORTEST! Shrink while valid."

--

## The Mental Model: "The Shopping List"

```
You're in a supermarket aisle (string s).
You have a shopping list (string t).
Find the SHORTEST section of aisle containing all items.

Strategy:
1. Walk right, picking up items (expand)
2. When you have ALL items → try to shrink from left
3. Track the shortest valid section
```

--

## Visual Dry Run

**Input:** `s = "ADOBECODEBANC"`, `t = "ABC"`

```
═══════════════════════════════════════════════════════════════

Setup:
  need = {A:1, B:1, C:1}
  required = 3 (distinct chars needed)
  formed = 0, left = 0

═══════════════════════════════════════════════════════════════

Step 0: right=0, char='A'
  Window: [A]DOBECODEBANC
  have = {A:1}
  'A' in need AND have[A]=1 == need[A]=1 → formed++
  formed = 1, required = 3
  formed ≠ required, continue expanding

═══════════════════════════════════════════════════════════════

Step 1-4: right=1,2,3,4 chars='D','O','B','E'
  Window: [ADOBE]CODEBANC
  have = {A:1, D:1, O:1, B:1, E:1}
  'B' matched → formed = 2
  formed ≠ required, continue expanding

═══════════════════════════════════════════════════════════════

Step 5: right=5, char='C'
  Window: [ADOBEC]ODEBANC
  have = {A:1, D:1, O:1, B:1, E:1, C:1}
  'C' matched → formed = 3
  formed == required ✓ VALID WINDOW!
  
  SHRINK LOOP:
    minLen = 6, minStart = 0, window = "ADOBEC"
    Remove 'A' at left=0: have[A]=0 < need[A]=1 → formed-
    formed = 2, left = 1
    formed ≠ required, stop shrinking

═══════════════════════════════════════════════════════════════

Step 6-9: right=6,7,8,9 chars='O','D','E','B'
  Window: D[OBECODEB]ANC
  Expanding, formed stays at 2 (missing A)

═══════════════════════════════════════════════════════════════

Step 10: right=10, char='A'
  Window: D[OBECODEBA]NC
  have = {A:1, ...}
  'A' matched → formed = 3
  formed == required ✓ VALID WINDOW!
  
  SHRINK LOOP:
    Current window size = 10, minLen = 6, no update
    Remove 'D' at left=1: 'D' not in need, formed unchanged
    left = 2
    
    Window size = 9, still > minLen = 6
    Remove 'O' at left=2: 'O' not in need
    left = 3
    
    ... continue shrinking ...
    
    Remove 'B' at left=5: have[B]=1 < need[B]=1 → formed-
    formed = 2, left = 6
    Stop shrinking

═══════════════════════════════════════════════════════════════

Step 11: right=11, char='N'
  Expanding, 'N' not in need

═══════════════════════════════════════════════════════════════

Step 12: right=12, char='C'
  Window: CODEBAN[C]
  Wait, let's trace more carefully...
  
  After step 10 shrinking: left=6, window starts at 'O'
  Window: [ODEBANC] (indices 6-12)
  have = {O:1, D:1, E:1, B:1, A:1, N:1, C:1}
  formed = 3 ✓
  
  SHRINK LOOP:
    Window size = 7, minLen = 6, no update
    Remove 'O': not in need, left = 7
    Window size = 6, minLen = 6, no update
    Remove 'D': not in need, left = 8
    Window size = 5 < minLen! Update: minLen = 5, minStart = 8
    Window = "EBANC"
    Remove 'E': not in need, left = 9
    Window size = 4 < minLen! Update: minLen = 4, minStart = 9
    Window = "BANC"
    Remove 'B': have[B]=0 < need[B]=1 → formed-
    formed = 2, left = 10
    Stop shrinking

═══════════════════════════════════════════════════════════════

Final: minLen = 4, minStart = 9
Result: s.substring(9, 13) = "BANC"
```

--

## The Code

```java
String minWindow(String s, String t) {
    Map<Character, Integer> need = new HashMap<>();
    Map<Character, Integer> have = new HashMap<>();
    
    for (char c : t.toCharArray()) 
        need.put(c, need.getOrDefault(c, 0) + 1);
    
    int required = need.size();
    int formed = 0;
    int left = 0;
    int minLen = Integer.MAX_VALUE;
    int minStart = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        have.put(c, have.getOrDefault(c, 0) + 1);
        
        if (need.containsKey(c) && 
            have.get(c).equals(need.get(c))) {
            formed++;
        }
        
        // SHRINK: While window is VALID
        while (formed == required) {
            // Update minimum
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                minStart = left;
            }
            
            // Remove left char
            char leftChar = s.charAt(left);
            have.put(leftChar, have.get(leftChar) - 1);
            if (need.containsKey(leftChar) && 
                have.get(leftChar) < need.get(leftChar)) {
                formed-;
            }
            left++;
        }
    }
    
    return minLen == Integer.MAX_VALUE ? "" : 
           s.substring(minStart, minStart + minLen);
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using == for Integer comparison | Reference comparison | Use .equals() |
| Shrinking with if instead of while | Miss shorter windows | Use while |
| Not handling "no solution" case | Return garbage | Check minLen == MAX_VALUE |

--

# PATTERN 16: Subarrays with K Different Integers (LC 992)

## Pattern Recognition Signal

**When you see:** "exactly K distinct"

**Instant thought:** "Use the trick: exactlyK = atMostK(k) - atMostK(k-1)"

--

## Visual Dry Run

**Input:** `nums = [1, 2, 1, 2, 3]`, `k = 2`

```
═══════════════════════════════════════════════════════════════

The Trick: exactlyK(2) = atMostK(2) - atMostK(1)

Let's trace atMostK(nums, 2):

═══════════════════════════════════════════════════════════════

Step 0: right=0, nums[0]=1
  Window: [1]
  left=0, right=0
  freq = {1:1}, distinct = 1 ≤ 2 ✓
  count += (0 - 0 + 1) = 1
  Subarrays: [1]

═══════════════════════════════════════════════════════════════

Step 1: right=1, nums[1]=2
  Window: [1, 2]
  left=0, right=1
  freq = {1:1, 2:1}, distinct = 2 ≤ 2 ✓
  count += (1 - 0 + 1) = 2
  Subarrays: [2], [1,2]
  Total count = 3

═══════════════════════════════════════════════════════════════

Step 2: right=2, nums[2]=1
  Window: [1, 2, 1]
  left=0, right=2
  freq = {1:2, 2:1}, distinct = 2 ≤ 2 ✓
  count += (2 - 0 + 1) = 3
  Subarrays: [1], [2,1], [1,2,1]
  Total count = 6

═══════════════════════════════════════════════════════════════

Step 3: right=3, nums[3]=2
  Window: [1, 2, 1, 2]
  left=0, right=3
  freq = {1:2, 2:2}, distinct = 2 ≤ 2 ✓
  count += (3 - 0 + 1) = 4
  Total count = 10

═══════════════════════════════════════════════════════════════

Step 4: right=4, nums[4]=3
  Window: [1, 2, 1, 2, 3]
  freq = {1:2, 2:2, 3:1}, distinct = 3 > 2 ✗
  
  SHRINK:
    Remove nums[0]=1: freq = {1:1, 2:2, 3:1}, distinct = 3 > 2
    left = 1
    Remove nums[1]=2: freq = {1:1, 2:1, 3:1}, distinct = 3 > 2
    left = 2
    Remove nums[2]=1: freq = {1:0→remove, 2:1, 3:1}, distinct = 2 ≤ 2 ✓
    left = 3
  
  Window: [2, 3]
  count += (4 - 3 + 1) = 2
  Total count = 12

═══════════════════════════════════════════════════════════════

atMostK(2) = 12

Now trace atMostK(nums, 1) similarly...
atMostK(1) = 5 (subarrays with at most 1 distinct)

═══════════════════════════════════════════════════════════════

Final: exactlyK(2) = 12 - 5 = 7

The 7 subarrays with exactly 2 distinct integers:
[1,2], [2,1], [1,2,1], [2,1,2], [1,2,1,2], [2,3], [1,2,1,2] wait...
Actually: [1,2], [2,1], [1,2,1], [2,1,2], [1,2,1,2], [2,3], [1,2,1,2]
Correct 7: [1,2], [2,1], [1,2,1], [2,1,2], [1,2,1,2], [2,3], [2,1,2,3]? 
Let me recount: indices (0,1), (1,2), (0,1,2), (1,2,3), (0,1,2,3), (2,3,4), (3,4)
= [1,2], [2,1], [1,2,1], [2,1,2], [1,2,1,2], [1,2,3]?, [2,3]
The answer is 7 ✓
```

--

## The Code

```java
int subarraysWithKDistinct(int[] nums, int k) {
    return atMostK(nums, k) - atMostK(nums, k - 1);
}

int atMostK(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    int left = 0, count = 0;
    
    for (int right = 0; right < nums.length; right++) {
        freq.put(nums[right], freq.getOrDefault(nums[right], 0) + 1);
        
        while (freq.size() > k) {
            freq.put(nums[left], freq.get(nums[left]) - 1);
            if (freq.get(nums[left]) == 0) freq.remove(nums[left]);
            left++;
        }
        
        count += right - left + 1;
    }
    return count;
}
```

--

# MAANG Coverage Map

| Pattern | Problem | Difficulty | Frequency |
|-----|-----|------|------|
| 0 | Max Sum Subarray K | Easy | ⭐⭐⭐ |
| 6 | Longest Without Repeating (LC 3) | Medium | ⭐⭐⭐⭐⭐ |
| 7 | Longest K Distinct (LC 340) | Medium | ⭐⭐⭐⭐ |
| 8 | Character Replacement (LC 424) | Medium | ⭐⭐⭐⭐ |
| 13 | Min Window Substring (LC 76) | Hard | ⭐⭐⭐⭐⭐ |
| 14 | Min Size Subarray Sum (LC 209) | Medium | ⭐⭐⭐⭐ |
| 16 | K Different Integers (LC 992) | Hard | ⭐⭐⭐⭐ |
| 17 | Find All Anagrams (LC 438) | Medium | ⭐⭐⭐⭐ |
| 18 | Sliding Window Maximum (LC 239) | Hard | ⭐⭐⭐⭐ |

--

# Mastery Checklist

## Tier 1: Must Know (Do these first)
- [ ] Longest Substring Without Repeating (LC 3)
- [ ] Minimum Window Substring (LC 76)
- [ ] Maximum Sum Subarray of Size K

## Tier 2: Interview Favorites
- [ ] Longest Repeating Character Replacement (LC 424)
- [ ] Find All Anagrams (LC 438)
- [ ] Minimum Size Subarray Sum (LC 209)

## Tier 3: Differentiators
- [ ] Sliding Window Maximum (LC 239)
- [ ] Subarrays with K Different Integers (LC 992)

--

# Quick Reference Card

```
┌─────────────────────────────────────────────────────────────┐
│                 SLIDING WINDOW CHEAT SHEET                  │
├─────────────────────────────────────────────────────────────┤
│ FIXED WINDOW:                                               │
│   for right in range(n):                                    │
│       add(right)                                            │
│       if right >= k-1: process(); remove(left); left++      │
├─────────────────────────────────────────────────────────────┤
│ VARIABLE - LONGEST:                                         │
│   while (invalid): shrink                                   │
│   maxLen = max(maxLen, right - left + 1)                    │
├─────────────────────────────────────────────────────────────┤
│ VARIABLE - SHORTEST:                                        │
│   while (valid): minLen = min(...); shrink                  │
├─────────────────────────────────────────────────────────────┤
│ VARIABLE - COUNT:                                           │
│   while (invalid): shrink                                   │
│   count += right - left + 1                                 │
├─────────────────────────────────────────────────────────────┤
│ EXACTLY K TRICK:                                            │
│   exactlyK(k) = atMostK(k) - atMostK(k-1)                   │
└─────────────────────────────────────────────────────────────┘
```

--

# ADDITIONAL DETAILED PATTERNS

--

# PATTERN 2: Sliding Window Maximum (LC 239)

## Pattern Recognition Signal

**When you see:** "maximum in each window", "sliding window", "k elements"

**Instant thought:** "Monotonic Deque! Keep decreasing order, front = max."

--

## The Mental Model: "The Bouncer Line"

```
Imagine a VIP line where only the TALLEST people matter.

Rule: If someone TALLER arrives, everyone shorter in front leaves.
      (They'll never be the tallest while the tall person is there)

The person at the FRONT of the line is always the current tallest.

When the window slides:
- Remove anyone who's now outside the window (check index)
- The front is still the maximum for current window
```

--

## Why Regular Max Doesn't Work

```
Naive approach: For each window, scan all K elements → O(n*k)

Problem: When window slides, we lose track of "second maximum"

Example: Window [5, 3, 4], max = 5
         Slide: 5 leaves, window = [3, 4, 2]
         What's the new max? We need to rescan!

Monotonic Deque: O(n) total - each element enters/exits once
```

--

## Visual Dry Run

**Input:** `nums = [1, 3, -1, -3, 5, 3, 6, 7]`, `k = 3`

```
═══════════════════════════════════════════════════════════════

Step 0: Process nums[0] = 1
  Deque: [0]  (store indices, not values)
  Window not ready yet (size < 3)

═══════════════════════════════════════════════════════════════

Step 1: Process nums[1] = 3
  3 > nums[deque.back()] = 1? YES → pop back
  Deque: [] → [1]
  Window not ready yet

═══════════════════════════════════════════════════════════════

Step 2: Process nums[2] = -1
  -1 > nums[deque.back()] = 3? NO → just add
  Deque: [1, 2]
  Window ready! Max = nums[deque.front()] = nums[1] = 3
  Result: [3]

═══════════════════════════════════════════════════════════════

Step 3: Process nums[3] = -3
  Remove front if outside window: 1 >= 3-3+1=1? YES, keep it
  -3 > -1? NO → just add
  Deque: [1, 2, 3]
  Max = nums[1] = 3
  Result: [3, 3]

═══════════════════════════════════════════════════════════════

Step 4: Process nums[4] = 5
  Remove front if outside: 1 >= 4-3+1=2? NO → remove index 1
  Deque: [2, 3]
  5 > -3? YES → pop. 5 > -1? YES → pop
  Deque: [] → [4]
  Max = nums[4] = 5
  Result: [3, 3, 5]

═══════════════════════════════════════════════════════════════

... continue ...

Final Result: [3, 3, 5, 5, 6, 7]
```

--

## The Code

```java
int[] maxSlidingWindow(int[] nums, int k) {
    if (nums == null || k == 0) return new int[0];
    
    int n = nums.length;
    int[] result = new int[n - k + 1];
    Deque<Integer> deque = new ArrayDeque<>();  // Store INDICES
    
    for (int i = 0; i < n; i++) {
        // 1. Remove indices outside current window
        while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
            deque.pollFirst();
        }
        
        // 2. Remove smaller elements (they'll never be max)
        while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
            deque.pollLast();
        }
        
        // 3. Add current index
        deque.offerLast(i);
        
        // 4. Record result when window is ready
        if (i >= k - 1) {
            result[i - k + 1] = nums[deque.peekFirst()];
        }
    }
    
    return result;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Storing values instead of indices | Can't check if element is outside window | Store indices |
| Using < instead of <= for removal | Keep duplicates incorrectly | Use < for max, <= for unique |
| Forgetting window size check | Access result before window ready | Check i >= k-1 |

--

## Mind-Map Anchor

```
"MAXIMUM IN EACH WINDOW"
           │
           ▼
┌─────────────────────────────────────────┐
│ Monotonic Deque (Decreasing)            │
│ 1. Remove outside window (front)        │
│ 2. Remove smaller elements (back)       │
│ 3. Add current index                    │
│ 4. Front = maximum                      │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Tall people kick out short people. Front is always tallest."

--

# PATTERN 3: Find All Anagrams in a String (LC 438)

## Pattern Recognition Signal

**When you see:** "anagram", "permutation", "same characters"

**Instant thought:** "Fixed window of size p.length! Compare frequency arrays."

--

## The Mental Model: "The Ingredient Checker"

```
You're a chef checking if a section of ingredients matches a recipe.

Recipe (p): "abc" → need {a:1, b:1, c:1}
Pantry (s): "cbaebabacd"

Slide a window of size 3 through pantry.
At each position, check: "Do I have EXACTLY the recipe ingredients?"

Window "cba" → {c:1, b:1, a:1} = recipe? YES! Index 0 is anagram.
Window "bae" → {b:1, a:1, e:1} ≠ recipe
... and so on
```

--

## Visual Dry Run

**Input:** `s = "cbaebabacd"`, `p = "abc"`

```
═══════════════════════════════════════════════════════════════

Setup:
  pCount = [1,1,1,0,0,...] (a=1, b=1, c=1)
  windowSize = 3
  sCount = [0,0,0,...]

═══════════════════════════════════════════════════════════════

Step 0: i=0, char='c'
  Window: [c]baebabacd
  sCount[c]++ → sCount = [0,1,1,0,...] (b=1,c=1? no, c is index 2)
  Actually: sCount['c'-'a'] = sCount[2]++ → [0,0,1,...]
  Window size = 1 < 3, not ready

═══════════════════════════════════════════════════════════════

Step 1: i=1, char='b'
  Window: [cb]aebabacd
  sCount['b'-'a'] = sCount[1]++ → [0,1,1,...]
  Window size = 2 < 3, not ready

═══════════════════════════════════════════════════════════════

Step 2: i=2, char='a'
  Window: [cba]ebabacd
  sCount['a'-'a'] = sCount[0]++ → [1,1,1,...]
  Window size = 3 = windowSize ✓
  
  Compare: sCount = [1,1,1,...] vs pCount = [1,1,1,...]
  Arrays.equals? YES! ✓
  result.add(i - windowSize + 1) = result.add(0)
  result = [0]

═══════════════════════════════════════════════════════════════

Step 3: i=3, char='e'
  Add 'e': sCount[4]++ → [1,1,1,0,1,...]
  Remove s[i-windowSize] = s[0] = 'c': sCount[2]- → [1,1,0,0,1,...]
  Window: c[bae]babacd
  
  Compare: sCount = [1,1,0,0,1,...] vs pCount = [1,1,1,...]
  Arrays.equals? NO (c count differs)

═══════════════════════════════════════════════════════════════

Step 4: i=4, char='b'
  Add 'b': sCount[1]++ → [1,2,0,0,1,...]
  Remove s[1] = 'b': sCount[1]- → [1,1,0,0,1,...]
  Window: cb[aeb]abacd
  
  Compare: NO (missing c, has e)

═══════════════════════════════════════════════════════════════

Step 5: i=5, char='a'
  Add 'a': sCount[0]++ → [2,1,0,0,1,...]
  Remove s[2] = 'a': sCount[0]- → [1,1,0,0,1,...]
  Window: cba[eba]bacd
  
  Compare: NO

═══════════════════════════════════════════════════════════════

Step 6: i=6, char='b'
  Add 'b': sCount[1]++ → [1,2,0,0,1,...]
  Remove s[3] = 'e': sCount[4]- → [1,2,0,0,0,...]
  Window: cbae[bab]acd
  
  Compare: NO (b count is 2)

═══════════════════════════════════════════════════════════════

Step 7: i=7, char='a'
  Add 'a': sCount[0]++ → [2,2,0,...]
  Remove s[4] = 'b': sCount[1]- → [2,1,0,...]
  Window: cbaeb[aba]cd
  
  Compare: NO

═══════════════════════════════════════════════════════════════

Step 8: i=8, char='c'
  Add 'c': sCount[2]++ → [2,1,1,...]
  Remove s[5] = 'a': sCount[0]- → [1,1,1,...]
  Window: cbaeba[bac]d
  
  Compare: sCount = [1,1,1,...] vs pCount = [1,1,1,...]
  Arrays.equals? YES! ✓
  result.add(8 - 3 + 1) = result.add(6)
  result = [0, 6]

═══════════════════════════════════════════════════════════════

Step 9: i=9, char='d'
  Add 'd': sCount[3]++ → [1,1,1,1,...]
  Remove s[6] = 'b': sCount[1]- → [1,0,1,1,...]
  Window: cbaebab[acd]
  
  Compare: NO

═══════════════════════════════════════════════════════════════

Final: result = [0, 6]
Anagrams found at indices 0 ("cba") and 6 ("bac")
```

--

## The Code

```java
List<Integer> findAnagrams(String s, String p) {
    List<Integer> result = new ArrayList<>();
    if (s.length() < p.length()) return result;
    
    int[] pCount = new int[26];
    int[] sCount = new int[26];
    
    // Count characters in p
    for (char c : p.toCharArray()) {
        pCount[c - 'a']++;
    }
    
    int windowSize = p.length();
    
    for (int i = 0; i < s.length(); i++) {
        // Add right character
        sCount[s.charAt(i) - 'a']++;
        
        // Remove left character when window exceeds size
        if (i >= windowSize) {
            sCount[s.charAt(i - windowSize) - 'a']-;
        }
        
        // Check if window matches (when window is ready)
        if (i >= windowSize - 1 && Arrays.equals(sCount, pCount)) {
            result.add(i - windowSize + 1);
        }
    }
    
    return result;
}
```

--

## Optimized Version (O(1) comparison)

```java
List<Integer> findAnagrams(String s, String p) {
    List<Integer> result = new ArrayList<>();
    if (s.length() < p.length()) return result;
    
    int[] count = new int[26];
    for (char c : p.toCharArray()) count[c - 'a']++;
    
    int required = p.length();  // Characters still needed
    int left = 0;
    
    for (int right = 0; right < s.length(); right++) {
        // Add right character
        if (count[s.charAt(right) - 'a']- > 0) {
            required-;  // Found a needed character
        }
        
        // Shrink if window too big
        if (right - left + 1 > p.length()) {
            if (count[s.charAt(left) - 'a']++ >= 0) {
                required++;  // Lost a needed character
            }
            left++;
        }
        
        // Check if all characters matched
        if (required == 0) {
            result.add(left);
        }
    }
    
    return result;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using HashMap (slower) | Array comparison is O(26) | Use int[26] array |
| Off-by-one in window start | Wrong index in result | Use i - windowSize + 1 |
| Comparing arrays with == | Reference comparison | Use Arrays.equals() |

--

# PATTERN 8: Longest Repeating Character Replacement (LC 424)

## Pattern Recognition Signal

**When you see:** "longest substring", "replace at most k characters", "same character"

**Instant thought:** "Variable window! Track maxFreq, shrink when replacements > k."

--

## The Mental Model: "The Painting Budget"

```
You have a wall with colored sections: "AABABBA"
You can repaint at most K=1 sections.
Find the longest stretch you can make ALL ONE COLOR.

Key insight:
- In any window, keep the MOST FREQUENT color
- Repaint everything else
- If (window size - maxFreq) > k → too many repaints needed → shrink

Window "AABA": maxFreq=3 (A), size=4, repaints=4-3=1 ≤ k ✓
Window "AABAB": maxFreq=3, size=5, repaints=5-3=2 > k=1 ✗ → shrink
```

--

## The Key Insight (Why We Don't Decrease maxFreq)

```
When we shrink the window, we DON'T decrease maxFreq.

Why? Because we're looking for the LONGEST valid window.
If maxFreq was 3 before, we already found a window that worked.
A smaller maxFreq would only give us a smaller or equal window.

So we keep maxFreq as a "high water mark" - only increase, never decrease.
This is the KEY optimization that makes this O(n)!
```

--

## Visual Dry Run

**Input:** `s = "AABABBA"`, `k = 1`

```
═══════════════════════════════════════════════════════════════

right=0, char='A'
  freq = {A:1}
  maxFreq = 1
  windowSize = 1, replacements = 1-1 = 0 ≤ 1 ✓
  maxLen = 1

═══════════════════════════════════════════════════════════════

right=1, char='A'
  freq = {A:2}
  maxFreq = 2
  windowSize = 2, replacements = 2-2 = 0 ≤ 1 ✓
  maxLen = 2

═══════════════════════════════════════════════════════════════

right=2, char='B'
  freq = {A:2, B:1}
  maxFreq = 2
  windowSize = 3, replacements = 3-2 = 1 ≤ 1 ✓
  maxLen = 3

═══════════════════════════════════════════════════════════════

right=3, char='A'
  freq = {A:3, B:1}
  maxFreq = 3
  windowSize = 4, replacements = 4-3 = 1 ≤ 1 ✓
  maxLen = 4

═══════════════════════════════════════════════════════════════

right=4, char='B'
  freq = {A:3, B:2}
  maxFreq = 3
  windowSize = 5, replacements = 5-3 = 2 > 1 ✗
  
  SHRINK: Remove s[left]='A', left++
  freq = {A:2, B:2}
  windowSize = 4, replacements = 4-3 = 1 ≤ 1 ✓
  maxLen = 4 (unchanged)

═══════════════════════════════════════════════════════════════

... continue ...

Final maxLen = 4
```

--

## The Code

```java
int characterReplacement(String s, int k) {
    int[] freq = new int[26];
    int left = 0;
    int maxFreq = 0;  // Max frequency of any single char in window
    int maxLen = 0;
    
    for (int right = 0; right < s.length(); right++) {
        // Add right character
        freq[s.charAt(right) - 'A']++;
        maxFreq = Math.max(maxFreq, freq[s.charAt(right) - 'A']);
        
        // Window size - maxFreq = characters to replace
        // If > k, we need to shrink
        int windowSize = right - left + 1;
        if (windowSize - maxFreq > k) {
            freq[s.charAt(left) - 'A']-;
            left++;
            // Note: We DON'T decrease maxFreq here!
        }
        
        maxLen = Math.max(maxLen, right - left + 1);
    }
    
    return maxLen;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Decreasing maxFreq when shrinking | Unnecessary, slows down | Keep maxFreq as high water mark |
| Using while instead of if | Over-shrinks the window | Use if (shrink by 1 is enough) |
| Using 'a' instead of 'A' | Wrong index for uppercase | Check if input is upper/lower |

--

## Mind-Map Anchor

```
"LONGEST WITH K REPLACEMENTS"
           │
           ▼
┌─────────────────────────────────────────┐
│ Variable Window                         │
│ Track: maxFreq (most common char)       │
│ Invalid: windowSize - maxFreq > k       │
│ Key: Don't decrease maxFreq!            │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Keep the majority, replace the rest. maxFreq only goes up."

--

# PATTERN 10: Minimum Size Subarray Sum (LC 209)

## Pattern Recognition Signal

**When you see:** "minimum length", "sum >= target", "positive integers"

**Instant thought:** "Variable window - SHORTEST! Shrink while valid."

--

## The Mental Model

```
Find the SHORTEST subarray with sum >= target.

This is the OPPOSITE of "longest" problems:
- Longest: shrink while INVALID, then record
- Shortest: shrink while VALID, recording each time

Why? Because we want the SMALLEST valid window.
```

--

## Visual Dry Run

**Input:** `target = 7`, `nums = [2, 3, 1, 2, 4, 3]`

```
═══════════════════════════════════════════════════════════════

Step 0: right=0, nums[0]=2
  Window: [2]
  left=0, right=0
  sum = 2
  sum < 7, not valid, continue expanding

═══════════════════════════════════════════════════════════════

Step 1: right=1, nums[1]=3
  Window: [2, 3]
  left=0, right=1
  sum = 2 + 3 = 5
  sum < 7, not valid, continue expanding

═══════════════════════════════════════════════════════════════

Step 2: right=2, nums[2]=1
  Window: [2, 3, 1]
  left=0, right=2
  sum = 5 + 1 = 6
  sum < 7, not valid, continue expanding

═══════════════════════════════════════════════════════════════

Step 3: right=3, nums[3]=2
  Window: [2, 3, 1, 2]
  left=0, right=3
  sum = 6 + 2 = 8
  sum >= 7 ✓ VALID!
  
  SHRINK LOOP (while valid):
    minLen = min(MAX, 4) = 4
    Remove nums[0]=2: sum = 8 - 2 = 6
    left = 1
    sum < 7, stop shrinking

═══════════════════════════════════════════════════════════════

Step 4: right=4, nums[4]=4
  Window: [3, 1, 2, 4]
  left=1, right=4
  sum = 6 + 4 = 10
  sum >= 7 ✓ VALID!
  
  SHRINK LOOP:
    minLen = min(4, 4) = 4
    Remove nums[1]=3: sum = 10 - 3 = 7
    left = 2
    sum >= 7 ✓ still valid!
    
    minLen = min(4, 3) = 3
    Remove nums[2]=1: sum = 7 - 1 = 6
    left = 3
    sum < 7, stop shrinking

═══════════════════════════════════════════════════════════════

Step 5: right=5, nums[5]=3
  Window: [2, 4, 3]
  left=3, right=5
  sum = 6 + 3 = 9
  sum >= 7 ✓ VALID!
  
  SHRINK LOOP:
    minLen = min(3, 3) = 3
    Remove nums[3]=2: sum = 9 - 2 = 7
    left = 4
    sum >= 7 ✓ still valid!
    
    minLen = min(3, 2) = 2 ← NEW MIN!
    Remove nums[4]=4: sum = 7 - 4 = 3
    left = 5
    sum < 7, stop shrinking

═══════════════════════════════════════════════════════════════

Final: minLen = 2
Shortest subarray: [4, 3] with sum = 7
```

--

## The Code

```java
int minSubArrayLen(int target, int[] nums) {
    int left = 0;
    int sum = 0;
    int minLen = Integer.MAX_VALUE;
    
    for (int right = 0; right < nums.length; right++) {
        sum += nums[right];  // Expand
        
        // Shrink while VALID (sum >= target)
        while (sum >= target) {
            minLen = Math.min(minLen, right - left + 1);
            sum -= nums[left];
            left++;
        }
    }
    
    return minLen == Integer.MAX_VALUE ? 0 : minLen;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Shrinking while invalid | That's for LONGEST problems | Shrink while VALID |
| Returning minLen directly | Might be MAX_VALUE if no solution | Check and return 0 |
| Using if instead of while | Miss shorter valid windows | Use while to keep shrinking |

--

# PATTERN 12: Subarray Product Less Than K (LC 713)

## Pattern Recognition Signal

**When you see:** "product < k", "count subarrays"

**Instant thought:** "Variable window - COUNT! Each valid window adds (right-left+1) subarrays."

--

## The Key Insight: Counting Subarrays

```
When window [left, right] is valid:
- How many NEW subarrays end at 'right'?
- Answer: (right - left + 1)

Why? Subarrays ending at right:
  [left...right], [left+1...right], ..., [right]
  That's (right - left + 1) subarrays!

Example: Window [2, 3, 4] (indices 1-3)
  Subarrays ending at index 3: [2,3,4], [3,4], [4]
  Count = 3 - 1 + 1 = 3 ✓
```

--

## Visual Dry Run

**Input:** `nums = [10, 5, 2, 6]`, `k = 100`

```
═══════════════════════════════════════════════════════════════

Step 0: right=0, nums[0]=10
  Window: [10]
  left=0, right=0
  product = 10
  product < 100 ✓ valid
  count += (0 - 0 + 1) = 1
  Subarrays: [10]

═══════════════════════════════════════════════════════════════

Step 1: right=1, nums[1]=5
  Window: [10, 5]
  left=0, right=1
  product = 10 * 5 = 50
  product < 100 ✓ valid
  count += (1 - 0 + 1) = 2
  Subarrays: [5], [10,5]
  Total count = 3

═══════════════════════════════════════════════════════════════

Step 2: right=2, nums[2]=2
  Window: [10, 5, 2]
  left=0, right=2
  product = 50 * 2 = 100
  product >= 100 ✗ invalid!
  
  SHRINK:
    product /= nums[0] = 10 → product = 10
    left = 1
    product < 100 ✓ valid now
  
  Window: [5, 2]
  count += (2 - 1 + 1) = 2
  Subarrays: [2], [5,2]
  Total count = 5

═══════════════════════════════════════════════════════════════

Step 3: right=3, nums[3]=6
  Window: [5, 2, 6]
  left=1, right=3
  product = 10 * 6 = 60
  product < 100 ✓ valid
  count += (3 - 1 + 1) = 3
  Subarrays: [6], [2,6], [5,2,6]
  Total count = 8

═══════════════════════════════════════════════════════════════

Final: count = 8

All 8 subarrays with product < 100:
[10], [5], [10,5], [2], [5,2], [6], [2,6], [5,2,6]
```

--

## The Code

```java
int numSubarrayProductLessThanK(int[] nums, int k) {
    if (k <= 1) return 0;  // Product can't be < 1 with positive nums
    
    int left = 0;
    int product = 1;
    int count = 0;
    
    for (int right = 0; right < nums.length; right++) {
        product *= nums[right];  // Expand
        
        // Shrink while invalid (product >= k)
        while (product >= k) {
            product /= nums[left];
            left++;
        }
        
        // Count all valid subarrays ending at right
        count += right - left + 1;
    }
    
    return count;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Not handling k <= 1 | Division issues, wrong count | Return 0 early |
| Counting wrong | Miss the formula | count += right - left + 1 |
| Integer overflow | Product can get huge | Use long if needed |

--

*End of Sliding Window Patterns Deep Dive*

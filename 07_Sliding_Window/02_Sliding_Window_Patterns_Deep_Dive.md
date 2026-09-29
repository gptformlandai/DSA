# Sliding Window Patterns Deep Dive (MAANG L5 Coverage)

---

# INDEX

| Category | Patterns |
|----------|----------|
| [Core Concepts](#core-concepts) | Templates, Decision Tree |
| [Fixed Window](#fixed-window-patterns) | Patterns 0-5 |
| [Variable - Longest](#variable-window-longest) | Patterns 6-12 |
| [Variable - Shortest](#variable-window-shortest) | Patterns 13-15 |
| [Variable - Count](#variable-window-count) | Patterns 16-20 |

---

# The One Sentence That Unlocks All Sliding Window Problems

> **"Maintain a WINDOW of elements, EXPAND to explore, SHRINK to restore validity."**

---

# 🌟 ZERO TO HERO: Understanding Sliding Window

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

---

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

---

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

---

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

---

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

---

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

---

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

---

## The "Exactly K" Trick 🔥

```
exactlyK(k) = atMostK(k) - atMostK(k-1)
```

This is CRITICAL for problems like "exactly K distinct characters"!

---

# PATTERN 0: Maximum Sum Subarray of Size K

## Pattern Recognition Signal

**When you see:** "subarray of size K", "maximum sum"

**Instant thought:** "Fixed window! Slide and track max."

---

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

---

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

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Starting max at 0 | Negative arrays fail | Use Integer.MIN_VALUE or first window |
| Off-by-one in window size | Window too big/small | right >= k-1 means window has k elements |

---

# PATTERN 6: Longest Substring Without Repeating Characters (LC 3) ⭐⭐

## Pattern Recognition Signal

**When you see:** "longest substring", "without repeating", "all unique"

**Instant thought:** "Variable window! Expand until duplicate, shrink until valid."

---

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

---

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

---

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

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Using if instead of while | Multiple duplicates | Use while loop |
| Forgetting to add char after shrinking | Window state wrong | Add after while |

---

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

---

# PATTERN 13: Minimum Window Substring (LC 76) ⭐⭐⭐

## Pattern Recognition Signal

**When you see:** "minimum window", "contains all characters"

**Instant thought:** "Variable window - SHORTEST! Shrink while valid."

---

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

---

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
                formed--;
            }
            left++;
        }
    }
    
    return minLen == Integer.MAX_VALUE ? "" : 
           s.substring(minStart, minStart + minLen);
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Using == for Integer comparison | Reference comparison | Use .equals() |
| Shrinking with if instead of while | Miss shorter windows | Use while |
| Not handling "no solution" case | Return garbage | Check minLen == MAX_VALUE |

---

# PATTERN 16: Subarrays with K Different Integers (LC 992) ⭐⭐

## Pattern Recognition Signal

**When you see:** "exactly K distinct"

**Instant thought:** "Use the trick: exactlyK = atMostK(k) - atMostK(k-1)"

---

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

---

# MAANG Coverage Map

| Pattern | Problem | Difficulty | Frequency |
|---------|---------|------------|-----------|
| 0 | Max Sum Subarray K | Easy | ⭐⭐⭐ |
| 6 | Longest Without Repeating (LC 3) | Medium | ⭐⭐⭐⭐⭐ |
| 7 | Longest K Distinct (LC 340) | Medium | ⭐⭐⭐⭐ |
| 8 | Character Replacement (LC 424) | Medium | ⭐⭐⭐⭐ |
| 13 | Min Window Substring (LC 76) | Hard | ⭐⭐⭐⭐⭐ |
| 14 | Min Size Subarray Sum (LC 209) | Medium | ⭐⭐⭐⭐ |
| 16 | K Different Integers (LC 992) | Hard | ⭐⭐⭐⭐ |
| 17 | Find All Anagrams (LC 438) | Medium | ⭐⭐⭐⭐ |
| 18 | Sliding Window Maximum (LC 239) | Hard | ⭐⭐⭐⭐ |

---

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

---

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

---

*End of Sliding Window Patterns Deep Dive*

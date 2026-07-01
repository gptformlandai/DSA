# Section 6 — Sliding Window Pattern

---

## 1. What Problem Does This Solve?

Problems asking for the **optimal contiguous subarray or substring** — longest, shortest, maximum sum, or satisfying a specific condition. Brute force checks every possible subarray: O(n²). Sliding Window maintains a **window** that intelligently expands and shrinks, processing each element at most twice: O(n).

---

## 2. Beginner-Friendly Intuition

Think of a **train looking through a window** at a passing landscape. The window has a fixed or adjustable width. As the train moves, the window slides right — you gain a new scene on the right and lose an old scene on the left. You never have to re-examine what you've already passed.

The key insight: instead of recomputing the entire subarray from scratch for each position, you **update the window incrementally**.

---

## 3. Real-World Analogy

**Stock price monitoring:** You're watching a 7-day rolling average of stock prices. Each new day, you add it to the sum and subtract the oldest day — O(1) update instead of O(7) recomputation. As the window slides forward, the sum stays current.

**Spam filter sliding window:** Keep a count of suspicious words in the last 100 emails. Each new email, add new word counts, subtract counts from the oldest email. Slide forward.

---

## 4. Core Concept

The pattern uses two pointers: `left` (window start) and `right` (window end).

- `right` always moves forward (expands the window)
- `left` only moves forward when the window violates a condition (shrinks the window)

**Two flavors:**

| Type | Window Size | When Left Moves |
|------|------------|----------------|
| **Fixed** | Constant k | Every step (maintain size k) |
| **Variable** | Grows/shrinks | When window is "invalid" |

**The invariant:** After processing `right`, the window `[left, right]` is always valid.

---

## 5. Pattern Recognition Signals

Use Sliding Window when:
```
"Longest subarray/substring where..."
"Shortest subarray/substring that..."
"Maximum/minimum sum of subarray of size k"
"Count subarrays with exactly/at most k..."
"Find smallest window containing all characters of..."
"Longest without repeating..."
"Subarray with given average..."
```

**NOT Sliding Window when:**
- Array has negative numbers and you need exact sum → use Prefix Sum + HashMap
- Subarray doesn't need to be contiguous → use DP or Greedy

---

## 6. Step-by-Step Algorithm

### Fixed-Size Window Template
```
Step 1: Build first window of size k (process elements 0..k-1)
Step 2: Record initial window answer
Step 3: For i from k to n-1:
    a. Add element at right (index i)
    b. Remove element at left (index i-k)
    c. Update answer
```

### Variable-Size Window Template
```
Step 1: Set left = 0, initialize window state (count, map, sum, etc.)
Step 2: For right from 0 to n-1:
    a. ADD arr[right] to window state
    b. WHILE window is INVALID:
          Remove arr[left] from window state
          left++
    c. At this point [left..right] is valid
    d. UPDATE answer (e.g., max length = right - left + 1)
```

---

## 7. Dry Run with Example

### Example 1: Max Sum Subarray of Size k=3

**Input:** `arr = [2, 1, 5, 1, 3, 2]`, `k = 3`

```
Initial window [0..2]: sum = 2+1+5 = 8, maxSum = 8

Slide to [1..3]: +arr[3]=1, -arr[0]=2 → sum = 8+1-2 = 7, maxSum = 8
Slide to [2..4]: +arr[4]=3, -arr[1]=1 → sum = 7+3-1 = 9, maxSum = 9 ← NEW MAX
Slide to [3..5]: +arr[5]=2, -arr[2]=5 → sum = 9+2-5 = 6, maxSum = 9

Answer: 9 (window [2,3,4] = [5,1,3])
```

### Example 2: Longest Substring Without Repeating Characters

**Input:** `s = "abcabcbb"`

```
left=0, window={}, maxLen=0

right=0('a'): 'a' not in window → add, window={'a'}, len=1, maxLen=1
right=1('b'): 'b' not in window → add, window={'a','b'}, len=2, maxLen=2
right=2('c'): 'c' not in window → add, window={'a','b','c'}, len=3, maxLen=3
right=3('a'): 'a' IN window → INVALID
  SHRINK: remove 'a'(left=0), left=1, window={'b','c'}
  Now 'a' not in window → add, window={'b','c','a'}, len=3, maxLen=3
right=4('b'): 'b' IN window → INVALID
  SHRINK: remove 'b'(left=1), left=2, window={'c','a'}
  Now 'b' not in window → add, window={'c','a','b'}, len=3, maxLen=3
right=5('c'): 'c' IN window → INVALID
  SHRINK: remove 'c'(left=2), left=3, window={'a','b'}
  Now 'c' not in window → add, window={'a','b','c'}, len=3, maxLen=3
right=6('b'): 'b' IN window → INVALID
  SHRINK: remove 'a'(left=3), left=4, window={'b','c'}
  'b' still in window → SHRINK: remove 'b'(left=4), left=5, window={'c'}
  Now 'b' not in window → add, window={'c','b'}, len=2, maxLen=3
right=7('b'): 'b' IN window...shrinks to len=1

Answer: 3 ("abc" or "bca" or "cab")
```

---

## 8. Code Implementation

### Fixed Size: Maximum Sum Subarray

```java
int maxSumSubarray(int[] arr, int k) {
    if (arr.length < k) return -1;
    int windowSum = 0;
    for (int i = 0; i < k; i++) windowSum += arr[i]; // build first window
    int maxSum = windowSum;
    for (int i = k; i < arr.length; i++) {
        windowSum += arr[i] - arr[i - k]; // add new, remove old
        maxSum = Math.max(maxSum, windowSum);
    }
    return maxSum;
}
```

### Variable Size: Longest Substring Without Repeating

```java
int lengthOfLongestSubstring(String s) {
    Set<Character> window = new HashSet<>();
    int left = 0, maxLen = 0;
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        while (window.contains(c))           // window invalid
            window.remove(s.charAt(left++)); // shrink from left
        window.add(c);                       // expand right
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

### Variable Size: Minimum Window Substring

```java
String minWindow(String s, String t) {
    if (s.isEmpty() || t.isEmpty()) return "";
    Map<Character, Integer> need = new HashMap<>();
    for (char c : t.toCharArray()) need.merge(c, 1, Integer::sum);

    Map<Character, Integer> have = new HashMap<>();
    int formed = 0, required = need.size();
    int left = 0, minLen = Integer.MAX_VALUE, minLeft = 0;

    for (int right = 0; right < s.length(); right++) {
        // EXPAND
        char c = s.charAt(right);
        have.merge(c, 1, Integer::sum);
        if (need.containsKey(c) && have.get(c).equals(need.get(c))) formed++;

        // SHRINK while window is valid
        while (formed == required) {
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                minLeft = left;
            }
            char leftChar = s.charAt(left);
            have.merge(leftChar, -1, Integer::sum);
            if (need.containsKey(leftChar) && have.get(leftChar) < need.get(leftChar))
                formed--;
            left++;
        }
    }
    return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
}
```

### "At Most K" Pattern — Foundation for "Exactly K"

```java
// Count subarrays with at most K distinct integers
int atMostK(int[] arr, int k) {
    Map<Integer, Integer> count = new HashMap<>();
    int left = 0, result = 0;
    for (int right = 0; right < arr.length; right++) {
        count.merge(arr[right], 1, Integer::sum);
        while (count.size() > k) {
            count.merge(arr[left], -1, Integer::sum);
            if (count.get(arr[left]) == 0) count.remove(arr[left]);
            left++;
        }
        result += right - left + 1; // all subarrays ending at right are valid
    }
    return result;
}

// Exactly K distinct = atMostK(k) - atMostK(k-1)
int exactlyK(int[] arr, int k) {
    return atMostK(arr, k) - atMostK(arr, k - 1);
}
```

### Max Consecutive Ones III (Flip at most K zeros)

```java
int longestOnes(int[] nums, int k) {
    int left = 0, zeros = 0, maxLen = 0;
    for (int right = 0; right < nums.length; right++) {
        if (nums[right] == 0) zeros++;
        while (zeros > k) {           // too many zeros — invalid window
            if (nums[left] == 0) zeros--;
            left++;
        }
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

---

## 9. Time Complexity

| Problem | Brute Force | Sliding Window | Why O(n) |
|---------|------------|---------------|---------|
| Max sum of size k | O(n×k) | O(n) | Each element added/removed once |
| Longest non-repeating | O(n²) | O(n) | Each character enters/leaves window once |
| Minimum window substring | O(n²×m) | O(n+m) | Two pointers traverse s once; t processed once |
| At most K distinct | O(n²) | O(n) | Each element enters/leaves window once |

**Key insight:** Every element is added to the window exactly once (when `right` passes it) and removed at most once (when `left` passes it). So total work = 2n = O(n).

---

## 10. Space Complexity

| Implementation | Space | Reason |
|---------------|-------|--------|
| Sum-based (no extra storage) | O(1) | Just running sum |
| HashSet window | O(k) | k = distinct chars in window |
| HashMap window | O(k) | k = distinct keys in window |
| Character frequency | O(1) | Fixed alphabet size (26 or 128) |

> If the problem involves only lowercase letters, prefer `int[26]` over `HashMap<Character,Integer>` for O(1) space and better performance.

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| `k > array length` | Return entire array or -1 |
| Empty string | Return 0 or "" immediately |
| All same characters | Window expands fully before shrinking |
| Window never becomes valid | Return "" or 0 |
| Negative numbers with sum | Cannot use variable window; use prefix sum |
| String with spaces | `Character.isLetterOrDigit()` check |
| `t` longer than `s` | Impossible — return "" immediately |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Not updating window state before checking validity
for (int right = 0; right < n; right++) {
    while (isInvalid(window)) { /* shrink */ }
    add(arr[right], window); // WRONG: add after check → right element not considered
    // FIX: add first, then check validity
    add(arr[right], window);
    while (isInvalid(window)) { /* shrink */ }
}

// MISTAKE 2: Using a non-sliding approach for "exactly k" problems
// "Exactly k" often doesn't yield a clean window condition
// FIX: Use atMostK(k) - atMostK(k-1) decomposition

// MISTAKE 3: Using sliding window when array has negatives and need exact sum
// A negative number can make previously-invalid window valid again → left can't move forward safely
// FIX: Use Prefix Sum + HashMap for negative values

// MISTAKE 4: Forgetting to reduce count when removing from window map
// If count goes to 0, remove the key (or leaving 0-count keys breaks size checks)
if (count.get(key) == 0) count.remove(key);

// MISTAKE 5: Off-by-one in window size
right - left + 1  // window size (both inclusive)
// NOT: right - left
```

---

## 13. Interview-Level Explanation

**Q: "Explain why Sliding Window is O(n)."**

> "Even though we have a while loop inside the for loop, the key insight is that each element is processed at most twice — once when `right` includes it (expansion), and once when `left` passes it (shrinkage). So despite the nested structure, the total number of operations is bounded by 2n. This amortized analysis gives us O(n) overall."

**Q: "How would you find the longest subarray with sum ≤ k if elements are positive?"**

> "I'd use a variable-size sliding window. Since all elements are positive, adding an element always increases the sum and removing always decreases it — the window has the monotonic property needed for sliding window to work. If elements could be negative, this breaks down and I'd need prefix sum with a HashMap."

---

## 14. Real-World Use Cases

| Application | Sliding Window Usage |
|------------|---------------------|
| **Network throughput** | Sliding window protocol in TCP: track unacknowledged packets |
| **Stock analysis** | Rolling average/min/max over past k days |
| **Spam detection** | Count suspicious words in last n emails |
| **Log monitoring** | Alert if error rate in last 5 minutes exceeds threshold |
| **Genomics** | Find gene motifs (substrings) in DNA sequences |
| **Image processing** | Box blur: average of k×k pixel window |
| **Rate limiting** | API gateway: count requests in sliding time window |

---

## 15. Variations of This Pattern

| Variation | Key Difference | Example Problem |
|-----------|---------------|----------------|
| Fixed window | Window size = constant k | Max sum subarray size k |
| Variable expand/shrink | Window size adjustable | Longest non-repeating |
| "Exactly k" via atMostK | Decompose into two calls | Subarrays with k distinct |
| Window with frequency map | Track char/num counts | Minimum window substring |
| Window max/min (deque) | Need max/min inside window | Sliding window maximum |
| Shrink from left only | Window only grows or left moves right | Longest valid window |
| Circular array window | Indices wrap around | Max sum circular subarray |

---

## 16. Practice Problems

### Easy — Core Template Practice
1. **Maximum Average Subarray I** (LeetCode #643)
   - *Task:* Find contiguous subarray of length k with maximum average.
   - *Hint:* Fixed window — maintain running sum.

2. **Find All Anagrams in a String** (LeetCode #438)
   - *Task:* Find all starting indices where anagram of p exists in s.
   - *Hint:* Fixed window of size p.length(). Use int[26] frequency comparison.

3. **Contains Duplicate II** (LeetCode #219)
   - *Task:* Check if same value appears twice within k indices.
   - *Hint:* Sliding window of size k with a HashSet.

### Medium — Variable Window
1. **Longest Substring Without Repeating Characters** (LeetCode #3)
   - *Task:* Longest substring with all unique characters.
   - *Hint:* Variable window + HashSet or HashMap.

2. **Longest Repeating Character Replacement** (LeetCode #424)
   - *Task:* Longest substring with at most k replacements to make all same.
   - *Hint:* Track max frequency char. Window valid if size - maxFreq ≤ k.

3. **Permutation in String** (LeetCode #567)
   - *Task:* Does s2 contain a permutation of s1?
   - *Hint:* Fixed window of size s1.length() on s2. Compare int[26].

4. **Fruit Into Baskets** (LeetCode #904)
   - *Task:* Longest subarray with at most 2 distinct values.
   - *Hint:* atMostK(2) with HashMap.

5. **Max Consecutive Ones III** (LeetCode #1004)
   - *Task:* Longest subarray of 1s after flipping at most k zeros.
   - *Hint:* Count zeros in window. Shrink when zeros > k.

### Hard — Complex Window Logic
1. **Minimum Window Substring** (LeetCode #76)
   - *Task:* Smallest window in s containing all chars of t.
   - *Hint:* Two HashMaps + "formed" counter tracking satisfied char requirements.

2. **Sliding Window Maximum** (LeetCode #239)
   - *Task:* Maximum in every window of size k.
   - *Hint:* Monotonic Deque (covered in Queue section) — not pure sliding window.

3. **Subarrays with K Different Integers** (LeetCode #992)
   - *Task:* Exactly k distinct integers.
   - *Hint:* atMostK(k) - atMostK(k-1).

---

## 17. How to Know You Have Mastered Sliding Window

You have mastered this topic when you can:
- [ ] Immediately identify fixed vs variable window from problem description
- [ ] Write the variable window template from memory correctly (expand first, then shrink)
- [ ] Explain why sliding window is O(n) using amortized analysis
- [ ] Know when NOT to use sliding window (negative numbers + exact sum)
- [ ] Apply the "atMostK - atMostK(k-1)" trick for exact-k problems
- [ ] Implement Minimum Window Substring without looking it up
- [ ] Recognize that `right - left + 1` is window size (both inclusive)
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. A sliding window problem asks for the "longest subarray with sum ≤ k." The array has only positive integers. Can you use a variable window? What if the array has negative integers?

2. The loop structure has `for (right...)` with `while (invalid) { left++; }` inside. How is this still O(n)?

3. What does `result += right - left + 1` count in the "at most K" pattern?

4. You have `s = "ADOBECODEBANC"` and `t = "ABC"`. Walk through why the minimum window is "BANC" and not "ADOBE".

5. In "Longest Repeating Character Replacement," the validity condition is `windowSize - maxCharFreq ≤ k`. Why is this the right condition?

6. Can you have a sliding window where `left` moves faster than `right`? When?

7. Why does "Subarray Sum Equals K" (with possible negatives) NOT work with sliding window?

8. What data structure tracks the maximum element inside a window in O(1) per step?

> **Answers:**
> 1. Yes for positives (adding always increases sum, removing always decreases). No for negatives (adding can decrease sum, so shrinking might not fix invalidity).
> 2. Each element is added exactly once and removed at most once → 2n total operations → O(n) amortized.
> 3. It counts all valid subarrays ending at index `right`: length 1, 2, ..., (right-left+1).
> 4. "ADOBE" has A,D,O,B,E — it contains A and B but not C. The minimum containing A,B,C is "BANC."
> 5. To make all chars the same, we keep the most frequent one and replace the rest. If replacements needed = size - maxFreq ≤ k, window is valid.
> 6. No — left can only move right and never passes right. The window size can be 0 but not negative.
> 7. Adding a negative can decrease the sum — a previously-too-large window might become valid again. So you can't safely advance left without reconsidering.
> 8. Monotonic Deque — maintains elements in decreasing order; front is always the max.

---

**Next →** `../07_Prefix_Sum/01_Prefix_Sum.md`

---

## 2. Beginner-Friendly Intuition

Imagine a **sliding frame** over a photo strip.  
- You expand it to capture more.
- You shrink it when a rule is violated.
- You track the best frame you've seen.

---

## 3. Types of Sliding Windows

| Type | Window Size | Pointer Movement |
|------|------------|-----------------|
| Fixed Size | Constant k | Both advance by 1 |
| Variable Size | Grows/shrinks | Right always expands, left shrinks conditionally |

---

## 4. Template: Fixed Size Window

**Problem:** Maximum sum subarray of size k.

```java
int maxSumSubarray(int[] arr, int k) {
    int windowSum = 0;
    // Build first window
    for (int i = 0; i < k; i++) windowSum += arr[i];
    int maxSum = windowSum;
    // Slide window
    for (int i = k; i < arr.length; i++) {
        windowSum += arr[i] - arr[i - k];  // add new, remove old
        maxSum = Math.max(maxSum, windowSum);
    }
    return maxSum;
}
```

**Dry Run:** arr=[2,1,5,1,3,2], k=3
```
Initial window [2,1,5]: sum=8
Slide: [1,5,1]: 8+1-2=7
Slide: [5,1,3]: 7+3-1=9  ← max
Slide: [1,3,2]: 9+2-5=6
Result: 9
```

---

## 5. Template: Variable Size Window

**The core pattern:**

```java
int left = 0;
// state tracking (map, sum, count...)
for (int right = 0; right < n; right++) {
    // EXPAND: add arr[right] to window state

    while (/* window is INVALID */) {
        // SHRINK: remove arr[left] from window state
        left++;
    }
    // At this point, window [left...right] is valid
    // UPDATE answer
}
```

---

## 6. Longest Substring Without Repeating Characters

```java
int lengthOfLongestSubstring(String s) {
    Set<Character> window = new HashSet<>();
    int left = 0, maxLen = 0;
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        while (window.contains(c)) {      // shrink until no duplicate
            window.remove(s.charAt(left++));
        }
        window.add(c);
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

**Dry Run:** "abcabcbb"
```
right=0(a): window={a}, len=1
right=1(b): window={a,b}, len=2
right=2(c): window={a,b,c}, len=3
right=3(a): 'a' in window → remove 'a'(left=0), left=1
            window={b,c,a}, len=3
right=4(b): 'b' in window → remove 'b'(left=1), left=2
            window={c,a,b}, len=3
...
maxLen = 3
```

---

## 7. Minimum Window Substring (Hard but important)

**Problem:** Find smallest window in s containing all chars of t.

```java
String minWindow(String s, String t) {
    if (s.isEmpty() || t.isEmpty()) return "";
    Map<Character, Integer> need = new HashMap<>();
    for (char c : t.toCharArray()) need.merge(c, 1, Integer::sum);

    int left = 0, formed = 0, required = need.size();
    Map<Character, Integer> have = new HashMap<>();
    int[] ans = {-1, 0, 0};  // {length, left, right}

    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        have.merge(c, 1, Integer::sum);
        if (need.containsKey(c) && have.get(c).equals(need.get(c)))
            formed++;

        while (formed == required) {
            if (ans[0] == -1 || right - left + 1 < ans[0]) {
                ans[0] = right - left + 1;
                ans[1] = left; ans[2] = right;
            }
            char leftChar = s.charAt(left);
            have.merge(leftChar, -1, Integer::sum);
            if (need.containsKey(leftChar) && have.get(leftChar) < need.get(leftChar))
                formed--;
            left++;
        }
    }
    return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
}
```

---

## 8. "At Most K" Pattern

**Problem:** Number of subarrays with at most K distinct integers.

```java
int atMostK(int[] arr, int k) {
    Map<Integer, Integer> count = new HashMap<>();
    int left = 0, result = 0;
    for (int right = 0; right < arr.length; right++) {
        count.merge(arr[right], 1, Integer::sum);
        while (count.size() > k) {
            count.merge(arr[left], -1, Integer::sum);
            if (count.get(arr[left]) == 0) count.remove(arr[left]);
            left++;
        }
        result += right - left + 1;  // all subarrays ending at right
    }
    return result;
}

// Exactly K = atMostK(k) - atMostK(k-1)
```

---

## 9. Longest Subarray with Sum ≤ K (Non-negative values)

```java
int longestSubarrayWithSumK(int[] arr, int k) {
    int left = 0, sum = 0, maxLen = 0;
    for (int right = 0; right < arr.length; right++) {
        sum += arr[right];
        while (sum > k) sum -= arr[left++];
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

---

## 10. When to Use Sliding Window

Pattern recognition signals:
- "Longest/shortest subarray/substring..."
- "Maximum sum subarray of size k"
- "Subarray with sum equal to..." (+ prefix sum for negative values)
- "Window with at most K distinct..."
- "Minimum window containing..."

**NOT sliding window when:**
- Array has negatives + you need exact sum → use prefix sum + map
- Non-contiguous elements needed → DP or greedy

---

## 11. Practice Problems

**Easy:**
1. Maximum Average Subarray of Size K.
2. Find all anagrams in a string.
3. Maximum sum subarray of size k.

**Medium:**
1. Longest Substring Without Repeating Characters.
2. Longest Substring with At Most K Distinct Characters.
3. Permutation in String.
4. Fruit Into Baskets (at most 2 types).
5. Max Consecutive Ones III (flip at most K zeros).

**Hard:**
1. Minimum Window Substring.
2. Substring with Concatenation of All Words.
3. Sliding Window Maximum (use deque — see Queue section).

---

**Next →** `../07_Prefix_Sum/01_Prefix_Sum.md`

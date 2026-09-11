# Section 5 — Two Pointer Pattern

---

## 1. What Problem Does This Solve?

Many array and string problems require comparing, pairing, or rearranging elements. The brute force tries every possible pair — O(n²). The Two Pointer pattern eliminates the inner loop entirely by using **two indices that move intelligently** based on what you observe at each step.

Problems it solves:
- Finding pairs/triplets with a given sum
- Checking palindromes
- Removing duplicates in-place
- Partitioning arrays
- Detecting cycles in linked lists
- Merging sorted arrays

---

## 2. Beginner-Friendly Intuition

Imagine two people walking toward each other on a bridge. Instead of every person meeting every other person (n² handshakes), they start at opposite ends and meet exactly once — O(n) total.

Or think about squeezing a tube of toothpaste: you push from both ends toward the middle simultaneously. Each squeeze makes progress — you never redo work.

---

## 3. Real-World Analogy

**Binary search in a phone book:** Two people start at opposite ends scanning inward for a match. One moves forward when their page is too early, the other moves backward when too late.

**Sorting socks:** You hold one sock in each hand. If they match, put them aside. If left sock comes first alphabetically, grab the next from the left pile. Otherwise grab from the right.

---

## 4. Core Concept

There are **three variants** of the Two Pointer pattern:

| Type | Starting Positions | Movement | Use Case |
|------|-------------------|----------|---------|
| **Opposite direction** | One at start, one at end | Move toward each other | Pairs in sorted array, palindrome |
| **Same direction** | Both at start, one faster | Both move right | Remove duplicates, compact array |
| **Fast & Slow** | Both at start, speeds differ | Slow moves 1, fast moves 2 | Cycle detection, find middle |

**The key invariant:** At each step, you make a decision that eliminates part of the search space — similar to binary search but for two-dimensional choices.

---

## 5. Pattern Recognition Signals

Use Two Pointers when:
```
- Input array is sorted (or can be sorted)
- "Find pair/triplet summing to target"
- "Check if palindrome"
- "Remove duplicates in-place"
- "Move/partition elements"
- "Reverse in-place"
- "Merge two sorted arrays"
- Linked list: "detect cycle", "find middle", "Kth from end"
- "Minimum window" or "longest subarray" (combined with sliding window)
```

**Red flag that brute force is O(n²):** Two nested loops both scanning the array independently.

---

## 6. Step-by-Step Algorithm

### Opposite Direction Template
```
Step 1: Sort the array (if not already sorted)
Step 2: Set left = 0, right = n-1
Step 3: While left < right:
    a. Compute value using arr[left] and arr[right]
    b. If value matches target: record answer, move both
    c. If value too small: left++ (need bigger values)
    d. If value too large: right-- (need smaller values)
Step 4: Return result
```

### Same Direction Template
```
Step 1: Set slow = 0 (write pointer), fast = 0 (read pointer)
Step 2: While fast < n:
    a. If arr[fast] is "valid" (should be kept):
       arr[slow] = arr[fast]; slow++
    b. fast++  (always advance)
Step 3: Array [0..slow-1] is the compacted result
```

---

## 7. Dry Run with Example

### Example 1: Two Sum (sorted input)

**Input:** `arr = [1, 2, 4, 6, 8, 9]`, `target = 11`

```
left=0(1), right=5(9): sum=10 < 11 → need bigger → left++
left=1(2), right=5(9): sum=11 == 11 → FOUND! return [1,5]

Why right doesn't need to move left first:
If we moved right-- to 8: sum=2+8=10 < 11 → still need left++
The decision is always: if sum < target, only increasing left helps (array sorted).
```

### Example 2: Remove Duplicates from Sorted Array

**Input:** `arr = [1, 1, 2, 3, 3, 4]`

```
slow=0, fast=0: start

fast=0(1): first element is always kept, slow remains 0
fast=1(1): arr[1]=1 == arr[0]=1 → duplicate, skip
fast=2(2): arr[2]=2 ≠ arr[0]=1 → different! slow++, arr[1]=2
           arr=[1,2,2,3,3,4], slow=1
fast=3(3): arr[3]=3 ≠ arr[1]=2 → different! slow++, arr[2]=3
           arr=[1,2,3,3,3,4], slow=2
fast=4(3): arr[4]=3 == arr[2]=3 → duplicate, skip
fast=5(4): arr[5]=4 ≠ arr[2]=3 → different! slow++, arr[3]=4
           arr=[1,2,3,4,3,4], slow=3

Return slow+1 = 4. First 4 elements: [1,2,3,4] ✓
```

### Example 3: Trapping Rain Water

**Input:** `height = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]`

```
left=0, right=11, leftMax=0, rightMax=0, water=0

left(0) <= right(1):
  leftMax = max(0, 0) = 0
  water += 0 - 0 = 0
  left=1

left(1) <= right(1):
  leftMax = max(0, 1) = 1
  water += 1 - 1 = 0
  left=2

left(0) < right(1):
  leftMax = max(1, 0) = 1
  water += 1 - 0 = 1  ← 1 unit trapped
  left=3

[...continues until left meets right]
Total water = 6 units
```

---

## 8. Code Implementation

### Opposite Direction: Pair Sum in Sorted Array

```java
boolean hasPairSum(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        int sum = arr[left] + arr[right];
        if (sum == target) return true;
        else if (sum < target) left++;   // sum too small → increase left
        else right--;                    // sum too large → decrease right
    }
    return false;
}
```

### 3Sum — Sort + Fix One + Two Pointers on Rest

```java
List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < nums.length - 2; i++) {
        if (i > 0 && nums[i] == nums[i-1]) continue; // skip duplicate i
        int left = i + 1, right = nums.length - 1;
        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];
            if (sum == 0) {
                result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                while (left < right && nums[left] == nums[left+1]) left++;
                while (left < right && nums[right] == nums[right-1]) right--;
                left++; right--;
            } else if (sum < 0) left++;
            else right--;
        }
    }
    return result;
}
```

### Same Direction: Remove Duplicates In-Place

```java
int removeDuplicates(int[] arr) {
    if (arr.length == 0) return 0;
    int slow = 0; // next write position
    for (int fast = 1; fast < arr.length; fast++) {
        if (arr[fast] != arr[slow]) {
            slow++;
            arr[slow] = arr[fast];
        }
    }
    return slow + 1;
}
```

### Same Direction: Move Zeros to End

```java
void moveZeros(int[] arr) {
    int slow = 0; // next position for non-zero
    for (int fast = 0; fast < arr.length; fast++) {
        if (arr[fast] != 0)
            arr[slow++] = arr[fast];
    }
    while (slow < arr.length) arr[slow++] = 0;
}
```

### Fast & Slow: Detect Cycle (Floyd's Algorithm)

```java
boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;        // moves 1 step
        fast = fast.next.next;   // moves 2 steps
        if (slow == fast) return true; // they meet inside cycle
    }
    return false;
}
```

### Container With Most Water

```java
int maxArea(int[] heights) {
    int left = 0, right = heights.length - 1, max = 0;
    while (left < right) {
        int area = (right - left) * Math.min(heights[left], heights[right]);
        max = Math.max(max, area);
        // Move the shorter side — moving taller side can only decrease area
        if (heights[left] < heights[right]) left++;
        else right--;
    }
    return max;
}
```

### Trapping Rain Water

```java
int trap(int[] height) {
    int left = 0, right = height.length - 1;
    int leftMax = 0, rightMax = 0, water = 0;
    while (left < right) {
        if (height[left] <= height[right]) {
            // left side is the bottleneck
            leftMax = Math.max(leftMax, height[left]);
            water += leftMax - height[left]; // guaranteed non-negative
            left++;
        } else {
            rightMax = Math.max(rightMax, height[right]);
            water += rightMax - height[right];
            right--;
        }
    }
    return water;
}
```

---

## 9. Time Complexity

| Problem | Brute Force | Two Pointers | Improvement |
|---------|------------|-------------|-------------|
| Pair sum (sorted) | O(n²) | O(n) | Linear |
| 3Sum | O(n³) | O(n²) | Quadratic |
| Remove duplicates | O(n²) | O(n) | Linear |
| Trapping rain water | O(n²) | O(n) | Linear |
| Cycle detection | O(n²) | O(n) | Linear |
| Container with most water | O(n²) | O(n) | Linear |

All Two Pointer solutions run in **O(n)** — each pointer traverses the array at most once.

---

## 10. Space Complexity

**O(1) extra space** — the defining advantage of Two Pointers over HashMap.

No auxiliary data structures needed. Only two (or three) integer pointers.

> This is why Two Pointers is preferred over HashMap when the input is **already sorted** — you get O(n) time AND O(1) space.

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| Empty array | Check `arr.length < 2` before starting |
| Single element | Condition `left < right` handles this (loop doesn't execute) |
| All same elements | 3Sum: skip duplicates with while loops |
| All zeros / all same | moveZeros: slow stays at 0 the whole time |
| Already sorted | Algorithm still works correctly |
| Negative numbers | Sorted order handles them naturally |
| Two elements | Handles correctly: left=0, right=1 → one iteration |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Forgetting to sort for pair-sum problems
// Two pointers ONLY work when array is sorted (sorted means monotonic)
// threeSum without sorting → WRONG
int left = 0, right = n-1;
// FIX: Arrays.sort(nums); first

// MISTAKE 2: Not skipping duplicates in 3Sum
// Without duplicate skip, returns duplicate triplets
// FIX: while (left < right && nums[left] == nums[left+1]) left++;

// MISTAKE 3: Wrong loop condition
while (left <= right) // BUG for pair-sum — may use same element twice
while (left < right)  // CORRECT

// MISTAKE 4: Wrong move direction
if (sum < target) right--; // WRONG — makes sum even smaller
if (sum < target) left++;  // CORRECT — moves to larger values

// MISTAKE 5: Fast pointer null check order
while (fast.next != null && fast != null)   // WRONG — NPE!
while (fast != null && fast.next != null)   // CORRECT — check fast first
```

---

## 13. Interview-Level Explanation

**Q: "How does Two Pointers improve over brute force for Container With Most Water?"**

> "Brute force checks every pair (i, j) — O(n²) pairs. But we can observe: the area = (j-i) × min(height[i], height[j]). If we start with the widest possible container (left=0, right=n-1), the only way to potentially find a bigger area is to sacrifice width. Since width only decreases when we move pointers inward, we should only move the pointer whose height is smaller — because moving the taller one can only decrease or keep the same min-height while width decreases. This greedy decision reduces the search space from O(n²) to O(n)."

**Q: "Why does Floyd's cycle detection work?"**

> "If there's a cycle, fast catches up to slow eventually — like a faster runner lapping a slower one on a circular track. The math: slow moves 1 step/iteration, fast moves 2. If cycle length is C, they must meet within C iterations after entering the cycle."

---

## 14. Real-World Use Cases

| Application | Two Pointer Usage |
|------------|-----------------|
| **Water supply systems** | Optimal pipe sizing (Container With Most Water analog) |
| **Memory management** | Two-pointer garbage collection (mark and compact) |
| **Text editors** | Palindrome checking, find-and-replace |
| **Network protocols** | Sliding window in TCP (variant of same-direction) |
| **Merge sort (merge step)** | Two pointers on two sorted halves |
| **Database join** | Sort-merge join uses two pointers on sorted relations |
| **Genomics** | DNA sequence alignment scanning |

---

## 15. Variations of This Pattern

| Variation | Description | Example Problem |
|-----------|-------------|----------------|
| Pair sum (sorted) | Opposite direction | Two Sum II |
| Pair sum (unsorted) | HashMap instead | Two Sum I |
| Triplet sum | Fix outer, two-pointer inner | 3Sum |
| Quadruplet sum | Fix two outer, two-pointer inner | 4Sum |
| Palindrome check | Opposite direction comparison | Valid Palindrome |
| Partition | Dutch National Flag | Sort Colors |
| Fast & Slow | Different speeds | Linked List Cycle |
| Remove element | Same direction | Remove Element |
| Merge sorted | Two pointers on two arrays | Merge Sorted Array |
| K-diff pairs | Two pointers on sorted + count | K-diff Pairs in Array |

---

## 16. Practice Problems

### Easy — Build Intuition
1. **Valid Palindrome** (LeetCode #125)
   - *Task:* Check if string is a palindrome (ignore non-alphanumeric).
   - *Hint:* Start from both ends. Skip non-alphanumeric. Compare letters.

2. **Two Sum II** (LeetCode #167)
   - *Task:* Find two numbers in a sorted array summing to target.
   - *Hint:* Classic opposite-direction template.

3. **Squares of a Sorted Array** (LeetCode #977)
   - *Task:* Return sorted squares of input (which may have negatives).
   - *Hint:* Two pointers from ends. Larger absolute value goes to the back.

### Medium — Apply Non-Obvious Decisions
1. **3Sum** (LeetCode #15)
   - *Task:* Find all unique triplets summing to zero.
   - *Hint:* Sort, fix i, two-pointer on rest. Skip duplicates carefully.

2. **Container With Most Water** (LeetCode #11)
   - *Task:* Find two lines forming container with most water.
   - *Hint:* Move the shorter pointer — proves we can't do better by keeping it.

3. **3Sum Closest** (LeetCode #16)
   - *Task:* Triplet sum closest to target.
   - *Hint:* Like 3Sum but track closest diff instead of exact match.

4. **Sort Colors** (LeetCode #75)
   - *Task:* Sort [0,1,2] in-place without counting.
   - *Hint:* Dutch National Flag — 3 pointers: low, mid, high.

5. **Trapping Rain Water** (LeetCode #42)
   - *Task:* Compute water trapped between bars.
   - *Hint:* Track leftMax and rightMax from both ends.

### Hard — Combine with Other Patterns
1. **4Sum** (LeetCode #18)
   - *Task:* All unique quadruplets summing to target.
   - *Hint:* Fix two outer pointers, two-pointer for inner pair. Skip duplicates at all levels.

2. **Minimum Size Subarray Sum** (LeetCode #209)
   - *Task:* Shortest contiguous subarray with sum ≥ target.
   - *Hint:* Same-direction two pointers (sliding window variant).

3. **Trapping Rain Water II** (LeetCode #407)
   - *Task:* 3D version — water trapped in a 3D grid.
   - *Hint:* Min-Heap + BFS from borders (two-pointer intuition extends to 3D).

---

## 17. How to Know You Have Mastered Two Pointers

You have mastered this topic when you can:
- [ ] Identify in 30 seconds whether a problem needs opposite-direction or same-direction pointers
- [ ] Implement 3Sum correctly (sorting + duplicate skipping) without bugs
- [ ] Explain WHY you move the shorter pointer in Container With Most Water
- [ ] Write Floyd's cycle detection from memory
- [ ] Distinguish: when to use Two Pointers vs HashMap for pair finding
- [ ] Handle the "skip duplicates" logic in 3Sum/4Sum correctly
- [ ] Solve Trapping Rain Water with O(1) space
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. For a **sorted** array, when finding a pair with sum = target, if `arr[left] + arr[right] > target`, which pointer do you move and why?

2. In 3Sum, why do you sort the array first?

3. What is the time complexity of 3Sum after sorting?

4. Floyd's cycle detection uses fast and slow pointers. If the fast pointer moves 3 steps per iteration instead of 2, does it still work? Why or why not?

5. In "Remove Duplicates from Sorted Array," what does the `slow` pointer represent at all times?

6. Trapping Rain Water brute force is O(n²) with O(1) space. Two pointers is O(n) with O(1) space. Is there a solution between these?

7. Can you use Two Pointers on an **unsorted** array to find a pair with sum = target? If yes, with what condition? If no, why?

8. In "Sort Colors" (Dutch National Flag), you have three pointers: low, mid, high. What invariant does each pointer maintain?

> **Answers:**
> 1. Move `right--`. Sum is too large; to decrease it, decrease the right element.
> 2. Sorting makes the two-pointer approach valid (monotonic order) and enables duplicate skipping.
> 3. O(n²) — outer loop O(n), inner two-pointer O(n).
> 4. Yes, but only if gcd(speed_difference, cycle_length) = 1. For safety, always use 2.
> 5. `slow` is the last position of the "unique elements" section. All elements in [0..slow] are unique.
> 6. O(n) time, O(n) space: precompute leftMax and rightMax arrays.
> 7. Only with sorting first (O(n log n)). Without sorting, use HashMap for O(n).
> 8. [0..low-1]=0s, [low..mid-1]=1s, [mid..high]=unknown, [high+1..n-1]=2s.

---

**Next →** `../06_Sliding_Window/01_Sliding_Window.md`

---

## 2. Beginner-Friendly Intuition

Imagine two people walking toward each other on a bridge. They start at opposite ends and meet in the middle. Instead of everyone checking everyone (n² meetings), they meet exactly once.

---

## 3. Types of Two Pointers

| Type | Movement | Use Case |
|------|----------|---------|
| Opposite direction | Start at ends, move toward center | Pair problems, palindrome |
| Same direction | Both start at left, move right at different speeds | Remove duplicates, fast/slow |
| Fast & Slow | One moves 2x speed | Cycle detection, middle finding |

---

## 4. Pattern 1: Opposite Direction (Sorted Array)

### Pair Sum Problem

```java
// Find if any two numbers sum to target in SORTED array
boolean hasPairSum(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        int sum = arr[left] + arr[right];
        if (sum == target) return true;
        else if (sum < target) left++;   // need bigger sum
        else right--;                    // need smaller sum
    }
    return false;
}
```

**Dry Run:** arr=[1,2,4,6,8,9], target=11
```
left=0(1), right=5(9): sum=10 < 11 → left++
left=1(2), right=5(9): sum=11 == 11 → return true ✓
```

**Why this works:** Array is sorted. If sum too small → move left pointer right (increase it). If too big → move right pointer left (decrease it).

---

### Container With Most Water

```java
int maxWater(int[] heights) {
    int left = 0, right = heights.length - 1, max = 0;
    while (left < right) {
        int water = (right - left) * Math.min(heights[left], heights[right]);
        max = Math.max(max, water);
        if (heights[left] < heights[right]) left++;
        else right--;
    }
    return max;
}
```

**Why move the shorter side?** Moving the taller side can only decrease width without increasing height. Moving the shorter side might find a taller one.

---

### Trapping Rain Water

```java
int trapRainWater(int[] height) {
    int left = 0, right = height.length - 1;
    int leftMax = 0, rightMax = 0, water = 0;
    while (left < right) {
        if (height[left] <= height[right]) {
            leftMax = Math.max(leftMax, height[left]);
            water += leftMax - height[left];
            left++;
        } else {
            rightMax = Math.max(rightMax, height[right]);
            water += rightMax - height[right];
            right--;
        }
    }
    return water;
}
```

**Dry Run:** [0,1,0,2,1,0,1,3,2,1,2,1]
```
At each position, water = min(maxLeft, maxRight) - height[i]
Total = 6
```

---

## 5. Pattern 2: Same Direction (Remove / Compact)

### Remove Duplicates from Sorted Array

```java
int removeDuplicates(int[] arr) {
    if (arr.length == 0) return 0;
    int slow = 0;  // boundary of unique elements
    for (int fast = 1; fast < arr.length; fast++) {
        if (arr[fast] != arr[slow]) {
            slow++;
            arr[slow] = arr[fast];
        }
    }
    return slow + 1;  // length of unique portion
}
```

**Dry Run:** [1,1,2,3,3,4]
```
slow=0(1), fast=1: same → skip
slow=0(1), fast=2(2): different → slow=1, arr[1]=2 → [1,2,2,3,3,4]
slow=1(2), fast=3(3): different → slow=2, arr[2]=3 → [1,2,3,3,3,4]
slow=2(3), fast=4: same → skip
slow=2(3), fast=5(4): different → slow=3, arr[3]=4 → [1,2,3,4,...]
return 4
```

---

### Move Zeros to End

```java
void moveZeros(int[] arr) {
    int insertPos = 0;
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] != 0) arr[insertPos++] = arr[i];
    }
    while (insertPos < arr.length) arr[insertPos++] = 0;
}
```

---

## 6. Pattern 3: Fast & Slow Pointers

### Detect Cycle in Linked List (Floyd's Algorithm)

```java
boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}
```

**Why it works:** If cycle exists, fast laps slow → they meet. If no cycle, fast reaches null.

### Find Cycle Start

```java
ListNode detectCycleStart(ListNode head) {
    ListNode slow = head, fast = head;
    boolean hasCycle = false;
    while (fast != null && fast.next != null) {
        slow = slow.next; fast = fast.next.next;
        if (slow == fast) { hasCycle = true; break; }
    }
    if (!hasCycle) return null;
    slow = head;
    while (slow != fast) { slow = slow.next; fast = fast.next; }
    return slow;  // cycle start
}
```

---

## 7. Pattern 4: Three Pointers — 3Sum

```java
List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < nums.length - 2; i++) {
        if (i > 0 && nums[i] == nums[i-1]) continue;  // skip duplicate i
        int left = i + 1, right = nums.length - 1;
        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];
            if (sum == 0) {
                result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                while (left < right && nums[left] == nums[left+1]) left++;
                while (left < right && nums[right] == nums[right-1]) right--;
                left++; right--;
            } else if (sum < 0) left++;
            else right--;
        }
    }
    return result;
}
```

---

## 8. How to Recognize Two Pointer Problems

- Sorted array + find pair/triple
- "Minimum/maximum window"
- "Remove/compact in place"
- "Palindrome check"
- Linked list cycle/middle

---

## 9. Practice Problems

**Easy:**
1. Valid Palindrome.
2. Squares of a Sorted Array.
3. Two Sum II (sorted input).

**Medium:**
1. 3Sum.
2. Container With Most Water.
3. 3Sum Closest.
4. Sort Colors (Dutch National Flag).
5. Trapping Rain Water.

**Hard:**
1. 4Sum.
2. Minimum Window Substring.
3. Trapping Rain Water II (3D).

---

**Next →** `../06_Sliding_Window/01_Sliding_Window.md`

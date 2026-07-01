# Section 2.1 — Arrays

---

## 1. What Problem Does This Solve?

You need to store a collection of items of the same type and access any item by position in O(1) time. Arrays are the most fundamental data structure — they form the foundation of nearly every other structure (stacks, queues, heaps, hash tables).

---

## 2. Beginner-Friendly Intuition

An array is a row of numbered mailboxes in memory. Each box has a fixed address, and "give me box 3" costs the same O(1) time regardless of array size — just compute `base_address + 3 × element_size`.

The trade-off: size is fixed at creation (for Java primitive arrays), and inserting/deleting in the middle is expensive (O(n) shifting required).

---

## 3. Real-World Analogy

**Seats in a cinema hall:** Seat 15 is always at the same physical location. You don't search for it — you walk directly to row 2, seat 5 based on arithmetic. That's O(1) access.

**Monthly calendar:** 12 months stored at fixed indices [0..11]. January = index 0, December = index 11. Random access by month number.

---

## 4. Core Concept

### Memory Layout
```
Memory address: 100  104  108  112  116
Value:           10   20   30   40   50
Index:           [0]  [1]  [2]  [3]  [4]

arr[i] = base_address + (i × element_size)
arr[2] = 100 + (2 × 4) = 108 → value 30
```

### Java Array Types

| Type | Declaration | Characteristics |
|------|-------------|----------------|
| Fixed array | `int[] arr = new int[5]` | Size fixed, O(1) access |
| Dynamic array | `ArrayList<Integer> list` | Auto-resize, O(1) amortized add |
| 2D array | `int[][] grid = new int[m][n]` | Row-major storage |

---

## 5. Pattern Recognition Signals

```
"Contiguous elements, find max/min subarray" → Sliding Window
"Pair summing to target" → Two Pointers (if sorted) / Hashing
"Running sum from index 0..i" → Prefix Sum
"Sort and process" → Sort the array first
"Find duplicate / missing" → Math or Hashing
"Rotate array" → Reverse technique
"Matrix traversal" → 2D array iteration
```

---

## 6. Step-by-Step Algorithm

### Rotate Array Right by K
```
reverse entire array
reverse first k elements
reverse remaining n-k elements

Example: [1,2,3,4,5], k=2
After reverse all:    [5,4,3,2,1]
After reverse 0..1:   [4,5,3,2,1]
After reverse 2..4:   [4,5,1,2,3] ✓
```

### Find Duplicate in [1..n] Array (One Duplicate)
```
For each element x, visit arr[|x|-1] and negate it.
If arr[|x|-1] is already negative → x is duplicate.
```

---

## 7. Dry Run with Example

### Two Sum — Brute Force vs HashMap
```
nums = [2, 7, 11, 15], target = 9

Brute force O(n^2):
  (0,1): 2+7=9 ✓ → [0,1]

HashMap O(n):
  i=0: need 9-2=7, map={} → add 2:0
  i=1: need 9-7=2, map={2:0} → found! return [0,1] ✓
```

### Prefix Sum for Range Queries
```
arr = [3, 1, 4, 1, 5, 9]
prefix = [0, 3, 4, 8, 9, 14, 23]  (prefix[i] = sum of arr[0..i-1])

sum(1, 3) = prefix[4] - prefix[1] = 9 - 3 = 6
  arr[1]+arr[2]+arr[3] = 1+4+1 = 6 ✓
```

---

## 8. Code Implementation

```java
import java.util.*;

public class ArrayOperations {

    // ── Basic Java Array Operations ───────────────────────────────────────
    public void basics() {
        int[] arr = new int[5];                 // [0, 0, 0, 0, 0]
        int[] init = {10, 20, 30, 40, 50};     // initialized
        int[][] grid = new int[3][4];           // 3 rows, 4 cols

        // Access and modify
        init[2] = 99;                           // O(1) write
        int val = init[2];                      // O(1) read

        // Sort and search
        Arrays.sort(init);                      // O(n log n)
        int idx = Arrays.binarySearch(init, 30); // O(log n) after sort

        // Copy
        int[] copy = Arrays.copyOf(init, init.length);
        int[] range = Arrays.copyOfRange(init, 1, 4); // [20, 30, 40]

        // Fill
        Arrays.fill(arr, 7);                    // [7, 7, 7, 7, 7]

        // ArrayList
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        list.add(4);                            // O(1) amortized
        list.remove(Integer.valueOf(2));        // O(n) — remove by value
        Collections.sort(list);                // O(n log n)
    }

    // ── Rotate Array (right by k) ─────────────────────────────────────────
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;                    // handle k > n
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    void reverse(int[] arr, int l, int r) {
        while (l < r) {
            int tmp = arr[l]; arr[l] = arr[r]; arr[r] = tmp;
            l++; r--;
        }
    }

    // ── Prefix Sum ────────────────────────────────────────────────────────
    public int[] buildPrefix(int[] arr) {
        int n = arr.length;
        int[] prefix = new int[n + 1]; // prefix[0] = 0 (sentinel)
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + arr[i];
        return prefix;
    }

    public int rangeSum(int[] prefix, int l, int r) {
        return prefix[r + 1] - prefix[l]; // sum arr[l..r]
    }

    // ── Find Single Duplicate (nums contains 1..n with one duplicate) ─────
    public int findDuplicate(int[] nums) {
        // Floyd's cycle detection (in-place, O(1) space)
        int slow = nums[0], fast = nums[0];
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        slow = nums[0];
        while (slow != fast) { slow = nums[slow]; fast = nums[fast]; }
        return slow;
    }

    // ── Maximum Subarray Sum (Kadane's Algorithm) ─────────────────────────
    public int maxSubArray(int[] nums) {
        int maxSum = nums[0], currentSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSum = Math.max(maxSum, currentSum);
        }
        return maxSum;
    }
}
```

---

## 9. Time Complexity

| Operation | int[] | ArrayList |
|-----------|-------|-----------|
| Access by index | O(1) | O(1) |
| Search (unsorted) | O(n) | O(n) |
| Search (sorted) | O(log n) | O(log n) |
| Insert at end | N/A | O(1) amortized |
| Insert at middle | O(n) | O(n) |
| Delete at end | N/A | O(1) |
| Delete at middle | O(n) | O(n) |
| Sort | O(n log n) | O(n log n) |

---

## 10. Space Complexity

| Structure | Space |
|-----------|-------|
| int[n] | O(n) |
| int[m][n] | O(m×n) |
| Prefix sum array | O(n) extra |
| ArrayList (capacity c) | O(c) |

---

## 11. Edge Cases

| Scenario | Handling |
|----------|---------|
| Empty array | Check `n == 0` before any operation |
| Single element | All operations still valid |
| k > n in rotate | Use `k %= n` |
| All negative in max subarray | Kadane handles: starts with nums[0] |
| Integer overflow in prefix sum | Use `long[]` for prefix if values are large |

---

## 12. Common Mistakes

```java
// MISTAKE 1: ArrayIndexOutOfBoundsException
for (int i = 0; i <= arr.length; i++) // WRONG: i goes to length
for (int i = 0; i < arr.length; i++)  // CORRECT

// MISTAKE 2: Shallow copy
int[] copy = arr; // WRONG: same reference, modifying copy modifies arr
int[] copy = Arrays.copyOf(arr, arr.length); // CORRECT

// MISTAKE 3: Comparing arrays with ==
if (arr1 == arr2)               // WRONG: compares references
if (Arrays.equals(arr1, arr2))  // CORRECT for 1D
if (Arrays.deepEquals(a, b))    // CORRECT for 2D

// MISTAKE 4: Using remove(int) vs remove(Object) on ArrayList
list.remove(2);               // removes index 2 (int overload)
list.remove(Integer.valueOf(2)); // removes element with value 2

// MISTAKE 5: ConcurrentModification
for (int x : list) if (x < 5) list.remove(x); // WRONG: CME
// CORRECT: use iterator.remove() or collect then remove
```

---

## 13. Interview-Level Explanation

**Q: "Why is array access O(1) while insertion at index i is O(n)?"**

> "Arrays store elements in contiguous memory. Accessing index i is just arithmetic: base_address + i × element_size — one multiplication and one addition, independent of array size. Insertion at index i requires shifting all elements from i to the end one position right to make space, which is proportional to the number of elements — O(n)."

**Q: "When would you use an int[] vs ArrayList<Integer>?"**

> "Use `int[]` when size is fixed and performance matters — no boxing overhead and cache-friendly memory layout. Use `ArrayList` when size is unknown at compile time or when you need dynamic resizing. For competitive programming with tight time limits, prefer `int[]` to avoid boxing cost. For most application code, `ArrayList` is more convenient."

---

## 14. Real-World Use Cases

| Application | Array Usage |
|------------|------------|
| **Image processing** | Pixel values stored in 2D array |
| **Databases** | Column data (columnar storage) |
| **Ring buffers** | Circular arrays for streaming data |
| **DP tables** | 1D/2D arrays for memoization |
| **Matrix operations** | 2D arrays for linear algebra |
| **Frequency counting** | count[256] for ASCII character frequency |

---

## 15. Variations

| Variation | Technique |
|-----------|----------|
| 2D array rotation | Transpose + reverse rows |
| Spiral order traversal | Four-direction pointers |
| Kadane's algorithm | Max subarray with O(n) |
| Dutch National Flag | 3-way partition (sort 0s, 1s, 2s) |
| Circular array | Use `index % n` for wrap-around |
| Difference array | Range updates in O(1) |

---

## 16. Practice Problems

### Easy — Foundation
1. **Best Time to Buy and Sell Stock** (LeetCode #121)
   - *Task:* Maximum profit from one buy-sell.
   - *Hint:* Track minimum seen so far; O(n) single pass.

2. **Move Zeroes** (LeetCode #283)
   - *Task:* Move all zeros to end, maintain relative order.
   - *Hint:* Two pointers — write pointer for non-zeros.

3. **Find the Duplicate Number** (LeetCode #287)
   - *Task:* Find duplicate in [1..n] array.
   - *Hint:* Floyd's cycle detection (no extra space).

### Medium — Core
1. **Maximum Subarray** (LeetCode #53)
   - *Task:* Find contiguous subarray with maximum sum.
   - *Hint:* Kadane's algorithm.

2. **Product of Array Except Self** (LeetCode #238)
   - *Task:* Output[i] = product of all except arr[i], no division.
   - *Hint:* Left prefix product × right suffix product.

3. **Rotate Array** (LeetCode #189)
   - *Task:* Right rotate by k steps in-place.
   - *Hint:* Triple reverse trick.

4. **Container With Most Water** (LeetCode #11)
   - *Task:* Max water between two lines.
   - *Hint:* Two pointers, always move the shorter line.

5. **Subarray Sum Equals K** (LeetCode #560)
   - *Task:* Count subarrays with sum equal to k.
   - *Hint:* Prefix sum + HashMap for frequency.

### Hard — Advanced
1. **Trapping Rain Water** (LeetCode #42)
   - *Task:* Total water trapped between bars.
   - *Hint:* Two pointers or prefix max arrays.

2. **First Missing Positive** (LeetCode #41)
   - *Task:* Smallest positive integer not in array, O(n) time O(1) space.
   - *Hint:* Use array indices as a hash — place each num at index num-1.

3. **Median of Two Sorted Arrays** (LeetCode #4)
   - *Task:* O(log(min(m,n))) median.
   - *Hint:* Binary search on partition of smaller array.

---

## 17. How to Know You Have Mastered Arrays

You have mastered this topic when you can:
- [ ] Explain why random access is O(1) and insertion is O(n)
- [ ] Use prefix sum for any range sum query
- [ ] Apply Kadane's algorithm for max subarray
- [ ] Perform in-place array rotation with triple reverse
- [ ] Use two pointers on sorted arrays
- [ ] Avoid off-by-one, overflow, and shallow-copy bugs
- [ ] Know when to use `int[]` vs `ArrayList`
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. arr = [1,2,3,4,5]. After `rotate(arr, 3)` (right by 3), what is arr?

2. prefix = [0,3,4,8,9,14,23]. What is rangeSum(2, 4)?

3. Why is `int[] copy = arr` a shallow copy? How do you make a deep copy?

4. What does `list.remove(2)` do vs `list.remove(Integer.valueOf(2))`?

5. Kadane's algorithm: what does it do when all elements are negative?

6. For `int[] arr = new int[10]`, what is `arr[5]` by default?

7. `Arrays.sort(int[])` vs `Collections.sort(List<Integer>)`: which is faster and why?

8. How do you find the sum of all elements in a 2D array in Java?

> **Answers:**
> 1. [3,4,5,1,2] — right rotate by 3: last 3 elements move to front.
> 2. prefix[5] - prefix[2] = 14 - 4 = 10. (arr[2]+arr[3]+arr[4] = 4+1+5=10 ✓)
> 3. `int[] copy = arr` copies the reference, not the data. Both variables point to the same array. Use `Arrays.copyOf(arr, arr.length)` or `arr.clone()`.
> 4. `list.remove(2)` uses the `remove(int index)` overload — removes element at index 2. `list.remove(Integer.valueOf(2))` uses `remove(Object o)` — removes the element with value 2.
> 5. It still finds the maximum: since `currentSum = Math.max(nums[i], currentSum + nums[i])`, it will reset to the current element (the "least negative") when the running sum is worse than starting fresh. The result is the single largest element.
> 6. 0 — Java initializes all int array elements to 0 by default.
> 7. `Arrays.sort(int[])` is faster — uses Dual-Pivot Quicksort on primitives, no boxing. `Collections.sort` uses TimSort on objects (Integer), which requires boxing/unboxing and handles object comparisons.
> 8. `int sum = 0; for (int[] row : grid) for (int val : row) sum += val;` Or use `Arrays.stream(grid).flatMapToInt(Arrays::stream).sum();`

---

**Next →** `02_Strings.md`

---

## 2. Beginner-Friendly Intuition

An array is like a **row of numbered mailboxes**:
```
Index:  [0]  [1]  [2]  [3]  [4]
Value:   10   20   30   40   50
```
- Box 0 holds 10, Box 2 holds 30.
- Access by index is instant: "Give me Box 3" → 40.

---

## 3. Real-World Analogy

- Seats in a cinema hall (fixed positions, you know seat number).
- Days of the week stored as [Mon, Tue, Wed, Thu, Fri, Sat, Sun].

---

## 4. Internal Working

Memory is a sequence of bytes. An array is a **contiguous block**:
```
Memory address: 100  104  108  112  116
Value:           10   20   30   40   50
```
`arr[i]` = base_address + (i × element_size)  
This is why indexing is O(1) — direct math!

---

## 5. Operations & Complexity

| Operation | Time | Notes |
|-----------|------|-------|
| Access arr[i] | O(1) | Direct index calculation |
| Search (unsorted) | O(n) | Must check each element |
| Search (sorted) | O(log n) | Binary search |
| Insert at end | O(1) amortized | ArrayList auto-resize |
| Insert at middle | O(n) | Shift elements right |
| Delete at end | O(1) | Just reduce size |
| Delete at middle | O(n) | Shift elements left |

---

## 6. Java Implementation

```java
// Fixed-size array
int[] arr = new int[5];        // [0, 0, 0, 0, 0]
int[] arr2 = {10, 20, 30};     // Initialize with values

// Dynamic array (ArrayList)
List<Integer> list = new ArrayList<>();
list.add(10);                  // add at end — O(1) amortized
list.add(1, 99);               // insert at index 1 — O(n)
list.get(0);                   // access — O(1)
list.set(0, 42);               // update — O(1)
list.remove(0);                // remove by index — O(n)
list.size();                   // length — O(1)

// 2D Array
int[][] matrix = new int[3][4]; // 3 rows, 4 columns
int[][] grid = {{1,2},{3,4},{5,6}};
grid[1][0];                    // row 1, col 0 = 3
```

---

## 7. Common Array Patterns

### Pattern 1: Two Pointers (opposite ends)
```java
int left = 0, right = n - 1;
while (left < right) {
    // process arr[left] and arr[right]
    left++;
    right--;
}
```

### Pattern 2: Sliding Window
```java
int left = 0, sum = 0;
for (int right = 0; right < n; right++) {
    sum += arr[right];
    while (/* window too big */) {
        sum -= arr[left++];
    }
    // record answer
}
```

### Pattern 3: Prefix Sum
```java
int[] prefix = new int[n + 1];
for (int i = 0; i < n; i++)
    prefix[i + 1] = prefix[i] + arr[i];
// Sum from l to r = prefix[r+1] - prefix[l]
```

---

## 8. Dry Run: Find Max in Array

Array: [3, 7, 1, 9, 4]

```
max = arr[0] = 3
i=1: arr[1]=7 > 3  → max = 7
i=2: arr[2]=1 < 7  → max = 7
i=3: arr[3]=9 > 7  → max = 9
i=4: arr[4]=4 < 9  → max = 9
Result: 9
```

```java
int max = arr[0];
for (int i = 1; i < arr.length; i++)
    if (arr[i] > max) max = arr[i];
```

---

## 9. Edge Cases

- Empty array `[]` → check `arr.length == 0` first
- Single element `[5]`
- All negative `[-3, -1, -7]`
- All same `[4, 4, 4]`
- Already sorted / reverse sorted

---

## 10. Common Mistakes

- Off-by-one: `i < n` vs `i <= n`
- Modifying array while iterating
- Forgetting to initialize result variables
- Index out of bounds (always check arr.length)
- Confusing `arr.length` (no parentheses) with `list.size()`

---

## 11. When to Use Arrays

✅ Use when:
- Size is fixed or bounded
- Need O(1) random access by index
- Memory must be contiguous (cache performance)
- Sorting or binary searching

❌ Don't use when:
- Frequent insertions/deletions in the middle
- Unknown size with lots of growth
- Need key-based lookup

---

## 12. Real-World Use Cases

- Image pixels (2D array of RGB values)
- Audio samples (waveform as array of amplitudes)
- Spreadsheet rows (2D array)
- Buffer in network I/O

---

## 13. Interview Patterns on Arrays

| Problem Type | Pattern |
|-------------|---------|
| Find pair summing to K | Two Pointers / HashMap |
| Longest subarray with sum K | Prefix Sum + HashMap |
| Max/min in window | Sliding Window / Deque |
| Move zeros to end | Two Pointers |
| Rotate array by K | Reverse trick |
| Find missing number | XOR / Sum formula |

---

## 14. Practice Problems

**Easy:**
1. Find the maximum element in an array.
2. Reverse an array in-place.
3. Check if array is sorted.

**Medium:**
1. Find the maximum subarray sum (Kadane's Algorithm).
2. Move all zeros to the end while maintaining order.
3. Find all pairs with a given sum.
4. Rotate an array by K positions.
5. Find the "missing number" from 1 to n.

**Hard:**
1. Trapping Rain Water.
2. Maximum product subarray.
3. Find minimum in a rotated sorted array.

---

## 15. Mastery Checklist

- [ ] Can implement insert/delete/search from memory
- [ ] Knows difference between `int[]` and `ArrayList<Integer>`
- [ ] Can solve Two Sum in O(n)
- [ ] Understands prefix sum concept
- [ ] Can rotate an array in O(n) time O(1) space
- [ ] Solved 10+ LeetCode array problems

---

## 16. Mini Quiz

1. What is the time complexity of accessing the middle element of an array?
2. Why is inserting in the middle of an array O(n)?
3. How does Java's ArrayList handle resizing?
4. What trick can rotate an array in O(n) with O(1) extra space?

---

**Next →** `02_Strings.md`

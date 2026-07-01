# Section 3 — Searching Algorithms

---

## 1. What Problem Does This Solve?

Given a collection of elements, find the position or existence of a target value. The challenge is doing this efficiently when the collection is large.

- **Linear Search:** Works on any collection, unsorted or sorted — O(n).
- **Binary Search:** Works on sorted collections — O(log n). 60,000× faster than linear for n = 10^6.
- **Binary Search on Answer:** When the answer itself has a monotonic property, search the answer space instead of the array.

---

## 2. Beginner-Friendly Intuition

**Linear Search:** Check each book on the shelf one by one until you find the right title. Simple, always works, but slow.

**Binary Search:** In a sorted shelf (A-Z), open to the middle. If the book you're looking for comes after the middle, throw away the left half. If before, throw away the right half. Each step cuts your search space in half.

**Binary Search on Answer:** Instead of searching for a value in an array, you're searching for the answer itself. "Can we do this in X moves?" If X=10 works, X=11 also works (monotonic). So binary search for the minimum valid X.

---

## 3. Real-World Analogy

**Binary Search — Dictionary lookup:** A physical dictionary has words sorted alphabetically. You open to the middle, check if your word is before or after, and discard half. In 20 comparisons you can find any word in a 1,000,000-word dictionary (log2(10^6) ≈ 20).

**Binary Search on Answer — Amazon delivery:** "Can you ship all packages within K days given a daily weight limit?" Increasing daily limit always makes it easier. So binary search on the limit: find minimum limit that allows delivery in K days.

---

## 4. Core Concept

### Classic Binary Search Template
```
left = 0, right = n - 1
while left <= right:
    mid = left + (right - left) / 2   ← avoid overflow (not (left+right)/2)
    if arr[mid] == target: return mid
    if arr[mid] < target: left = mid + 1
    else: right = mid - 1
return -1  (not found)
```

### Binary Search on Answer Template
```
left = min_possible_answer
right = max_possible_answer
while left < right:
    mid = left + (right - left) / 2
    if canAchieve(mid):
        right = mid       ← try smaller (find minimum)
    else:
        left = mid + 1
return left
```

### Key Insight: Left-Biased vs Right-Biased Mid
- For **find minimum**: `mid = left + (right - left) / 2` (floor)
- For **find maximum**: `mid = left + (right - left + 1) / 2` (ceiling)

---

## 5. Pattern Recognition Signals

```
"Find in sorted array" → Classic Binary Search
"Search in rotated sorted array" → Modified Binary Search
"First/last occurrence of target" → Binary Search (left-biased / right-biased)
"Find peak element" → Binary Search on condition
"Minimum/maximum satisfying some condition" → Binary Search on Answer
"Kth smallest in matrix/sorted structure" → Binary Search on Answer
"Square root / nth root" → Binary Search on Answer
"Minimize maximum / maximize minimum" → Binary Search on Answer
```

---

## 6. Step-by-Step Algorithm

### First Occurrence of Target
```
left = 0, right = n-1, result = -1
while left <= right:
    mid = left + (right - left) / 2
    if arr[mid] == target:
        result = mid
        right = mid - 1    ← keep searching LEFT for earlier occurrence
    elif arr[mid] < target:
        left = mid + 1
    else:
        right = mid - 1
return result
```

### Binary Search in Rotated Sorted Array
```
// One half is always sorted. Check which half, then decide direction.
while left <= right:
    mid = left + (right - left) / 2
    if arr[mid] == target: return mid
    if arr[left] <= arr[mid]:  // left half is sorted
        if arr[left] <= target < arr[mid]: right = mid - 1
        else: left = mid + 1
    else:                      // right half is sorted
        if arr[mid] < target <= arr[right]: left = mid + 1
        else: right = mid - 1
```

---

## 7. Dry Run with Example

### Classic Binary Search: arr = [1, 3, 5, 7, 9, 11], target = 7
```
left=0, right=5
Step 1: mid=2, arr[2]=5 < 7 → left=3
Step 2: mid=4, arr[4]=9 > 7 → right=3
Step 3: mid=3, arr[3]=7 == 7 → return 3 ✓
```

### Binary Search on Answer: Koko Eating Bananas
```
piles = [3, 6, 7, 11], h = 8 hours
Answer range: [1, 11] (1 banana/hr minimum, max pile maximum)

Can she finish at speed=6?
  pile[0]=3: ceil(3/6)=1 hr
  pile[1]=6: ceil(6/6)=1 hr
  pile[2]=7: ceil(7/6)=2 hr
  pile[3]=11: ceil(11/6)=2 hr
  Total = 6 ≤ 8 → YES ✓

Binary search in [1,11]:
  mid=6 → 6 hrs ≤ 8 → right=6
  mid=3 → ceil(3/3)+ceil(6/3)+ceil(7/3)+ceil(11/3)=1+2+3+4=10 > 8 → left=4
  mid=5 → ceil(3/5)+ceil(6/5)+ceil(7/5)+ceil(11/5)=1+2+2+3=8 ≤ 8 → right=5
  mid=4 → 1+2+2+3=8... wait: ceil(3/4)=1,ceil(6/4)=2,ceil(7/4)=2,ceil(11/4)=3 → 8 ≤ 8 → right=4
  left=right=4 → answer=4 ✓
```

---

## 8. Code Implementation

```java
public class SearchingAlgorithms {

    // ── Linear Search ─────────────────────────────────────────────────────
    static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }
        return -1; // not found
    }

    // ── Classic Binary Search ─────────────────────────────────────────────
    static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2; // avoid overflow
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    // ── First Occurrence ──────────────────────────────────────────────────
    static int firstOccurrence(int[] arr, int target) {
        int left = 0, right = arr.length - 1, result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                result = mid;
                right = mid - 1; // search left for earlier occurrence
            } else if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return result;
    }

    // ── Last Occurrence ───────────────────────────────────────────────────
    static int lastOccurrence(int[] arr, int target) {
        int left = 0, right = arr.length - 1, result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                result = mid;
                left = mid + 1; // search right for later occurrence
            } else if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return result;
    }

    // ── Search in Rotated Sorted Array ────────────────────────────────────
    static int searchRotated(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return mid;
            if (arr[left] <= arr[mid]) { // left half is sorted
                if (arr[left] <= target && target < arr[mid]) right = mid - 1;
                else left = mid + 1;
            } else { // right half is sorted
                if (arr[mid] < target && target <= arr[right]) left = mid + 1;
                else right = mid - 1;
            }
        }
        return -1;
    }

    // ── Binary Search on Answer: Koko Eating Bananas ─────────────────────
    static int minEatingSpeed(int[] piles, int h) {
        int left = 1, right = 0;
        for (int p : piles) right = Math.max(right, p); // max pile

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canFinish(piles, h, mid)) right = mid; // feasible, try smaller
            else left = mid + 1;
        }
        return left;
    }

    static boolean canFinish(int[] piles, int h, int speed) {
        long hours = 0;
        for (int p : piles) hours += (p + speed - 1) / speed; // ceil division
        return hours <= h;
    }

    // ── Find Peak Element ─────────────────────────────────────────────────
    static int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid + 1]) right = mid; // peak is on left
            else left = mid + 1;                        // peak is on right
        }
        return left;
    }
}
```

---

## 9. Time Complexity

| Algorithm | Time | Notes |
|-----------|------|-------|
| Linear Search | O(n) | Works on unsorted arrays |
| Binary Search (sorted) | O(log n) | Array must be sorted |
| First/Last Occurrence | O(log n) | Same binary search with extra tracking |
| Binary Search Rotated | O(log n) | One half is always sorted |
| Binary Search on Answer | O(log(range) × f(n)) | f(n) = cost of feasibility check |

---

## 10. Space Complexity

| Algorithm | Space |
|-----------|-------|
| Linear Search | O(1) |
| Binary Search (iterative) | O(1) |
| Binary Search (recursive) | O(log n) stack |

---

## 11. Edge Cases

| Scenario | Handling |
|----------|---------|
| Empty array | Check `n == 0` before searching; return -1 |
| Single element | Binary search handles: left=right=0, one comparison |
| Target not in array | Return -1 |
| All same elements | First/last occurrence must handle duplicates |
| Rotated at position 0 or n-1 | No actual rotation — handled by standard check |
| Binary Search on Answer — overflow | Use `long` for feasibility check sums |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Overflow in mid calculation
int mid = (left + right) / 2; // WRONG: left+right overflows if both ~10^9
int mid = left + (right - left) / 2; // CORRECT

// MISTAKE 2: Wrong termination condition
while (left < right) { // OK for "find minimum" pattern
    int mid = left + (right - left) / 2;
    if (condition(mid)) right = mid;
    else left = mid + 1;
}
// But for classic search:
while (left <= right) // CORRECT for classic (target may be at left==right)

// MISTAKE 3: Infinite loop in "find minimum" pattern with ceiling mid
while (left < right) {
    int mid = left + (right - left) / 2; // floor mid → safe for right=mid
    int mid = left + (right - left + 1) / 2; // ceiling mid → needed for left=mid
    // Use ceiling mid when your update is: left = mid (not left = mid+1)
}

// MISTAKE 4: Not handling duplicates in rotated array search
// LeetCode #81 has duplicates: when arr[left]==arr[mid]==arr[right], shrink both sides:
if (arr[left] == arr[mid] && arr[mid] == arr[right]) { left++; right--; continue; }
```

---

## 13. Interview-Level Explanation

**Q: "Why is `left + (right - left) / 2` safer than `(left + right) / 2`?"**

> "If left and right are both large integers (near Integer.MAX_VALUE = 2^31-1), their sum overflows a 32-bit int and becomes negative. Adding (right - left) to left avoids this because (right - left) is at most the array length, which is bounded."

**Q: "How do you approach Binary Search on Answer problems?"**

> "The key insight is identifying that the answer space is monotonic: if answer X is feasible, then X+1 is also feasible (or vice versa). Once you identify this monotonic property, define a `canAchieve(X)` function and binary search on X directly. The hard part is recognizing the monotonic property and writing the feasibility check correctly. The search itself is mechanical."

---

## 14. Real-World Use Cases

| Application | Algorithm |
|------------|-----------|
| Database index lookup | Binary search on B-tree index |
| Git bisect | Binary search for commit introducing a bug |
| Load balancing | Binary search on answer for optimal partition |
| Delivery scheduling | Binary search on answer for minimum truck capacity |
| Computer vision | Binary search on threshold (e.g., image binarization) |
| Spell checkers | Binary search in sorted word list |

---

## 15. Variations

| Variation | Approach |
|-----------|---------|
| Search in sorted matrix | Binary search treating matrix as flat array |
| Kth smallest in sorted matrix | Binary search on answer |
| Minimum in rotated array | Binary search without target — track minimum |
| Count of range | Upper bound − lower bound using binary search |
| Ternary Search | Find peak of unimodal function (rare in interviews) |

---

## 16. Practice Problems

### Easy — Foundation
1. **Binary Search** (LeetCode #704)
   - *Task:* Classic binary search in sorted array.
   - *Hint:* Direct application. Practice both iterative and recursive.

2. **First Bad Version** (LeetCode #278)
   - *Task:* Find first version where isBadVersion() returns true.
   - *Hint:* Binary search for first occurrence of "bad".

3. **Sqrt(x)** (LeetCode #69)
   - *Task:* Find integer square root.
   - *Hint:* Binary search in [0, x]. Return right at end.

### Medium — Core
1. **Search in Rotated Sorted Array** (LeetCode #33)
   - *Task:* Find target in rotated array. O(log n).
   - *Hint:* One half is always sorted — check which.

2. **Find First and Last Position** (LeetCode #34)
   - *Task:* First and last occurrence of target.
   - *Hint:* Two binary searches — left-biased and right-biased.

3. **Koko Eating Bananas** (LeetCode #875)
   - *Task:* Minimum speed to eat all bananas in h hours.
   - *Hint:* Binary search on answer in [1, max(piles)].

4. **Minimum Number of Days to Make m Bouquets** (LeetCode #1482)
   - *Task:* Minimum days so m bouquets of k adjacent bloomed flowers exist.
   - *Hint:* Binary search on answer (days). Feasibility check in O(n).

5. **Find Peak Element** (LeetCode #162)
   - *Task:* Find any peak (element greater than neighbors).
   - *Hint:* If nums[mid] < nums[mid+1], peak is to the right.

### Hard — Advanced
1. **Median of Two Sorted Arrays** (LeetCode #4)
   - *Task:* O(log(min(m,n))) median of two sorted arrays.
   - *Hint:* Binary search on partition of smaller array.

2. **Split Array Largest Sum** (LeetCode #410)
   - *Task:* Minimize the largest sum among m subarrays.
   - *Hint:* Binary search on answer (the max sum limit).

3. **Find K-th Smallest Pair Distance** (LeetCode #719)
   - *Task:* Kth smallest absolute difference between pairs.
   - *Hint:* Binary search on answer (distance) + sliding window for count.

---

## 17. How to Know You Have Mastered Searching Algorithms

You have mastered this topic when you can:
- [ ] Implement iterative binary search without overflow bugs
- [ ] Find first and last occurrence with correct left/right biased search
- [ ] Handle binary search in rotated sorted arrays
- [ ] Identify "Binary Search on Answer" from problem description
- [ ] Write a correct feasibility check for binary search on answer problems
- [ ] Know when to use `left < right` vs `left <= right`
- [ ] Understand when ceiling mid vs floor mid prevents infinite loops
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. Binary search: arr = [1,2,3,4,5,6,7,8,9,10], target = 6. How many iterations?

2. What's wrong with `int mid = (left + right) / 2`?

3. In "find minimum satisfying condition" pattern, why do we set `right = mid` (not `right = mid - 1`)?

4. For rotated array [4,5,6,7,0,1,2], target=0: which half do you search at mid index 3 (value 7)?

5. What is the time complexity of Binary Search on Answer for Koko's problem with n piles and max pile M?

6. When searching for first occurrence, after finding `arr[mid] == target`, do you set `left=mid+1` or `right=mid-1`?

7. For `findPeakElement`, if `nums[mid] > nums[mid+1]`, where is the peak?

8. Binary search requires sorted array. What can you do if the array is nearly sorted (one rotation)?

> **Answers:**
> 1. arr[5]=6. Iterations: mid=4(5<6,left=5) → mid=7(8>6,right=6) → mid=5(6==6). 3 iterations.
> 2. left+right may overflow a 32-bit int if both are close to Integer.MAX_VALUE. Use `left + (right-left)/2`.
> 3. Because `mid` itself is a valid candidate — we don't want to exclude it. We shrink the right boundary to mid, not mid-1, so the valid answer is never discarded.
> 4. Value at mid=3 is 7. Left half [4,5,6,7] is sorted. Target 0 is NOT in [4,7], so search the right half (left=mid+1).
> 5. O(n × log M): O(log M) binary search iterations, each feasibility check is O(n).
> 6. Set `right = mid - 1` to search further left for an earlier occurrence.
> 7. The peak is at mid or to the left (since nums[mid] > nums[mid+1] means there's a local decrease going right). So `right = mid`.
> 8. Use the modified binary search for rotated arrays — at each step, one half must be sorted, and you can determine which half contains the target.

---

**Next →** `../04_Sorting/01_Sorting_Algorithms.md`

### When to use
- Unsorted array
- Small input size (n ≤ 1000)
- One-time search

```java
int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++)
        if (arr[i] == target) return i;
    return -1;  // not found
}
```

**Complexity:** O(n) time, O(1) space

---

## Part 2: Binary Search — Deep Dive

### What Problem Does This Solve?

Find an element in a **sorted** collection in O(log n) instead of O(n).

### Beginner-Friendly Intuition

You're guessing a number from 1–100:
- Guess 50. "Higher." → range is 51–100.
- Guess 75. "Lower."  → range is 51–74.
- Guess 62. "Correct!"

Each guess eliminates **half** the remaining possibilities. After 7 guesses, you narrow 100 to 1.

### Real-World Analogy

Opening a dictionary to the middle and deciding "go left or right."

### Why Binary Search Works

**Precondition:** The array is **sorted** (monotonic property).  
**Key Insight:** If `arr[mid] < target`, every element left of mid is also too small → safely discard left half.

### Core Template

```java
int binarySearch(int[] arr, int target) {
    int lo = 0, hi = arr.length - 1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;  // NEVER use (lo + hi) / 2 — overflow risk!
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) lo = mid + 1;
        else hi = mid - 1;
    }
    return -1;  // not found
}
```

**Why `lo + (hi - lo) / 2` and not `(lo + hi) / 2`?**  
If lo=2B and hi=2B, their sum overflows int. Safe form avoids this.

---

### Dry Run: [1, 3, 5, 7, 9, 11], target = 7

```
lo=0, hi=5, mid=2 → arr[2]=5 < 7 → lo=3
lo=3, hi=5, mid=4 → arr[4]=9 > 7 → hi=3
lo=3, hi=3, mid=3 → arr[3]=7 == 7 → return 3  ✓
```

---

### `lo <= hi` vs `lo < hi`

| Condition | Use when |
|-----------|---------|
| `lo <= hi` | Finding exact match |
| `lo < hi` | Finding boundary / leftmost / rightmost |

---

### Find First Occurrence (Left Boundary)

```java
int firstOccurrence(int[] arr, int target) {
    int lo = 0, hi = arr.length - 1, result = -1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (arr[mid] == target) {
            result = mid;   // record it, keep searching left
            hi = mid - 1;
        } else if (arr[mid] < target) lo = mid + 1;
        else hi = mid - 1;
    }
    return result;
}
```

### Find Last Occurrence (Right Boundary)

```java
int lastOccurrence(int[] arr, int target) {
    int lo = 0, hi = arr.length - 1, result = -1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (arr[mid] == target) {
            result = mid;   // record it, keep searching right
            lo = mid + 1;
        } else if (arr[mid] < target) lo = mid + 1;
        else hi = mid - 1;
    }
    return result;
}
```

---

### Binary Search on Answer (Most Important Pattern!)

**Idea:** The answer exists in a range [lo, hi]. You can check "is answer X feasible?" in O(n). Binary search over answer space.

**Example:** "Minimum number of days to make m bouquets"

```java
// Check: can we make m bouquets in 'days' days?
boolean canMake(int[] bloomDay, int m, int k, int days) {
    int bouquets = 0, consecutive = 0;
    for (int d : bloomDay) {
        if (d <= days) consecutive++;
        else consecutive = 0;
        if (consecutive == k) { bouquets++; consecutive = 0; }
    }
    return bouquets >= m;
}

int minDays(int[] bloomDay, int m, int k) {
    int lo = 1, hi = (int)1e9, result = -1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (canMake(bloomDay, m, k, mid)) {
            result = mid;
            hi = mid - 1;  // try fewer days
        } else {
            lo = mid + 1;
        }
    }
    return result;
}
```

---

### Search in Rotated Sorted Array

```
Original: [1, 2, 3, 4, 5, 6, 7]
Rotated:  [4, 5, 6, 7, 1, 2, 3]  (rotated at index 3)
```

```java
int searchRotated(int[] nums, int target) {
    int lo = 0, hi = nums.length - 1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] == target) return mid;
        // Left half is sorted
        if (nums[lo] <= nums[mid]) {
            if (nums[lo] <= target && target < nums[mid]) hi = mid - 1;
            else lo = mid + 1;
        }
        // Right half is sorted
        else {
            if (nums[mid] < target && target <= nums[hi]) lo = mid + 1;
            else hi = mid - 1;
        }
    }
    return -1;
}
```

---

### Search in 2D Matrix

```
Matrix (sorted row-by-row, each row > last element of prev row):
[[ 1,  3,  5,  7],
 [10, 11, 16, 20],
 [23, 30, 34, 60]]
```

**Trick:** Treat as 1D sorted array of n×m elements.
```java
boolean searchMatrix(int[][] matrix, int target) {
    int m = matrix.length, n = matrix[0].length;
    int lo = 0, hi = m * n - 1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        int val = matrix[mid / n][mid % n];  // convert 1D index to 2D
        if (val == target) return true;
        else if (val < target) lo = mid + 1;
        else hi = mid - 1;
    }
    return false;
}
```

---

### Binary Search Bugs Checklist

- [ ] Use `lo + (hi - lo) / 2` not `(lo + hi) / 2`
- [ ] `lo <= hi` for exact search, `lo < hi` for boundary
- [ ] Update `lo = mid + 1` and `hi = mid - 1` (not `mid`)
- [ ] If using `lo < hi`, check leftover element at end
- [ ] Search space should monotonically satisfy the condition

---

## Part 3: Other Search Algorithms

### Lower Bound (first position ≥ target)
```java
int lowerBound(int[] arr, int target) {
    int lo = 0, hi = arr.length;
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (arr[mid] < target) lo = mid + 1;
        else hi = mid;
    }
    return lo;  // first index where arr[i] >= target
}
```

### Upper Bound (first position > target)
```java
int upperBound(int[] arr, int target) {
    int lo = 0, hi = arr.length;
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (arr[mid] <= target) lo = mid + 1;
        else hi = mid;
    }
    return lo;  // first index where arr[i] > target
}
```

### Ternary Search (for unimodal functions)
Split into thirds. Compare f(m1) vs f(m2). O(log₃ n).  
Used for: finding maximum of a unimodal function.

### Exponential Search
Start at index 1, double until exceeding target range, then binary search.  
Used for: unbounded arrays, when element is near beginning.

---

## Practice Problems

**Easy:**
1. Binary search (basic).
2. First and last position of element in sorted array.
3. Search insert position.

**Medium:**
1. Search in Rotated Sorted Array.
2. Find Minimum in Rotated Sorted Array.
3. Search in a 2D Matrix.
4. Find Peak Element.
5. Koko Eating Bananas (Binary Search on Answer).

**Hard:**
1. Median of Two Sorted Arrays.
2. Split Array Largest Sum.
3. Aggressive Cows (classic BS on answer).

---

## Mini Quiz

1. Why can't you use binary search on an unsorted array?
2. What is the maximum number of comparisons for binary search on n=1,000,000?
3. What is "binary search on answer"?
4. What bug occurs with `(lo + hi) / 2` when lo and hi are large?

---

**Next →** `../04_Sorting/01_Sorting_Algorithms.md`

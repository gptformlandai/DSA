# Section 7 — Prefix Sum & Difference Array

---

## 1. What Problem Does This Solve?

When you need to answer many **range sum queries** — "What is the sum from index l to r?" — recomputing from scratch each time is O(n) per query. With Q queries that's O(n×Q). Prefix Sum precomputes a lookup table in O(n), then answers every query in O(1).

Beyond simple sums, the prefix-sum idea extends to:
- Finding subarrays with exact sum k (even with negatives)
- Range update + range query (Difference Array)
- 2D range sums (Prefix Sum Matrix)

---

## 2. Beginner-Friendly Intuition

Imagine counting the total score of a bowling game at any point. Instead of re-adding all previous frames every time, you keep a running total. To find the score from frame 4 to frame 8, you take `totalUpToFrame8 - totalUpToFrame3`. You never re-add individual frames.

That running total IS the prefix sum array.

---

## 3. Real-World Analogy

**Odometer on a car:** The odometer shows total miles driven from the start. To know how far you drove on Tuesday (day 3) to Thursday (day 5), you take `odometer[Thursday] - odometer[Monday]`. You don't re-count every mile driven on those days.

**Bank statement:** Account balance at any point = starting balance + sum of all transactions. Balance from date A to date B = `balance[B] - balance[A-1]`.

---

## 4. Core Concept

### Prefix Sum Array

Given array `arr[0..n-1]`, build `prefix[0..n]` where:
- `prefix[0] = 0` (empty prefix)
- `prefix[i] = arr[0] + arr[1] + ... + arr[i-1]`

**Range sum query:** `sum(l, r) = prefix[r+1] - prefix[l]`

### Prefix Sum + HashMap

For subarray sum problems (especially with negatives), pair with HashMap:
- Store `(prefix_sum → first_index)` or `(prefix_sum → count)`
- At index i: if `prefix[i] - k` was seen before, a subarray with sum k exists

### Difference Array

For range-update problems: "Add delta to all elements from l to r."
- `diff[l] += delta`
- `diff[r+1] -= delta`
- Reconstruct with prefix sum: one O(n) scan applies all updates

---

## 5. Pattern Recognition Signals

Use Prefix Sum when:
```
"Range sum query on static array" (multiple queries)
"Subarray sum equals k" (especially with negatives)
"Count subarrays with sum divisible by k"
"Find pivot index where left sum == right sum"
"Running total or cumulative sum"
"Number of subarrays with sum in range [lo, hi]"
"Add val to all elements in range [l, r]" (Difference Array)
"Multiple range updates then read final array"
```

**Key signal:** If the problem involves sums over contiguous ranges AND there are negatives or you can't use a sliding window, prefix sum + HashMap is the go-to.

---

## 6. Step-by-Step Algorithm

### Build Prefix Sum
```
prefix[0] = 0
For i from 0 to n-1:
    prefix[i+1] = prefix[i] + arr[i]

Range sum [l, r] = prefix[r+1] - prefix[l]
```

### Subarray Sum Equals K (HashMap approach)
```
Step 1: Initialize map = {0: 1} (empty prefix seen once), count = 0, prefixSum = 0
Step 2: For each element x in arr:
    prefixSum += x
    If (prefixSum - k) exists in map:
        count += map[prefixSum - k]
    map[prefixSum]++
Step 3: Return count
```

### Difference Array Range Update
```
Step 1: diff = int[n+1]  (all zeros)
Step 2: For each update (l, r, delta):
    diff[l] += delta
    diff[r+1] -= delta
Step 3: Reconstruct: arr[i] = arr[i-1] + diff[i] (prefix sum of diff)
```

---

## 7. Dry Run with Example

### Example 1: Range Sum Query

**Input:** `arr = [3, 1, 4, 1, 5, 9, 2, 6]`

```
Build prefix:
  index:  0  1  2  3  4   5   6   7   8
  prefix: 0  3  4  8  9  14  23  25  31

Query sum(2, 5) = prefix[6] - prefix[2] = 23 - 4 = 19
Verify: arr[2]+arr[3]+arr[4]+arr[5] = 4+1+5+9 = 19 ✓

Query sum(0, 3) = prefix[4] - prefix[0] = 9 - 0 = 9
Verify: 3+1+4+1 = 9 ✓
```

### Example 2: Subarray Sum Equals K

**Input:** `arr = [3, 4, 7, 2, -3, 1, 4, 2]`, `k = 7`

```
map = {0:1}, prefixSum = 0, count = 0

i=0 (3):  prefixSum=3,  need 3-7=-4   → not in map. map={0:1, 3:1}
i=1 (4):  prefixSum=7,  need 7-7=0    → in map (count 1)! count=1. map={0:1,3:1,7:1}
i=2 (7):  prefixSum=14, need 14-7=7   → in map (count 1)! count=2. map={...,14:1}
i=3 (2):  prefixSum=16, need 16-7=9   → not in map. map={...,16:1}
i=4 (-3): prefixSum=13, need 13-7=6   → not in map. map={...,13:1}
i=5 (1):  prefixSum=14, need 14-7=7   → in map! count=3. map={...,14:2}
i=6 (4):  prefixSum=18, need 18-7=11  → not in map. map={...,18:1}
i=7 (2):  prefixSum=20, need 20-7=13  → in map! count=4. map={...,20:1}

Answer: 4
```

### Example 3: Difference Array

**Input:** `arr = [1,2,3,4,5]`, updates: `(1,3,+10)`, `(2,4,+5)`

```
diff = [0, 0, 0, 0, 0, 0]  (size n+1)

Update (1,3,+10): diff[1]+=10, diff[4]-=10 → diff=[0,10,0,0,-10,0]
Update (2,4,+5):  diff[2]+=5,  diff[5]-=5  → diff=[0,10,5,0,-10,-5]

Reconstruct (prefix sum of diff):
running=0
i=0: running+=0=0, arr[0]=1+0=1
i=1: running+=10=10, arr[1]=2+10=12
i=2: running+=5=15, arr[2]=3+15=18
i=3: running+=0=15, arr[3]=4+15=19
i=4: running+=-10=5, arr[4]=5+5=10

Result: [1, 12, 18, 19, 10]
Verify: update (1,3,+10): positions 1,2,3 → 2+10=12 ✓, 3+10=13... wait
Actually 3+15=18 because both updates apply to index 2: +10 from update1, +5 from update2 → 3+15=18 ✓
```

---

## 8. Code Implementation

### Basic Prefix Sum + Range Query

```java
class NumArray {
    private int[] prefix;

    NumArray(int[] nums) {
        prefix = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++)
            prefix[i + 1] = prefix[i] + nums[i];
    }

    // sum of nums[left..right] inclusive
    int sumRange(int left, int right) {
        return prefix[right + 1] - prefix[left];
    }
}
```

### Subarray Sum Equals K

```java
int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> count = new HashMap<>();
    count.put(0, 1); // empty prefix has sum 0
    int prefixSum = 0, result = 0;
    for (int x : nums) {
        prefixSum += x;
        // how many previous prefixes equal prefixSum - k?
        result += count.getOrDefault(prefixSum - k, 0);
        count.merge(prefixSum, 1, Integer::sum);
    }
    return result;
}
```

### Find Pivot Index

```java
int pivotIndex(int[] nums) {
    int total = 0;
    for (int x : nums) total += x;
    int leftSum = 0;
    for (int i = 0; i < nums.length; i++) {
        // right sum = total - leftSum - nums[i]
        if (leftSum == total - leftSum - nums[i]) return i;
        leftSum += nums[i];
    }
    return -1;
}
```

### Subarray Sums Divisible by K

```java
int subarraysDivByK(int[] nums, int k) {
    Map<Integer, Integer> remainderCount = new HashMap<>();
    remainderCount.put(0, 1);
    int prefixSum = 0, result = 0;
    for (int x : nums) {
        prefixSum += x;
        int rem = ((prefixSum % k) + k) % k; // handle negatives
        result += remainderCount.getOrDefault(rem, 0);
        remainderCount.merge(rem, 1, Integer::sum);
    }
    return result;
}
```

### Difference Array — Range Update

```java
int[] applyRangeUpdates(int[] arr, int[][] updates) {
    int n = arr.length;
    int[] diff = new int[n + 1];
    for (int[] u : updates) {
        diff[u[0]] += u[2];      // start of range
        diff[u[1] + 1] -= u[2]; // one past end of range
    }
    int running = 0;
    for (int i = 0; i < n; i++) {
        running += diff[i];
        arr[i] += running;
    }
    return arr;
}
```

### 2D Prefix Sum

```java
class NumMatrix {
    private int[][] prefix;

    NumMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        prefix = new int[m + 1][n + 1];
        for (int r = 1; r <= m; r++)
            for (int c = 1; c <= n; c++)
                prefix[r][c] = matrix[r-1][c-1]
                    + prefix[r-1][c] + prefix[r][c-1] - prefix[r-1][c-1];
    }

    // sum of region (r1,c1) to (r2,c2) inclusive
    int sumRegion(int r1, int c1, int r2, int c2) {
        return prefix[r2+1][c2+1] - prefix[r1][c2+1]
             - prefix[r2+1][c1] + prefix[r1][c1];
    }
}
```

---

## 9. Time Complexity

| Operation | Brute Force | Prefix Sum |
|-----------|------------|-----------|
| Build (one-time) | — | O(n) |
| Single range query | O(n) | O(1) |
| Q range queries | O(n×Q) | O(n + Q) |
| Subarray sum equals k | O(n²) | O(n) |
| Range update (Difference Array) | O(n×U) | O(n + U) |
| 2D range query | O(m×n) | O(1) after O(m×n) build |

---

## 10. Space Complexity

| Approach | Space | Reason |
|----------|-------|--------|
| Prefix Sum array | O(n) | Array of same size |
| Prefix Sum + HashMap | O(n) | Map stores at most n unique prefix sums |
| Difference Array | O(n) | One extra array |
| 2D Prefix Sum | O(m×n) | Grid of same size |

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| Empty array | Return 0 for sum queries |
| Negative numbers | Prefix sum works fine; modulo needs `((rem % k) + k) % k` |
| k = 0 in sum divisible by k | Division by zero — check constraints |
| Single element | `prefix = [0, arr[0]]`; handles correctly |
| All elements negative | Works normally — prefix values decrease |
| Query l = r | Returns single element |
| 2D matrix with single row/col | Degenerates to 1D prefix sum |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Off-by-one in prefix array indexing
// prefix[i] = sum of first i elements (0-indexed: arr[0..i-1])
// sum(l, r) = prefix[r+1] - prefix[l]  ← NOT prefix[r] - prefix[l-1]

// MISTAKE 2: Forgetting to seed the HashMap with {0: 1}
// If you don't add 0:1, you miss subarrays starting at index 0
Map<Integer, Integer> count = new HashMap<>();
// WRONG: map is empty
count.put(0, 1); // CORRECT: empty prefix sum is 0, seen once

// MISTAKE 3: Negative modulo in Java
int rem = prefixSum % k; // WRONG: can be negative in Java (-3 % 5 = -3)
int rem = ((prefixSum % k) + k) % k; // CORRECT

// MISTAKE 4: Wrong difference array boundary
diff[u[1]] -= u[2];       // WRONG: affects element at u[1]
diff[u[1] + 1] -= u[2];  // CORRECT: ends AFTER u[1]

// MISTAKE 5: 2D prefix sum inclusion-exclusion error
// Wrong:
prefix[r][c] = matrix[r-1][c-1] + prefix[r-1][c] + prefix[r][c-1];
// CORRECT (subtract double-counted top-left rectangle):
prefix[r][c] = matrix[r-1][c-1] + prefix[r-1][c] + prefix[r][c-1] - prefix[r-1][c-1];
```

---

## 13. Interview-Level Explanation

**Q: "Why do you initialize the HashMap with `{0: 1}` in Subarray Sum Equals K?"**

> "The prefix sum represents the cumulative sum from the start. When we check `prefixSum - k` in the map, we're asking: 'Has there been a prefix that, when subtracted, gives us exactly k?' The value `0` represents the empty prefix — meaning the subarray starts from index 0. Without initializing `{0:1}`, we'd miss valid subarrays that start at the beginning of the array."

**Q: "When would you use Difference Array vs Prefix Sum?"**

> "Prefix Sum is for range queries on a static array. Difference Array is for range updates on a dynamic array that you'll read at the end. If you have many updates like 'add 5 to all elements in [2,7],' doing each update naively is O(n) per update. With Difference Array, each update is O(1) and you reconstruct the final array in one O(n) prefix-sum pass."

---

## 14. Real-World Use Cases

| Application | Prefix Sum Usage |
|------------|----------------|
| **Database analytics** | Sum/avg queries over time ranges in data warehouses |
| **Game development** | Cumulative score tables, weighted random selection |
| **Image processing** | Summed area tables for box blur |
| **Financial systems** | Running account balances, range P&L calculation |
| **Network monitoring** | Bandwidth usage over time windows |
| **Competitive gaming** | Leaderboard range queries |
| **Genomics** | GC content in DNA regions |

---

## 15. Variations of This Pattern

| Variation | Key Idea | Example |
|-----------|---------|---------|
| 1D range sum | Basic prefix sum | Range Sum Query |
| Subarray sum = k | Prefix + HashMap | Subarray Sum Equals K |
| Sum divisible by k | Prefix + modulo + HashMap | Subarrays Divisible by K |
| Pivot index | Prefix vs suffix sum | Find Pivot Index |
| Difference Array | Range update O(1) | Range Addition |
| 2D prefix sum | Grid rectangle sum | Range Sum Query 2D |
| Product prefix | Use products not sums | Product Except Self |
| XOR prefix | Replace sum with XOR | Find Subarray XOR = k |
| Binary prefix sum | Fenwick Tree (BIT) | Dynamic range queries |

---

## 16. Practice Problems

### Easy — Build the Foundation
1. **Range Sum Query - Immutable** (LeetCode #303)
   - *Task:* Build a structure for O(1) range sum queries.
   - *Hint:* Direct application of prefix sum constructor + query formula.

2. **Find Pivot Index** (LeetCode #724)
   - *Task:* Find index where left sum equals right sum.
   - *Hint:* Total sum - leftSum - arr[i] = leftSum → solve for condition.

3. **Running Sum of 1D Array** (LeetCode #1480)
   - *Task:* Return running sum of array (prefix sum IS the answer).
   - *Hint:* prefix[i] = prefix[i-1] + arr[i].

### Medium — HashMap + Prefix Sum
1. **Subarray Sum Equals K** (LeetCode #560)
   - *Task:* Count subarrays summing to exactly k.
   - *Hint:* prefixSum - k in map. Initialize {0:1}.

2. **Continuous Subarray Sum** (LeetCode #523)
   - *Task:* Subarray of length ≥ 2 with sum multiple of k.
   - *Hint:* prefix modulo k. Same remainder means the gap is divisible by k.

3. **Subarray Sums Divisible by K** (LeetCode #974)
   - *Task:* Count subarrays with sum divisible by k.
   - *Hint:* Frequency map of remainders. Pairs of same remainder.

4. **Range Addition** (LeetCode #370)
   - *Task:* Apply range updates, return final array.
   - *Hint:* Difference array. One O(n) reconstruction pass.

5. **Product of Array Except Self** (LeetCode #238)
   - *Task:* Output[i] = product of all elements except index i.
   - *Hint:* Prefix products from left × suffix products from right.

### Hard — Advanced Variants
1. **Count of Range Sum** (LeetCode #327)
   - *Task:* Count subarrays with sum in [lower, upper].
   - *Hint:* Prefix sum + merge sort (or BIT/segment tree).

2. **Maximum Sum of Two Non-Overlapping Subarrays** (LeetCode #1031)
   - *Task:* Max sum of two non-overlapping subarrays of lengths L and M.
   - *Hint:* Prefix sums + sweeping max from left and right.

3. **Range Sum Query 2D - Immutable** (LeetCode #304)
   - *Task:* Range sum queries on 2D matrix.
   - *Hint:* 2D prefix sum with inclusion-exclusion.

---

## 17. How to Know You Have Mastered Prefix Sum

You have mastered this topic when you can:
- [ ] Build a prefix sum array and answer range queries in O(1) without looking it up
- [ ] Implement Subarray Sum Equals K from memory (including the `{0:1}` initialization)
- [ ] Explain why the HashMap approach works for negative numbers when sliding window doesn't
- [ ] Implement the modulo trick for "divisible by k" problems with negative inputs
- [ ] Write a Difference Array for batch range updates correctly
- [ ] Build the 2D prefix sum and apply the inclusion-exclusion formula correctly
- [ ] Solve Product of Array Except Self without division
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. Given `arr = [5, -1, 3, 2, -4]`, what is `prefix[3]`? What is `sum(1, 3)`?

2. In "Subarray Sum Equals K," why can't you use a sliding window instead?

3. If you see prefix sums `[0, 3, 7, 10, 10, 12]` and k=3, at index 5 you look up `12-3=9`. Is 9 in the prefix array so far? What does this mean?

4. In "Subarray Sums Divisible by K" with k=5, two indices have prefix sums 13 and 23. Is the subarray between them divisible by 5?

5. For range update `(2, 5, +7)` on a size-8 array, what values do you set in the difference array?

6. The 2D prefix sum formula subtracts `prefix[r-1][c-1]` at the end. Why?

7. Can you compute "number of subarrays with sum ≥ k" using prefix sum + HashMap? What's the challenge?

8. Product of Array Except Self — why can't you just compute total product and divide by arr[i]?

> **Answers:**
> 1. `prefix[3] = 5 + (-1) + 3 = 7`. `sum(1,3) = prefix[4] - prefix[1] = 9 - 5 = 4`.
> 2. Array has negatives. Adding an element might decrease the sum, so you can't safely advance left when sum > k.
> 3. No, 9 is not in {0,3,7,10,10,12}. No new subarray ending at index 5 has sum 3.
> 4. 23-13=10, 10%5=0 → yes, divisible by 5.
> 5. `diff[2] += 7`, `diff[6] -= 7`.
> 6. When we add prefix[r-1][c] and prefix[r][c-1], the top-left rectangle prefix[r-1][c-1] is double-counted. Subtracting removes one copy.
> 7. Not directly with a simple map. You'd need a sorted structure (BIT/merge sort) to count prefixSums ≤ currentPrefixSum - k.
> 8. If any element is zero, total product is 0 and division is undefined. Also, integer division loses precision.

---

**Next →** `../09_Stack_Patterns/01_Stack_Patterns.md`

---

## 2. Beginner-Friendly Intuition

Imagine a running total of your daily expenses:
```
Day:      [1, 2, 3, 4, 5]
Expenses: [10, 20, 30, 40, 50]
Running:  [10, 30, 60, 100, 150]
```
"Total from day 2 to day 4" = running[4] - running[1] = 100 - 10 = 90.  
You precomputed the running total once. Now any range query is instant.

---

## 3. Core Prefix Sum

```java
// Build prefix sum
int[] prefix = new int[n + 1];  // prefix[0] = 0
for (int i = 0; i < n; i++)
    prefix[i + 1] = prefix[i] + arr[i];

// Query: sum from l to r (0-indexed, inclusive)
int rangeSum(int l, int r) {
    return prefix[r + 1] - prefix[l];
}
```

**Example:** arr=[3,1,4,1,5], prefix=[0,3,4,8,9,14]
- Sum from index 1 to 3 = prefix[4] - prefix[1] = 9 - 3 = 6 ✓ (1+4+1=6)

---

## 4. Subarray Sum Equals K

**Problem:** Count subarrays whose sum equals k.

**Key Insight:** sum(l,r) = prefix[r+1] - prefix[l] = k → prefix[l] = prefix[r+1] - k

```java
int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0, 1);  // empty prefix with sum 0 exists once
    int sum = 0, count = 0;
    for (int num : nums) {
        sum += num;
        count += prefixCount.getOrDefault(sum - k, 0);
        prefixCount.merge(sum, 1, Integer::sum);
    }
    return count;
}
```

**Dry Run:** nums=[1,1,1], k=2
```
sum=0, map={0:1}
num=1: sum=1, look for sum-k=1-2=-1 → 0, map={0:1, 1:1}
num=1: sum=2, look for 2-2=0 → count+=1, map={0:1,1:1,2:1}
num=1: sum=3, look for 3-2=1 → count+=1, map={0:1,1:1,2:1,3:1}
Result: 2 (subarrays: [1,1] at 0-1 and [1,1] at 1-2)
```

---

## 5. 2D Prefix Sum

```java
// Build 2D prefix sum
int[][] prefix = new int[m+1][n+1];
for (int i = 1; i <= m; i++)
    for (int j = 1; j <= n; j++)
        prefix[i][j] = matrix[i-1][j-1]
            + prefix[i-1][j]
            + prefix[i][j-1]
            - prefix[i-1][j-1];  // subtract double-counted corner

// Query: sum of submatrix from (r1,c1) to (r2,c2) (1-indexed)
int regionSum(int r1, int c1, int r2, int c2) {
    return prefix[r2][c2]
        - prefix[r1-1][c2]
        - prefix[r2][c1-1]
        + prefix[r1-1][c1-1];
}
```

---

## 6. Difference Array (Range Updates in O(1))

**Problem:** Add v to every element from index l to r. Do Q such updates, then read final array.

Naive: O(Q × n). Difference Array: O(Q + n).

```java
// Difference array
int[] diff = new int[n + 1];

// Range update: add v to arr[l..r]
void update(int l, int r, int v) {
    diff[l] += v;
    diff[r + 1] -= v;
}

// Reconstruct original array after all updates
int[] getResult() {
    int[] result = new int[n];
    int running = 0;
    for (int i = 0; i < n; i++) {
        running += diff[i];
        result[i] = running;
    }
    return result;
}
```

**Example:** n=5, updates: [1,3,+2], [0,2,+3]
```
diff after [1,3,+2]: [0, +2, 0, 0, -2, 0]
diff after [0,2,+3]: [+3, +2, 0, -3, -2, 0]
Reconstruct: [3, 5, 5, 2, 0]
```

---

## 7. Kadane's Algorithm (Maximum Subarray)

**Problem:** Find contiguous subarray with maximum sum.

```java
int maxSubArray(int[] nums) {
    int maxSoFar = nums[0], maxEndingHere = nums[0];
    for (int i = 1; i < nums.length; i++) {
        maxEndingHere = Math.max(nums[i], maxEndingHere + nums[i]);
        maxSoFar = Math.max(maxSoFar, maxEndingHere);
    }
    return maxSoFar;
}
```

**Dry Run:** [-2, 1, -3, 4, -1, 2, 1, -5, 4]
```
i=1(1):   maxEnd=max(1, -2+1)=1,  maxSoFar=1
i=2(-3):  maxEnd=max(-3,1-3)=-2,  maxSoFar=1
i=3(4):   maxEnd=max(4,-2+4)=4,   maxSoFar=4
i=4(-1):  maxEnd=max(-1,4-1)=3,   maxSoFar=4
i=5(2):   maxEnd=max(2,3+2)=5,    maxSoFar=5
i=6(1):   maxEnd=max(1,5+1)=6,    maxSoFar=6 ← answer
```

---

## 8. Prefix XOR

XOR has a special property: `a ^ a = 0` and `a ^ 0 = a`.

```java
// Count subarrays with XOR equal to k
int countXorSubarrays(int[] arr, int k) {
    Map<Integer, Integer> prefixXorCount = new HashMap<>();
    prefixXorCount.put(0, 1);
    int xor = 0, count = 0;
    for (int num : arr) {
        xor ^= num;
        count += prefixXorCount.getOrDefault(xor ^ k, 0);
        prefixXorCount.merge(xor, 1, Integer::sum);
    }
    return count;
}
```

---

## 8b. 2D Difference Array (Range Updates on a Grid)

The 1D difference trick extends to 2D: to add `v` to every cell in the rectangle `(r1,c1)..(r2,c2)`, stamp four corners, then take a **2D prefix sum** to materialize the grid. Turns `Q` rectangle updates from O(Q·area) into **O(Q + R·C)**.

```java
// Apply many rectangle add-updates, then reconstruct the final grid.
int[][] applyUpdates(int rows, int cols, int[][] updates) { // update = {r1,c1,r2,c2,val}
    int[][] diff = new int[rows + 1][cols + 1];
    for (int[] u : updates) {
        int r1 = u[0], c1 = u[1], r2 = u[2], c2 = u[3], v = u[4];
        diff[r1][c1]         += v;
        diff[r2 + 1][c1]     -= v;
        diff[r1][c2 + 1]     -= v;
        diff[r2 + 1][c2 + 1] += v;   // inclusion-exclusion corners
    }
    int[][] grid = new int[rows][cols];
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++) {
            int up   = i > 0 ? grid[i - 1][j] : 0;
            int left = j > 0 ? grid[i][j - 1] : 0;
            int diag = (i > 0 && j > 0) ? grid[i - 1][j - 1] : 0;
            grid[i][j] = diff[i][j] + up + left - diag;   // 2D prefix sum of diff
        }
    return grid;
}
```
**Canonical problems:** LeetCode 2536 *Increment Submatrices by One*, 2132 *Stamping the Grid*.

---

## 9. Real-World Use Cases

- **Analytics dashboards:** Sum sales from date A to date B in O(1).
- **Financial summaries:** Running P&L over ranges.
- **Game development:** Imos method for area-of-effect damage.
- **Image processing:** Summed area table for fast blur.

---

## 10. Practice Problems

**Easy:**
1. Range Sum Query (immutable).
2. Find pivot index (sum left == sum right).
3. Running sum of array.

**Medium:**
1. Subarray Sum Equals K.
2. Continuous Subarray Sum (multiple of K).
3. Product of Array Except Self.
4. Maximum Subarray (Kadane's).
5. Range Sum Query 2D (immutable).

**Hard:**
1. Maximum Sum of Two Non-Overlapping Subarrays.
2. Count of Range Sum.
3. Shortest Subarray with Sum at Least K (includes negatives).

---

**Next →** `../08_Hashing/01_Hashing_Patterns.md`

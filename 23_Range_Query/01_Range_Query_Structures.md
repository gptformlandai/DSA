# Section 23 — Range Query Structures

---

## 1. What Problem Does This Solve?

Range query structures support two operations on an array:
1. **Query:** Compute an aggregate (sum, min, max, GCD) over a range [l, r]
2. **Update:** Modify one or more elements

The challenge is doing both efficiently. A static array supports O(1) query after O(n) prefix-sum build, but updates require O(n) rebuild. Range query structures support both operations in O(log n).

---

## 2. Beginner-Friendly Intuition

**Segment Tree:** Divide the array in half repeatedly, like a binary search tree over ranges. Each node stores the aggregate of its range. Updating one element affects only O(log n) nodes (the path from leaf to root). Querying any range requires combining O(log n) nodes.

**Binary Indexed Tree (BIT / Fenwick Tree):** Uses the binary representation of indices to cleverly store partial sums. Each index is responsible for a range determined by its lowest set bit. Even simpler to code than a Segment Tree for sum queries.

---

## 3. Real-World Analogy

**Corporate hierarchy (Segment Tree):** A company has teams grouped into departments grouped into divisions. To get the total salary of any range of employees, you query the appropriate department/division nodes — O(log n) nodes cover any range.

**BIT — Ledger with running totals:** Each ledger entry summarizes a specific range based on a binary pattern. Adding a new entry updates only O(log n) summary entries.

---

## 4. Core Concept

### When to Use Which Structure

| Structure | Point Update | Range Query | Range Update | Space | Complexity |
|-----------|------------|------------|-------------|-------|-----------|
| **Prefix Sum** | O(n) rebuild | O(1) | No | O(n) | Static only |
| **Segment Tree** | O(log n) | O(log n) | O(log n)+lazy | O(4n) | Flexible |
| **BIT (Fenwick)** | O(log n) | O(log n) | Limited | O(n) | Sum only |
| **Sparse Table** | No updates | O(1) | No | O(n log n) | Static min/max |

### Segment Tree Node

```
node[i] covers a range. For root at 1:
- node[1] = entire array
- node[2] = left half, node[3] = right half
- node[2k] = left child of node[k]
- node[2k+1] = right child of node[k]
```

### BIT Key Property

`BIT[i]` stores the sum of elements from `i - lowbit(i) + 1` to `i`, where `lowbit(i) = i & (-i)`.

---

## 5. Pattern Recognition Signals

Use Range Query structures when:
```
"Point update + range sum query" → BIT or Segment Tree
"Range update + range query" → Segment Tree with lazy propagation
"Range minimum/maximum query (static)" → Sparse Table
"Range GCD/XOR/product query" → Segment Tree
"Count inversions" → BIT
"Kth smallest in range" → Segment Tree with merge sort
"2D range queries" → 2D Segment Tree or 2D BIT
```

---

## 6. Step-by-Step Algorithm

### Build Segment Tree
```
build(node, start, end):
    if start == end:
        tree[node] = arr[start]
    else:
        mid = (start + end) / 2
        build(2*node, start, mid)
        build(2*node+1, mid+1, end)
        tree[node] = combine(tree[2*node], tree[2*node+1])
```

### Segment Tree Point Update
```
update(node, start, end, idx, val):
    if start == end:
        arr[idx] = val; tree[node] = val
    else:
        mid = (start + end) / 2
        if idx <= mid: update(2*node, start, mid, idx, val)
        else: update(2*node+1, mid+1, end, idx, val)
        tree[node] = combine(tree[2*node], tree[2*node+1])
```

### Segment Tree Range Query
```
query(node, start, end, l, r):
    if r < start OR end < l: return identity  (out of range)
    if l <= start AND end <= r: return tree[node]  (fully in range)
    mid = (start + end) / 2
    leftResult = query(2*node, start, mid, l, r)
    rightResult = query(2*node+1, mid+1, end, l, r)
    return combine(leftResult, rightResult)
```

### BIT Update (Point)
```
update(i, delta):  (1-indexed)
    While i <= n:
        bit[i] += delta
        i += i & (-i)   ← move to next responsible node
```

### BIT Query (Prefix Sum)
```
query(i):  (1-indexed, returns sum [1..i])
    sum = 0
    While i > 0:
        sum += bit[i]
        i -= i & (-i)   ← move to parent
    return sum
```

---

## 7. Dry Run with Example

### BIT: Array [3, 2, -1, 6, 5, 4]

```
1-indexed: arr[1..6] = [3, 2, -1, 6, 5, 4]
BIT initialization (after updates):

bit[1] = arr[1] = 3                 (covers index 1)
bit[2] = arr[1]+arr[2] = 3+2 = 5   (covers indices 1-2, lowbit(2)=2)
bit[3] = arr[3] = -1                (covers index 3)
bit[4] = sum[1..4] = 3+2-1+6 = 10  (covers 1-4, lowbit(4)=4)
bit[5] = arr[5] = 5                 (covers index 5)
bit[6] = arr[5]+arr[6] = 5+4 = 9   (covers 5-6, lowbit(6)=2)

Query sum[1..5]:
  i=5: sum += bit[5]=5. i -= lowbit(5)=1 → i=4
  i=4: sum += bit[4]=10. i -= lowbit(4)=4 → i=0
  Stop. sum = 15 ✓ (3+2-1+6+5=15)

Query sum[3..5] = sum[1..5] - sum[1..2] = 15 - 5 = 10 ✓
```

---

## 8. Code Implementation

### Segment Tree (Sum)

```java
class SegmentTree {
    int[] tree;
    int n;

    SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];
        build(arr, 1, 0, n - 1);
    }

    void build(int[] arr, int node, int start, int end) {
        if (start == end) { tree[node] = arr[start]; return; }
        int mid = (start + end) / 2;
        build(arr, 2*node, start, mid);
        build(arr, 2*node+1, mid+1, end);
        tree[node] = tree[2*node] + tree[2*node+1];
    }

    void update(int node, int start, int end, int idx, int val) {
        if (start == end) { tree[node] = val; return; }
        int mid = (start + end) / 2;
        if (idx <= mid) update(2*node, start, mid, idx, val);
        else update(2*node+1, mid+1, end, idx, val);
        tree[node] = tree[2*node] + tree[2*node+1];
    }

    int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l) return 0; // out of range, identity for sum
        if (l <= start && end <= r) return tree[node]; // fully in range
        int mid = (start + end) / 2;
        return query(2*node, start, mid, l, r) +
               query(2*node+1, mid+1, end, l, r);
    }

    public void update(int idx, int val) { update(1, 0, n-1, idx, val); }
    public int query(int l, int r) { return query(1, 0, n-1, l, r); }
}
```

### Binary Indexed Tree (BIT)

```java
class BIT {
    int[] bit;
    int n;

    BIT(int n) { this.n = n; bit = new int[n + 1]; }

    void update(int i, int delta) { // 1-indexed
        for (; i <= n; i += i & (-i)) bit[i] += delta;
    }

    int query(int i) { // prefix sum [1..i]
        int sum = 0;
        for (; i > 0; i -= i & (-i)) sum += bit[i];
        return sum;
    }

    int query(int l, int r) { return query(r) - query(l - 1); } // range [l,r]
}
```

### Sparse Table (Range Minimum Query — Static)

```java
class SparseTable {
    int[][] table;
    int[] log2;
    int n;

    SparseTable(int[] arr) {
        n = arr.length;
        int LOG = (int)(Math.log(n) / Math.log(2)) + 1;
        table = new int[n][LOG];
        log2 = new int[n + 1];
        for (int i = 2; i <= n; i++) log2[i] = log2[i/2] + 1;
        for (int i = 0; i < n; i++) table[i][0] = arr[i];
        for (int j = 1; j < LOG; j++)
            for (int i = 0; i + (1 << j) <= n; i++)
                table[i][j] = Math.min(table[i][j-1], table[i + (1 << (j-1))][j-1]);
    }

    int queryMin(int l, int r) { // O(1) range minimum
        int k = log2[r - l + 1];
        return Math.min(table[l][k], table[r - (1 << k) + 1][k]);
    }
}
```

---

## 9. Time Complexity

| Structure | Build | Point Update | Range Query |
|-----------|-------|-------------|------------|
| Prefix Sum | O(n) | O(n) | O(1) |
| Segment Tree | O(n) | O(log n) | O(log n) |
| BIT | O(n log n) | O(log n) | O(log n) |
| Sparse Table | O(n log n) | No updates | O(1) |

---

## 10. Space Complexity

| Structure | Space |
|-----------|-------|
| Prefix Sum | O(n) |
| Segment Tree | O(4n) = O(n) |
| BIT | O(n) |
| Sparse Table | O(n log n) |

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| Single element range query | Works correctly for all structures |
| l = r in range query | Returns single element value |
| n not a power of 2 | Segment Tree and Sparse Table still work |
| Update out of bounds | Check before calling update |
| BIT: 0-indexed array | Convert to 1-indexed internally |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Segment tree size too small
int[] tree = new int[2 * n]; // WRONG: may not fit all nodes
int[] tree = new int[4 * n]; // CORRECT: 4n is safe for any n

// MISTAKE 2: Wrong identity element for query
if (r < start || end < l) return 0;    // correct for sum
if (r < start || end < l) return Integer.MAX_VALUE; // correct for min
if (r < start || end < l) return Integer.MIN_VALUE; // correct for max

// MISTAKE 3: BIT off-by-one (0-indexed vs 1-indexed)
// BIT is naturally 1-indexed. Convert: BIT index = array index + 1
bit.update(i + 1, val);   // for 0-indexed array[i]

// MISTAKE 4: Sparse Table — wrong merge for non-idempotent functions
// Sparse Table overlapping ranges work ONLY for idempotent functions (min, max, GCD)
// Do NOT use Sparse Table for sum — overlapping ranges double-count
```

---

## 13. Interview-Level Explanation

**Q: "When would you choose BIT over Segment Tree?"**

> "BIT is simpler to implement and uses less memory, but is limited to prefix-sum-style queries (sum, XOR). Segment Tree is more general — it supports any associative operation (min, max, GCD, product) and can handle range updates with lazy propagation. For simple sum queries with point updates, BIT is preferred. For range updates or non-sum aggregates, use Segment Tree."

**Q: "How does Sparse Table achieve O(1) range minimum query?"**

> "Sparse Table precomputes minimums for all ranges of length 2^k starting at each index. For any query [l, r], the range length is (r-l+1). We pick k = floor(log2(length)), and use two overlapping precomputed ranges: [l, l+2^k-1] and [r-2^k+1, r]. Both cover the entire [l, r]. Taking the minimum of two values is O(1). This works because min is idempotent — repeating elements in the overlap doesn't change the result."

---

## 14. Real-World Use Cases

| Application | Range Query Structure |
|------------|----------------------|
| **Database engines** | Range sum/count on indexed columns |
| **Stock market** | Min/Max price in time range |
| **Game leaderboards** | Range rank queries |
| **Computational geometry** | Range tree for 2D point queries |
| **String matching** | Sparse Table for LCP (Longest Common Prefix) |
| **Competitive programming** | Segment Tree with lazy propagation |

---

## 15. Variations of This Pattern

| Variation | Structure | Example |
|-----------|----------|---------|
| Point update + range sum | BIT or SegTree | Range Sum Query Mutable |
| Range update + point query | BIT difference array | Range Addition |
| Range update + range sum | SegTree with lazy | Range Sum Update |
| Static range min/max | Sparse Table | RMQ |
| Count inversions | BIT + merge sort | Count Inversions |
| 2D range sum | 2D BIT | 2D Range Sum Query |

---

## 16. Practice Problems

### Easy — Foundation
1. **Range Sum Query - Mutable** (LeetCode #307)
   - *Task:* Point update + range sum query.
   - *Hint:* BIT or Segment Tree. BIT preferred for simplicity.

2. **Range Minimum Query** (static)
   - *Task:* Precompute to answer min(l, r) in O(1).
   - *Hint:* Sparse Table — build in O(n log n), query O(1).

3. **Count of Smaller Numbers After Self** (LeetCode #315)
   - *Task:* For each element, count smaller elements to its right.
   - *Hint:* BIT — process from right to left, query prefix sum.

### Medium — Core Structures
1. **Range Sum Query - Mutable** (LeetCode #307)
   - *Task:* Support point updates and range sum queries.
   - *Hint:* Implement both BIT and Segment Tree to compare.

2. **Maximum Sum of Rectangle No Larger Than K** (LeetCode #363)
   - *Task:* Max rectangle sum ≤ k in matrix.
   - *Hint:* 2D prefix sum + sorted set / BIT.

3. **Number of Inversions** (classic)
   - *Task:* Count pairs (i,j) where i<j but arr[i]>arr[j].
   - *Hint:* BIT — coordinate compress values, process right-to-left.

4. **My Calendar I/II/III** (LeetCode #729, #731, #732)
   - *Task:* Book events, track overlaps.
   - *Hint:* Segment Tree with lazy propagation for range max.

5. **Falling Squares** (LeetCode #699)
   - *Task:* Max height after each square drop.
   - *Hint:* Segment Tree with range max update + range max query.

### Hard — Advanced
1. **The Skyline Problem** (LeetCode #218)
   - *Task:* Given building positions and heights, find skyline.
   - *Hint:* Events (start/end) + TreeMap or Segment Tree.

2. **Minimum Interval to Include Each Query** (LeetCode #1851)
   - *Task:* For each query, find smallest interval containing it.
   - *Hint:* Sort both intervals and queries; min-heap of active intervals.

3. **Range Module** (LeetCode #715)
   - *Task:* Track ranges; supports add, remove, query.
   - *Hint:* TreeMap interval merging or Segment Tree.

---

## 17. How to Know You Have Mastered Range Query Structures

You have mastered this topic when you can:
- [ ] Implement a Segment Tree (build, update, query) from scratch
- [ ] Implement a BIT with correct 1-indexed update and prefix query
- [ ] Explain the lowbit trick `i & (-i)` and how it drives BIT
- [ ] Use Sparse Table for static O(1) range minimum
- [ ] Choose the right structure for a given problem (sum vs min vs range update)
- [ ] Handle the identity element correctly for out-of-range returns
- [ ] Know when Sparse Table is invalid (non-idempotent operations)
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. You have an array of 8 elements. The Segment Tree has size 4×8 = 32. How many nodes are actually used?

2. BIT query for sum [3..6] (1-indexed). What two prefix sums do you compute?

3. For Sparse Table, why can't you use it for range sum (but can for range min)?

4. `lowbit(12)` = ? Show the calculation using `12 & (-12)`.

5. In Segment Tree range query, when do you return the identity element?

6. If you need range update (add delta to all elements in [l, r]) + point query, can you use BIT? How?

7. Segment Tree uses `4*n` nodes. Why 4 and not 2?

8. For a Sparse Table with n=8, how many levels (k values) are precomputed?

> **Answers:**
> 1. A complete binary tree of 8 leaves has 15 nodes. With 0-indexed leaves starting at position 8, the tree uses nodes 1-15. With the 4n allocation, 2×8=16 is the minimum needed, 4×8=32 is the safe bound.
> 2. query(6) - query(2) = (sum[1..6]) - (sum[1..2]).
> 3. Min is idempotent: min(min(a,b), min(b,c)) correctly gives min(a,b,c) even with b counted twice. Sum is NOT idempotent: sum(sum(a,b), sum(b,c)) = a+2b+c ≠ a+b+c. Overlap double-counts for sum.
> 4. 12 = 1100, -12 = ...10100 (two's complement). 1100 & 10100 = 0100 = 4. lowbit(12) = 4.
> 5. When the query range [l,r] doesn't overlap the node's range [start,end] at all (r < start OR end < l).
> 6. Yes! Use a BIT difference array. `rangeUpdate(l, r, delta)` = BIT.update(l, delta) + BIT.update(r+1, -delta). `pointQuery(i)` = BIT.prefixSum(i).
> 7. For non-power-of-2 sizes, the tree may need more than 2n nodes. In the worst case (n = 2^k + 1), you need nearly 4n nodes. 4n is a safe universal upper bound.
> 8. 4 levels: k=0 (size 1), k=1 (size 2), k=2 (size 4), k=3 (size 8). Since floor(log2(8)) = 3.

---

**Next →** `../24_Math/01_Math_Algorithms.md`
| Prefix Sum | None (static) | O(1) | O(n) | Static range sum |
| Fenwick Tree (BIT) | O(log n) | O(log n) | O(n) | Point update + prefix sum |
| Segment Tree | O(log n) | O(log n) | O(4n) | Range update + range query |
| Sparse Table | None | O(1) | O(n log n) | Static RMQ (min/max) |

---

## Part 1: Segment Tree

**Supports:** Range sum, range min/max, point/range updates.

### Build, Query, Update

```java
class SegmentTree {
    int[] tree;
    int n;

    SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];
        build(arr, 0, 0, n - 1);
    }

    void build(int[] arr, int node, int start, int end) {
        if (start == end) { tree[node] = arr[start]; return; }
        int mid = (start + end) / 2;
        build(arr, 2*node+1, start, mid);
        build(arr, 2*node+2, mid+1, end);
        tree[node] = tree[2*node+1] + tree[2*node+2];  // sum — change for min/max
    }

    // Range sum query [l, r]
    int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l) return 0;              // out of range
        if (l <= start && end <= r) return tree[node];   // fully inside
        int mid = (start + end) / 2;
        return query(2*node+1, start, mid, l, r)
             + query(2*node+2, mid+1, end, l, r);
    }

    // Point update: set arr[idx] = val
    void update(int node, int start, int end, int idx, int val) {
        if (start == end) { tree[node] = val; return; }
        int mid = (start + end) / 2;
        if (idx <= mid) update(2*node+1, start, mid, idx, val);
        else update(2*node+2, mid+1, end, idx, val);
        tree[node] = tree[2*node+1] + tree[2*node+2];
    }

    // Public API
    int query(int l, int r) { return query(0, 0, n-1, l, r); }
    void update(int idx, int val) { update(0, 0, n-1, idx, val); }
}
```

---

## Part 2: Fenwick Tree (Binary Indexed Tree)

Simpler implementation than Segment Tree. Supports point updates and prefix sum queries.

```java
class FenwickTree {
    int[] tree;
    int n;

    FenwickTree(int n) { this.n = n; tree = new int[n+1]; }

    // Add val to index i (1-indexed)
    void update(int i, int val) {
        for (; i <= n; i += i & (-i))  // i & (-i) = lowest set bit
            tree[i] += val;
    }

    // Prefix sum [1..i]
    int query(int i) {
        int sum = 0;
        for (; i > 0; i -= i & (-i)) sum += tree[i];
        return sum;
    }

    // Range sum [l..r]
    int query(int l, int r) { return query(r) - query(l-1); }
}
```

**How the Lowbit Trick Works:**
```
i & (-i) isolates the lowest set bit
i=6 (110): 6 & -6 = 110 & 010 = 010 = 2
So tree[6] stores sum of 2 elements: arr[5]+arr[6]
```

**Dry Run: Build FenwickTree from [1,2,3,4,5]**
```
update(1,1): tree[1]+=1, tree[2]+=1, tree[4]+=1
update(2,2): tree[2]+=2, tree[4]+=2
update(3,3): tree[3]+=3, tree[4]+=3
update(4,4): tree[4]+=4
update(5,5): tree[5]+=5, tree[6]+=5, tree[8]+=5
query(3) = tree[2]+tree[1] = ... = 6 (1+2+3)
```

---

## Part 3: Sparse Table (Static RMQ)

**Perfect for:** Minimum/Maximum queries with no updates. O(1) query.

```java
class SparseTable {
    int[][] table;
    int[] log2;
    int n;

    SparseTable(int[] arr) {
        n = arr.length;
        int maxLog = (int)(Math.log(n)/Math.log(2)) + 1;
        table = new int[maxLog][n];
        log2 = new int[n+1];

        // Precompute log2
        log2[1] = 0;
        for (int i = 2; i <= n; i++) log2[i] = log2[i/2] + 1;

        // Build table
        table[0] = arr.clone();
        for (int j = 1; j < maxLog; j++)
            for (int i = 0; i + (1<<j) <= n; i++)
                table[j][i] = Math.min(table[j-1][i], table[j-1][i + (1<<(j-1))]);
    }

    // Range minimum query [l, r] in O(1)
    int query(int l, int r) {
        int k = log2[r - l + 1];
        return Math.min(table[k][l], table[k][r - (1<<k) + 1]);
    }
}
```

**Why it's O(1):** Precompute answers for all power-of-2 length intervals. Any query = overlap of two precomputed intervals (idempotent for min/max).

---

## Choosing the Right Structure

```
No updates needed + min/max query? → Sparse Table O(1)
Point updates + prefix sum?        → Fenwick Tree (simpler code)
Range updates + range queries?     → Segment Tree
Static range sums?                 → Prefix Sum Array
```

---

## Practice Problems

**Medium:**
1. Range Sum Query — Mutable (Segment Tree / BIT).
2. Count of Smaller Numbers After Self (BIT).
3. Range Sum Query 2D — Mutable.

**Hard:**
1. The Skyline Problem.
2. Count of Range Sum.
3. Sliding Window Median (Segment Tree).

# Segment Tree & Fenwick Tree Patterns Deep Dive
## Junior Dev's Complete Guide to L5 MAANG Range Query Mastery

---

# THE RANGE QUERY MINDSET: Before You Code Anything

## The One Sentence That Unlocks Range Queries

> **When you need to answer MANY range queries (sum, min, max) on an array that MAY be updated, use Segment Tree or Fenwick Tree.**

## When to Use What?

```
STATIC ARRAY (no updates):
  -> Prefix Sum (simplest, O(1) query)

DYNAMIC ARRAY (with updates):
  -> Fenwick Tree: Point update + Range sum (simpler, less code)
  -> Segment Tree: Range update + Range query (more powerful)

DECISION TREE:
                    Need range queries?
                          |
              +-----------+-----------+
              |                       |
           No updates              Updates needed
              |                       |
         Prefix Sum           What type of query?
                                      |
                    +-----------------+-----------------+
                    |                 |                 |
               Sum only         Min/Max/GCD        Range updates
                    |                 |                 |
              Fenwick Tree      Segment Tree      Segment Tree
                                                  (with lazy)
```

---

# PART 1: FENWICK TREE (Binary Indexed Tree)

## The Fenwick Tree Mental Model

**Analogy:** Imagine a library where books are organized in a special way:
- Some shelves hold individual books
- Some shelves hold summaries of multiple shelves below them
- To find total books in range, you only check a few "summary" shelves

**Key Insight:** Each index stores sum of a specific range determined by its lowest set bit.

```
Index (1-based):  1    2    3    4    5    6    7    8
Binary:          001  010  011  100  101  110  111  1000
Stores sum of:   [1]  [1-2] [3] [1-4] [5] [5-6] [7] [1-8]
                  ↑    ↑↑    ↑   ↑↑↑↑  ↑   ↑↑    ↑   ↑↑↑↑↑↑↑↑
```

## The Fenwick Tree Template (MEMORIZE THIS!)

```java
class FenwickTree {
    int[] tree;
    int n;
    
    public FenwickTree(int n) {
        this.n = n;
        this.tree = new int[n + 1];  // 1-indexed!
    }
    
    // Add delta to index i (1-indexed)
    public void update(int i, int delta) {
        while (i <= n) {
            tree[i] += delta;
            i += i & (-i);  // Move to parent
        }
    }
    
    // Get prefix sum [1, i]
    public int query(int i) {
        int sum = 0;
        while (i > 0) {
            sum += tree[i];
            i -= i & (-i);  // Move to previous range
        }
        return sum;
    }
    
    // Get range sum [left, right]
    public int rangeQuery(int left, int right) {
        return query(right) - query(left - 1);
    }
}
```

### The Magic: `i & (-i)`

```
This extracts the LOWEST SET BIT:
  6 = 110 in binary
 -6 = 010 in two's complement (invert + 1)
6 & (-6) = 010 = 2

Why it works:
- Update: Add lowest bit to move UP the tree
- Query: Subtract lowest bit to move to PREVIOUS range
```

---

## PATTERN 1: Range Sum Query - Mutable (LC 307)

### Pattern Recognition Signal

> **When you see:** "Range sum with point updates"
> **Instant thought:** "Fenwick Tree! O(log n) update and query"

### Visual Dry Run

```
nums = [1, 3, 5, 7, 9, 11]

Build Fenwick Tree (1-indexed):
Index:    1    2    3    4    5    6
Value:    1    3    5    7    9    11

tree[1] = nums[0] = 1                    (covers [1])
tree[2] = nums[0] + nums[1] = 4          (covers [1,2])
tree[3] = nums[2] = 5                    (covers [3])
tree[4] = nums[0]+nums[1]+nums[2]+nums[3] = 16  (covers [1,2,3,4])
tree[5] = nums[4] = 9                    (covers [5])
tree[6] = nums[4] + nums[5] = 20         (covers [5,6])

Query sumRange(2, 5):  (0-indexed input, convert to 1-indexed: [3, 6])
  query(6) = tree[6] + tree[4] = 20 + 16 = 36
  query(2) = tree[2] = 4
  Answer = 36 - 4 = 32

Verify: 5 + 7 + 9 + 11 = 32 ✓

Update(3, 2):  (change nums[3] from 7 to 2, delta = -5)
  update(4, -5):
    tree[4] -= 5 → 16 - 5 = 11
    4 + (4 & -4) = 4 + 4 = 8 (out of range, stop)
```

### The Code

```java
class NumArray {
    private int[] nums;
    private int[] tree;
    private int n;
    
    public NumArray(int[] nums) {
        this.n = nums.length;
        this.nums = new int[n];
        this.tree = new int[n + 1];
        
        for (int i = 0; i < n; i++) {
            update(i, nums[i]);
        }
    }
    
    public void update(int index, int val) {
        int delta = val - nums[index];
        nums[index] = val;
        
        int i = index + 1;  // Convert to 1-indexed
        while (i <= n) {
            tree[i] += delta;
            i += i & (-i);
        }
    }
    
    public int sumRange(int left, int right) {
        return prefixSum(right + 1) - prefixSum(left);
    }
    
    private int prefixSum(int i) {
        int sum = 0;
        while (i > 0) {
            sum += tree[i];
            i -= i & (-i);
        }
        return sum;
    }
}
```

### Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| 0-indexed tree | Fenwick needs 1-indexed | Use `tree[n+1]`, convert indices |
| Forgetting delta | Update needs difference, not new value | `delta = newVal - oldVal` |
| Wrong range | Off-by-one in range query | `query(right) - query(left-1)` |

### Mind-Map Anchor

```
FENWICK TREE (BIT)
        |
        v
+------------------------+
| tree[i] stores partial |
| sum based on lowest    |
| set bit of i           |
|                        |
| Update: i += i & (-i)  |
| Query:  i -= i & (-i)  |
| O(log n) both ops      |
+------------------------+
```

**Memory phrase:** "Lowest bit magic: add to go up, subtract to go left"

---

## PATTERN 2: Count of Smaller Numbers After Self (LC 315)

### Pattern Recognition Signal

> **When you see:** "Count elements smaller/larger after/before current"
> **Instant thought:** "Fenwick Tree on value frequencies!"

### The Key Insight

Process array from RIGHT to LEFT. Use Fenwick Tree to count how many numbers we've seen that are smaller than current.

### Visual Dry Run

```
nums = [5, 2, 6, 1]

Process right to left, BIT tracks frequency of values seen:

i=3, num=1:
  query(0) = 0 (no numbers < 1 seen)
  update(1) → BIT now has 1 at position 1
  counts[3] = 0

i=2, num=6:
  query(5) = 1 (one number ≤ 5 seen, which is 1)
  update(6) → BIT now has 1 at positions 1, 6
  counts[2] = 1

i=1, num=2:
  query(1) = 1 (one number ≤ 1 seen)
  update(2) → BIT now has 1 at positions 1, 2, 6
  counts[1] = 1

i=0, num=5:
  query(4) = 2 (numbers ≤ 4: 1 and 2)
  update(5) → BIT updated
  counts[0] = 2

Result: [2, 1, 1, 0]
```

### The Code

```java
public List<Integer> countSmaller(int[] nums) {
    int n = nums.length;
    Integer[] result = new Integer[n];
    
    // Coordinate compression
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    Map<Integer, Integer> rank = new HashMap<>();
    int r = 1;
    for (int num : sorted) {
        if (!rank.containsKey(num)) {
            rank.put(num, r++);
        }
    }
    
    int[] tree = new int[r + 1];
    
    // Process right to left
    for (int i = n - 1; i >= 0; i--) {
        int pos = rank.get(nums[i]);
        result[i] = query(tree, pos - 1);  // Count smaller
        update(tree, pos);                  // Add current
    }
    
    return Arrays.asList(result);
}

private void update(int[] tree, int i) {
    while (i < tree.length) {
        tree[i]++;
        i += i & (-i);
    }
}

private int query(int[] tree, int i) {
    int sum = 0;
    while (i > 0) {
        sum += tree[i];
        i -= i & (-i);
    }
    return sum;
}
```

### Mind-Map Anchor

```
COUNT SMALLER AFTER SELF
          |
          v
+------------------------+
| Process RIGHT to LEFT  |
| BIT tracks frequencies |
| query(val-1) = count   |
| of smaller values seen |
| Coordinate compression |
| for large values       |
+------------------------+
```

**Memory phrase:** "Right to left, BIT counts frequencies, query for smaller"

---

## PATTERN 3: Count Inversions (Classic)

### Pattern Recognition Signal

> **When you see:** "Count pairs where i < j but arr[i] > arr[j]"
> **Instant thought:** "Fenwick Tree! Count larger elements before current"

### Visual Dry Run

```
arr = [8, 4, 2, 1]

Process left to right, count elements LARGER than current seen so far:

i=0, num=8:
  inversions += (elements seen) - query(8) = 0 - 0 = 0
  update(8)
  
i=1, num=4:
  inversions += 1 - query(4) = 1 - 0 = 1  (8 > 4)
  update(4)
  
i=2, num=2:
  inversions += 2 - query(2) = 2 - 0 = 2  (8 > 2, 4 > 2)
  update(2)
  
i=3, num=1:
  inversions += 3 - query(1) = 3 - 0 = 3  (8 > 1, 4 > 1, 2 > 1)
  update(1)

Total inversions = 0 + 1 + 2 + 3 = 6
```

### The Code

```java
public long countInversions(int[] arr) {
    int n = arr.length;
    
    // Coordinate compression
    int[] sorted = arr.clone();
    Arrays.sort(sorted);
    Map<Integer, Integer> rank = new HashMap<>();
    int r = 1;
    for (int num : sorted) {
        if (!rank.containsKey(num)) {
            rank.put(num, r++);
        }
    }
    
    int[] tree = new int[r + 1];
    long inversions = 0;
    
    for (int i = 0; i < n; i++) {
        int pos = rank.get(arr[i]);
        // Count elements seen so far that are LARGER than current
        int totalSeen = i;
        int smallerOrEqual = query(tree, pos);
        inversions += totalSeen - smallerOrEqual;
        update(tree, pos);
    }
    
    return inversions;
}
```

### Mind-Map Anchor

```
COUNT INVERSIONS
       |
       v
+------------------------+
| Process LEFT to RIGHT  |
| For each element:      |
| inversions += seen -   |
|              query(val)|
| (counts larger before) |
+------------------------+
```

**Memory phrase:** "Left to right, inversions = seen - query(val)"

---

# PART 2: SEGMENT TREE

## The Segment Tree Mental Model

**Analogy:** A tournament bracket where:
- Leaves = individual players (array elements)
- Internal nodes = winners of sub-tournaments (aggregated values)
- Root = overall winner (aggregate of entire array)

```
Array: [2, 1, 5, 3, 4]

Segment Tree (for sum):
                    [15]           <- sum of [0,4]
                   /    \
              [8]        [7]       <- sum of [0,2], [3,4]
             /   \      /   \
          [3]   [5]  [3]   [4]    <- sum of [0,1], [2], [3], [4]
         /   \
       [2]   [1]                   <- individual elements
```

## The Segment Tree Template (MEMORIZE THIS!)

```java
class SegmentTree {
    int[] tree;
    int n;
    
    public SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];  // Safe size
        build(arr, 0, 0, n - 1);
    }
    
    // Build tree recursively
    private void build(int[] arr, int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];  // Leaf
        } else {
            int mid = (start + end) / 2;
            build(arr, 2 * node + 1, start, mid);      // Left child
            build(arr, 2 * node + 2, mid + 1, end);    // Right child
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];  // Merge
        }
    }
    
    // Point update: set arr[idx] = val
    public void update(int idx, int val) {
        update(0, 0, n - 1, idx, val);
    }
    
    private void update(int node, int start, int end, int idx, int val) {
        if (start == end) {
            tree[node] = val;
        } else {
            int mid = (start + end) / 2;
            if (idx <= mid) {
                update(2 * node + 1, start, mid, idx, val);
            } else {
                update(2 * node + 2, mid + 1, end, idx, val);
            }
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }
    
    // Range query: sum of [l, r]
    public int query(int l, int r) {
        return query(0, 0, n - 1, l, r);
    }
    
    private int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l) {
            return 0;  // Out of range
        }
        if (l <= start && end <= r) {
            return tree[node];  // Fully inside
        }
        int mid = (start + end) / 2;
        int leftSum = query(2 * node + 1, start, mid, l, r);
        int rightSum = query(2 * node + 2, mid + 1, end, l, r);
        return leftSum + rightSum;
    }
}
```

---

## PATTERN 4: Range Sum Query - Mutable (Segment Tree Version)

### Pattern Recognition Signal

> **When you see:** "Range sum with updates" or "Need range updates later"
> **Instant thought:** "Segment Tree for flexibility!"

### Visual Dry Run

```
nums = [1, 3, 5, 7, 9, 11]

Build Segment Tree:
                      [36]                    <- [0,5] sum
                    /      \
               [9]          [27]              <- [0,2], [3,5]
              /    \       /    \
           [4]    [5]   [16]   [11]          <- [0,1], [2], [3,4], [5]
          /   \        /    \
        [1]   [3]    [7]    [9]              <- leaves

Query(1, 4):  sum of indices 1 to 4
  Node [0,5]: partially overlaps → recurse
    Node [0,2]: partially overlaps → recurse
      Node [0,1]: partially overlaps → recurse
        Node [0]: out of range → 0
        Node [1]: fully inside → 3
      Node [2]: fully inside → 5
    Node [3,5]: partially overlaps → recurse
      Node [3,4]: fully inside → 16
      Node [5]: out of range → 0
  
  Result: 3 + 5 + 16 = 24
  Verify: 3 + 5 + 7 + 9 = 24 ✓

Update(2, 10):  change nums[2] from 5 to 10
  Path: root → [0,2] → [2]
  Update leaf [2] = 10
  Propagate up: [0,2] = 4 + 10 = 14
                root = 14 + 27 = 41
```

### The Code

```java
class NumArray {
    private int[] tree;
    private int n;
    
    public NumArray(int[] nums) {
        n = nums.length;
        tree = new int[4 * n];
        if (n > 0) build(nums, 0, 0, n - 1);
    }
    
    private void build(int[] nums, int node, int start, int end) {
        if (start == end) {
            tree[node] = nums[start];
        } else {
            int mid = start + (end - start) / 2;
            build(nums, 2 * node + 1, start, mid);
            build(nums, 2 * node + 2, mid + 1, end);
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }
    
    public void update(int index, int val) {
        update(0, 0, n - 1, index, val);
    }
    
    private void update(int node, int start, int end, int idx, int val) {
        if (start == end) {
            tree[node] = val;
        } else {
            int mid = start + (end - start) / 2;
            if (idx <= mid) {
                update(2 * node + 1, start, mid, idx, val);
            } else {
                update(2 * node + 2, mid + 1, end, idx, val);
            }
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }
    
    public int sumRange(int left, int right) {
        return query(0, 0, n - 1, left, right);
    }
    
    private int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l) return 0;
        if (l <= start && end <= r) return tree[node];
        
        int mid = start + (end - start) / 2;
        return query(2 * node + 1, start, mid, l, r) +
               query(2 * node + 2, mid + 1, end, l, r);
    }
}
```

### Mind-Map Anchor

```
SEGMENT TREE
     |
     v
+------------------------+
| tree[4*n] array        |
| Leaves = elements      |
| Parents = merged kids  |
|                        |
| Build: O(n)            |
| Update: O(log n)       |
| Query: O(log n)        |
+------------------------+
```

**Memory phrase:** "Tournament bracket: leaves are players, parents are winners"

---

## PATTERN 5: Range Minimum Query (RMQ)

### Pattern Recognition Signal

> **When you see:** "Find minimum in range with updates"
> **Instant thought:** "Segment Tree with min instead of sum!"

### Visual Dry Run

```
nums = [2, 5, 1, 4, 9, 3]

Segment Tree (for min):
                    [1]              <- min of [0,5]
                  /     \
              [1]        [3]         <- min of [0,2], [3,5]
             /   \      /   \
          [2]   [1]  [4]   [3]      <- min of [0,1], [2], [3,4], [5]
         /   \      /   \
       [2]   [5]  [4]   [9]         <- leaves

Query(1, 4): min of indices 1 to 4
  = min(5, 1, 4, 9) = 1

Query(3, 5): min of indices 3 to 5
  = min(4, 9, 3) = 3
```

### The Code

```java
class RangeMinQuery {
    private int[] tree;
    private int n;
    
    public RangeMinQuery(int[] nums) {
        n = nums.length;
        tree = new int[4 * n];
        Arrays.fill(tree, Integer.MAX_VALUE);
        if (n > 0) build(nums, 0, 0, n - 1);
    }
    
    private void build(int[] nums, int node, int start, int end) {
        if (start == end) {
            tree[node] = nums[start];
        } else {
            int mid = start + (end - start) / 2;
            build(nums, 2 * node + 1, start, mid);
            build(nums, 2 * node + 2, mid + 1, end);
            tree[node] = Math.min(tree[2 * node + 1], tree[2 * node + 2]);
        }
    }
    
    public void update(int index, int val) {
        update(0, 0, n - 1, index, val);
    }
    
    private void update(int node, int start, int end, int idx, int val) {
        if (start == end) {
            tree[node] = val;
        } else {
            int mid = start + (end - start) / 2;
            if (idx <= mid) {
                update(2 * node + 1, start, mid, idx, val);
            } else {
                update(2 * node + 2, mid + 1, end, idx, val);
            }
            tree[node] = Math.min(tree[2 * node + 1], tree[2 * node + 2]);
        }
    }
    
    public int queryMin(int left, int right) {
        return query(0, 0, n - 1, left, right);
    }
    
    private int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l) return Integer.MAX_VALUE;
        if (l <= start && end <= r) return tree[node];
        
        int mid = start + (end - start) / 2;
        return Math.min(
            query(2 * node + 1, start, mid, l, r),
            query(2 * node + 2, mid + 1, end, l, r)
        );
    }
}
```

### Mind-Map Anchor

```
RANGE MIN QUERY
       |
       v
+------------------------+
| Same as sum tree but   |
| merge = Math.min()     |
| Identity = MAX_VALUE   |
+------------------------+
```

**Memory phrase:** "Same structure, just change merge function to min"


---

## PATTERN 6: Lazy Propagation (Range Updates)

### Pattern Recognition Signal

> **When you see:** "Add value to entire range" or "Range update + Range query"
> **Instant thought:** "Segment Tree with Lazy Propagation!"

### The Key Insight

Instead of updating all elements in a range immediately, we "lazily" store the update and propagate it only when needed.

### Visual Dry Run

```
nums = [1, 3, 5, 7, 9]

Range Add: add 10 to [1, 3]

WITHOUT lazy (slow):
  Update index 1: 3 → 13
  Update index 2: 5 → 15
  Update index 3: 7 → 17
  3 separate O(log n) operations = O(n log n) worst case

WITH lazy (fast):
  Mark node covering [1,3] with lazy=10
  Don't update children yet
  When querying, push lazy down as needed
  O(log n) for range update!
```

### The Code

```java
class LazySegmentTree {
    private long[] tree;
    private long[] lazy;
    private int n;
    
    public LazySegmentTree(int[] nums) {
        n = nums.length;
        tree = new long[4 * n];
        lazy = new long[4 * n];
        build(nums, 0, 0, n - 1);
    }
    
    private void build(int[] nums, int node, int start, int end) {
        if (start == end) {
            tree[node] = nums[start];
        } else {
            int mid = start + (end - start) / 2;
            build(nums, 2 * node + 1, start, mid);
            build(nums, 2 * node + 2, mid + 1, end);
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }
    
    // Push lazy value down to children
    private void pushDown(int node, int start, int end) {
        if (lazy[node] != 0) {
            int mid = start + (end - start) / 2;
            int leftChild = 2 * node + 1;
            int rightChild = 2 * node + 2;
            
            // Update children's tree values
            tree[leftChild] += lazy[node] * (mid - start + 1);
            tree[rightChild] += lazy[node] * (end - mid);
            
            // Pass lazy to children
            lazy[leftChild] += lazy[node];
            lazy[rightChild] += lazy[node];
            
            // Clear current lazy
            lazy[node] = 0;
        }
    }
    
    // Range update: add val to all elements in [l, r]
    public void rangeAdd(int l, int r, int val) {
        rangeAdd(0, 0, n - 1, l, r, val);
    }
    
    private void rangeAdd(int node, int start, int end, int l, int r, int val) {
        if (r < start || end < l) return;  // Out of range
        
        if (l <= start && end <= r) {
            // Fully inside - apply lazy
            tree[node] += (long) val * (end - start + 1);
            lazy[node] += val;
            return;
        }
        
        pushDown(node, start, end);  // Push before going down
        
        int mid = start + (end - start) / 2;
        rangeAdd(2 * node + 1, start, mid, l, r, val);
        rangeAdd(2 * node + 2, mid + 1, end, l, r, val);
        tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
    }
    
    // Range query: sum of [l, r]
    public long rangeSum(int l, int r) {
        return rangeSum(0, 0, n - 1, l, r);
    }
    
    private long rangeSum(int node, int start, int end, int l, int r) {
        if (r < start || end < l) return 0;
        
        if (l <= start && end <= r) {
            return tree[node];
        }
        
        pushDown(node, start, end);  // Push before going down
        
        int mid = start + (end - start) / 2;
        return rangeSum(2 * node + 1, start, mid, l, r) +
               rangeSum(2 * node + 2, mid + 1, end, l, r);
    }
}
```

### Mind-Map Anchor

```
LAZY PROPAGATION
       |
       v
+------------------------+
| lazy[] stores pending  |
| updates                |
| pushDown() before      |
| accessing children     |
| Range update: O(log n) |
| Range query: O(log n)  |
+------------------------+
```

**Memory phrase:** "Lazy = procrastinate updates, push down when needed"

---

## PATTERN 7: Range Maximum Query with Point Update

### Pattern Recognition Signal

> **When you see:** "Find maximum in range with updates"
> **Instant thought:** "Segment Tree with max merge!"

### The Code

```java
class RangeMaxQuery {
    private int[] tree;
    private int n;
    
    public RangeMaxQuery(int[] nums) {
        n = nums.length;
        tree = new int[4 * n];
        Arrays.fill(tree, Integer.MIN_VALUE);
        if (n > 0) build(nums, 0, 0, n - 1);
    }
    
    private void build(int[] nums, int node, int start, int end) {
        if (start == end) {
            tree[node] = nums[start];
        } else {
            int mid = start + (end - start) / 2;
            build(nums, 2 * node + 1, start, mid);
            build(nums, 2 * node + 2, mid + 1, end);
            tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
        }
    }
    
    public void update(int index, int val) {
        update(0, 0, n - 1, index, val);
    }
    
    private void update(int node, int start, int end, int idx, int val) {
        if (start == end) {
            tree[node] = val;
        } else {
            int mid = start + (end - start) / 2;
            if (idx <= mid) {
                update(2 * node + 1, start, mid, idx, val);
            } else {
                update(2 * node + 2, mid + 1, end, idx, val);
            }
            tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
        }
    }
    
    public int queryMax(int left, int right) {
        return query(0, 0, n - 1, left, right);
    }
    
    private int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l) return Integer.MIN_VALUE;
        if (l <= start && end <= r) return tree[node];
        
        int mid = start + (end - start) / 2;
        return Math.max(
            query(2 * node + 1, start, mid, l, r),
            query(2 * node + 2, mid + 1, end, l, r)
        );
    }
}
```

### Mind-Map Anchor

```
RANGE MAX QUERY
       |
       v
+------------------------+
| merge = Math.max()     |
| Identity = MIN_VALUE   |
| Same structure as sum  |
+------------------------+
```

---

## PATTERN 8: Count of Range Sum (LC 327)

### Pattern Recognition Signal

> **When you see:** "Count subarrays with sum in [lower, upper]"
> **Instant thought:** "Prefix sum + Segment Tree/Merge Sort!"

### The Key Insight

For subarray sum in range [lower, upper]:
- Compute prefix sums
- For each prefix[j], count prefix[i] where: lower ≤ prefix[j] - prefix[i] ≤ upper
- Rearranged: prefix[j] - upper ≤ prefix[i] ≤ prefix[j] - lower

### Visual Dry Run

```
nums = [-2, 5, -1], lower = -2, upper = 2

Prefix sums: [0, -2, 3, 2]

For j=1, prefix[1]=-2:
  Need prefix[i] in [-2-2, -2-(-2)] = [-4, 0]
  prefix[0]=0 is in range → count 1
  
For j=2, prefix[2]=3:
  Need prefix[i] in [3-2, 3-(-2)] = [1, 5]
  prefix[1]=-2, prefix[0]=0 not in range → count 0
  
For j=3, prefix[3]=2:
  Need prefix[i] in [2-2, 2-(-2)] = [0, 4]
  prefix[0]=0, prefix[2]=3 in range → count 2

Total: 1 + 0 + 2 = 3
Subarrays: [-2], [-1], [5,-1]
```

### The Code (Merge Sort approach - cleaner)

```java
public int countRangeSum(int[] nums, int lower, int upper) {
    int n = nums.length;
    long[] prefix = new long[n + 1];
    for (int i = 0; i < n; i++) {
        prefix[i + 1] = prefix[i] + nums[i];
    }
    return mergeSort(prefix, 0, n, lower, upper);
}

private int mergeSort(long[] prefix, int start, int end, int lower, int upper) {
    if (start >= end) return 0;
    
    int mid = start + (end - start) / 2;
    int count = mergeSort(prefix, start, mid, lower, upper) +
                mergeSort(prefix, mid + 1, end, lower, upper);
    
    // Count valid pairs across left and right
    int j = mid + 1, k = mid + 1;
    for (int i = start; i <= mid; i++) {
        while (j <= end && prefix[j] - prefix[i] < lower) j++;
        while (k <= end && prefix[k] - prefix[i] <= upper) k++;
        count += k - j;
    }
    
    // Merge
    long[] sorted = new long[end - start + 1];
    int left = start, right = mid + 1, idx = 0;
    while (left <= mid && right <= end) {
        if (prefix[left] <= prefix[right]) {
            sorted[idx++] = prefix[left++];
        } else {
            sorted[idx++] = prefix[right++];
        }
    }
    while (left <= mid) sorted[idx++] = prefix[left++];
    while (right <= end) sorted[idx++] = prefix[right++];
    
    System.arraycopy(sorted, 0, prefix, start, sorted.length);
    return count;
}
```

### Mind-Map Anchor

```
COUNT OF RANGE SUM
        |
        v
+------------------------+
| Prefix sum array       |
| For each j, count i    |
| where prefix[i] in     |
| [prefix[j]-upper,      |
|  prefix[j]-lower]      |
| Use merge sort or      |
| segment tree           |
+------------------------+
```

**Memory phrase:** "Prefix sums + count in range during merge sort"

---

## PATTERN 9: Falling Squares (LC 699)

### Pattern Recognition Signal

> **When you see:** "Squares falling, find max height at each step"
> **Instant thought:** "Segment Tree for range max query + range update!"

### The Code

```java
public List<Integer> fallingSquares(int[][] positions) {
    // Coordinate compression
    Set<Integer> coords = new TreeSet<>();
    for (int[] pos : positions) {
        coords.add(pos[0]);
        coords.add(pos[0] + pos[1] - 1);
    }
    Map<Integer, Integer> index = new HashMap<>();
    int idx = 0;
    for (int c : coords) {
        index.put(c, idx++);
    }
    
    int[] tree = new int[4 * idx];
    int[] lazy = new int[4 * idx];
    
    List<Integer> result = new ArrayList<>();
    int maxHeight = 0;
    
    for (int[] pos : positions) {
        int left = index.get(pos[0]);
        int right = index.get(pos[0] + pos[1] - 1);
        int side = pos[1];
        
        // Query max height in range
        int baseHeight = queryMax(tree, lazy, 0, 0, idx - 1, left, right);
        int newHeight = baseHeight + side;
        
        // Update range with new height
        updateRange(tree, lazy, 0, 0, idx - 1, left, right, newHeight);
        
        maxHeight = Math.max(maxHeight, newHeight);
        result.add(maxHeight);
    }
    
    return result;
}

private void pushDown(int[] tree, int[] lazy, int node) {
    if (lazy[node] != 0) {
        tree[2 * node + 1] = Math.max(tree[2 * node + 1], lazy[node]);
        tree[2 * node + 2] = Math.max(tree[2 * node + 2], lazy[node]);
        lazy[2 * node + 1] = Math.max(lazy[2 * node + 1], lazy[node]);
        lazy[2 * node + 2] = Math.max(lazy[2 * node + 2], lazy[node]);
        lazy[node] = 0;
    }
}

private int queryMax(int[] tree, int[] lazy, int node, int start, int end, int l, int r) {
    if (r < start || end < l) return 0;
    if (l <= start && end <= r) return tree[node];
    
    pushDown(tree, lazy, node);
    int mid = start + (end - start) / 2;
    return Math.max(
        queryMax(tree, lazy, 2 * node + 1, start, mid, l, r),
        queryMax(tree, lazy, 2 * node + 2, mid + 1, end, l, r)
    );
}

private void updateRange(int[] tree, int[] lazy, int node, int start, int end, int l, int r, int val) {
    if (r < start || end < l) return;
    if (l <= start && end <= r) {
        tree[node] = Math.max(tree[node], val);
        lazy[node] = Math.max(lazy[node], val);
        return;
    }
    
    pushDown(tree, lazy, node);
    int mid = start + (end - start) / 2;
    updateRange(tree, lazy, 2 * node + 1, start, mid, l, r, val);
    updateRange(tree, lazy, 2 * node + 2, mid + 1, end, l, r, val);
    tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
}
```

### Mind-Map Anchor

```
FALLING SQUARES
      |
      v
+------------------------+
| Coordinate compression |
| Segment tree for max   |
| Query max in range     |
| Update range with new  |
| height                 |
+------------------------+
```

---

## PATTERN 10: My Calendar III (LC 732)

### Pattern Recognition Signal

> **When you see:** "Maximum overlapping intervals at any point"
> **Instant thought:** "Segment Tree with range add + range max!"

### The Code

```java
class MyCalendarThree {
    private Map<Integer, Integer> tree;
    private Map<Integer, Integer> lazy;
    
    public MyCalendarThree() {
        tree = new HashMap<>();
        lazy = new HashMap<>();
    }
    
    public int book(int start, int end) {
        update(start, end - 1, 0, 0, 1_000_000_000);
        return tree.getOrDefault(0, 0);
    }
    
    private void update(int s, int e, int node, int start, int end) {
        if (s > end || e < start) return;
        
        if (s <= start && end <= e) {
            tree.put(node, tree.getOrDefault(node, 0) + 1);
            lazy.put(node, lazy.getOrDefault(node, 0) + 1);
            return;
        }
        
        int mid = start + (end - start) / 2;
        update(s, e, 2 * node + 1, start, mid);
        update(s, e, 2 * node + 2, mid + 1, end);
        
        tree.put(node, lazy.getOrDefault(node, 0) + 
                Math.max(tree.getOrDefault(2 * node + 1, 0),
                        tree.getOrDefault(2 * node + 2, 0)));
    }
}
```

---

# PART 3: ADDITIONAL PATTERNS

## PATTERN 11: Range GCD Query

### Pattern Recognition Signal

> **When you see:** "GCD of range with updates"
> **Instant thought:** "Segment Tree with GCD merge!"

### The Code

```java
class RangeGCDQuery {
    private int[] tree;
    private int n;
    
    public RangeGCDQuery(int[] nums) {
        n = nums.length;
        tree = new int[4 * n];
        if (n > 0) build(nums, 0, 0, n - 1);
    }
    
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
    
    private void build(int[] nums, int node, int start, int end) {
        if (start == end) {
            tree[node] = nums[start];
        } else {
            int mid = start + (end - start) / 2;
            build(nums, 2 * node + 1, start, mid);
            build(nums, 2 * node + 2, mid + 1, end);
            tree[node] = gcd(tree[2 * node + 1], tree[2 * node + 2]);
        }
    }
    
    public void update(int index, int val) {
        update(0, 0, n - 1, index, val);
    }
    
    private void update(int node, int start, int end, int idx, int val) {
        if (start == end) {
            tree[node] = val;
        } else {
            int mid = start + (end - start) / 2;
            if (idx <= mid) {
                update(2 * node + 1, start, mid, idx, val);
            } else {
                update(2 * node + 2, mid + 1, end, idx, val);
            }
            tree[node] = gcd(tree[2 * node + 1], tree[2 * node + 2]);
        }
    }
    
    public int queryGCD(int left, int right) {
        return query(0, 0, n - 1, left, right);
    }
    
    private int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l) return 0;
        if (l <= start && end <= r) return tree[node];
        
        int mid = start + (end - start) / 2;
        int leftGCD = query(2 * node + 1, start, mid, l, r);
        int rightGCD = query(2 * node + 2, mid + 1, end, l, r);
        
        if (leftGCD == 0) return rightGCD;
        if (rightGCD == 0) return leftGCD;
        return gcd(leftGCD, rightGCD);
    }
}
```

### Mind-Map Anchor

```
RANGE GCD QUERY
      |
      v
+------------------------+
| merge = gcd(left,right)|
| Identity = 0           |
| gcd(0, x) = x          |
+------------------------+
```

---

## PATTERN 12: 2D Segment Tree (Range Sum 2D - Mutable)

### Pattern Recognition Signal

> **When you see:** "2D matrix with range sum and updates"
> **Instant thought:** "2D Segment Tree or 2D Fenwick Tree!"

### The Code (2D Fenwick Tree - simpler)

```java
class NumMatrix {
    private int[][] tree;
    private int[][] nums;
    private int m, n;
    
    public NumMatrix(int[][] matrix) {
        if (matrix.length == 0) return;
        m = matrix.length;
        n = matrix[0].length;
        tree = new int[m + 1][n + 1];
        nums = new int[m][n];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                update(i, j, matrix[i][j]);
            }
        }
    }
    
    public void update(int row, int col, int val) {
        int delta = val - nums[row][col];
        nums[row][col] = val;
        
        for (int i = row + 1; i <= m; i += i & (-i)) {
            for (int j = col + 1; j <= n; j += j & (-j)) {
                tree[i][j] += delta;
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return sum(row2 + 1, col2 + 1) - sum(row1, col2 + 1) 
             - sum(row2 + 1, col1) + sum(row1, col1);
    }
    
    private int sum(int row, int col) {
        int result = 0;
        for (int i = row; i > 0; i -= i & (-i)) {
            for (int j = col; j > 0; j -= j & (-j)) {
                result += tree[i][j];
            }
        }
        return result;
    }
}
```

### Mind-Map Anchor

```
2D FENWICK TREE
      |
      v
+------------------------+
| Nested loops for both  |
| dimensions             |
| Update: i += i & (-i)  |
|         j += j & (-j)  |
| Query: i -= i & (-i)   |
|        j -= j & (-j)   |
+------------------------+
```

---

# QUICK REFERENCE: All 12 Patterns

| # | Pattern | Data Structure | Key Technique |
|---|---------|----------------|---------------|
| 1 | Range Sum Mutable | Fenwick Tree | Point update, prefix sum |
| 2 | Count Smaller After | Fenwick Tree | Frequency counting |
| 3 | Count Inversions | Fenwick Tree | Left-to-right processing |
| 4 | Range Sum Mutable | Segment Tree | Build, update, query |
| 5 | Range Min Query | Segment Tree | Min merge function |
| 6 | Lazy Propagation | Segment Tree | Range updates |
| 7 | Range Max Query | Segment Tree | Max merge function |
| 8 | Count of Range Sum | Merge Sort/ST | Prefix sum + counting |
| 9 | Falling Squares | Segment Tree | Range max + update |
| 10 | My Calendar III | Segment Tree | Overlapping intervals |
| 11 | Range GCD Query | Segment Tree | GCD merge function |
| 12 | 2D Range Sum | 2D Fenwick | Nested BIT operations |

---

## Fenwick vs Segment Tree Decision

```
USE FENWICK TREE WHEN:
├── Point updates only
├── Prefix/range SUM queries
├── Simpler code preferred
└── Memory efficiency matters

USE SEGMENT TREE WHEN:
├── Range updates needed (lazy propagation)
├── Min/Max/GCD queries
├── Complex merge operations
└── Need more flexibility
```

---

## Complexity Comparison

| Operation | Fenwick Tree | Segment Tree |
|-----------|--------------|--------------|
| Build | O(n log n) | O(n) |
| Point Update | O(log n) | O(log n) |
| Range Update | ❌ | O(log n) with lazy |
| Point Query | O(log n) | O(log n) |
| Range Query | O(log n) | O(log n) |
| Space | O(n) | O(4n) |

---

## Common Traps Table

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| 0-indexed Fenwick | BIT needs 1-indexed | Use `tree[n+1]`, convert indices |
| Forgetting pushDown | Lazy values not propagated | Always pushDown before recursing |
| Wrong tree size | Segment tree needs 4n | Use `tree[4 * n]` |
| Identity value | Wrong default for min/max | Use MAX_VALUE for min, MIN_VALUE for max |
| Range boundaries | Off-by-one errors | Draw the tree, trace carefully |

---

*End of Segment Tree & Fenwick Tree Deep Dive - 12 Patterns for L5 MAANG*


# Bucket C: Matrix Search

## 🧠 Core Mental Model

> **A sorted matrix is a search space with structure. Exploit the structure to eliminate rows or columns in one step.**

There are only **3 search strategies** for matrices. Every problem maps to one:

| Strategy | When to use | Elimination per step |
|---|---|---|
| **Flatten to 1D Binary Search** | Fully sorted (row-major) | Half the matrix |
| **Staircase Search** | Row-wise + Column-wise sorted | One row or one column |
| **Binary Search on Columns** | Peak / extrema problems | Half the columns |

---

## Strategy 1: Flatten to 1D Binary Search

### When: Matrix is **fully sorted** — last element of row i < first element of row i+1

```
1   3   5   7
10  11  16  20
23  30  34  60
```

This is really just a **sorted 1D array wrapped into rows**. Treat it as one.

### The Bridge Formula

```
Given: m rows, n columns, linear index mid

Row = mid / n
Col = mid % n

Value at mid = matrix[mid / n][mid % n]
```

This is the **same index conversion** from Bucket A. It keeps paying off.

---

### Search a 2D Matrix (LC 74) ⭐⭐ — GOOGLE FAVORITE

**Problem**: Find if `target` exists in a row-sorted matrix where each row starts greater than the previous row's end.

```java
public boolean searchMatrix(int[][] matrix, int target) {
    int m = matrix.length, n = matrix[0].length;
    int left = 0, right = m * n - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        int value = matrix[mid / n][mid % n];   // THE KEY LINE

        if (value == target) return true;
        else if (value < target) left = mid + 1;
        else right = mid - 1;
    }
    return false;
}
```

**Why it works**: The matrix IS a sorted array — we just need `mid / n` and `mid % n` to convert back to 2D.

**Time**: O(log(m × n)) | **Space**: O(1)

**⚠️ Common mistake**: Using `mid / m` instead of `mid / n`. It's always **divide by columns** for row, **mod by columns** for col.

---

## Strategy 2: Staircase Search (Top-Right or Bottom-Left)

### When: Each row is sorted AND each column is sorted (independently)

```
1   4   7   11  15
2   5   8   12  19
3   6   9   16  22
10  13  14  17  24
18  21  23  26  30
```

**Key Insight**: Start at a corner where one direction increases and the other decreases.

### The Two Magic Corners

```
                    ← smaller
                    ┌──────────┐
                    │  TOP-RIGHT │  → dead end (both increase)
         smaller ↑  │  START ★  │
                    │           │  ↓ bigger
                    └──────────┘
                       bigger →

    Top-Right (0, n-1):    Go LEFT to decrease, DOWN to increase
    Bottom-Left (m-1, 0):  Go UP to decrease, RIGHT to increase
```

**Why not Top-Left or Bottom-Right?** At top-left, both right and down increase — you can't decide which way to go. At bottom-right, both left and up decrease — same problem.

---

### Search a 2D Matrix II (LC 240) ⭐⭐ — META FAVORITE

**Problem**: Row-wise and column-wise sorted matrix. Find if `target` exists.

```java
public boolean searchMatrix(int[][] matrix, int target) {
    int m = matrix.length, n = matrix[0].length;
    int row = 0, col = n - 1;   // Start at TOP-RIGHT

    while (row < m && col >= 0) {
        if (matrix[row][col] == target) {
            return true;
        } else if (matrix[row][col] > target) {
            col--;    // Too big → eliminate this column (go left)
        } else {
            row++;    // Too small → eliminate this row (go down)
        }
    }
    return false;
}
```

**Walk-through** with target = 9:
```
Start at 15 → too big → go left
At 11 → too big → go left
At 7  → too small → go down
At 8  → too small → go down
At 9  → FOUND ✓
```

**Why it works**: Each comparison eliminates an **entire row or column**. At top-right:
- If `current > target`: everything below in this column is also bigger → eliminate column
- If `current < target`: everything left in this row is also smaller → eliminate row

**Time**: O(m + n) | **Space**: O(1)

This is one of the most elegant algorithms in all of DSA. **One comparison = one row or column gone.**

---

### Count Negative Numbers in Sorted Matrix (LC 1351) ⭐

**Problem**: Row-wise and column-wise sorted in **non-increasing** order. Count all negatives.

```
 4   3   2  -1
 3   2   1  -1
 1   1  -1  -2
-1  -1  -2  -3
```

**Same staircase idea**, but we count instead of search.

```java
public int countNegatives(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int count = 0;
    int row = 0, col = n - 1;   // Start top-right

    while (row < m && col >= 0) {
        if (grid[row][col] < 0) {
            count += (m - row);  // Everything below in this col is also negative
            col--;               // Move left
        } else {
            row++;               // Move down to find negatives
        }
    }
    return count;
}
```

**Key insight**: When you find a negative at `(row, col)`, ALL cells below it in that column are also negative (column is sorted). So add `(m - row)` in one shot, then move left.

**Time**: O(m + n) | **Space**: O(1)

---

## Strategy 3: Binary Search on Value Range / Columns

### When: Finding extrema (peak, kth smallest) — not just "does target exist?"

---

### Find Peak Element in 2D Matrix (LC 1901) ⭐⭐

**Problem**: Find any element that is strictly greater than all 4 neighbors. Matrix has no two adjacent equal elements.

**Key Insight**: Binary search on **columns**. For the middle column, find the **maximum**. Then decide which half contains a peak.

```java
public int[] findPeakGrid(int[][] mat) {
    int m = mat.length, n = mat[0].length;
    int left = 0, right = n - 1;

    while (left <= right) {
        int midCol = left + (right - left) / 2;

        // Find the row with max value in midCol
        int maxRow = 0;
        for (int i = 0; i < m; i++) {
            if (mat[i][midCol] > mat[maxRow][midCol])
                maxRow = i;
        }

        // Check if this max is a peak
        int leftVal = (midCol > 0) ? mat[maxRow][midCol - 1] : -1;
        int rightVal = (midCol < n - 1) ? mat[maxRow][midCol + 1] : -1;

        if (mat[maxRow][midCol] > leftVal && mat[maxRow][midCol] > rightVal) {
            return new int[]{maxRow, midCol};
        } else if (leftVal > mat[maxRow][midCol]) {
            right = midCol - 1;   // Peak must be on the left side
        } else {
            left = midCol + 1;    // Peak must be on the right side
        }
    }
    return new int[]{-1, -1};
}
```

**Why it works**:
1. Take the middle column, find its **maximum element** → this element is already greater than everything above and below in its column
2. Compare with left and right neighbors:
   - If both smaller → it's a peak!
   - If left is bigger → the left half **must** contain a peak (by pigeonhole/greedy argument)
   - If right is bigger → right half must contain a peak
3. Each step halves the columns

**Intuition**: The column maximum is already a "1D peak" vertically. We only need to verify it horizontally.

**Time**: O(m × log n) | **Space**: O(1)

---

### Kth Smallest Element in Sorted Matrix (LC 378) ⭐⭐

**Problem**: Row-wise and column-wise sorted matrix. Find the kth smallest element.

#### Approach 1: Min-Heap (Intuitive)

Think of it as merging k sorted lists.

```java
public int kthSmallest(int[][] matrix, int k) {
    int n = matrix.length;
    // PriorityQueue stores: [value, row, col]
    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

    // Add first element of each row
    for (int i = 0; i < Math.min(n, k); i++)
        minHeap.offer(new int[]{matrix[i][0], i, 0});

    int result = 0;
    for (int count = 0; count < k; count++) {
        int[] curr = minHeap.poll();
        result = curr[0];
        int row = curr[1], col = curr[2];

        // Add next element from the same row
        if (col + 1 < n)
            minHeap.offer(new int[]{matrix[row][col + 1], row, col + 1});
    }
    return result;
}
```

**Time**: O(k × log n) | **Space**: O(n)

#### Approach 2: Binary Search on Value Range (Optimal) ⭐

**Key Insight**: Binary search NOT on index, but on the **value**. Count how many elements are ≤ mid.

```java
public int kthSmallest(int[][] matrix, int k) {
    int n = matrix.length;
    int low = matrix[0][0];              // Smallest element
    int high = matrix[n - 1][n - 1];    // Largest element

    while (low < high) {
        int mid = low + (high - low) / 2;
        int count = countLessOrEqual(matrix, mid, n);

        if (count < k)
            low = mid + 1;     // Need more elements → increase value
        else
            high = mid;        // Enough elements → try smaller value
    }
    return low;
}

private int countLessOrEqual(int[][] matrix, int target, int n) {
    int count = 0;
    int row = n - 1, col = 0;   // Start at BOTTOM-LEFT

    while (row >= 0 && col < n) {
        if (matrix[row][col] <= target) {
            count += (row + 1);  // All elements above in this col are also ≤ target
            col++;               // Move right
        } else {
            row--;               // Move up
        }
    }
    return count;
}
```

**Why bottom-left for counting?** At bottom-left, going up decreases and going right increases. When `matrix[row][col] <= target`, everything above it in that column is also ≤ target → add `row + 1` at once.

**Time**: O(n × log(max - min)) | **Space**: O(1)

**Which to choose?** 
- Heap is simpler to understand and implement under pressure
- Binary search on value is better for very large matrices

---

## LC 74 vs LC 240: The Critical Distinction

This trips up many people. Know the difference cold.

| | LC 74: Search a 2D Matrix | LC 240: Search a 2D Matrix II |
|---|---|---|
| **Sort property** | Fully sorted (row-major) — row i end < row i+1 start | Row-wise + column-wise sorted independently |
| **Strategy** | Flatten to 1D → Binary Search | Staircase from top-right or bottom-left |
| **Time** | O(log(m × n)) | O(m + n) |
| **Key test** | Is `matrix[i][n-1] < matrix[i+1][0]`? | Are rows sorted AND cols sorted? |
| **Visual** | One long sorted sequence | Each row sorted, each col sorted, but rows overlap |

**Quick decision**: Can you treat the entire matrix as one sorted array? → LC 74 approach. Otherwise → Staircase.

---

## Common Pitfalls & Edge Cases

### 1. Index Conversion: Divide by COLUMNS, not ROWS
```java
// ❌ WRONG
int row = mid / m;   // divides by rows
int col = mid % m;

// ✅ CORRECT
int row = mid / n;   // divides by columns
int col = mid % n;
```
**Memory aid**: You fill `n` columns before moving to the next row, so you divide by `n`.

### 2. Staircase: Starting Corner Matters
- **Top-right** `(0, n-1)`: one direction decreases, one increases ✅
- **Bottom-left** `(m-1, 0)`: one direction decreases, one increases ✅
- **Top-left** or **Bottom-right**: both directions go the same way ❌

### 3. Binary Search on Value: Answer May Not Exist in Matrix
In Kth smallest, `mid` might not be an actual matrix element. That's fine — we converge to the actual value because we use `low < high` and `high = mid` (not `mid - 1`).

### 4. Single Row or Single Column
- Degrades to standard 1D binary search or linear scan
- Staircase still works — it just moves in one direction

### 5. Negative Numbers
- All algorithms work the same with negatives
- Don't assume `matrix[0][0] >= 0`

---

## Pattern Recognition Cheat Sheet

| If the problem says... | Think... |
|---|---|
| "sorted matrix" + "search for target" | LC 74 (1D binary search) or LC 240 (staircase) — check sort type |
| "row-wise AND column-wise sorted" | Staircase search from top-right or bottom-left |
| "fully sorted" / "each row starts after previous ends" | Flatten to 1D → standard binary search |
| "kth smallest" in sorted matrix | Binary search on value range + staircase count |
| "peak element" in 2D | Binary search on columns + find column max |
| "count elements" in sorted matrix | Staircase with accumulation |

---

## Practice Order (Recommended)

1. **Search a 2D Matrix** (LC 74) — Foundation. Master 1D ↔ 2D index conversion in binary search.
2. **Search a 2D Matrix II** (LC 240) — The staircase pattern. Core interview problem.
3. **Count Negative Numbers** (LC 1351) — Staircase with counting. Quick confidence builder.
4. **Kth Smallest in Sorted Matrix** (LC 378) — Both heap and binary search approaches.
5. **Find Peak Element II** (LC 1901) — Advanced. Binary search on columns + column max.

---

## Time & Space Complexity Summary

| Problem | Time | Space | Strategy |
|---|---|---|---|
| Search 2D Matrix (LC 74) | O(log(m×n)) | O(1) | 1D Binary Search |
| Search 2D Matrix II (LC 240) | O(m + n) | O(1) | Staircase |
| Count Negatives (LC 1351) | O(m + n) | O(1) | Staircase |
| Kth Smallest — Heap (LC 378) | O(k log n) | O(n) | Min-Heap |
| Kth Smallest — BS (LC 378) | O(n log(max-min)) | O(1) | Binary Search on Value |
| Peak Element 2D (LC 1901) | O(m log n) | O(1) | Binary Search on Columns |

---

## Quick Revision Anchors

- **LC 74** = Flatten to 1D. `row = mid / cols`, `col = mid % cols`. Standard binary search.
- **LC 240** = Start top-right. `bigger → go down`, `smaller → go left`. One row/col eliminated per step.
- **Count negatives** = Same staircase as LC 240, but accumulate `(m - row)` when you find a negative.
- **Kth smallest** = Binary search on VALUE (not index). Count elements ≤ mid using bottom-left staircase.
- **2D Peak** = Binary search on columns. Find column max → compare left/right → halve.
- **The golden rule**: Fully sorted → 1D binary search. Row+Col sorted → staircase. Extrema → binary search on columns/values.

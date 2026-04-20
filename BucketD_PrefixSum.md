# Bucket D: Matrix Math & Prefix Sum

## 🧠 Core Mental Model

> **Prefix sum turns repeated range queries from O(m×n) each into O(1) each. You pay O(m×n) once to build it, then every query is instant.**

This bucket has **2 main ideas**:
1. **2D Prefix Sum** — the rectangle inclusion-exclusion trick
2. **Matrix Validation** — using sets/arrays to track constraints (Sudoku)

---

## The Big Idea: 2D Prefix Sum

### 1D Prefix Sum Recap (30 seconds)

```
Array:      [2, 4, 1, 3, 5]
Prefix:  [0, 2, 6, 7, 10, 15]

Sum(i..j) = prefix[j+1] - prefix[i]
Sum(1..3) = prefix[4] - prefix[1] = 10 - 2 = 8  → (4 + 1 + 3) ✓
```

### 2D Prefix Sum: The Rectangle Formula

For a matrix, `prefix[i][j]` = sum of all elements in the rectangle from `(0,0)` to `(i-1, j-1)`.

```
Original Matrix:            Prefix Sum Matrix (1-indexed):
1  2  3                     0  0  0  0
4  5  6                     0  1  3  6
7  8  9                     0  5  12 21
                            0  12 27 45
```

### Building the Prefix Sum

**Formula**:
```
prefix[i][j] = matrix[i-1][j-1]
             + prefix[i-1][j]     ← top
             + prefix[i][j-1]     ← left
             - prefix[i-1][j-1]   ← top-left (subtract double-count)
```

**Visual**:
```
┌──────────┬─────┐
│          │     │
│  prefix  │ top │
│ [i-1][j-1]     │
├──────────┼─────┤
│   left   │ NEW │
│          │(i,j)│
└──────────┴─────┘

NEW = matrix value + top + left - top-left (double counted)
```

```java
int[][] prefix = new int[m + 1][n + 1];  // 1-indexed, row/col 0 are padding

for (int i = 1; i <= m; i++)
    for (int j = 1; j <= n; j++)
        prefix[i][j] = matrix[i-1][j-1]
                      + prefix[i-1][j]
                      + prefix[i][j-1]
                      - prefix[i-1][j-1];
```

### Querying a Sub-Rectangle Sum

**Sum of rectangle from (r1, c1) to (r2, c2)** (0-indexed in original matrix):

```
sum = prefix[r2+1][c2+1]
    - prefix[r1][c2+1]       ← remove top portion
    - prefix[r2+1][c1]       ← remove left portion
    + prefix[r1][c1]         ← add back double-subtracted corner
```

**Visual — Inclusion-Exclusion**:
```
┌──────────────────────┐
│  A (double removed)  │  B (remove top)     │
│  prefix[r1][c1]      │  prefix[r1][c2+1]   │
├──────────────────────┤─────────────────────-│
│  C (remove left)     │  D (WANT THIS)      │
│  prefix[r2+1][c1]    │  prefix[r2+1][c2+1] │
└──────────────────────┘

D = Total - B - C + A
sum = prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]
```

**MEMORIZE THIS**: `Total - Top - Left + Corner` (because corner was subtracted twice).

Same logic as: Area of a region = Big rectangle - Top strip - Left strip + Corner overlap.

---

## Problem-by-Problem Breakdown

---

### 1. Range Sum Query 2D - Immutable (LC 304) ⭐⭐ — CRITICAL PATTERN

**Problem**: Given a matrix, answer multiple queries: "What's the sum of the sub-rectangle from (r1,c1) to (r2,c2)?"

```java
class NumMatrix {
    private int[][] prefix;

    public NumMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        prefix = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                prefix[i][j] = matrix[i-1][j-1]
                              + prefix[i-1][j]
                              + prefix[i][j-1]
                              - prefix[i-1][j-1];
    }

    public int sumRegion(int r1, int c1, int r2, int c2) {
        return prefix[r2+1][c2+1]
             - prefix[r1][c2+1]
             - prefix[r2+1][c1]
             + prefix[r1][c1];
    }
}
```

**Why 1-indexed prefix array?** Row 0 and column 0 are all zeros — this eliminates boundary checks. No `if (i == 0)` needed.

**Time**: O(m × n) to build, O(1) per query | **Space**: O(m × n)

---

### 2. Matrix Block Sum (LC 1314) ⭐

**Problem**: For each cell `(i, j)`, compute the sum of all elements in the sub-matrix from `(i-k, j-k)` to `(i+k, j+k)`, clamped to matrix bounds.

```
Input:                  k = 1
1  2  3                 Output:
4  5  6                 12  21  16
7  8  9                 27  45  33
                        24  39  28

For cell (0,0): sum of (0,0) to (1,1) = 1+2+4+5 = 12
For cell (1,1): sum of (0,0) to (2,2) = 1+2+3+4+5+6+7+8+9 = 45
```

**Approach**: Build prefix sum once, then query for each cell.

```java
public int[][] matrixBlockSum(int[][] mat, int k) {
    int m = mat.length, n = mat[0].length;

    // Build prefix sum
    int[][] prefix = new int[m + 1][n + 1];
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            prefix[i][j] = mat[i-1][j-1]
                          + prefix[i-1][j]
                          + prefix[i][j-1]
                          - prefix[i-1][j-1];

    // Query for each cell
    int[][] result = new int[m][n];
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            // Clamp boundaries
            int r1 = Math.max(0, i - k);
            int c1 = Math.max(0, j - k);
            int r2 = Math.min(m - 1, i + k);
            int c2 = Math.min(n - 1, j + k);

            result[i][j] = prefix[r2+1][c2+1]
                         - prefix[r1][c2+1]
                         - prefix[r2+1][c1]
                         + prefix[r1][c1];
        }
    }
    return result;
}
```

**Without prefix sum**: Each cell query = O(k²) → total O(m × n × k²). 
**With prefix sum**: Build O(m × n) + each query O(1) → total O(m × n).

**Time**: O(m × n) | **Space**: O(m × n)

---

### 3. Range Sum Query 2D - Mutable (LC 308) ⭐⭐⭐

**Problem**: Same as LC 304, but you can also **update** individual cells.

**The challenge**: If you update a cell, the prefix sum becomes stale. Rebuilding = O(m × n) per update.

#### Approach: 2D Binary Indexed Tree (Fenwick Tree)

This is advanced. Know it exists, understand the idea, code it if you're targeting top companies.

```java
class NumMatrix {
    private int[][] tree;
    private int[][] nums;
    private int m, n;

    public NumMatrix(int[][] matrix) {
        m = matrix.length;
        n = matrix[0].length;
        tree = new int[m + 1][n + 1];
        nums = new int[m][n];

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                update(i, j, matrix[i][j]);
    }

    public void update(int row, int col, int val) {
        int delta = val - nums[row][col];
        nums[row][col] = val;

        for (int i = row + 1; i <= m; i += i & (-i))
            for (int j = col + 1; j <= n; j += j & (-j))
                tree[i][j] += delta;
    }

    private int query(int row, int col) {
        int sum = 0;
        for (int i = row; i > 0; i -= i & (-i))
            for (int j = col; j > 0; j -= j & (-j))
                sum += tree[i][j];
        return sum;
    }

    public int sumRegion(int r1, int c1, int r2, int c2) {
        return query(r2 + 1, c2 + 1)
             - query(r1, c2 + 1)
             - query(r2 + 1, c1)
             + query(r1, c1);
    }
}
```

**Key Concepts**:
- `i & (-i)` gives the lowest set bit — this is what makes BIT work
- Update propagates **up** the tree: `i += i & (-i)`
- Query accumulates **down** the tree: `i -= i & (-i)`
- The **query formula is identical** to prefix sum: Total - Top - Left + Corner

**Time**: O(log m × log n) per update AND per query | **Space**: O(m × n)

**When to use which**:
| Scenario | Use |
|---|---|
| No updates, many queries | Plain 2D prefix sum (LC 304) |
| Updates + queries | 2D BIT / Fenwick Tree (LC 308) |
| Very few queries | Just compute each sum directly |

---

### 4. Valid Sudoku (LC 36) ⭐⭐

**Problem**: Check if a 9×9 Sudoku board is valid. Each row, column, and 3×3 box must contain digits 1-9 with no repeats.

**This is NOT a prefix sum problem** — it's a **constraint validation** problem using sets or boolean arrays.

```java
public boolean isValidSudoku(char[][] board) {
    // Track seen numbers for each row, column, and box
    boolean[][] rows = new boolean[9][9];
    boolean[][] cols = new boolean[9][9];
    boolean[][] boxes = new boolean[9][9];

    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (board[i][j] == '.') continue;

            int num = board[i][j] - '1';   // Map '1'-'9' to 0-8
            int boxIdx = (i / 3) * 3 + (j / 3);   // THE KEY FORMULA

            if (rows[i][num] || cols[j][num] || boxes[boxIdx][num])
                return false;

            rows[i][num] = true;
            cols[j][num] = true;
            boxes[boxIdx][num] = true;
        }
    }
    return true;
}
```

**The Box Index Formula**: `boxIdx = (i / 3) * 3 + (j / 3)`

```
Box layout:
┌───────┬───────┬───────┐
│ Box 0 │ Box 1 │ Box 2 │   i/3 = 0
├───────┼───────┼───────┤
│ Box 3 │ Box 4 │ Box 5 │   i/3 = 1
├───────┼───────┼───────┤
│ Box 6 │ Box 7 │ Box 8 │   i/3 = 2
└───────┴───────┴───────┘
  j/3=0   j/3=1   j/3=2

(i/3) * 3 + (j/3) maps any cell to its box number 0-8
```

**Example**: Cell (4, 7) → boxIdx = (4/3)*3 + (7/3) = 1*3 + 2 = 5 → Box 5 ✓

**Time**: O(81) = O(1) — fixed size | **Space**: O(81) = O(1)

---

## Building Prefix Sum: Step-by-Step Visual

For matrix:
```
1  2  3
4  5  6
```

**Step 1**: Create (m+1) × (n+1) prefix array initialized to 0:
```
0  0  0  0
0  _  _  _
0  _  _  _
```

**Step 2**: Fill cell by cell:
```
prefix[1][1] = mat[0][0] + prefix[0][1] + prefix[1][0] - prefix[0][0]
             = 1 + 0 + 0 - 0 = 1

prefix[1][2] = mat[0][1] + prefix[0][2] + prefix[1][1] - prefix[0][1]
             = 2 + 0 + 1 - 0 = 3

prefix[1][3] = mat[0][2] + prefix[0][3] + prefix[1][2] - prefix[0][2]
             = 3 + 0 + 3 - 0 = 6

prefix[2][1] = mat[1][0] + prefix[1][1] + prefix[2][0] - prefix[1][0]
             = 4 + 1 + 0 - 0 = 5

prefix[2][2] = mat[1][1] + prefix[1][2] + prefix[2][1] - prefix[1][1]
             = 5 + 3 + 5 - 1 = 12

prefix[2][3] = mat[1][2] + prefix[1][3] + prefix[2][2] - prefix[1][2]
             = 6 + 6 + 12 - 3 = 21
```

**Result**:
```
0   0   0   0
0   1   3   6
0   5   12  21
```

**Verify**: Sum of entire matrix = prefix[2][3] = 21 = 1+2+3+4+5+6 ✓

---

## Prefix Sum Query: Visual Walkthrough

Query: Sum from (0,1) to (1,2) in original matrix = 2+3+5+6 = 16

```
Using formula: prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]
             = prefix[2][3]      - prefix[0][3]      - prefix[2][1]      + prefix[0][1]
             = 21                - 0                  - 5                 + 0
             = 16 ✓
```

Visual:
```
    j=0  j=1  j=2
i=0 [ 1 | 2 | 3 ]
i=1 [ 4 | 5 | 6 ]

Query (0,1) to (1,2):
Total rectangle (0,0)→(1,2) = 21
Remove left strip (0,0)→(1,0) = 5
Result = 21 - 0 - 5 + 0 = 16 ✓
```

---

## The Inclusion-Exclusion Principle (Why + Corner?)

```
We want the shaded region:

┌────────┬──────────┐
│   A    │    B     │
│ (over- │ (remove) │
│ removed)│         │
├────────┼──────────┤
│   C    │ ████████ │
│(remove)│ ██WANT██ │
│        │ ████████ │
└────────┴──────────┘

Total    = A + B + C + WANT
- Top    = A + B
- Left   = A + C
───────────────────────
Subtotal = -A + WANT       ← A was removed TWICE!
+ Corner = +A
───────────────────────
Result   = WANT ✓
```

This is exactly the same as:
- Venn diagram: `|A ∪ B| = |A| + |B| - |A ∩ B|`
- Area calculation: Total - overlapping parts + doubly-subtracted part

---

## Common Pitfalls & Edge Cases

### 1. Off-by-One in Prefix Sum Indexing
```java
// ❌ WRONG: mixing 0-indexed and 1-indexed
prefix[i][j] = matrix[i][j] + prefix[i-1][j] + ...  // out of bounds when i=0

// ✅ CORRECT: prefix is 1-indexed, matrix is 0-indexed
prefix[i][j] = matrix[i-1][j-1] + prefix[i-1][j] + ...  // row 0 of prefix is padding
```

### 2. Forgetting to Clamp Boundaries in Block Sum
```java
// ❌ WRONG: i-k might be negative
int r1 = i - k;

// ✅ CORRECT: clamp to valid range
int r1 = Math.max(0, i - k);
int r2 = Math.min(m - 1, i + k);
```

### 3. Sudoku Box Index
```java
// ❌ WRONG: integer division matters
int boxIdx = i / 3 + j / 3;     // Box 0 and Box (0,3) both give 1

// ✅ CORRECT: multiply row-group by 3 first
int boxIdx = (i / 3) * 3 + (j / 3);
```

### 4. BIT (Fenwick Tree): 1-Indexed
BIT arrays are **always 1-indexed**. Index 0 is unused. Forgetting this causes silent wrong answers.

### 5. Integer Overflow
For large matrices with large values, prefix sums can overflow `int`. Use `long` if matrix has up to 10⁴ × 10⁴ cells with values up to 10⁴.

---

## Pattern Recognition Cheat Sheet

| If the problem says... | Think... |
|---|---|
| "sum of sub-rectangle" / "range sum" | 2D Prefix Sum |
| "multiple queries on same matrix" | Build prefix once, query O(1) |
| "sum around each cell" / "block sum" | Prefix sum + boundary clamping |
| "update + query" | 2D BIT (Fenwick Tree) |
| "validate rows/cols/boxes" | Boolean tracking arrays |
| "sudoku" | Box formula: `(i/3)*3 + (j/3)` |

---

## When Prefix Sum is Overkill

| Scenario | Better approach |
|---|---|
| Single query on the matrix | Just iterate — O(m × n) |
| Updating frequently, few queries | Recompute each time |
| Matrix is sparse (mostly zeros) | Hash map of non-zero positions |
| 1D subarray sum | 1D prefix sum (simpler) |

---

## Practice Order (Recommended)

1. **Range Sum Query 2D - Immutable** (LC 304) — The foundation. Master build + query formula.
2. **Matrix Block Sum** (LC 1314) — Apply prefix sum to every cell. Reinforces boundary clamping.
3. **Valid Sudoku** (LC 36) — Different skill (validation), but important matrix problem. Quick solve.
4. **Range Sum Query 2D - Mutable** (LC 308) — Advanced. Only if targeting FAANG-level.

---

## Time & Space Complexity Summary

| Problem | Build Time | Query Time | Space |
|---|---|---|---|
| Range Sum 2D Immutable (LC 304) | O(m × n) | O(1) | O(m × n) |
| Matrix Block Sum (LC 1314) | O(m × n) | O(1) per cell | O(m × n) |
| Range Sum 2D Mutable (LC 308) | O(m × n × log m × log n) | O(log m × log n) | O(m × n) |
| Valid Sudoku (LC 36) | — | O(81) = O(1) | O(81) = O(1) |

---

## Quick Revision Anchors

- **Build prefix**: `prefix[i][j] = matrix[i-1][j-1] + top + left - topLeft`. 1-indexed prefix, 0-indexed matrix.
- **Query sub-rectangle**: `prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]` → **Total - Top - Left + Corner**.
- **Why + Corner?** Corner gets subtracted twice (once by top, once by left). Add it back. Same as Venn diagram inclusion-exclusion.
- **Sudoku box**: `(i/3) * 3 + (j/3)` maps any cell to box 0-8.
- **Mutable?** Use 2D Fenwick Tree. Same query formula, but update propagates with `i += i & (-i)`.
- **Decision**: No updates → plain prefix sum. With updates → BIT. One-off query → just iterate.

# Bucket A: Matrix Traversal & Boundary Management

## 🧠 Core Mental Model

> **A matrix is a rectangle. Every traversal is just a rule for visiting cells in a specific order.**

There are only **4 traversal families**. Every problem maps to one of them.

---

## 1. Row / Column Scan (The Basics)

### Row-wise (Left → Right, Top → Bottom)
```
1  2  3
4  5  6
7  8  9
→ Output: 1 2 3 4 5 6 7 8 9
```

```java
for (int i = 0; i < rows; i++)
    for (int j = 0; j < cols; j++)
        visit(matrix[i][j]);
```

### Column-wise (Top → Bottom, Left → Right)
```
1  2  3
4  5  6
7  8  9
→ Output: 1 4 7 2 5 8 3 6 9
```

```java
for (int j = 0; j < cols; j++)
    for (int i = 0; i < rows; i++)
        visit(matrix[i][j]);
```

**When to use**: Simplest form. Building block for everything else.

---

## 2. Spiral / Boundary Traversal (The Onion Peel)

### Mental Image: 4 Walls Closing In

```
→ → → → ↓
↑       ↓
↑       ↓
↑ ← ← ← 
```

You maintain **4 boundaries**: `top`, `bottom`, `left`, `right`  
After completing each side, **shrink that boundary inward**.

### The Pattern (MEMORIZE THIS)

```
while (top <= bottom && left <= right):
    1. Go RIGHT along top row    → then top++
    2. Go DOWN along right col   → then right--
    3. Go LEFT along bottom row  → then bottom--  (check top <= bottom first!)
    4. Go UP along left col      → then left++    (check left <= right first!)
```

### Spiral Matrix (LC 54) — Read spiral order

```java
public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> result = new ArrayList<>();
    int top = 0, bottom = matrix.length - 1;
    int left = 0, right = matrix[0].length - 1;

    while (top <= bottom && left <= right) {
        // → Right along top row
        for (int j = left; j <= right; j++)
            result.add(matrix[top][j]);
        top++;

        // ↓ Down along right column
        for (int i = top; i <= bottom; i++)
            result.add(matrix[i][right]);
        right--;

        // ← Left along bottom row
        if (top <= bottom) {
            for (int j = right; j >= left; j--)
                result.add(matrix[bottom][j]);
            bottom--;
        }

        // ↑ Up along left column
        if (left <= right) {
            for (int i = bottom; i >= top; i--)
                result.add(matrix[i][left]);
            left++;
        }
    }
    return result;
}
```

**⚠️ Critical Gotcha**: The `if (top <= bottom)` and `if (left <= right)` checks before steps 3 and 4 prevent **double-counting** in non-square matrices.

### Spiral Matrix II (LC 59) — Generate matrix in spiral order

Same boundary logic, but instead of reading, you **write** values 1 to n².

```java
public int[][] generateMatrix(int n) {
    int[][] matrix = new int[n][n];
    int top = 0, bottom = n - 1, left = 0, right = n - 1;
    int num = 1;

    while (top <= bottom && left <= right) {
        for (int j = left; j <= right; j++)
            matrix[top][j] = num++;
        top++;

        for (int i = top; i <= bottom; i++)
            matrix[i][right] = num++;
        right--;

        if (top <= bottom) {
            for (int j = right; j >= left; j--)
                matrix[bottom][j] = num++;
            bottom--;
        }

        if (left <= right) {
            for (int i = bottom; i >= top; i--)
                matrix[i][left] = num++;
            left++;
        }
    }
    return matrix;
}
```

### Boundary Traversal (GFG) — Print only the outer border

This is spiral but **only the first layer** (no while loop needed).

```java
public List<Integer> boundaryTraversal(int[][] matrix) {
    List<Integer> result = new ArrayList<>();
    int rows = matrix.length, cols = matrix[0].length;

    // Top row (left to right)
    for (int j = 0; j < cols; j++)
        result.add(matrix[0][j]);

    // Right column (top+1 to bottom)
    for (int i = 1; i < rows; i++)
        result.add(matrix[i][cols - 1]);

    // Bottom row (right-1 to left) — only if more than 1 row
    if (rows > 1)
        for (int j = cols - 2; j >= 0; j--)
            result.add(matrix[rows - 1][j]);

    // Left column (bottom-1 to top+1) — only if more than 1 col
    if (cols > 1)
        for (int i = rows - 2; i >= 1; i--)
            result.add(matrix[i][0]);

    return result;
}
```

---

## 3. Diagonal Traversal

### Key Insight: Cells on the same diagonal share `i + j = constant`

```
(0,0)  (0,1)  (0,2)
(1,0)  (1,1)  (1,2)
(2,0)  (2,1)  (2,2)

Diagonal 0: i+j=0 → (0,0)
Diagonal 1: i+j=1 → (0,1), (1,0)
Diagonal 2: i+j=2 → (0,2), (1,1), (2,0)
Diagonal 3: i+j=3 → (1,2), (2,1)
Diagonal 4: i+j=4 → (2,2)
```

Total diagonals = `rows + cols - 1`

### Diagonal Traverse (LC 498) — Zigzag diagonals

Direction alternates: even diagonals go **up-right** ↗, odd diagonals go **down-left** ↙.

```java
public int[] findDiagonalOrder(int[][] mat) {
    int rows = mat.length, cols = mat[0].length;
    int[] result = new int[rows * cols];
    int idx = 0;

    for (int d = 0; d < rows + cols - 1; d++) {
        if (d % 2 == 0) {
            // Going UP-RIGHT ↗
            int r = Math.min(d, rows - 1);
            int c = d - r;
            while (r >= 0 && c < cols) {
                result[idx++] = mat[r][c];
                r--;
                c++;
            }
        } else {
            // Going DOWN-LEFT ↙
            int c = Math.min(d, cols - 1);
            int r = d - c;
            while (r < rows && c >= 0) {
                result[idx++] = mat[r][c];
                r++;
                c--;
            }
        }
    }
    return result;
}
```

**Key trick**: For each diagonal `d`, find the **starting cell**:
- Going up: start at `(min(d, rows-1), d - row)`
- Going down: start at `(d - col, min(d, cols-1))`

### Why are we using the diagonal number `d`?

Because `d` is not a random loop variable. It is the **identity of the anti-diagonal**.

For this traversal, every cell on one anti-diagonal satisfies:

```text
r + c = d
```

So when `d = 2`, the only valid cells are the ones whose row and column add up to `2`:

```text
(0,2), (1,1), (2,0)
```

That is why the code keeps deriving one coordinate from the other using `d`.

If you know `r`, then:

```text
c = d - r
```

If you know `c`, then:

```text
r = d - c
```

So the comparison is not really “compare with diagonal number” in the usual sense. The idea is:

- `d` tells us **which diagonal** we are currently visiting.
- `r + c = d` keeps us **on that same diagonal**.
- `Math.min(...)` keeps the starting point **inside matrix bounds**.

### Logic Behind This Block (UP-RIGHT ↗)

```java
int r = Math.min(d, rows - 1);
int c = d - r;
while (r >= 0 && c < cols) {
    result[idx++] = mat[r][c];
    r--;
    c++;
}
```

Think of it in 3 steps:

1. Pick diagonal `d`.
2. Find the **lowest possible row** on that diagonal.
3. Move **up-right** until you fall out of the matrix.

#### Step 1: Why `r = Math.min(d, rows - 1)`?

For diagonal `d`, the row cannot exceed:

- `d`, because `c = d - r` must stay non-negative
- `rows - 1`, because that is the last valid row

So we choose:

```java
r = Math.min(d, rows - 1);
```

This means:

- if the diagonal is still in the upper part, start from row `d`
- once `d` becomes larger than the last row, clamp to the bottom row

#### Step 2: Why `c = d - r`?

Because every cell on this diagonal must satisfy:

```text
r + c = d
```

So once `r` is fixed, the only column that keeps us on diagonal `d` is:

```java
c = d - r;
```

#### Step 3: Why `r--` and `c++`?

Moving up-right means:

- row decreases by `1`
- column increases by `1`

So:

```java
r--;
c++;
```

Notice the sum stays unchanged:

```text
(r - 1) + (c + 1) = r + c = d
```

So we stay on the same diagonal the whole time.

### Vice Versa Logic (DOWN-LEFT ↙)

```java
int c = Math.min(d, cols - 1);
int r = d - c;
while (r < rows && c >= 0) {
    result[idx++] = mat[r][c];
    r++;
    c--;
}
```

This is the exact mirror image.

Instead of starting from the **lowest row**, we start from the **rightmost column** on diagonal `d`.

#### Why `c = Math.min(d, cols - 1)`?

For diagonal `d`, the column cannot exceed:

- `d`, because `r = d - c` must stay non-negative
- `cols - 1`, because that is the last valid column

So we clamp it:

```java
c = Math.min(d, cols - 1);
```

#### Why `r = d - c`?

Same diagonal rule:

```text
r + c = d
```

Once `c` is fixed, `r` must be:

```java
r = d - c;
```

#### Why `r++` and `c--`?

Moving down-left means:

- row increases by `1`
- column decreases by `1`

Again the sum stays constant:

```text
(r + 1) + (c - 1) = r + c = d
```

So we still remain on the same diagonal.

### Dry Run Example

Take this `3 x 4` matrix:

```text
1   2   3   4
5   6   7   8
9  10  11  12
```

Here:

- `rows = 3`
- `cols = 4`
- total diagonals = `3 + 4 - 1 = 6`
- so `d` goes from `0` to `5`

#### `d = 0` (even, go UP-RIGHT)

```java
r = min(0, 2) = 0
c = 0 - 0 = 0
```

Visit:

```text
(0,0) = 1
```

Output so far:

```text
1
```

#### `d = 1` (odd, go DOWN-LEFT)

```java
c = min(1, 3) = 1
r = 1 - 1 = 0
```

Visit:

```text
(0,1) = 2
(1,0) = 5
```

Output so far:

```text
1, 2, 5
```

#### `d = 2` (even, go UP-RIGHT)

```java
r = min(2, 2) = 2
c = 2 - 2 = 0
```

Visit:

```text
(2,0) = 9
(1,1) = 6
(0,2) = 3
```

Output so far:

```text
1, 2, 5, 9, 6, 3
```

#### `d = 3` (odd, go DOWN-LEFT)

```java
c = min(3, 3) = 3
r = 3 - 3 = 0
```

Visit:

```text
(0,3) = 4
(1,2) = 7
(2,1) = 10
```

Output so far:

```text
1, 2, 5, 9, 6, 3, 4, 7, 10
```

#### `d = 4` (even, go UP-RIGHT)

```java
r = min(4, 2) = 2
c = 4 - 2 = 2
```

Visit:

```text
(2,2) = 11
(1,3) = 8
```

Output so far:

```text
1, 2, 5, 9, 6, 3, 4, 7, 10, 11, 8
```

#### `d = 5` (odd, go DOWN-LEFT)

```java
c = min(5, 3) = 3
r = 5 - 3 = 2
```

Visit:

```text
(2,3) = 12
```

Final output:

```text
1, 2, 5, 9, 6, 3, 4, 7, 10, 11, 8, 12
```

### One Sentence Summary

`d` is the diagonal id, `r + c = d` is the invariant that keeps you on that diagonal, and `Math.min(...)` chooses the farthest valid starting point before walking in the required direction.

### Print All Diagonals (Top-Left to Bottom-Right)

```java
// Anti-diagonals where i - j = constant
for (int d = -(cols - 1); d <= rows - 1; d++) {
    for (int i = 0; i < rows; i++) {
        int j = i - d;
        if (j >= 0 && j < cols)
            visit(matrix[i][j]);
    }
}
```

---

## 4. Snake / Zigzag Traversal

### Mental Image: A snake slithering row by row, flipping direction each row

### Snake Pattern (Row-wise zigzag)

```
→ 1  2  3
  6  5  4 ←
→ 7  8  9
```

```java
for (int i = 0; i < rows; i++) {
    if (i % 2 == 0) {
        // Left to right
        for (int j = 0; j < cols; j++)
            visit(matrix[i][j]);
    } else {
        // Right to left
        for (int j = cols - 1; j >= 0; j--)
            visit(matrix[i][j]);
    }
}
```

**The trick**: `i % 2` decides direction. That's it.

### Wave / Zigzag (Column-wise)

```
↓  ↑  ↓
1  4  7
2  5  8
3  6  9
```

```java
for (int j = 0; j < cols; j++) {
    if (j % 2 == 0) {
        for (int i = 0; i < rows; i++)       // Top to bottom
            visit(matrix[i][j]);
    } else {
        for (int i = rows - 1; i >= 0; i--)  // Bottom to top
            visit(matrix[i][j]);
    }
}
```

Same logic, just `j % 2` instead of `i % 2`.

---

## Index Conversion Cheat Sheet

This is used constantly across matrix problems.

| Operation | Formula |
|---|---|
| 2D → 1D (linear index) | `idx = i * cols + j` |
| 1D → 2D (row) | `i = idx / cols` |
| 1D → 2D (col) | `j = idx % cols` |
| Main diagonal | `i == j` |
| Anti-diagonal | `i + j == n - 1` |
| Same diagonal (↘) | `i - j` is constant |
| Same anti-diagonal (↗) | `i + j` is constant |

---

## Common Pitfalls & Edge Cases

### 1. Single Row or Single Column Matrix
```
[1, 2, 3]      → Spiral is just left-to-right
[[1], [2], [3]] → Spiral is just top-to-bottom
```
Your spiral code must handle these without double-counting.

### 2. Off-by-One in Boundary Traversal
- After `top++`, the next loop must start from the **new** `top`, not the old one
- The `if` guards before steps 3 & 4 in spiral are **non-negotiable**

### 3. Non-Square Matrices in Diagonal Traverse
- Starting position calculation changes for rectangular matrices
- Always compute starting `(r, c)` using `min()` to clamp to valid range

### 4. Empty Matrix
```java
if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
    return result;
```
Always check this first.

---

## Pattern Recognition Cheat Sheet

| If the problem says... | Think... |
|---|---|
| "spiral order" | 4 boundaries, shrink inward |
| "diagonal" | Group by `i + j` |
| "zigzag / snake" | `index % 2` flips direction |
| "boundary / border" | First & last row/col only |
| "layer by layer" | Spiral with `layer` variable |
| "rotate" | This is Bucket B (Transformations) |

---

## Practice Order (Recommended)

1. **Spiral Matrix** (LC 54) — The flagship problem. Master this first.
2. **Spiral Matrix II** (LC 59) — Same pattern, builds confidence.
3. **Boundary Traversal** (GFG) — Spiral but simpler, reinforces boundaries.
4. **Snake Pattern** (GFG) — Easy win, builds `% 2` intuition.
5. **Diagonal Traverse** (LC 498) — Hardest in this bucket. Do last.
6. **Zigzag/Wave Traversal** (GFG) — Similar to snake, quick solve.
7. **Print Diagonals** (GFG) — Reinforces `i + j` property.

---

## Time & Space Complexity Summary

| Problem | Time | Space |
|---|---|---|
| Row/Col scan | O(m × n) | O(1) extra |
| Spiral Matrix | O(m × n) | O(1) extra (output excluded) |
| Spiral Matrix II | O(n²) | O(n²) for the matrix |
| Boundary Traversal | O(m + n) | O(1) extra |
| Diagonal Traverse | O(m × n) | O(1) extra |
| Snake/Zigzag | O(m × n) | O(1) extra |

All traversals are **O(m × n)** — you visit each cell exactly once. No traversal problem requires extra space beyond the output.

---

## Quick Revision Anchors

- **Spiral** = 4 walls closing in. Right → Down → Left → Up. Shrink after each.
- **Diagonal** = `i + j` groups cells. Zigzag = flip direction on even/odd.
- **Snake** = `row % 2` flips direction. Done.
- **Boundary** = Spiral's first layer only.
- **Index conversion** = `i * cols + j` ↔ `idx / cols, idx % cols`

=== Technical Concepts Used ===

- Concept: Diagonal invariant `r + c = d`.
    File: `BucketA_Traversals.md`
    Line/Range: `176-231`
    Reason: This is the core idea that lets one loop variable represent a whole anti-diagonal.
    Docs: https://leetcode.com/problems/diagonal-traverse/
- Concept: Boundary clamping with `Math.min(...)`.
    File: `BucketA_Traversals.md`
    Line/Range: `232-383`
    Reason: The starting row or column is clamped to the last valid index before traversing within one diagonal.
    Docs: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Math.html#min(int,int)

=== Learning Radar ===

- Already Known: Matrix traversal families and the fact that diagonal traversal groups cells by an index relationship.
- Newly Introduced: Using `d` as the anti-diagonal id, deriving one coordinate with `d - otherCoordinate`, and clamping the start with `Math.min(...)`.
- Needs Deeper Review: Why the chosen starting point changes with direction, and how the same invariant works for rectangular matrices.

=== Daily Learning Entry (Markdown) ===

## 2026-04-11

**Task Summary:** Expanded the diagonal traversal notes with a dry run explaining why `d` identifies a diagonal, how `r + c = d` keeps traversal on that diagonal, and why `Math.min(...)` is used to clamp the starting cell.

**Concepts:** Diagonal invariant, coordinate derivation using `d - x`, start-point clamping, up-right vs down-left mirror traversal.

**Radar:**
- Already Known: Basic matrix traversal patterns.
- Newly Introduced: Deriving diagonal start positions from the diagonal id.
- Needs Deeper Review: Fast visual recognition of start cells for any `d` in non-square matrices.

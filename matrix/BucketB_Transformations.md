# Bucket B: Matrix Rotation & Transformation

## 🧠 Core Mental Model

> **Every transformation is an axis operation. Transpose, reverse, or both. That's all there is.**

Once you see that rotation = transpose + reverse, you'll never forget it. Every problem in this bucket is a **combination of 2-3 basic moves**.

---

## The 3 Atomic Operations (MEMORIZE THESE)

Everything in this bucket is built from just 3 moves:

### 1. Transpose — Mirror along main diagonal

Swap `(i, j) ↔ (j, i)`

```
1 2 3       1 4 7
4 5 6  →    2 5 8
7 8 9       3 6 9
```

```java
// In-place transpose (only for square matrices)
for (int i = 0; i < n; i++)
    for (int j = i + 1; j < n; j++) {   // j starts at i+1 to avoid double-swap
        int temp = matrix[i][j];
        matrix[i][j] = matrix[j][i];
        matrix[j][i] = temp;
    }
```

**⚠️ Critical**: `j` starts at `i + 1`, NOT `0`. Starting at `0` swaps twice = no change.

For **non-square** matrices (m × n → n × m), you need a **new matrix**:

```java
int[][] result = new int[cols][rows];
for (int i = 0; i < rows; i++)
    for (int j = 0; j < cols; j++)
        result[j][i] = matrix[i][j];
```

### 2. Reverse Each Row — Mirror along vertical axis

```
1 2 3       3 2 1
4 5 6  →    6 5 4
7 8 9       9 8 7
```

```java
for (int i = 0; i < n; i++) {
    int left = 0, right = n - 1;
    while (left < right) {
        int temp = matrix[i][left];
        matrix[i][left] = matrix[i][right];
        matrix[i][right] = temp;
        left++;
        right--;
    }
}
```

### 3. Reverse Each Column — Mirror along horizontal axis

```
1 2 3       7 8 9
4 5 6  →    4 5 6
7 8 9       1 2 3
```

```java
for (int j = 0; j < n; j++) {
    int top = 0, bottom = n - 1;
    while (top < bottom) {
        int temp = matrix[top][j];
        matrix[top][j] = matrix[bottom][j];
        matrix[bottom][j] = temp;
        top++;
        bottom--;
    }
}
```

---

## The Rotation Cheat Sheet (THE KEY TABLE)

| Rotation | Formula | Steps |
|---|---|---|
| **90° Clockwise** | `new[j][n-1-i] = old[i][j]` | Transpose → Reverse each row |
| **90° Counter-Clockwise** | `new[n-1-j][i] = old[i][j]` | Transpose → Reverse each column |
| **180°** | `new[n-1-i][n-1-j] = old[i][j]` | Reverse each row → Reverse each column |

**Mental Anchor**: 
- **CW 90°** = Transpose + Reverse Rows (**TR**)
- **CCW 90°** = Transpose + Reverse Columns (**TC**)
- **180°** = Reverse Rows + Reverse Columns (**RC**) (or just do CW 90° twice)

Think: **"Transpose Right, Transpose Column, Rows Columns"** → TR, TC, RC

---

## Problem-by-Problem Breakdown

---

### 1. Rotate Image 90° Clockwise (LC 48) ⭐⭐

**The most important problem in this bucket. AMAZON / GOOGLE favorite.**

```
1 2 3       7 4 1
4 5 6  →    8 5 2
7 8 9       9 6 3
```

**Approach**: Transpose → Reverse each row

```java
public void rotate(int[][] matrix) {
    int n = matrix.length;

    // Step 1: Transpose
    for (int i = 0; i < n; i++)
        for (int j = i + 1; j < n; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
        }

    // Step 2: Reverse each row
    for (int i = 0; i < n; i++) {
        int left = 0, right = n - 1;
        while (left < right) {
            int temp = matrix[i][left];
            matrix[i][left] = matrix[i][right];
            matrix[i][right] = temp;
            left++;
            right--;
        }
    }
}
```

**Why it works (Intuition)**:
```
Original:       Transpose:       Reverse rows:
1 2 3           1 4 7            7 4 1
4 5 6    →      2 5 8     →      8 5 2
7 8 9           3 6 9            9 6 3  ✓
```
- Transpose moves elements to the right row but wrong column order
- Reverse rows fixes the column order

**Time**: O(n²) | **Space**: O(1) — fully in-place

---

### 2. Rotate Image 90° Counter-Clockwise (Variation)

```
1 2 3       3 6 9
4 5 6  →    2 5 8
7 8 9       1 4 7
```

**Approach**: Transpose → Reverse each column

```java
public void rotateCounterClockwise(int[][] matrix) {
    int n = matrix.length;

    // Step 1: Transpose
    for (int i = 0; i < n; i++)
        for (int j = i + 1; j < n; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
        }

    // Step 2: Reverse each column
    for (int j = 0; j < n; j++) {
        int top = 0, bottom = n - 1;
        while (top < bottom) {
            int temp = matrix[top][j];
            matrix[top][j] = matrix[bottom][j];
            matrix[bottom][j] = temp;
            top++;
            bottom--;
        }
    }
}
```

---

### 3. Rotate Image 180° (Variation)

```
1 2 3       9 8 7
4 5 6  →    6 5 4
7 8 9       3 2 1
```

**Approach**: Reverse each row → Reverse each column (or just rotate 90° twice)

```java
public void rotate180(int[][] matrix) {
    int n = matrix.length;

    // Step 1: Reverse each row
    for (int i = 0; i < n; i++) {
        int left = 0, right = n - 1;
        while (left < right) {
            int temp = matrix[i][left];
            matrix[i][left] = matrix[i][right];
            matrix[i][right] = temp;
            left++;
            right--;
        }
    }

    // Step 2: Reverse each column
    for (int j = 0; j < n; j++) {
        int top = 0, bottom = n - 1;
        while (top < bottom) {
            int temp = matrix[top][j];
            matrix[top][j] = matrix[bottom][j];
            matrix[bottom][j] = temp;
            top++;
            bottom--;
        }
    }
}
```

**Alternative** (simpler to remember): Just call 90° CW rotation twice.

---

### 4. Transpose Matrix (LC 867)

For **non-square** m × n matrix → n × m matrix.

```
1 2 3       1 4
4 5 6  →    2 5
            3 6
```

```java
public int[][] transpose(int[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    int[][] result = new int[n][m];

    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++)
            result[j][i] = matrix[i][j];

    return result;
}
```

**Note**: Non-square transpose **cannot be done in-place** — dimensions change.

**Time**: O(m × n) | **Space**: O(m × n)

---

### 5. Set Matrix Zeroes (LC 73) ⭐⭐

**AMAZON FAVORITE. Asked very frequently.**

If any cell is `0`, set its **entire row and column** to `0`.

```
1 1 1       1 0 1
1 0 1  →    0 0 0
1 1 1       1 0 1
```

#### Approach 1: Brute Force — O(m + n) extra space

Use separate boolean arrays for rows and columns.

```java
public void setZeroes(int[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    boolean[] zeroRow = new boolean[m];
    boolean[] zeroCol = new boolean[n];

    // Pass 1: Find zeroes
    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++)
            if (matrix[i][j] == 0) {
                zeroRow[i] = true;
                zeroCol[j] = true;
            }

    // Pass 2: Set zeroes
    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++)
            if (zeroRow[i] || zeroCol[j])
                matrix[i][j] = 0;
}
```

#### Approach 2: Optimal — O(1) extra space ⭐

**Key Insight**: Use **first row and first column as marker arrays** instead of extra boolean arrays.

```java
public void setZeroes(int[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    boolean firstRowZero = false, firstColZero = false;

    // Check if first row has zero
    for (int j = 0; j < n; j++)
        if (matrix[0][j] == 0) firstRowZero = true;

    // Check if first col has zero
    for (int i = 0; i < m; i++)
        if (matrix[i][0] == 0) firstColZero = true;

    // Use first row/col as markers for rest of matrix
    for (int i = 1; i < m; i++)
        for (int j = 1; j < n; j++)
            if (matrix[i][j] == 0) {
                matrix[i][0] = 0;   // Mark row
                matrix[0][j] = 0;   // Mark column
            }

    // Zero out cells based on markers (skip first row/col)
    for (int i = 1; i < m; i++)
        for (int j = 1; j < n; j++)
            if (matrix[i][0] == 0 || matrix[0][j] == 0)
                matrix[i][j] = 0;

    // Handle first row
    if (firstRowZero)
        for (int j = 0; j < n; j++)
            matrix[0][j] = 0;

    // Handle first column
    if (firstColZero)
        for (int i = 0; i < m; i++)
            matrix[i][0] = 0;
}
```

**Why 2 separate booleans?** First row and first col are being **used** as markers, so their original state needs separate tracking.

**Order matters**: Zero out inner cells FIRST, then first row/col. Reversing this corrupts markers.

**Time**: O(m × n) | **Space**: O(1)

---

### 6. Reshape the Matrix (LC 566) ⭐

Convert m × n matrix to r × c matrix (if valid).

```
1 2        1 2 3 4
3 4   →    (2×2 → 1×4)
```

**Key Insight**: Use linear index conversion.

```java
public int[][] matrixReshape(int[][] mat, int r, int c) {
    int m = mat.length, n = mat[0].length;

    // Can't reshape if total elements differ
    if (m * n != r * c) return mat;

    int[][] result = new int[r][c];
    for (int i = 0; i < m * n; i++) {
        // 1D → 2D for source
        int oldRow = i / n;
        int oldCol = i % n;
        // 1D → 2D for destination
        int newRow = i / c;
        int newCol = i % c;
        result[newRow][newCol] = mat[oldRow][oldCol];
    }
    return result;
}
```

**The trick**: Flatten mentally to 1D with `idx = i * cols + j`, then un-flatten to new dimensions with `i = idx / newCols`, `j = idx % newCols`.

**Time**: O(m × n) | **Space**: O(r × c)

---

### 7. Flipping an Image (LC 832) ⭐

Flip horizontally (reverse each row), then invert (0↔1).

```
1 1 0       1 0 0
1 0 1  →    0 1 0
0 0 0       1 1 1
```

**Key Insight**: Combine flip + invert in a single pass using two-pointer.

```java
public int[][] flipAndInvertImage(int[][] image) {
    for (int[] row : image) {
        int left = 0, right = row.length - 1;
        while (left <= right) {
            // Swap and invert in one step
            int temp = row[left] ^ 1;       // invert left
            row[left] = row[right] ^ 1;     // place inverted right at left
            row[right] = temp;              // place inverted left at right
            left++;
            right--;
        }
    }
    return image;
}
```

**Why `left <= right` (not `<`)?** When `left == right` (middle element), it still needs to be inverted.

**XOR trick**: `x ^ 1` flips `0 → 1` and `1 → 0`.

**Time**: O(m × n) | **Space**: O(1)

---

## The Big Picture: How All Transformations Connect

```
                    Transpose
                   ┌─────────┐
                   │ swap     │
         Original  │ (i,j) ↔  │  Transposed
           Matrix  │ (j,i)   │  Matrix
                   └─────────┘
                        │
              ┌─────────┼─────────┐
              │         │         │
        Reverse Rows  Reverse Cols  Both
              │         │         │
              ▼         ▼         ▼
         90° CW     90° CCW    180°
```

**Everything flows from transpose.** It's the universal building block.

---

## In-Place vs New Matrix Decision Guide

| Scenario | In-Place? | Why |
|---|---|---|
| Square matrix rotation | ✅ Yes | Dimensions don't change |
| Non-square transpose | ❌ No | m × n → n × m (different shape) |
| Set zeroes | ✅ Yes | Same dimensions, use first row/col as markers |
| Reshape | ❌ No | Dimensions change |
| Flip + Invert | ✅ Yes | Same dimensions, two-pointer swap |

**Rule**: If dimensions change, you need a new matrix. If same dimensions, aim for in-place.

---

## Common Pitfalls & Edge Cases

### 1. Transpose: Double-Swap Bug
```java
// ❌ WRONG: swaps twice = no change
for (int i = 0; i < n; i++)
    for (int j = 0; j < n; j++)    // j starts at 0
        swap(matrix[i][j], matrix[j][i]);

// ✅ CORRECT: only swap upper triangle
for (int i = 0; i < n; i++)
    for (int j = i + 1; j < n; j++)  // j starts at i+1
        swap(matrix[i][j], matrix[j][i]);
```

### 2. Set Zeroes: Marker Corruption
If you process first row/col before inner cells, you lose the original marker info. **Always process inner cells first.**

### 3. Rotation Direction Confusion
Quick test: Where does top-right corner `(0, n-1)` go?
- **CW 90°**: `(0, n-1) → (n-1, n-1)` (top-right → bottom-right)
- **CCW 90°**: `(0, n-1) → (0, 0)` (top-right → top-left)
- **180°**: `(0, n-1) → (n-1, 0)` (top-right → bottom-left)

### 4. Single Element / Single Row / Single Column
- 1×1 matrix: All rotations return the same matrix
- 1×n row: 90° CW turns it into n×1 column (can't be in-place)

---

## Pattern Recognition Cheat Sheet

| If the problem says... | Think... |
|---|---|
| "rotate 90°" | Transpose + Reverse rows (CW) or cols (CCW) |
| "rotate 180°" | Reverse rows + Reverse cols |
| "transpose" | Swap `(i,j) ↔ (j,i)`, watch for non-square |
| "set zeroes" / "propagate" | In-place marking with first row/col |
| "reshape" / "flatten" | Linear index: `i*cols+j` ↔ `idx/cols, idx%cols` |
| "flip" / "mirror" | Reverse rows (horizontal) or cols (vertical) |
| "in-place" | Use the matrix itself to store state |

---

## Practice Order (Recommended)

1. **Rotate Image 90° CW** (LC 48) — The king problem. Nail transpose + reverse.
2. **Transpose Matrix** (LC 867) — Solidify the transpose operation.
3. **Set Matrix Zeroes** (LC 73) — Master in-place marking. High interview frequency.
4. **Reshape Matrix** (LC 566) — Reinforce index conversion.
5. **Flipping an Image** (LC 832) — Fun two-pointer + XOR combo.
6. **Rotate 90° CCW** (variation) — Confirm you understand the CW/CCW difference.
7. **Rotate 180°** (variation) — Quick combo of reverses.

---

## Time & Space Complexity Summary

| Problem | Time | Space |
|---|---|---|
| Rotate 90° CW (in-place) | O(n²) | O(1) |
| Rotate 90° CCW (in-place) | O(n²) | O(1) |
| Rotate 180° (in-place) | O(n²) | O(1) |
| Transpose (square, in-place) | O(n²) | O(1) |
| Transpose (non-square) | O(m × n) | O(m × n) |
| Set Matrix Zeroes (optimal) | O(m × n) | O(1) |
| Reshape Matrix | O(m × n) | O(r × c) |
| Flip and Invert Image | O(m × n) | O(1) |

---

## Quick Revision Anchors

- **Rotation** = Transpose + one Reverse. CW → reverse rows. CCW → reverse cols. 180° → both reverses.
- **Transpose** = swap `(i,j) ↔ (j,i)`. Loop `j` from `i+1` to avoid double-swap.
- **Set Zeroes** = Use first row/col as markers. Save their state in 2 booleans. Process inner first.
- **Reshape** = Flatten to 1D index, un-flatten to new dimensions. `i*cols+j` is the bridge.
- **Flip** = Two-pointer + XOR. `left <= right` to handle middle element.
- **In-place rule**: Same dimensions → in-place possible. Different dimensions → new matrix needed.

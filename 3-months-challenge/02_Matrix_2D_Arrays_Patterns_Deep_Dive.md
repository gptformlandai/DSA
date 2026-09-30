# Matrix & 2D Arrays Patterns Deep Dive
## Junior Dev's Complete Guide to L5 MAANG Matrix Mastery

--

# THE MATRIX MINDSET: Before You Code Anything

## The One Sentence That Unlocks Matrix Problems

> **Matrix = 2D array where position (row, col) matters. Master traversal directions and boundary handling.**

## When to Use What?

```
TRAVERSAL PROBLEMS:
  -> Spiral, Diagonal, Zigzag patterns

SEARCH PROBLEMS:
  -> Binary Search (sorted matrix)
  -> Staircase Search (row+col sorted)

TRANSFORMATION PROBLEMS:
  -> Rotate, Transpose, Flip

MODIFICATION PROBLEMS:
  -> Set Zeroes, Fill patterns

GRAPH ON MATRIX:
  -> DFS/BFS for islands, paths
```

--

## The 4 Direction Arrays (MEMORIZE!)

```java
// 4 directions: up, down, left, right
int[] dr = {-1, 1, 0, 0};
int[] dc = {0, 0, -1, 1};

// 8 directions: including diagonals
int[] dr8 = {-1, -1, -1, 0, 0, 1, 1, 1};
int[] dc8 = {-1, 0, 1, -1, 1, -1, 0, 1};

// Boundary check
boolean isValid(int r, int c, int rows, int cols) {
    return r >= 0 && r < rows && c >= 0 && c < cols;
}
```

--

# PART 1: TRAVERSAL PATTERNS

## PATTERN 1: Spiral Matrix (LC 54)

### Pattern Recognition Signal

> **When you see:** "Print matrix in spiral order"
> **Instant thought:** "Layer-by-layer, 4 boundaries shrinking inward!"

### The Mental Model

Think of peeling an onion layer by layer:
- Start from outer boundary
- Go Right → Down → Left → Up
- Shrink boundaries after each direction
- Repeat until boundaries cross

### Visual Dry Run

```
Matrix:
[1, 2, 3]
[4, 5, 6]
[7, 8, 9]

Initial: top=0, bottom=2, left=0, right=2

Layer 1:
  Right (top row): 1 → 2 → 3, then top++
  Down (right col): 6 → 9, then right-
  Left (bottom row): 8 → 7, then bottom-
  Up (left col): 4, then left++

Now: top=1, bottom=1, left=1, right=1

Layer 2:
  Right: 5, then top++
  
Now: top=2 > bottom=1, STOP

Result: [1, 2, 3, 6, 9, 8, 7, 4, 5]
```

### The Code

```java
public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> result = new ArrayList<>();
    if (matrix.length == 0) return result;
    
    int top = 0, bottom = matrix.length - 1;
    int left = 0, right = matrix[0].length - 1;
    
    while (top <= bottom && left <= right) {
        // Right: traverse top row
        for (int col = left; col <= right; col++) {
            result.add(matrix[top][col]);
        }
        top++;
        
        // Down: traverse right column
        for (int row = top; row <= bottom; row++) {
            result.add(matrix[row][right]);
        }
        right-;
        
        // Left: traverse bottom row (if still valid)
        if (top <= bottom) {
            for (int col = right; col >= left; col-) {
                result.add(matrix[bottom][col]);
            }
            bottom-;
        }
        
        // Up: traverse left column (if still valid)
        if (left <= right) {
            for (int row = bottom; row >= top; row-) {
                result.add(matrix[row][left]);
            }
            left++;
        }
    }
    
    return result;
}
```

### Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Missing boundary check | Left/Up may traverse invalid | Add `if (top <= bottom)` before Left |
| Off-by-one in loops | Wrong start/end indices | Draw and trace carefully |
| Rectangular matrix | Different rows vs cols | Handle non-square matrices |

### Mind-Map Anchor

```
SPIRAL MATRIX
     |
     v
+------------+
| 4 boundaries:          |
| top, bottom, left,right|
| Go: Right→Down→Left→Up |
| Shrink after each dir  |
| Check validity before  |
| Left and Up traversals |
+------------+
```

**Memory phrase:** "Peel the onion: Right-Down-Left-Up, shrink boundaries"

--

## PATTERN 2: Spiral Matrix II (LC 59)

### Pattern Recognition Signal

> **When you see:** "Generate n×n matrix in spiral order"
> **Instant thought:** "Same spiral logic, but FILL instead of READ!"

### Visual Dry Run

```
n = 3, fill 1 to 9

Start: empty 3x3 matrix
       top=0, bottom=2, left=0, right=2
       num = 1

Layer 1:
  Right: fill [0][0]=1, [0][1]=2, [0][2]=3, top++
  Down: fill [1][2]=4, [2][2]=5, right-
  Left: fill [2][1]=6, [2][0]=7, bottom-
  Up: fill [1][0]=8, left++

Layer 2:
  Right: fill [1][1]=9, top++
  
Result:
[1, 2, 3]
[8, 9, 4]
[7, 6, 5]
```

### The Code

```java
public int[][] generateMatrix(int n) {
    int[][] matrix = new int[n][n];
    int top = 0, bottom = n - 1;
    int left = 0, right = n - 1;
    int num = 1;
    
    while (top <= bottom && left <= right) {
        // Right
        for (int col = left; col <= right; col++) {
            matrix[top][col] = num++;
        }
        top++;
        
        // Down
        for (int row = top; row <= bottom; row++) {
            matrix[row][right] = num++;
        }
        right-;
        
        // Left
        if (top <= bottom) {
            for (int col = right; col >= left; col-) {
                matrix[bottom][col] = num++;
            }
            bottom-;
        }
        
        // Up
        if (left <= right) {
            for (int row = bottom; row >= top; row-) {
                matrix[row][left] = num++;
            }
            left++;
        }
    }
    
    return matrix;
}
```

### Mind-Map Anchor

```
SPIRAL MATRIX II
      |
      v
+------------+
| Same as Spiral I       |
| But FILL instead of    |
| READ                   |
| Use counter num++      |
+------------+
```

**Memory phrase:** "Same spiral, fill with incrementing counter"

--

## PATTERN 3: Diagonal Traverse (LC 498)

### Pattern Recognition Signal

> **When you see:** "Traverse matrix diagonally in zigzag"
> **Instant thought:** "Alternate direction, handle boundary transitions!"

### Visual Dry Run

```
Matrix:
[1, 2, 3]
[4, 5, 6]
[7, 8, 9]

Diagonals (alternating direction):
  Diagonal 0 (up-right): 1
  Diagonal 1 (down-left): 2 → 4
  Diagonal 2 (up-right): 7 → 5 → 3
  Diagonal 3 (down-left): 6 → 8
  Diagonal 4 (up-right): 9

Result: [1, 2, 4, 7, 5, 3, 6, 8, 9]
```

### The Code

```java
public int[] findDiagonalOrder(int[][] mat) {
    if (mat.length == 0) return new int[0];
    
    int m = mat.length, n = mat[0].length;
    int[] result = new int[m * n];
    int row = 0, col = 0;
    int idx = 0;
    boolean goingUp = true;
    
    while (idx < m * n) {
        result[idx++] = mat[row][col];
        
        if (goingUp) {
            if (col == n - 1) {
                // Hit right boundary, go down
                row++;
                goingUp = false;
            } else if (row == 0) {
                // Hit top boundary, go right
                col++;
                goingUp = false;
            } else {
                // Continue up-right
                row-;
                col++;
            }
        } else {
            if (row == m - 1) {
                // Hit bottom boundary, go right
                col++;
                goingUp = true;
            } else if (col == 0) {
                // Hit left boundary, go down
                row++;
                goingUp = true;
            } else {
                // Continue down-left
                row++;
                col-;
            }
        }
    }
    
    return result;
}
```

### Mind-Map Anchor

```
DIAGONAL TRAVERSE
       |
       v
+------------+
| Alternate up-right and |
| down-left directions   |
| On boundary: change    |
| direction + move       |
| Priority: corner cases |
+------------+
```

**Memory phrase:** "Zigzag diagonals, boundary triggers direction flip"

--

# PART 2: ROTATION & TRANSFORMATION

## PATTERN 4: Rotate Image 90° Clockwise (LC 48)

### Pattern Recognition Signal

> **When you see:** "Rotate matrix 90 degrees in-place"
> **Instant thought:** "Transpose + Reverse each row!"

### The Key Insight

```
90° clockwise = Transpose + Reverse rows
90° counter-clockwise = Transpose + Reverse columns
180° = Reverse rows + Reverse columns
```

### Visual Dry Run

```
Original:
[1, 2, 3]
[4, 5, 6]
[7, 8, 9]

Step 1: Transpose (swap [i][j] with [j][i])
[1, 4, 7]
[2, 5, 8]
[3, 6, 9]

Step 2: Reverse each row
[7, 4, 1]
[8, 5, 2]
[9, 6, 3]

Result: 90° clockwise rotation!
```

### The Code

```java
public void rotate(int[][] matrix) {
    int n = matrix.length;
    
    // Step 1: Transpose
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
        }
    }
    
    // Step 2: Reverse each row
    for (int i = 0; i < n; i++) {
        int left = 0, right = n - 1;
        while (left < right) {
            int temp = matrix[i][left];
            matrix[i][left] = matrix[i][right];
            matrix[i][right] = temp;
            left++;
            right-;
        }
    }
}
```

### Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Transpose full matrix | Swaps twice = no change | Only swap upper triangle (j > i) |
| Wrong rotation direction | Counter-clockwise instead | Transpose + reverse ROWS for clockwise |

### Mind-Map Anchor

```
ROTATE 90° CLOCKWISE
         |
         v
+------------+
| Step 1: Transpose      |
| (swap [i][j] ↔ [j][i]) |
| Step 2: Reverse rows   |
| In-place, O(1) space   |
+------------+
```

**Memory phrase:** "Transpose then reverse rows = 90° clockwise"

--

## PATTERN 5: Set Matrix Zeroes (LC 73)

### Pattern Recognition Signal

> **When you see:** "Set entire row and column to 0 if element is 0"
> **Instant thought:** "Use first row/col as markers for O(1) space!"

### The Key Insight

Instead of using extra O(m+n) space for markers, use the first row and first column of the matrix itself as markers.

### Visual Dry Run

```
Original:
[1, 1, 1]
[1, 0, 1]
[1, 1, 1]

Step 1: Check if first row/col have zeros
  firstRowZero = false, firstColZero = false

Step 2: Mark zeros in first row/col
  matrix[1][0] = 0 (row 1 has zero)
  matrix[0][1] = 0 (col 1 has zero)

Matrix after marking:
[1, 0, 1]
[0, 0, 1]
[1, 1, 1]

Step 3: Set zeros based on markers (skip first row/col)
[1, 0, 1]
[0, 0, 0]
[1, 0, 1]

Step 4: Handle first row/col if needed
  (no change needed in this case)

Result:
[1, 0, 1]
[0, 0, 0]
[1, 0, 1]
```

### The Code

```java
public void setZeroes(int[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    boolean firstRowZero = false, firstColZero = false;
    
    // Check if first row has zero
    for (int j = 0; j < n; j++) {
        if (matrix[0][j] == 0) {
            firstRowZero = true;
            break;
        }
    }
    
    // Check if first column has zero
    for (int i = 0; i < m; i++) {
        if (matrix[i][0] == 0) {
            firstColZero = true;
            break;
        }
    }
    
    // Use first row/col as markers
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            if (matrix[i][j] == 0) {
                matrix[i][0] = 0;  // Mark row
                matrix[0][j] = 0;  // Mark column
            }
        }
    }
    
    // Set zeros based on markers
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                matrix[i][j] = 0;
            }
        }
    }
    
    // Handle first row
    if (firstRowZero) {
        for (int j = 0; j < n; j++) {
            matrix[0][j] = 0;
        }
    }
    
    // Handle first column
    if (firstColZero) {
        for (int i = 0; i < m; i++) {
            matrix[i][0] = 0;
        }
    }
}
```

### Mind-Map Anchor

```
SET MATRIX ZEROES
       |
       v
+------------+
| Use first row/col as   |
| markers (O(1) space)   |
| 1. Check first row/col |
| 2. Mark in first row/col|
| 3. Set zeros (skip 1st)|
| 4. Handle first row/col|
+------------+
```

**Memory phrase:** "First row/col are markers, handle them last"

--

# PART 3: MATRIX SEARCH

## PATTERN 6: Search a 2D Matrix (LC 74)

### Pattern Recognition Signal

> **When you see:** "Sorted matrix (each row starts > previous row ends)"
> **Instant thought:** "Treat as 1D array, binary search!"

### Visual Dry Run

```
Matrix:
[1,  3,  5,  7]
[10, 11, 16, 20]
[23, 30, 34, 60]

Target: 16

Treat as 1D: [1, 3, 5, 7, 10, 11, 16, 20, 23, 30, 34, 60]
             indices 0-11

Binary search:
  mid = 5, value = matrix[5/4][5%4] = matrix[1][1] = 11 < 16
  mid = 8, value = matrix[8/4][8%4] = matrix[2][0] = 23 > 16
  mid = 6, value = matrix[6/4][6%4] = matrix[1][2] = 16 = target!

Found at row=1, col=2
```

### The Code

```java
public boolean searchMatrix(int[][] matrix, int target) {
    int m = matrix.length, n = matrix[0].length;
    int left = 0, right = m * n - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        int row = mid / n;
        int col = mid % n;
        int value = matrix[row][col];
        
        if (value == target) {
            return true;
        } else if (value < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return false;
}
```

### Mind-Map Anchor

```
SEARCH 2D MATRIX (SORTED)
           |
           v
+------------+
| Treat as 1D array      |
| row = mid / cols       |
| col = mid % cols       |
| Standard binary search |
| O(log(m*n)) time       |
+------------+
```

**Memory phrase:** "Flatten to 1D: row = mid/n, col = mid%n"

--

## PATTERN 7: Search a 2D Matrix II (LC 240)

### Pattern Recognition Signal

> **When you see:** "Row-wise AND column-wise sorted"
> **Instant thought:** "Staircase search from top-right or bottom-left!"

### The Key Insight

Start from top-right corner:
- If current > target: move LEFT (smaller values)
- If current < target: move DOWN (larger values)
- If equal: FOUND!

### Visual Dry Run

```
Matrix:
[1,  4,  7, 11, 15]
[2,  5,  8, 12, 19]
[3,  6,  9, 16, 22]
[10, 13, 14, 17, 24]
[18, 21, 23, 26, 30]

Target: 14

Start at top-right: (0, 4) = 15
  15 > 14 → move left
(0, 3) = 11
  11 < 14 → move down
(1, 3) = 12
  12 < 14 → move down
(2, 3) = 16
  16 > 14 → move left
(2, 2) = 9
  9 < 14 → move down
(3, 2) = 14 = target!

Found!
```

### The Code

```java
public boolean searchMatrix(int[][] matrix, int target) {
    int m = matrix.length, n = matrix[0].length;
    int row = 0, col = n - 1;  // Start top-right
    
    while (row < m && col >= 0) {
        if (matrix[row][col] == target) {
            return true;
        } else if (matrix[row][col] > target) {
            col-;  // Move left
        } else {
            row++;  // Move down
        }
    }
    
    return false;
}
```

### Mind-Map Anchor

```
SEARCH 2D MATRIX II
        |
        v
+------------+
| Start top-right corner |
| > target: go LEFT      |
| < target: go DOWN      |
| O(m + n) time          |
| "Staircase search"     |
+------------+
```

**Memory phrase:** "Top-right: bigger go left, smaller go down"

--

# PART 4: PREFIX SUM 2D

## PATTERN 8: Range Sum Query 2D - Immutable (LC 304)

### Pattern Recognition Signal

> **When you see:** "Multiple submatrix sum queries"
> **Instant thought:** "2D prefix sum! Precompute for O(1) queries"

### The Key Insight

```
prefix[i][j] = sum of all elements from (0,0) to (i-1,j-1)

Build:
prefix[i][j] = matrix[i-1][j-1] + prefix[i-1][j] + prefix[i][j-1] - prefix[i-1][j-1]

Query (r1,c1) to (r2,c2):
sum = prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]
```

### Visual Dry Run

```
Matrix:
[3, 0, 1, 4, 2]
[5, 6, 3, 2, 1]
[1, 2, 0, 1, 5]
[4, 1, 0, 1, 7]
[1, 0, 3, 0, 5]

Prefix Sum (1-indexed):
[0, 0,  0,  0,  0,  0]
[0, 3,  3,  4,  8, 10]
[0, 8, 14, 18, 24, 27]
[0, 9, 17, 21, 28, 36]
[0,13, 22, 26, 34, 49]
[0,14, 23, 30, 38, 58]

Query: sum from (2,1) to (4,3)
= prefix[5][4] - prefix[2][4] - prefix[5][1] + prefix[2][1]
= 38 - 24 - 14 + 8
= 8

Verify: 2+0+1 + 1+0+1 + 0+3+0 = 8 ✓
```

### The Code

```java
class NumMatrix {
    private int[][] prefix;
    
    public NumMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        prefix = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                prefix[i][j] = matrix[i-1][j-1] 
                             + prefix[i-1][j] 
                             + prefix[i][j-1] 
                             - prefix[i-1][j-1];
            }
        }
    }
    
    public int sumRegion(int r1, int c1, int r2, int c2) {
        return prefix[r2+1][c2+1] 
             - prefix[r1][c2+1] 
             - prefix[r2+1][c1] 
             + prefix[r1][c1];
    }
}
```

### Mind-Map Anchor

```
2D PREFIX SUM
      |
      v
+------------+
| prefix[i][j] = sum of  |
| rectangle (0,0)→(i-1,j-1)|
| Build: add 3, subtract 1|
| Query: add 2, subtract 2|
| O(1) per query         |
+------------+
```

**Memory phrase:** "Build: +top +left -diagonal. Query: +BR -TR -BL +TL"

--

# PART 5: MATRIX AS GRAPH (Preview)

## PATTERN 9: Number of Islands (LC 200)

### Pattern Recognition Signal

> **When you see:** "Count connected regions in grid"
> **Instant thought:** "DFS/BFS from each unvisited '1', mark visited!"

### Visual Dry Run

```
Grid:
[1, 1, 0, 0, 0]
[1, 1, 0, 0, 0]
[0, 0, 1, 0, 0]
[0, 0, 0, 1, 1]

DFS from (0,0): marks all connected 1s
  Visit: (0,0), (0,1), (1,0), (1,1)
  Island count = 1

DFS from (2,2): marks single 1
  Visit: (2,2)
  Island count = 2

DFS from (3,3): marks connected 1s
  Visit: (3,3), (3,4)
  Island count = 3

Result: 3 islands
```

### The Code

```java
public int numIslands(char[][] grid) {
    int count = 0;
    int m = grid.length, n = grid[0].length;
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == '1') {
                count++;
                dfs(grid, i, j);
            }
        }
    }
    
    return count;
}

private void dfs(char[][] grid, int r, int c) {
    if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return;
    if (grid[r][c] != '1') return;
    
    grid[r][c] = '0';  // Mark visited
    
    dfs(grid, r + 1, c);
    dfs(grid, r - 1, c);
    dfs(grid, r, c + 1);
    dfs(grid, r, c - 1);
}
```

### Mind-Map Anchor

```
NUMBER OF ISLANDS
       |
       v
+------------+
| For each '1', do DFS   |
| Mark visited by setting|
| to '0' (or use visited)|
| Count DFS starts       |
+------------+
```

**Memory phrase:** "Each DFS start = one island, mark as you go"

--

## PATTERN 10: Max Area of Island (LC 695)

### Pattern Recognition Signal

> **When you see:** "Find largest connected region"
> **Instant thought:** "DFS returns area, track maximum!"

### The Code

```java
public int maxAreaOfIsland(int[][] grid) {
    int maxArea = 0;
    int m = grid.length, n = grid[0].length;
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 1) {
                maxArea = Math.max(maxArea, dfs(grid, i, j));
            }
        }
    }
    
    return maxArea;
}

private int dfs(int[][] grid, int r, int c) {
    if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return 0;
    if (grid[r][c] != 1) return 0;
    
    grid[r][c] = 0;  // Mark visited
    
    return 1 + dfs(grid, r + 1, c) 
             + dfs(grid, r - 1, c) 
             + dfs(grid, r, c + 1) 
             + dfs(grid, r, c - 1);
}
```

### Mind-Map Anchor

```
MAX AREA OF ISLAND
        |
        v
+------------+
| DFS returns area count |
| 1 + sum of 4 neighbors |
| Track maximum area     |
+------------+
```

**Memory phrase:** "DFS returns 1 + neighbors, track max"


--

## PATTERN 11: Flood Fill (LC 733)

### Pattern Recognition Signal

> **When you see:** "Fill connected region with new color"
> **Instant thought:** "DFS from starting point, change color!"

### The Code

```java
public int[][] floodFill(int[][] image, int sr, int sc, int color) {
    int originalColor = image[sr][sc];
    if (originalColor != color) {
        dfs(image, sr, sc, originalColor, color);
    }
    return image;
}

private void dfs(int[][] image, int r, int c, int original, int newColor) {
    if (r < 0 || r >= image.length || c < 0 || c >= image[0].length) return;
    if (image[r][c] != original) return;
    
    image[r][c] = newColor;
    
    dfs(image, r + 1, c, original, newColor);
    dfs(image, r - 1, c, original, newColor);
    dfs(image, r, c + 1, original, newColor);
    dfs(image, r, c - 1, original, newColor);
}
```

### Mind-Map Anchor

```
FLOOD FILL
    |
    v
+------------+
| DFS from start point   |
| Change original→new    |
| Skip if same color     |
| (avoid infinite loop)  |
+------------+
```

**Memory phrase:** "Paint bucket tool: DFS and recolor"

--

## PATTERN 12: Rotting Oranges (LC 994)

### Pattern Recognition Signal

> **When you see:** "Spread from multiple sources simultaneously"
> **Instant thought:** "Multi-source BFS! Add all sources to queue first"

### Visual Dry Run

```
Grid:
[2, 1, 1]
[1, 1, 0]
[0, 1, 1]

Initial queue: [(0,0)] (rotten orange)
Time = 0

BFS Level 1:
  Process (0,0): rot (0,1), (1,0)
  Queue: [(0,1), (1,0)]
  Time = 1

BFS Level 2:
  Process (0,1): rot (0,2)
  Process (1,0): rot (1,1)
  Queue: [(0,2), (1,1)]
  Time = 2

BFS Level 3:
  Process (0,2): no fresh neighbors
  Process (1,1): rot (2,1)
  Queue: [(2,1)]
  Time = 3

BFS Level 4:
  Process (2,1): rot (2,2)
  Queue: [(2,2)]
  Time = 4

All oranges rotten. Answer: 4
```

### The Code

```java
public int orangesRotting(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    int fresh = 0;
    
    // Add all rotten oranges to queue, count fresh
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 2) {
                queue.offer(new int[]{i, j});
            } else if (grid[i][j] == 1) {
                fresh++;
            }
        }
    }
    
    if (fresh == 0) return 0;
    
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};
    int time = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        boolean rotted = false;
        
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            
            for (int d = 0; d < 4; d++) {
                int nr = curr[0] + dr[d];
                int nc = curr[1] + dc[d];
                
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 1) {
                    grid[nr][nc] = 2;
                    queue.offer(new int[]{nr, nc});
                    fresh-;
                    rotted = true;
                }
            }
        }
        
        if (rotted) time++;
    }
    
    return fresh == 0 ? time : -1;
}
```

### Mind-Map Anchor

```
ROTTING ORANGES
      |
      v
+------------+
| Multi-source BFS       |
| Add ALL rotten first   |
| Level = time unit      |
| Count fresh, return -1 |
| if any remain          |
+------------+
```

**Memory phrase:** "All rotten in queue first, BFS levels = time"

--

## PATTERN 13: 01 Matrix (LC 542)

### Pattern Recognition Signal

> **When you see:** "Distance to nearest 0 for each cell"
> **Instant thought:** "Multi-source BFS from all 0s!"

### The Code

```java
public int[][] updateMatrix(int[][] mat) {
    int m = mat.length, n = mat[0].length;
    Queue<int[]> queue = new LinkedList<>();
    
    // Add all 0s to queue, mark 1s as unvisited
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (mat[i][j] == 0) {
                queue.offer(new int[]{i, j});
            } else {
                mat[i][j] = Integer.MAX_VALUE;
            }
        }
    }
    
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        
        for (int d = 0; d < 4; d++) {
            int nr = curr[0] + dr[d];
            int nc = curr[1] + dc[d];
            
            if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                if (mat[nr][nc] > mat[curr[0]][curr[1]] + 1) {
                    mat[nr][nc] = mat[curr[0]][curr[1]] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
    }
    
    return mat;
}
```

### Mind-Map Anchor

```
01 MATRIX
    |
    v
+------------+
| Multi-source BFS from  |
| all 0s simultaneously  |
| Distance = parent + 1  |
| Update if shorter      |
+------------+
```

**Memory phrase:** "BFS from all zeros, distance spreads outward"

--

## PATTERN 14: Valid Sudoku (LC 36)

### Pattern Recognition Signal

> **When you see:** "Check if Sudoku board is valid"
> **Instant thought:** "Check rows, columns, and 3x3 boxes for duplicates!"

### The Code

```java
public boolean isValidSudoku(char[][] board) {
    // Use HashSets for each row, column, and box
    Set<Character>[] rows = new HashSet[9];
    Set<Character>[] cols = new HashSet[9];
    Set<Character>[] boxes = new HashSet[9];
    
    for (int i = 0; i < 9; i++) {
        rows[i] = new HashSet<>();
        cols[i] = new HashSet<>();
        boxes[i] = new HashSet<>();
    }
    
    for (int r = 0; r < 9; r++) {
        for (int c = 0; c < 9; c++) {
            char val = board[r][c];
            if (val == '.') continue;
            
            // Check row
            if (rows[r].contains(val)) return false;
            rows[r].add(val);
            
            // Check column
            if (cols[c].contains(val)) return false;
            cols[c].add(val);
            
            // Check 3x3 box
            int boxIdx = (r / 3) * 3 + (c / 3);
            if (boxes[boxIdx].contains(val)) return false;
            boxes[boxIdx].add(val);
        }
    }
    
    return true;
}
```

### Mind-Map Anchor

```
VALID SUDOKU
     |
     v
+------------+
| 9 sets for rows        |
| 9 sets for columns     |
| 9 sets for 3x3 boxes   |
| Box index: (r/3)*3+c/3 |
+------------+
```

**Memory phrase:** "Box index = (row/3)*3 + col/3"

--

## PATTERN 15: Kth Smallest Element in Sorted Matrix (LC 378)

### Pattern Recognition Signal

> **When you see:** "Kth smallest in row+col sorted matrix"
> **Instant thought:** "Binary search on value range OR min-heap!"

### The Code (Binary Search approach)

```java
public int kthSmallest(int[][] matrix, int k) {
    int n = matrix.length;
    int lo = matrix[0][0];
    int hi = matrix[n-1][n-1];
    
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        int count = countLessOrEqual(matrix, mid);
        
        if (count < k) {
            lo = mid + 1;
        } else {
            hi = mid;
        }
    }
    
    return lo;
}

private int countLessOrEqual(int[][] matrix, int target) {
    int n = matrix.length;
    int count = 0;
    int row = n - 1, col = 0;  // Start bottom-left
    
    while (row >= 0 && col < n) {
        if (matrix[row][col] <= target) {
            count += row + 1;  // All elements in this column up to row
            col++;
        } else {
            row-;
        }
    }
    
    return count;
}
```

### Mind-Map Anchor

```
KTH SMALLEST IN MATRIX
          |
          v
+------------+
| Binary search on VALUE |
| Count elements <= mid  |
| Use staircase count    |
| O(n log(max-min))      |
+------------+
```

**Memory phrase:** "Binary search value, staircase count"

--

# QUICK REFERENCE: All 15 Matrix Patterns

| # | Pattern | Key Technique |
|--|-----|--------|
| 1 | Spiral Matrix | 4 boundaries, shrink inward |
| 2 | Spiral Matrix II | Same, but fill |
| 3 | Diagonal Traverse | Zigzag, boundary direction flip |
| 4 | Rotate Image 90° | Transpose + reverse rows |
| 5 | Set Matrix Zeroes | First row/col as markers |
| 6 | Search 2D Matrix | Flatten to 1D, binary search |
| 7 | Search 2D Matrix II | Staircase from top-right |
| 8 | Range Sum Query 2D | 2D prefix sum |
| 9 | Number of Islands | DFS, count starts |
| 10 | Max Area of Island | DFS returns area |
| 11 | Flood Fill | DFS recolor |
| 12 | Rotting Oranges | Multi-source BFS |
| 13 | 01 Matrix | Multi-source BFS from 0s |
| 14 | Valid Sudoku | Row/col/box sets |
| 15 | Kth Smallest | Binary search on value |

--

## Matrix Pattern Decision Tree

```
MATRIX PROBLEM
      |
      +- Traversal? -> Spiral/Diagonal/Zigzag patterns
      |
      +- Search? -> Sorted? -> Binary search / Staircase
      |
      +- Transform? -> Rotate/Transpose/Flip
      |
      +- Modify? -> Set Zeroes / Fill patterns
      |
      +- Connected regions? -> DFS/BFS (treat as graph)
      |
      +- Range queries? -> 2D Prefix Sum
```

--

## Common Traps Table

| Trap | Why Wrong | Fix |
|---|------|---|
| Wrong boundary check | Off-by-one errors | Draw matrix, trace indices |
| Modifying while iterating | Affects later iterations | Use markers or copy |
| Rectangular vs square | Assuming n×n | Use m (rows) and n (cols) |
| Direction arrays wrong | Incorrect neighbors | Test with small example |
| Forgetting diagonal | 4-dir vs 8-dir | Check problem requirements |

--

*End of Matrix & 2D Arrays Deep Dive - 15 Patterns for L5 MAANG*


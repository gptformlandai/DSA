# Bucket E: Matrix as Graph (Islands)

## 🧠 Core Mental Model

> **Every cell is a node. Every adjacent cell (up/down/left/right) is an edge. DFS/BFS on a matrix IS graph traversal — just with `(i±1, j)` and `(i, j±1)` instead of adjacency lists.**

Once you see a matrix as a graph, **every island problem becomes a connected components problem**.

---

## The Universal Template

Almost every problem in this bucket uses the same skeleton:

```java
// 4 directions: up, down, left, right
int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

// Bounds check
boolean inBounds(int i, int j, int m, int n) {
    return i >= 0 && i < m && j >= 0 && j < n;
}

// DFS from a cell
void dfs(int[][] grid, int i, int j) {
    if (!inBounds(i, j, grid.length, grid[0].length)) return;
    if (grid[i][j] != 1) return;   // Not land or already visited

    grid[i][j] = 0;   // Mark visited (sink the land)

    for (int[] d : dirs)
        dfs(grid, i + d[0], j + d[1]);
}
```

**3 things every DFS needs**:
1. **Bounds check** — don't go outside the matrix
2. **Visit check** — don't revisit (either use visited array or modify grid in-place)
3. **Recursive/iterative exploration** — visit all 4 neighbors

---

## Problem-by-Problem Breakdown

---

### 1. Island Perimeter (LC 463) ⭐

**Problem**: Grid of 0s (water) and 1s (land). Single island. Find its perimeter.

```
0 1 0 0
1 1 1 0
0 1 0 0
0 1 0 0

Perimeter = 16
```

**Key Insight**: Each land cell contributes 4 edges. Every adjacent land cell **removes 2 edges** (one from each side of the shared border).

#### Approach 1: Count and Subtract

```java
public int islandPerimeter(int[][] grid) {
    int cells = 0, neighbors = 0;

    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == 1) {
                cells++;
                // Only count RIGHT and DOWN to avoid double-counting
                if (i + 1 < grid.length && grid[i + 1][j] == 1)
                    neighbors++;
                if (j + 1 < grid[0].length && grid[i][j + 1] == 1)
                    neighbors++;
            }
        }
    }
    return cells * 4 - neighbors * 2;
}
```

**Why `neighbors * 2`?** Each shared edge removes 1 edge from EACH cell = 2 total.

**Why only check right and down?** Each pair of adjacent cells is counted once (not twice).

#### Approach 2: Count Exposed Edges Directly

```java
public int islandPerimeter(int[][] grid) {
    int perimeter = 0;
    int m = grid.length, n = grid[0].length;
    int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 1) {
                for (int[] d : dirs) {
                    int ni = i + d[0], nj = j + d[1];
                    // Edge contributes to perimeter if neighbor is water or out of bounds
                    if (ni < 0 || ni >= m || nj < 0 || nj >= n || grid[ni][nj] == 0)
                        perimeter++;
                }
            }
        }
    }
    return perimeter;
}
```

**No DFS needed!** This is a pure counting problem.

**Time**: O(m × n) | **Space**: O(1)

---

### 2. Max Area of Island (LC 695) ⭐⭐

**Problem**: Grid of 0s and 1s. Multiple islands. Find the area (cell count) of the largest island.

```
0 0 1 0 0 0 0 1 0 0
0 0 0 0 0 0 1 1 1 0
0 1 1 0 1 0 0 0 0 0
0 1 0 0 1 1 0 0 1 0
0 1 0 0 1 1 0 0 1 0

Max area = 6 (the island on the left-middle)
```

**Approach**: For each unvisited land cell, DFS and count cells. Track maximum.

```java
public int maxAreaOfIsland(int[][] grid) {
    int maxArea = 0;

    for (int i = 0; i < grid.length; i++)
        for (int j = 0; j < grid[0].length; j++)
            if (grid[i][j] == 1)
                maxArea = Math.max(maxArea, dfs(grid, i, j));

    return maxArea;
}

private int dfs(int[][] grid, int i, int j) {
    // Out of bounds or water/visited
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1)
        return 0;

    grid[i][j] = 0;   // Sink the land (mark visited)

    return 1 + dfs(grid, i + 1, j)
             + dfs(grid, i - 1, j)
             + dfs(grid, i, j + 1)
             + dfs(grid, i, j - 1);
}
```

**The "sinking" trick**: Instead of a separate `visited[][]` array, set `grid[i][j] = 0` to mark as visited. This saves O(m × n) space.

**⚠️ Trade-off**: This modifies the input. If you can't modify it, use a `boolean[][] visited` instead.

**Time**: O(m × n) — each cell visited at most once | **Space**: O(m × n) worst case for recursion stack

---

### 3. Number of Closed Islands (LC 1254) ⭐⭐

**Problem**: Grid of 0s (land) and 1s (water). Count islands that are **completely surrounded by water** (not touching any boundary).

```
1 1 1 1 1 1 1 0
1 0 0 0 0 1 1 0
1 0 1 0 1 1 1 0
1 0 0 0 0 1 0 1
1 1 1 1 1 1 1 0

Answer = 2 (the two inner islands)
```

**⚠️ Note**: In this problem, `0 = land` and `1 = water` (opposite of LC 695!). Always check.

**Key Insight**: An island touching the boundary is NOT closed. So:
1. First, **sink all boundary-connected islands** (DFS from edges)
2. Then, **count remaining islands** (these are all closed)

```java
public int closedIsland(int[][] grid) {
    int m = grid.length, n = grid[0].length;

    // Step 1: Sink all islands touching the boundary
    for (int i = 0; i < m; i++) {
        if (grid[i][0] == 0) sink(grid, i, 0);
        if (grid[i][n - 1] == 0) sink(grid, i, n - 1);
    }
    for (int j = 0; j < n; j++) {
        if (grid[0][j] == 0) sink(grid, 0, j);
        if (grid[m - 1][j] == 0) sink(grid, m - 1, j);
    }

    // Step 2: Count remaining islands
    int count = 0;
    for (int i = 1; i < m - 1; i++)
        for (int j = 1; j < n - 1; j++)
            if (grid[i][j] == 0) {
                count++;
                sink(grid, i, j);
            }

    return count;
}

private void sink(int[][] grid, int i, int j) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 0)
        return;

    grid[i][j] = 1;   // Sink (land=0 → water=1)

    sink(grid, i + 1, j);
    sink(grid, i - 1, j);
    sink(grid, i, j + 1);
    sink(grid, i, j - 1);
}
```

**The 2-pass pattern**:
1. **Eliminate disqualified components** (boundary-touching)
2. **Count remaining components**

This pattern appears in many graph problems beyond matrices.

**Time**: O(m × n) | **Space**: O(m × n) recursion stack

---

## DFS vs BFS: Which to Use?

| | DFS | BFS |
|---|---|---|
| **Implementation** | Recursive (simpler) or stack | Queue |
| **Space** | O(m×n) recursion stack worst case | O(min(m,n)) queue typical |
| **When to prefer** | Counting area, checking connectivity | Shortest path, level-by-level |
| **Risk** | Stack overflow on large grids | None |

### BFS Version of Max Area (for comparison)

```java
private int bfs(int[][] grid, int i, int j) {
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{i, j});
    grid[i][j] = 0;
    int area = 0;
    int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};

    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        area++;
        for (int[] d : dirs) {
            int ni = cell[0] + d[0], nj = cell[1] + d[1];
            if (ni >= 0 && ni < grid.length && nj >= 0 && nj < grid[0].length
                && grid[ni][nj] == 1) {
                grid[ni][nj] = 0;
                queue.offer(new int[]{ni, nj});
            }
        }
    }
    return area;
}
```

**For interviews**: DFS is shorter to write. Use BFS only when the problem requires shortest path or level order.

---

## The 4-Direction Array Pattern

```java
int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
//               up       down     left      right

// Some problems need 8 directions (diagonals too):
int[][] dirs8 = {{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,-1},{1,1}};
```

**Always use this array** instead of writing 4 separate if-statements. Cleaner, less error-prone.

---

## Common Pitfalls & Edge Cases

### 1. Land/Water Values Vary By Problem!
```
LC 695 (Max Area):     1 = land, 0 = water
LC 1254 (Closed):      0 = land, 1 = water    ← OPPOSITE!
LC 463 (Perimeter):    1 = land, 0 = water
```
**Always check** what 0 and 1 mean. Read the problem statement carefully.

### 2. Stack Overflow on Large Grids
For a 500×500 grid with all land, DFS recursion depth = 250,000. This **will** stack overflow in Java.

**Fix**: Use iterative DFS with an explicit stack, or BFS.

```java
// Iterative DFS
private int dfsIterative(int[][] grid, int i, int j) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{i, j});
    grid[i][j] = 0;
    int area = 0;
    int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};

    while (!stack.isEmpty()) {
        int[] cell = stack.pop();
        area++;
        for (int[] d : dirs) {
            int ni = cell[0] + d[0], nj = cell[1] + d[1];
            if (ni >= 0 && ni < grid.length && nj >= 0 && nj < grid[0].length
                && grid[ni][nj] == 1) {
                grid[ni][nj] = 0;
                stack.push(new int[]{ni, nj});
            }
        }
    }
    return area;
}
```

### 3. Marking Visited BEFORE Pushing to Queue/Stack
```java
// ❌ WRONG: mark when popping → same cell pushed multiple times
while (!queue.isEmpty()) {
    int[] cell = queue.poll();
    grid[cell[0]][cell[1]] = 0;   // Too late!
    ...
}

// ✅ CORRECT: mark when pushing → each cell pushed exactly once
grid[ni][nj] = 0;                // Mark immediately
queue.offer(new int[]{ni, nj});
```

### 4. Modifying Input vs Using Visited Array
| Approach | Pros | Cons |
|---|---|---|
| Modify grid (`grid[i][j] = 0`) | No extra space | Destroys input |
| `boolean[][] visited` | Preserves input | O(m×n) extra space |

**Interview tip**: Ask "Can I modify the input?" If yes, sink. If no, use visited array.

### 5. Empty Grid / All Water / All Land
```java
// Always handle:
if (grid == null || grid.length == 0) return 0;
```

---

## How These Problems Connect to Full Graph Theory

| Matrix Problem | Graph Equivalent |
|---|---|
| Max Area of Island | Largest connected component (by node count) |
| Number of Islands | Count connected components |
| Closed Islands | Components not touching boundary |
| Island Perimeter | Edges between component and non-component |
| Shortest path in grid | BFS (unweighted shortest path) |

This bucket is your **bridge** from matrices to full graph theory. The same DFS/BFS patterns will appear in:
- Number of Islands (LC 200) — Week 11
- Surrounded Regions (LC 130) — Same 2-pass boundary elimination
- Pacific Atlantic Water Flow (LC 417) — DFS from boundaries inward
- Rotting Oranges (LC 994) — BFS level-by-level

---

## Pattern Recognition Cheat Sheet

| If the problem says... | Think... |
|---|---|
| "island" / "connected region" | DFS/BFS, mark visited |
| "area of island" | DFS returning count |
| "perimeter" | Count edges facing water/boundary |
| "closed" / "surrounded" / "not touching boundary" | 2-pass: sink boundary islands first, then count |
| "shortest path in grid" | BFS (not DFS!) |
| "number of islands" | Count DFS/BFS invocations from main loop |

---

## Practice Order (Recommended)

1. **Island Perimeter** (LC 463) — No DFS needed. Pure counting. Warm-up.
2. **Max Area of Island** (LC 695) — Core DFS + area counting. Foundation for everything.
3. **Number of Closed Islands** (LC 1254) — 2-pass pattern. Boundary elimination.

**After matrices**: These lead directly into full island problems in graph theory (LC 200, 130, 417, 994).

---

## Time & Space Complexity Summary

| Problem | Time | Space | Technique |
|---|---|---|---|
| Island Perimeter (LC 463) | O(m × n) | O(1) | Counting |
| Max Area of Island (LC 695) | O(m × n) | O(m × n) stack | DFS |
| Number of Closed Islands (LC 1254) | O(m × n) | O(m × n) stack | 2-pass DFS |

All are O(m × n) time — you visit each cell at most once across all DFS calls.

---

## Quick Revision Anchors

- **Matrix = Graph**: Cell = node, 4 neighbors = edges. DFS/BFS works identically.
- **Perimeter**: Each land cell = 4 edges. Each land neighbor = -2 edges. Or just count edges facing water/boundary.
- **Area**: DFS returns `1 + sum of 4 recursive calls`. Sink visited cells.
- **Closed islands**: 2-pass. First sink boundary-connected islands, then count what's left.
- **Always use `int[][] dirs`**: Cleaner than 4 separate if-blocks.
- **Mark visited when PUSHING**, not when popping. Prevents duplicates.
- **Check land/water values**: 0 and 1 mean different things in different problems!

# Graph Search Patterns Deep Dive (MAANG L5 Coverage)

---

# INDEX

| Category | Patterns |
|----------|----------|
| [Core Concepts](#core-templates) | BFS vs DFS, Templates |
| [Matrix/Grid](#pattern-0-number-of-islands-lc-200-) | Patterns 0-8 |
| [Graph Traversal](#pattern-9-course-schedule-lc-207-) | Patterns 9-14 |
| [Shortest Path](#pattern-15-word-ladder-lc-127-) | Patterns 15-18 |
| [Advanced](#additional-detailed-patterns) | Patterns 19-22 |

---

# The One Sentence That Unlocks All Graph Problems

> **"BFS for SHORTEST PATH, DFS for EXPLORING ALL PATHS."**

---

# 🌟 ZERO TO HERO: Understanding Graph Search

## BFS vs DFS - When to Use Which

```
┌─────────────────────────────────────────────────────────────┐
│                    BFS vs DFS                               │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  BFS (Breadth-First):              DFS (Depth-First):       │
│  - Level by level                  - Go deep first          │
│  - Uses QUEUE                      - Uses STACK/Recursion   │
│  - SHORTEST PATH                   - EXPLORE ALL            │
│  - Minimum steps                   - Cycle detection        │
│                                    - Topological sort       │
│                                                             │
│  Use BFS when:                     Use DFS when:            │
│  - "Minimum moves"                 - "Find all paths"       │
│  - "Shortest distance"             - "Does path exist"      │
│  - "Level order"                   - "Connected components" │
│  - "Nearest X"                     - "Cycle detection"      │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

# CORE TEMPLATES

## Template 1: BFS (Breadth-First Search)

```java
int bfs(int[][] grid, int startR, int startC) {
    int rows = grid.length, cols = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    boolean[][] visited = new boolean[rows][cols];
    
    queue.offer(new int[]{startR, startC});
    visited[startR][startC] = true;
    int level = 0;
    
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    
    while (!queue.isEmpty()) {
        int size = queue.size();  // Process level by level
        
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1];
            
            // Check if target reached
            if (isTarget(r, c)) return level;
            
            // Add neighbors
            for (int[] dir : dirs) {
                int nr = r + dir[0], nc = c + dir[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols 
                    && !visited[nr][nc] && grid[nr][nc] == 1) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        level++;
    }
    return -1;  // Not found
}
```

---

## Template 2: DFS (Depth-First Search)

```java
// Recursive DFS
void dfs(int[][] grid, int r, int c, boolean[][] visited) {
    // Bounds check
    if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) 
        return;
    // Already visited or invalid
    if (visited[r][c] || grid[r][c] == 0) 
        return;
    
    visited[r][c] = true;  // Mark visited BEFORE recursing
    
    // Process current cell
    
    // Explore all 4 directions
    dfs(grid, r + 1, c, visited);
    dfs(grid, r - 1, c, visited);
    dfs(grid, r, c + 1, visited);
    dfs(grid, r, c - 1, visited);
}
```

---

## The 4-Directions Array

```java
// 4 directions: right, down, left, up
int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};

// 8 directions (including diagonals)
int[][] dirs8 = {{0,1}, {1,0}, {0,-1}, {-1,0}, 
                 {1,1}, {1,-1}, {-1,1}, {-1,-1}};

// Bounds check helper
boolean isValid(int r, int c, int rows, int cols) {
    return r >= 0 && r < rows && c >= 0 && c < cols;
}
```

---

# PATTERN 0: Number of Islands (LC 200) ⭐⭐

## Pattern Recognition Signal

**When you see:** "grid", "count connected regions", "islands"

**Instant thought:** "DFS flood fill! Sink each island as you count."

---

## The Mental Model: "The Flood"

```
Imagine flying over an ocean with islands.
You want to count separate land masses.

Strategy:
1. Scan the grid from top-left
2. When you spot land (1) → count it as new island
3. FLOOD that entire island (turn all connected 1s to 0s)
4. Continue scanning

The flooding ensures each island counted exactly once.
```

---

## Visual Dry Run

**Input:**
```
1 1 0 0 0
1 1 0 0 0
0 0 1 0 0
0 0 0 1 1
```

```
═══════════════════════════════════════════════════════════════

Step 1: Found '1' at (0,0)
  count = 1
  
  DFS flood from (0,0):
  - Sink (0,0) → '0'
  - Sink (0,1) → '0'
  - Sink (1,0) → '0'
  - Sink (1,1) → '0'

═══════════════════════════════════════════════════════════════

Step 2: Found '1' at (2,2)
  count = 2
  DFS flood: Sink (2,2)

═══════════════════════════════════════════════════════════════

Step 3: Found '1' at (3,3)
  count = 3
  DFS flood: Sink (3,3), (3,4)

═══════════════════════════════════════════════════════════════

Final count = 3 ✓
```

---

## The Code

```java
int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    
    int count = 0;
    int rows = grid.length, cols = grid[0].length;
    
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (grid[r][c] == '1') {
                count++;           // Found new island
                dfs(grid, r, c);   // Sink it
            }
        }
    }
    return count;
}

void dfs(char[][] grid, int r, int c) {
    // Bounds + water check
    if (r < 0 || r >= grid.length || 
        c < 0 || c >= grid[0].length ||
        grid[r][c] == '0') {
        return;
    }
    
    grid[r][c] = '0';  // Sink this cell
    
    // Flood in all 4 directions
    dfs(grid, r + 1, c);
    dfs(grid, r - 1, c);
    dfs(grid, r, c + 1);
    dfs(grid, r, c - 1);
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Not sinking before recursing | Infinite loop | Sink BEFORE recursive calls |
| Using '1' vs 1 | char vs int | Use '1' and '0' for char grid |

---

## Mind-Map Anchor

```
"COUNT CONNECTED REGIONS"
           │
           ▼
┌─────────────────────────────────────────┐
│ DFS Flood Fill                          │
│ 1. Find '1' → count++                   │
│ 2. DFS to sink entire island            │
│ 3. Continue scanning                    │
│ Key: Sink BEFORE recursing              │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Find land, count it, flood it, move on."

---

# PATTERN 3: Rotting Oranges (LC 994) ⭐⭐

## Pattern Recognition Signal

**When you see:** "spreading", "infection", "minimum time to spread"

**Instant thought:** "Multi-source BFS! Start from ALL sources."

---

## The Mental Model: "The Zombie Apocalypse"

```
Rotten oranges are zombies.
Fresh oranges are humans.
Each minute, zombies infect adjacent humans.

How long until all humans are zombies (or some survive)?

Key insight: Start BFS from ALL zombies simultaneously!
```

---

## Visual Dry Run

**Input:**
```
2 1 1
1 1 0
0 1 1
```

```
═══════════════════════════════════════════════════════════════

Step 0: Initialize
  Queue: [(0,0)]  ← rotten orange at (0,0)
  Fresh count: 6
  
  Grid:
  [2] 1  1
   1  1  0
   0  1  1

═══════════════════════════════════════════════════════════════

Step 1: Minute 1 - Process (0,0)
  Queue: [(0,1), (1,0)]  ← newly rotted
  Fresh count: 4
  
  Grid:
  [2][2] 1      ← (0,1) rotted
  [2] 1  0      ← (1,0) rotted
   0  1  1

═══════════════════════════════════════════════════════════════

Step 2: Minute 2 - Process (0,1), (1,0)
  Queue: [(0,2), (1,1)]  ← newly rotted
  Fresh count: 2
  
  Grid:
  [2][2][2]     ← (0,2) rotted
  [2][2] 0      ← (1,1) rotted
   0  1  1

═══════════════════════════════════════════════════════════════

Step 3: Minute 3 - Process (0,2), (1,1)
  Queue: [(2,1)]  ← newly rotted
  Fresh count: 1
  
  Grid:
  [2][2][2]
  [2][2] 0
   0 [2] 1      ← (2,1) rotted

═══════════════════════════════════════════════════════════════

Step 4: Minute 4 - Process (2,1)
  Queue: [(2,2)]  ← newly rotted
  Fresh count: 0
  
  Grid:
  [2][2][2]
  [2][2] 0
   0 [2][2]     ← (2,2) rotted

═══════════════════════════════════════════════════════════════

Final: minutes = 4, fresh = 0 → Return 4 ✓
```

---

## The Code

```java
int orangesRotting(int[][] grid) {
    int rows = grid.length, cols = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    int fresh = 0;
    
    // Add ALL rotten oranges to queue (multi-source)
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (grid[r][c] == 2) queue.offer(new int[]{r, c});
            else if (grid[r][c] == 1) fresh++;
        }
    }
    
    if (fresh == 0) return 0;  // No fresh oranges
    
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    int minutes = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        boolean rotted = false;
        
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            
            for (int[] dir : dirs) {
                int nr = curr[0] + dir[0];
                int nc = curr[1] + dir[1];
                
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols 
                    && grid[nr][nc] == 1) {
                    grid[nr][nc] = 2;  // Rot it
                    fresh--;
                    queue.offer(new int[]{nr, nc});
                    rotted = true;
                }
            }
        }
        if (rotted) minutes++;
    }
    
    return fresh == 0 ? minutes : -1;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Single-source BFS | Wrong time calculation | Add ALL rotten to queue first |
| Incrementing time wrong | Off by one | Only increment when something rotted |
| Forgetting impossible case | Return wrong answer | Check fresh == 0 at end |

---

# PATTERN 9: Course Schedule (LC 207) ⭐⭐

## Pattern Recognition Signal

**When you see:** "prerequisites", "dependencies", "can finish all"

**Instant thought:** "Cycle detection in directed graph!"

---

## The Mental Model: "The Chicken and Egg"

```
Course A requires B, B requires C, C requires A
→ Impossible! Circular dependency.

We need to detect if there's a CYCLE in the dependency graph.
No cycle = can finish all courses.
```

---

## Visual Dry Run

**Input:** `numCourses = 4, prerequisites = [[1,0], [2,0], [3,1], [3,2]]`

```
Graph:
  0 → 1 → 3
  ↓       ↑
  2 ──────┘

═══════════════════════════════════════════════════════════════

Step 1: Start DFS from node 0
  State: [1, 0, 0, 0]  (0=WHITE, 1=GRAY, 2=BLACK)
         node 0 is GRAY (visiting)
  
  Call Stack: [0]

═══════════════════════════════════════════════════════════════

Step 2: From 0, visit neighbor 1
  State: [1, 1, 0, 0]
         nodes 0,1 are GRAY
  
  Call Stack: [0, 1]

═══════════════════════════════════════════════════════════════

Step 3: From 1, visit neighbor 3
  State: [1, 1, 0, 1]
         nodes 0,1,3 are GRAY
  
  Call Stack: [0, 1, 3]

═══════════════════════════════════════════════════════════════

Step 4: Node 3 has no unvisited neighbors
  State: [1, 1, 0, 2]
         node 3 → BLACK (done)
  
  Call Stack: [0, 1]  ← backtrack

═══════════════════════════════════════════════════════════════

Step 5: Node 1 done
  State: [1, 2, 0, 2]
         node 1 → BLACK
  
  Call Stack: [0]

═══════════════════════════════════════════════════════════════

Step 6: From 0, visit neighbor 2
  State: [1, 2, 1, 2]
         node 2 is GRAY
  
  Call Stack: [0, 2]

═══════════════════════════════════════════════════════════════

Step 7: From 2, check neighbor 3
  State: [1, 2, 1, 2]
         node 3 is BLACK → skip (already processed)
  
  Call Stack: [0, 2]

═══════════════════════════════════════════════════════════════

Step 8: Node 2 done, Node 0 done
  State: [2, 2, 2, 2]
         All nodes BLACK
  
  Call Stack: []

═══════════════════════════════════════════════════════════════

Final: No GRAY→GRAY edge found → No cycle → Return true ✓
```

---

## The Code (DFS with 3 colors)

```java
boolean canFinish(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) 
        graph.add(new ArrayList<>());
    
    for (int[] pre : prerequisites)
        graph.get(pre[1]).add(pre[0]);
    
    // 0 = unvisited, 1 = visiting, 2 = visited
    int[] state = new int[numCourses];
    
    for (int i = 0; i < numCourses; i++) {
        if (hasCycle(graph, i, state)) return false;
    }
    return true;
}

boolean hasCycle(List<List<Integer>> graph, int node, int[] state) {
    if (state[node] == 1) return true;   // Cycle! Back edge
    if (state[node] == 2) return false;  // Already processed
    
    state[node] = 1;  // Mark as visiting
    
    for (int neighbor : graph.get(node)) {
        if (hasCycle(graph, neighbor, state)) return true;
    }
    
    state[node] = 2;  // Mark as visited
    return false;
}
```

---

## The 3-Color Approach

```
WHITE (0) = Unvisited
GRAY (1)  = Currently visiting (in recursion stack)
BLACK (2) = Completely processed

If we reach a GRAY node → CYCLE detected!
(We found a back edge to a node still being processed)
```

---

# PATTERN 15: Word Ladder (LC 127) ⭐⭐

## Pattern Recognition Signal

**When you see:** "transform word", "minimum steps", "change one letter"

**Instant thought:** "BFS on implicit graph! Words are nodes."

---

## Visual Dry Run

**Input:** `beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]`

```
═══════════════════════════════════════════════════════════════

Step 1: Level 1 - Start with "hit"
  Queue: ["hit"]
  WordSet: {hot, dot, dog, lot, log, cog}
  
  Process "hit":
    Try h→a-z: "ait","bit"... "hot" ✓ (in set!)
    
  Queue: ["hot"]
  WordSet: {dot, dog, lot, log, cog}  ← "hot" removed

═══════════════════════════════════════════════════════════════

Step 2: Level 2 - Process "hot"
  Queue: ["hot"]
  
  Process "hot":
    Try h→a-z: "aot","bot"... "dot" ✓, "lot" ✓
    Try o→a-z: "hat","hbt"... none
    Try t→a-z: "hoa","hob"... none
    
  Queue: ["dot", "lot"]
  WordSet: {dog, log, cog}

═══════════════════════════════════════════════════════════════

Step 3: Level 3 - Process "dot", "lot"
  Queue: ["dot", "lot"]
  
  Process "dot":
    Try d→a-z: "aot"... "dog" ✓
    
  Process "lot":
    Try l→a-z: "aot"... "log" ✓
    
  Queue: ["dog", "log"]
  WordSet: {cog}

═══════════════════════════════════════════════════════════════

Step 4: Level 4 - Process "dog", "log"
  Queue: ["dog", "log"]
  
  Process "dog":
    Try d→a-z: "aog","bog","cog" ✓
    
  Queue: ["cog"]
  WordSet: {}

═══════════════════════════════════════════════════════════════

Step 5: Level 5 - Process "cog"
  Queue: ["cog"]
  
  "cog" == endWord → FOUND!

═══════════════════════════════════════════════════════════════

Final: Return level = 5 ✓

Path: hit → hot → dot → dog → cog (5 words)
```

---

## The Code

```java
int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord)) return 0;
    
    Queue<String> queue = new LinkedList<>();
    queue.offer(beginWord);
    int level = 1;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        
        for (int i = 0; i < size; i++) {
            String word = queue.poll();
            
            if (word.equals(endWord)) return level;
            
            // Try changing each character
            char[] chars = word.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char original = chars[j];
                
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == original) continue;
                    chars[j] = c;
                    String newWord = new String(chars);
                    
                    if (wordSet.contains(newWord)) {
                        queue.offer(newWord);
                        wordSet.remove(newWord);  // Mark visited
                    }
                }
                chars[j] = original;  // Restore
            }
        }
        level++;
    }
    return 0;
}
```

---

# MAANG Coverage Map

| Pattern | Problem | Difficulty | Frequency |
|---------|---------|------------|-----------|
| 0 | Number of Islands (LC 200) | Medium | ⭐⭐⭐⭐⭐ |
| 3 | Rotting Oranges (LC 994) | Medium | ⭐⭐⭐⭐⭐ |
| 5 | Word Search (LC 79) | Medium | ⭐⭐⭐⭐ |
| 9 | Course Schedule (LC 207) | Medium | ⭐⭐⭐⭐⭐ |
| 10 | Course Schedule II (LC 210) | Medium | ⭐⭐⭐⭐ |
| 12 | Clone Graph (LC 133) | Medium | ⭐⭐⭐⭐ |
| 15 | Word Ladder (LC 127) | Hard | ⭐⭐⭐⭐ |

---

# Mastery Checklist

## Tier 1: Must Know
- [ ] Number of Islands (LC 200)
- [ ] Rotting Oranges (LC 994)
- [ ] Course Schedule (LC 207)
- [ ] Clone Graph (LC 133)

## Tier 2: Interview Favorites
- [ ] Word Search (LC 79)
- [ ] Word Ladder (LC 127)
- [ ] Pacific Atlantic Water Flow (LC 417)

## Tier 3: Differentiators
- [ ] Alien Dictionary (LC 269)
- [ ] Critical Connections (LC 1192)

---

# Quick Reference Card

```
┌─────────────────────────────────────────────────────────────┐
│                 GRAPH SEARCH CHEAT SHEET                    │
├─────────────────────────────────────────────────────────────┤
│ BFS (Queue):                                                │
│   - Shortest path in unweighted graph                       │
│   - Level-order traversal                                   │
│   - Multi-source: add ALL sources to queue first            │
├─────────────────────────────────────────────────────────────┤
│ DFS (Stack/Recursion):                                      │
│   - Explore all paths                                       │
│   - Cycle detection (3 colors)                              │
│   - Topological sort                                        │
│   - Connected components                                    │
├─────────────────────────────────────────────────────────────┤
│ 4-DIRECTIONS:                                               │
│   int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};            │
├─────────────────────────────────────────────────────────────┤
│ CYCLE DETECTION (Directed):                                 │
│   WHITE(0) → GRAY(1) → BLACK(2)                             │
│   GRAY → GRAY = CYCLE                                       │
└─────────────────────────────────────────────────────────────┘
```

---

# ADDITIONAL DETAILED PATTERNS

---

# PATTERN 1: Max Area of Island (LC 695)

## Pattern Recognition Signal

**When you see:** "largest island", "maximum area", "connected 1s"

**Instant thought:** "DFS flood fill with counting! Return max count."

---

## The Mental Model

```
Same as Number of Islands, but now we COUNT cells as we flood.

For each island:
1. Start DFS from a '1'
2. Count every cell we visit
3. Return the count
4. Track maximum across all islands
```

---

## Visual Dry Run

**Input:**
```
0 0 1 0 0
0 0 1 1 0
0 1 1 0 0
0 0 0 0 0
```

```
═══════════════════════════════════════════════════════════════

Step 1: Scan grid, found '1' at (0,2)
  maxArea = 0
  
  Grid:
  0  0 [1] 0  0
  0  0  1  1  0
  0  1  1  0  0
  0  0  0  0  0

═══════════════════════════════════════════════════════════════

Step 2: DFS from (0,2)
  Call Stack: [(0,2)]
  
  Visit (0,2): count = 1, sink to 0
  Grid:
  0  0 [0] 0  0
  0  0  1  1  0
  0  1  1  0  0
  0  0  0  0  0

═══════════════════════════════════════════════════════════════

Step 3: DFS explores down to (1,2)
  Call Stack: [(0,2), (1,2)]
  
  Visit (1,2): count = 2, sink to 0
  Grid:
  0  0  0  0  0
  0  0 [0] 1  0
  0  1  1  0  0
  0  0  0  0  0

═══════════════════════════════════════════════════════════════

Step 4: DFS explores right to (1,3)
  Call Stack: [(0,2), (1,2), (1,3)]
  
  Visit (1,3): count = 3, sink to 0
  Grid:
  0  0  0  0  0
  0  0  0 [0] 0
  0  1  1  0  0
  0  0  0  0  0

═══════════════════════════════════════════════════════════════

Step 5: Backtrack, DFS explores down to (2,2)
  Call Stack: [(0,2), (1,2), (2,2)]
  
  Visit (2,2): count = 4, sink to 0
  Grid:
  0  0  0  0  0
  0  0  0  0  0
  0  1 [0] 0  0
  0  0  0  0  0

═══════════════════════════════════════════════════════════════

Step 6: DFS explores left to (2,1)
  Call Stack: [(0,2), (1,2), (2,2), (2,1)]
  
  Visit (2,1): count = 5, sink to 0
  Grid:
  0  0  0  0  0
  0  0  0  0  0
  0 [0] 0  0  0
  0  0  0  0  0

═══════════════════════════════════════════════════════════════

Step 7: DFS complete, backtrack all
  Island area = 5
  maxArea = max(0, 5) = 5

═══════════════════════════════════════════════════════════════

Step 8: Continue scanning... no more '1's found

Final: maxArea = 5 ✓
```

---

## The Code

```java
int maxAreaOfIsland(int[][] grid) {
    int maxArea = 0;
    
    for (int r = 0; r < grid.length; r++) {
        for (int c = 0; c < grid[0].length; c++) {
            if (grid[r][c] == 1) {
                int area = dfs(grid, r, c);
                maxArea = Math.max(maxArea, area);
            }
        }
    }
    
    return maxArea;
}

int dfs(int[][] grid, int r, int c) {
    // Bounds check + water check
    if (r < 0 || r >= grid.length || 
        c < 0 || c >= grid[0].length ||
        grid[r][c] == 0) {
        return 0;
    }
    
    grid[r][c] = 0;  // Sink this cell
    
    // Count this cell + all connected cells
    return 1 + dfs(grid, r+1, c) + dfs(grid, r-1, c) 
             + dfs(grid, r, c+1) + dfs(grid, r, c-1);
}
```

---

## Mind-Map Anchor

**Memory phrase:** "Flood and count. Return 1 + four neighbors."

---

# PATTERN 4: Pacific Atlantic Water Flow (LC 417) ⭐

## Pattern Recognition Signal

**When you see:** "water flow", "reach both oceans", "from edges"

**Instant thought:** "Reverse DFS! Start from oceans, go uphill."

---

## The Mental Model: "Reverse the Flow"

```
Normal thinking: For each cell, can water flow to both oceans?
Problem: Would need to check every path from every cell → slow!

Reverse thinking: 
- Start from Pacific edge, mark all cells water CAN reach (going uphill)
- Start from Atlantic edge, mark all cells water CAN reach (going uphill)
- Answer = cells marked by BOTH

Why uphill? Water flows downhill, so if we go uphill from ocean,
we find all cells that COULD flow down to that ocean.
```

---

## Visual Explanation

```
Pacific Ocean (top and left edges)
  ↓ ↓ ↓ ↓ ↓
→ 1 2 2 3 5 →
→ 3 2 3 4 4 →
→ 2 4 5 3 1 →  Atlantic Ocean
→ 6 7 1 4 5 →  (right and bottom edges)
→ 5 1 1 2 4 →
  ↓ ↓ ↓ ↓ ↓

Cells that can reach Pacific: Start from top/left, go to higher/equal
Cells that can reach Atlantic: Start from right/bottom, go to higher/equal
Intersection = answer
```

---

## Visual Dry Run

**Input:**
```
Pacific ~   ~   ~   ~   ~
       ~  1   2   2   3   5  *
       ~  3   2   3   4   4  *
       ~  2   4   5   3   1  *  Atlantic
       ~  6   7   1   4   5  *
       ~  5   1   1   2   4  *
           *   *   *   *   *
```

```
═══════════════════════════════════════════════════════════════

Step 1: DFS from Pacific edges (top row + left column)
  Start positions: (0,0), (0,1), (0,2), (0,3), (0,4)
                   (1,0), (2,0), (3,0), (4,0)
  
  Pacific reachable (going uphill ≥):
  [P] [P] [P] [P] [P]
  [P] [P] [P] [P] [P]
  [P] [P] [P]  .   .
  [P] [P]  .   .   .
  [P] [P]  .   .   .

═══════════════════════════════════════════════════════════════

Step 2: DFS from Atlantic edges (bottom row + right column)
  Start positions: (4,0), (4,1), (4,2), (4,3), (4,4)
                   (0,4), (1,4), (2,4), (3,4)
  
  Atlantic reachable (going uphill ≥):
   .   .   .  [A] [A]
   .   .   .  [A] [A]
   .  [A] [A] [A] [A]
  [A] [A]  .  [A] [A]
  [A] [A] [A] [A] [A]

═══════════════════════════════════════════════════════════════

Step 3: Find intersection (both P and A)
  
  Grid with both:
   .   .   .   .  [✓]     (0,4)
   .   .   .  [✓] [✓]     (1,3), (1,4)
   .  [✓] [✓]  .   .      (2,1), (2,2)
  [✓] [✓]  .   .   .      (3,0), (3,1)
  [✓]  .   .   .   .      (4,0)

═══════════════════════════════════════════════════════════════

Final: Result = [[0,4], [1,3], [1,4], [2,1], [2,2], [3,0], [3,1], [4,0]] ✓
```

---

## The Code

```java
List<List<Integer>> pacificAtlantic(int[][] heights) {
    List<List<Integer>> result = new ArrayList<>();
    if (heights == null || heights.length == 0) return result;
    
    int rows = heights.length, cols = heights[0].length;
    boolean[][] pacific = new boolean[rows][cols];
    boolean[][] atlantic = new boolean[rows][cols];
    
    // DFS from Pacific edges (top row and left column)
    for (int c = 0; c < cols; c++) dfs(heights, 0, c, pacific);
    for (int r = 0; r < rows; r++) dfs(heights, r, 0, pacific);
    
    // DFS from Atlantic edges (bottom row and right column)
    for (int c = 0; c < cols; c++) dfs(heights, rows-1, c, atlantic);
    for (int r = 0; r < rows; r++) dfs(heights, r, cols-1, atlantic);
    
    // Find intersection
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (pacific[r][c] && atlantic[r][c]) {
                result.add(Arrays.asList(r, c));
            }
        }
    }
    
    return result;
}

void dfs(int[][] heights, int r, int c, boolean[][] visited) {
    visited[r][c] = true;
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    
    for (int[] dir : dirs) {
        int nr = r + dir[0], nc = c + dir[1];
        if (nr >= 0 && nr < heights.length && 
            nc >= 0 && nc < heights[0].length &&
            !visited[nr][nc] && 
            heights[nr][nc] >= heights[r][c]) {  // Go UPHILL
            dfs(heights, nr, nc, visited);
        }
    }
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Going downhill | That's forward flow, not reverse | Use >= for uphill |
| Starting from all cells | Too slow O(n²m²) | Start from edges only |
| Forgetting to mark visited | Infinite loop | Mark at start of DFS |

---

# PATTERN 6: Shortest Path in Binary Matrix (LC 1091)

## Pattern Recognition Signal

**When you see:** "shortest path", "binary matrix", "8 directions"

**Instant thought:** "BFS! Level = distance. Use 8 directions."

---

## Visual Dry Run

**Input:**
```
0 0 0
1 1 0
1 1 0
```

```
═══════════════════════════════════════════════════════════════

Step 1: Initialize BFS from (0,0)
  Queue: [(0,0)]
  Path length: 1
  
  Grid (0=open, 1=blocked, V=visited):
  [V] 0  0
   1  1  0
   1  1  0

═══════════════════════════════════════════════════════════════

Step 2: Process (0,0), explore 8 directions
  Queue: [(0,1), (1,1) blocked, (1,0) blocked, ...]
  Valid neighbors: (0,1)
  Path length: 1
  
  Grid:
  [V][V] 0
   1  1  0
   1  1  0

═══════════════════════════════════════════════════════════════

Step 3: Level 2 - Process (0,1)
  Queue: [(0,2), (1,2)]
  Path length: 2
  
  Grid:
  [V][V][V]
   1  1 [V]
   1  1  0

═══════════════════════════════════════════════════════════════

Step 4: Level 3 - Process (0,2), (1,2)
  From (0,2): no new valid neighbors
  From (1,2): neighbor (2,2) is valid!
  Queue: [(2,2)]
  Path length: 3
  
  Grid:
  [V][V][V]
   1  1 [V]
   1  1 [V]

═══════════════════════════════════════════════════════════════

Step 5: Level 4 - Process (2,2)
  (2,2) is target (n-1, n-1)!
  
  Grid:
  [V][V][V]
   1  1 [V]
   1  1 [✓]  ← destination reached!

═══════════════════════════════════════════════════════════════

Final: Return path = 4 ✓

Path: (0,0) → (0,1) → (1,2) → (2,2)
```

---

## The Code

```java
int shortestPathBinaryMatrix(int[][] grid) {
    int n = grid.length;
    if (grid[0][0] == 1 || grid[n-1][n-1] == 1) return -1;
    
    int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}};
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{0, 0});
    grid[0][0] = 1;  // Mark visited
    int path = 1;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            
            if (curr[0] == n-1 && curr[1] == n-1) return path;
            
            for (int[] dir : dirs) {
                int nr = curr[0] + dir[0], nc = curr[1] + dir[1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] == 0) {
                    grid[nr][nc] = 1;  // Mark visited
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        path++;
    }
    
    return -1;
}
```

---

# PATTERN 10: Word Search (LC 79) ⭐⭐

## Pattern Recognition Signal

**When you see:** "find word in grid", "adjacent cells", "path"

**Instant thought:** "DFS backtracking! Try each cell as start, explore paths."

---

## The Mental Model: "The Maze Walker"

```
You're walking through a letter maze trying to spell a word.

At each step:
1. Check if current letter matches
2. If yes, mark as visited and try all 4 directions
3. If we complete the word → found it!
4. If stuck, BACKTRACK (unmark and try different path)

Key: Must UNMARK after exploring (backtracking)
```

---

## Visual Dry Run

**Input:**
```
board = [["A","B","C","E"],
         ["S","F","C","S"],
         ["A","D","E","E"]]
word = "ABCCED"
```

```
═══════════════════════════════════════════════════════════════

Step 1: Start search, try (0,0) for 'A'
  word[0] = 'A', board[0][0] = 'A' ✓ MATCH!
  Mark visited: board[0][0] = '#'
  
  Grid:
  [#] B  C  E
   S  F  C  S
   A  D  E  E
  
  Call Stack: [(0,0,'A')]

═══════════════════════════════════════════════════════════════

Step 2: From (0,0), look for 'B' (index 1)
  Try right (0,1): board[0][1] = 'B' ✓ MATCH!
  Mark visited: board[0][1] = '#'
  
  Grid:
  [#][#] C  E
   S  F  C  S
   A  D  E  E
  
  Call Stack: [(0,0,'A'), (0,1,'B')]

═══════════════════════════════════════════════════════════════

Step 3: From (0,1), look for 'C' (index 2)
  Try right (0,2): board[0][2] = 'C' ✓ MATCH!
  Mark visited: board[0][2] = '#'
  
  Grid:
  [#][#][#] E
   S  F  C  S
   A  D  E  E
  
  Call Stack: [(0,0,'A'), (0,1,'B'), (0,2,'C')]

═══════════════════════════════════════════════════════════════

Step 4: From (0,2), look for 'C' (index 3)
  Try down (1,2): board[1][2] = 'C' ✓ MATCH!
  Mark visited: board[1][2] = '#'
  
  Grid:
  [#][#][#] E
   S  F [#] S
   A  D  E  E
  
  Call Stack: [(0,0,'A'), (0,1,'B'), (0,2,'C'), (1,2,'C')]

═══════════════════════════════════════════════════════════════

Step 5: From (1,2), look for 'E' (index 4)
  Try down (2,2): board[2][2] = 'E' ✓ MATCH!
  Mark visited: board[2][2] = '#'
  
  Grid:
  [#][#][#] E
   S  F [#] S
   A  D [#] E
  
  Call Stack: [(0,0,'A'), (0,1,'B'), (0,2,'C'), (1,2,'C'), (2,2,'E')]

═══════════════════════════════════════════════════════════════

Step 6: From (2,2), look for 'D' (index 5)
  Try left (2,1): board[2][1] = 'D' ✓ MATCH!
  Mark visited: board[2][1] = '#'
  
  Grid:
  [#][#][#] E
   S  F [#] S
   A [#][#] E
  
  Call Stack: [(0,0,'A'), (0,1,'B'), (0,2,'C'), (1,2,'C'), (2,2,'E'), (2,1,'D')]

═══════════════════════════════════════════════════════════════

Step 7: index = 6 = word.length()
  WORD COMPLETE! Return true

═══════════════════════════════════════════════════════════════

Final: Return true ✓

Path found: A(0,0) → B(0,1) → C(0,2) → C(1,2) → E(2,2) → D(2,1)
```

---

## The Code

```java
boolean exist(char[][] board, String word) {
    for (int r = 0; r < board.length; r++) {
        for (int c = 0; c < board[0].length; c++) {
            if (dfs(board, word, r, c, 0)) {
                return true;
            }
        }
    }
    return false;
}

boolean dfs(char[][] board, String word, int r, int c, int index) {
    // Found complete word
    if (index == word.length()) return true;
    
    // Bounds check + character match
    if (r < 0 || r >= board.length || 
        c < 0 || c >= board[0].length ||
        board[r][c] != word.charAt(index)) {
        return false;
    }
    
    // Mark visited (save original)
    char temp = board[r][c];
    board[r][c] = '#';
    
    // Explore all 4 directions
    boolean found = dfs(board, word, r+1, c, index+1) ||
                    dfs(board, word, r-1, c, index+1) ||
                    dfs(board, word, r, c+1, index+1) ||
                    dfs(board, word, r, c-1, index+1);
    
    // BACKTRACK: Restore original character
    board[r][c] = temp;
    
    return found;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Not backtracking | Can't reuse cells in different paths | Restore after DFS |
| Using visited array | Extra space, slower | Modify board in-place |
| Checking index at wrong place | Off-by-one errors | Check at START of DFS |

---

## Mind-Map Anchor

```
"FIND WORD IN GRID"
        │
        ▼
┌─────────────────────────────────────────┐
│ DFS Backtracking                        │
│ 1. Match current char                   │
│ 2. Mark visited (temp = board[r][c])    │
│ 3. Explore 4 directions                 │
│ 4. BACKTRACK (restore board[r][c])      │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Match, mark, explore, restore."

---

# PATTERN 16: Course Schedule II (LC 210) ⭐

## Pattern Recognition Signal

**When you see:** "order of courses", "prerequisites", "topological"

**Instant thought:** "Topological Sort! Use Kahn's (BFS) or DFS post-order."

---

## The Mental Model: "The Dependency Chain"

```
Courses are like tasks with dependencies.
You can only take a course after completing its prerequisites.

Topological Sort gives an ORDER where all dependencies come first.

Two approaches:
1. Kahn's (BFS): Start with courses that have NO prerequisites
2. DFS: Post-order traversal, reverse at end
```

---

## Visual Dry Run

**Input:** `numCourses = 4, prerequisites = [[1,0], [2,0], [3,1], [3,2]]`

```
Graph:
  0 → 1 → 3
  ↓       ↑
  2 ──────┘

Indegree: [0, 1, 1, 2]
          (0 has no prereqs, 3 needs 2 courses)

═══════════════════════════════════════════════════════════════

Step 1: Initialize
  Queue: [0]  ← only node with indegree 0
  Indegree: [0, 1, 1, 2]
  Result: []

═══════════════════════════════════════════════════════════════

Step 2: Process node 0
  Remove 0 from queue, add to result
  Decrease indegree of neighbors (1, 2)
  
  Queue: [1, 2]  ← both now have indegree 0
  Indegree: [0, 0, 0, 2]
  Result: [0]

═══════════════════════════════════════════════════════════════

Step 3: Process node 1
  Remove 1 from queue, add to result
  Decrease indegree of neighbor (3)
  
  Queue: [2]
  Indegree: [0, 0, 0, 1]
  Result: [0, 1]

═══════════════════════════════════════════════════════════════

Step 4: Process node 2
  Remove 2 from queue, add to result
  Decrease indegree of neighbor (3)
  
  Queue: [3]  ← 3 now has indegree 0
  Indegree: [0, 0, 0, 0]
  Result: [0, 1, 2]

═══════════════════════════════════════════════════════════════

Step 5: Process node 3
  Remove 3 from queue, add to result
  No neighbors to update
  
  Queue: []
  Indegree: [0, 0, 0, 0]
  Result: [0, 1, 2, 3]

═══════════════════════════════════════════════════════════════

Step 6: Check completion
  Result size (4) == numCourses (4) ✓
  
Final: Return [0, 1, 2, 3] ✓

Valid order: Take 0 first, then 1 or 2, finally 3
```

---

## Kahn's Algorithm (BFS with Indegree)

```java
int[] findOrder(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    int[] indegree = new int[numCourses];
    
    for (int i = 0; i < numCourses; i++) 
        graph.add(new ArrayList<>());
    
    // Build graph and count indegrees
    for (int[] pre : prerequisites) {
        graph.get(pre[1]).add(pre[0]);  // pre[1] → pre[0]
        indegree[pre[0]]++;
    }
    
    // Start with nodes that have no prerequisites
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
        if (indegree[i] == 0) queue.offer(i);
    }
    
    int[] result = new int[numCourses];
    int index = 0;
    
    while (!queue.isEmpty()) {
        int course = queue.poll();
        result[index++] = course;
        
        for (int next : graph.get(course)) {
            indegree[next]--;
            if (indegree[next] == 0) {
                queue.offer(next);
            }
        }
    }
    
    // If we processed all courses, return order; else cycle exists
    return index == numCourses ? result : new int[0];
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Wrong edge direction | Dependency goes wrong way | pre[1] → pre[0] |
| Not detecting cycle | Return invalid order | Check index == numCourses |
| Forgetting to decrement indegree | Nodes never become ready | indegree[next]-- |

---

# PATTERN 19: Word Ladder (LC 127) ⭐⭐

## Pattern Recognition Signal

**When you see:** "transform word", "one letter at a time", "shortest"

**Instant thought:** "BFS on implicit graph! Words are nodes, edges = 1 letter diff."

---

## The Mental Model: "The Word Network"

```
Each word is a NODE in a graph.
Two words are CONNECTED if they differ by exactly 1 letter.

Finding shortest transformation = BFS shortest path!

hit → hot → dot → dog → cog
 └─1─┘ └─1─┘ └─1─┘ └─1─┘
 
Total: 5 words (4 transformations)
```

---

## Visual Dry Run

**Input:** `beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]`

```
═══════════════════════════════════════════════════════════════

Step 1: Level 1 - Initialize
  Queue: ["hit"]
  WordSet: {hot, dot, dog, lot, log, cog}
  Level: 1

═══════════════════════════════════════════════════════════════

Step 2: Process "hit"
  Try all single-letter changes:
    h→a: "ait" ✗  h→b: "bit" ✗  ... h→h: skip
    i→a: "hat" ✗  i→o: "hot" ✓ FOUND IN SET!
    t→a: "hia" ✗  ...
  
  Queue: ["hot"]
  WordSet: {dot, dog, lot, log, cog}  ← removed "hot"
  Level: 2

═══════════════════════════════════════════════════════════════

Step 3: Level 2 - Process "hot"
  Try all single-letter changes:
    h→d: "dot" ✓  h→l: "lot" ✓
    o→?: no matches
    t→?: no matches
  
  Queue: ["dot", "lot"]
  WordSet: {dog, log, cog}
  Level: 3

═══════════════════════════════════════════════════════════════

Step 4: Level 3 - Process "dot", "lot"
  "dot": d→d: skip, d→o: "oot" ✗, ... t→g: "dog" ✓
  "lot": l→l: skip, ... t→g: "log" ✓
  
  Queue: ["dog", "log"]
  WordSet: {cog}
  Level: 4

═══════════════════════════════════════════════════════════════

Step 5: Level 4 - Process "dog", "log"
  "dog": d→c: "cog" ✓ FOUND IN SET!
  
  Queue: ["cog"]
  WordSet: {}
  Level: 5

═══════════════════════════════════════════════════════════════

Step 6: Level 5 - Process "cog"
  "cog" == endWord → FOUND!

═══════════════════════════════════════════════════════════════

Final: Return level = 5 ✓

Transformation: hit → hot → dot → dog → cog
```

---

## The Code

```java
int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord)) return 0;
    
    Queue<String> queue = new LinkedList<>();
    queue.offer(beginWord);
    int level = 1;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        
        for (int i = 0; i < size; i++) {
            String word = queue.poll();
            
            if (word.equals(endWord)) return level;
            
            // Try changing each character
            char[] chars = word.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char original = chars[j];
                
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == original) continue;
                    
                    chars[j] = c;
                    String newWord = new String(chars);
                    
                    if (wordSet.contains(newWord)) {
                        queue.offer(newWord);
                        wordSet.remove(newWord);  // Mark visited
                    }
                }
                
                chars[j] = original;  // Restore
            }
        }
        level++;
    }
    
    return 0;  // No path found
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Not removing from wordSet | Revisit same word, TLE | Remove when adding to queue |
| Checking endWord in wordSet | beginWord might not be in list | Only check endWord |
| Returning level-1 | Off by one | Return level (count words, not edges) |

---

## Mind-Map Anchor

```
"SHORTEST WORD TRANSFORMATION"
           │
           ▼
┌─────────────────────────────────────────┐
│ BFS on Implicit Graph                   │
│ Nodes = words                           │
│ Edges = 1 letter difference             │
│ Generate neighbors: try a-z at each pos │
│ Remove from set = mark visited          │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Words are nodes. BFS finds shortest path. Remove visited from set."

---

# PATTERN 25: Topological Sort (Kahn's Algorithm)

## The Algorithm

```
1. Calculate indegree for all nodes
2. Add all nodes with indegree 0 to queue
3. While queue not empty:
   a. Remove node from queue, add to result
   b. For each neighbor, decrease indegree
   c. If neighbor's indegree becomes 0, add to queue
4. If result.size() != numNodes → cycle exists!
```

---

## Visual Dry Run

**Input:** `n = 6, edges = [[5,2], [5,0], [4,0], [4,1], [2,3], [3,1]]`

```
Graph:
    5 ──→ 2 ──→ 3
    │           │
    ↓           ↓
    0 ←── 4 ──→ 1

Indegree: [2, 2, 1, 1, 0, 0]
          (0 needs 5,4) (1 needs 4,3) (2 needs 5) (3 needs 2)

═══════════════════════════════════════════════════════════════

Step 1: Initialize
  Find nodes with indegree 0: [4, 5]
  Queue: [4, 5]
  Indegree: [2, 2, 1, 1, 0, 0]
  Result: []

═══════════════════════════════════════════════════════════════

Step 2: Process node 4
  Remove 4, add to result
  Neighbors of 4: [0, 1]
  Decrease indegree: 0→1, 1→1
  
  Queue: [5, 0]  ← 0 now has indegree 0 (after 5 processes it)
  Indegree: [1, 1, 1, 1, 0, 0]
  Result: [4]

═══════════════════════════════════════════════════════════════

Step 3: Process node 5
  Remove 5, add to result
  Neighbors of 5: [2, 0]
  Decrease indegree: 2→0, 0→0
  
  Queue: [0, 2]  ← both now have indegree 0
  Indegree: [0, 1, 0, 1, 0, 0]
  Result: [4, 5]

═══════════════════════════════════════════════════════════════

Step 4: Process node 0
  Remove 0, add to result
  Neighbors of 0: none
  
  Queue: [2]
  Indegree: [0, 1, 0, 1, 0, 0]
  Result: [4, 5, 0]

═══════════════════════════════════════════════════════════════

Step 5: Process node 2
  Remove 2, add to result
  Neighbors of 2: [3]
  Decrease indegree: 3→0
  
  Queue: [3]  ← 3 now has indegree 0
  Indegree: [0, 1, 0, 0, 0, 0]
  Result: [4, 5, 0, 2]

═══════════════════════════════════════════════════════════════

Step 6: Process node 3
  Remove 3, add to result
  Neighbors of 3: [1]
  Decrease indegree: 1→0
  
  Queue: [1]  ← 1 now has indegree 0
  Indegree: [0, 0, 0, 0, 0, 0]
  Result: [4, 5, 0, 2, 3]

═══════════════════════════════════════════════════════════════

Step 7: Process node 1
  Remove 1, add to result
  Neighbors of 1: none
  
  Queue: []
  Indegree: [0, 0, 0, 0, 0, 0]
  Result: [4, 5, 0, 2, 3, 1]

═══════════════════════════════════════════════════════════════

Step 8: Check completion
  Result size (6) == n (6) ✓ No cycle!

Final: Return [4, 5, 0, 2, 3, 1] ✓

Valid topological order: 4 → 5 → 0 → 2 → 3 → 1
```

---

## Template Code

```java
List<Integer> topologicalSort(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    int[] indegree = new int[n];
    
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        indegree[edge[1]]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (indegree[i] == 0) queue.offer(i);
    }
    
    List<Integer> result = new ArrayList<>();
    
    while (!queue.isEmpty()) {
        int node = queue.poll();
        result.add(node);
        
        for (int neighbor : graph.get(node)) {
            if (--indegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }
    
    return result.size() == n ? result : new ArrayList<>();  // Empty if cycle
}
```

---

---

# PART 2: MATRIX AS GRAPH PATTERNS

> **The Core Insight:** Every matrix cell is a NODE. Adjacent cells are EDGES. Matrix traversal IS graph traversal!

```
Matrix:          Graph View:
[1][2][3]        (0,0)---(0,1)---(0,2)
[4][5][6]          |       |       |
[7][8][9]        (1,0)---(1,1)---(1,2)
                   |       |       |
                 (2,0)---(2,1)---(2,2)
```

## The Universal 4-Directions Array

```java
int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};  // RIGHT, DOWN, LEFT, UP

// Usage:
for (int[] d : dirs) {
    int newRow = row + d[0];
    int newCol = col + d[1];
    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
        // Valid neighbor!
    }
}
```

---

## PATTERN 10: Number of Islands (LC 200)

### Pattern Recognition Signal

> **When you see:** "Count connected components" or "How many groups/islands?"
> **Instant thought:** "DFS/BFS flood fill - mark visited, count starts!"

### The Mental Model: The Cartographer

You're a cartographer flying over an ocean. Every time you spot NEW land, you:
1. Land on it (count +1)
2. Explore the ENTIRE island (mark all connected land)
3. Take off and continue scanning

```
Grid:           After visiting island 1:    After all:
1 1 0 0 0       X X 0 0 0                   X X 0 0 0
1 1 0 0 0  -->  X X 0 0 0              -->  X X 0 0 0
0 0 1 0 0       0 0 1 0 0                   0 0 X 0 0
0 0 0 1 1       0 0 0 1 1                   0 0 0 X X

Count: 1        Count: 1                    Count: 3
```

### Visual Dry Run

```
Grid:
1 1 0 0 0
1 1 0 0 0
0 0 1 0 0
0 0 0 1 1

Step 1: Scan (0,0) = '1' (land!)
  - count = 1
  - DFS flood fill: mark (0,0), (0,1), (1,0), (1,1) as visited
  
Step 2: Scan (0,1) = visited, skip
Step 3: Scan (0,2) = '0' (water), skip
...
Step 7: Scan (2,2) = '1' (NEW land!)
  - count = 2
  - DFS flood fill: mark (2,2) as visited

Step 8-11: Continue scanning...
Step 12: Scan (3,3) = '1' (NEW land!)
  - count = 3
  - DFS flood fill: mark (3,3), (3,4) as visited

Final count = 3
```

### The Code

```java
public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    
    int count = 0;
    int rows = grid.length, cols = grid[0].length;
    
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (grid[r][c] == '1') {      // Found NEW island!
                count++;                   // Count it
                dfs(grid, r, c);          // Sink the entire island
            }
        }
    }
    return count;
}

private void dfs(char[][] grid, int r, int c) {
    // Boundary check + water check
    if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] != '1') {
        return;
    }
    
    grid[r][c] = '0';  // SINK IT! (mark visited by changing to water)
    
    // Explore all 4 directions
    dfs(grid, r + 1, c);  // Down
    dfs(grid, r - 1, c);  // Up
    dfs(grid, r, c + 1);  // Right
    dfs(grid, r, c - 1);  // Left
}
```

### Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Forgetting to mark visited | Infinite loop | Mark BEFORE recursing |
| Using separate visited array | Extra O(mn) space | Modify grid in-place |
| Checking bounds after access | ArrayIndexOutOfBounds | Check bounds FIRST |

### Mind-Map Anchor

```
NUMBER OF ISLANDS
      |
      v
+------------------+
| For each cell:   |
| If '1' -> count++|
| DFS sink island  |
| Mark as '0'      |
+------------------+
```

**Memory phrase:** "Scan, count new land, sink the island"

---

## PATTERN 11: Rotting Oranges (LC 994) - Multi-Source BFS

### Pattern Recognition Signal

> **When you see:** "Spread from multiple sources simultaneously" or "Minimum time to affect all"
> **Instant thought:** "Multi-source BFS - add ALL sources to queue first, level = time!"

### The Mental Model: The Zombie Apocalypse

Multiple zombies start at different locations. Each minute, they infect adjacent humans. How long until everyone is infected (or some survive)?

```
Minute 0:     Minute 1:     Minute 2:
2 1 1         2 2 1         2 2 2
1 1 0    -->  2 1 0    -->  2 2 0
0 1 1         0 1 1         0 2 2

Rotten=2, Fresh=1, Empty=0
Answer: 2 minutes (but check if any fresh remain!)
```

### The Key Insight: Multi-Source BFS

```
WRONG: Run BFS from each rotten orange separately
  - Time complexity: O(k * m * n) where k = rotten count
  - Doesn't simulate simultaneous spreading

RIGHT: Add ALL rotten oranges to queue FIRST, then BFS
  - All sources spread simultaneously
  - Level in BFS = time elapsed
  - Time complexity: O(m * n)
```

### Visual Dry Run

```
Initial:
2 1 1
1 1 0
0 1 1

Step 1: Find all rotten, count fresh
  Queue: [(0,0)]
  Fresh count: 6

Step 2: BFS Level 0 (minute 0 -> 1)
  Process (0,0): Infect neighbors (0,1), (1,0)
  Queue: [(0,1), (1,0)]
  Fresh count: 6 - 2 = 4
  
  Grid now:
  2 2 1
  2 1 0
  0 1 1

Step 3: BFS Level 1 (minute 1 -> 2)
  Process (0,1): Infect (0,2)
  Process (1,0): Infect (1,1)
  Queue: [(0,2), (1,1)]
  Fresh count: 4 - 2 = 2
  
  Grid now:
  2 2 2
  2 2 0
  0 1 1

Step 4: BFS Level 2 (minute 2 -> 3)
  Process (0,2): No fresh neighbors
  Process (1,1): Infect (2,1)
  Queue: [(2,1)]
  Fresh count: 2 - 1 = 1

Step 5: BFS Level 3 (minute 3 -> 4)
  Process (2,1): Infect (2,2)
  Queue: [(2,2)]
  Fresh count: 1 - 1 = 0

Step 6: Fresh count = 0, return minutes = 4
```

### The Code

```java
public int orangesRotting(int[][] grid) {
    int rows = grid.length, cols = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    int fresh = 0;
    
    // Step 1: Add ALL rotten to queue, count fresh
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (grid[r][c] == 2) queue.offer(new int[]{r, c});
            else if (grid[r][c] == 1) fresh++;
        }
    }
    
    if (fresh == 0) return 0;  // No fresh oranges!
    
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    int minutes = 0;
    
    // Step 2: BFS - each level = 1 minute
    while (!queue.isEmpty()) {
        int size = queue.size();  // Process entire level
        boolean infected = false;
        
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            
            for (int[] d : dirs) {
                int nr = curr[0] + d[0];
                int nc = curr[1] + d[1];
                
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == 1) {
                    grid[nr][nc] = 2;  // Infect!
                    queue.offer(new int[]{nr, nc});
                    fresh--;
                    infected = true;
                }
            }
        }
        
        if (infected) minutes++;  // Only count if something rotted
    }
    
    return fresh == 0 ? minutes : -1;  // -1 if fresh remain
}
```

### Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| BFS from one source at a time | Wrong time calculation | Multi-source: add ALL first |
| Forgetting to check fresh == 0 at start | Returns wrong for no fresh | Check before BFS |
| Incrementing minutes every iteration | Overcounts | Only increment if infected |
| Not returning -1 for unreachable | Wrong answer | Check fresh == 0 at end |

### Mind-Map Anchor

```
ROTTING ORANGES
      |
      v
+--------------------+
| 1. Queue ALL rotten|
| 2. Count fresh     |
| 3. BFS by level    |
| 4. Level = minute  |
| 5. Check fresh==0  |
+--------------------+
```

**Memory phrase:** "All zombies start together, level = time, check survivors"

---

## PATTERN 12: Pacific Atlantic Water Flow (LC 417)

### Pattern Recognition Signal

> **When you see:** "Can reach both boundaries" or "Flow from edges"
> **Instant thought:** "Reverse BFS/DFS from boundaries, find intersection!"

### The Mental Model: Reverse the Flow

**WRONG thinking:** For each cell, check if water can flow to both oceans (expensive!)

**RIGHT thinking:** Start from oceans, flow UPHILL (reverse), find cells reachable by BOTH

```
Pacific ~   ~   ~   ~   ~
       ~  1   2   2   3  (5) *
       ~  3   2   3  (4) (4) *
       ~  2   4  (5)  3   1  *
       ~ (6) (7)  1   4   5  *
       ~ (5)  1   1   2   4  *
          *   *   *   *   * Atlantic

Cells in () can reach BOTH oceans
```

### Visual Dry Run

```
Heights:
1 2 2 3 5
3 2 3 4 4
2 4 5 3 1
6 7 1 4 5
5 1 1 2 4

Step 1: BFS from Pacific (top row + left col)
  Start: (0,0), (0,1), (0,2), (0,3), (0,4), (1,0), (2,0), (3,0), (4,0)
  
  Flow uphill (to cells with height >= current):
  Pacific reachable:
  P P P P P
  P P P P P
  P P P . .
  P P . P P
  P . . P P

Step 2: BFS from Atlantic (bottom row + right col)
  Start: (4,0)...(4,4), (0,4)...(3,4)
  
  Atlantic reachable:
  . . . A A
  . . A A A
  . A A A A
  A A . A A
  A A A A A

Step 3: Intersection (both P and A):
  . . . . P&A
  . . . P&A P&A
  . . P&A . .
  P&A P&A . P&A P&A
  P&A . . . .

Result: [[0,4], [1,3], [1,4], [2,2], [3,0], [3,1], [3,3], [3,4], [4,0]]
```

### The Code

```java
public List<List<Integer>> pacificAtlantic(int[][] heights) {
    List<List<Integer>> result = new ArrayList<>();
    if (heights == null || heights.length == 0) return result;
    
    int rows = heights.length, cols = heights[0].length;
    boolean[][] pacific = new boolean[rows][cols];
    boolean[][] atlantic = new boolean[rows][cols];
    
    // BFS from Pacific (top + left edges)
    Queue<int[]> pQueue = new LinkedList<>();
    for (int c = 0; c < cols; c++) { pQueue.offer(new int[]{0, c}); pacific[0][c] = true; }
    for (int r = 1; r < rows; r++) { pQueue.offer(new int[]{r, 0}); pacific[r][0] = true; }
    bfs(heights, pQueue, pacific);
    
    // BFS from Atlantic (bottom + right edges)
    Queue<int[]> aQueue = new LinkedList<>();
    for (int c = 0; c < cols; c++) { aQueue.offer(new int[]{rows-1, c}); atlantic[rows-1][c] = true; }
    for (int r = 0; r < rows-1; r++) { aQueue.offer(new int[]{r, cols-1}); atlantic[r][cols-1] = true; }
    bfs(heights, aQueue, atlantic);
    
    // Find intersection
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (pacific[r][c] && atlantic[r][c]) {
                result.add(Arrays.asList(r, c));
            }
        }
    }
    return result;
}

private void bfs(int[][] heights, Queue<int[]> queue, boolean[][] reachable) {
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    int rows = heights.length, cols = heights[0].length;
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        
        for (int[] d : dirs) {
            int nr = curr[0] + d[0];
            int nc = curr[1] + d[1];
            
            // Flow UPHILL (reverse): neighbor height >= current height
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols 
                && !reachable[nr][nc] 
                && heights[nr][nc] >= heights[curr[0]][curr[1]]) {
                reachable[nr][nc] = true;
                queue.offer(new int[]{nr, nc});
            }
        }
    }
}
```

### Mind-Map Anchor

```
PACIFIC ATLANTIC
      |
      v
+---------------------+
| 1. Reverse thinking |
| 2. BFS from Pacific |
| 3. BFS from Atlantic|
| 4. Flow UPHILL      |
| 5. Find intersection|
+---------------------+
```

**Memory phrase:** "Reverse flow from oceans, meet in the middle"

---

## PATTERN 13: Shortest Path in Binary Matrix (LC 1091)

### Pattern Recognition Signal

> **When you see:** "Shortest path in grid" or "Minimum steps to reach"
> **Instant thought:** "BFS! First time we reach destination = shortest path"

### The Mental Model: The Expanding Ripple

Drop a stone in water. Ripples expand equally in ALL directions. The first ripple to hit the shore = shortest distance.

BFS explores level by level. Level = distance from start. First time we reach target = minimum distance!

### Key Difference: 8 Directions!

```java
// 4 directions (standard):
int[][] dirs4 = {{0,1}, {1,0}, {0,-1}, {-1,0}};

// 8 directions (including diagonals):
int[][] dirs8 = {{0,1}, {1,0}, {0,-1}, {-1,0}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};
```

### Visual Dry Run

```
Grid (0 = open, 1 = blocked):
0 0 0
1 1 0
1 1 0

Start: (0,0), End: (2,2)

BFS Level 0: [(0,0)]
  Distance: 1 (we count the starting cell)

BFS Level 1: Process (0,0)
  Valid neighbors: (0,1), (1,0)❌blocked, (1,1)❌blocked
  Queue: [(0,1)]
  Distance at (0,1): 2

BFS Level 2: Process (0,1)
  Valid neighbors: (0,2), (1,2), (1,1)❌blocked
  Queue: [(0,2), (1,2)]
  Distance at (0,2): 3, (1,2): 3

BFS Level 3: Process (0,2), (1,2)
  From (0,2): neighbors (1,2)✓already queued
  From (1,2): neighbors (2,2)✓ TARGET FOUND!
  Distance at (2,2): 4

Return 4
```

### The Code

```java
public int shortestPathBinaryMatrix(int[][] grid) {
    int n = grid.length;
    if (grid[0][0] == 1 || grid[n-1][n-1] == 1) return -1;  // Start/end blocked
    
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{0, 0});
    grid[0][0] = 1;  // Mark visited (change to blocked)
    int distance = 1;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            
            if (curr[0] == n-1 && curr[1] == n-1) return distance;  // Reached end!
            
            for (int[] d : dirs) {
                int nr = curr[0] + d[0];
                int nc = curr[1] + d[1];
                
                if (nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] == 0) {
                    grid[nr][nc] = 1;  // Mark visited
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        distance++;
    }
    
    return -1;  // Can't reach end
}
```

### Mind-Map Anchor

```
SHORTEST PATH BINARY MATRIX
         |
         v
+-------------------+
| BFS = shortest    |
| 8 directions!     |
| Level = distance  |
| First reach = ans |
+-------------------+
```

**Memory phrase:** "BFS ripple, 8 directions, first touch wins"

---

## PATTERN 14: Clone Graph (LC 133)

### Pattern Recognition Signal

> **When you see:** "Deep copy a graph" or "Clone with all connections"
> **Instant thought:** "HashMap: original -> clone, DFS/BFS to traverse"

### The Mental Model: The Cloning Machine

You have a cloning machine. For each person:
1. Check if already cloned (HashMap lookup)
2. If not, create clone and register in HashMap
3. Clone all their friends recursively

### Visual Dry Run

```
Original Graph:
    1 --- 2
    |     |
    4 --- 3

Step 1: Start at node 1
  - Create clone of 1: clone1
  - Map: {1 -> clone1}
  - Process neighbors: 2, 4

Step 2: Clone node 2
  - Create clone of 2: clone2
  - Map: {1 -> clone1, 2 -> clone2}
  - Connect clone1 -- clone2
  - Process neighbors: 1 (already cloned), 3

Step 3: Clone node 3
  - Create clone of 3: clone3
  - Map: {1 -> clone1, 2 -> clone2, 3 -> clone3}
  - Connect clone2 -- clone3
  - Process neighbors: 2 (done), 4

Step 4: Clone node 4
  - Create clone of 4: clone4
  - Map: {1 -> clone1, 2 -> clone2, 3 -> clone3, 4 -> clone4}
  - Connect clone3 -- clone4
  - Connect clone4 -- clone1

Cloned Graph:
    clone1 --- clone2
      |          |
    clone4 --- clone3
```

### The Code

```java
public Node cloneGraph(Node node) {
    if (node == null) return null;
    
    Map<Node, Node> map = new HashMap<>();  // original -> clone
    return dfs(node, map);
}

private Node dfs(Node node, Map<Node, Node> map) {
    // Already cloned? Return the clone!
    if (map.containsKey(node)) return map.get(node);
    
    // Create clone (without neighbors yet)
    Node clone = new Node(node.val);
    map.put(node, clone);  // Register BEFORE recursing (handles cycles!)
    
    // Clone all neighbors
    for (Node neighbor : node.neighbors) {
        clone.neighbors.add(dfs(neighbor, map));
    }
    
    return clone;
}
```

### Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Not handling cycles | Infinite recursion | Check map BEFORE creating |
| Adding to map AFTER recursion | Cycles cause duplicates | Add to map BEFORE recursing |
| Shallow copy of neighbors | Points to original nodes | Recursively clone neighbors |

### Mind-Map Anchor

```
CLONE GRAPH
    |
    v
+------------------+
| Map: old -> new  |
| Check map first  |
| Add BEFORE recurse|
| Clone neighbors  |
+------------------+
```

**Memory phrase:** "Map old to new, register before recursing"

---

## PATTERN 15: Course Schedule (LC 207) - Cycle Detection

### Pattern Recognition Signal

> **When you see:** "Can finish all tasks with prerequisites" or "Is ordering possible"
> **Instant thought:** "Cycle detection in directed graph! Cycle = impossible"

### The Mental Model: The Chicken-Egg Problem

If Course A requires B, and B requires A... you can NEVER start! That's a cycle.

```
A -> B -> C -> A  (CYCLE! Can't finish)
A -> B -> C      (No cycle, can finish: C, B, A)
```

### Two Approaches

**Approach 1: Kahn's Algorithm (BFS)**
- Count indegrees
- Start with indegree=0 nodes
- If we process all nodes, no cycle!

**Approach 2: DFS with 3 Colors**
- WHITE (0): Not visited
- GRAY (1): Currently in recursion stack
- BLACK (2): Completely processed

If we visit a GRAY node, we found a cycle!

### Visual Dry Run (3-Color DFS)

```
Courses: 4, Prerequisites: [[1,0], [2,0], [3,1], [3,2]]

Graph:
0 -> 1 -> 3
 \-> 2 -/

Initial colors: [WHITE, WHITE, WHITE, WHITE]

DFS from 0:
  Color 0 = GRAY: [GRAY, WHITE, WHITE, WHITE]
  
  Visit neighbor 1:
    Color 1 = GRAY: [GRAY, GRAY, WHITE, WHITE]
    
    Visit neighbor 3:
      Color 3 = GRAY: [GRAY, GRAY, WHITE, GRAY]
      No neighbors, Color 3 = BLACK: [GRAY, GRAY, WHITE, BLACK]
    
    Color 1 = BLACK: [GRAY, BLACK, WHITE, BLACK]
  
  Visit neighbor 2:
    Color 2 = GRAY: [GRAY, BLACK, GRAY, BLACK]
    
    Visit neighbor 3:
      Color 3 = BLACK (already done), skip
    
    Color 2 = BLACK: [GRAY, BLACK, BLACK, BLACK]
  
  Color 0 = BLACK: [BLACK, BLACK, BLACK, BLACK]

All nodes BLACK, no GRAY->GRAY edge found = NO CYCLE!
Return true (can finish)
```

### The Code (3-Color DFS)

```java
public boolean canFinish(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
    
    for (int[] pre : prerequisites) {
        graph.get(pre[1]).add(pre[0]);  // pre[1] -> pre[0]
    }
    
    int[] color = new int[numCourses];  // 0=WHITE, 1=GRAY, 2=BLACK
    
    for (int i = 0; i < numCourses; i++) {
        if (color[i] == 0 && hasCycle(graph, i, color)) {
            return false;  // Cycle found!
        }
    }
    return true;
}

private boolean hasCycle(List<List<Integer>> graph, int node, int[] color) {
    color[node] = 1;  // GRAY: currently processing
    
    for (int neighbor : graph.get(node)) {
        if (color[neighbor] == 1) return true;   // GRAY -> GRAY = CYCLE!
        if (color[neighbor] == 0 && hasCycle(graph, neighbor, color)) {
            return true;
        }
    }
    
    color[node] = 2;  // BLACK: done processing
    return false;
}
```

### Mind-Map Anchor

```
COURSE SCHEDULE (CYCLE DETECTION)
            |
            v
+------------------------+
| 3 Colors: W -> G -> B  |
| GRAY = in progress     |
| GRAY meets GRAY = CYCLE|
| All BLACK = no cycle   |
+------------------------+
```

**Memory phrase:** "Gray meets gray = cycle detected"

---

## PATTERN 16: Course Schedule II (LC 210) - Topological Sort

### Pattern Recognition Signal

> **When you see:** "Order tasks respecting dependencies" or "Find valid sequence"
> **Instant thought:** "Topological sort! Kahn's BFS or DFS post-order"

### The Mental Model: The Assembly Line

You're building a car. Some parts must be installed before others:
- Engine before hood
- Wheels before tires
- Frame before everything

Topological sort gives you a valid assembly order!

### Kahn's Algorithm (BFS)

```
1. Calculate indegree for each node
2. Add all indegree=0 nodes to queue (no prerequisites!)
3. Process queue:
   - Remove node, add to result
   - Decrease indegree of neighbors
   - If neighbor's indegree becomes 0, add to queue
4. If result.size() == n, valid order exists!
```

### Visual Dry Run

```
Courses: 4, Prerequisites: [[1,0], [2,0], [3,1], [3,2]]

Graph: 0 -> 1 -> 3
        \-> 2 -/

Indegrees: [0, 1, 1, 2]
           0 has 0 prereqs
           1 needs 0
           2 needs 0
           3 needs 1 and 2

Step 1: Queue nodes with indegree 0
  Queue: [0]
  Result: []

Step 2: Process 0
  Result: [0]
  Decrease indegree of 1, 2
  Indegrees: [0, 0, 0, 2]
  Queue: [1, 2]

Step 3: Process 1
  Result: [0, 1]
  Decrease indegree of 3
  Indegrees: [0, 0, 0, 1]
  Queue: [2]

Step 4: Process 2
  Result: [0, 1, 2]
  Decrease indegree of 3
  Indegrees: [0, 0, 0, 0]
  Queue: [3]

Step 5: Process 3
  Result: [0, 1, 2, 3]
  Queue: []

Result size (4) == numCourses (4) ✓
Return [0, 1, 2, 3]
```

### The Code

```java
public int[] findOrder(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    int[] indegree = new int[numCourses];
    
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
    
    for (int[] pre : prerequisites) {
        graph.get(pre[1]).add(pre[0]);
        indegree[pre[0]]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
        if (indegree[i] == 0) queue.offer(i);
    }
    
    int[] result = new int[numCourses];
    int index = 0;
    
    while (!queue.isEmpty()) {
        int course = queue.poll();
        result[index++] = course;
        
        for (int next : graph.get(course)) {
            if (--indegree[next] == 0) {
                queue.offer(next);
            }
        }
    }
    
    return index == numCourses ? result : new int[0];
}
```

### Mind-Map Anchor

```
TOPOLOGICAL SORT (KAHN'S)
          |
          v
+---------------------+
| 1. Calc indegrees   |
| 2. Queue indegree=0 |
| 3. Process & reduce |
| 4. Add when ind=0   |
| 5. Check size == n  |
+---------------------+
```

**Memory phrase:** "Start with no prereqs, reduce neighbors, queue when free"

---

## PATTERN 17: Is Graph Bipartite (LC 785)

### Pattern Recognition Signal

> **When you see:** "Two groups with no internal edges" or "2-colorable"
> **Instant thought:** "BFS/DFS coloring - alternate colors, conflict = not bipartite"

### The Mental Model: The Party Seating

You're seating guests at a wedding. Two tables: Bride's side and Groom's side. Rule: No two people who dislike each other can sit at the same table.

If you can seat everyone following this rule = Bipartite!

```
Bipartite:          Not Bipartite:
  A---B               A---B
  |   |               |\ /|
  C---D               | X |
                      |/ \|
A,D = Table 1         C---D
B,C = Table 2
                    A-B-C forms odd cycle!
```

### Visual Dry Run

```
Graph: [[1,3], [0,2], [1,3], [0,2]]

Adjacency:
0 -- 1
|    |
3 -- 2

BFS Coloring:
Step 1: Start at 0, color RED
  Colors: [RED, -, -, -]
  Queue: [0]

Step 2: Process 0, color neighbors BLUE
  Neighbors: 1, 3
  Colors: [RED, BLUE, -, BLUE]
  Queue: [1, 3]

Step 3: Process 1, color neighbors RED
  Neighbors: 0 (RED, same as expected ✓), 2 (uncolored)
  Colors: [RED, BLUE, RED, BLUE]
  Queue: [3, 2]

Step 4: Process 3, color neighbors RED
  Neighbors: 0 (RED ✓), 2 (RED ✓ already colored correctly)
  Queue: [2]

Step 5: Process 2, check neighbors
  Neighbors: 1 (BLUE ✓), 3 (BLUE ✓)
  All consistent!

Return TRUE - Graph is bipartite!
```

### The Code

```java
public boolean isBipartite(int[][] graph) {
    int n = graph.length;
    int[] colors = new int[n];  // 0 = uncolored, 1 = RED, -1 = BLUE
    
    for (int i = 0; i < n; i++) {
        if (colors[i] == 0 && !bfs(graph, i, colors)) {
            return false;
        }
    }
    return true;
}

private boolean bfs(int[][] graph, int start, int[] colors) {
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(start);
    colors[start] = 1;  // Color RED
    
    while (!queue.isEmpty()) {
        int node = queue.poll();
        
        for (int neighbor : graph[node]) {
            if (colors[neighbor] == 0) {
                colors[neighbor] = -colors[node];  // Opposite color!
                queue.offer(neighbor);
            } else if (colors[neighbor] == colors[node]) {
                return false;  // Same color = NOT bipartite!
            }
        }
    }
    return true;
}
```

### Mind-Map Anchor

```
BIPARTITE CHECK
      |
      v
+-------------------+
| 2 colors: 1, -1   |
| Neighbor = -color |
| Same color = FAIL |
| Odd cycle = FAIL  |
+-------------------+
```

**Memory phrase:** "Alternate colors, same color neighbors = not bipartite"

---

## PATTERN 18: Alien Dictionary (LC 269)

### Pattern Recognition Signal

> **When you see:** "Derive order from sorted list" or "Find character ordering"
> **Instant thought:** "Build graph from adjacent word pairs, topological sort!"

### The Mental Model: The Detective

You're a detective. You found a sorted dictionary in an alien language. By comparing adjacent words, you can deduce the alphabet order!

```
Words: ["wrt", "wrf", "er", "ett", "rftt"]

Compare adjacent pairs:
"wrt" vs "wrf" → t comes before f (first diff: t vs f)
"wrf" vs "er"  → w comes before e (first diff: w vs e)
"er" vs "ett"  → r comes before t (first diff: r vs t)
"ett" vs "rftt"→ e comes before r (first diff: e vs r)

Graph edges: t->f, w->e, r->t, e->r
Topo sort: w -> e -> r -> t -> f
```

### Visual Dry Run

```
Words: ["wrt", "wrf", "er", "ett", "rftt"]

Step 1: Build graph from adjacent pairs

"wrt" vs "wrf":
  w=w, r=r, t≠f → t -> f

"wrf" vs "er":
  w≠e → w -> e

"er" vs "ett":
  e=e, r≠t → r -> t

"ett" vs "rftt":
  e≠r → e -> r

Graph:
  w -> e -> r -> t -> f

Step 2: Topological sort (Kahn's)
  Indegrees: {w:0, e:1, r:1, t:1, f:1}
  
  Queue: [w]
  Result: []
  
  Process w: Result=[w], reduce e's indegree
  Indegrees: {e:0, r:1, t:1, f:1}
  Queue: [e]
  
  Process e: Result=[w,e], reduce r's indegree
  Queue: [r]
  
  Process r: Result=[w,e,r], reduce t's indegree
  Queue: [t]
  
  Process t: Result=[w,e,r,t], reduce f's indegree
  Queue: [f]
  
  Process f: Result=[w,e,r,t,f]

Return "wertf"
```

### The Code

```java
public String alienOrder(String[] words) {
    // Step 1: Initialize graph with all characters
    Map<Character, Set<Character>> graph = new HashMap<>();
    Map<Character, Integer> indegree = new HashMap<>();
    
    for (String word : words) {
        for (char c : word.toCharArray()) {
            graph.putIfAbsent(c, new HashSet<>());
            indegree.putIfAbsent(c, 0);
        }
    }
    
    // Step 2: Build edges from adjacent word pairs
    for (int i = 0; i < words.length - 1; i++) {
        String w1 = words[i], w2 = words[i + 1];
        
        // Edge case: "abc" before "ab" is INVALID!
        if (w1.length() > w2.length() && w1.startsWith(w2)) {
            return "";
        }
        
        // Find first different character
        for (int j = 0; j < Math.min(w1.length(), w2.length()); j++) {
            char c1 = w1.charAt(j), c2 = w2.charAt(j);
            if (c1 != c2) {
                if (!graph.get(c1).contains(c2)) {
                    graph.get(c1).add(c2);
                    indegree.put(c2, indegree.get(c2) + 1);
                }
                break;  // Only first diff matters!
            }
        }
    }
    
    // Step 3: Topological sort
    Queue<Character> queue = new LinkedList<>();
    for (char c : indegree.keySet()) {
        if (indegree.get(c) == 0) queue.offer(c);
    }
    
    StringBuilder result = new StringBuilder();
    while (!queue.isEmpty()) {
        char c = queue.poll();
        result.append(c);
        
        for (char next : graph.get(c)) {
            indegree.put(next, indegree.get(next) - 1);
            if (indegree.get(next) == 0) queue.offer(next);
        }
    }
    
    // Check if all characters included (no cycle)
    return result.length() == indegree.size() ? result.toString() : "";
}
```

### Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Not checking prefix case | "abc" before "ab" is invalid | Check startsWith |
| Using all char diffs | Only first diff gives order | Break after first diff |
| Missing characters | Some chars have no edges | Initialize all chars first |
| Not detecting cycle | Invalid input | Check result length |

### Mind-Map Anchor

```
ALIEN DICTIONARY
       |
       v
+----------------------+
| 1. Init all chars    |
| 2. Compare adj words |
| 3. First diff = edge |
| 4. Check prefix trap |
| 5. Topo sort result  |
+----------------------+
```

**Memory phrase:** "Adjacent words, first diff = edge, topo sort the alphabet"

---

## PATTERN 19: Surrounded Regions (LC 130)

### Pattern Recognition Signal

> **When you see:** "Capture all except border-connected" or "Flip interior regions"
> **Instant thought:** "Mark border-connected first, then flip the rest!"

### The Mental Model: The Island Invasion

You're invading an island. The enemy can escape via the border. Strategy:
1. First, mark all border-connected regions as "safe" (they can escape)
2. Then, capture everything else!

```
Before:          Mark border 'O's:    After capture:
X X X X          X X X X              X X X X
X O O X    →     X O O X         →    X X X X
X X O X          X X O X              X X X X
X O X X          X S X X              X O X X

S = Safe (border-connected), stays 'O'
Interior O's become X
```

### Visual Dry Run

```
Board:
X X X X
X O O X
X X O X
X O X X

Step 1: DFS from all border 'O's, mark as 'S' (safe)
  Border cells: (3,1) is 'O'
  DFS from (3,1): Only (3,1) is connected
  
  Board after marking:
  X X X X
  X O O X
  X X O X
  X S X X

Step 2: Scan entire board
  - 'O' → 'X' (captured!)
  - 'S' → 'O' (restore safe)
  - 'X' → 'X' (unchanged)

Final:
X X X X
X X X X
X X X X
X O X X
```

### The Code

```java
public void solve(char[][] board) {
    if (board == null || board.length == 0) return;
    
    int rows = board.length, cols = board[0].length;
    
    // Step 1: Mark border-connected 'O's as 'S' (safe)
    for (int r = 0; r < rows; r++) {
        if (board[r][0] == 'O') dfs(board, r, 0);
        if (board[r][cols-1] == 'O') dfs(board, r, cols-1);
    }
    for (int c = 0; c < cols; c++) {
        if (board[0][c] == 'O') dfs(board, 0, c);
        if (board[rows-1][c] == 'O') dfs(board, rows-1, c);
    }
    
    // Step 2: Capture and restore
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (board[r][c] == 'O') board[r][c] = 'X';       // Capture!
            else if (board[r][c] == 'S') board[r][c] = 'O';  // Restore safe
        }
    }
}

private void dfs(char[][] board, int r, int c) {
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c] != 'O') {
        return;
    }
    board[r][c] = 'S';  // Mark safe
    dfs(board, r+1, c);
    dfs(board, r-1, c);
    dfs(board, r, c+1);
    dfs(board, r, c-1);
}
```

### Mind-Map Anchor

```
SURROUNDED REGIONS
        |
        v
+--------------------+
| 1. DFS from borders|
| 2. Mark safe as 'S'|
| 3. O -> X (capture)|
| 4. S -> O (restore)|
+--------------------+
```

**Memory phrase:** "Save the border-connected, capture the rest"

---

## PATTERN 20: Walls and Gates (LC 286) - Multi-Source BFS

### Pattern Recognition Signal

> **When you see:** "Distance to nearest X from every cell" or "Fill with distances"
> **Instant thought:** "Multi-source BFS from all X's simultaneously!"

### The Mental Model: The Fire Drill

Gates are exits. Start a "fire" from ALL gates simultaneously. The fire spreads one step per second. When fire reaches a room, that's the distance to nearest gate!

```
INF = Empty room, -1 = Wall, 0 = Gate

Before:           After:
INF -1  0  INF    3  -1  0   1
INF INF INF -1    2   2  1  -1
INF -1  INF -1    1  -1  2  -1
  0 -1  INF INF   0  -1  3   4
```

### Visual Dry Run

```
Initial:
INF -1   0  INF
INF INF INF  -1
INF  -1 INF  -1
  0  -1 INF INF

Step 1: Add all gates (0s) to queue
  Queue: [(0,2), (3,0)]

Step 2: BFS Level 1 (distance = 1)
  From (0,2): Update (0,3)=1, (1,2)=1
  From (3,0): Update (2,0)=1
  Queue: [(0,3), (1,2), (2,0)]
  
  Grid:
  INF -1   0   1
  INF INF  1  -1
    1  -1 INF  -1
    0  -1 INF INF

Step 3: BFS Level 2 (distance = 2)
  From (0,3): No valid neighbors
  From (1,2): Update (1,1)=2
  From (2,0): Update (1,0)=2
  Queue: [(1,1), (1,0)]
  
  Grid:
  INF -1   0   1
    2   2   1  -1
    1  -1 INF  -1
    0  -1 INF INF

Step 4: BFS Level 3 (distance = 3)
  From (1,1): Update (0,1)? No, it's wall. Update (2,1)? Wall.
  From (1,0): Update (0,0)=3
  Queue: [(0,0)]
  
  Grid:
    3  -1   0   1
    2   2   1  -1
    1  -1 INF  -1
    0  -1 INF INF

Continue until queue empty...

Final:
  3  -1   0   1
  2   2   1  -1
  1  -1   2  -1
  0  -1   3   4
```

### The Code

```java
public void wallsAndGates(int[][] rooms) {
    if (rooms == null || rooms.length == 0) return;
    
    int rows = rooms.length, cols = rooms[0].length;
    int INF = Integer.MAX_VALUE;
    Queue<int[]> queue = new LinkedList<>();
    
    // Add all gates to queue
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (rooms[r][c] == 0) queue.offer(new int[]{r, c});
        }
    }
    
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        
        for (int[] d : dirs) {
            int nr = curr[0] + d[0];
            int nc = curr[1] + d[1];
            
            // Only update empty rooms (INF)
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && rooms[nr][nc] == INF) {
                rooms[nr][nc] = rooms[curr[0]][curr[1]] + 1;
                queue.offer(new int[]{nr, nc});
            }
        }
    }
}
```

### Mind-Map Anchor

```
WALLS AND GATES
      |
      v
+-------------------+
| Multi-source BFS  |
| Queue ALL gates   |
| Spread distance   |
| Only update INF   |
+-------------------+
```

**Memory phrase:** "All gates start together, spread distance outward"

---

## PATTERN 21: 01 Matrix (LC 542)

### Pattern Recognition Signal

> **When you see:** "Distance to nearest 0 for each cell"
> **Instant thought:** "Multi-source BFS from all 0s!"

This is essentially the same as Walls and Gates!

### The Code

```java
public int[][] updateMatrix(int[][] mat) {
    int rows = mat.length, cols = mat[0].length;
    Queue<int[]> queue = new LinkedList<>();
    
    // Add all 0s to queue, mark 1s as unvisited (-1)
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (mat[r][c] == 0) {
                queue.offer(new int[]{r, c});
            } else {
                mat[r][c] = -1;  // Mark as unvisited
            }
        }
    }
    
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        
        for (int[] d : dirs) {
            int nr = curr[0] + d[0];
            int nc = curr[1] + d[1];
            
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && mat[nr][nc] == -1) {
                mat[nr][nc] = mat[curr[0]][curr[1]] + 1;
                queue.offer(new int[]{nr, nc});
            }
        }
    }
    
    return mat;
}
```

---

## PATTERN 22: Shortest Bridge (LC 934)

### Pattern Recognition Signal

> **When you see:** "Connect two islands with minimum cells" or "Shortest path between components"
> **Instant thought:** "DFS to find first island, BFS to expand until hitting second!"

### The Mental Model: Building a Bridge

1. Find the first island (DFS flood fill)
2. Add all its border cells to a queue
3. BFS expand outward (building bridge)
4. When you hit the second island, that's the shortest bridge!

### Visual Dry Run

```
Grid:
0 1 0
0 0 0
0 0 1

Step 1: Find first island with DFS
  Start at (0,1), mark as visited (change to 2)
  Grid: 0 2 0
        0 0 0
        0 0 1
  Queue (border of island 1): [(0,1)]

Step 2: BFS to expand
  Level 0: Process (0,1)
    Neighbors: (0,0), (0,2), (1,1) - all water
    Mark as visited, add to queue
    Bridge length so far: 0
  
  Level 1: Process (0,0), (0,2), (1,1)
    From (1,1): neighbor (2,1) is water
    From (1,1): neighbor (1,2) is water
    Bridge length: 1
  
  Level 2: Process (2,1), (1,2)
    From (1,2): neighbor (2,2) = 1 = SECOND ISLAND FOUND!
    Bridge length: 2

Return 2
```

### The Code

```java
public int shortestBridge(int[][] grid) {
    int n = grid.length;
    Queue<int[]> queue = new LinkedList<>();
    boolean found = false;
    
    // Step 1: Find first island with DFS, add border to queue
    for (int r = 0; r < n && !found; r++) {
        for (int c = 0; c < n && !found; c++) {
            if (grid[r][c] == 1) {
                dfs(grid, r, c, queue);
                found = true;
            }
        }
    }
    
    // Step 2: BFS to find second island
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    int bridge = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            
            for (int[] d : dirs) {
                int nr = curr[0] + d[0];
                int nc = curr[1] + d[1];
                
                if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                    if (grid[nr][nc] == 1) return bridge;  // Found second island!
                    if (grid[nr][nc] == 0) {
                        grid[nr][nc] = 2;  // Mark as visited
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }
        bridge++;
    }
    
    return -1;
}

private void dfs(int[][] grid, int r, int c, Queue<int[]> queue) {
    if (r < 0 || r >= grid.length || c < 0 || c >= grid.length || grid[r][c] != 1) {
        return;
    }
    grid[r][c] = 2;  // Mark as visited
    queue.offer(new int[]{r, c});  // Add to BFS queue
    
    dfs(grid, r+1, c, queue);
    dfs(grid, r-1, c, queue);
    dfs(grid, r, c+1, queue);
    dfs(grid, r, c-1, queue);
}
```

### Mind-Map Anchor

```
SHORTEST BRIDGE
      |
      v
+-------------------+
| 1. DFS find island|
| 2. Queue its cells|
| 3. BFS expand     |
| 4. Hit 1 = done!  |
+-------------------+
```

**Memory phrase:** "DFS to find, BFS to bridge"

---

## PATTERN 23: Evaluate Division (LC 399)

### Pattern Recognition Signal

> **When you see:** "A/B = x, B/C = y, find A/C" or "Path with multiplication"
> **Instant thought:** "Weighted graph! DFS/BFS multiply weights along path"

### The Mental Model: Currency Exchange

Think of it as currency exchange rates:
- USD/EUR = 2.0 means 1 USD = 2 EUR
- EUR/GBP = 3.0 means 1 EUR = 3 GBP
- What's USD/GBP? Follow the path: 1 USD → 2 EUR → 6 GBP = 6.0

### Visual Dry Run

```
Equations: [["a","b"], ["b","c"]]
Values: [2.0, 3.0]

Graph (bidirectional with reciprocal weights):
a --2.0--> b --3.0--> c
a <--0.5-- b <--0.33-- c

Query: a/c
  Path: a -> b -> c
  Weight: 2.0 * 3.0 = 6.0

Query: c/a
  Path: c -> b -> a
  Weight: 0.33 * 0.5 = 0.166...

Query: a/e
  'e' not in graph → -1.0
```

### The Code

```java
public double[] calcEquation(List<List<String>> equations, double[] values, 
                             List<List<String>> queries) {
    // Build weighted graph
    Map<String, Map<String, Double>> graph = new HashMap<>();
    
    for (int i = 0; i < equations.size(); i++) {
        String a = equations.get(i).get(0);
        String b = equations.get(i).get(1);
        double val = values[i];
        
        graph.putIfAbsent(a, new HashMap<>());
        graph.putIfAbsent(b, new HashMap<>());
        graph.get(a).put(b, val);       // a/b = val
        graph.get(b).put(a, 1.0 / val); // b/a = 1/val
    }
    
    // Process queries
    double[] result = new double[queries.size()];
    for (int i = 0; i < queries.size(); i++) {
        String src = queries.get(i).get(0);
        String dst = queries.get(i).get(1);
        
        if (!graph.containsKey(src) || !graph.containsKey(dst)) {
            result[i] = -1.0;
        } else if (src.equals(dst)) {
            result[i] = 1.0;
        } else {
            result[i] = dfs(graph, src, dst, new HashSet<>());
        }
    }
    return result;
}

private double dfs(Map<String, Map<String, Double>> graph, 
                   String curr, String target, Set<String> visited) {
    if (curr.equals(target)) return 1.0;
    
    visited.add(curr);
    
    for (Map.Entry<String, Double> neighbor : graph.get(curr).entrySet()) {
        if (!visited.contains(neighbor.getKey())) {
            double result = dfs(graph, neighbor.getKey(), target, visited);
            if (result != -1.0) {
                return neighbor.getValue() * result;  // Multiply along path!
            }
        }
    }
    
    return -1.0;
}
```

### Mind-Map Anchor

```
EVALUATE DIVISION
       |
       v
+---------------------+
| Weighted graph      |
| a/b=x → a->b (x)    |
|       → b->a (1/x)  |
| DFS multiply path   |
+---------------------+
```

**Memory phrase:** "Build weighted graph, multiply along the path"

---

## PATTERN 24: Longest Increasing Path in Matrix (LC 329)

### Pattern Recognition Signal

> **When you see:** "Longest path with increasing values" or "Path where each step is larger"
> **Instant thought:** "DFS + Memoization! Each cell stores its longest path"

### The Mental Model: The Mountain Climber

You're climbing a mountain. You can only step to HIGHER ground. What's the longest trail you can take?

Key insight: This is a DAG (Directed Acyclic Graph) because you can only go to strictly larger values - no cycles possible!

### Visual Dry Run

```
Matrix:
9 9 4
6 6 8
2 1 1

DFS with memoization:

Start from each cell, find longest path:

From (2,1)=1: Can go to 6,6,8,4,9,9 (all larger)
  Path: 1 -> 2 -> 6 -> 9 = length 4

Memo after full computation:
4 4 2
3 3 3
2 1 2

Maximum = 4
Path example: 1 -> 2 -> 6 -> 9
```

### The Code

```java
public int longestIncreasingPath(int[][] matrix) {
    if (matrix == null || matrix.length == 0) return 0;
    
    int rows = matrix.length, cols = matrix[0].length;
    int[][] memo = new int[rows][cols];  // memo[r][c] = longest path starting from (r,c)
    int maxPath = 0;
    
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            maxPath = Math.max(maxPath, dfs(matrix, r, c, memo));
        }
    }
    
    return maxPath;
}

private int dfs(int[][] matrix, int r, int c, int[][] memo) {
    if (memo[r][c] != 0) return memo[r][c];  // Already computed!
    
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    int maxLen = 1;  // At minimum, the cell itself
    
    for (int[] d : dirs) {
        int nr = r + d[0];
        int nc = c + d[1];
        
        // Only go to STRICTLY LARGER values
        if (nr >= 0 && nr < matrix.length && nc >= 0 && nc < matrix[0].length 
            && matrix[nr][nc] > matrix[r][c]) {
            maxLen = Math.max(maxLen, 1 + dfs(matrix, nr, nc, memo));
        }
    }
    
    memo[r][c] = maxLen;
    return maxLen;
}
```

### Mind-Map Anchor

```
LONGEST INCREASING PATH
         |
         v
+--------------------+
| DFS + Memoization  |
| Only go to LARGER  |
| No cycles (it's DAG)|
| memo[r][c] = answer|
+--------------------+
```

**Memory phrase:** "DFS to larger neighbors, memo the result"

---

# GRAPH PATTERNS REFERENCE SECTION

## BFS vs DFS Decision Tree

```
Need SHORTEST path/distance?
  → BFS (level = distance)

Need to EXPLORE ALL paths?
  → DFS (backtracking)

Need TOPOLOGICAL ORDER?
  → BFS (Kahn's) or DFS (post-order)

Need CYCLE DETECTION?
  → DFS (3-color) or BFS (Kahn's - incomplete sort = cycle)

Need to CLONE/COPY graph?
  → DFS or BFS with HashMap

Need CONNECTED COMPONENTS?
  → DFS or BFS or Union-Find
```

## The 4 vs 8 Directions

```java
// 4-directions (up, down, left, right)
int[][] dirs4 = {{0,1}, {1,0}, {0,-1}, {-1,0}};

// 8-directions (including diagonals)
int[][] dirs8 = {{0,1}, {1,0}, {0,-1}, {-1,0}, 
                 {1,1}, {1,-1}, {-1,1}, {-1,-1}};

// Knight moves (chess)
int[][] knight = {{2,1}, {2,-1}, {-2,1}, {-2,-1},
                  {1,2}, {1,-2}, {-1,2}, {-1,-2}};
```

## Multi-Source BFS Template

```java
// Add ALL sources to queue FIRST
Queue<int[]> queue = new LinkedList<>();
for (each source) {
    queue.offer(source);
    mark as visited;
}

// BFS - level = distance from nearest source
int distance = 0;
while (!queue.isEmpty()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
        int[] curr = queue.poll();
        // Process curr at distance 'distance'
        
        for (each neighbor) {
            if (valid && not visited) {
                mark visited;
                queue.offer(neighbor);
            }
        }
    }
    distance++;
}
```

## Topological Sort: Kahn's vs DFS

| Aspect | Kahn's (BFS) | DFS |
|--------|--------------|-----|
| Approach | Remove indegree-0 nodes | Post-order traversal |
| Cycle detection | result.size() < n | Gray meets gray |
| Output order | Direct order | Reverse of post-order |
| Space | O(V) for indegree array | O(V) for recursion stack |
| When to use | Need to process in order | Need reverse order |

## Cycle Detection Summary

| Graph Type | Method | Key Insight |
|------------|--------|-------------|
| Directed | 3-color DFS | Gray → Gray = cycle |
| Directed | Kahn's BFS | result.size() < n = cycle |
| Undirected | DFS with parent | Visit non-parent visited = cycle |
| Undirected | Union-Find | Same component edge = cycle |

## MAANG Graph Coverage Map

| Pattern | Frequency | Must Know |
|---------|-----------|-----------|
| Number of Islands | Very High | ⭐⭐⭐ |
| Clone Graph | High | ⭐⭐⭐ |
| Course Schedule I/II | Very High | ⭐⭐⭐ |
| Rotting Oranges | High | ⭐⭐⭐ |
| Pacific Atlantic | Medium | ⭐⭐ |
| Word Ladder | Medium | ⭐⭐ |
| Alien Dictionary | High | ⭐⭐⭐ |
| Is Graph Bipartite | Medium | ⭐⭐ |
| Evaluate Division | Medium | ⭐⭐ |
| Shortest Path Binary Matrix | Medium |
| Union-Find | Very High | ⭐⭐⭐ |
| Dijkstra | Very High | ⭐⭐⭐ |
| Word Ladder | High | ⭐⭐⭐ |
| Critical Connections | Medium | ⭐⭐ |

---

# PART 3: UNION-FIND (DISJOINT SET UNION)

## The Core Data Structure

Union-Find answers ONE question efficiently: **Are X and Y in the same group?**

### The Mental Model: Family Trees

Think of it as tracking family lineages:
- Each person points to their parent
- The "root" is the family patriarch
- Two people are related if they share the same patriarch

### The Template (MEMORIZE THIS!)

```java
class UnionFind {
    int[] parent;
    int[] rank;
    int components;
    
    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        components = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }
    
    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression
        }
        return parent[x];
    }
    
    boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) return false;
        
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        components--;
        return true;
    }
}
```

---

## PATTERN 25: Redundant Connection (LC 684)

### Pattern Recognition Signal

> **When you see:** "Remove one edge to make tree" or "Find edge that creates cycle"
> **Instant thought:** "Union-Find! Edge connecting same component = redundant"

### Visual Dry Run

```
Edges: [[1,2], [1,3], [2,3]]

Process [1,2]: find(1)=1, find(2)=2, different -> Union
Process [1,3]: find(1)=1, find(3)=3, different -> Union
Process [2,3]: find(2)=1, find(3)=1, SAME ROOT! -> Return [2,3]
```

### The Code

```java
public int[] findRedundantConnection(int[][] edges) {
    int n = edges.length;
    int[] parent = new int[n + 1];
    for (int i = 0; i <= n; i++) parent[i] = i;
    
    for (int[] edge : edges) {
        int rootA = find(parent, edge[0]);
        int rootB = find(parent, edge[1]);
        
        if (rootA == rootB) return edge;
        parent[rootA] = rootB;
    }
    return new int[0];
}
```

**Memory phrase:** "Union until same root - that's the cycle edge"

### Mind-Map Anchor

```
REDUNDANT CONNECTION
        |
        v
+-------------------+
| Process edges     |
| Union-Find each   |
| Same root = cycle |
| Return that edge  |
+-------------------+
```

---

## PATTERN 26: Graph Valid Tree (LC 261)

### The Rules for a Valid Tree

1. Exactly n-1 edges
2. All nodes connected (1 component)
3. No cycles

```java
public boolean validTree(int n, int[][] edges) {
    if (edges.length != n - 1) return false;
    
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    for (int[] edge : edges) {
        int rootA = find(parent, edge[0]);
        int rootB = find(parent, edge[1]);
        if (rootA == rootB) return false;
        parent[rootA] = rootB;
    }
    return true;
}
```

### Mind-Map Anchor

```
GRAPH VALID TREE
      |
      v
+------------------+
| n-1 edges?       |
| No cycles?       |
| All connected?   |
| Union-Find check |
+------------------+
```

**Memory phrase:** "n-1 edges, no cycles, all connected"

---

## PATTERN 27: Accounts Merge (LC 721)

### The Mental Model

Each email is a node. If two emails belong to same account, union them.

### Visual Dry Run

```
accounts = [
  ["John", "a@mail", "b@mail"],
  ["John", "c@mail"],
  ["John", "a@mail", "d@mail"]
]

Union emails in same account:
  Account 0: union(a, b)
  Account 2: union(a, d) -> {a,b,d} connected

Group by root -> Merge accounts sharing emails
```

### Mind-Map Anchor

```
ACCOUNTS MERGE
      |
      v
+--------------------+
| Email = node       |
| Same account=union |
| Group by root      |
| Sort & add name    |
+--------------------+
```

**Memory phrase:** "Union emails in same account, group by root"

---

# PART 4: SHORTEST PATH ALGORITHMS

| Algorithm | Weights | Time | Use Case |
|-----------|---------|------|----------|
| BFS | Unweighted | O(V+E) | Shortest hops |
| Dijkstra | Non-negative | O(E log V) | Shortest distance |
| Bellman-Ford | Any | O(VE) | Negative weights, K stops |
| Floyd-Warshall | Any | O(V3) | All pairs |

---

## PATTERN 28: Network Delay Time (LC 743) - Dijkstra

### Pattern Recognition Signal

> **When you see:** "Minimum time/cost to reach" with positive weights
> **Instant thought:** "Dijkstra! Priority queue by distance"

### Visual Dry Run

```
Graph: 1 --(1)--> 2 --(1)--> 3
Source: 1

PQ: [(0,1)]
Process (0,1): Update dist[2]=1, dist[4]=2
PQ: [(1,2), (2,4)]
Process (1,2): Update dist[3]=2
...
Max distance = answer
```

### The Code

```java
public int networkDelayTime(int[][] times, int n, int k) {
    Map<Integer, List<int[]>> graph = new HashMap<>();
    for (int[] t : times) {
        graph.computeIfAbsent(t[0], x -> new ArrayList<>()).add(new int[]{t[1], t[2]});
    }
    
    int[] dist = new int[n + 1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[k] = 0;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    pq.offer(new int[]{0, k});
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int d = curr[0], node = curr[1];
        
        if (d > dist[node]) continue;
        if (!graph.containsKey(node)) continue;
        
        for (int[] neighbor : graph.get(node)) {
            int next = neighbor[0], weight = neighbor[1];
            if (dist[node] + weight < dist[next]) {
                dist[next] = dist[node] + weight;
                pq.offer(new int[]{dist[next], next});
            }
        }
    }
    
    int max = 0;
    for (int i = 1; i <= n; i++) {
        if (dist[i] == Integer.MAX_VALUE) return -1;
        max = Math.max(max, dist[i]);
    }
    return max;
}
```

**Memory phrase:** "PQ by distance, process closest, relax edges"

### Mind-Map Anchor

```
DIJKSTRA
   |
   v
+--------------------+
| PQ by distance     |
| Process closest    |
| Relax neighbors    |
| Skip if outdated   |
+--------------------+
```

---

## PATTERN 29: Cheapest Flights K Stops (LC 787) - Bellman-Ford

### Pattern Recognition Signal

> **When you see:** "Shortest path with limited stops/edges"
> **Instant thought:** "Bellman-Ford! K+1 iterations"

### The Code

```java
public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    
    for (int i = 0; i <= k; i++) {
        int[] temp = dist.clone();
        for (int[] f : flights) {
            if (dist[f[0]] != Integer.MAX_VALUE) {
                temp[f[1]] = Math.min(temp[f[1]], dist[f[0]] + f[2]);
            }
        }
        dist = temp;
    }
    return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
}
```

**Memory phrase:** "K+1 iterations, clone before relax"

### Mind-Map Anchor

```
BELLMAN-FORD (K STOPS)
         |
         v
+---------------------+
| K+1 iterations      |
| Clone dist each iter|
| Relax ALL edges     |
| Use prev iter values|
+---------------------+
```

---

## PATTERN 30: Word Ladder (LC 127)

### Pattern Recognition Signal

> **When you see:** "Transform word one letter at a time"
> **Instant thought:** "BFS! Words are nodes, one-letter diff = edge"

### The Code

```java
public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord)) return 0;
    
    Queue<String> queue = new LinkedList<>();
    queue.offer(beginWord);
    Set<String> visited = new HashSet<>();
    visited.add(beginWord);
    int level = 1;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            String word = queue.poll();
            if (word.equals(endWord)) return level;
            
            char[] chars = word.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char orig = chars[j];
                for (char c = 'a'; c <= 'z'; c++) {
                    chars[j] = c;
                    String newWord = new String(chars);
                    if (wordSet.contains(newWord) && !visited.contains(newWord)) {
                        queue.offer(newWord);
                        visited.add(newWord);
                    }
                }
                chars[j] = orig;
            }
        }
        level++;
    }
    return 0;
}
```

### Mind-Map Anchor

```
WORD LADDER
    |
    v
+---------------------+
| BFS level = length  |
| Try all 26 letters  |
| Check in wordSet    |
| Mark visited        |
+---------------------+
```

**Memory phrase:** "BFS, try all letters, level = transformation count"

---

# PART 5: ADVANCED GRAPH PATTERNS

## PATTERN 31: Critical Connections (LC 1192) - Bridges

### Pattern Recognition Signal

> **When you see:** "Remove edge disconnects graph" or "Find critical edges"
> **Instant thought:** "Tarjan's algorithm! low[v] > disc[u] = bridge"

### The Code

```java
int time = 0;

public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    
    for (List<Integer> c : connections) {
        graph.get(c.get(0)).add(c.get(1));
        graph.get(c.get(1)).add(c.get(0));
    }
    
    int[] disc = new int[n], low = new int[n];
    Arrays.fill(disc, -1);
    List<List<Integer>> bridges = new ArrayList<>();
    
    dfs(0, -1, graph, disc, low, bridges);
    return bridges;
}

void dfs(int u, int parent, List<List<Integer>> graph, int[] disc, int[] low, List<List<Integer>> bridges) {
    disc[u] = low[u] = time++;
    
    for (int v : graph.get(u)) {
        if (v == parent) continue;
        if (disc[v] == -1) {
            dfs(v, u, graph, disc, low, bridges);
            low[u] = Math.min(low[u], low[v]);
            if (low[v] > disc[u]) bridges.add(Arrays.asList(u, v));
        } else {
            low[u] = Math.min(low[u], disc[v]);
        }
    }
}
```

**Memory phrase:** "Discovery time, low-link, low > disc = bridge"

### Mind-Map Anchor

```
CRITICAL CONNECTIONS (BRIDGES)
            |
            v
+------------------------+
| Tarjan's Algorithm     |
| disc[] = discovery time|
| low[] = min reachable  |
| low[v] > disc[u] = BRIDGE|
+------------------------+
```

---

## PATTERN 32: Reconstruct Itinerary (LC 332) - Eulerian Path

### Pattern Recognition Signal

> **When you see:** "Use all edges exactly once"
> **Instant thought:** "Hierholzer's algorithm! DFS, add when stuck"

### The Code

```java
public List<String> findItinerary(List<List<String>> tickets) {
    Map<String, PriorityQueue<String>> graph = new HashMap<>();
    for (List<String> t : tickets) {
        graph.computeIfAbsent(t.get(0), k -> new PriorityQueue<>()).offer(t.get(1));
    }
    
    LinkedList<String> result = new LinkedList<>();
    dfs("JFK", graph, result);
    return result;
}

void dfs(String airport, Map<String, PriorityQueue<String>> graph, LinkedList<String> result) {
    PriorityQueue<String> dests = graph.get(airport);
    while (dests != null && !dests.isEmpty()) {
        dfs(dests.poll(), graph, result);
    }
    result.addFirst(airport);
}
```

### Mind-Map Anchor

```
RECONSTRUCT ITINERARY (EULERIAN)
              |
              v
+------------------------+
| Hierholzer's Algorithm |
| PQ for lex order       |
| DFS greedily           |
| Add to front when stuck|
+------------------------+
```

**Memory phrase:** "Greedy DFS, add when stuck, result is reversed"

---

## Quick Reference: All 32 Graph Patterns

| # | Pattern | Technique |
|---|---------|-----------|
| 10-14 | Matrix BFS/DFS | Flood fill, Multi-source |
| 15-18 | Topo Sort/Cycle | 3-color, Kahn's, Bipartite |
| 19-24 | Advanced Matrix | Borders, Weighted, Memo |
| 25-27 | Union-Find | Cycle, Components, Tree |
| 28-30 | Shortest Path | Dijkstra, Bellman-Ford, BFS |
| 31-32 | Advanced | Tarjan Bridges, Eulerian |

---

*End of Graph Search Patterns Deep Dive*

---

## PATTERN 33: Flood Fill (LC 733)

### Pattern Recognition Signal

> **When you see:** "Change color of connected region" or "Paint bucket tool"
> **Instant thought:** "Simple DFS/BFS from starting cell!"

### The Mental Model: Paint Bucket

Like the paint bucket in MS Paint - click a pixel, all connected same-color pixels change.

### Visual Dry Run

```
Image: [[1,1,1],[1,1,0],[1,0,1]], sr=1, sc=1, color=2

Start at (1,1)=1, change to 2
DFS to all connected 1s:

Step 1: (1,1)=1 -> 2
Step 2: (0,1)=1 -> 2, (1,0)=1 -> 2
Step 3: (0,0)=1 -> 2, (0,2)=1 -> 2

Result: [[2,2,2],[2,2,0],[2,0,1]]
```

### The Code

```java
public int[][] floodFill(int[][] image, int sr, int sc, int color) {
    int originalColor = image[sr][sc];
    if (originalColor == color) return image;  // Already target color!
    
    dfs(image, sr, sc, originalColor, color);
    return image;
}

private void dfs(int[][] image, int r, int c, int original, int newColor) {
    if (r < 0 || r >= image.length || c < 0 || c >= image[0].length) return;
    if (image[r][c] != original) return;
    
    image[r][c] = newColor;
    dfs(image, r+1, c, original, newColor);
    dfs(image, r-1, c, original, newColor);
    dfs(image, r, c+1, original, newColor);
    dfs(image, r, c-1, original, newColor);
}
```

### Mind-Map Anchor

```
FLOOD FILL
    |
    v
+------------------+
| Start at (sr,sc) |
| DFS to same color|
| Change to new    |
| Check orig!=new  |
+------------------+
```

**Memory phrase:** "Paint bucket - DFS to same color neighbors"


---

## PATTERN 34: Word Search II (LC 212) - Trie + DFS

### Pattern Recognition Signal

> **When you see:** "Find multiple words in grid" or "Search many patterns"
> **Instant thought:** "Trie for words + DFS on grid!"

### The Mental Model: Dictionary Search

Instead of searching each word separately O(words * m*n), build a Trie of all words. DFS once, checking Trie at each step.

### Visual Dry Run

```
Board: [["o","a","a","n"],
        ["e","t","a","e"],
        ["i","h","k","r"],
        ["i","f","l","v"]]
Words: ["oath","pea","eat","rain"]

Build Trie:
       root
      / | \ \
     o  p  e  r
     |  |  |  |
     a  e  a  a
     |  |  |  |
     t  a  t  i
     |        |
     h        n

DFS from each cell, follow Trie:
- Start (0,0)='o': Trie has 'o' -> continue
- (1,0)='e': no 'oe' in Trie, backtrack
- (0,1)='a': Trie o->a exists -> continue
- (1,1)='t': Trie o->a->t exists -> continue
- (2,1)='h': Trie o->a->t->h exists -> "oath" FOUND!

Result: ["oath", "eat"]
```

### The Code

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    String word = null;  // Store word at end node
}

public List<String> findWords(char[][] board, String[] words) {
    // Build Trie
    TrieNode root = new TrieNode();
    for (String word : words) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new TrieNode();
            }
            node = node.children[c - 'a'];
        }
        node.word = word;
    }
    
    List<String> result = new ArrayList<>();
    
    for (int r = 0; r < board.length; r++) {
        for (int c = 0; c < board[0].length; c++) {
            dfs(board, r, c, root, result);
        }
    }
    return result;
}

private void dfs(char[][] board, int r, int c, TrieNode node, List<String> result) {
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) return;
    
    char ch = board[r][c];
    if (ch == '#' || node.children[ch - 'a'] == null) return;
    
    node = node.children[ch - 'a'];
    
    if (node.word != null) {
        result.add(node.word);
        node.word = null;  // Avoid duplicates
    }
    
    board[r][c] = '#';  // Mark visited
    dfs(board, r+1, c, node, result);
    dfs(board, r-1, c, node, result);
    dfs(board, r, c+1, node, result);
    dfs(board, r, c-1, node, result);
    board[r][c] = ch;   // Restore
}
```

### Mind-Map Anchor

```
WORD SEARCH II
      |
      v
+--------------------+
| Build Trie of words|
| DFS from each cell |
| Follow Trie path   |
| Mark word=null used|
+--------------------+
```

**Memory phrase:** "Trie holds words, DFS follows Trie path"


---

## PATTERN 35: Number of Provinces (LC 547)

### Pattern Recognition Signal

> **When you see:** "Count groups in adjacency matrix" or "Friend circles"
> **Instant thought:** "Union-Find or DFS - count connected components!"

### Visual Dry Run

```
isConnected = [[1,1,0],
               [1,1,0],
               [0,0,1]]

Person 0 connected to 1 (isConnected[0][1]=1)
Person 2 alone

DFS/Union-Find:
- Start person 0: visit 0, visit 1 (connected), province 1
- Start person 1: already visited
- Start person 2: visit 2, province 2

Answer: 2 provinces
```

### The Code

```java
public int findCircleNum(int[][] isConnected) {
    int n = isConnected.length;
    boolean[] visited = new boolean[n];
    int provinces = 0;
    
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            dfs(isConnected, visited, i);
            provinces++;
        }
    }
    return provinces;
}

private void dfs(int[][] isConnected, boolean[] visited, int person) {
    visited[person] = true;
    for (int other = 0; other < isConnected.length; other++) {
        if (isConnected[person][other] == 1 && !visited[other]) {
            dfs(isConnected, visited, other);
        }
    }
}
```

### Mind-Map Anchor

```
NUMBER OF PROVINCES
        |
        v
+-------------------+
| Adjacency matrix  |
| DFS from unvisited|
| Count DFS starts  |
| Or use Union-Find |
+-------------------+
```

**Memory phrase:** "Count DFS starts = count provinces"


---

## PATTERN 36: Keys and Rooms (LC 841)

### Pattern Recognition Signal

> **When you see:** "Collect keys to unlock rooms" or "Can visit all with collected items"
> **Instant thought:** "DFS/BFS - keys are edges to new nodes!"

### Visual Dry Run

```
rooms = [[1],[2],[3],[]]

Room 0: has key to room 1
Room 1: has key to room 2
Room 2: has key to room 3
Room 3: empty

Start in room 0 (unlocked):
- Visit 0, get key 1
- Visit 1, get key 2
- Visit 2, get key 3
- Visit 3, no new keys

Visited all 4 rooms? YES!
```

### The Code

```java
public boolean canVisitAllRooms(List<List<Integer>> rooms) {
    boolean[] visited = new boolean[rooms.size()];
    dfs(rooms, 0, visited);
    
    for (boolean v : visited) {
        if (!v) return false;
    }
    return true;
}

private void dfs(List<List<Integer>> rooms, int room, boolean[] visited) {
    if (visited[room]) return;
    visited[room] = true;
    
    for (int key : rooms.get(room)) {
        dfs(rooms, key, visited);
    }
}
```

### Mind-Map Anchor

```
KEYS AND ROOMS
      |
      v
+------------------+
| Room 0 unlocked  |
| Keys = edges     |
| DFS collect keys |
| Check all visited|
+------------------+
```

**Memory phrase:** "Start room 0, keys unlock rooms, DFS to all"


---

## PATTERN 37: Possible Bipartition (LC 886)

### Pattern Recognition Signal

> **When you see:** "Split into two groups, enemies apart" or "No two disliked in same group"
> **Instant thought:** "Bipartite check! 2-color the graph"

### Visual Dry Run

```
n = 4, dislikes = [[1,2],[1,3],[2,4]]

Build graph: 1-2, 1-3, 2-4

2-coloring:
- Color 1 = RED
- Color 2 = BLUE (enemy of 1)
- Color 3 = BLUE (enemy of 1)
- Color 4 = RED (enemy of 2 which is BLUE)

Check: No two same-color connected? YES!
Answer: true
```

### The Code

```java
public boolean possibleBipartition(int n, int[][] dislikes) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
    
    for (int[] d : dislikes) {
        graph.get(d[0]).add(d[1]);
        graph.get(d[1]).add(d[0]);
    }
    
    int[] color = new int[n + 1];  // 0=uncolored, 1=red, -1=blue
    
    for (int i = 1; i <= n; i++) {
        if (color[i] == 0 && !bfs(graph, i, color)) {
            return false;
        }
    }
    return true;
}

private boolean bfs(List<List<Integer>> graph, int start, int[] color) {
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(start);
    color[start] = 1;
    
    while (!queue.isEmpty()) {
        int node = queue.poll();
        for (int neighbor : graph.get(node)) {
            if (color[neighbor] == 0) {
                color[neighbor] = -color[node];
                queue.offer(neighbor);
            } else if (color[neighbor] == color[node]) {
                return false;
            }
        }
    }
    return true;
}
```

### Mind-Map Anchor

```
POSSIBLE BIPARTITION
        |
        v
+--------------------+
| Dislikes = edges   |
| 2-color the graph  |
| Same color = FAIL  |
| Bipartite = split  |
+--------------------+
```

**Memory phrase:** "Enemies get opposite colors, same color = can't split"


---

## PATTERN 38: Find Eventual Safe States (LC 802)

### Pattern Recognition Signal

> **When you see:** "Nodes that don't lead to cycles" or "Terminal or leads to terminal"
> **Instant thought:** "Reverse graph + topo sort OR 3-color cycle detection!"

### The Mental Model

Safe node = eventually reaches terminal (no outgoing edges). Unsafe = part of cycle or leads to cycle.

### Visual Dry Run

```
graph = [[1,2],[2,3],[5],[0],[5],[],[]]

0 -> 1,2
1 -> 2,3
2 -> 5
3 -> 0 (CYCLE: 0->1->3->0)
4 -> 5
5 -> (terminal)
6 -> (terminal)

3-color DFS:
- Node 5: no neighbors, safe (BLACK)
- Node 6: no neighbors, safe (BLACK)
- Node 2: leads to 5 (safe), safe (BLACK)
- Node 4: leads to 5 (safe), safe (BLACK)
- Node 1: leads to 2(safe), 3...
- Node 3: leads to 0 -> 1 -> 3 (GRAY meets GRAY = CYCLE!)
- Node 0,1,3: unsafe

Safe: [2, 4, 5, 6]
```

### The Code

```java
public List<Integer> eventualSafeNodes(int[][] graph) {
    int n = graph.length;
    int[] color = new int[n];  // 0=white, 1=gray, 2=black
    List<Integer> result = new ArrayList<>();
    
    for (int i = 0; i < n; i++) {
        if (isSafe(graph, i, color)) {
            result.add(i);
        }
    }
    return result;
}

private boolean isSafe(int[][] graph, int node, int[] color) {
    if (color[node] > 0) return color[node] == 2;  // Gray=unsafe, Black=safe
    
    color[node] = 1;  // Gray: processing
    
    for (int neighbor : graph[node]) {
        if (!isSafe(graph, neighbor, color)) {
            return false;  // Leads to cycle
        }
    }
    
    color[node] = 2;  // Black: safe
    return true;
}
```

### Mind-Map Anchor

```
EVENTUAL SAFE STATES
         |
         v
+---------------------+
| 3-color DFS         |
| Gray = in progress  |
| Black = confirmed safe|
| Gray->Gray = cycle  |
+---------------------+
```

**Memory phrase:** "Black nodes are safe, gray means cycle danger"


---

## PATTERN 39: Minimum Height Trees (LC 310)

### Pattern Recognition Signal

> **When you see:** "Find roots that minimize tree height" or "Center of tree"
> **Instant thought:** "Trim leaves layer by layer until 1-2 nodes remain!"

### The Mental Model: Peeling an Onion

Keep removing the outermost layer (leaves with degree 1) until you reach the center. The center is the root(s) for minimum height tree.

### Visual Dry Run

```
n = 6, edges = [[3,0],[3,1],[3,2],[3,4],[5,4]]

Tree:    0
         |
     1 - 3 - 2
         |
         4
         |
         5

Round 1: Remove leaves (degree 1): 0, 1, 2, 5
  Remaining: 3, 4

Round 2: Remove leaves: 4
  Remaining: 3

Only 1 node left: [3] is the answer!
```

### The Code

```java
public List<Integer> findMinHeightTrees(int n, int[][] edges) {
    if (n == 1) return Arrays.asList(0);
    
    List<Set<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new HashSet<>());
    
    for (int[] e : edges) {
        graph.get(e[0]).add(e[1]);
        graph.get(e[1]).add(e[0]);
    }
    
    // Find initial leaves (degree 1)
    Queue<Integer> leaves = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (graph.get(i).size() == 1) leaves.offer(i);
    }
    
    int remaining = n;
    
    // Trim leaves until 1-2 nodes remain
    while (remaining > 2) {
        int size = leaves.size();
        remaining -= size;
        
        for (int i = 0; i < size; i++) {
            int leaf = leaves.poll();
            int neighbor = graph.get(leaf).iterator().next();
            graph.get(neighbor).remove(leaf);
            
            if (graph.get(neighbor).size() == 1) {
                leaves.offer(neighbor);
            }
        }
    }
    
    return new ArrayList<>(leaves);
}
```

### Mind-Map Anchor

```
MINIMUM HEIGHT TREES
         |
         v
+---------------------+
| Trim leaves (deg 1) |
| Layer by layer      |
| Until 1-2 remain    |
| Those are centers   |
+---------------------+
```

**Memory phrase:** "Peel leaves until center remains"


---

## PATTERN 40: Shortest Path with Obstacles Elimination (LC 1293)

### Pattern Recognition Signal

> **When you see:** "Shortest path with K removals allowed" or "BFS with extra state"
> **Instant thought:** "BFS with state (row, col, remaining_k)!"

### The Mental Model: 3D BFS

Normal BFS is 2D (row, col). With K obstacles allowed, add third dimension: how many removals left.

### Visual Dry Run

```
grid = [[0,0,0],
        [1,1,0],
        [0,0,0],
        [0,1,1],
        [0,0,0]], k = 1

BFS with state (r, c, k_remaining):
Start: (0,0,1)

Level 0: (0,0,1)
Level 1: (0,1,1), (1,0,0) <- used 1 removal on obstacle
Level 2: (0,2,1), (1,1,0) <- another removal but k=0 now
...

Shortest path using 1 removal = 6 steps
```

### The Code

```java
public int shortestPath(int[][] grid, int k) {
    int m = grid.length, n = grid[0].length;
    
    // State: (row, col, remaining_k)
    boolean[][][] visited = new boolean[m][n][k + 1];
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{0, 0, k});
    visited[0][0][k] = true;
    
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    int steps = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1], rem = curr[2];
            
            if (r == m-1 && c == n-1) return steps;
            
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
                
                int newRem = rem - grid[nr][nc];  // Use removal if obstacle
                
                if (newRem >= 0 && !visited[nr][nc][newRem]) {
                    visited[nr][nc][newRem] = true;
                    queue.offer(new int[]{nr, nc, newRem});
                }
            }
        }
        steps++;
    }
    return -1;
}
```

### Mind-Map Anchor

```
SHORTEST PATH K OBSTACLES
           |
           v
+------------------------+
| State = (r, c, k_left) |
| BFS level = distance   |
| Obstacle costs 1 k     |
| 3D visited array       |
+------------------------+
```

**Memory phrase:** "BFS with extra dimension for remaining removals"


---

## PATTERN 41: Path With Minimum Effort (LC 1631)

### Pattern Recognition Signal

> **When you see:** "Minimize maximum edge weight" or "Path with least effort"
> **Instant thought:** "Dijkstra! But track max edge, not sum"

### The Mental Model: Mountain Hiking

You want the easiest hike. Effort = maximum height difference on your path. Find path that minimizes this maximum.

### Visual Dry Run

```
heights = [[1,2,2],
           [3,8,2],
           [5,3,5]]

Dijkstra (min-heap by effort):
Start (0,0) effort=0

Process (0,0): neighbors (0,1) effort=|2-1|=1, (1,0) effort=|3-1|=2
Heap: [(1,(0,1)), (2,(1,0))]

Process (0,1): neighbor (0,2) effort=max(1,|2-2|)=1
Heap: [(1,(0,2)), (2,(1,0)), ...]

Process (0,2): neighbor (1,2) effort=max(1,|2-2|)=1
...

Reach (2,2) with effort=2
```

### The Code

```java
public int minimumEffortPath(int[][] heights) {
    int m = heights.length, n = heights[0].length;
    int[][] effort = new int[m][n];
    for (int[] row : effort) Arrays.fill(row, Integer.MAX_VALUE);
    effort[0][0] = 0;
    
    // Min-heap: (effort, row, col)
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);
    pq.offer(new int[]{0, 0, 0});
    
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int e = curr[0], r = curr[1], c = curr[2];
        
        if (r == m-1 && c == n-1) return e;
        if (e > effort[r][c]) continue;
        
        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1];
            if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
            
            int newEffort = Math.max(e, Math.abs(heights[nr][nc] - heights[r][c]));
            
            if (newEffort < effort[nr][nc]) {
                effort[nr][nc] = newEffort;
                pq.offer(new int[]{newEffort, nr, nc});
            }
        }
    }
    return 0;
}
```

### Mind-Map Anchor

```
PATH MIN EFFORT
      |
      v
+--------------------+
| Dijkstra variant   |
| Track MAX edge     |
| Not sum of edges   |
| Min-heap by effort |
+--------------------+
```

**Memory phrase:** "Dijkstra but max edge weight, not sum"


---

## PATTERN 42: Swim in Rising Water (LC 778)

### Pattern Recognition Signal

> **When you see:** "Wait for water level to rise" or "Min time to traverse"
> **Instant thought:** "Dijkstra or Binary Search + BFS!"

### The Mental Model

Water rises over time. At time t, you can swim through cells with elevation <= t. Find minimum t to reach end.

### The Code (Dijkstra approach)

```java
public int swimInWater(int[][] grid) {
    int n = grid.length;
    int[][] time = new int[n][n];
    for (int[] row : time) Arrays.fill(row, Integer.MAX_VALUE);
    
    // Min-heap: (time_needed, row, col)
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);
    pq.offer(new int[]{grid[0][0], 0, 0});
    time[0][0] = grid[0][0];
    
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int t = curr[0], r = curr[1], c = curr[2];
        
        if (r == n-1 && c == n-1) return t;
        
        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1];
            if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
            
            int newTime = Math.max(t, grid[nr][nc]);
            
            if (newTime < time[nr][nc]) {
                time[nr][nc] = newTime;
                pq.offer(new int[]{newTime, nr, nc});
            }
        }
    }
    return -1;
}
```

### Mind-Map Anchor

```
SWIM IN RISING WATER
        |
        v
+---------------------+
| Dijkstra: max elev  |
| Or Binary Search+BFS|
| Time = max cell val |
| Along the path      |
+---------------------+
```

**Memory phrase:** "Dijkstra tracking max elevation on path"


---

## PATTERN 43: All Paths From Source to Target (LC 797)

### Pattern Recognition Signal

> **When you see:** "Find ALL paths in DAG" or "List every route"
> **Instant thought:** "Backtracking DFS! No visited needed (it's a DAG)"

### Visual Dry Run

```
graph = [[1,2],[3],[3],[]]

0 -> 1, 2
1 -> 3
2 -> 3
3 -> (end)

DFS backtracking:
Path: [0]
  -> [0,1] -> [0,1,3] FOUND! Backtrack
  -> [0,2] -> [0,2,3] FOUND! Backtrack

Result: [[0,1,3], [0,2,3]]
```

### The Code

```java
public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    path.add(0);
    
    dfs(graph, 0, path, result);
    return result;
}

private void dfs(int[][] graph, int node, List<Integer> path, List<List<Integer>> result) {
    if (node == graph.length - 1) {
        result.add(new ArrayList<>(path));  // Found path to end!
        return;
    }
    
    for (int next : graph[node]) {
        path.add(next);
        dfs(graph, next, path, result);
        path.remove(path.size() - 1);  // Backtrack
    }
}
```

### Mind-Map Anchor

```
ALL PATHS SOURCE TO TARGET
           |
           v
+----------------------+
| DAG = no cycles      |
| No visited needed    |
| Backtrack to find all|
| Copy path when done  |
+----------------------+
```

**Memory phrase:** "DAG backtracking - add, recurse, remove"


---

## PATTERN 44: Most Stones Removed (LC 947)

### Pattern Recognition Signal

> **When you see:** "Remove stones sharing row/col" or "Connected by coordinate"
> **Instant thought:** "Union-Find! Stones in same row/col = same component"

### The Mental Model

Stones sharing row OR column are connected. In each connected component of size k, we can remove k-1 stones (leave 1).

Answer = total stones - number of components

### Visual Dry Run

```
stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]

Connections:
(0,0)-(0,1) same row
(0,0)-(1,0) same col
(1,0)-(1,2) same row
(0,1)-(2,1) same col
(1,2)-(2,2) same col
(2,1)-(2,2) same row

All connected! 1 component.
Remove: 6 - 1 = 5 stones
```

### The Code

```java
public int removeStones(int[][] stones) {
    int n = stones.length;
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    // Union stones sharing row or column
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (stones[i][0] == stones[j][0] || stones[i][1] == stones[j][1]) {
                union(parent, i, j);
            }
        }
    }
    
    // Count components
    int components = 0;
    for (int i = 0; i < n; i++) {
        if (find(parent, i) == i) components++;
    }
    
    return n - components;
}

private int find(int[] parent, int x) {
    if (parent[x] != x) parent[x] = find(parent, parent[x]);
    return parent[x];
}

private void union(int[] parent, int x, int y) {
    parent[find(parent, x)] = find(parent, y);
}
```

### Mind-Map Anchor

```
MOST STONES REMOVED
        |
        v
+---------------------+
| Same row/col = edge |
| Union-Find groups   |
| Remove = n - groups |
| Keep 1 per component|
+---------------------+
```

**Memory phrase:** "Union by row/col, remove = total - components"


---

## PATTERN 45: Unique Paths III (LC 980)

### Pattern Recognition Signal

> **When you see:** "Visit every empty cell exactly once" or "Hamiltonian path count"
> **Instant thought:** "Backtracking with cell count!"

### Visual Dry Run

```
grid = [[1,0,0,0],
        [0,0,0,0],
        [0,0,2,-1]]

1 = start, 2 = end, 0 = empty, -1 = obstacle
Empty cells to visit: 10 (including start, excluding obstacle)

Backtrack from start, count cells visited:
Path 1: 1->right->right->right->down->down->left->2 (visits all)
Path 2: 1->down->down->right->right->up->right->down->2 (visits all)
...

Count paths that visit exactly all empty cells.
```

### The Code

```java
int result = 0;
int emptyCount = 0;

public int uniquePathsIII(int[][] grid) {
    int startR = 0, startC = 0;
    
    // Count empty cells (including start)
    for (int r = 0; r < grid.length; r++) {
        for (int c = 0; c < grid[0].length; c++) {
            if (grid[r][c] == 0) emptyCount++;
            else if (grid[r][c] == 1) { startR = r; startC = c; emptyCount++; }
        }
    }
    
    backtrack(grid, startR, startC, 0);
    return result;
}

private void backtrack(int[][] grid, int r, int c, int count) {
    if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return;
    if (grid[r][c] == -1 || grid[r][c] == 3) return;  // Obstacle or visited
    
    if (grid[r][c] == 2) {
        if (count == emptyCount) result++;  // Visited all empty cells!
        return;
    }
    
    int temp = grid[r][c];
    grid[r][c] = 3;  // Mark visited
    count++;
    
    backtrack(grid, r+1, c, count);
    backtrack(grid, r-1, c, count);
    backtrack(grid, r, c+1, count);
    backtrack(grid, r, c-1, count);
    
    grid[r][c] = temp;  // Restore
}
```

### Mind-Map Anchor

```
UNIQUE PATHS III
      |
      v
+---------------------+
| Count empty cells   |
| Backtrack all paths |
| Check count at end  |
| Must visit ALL      |
+---------------------+
```

**Memory phrase:** "Backtrack, count cells, valid only if visited all"


---

## PATTERN 46: Parallel Courses (LC 1136)

### Pattern Recognition Signal

> **When you see:** "Minimum semesters to finish all courses" or "Levels in topo sort"
> **Instant thought:** "Kahn's BFS - count levels!"

### Visual Dry Run

```
n = 3, relations = [[1,3],[2,3]]

1 -> 3
2 -> 3

Indegrees: [0, 0, 2] (1-indexed: course 1=0, 2=0, 3=2)

Semester 1: Take courses with indegree 0 -> [1, 2]
  Reduce indegree of 3: now 0
  
Semester 2: Take course 3

Total: 2 semesters
```

### The Code

```java
public int minimumSemesters(int n, int[][] relations) {
    List<List<Integer>> graph = new ArrayList<>();
    int[] indegree = new int[n + 1];
    
    for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
    
    for (int[] r : relations) {
        graph.get(r[0]).add(r[1]);
        indegree[r[1]]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 1; i <= n; i++) {
        if (indegree[i] == 0) queue.offer(i);
    }
    
    int semesters = 0;
    int completed = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        semesters++;
        
        for (int i = 0; i < size; i++) {
            int course = queue.poll();
            completed++;
            
            for (int next : graph.get(course)) {
                if (--indegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
    }
    
    return completed == n ? semesters : -1;
}
```

### Mind-Map Anchor

```
PARALLEL COURSES
       |
       v
+--------------------+
| Kahn's BFS         |
| Level = semester   |
| Take all indeg=0   |
| Count levels       |
+--------------------+
```

**Memory phrase:** "Kahn's levels = minimum semesters"


---

## PATTERN 47: Redundant Connection II (LC 685) - Directed Graph

### Pattern Recognition Signal

> **When you see:** "Remove edge to make valid rooted tree" in DIRECTED graph
> **Instant thought:** "Check for node with 2 parents + cycle detection!"

### The Key Insight

In a valid rooted tree:
1. Every node except root has exactly 1 parent
2. No cycles

Two cases:
- Case 1: A node has 2 parents -> one edge is redundant
- Case 2: There's a cycle -> edge in cycle is redundant

### The Code

```java
public int[] findRedundantDirectedConnection(int[][] edges) {
    int n = edges.length;
    int[] parent = new int[n + 1];
    int[] candidate1 = null, candidate2 = null;
    
    // Find node with 2 parents
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1];
        if (parent[v] != 0) {
            candidate1 = new int[]{parent[v], v};  // First parent edge
            candidate2 = new int[]{u, v};          // Second parent edge
            edge[1] = 0;  // Temporarily remove candidate2
        } else {
            parent[v] = u;
        }
    }
    
    // Union-Find to detect cycle
    for (int i = 0; i <= n; i++) parent[i] = i;
    
    for (int[] edge : edges) {
        if (edge[1] == 0) continue;  // Skip removed edge
        
        int u = edge[0], v = edge[1];
        int pu = find(parent, u);
        
        if (pu == v) {
            // Cycle found
            return candidate1 == null ? edge : candidate1;
        }
        parent[v] = pu;
    }
    
    return candidate2;  // No cycle, candidate2 is the answer
}

private int find(int[] parent, int x) {
    if (parent[x] != x) parent[x] = find(parent, parent[x]);
    return parent[x];
}
```

### Mind-Map Anchor

```
REDUNDANT CONNECTION II
          |
          v
+------------------------+
| Check 2-parent node    |
| Union-Find for cycle   |
| 2 parents + cycle: 1st |
| 2 parents no cycle: 2nd|
| No 2-parent: cycle edge|
+------------------------+
```

**Memory phrase:** "Two parents or cycle - find the bad edge"

---

# UPDATED Quick Reference: All 47 Graph Patterns

| # | Pattern | Technique |
|---|---------|-----------|
| 10-14 | Matrix BFS/DFS | Flood fill, Multi-source |
| 15-18 | Topo Sort/Cycle | 3-color, Kahn's, Bipartite |
| 19-24 | Advanced Matrix | Borders, Weighted, Memo |
| 25-27 | Union-Find | Cycle, Components, Tree |
| 28-32 | Shortest Path | Dijkstra, Bellman-Ford, Tarjan |
| 33-37 | More BFS/DFS | Flood, Trie+DFS, Bipartite |
| 38-42 | Advanced | Safe states, MHT, 3D BFS |
| 43-47 | Backtrack/UF | All paths, Stones, Directed |

---

*End of Graph Search Patterns Deep Dive - 47 Patterns for L5 MAANG*

